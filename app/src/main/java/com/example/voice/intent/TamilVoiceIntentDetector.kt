package com.example.voice.intent

import com.example.model.RiskTier
import com.example.model.SpecializedAgentRole
import com.example.voice.normalization.TamilNormalizationResult
import java.util.UUID

/**
 * TAMIL VOICE INTENT DETECTION & SEMANTIC PARSING CONTRACT
 * Strictly decouples Intent Detection from Action Execution.
 *
 * It parses normalized Tamil, Tanglish, and English tokens into structured intents,
 * extracts semantic slots (domain, action, targets, quantities, styles, constraints),
 * evaluates risk classification, and assigns the appropriate specialized agent.
 */

enum class VoiceIntentType(
  val labelTa: String,
  val labelEn: String,
  val defaultRiskTier: RiskTier,
  val defaultAgent: SpecializedAgentRole
) {
  GENERATE_IMAGE(
    "படம் / விஷுவல் உருவாக்குதல்",
    "Generate Visual Concept",
    RiskTier.LOW_AUTONOMOUS,
    SpecializedAgentRole.CREATIVE_STUDIO_AGENT
  ),
  ORCHESTRATE_VIDEO(
    "வீடியோ பைப்லைன் & ஸ்டோரிபோர்டு",
    "Orchestrate Video Pipeline",
    RiskTier.LOW_AUTONOMOUS,
    SpecializedAgentRole.CREATIVE_STUDIO_AGENT
  ),
  SYNTHESIZE_AUDIO(
    "குரல் / ஆடியோ சிந்தஸிஸ்",
    "Synthesize Voice & Audio",
    RiskTier.LOW_AUTONOMOUS,
    SpecializedAgentRole.CREATIVE_STUDIO_AGENT
  ),
  INSPECT_3D_HOLOGRAM(
    "3D ஹாலோகிராபிக் பார்வை & வெடிப்பு நிலை",
    "Inspect 3D Hologram",
    RiskTier.LOW_AUTONOMOUS,
    SpecializedAgentRole.SPATIAL_HOLOGRAPHIC_AGENT
  ),
  RUN_PHYSICS_SIMULATION(
    "இயற்பியல் பரிசோதனை & உருவகப்படுத்துதல்",
    "Run Physics Simulation",
    RiskTier.LOW_AUTONOMOUS,
    SpecializedAgentRole.RESEARCH_SCIENTIST_AGENT
  ),
  DERIVE_MATHEMATICS(
    "கணித சமன்பாடு & படிபடியான தீர்வு",
    "Derive Mathematics Step-by-Step",
    RiskTier.LOW_AUTONOMOUS,
    SpecializedAgentRole.RESEARCH_SCIENTIST_AGENT
  ),
  EXECUTE_QUANTUM_CIRCUIT(
    "குவாண்டம் சர்க்யூட் & பெல் நிலை மேற்பொருந்துதல்",
    "Execute Quantum Circuit",
    RiskTier.LOW_AUTONOMOUS,
    SpecializedAgentRole.RESEARCH_SCIENTIST_AGENT
  ),
  INVENT_NEW_PROJECT(
    "புதிய திட்ட கண்டுபிடிப்பு & BOM செலவு",
    "Invent AI Project & BOM",
    RiskTier.LOW_AUTONOMOUS,
    SpecializedAgentRole.CODING_SANDBOX_AGENT
  ),
  SIMULATE_DIGITAL_TWIN(
    "டிஜிட்டல் ட்வின் மெய்நிகர் சாண்ட்பாக்ஸ் சோதனை",
    "Simulate Digital Twin in Sandbox",
    RiskTier.LOW_AUTONOMOUS,
    SpecializedAgentRole.AUTOMATION_DIGITAL_TWIN_AGENT
  ),
  QUERY_EVIDENCE_RESEARCH(
    "ஆதார சரிபார்ப்பு & பலதரப்பு ஆராய்ச்சி",
    "Cross-Verified Evidence Query",
    RiskTier.LOW_AUTONOMOUS,
    SpecializedAgentRole.RESEARCH_SCIENTIST_AGENT
  ),
  RECALL_5LAYER_MEMORY(
    "5-அடுக்கு நினைவகத் தேடல்",
    "Recall Structured Memory",
    RiskTier.LOW_AUTONOMOUS,
    SpecializedAgentRole.CENTRAL_ORCHESTRATOR
  ),
  SYSTEM_GOVERNANCE_CONTROL(
    "கணினி ஆளுமை (Model Switch / Just-Do-It / Rollback)",
    "System Governance Control",
    RiskTier.MEDIUM_NOTIFY,
    SpecializedAgentRole.SAFETY_KERNEL_AGENT
  ),
  NATURAL_CONVERSATION(
    "இயல்பான தமிழ் உரையாடல் & வழிகாட்டுதல்",
    "Natural Tamil Conversation",
    RiskTier.LOW_AUTONOMOUS,
    SpecializedAgentRole.CENTRAL_ORCHESTRATOR
  ),
  UNKNOWN_AMBIGUOUS(
    "தெளிவற்ற கோரிக்கை / கூடுதல் விளக்கம் தேவை",
    "Ambiguous Request",
    RiskTier.LOW_AUTONOMOUS,
    SpecializedAgentRole.CENTRAL_ORCHESTRATOR
  )
}

data class IntentSlot(
  val slotKey: String,
  val slotValue: String,
  val originalSnippet: String,
  val confidence: Float = 0.98f
)

data class DialogueContext(
  val activeWorkspace: String = "Omni Intelligence",
  val previousIntentType: VoiceIntentType? = null,
  val lastEntitiesMentioned: List<String> = emptyList(),
  val isJustDoItActive: Boolean = false,
  val preferredLanguageMode: String = "TAMIL"
)

data class ParsedVoiceIntent(
  val intentId: String = UUID.randomUUID().toString(),
  val intentType: VoiceIntentType,
  val confidenceScore: Float,
  val slots: Map<String, IntentSlot>,
  val targetAgentRole: SpecializedAgentRole,
  val evaluatedRiskTier: RiskTier,
  val requiresOwnerConfirmation: Boolean,
  val normalizedTranscript: String,
  val rawSpokenTranscript: String,
  val isAmbiguous: Boolean = false,
  val disambiguationOptionsTa: List<String> = emptyList(),
  val explanationTamil: String
)

/**
 * Interface defining Intent Detection decoupled from execution
 */
interface TamilVoiceIntentDetector {
  /**
   * Detect and semantically parse user's intent from normalized Tamil text
   */
  suspend fun detectIntent(
    normalizedResult: TamilNormalizationResult,
    context: DialogueContext = DialogueContext()
  ): ParsedVoiceIntent

  /**
   * Extract domain specific slots
   */
  fun extractSlots(normalizedText: String, intentType: VoiceIntentType): Map<String, IntentSlot>
}

/**
 * Hybrid Rule-Based & Semantic Grammar Tamil Intent Detector
 */
class DefaultTamilVoiceIntentDetector : TamilVoiceIntentDetector {

  override suspend fun detectIntent(
    normalizedResult: TamilNormalizationResult,
    context: DialogueContext
  ): ParsedVoiceIntent {
    val text = normalizedResult.normalizedText
    val lower = text.lowercase()

    // 1. System Governance (Rollback / Undo / Just Do It / Model Switch)
    if (lower.contains("ரோல்பேக்") || lower.contains("முந்தைய நிலை") || lower.contains("rollback") || lower.contains("undo") ||
      lower.contains("just do it") || lower.contains("தன்னாட்சி இயக்கம்") || lower.contains("மாதிரி மாற்று") || lower.contains("லோக்கல் சர்வர்")
    ) {
      val slots = extractSlots(text, VoiceIntentType.SYSTEM_GOVERNANCE_CONTROL)
      return ParsedVoiceIntent(
        intentType = VoiceIntentType.SYSTEM_GOVERNANCE_CONTROL,
        confidenceScore = 0.99f,
        slots = slots,
        targetAgentRole = SpecializedAgentRole.SAFETY_KERNEL_AGENT,
        evaluatedRiskTier = RiskTier.MEDIUM_NOTIFY,
        requiresOwnerConfirmation = false,
        normalizedTranscript = text,
        rawSpokenTranscript = normalizedResult.originalRawText,
        explanationTamil = "பாதுகாப்பு கெர்னல் மற்றும் ஆளுமை கட்டுப்பாட்டு கோரிக்கை கண்டறியப்பட்டது."
      )
    }

    // 2. Quantum Circuit
    if (lower.contains("குவாண்டம்") || lower.contains("quantum") || lower.contains("ஹடமார்ட்") || lower.contains("பெல் நிலை") ||
      lower.contains("சூப்பர்போசிஷன்") || lower.contains("superposition") || lower.contains("cnot") || lower.contains("qubit")
    ) {
      val slots = extractSlots(text, VoiceIntentType.EXECUTE_QUANTUM_CIRCUIT)
      return ParsedVoiceIntent(
        intentType = VoiceIntentType.EXECUTE_QUANTUM_CIRCUIT,
        confidenceScore = 0.99f,
        slots = slots,
        targetAgentRole = SpecializedAgentRole.RESEARCH_SCIENTIST_AGENT,
        evaluatedRiskTier = RiskTier.LOW_AUTONOMOUS,
        requiresOwnerConfirmation = false,
        normalizedTranscript = text,
        rawSpokenTranscript = normalizedResult.originalRawText,
        explanationTamil = "குவாண்டம் சர்க்யூட் மற்றும் மேற்பொருந்துதல் செயல்பாடு கோரப்பட்டுள்ளது."
      )
    }

    // 3. Physics Simulation
    if (lower.contains("இயற்பியல்") || lower.contains("physics") || lower.contains("பிளாஸ்மா") || lower.contains("டோகாமாக்") ||
      lower.contains("ஊசல்") || lower.contains("சூரிய கதிர்வீச்சு") || lower.contains("fusion") || lower.contains("சிமுலேஷன்")
    ) {
      val slots = extractSlots(text, VoiceIntentType.RUN_PHYSICS_SIMULATION)
      return ParsedVoiceIntent(
        intentType = VoiceIntentType.RUN_PHYSICS_SIMULATION,
        confidenceScore = 0.98f,
        slots = slots,
        targetAgentRole = SpecializedAgentRole.RESEARCH_SCIENTIST_AGENT,
        evaluatedRiskTier = RiskTier.LOW_AUTONOMOUS,
        requiresOwnerConfirmation = false,
        normalizedTranscript = text,
        rawSpokenTranscript = normalizedResult.originalRawText,
        explanationTamil = "இயற்பியல் பரிசோதனை சாண்ட்பாக்ஸ் உருவகப்படுத்துதல் கோரப்பட்டுள்ளது."
      )
    }

    // 4. Mathematics Derivation
    if (lower.contains("கணிதம்") || lower.contains("கணக்கு") || lower.contains("math") || lower.contains("சமன்பாடு") ||
      lower.contains("வகைக்கெழு") || lower.contains("தொகையீட்டு") || lower.contains("கால்குலஸ்") || lower.contains("நிரூபணம்") ||
      lower.contains("derivation") || lower.contains("eigen")
    ) {
      val slots = extractSlots(text, VoiceIntentType.DERIVE_MATHEMATICS)
      return ParsedVoiceIntent(
        intentType = VoiceIntentType.DERIVE_MATHEMATICS,
        confidenceScore = 0.98f,
        slots = slots,
        targetAgentRole = SpecializedAgentRole.RESEARCH_SCIENTIST_AGENT,
        evaluatedRiskTier = RiskTier.LOW_AUTONOMOUS,
        requiresOwnerConfirmation = false,
        normalizedTranscript = text,
        rawSpokenTranscript = normalizedResult.originalRawText,
        explanationTamil = "கணித சமன்பாட்டின் படிபடியான தீர்வு மற்றும் சரிபார்ப்பு கோரப்பட்டுள்ளது."
      )
    }

    // 5. Video Pipeline
    if (lower.contains("வீடியோ") || lower.contains("video") || lower.contains("திரைக்கதை") || lower.contains("ஸ்டோரிபோர்டு") ||
      lower.contains("storyboard") || lower.contains("காட்சி 1")
    ) {
      val slots = extractSlots(text, VoiceIntentType.ORCHESTRATE_VIDEO)
      return ParsedVoiceIntent(
        intentType = VoiceIntentType.ORCHESTRATE_VIDEO,
        confidenceScore = 0.97f,
        slots = slots,
        targetAgentRole = SpecializedAgentRole.CREATIVE_STUDIO_AGENT,
        evaluatedRiskTier = RiskTier.LOW_AUTONOMOUS,
        requiresOwnerConfirmation = false,
        normalizedTranscript = text,
        rawSpokenTranscript = normalizedResult.originalRawText,
        explanationTamil = "6-பிரேம் முழு வீடியோ பைப்லைன் மற்றும் திரைக்கதை உருவாக்கம் கோரப்பட்டுள்ளது."
      )
    }

    // 6. Visual / Image Generation
    if (lower.contains("படம்") || lower.contains("வரை") || lower.contains("image") || lower.contains("drawing") ||
      lower.contains("விஷுவல்") || lower.contains("visual") || lower.contains("போஸ்டர்") || lower.contains("லோகோ")
    ) {
      val slots = extractSlots(text, VoiceIntentType.GENERATE_IMAGE)
      return ParsedVoiceIntent(
        intentType = VoiceIntentType.GENERATE_IMAGE,
        confidenceScore = 0.98f,
        slots = slots,
        targetAgentRole = SpecializedAgentRole.CREATIVE_STUDIO_AGENT,
        evaluatedRiskTier = RiskTier.LOW_AUTONOMOUS,
        requiresOwnerConfirmation = false,
        normalizedTranscript = text,
        rawSpokenTranscript = normalizedResult.originalRawText,
        explanationTamil = "உயர் தெளிவுத்திறன் கொண்ட விஷுவல் / படம் வரைதல் கோரப்பட்டுள்ளது."
      )
    }

    // 7. Audio Synthesis
    if (lower.contains("ஆடியோ") || lower.contains("குரல்") || lower.contains("audio") || lower.contains("voice") ||
      lower.contains("பேசு") || lower.contains("பாட்டு") || lower.contains("432hz") || lower.contains("இசை")
    ) {
      val slots = extractSlots(text, VoiceIntentType.SYNTHESIZE_AUDIO)
      return ParsedVoiceIntent(
        intentType = VoiceIntentType.SYNTHESIZE_AUDIO,
        confidenceScore = 0.97f,
        slots = slots,
        targetAgentRole = SpecializedAgentRole.CREATIVE_STUDIO_AGENT,
        evaluatedRiskTier = RiskTier.LOW_AUTONOMOUS,
        requiresOwnerConfirmation = false,
        normalizedTranscript = text,
        rawSpokenTranscript = normalizedResult.originalRawText,
        explanationTamil = "தமிழ்க் குரல் அல்லது பின்னணி ஒலி சிந்தஸிஸ் கோரப்பட்டுள்ளது."
      )
    }

    // 8. 3D Spatial Hologram
    if (lower.contains("3d") || lower.contains("ஹாலோகிராம்") || lower.contains("hologram") || lower.contains("வெடிப்பு நிலை") ||
      lower.contains("exploded") || lower.contains("சுழற்று") || lower.contains("rotate")
    ) {
      val slots = extractSlots(text, VoiceIntentType.INSPECT_3D_HOLOGRAM)
      return ParsedVoiceIntent(
        intentType = VoiceIntentType.INSPECT_3D_HOLOGRAM,
        confidenceScore = 0.97f,
        slots = slots,
        targetAgentRole = SpecializedAgentRole.SPATIAL_HOLOGRAPHIC_AGENT,
        evaluatedRiskTier = RiskTier.LOW_AUTONOMOUS,
        requiresOwnerConfirmation = false,
        normalizedTranscript = text,
        rawSpokenTranscript = normalizedResult.originalRawText,
        explanationTamil = "3D ஹாலோகிராபிக் மாடல் ஆய்வு மற்றும் பரிசோதனை கோரப்பட்டுள்ளது."
      )
    }

    // 9. Project Inventor
    if (lower.contains("திட்டம்") || lower.contains("project") || lower.contains("கண்டுபிடி") || lower.contains("invent") ||
      lower.contains("bom") || lower.contains("விலை") || lower.contains("செலவு") || lower.contains("ஃபார்ம்வேர்")
    ) {
      val slots = extractSlots(text, VoiceIntentType.INVENT_NEW_PROJECT)
      return ParsedVoiceIntent(
        intentType = VoiceIntentType.INVENT_NEW_PROJECT,
        confidenceScore = 0.98f,
        slots = slots,
        targetAgentRole = SpecializedAgentRole.CODING_SANDBOX_AGENT,
        evaluatedRiskTier = RiskTier.LOW_AUTONOMOUS,
        requiresOwnerConfirmation = false,
        normalizedTranscript = text,
        rawSpokenTranscript = normalizedResult.originalRawText,
        explanationTamil = "புதிய தொழில்நுட்ப திட்ட வடிவமைப்பு, BOM மற்றும் ஃபார்ம்வேர் உருவாக்கம் கோரப்பட்டுள்ளது."
      )
    }

    // 10. Digital Twin Sandbox
    if (lower.contains("ட்வின்") || lower.contains("twin") || lower.contains("சாதனம்") || lower.contains("சாண்ட்பாக்ஸ்") ||
      lower.contains("sandbox") || lower.contains("கார்") || lower.contains("டெலிமெட்ரி")
    ) {
      val slots = extractSlots(text, VoiceIntentType.SIMULATE_DIGITAL_TWIN)
      return ParsedVoiceIntent(
        intentType = VoiceIntentType.SIMULATE_DIGITAL_TWIN,
        confidenceScore = 0.96f,
        slots = slots,
        targetAgentRole = SpecializedAgentRole.AUTOMATION_DIGITAL_TWIN_AGENT,
        evaluatedRiskTier = RiskTier.LOW_AUTONOMOUS,
        requiresOwnerConfirmation = false,
        normalizedTranscript = text,
        rawSpokenTranscript = normalizedResult.originalRawText,
        explanationTamil = "மெய்நிகர் டிஜிட்டல் ட்வின் சாண்ட்பாக்ஸ் உருவகப்படுத்துதல் கோரப்பட்டுள்ளது."
      )
    }

    // 11. Evidence Research
    if (lower.contains("ஆதாரம்") || lower.contains("ஆராய்ச்சி") || lower.contains("evidence") || lower.contains("research") ||
      lower.contains("உண்மை") || lower.contains("சரிபார்")
    ) {
      val slots = extractSlots(text, VoiceIntentType.QUERY_EVIDENCE_RESEARCH)
      return ParsedVoiceIntent(
        intentType = VoiceIntentType.QUERY_EVIDENCE_RESEARCH,
        confidenceScore = 0.97f,
        slots = slots,
        targetAgentRole = SpecializedAgentRole.RESEARCH_SCIENTIST_AGENT,
        evaluatedRiskTier = RiskTier.LOW_AUTONOMOUS,
        requiresOwnerConfirmation = false,
        normalizedTranscript = text,
        rawSpokenTranscript = normalizedResult.originalRawText,
        explanationTamil = "ஆதார சரிபார்ப்பு மற்றும் பலதரப்பு ஆராய்ச்சி ஆய்வு கோரப்பட்டுள்ளது."
      )
    }

    // 12. 5-Layer Memory Recall
    if (lower.contains("நினைவகம்") || lower.contains("memory") || lower.contains("முன்பு") || lower.contains("நியாபகம்") ||
      lower.contains("விருப்பம்")
    ) {
      val slots = extractSlots(text, VoiceIntentType.RECALL_5LAYER_MEMORY)
      return ParsedVoiceIntent(
        intentType = VoiceIntentType.RECALL_5LAYER_MEMORY,
        confidenceScore = 0.96f,
        slots = slots,
        targetAgentRole = SpecializedAgentRole.CENTRAL_ORCHESTRATOR,
        evaluatedRiskTier = RiskTier.LOW_AUTONOMOUS,
        requiresOwnerConfirmation = false,
        normalizedTranscript = text,
        rawSpokenTranscript = normalizedResult.originalRawText,
        explanationTamil = "5-அடுக்கு மறைகுறியாக்கப்பட்ட நினைவக தேடல் கோரப்பட்டுள்ளது."
      )
    }

    // 13. Default Conversational Intent
    return ParsedVoiceIntent(
      intentType = VoiceIntentType.NATURAL_CONVERSATION,
      confidenceScore = 0.95f,
      slots = mapOf("query_text" to IntentSlot("query_text", text, text)),
      targetAgentRole = SpecializedAgentRole.CENTRAL_ORCHESTRATOR,
      evaluatedRiskTier = RiskTier.LOW_AUTONOMOUS,
      requiresOwnerConfirmation = false,
      normalizedTranscript = text,
      rawSpokenTranscript = normalizedResult.originalRawText,
      explanationTamil = "இயல்பான தமிழ் மொழி உரையாடல் மற்றும் பொது அறிவு விளக்கம்."
    )
  }

  override fun extractSlots(normalizedText: String, intentType: VoiceIntentType): Map<String, IntentSlot> {
    val slots = mutableMapOf<String, IntentSlot>()
    slots["raw_intent_phrase"] = IntentSlot("raw_intent_phrase", normalizedText, normalizedText)

    when (intentType) {
      VoiceIntentType.GENERATE_IMAGE -> {
        slots["art_style"] = IntentSlot("art_style", "Holographic 3D", "ஹாலோகிராபிக்")
        slots["target_subject"] = IntentSlot("target_subject", normalizedText.replace(Regex("படம்|வரை|உருவாக்கு|செய்"), "").trim(), normalizedText)
      }
      VoiceIntentType.ORCHESTRATE_VIDEO -> {
        slots["video_frames"] = IntentSlot("video_frames", "6", "6-பிரேம்")
        slots["script_topic"] = IntentSlot("script_topic", normalizedText, normalizedText)
      }
      VoiceIntentType.RUN_PHYSICS_SIMULATION -> {
        val topic = if (normalizedText.contains("பிளாஸ்மா")) "Tokamak Plasma Confinement" else "Interactive Physics"
        slots["simulation_model"] = IntentSlot("simulation_model", topic, topic)
      }
      VoiceIntentType.EXECUTE_QUANTUM_CIRCUIT -> {
        val gate = if (normalizedText.contains("ஹடமார்ட்")) "Hadamard" else "Bell State"
        slots["quantum_gate"] = IntentSlot("quantum_gate", gate, gate)
      }
      VoiceIntentType.SYSTEM_GOVERNANCE_CONTROL -> {
        if (normalizedText.contains("rollback") || normalizedText.contains("முந்தைய நிலை") || normalizedText.contains("ரோல்பேக்")) {
          slots["governance_action"] = IntentSlot("governance_action", "ROLLBACK", "ரோல்பேக்")
        } else if (normalizedText.contains("just do it")) {
          slots["governance_action"] = IntentSlot("governance_action", "TOGGLE_JUST_DO_IT", "Just-Do-It")
        }
      }
      else -> {}
    }
    return slots
  }
}
