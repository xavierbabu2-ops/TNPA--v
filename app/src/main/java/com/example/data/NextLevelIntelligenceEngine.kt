package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

/**
 * NEXT-LEVEL PERSONAL INTELLIGENCE ENGINE (30 PILLARS)
 * Unifies Intent Prediction, AI Eyes (Camera Context), AI Ears (360° Sound Awareness),
 * Presence, Habit Intelligence, Forgetfulness Radar, Life Commands, Decision "Why?" Memory,
 * Contradiction Detector, Think-With-Me / Devil's Advocate, Personal AI Constitution, Skill Tree.
 */
object NextLevelIntelligenceEngine {

  // 1. INTENT PREDICTION
  private val _predictedIntents = MutableStateFlow<List<PredictedIntentSuggestion>>(getDefaultPredictedIntents())
  val predictedIntents: StateFlow<List<PredictedIntentSuggestion>> = _predictedIntents.asStateFlow()

  // 2. "AI EYES" - CAMERA CONTEXT & AR DIAGNOSTICS
  private val _visualInsights = MutableStateFlow<List<VisualSceneInsight>>(getDefaultVisualInsights())
  val visualInsights: StateFlow<List<VisualSceneInsight>> = _visualInsights.asStateFlow()

  // 3. "AI EARS" - 360° SOUND & AMBIENT AWARENESS
  private val _ambientSounds = MutableStateFlow<List<DetectedAmbientSound>>(getDefaultAmbientSounds())
  val ambientSounds: StateFlow<List<DetectedAmbientSound>> = _ambientSounds.asStateFlow()

  // 4. PRESENCE & DEVICE SIGNALS
  private val _presenceContext = MutableStateFlow(
    PresenceContext(
      primaryLocationLabel = "வீட்டு ஆய்வகம் (Home Lab)",
      ownerDeviceInHand = true,
      nearbyAuthorizedDevices = listOf("Workstation Linux Node", "ESP32 Sensor Hub", "Tablet Pad"),
      ambientLightingState = "வழக்கமான பகல் வெளிச்சம் (550 Lux)",
      acousticNoiseLevel = "அமைதியானது (32 dB)",
      privacyZoneActive = false
    )
  )
  val presenceContext: StateFlow<PresenceContext> = _presenceContext.asStateFlow()

  // 5. HABIT INTELLIGENCE
  private val _habitInsights = MutableStateFlow<List<PersonalHabitInsight>>(getDefaultHabits())
  val habitInsights: StateFlow<List<PersonalHabitInsight>> = _habitInsights.asStateFlow()

  // 6. "WHAT AM I FORGETTING?" FORGETFULNESS RADAR
  private val _departureChecklist = MutableStateFlow<List<DepartureChecklistItem>>(getDefaultDepartureChecklist())
  val departureChecklist: StateFlow<List<DepartureChecklistItem>> = _departureChecklist.asStateFlow()

  // 7. LIFE COMMAND MULTI-STEP WORKFLOWS
  private val _lifeCommands = MutableStateFlow<List<LifeCommandWorkflow>>(getDefaultLifeCommands())
  val lifeCommands: StateFlow<List<LifeCommandWorkflow>> = _lifeCommands.asStateFlow()

  // 8. "WHY?" DECISION MEMORY
  private val _decisionWhyRecords = MutableStateFlow<List<DecisionWhyRecord>>(getDefaultDecisionWhyRecords())
  val decisionWhyRecords: StateFlow<List<DecisionWhyRecord>> = _decisionWhyRecords.asStateFlow()

  // 9. CONTRADICTION DETECTOR
  private val _contradictions = MutableStateFlow<List<ContradictionConflict>>(getDefaultContradictions())
  val contradictions: StateFlow<List<ContradictionConflict>> = _contradictions.asStateFlow()

  // 10. "THINK WITH ME" & DEVIL'S ADVOCATE
  private val _thinkWithMeSessions = MutableStateFlow<List<ThinkWithMeSession>>(getDefaultThinkWithMeSessions())
  val thinkWithMeSessions: StateFlow<List<ThinkWithMeSession>> = _thinkWithMeSessions.asStateFlow()

  // 11. PERSONAL AI CONSTITUTION
  private val _constitutionRules = MutableStateFlow<List<ConstitutionRule>>(getDefaultConstitutionRules())
  val constitutionRules: StateFlow<List<ConstitutionRule>> = _constitutionRules.asStateFlow()

  // 12. SKILL TREE & OPPORTUNITY RADAR
  private val _skillTree = MutableStateFlow<List<SkillTreeNode>>(getDefaultSkillTree())
  val skillTree: StateFlow<List<SkillTreeNode>> = _skillTree.asStateFlow()

  private val _opportunityRadar = MutableStateFlow<List<OpportunityRadarItem>>(getDefaultOpportunityRadar())
  val opportunityRadar: StateFlow<List<OpportunityRadarItem>> = _opportunityRadar.asStateFlow()

  private val _ideaGraveyard = MutableStateFlow<List<IdeaGraveyardItem>>(getDefaultIdeaGraveyard())
  val ideaGraveyard: StateFlow<List<IdeaGraveyardItem>> = _ideaGraveyard.asStateFlow()

  // Actions
  fun toggleDepartureItem(itemId: String) {
    _departureChecklist.value = _departureChecklist.value.map { item ->
      if (item.id == itemId) item.copy(isChecked = !item.isChecked) else item
    }
  }

  fun adoptHabitShortcut(habitId: String) {
    _habitInsights.value = _habitInsights.value.map { habit ->
      if (habit.id == habitId) habit.copy(isAdopted = true) else habit
    }
  }

  fun resolveContradiction(conflictId: String) {
    _contradictions.value = _contradictions.value.map { conflict ->
      if (conflict.id == conflictId) conflict.copy(isResolved = true) else conflict
    }
  }

  fun reviveIdea(ideaId: String) {
    _ideaGraveyard.value = _ideaGraveyard.value.map { idea ->
      if (idea.id == ideaId) idea.copy(isRevived = true) else idea
    }
  }

  fun executeNextLifeCommandStep(workflowId: String) {
    _lifeCommands.value = _lifeCommands.value.map { cmd ->
      if (cmd.id == workflowId) {
        val nextIdx = cmd.currentExecutingStepIndex + 1
        cmd.copy(
          currentExecutingStepIndex = nextIdx.coerceAtMost(cmd.totalStepsCount),
          isCompleted = nextIdx >= cmd.totalStepsCount
        )
      } else cmd
    }
  }

  private fun getDefaultPredictedIntents(): List<PredictedIntentSuggestion> {
    return listOf(
      PredictedIntentSuggestion(
        triggerContext = "நீங்கள் மாலை 06:15 மணிக்கு காரில் ஏறுகிறீர்கள் + காலண்டரில் 07:00 PM சந்திப்பு உள்ளது",
        predictedIntentTa = "«“நான் இப்போது வெளியே கிளம்பணும்…”»",
        predictedIntentEn = "I need to head out now...",
        suggestedActionTa = "வழக்கம் போல் அண்ணா நகர் அலுவலகத்திற்கு டிராஃபிக் குறைவான பாதையைத் தயார் செய்யட்டுமா? (22 நிமிடம் ஆகும்)",
        confidenceScore = 0.96f
      ),
      PredictedIntentSuggestion(
        triggerContext = "திங்கட்கிழமை காலை 09:00 AM + லேப் வொர்க்ஸ்டேஷன் ஆன் செய்யப்பட்டுள்ளது",
        predictedIntentTa = "«“டோகமாக் பிளாஸ்மா ப்ராஜெக்ட்டைத் தொடங்கு…”»",
        predictedIntentEn = "Resume Tokamak Plasma Project...",
        suggestedActionTa = "நேற்று விட்ட இடத்திலிருந்து பிளாஸ்மா காந்த சமன்பாடுகளைத் திரையில் திறக்கட்டுமா?",
        confidenceScore = 0.92f
      )
    )
  }

  private fun getDefaultVisualInsights(): List<VisualSceneInsight> {
    return listOf(
      VisualSceneInsight(
        sceneTitleTa = "ESP32 IoT சென்சார் போர்டு - பழுது ஆய்வு (Visual AI Eyes)",
        diagnosticType = VisualDiagnosticType.CIRCUIT_INSPECTION,
        detectedComponents = listOf("ESP32-WROOM", "LM7805 Voltage Regulator", "DHT22 Sensor", "10uF Capacitor"),
        rootCauseDiagnosisTa = "LM7805 ரெகுலேட்டரின் Ground பின்னில் லூஸ் சோல்டரிங் (Cold Joint) உள்ளது. இதனால் வோல்டேஜ் ஏற்ற இறக்கம் ஏற்படுகிறது.",
        repairStepsTa = listOf(
          "1. பவர் சப்ளையை உடனடியாகத் துண்டிக்கவும்.",
          "2. LM7805-ன் நடுப்பகுதி பின்னை 350°C சோல்டரிங் அயர்ன் மூலம் ரீ-சோல்டர் செய்யவும்.",
          "3. மல்டிமீட்டர் கொண்டு 5.0V அவுட்புட்டை உறுதிப்படுத்திய பின் மீண்டும் பவர் ஆன் செய்யவும்."
        ),
        arOverlayNotes = "AR மேலடுக்கு: சோல்டர் செய்ய வேண்டிய பாயிண்ட் பச்சை நிற வட்டத்தில் ஒளிர்கிறது.",
        safetyWarningTa = "எச்சரிக்கை: பவர் ஆன் நிலையில் சோல்டரிங் செய்ய வேண்டாம்."
      )
    )
  }

  private fun getDefaultAmbientSounds(): List<DetectedAmbientSound> {
    return listOf(
      DetectedAmbientSound(
        soundType = AmbientSoundType.DOORBELL,
        timestamp = "இன்று காலை 11:20 AM",
        decibelLevel = 68,
        confidence = 0.99f,
        contextAdviceTa = "முன்வாசல் அழைப்பு மணி ஒலித்தது. உங்கள் போன் வைப்ரேட் செய்யப்பட்டுள்ளது."
      ),
      DetectedAmbientSound(
        soundType = AmbientSoundType.APPLIANCE_BEEP,
        timestamp = "இன்று காலை 09:45 AM",
        decibelLevel = 54,
        confidence = 0.95f,
        contextAdviceTa = "ஆய்வக பேட்டரி சார்ஜர் 100% முழுமையாக சார்ஜ் ஆகிவிட்டது."
      ),
      DetectedAmbientSound(
        soundType = AmbientSoundType.GLASS_BREAKING,
        timestamp = "நேற்று இரவு 02:10 AM",
        decibelLevel = 40,
        confidence = 0.32f,
        contextAdviceTa = "லேசான சத்தம் (காற்று வீசியதால் கதவு உராய்வு) - பாதுகாப்பு சோதனை முடிந்தது, அமைதி நிலவுகிறது."
      )
    )
  }

  private fun getDefaultHabits(): List<PersonalHabitInsight> {
    return listOf(
      PersonalHabitInsight(
        patternNameTa = "வார இறுதி அறிவியல் சுருக்கம் & சிமுலேஷன்",
        patternNameEn = "Weekly Science Derivation Archive",
        frequencyDescription = "ஒவ்வொரு சனிக்கிழமையும் காலை 10:00 மணிக்கு இயற்பியல் குறிப்புகளைத் திறக்கிறீர்கள்.",
        proposedShortcutTa = "«“வார இறுதி அறிவியல் லேப்”» என்ற ஒரே குரல் கட்டளையில் சிமுலேஷன் மற்றும் நோட்ஸ்களைத் திறக்க ஷார்ட்கட் உருவாக்கட்டுமா?",
        estimatedTimeSavedMinutesPerWeek = 25,
        isAdopted = false
      )
    )
  }

  private fun getDefaultDepartureChecklist(): List<DepartureChecklistItem> {
    return listOf(
      DepartureChecklistItem(
        itemNameTa = "லேப்டாப் மற்றும் USB செக்யூரிட்டி கீ (Sovereign Token)",
        reasonTa = "இன்று மதியம் திட்ட டெமோ இருக்கிறது.",
        urgencyLevel = "Critical",
        isChecked = false
      ),
      DepartureChecklistItem(
        itemNameTa = "குடை / மழை அங்கி (Rain Gear)",
        reasonTa = "இன்று மாலை 05:00 மணிக்கு மழை பெய்ய 75% வாய்ப்பு உள்ளது.",
        urgencyLevel = "Weather Alert",
        isChecked = true
      ),
      DepartureChecklistItem(
        itemNameTa = "காரின் சாவிகள் & அடையாள அட்டை",
        reasonTa = "அலுவலக நுழைவு வாயில் சோதனைக்குத் தேவை.",
        urgencyLevel = "Critical",
        isChecked = true
      )
    )
  }

  private fun getDefaultLifeCommands(): List<LifeCommandWorkflow> {
    return listOf(
      LifeCommandWorkflow(
        commandNameTa = "திட்ட பிரசன்டேஷன் முழு தயாரிப்பு (Life Command)",
        originalPrompt = "«“நாளைக்கு project presentation-க்கு என்னை தயார் பண்ணு.”»",
        totalStepsCount = 6,
        currentExecutingStepIndex = 4,
        executionStepsTa = listOf(
          "1. ஆய்வுக் குறிப்புகள் மற்றும் அறிவியல் தரவுகளைச் சேகரித்தல் (Done)",
          "2. 12 ஸ்லைடுகள் கொண்ட பிரசன்டேஷன் கட்டமைப்பு உருவாக்குதல் (Done)",
          "3. எதிர்பார்க்கப்படும் 5 கடினமான கேள்வி-பதில்கள் தயாரித்தல் (Done)",
          "4. தமிழ் & ஆங்கில குரல் ஒத்திகை வழிகாட்டல் உருவாக்குதல் (In Progress)",
          "5. தேவையான அனைத்து கோப்புகளையும் ஒரே ஜிப் ஆவணத்தில் தொகுத்தல் (Pending)",
          "6. நாளை காலை 08:30 AM நினைவூட்டல் அமைத்தல் (Pending)"
        ),
        generatedArtifactsTa = listOf("Presentation_Deck_V2.pdf", "QA_CheatSheet.md", "Voice_Rehearsal.mp3"),
        isCompleted = false
      )
    )
  }

  private fun getDefaultDecisionWhyRecords(): List<DecisionWhyRecord> {
    return listOf(
      DecisionWhyRecord(
        projectOrTopicName = "டோகமாக் பிளாஸ்மா காந்தக் கட்டுப்பாடு (Tokamak Project)",
        decisionSummaryTa = "Divertor அமைப்பிற்கு டங்ஸ்டன் (Tungsten) பூச்சு தேர்வு செய்யப்பட்டது.",
        whyRationaleTa = "காரணம்: கார்பன் ஃபைபரை விட டங்ஸ்டன் 3422°C வரை வெப்பத்தைத் தாங்கும் மற்றும் பிளாஸ்மாவில் ட்ரிட்டியம் உறிஞ்சுதலைக் கணிசமாகக் குறைக்கும்.",
        rejectedAlternativesTa = listOf("கார்பன்-கார்பன் காம்போசிட் (டிரிட்டியம் உறிஞ்சுதல் அதிகம்)", "பெரிலியம் (குறைந்த உருகுநிலை)"),
        decisionTimestamp = "3 மாதங்களுக்கு முன்பு (மே 14, 2026)",
        contextFactors = listOf("வெப்பநிலை தாங்குதிறன்", "சுத்தமான பிளாஸ்மா வெற்றிடம்", "நீண்டகால நம்பகத்தன்மை")
      )
    )
  }

  private fun getDefaultContradictions(): List<ContradictionConflict> {
    return listOf(
      ContradictionConflict(
        currentStatementTa = "«“இன்ஸ்டாகிராம் பதிவிற்கு ஃபேன்ஸி எழுத்துரு (Decorative Font) பயன்படுத்தலாம்.”»",
        pastConflictingStatementTa = "«“மொபைல் திரையில் படிக்க எளிதாக இருக்க எப்போதும் Clean Sans-Serif மட்டுமே பயன்படுத்த வேண்டும்.”»",
        pastDateOrContext = "கடந்த வாரம் வடிவமைப்பு விவாதத்தில் நீங்கள் கூறியது",
        aiClarificationQueryTa = "முன்பு Sans-Serif மட்டுமே பயன்படுத்தலாம் என்று கூறியிருந்தீர்கள். இப்போது இந்த குறிப்பிட்ட பதிவுக்கு மட்டும் Fancy font பயன்படுத்த விரும்புகிறீர்களா?",
        isResolved = false
      )
    )
  }

  private fun getDefaultThinkWithMeSessions(): List<ThinkWithMeSession> {
    return listOf(
      ThinkWithMeSession(
        problemTitleTa = "திட்ட கட்டமைப்பு தேர்வு: லோக்கல் எட்ஜ் மாடல் vs ஹைபிரிட் பிரைவேட் கிளவுட்",
        options = listOf(
          TradeoffOptionAnalysis(
            optionTitleTa = "Option A: 100% On-Device Local Edge Model",
            advantagesTa = listOf("முழுமையான தனியுரிமை (Zero Cloud)", "இணையம் இல்லாமலும் இயங்கும்", "ஜீரோ கிளவுட் சர்வர் செலவு"),
            disadvantagesTa = listOf("மொபைல் ரேம் (RAM) மற்றும் பேட்டரி பயன்பாடு அதிகம்", "மிகப் பெரிய 70B மாடல்களை இயக்க முடியாது"),
            potentialRisksTa = listOf("மொபைல் சூடாதல் சாத்தியம்"),
            resourceCostScore = "₹0 Cloud Cost"
          ),
          TradeoffOptionAnalysis(
            optionTitleTa = "Option B: ஹைபிரிட் பிரைவேட் விபிஎன் சர்வர் (Private Self-Hosted Server)",
            advantagesTa = listOf("மிகப்பெரிய மாடல்களை அதிவேகமாக இயக்கலாம்", "மொபைல் பேட்டரி மிச்சமாகும்", "நமது சொந்த சர்வரிலேயே தரவு இருக்கும்"),
            disadvantagesTa = listOf("வீட்டு சர்வர் 24/7 ஆன் நிலையில் இருக்க வேண்டும்", "இணைய இணைப்பு அவசியம்"),
            potentialRisksTa = listOf("மின்வெட்டு ஏற்பட்டால் சர்வர் ஆஃப் ஆகும் ஆபத்து"),
            resourceCostScore = "குறைந்த மின்சாரச் செலவு மட்டும்"
          )
        ),
        devilsAdvocateCritiqueTa = "சாத்தானின் வழக்கறிஞர் பார்வை (Devil's Advocate): நீங்கள் 100% On-Device மட்டுமே போதும் என்று நினைத்தால், எதிர்காலத்தில் நீங்கள் செய்யப்போகும் பெரிய 3D வீடியோ ரெண்டரிங் மற்றும் குவாண்டம் சிமுலேஷன்களுக்கு மொபைல் செயலிழக்கக்கூடும். எனவே ஹைப்ரிட் அணுகுமுறையே நீண்டகாலத்திற்கு பாதுகாப்பானது.",
        synthesizedBalancedRecommendationTa = "பரிந்துரை: முக்கியமான குரல் மற்றும் பாதுகாப்பு பணிகளை மொபைலிலும், கடினமான வீடியோ/அறிவியல் கணக்கீடுகளை சொந்த வீட்டு சர்வரிலும் இயக்கும் 'ஹைப்ரிட் தன்னாட்சி' முறையைத் தேர்ந்தெடுக்கலாம்."
      )
    )
  }

  private fun getDefaultConstitutionRules(): List<ConstitutionRule> {
    return listOf(
      ConstitutionRule(
        ruleId = "CONST_01_DATA_SOVEREIGNTY",
        titleTa = "தரவு தன்னாட்சி விதி (Data Sovereignty Rule)",
        ruleDescriptionTa = "உரிமையாளரின் தனிப்பட்ட செய்திகள், குரல் மாதிரிகள் மற்றும் இருப்பிடத் தரவு எந்தவொரு மூன்றாம் தரப்பு சர்வர்களுக்கும் பகிரப்படக்கூடாது.",
        isAutonomousAllowed = true,
        requiresExplicitConfirmation = false,
        isStrictlyForbidden = false,
        securityPriority = 1
      ),
      ConstitutionRule(
        ruleId = "CONST_02_ACTION_CONFIRMATION",
        titleTa = "முக்கிய செயல்பாடுகளுக்கு முன் அனுமதி (Confirmation Gate)",
        ruleDescriptionTa = "மின்னஞ்சல் அனுப்புதல், பணம் செலுத்துதல், கோப்புகளை நிரந்தரமாக அழித்தல் மற்றும் சமூக வலைத்தளங்களில் பொதுப் பதிவுகள் இடுவதற்கு உரிமையாளரின் வெளிப்படையான அனுமதி கட்டாயம்.",
        isAutonomousAllowed = false,
        requiresExplicitConfirmation = true,
        isStrictlyForbidden = false,
        securityPriority = 1
      ),
      ConstitutionRule(
        ruleId = "CONST_03_NO_HALLUCINATION",
        titleTa = "உண்மைத்தன்மை & ஆதாரம் நிலைநிறுத்தல் (Evidence-First Rule)",
        ruleDescriptionTa = "அறியப்படாத நபரின் பெயர் அல்லது உறுதிப்படுத்தப்படாத அறிவியல் தகவலை AI ஒருபோதும் ஊகிக்கக்கூடாது; 'தகவல் கிடைக்கவில்லை' என வெளிப்படையாகக் கூற வேண்டும்.",
        isAutonomousAllowed = true,
        requiresExplicitConfirmation = false,
        isStrictlyForbidden = false,
        securityPriority = 2
      )
    )
  }

  private fun getDefaultSkillTree(): List<SkillTreeNode> {
    return listOf(
      SkillTreeNode(
        id = "skill_quantum",
        skillNameTa = "குவாண்டம் இயற்பியல் & கம்ப்யூட்டிங்",
        skillNameEn = "Quantum Computing",
        category = "Science",
        masteryPercentage = 84,
        prerequisiteSkills = listOf("லீனியர் அல்ஜீப்ரா", "அலை இயக்கவியல்"),
        subSkills = listOf("குவாண்டம் மேற்பொருந்துதல்", "குவாண்டம் பிணைப்பு (Bell State)", "க்யூபிட் லாஜிக் கேட்ஸ்"),
        isCompleted = false
      ),
      SkillTreeNode(
        id = "skill_embedded",
        skillNameTa = "எம்படட் சிஸ்டம்ஸ் & எட்ஜ் AI",
        skillNameEn = "Embedded Systems & Edge AI",
        category = "Engineering",
        masteryPercentage = 92,
        prerequisiteSkills = listOf("C++ / Rust", "டிஜிட்டல் சர்க்யூட்ஸ்"),
        subSkills = listOf("ESP32 ஃபார்ம்வேர்", "TensorFlow Lite Micro", "LoRaWAN ரேடியோ"),
        isCompleted = true
      )
    )
  }

  private fun getDefaultOpportunityRadar(): List<OpportunityRadarItem> {
    return listOf(
      OpportunityRadarItem(
        titleTa = "எட்ஜ் AI + சோலார் IoT இயற்பியல் மானிட்டர்",
        intersectingFactors = listOf("ESP32 IoT ப்ராஜெக்ட்", "குவாண்டம் ஆற்றல் ஆய்வு", "சோலார் சென்சார்"),
        potentialInnovationTa = "பேட்டரி இல்லாத, சூரிய ஒளியில் மட்டுமே இயங்கும் எட்ஜ் AI சென்சார் மூலம் சுற்றுச்சூழல் கதிர்வீச்சை அளவிடும் ஒரு புதிய திறந்த மூல வன்பொருள் சாதனம் உருவாக்கலாம்.",
        feasibilityScore = 91
      )
    )
  }

  private fun getDefaultIdeaGraveyard(): List<IdeaGraveyardItem> {
    return listOf(
      IdeaGraveyardItem(
        originalIdeaTitleTa = "மொபைல் போனில் 3D ஹாலோகிராபிக் வாய்ஸ் அசிஸ்டெண்ட்",
        archivedDate = "6 மாதங்களுக்கு முன்பு (பிப்ரவரி 2026)",
        originalBlockerReasonTa = "அப்போது மொபைலில் ஆன்-டிவைஸ் நியூரல் ரெண்டரிங் வேகம் குறைவாக இருந்தது.",
        whyPracticalNowTa = "இப்போது புதிய GPU-முடுக்கப்பட்ட Vulkan என்ஜின் மூலம் நிகழ்நேர 60fps ஹாலோகிராம் ரெண்டரிங் சாத்தியமாகியுள்ளது!",
        isRevived = false
      )
    )
  }
}
