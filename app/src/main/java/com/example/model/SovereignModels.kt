package com.example.model

import androidx.compose.ui.graphics.Color
import java.util.UUID

/**
 * PERSONAL SOVEREIGN AI OPERATING SYSTEM (தனிநபர் தன்னாட்சி AI இயங்குதளம்)
 * Complete Cognitive Architecture, Personal World Model, 5-Layer Memory,
 * Dynamic Model Routing, AI Research Scientist Mode, Tamil Voice Intelligence,
 * Safety Kernel & Reversible Action Architecture.
 */

// ============================================================================
// 1. COGNITIVE ARCHITECTURE & 10-STAGE AUTONOMOUS LOOP
// ============================================================================

enum class CognitivePipelineStage(val labelTa: String, val labelEn: String, val stageNumber: Int) {
  PERCEPTION("1. பார்வை / உணர்தல்", "Perception & Sensory Dock", 1),
  CONTEXT_ENGINE("2. சூழல் தொகுப்பு", "Context Synthesis", 2),
  WORLD_MODEL_MAPPING("3. உலக மாதிரி பொருத்தம்", "Personal World Graph Mapping", 3),
  MEMORY_RETRIEVAL("4. 5-அடுக்கு நினைவகம்", "5-Layer Memory Recall", 4),
  REASONING_ENGINE("5. ஆழ்ந்த பகுப்பாய்வு", "Deep Cognitive Reasoning", 5),
  GOAL_PLANNING("6. இலக்கு & திட்டமிடல்", "Goal & Action Planning", 6),
  CAPABILITY_ROUTING("7. திறன் & மாதிரி தேர்வு", "Capability & Model Router", 7),
  SANDBOX_SIMULATION("8. மெய்நிகர் சாண்ட்பாக்ஸ்", "AI Sandbox & Twin Simulation", 8),
  VERIFICATION_ENGINE("9. ஆதார & பாதுகாப்பு தணிக்கை", "Verification & Safety Audit", 9),
  EXECUTION_AND_LEARNING("10. செயலாக்கம் & கற்றல்", "Execution & Continuous Learning", 10),
  IDLE("தயார் நிலை", "Sovereign Kernel Ready", 0)
}

enum class MultimodalInputType(val labelTa: String, val labelEn: String) {
  TAMIL_VOICE("தமிழ்க் குரல்", "Tamil Voice Stream"),
  TEXT("எழுத்து / உரை", "Text Prompt"),
  IMAGE("புகைப்படம் / விஷுவல்", "Image Input"),
  DOCUMENT("ஆவணம் / PDF / தரவு", "Document & Data"),
  CAMERA("நேரடி கேமரா", "Live Vision Feed"),
  SENSOR("சென்சார் / டெலிமெட்ரி", "Hardware Telemetry"),
  SPATIAL_3D("3D / AR சைகை", "Spatial / 3D Gesture"),
  CODE_FILE("நிரல் / ஸ்கிரிப்ட்", "Source Code File")
}

enum class ConfidenceLevel(val labelTa: String, val labelEn: String, val colorHex: Long, val symbol: String) {
  VERIFIED("நிரூபிக்கப்பட்டது (Verified)", "Verified", 0xFF10B981, "🟢"),
  STRONGLY_SUPPORTED("வலுவான ஆதாரம் (Strongly Supported)", "Strongly Supported", 0xFF38BDF8, "🔵"),
  LIKELY("அதிக சாத்தியம் (Likely)", "Likely", 0xFFF59E0B, "🟡"),
  UNCERTAIN("தெளிவற்றது (Uncertain)", "Uncertain", 0xFFFB923C, "🟠"),
  UNSUPPORTED("ஆதாரமற்றது (Unsupported)", "Unsupported", 0xFFEF4444, "🔴"),
  UNKNOWN("அறியப்படவில்லை (Unknown)", "Unknown", 0xFF6B7280, "⚫")
}

data class EvidenceCitation(
  val claim: String,
  val claimCategory: ScientificClaimType = ScientificClaimType.FACT,
  val evidenceSummary: String,
  val sources: List<String>,
  val confidence: ConfidenceLevel,
  val agreementState: String, // e.g. "3 சர்வதேச ஆய்வுகளும் ஒன்றுபடுகின்றன"
  val dateRecorded: String,
  val mathematicalProofSnippet: String? = null
)

enum class ScientificClaimType(val labelTa: String, val labelEn: String, val badgeColor: Long) {
  FACT("உண்மைத் தகவல் (Fact)", "Empirical Fact", 0xFF10B981),
  HYPOTHESIS("கருதுகோள் (Hypothesis)", "Hypothesis", 0xFF818CF8),
  SIMULATION_RESULT("உருவகப்படுத்துதல் முடிவு (Simulation)", "Simulation Result", 0xFF38BDF8),
  EXPERIMENTAL_EVIDENCE("பரிசோதனைத் தரவு (Experiment)", "Experimental Evidence", 0xFFA855F7),
  NEW_PROPOSAL("புதிய தீர்வு (New Proposal)", "Innovative Proposal", 0xFFF59E0B)
}

data class OmniChatMessage(
  val id: String = UUID.randomUUID().toString(),
  val sender: MessageSender,
  val text: String,
  val timestamp: String,
  val inputType: MultimodalInputType = MultimodalInputType.TEXT,
  val cognitiveStage: CognitivePipelineStage = CognitivePipelineStage.IDLE,
  val activeAgent: SpecializedAgentRole? = null,
  val toolUsed: String? = null,
  val evidence: EvidenceCitation? = null,
  val voiceAudioPlaybackAvailable: Boolean = false,
  val voiceStyleUsed: VoiceStyle = VoiceStyle.NORMAL_CONVERSATIONAL,
  val mediaPreviewUrl: String? = null,
  val mediaType: String? = null, // "image", "video", "audio", "3d", "math", "code", "research", "learning", "sandbox"
  val provenanceHash: String? = null,
  val rollbackSnapshotId: String? = null
)

enum class MessageSender {
  OWNER,
  SOVEREIGN_AI,
  SPECIALIZED_AGENT
}

// ============================================================================
// 2. PERSONAL WORLD MODEL & PERSONAL KNOWLEDGE GRAPH
// ============================================================================

enum class WorldEntityCategory(val labelTa: String, val labelEn: String) {
  PERSON("உரிமையாளர் விவரம்", "Owner Profile"),
  DEVICE("இணைக்கப்பட்ட சாதனம்", "Connected Device"),
  ENVIRONMENT("சுற்றுச்சூழல் / இடம்", "Physical Environment"),
  DIGITAL_WORKSPACE("டிஜிட்டல் ஆவணங்கள் / கோப்புகள்", "Digital Workspace"),
  ACTIVE_PROJECT("செயலில் உள்ள திட்டம்", "Active Project"),
  SMART_SENSOR("சென்சார் / டெலிமெட்ரி", "Smart Sensor"),
  PERMISSION_POLICY("பாதுகாப்பு & அனுமதி கொள்கை", "Permission Policy")
}

data class PersonalWorldNode(
  val id: String,
  val name: String,
  val nameTa: String,
  val category: WorldEntityCategory,
  val status: String,
  val contextSnippet: String,
  val boundDevice: String? = null, // "Phone", "Workstation PC", "Vehicle OBD", "Smart Home Hub", "AR Glasses"
  val securityTier: RiskTier = RiskTier.LOW_AUTONOMOUS,
  val lastInteraction: String
)

data class WorldGraphEdge(
  val sourceNodeId: String,
  val targetNodeId: String,
  val relationshipTa: String,
  val relationshipEn: String
)

// ============================================================================
// 3. 5-LAYER MEMORY SYSTEM (5-அடுக்கு நினைவகக் கட்டமைப்பு)
// ============================================================================

enum class MemoryLayerType(val labelTa: String, val labelEn: String, val iconDesc: String) {
  WORKING_MEMORY("1. நடப்பு நினைவகம் (Working)", "Working Memory", "தற்போதைய உரையாடல் சூழல் & தற்காலிக பஃபர்"),
  EPISODIC_MEMORY("2. நிகழ்வு நினைவகம் (Episodic)", "Episodic Memory", "முன்பு நடந்த செயல்கள், முடிவுகள், உரையாடல்கள்"),
  SEMANTIC_MEMORY("3. அறிவு நினைவகம் (Semantic)", "Semantic Memory", "கண்டுபிடிக்கப்பட்ட உண்மைகள், அறிவியல் கோட்பாடுகள்"),
  PROCEDURAL_MEMORY("4. நடைமுறை நினைவகம் (Procedural)", "Procedural Memory", "உரிமையாளரின் பணி பாணி, விருப்பங்கள், அணுகுமுறைகள்"),
  PROJECT_MEMORY("5. திட்ட நினைவகம் (Project)", "Project Memory", "திட்டக் கோப்புகள், BOM, சிமுலேஷன், நிரல்கள்")
}

data class StructuredMemoryRecord(
  val id: String = UUID.randomUUID().toString(),
  val layer: MemoryLayerType,
  val title: String,
  val titleTa: String,
  val contentTamil: String,
  val associatedEntityId: String? = null,
  val confidenceScore: Float = 0.98f,
  val timestamp: String,
  val isEncrypted: Boolean = true
)

// ============================================================================
// 4. CAPABILITY DISCOVERY ENGINE & METADATA MATRIX
// ============================================================================

enum class RiskTier(val labelTa: String, val labelEn: String, val executionPolicyTa: String, val colorHex: Long) {
  LOW_AUTONOMOUS("குறைந்த ஆபத்து (Low Risk)", "Low Risk", "தானியங்கி Just-Do-It முறை", 0xFF10B981),
  MEDIUM_NOTIFY("நடுத்தர ஆபத்து (Medium Risk)", "Medium Risk", "அறிவிப்பு + அமைவு அனுமதி", 0xFF38BDF8),
  HIGH_EXPLICIT_CONFIRM("அதிக ஆபத்து (High Risk)", "High Risk", "நேரடி உரிமையாளர் ஒப்புதல் கட்டாயம்", 0xFFF59E0B),
  CRITICAL_MULTI_STEP("மிக மிக முக்கியம் (Critical)", "Critical", "இருபடி கிரிப்டோகிராஃபிக் அனுமதி", 0xFFEF4444)
}

data class CapabilityMetadata(
  val id: String,
  val nameTa: String,
  val nameEn: String,
  val descriptionTa: String,
  val requiredInputs: List<String>,
  val outputType: String,
  val riskTier: RiskTier,
  val isOfflineReady: Boolean = true,
  val mappedAgent: SpecializedAgentRole,
  val recommendedModel: String,
  val requiresSandbox: Boolean = false,
  val isReversible: Boolean = true
)

// ============================================================================
// 5. SPECIALIZED AGENT ECOSYSTEM
// ============================================================================

enum class SpecializedAgentRole(val titleTa: String, val titleEn: String, val specialtyTa: String) {
  RESEARCH_SCIENTIST_AGENT("அறிவியல் ஆய்வாளர் (Research Agent)", "Research Scientist Agent", "கருதுகோள், பலதரப்பு ஆதாரம் & அறிவியல் சமன்பாடுகள்"),
  CODING_SANDBOX_AGENT("நிரலாக்க முகவர் (Coding Agent)", "Coding & Sandbox Agent", "நிரல் உருவாக்கம், சாண்ட்பாக்ஸ் டெஸ்ட் & ஃபார்ம்வேர்"),
  CREATIVE_STUDIO_AGENT("படைப்பாற்றல் முகவர் (Creative Agent)", "Creative Studios Agent", "விஷுவல் ஆர்ட், 6-பிரேம் வீடியோ & ஆடியோ சிந்தஸிஸ்"),
  SPATIAL_HOLOGRAPHIC_AGENT("3D & AR முகவர் (Spatial Agent)", "Spatial & Hologram Agent", "3D மாடல்கள், இன்டராக்டிவ் சிமுலேஷன் & AR"),
  LEARNING_COACH_AGENT("கற்றல் ஆசான் (Learning Coach)", "Personal Learning Coach", "கருத்து விளக்கம், வினாடி வினா & படிபடியான பயிற்சி"),
  AUTOMATION_DIGITAL_TWIN_AGENT("டிஜிட்டல் ட்வின் முகவர் (Twin Agent)", "Automation & Digital Twin Agent", "சாதன மெய்நிகர் சோதனை & ஸ்மார்ட் தானியங்கி"),
  SAFETY_KERNEL_AGENT("பாதுகாப்பு கெர்னல் (Safety Kernel)", "Security & Safety Kernel", "அனுமதி மேட்ரிக்ஸ், தணிக்கை & ரோல்பேக் ஸ்னாப்ஷாட்"),
  TAMIL_VOICE_INTELLIGENCE_AGENT("தமிழ் குரல் முகவர் (Voice Intelligence)", "Tamil Native Voice Agent", "இயற்கையான தமிழ் பேச்சு, உச்சரிப்பு & குரல் பாணி"),
  CENTRAL_ORCHESTRATOR("மத்திய நுண்ணறிவு ஒருங்கிணைப்பாளர்", "Central Sovereign Orchestrator", "அனைத்து முகவர்களையும் இணைக்கும் முதன்மை அறிவுத்தளம்")
}

data class AgentExecutionState(
  val role: SpecializedAgentRole,
  val currentStatus: String,
  val currentTaskTa: String,
  val isBusy: Boolean = false,
  val progressPercent: Int = 100
)

// ============================================================================
// 6. TAMIL NATIVE VOICE INTELLIGENCE
// ============================================================================

enum class VoiceStyle(val labelTa: String, val labelEn: String, val speedFactor: Float, val pitchHz: Int) {
  NORMAL_CONVERSATIONAL("இயல்பான உரையாடல் (Conversational)", "Normal Conversational", 1.0f, 210),
  TEACHING_EXPLAIN("கற்பித்தல் & விளக்கம் (Teaching)", "Teaching & Detailed", 0.88f, 200),
  TECHNICAL_PRECISE("துல்லிய அறிவியல் (Technical)", "Technical & Precise", 1.05f, 220),
  URGENT_ALERT("எச்சரிக்கை / அவசரம் (Alert)", "Urgent Alert", 1.2f, 250),
  CASUAL_WARM("நட்பான குரல் (Casual)", "Casual & Warm", 0.95f, 195)
}

data class TamilVoiceProfile(
  val ownerVoiceSpeed: Float = 1.0f,
  val dialectPreference: String = "இயல்பான பொதுத்தமிழ் (Standard Modern Tamil)",
  val tanglishHandlingLevel: String = "முழுமையாகப் புரிந்துகொள்ளும் திறன் (High)",
  val activeVoiceStyle: VoiceStyle = VoiceStyle.NORMAL_CONVERSATIONAL,
  val speechRecognitionAccuracy: Float = 0.992f,
  val isListening: Boolean = false,
  val lastSpokenTranscriptTa: String = "",
  val naturalVoicePersonaName: String = "தன்னாட்சி தமிழ் குரல் (Sovereign Tamil Voice)"
)

// ============================================================================
// 7. AI RESEARCH SCIENTIST & MATHEMATICS BRAIN
// ============================================================================

data class ResearchPipelineStep(
  val stepNumber: Int,
  val phaseNameTa: String,
  val phaseNameEn: String,
  val descriptionTa: String,
  val outputSnippet: String,
  val claimType: ScientificClaimType
)

data class ResearchProjectRecord(
  val id: String = UUID.randomUUID().toString(),
  val problemTitle: String,
  val problemTitleTa: String,
  val targetDomain: String,
  val steps: List<ResearchPipelineStep>,
  val verifiedProposalTa: String,
  val overallConfidence: ConfidenceLevel = ConfidenceLevel.VERIFIED,
  val citationSources: List<String>
)

data class PhysicsSimulationExperiment(
  val id: String = UUID.randomUUID().toString(),
  val title: String,
  val titleTa: String,
  val field: String,
  val hypothesis: String,
  val mathematicalEquation: String,
  val parameter1Name: String,
  val parameter1Value: Float,
  val parameter2Name: String,
  val parameter2Value: Float,
  val observedResultTa: String,
  val verifiedConclusion: String,
  val statusBadge: ConfidenceLevel = ConfidenceLevel.VERIFIED
)

data class MathStep(
  val stepNumber: Int,
  val formula: String,
  val explanationTa: String,
  val verified: Boolean = true
)

data class MathematicsDerivation(
  val id: String = UUID.randomUUID().toString(),
  val problemTitle: String,
  val category: String,
  val initialExpression: String,
  val finalResult: String,
  val steps: List<MathStep>,
  val verificationMethod: String = "Symbolic Computer Algebra & Numerical Proof"
)

enum class QuantumGateType(val symbol: String, val nameTa: String) {
  HADAMARD("H", "ஹடமார்ட் (Superposition)"),
  PAULI_X("X", "பாலி-X (Bit Flip)"),
  PAULI_Y("Y", "பாலி-Y (Phase+Bit)"),
  PAULI_Z("Z", "பாலி-Z (Phase Flip)"),
  CNOT("CX", "கட்டுப்படுத்தப்பட்ட NOT (Entanglement)"),
  PHASE_S("S", "ஃபேஸ் கேட் (π/2 Phase)")
}

data class QuantumCircuitState(
  val qubitCount: Int = 2,
  val gatesOnQ0: List<QuantumGateType> = listOf(QuantumGateType.HADAMARD),
  val gatesOnQ1: List<QuantumGateType> = listOf(QuantumGateType.CNOT),
  val probability00: Float = 0.50f,
  val probability01: Float = 0.00f,
  val probability10: Float = 0.00f,
  val probability11: Float = 0.50f,
  val entanglementState: String = "பெல் நிலை (|Φ+⟩ = (|00⟩ + |11⟩)/√2) - Entangled"
)

data class KnowledgeGraphNode(
  val id: String,
  val label: String,
  val labelTa: String,
  val domain: String,
  val connectedNodeIds: List<String>,
  val crossDomainInsightTa: String
)

// ============================================================================
// 8. PERSONAL LEARNING ENGINE
// ============================================================================

enum class KnowledgeLevel(val labelTa: String, val labelEn: String) {
  BEGINNER("தொடக்க நிலை (Beginner)", "Beginner"),
  INTERMEDIATE("இடைநிலை (Intermediate)", "Intermediate"),
  ADVANCED("மேம்பட்ட நிலை (Advanced)", "Advanced")
}

data class LearningLesson(
  val lessonNumber: Int,
  val titleTa: String,
  val titleEn: String,
  val conceptExplanationTa: String,
  val realWorldExampleTa: String,
  val interactiveSimulationFormula: String?,
  val quizQuestionTa: String,
  val quizOptions: List<String>,
  val correctOptionIndex: Int,
  val isCompleted: Boolean = false
)

data class PersonalLearningTrack(
  val id: String = UUID.randomUUID().toString(),
  val subjectTa: String,
  val subjectEn: String,
  val currentLevel: KnowledgeLevel = KnowledgeLevel.BEGINNER,
  val progressPercent: Int = 35,
  val lessons: List<LearningLesson>,
  val practicalProjectLink: String? = null
)

// ============================================================================
// 9. GOAL ENGINE & PROJECT UNIVERSE
// ============================================================================

data class LongTermGoal(
  val id: String = UUID.randomUUID().toString(),
  val goalTitleTa: String,
  val targetDate: String,
  val domain: String,
  val milestoneSteps: List<String>,
  val completedMilestones: Int,
  val statusTa: String
)

data class BillOfMaterialItem(
  val name: String,
  val nameTa: String,
  val quantity: Int,
  val estimatedCostInr: Double,
  val componentType: String
)

data class InventedProject(
  val id: String = UUID.randomUUID().toString(),
  val title: String,
  val titleTa: String,
  val problemStatement: String,
  val architecturalApproach: String,
  val billOfMaterials: List<BillOfMaterialItem>,
  val estimatedTotalCostInr: Double,
  val prototypePhases: List<String>,
  val testPlanChecklist: List<String>,
  val codeSnippet: String,
  val simulationTestedInSandbox: Boolean = true
)

data class VirtualDeviceState(
  val id: String,
  val name: String,
  val type: String,
  val status: String,
  val telemetryValue: String,
  val isSimulatedOnly: Boolean = true
)

data class DigitalTwinSandbox(
  val simulationName: String,
  val activeScenario: String,
  val riskAssessment: String,
  val safetyApproved: Boolean,
  val devices: List<VirtualDeviceState>
)

// ============================================================================
// 10. MULTIMODAL CREATIVE STUDIOS
// ============================================================================

enum class ImageStyle(val labelTa: String, val labelEn: String) {
  FUTURISTIC_CONCEPT("எதிர்கால வடிவமைப்பு", "Futuristic Concept"),
  HOLOGRAPHIC_3D("ஹாலோகிராபிக் 3D", "Holographic 3D"),
  SCIENTIFIC_DIAGRAM("அறிவியல் வரைபடம்", "Scientific Diagram"),
  UI_UX_PROTOTYPE("செயலி வடிவமைப்பு", "UI/UX Prototype"),
  CINEMATIC_POSTER("திரைப்பட போஸ்டர்", "Cinematic Poster"),
  BRAND_LOGO("சின்னம் / லோகோ", "Brand Logo"),
  EDUCATIONAL_INFOGRAPHIC("கல்வி தகவல் வரைபடம்", "Educational Infographic")
}

data class ImageCreationProject(
  val id: String = UUID.randomUUID().toString(),
  val title: String,
  val prompt: String,
  val style: ImageStyle,
  val aspectRatio: String = "16:9",
  val createdAt: String,
  val generatedImageUrl: String,
  val promptExpansionTamil: String,
  val qualityScore: Int = 98
)

data class VideoStoryboardFrame(
  val frameNumber: Int,
  val timestampSec: String,
  val visualDescription: String,
  val visualDescriptionTa: String,
  val voiceoverScript: String,
  val motionDirective: String,
  val frameImageUrl: String
)

data class VideoPipelineProject(
  val id: String = UUID.randomUUID().toString(),
  val title: String,
  val ideaPrompt: String,
  val scriptTamil: String,
  val scriptEnglish: String,
  val targetDurationSec: Int = 30,
  val storyboardFrames: List<VideoStoryboardFrame>,
  val audioTrackName: String,
  val status: String = "வடிவமைக்கப்பட்டது (Ready to Play)"
)

data class AudioCreationProject(
  val id: String = UUID.randomUUID().toString(),
  val title: String,
  val spokenText: String,
  val voiceName: String,
  val language: String,
  val durationSec: Int,
  val soundscapeType: String,
  val waveformData: List<Float>
)

data class SpatialHologramModel(
  val id: String = UUID.randomUUID().toString(),
  val name: String,
  val category: String,
  val descriptionTa: String,
  val polygonCount: String,
  val isExplodedView: Boolean = false,
  val rotationX: Float = 25f,
  val rotationY: Float = 45f,
  val rotationZ: Float = 0f,
  val coreParts: List<String>
)

// ============================================================================
// 11. PERMISSION INTELLIGENCE, SAFETY KERNEL & REVERSIBILITY
// ============================================================================

enum class PermissionActionType(val labelTa: String, val labelEn: String) {
  READ("பார்வையிடு (Read)", "Read"),
  ANALYZE("பகுப்பாய்வு (Analyze)", "Analyze"),
  CREATE("உருவாக்கு (Create)", "Create"),
  MODIFY("மாற்றியமை (Modify)", "Modify"),
  DELETE("நீக்கு (Delete)", "Delete"),
  EXECUTE("செயல்படுத்து (Execute)", "Execute"),
  SHARE("பகிர் (Share)", "Share"),
  PURCHASE("கொள்முதல் (Purchase)", "Purchase"),
  COMMUNICATE("தொடர்புகொள் (Communicate)", "Communicate")
}

data class PermissionMatrixRule(
  val action: PermissionActionType,
  val entityCategory: WorldEntityCategory,
  val isAllowedAutonomous: Boolean,
  val requiresConfirmation: Boolean,
  val allowedScopeDescriptionTa: String
)

data class ReversibleActionSnapshot(
  val snapshotId: String = UUID.randomUUID().toString(),
  val actionTitleTa: String,
  val affectedTarget: String,
  val previousStateSummary: String,
  val newStateSummary: String,
  val timestamp: String,
  val isRollbackAvailable: Boolean = true
)

data class ProvenanceRecord(
  val outputId: String,
  val inputPromptHash: String,
  val modelsInvoked: List<String>,
  val toolsUsed: List<String>,
  val sourcesRetrieved: List<String>,
  val transformationsApplied: List<String>,
  val confidence: ConfidenceLevel,
  val sha256Signature: String,
  val timestamp: String
)

enum class ProviderIndependence(val titleTa: String, val titleEn: String) {
  LOCAL_CORE_ON_DEVICE("உள் சாதனம் (On-Device Local Core)", "Local On-Device"),
  PRIVATE_HOME_SERVER("தனிநபர் பிரைவேட் சர்வர் (Private Server)", "Private Local Server"),
  HYBRID_OPTIONAL_CLOUD("விரும்பினால் மட்டும் கிளவுட் (Optional Adapter)", "Hybrid Adapter")
}

data class SovereignAuditLog(
  val id: String = UUID.randomUUID().toString(),
  val timestamp: String,
  val actionSummary: String,
  val toolTriggered: String,
  val executionMode: String,
  val securityHash: String
)

enum class DeviceContinuityStatus(val deviceName: String, val iconType: String, val statusTa: String, val isCurrent: Boolean) {
  PHONE("Sovereign Phone (முதன்மை கைபேசி)", "phone", "ஆன்லைன் • செயலில்", true),
  WORKSTATION("AI Research Workstation PC", "computer", "ஒத்திசைக்கப்பட்டது • தயார்", false),
  SMART_HUB("Home IoT Sovereign Hub", "home", "பாதுகாப்பான உள்ளூர் நெட்வொர்க்", false),
  VEHICLE("Automated Vehicle Telemetry", "car", "ஸ்டேண்ட்பை • இணைக்கப்பட்டது", false),
  SPATIAL_GLASSES("Holographic AR Glasses", "glasses", "3D ஸ்ட்ரீமிங் தயார்", false)
}
