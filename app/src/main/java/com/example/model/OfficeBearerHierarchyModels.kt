package com.example.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

// ============================================================================
// 1. ADMINISTRATIVE HIERARCHY LEVELS (8 DISTINCT SECTIONS)
// ============================================================================

enum class AdminHierarchyLevel(
  val id: String,
  val labelTamil: String,
  val labelEnglish: String,
  val iconEmoji: String,
  val orderIndex: Int
) {
  STATE("state", "மாநில நிர்வாகிகள்", "State Office Bearers", "🏛️", 1),
  ZONE("zone", "மண்டல நிர்வாகிகள்", "Zone / Region Office Bearers", "🗺️", 2),
  DISTRICT("district", "மாவட்ட நிர்வாகிகள்", "District Office Bearers", "🏢", 3),
  UNION("union", "ஒன்றிய நிர்வாகிகள்", "Union / Block Office Bearers", "🌾", 4),
  CITY("city", "நகர நிர்வாகிகள்", "City / Town Office Bearers", "🏙️", 5),
  DISTRICT_YOUTH("dist_youth", "மாவட்ட இளைஞரணி", "District Youth Wing", "⚡", 6),
  UNION_YOUTH("union_youth", "ஒன்றிய இளைஞரணி", "Union Youth Wing", "🚩", 7),
  CITY_YOUTH("city_youth", "நகர இளைஞரணி", "City Youth Wing", "🎯", 8)
}

// ============================================================================
// 2. MASTER DATA: 38 OFFICIAL DISTRICTS OF TAMIL NADU
// ============================================================================

object TamilNaduMasterData {

  val DISTRICTS_38 = listOf(
    "சென்னை (Chennai)",
    "மதுரை (Madurai)",
    "திருச்சிராப்பள்ளி (Tiruchirappalli)",
    "கோயம்புத்தூர் (Coimbatore)",
    "சேலம் (Salem)",
    "திருநெல்வேலி (Tirunelveli)",
    "தூத்துக்குடி (Thoothukudi)",
    "ஈரோடு (Erode)",
    "திருப்பூர் (Tiruppur)",
    "திண்டுக்கல் (Dindigul)",
    "தஞ்சாவூர் (Thanjavur)",
    "வேலூர் (Vellore)",
    "திருப்பத்தூர் (Tirupathur)",
    "இராணிப்பேட்டை (Ranipet)",
    "திருவண்ணாமலை (Tiruvannamalai)",
    "விழுப்புரம் (Villupuram)",
    "கள்ளக்குறிச்சி (Kallakurichi)",
    "கடலூர் (Cuddalore)",
    "மயிலாடுதுறை (Mayiladuthurai)",
    "நாகப்பட்டினம் (Nagapattinam)",
    "திருவாரூர் (Tiruvarur)",
    "புதுக்கோட்டை (Pudukkottai)",
    "சிவகங்கை (Sivagangai)",
    "இராமநாதபுரம் (Ramanathapuram)",
    "விருதுநகர் (Virudhunagar)",
    "தென்காசி (Tenkasi)",
    "கன்னியாகுமரி (Kanniyakumari)",
    "தேனி (Theni)",
    "கரூர் (Karur)",
    "அரியலூர் (Ariyalur)",
    "பெரம்பலூர் (Perambalur)",
    "நாமக்கல் (Namakkal)",
    "தர்மபுரி (Dharmapuri)",
    "கிருஷ்ணகிரி (Krishnagiri)",
    "காஞ்சிபுரம் (Kanchipuram)",
    "செங்கல்பட்டு (Chengalpattu)",
    "திருவள்ளூர் (Tiruvallur)",
    "நீலகிரி (Nilgiris)"
  )

  val ZONES_LIST = listOf(
    "தென் மண்டலம் (South Zone - மதுரை, நெல்லை, தூத்துக்குடி, கன்னியாகுமரி, தென்காசி, விருதுநகர், ராமநாதபுரம், சிவகங்கை, திண்டுக்கல், தேனி)",
    "மத்திய & டெல்டா மண்டலம் (Central & Delta Zone - திருச்சி, தஞ்சை, திருவாரூர், நாகை, மயிலாடுதுறை, புதுக்கோட்டை, கரூர், பெரம்பலூர், அரியலூர்)",
    "மேற்கு மண்டலம் (West Zone - கோவை, திருப்பூர், ஈரோடு, சேலம், நாமக்கல், நீலகிரி)",
    "வட மண்டலம் (North Zone - வேலூர், திருப்பத்தூர், ராணிப்பேட்டை, திருவண்ணாமலை, விழுப்புரம், கள்ளக்குறிச்சி, கடலூர், தர்மபுரி, கிருஷ்ணகிரி)",
    "சென்னை பெருநகர மண்டலம் (Chennai Metropolitan Zone - சென்னை, செங்கல்பட்டு, காஞ்சிபுரம், திருவள்ளூர்)"
  )

  // Standard Post Suggestions for Each Level
  val STATE_POSTS = listOf(
    "மாநிலத் தலைவர் (State President)",
    "மாநில பொதுச் செயலாளர் (State General Secretary)",
    "மாநில பொருளாளர் (State Treasurer)",
    "மாநில துணைத் தலைவர் (State Vice President)",
    "மாநில துணைப் பொதுச் செயலாளர் (State Joint General Secretary)",
    "மாநில செயலாளர் (State Secretary)",
    "மாநில அமைப்புச் செயலாளர் (State Organizing Secretary)",
    "மாநில கொள்கை பரப்புச் செயலாளர் (State Propaganda Secretary)",
    "மாநில நிர்வாகக் குழு உறுப்பினர் (State Executive Member)",
    "மாநில சட்ட ஆலோசகர் (State Legal Advisor)"
  )

  val ZONE_POSTS = listOf(
    "மண்டலத் தலைவர் (Zonal President)",
    "மண்டல செயலாளர் (Zonal Secretary)",
    "மண்டல பொருளாளர் (Zonal Treasurer)",
    "மண்டல அமைப்பாளர் (Zonal Organizer)",
    "மண்டல ஒருங்கிணைப்பாளர் (Zonal Coordinator)"
  )

  val DISTRICT_POSTS = listOf(
    "மாவட்டத் தலைவர் (District President)",
    "மாவட்டச் செயலாளர் (District Secretary)",
    "மாவட்டப் பொருளாளர் (District Treasurer)",
    "மாவட்ட துணைத் தலைவர் (District Vice President)",
    "மாவட்ட துணைச் செயலாளர் (District Joint Secretary)",
    "மாவட்ட அமைப்பாளர் (District Organizer)",
    "மாவட்ட நிர்வாகக் குழு உறுப்பினர் (District Executive Member)"
  )

  val UNION_POSTS = listOf(
    "ஒன்றியத் தலைவர் (Union President)",
    "ஒன்றியச் செயலாளர் (Union Secretary)",
    "ஒன்றியப் பொருளாளர் (Union Treasurer)",
    "ஒன்றிய துணைத் தலைவர் (Union Vice President)",
    "ஒன்றிய அமைப்பாளர் (Union Organizer)"
  )

  val CITY_POSTS = listOf(
    "நகரத் தலைவர் (City / Town President)",
    "நகரச் செயலாளர் (City / Town Secretary)",
    "நகரப் பொருளாளர் (City / Town Treasurer)",
    "நகர துணைத் தலைவர் (City Vice President)",
    "நகர அமைப்பாளர் (City Organizer)"
  )

  val YOUTH_WING_POSTS = listOf(
    "இளைஞரணித் தலைவர் (Youth Wing President)",
    "இளைஞரணிச் செயலாளர் (Youth Wing Secretary)",
    "இளைஞரணிப் பொருளாளர் (Youth Wing Treasurer)",
    "இளைஞரணி துணைத் தலைவர் (Youth Wing Vice President)",
    "இளைஞரணி அமைப்பாளர் (Youth Wing Organizer)"
  )
}

// ============================================================================
// 3. HIERARCHY OFFICE BEARER MODEL
// ============================================================================

data class HierarchyOfficeBearer(
  val id: String = "TNPA-OB-${UUID.randomUUID().toString().take(6).uppercase()}",
  val fullName: String,
  val tamilName: String,
  val designation: String,
  val level: AdminHierarchyLevel,
  val district: String = "தமிழ்நாடு முழுவதும் (All TN)",
  val zone: String = "",
  val unionName: String = "",
  val cityName: String = "",
  val mobile: String,
  val altPhone: String = "",
  val photoUrl: String? = null,
  val startDate: String = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(Date()),
  val endDate: String? = null,
  val isActive: Boolean = true,
  val appointedByAdmin: String = "Super Admin",
  val notes: String = "",
  val createdAt: Long = System.currentTimeMillis()
)

// ============================================================================
// 4. APPOINTMENT AUDIT & TRANSFER HISTORY LOG
// ============================================================================

data class AppointmentAuditLog(
  val id: String = "LOG-${UUID.randomUUID().toString().take(6).uppercase()}",
  val positionName: String,
  val level: AdminHierarchyLevel,
  val jurisdiction: String, // District / Union / City / Zone
  val previousBearerName: String = "-",
  val newBearerName: String,
  val changedByAdmin: String,
  val adminRole: String,
  val actionType: String, // "புதிய நியமனம்", "பொறுப்பு மாற்றம்", "செயலிழக்கம்", "மீண்டும் செயல்முறை", "நீக்கம்"
  val timestamp: String = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date()),
  val reason: String = "சங்க நிர்வாக ஒழுங்குமுறை"
)
