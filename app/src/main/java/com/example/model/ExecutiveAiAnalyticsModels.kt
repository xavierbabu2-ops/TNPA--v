package com.example.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

// ============================================================================
// 1. PERFORMANCE TIERS & STATUS
// ============================================================================

enum class PerformanceTier(
  val labelTamil: String,
  val labelEnglish: String,
  val gradeBadge: String,
  val colorHex: Long
) {
  EXEMPLARY("முன்மாதிரி சாதனையாளர் (Exemplary)", "Exemplary", "A+", 0xFF10B981), // Emerald Green
  EXCELLENT("சிறந்த செயல்பாடு (Excellent)", "Excellent", "A", 0xFF059669),
  GOOD("திருப்திகரமான முன்னேற்றம் (Good)", "Good", "B+", 0xFF3B82F6), // Blue
  NEEDS_ATTENTION("கவனம் & முன்னேற்றம் தேவை", "Needs Attention", "B", 0xFFF59E0B), // Amber
  CRITICAL("உடனடி கள நடவடிக்கை தேவை", "Critical Action Required", "C", 0xFFEF4444) // Red
}

// ============================================================================
// 2. AI GENERATED ADVISORY MODEL
// ============================================================================

data class AiGeneratedAdvisory(
  val id: String = "AI-ADV-${UUID.randomUUID().toString().take(6).uppercase()}",
  val generatedAt: String = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date()),
  val modelUsed: String = "Gemini 3.5 Flash AI",
  val executiveSummaryTamil: String,
  val strengthsReview: List<String>,
  val priorityActionPlan: List<String>,
  val memberGrowthStrategy: List<String>,
  val welfareSchemePush: List<String>,
  val weeklyMilestones: List<String>,
  val superAdminBriefingNote: String
)

// ============================================================================
// 3. LEADER PERFORMANCE PROFILE (STATE, DISTRICT, UNION, CITY, YOUTH WINGS)
// ============================================================================

data class LeaderPerformanceProfile(
  val id: String,
  val fullName: String,
  val tamilName: String,
  val designation: String,
  val level: AdminHierarchyLevel,
  val district: String,
  val unionOrCity: String = "",
  val mobile: String,
  val photoUrl: String? = null,
  val periodLabel: String = "நடப்பு மாதம் (ஆகஸ்ட் 2026)",

  // --- Core Monitored Metrics ---
  val memberEnrollmentTarget: Int = 500,
  val memberEnrollmentActual: Int = 380,
  val welfareBoardTarget: Int = 200,
  val welfareBoardActual: Int = 160,
  val monthlyMeetingsTarget: Int = 4,
  val monthlyMeetingsHeld: Int = 4,
  val meetingAttendancePct: Int = 92,
  val grievancesReceived: Int = 15,
  val grievancesResolved: Int = 14,
  val subscriptionCollectionPct: Int = 88,
  val conferenceAttendanceRate: Int = 100,
  val jobPlacementsAssisted: Int = 8,
  val awarenessCampsConducted: Int = 2,

  // --- Computed AI Metrics ---
  val overallAiScore: Int = 88,
  val tier: PerformanceTier = PerformanceTier.EXCELLENT,
  val statusBadgeTamil: String = "🏆 சிறந்த சாதனையாளர்",
  val rankInLevel: Int = 1,
  val keyStrengths: List<String> = listOf("துரித நலவாரிய அட்டை உதவி", "வழக்கமான நிர்வாகிகள் கூட்டம்"),
  val criticalWeaknesses: List<String> = listOf("இளைஞரணி சேர்க்கையை தீவிரப்படுத்தலாம்"),
  val lastAuditedTimestamp: String = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date()),
  val cachedAdvisory: AiGeneratedAdvisory? = null
) {
  val memberEnrollmentPct: Int
    get() = if (memberEnrollmentTarget > 0) ((memberEnrollmentActual.toDouble() / memberEnrollmentTarget) * 100).toInt().coerceIn(0, 150) else 0

  val welfareEnrollmentPct: Int
    get() = if (welfareBoardTarget > 0) ((welfareBoardActual.toDouble() / welfareBoardTarget) * 100).toInt().coerceIn(0, 150) else 0

  val grievanceResolutionPct: Int
    get() = if (grievancesReceived > 0) ((grievancesResolved.toDouble() / grievancesReceived) * 100).toInt().coerceIn(0, 100) else 100
}

// ============================================================================
// 4. OVERALL STATE-WIDE MONITORING DASHBOARD SUMMARY
// ============================================================================

data class AiMonitoringDashboardSummary(
  val totalMonitoredLeaders: Int = 48,
  val stateAverageScore: Int = 84,
  val topPerformingDistrict: String = "மதுரை மாவட்டம்",
  val topPerformingUnion: String = "மணப்பாறை ஒன்றியம் (திருச்சி)",
  val leadersExemplaryCount: Int = 14,
  val leadersNeedsAttentionCount: Int = 6,
  val totalMembersEnrolledStateWide: Int = 14850,
  val totalStateEnrollmentGoal: Int = 20000,
  val totalWelfareCardsStateWide: Int = 8920,
  val lastUpdated: String = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date())
)

// ============================================================================
// 5. INTERACTIVE AI CHAT & ADVISORY MESSAGES
// ============================================================================

enum class ChatSender {
  USER,
  AI_ASSISTANT
}

data class AiChatMessage(
  val id: String = UUID.randomUUID().toString(),
  val sender: ChatSender,
  val textTamil: String,
  val timestamp: String = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date()),
  val suggestedPromptChips: List<String> = emptyList()
)

// ============================================================================
// 6. DISTRICT/UNION KPI ALLOCATION
// ============================================================================

data class DistrictKpiAllocation(
  val districtName: String,
  val targetMembers: Int,
  val targetWelfareCards: Int,
  val minimumMeetingsPerMonth: Int = 4,
  val assignedBy: String = "மாநில பொதுக்குழு"
)
