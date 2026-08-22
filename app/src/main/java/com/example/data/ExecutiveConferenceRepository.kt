package com.example.data

import com.example.model.AdminHierarchyLevel
import com.example.model.AdminRole
import com.example.model.ConferenceChatMessage
import com.example.model.ConferenceParticipant
import com.example.model.ConferenceRoom
import com.example.model.ExecutiveVerificationResult
import com.example.model.HierarchyOfficeBearer
import com.example.model.MeetingResolution
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

object ExecutiveConferenceRepository {

  // Active & Scheduled Video Conference Rooms
  private val _rooms = MutableStateFlow<List<ConferenceRoom>>(
    listOf(
      ConferenceRoom(
        id = "CONF-TNPA-001",
        titleTamil = "மாநில, மண்டல, மாவட்ட, நகர, ஒன்றிய நிர்வாகிகள் அவசர மாநில மாநாடு",
        titleEnglish = "TNPA Statewide All-Executives Emergency Video Conference",
        meetingCode = "TNPA-7010-8148",
        passcode = "TNPA2024",
        hostName = "சேவியர் பாபு & எஸ். மைக்கேல் ஆல்வின்",
        hostRole = "Super Admin (மாநில தலைமை)",
        hostMobile = "7010131915",
        isLive = true,
        scheduledTime = "இன்று • 10:30 AM (🔴 நேரலையில் செயலில் உள்ளது)",
        agendaPoints = listOf(
          "1. 38 மாவட்டங்களிலும் நலவாரிய அட்டை பதிவு மற்றும் உறுப்பினர் விரிவாக்கம்.",
          "2. மாநில, மண்டல, மாவட்ட, நகர, ஒன்றிய நிர்வாகிகள் களப்பணி ஒருங்கிணைப்பு.",
          "3. ஓவியர்களின் உரிமைகள், மாதாந்திர ஓய்வூதியம் & விபத்து காப்பீட்டு மானியம்.",
          "4. மாவட்ட அளவிலான மாநாடு மற்றும் பயிற்சிப் பாசறை அமைத்தல்."
        )
      ),
      ConferenceRoom(
        id = "CONF-TNPA-002",
        titleTamil = "மாநில செயற்குழு & மண்டல ஒருங்கிணைப்பாளர்கள் ஆலோசனைக் கூட்டம்",
        titleEnglish = "State Executive Committee & Zonal Leaders Council",
        meetingCode = "TNPA-ZONE-2024",
        passcode = "ZONE2024",
        hostName = "எஸ். மைக்கேல் ஆல்வின்",
        hostRole = "மாநிலத் தலைவர் (Super Admin)",
        hostMobile = "9789331681",
        isLive = true,
        scheduledTime = "இன்று • 02:00 PM (🔴 செயலில்)",
        agendaPoints = listOf(
          "1. மண்டல வாரியாக 38 மாவட்டங்களின் செயல்பாட்டு அறிக்கை.",
          "2. புதிய நிர்வாகிகள் தேர்வு மற்றும் பொறுப்பு உறுதிப்படுத்துதல்.",
          "3. தலைமையக நிதி மேலாண்மை மற்றும் வரவு செலவு சமர்ப்பிப்பு."
        )
      ),
      ConferenceRoom(
        id = "CONF-TNPA-003",
        titleTamil = "38 மாவட்ட தலைவர்கள் & செயலாளர்கள் சிறப்பு கலந்தாய்வு",
        titleEnglish = "38 District Presidents & Secretaries High-Level Meet",
        meetingCode = "TNPA-DIST-38",
        passcode = "DIST38",
        hostName = "சேவியர் பாபு",
        hostRole = "மாநில பொதுச் செயலாளர் (Super Admin)",
        hostMobile = "7010131915",
        isLive = false,
        scheduledTime = "நாளை • 11:00 AM (திட்டமிடப்பட்டது)",
        agendaPoints = listOf(
          "1. மாவட்ட வாரியான புதிய உறுப்பினர் சேர்க்கை அட்டவணை.",
          "2. நலத்திட்ட உதவிகள் விநியோக கண்காணிப்பு.",
          "3. மாவட்ட நிர்வாகிகள் குறைதீர்ப்பு."
        )
      ),
      ConferenceRoom(
        id = "CONF-TNPA-004",
        titleTamil = "தமிழ்நாடு ஓவியர்கள் இளைஞரணி (Youth Wing) மாநில மாநாடு",
        titleEnglish = "Statewide Youth Wing Leaders Video Summit",
        meetingCode = "TNPA-YOUTH-WING",
        passcode = "YOUTH24",
        hostName = "சக்திவேல் & இளைஞரணி ஒருங்கிணைப்பாளர்கள்",
        hostRole = "மாநில பொருளாளர் / இளைஞரணி பொறுப்பாளர்",
        hostMobile = "9080047281",
        isLive = false,
        scheduledTime = "வரும் ஞாயிறு • 04:00 PM",
        agendaPoints = listOf(
          "1. இளைஞர்களுக்கு நவீன 3D மியூரல் & ஸ்ப்ரே பெயிண்டிங் பயிலரங்கம்.",
          "2. டிஜிட்டல் உறுப்பினர் அடையாள அட்டை மற்றும் செயலியை மக்களிடம் கொண்டு சேர்த்தல்.",
          "3. மாவட்ட, ஒன்றிய, நகர இளைஞரணி கிளைகள் தொடங்குதல்."
        )
      )
    )
  )
  val rooms: StateFlow<List<ConferenceRoom>> = _rooms.asStateFlow()

  // Active Participants in Meeting (State, Zone, District, City, Union Leaders)
  private val _participants = MutableStateFlow<List<ConferenceParticipant>>(
    listOf(
      ConferenceParticipant(
        id = "PART-001",
        name = "Xavier Babu",
        tamilName = "சேவியர் பாபு",
        designation = "மாநில பொதுச் செயலாளர் (Super Admin)",
        level = AdminHierarchyLevel.STATE,
        district = "மதுரை தலைமையகம்",
        unionOrCity = "மாநில தலைமை",
        mobile = "7010131915",
        isHost = true,
        isMicOn = true,
        isVideoOn = true,
        isHandRaised = false,
        isSpeaking = true,
        joinedAt = "10:30 AM",
        avatarColorHex = 0xFFDC2626
      ),
      ConferenceParticipant(
        id = "PART-002",
        name = "S. Michael Alvin",
        tamilName = "எஸ். மைக்கேல் ஆல்வின்",
        designation = "மாநிலத் தலைவர் (Super Admin)",
        level = AdminHierarchyLevel.STATE,
        district = "மதுரை தலைமையகம்",
        unionOrCity = "மாநில தலைமை",
        mobile = "9789331681",
        isHost = true,
        isMicOn = true,
        isVideoOn = true,
        isHandRaised = false,
        isSpeaking = false,
        joinedAt = "10:31 AM",
        avatarColorHex = 0xFF991B1B
      ),
      ConferenceParticipant(
        id = "PART-003",
        name = "Sakthivel",
        tamilName = "சக்திவேல்",
        designation = "மாநில பொருளாளர்",
        level = AdminHierarchyLevel.STATE,
        district = "திருச்சிராப்பள்ளி",
        unionOrCity = "மாநில தலைமை",
        mobile = "9080047281",
        isHost = false,
        isMicOn = false,
        isVideoOn = true,
        isHandRaised = false,
        isSpeaking = false,
        joinedAt = "10:32 AM",
        avatarColorHex = 0xFFD97706
      ),
      ConferenceParticipant(
        id = "PART-004",
        name = "K. Ramanathan",
        tamilName = "கே. ராமநாதன்",
        designation = "மண்டல தலைவர் (தென் மண்டலம்)",
        level = AdminHierarchyLevel.ZONE,
        district = "திருநெல்வேலி",
        unionOrCity = "தென் மண்டலம்",
        mobile = "9842156780",
        isHost = false,
        isMicOn = false,
        isVideoOn = true,
        isHandRaised = false,
        isSpeaking = false,
        joinedAt = "10:33 AM",
        avatarColorHex = 0xFF2563EB
      ),
      ConferenceParticipant(
        id = "PART-005",
        name = "V. Selvaraj",
        tamilName = "வி. செல்வராஜ்",
        designation = "மாவட்டத் தலைவர்",
        level = AdminHierarchyLevel.DISTRICT,
        district = "கோயம்புத்தூர்",
        unionOrCity = "கோவை மாவட்டம்",
        mobile = "9894123456",
        isHost = false,
        isMicOn = false,
        isVideoOn = true,
        isHandRaised = true,
        isSpeaking = false,
        joinedAt = "10:34 AM",
        avatarColorHex = 0xFF059669
      ),
      ConferenceParticipant(
        id = "PART-006",
        name = "M. Ganesan",
        tamilName = "எம். கணேசன்",
        designation = "மாவட்டச் செயலாளர்",
        level = AdminHierarchyLevel.DISTRICT,
        district = "சேலம்",
        unionOrCity = "சேலம் மாவட்டம்",
        mobile = "9786154321",
        isHost = false,
        isMicOn = false,
        isVideoOn = true,
        isHandRaised = false,
        isSpeaking = false,
        joinedAt = "10:35 AM",
        avatarColorHex = 0xFF7C3AED
      ),
      ConferenceParticipant(
        id = "PART-007",
        name = "P. Arumugam",
        tamilName = "பி. ஆறுமுகம்",
        designation = "நகரத் தலைவர்",
        level = AdminHierarchyLevel.CITY,
        district = "மதுரை",
        unionOrCity = "மேலூர் நகரம்",
        mobile = "9443187654",
        isHost = false,
        isMicOn = false,
        isVideoOn = true,
        isHandRaised = false,
        isSpeaking = false,
        joinedAt = "10:36 AM",
        avatarColorHex = 0xFFDB2777
      ),
      ConferenceParticipant(
        id = "PART-008",
        name = "S. Murugavel",
        tamilName = "எஸ். முருகவேல்",
        designation = "ஒன்றியச் செயலாளர்",
        level = AdminHierarchyLevel.UNION,
        district = "திண்டுக்கல்",
        unionOrCity = "நத்தம் ஒன்றியம்",
        mobile = "9655123890",
        isHost = false,
        isMicOn = false,
        isVideoOn = true,
        isHandRaised = false,
        isSpeaking = false,
        joinedAt = "10:37 AM",
        avatarColorHex = 0xFF4F46E5
      ),
      ConferenceParticipant(
        id = "PART-009",
        name = "T. Vijayakumar",
        tamilName = "டி. விஜயகுமார்",
        designation = "மாவட்ட இளைஞரணி செயலாளர்",
        level = AdminHierarchyLevel.DISTRICT_YOUTH,
        district = "சென்னை",
        unionOrCity = "வட சென்னை",
        mobile = "9940123789",
        isHost = false,
        isMicOn = false,
        isVideoOn = true,
        isHandRaised = false,
        isSpeaking = false,
        joinedAt = "10:38 AM",
        avatarColorHex = 0xFFEA580C
      )
    )
  )
  val participants: StateFlow<List<ConferenceParticipant>> = _participants.asStateFlow()

  // In-Conference Live Chat
  private val _chatMessages = MutableStateFlow<List<ConferenceChatMessage>>(
    listOf(
      ConferenceChatMessage(
        senderName = "சேவியர் பாபு",
        senderDesignation = "மாநில பொதுச் செயலாளர் (Super Admin)",
        level = AdminHierarchyLevel.STATE,
        district = "மதுரை",
        message = "வணக்கம் தோழர்களே! 38 மாவட்ட, மண்டல, நகர, ஒன்றிய நிர்வாகிகள் அனைவருக்கும் மாநில தலைமையகத்தின் அன்பான வரவேற்பு.",
        time = "10:30 AM"
      ),
      ConferenceChatMessage(
        senderName = "எஸ். மைக்கேல் ஆல்வின்",
        senderDesignation = "மாநிலத் தலைவர் (Super Admin)",
        level = AdminHierarchyLevel.STATE,
        district = "மதுரை",
        message = "இன்றைய கூட்டத்தில் நலவாரிய பதிவு மற்றும் புதிய உறுப்பினர்கள் சேர்ப்பது குறித்து விவாதிக்கப்படும். அனைத்து மாவட்ட நிர்வாகிகளும் தயாராக இருக்கவும்.",
        time = "10:32 AM"
      ),
      ConferenceChatMessage(
        senderName = "வி. செல்வராஜ்",
        senderDesignation = "மாவட்டத் தலைவர் (கோவை)",
        level = AdminHierarchyLevel.DISTRICT,
        district = "கோயம்புத்தூர்",
        message = "கோவை மாவட்டத்தில் 150-க்கும் மேற்பட்ட ஓவியர்களுக்கு நலவாரிய அட்டை பதிவு விண்ணப்பம் செய்யப்பட்டுள்ளது தோழர்.",
        time = "10:34 AM"
      ),
      ConferenceChatMessage(
        senderName = "எம். கணேசன்",
        senderDesignation = "மாவட்டச் செயலாளர் (சேலம்)",
        level = AdminHierarchyLevel.DISTRICT,
        district = "சேலம்",
        message = "சேலம் மண்டலத்தில் 5 புதிய ஒன்றியங்கள் தொடங்கப்பட்டு நிர்வாகிகள் பொறுப்பேற்றுள்ளனர்.",
        time = "10:36 AM"
      )
    )
  )
  val chatMessages: StateFlow<List<ConferenceChatMessage>> = _chatMessages.asStateFlow()

  // Meeting Resolutions
  private val _resolutions = MutableStateFlow<List<MeetingResolution>>(
    listOf(
      MeetingResolution(
        resolutionNo = 1,
        titleTamil = "தமிழ்நாடு முழுவதும் உள்ள 38 மாவட்டங்களிலும் 10,000 புதிய ஓவியர்களை சங்கத்தில் உறுப்பினர்களாக இணைக்க இலக்கு நிர்ணயம்.",
        proposedBy = "சேவியர் பாபு (மாநில பொதுச் செயலாளர்)",
        secondedBy = "எஸ். மைக்கேல் ஆல்வின் (மாநிலத் தலைவர்)",
        status = "ஏகமனதாக நிறைவேற்றப்பட்டது (Passed)",
        timestamp = "10:40 AM"
      ),
      MeetingResolution(
        resolutionNo = 2,
        titleTamil = "ஓவியர் தொழிலாளர்களுக்கு தமிழ்நாடு கட்டுமான நலவாரிய மாதாந்திர ஓய்வூதியத்தை ரூ. 3,000 ஆக உயர்த்த அரசிடம் வலியுறுத்துதல்.",
        proposedBy = "சக்திவேல் (மாநில பொருளாளர்)",
        secondedBy = "அனைத்து மாவட்ட தலைவர்கள் & செயலாளர்கள்",
        status = "ஏகமனதாக நிறைவேற்றப்பட்டது (Passed)",
        timestamp = "10:45 AM"
      )
    )
  )
  val resolutions: StateFlow<List<MeetingResolution>> = _resolutions.asStateFlow()

  // ============================================================================
  // EXECUTIVE VERIFICATION ENGINE: ONLY REGISTERED EXECUTIVES CAN ENTER
  // ============================================================================
  fun verifyExecutiveAccess(
    mobileOrId: String,
    pinOrPasscode: String = ""
  ): ExecutiveVerificationResult {
    val cleanInput = mobileOrId.trim().replace("+91", "").replace(" ", "").replace("-", "")
    
    if (cleanInput.isBlank()) {
      return ExecutiveVerificationResult.Denied(
        reasonTamil = "தயவுசெய்து பதிவு செய்யப்பட்ட மொபைல் எண் அல்லது நிர்வாகி அடையாள எண்ணை உள்ளிடவும்.",
        suggestedAction = "சங்கத்தில் பதிவு செய்யப்பட்ட 10 இலக்க மொபைல் எண்ணை உள்ளிடவும்."
      )
    }

    // 1. Check Super Admins (Direct Super Admin Verification)
    if (cleanInput == "7010131915" || cleanInput.equals("admin", ignoreCase = true) || cleanInput.equals("xavier", ignoreCase = true)) {
      val participant = ConferenceParticipant(
        bearerId = "TNPA-OB-002",
        name = "Xavier Babu",
        tamilName = "சேவியர் பாபு",
        designation = "மாநில பொதுச் செயலாளர் (Super Admin)",
        level = AdminHierarchyLevel.STATE,
        district = "மதுரை தலைமையகம்",
        unionOrCity = "மாநில தலைமை",
        mobile = "7010131915",
        isHost = true,
        isMicOn = true,
        isVideoOn = true,
        avatarColorHex = 0xFFDC2626
      )
      return ExecutiveVerificationResult.Success(participant)
    }

    if (cleanInput == "9789331681" || cleanInput == "8148384074" || cleanInput.equals("alvin", ignoreCase = true)) {
      val participant = ConferenceParticipant(
        bearerId = "TNPA-OB-001",
        name = "S. Michael Alvin",
        tamilName = "எஸ். மைக்கேல் ஆல்வின்",
        designation = "மாநிலத் தலைவர் (Super Admin)",
        level = AdminHierarchyLevel.STATE,
        district = "மதுரை தலைமையகம்",
        unionOrCity = "மாநில தலைமை",
        mobile = "9789331681",
        isHost = true,
        isMicOn = true,
        isVideoOn = true,
        avatarColorHex = 0xFF991B1B
      )
      return ExecutiveVerificationResult.Success(participant)
    }

    if (cleanInput == "9080047281") {
      val participant = ConferenceParticipant(
        bearerId = "TNPA-OB-003",
        name = "Sakthivel",
        tamilName = "சக்திவேல்",
        designation = "மாநில பொருளாளர் (State Treasurer)",
        level = AdminHierarchyLevel.STATE,
        district = "திருச்சிராப்பள்ளி",
        unionOrCity = "மாநில தலைமை",
        mobile = "9080047281",
        isHost = false,
        isMicOn = true,
        isVideoOn = true,
        avatarColorHex = 0xFFD97706
      )
      return ExecutiveVerificationResult.Success(participant)
    }

    // 2. Check in OfficeBearerRepository for State, Zone, District, Union, City, Youth Wing Bearers
    val registeredBearers = OfficeBearerRepository.bearers.value
    val matchedBearer = registeredBearers.find { bearer ->
      bearer.isActive && (
        bearer.mobile.replace(" ", "").contains(cleanInput) ||
        bearer.altPhone.replace(" ", "").contains(cleanInput) ||
        bearer.id.equals(cleanInput, ignoreCase = true)
      )
    }

    if (matchedBearer != null) {
      val participant = ConferenceParticipant(
        bearerId = matchedBearer.id,
        name = matchedBearer.fullName.ifBlank { matchedBearer.tamilName },
        tamilName = matchedBearer.tamilName.ifBlank { matchedBearer.fullName },
        designation = matchedBearer.designation,
        level = matchedBearer.level,
        district = matchedBearer.district,
        unionOrCity = when (matchedBearer.level) {
          AdminHierarchyLevel.UNION, AdminHierarchyLevel.UNION_YOUTH -> matchedBearer.unionName
          AdminHierarchyLevel.CITY, AdminHierarchyLevel.CITY_YOUTH -> matchedBearer.cityName
          AdminHierarchyLevel.ZONE -> matchedBearer.zone
          else -> matchedBearer.district
        },
        mobile = matchedBearer.mobile,
        isHost = matchedBearer.level == AdminHierarchyLevel.STATE,
        isMicOn = false,
        isVideoOn = true,
        avatarColorHex = when (matchedBearer.level) {
          AdminHierarchyLevel.STATE -> 0xFFDC2626
          AdminHierarchyLevel.ZONE -> 0xFF2563EB
          AdminHierarchyLevel.DISTRICT, AdminHierarchyLevel.DISTRICT_YOUTH -> 0xFF059669
          AdminHierarchyLevel.UNION, AdminHierarchyLevel.UNION_YOUTH -> 0xFF7C3AED
          AdminHierarchyLevel.CITY, AdminHierarchyLevel.CITY_YOUTH -> 0xFFEA580C
        }
      )
      return ExecutiveVerificationResult.Success(participant)
    }

    // 3. Check Admin Accounts from AdminApprovalRepository
    val adminAccounts = AdminApprovalRepository.getAllAdmins()
    val matchedAdmin = adminAccounts.find { admin ->
      admin.status == com.example.model.AdminStatus.ACTIVE && (
        admin.mobileNumber.replace(" ", "").contains(cleanInput) ||
        admin.username.equals(cleanInput, ignoreCase = true) ||
        admin.id.equals(cleanInput, ignoreCase = true)
      )
    }

    if (matchedAdmin != null) {
      val level = when (matchedAdmin.role) {
        AdminRole.SUPER_ADMIN, AdminRole.STATE_ADMIN -> AdminHierarchyLevel.STATE
        AdminRole.DISTRICT_ADMIN -> AdminHierarchyLevel.DISTRICT
      }
      val participant = ConferenceParticipant(
        bearerId = matchedAdmin.id,
        name = matchedAdmin.fullName,
        tamilName = matchedAdmin.fullName,
        designation = when (matchedAdmin.role) {
          AdminRole.SUPER_ADMIN -> "மாநில தலைமை (Super Admin)"
          AdminRole.STATE_ADMIN -> "மாநில நிர்வாகி (State Admin)"
          AdminRole.DISTRICT_ADMIN -> "மாவட்ட நிர்வாகி (${matchedAdmin.assignedDistrict})"
        },
        level = level,
        district = matchedAdmin.assignedDistrict ?: "தமிழ்நாடு",
        unionOrCity = matchedAdmin.assignedDistrict ?: "",
        mobile = matchedAdmin.mobileNumber,
        isHost = matchedAdmin.role == AdminRole.SUPER_ADMIN,
        isMicOn = false,
        isVideoOn = true,
        avatarColorHex = 0xFF059669
      )
      return ExecutiveVerificationResult.Success(participant)
    }

    // 4. If not found in any executive list -> Strictly Deny Entry
    return ExecutiveVerificationResult.Denied(
      reasonTamil = "⚠️ அனுமதி மறுக்கப்பட்டது: '$cleanInput' என்ற எண் மாநில, மண்டல, மாவட்ட, நகர அல்லது ஒன்றிய நிர்வாகி பட்டியலில் பதிவு செய்யப்படவில்லை.\n\nஇந்த வீடியோ கான்பரன்ஸில் பதிவு செய்யப்பட்ட நிர்வாகிகள் மட்டுமே கலந்து கொள்ள முடியும்.",
      suggestedAction = "நீங்கள் சங்க நிர்வாகியாக நியமிக்கப்பட்டிருந்தால், மாநில தலைமைச் செயலகம் (7010131915 / 9789331681) அல்லது உங்கள் மாவட்ட தலைவரைத் தொடர்புகொண்டு 'நிர்வாகிகள் பட்டியலில்' உங்கள் பெயரைப் பதிவு செய்துகொள்ளவும்."
    )
  }

  // ============================================================================
  // PARTICIPANT & MEETING ACTIONS
  // ============================================================================

  fun addParticipantToMeeting(participant: ConferenceParticipant) {
    val current = _participants.value.toMutableList()
    val existingIdx = current.indexOfFirst { it.mobile == participant.mobile || it.id == participant.id }
    if (existingIdx != -1) {
      current[existingIdx] = participant
    } else {
      current.add(0, participant)
    }
    _participants.value = current
  }

  fun removeParticipant(participantId: String) {
    _participants.value = _participants.value.filterNot { it.id == participantId }
  }

  fun toggleMic(participantId: String) {
    _participants.value = _participants.value.map { p ->
      if (p.id == participantId) p.copy(isMicOn = !p.isMicOn, isSpeaking = !p.isMicOn) else p
    }
  }

  fun toggleVideo(participantId: String) {
    _participants.value = _participants.value.map { p ->
      if (p.id == participantId) p.copy(isVideoOn = !p.isVideoOn) else p
    }
  }

  fun toggleRaiseHand(participantId: String) {
    _participants.value = _participants.value.map { p ->
      if (p.id == participantId) p.copy(isHandRaised = !p.isHandRaised) else p
    }
  }

  fun muteAllParticipants(hostAdminId: String) {
    _participants.value = _participants.value.map { p ->
      if (p.id == hostAdminId || p.isHost) p else p.copy(isMicOn = false, isSpeaking = false)
    }
  }

  fun sendChatMessage(message: ConferenceChatMessage) {
    _chatMessages.value = _chatMessages.value + message
  }

  fun addResolution(resolution: MeetingResolution) {
    _resolutions.value = _resolutions.value + resolution
  }

  fun createConferenceRoom(room: ConferenceRoom) {
    _rooms.value = listOf(room) + _rooms.value
  }
}
