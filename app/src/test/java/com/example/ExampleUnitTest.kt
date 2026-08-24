package com.example

import com.example.model.RiskTier
import com.example.model.SpecializedAgentRole
import com.example.voice.intent.DefaultTamilVoiceIntentDetector
import com.example.voice.intent.VoiceIntentType
import com.example.voice.normalization.DefaultTamilTextNormalizer
import com.example.voice.routing.DefaultVoiceActionRouter
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  private val normalizer = DefaultTamilTextNormalizer()
  private val intentDetector = DefaultTamilVoiceIntentDetector()
  private val router = DefaultVoiceActionRouter()

  @Test
  fun testColloquialTamilNormalization() {
    val input = "பாபு ஒரு படம் பண்ணு"
    val result = normalizer.normalize(input)
    assertTrue("Colloquial word should be normalized", result.normalizedText.contains("செய்"))
    assertTrue(result.colloquialReplacementsCount > 0)
  }

  @Test
  fun testTanglishTransliteration() {
    val input = "solunga babu kandupidi"
    val result = normalizer.normalize(input)
    assertTrue("Tanglish should be normalized to Tamil", result.containsTanglish)
    assertTrue(result.normalizedText.contains("சொல்லுங்கள்") || result.normalizedText.contains("கண்டுபிடி"))
  }

  @Test
  fun testSandhiCompoundDecomposition() {
    val input = "ஹாலோகிராபிக் படம்வரைய வேண்டும்"
    val result = normalizer.normalize(input)
    assertTrue("Sandhi compound should be split", result.normalizedText.contains("படம் வரை"))
    assertTrue(result.resolvedSandhiCount > 0)
  }

  @Test
  fun testQuantumCircuitIntentDetection() = runBlocking {
    val normResult = normalizer.normalize("குவாண்டம் பெல் நிலை சர்க்யூட் செக் பண்ணு")
    val parsedIntent = intentDetector.detectIntent(normResult)

    assertEquals(VoiceIntentType.EXECUTE_QUANTUM_CIRCUIT, parsedIntent.intentType)
    assertEquals(SpecializedAgentRole.RESEARCH_SCIENTIST_AGENT, parsedIntent.targetAgentRole)
    assertEquals(RiskTier.LOW_AUTONOMOUS, parsedIntent.evaluatedRiskTier)
    assertTrue(parsedIntent.confidenceScore >= 0.85f)
  }

  @Test
  fun testImageGenerationIntentDetection() = runBlocking {
    val normResult = normalizer.normalize("எதிர்கால விண்கலத்தின் கான்செப்ட் படம் ஒன்று வரை")
    val parsedIntent = intentDetector.detectIntent(normResult)

    assertEquals(VoiceIntentType.GENERATE_IMAGE, parsedIntent.intentType)
    assertEquals(SpecializedAgentRole.CREATIVE_STUDIO_AGENT, parsedIntent.targetAgentRole)
  }

  @Test
  fun testPhysicsSimulationIntentDetection() = runBlocking {
    val normResult = normalizer.normalize("டோகாமாக் பிளாஸ்மா இயற்பியல் பரிசோதனை simulation பண்ணு")
    val parsedIntent = intentDetector.detectIntent(normResult)

    assertEquals(VoiceIntentType.RUN_PHYSICS_SIMULATION, parsedIntent.intentType)
    assertEquals(SpecializedAgentRole.RESEARCH_SCIENTIST_AGENT, parsedIntent.targetAgentRole)
  }

  @Test
  fun testTamilVoiceIntelligencePipelineEndToEnd() = runBlocking {
    val pipeline = com.example.voice.TamilVoiceIntelligencePipeline.INSTANCE
    val session = pipeline.processVoiceUtterance("பாபு ஒரு 6-frame வீடியோ உருவாக்கு")

    assertEquals(com.example.voice.PipelineStageStatus.COMPLETED, session.currentStage)
    assertTrue(session.isOwnerAuthorized)
    assertNotNull(session.normalizationResult)
    assertNotNull(session.parsedIntent)
    assertEquals(VoiceIntentType.ORCHESTRATE_VIDEO, session.parsedIntent?.intentType)
    assertNotNull(session.executionResult)
    assertTrue(session.executionResult?.isSuccess == true)
  }

  @Test
  fun testNonOwnerVoiceRejectionAndZeroLeakage() = runBlocking {
    val pipeline = com.example.voice.TamilVoiceIntelligencePipeline.INSTANCE
    val session = pipeline.processVoiceUtterance(
      rawTranscript = "என் தனிப்பட்ட குறிப்புகளைத் திறந்து காட்டு",
      simulatedSpeaker = com.example.voice.diarization.IdentifiedSpeakerType.GUEST_SPEAKER_A
    )

    assertEquals(com.example.voice.PipelineStageStatus.REJECTED_UNAUTHORIZED, session.currentStage)
    assertFalse(session.isOwnerAuthorized)
    assertNull(session.parsedIntent)
    assertEquals("உரிமையாளரின் குரலை உறுதிப்படுத்த முடியவில்லை.", session.authorizationEvaluation?.secureSpokenResponseTa)
  }

  @Test
  fun testAntiSpoofingSyntheticDeepfakeRejection() = runBlocking {
    val pipeline = com.example.voice.TamilVoiceIntelligencePipeline.INSTANCE
    val session = pipeline.processVoiceUtterance(
      rawTranscript = "பாபு என் நிதிக் கணக்குகளை மாற்று",
      simulatedSpeaker = com.example.voice.diarization.IdentifiedSpeakerType.OWNER_PRIMARY,
      simulatedThreat = com.example.voice.security.SpoofThreatType.SYNTHETIC_CLONE_TTS
    )

    assertEquals(com.example.voice.PipelineStageStatus.REJECTED_UNAUTHORIZED, session.currentStage)
    assertFalse(session.isOwnerAuthorized)
    assertEquals(com.example.voice.security.SpoofThreatType.SYNTHETIC_CLONE_TTS, session.livenessResult?.detectedThreatType)
  }

  @Test
  fun testPersonalAwarenessIncomingMessagesVoiceQuery() {
    val response = com.example.data.PersonalAwarenessEngine.processVoiceAwarenessQuery(
      queryText = "எனக்கு என்ன message வந்திருக்கு?",
      isOwnerVerified = true
    )
    assertTrue(response.isSuccess)
    assertTrue(response.spokenTamilText.contains("WhatsApp") && response.spokenTamilText.contains("Arun"))
    assertTrue(response.provenanceHash.isNotEmpty())
  }

  @Test
  fun testPersonalAwarenessLocationAndRouteVoiceQuery() {
    val locResponse = com.example.data.PersonalAwarenessEngine.processVoiceAwarenessQuery(
      queryText = "நான் இப்போது எங்கே இருக்கிறேன்?",
      isOwnerVerified = true
    )
    assertTrue(locResponse.isSuccess)
    assertTrue(locResponse.spokenTamilText.contains("அடையாறு"))

    val navResponse = com.example.data.PersonalAwarenessEngine.processVoiceAwarenessQuery(
      queryText = "அங்கே எப்படி போகணும்?",
      isOwnerVerified = true
    )
    assertTrue(navResponse.isSuccess)
    assertTrue(navResponse.spokenTamilText.contains("Inner Ring Road") || navResponse.spokenTamilText.contains("வழி"))
  }

  @Test
  fun testPersonalAwarenessZeroLeakageOnGuestSpeaker() {
    val response = com.example.data.PersonalAwarenessEngine.processVoiceAwarenessQuery(
      queryText = "எனக்கு என்ன message வந்திருக்கு?",
      isOwnerVerified = false
    )
    assertFalse(response.isSuccess)
    assertTrue(response.spokenTamilText.contains("உரிமையாளரின் குரல் சரிபார்க்கப்படவில்லை"))
    assertFalse(response.spokenTamilText.contains("Arun"))
  }

  @Test
  fun testMessageConversionToTaskAndCalendar() {
    val messages = com.example.data.PersonalAwarenessEngine.unifiedMessages.value
    val firstMsg = messages.first()

    val taskCreated = com.example.data.PersonalAwarenessEngine.convertMessageToTask(firstMsg.id)
    assertTrue(taskCreated)

    val calCreated = com.example.data.PersonalAwarenessEngine.convertMessageToCalendar(firstMsg.id)
    assertTrue(calCreated)

    val updatedMsg = com.example.data.PersonalAwarenessEngine.unifiedMessages.value.first { it.id == firstMsg.id }
    assertNotNull(updatedMsg.linkedTaskDraft)
    assertNotNull(updatedMsg.linkedCalendarDraft)
  }
}

