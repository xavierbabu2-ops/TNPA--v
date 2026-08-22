package com.example.data

import com.example.model.AdminHierarchyLevel
import com.example.model.AiChatMessage
import com.example.model.AiGeneratedAdvisory
import com.example.model.AiMonitoringDashboardSummary
import com.example.model.ChatSender
import com.example.model.HierarchyOfficeBearer
import com.example.model.LeaderPerformanceProfile
import com.example.model.PerformanceTier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ExecutiveAiMonitoringRepository {

  private val _leaderProfiles = MutableStateFlow<List<LeaderPerformanceProfile>>(emptyList())
  val leaderProfiles: StateFlow<List<LeaderPerformanceProfile>> = _leaderProfiles.asStateFlow()

  private val _dashboardSummary = MutableStateFlow(AiMonitoringDashboardSummary())
  val dashboardSummary: StateFlow<AiMonitoringDashboardSummary> = _dashboardSummary.asStateFlow()

  private val _selectedLeader = MutableStateFlow<LeaderPerformanceProfile?>(null)
  val selectedLeader: StateFlow<LeaderPerformanceProfile?> = _selectedLeader.asStateFlow()

  private val _isAiAnalyzing = MutableStateFlow(false)
  val isAiAnalyzing: StateFlow<Boolean> = _isAiAnalyzing.asStateFlow()

  private val _chatMessages = MutableStateFlow<List<AiChatMessage>>(
    listOf(
      AiChatMessage(
        sender = ChatSender.AI_ASSISTANT,
        textTamil = "வணக்கம்! நான் TNPA மாநில நிர்வாகிகள் வழிகாட்டி செயற்கை நுண்ணறிவு (AI Strategic Advisor). மாநில, மண்டல, மாவட்ட, நகர மற்றும் ஒன்றிய நிர்வாகிகளின் களச் செயல்பாடுகளை மேம்படுத்த தேவையான உடனடி ஆலோசனைகளை கேட்கலாம்.",
        suggestedPromptChips = listOf(
          "உறுப்பினர் சேர்க்கையை அதிகரிக்க உத்தி",
          "நலவாரிய அட்டை சிறப்பு முகாம் நடத்துவது எப்படி?",
          "கூட்டங்களில் நிர்வாகிகள் வருகையை கூட்டுவது எப்படி?",
          "பின்தங்கிய ஒன்றியத்தை முன்னேற்றுவது எப்படி?"
        )
      )
    )
  )
  val chatMessages: StateFlow<List<AiChatMessage>> = _chatMessages.asStateFlow()

  init {
    seedInitialProfiles()
    recalculateDashboard()
  }

  private fun seedInitialProfiles() {
    val initial = listOf(
      // 1. State Leaders
      LeaderPerformanceProfile(
        id = "TNPA-OB-001",
        fullName = "S. Michael Alvin",
        tamilName = "எஸ். மைக்கேல் ஆல்வின்",
        designation = "மாநிலத் தலைவர் (State President)",
        level = AdminHierarchyLevel.STATE,
        district = "மதுரை மாவட்டம் (HQ)",
        unionOrCity = "மாநில தலைமை",
        mobile = "9789331681",
        memberEnrollmentTarget = 2000,
        memberEnrollmentActual = 1940,
        welfareBoardTarget = 1000,
        welfareBoardActual = 970,
        monthlyMeetingsTarget = 4,
        monthlyMeetingsHeld = 4,
        meetingAttendancePct = 98,
        grievancesReceived = 45,
        grievancesResolved = 44,
        subscriptionCollectionPct = 96,
        conferenceAttendanceRate = 100,
        jobPlacementsAssisted = 32,
        awarenessCampsConducted = 6,
        overallAiScore = 98,
        tier = PerformanceTier.EXEMPLARY,
        statusBadgeTamil = "🏆 மாநில முதன்மை சாதனையாளர்",
        rankInLevel = 1,
        keyStrengths = listOf("மாநில அளவில் வலுவான தலைமை", "38 மாவட்ட ஒருங்கிணைப்பு", "துரித அரசாங்க நலத்திட்ட வழிகாட்டல்"),
        criticalWeaknesses = listOf("கிராமப்புற ஒன்றியங்களில் நேரடி களப்பார்வை அதிகரித்தல்")
      ),
      LeaderPerformanceProfile(
        id = "TNPA-OB-002",
        fullName = "Xavier Babu",
        tamilName = "சேவியர் பாபு",
        designation = "மாநில பொதுச் செயலாளர் (General Secretary)",
        level = AdminHierarchyLevel.STATE,
        district = "மதுரை மாவட்டம் (HQ)",
        unionOrCity = "மாநில தலைமை செயலகம்",
        mobile = "7010131915",
        memberEnrollmentTarget = 2000,
        memberEnrollmentActual = 1910,
        welfareBoardTarget = 1000,
        welfareBoardActual = 945,
        monthlyMeetingsTarget = 4,
        monthlyMeetingsHeld = 4,
        meetingAttendancePct = 96,
        grievancesReceived = 52,
        grievancesResolved = 51,
        subscriptionCollectionPct = 95,
        conferenceAttendanceRate = 100,
        jobPlacementsAssisted = 28,
        awarenessCampsConducted = 5,
        overallAiScore = 96,
        tier = PerformanceTier.EXEMPLARY,
        statusBadgeTamil = "⚡ நிர்வாக ஒருங்கிணைப்பு சிற்பி",
        rankInLevel = 2,
        keyStrengths = listOf("துரித டிஜிட்டல் நிர்வாகம்", "நிர்வாகிகள் வீடியோ கான்பிரன்ஸ் ஒழுங்கு", "உறுப்பினர் நலன்"),
        criticalWeaknesses = listOf("மண்டல மாநாடுகள் திட்டமிடல்")
      ),
      LeaderPerformanceProfile(
        id = "TNPA-OB-003",
        fullName = "Sakthivel",
        tamilName = "சக்திவேல்",
        designation = "மாநில பொருளாளர் (State Treasurer)",
        level = AdminHierarchyLevel.STATE,
        district = "திருச்சிராப்பள்ளி (HQ)",
        unionOrCity = "மாநில நிதி அலுவலகம்",
        mobile = "9080047281",
        memberEnrollmentTarget = 1500,
        memberEnrollmentActual = 1380,
        welfareBoardTarget = 800,
        welfareBoardActual = 720,
        monthlyMeetingsTarget = 4,
        monthlyMeetingsHeld = 4,
        meetingAttendancePct = 94,
        grievancesReceived = 20,
        grievancesResolved = 19,
        subscriptionCollectionPct = 98,
        conferenceAttendanceRate = 100,
        jobPlacementsAssisted = 15,
        awarenessCampsConducted = 4,
        overallAiScore = 94,
        tier = PerformanceTier.EXEMPLARY,
        statusBadgeTamil = "💎 நிதி மேலாண்மை சாதனையாளர்",
        rankInLevel = 3,
        keyStrengths = listOf("துல்லியமான வரவு செலவு பராமரிப்பு", "சங்க நிதி பாதுகாப்பு", "டெல்டா மண்டல உதவி"),
        criticalWeaknesses = listOf("உறுப்பினர் சேர்க்கை இலக்கை இன்னும் 8% உயர்த்தலாம்")
      ),

      // 2. District Leaders
      LeaderPerformanceProfile(
        id = "TNPA-OB-DIST-TRY-01",
        fullName = "R. Sundaramurthy",
        tamilName = "ஆர். சுந்தரமூர்த்தி",
        designation = "திருச்சி மாவட்டத் தலைவர்",
        level = AdminHierarchyLevel.DISTRICT,
        district = "திருச்சிராப்பள்ளி (Tiruchirappalli)",
        unionOrCity = "திருச்சி மாநகரம் & புறநகர்",
        mobile = "9442987654",
        memberEnrollmentTarget = 800,
        memberEnrollmentActual = 745,
        welfareBoardTarget = 400,
        welfareBoardActual = 360,
        monthlyMeetingsTarget = 4,
        monthlyMeetingsHeld = 4,
        meetingAttendancePct = 95,
        grievancesReceived = 22,
        grievancesResolved = 21,
        subscriptionCollectionPct = 92,
        conferenceAttendanceRate = 100,
        jobPlacementsAssisted = 18,
        awarenessCampsConducted = 3,
        overallAiScore = 93,
        tier = PerformanceTier.EXEMPLARY,
        statusBadgeTamil = "🌟 மாவட்ட முதலிடம்",
        rankInLevel = 1,
        keyStrengths = listOf("14 ஒன்றியங்களையும் சீராக ஒருங்கிணைத்தல்", "நலவாரிய அட்டை விரைவு பதிவு"),
        criticalWeaknesses = listOf("துறையூர் ஒன்றியத்தில் கூடுதல் கிளை துவங்குதல்")
      ),
      LeaderPerformanceProfile(
        id = "TNPA-OB-DIST-MDU-02",
        fullName = "S. Ganesan",
        tamilName = "எஸ். கணேசன்",
        designation = "மதுரை மாவட்டச் செயலாளர்",
        level = AdminHierarchyLevel.DISTRICT,
        district = "மதுரை (Madurai)",
        unionOrCity = "மதுரை மாநகர் & தெற்கு",
        mobile = "9842198765",
        memberEnrollmentTarget = 850,
        memberEnrollmentActual = 780,
        welfareBoardTarget = 450,
        welfareBoardActual = 390,
        monthlyMeetingsTarget = 4,
        monthlyMeetingsHeld = 4,
        meetingAttendancePct = 90,
        grievancesReceived = 25,
        grievancesResolved = 23,
        subscriptionCollectionPct = 89,
        conferenceAttendanceRate = 100,
        jobPlacementsAssisted = 16,
        awarenessCampsConducted = 4,
        overallAiScore = 91,
        tier = PerformanceTier.EXEMPLARY,
        statusBadgeTamil = "🔥 களப்பணி நாயகன்",
        rankInLevel = 2,
        keyStrengths = listOf("வழக்கமான மாதாந்திர கூட்டங்கள்", "தொழிலாளர் வேலைவாய்ப்பு உதவி"),
        criticalWeaknesses = listOf("உசிலம்பட்டி ஒன்றியத்தில் சேர்க்கையை அதிகரிப்பது")
      ),
      LeaderPerformanceProfile(
        id = "TNPA-OB-DIST-CHN-03",
        fullName = "K. Jayachandran",
        tamilName = "கே. ஜெயச்சந்திரன்",
        designation = "சென்னை மாவட்டத் தலைவர்",
        level = AdminHierarchyLevel.DISTRICT,
        district = "சென்னை (Chennai)",
        unionOrCity = "வட சென்னை & மத்திய சென்னை",
        mobile = "9884123456",
        memberEnrollmentTarget = 1200,
        memberEnrollmentActual = 980,
        welfareBoardTarget = 600,
        welfareBoardActual = 480,
        monthlyMeetingsTarget = 4,
        monthlyMeetingsHeld = 3,
        meetingAttendancePct = 85,
        grievancesReceived = 30,
        grievancesResolved = 26,
        subscriptionCollectionPct = 82,
        conferenceAttendanceRate = 90,
        jobPlacementsAssisted = 22,
        awarenessCampsConducted = 2,
        overallAiScore = 83,
        tier = PerformanceTier.GOOD,
        statusBadgeTamil = "📈 நிலையான வளர்ச்சி",
        rankInLevel = 3,
        keyStrengths = listOf("பெருநகர தொழில் நிறுவனங்களுடன் இணைப்பு", "அதிக வேலைவாய்ப்புகள்"),
        criticalWeaknesses = listOf("மாதாந்திர நிர்வாகிகள் கூட்டங்கள் எண்ணிக்கை உயர்த்துதல்")
      ),
      LeaderPerformanceProfile(
        id = "TNPA-OB-DIST-CBE-04",
        fullName = "M. Shanmugam",
        tamilName = "எம். சண்முகம்",
        designation = "கோவை மாவட்டச் செயலாளர்",
        level = AdminHierarchyLevel.DISTRICT,
        district = "கோயம்புத்தூர் (Coimbatore)",
        unionOrCity = "கோவை மேற்கு & பொள்ளாச்சி",
        mobile = "9443211223",
        memberEnrollmentTarget = 750,
        memberEnrollmentActual = 660,
        welfareBoardTarget = 350,
        welfareBoardActual = 305,
        monthlyMeetingsTarget = 4,
        monthlyMeetingsHeld = 4,
        meetingAttendancePct = 88,
        grievancesReceived = 18,
        grievancesResolved = 17,
        subscriptionCollectionPct = 87,
        conferenceAttendanceRate = 100,
        jobPlacementsAssisted = 14,
        awarenessCampsConducted = 3,
        overallAiScore = 87,
        tier = PerformanceTier.EXCELLENT,
        statusBadgeTamil = "⚙️ மேற்கு மண்டல வேகம்",
        rankInLevel = 4,
        keyStrengths = listOf("தொழில்துறை ஒப்பந்தங்கள்", "நலவாரிய அட்டை தீர்வு"),
        criticalWeaknesses = listOf("வால்பாறை மலைப்பகுதியில் உறுப்பினர் சேவை")
      ),
      LeaderPerformanceProfile(
        id = "TNPA-OB-DIST-SLM-05",
        fullName = "V. Periyasamy",
        tamilName = "வி. பெரியசாமி",
        designation = "சேலம் மாவட்டப் பொருளாளர்",
        level = AdminHierarchyLevel.DISTRICT,
        district = "சேலம் (Salem)",
        unionOrCity = "சேலம் மாநகர் & ஆத்தூர்",
        mobile = "9843076543",
        memberEnrollmentTarget = 600,
        memberEnrollmentActual = 420,
        welfareBoardTarget = 300,
        welfareBoardActual = 190,
        monthlyMeetingsTarget = 4,
        monthlyMeetingsHeld = 2,
        meetingAttendancePct = 72,
        grievancesReceived = 14,
        grievancesResolved = 9,
        subscriptionCollectionPct = 68,
        conferenceAttendanceRate = 80,
        jobPlacementsAssisted = 6,
        awarenessCampsConducted = 1,
        overallAiScore = 69,
        tier = PerformanceTier.NEEDS_ATTENTION,
        statusBadgeTamil = "⚠️ தீவிர கவனம் தேவை",
        rankInLevel = 5,
        keyStrengths = listOf("அனுபவமிக்க மூத்த நிர்வாகி"),
        criticalWeaknesses = listOf("இலக்கு பின்தங்கியுள்ளது", "கூட்டங்கள் வருகை குறைவு", "நலவாரிய முகாம் தேவை")
      ),

      // 3. Union & Town Leaders
      LeaderPerformanceProfile(
        id = "TNPA-OB-UN-MNP-01",
        fullName = "P. Arumugam",
        tamilName = "பி. ஆறுமுகம்",
        designation = "மணப்பாறை ஒன்றியத் தலைவர்",
        level = AdminHierarchyLevel.UNION,
        district = "திருச்சிராப்பள்ளி (Tiruchirappalli)",
        unionOrCity = "மணப்பாறை ஒன்றியம்",
        mobile = "9786543210",
        memberEnrollmentTarget = 300,
        memberEnrollmentActual = 290,
        welfareBoardTarget = 150,
        welfareBoardActual = 148,
        monthlyMeetingsTarget = 4,
        monthlyMeetingsHeld = 4,
        meetingAttendancePct = 98,
        grievancesReceived = 12,
        grievancesResolved = 12,
        subscriptionCollectionPct = 96,
        conferenceAttendanceRate = 100,
        jobPlacementsAssisted = 11,
        awarenessCampsConducted = 2,
        overallAiScore = 97,
        tier = PerformanceTier.EXEMPLARY,
        statusBadgeTamil = "🏆 சிறந்த ஒன்றிய சாதனையாளர்",
        rankInLevel = 1,
        keyStrengths = listOf("100% குறைகள் தீர்ப்பு", "கிராம வாரியாக தீவிர உறுப்பினர் முகாம்"),
        criticalWeaknesses = listOf("இளைஞரணி துணை அமைப்பை துவக்குதல்")
      ),
      LeaderPerformanceProfile(
        id = "TNPA-OB-UN-MDU-EAST",
        fullName = "T. Veeramani",
        tamilName = "டி. வீரமணி",
        designation = "மதுரை கிழக்கு ஒன்றியச் செயலாளர்",
        level = AdminHierarchyLevel.UNION,
        district = "மதுரை (Madurai)",
        unionOrCity = "மதுரை கிழக்கு ஒன்றியம்",
        mobile = "9944112233",
        memberEnrollmentTarget = 350,
        memberEnrollmentActual = 320,
        welfareBoardTarget = 180,
        welfareBoardActual = 165,
        monthlyMeetingsTarget = 4,
        monthlyMeetingsHeld = 4,
        meetingAttendancePct = 92,
        grievancesReceived = 16,
        grievancesResolved = 15,
        subscriptionCollectionPct = 90,
        conferenceAttendanceRate = 100,
        jobPlacementsAssisted = 9,
        awarenessCampsConducted = 2,
        overallAiScore = 92,
        tier = PerformanceTier.EXCELLENT,
        statusBadgeTamil = "🌾 முன்னணி ஒன்றிய தலைவர்",
        rankInLevel = 2,
        keyStrengths = listOf("தொடர் தொழிலாளர் நலம்", "நலவாரிய அட்டை விநியோகம்"),
        criticalWeaknesses = listOf("விபத்து நிவாரண உதவித்தொகை விழிப்புணர்வு")
      ),
      LeaderPerformanceProfile(
        id = "TNPA-OB-CITY-TAMB-01",
        fullName = "S. Radhakrishnan",
        tamilName = "எஸ். ராதாகிருஷ்ணன்",
        designation = "தாம்பரம் நகரத் தலைவர்",
        level = AdminHierarchyLevel.CITY,
        district = "செங்கல்பட்டு (Chengalpattu)",
        unionOrCity = "தாம்பரம் நகரம்",
        mobile = "9884567890",
        memberEnrollmentTarget = 400,
        memberEnrollmentActual = 350,
        welfareBoardTarget = 200,
        welfareBoardActual = 175,
        monthlyMeetingsTarget = 4,
        monthlyMeetingsHeld = 3,
        meetingAttendancePct = 86,
        grievancesReceived = 15,
        grievancesResolved = 13,
        subscriptionCollectionPct = 85,
        conferenceAttendanceRate = 90,
        jobPlacementsAssisted = 10,
        awarenessCampsConducted = 2,
        overallAiScore = 86,
        tier = PerformanceTier.EXCELLENT,
        statusBadgeTamil = "🏙️ நகர சாதனையாளர்",
        rankInLevel = 1,
        keyStrengths = listOf("அபார்ட்மெண்ட் பெயிண்டர்கள் ஒருங்கிணைப்பு"),
        criticalWeaknesses = listOf("மாதாந்திர கூட்டம் 4-ஆக உயர்த்துதல்")
      ),

      // 4. Youth Wing
      LeaderPerformanceProfile(
        id = "TNPA-OB-YOUTH-TRY-01",
        fullName = "K. Vignesh",
        tamilName = "கே. விக்னேஷ்",
        designation = "திருச்சி மாவட்ட இளைஞரணிச் செயலாளர்",
        level = AdminHierarchyLevel.DISTRICT_YOUTH,
        district = "திருச்சிராப்பள்ளி (Tiruchirappalli)",
        unionOrCity = "மாவட்ட இளைஞரணி பிரிவு",
        mobile = "9842887766",
        memberEnrollmentTarget = 500,
        memberEnrollmentActual = 480,
        welfareBoardTarget = 250,
        welfareBoardActual = 230,
        monthlyMeetingsTarget = 4,
        monthlyMeetingsHeld = 4,
        meetingAttendancePct = 95,
        grievancesReceived = 14,
        grievancesResolved = 13,
        subscriptionCollectionPct = 91,
        conferenceAttendanceRate = 100,
        jobPlacementsAssisted = 19,
        awarenessCampsConducted = 3,
        overallAiScore = 95,
        tier = PerformanceTier.EXEMPLARY,
        statusBadgeTamil = "⚡ இளம் புயல் சாதனையாளர்",
        rankInLevel = 1,
        keyStrengths = listOf("இளைஞர்கள் மத்தியில் பெரும் விழிப்புணர்வு", "டிஜிட்டல் மீடியா & செயலி பயன்பாடு"),
        criticalWeaknesses = listOf("கிராமப்புற இளைஞரணி அமைப்புகளை வலுப்படுத்துதல்")
      )
    )

    // Pre-populate initial heuristic advisory for the first profile
    val updatedFirst = initial[0].copy(
      cachedAdvisory = ExecutiveAiService.generateHeuristicAdvisory(initial[0])
    )
    val updatedSecond = initial[1].copy(
      cachedAdvisory = ExecutiveAiService.generateHeuristicAdvisory(initial[1])
    )
    val updatedThird = initial[3].copy(
      cachedAdvisory = ExecutiveAiService.generateHeuristicAdvisory(initial[3])
    )

    _leaderProfiles.value = listOf(updatedFirst, updatedSecond, initial[2], updatedThird) + initial.drop(4)
    _selectedLeader.value = updatedFirst
  }

  // ==========================================================================
  // SYNC WITH NEWLY ADDED BEARERS FROM MAIN REPOSITORY
  // ==========================================================================
  fun syncWithBearer(bearer: HierarchyOfficeBearer) {
    val current = _leaderProfiles.value.toMutableList()
    val existingIndex = current.indexOfFirst { it.id == bearer.id }
    if (existingIndex == -1) {
      val newProfile = LeaderPerformanceProfile(
        id = bearer.id,
        fullName = bearer.fullName,
        tamilName = bearer.tamilName,
        designation = bearer.designation,
        level = bearer.level,
        district = bearer.district,
        unionOrCity = bearer.unionName.ifBlank { bearer.cityName },
        mobile = bearer.mobile,
        memberEnrollmentTarget = if (bearer.level == AdminHierarchyLevel.STATE) 1500 else if (bearer.level == AdminHierarchyLevel.DISTRICT) 600 else 250,
        memberEnrollmentActual = 40,
        welfareBoardTarget = 100,
        welfareBoardActual = 25,
        monthlyMeetingsTarget = 4,
        monthlyMeetingsHeld = 1,
        overallAiScore = 75,
        tier = PerformanceTier.GOOD,
        statusBadgeTamil = "🌱 புதிய நிர்வாகி",
        keyStrengths = listOf("புதிய உற்சாகமான தலைமை"),
        criticalWeaknesses = listOf("உறுப்பினர் சேர்க்கையை உடனடியாக தொடங்கவும்")
      )
      current.add(newProfile)
      _leaderProfiles.value = current
      recalculateDashboard()
    }
  }

  // ==========================================================================
  // ACTIONS
  // ==========================================================================

  fun selectLeader(profile: LeaderPerformanceProfile) {
    _selectedLeader.value = profile
  }

  suspend fun requestAiAdvisoryForSelectedLeader() {
    val current = _selectedLeader.value ?: return
    _isAiAnalyzing.value = true
    try {
      val advisory = ExecutiveAiService.generateAdvisoryForLeader(current)
      val updated = current.copy(
        cachedAdvisory = advisory,
        lastAuditedTimestamp = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date())
      )
      _selectedLeader.value = updated

      // update in list
      _leaderProfiles.value = _leaderProfiles.value.map { if (it.id == updated.id) updated else it }
      recalculateDashboard()
    } finally {
      _isAiAnalyzing.value = false
    }
  }

  suspend fun sendChatMessage(userText: String) {
    if (userText.isBlank()) return
    val now = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
    val userMsg = AiChatMessage(
      sender = ChatSender.USER,
      textTamil = userText.trim(),
      timestamp = now
    )
    _chatMessages.value = _chatMessages.value + userMsg

    // Generate AI response
    val aiResponseText = ExecutiveAiService.askExecutiveAiAdvisor(userText, _selectedLeader.value)
    val aiMsg = AiChatMessage(
      sender = ChatSender.AI_ASSISTANT,
      textTamil = aiResponseText,
      timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date()),
      suggestedPromptChips = listOf(
        "உறுப்பினர் சேர்க்கை இலக்கு",
        "நலவாரிய அட்டை விண்ணப்ப முறை",
        "மாதாந்திர கூட்டம் ஏற்பாடு",
        "மாவட்ட அளவிலான அறிக்கை"
      )
    )
    _chatMessages.value = _chatMessages.value + aiMsg
  }

  fun updateLeaderMetrics(
    leaderId: String,
    newEnrollmentActual: Int,
    newWelfareActual: Int,
    newMeetingsHeld: Int
  ) {
    val current = _leaderProfiles.value.toMutableList()
    val index = current.indexOfFirst { it.id == leaderId }
    if (index != -1) {
      val item = current[index]
      val enrollmentPct = if (item.memberEnrollmentTarget > 0) (newEnrollmentActual.toDouble() / item.memberEnrollmentTarget) * 100 else 0.0
      val welfarePct = if (item.welfareBoardTarget > 0) (newWelfareActual.toDouble() / item.welfareBoardTarget) * 100 else 0.0
      val meetingsPct = if (item.monthlyMeetingsTarget > 0) (newMeetingsHeld.toDouble() / item.monthlyMeetingsTarget) * 100 else 0.0

      val computedScore = ((enrollmentPct * 0.4) + (welfarePct * 0.35) + (meetingsPct * 0.25)).toInt().coerceIn(20, 100)
      val tier = when {
        computedScore >= 90 -> PerformanceTier.EXEMPLARY
        computedScore >= 80 -> PerformanceTier.EXCELLENT
        computedScore >= 70 -> PerformanceTier.GOOD
        computedScore >= 55 -> PerformanceTier.NEEDS_ATTENTION
        else -> PerformanceTier.CRITICAL
      }

      val updated = item.copy(
        memberEnrollmentActual = newEnrollmentActual,
        welfareBoardActual = newWelfareActual,
        monthlyMeetingsHeld = newMeetingsHeld,
        overallAiScore = computedScore,
        tier = tier,
        lastAuditedTimestamp = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date())
      )
      current[index] = updated
      _leaderProfiles.value = current
      if (_selectedLeader.value?.id == leaderId) {
        _selectedLeader.value = updated
      }
      recalculateDashboard()
    }
  }

  private fun recalculateDashboard() {
    val list = _leaderProfiles.value
    if (list.isEmpty()) return

    val avgScore = (list.map { it.overallAiScore }.average()).toInt()
    val exemplaryCount = list.count { it.tier == PerformanceTier.EXEMPLARY || it.tier == PerformanceTier.EXCELLENT }
    val needsAttentionCount = list.count { it.tier == PerformanceTier.NEEDS_ATTENTION || it.tier == PerformanceTier.CRITICAL }
    val totalEnrolled = list.sumOf { it.memberEnrollmentActual }
    val totalGoal = list.sumOf { it.memberEnrollmentTarget }
    val totalWelfare = list.sumOf { it.welfareBoardActual }

    val topDistrict = list.filter { it.level == AdminHierarchyLevel.DISTRICT }
      .maxByOrNull { it.overallAiScore }?.district ?: "மதுரை மாவட்டம்"

    val topUnion = list.filter { it.level == AdminHierarchyLevel.UNION }
      .maxByOrNull { it.overallAiScore }?.let { "${it.unionOrCity} (${it.district.split(" ").first()})" } ?: "மணப்பாறை ஒன்றியம் (திருச்சி)"

    _dashboardSummary.value = AiMonitoringDashboardSummary(
      totalMonitoredLeaders = list.size,
      stateAverageScore = avgScore,
      topPerformingDistrict = topDistrict,
      topPerformingUnion = topUnion,
      leadersExemplaryCount = exemplaryCount,
      leadersNeedsAttentionCount = needsAttentionCount,
      totalMembersEnrolledStateWide = totalEnrolled,
      totalStateEnrollmentGoal = totalGoal,
      totalWelfareCardsStateWide = totalWelfare,
      lastUpdated = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date())
    )
  }
}
