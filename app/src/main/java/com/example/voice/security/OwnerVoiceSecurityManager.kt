package com.example.voice.security

import com.example.model.RiskTier
import com.example.voice.diarization.IdentifiedSpeakerType
import java.util.UUID

/**
 * OWNER VOICE SECURITY & AUTHORIZATION POLICY MANAGER
 *
 * Implements the core Sovereign Principles:
 * 1. "Hear Everyone. Separate Speakers. Identify the Owner. Verify the Owner. Detect Spoofing. Authorize Only the Owner."
 * 2. Independent Verification:
 *    - Speech Recognition Confidence (e.g. 96%)
 *    - Owner Speaker Biometric Confidence (e.g. 99%)
 *    - Liveness / Anti-Spoof Confidence (e.g. 99%)
 * 3. Strict Non-Owner Isolation:
 *    - Unknown / Guest speakers cannot access personal memory, files, IoT devices, or trigger automations.
 *    - Fallback: "உரிமையாளரின் குரலை உறுதிப்படுத்த முடியவில்லை." (Cannot verify owner voice).
 * 4. High-Risk Multi-Factor Escalation:
 *    - Actions in HIGH_EXPLICIT_CONFIRM or CRITICAL_MULTI_STEP require re-verification or explicit approval.
 * 5. Time-Limited Secure Session Continuity.
 */

enum class DeviceTrustLevel(val labelTa: String, val trustWeight: Float) {
  TRUSTED_ON_DEVICE_HARDWARE("நம்பகமான சாதன மைக்ரோஃபோன் (Trusted Device)", 1.0f),
  PAIRED_ENCRYPTED_BLUETOOTH("குறியாக்கம் செய்யப்பட்ட புளூடூத் (Paired Bluetooth)", 0.90f),
  REMOTE_NETWORK_STREAM("தொலைநிலை இணைப்பு (Remote Audio Stream)", 0.65f),
  UNTRUSTED_EXTERNAL_PORT("தெரியாத வெளிப்புற சாதனம் (Untrusted External)", 0.40f)
}

data class OwnerVoiceSessionState(
  val sessionToken: String = UUID.randomUUID().toString(),
  val isOwnerAuthenticated: Boolean = false,
  val authenticatedTimestampMs: Long = 0L,
  val sessionExpiryDurationMs: Long = 90_000L, // 90-second sliding session window
  val activeSpeakerType: IdentifiedSpeakerType = IdentifiedSpeakerType.UNKNOWN_SPEAKER,
  val lastBiometricConfidence: Float = 0.0f,
  val lastLivenessConfidence: Float = 0.0f,
  val deviceTrustLevel: DeviceTrustLevel = DeviceTrustLevel.TRUSTED_ON_DEVICE_HARDWARE,
  val consecutiveVerifiedUtterances: Int = 0
) {
  val isSessionActive: Boolean
    get() = isOwnerAuthenticated && (System.currentTimeMillis() - authenticatedTimestampMs) < sessionExpiryDurationMs
}

data class OwnerAuthorizationEvaluation(
  val isActionPermitted: Boolean,
  val speakerIdentified: IdentifiedSpeakerType,
  val speechRecognitionConfidence: Float,
  val ownerBiometricConfidence: Float,
  val livenessConfidence: Float,
  val evaluatedRiskTier: RiskTier,
  val requiresSecondaryConfirmation: Boolean,
  val denialReasonTa: String? = null,
  val denialReasonEn: String? = null,
  val secureSpokenResponseTa: String
)

interface OwnerVoiceSecurityManager {
  /**
   * Evaluate full biometric, liveness, and speaker attribution chain for an incoming voice utterance
   */
  suspend fun evaluateVoiceAuthorization(
    speakerType: IdentifiedSpeakerType,
    speechConfidence: Float,
    biometricResult: BiometricVerificationResult,
    livenessResult: LivenessVerificationResult,
    targetRiskTier: RiskTier,
    isExplicitWakeWordPresent: Boolean = false
  ): OwnerAuthorizationEvaluation

  /**
   * Get active session state
   */
  fun getCurrentSessionState(): OwnerVoiceSessionState

  /**
   * Invalidate active authenticated owner session
   */
  fun revokeOwnerSession()

  /**
   * Reset session timer on continuous active verified conversation
   */
  fun extendSessionContinuity()
}

/**
 * Production-grade Owner Voice Security Manager implementation
 */
class DefaultOwnerVoiceSecurityManager(
  private val minBiometricThreshold: Float = 0.88f,
  private val minLivenessThreshold: Float = 0.85f,
  private val minSpeechConfidence: Float = 0.80f
) : OwnerVoiceSecurityManager {

  private var sessionState = OwnerVoiceSessionState()

  override suspend fun evaluateVoiceAuthorization(
    speakerType: IdentifiedSpeakerType,
    speechConfidence: Float,
    biometricResult: BiometricVerificationResult,
    livenessResult: LivenessVerificationResult,
    targetRiskTier: RiskTier,
    isExplicitWakeWordPresent: Boolean
  ): OwnerAuthorizationEvaluation {
    val currentTime = System.currentTimeMillis()

    // 1. Check Anti-Spoofing & Liveness first
    if (!livenessResult.isLiveSpeech || livenessResult.livenessConfidenceScore < minLivenessThreshold) {
      return OwnerAuthorizationEvaluation(
        isActionPermitted = false,
        speakerIdentified = speakerType,
        speechRecognitionConfidence = speechConfidence,
        ownerBiometricConfidence = biometricResult.biometricSimilarityScore,
        livenessConfidence = livenessResult.livenessConfidenceScore,
        evaluatedRiskTier = targetRiskTier,
        requiresSecondaryConfirmation = false,
        denialReasonTa = "போலி அல்லது மறுபதிவு செய்யப்பட்ட குரல் கண்டறியப்பட்டது (${livenessResult.detectedThreatType.labelTa}).",
        denialReasonEn = "Anti-spoofing rejection: Synthetic or replayed speech detected.",
        secureSpokenResponseTa = "மன்னிக்கவும், நேரலை மனிதக் குரல் உறுதிப்படுத்தப்படவில்லை."
      )
    }

    // 2. Check Speaker Type & Biometrics
    val isBiometricallyVerified = biometricResult.isOwnerVerified &&
      biometricResult.biometricSimilarityScore >= minBiometricThreshold

    val isSessionValid = sessionState.isSessionActive

    // Continuous session allowance for low-risk actions if previously authenticated
    val isOwnerAuthorized = (speakerType == IdentifiedSpeakerType.OWNER_PRIMARY && isBiometricallyVerified) ||
      (isSessionValid && biometricResult.biometricSimilarityScore >= 0.80f && targetRiskTier == RiskTier.LOW_AUTONOMOUS)

    if (!isOwnerAuthorized) {
      return OwnerAuthorizationEvaluation(
        isActionPermitted = false,
        speakerIdentified = speakerType,
        speechRecognitionConfidence = speechConfidence,
        ownerBiometricConfidence = biometricResult.biometricSimilarityScore,
        livenessConfidence = livenessResult.livenessConfidenceScore,
        evaluatedRiskTier = targetRiskTier,
        requiresSecondaryConfirmation = false,
        denialReasonTa = "உரிமையாளர் அல்லாத அல்லது உறுதிப்படுத்தப்படாத குரல்.",
        denialReasonEn = "Non-owner voice. Owner biometric authentication failed.",
        secureSpokenResponseTa = "உரிமையாளரின் குரலை உறுதிப்படுத்த முடியவில்லை."
      )
    }

    // 3. High-Risk / Critical Escalation Check
    val requiresSecondaryConfirm = targetRiskTier == RiskTier.HIGH_EXPLICIT_CONFIRM ||
      targetRiskTier == RiskTier.CRITICAL_MULTI_STEP

    // Update active authenticated session
    sessionState = sessionState.copy(
      isOwnerAuthenticated = true,
      authenticatedTimestampMs = currentTime,
      activeSpeakerType = IdentifiedSpeakerType.OWNER_PRIMARY,
      lastBiometricConfidence = biometricResult.biometricSimilarityScore,
      lastLivenessConfidence = livenessResult.livenessConfidenceScore,
      consecutiveVerifiedUtterances = sessionState.consecutiveVerifiedUtterances + 1
    )

    return OwnerAuthorizationEvaluation(
      isActionPermitted = !requiresSecondaryConfirm,
      speakerIdentified = IdentifiedSpeakerType.OWNER_PRIMARY,
      speechRecognitionConfidence = speechConfidence,
      ownerBiometricConfidence = biometricResult.biometricSimilarityScore,
      livenessConfidence = livenessResult.livenessConfidenceScore,
      evaluatedRiskTier = targetRiskTier,
      requiresSecondaryConfirmation = requiresSecondaryConfirm,
      denialReasonTa = if (requiresSecondaryConfirm) "அதிக ஆபத்துள்ள செயல்பாடு: உரிமையாளரின் நேரடி அனுமதி தேவை." else null,
      denialReasonEn = if (requiresSecondaryConfirm) "High-risk command staged for explicit confirmation." else null,
      secureSpokenResponseTa = if (requiresSecondaryConfirm) {
        "உரிமையாளர் குரல் உறுதி செய்யப்பட்டது. இந்த முக்கிய செயல்பாட்டிற்கு உங்கள் நேரடி உறுதிப்படுத்தல் தேவைப்படுகிறது."
      } else {
        "உரிமையாளரின் குரல் வெற்றிகரமாக அங்கீகரிக்கப்பட்டது."
      }
    )
  }

  override fun getCurrentSessionState(): OwnerVoiceSessionState = sessionState

  override fun revokeOwnerSession() {
    sessionState = OwnerVoiceSessionState()
  }

  override fun extendSessionContinuity() {
    if (sessionState.isOwnerAuthenticated) {
      sessionState = sessionState.copy(authenticatedTimestampMs = System.currentTimeMillis())
    }
  }
}
