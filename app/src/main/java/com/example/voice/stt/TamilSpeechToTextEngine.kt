package com.example.voice.stt

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

/**
 * SPEECH-TO-TEXT CONTRACT FOR TAMIL NATIVE VOICE INTELLIGENCE
 * Supports streaming audio chunks, one-shot transcription, confidence scoring,
 * audio quality telemetry (SNR, clipping), and dialect recognition (ta-IN, ta-LK, Tanglish).
 */

enum class TamilDialectRegion(val code: String, val labelTa: String, val labelEn: String) {
  TAMIL_NADU("ta-IN", "தமிழ்நாடு பொதுத்தமிழ் (Standard Tamil Nadu)", "Tamil Nadu Modern"),
  SRI_LANKA("ta-LK", "இலங்கைத் தமிழ் (Eelam / Sri Lankan Tamil)", "Sri Lankan Tamil"),
  MALAYSIA_SINGAPORE("ta-MY", "மலேசிய / சிங்கப்பூர் தமிழ்", "Malaysia/Singapore Tamil"),
  TANGLISH_CODE_MIXED("ta-LATN", "டாங்கிலிஷ் / இருமொழி கலப்பு (Tanglish)", "Tanglish Mixed")
}

data class AudioQualityTelemetry(
  val signalToNoiseRatioDb: Float = 28.5f,
  val peakAmplitude: Float = 0.82f,
  val isClipping: Boolean = false,
  val ambientNoiseLevel: String = "அமைதியான சூழல் (Low Noise)",
  val audioSampleRateHz: Int = 16000
)

data class STTToken(
  val rawWord: String,
  val phonemesTa: String,
  val startTimeMs: Long,
  val endTimeMs: Long,
  val confidence: Float
)

data class STTResult(
  val utteranceId: String = UUID.randomUUID().toString(),
  val rawTranscript: String,
  val confidence: Float = 0.985f,
  val detectedDialect: TamilDialectRegion = TamilDialectRegion.TAMIL_NADU,
  val tokens: List<STTToken> = emptyList(),
  val alternativeTranscripts: List<String> = emptyList(),
  val audioTelemetry: AudioQualityTelemetry = AudioQualityTelemetry(),
  val isFinal: Boolean = true
)

sealed class StreamingSTTState {
  object Idle : StreamingSTTState()
  data class Listening(val bufferDurationMs: Long, val audioLevelRms: Float) : StreamingSTTState()
  data class PartialTranscript(val interimText: String, val confidence: Float) : StreamingSTTState()
  data class FinalTranscript(val result: STTResult) : StreamingSTTState()
  data class Error(val errorMessageTa: String, val errorCode: String) : StreamingSTTState()
}

/**
 * Interface defining Tamil Speech-To-Text processing
 */
interface TamilSpeechToTextEngine {
  /**
   * Transcribe a complete audio payload (PCM / WAV / Opus)
   */
  suspend fun transcribeAudio(audioData: ByteArray, sampleRateHz: Int = 16000): STTResult

  /**
   * Continuous streaming recognition interface for real-time Tamil voice interactions
   */
  fun startStreamingRecognition(audioChunksFlow: Flow<ByteArray>): Flow<StreamingSTTState>

  /**
   * Stop active streaming capture
   */
  fun stopStreaming()

  /**
   * Set dialect preference hint
   */
  fun setDialectHint(dialect: TamilDialectRegion)

  /**
   * Check if on-device offline Tamil acoustic model is loaded
   */
  fun isOfflineModelLoaded(): Boolean
}

/**
 * Simulated Local Tamil STT implementation with instant processing and high accuracy
 */
class LocalTamilSpeechToTextEngine : TamilSpeechToTextEngine {
  private var activeDialect: TamilDialectRegion = TamilDialectRegion.TAMIL_NADU
  private var isStreamingActive = false

  override suspend fun transcribeAudio(audioData: ByteArray, sampleRateHz: Int): STTResult {
    // Generates simulated high-precision STT output based on sample duration
    return STTResult(
      rawTranscript = "வணக்கம் பாபு குவாண்டம் இயற்பியல் மாதிரி ஒன்றை உருவாக்கு",
      confidence = 0.992f,
      detectedDialect = activeDialect,
      audioTelemetry = AudioQualityTelemetry(
        signalToNoiseRatioDb = 32.0f,
        peakAmplitude = 0.78f,
        isClipping = false,
        ambientNoiseLevel = "தெளிவான ஆடியோ",
        audioSampleRateHz = sampleRateHz
      ),
      isFinal = true
    )
  }

  override fun startStreamingRecognition(audioChunksFlow: Flow<ByteArray>): Flow<StreamingSTTState> = flow {
    isStreamingActive = true
    emit(StreamingSTTState.Listening(bufferDurationMs = 150, audioLevelRms = 0.65f))
    emit(StreamingSTTState.PartialTranscript(interimText = "வணக்கம்...", confidence = 0.85f))
    emit(StreamingSTTState.PartialTranscript(interimText = "வணக்கம் பாபு...", confidence = 0.94f))
    emit(
      StreamingSTTState.FinalTranscript(
        STTResult(
          rawTranscript = "வணக்கம் பாபு குவாண்டம் சர்க்யூட் வரைபடம் காட்டு",
          confidence = 0.988f,
          detectedDialect = activeDialect,
          isFinal = true
        )
      )
    )
  }

  override fun stopStreaming() {
    isStreamingActive = false
  }

  override fun setDialectHint(dialect: TamilDialectRegion) {
    activeDialect = dialect
  }

  override fun isOfflineModelLoaded(): Boolean = true
}
