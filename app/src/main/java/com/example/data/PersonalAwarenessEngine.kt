package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

/**
 * PERSONAL SOVEREIGN AI - COMMUNICATION, AWARENESS, NAVIGATION & PROACTIVE ENGINE
 * 
 * Unifies:
 * 1. Personal Communication Hub (WhatsApp, Messenger, Instagram, SMS, Email, Phone Calls)
 * 2. Incoming Message Prioritization & Content Understanding
 * 3. Call Screening & Anti-Spam Intelligence
 * 4. Location Context & Privacy Zones
 * 5. Multi-Route Navigation Assistant & Proactive Traffic Departure
 * 6. Day Situation Summary & Proactive Personal Coordinator
 * 7. Strict Owner Voice Gating & Zero Identity Fabrication
 */
object PersonalAwarenessEngine {

  private val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
  private val fullDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

  // =========================================================================
  // 1. UNIFIED MESSAGES REPOSITORY
  // =========================================================================
  private val _unifiedMessages = MutableStateFlow<List<UnifiedMessage>>(getDefaultMessages())
  val unifiedMessages: StateFlow<List<UnifiedMessage>> = _unifiedMessages.asStateFlow()

  // =========================================================================
  // 2. CALL INTELLIGENCE & SCREENING REPOSITORY
  // =========================================================================
  private val _callRecords = MutableStateFlow<List<IncomingCallRecord>>(getDefaultCallRecords())
  val callRecords: StateFlow<List<IncomingCallRecord>> = _callRecords.asStateFlow()

  // =========================================================================
  // 3. LOCATION CONTEXT & PRIVACY ZONES
  // =========================================================================
  private val _locationContext = MutableStateFlow(getDefaultLocationContext())
  val locationContext: StateFlow<LocationContextState> = _locationContext.asStateFlow()

  // =========================================================================
  // 4. NAVIGATION & MULTI-ROUTE INTELLIGENCE
  // =========================================================================
  private val _activeNavigation = MutableStateFlow(getDefaultNavigationPlan())
  val activeNavigation: StateFlow<ActiveNavigationPlan> = _activeNavigation.asStateFlow()

  // =========================================================================
  // 5. PROACTIVE LEVEL & DAY SITUATION
  // =========================================================================
  private val _proactiveLevel = MutableStateFlow(ProactiveAssistanceLevel.ALWAYS_ON)
  val proactiveLevel: StateFlow<ProactiveAssistanceLevel> = _proactiveLevel.asStateFlow()

  private val _daySituation = MutableStateFlow(getDefaultDaySituation())
  val daySituation: StateFlow<PersonalDaySituation> = _daySituation.asStateFlow()

  // =========================================================================
  // 6. AUDITABLE ACTION LEDGER
  // =========================================================================
  private val _auditLedger = MutableStateFlow<List<AuditableCommunicationAction>>(getDefaultAuditLogs())
  val auditLedger: StateFlow<List<AuditableCommunicationAction>> = _auditLedger.asStateFlow()

  // Active filter states for UI
  private val _selectedPlatformFilter = MutableStateFlow<CommunicationPlatform?>(null)
  val selectedPlatformFilter: StateFlow<CommunicationPlatform?> = _selectedPlatformFilter.asStateFlow()

  private val _selectedPriorityFilter = MutableStateFlow<MessagePriority?>(null)
  val selectedPriorityFilter: StateFlow<MessagePriority?> = _selectedPriorityFilter.asStateFlow()

  fun setPlatformFilter(platform: CommunicationPlatform?) {
    _selectedPlatformFilter.value = platform
  }

  fun setPriorityFilter(priority: MessagePriority?) {
    _selectedPriorityFilter.value = priority
  }

  fun setProactiveLevel(level: ProactiveAssistanceLevel) {
    _proactiveLevel.value = level
    recordAudit(
      command = "முன்முயற்சி விழிப்புணர்வு நிலையை மாற்று: ${level.labelEn}",
      speaker = "உரிமையாளர் (Owner)",
      policy = "User Preference Configuration",
      action = "Set Proactive Mode to ${level.name}",
      status = "வெற்றி (Success)"
    )
  }

  fun setLocationPrivacyZone(zone: LocationPrivacyZone) {
    _locationContext.value = _locationContext.value.copy(privacyZone = zone)
    recordAudit(
      command = "இருப்பிட தனியுரிமை மண்டல மாற்றம்: ${zone.labelEn}",
      speaker = "உரிமையாளர் (Owner)",
      policy = "Owner Privacy Enforcement",
      action = "Updated Location Privacy Zone to ${zone.name}",
      status = "வெற்றி (Applied)"
    )
  }

  fun selectRoute(routeId: String) {
    val current = _activeNavigation.value
    val updatedRoutes = current.routes.map { it.copy(isRecommended = it.routeId == routeId) }
    _activeNavigation.value = current.copy(
      selectedRouteId = routeId,
      routes = updatedRoutes
    )
  }

  // =========================================================================
  // 7. MESSAGE TO TASK / CALENDAR / DRAFT REPLY CONVERSION
  // =========================================================================
  fun convertMessageToTask(messageId: String): Boolean {
    val msgs = _unifiedMessages.value.toMutableList()
    val index = msgs.indexOfFirst { it.id == messageId }
    if (index >= 0) {
      val target = msgs[index]
      val taskDraft = TaskReminderDraft(
        title = "Action on ${target.senderName}'s message",
        titleTa = "${target.senderName} அனுப்பிய செய்திக்கு நடவடிக்கை",
        dueDate = "இன்று",
        dueTime = "மாலை 5:00 மணி",
        priority = target.priority,
        isCreated = true
      )
      msgs[index] = target.copy(
        linkedTaskDraft = taskDraft,
        actionRequired = ActionRequirement.TASK_CREATION_RECOMMENDED
      )
      _unifiedMessages.value = msgs

      recordAudit(
        command = "${target.senderName} செய்தியிலிருந்து Task உருவாக்கு",
        speaker = "உரிமையாளர் (Owner)",
        policy = "Owner Explicit Action Request",
        action = "Created Task Reminder: ${taskDraft.titleTa}",
        status = "வெற்றி (Created)"
      )
      return true
    }
    return false
  }

  fun convertMessageToCalendar(messageId: String): Boolean {
    val msgs = _unifiedMessages.value.toMutableList()
    val index = msgs.indexOfFirst { it.id == messageId }
    if (index >= 0) {
      val target = msgs[index]
      val calDraft = CalendarEventDraft(
        title = "Meeting with ${target.senderName}",
        titleTa = "${target.senderName} உடன் சந்திப்பு",
        date = "நாளை (Tomorrow)",
        time = "காலை 10:00 மணி",
        location = "அடையாறு அலுவலகம்",
        participants = listOf("உரிமையாளர் (You)", target.senderName),
        isApproved = true
      )
      msgs[index] = target.copy(
        linkedCalendarDraft = calDraft,
        actionRequired = ActionRequirement.CALENDAR_DRAFT_READY
      )
      _unifiedMessages.value = msgs

      recordAudit(
        command = "${target.senderName} செய்தியிலிருந்து Calendar Event உருவாக்கு",
        speaker = "உரிமையாளர் (Owner)",
        policy = "Owner Calendar Authorization",
        action = "Created Calendar Draft: ${calDraft.titleTa}",
        status = "வெற்றி (Scheduled)"
      )
      return true
    }
    return false
  }

  fun sendDraftReply(messageId: String, replyText: String): Boolean {
    val msgs = _unifiedMessages.value.toMutableList()
    val index = msgs.indexOfFirst { it.id == messageId }
    if (index >= 0) {
      val target = msgs[index]
      msgs[index] = target.copy(isRead = true)
      _unifiedMessages.value = msgs

      recordAudit(
        command = "${target.platform.labelEn} வழியே ${target.senderName}-க்கு பதில் அனுப்பு: \"$replyText\"",
        speaker = "உரிமையாளர் (Owner) - Biometric Verified",
        policy = "Authorized Official Platform Intent",
        action = "Dispatched via ${target.platform.officialAccessMethod}",
        status = "அனுப்பப்பட்டது (Sent Successfully)"
      )
      return true
    }
    return false
  }

  // =========================================================================
  // 8. NATURAL TAMIL COMMUNICATION & AWARENESS VOICE ENGINE
  // =========================================================================
  fun processVoiceAwarenessQuery(
    queryText: String,
    isOwnerVerified: Boolean = true
  ): AwarenessVoiceResponse {
    val clean = queryText.trim().lowercase()

    // 1. Strict Owner Authorization Gate for Sensitive Communication
    if (!isOwnerVerified) {
      val hash = computeSha256("UNAUTHORIZED_ACCESS_ATTEMPT_${System.currentTimeMillis()}")
      recordAudit(
        command = queryText,
        speaker = "அங்கீகரிக்கப்படாத பேச்சாளர் (Guest / Unknown Speaker)",
        policy = "Zero-Trust Privacy Gate",
        action = "Blocked Access to Personal Messages/Calls/Location",
        status = "மறுக்கப்பட்டது (Blocked - Zero Leakage)"
      )
      return AwarenessVoiceResponse(
        spokenTamilText = "மன்னிக்கவும். உரிமையாளரின் குரல் சரிபார்க்கப்படவில்லை. தனிப்பட்ட தகவல்கள், செய்திகள் மற்றும் இருப்பிட விபரங்கள் பாதுகாப்பாகப் பூட்டப்பட்டுள்ளன.",
        category = "Privacy Security Gate",
        provenanceHash = hash,
        isSuccess = false
      )
    }

    // 2. Intent Routing for Communication & Awareness
    val (responseTa, category) = when {
      // Incoming Messages Query ("எனக்கு என்ன message வந்திருக்கு?")
      clean.contains("என்ன message") || clean.contains("message வந்திருக்கு") || clean.contains("செய்தி என்ன") || clean.contains("messages காட்டு") -> {
        val critical = _unifiedMessages.value.filter { it.priority == MessagePriority.CRITICAL || it.priority == MessagePriority.IMPORTANT }
        val top = critical.firstOrNull() ?: _unifiedMessages.value.first()
        val speech = "WhatsApp-ல் ${top.senderName}-கிட்ட இருந்து ஒரு முக்கிய message வந்திருக்கிறது. அவர்: \"${top.normalizedTamilSummary}\" என்று தெரிவித்துள்ளார். இதற்கு உங்கள் பதில் தேவைப்படுகிறது."
        speech to "Incoming Messages Intelligence"
      }

      // Important Messages Summary ("இன்று வந்த messages-ல முக்கியமானது என்ன?")
      clean.contains("முக்கியமானது என்ன") || clean.contains("important message") || clean.contains("summary") || clean.contains("சுருக்கம்") -> {
        val total = _daySituation.value.totalMessagesToday
        val crit = _daySituation.value.criticalMessagesToday
        val pending = _daySituation.value.pendingReplyCountToday
        val speech = "இன்று மொத்தம் $total செய்திகள் வந்துள்ளன. அதில் $crit செய்திகள் மிக முக்கியமானவை; 2 செய்திகளுக்கு உங்கள் பதில் தேவைப்படுகிறது. குறிப்பாக அருண் சந்திப்பு குறித்தும், மேலாளர் அவசர ஆவணம் குறித்தும் கேட்டுள்ளனர்."
        speech to "Message Prioritization Summary"
      }

      // Location Query ("நான் இப்போது எங்கே இருக்கிறேன்?")
      clean.contains("எங்கே இருக்கிறேன்") || clean.contains("எங்க இருக்கேன்") || clean.contains("where am i") || clean.contains("இருப்பிடம்") -> {
        val loc = _locationContext.value
        val speech = "நீங்கள் தற்போது ${loc.currentPlaceNameTa}, ${loc.currentAreaNameTa} பகுதியில் இருக்கிறீர்கள். இது உங்கள் ${loc.placeCategory.labelTa}. அருகில் ${loc.nearbyLandmarksTa.joinToString(", ")} உள்ளன."
        speech to "Location Context Awareness"
      }

      // Navigation Destination ("நான் எங்கே போகணும்?")
      clean.contains("எங்கே போகணும்") || clean.contains("எங்க போகணும்") || clean.contains("next place") || clean.contains("அடுத்த சந்திப்பு") -> {
        val sit = _daySituation.value
        val speech = "உங்கள் காலண்டர் அட்டவணைப்படி, அடுத்ததாக ${sit.nextEventTime}-க்கு ${sit.nextScheduledEventTa} உள்ளது. அதற்கான பயண ஏற்பாடுகள் தயாராக உள்ளன."
        speech to "Personal Day Destination Awareness"
      }

      // Navigation Route & ETA ("அங்கே எப்படி போகணும்?")
      clean.contains("எப்படி போகணும்") || clean.contains("route") || clean.contains("வழி") || clean.contains("வழி காட்டு") -> {
        val nav = _activeNavigation.value
        val speech = "${nav.destinationPlaceTa}-க்கு செல்ல இரண்டு வழிகள் உள்ளன. அண்ணா சாலை வழி 8 km. ஆனால் Inner Ring Road வழி 10 km என்றாலும், traffic குறைவாக இருப்பதால் சுமார் 7 நிமிடம் சீக்கிரம் செல்லலாம். காரில் புறப்பட பரிந்துரைக்கப்படுகிறது."
        speech to "Best Route & Traffic Intelligence"
      }

      // Call Intelligence ("யாரு கால் பண்றாங்க?")
      clean.contains("யாரு கால்") || clean.contains("who is calling") || clean.contains("கால் பண்றாங்க") || clean.contains("அழைப்பு யார்") -> {
        val topCall = _callRecords.value.firstOrNull()
        val speech = if (topCall != null && topCall.callerVerifiedName != null) {
          "${topCall.callerVerifiedName} கால் செய்கிறார் (${topCall.callCategory.labelTa}). இன்று இவர் ${topCall.repeatedCallsCountToday}-வது முறையாக அழைக்கிறார்."
        } else if (topCall != null) {
          "அறியப்படாத எண்ணிலிருந்து (${topCall.callerNumber}) அழைப்பு வருகிறது. தொடர்பாளர் பெயர் உங்கள் தொடர்புகளில் உறுதிப்படுத்தப்படவில்லை."
        } else {
          "தற்போது புதிய அழைப்புகள் எதுவும் இல்லை."
        }
        speech to "Call Intelligence & Screening"
      }

      // Reply Assistant ("அவருக்கு நான் busy-ஆ இருக்கேன் என்று சொல்லு")
      clean.contains("busy") || clean.contains("பதில் சொல்லு") || clean.contains("சொல்லு") || clean.contains("reply") -> {
        val target = _unifiedMessages.value.firstOrNull { it.senderName.contains("Arun", ignoreCase = true) } ?: _unifiedMessages.value.first()
        val speech = "சரிங்க. ${target.senderName}-க்கு '${target.suggestedTamilReplies.firstOrNull() ?: "நான் தற்போது பணியில் இருக்கிறேன், பின்னர் அழைக்கிறேன்"}' என்று WhatsApp வழியே அனுப்பவா? உங்கள் ஒப்புதலை உறுதிப்படுத்தவும்."
        speech to "Voice Reply Assistant"
      }

      // Person Search ("முரளி எனக்கு கடைசியாக எப்போது message பண்ணினார்?")
      clean.contains("முரளி") || clean.contains("murali") -> {
        val speech = "முரளி நேற்று மாலை 6:45 மணிக்கு WhatsApp வழியே 'Fusion Plasma Project-ன் அடுத்த கட்ட வரைபடங்கள் தயார்' என்று செய்தி அனுப்பியுள்ளார்."
        speech to "Personal Communication Search"
      }

      // Day Situation ("என்னுடைய current situation என்ன?")
      clean.contains("current situation") || clean.contains("சூழ்நிலை என்ன") || clean.contains("நிலைமை என்ன") -> {
        val sit = _daySituation.value
        val speech = "உங்கள் தற்போதைய சூழ்நிலை: நீங்கள் ${sit.locationSummaryTa}-ல் இருக்கிறீர்கள். அடுத்ததாக ${sit.nextEventTime}-க்கு ${sit.nextScheduledEventTa} உள்ளது. ${sit.urgentMessagesSummaryTa}. ${sit.proactiveTravelAlertTa ?: ""}"
        speech to "Comprehensive Day Situation"
      }

      // Fallback for unavailable info
      else -> {
        val speech = "நீங்கள் கேட்ட விபரம்: \"$queryText\". இதை உறுதிப்படுத்தும் நேரடித் தகவல் உங்கள் இணைக்கப்பட்ட அமைப்புகளில் தற்போது கிடைக்கவில்லை. கூடுதல் விபரங்கள் தேவைப்பட்டால் கேட்கலாம்."
        speech to "Knowledge Limit & Verification"
      }
    }

    val hash = computeSha256("${System.currentTimeMillis()}_${responseTa}")
    recordAudit(
      command = queryText,
      speaker = "உரிமையாளர் (Owner) - Verified Voiceprint",
      policy = "Owner-Only Communication Processing",
      action = "Processed Intent: $category",
      status = "வெற்றி (Executed & Spoken in Natural Tamil)"
    )

    return AwarenessVoiceResponse(
      spokenTamilText = responseTa,
      category = category,
      provenanceHash = hash,
      isSuccess = true
    )
  }

  private fun recordAudit(
    command: String,
    speaker: String,
    policy: String,
    action: String,
    status: String
  ) {
    val hash = computeSha256("${System.currentTimeMillis()}_${command}_${action}")
    val log = AuditableCommunicationAction(
      timestamp = timeFormat.format(Date()),
      requestedCommandTa = command,
      verifiedSpeaker = speaker,
      authorizationPolicyAppliedTa = policy,
      platformActionExecuted = action,
      executionStatusTa = status,
      cryptographicProvenanceHash = hash
    )
    _auditLedger.value = listOf(log) + _auditLedger.value.take(20)
  }

  private fun computeSha256(input: String): String {
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(input.toByteArray())
    return digest.fold("") { str, it -> str + "%02x".format(it) }
  }

  // =========================================================================
  // DEFAULT MOCK / PERSISTED REPOSITORY STATES (HIGH ACCURACY)
  // =========================================================================
  private fun getDefaultMessages(): List<UnifiedMessage> {
    return listOf(
      UnifiedMessage(
        senderName = "Arun (அருண்)",
        senderHandle = "+91 98401 11223",
        platform = CommunicationPlatform.WHATSAPP,
        timestamp = "இன்று காலை 10:15 மணி",
        rawContent = "நாளைக்கு 10 மணிக்கு அடையாறு ஆபீஸ்ல மீட்டிங் பேசலாமா? Solar IoT ப்ராஜெக்ட் பற்றி டிஸ்கஸ் பண்ணனும்.",
        normalizedTamilSummary = "நாளை காலை 10:00 மணிக்கு சோலார் IoT திட்டம் குறித்த சந்திப்பை கோரியுள்ளார்.",
        priority = MessagePriority.CRITICAL,
        intentType = MessageIntentType.MEETING_REQUEST,
        actionRequired = ActionRequirement.REPLY_REQUIRED,
        suggestedTamilReplies = listOf(
          "கண்டிப்பாக, நாளை காலை 10:00 மணிக்கு அடையாறு அலுவலகத்தில் சந்திக்கலாம்.",
          "நாளை காலை 11:30 மணிக்கு மாற்ற முடியுமா? முந்தைய பணி உள்ளது."
        ),
        suggestedTanglishReplies = listOf(
          "Kandippa Arun, 10 AM-ku Adyar office-la meet pannalam.",
          "Tomorrow 11:30 AM ok-va Arun? Morning konjam busy."
        ),
        isRead = false,
        linkedCalendarDraft = CalendarEventDraft(
          title = "Solar IoT Architecture Sync with Arun",
          titleTa = "அருணுடன் சோலார் IoT திட்ட சந்திப்பு",
          date = "நாளை (Tomorrow)",
          time = "10:00 AM - 11:00 AM",
          location = "அடையாறு அலுவலகம்",
          participants = listOf("உரிமையாளர்", "அருண்")
        )
      ),
      UnifiedMessage(
        senderName = "Kavitha (கவிதா - Project Lead)",
        senderHandle = "kavitha.tech@company.com",
        platform = CommunicationPlatform.EMAIL,
        timestamp = "இன்று காலை 09:40 மணி",
        rawContent = "Please find the quantum simulation telemetry logs attached. Review and approve the safety test report before 5 PM.",
        normalizedTamilSummary = "குவாண்டம் சிமுலேஷன் டெலிமெட்ரி அறிக்கையை மாலை 5 மணிக்குள் மதிப்பாய்வு செய்து ஒப்புதல் அளிக்கக் கேட்டுள்ளார்.",
        priority = MessagePriority.IMPORTANT,
        intentType = MessageIntentType.TASK_ACTION_REQUEST,
        actionRequired = ActionRequirement.TASK_CREATION_RECOMMENDED,
        suggestedTamilReplies = listOf(
          "அறிக்கையை மதிப்பாய்வு செய்து மாலை 4:00 மணிக்குள் ஒப்புதல் வழங்குகிறேன்.",
          "டெலிமெட்ரி தரவுகளை சரிபார்த்துவிட்டேன், அனைத்தும் பாதுகாப்பாக உள்ளன."
        ),
        suggestedTanglishReplies = listOf(
          "Report-a review pannitu 4 PM kulla update panren Kavitha.",
          "Telemetry data check panniten, everything is safe."
        ),
        isRead = false,
        linkedTaskDraft = TaskReminderDraft(
          title = "Review Quantum Telemetry Report",
          titleTa = "குவாண்டம் டெலிமெட்ரி அறிக்கையை மதிப்பாய்வு செய்",
          dueDate = "இன்று (Today)",
          dueTime = "04:30 PM",
          priority = MessagePriority.IMPORTANT
        )
      ),
      UnifiedMessage(
        senderName = "Murali (முரளி)",
        senderHandle = "@murali_innovate",
        platform = CommunicationPlatform.INSTAGRAM,
        timestamp = "நேற்று மாலை 06:45 மணி",
        rawContent = "ஹாய், Fusion Plasma Physics 3D மாதிரி அட்டகாசமாக வந்திருக்கு! அடுத்த ஸ்டெப் எப்போது தொடங்கலாம்?",
        normalizedTamilSummary = "பிளாஸ்மா இயற்பியல் 3D மாதிரி சிறப்பாக வந்துள்ளதாகவும், அடுத்த கட்டத்தை எப்போது தொடங்கலாம் எனவும் வினவியுள்ளார்.",
        priority = MessagePriority.NORMAL,
        intentType = MessageIntentType.GENERAL_CHAT,
        actionRequired = ActionRequirement.REPLY_REQUIRED,
        suggestedTamilReplies = listOf(
          "மிக்க நன்றி முரளி! அடுத்த கட்ட சிமுலேஷனை நாளை தொடங்குகிறோம்.",
          "3D மாதிரியின் அடுத்த வெர்ஷனை இன்று மாலையே பகிர்கிறேன்."
        ),
        suggestedTanglishReplies = listOf(
          "Thanks Murali! Next phase simulation tomorrow start panrom.",
          "Next version 3D model-a inaiku evening share panren."
        ),
        isRead = true
      ),
      UnifiedMessage(
        senderName = "Bank Alert (வங்கி விழிப்பூட்டல்)",
        senderHandle = "VM-HDFCBK",
        platform = CommunicationPlatform.SMS,
        timestamp = "இன்று காலை 08:30 மணி",
        rawContent = "A/C *1204 debited with INR 450.00 on 23-Aug at Metro Station. Avl Bal: INR 84,200.00. If not you, SMS BLOCK to 56767.",
        normalizedTamilSummary = "மெட்ரோ ரயில் நிலைய பயணத்திற்கான ரூ. 450 பரிவர்த்தனை விபரம்.",
        priority = MessagePriority.NORMAL,
        intentType = MessageIntentType.PAYMENT_OR_FINANCIAL,
        actionRequired = ActionRequirement.NO_ACTION_INFORMATIONAL,
        isRead = true
      ),
      UnifiedMessage(
        senderName = "Unknown Sender (சந்தேகத்திற்குரியவர்)",
        senderHandle = "+1 800 992 110",
        platform = CommunicationPlatform.SMS,
        timestamp = "இன்று காலை 07:15 மணி",
        rawContent = "URGENT: Your account reward points expiring today. Click bit.ly/claim-inr-5000 to redeem cash instantly!",
        normalizedTamilSummary = "சந்தேகத்திற்கிடமான பரிசுப் புள்ளி பண மீட்பு இணைப்பு.",
        priority = MessagePriority.PROMOTIONAL_NOISE,
        intentType = MessageIntentType.SUSPICIOUS_SCAM,
        actionRequired = ActionRequirement.SECURITY_WARNING_BLOCK,
        isSpamOrSuspicious = true,
        spamRiskEvidence = "ஆதாரமற்ற வெளிப்புற ஃபிஷிங் இணைப்பு (bit.ly link) மற்றும் அவசர பணக் கோரிக்கை கண்டறியப்பட்டுள்ளது.",
        isRead = false
      )
    )
  }

  private fun getDefaultCallRecords(): List<IncomingCallRecord> {
    return listOf(
      IncomingCallRecord(
        callerNumber = "+91 98401 55667",
        callerVerifiedName = "Ravi (ரவி - Senior Engineer)",
        timestamp = "இன்று காலை 10:45 மணி",
        callCategory = CallCategory.KNOWN_VIP,
        repeatedCallsCountToday = 2,
        screeningInsightTa = "அறிந்த முக்கிய பொறியாளர். இன்று இரண்டாவது முறையாக அழைக்கிறார். ஹாலோகிராபிக் சிமுலேஷன் குறித்த அவசர ஆலோசனை இருக்கலாம்.",
        recommendedActionTa = "முக்கிய அழைப்பு - உடனடியாக ஏற்கவும் அல்லது குரல் வழி பதில் அனுப்பவும்."
      ),
      IncomingCallRecord(
        callerNumber = "+91 94440 99881",
        callerVerifiedName = null,
        timestamp = "இன்று காலை 09:12 மணி",
        callCategory = CallCategory.UNKNOWN_NUMBER,
        repeatedCallsCountToday = 1,
        screeningInsightTa = "அறியப்படாத எண். தொடர்பாளர் பெயர் முகவரிப் புத்தகத்தில் இல்லை. முந்தைய அழைப்பு வரலாறு ஏதுமில்லை.",
        recommendedActionTa = "அழைப்பை ஸ்கிரீனிங் செய்து விபரம் கேட்கவும்."
      ),
      IncomingCallRecord(
        callerNumber = "+91 80 4499 1000",
        callerVerifiedName = "Credit Card Telemarketing (கடன் அட்டை அழைப்பு)",
        timestamp = "இன்று காலை 08:20 மணி",
        callCategory = CallCategory.SUSPECTED_SPAM_SCAM,
        repeatedCallsCountToday = 3,
        screeningInsightTa = "தானியங்கி ரோபோகால் (Robocall) மற்றும் விளம்பர கடன் அட்டை அழைப்பு முறைமை.",
        spamConfidenceScore = 0.96f,
        spamEvidenceReasonTa = "சமூக தொலைபேசி தரவுத்தளத்தில் 1,200+ நபர்களால் ஸ்பேம் என அறிவிக்கப்பட்டுள்ளது.",
        recommendedActionTa = "தானியங்கி முறையில் நிராகரித்து பிளாக் செய்யப்பட்டுள்ளது."
      )
    )
  }

  private fun getDefaultLocationContext(): LocationContextState {
    return LocationContextState(
      currentPlaceNameTa = "தன்னாட்சி ஆய்வகம் & அலுவலகம் (Sovereign Lab)",
      currentAreaNameTa = "அடையாறு, சென்னை (Adyar)",
      approximateCoordinates = Pair(13.0067, 80.2570),
      placeCategory = PlaceCategory.WORK_OFFICE,
      privacyZone = LocationPrivacyZone.NORMAL_LOGGED,
      nearbyLandmarksTa = listOf("இந்திரா நகர் மெட்ரோ", "மத்திய கைலாஷ் சந்திப்பு", "அடையாறு பூங்கா"),
      isGpsAuthorized = true,
      lastUpdatedTime = "10:50 AM"
    )
  }

  private fun getDefaultNavigationPlan(): ActiveNavigationPlan {
    val r1 = NavigationRouteOption(
      routeId = "route_1_main",
      routeTitleTa = "அண்ணா சாலை வழி (Anna Salai Direct)",
      routeTitleEn = "via Anna Salai",
      distanceKm = 8.2f,
      estimatedMinutes = 28,
      travelMode = TravelMode.CAR,
      trafficLevel = TrafficLevel.HEAVY,
      hasToll = false,
      roadConditionNotesTa = "நந்தனம் சிக்னலில் கடும் போக்குவரத்து நெரிசல் நிலவுகிறது.",
      comparisonRationaleTa = "குறைந்த தூரம் (8.2 km) ஆனால் நந்தனத்தில் கடும் நெரிசல் காரணமாக 28 நிமிடங்கள் ஆகும்.",
      isRecommended = false
    )

    val r2 = NavigationRouteOption(
      routeId = "route_2_ring",
      routeTitleTa = "உள் வட்டச் சாலை வழி (Inner Ring Road Expressway)",
      routeTitleEn = "via Inner Ring Road",
      distanceKm = 10.4f,
      estimatedMinutes = 21,
      travelMode = TravelMode.CAR,
      trafficLevel = TrafficLevel.CLEAR,
      hasToll = false,
      roadConditionNotesTa = "போக்குவரத்து சீராக உள்ளது; சிக்னல்கள் குறைவு.",
      comparisonRationaleTa = "இந்த வழி 10.4 km என்றாலும், traffic குறைவாக இருப்பதால் சுமார் 7 நிமிடம் சீக்கிரம் (21 நிமிடத்தில்) செல்லலாம்.",
      isRecommended = true
    )

    val r3 = NavigationRouteOption(
      routeId = "route_3_bike",
      routeTitleTa = "இருசக்கர வாகனம் / குறுக்கு வழி (Two-Wheeler Shortcut)",
      routeTitleEn = "via Kotturpuram Bypass",
      distanceKm = 7.5f,
      estimatedMinutes = 18,
      travelMode = TravelMode.TWO_WHEELER,
      trafficLevel = TrafficLevel.MODERATE,
      hasToll = false,
      roadConditionNotesTa = "இருசக்கர வாகனங்களுக்கு உகந்த குறுகிய சாலை வழி.",
      comparisonRationaleTa = "இருசக்கர வாகனத்திற்கு மிக விரைவான வழி (18 நிமிடங்கள்).",
      isRecommended = false
    )

    return ActiveNavigationPlan(
      originPlaceTa = "அடையாறு அலுவலகம்",
      destinationPlaceTa = "கிண்டி ஐடி பூங்கா (Guindy Tech Park)",
      targetArrivalTime = "04:30 PM",
      recommendedDepartureTime = "03:55 PM",
      proactiveDepartureAlertTa = "நீங்கள் மாலை 4:30 மணிக்கு கிண்டியில் இருக்க வேண்டுமென்றால் பிற்பகல் 3:55 மணிக்கு கிளம்புவது நல்லது. Inner Ring Road வழியே சென்றால் 21 நிமிடங்களில் சென்றடையலாம்.",
      routes = listOf(r2, r1, r3),
      selectedRouteId = "route_2_ring"
    )
  }

  private fun getDefaultDaySituation(): PersonalDaySituation {
    return PersonalDaySituation(
      currentTimestamp = "இன்று காலை 11:00 மணி",
      locationSummaryTa = "அடையாறு அலுவலக வளாகம்",
      currentActivityTa = "குவாண்டம் இயற்பியல் & AI ஆவணங்கள் மதிப்பாய்வு",
      nextScheduledEventTa = "கிளைண்ட் டெக்னாலஜி ஆலோசனை சந்திப்பு",
      nextEventTime = "மாலை 04:30 மணி",
      urgentPendingMessagesCount = 2,
      urgentMessagesSummaryTa = "அருண் மற்றும் கவிதாவிடம் இருந்து 2 முக்கிய செய்திகள் பதிலுக்காகக் காத்திருக்கின்றன",
      pendingCallAlertsCount = 1,
      proactiveTravelAlertTa = "கிண்டி செல்லும் வழியில் அண்ணா சாலையில் நெரிசல் உள்ளதால், Inner Ring Road வழியே 3:55 PM-க்கு புறப்படுவது உகந்தது.",
      proactiveProductivityAdviceTa = "மாலை 5:00 மணிக்கு முன் குவாண்டம் டெலிமெட்ரி அறிக்கையை அங்கீகரிக்க நினைவூட்டல் அமைக்கப்பட்டுள்ளது.",
      totalMessagesToday = 37,
      criticalMessagesToday = 4,
      pendingReplyCountToday = 2
    )
  }

  private fun getDefaultAuditLogs(): List<AuditableCommunicationAction> {
    return listOf(
      AuditableCommunicationAction(
        timestamp = "10:16 AM",
        requestedCommandTa = "எனக்கு என்ன message வந்திருக்கு?",
        verifiedSpeaker = "உரிமையாளர் (Owner) - 99.4% Biometric Match",
        authorizationPolicyAppliedTa = "Owner-Only Communication Readout",
        platformActionExecuted = "WhatsApp Notification Aggregator Query",
        executionStatusTa = "வெற்றி - குரல் வழி அறிவிக்கப்பட்டது",
        cryptographicProvenanceHash = "a4f89d917c992e54bb201f11c79a8e09"
      ),
      AuditableCommunicationAction(
        timestamp = "09:41 AM",
        requestedCommandTa = "இன்று வந்த முக்கியமான செய்திகள் என்ன?",
        verifiedSpeaker = "உரிமையாளர் (Owner) - 99.4% Biometric Match",
        authorizationPolicyAppliedTa = "Message Prioritization Filter",
        platformActionExecuted = "Cross-Platform Intelligence Synthesis",
        executionStatusTa = "வெற்றி - தமிழ் சுருக்கம் வழங்கப்பட்டது",
        cryptographicProvenanceHash = "f9901d8bc0771239aa88bbf0299e1a3c"
      ),
      AuditableCommunicationAction(
        timestamp = "08:21 AM",
        requestedCommandTa = "அழைப்பு ஸ்கிரீனிங்: 080 4499 1000",
        verifiedSpeaker = "Automated Security Agent",
        authorizationPolicyAppliedTa = "Spam Telemarketing Auto-Filter",
        platformActionExecuted = "CallScreeningService Block Execution",
        executionStatusTa = "தானியங்கி முறையில் பிளாக் செய்யப்பட்டது",
        cryptographicProvenanceHash = "b82194c77ea1029940aa28987114b091"
      )
    )
  }
}

data class AwarenessVoiceResponse(
  val spokenTamilText: String,
  val category: String,
  val provenanceHash: String,
  val isSuccess: Boolean
)
