package com.example.model

import java.util.UUID

/**
 * NEXT-LEVEL PERSONAL SOVEREIGN INTELLIGENCE SUITE (30 Groundbreaking Pillars)
 * Intent Prediction, AI Eyes (Vision AR), AI Ears (360° Sounds), Presence & Habit Intelligence,
 * "What Am I Forgetting?", Life Commands, Decision "Why?" Memory, Contradiction Detection,
 * Think-With-Me, Devil's Advocate, Personal Constitution, Second Brain & Skill Tree.
 */

// 1. INTENT PREDICTION ENGINE
data class PredictedIntentSuggestion(
  val id: String = UUID.randomUUID().toString(),
  val triggerContext: String,
  val predictedIntentTa: String,
  val predictedIntentEn: String,
  val suggestedActionTa: String,
  val confidenceScore: Float, // 0.0 to 1.0
  val requiresOwnerConsent: Boolean = true
)

// 2. "AI EYES" - CAMERA CONTEXT & AR REPAIR
enum class VisualDiagnosticType(val labelTa: String, val labelEn: String) {
  OBJECT_IDENTIFICATION("பொருள் அடையாளம் காணுதல்", "Object Identification"),
  FAULT_DETECTION("சிக்கல் / பழுது கண்டறிதல்", "Fault / Defect Diagnosis"),
  REPAIR_GUIDANCE("பழுதுநீக்கும் வழிகாட்டல் (AR)", "Step-by-Step Repair Guide"),
  CIRCUIT_INSPECTION("மின்சுற்று & PCB ஆய்வு", "Electronic Circuit Inspection"),
  DOCUMENT_ANALYSIS("ஆவண ஸ்கேன் & சுருக்கம்", "Document & Blueprint Scan")
}

data class VisualSceneInsight(
  val id: String = UUID.randomUUID().toString(),
  val sceneTitleTa: String,
  val diagnosticType: VisualDiagnosticType,
  val detectedComponents: List<String>,
  val rootCauseDiagnosisTa: String,
  val repairStepsTa: List<String>,
  val arOverlayNotes: String,
  val safetyWarningTa: String?
)

// 3. "AI EARS" - 360° SOUND & AMBIENT ENVIRONMENT AWARENESS
enum class AmbientSoundType(
  val labelTa: String,
  val labelEn: String,
  val severity: String,
  val iconName: String,
  val colorHex: Long
) {
  DOORBELL("அழைப்பு மணி (Doorbell)", "Doorbell", "Normal", "doorbell", 0xFF38BDF8),
  SMOKE_ALARM("புகை / தீ எச்சரிக்கை (Smoke Alarm)", "Smoke Alarm", "High Emergency", "fire", 0xFFEF4444),
  BABY_CRY("குழந்தை அழுகை (Baby Cry)", "Baby Cry", "Attention Needed", "child", 0xFFF59E0B),
  GLASS_BREAKING("கண்ணாடி உடைதல் (Glass Break)", "Glass Breaking", "Security Alert", "warning", 0xFFEF4444),
  VEHICLE_HORN("வாகன ஒலி (Vehicle Horn)", "Vehicle Horn", "Contextual", "car", 0xFF94A3B8),
  EMERGENCY_SIREN("அவசர ஊர்தி சைரன் (Siren)", "Emergency Siren", "High Alert", "siren", 0xFFEF4444),
  APPLIANCE_BEEP("சமையல் / வாஷிங் இயந்திர பீப்", "Appliance Finished", "Normal", "kitchen", 0xFF10B981)
}

data class DetectedAmbientSound(
  val id: String = UUID.randomUUID().toString(),
  val soundType: AmbientSoundType,
  val timestamp: String,
  val decibelLevel: Int,
  val confidence: Float,
  val contextAdviceTa: String,
  val autoMutedForOwnerPrivacy: Boolean = false
)

// 4. PRESENCE & DEVICE SIGNAL INTELLIGENCE
data class PresenceContext(
  val primaryLocationLabel: String, // Home Office, Living Room, Vehicle, Transit
  val ownerDeviceInHand: Boolean,
  val nearbyAuthorizedDevices: List<String>,
  val ambientLightingState: String,
  val acousticNoiseLevel: String,
  val privacyZoneActive: Boolean
)

// 5. HABIT INTELLIGENCE & SHORTCUT CREATOR
data class PersonalHabitInsight(
  val id: String = UUID.randomUUID().toString(),
  val patternNameTa: String,
  val patternNameEn: String,
  val frequencyDescription: String,
  val proposedShortcutTa: String,
  val estimatedTimeSavedMinutesPerWeek: Int,
  val isAdopted: Boolean = false
)

// 6. "WHAT AM I FORGETTING?" FORGETFULNESS RADAR
data class DepartureChecklistItem(
  val id: String = UUID.randomUUID().toString(),
  val itemNameTa: String,
  val reasonTa: String,
  val urgencyLevel: String, // Critical, Useful, Weather-Related
  val isChecked: Boolean = false
)

// 7. LIFE COMMAND MULTI-STEP ORCHESTRATION
data class LifeCommandWorkflow(
  val id: String = UUID.randomUUID().toString(),
  val commandNameTa: String,
  val originalPrompt: String,
  val totalStepsCount: Int,
  val currentExecutingStepIndex: Int,
  val executionStepsTa: List<String>,
  val generatedArtifactsTa: List<String>,
  val isCompleted: Boolean = false
)

// 8. "WHY?" DECISION MEMORY
data class DecisionWhyRecord(
  val id: String = UUID.randomUUID().toString(),
  val projectOrTopicName: String,
  val decisionSummaryTa: String,
  val whyRationaleTa: String,
  val rejectedAlternativesTa: List<String>,
  val decisionTimestamp: String,
  val contextFactors: List<String>
)

// 9. CONTRADICTION DETECTOR
data class ContradictionConflict(
  val id: String = UUID.randomUUID().toString(),
  val currentStatementTa: String,
  val pastConflictingStatementTa: String,
  val pastDateOrContext: String,
  val aiClarificationQueryTa: String,
  val isResolved: Boolean = false
)

// 10. "THINK WITH ME" & DEVIL'S ADVOCATE
data class TradeoffOptionAnalysis(
  val optionTitleTa: String,
  val advantagesTa: List<String>,
  val disadvantagesTa: List<String>,
  val potentialRisksTa: List<String>,
  val resourceCostScore: String
)

data class ThinkWithMeSession(
  val id: String = UUID.randomUUID().toString(),
  val problemTitleTa: String,
  val options: List<TradeoffOptionAnalysis>,
  val devilsAdvocateCritiqueTa: String,
  val synthesizedBalancedRecommendationTa: String
)

// 11. DISCOVERY & AUTO EXPERIMENT DESIGNER
data class ExperimentDesignPlan(
  val id: String = UUID.randomUUID().toString(),
  val researchHypothesisTa: String,
  val independentVariables: List<String>,
  val dependentVariables: List<String>,
  val experimentalMethodologyTa: String,
  val simulationTestStepsTa: List<String>,
  val expectedMeasurableOutcomeTa: String,
  val empiricalVerificationStatus: String
)

// 12. PERSONAL AI CONSTITUTION (OWNER-ONLY POLICY KERNEL)
data class ConstitutionRule(
  val ruleId: String,
  val titleTa: String,
  val ruleDescriptionTa: String,
  val isAutonomousAllowed: Boolean,
  val requiresExplicitConfirmation: Boolean,
  val isStrictlyForbidden: Boolean = false,
  val securityPriority: Int = 1
)

// 13. PERSONAL SECOND BRAIN & SKILL TREE
data class SkillTreeNode(
  val id: String,
  val skillNameTa: String,
  val skillNameEn: String,
  val category: String, // Science, Code, Creative, Life
  val masteryPercentage: Int, // 0 to 100
  val prerequisiteSkills: List<String>,
  val subSkills: List<String>,
  val isCompleted: Boolean
)

data class OpportunityRadarItem(
  val id: String = UUID.randomUUID().toString(),
  val titleTa: String,
  val intersectingFactors: List<String>, // e.g. ["IoT Project", "Plasma Research", "3D Printing"]
  val potentialInnovationTa: String,
  val feasibilityScore: Int // 0 to 100
)

data class IdeaGraveyardItem(
  val id: String = UUID.randomUUID().toString(),
  val originalIdeaTitleTa: String,
  val archivedDate: String,
  val originalBlockerReasonTa: String,
  val whyPracticalNowTa: String,
  val isRevived: Boolean = false
)
