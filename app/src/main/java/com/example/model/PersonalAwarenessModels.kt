package com.example.model

import androidx.compose.ui.graphics.Color
import java.util.UUID

/**
 * PERSONAL SOVEREIGN AI - PERSONAL COMMUNICATION, AWARENESS, NAVIGATION & PROACTIVE ASSISTANT
 * 
 * Core Architectural Mandates:
 * 1. Official APIs & OS Permissions only (Zero unsafe password storage).
 * 2. Owner-Controlled Privacy & Strict Voice Authentication before exposing sensitive communication.
 * 3. Never hallucinate/guess missing caller names or locations; explicit fallback when data is unavailable.
 * 4. Observe -> Understand -> Prioritize -> Inform -> Assist -> Execute -> Verify workflow.
 */

// ============================================================================
// 1. COMMUNICATION PLATFORMS & ACCESS GATEWAY
// ============================================================================

enum class CommunicationPlatform(
  val id: String,
  val labelTa: String,
  val labelEn: String,
  val officialAccessMethod: String,
  val brandColorHex: Long
) {
  WHATSAPP("whatsapp", "வாட்ஸ்அப் (WhatsApp)", "WhatsApp", "Official Android Notification Listener & Intent Gateway", 0xFF25D366),
  MESSENGER("messenger", "மெசஞ்சர் (Messenger)", "Messenger", "Meta Graph API (User-Authorized OAuth2 / OS Channel)", 0xFF0084FF),
  INSTAGRAM("instagram", "இன்ஸ்டாகிராம் (Instagram)", "Instagram", "Meta Official Direct Messaging API Adapter", 0xFFE1306C),
  SMS("sms", "குறுஞ்செய்தி (SMS)", "SMS Messaging", "Android Telephony OS Permissions (Telephony / Carrier)", 0xFF38BDF8),
  EMAIL("email", "மின்னஞ்சல் (Email)", "Secure Email", "IMAP/SMTP with OAuth2 Tokenization (No Raw Password)", 0xFFEA4335),
  PHONE_CALL("phone", "தொலைபேசி அழைப்பு (Phone Calls)", "Phone Calls", "Android Telecom / CallScreeningService Framework", 0xFF10B981)
}

enum class MessagePriority(
  val labelTa: String,
  val labelEn: String,
  val rank: Int,
  val badgeColorHex: Long
) {
  CRITICAL("உடனடி கவனம் தேவை (Critical)", "Critical", 1, 0xFFEF4444),
  IMPORTANT("முக்கியமானது (Important)", "Important", 2, 0xFFF59E0B),
  NORMAL("சாதாரணமானது (Normal)", "Normal", 3, 0xFF38BDF8),
  LOW_PRIORITY("குறைந்த முன்னுரிமை (Low Priority)", "Low Priority", 4, 0xFF94A3B8),
  PROMOTIONAL_NOISE("விளம்பரம் / அவசியமற்றது (Promotional)", "Promotional / Noise", 5, 0xFF64748B)
}

enum class MessageIntentType(
  val labelTa: String,
  val labelEn: String,
  val iconName: String
) {
  MEETING_REQUEST("சந்திப்பு / அப்பாயின்ட்மென்ட் கோரிக்கை", "Meeting / Appointment Request", "calendar"),
  TASK_ACTION_REQUEST("பணி / ஆவண அனுப்புகை கோரிக்கை", "Task / Action Request", "task"),
  URGENT_QUERY("உடனடி தகவல் வினவல்", "Urgent Query", "help"),
  GENERAL_CHAT("பொதுவான நட்பு உரையாடல்", "Casual Conversation", "chat"),
  PAYMENT_OR_FINANCIAL("நிதி / பரிவர்த்தனை செய்தி", "Financial / Payment", "payment"),
  SUSPICIOUS_SCAM("சந்தேகத்திற்கிடமான செய்தி (Suspicious)", "Potential Scam / Spam", "warning")
}

enum class ActionRequirement(val labelTa: String, val labelEn: String) {
  REPLY_REQUIRED("பதில் அனுப்ப வேண்டும்", "Reply Required"),
  CALENDAR_DRAFT_READY("நாட்காட்டியில் சேர்க்கலாம்", "Calendar Event Draft Ready"),
  TASK_CREATION_RECOMMENDED("நினைவூட்டல் / Task உருவாக்கலாம்", "Task Reminder Recommended"),
  NO_ACTION_INFORMATIONAL("தகவலுக்கு மட்டும்", "Informational Only"),
  SECURITY_WARNING_BLOCK("எச்சரிக்கை / பாதுகாப்பு நடவடிக்கை", "Security Warning / Block")
}

data class UnifiedMessage(
  val id: String = UUID.randomUUID().toString(),
  val senderName: String,
  val senderHandle: String,
  val platform: CommunicationPlatform,
  val timestamp: String,
  val rawContent: String,
  val normalizedTamilSummary: String,
  val priority: MessagePriority,
  val intentType: MessageIntentType,
  val actionRequired: ActionRequirement,
  val suggestedTamilReplies: List<String> = emptyList(),
  val suggestedTanglishReplies: List<String> = emptyList(),
  val isRead: Boolean = false,
  val isSpamOrSuspicious: Boolean = false,
  val spamRiskEvidence: String? = null,
  val linkedCalendarDraft: CalendarEventDraft? = null,
  val linkedTaskDraft: TaskReminderDraft? = null
)

data class CalendarEventDraft(
  val id: String = UUID.randomUUID().toString(),
  val title: String,
  val titleTa: String,
  val date: String,
  val time: String,
  val location: String?,
  val participants: List<String>,
  val isApproved: Boolean = false
)

data class TaskReminderDraft(
  val id: String = UUID.randomUUID().toString(),
  val title: String,
  val titleTa: String,
  val dueDate: String,
  val dueTime: String,
  val priority: MessagePriority,
  val isCreated: Boolean = false
)

// ============================================================================
// 2. CALL INTELLIGENCE & SCREENING
// ============================================================================

enum class CallCategory(val labelTa: String, val labelEn: String, val colorHex: Long) {
  KNOWN_VIP("முக்கிய நபர் (VIP Contact)", "VIP Contact", 0xFF10B981),
  KNOWN_CONTACT("அறிந்த தொடர்பாளர் (Known Contact)", "Known Contact", 0xFF38BDF8),
  REPEAT_CALLER_TODAY("இன்று பலமுறை அழைத்தவர் (Repeated Caller)", "Repeated Caller", 0xFFF59E0B),
  UNKNOWN_NUMBER("அறியப்படாத எண் (Unknown Number)", "Unknown Caller", 0xFF94A3B8),
  SUSPECTED_SPAM_SCAM("சந்தேகத்திற்கிடமான ஸ்பேம் (Potential Spam)", "Suspected Spam/Robocall", 0xFFEF4444),
  EMERGENCY_PRIORITY("அவசர அழைப்பு (Emergency Priority)", "Emergency Priority", 0xFFDC2626)
}

data class IncomingCallRecord(
  val id: String = UUID.randomUUID().toString(),
  val callerNumber: String,
  val callerVerifiedName: String?, // null if unverified / not in contacts
  val timestamp: String,
  val callCategory: CallCategory,
  val repeatedCallsCountToday: Int = 1,
  val screeningInsightTa: String,
  val spamConfidenceScore: Float = 0.0f,
  val spamEvidenceReasonTa: String? = null,
  val recommendedActionTa: String
)

// ============================================================================
// 3. LOCATION CONTEXT & PRIVACY ZONES
// ============================================================================

enum class LocationPrivacyZone(val labelTa: String, val labelEn: String, val policyDescTa: String) {
  NORMAL_LOGGED("இயல்பான பதிவு (Normal)", "Normal Awareness", "பயண வழிசெலுத்தலுக்கு சூழல் பதிவு அனுமதிக்கப்பட்டுள்ளது"),
  STRICT_NO_STORE("பதிவு செய்யாதே (Do Not Store)", "Private / Do Not Store", "இருப்பிட வரலாறு நினைவகத்தில் சேமிக்கப்படாது"),
  RESTRICTED_SAFE_ZONE("பாதுகாக்கப்பட்ட பகுதி (Restricted)", "Restricted Zone", "உரிமையாளர் தவிர மற்றவருக்கு எந்த இருப்பிட தகவலும் இல்லை"),
  ANONYMIZED_COARSE("தோராயமான பகுதி மட்டும் (Coarse Only)", "Coarse Anonymized", "துல்லியமான GPS இல்லாமல் பகுதி பெயர் மட்டும்")
}

enum class PlaceCategory(val labelTa: String, val labelEn: String) {
  HOME("வீடு (Home)", "Home"),
  WORK_OFFICE("பணியிடம் / அலுவலகம் (Work)", "Work / Office"),
  FREQUENT_SPOT("அடிக்கடி செல்லும் இடம் (Frequent)", "Frequent Spot"),
  TRANSIT_ROUTE("பயண வழித்தடம் (In Transit)", "In Transit"),
  NEW_LOCATION("புதிய இடம் (New Location)", "New Location")
}

data class LocationContextState(
  val currentPlaceNameTa: String,
  val currentAreaNameTa: String,
  val approximateCoordinates: Pair<Double, Double>,
  val placeCategory: PlaceCategory,
  val privacyZone: LocationPrivacyZone = LocationPrivacyZone.NORMAL_LOGGED,
  val nearbyLandmarksTa: List<String> = emptyList(),
  val isGpsAuthorized: Boolean = true,
  val lastUpdatedTime: String
)

// ============================================================================
// 4. NAVIGATION ASSISTANT & MULTI-ROUTE INTELLIGENCE
// ============================================================================

enum class TravelMode(val labelTa: String, val labelEn: String, val iconName: String) {
  CAR("கார் (Car)", "Car / Automobile", "car"),
  TWO_WHEELER("இருசக்கர வாகனம் (Two-Wheeler)", "Two-Wheeler / Bike", "bike"),
  WALKING("நடந்து செல்லுதல் (Walking)", "Walking", "walk"),
  PUBLIC_TRANSIT("பொதுப் போக்குவரத்து (Public Transit)", "Metro / Bus", "transit")
}

enum class TrafficLevel(val labelTa: String, val labelEn: String, val colorHex: Long) {
  CLEAR("போக்குவரத்து சீரானது (Clear)", "Clear Green", 0xFF10B981),
  MODERATE("மிதமான போக்குவரத்து (Moderate)", "Moderate Yellow", 0xFFF59E0B),
  HEAVY("அதிக நெரிசல் (Heavy Traffic)", "Heavy Red", 0xFFEF4444),
  ROAD_WORK("சாலைப் பராமரிப்பு / வழிமாற்றம் (Road Work)", "Road Closure / Work", 0xFFDC2626)
}

data class NavigationRouteOption(
  val routeId: String,
  val routeTitleTa: String,
  val routeTitleEn: String,
  val distanceKm: Float,
  val estimatedMinutes: Int,
  val travelMode: TravelMode,
  val trafficLevel: TrafficLevel,
  val hasToll: Boolean,
  val roadConditionNotesTa: String,
  val comparisonRationaleTa: String, // e.g., "இந்த வழி 8 km. இன்னொரு வழி 10 km, ஆனால் traffic குறைவாக இருப்பதால் சுமார் 7 நிமிடம் சீக்கிரம் செல்லலாம்."
  val isRecommended: Boolean = false
)

data class ActiveNavigationPlan(
  val originPlaceTa: String,
  val destinationPlaceTa: String,
  val targetArrivalTime: String,
  val recommendedDepartureTime: String,
  val proactiveDepartureAlertTa: String,
  val routes: List<NavigationRouteOption>,
  val selectedRouteId: String
)

// ============================================================================
// 5. PROACTIVE PERSONAL ASSISTANCE & DAY SITUATION
// ============================================================================

enum class ProactiveAssistanceLevel(val labelTa: String, val labelEn: String, val descriptionTa: String) {
  ALWAYS_ON("எப்போதும் விழிப்புடன் (Always On)", "Always On", "அனைத்து முக்கிய சூழல்களையும் முன்கூட்டியே தெரிவிக்கும்"),
  IMPORTANT_ONLY("முக்கியமானவை மட்டும் (Important Only)", "Important Only", "அவசர செய்திகள், போக்குவரத்து மற்றும் சந்திப்புகள் மட்டும்"),
  QUIET_MODE("அமைதி முறை (Quiet)", "Quiet Mode", "கேட்டால் மட்டுமே பதிலளிக்கும், தானியங்கி தொந்தரவு இல்லை"),
  OFF("முடக்கு (Off)", "Disabled", "முன்முயற்சி அறிவிப்புகள் முடக்கப்பட்டுள்ளன")
}

data class PersonalDaySituation(
  val currentTimestamp: String,
  val locationSummaryTa: String,
  val currentActivityTa: String,
  val nextScheduledEventTa: String,
  val nextEventTime: String,
  val urgentPendingMessagesCount: Int,
  val urgentMessagesSummaryTa: String,
  val pendingCallAlertsCount: Int,
  val proactiveTravelAlertTa: String?,
  val proactiveProductivityAdviceTa: String?,
  val totalMessagesToday: Int = 37,
  val criticalMessagesToday: Int = 4,
  val pendingReplyCountToday: Int = 2
)

data class AuditableCommunicationAction(
  val id: String = UUID.randomUUID().toString(),
  val timestamp: String,
  val requestedCommandTa: String,
  val verifiedSpeaker: String, // e.g. "உரிமையாளர் (Primary Owner) - 99.4% Biometric"
  val authorizationPolicyAppliedTa: String,
  val platformActionExecuted: String,
  val executionStatusTa: String,
  val cryptographicProvenanceHash: String
)
