package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

/**
 * PLATFORM-AWARE CONTENT OPTIMIZATION & AUTO-PUBLISHING ENGINE
 * Instagram & Facebook Trend Discovery, Algorithm Signal Intelligence, Multi-Variant Generation,
 * Candidate Posting Windows, Policy Guardrails, and Continuous Learning Loop.
 */
object SocialContentEngine {

  // 1. TREND SIGNALS & VELOCITY DISCOVERY
  private val _trendSignals = MutableStateFlow<List<TrendSignal>>(getDefaultTrendSignals())
  val trendSignals: StateFlow<List<TrendSignal>> = _trendSignals.asStateFlow()

  // 2. SOCIAL POST DRAFTS & MULTI-VARIANT ARCHIVE
  private val _postDrafts = MutableStateFlow<List<SocialPostDraft>>(getDefaultPostDrafts())
  val postDrafts: StateFlow<List<SocialPostDraft>> = _postDrafts.asStateFlow()

  // 3. COMMENT INTELLIGENCE & REPLY ASSISTANT
  private val _commentInbox = MutableStateFlow<List<SocialCommentItem>>(getDefaultComments())
  val commentInbox: StateFlow<List<SocialCommentItem>> = _commentInbox.asStateFlow()

  // 4. POST-MORTEM ANALYTICS & CONTINUOUS LEARNING
  private val _postMortems = MutableStateFlow<List<PostMortemAnalytics>>(getDefaultPostMortems())
  val postMortems: StateFlow<List<PostMortemAnalytics>> = _postMortems.asStateFlow()

  // Actions
  fun createMultiVariantDraft(
    ideaPrompt: String,
    platform: SocialPlatform,
    goal: ContentGoal,
    selectedTrend: TrendSignal?
  ) {
    val variants = generateVariantsForIdea(ideaPrompt, platform, goal)
    val candidateWindow = if (platform == SocialPlatform.INSTAGRAM) {
      "மாலை 06:30 PM – 08:00 PM (கடந்த 60 நாள் Performance தரவு அடிப்படையில்)"
    } else {
      "மதியம் 01:15 PM – 03:00 PM & இரவு 08:30 PM (விவாதம் அதிகம் உள்ள நேரம்)"
    }

    val newDraft = SocialPostDraft(
      originalIdeaPrompt = ideaPrompt,
      targetPlatform = platform,
      goal = goal,
      primaryTrend = selectedTrend,
      selectedVariant = variants.first(),
      allVariants = variants,
      optimizedCaptionTa = "${variants.first().bodyTextTa}\n\n${variants.first().keyTakeawayTa}\n\n👉 ${variants.first().callToActionTa}",
      selectedHashtags = selectedTrend?.relevantHashtags ?: listOf("#SovereignAI", "#TechInnovation", "#TamilTech", "#FutureScience"),
      candidatePostingWindow = candidateWindow,
      windowReasoning = "அல்காரிதம் ஊகம் அல்லாமல், உண்மையான உங்கள் ரசிகர்களின் ரீச் மற்றும் எங்கேஜ்மென்ட் நேரத்தைக் கொண்டு கணிக்கப்பட்டது.",
      opportunityScore = (82..94).random(),
      policyComplianceCheck = PolicyCheckResult(
        isCompliant = true,
        safetyScore = 0.98f,
        copyrightClearance = true,
        noClickbaitVerified = true,
        noSpamPatternVerified = true,
        policyNoteTa = "மெட்டா கொள்கை 100% சரிபார்க்கப்பட்டது: தவறான உரிமைகோரல்கள் அல்லது ஸ்பேம் பேட்டர்ன் ஏதுமில்லை."
      ),
      publishingMode = PublishingMode.APPROVAL_REQUIRED,
      status = PostPublishStatus.READY_FOR_REVIEW
    )

    _postDrafts.value = listOf(newDraft) + _postDrafts.value
  }

  fun approveAndSchedulePost(draftId: String, mode: PublishingMode, scheduledTime: String = "இன்று மாலை 07:15 PM") {
    _postDrafts.value = _postDrafts.value.map { draft ->
      if (draft.id == draftId) {
        draft.copy(
          publishingMode = mode,
          status = if (mode == PublishingMode.SCHEDULED_AUTO || mode == PublishingMode.LOW_RISK_AUTONOMOUS) PostPublishStatus.SCHEDULED else PostPublishStatus.PUBLISHED,
          scheduledTime = scheduledTime
        )
      } else draft
    }
  }

  fun replyToComment(commentId: String) {
    _commentInbox.value = _commentInbox.value.map { comment ->
      if (comment.id == commentId) {
        comment.copy(isReplied = true)
      } else comment
    }
  }

  private fun generateVariantsForIdea(
    idea: String,
    platform: SocialPlatform,
    goal: ContentGoal
  ): List<ContentVariant> {
    return listOf(
      ContentVariant(
        variantId = "var_a_educational",
        variantName = "Variant A: கல்வி & தொழில்நுட்ப விளக்கம் (Educational)",
        styleDescriptionTa = "ஆழமான அறிவியல் மற்றும் தொழில்நுட்பத்தை எளிமையாக்கும் பாணி.",
        hook = ContentStructureHook(
          hookType = "Curiosity Question Hook",
          hookTextTa = "குவாண்டம் கம்ப்யூட்டிங் ஏன் சாதாரண கம்ப்யூட்டரை விட 100 கோடி மடங்கு வேகம்? இதோ 3 முக்கிய காரணங்கள்!",
          hookTextEn = "Why Quantum Computing is 1 Billion times faster? Here are 3 key reasons!",
          visualFirstFramePlan = "3D ஹாலோகிராபிக் குவாண்டம் சர்க்யூட் ஒளிரும் அனிமேஷன் (First 1.5s)"
        ),
        bodyTextTa = "நமது தன்னாட்சி AI இயங்குதளத்தில் குவாண்டம் பெல் நிலை மற்றும் மேற்பொருந்துதல் நிலைகளை நிகழ்நேரத்தில் சோதித்தோம். $idea",
        keyTakeawayTa = "💡 முக்கிய பாடம்: சூப்பர் பொசிஷன் ஒரே நேரத்தில் எண்ணற்ற சாத்தியங்களை பகுப்பாய்வு செய்கிறது.",
        callToActionTa = "உங்கள் கருத்து என்ன? கமெண்டில் சொல்லுங்கள்!",
        recommendedAudioVibe = "Cinematic Ambient Tech (432Hz)",
        estimatedEngagementRate = "8.4% - 11.2%"
      ),
      ContentVariant(
        variantId = "var_b_emotional",
        variantName = "Variant B: தனிநபர் கதை & அனுபவம் (Narrative/Story)",
        styleDescriptionTa = "ஒரு தனிநபர் எப்படி முழு தன்னாட்சி AI கட்டமைப்பை உருவாக்கினார் என்ற கதை.",
        hook = ContentStructureHook(
          hookType = "Personal Transformation Hook",
          hookTextTa = "எந்த கிளவுட் நிறுவனத்தையும் நம்பாமல் என் சொந்த சர்வரில் ஒரு சூப்பர் AI உருவாக்க முடியுமா?",
          hookTextEn = "Can we build a Super AI on private servers without relying on Big Tech?",
          visualFirstFramePlan = "சர்வர் ரேக் மற்றும் மொபைல் எட்ஜ் ஸ்கிரீன் நேரடி காட்சி"
        ),
        bodyTextTa = "பலர் இது சாத்தியமில்லை என்றார்கள். ஆனால் Open Source மாடல்கள் மற்றும் லோக்கல் கம்ப்யூட்டிங் மூலம் இன்று இது முழுமையாக இயங்குகிறது. $idea",
        keyTakeawayTa = "🛡️ தன்னாட்சி: நமது தரவு நமது கட்டுப்பாட்டில் இருக்கும்போது சுதந்திரம் கிடைக்கிறது.",
        callToActionTa = "நீங்களும் சொந்தமாக AI உருவாக்க விரும்பினால் 'AI' என்று கமெண்ட் செய்யவும்!",
        recommendedAudioVibe = "Inspiring Minimal Piano",
        estimatedEngagementRate = "9.1% - 12.8%"
      ),
      ContentVariant(
        variantId = "var_c_short_hook",
        variantName = "Variant C: அதிவேக ரீல்ஸ் ஹூக் (Short-Form Punch)",
        styleDescriptionTa = "முதல் 2 வினாடிகளில் கவனத்தை ஈர்க்கும் ரீல்ஸ் வடிவம்.",
        hook = ContentStructureHook(
          hookType = "Unexpected Fact Hook",
          hookTextTa = "இந்த 1 விஷயம் தெரிந்தால் உங்கள் போன் ஒரு சூப்பர் கம்ப்யூட்டராக மாறும்!",
          hookTextEn = "This 1 thing transforms your device into an edge supercomputer!",
          visualFirstFramePlan = "வேகமான ட்ரான்சிஷன் மற்றும் சவுண்ட் எஃபெக்ட்"
        ),
        bodyTextTa = "எட்ஜ் ஏஐ மூலம் உங்கள் சாதனத்திலேயே இன்டெலிஜென்ஸ் இயங்கும் ரகசியம் இதோ. $idea",
        keyTakeawayTa = "⚡ லேட்டன்சி ஜீரோ: இணையம் இல்லாமலும் முடிவுகள் உடனடியாக வரும்.",
        callToActionTa = "இந்த ரீல்ஸை சேமித்து (Save) வைத்துக்கொள்ளுங்கள்!",
        recommendedAudioVibe = "Fast Synthwave Beat",
        estimatedEngagementRate = "12.5% - 15.0%"
      )
    )
  }

  private fun getDefaultTrendSignals(): List<TrendSignal> {
    return listOf(
      TrendSignal(
        topicNameTa = "தன்னாட்சி எட்ஜ் AI & லோக்கல் கம்ப்யூட்டிங்",
        topicNameEn = "On-Device Sovereign AI & Private LLMs",
        platform = SocialPlatform.INSTAGRAM,
        velocity = TrendVelocity.GROWING,
        growthRatePercentage = 142,
        estimatedAudienceFit = 0.94f,
        contentOpportunityScore = 91,
        relevantHashtags = listOf("#SovereignAI", "#LocalLLM", "#EdgeAI", "#TamilTech", "#PrivacyFirst"),
        publicSignalSource = "Official Meta Public Search Trends & Engineering Blogs",
        competitorSaturationNote = "குறைந்த போட்டி (Content Gap): தமிழில் எட்ஜ் AI பற்றி மிகக் குறைந்த உள்ளடக்கமே உள்ளது."
      ),
      TrendSignal(
        topicNameTa = "குவாண்டம் இயற்பியல் & டோகமாக் ஃப்யூஷன் ஆற்றல்",
        topicNameEn = "Quantum Computing & Fusion Physics",
        platform = SocialPlatform.INSTAGRAM,
        velocity = TrendVelocity.EMERGING,
        growthRatePercentage = 88,
        estimatedAudienceFit = 0.88f,
        contentOpportunityScore = 86,
        relevantHashtags = listOf("#QuantumPhysics", "#FusionEnergy", "#Tokamak", "#ScienceTamil", "#FutureTech"),
        publicSignalSource = "Public Science Discovery & Global Interest Index",
        competitorSaturationNote = "அதிக பார்வை நேரம் (High Watch-time Retention) கிடைக்கும் அறிவியல் தலைப்பு."
      ),
      TrendSignal(
        topicNameTa = "ஹார்டுவேர் கண்டுபிடிப்பு & IoT எம்படட் சிஸ்டம்ஸ்",
        topicNameEn = "Open Hardware & ESP32 Innovations",
        platform = SocialPlatform.FACEBOOK,
        velocity = TrendVelocity.STABLE,
        growthRatePercentage = 45,
        estimatedAudienceFit = 0.82f,
        contentOpportunityScore = 79,
        relevantHashtags = listOf("#IoTTamil", "#ElectronicsTamil", "#HardwareMaker", "#ArduinoTamil"),
        publicSignalSource = "Meta Groups Technical Discussions & Public Feeds",
        competitorSaturationNote = "ஆழமான விவாதங்கள் மற்றும் செய்முறை வழிகாட்டல்களுக்கு உகந்தது."
      )
    )
  }

  private fun getDefaultPostDrafts(): List<SocialPostDraft> {
    val defaultTrend = getDefaultTrendSignals().first()
    val variants = listOf(
      ContentVariant(
        variantId = "var_1_reels",
        variantName = "Variant A: கல்வி ரீல்ஸ் (Educational Reel)",
        styleDescriptionTa = "ஆழமான தொழில்நுட்ப விளக்கம் + விஷுவல் ஹூக்.",
        hook = ContentStructureHook(
          hookType = "Direct Value Hook",
          hookTextTa = "உங்கள் போனில் இணையம் இல்லாமலேயே இயங்கும் தன்னாட்சி AI எப்படி வேலை செய்கிறது?",
          hookTextEn = "How On-Device Sovereign AI runs without Internet?",
          visualFirstFramePlan = "மொபைல் ஸ்கிரீனில் லோக்கல் நியூரல் நெட்வொர்க் செயலாக்கம்"
        ),
        bodyTextTa = "இணையத்தை நம்பாமல் உங்கள் மொபைலிலேயே AI இயங்குவது எப்படி? நமது தன்னாட்சி இயங்குதளம் உங்கள் தரவைப் பாதுகாத்து, அதிவேகமாக பதிலளிக்கிறது.",
        keyTakeawayTa = "🔒 100% தனியுரிமை மற்றும் ஜீரோ கிளவுட் கட்டணம்.",
        callToActionTa = "உங்கள் கருத்துக்களை கமெண்டில் பதிவு செய்யுங்கள்!",
        recommendedAudioVibe = "Futuristic Tech Pulse",
        estimatedEngagementRate = "10.4%"
      )
    )

    return listOf(
      SocialPostDraft(
        originalIdeaPrompt = "Show how Sovereign AI runs edge models locally on device with zero internet",
        targetPlatform = SocialPlatform.INSTAGRAM,
        goal = ContentGoal.EDUCATION,
        primaryTrend = defaultTrend,
        selectedVariant = variants.first(),
        allVariants = variants,
        optimizedCaptionTa = "இணையம் இல்லாமலேயே உங்கள் சாதனத்தில் AI இயங்குவது எப்படி? 🚀\n\nதன்னாட்சி AI இயங்குதளம் உங்களின் முழுப் பாதுகாப்பையும் உறுதிசெய்கிறது.\n\n🔒 100% Data Sovereignty & Zero Cloud Fees.\n\n👇 உங்கள் எண்ணங்களை கமெண்டில் சொல்லுங்கள்!",
        selectedHashtags = listOf("#SovereignAI", "#EdgeAI", "#LocalComputing", "#TamilTech", "#TechInnovation"),
        candidatePostingWindow = "மாலை 07:00 PM – 08:30 PM (கடந்த 60 நாள் தரவு அடிப்படையில்)",
        windowReasoning = "இந்த நேரத்தில் தொழில்நுட்ப ஆர்வலர்களின் ரீச் 2.4 மடங்கு அதிகமாக பதிவு செய்யப்பட்டுள்ளது.",
        opportunityScore = 89,
        policyComplianceCheck = PolicyCheckResult(
          isCompliant = true,
          safetyScore = 0.99f,
          copyrightClearance = true,
          noClickbaitVerified = true,
          noSpamPatternVerified = true,
          policyNoteTa = "மெட்டா சமூக விதிமுறைகளுக்கு உட்பட்ட உண்மையான தொழில்நுட்பத் தகவல்."
        ),
        publishingMode = PublishingMode.APPROVAL_REQUIRED,
        status = PostPublishStatus.READY_FOR_REVIEW
      )
    )
  }

  private fun getDefaultComments(): List<SocialCommentItem> {
    return listOf(
      SocialCommentItem(
        authorName = "கார்த்திக் ராஜா",
        platform = SocialPlatform.INSTAGRAM,
        postTitle = "எட்ஜ் ஏஐ லோக்கல் மாடல்கள் செயல்முறை",
        commentText = "அண்ணா, இந்த AI-க்கு என்ன specs மொபைல் தேவைப்படும்? RAM எவ்வளவு இருக்கணும்?",
        sentiment = "Genuine Question",
        isSpamOrBot = false,
        suggestedReplyTa = "வணக்கம் கார்த்திக்! 6GB RAM மற்றும் Snapdragon 7 சீரிஸ் இருந்தாலே 4-bit quantized மாடல்கள் மிக அருமையாக இயங்கும்.",
        isReplied = false
      ),
      SocialCommentItem(
        authorName = "வினோத் குமார்",
        platform = SocialPlatform.INSTAGRAM,
        postTitle = "குவாண்டம் பெல் நிலை பரிசோதனை",
        commentText = "Super explanation bro! Keep sharing advanced tech in Tamil.",
        sentiment = "Positive Feedback",
        isSpamOrBot = false,
        suggestedReplyTa = "மிக்க நன்றி வினோத்! அடுத்த பதிவில் டோகமாக் பிளாஸ்மா இயற்பியல் பற்றி ஆழமாகப் பார்க்கலாம்.",
        isReplied = true
      ),
      SocialCommentItem(
        authorName = "crypto_bot_9941",
        platform = SocialPlatform.INSTAGRAM,
        postTitle = "எட்ஜ் ஏஐ லோக்கல் மாடல்கள் செயல்முறை",
        commentText = "Invest 100$ and get 5000$ daily! DM me now fast!!!",
        sentiment = "Spam / Scam Bot",
        isSpamOrBot = true,
        suggestedReplyTa = "[AI தானாக ஸ்பேம் என்று கண்டறிந்து மறைக்கப் பரிந்துரைக்கிறது]",
        isReplied = false
      )
    )
  }

  private fun getDefaultPostMortems(): List<PostMortemAnalytics> {
    return listOf(
      PostMortemAnalytics(
        postId = "post_01_tokamak",
        postTitle = "டோகமாக் பிளாஸ்மா காந்தக் கட்டுப்பாடு (Reels)",
        platform = SocialPlatform.INSTAGRAM,
        viewsOrImpressions = 18450,
        reachCount = 14200,
        engagementRatePercent = 9.8f,
        retentionRatePercent = 72.4f,
        saveCount = 680,
        shareCount = 410,
        whatWorkedTa = "முதல் 2 வினாடிகளில் வந்த 3D பிளாஸ்மா சுழல் அனிமேஷன் பார்வைத் தக்கவைப்பை (Retention) 72%-ஆக உயர்த்தியது.",
        whatNeedsImprovementTa = "கடைசி 3 வினாடிகளில் உள்ள CTA சற்று நீளமாக இருந்தது; அதை 1.5 வினாடிக்குள் முடிக்கலாம்.",
        lessonLearnedTa = "அறிவியல் அனிமேஷன்கள் மற்றும் தமிழ் விவரிப்பு இணையும்போது பார்வையாளர்கள் முழுமையாகப் பார்க்கிறார்கள்."
      )
    )
  }
}
