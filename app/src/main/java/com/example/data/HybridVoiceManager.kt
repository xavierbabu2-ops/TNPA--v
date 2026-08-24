package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

/**
 * OWNER VOICE + ROBOTIC AI HYBRID VOCAL ARCHITECTURE CONTROLLER
 * Handles Layer A (Owner Natural Voice) + Layer B (Futuristic AI Robotic Layer) synthesis,
 * Real-time Mixing Ratio Slider, Dynamic Context Switching, Prosody/Formant alignment,
 * Voice Interrupt / Barge-in, and Emotion Detection.
 */
object HybridVoiceManager {

  private val _voiceConfig = MutableStateFlow(
    HybridVoiceConfig(
      activeProfile = VoiceMixProfile.BALANCED_NATURAL,
      customOwnerPercentage = 65,
      customAiPercentage = 35,
      dynamicContextMixingEnabled = true,
      pitchHz = 210,
      vocalCadenceSpeed = 1.0f,
      formantClarityBoost = 0.88f,
      antiSpoofingVerificationActive = true,
      bargeInInterruptEnabled = true
    )
  )
  val voiceConfig: StateFlow<HybridVoiceConfig> = _voiceConfig.asStateFlow()

  private val _activeEmotionCue = MutableStateFlow(VocalEmotionCue.CALM_DEFAULT)
  val activeEmotionCue: StateFlow<VocalEmotionCue> = _activeEmotionCue.asStateFlow()

  private val _spokenSamples = MutableStateFlow<List<SpokenVocalSample>>(getDefaultSpokenSamples())
  val spokenSamples: StateFlow<List<SpokenVocalSample>> = _spokenSamples.asStateFlow()

  private val _isPlayingSample = MutableStateFlow(false)
  val isPlayingSample: StateFlow<Boolean> = _isPlayingSample.asStateFlow()

  fun setMixProfile(profile: VoiceMixProfile) {
    _voiceConfig.value = _voiceConfig.value.copy(
      activeProfile = profile,
      customOwnerPercentage = profile.ownerVoicePercentage,
      customAiPercentage = profile.aiVoicePercentage
    )
  }

  fun setCustomMixRatio(ownerPercent: Int) {
    val clampedOwner = ownerPercent.coerceIn(0, 100)
    val aiPercent = 100 - clampedOwner
    _voiceConfig.value = _voiceConfig.value.copy(
      customOwnerPercentage = clampedOwner,
      customAiPercentage = aiPercent
    )
  }

  fun toggleDynamicContextMixing() {
    _voiceConfig.value = _voiceConfig.value.copy(
      dynamicContextMixingEnabled = !_voiceConfig.value.dynamicContextMixingEnabled
    )
  }

  fun setEmotionCue(cue: VocalEmotionCue) {
    _activeEmotionCue.value = cue
  }

  fun triggerBargeInInterrupt() {
    _isPlayingSample.value = false
    _spokenSamples.value = _spokenSamples.value.map { sample ->
      sample.copy(isBargeInTriggered = true)
    }
  }

  fun synthesizeHybridUtterance(phraseTa: String, contextTag: String = "நேரலை உரையாடல்") {
    val newSample = SpokenVocalSample(
      phraseTa = phraseTa,
      contextLabel = contextTag,
      activeProfile = _voiceConfig.value.activeProfile,
      simulatedDurationSec = (phraseTa.length * 0.08f).coerceAtLeast(1.8f)
    )
    _spokenSamples.value = listOf(newSample) + _spokenSamples.value
    _isPlayingSample.value = true
  }

  private fun getDefaultSpokenSamples(): List<SpokenVocalSample> {
    return listOf(
      SpokenVocalSample(
        phraseTa = "வணக்கம் பாபு, உங்களின் தன்னாட்சி நுண்ணறிவு தயார். இன்று நாம் என்ன ஆராய்ச்சி செய்யப்போகிறோம்?",
        contextLabel = "தொடக்க வரவேற்பு (Hybrid 65/35)",
        activeProfile = VoiceMixProfile.BALANCED_NATURAL,
        simulatedDurationSec = 3.8f
      ),
      SpokenVocalSample(
        phraseTa = "டோகமாக் பிளாஸ்மா சமன்பாடுகளில் 3422°C டங்ஸ்டன் உறிஞ்சுதல் வரம்பு துல்லியமாக கணக்கிடப்பட்டுள்ளது.",
        contextLabel = "தொழில்நுட்ப விளக்கம் (Technical 50/50)",
        activeProfile = VoiceMixProfile.TECHNICAL_BALANCED,
        simulatedDurationSec = 4.2f
      ),
      SpokenVocalSample(
        phraseTa = "எச்சரிக்கை: அங்கீகரிக்கப்படாத விருந்தினர் குரல் கண்டறியப்பட்டது; தனிநபர் தகவல்கள் தானாக பூட்டப்பட்டுள்ளன.",
        contextLabel = "பாதுகாப்பு விழிப்பூட்டல் (AI Dominant 30/70)",
        activeProfile = VoiceMixProfile.AI_DOMINANT,
        simulatedDurationSec = 3.6f
      )
    )
  }
}
