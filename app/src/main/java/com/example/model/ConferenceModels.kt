package com.example.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

// ============================================================================
// 1. EXECUTIVE CONFERENCE ROOM MODEL
// ============================================================================

data class ConferenceRoom(
  val id: String = "ROOM-${UUID.randomUUID().toString().take(6).uppercase()}",
  val titleTamil: String,
  val titleEnglish: String,
  val meetingCode: String,
  val passcode: String = "TNPA2024",
  val hostName: String,
  val hostRole: String,
  val hostMobile: String = "7010131915",
  val allowedLevels: List<AdminHierarchyLevel> = listOf(
    AdminHierarchyLevel.STATE,
    AdminHierarchyLevel.ZONE,
    AdminHierarchyLevel.DISTRICT,
    AdminHierarchyLevel.UNION,
    AdminHierarchyLevel.CITY,
    AdminHierarchyLevel.DISTRICT_YOUTH,
    AdminHierarchyLevel.UNION_YOUTH,
    AdminHierarchyLevel.CITY_YOUTH
  ),
  val isLive: Boolean = true,
  val scheduledTime: String = "இன்று • 10:30 AM (தற்போது செயலில்)",
  val agendaPoints: List<String> = listOf(
    "1. தமிழ்நாடு முழுவதும் 38 மாவட்டங்களில் உறுப்பினர் பதிவு விரிவாக்கம்.",
    "2. தமிழ்நாடு கட்டுமான மற்றும் அமைப்புசாரா நலவாரிய அட்டைகள் விரைவு வழங்கல்.",
    "3. மாநில, மண்டல, மாவட்ட, நகர, ஒன்றிய நிர்வாகிகள் ஒருங்கிணைப்பு.",
    "4. ஓவியர்களின் உரிமைகள் & அரசு நலத்திட்ட மானியக் கோரிக்கைகள்."
  ),
  val maxParticipants: Int = 500
)

// ============================================================================
// 2. CONFERENCE PARTICIPANT (REGISTERED EXECUTIVES ONLY)
// ============================================================================

data class ConferenceParticipant(
  val id: String = "PART-${UUID.randomUUID().toString().take(6).uppercase()}",
  val bearerId: String? = null,
  val name: String,
  val tamilName: String,
  val designation: String,
  val level: AdminHierarchyLevel,
  val district: String,
  val unionOrCity: String = "",
  val mobile: String,
  val isHost: Boolean = false,
  val isMicOn: Boolean = false,
  val isVideoOn: Boolean = true,
  val isHandRaised: Boolean = false,
  val isSpeaking: Boolean = false,
  val joinedAt: String = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date()),
  val avatarColorHex: Long = 0xFFDC2626
)

// ============================================================================
// 3. IN-CONFERENCE CHAT MESSAGE & RESOLUTION
// ============================================================================

data class ConferenceChatMessage(
  val id: String = "MSG-${UUID.randomUUID().toString().take(6).uppercase()}",
  val senderName: String,
  val senderDesignation: String,
  val level: AdminHierarchyLevel,
  val district: String,
  val message: String,
  val time: String = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date()),
  val isResolution: Boolean = false
)

data class MeetingResolution(
  val id: String = "RES-${UUID.randomUUID().toString().take(4).uppercase()}",
  val resolutionNo: Int,
  val titleTamil: String,
  val proposedBy: String,
  val secondedBy: String,
  val status: String = "ஏகமனதாக நிறைவேற்றப்பட்டது (Passed)",
  val timestamp: String = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date())
)

// ============================================================================
// 4. VERIFICATION RESULT FOR EXECUTIVE ACCESS GATE
// ============================================================================

sealed class ExecutiveVerificationResult {
  data class Success(
    val participant: ConferenceParticipant,
    val token: String = UUID.randomUUID().toString()
  ) : ExecutiveVerificationResult()

  data class Denied(
    val reasonTamil: String,
    val suggestedAction: String
  ) : ExecutiveVerificationResult()
}
