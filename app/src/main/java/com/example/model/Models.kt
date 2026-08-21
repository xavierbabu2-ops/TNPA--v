package com.example.model

enum class StreamStatus {
  LIVE,
  CONNECTING,
  OFFLINE,
  ERROR
}

enum class MediaType {
  HLS,
  YOUTUBE,
  MP4_DIRECT,
  WEBM
}

data class StreamHealthReport(
  val status: StreamStatus,
  val statusCode: Int = 200,
  val statusMessage: String = "OK",
  val latencyMs: Long = 34L,
  val isServerReachable: Boolean = true,
  val isHlsValid: Boolean = true,
  val activeBitrate: String = "4500 kbps",
  val fps: Int = 60,
  val resolution: String = "1080p",
  val endpoint: String = "/api/stream/health",
  val timestamp: String = "00:00:00",
  val errorDetails: String? = null
)

data class MemberProfile(
  val id: String,
  val fullName: String,
  val tamilName: String,
  val age: Int = 32,
  val experienceYears: Int = 8,
  val mobile: String,
  val email: String = "",
  val whatsapp: String = "",
  val designation: String = "உறுப்பினர் (Member)",
  val district: String,
  val address: String = "",
  val joinedDate: String = "17-Aug-2026",
  val status: String = "செயலில் உள்ளது (Active)",
  val bloodGroup: String = "O+",
  val specialization: String = "சுவர் ஓவியம் / பில்டிங் பெயிண்டிங் (Wall & Building)",
  val isSyncedToFirestore: Boolean = true
)

data class ArtItem(
  val id: String,
  val title: String,
  val artistName: String,
  val district: String,
  val category: String, // "சுவர் ஓவியம்", "உருவப்படம்", "3D ஆர்ட்", "கோவில் சித்திரம்", "போர்டு ரைட்டிங்"
  val likesCount: Int,
  val description: String,
  val medium: String = "அக்ரிலிக் & ஆயில் (Acrylic & Oil)",
  val dimensions: String = "12 x 8 அடி"
)

data class WelfareScheme(
  val id: String,
  val title: String,
  val tamilTitle: String,
  val amount: String,
  val description: String,
  val eligibility: String,
  val tag: String
)

data class OfficeBearer(
  val name: String,
  val role: String,
  val district: String,
  val contact: String
)

data class StateLeaderItem(
  val id: String,
  val designationTamil: String,
  val designationEnglish: String,
  val fullNameTamil: String,
  val mobileNumber: String,
  val location: String,
  val photoUrl: String? = null,
  val email: String? = null,
  val badgeThemeColorHex: Long = 0xFFDC2626,
  val orderPriority: Int = 1,
  val isTopLeader: Boolean = true
)
