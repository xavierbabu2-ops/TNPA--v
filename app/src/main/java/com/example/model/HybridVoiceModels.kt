package com.example.model

import java.util.UUID

/**
 * OWNER VOICE + ROBOTIC AI HYBRID VOCAL ARCHITECTURE
 * 
 * Unifies:
 * 1. Owner Voice Identity (Natural timbre, Tamil native pronunciation, speech patterns)
 * 2. Futuristic Robotic AI Layer (Clean, crystalline, intelligent, subtle synthetic resonance)
 * 3. Dynamic Context-Aware Mixing Control (Human Dominant, Balanced, AI Dominant, Urgent Alert)
 * 4. Architectural Separation between Voice Authentication and Voice Generation.
 * 5. Barge-in / Interrupt & Real-time Emotion Context Cues.
 */

enum class VoiceMixProfile(
  val id: String,
  val labelTa: String,
  val labelEn: String,
  val ownerVoicePercentage: Int,
  val aiVoicePercentage: Int,
  val descriptionTa: String
) {
  HUMAN_DOMINANT(
    "human_dominant",
    "உரிமையாளர் ஆதிக்கம் (Human Dominant)",
    "Human Dominant",
    80,
    20,
    "இயற்கையான மனித குரல் முன்னிலை; மிக மெல்லிய AI அதிர்வு."
  ),
  BALANCED_NATURAL(
    "balanced_natural",
    "இயற்கை சமநிலை (Balanced Hybrid - Default)",
    "Balanced Hybrid",
    65,
    35,
    "அமைதியான, தெளிவான மனிதத் தன்மையுடன் நவீன AI குரல் லேயர்."
  ),
  TECHNICAL_BALANCED(
    "technical_balanced",
    "தொழில்நுட்ப சமநிலை (Technical Mode)",
    "Technical Mode",
    50,
    50,
    "கணிதம் மற்றும் ஆய்வுகளுக்கான துல்லியமான சிந்தடிக் தெளிவு."
  ),
  AI_DOMINANT(
    "ai_dominant",
    "AI ஆதிக்கம் (Futuristic AI Dominant)",
    "AI Dominant",
    30,
    70,
    "எதிர்கால ஹை-டெக் ரோபோடிக் அதிர்வு; அறிவிப்புகளுக்கு உகந்தது."
  ),
  ROBOT_SHARP(
    "robot_sharp",
    "கிரிஸ்டல் ரோபோடிக் (Crystal Robot)",
    "Crystal Robot",
    10,
    90,
    "அதிவேக, கூர்மையான எலக்ட்ரானிக் ஒலி; சிஸ்டம் எச்சரிக்கைகளுக்கு."
  ),
  CINEMATIC_RESONANCE(
    "cinematic",
    "சினிமாட்டிக் ஹைப்ரிட் (Cinematic Hybrid)",
    "Cinematic",
    55,
    45,
    "அறிவியல் புனைகதை மற்றும் பிரம்மாண்ட கதைகளுக்கான குரல்."
  )
}

enum class VocalEmotionCue(
  val labelTa: String,
  val labelEn: String,
  val pitchModifierHz: Int,
  val speedModifier: Float
) {
  CALM_DEFAULT("அமைதியானது (Calm)", "Calm", 0, 1.0f),
  TECHNICAL_ANALYTICAL("ஆராய்ச்சி பூர்வமானது (Analytical)", "Analytical", +10, 0.98f),
  FRIENDLY_WARM("நட்பான உரையாடல் (Warm Friendly)", "Warm", +15, 1.05f),
  URGENT_ALERT("அவசர விழிப்பூட்டல் (Urgent Alert)", "Urgent", +35, 1.15f),
  CURIOUS_QUESTIONING("ஆர்வமுள்ள வினா (Curious)", "Curious", +20, 1.02f),
  CONCERNED_ATTENTIVE("கவனமான எச்சரிக்கை (Attentive)", "Attentive", -5, 0.95f)
}

data class HybridVoiceConfig(
  val activeProfile: VoiceMixProfile = VoiceMixProfile.BALANCED_NATURAL,
  val customOwnerPercentage: Int = 65,
  val customAiPercentage: Int = 35,
  val dynamicContextMixingEnabled: Boolean = true,
  val pitchHz: Int = 210,
  val vocalCadenceSpeed: Float = 1.0f,
  val formantClarityBoost: Float = 0.85f,
  val antiSpoofingVerificationActive: Boolean = true,
  val bargeInInterruptEnabled: Boolean = true,
  val ownerVoiceprintHash: String = "SHA256:e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
  val speechSanityCheckPassed: Boolean = true
)

data class SpokenVocalSample(
  val id: String = UUID.randomUUID().toString(),
  val phraseTa: String,
  val contextLabel: String,
  val activeProfile: VoiceMixProfile,
  val simulatedDurationSec: Float,
  val isBargeInTriggered: Boolean = false
)
