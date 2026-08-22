package com.example.data

import android.util.Log
import com.example.BuildConfig
import com.example.model.AiGeneratedAdvisory
import com.example.model.LeaderPerformanceProfile
import com.example.model.PerformanceTier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object ExecutiveAiService {

  private const val TAG = "ExecutiveAiService"
  private const val MODEL_NAME = "gemini-3.5-flash"
  private const val GEMINI_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL_NAME:generateContent"

  private val httpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(45, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()

  /**
   * Generates a deep organizational performance assessment and strategic advisory for an office bearer.
   */
  suspend fun generateAdvisoryForLeader(leader: LeaderPerformanceProfile): AiGeneratedAdvisory = withContext(Dispatchers.IO) {
    val prompt = buildLeaderAdvisoryPrompt(leader)
    val apiKey = getApiKey()

    if (apiKey.isNotBlank()) {
      try {
        val responseJson = callGeminiRestApi(apiKey, prompt)
        val extractedText = extractTextFromGeminiResponse(responseJson)
        if (extractedText.isNotBlank()) {
          val parsedAdvisory = parseStructuredAdvisory(extractedText, leader)
          if (parsedAdvisory != null) {
            return@withContext parsedAdvisory
          }
        }
      } catch (e: Exception) {
        Log.w(TAG, "Gemini API call failed, switching to intelligent heuristic fallback: ${e.message}")
      }
    }

    // High-quality data-driven heuristic fallback in pure formal Tamil
    return@withContext generateHeuristicAdvisory(leader)
  }

  /**
   * Interactive AI chat assistance for administrators & leaders.
   */
  suspend fun askExecutiveAiAdvisor(
    userQuestion: String,
    selectedLeader: LeaderPerformanceProfile? = null
  ): String = withContext(Dispatchers.IO) {
    val contextInfo = if (selectedLeader != null) {
      """
      தற்போதைய நிர்வாகி விவரம்:
      பெயர்: ${selectedLeader.tamilName} (${selectedLeader.fullName})
      பொறுப்பு: ${selectedLeader.designation} (${selectedLeader.level.labelTamil})
      மாவட்டம்: ${selectedLeader.district} ${if (selectedLeader.unionOrCity.isNotBlank()) "• ${selectedLeader.unionOrCity}" else ""}
      செயல்திறன் ஸ்கோர்: ${selectedLeader.overallAiScore}/100 (${selectedLeader.tier.labelTamil})
      உறுப்பினர் சேர்க்கை: ${selectedLeader.memberEnrollmentActual}/${selectedLeader.memberEnrollmentTarget} (${selectedLeader.memberEnrollmentPct}%)
      நலவாரிய பதிவு: ${selectedLeader.welfareBoardActual}/${selectedLeader.welfareBoardTarget} (${selectedLeader.welfareEnrollmentPct}%)
      கூட்டங்கள்: ${selectedLeader.monthlyMeetingsHeld}/${selectedLeader.monthlyMeetingsTarget}
      """.trimIndent()
    } else {
      "பொதுவான தமிழ்நாடு பெயிண்டர்கள் முன்னேற்ற சங்க (TNPA²) மாநில மேலாண்மை சூழல்."
    }

    val prompt = """
      You are the Chief AI Strategic Advisor for the Tamil Nadu Painters Association (TNPA² - தமிழ்நாடு பெயிண்டர்கள் முன்னேற்ற சங்கம்).
      Respond in pure, formal, motivating and actionable Tamil.
      Give clear bullet points, practical field steps, organizational wisdom, and constructive encouragement.
      
      $contextInfo
      
      கேள்வி / ஆலோசனைக் கோரிக்கை:
      $userQuestion
      
      பதில் கட்டமைப்பு:
      1. நேரடி தெளிவான வழிகாட்டுதல்
      2. உடனடி களப்பணி உத்திகள் (Field Action Steps)
      3. நிர்வாகிகள் ஒருங்கிணைப்பு & விழிப்புணர்வு முறை
      4. எதிர்பார்க்கப்படும் வெற்றி இலக்கு
    """.trimIndent()

    val apiKey = getApiKey()
    if (apiKey.isNotBlank()) {
      try {
        val responseJson = callGeminiRestApi(apiKey, prompt)
        val extractedText = extractTextFromGeminiResponse(responseJson)
        if (extractedText.isNotBlank()) {
          return@withContext extractedText
        }
      } catch (e: Exception) {
        Log.w(TAG, "Gemini Chat API call error: ${e.message}")
      }
    }

    // Heuristic response based on question keywords
    return@withContext generateHeuristicChatResponse(userQuestion, selectedLeader)
  }

  // ==========================================================================
  // GEMINI REST API PROTOCOL
  // ==========================================================================

  private fun getApiKey(): String {
    return try {
      val key = BuildConfig.GEMINI_API_KEY
      if (key == "DEFAULT_GEMINI_KEY" || key.isBlank()) "" else key
    } catch (e: Exception) {
      ""
    }
  }

  private fun callGeminiRestApi(apiKey: String, prompt: String): String {
    val url = "$GEMINI_ENDPOINT?key=$apiKey"

    val requestJson = JSONObject().apply {
      val contentsArray = JSONArray().apply {
        val contentObj = JSONObject().apply {
          val partsArray = JSONArray().apply {
            put(JSONObject().put("text", prompt))
          }
          put("parts", partsArray)
        }
        put(contentObj)
      }
      put("contents", contentsArray)

      val generationConfig = JSONObject().apply {
        put("temperature", 0.3)
        put("topP", 0.85)
        put("topK", 40)
      }
      put("generationConfig", generationConfig)

      val systemInstruction = JSONObject().apply {
        val parts = JSONArray().apply {
          put(JSONObject().put("text", "You are the Senior Organizational AI Advisor for the Tamil Nadu Painters Association (TNPA). Always write high quality, professional, empowering Tamil."))
        }
        put("parts", parts)
      }
      put("systemInstruction", systemInstruction)
    }

    val requestBody = requestJson.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
    val request = Request.Builder()
      .url(url)
      .post(requestBody)
      .build()

    val response = httpClient.newCall(request).execute()
    if (!response.isSuccessful) {
      throw RuntimeException("Gemini HTTP Error ${response.code}: ${response.message}")
    }

    return response.body?.string().orEmpty()
  }

  private fun extractTextFromGeminiResponse(jsonString: String): String {
    if (jsonString.isBlank()) return ""
    try {
      val root = JSONObject(jsonString)
      val candidates = root.optJSONArray("candidates") ?: return ""
      if (candidates.length() == 0) return ""
      val firstCandidate = candidates.getJSONObject(0)
      val content = firstCandidate.optJSONObject("content") ?: return ""
      val parts = content.optJSONArray("parts") ?: return ""
      val stringBuilder = StringBuilder()
      for (i in 0 until parts.length()) {
        val part = parts.getJSONObject(i)
        stringBuilder.append(part.optString("text", ""))
      }
      return stringBuilder.toString().trim()
    } catch (e: Exception) {
      Log.e(TAG, "Error parsing Gemini response json", e)
      return ""
    }
  }

  // ==========================================================================
  // PROMPT BUILDER & PARSER
  // ==========================================================================

  private fun buildLeaderAdvisoryPrompt(leader: LeaderPerformanceProfile): String {
    return """
      கீழே கொடுக்கப்பட்டுள்ள தமிழ்நாடு பெயிண்டர்கள் முன்னேற்ற சங்க (TNPA²) நிர்வாகியின் செயல்பாடுகளை ஆய்வு செய்து முழுமையான தமிழ் ஆலோசனை அறிக்கையை உருவாக்குங்கள்.
      
      நிர்வாகி விவரங்கள்:
      - பெயர்: ${leader.tamilName} (${leader.fullName})
      - பதவி: ${leader.designation} (${leader.level.labelTamil})
      - மாவட்டம்: ${leader.district}
      - ஒன்றியம்/நகரம்: ${if (leader.unionOrCity.isBlank()) "தலைமையகம்" else leader.unionOrCity}
      - தொடர்பு: ${leader.mobile}
      - உறுப்பினர் சேர்க்கை: இலக்கு ${leader.memberEnrollmentTarget} | சாதனை ${leader.memberEnrollmentActual} (${leader.memberEnrollmentPct}%)
      - நலவாரிய அட்டை பதிவு: இலக்கு ${leader.welfareBoardTarget} | சாதனை ${leader.welfareBoardActual} (${leader.welfareEnrollmentPct}%)
      - மாதாந்திர நிர்வாகிகள் கூட்டங்கள்: இலக்கு ${leader.monthlyMeetingsTarget} | நடத்தியவை ${leader.monthlyMeetingsHeld} (வருகை ${leader.meetingAttendancePct}%)
      - குறைகள் தீர்ப்பு: பெறப்பட்டவை ${leader.grievancesReceived} | தீர்க்கப்பட்டவை ${leader.grievancesResolved} (${leader.grievanceResolutionPct}%)
      - சந்தா வசூல் ஒழுங்கு: ${leader.subscriptionCollectionPct}%
      - பொது மாநாடு பங்கேற்பு: ${leader.conferenceAttendanceRate}%
      - வேலைவாய்ப்பு உதவி: ${leader.jobPlacementsAssisted} தொழிலாளர்கள்
      - விழிப்புணர்வு முகாம்கள்: ${leader.awarenessCampsConducted}
      - தற்போதைய AI செயல்திறன் ஸ்கோர்: ${leader.overallAiScore}/100 (${leader.tier.labelTamil})
      
      கீழ்கண்ட வடிவில் துல்லியமான தமிழ் அறிக்கை வழங்கவும் (Markdown formatting பயன்படுத்தவும்):
      
      ### [சுருக்கம்]
      (நிர்வாகியின் நடப்பு மாத ஒட்டுமொத்த செயல்பாட்டின் சுருக்கமான மதிப்பீடு - 3 வரிகள்)
      
      ### [முக்கிய பலங்கள்]
      - (பலம் 1)
      - (பலம் 2)
      - (பலம் 3)
      
      ### [உடனடி செயல் திட்டம்]
      - (நடவடிக்கை 1)
      - (நடவடிக்கை 2)
      - (நடவடிக்கை 3)
      
      ### [உறுப்பினர் பெருக்க உத்திகள்]
      - (உறுப்பினர் சேர்க்கை அதிகரிப்பு உத்தி 1)
      - (உறுப்பினர் சேர்க்கை அதிகரிப்பு உத்தி 2)
      
      ### [நலவாரிய பதிவு வழிகாட்டுதல்]
      - (நலவாரிய அட்டை விழிப்புணர்வு & பதிவு திட்டம் 1)
      - (நலவாரிய அட்டை திட்டம் 2)
      
      ### [வாராந்திர மைல்கல் இலக்குகள்]
      - வாரம் 1: (இலக்கு 1)
      - வாரம் 2: (இலக்கு 2)
      - வாரம் 3: (இலக்கு 3)
      - வாரம் 4: (இலக்கு 4)
      
      ### [தலைமைக்கான குறிப்பு]
      (மாநில தலைவர் & பொதுச் செயலாளர் கவனத்திற்கான பிரத்யேக நிர்வாக பரிந்துரை)
    """.trimIndent()
  }

  private fun parseStructuredAdvisory(rawText: String, leader: LeaderPerformanceProfile): AiGeneratedAdvisory? {
    try {
      var summary = ""
      val strengths = mutableListOf<String>()
      val actionPlan = mutableListOf<String>()
      val memberGrowth = mutableListOf<String>()
      val welfarePush = mutableListOf<String>()
      val weeklyRoadmap = mutableListOf<String>()
      var adminBriefing = ""

      var currentSection = ""

      val lines = rawText.lines()
      for (line in lines) {
        val trimmed = line.trim()
        if (trimmed.startsWith("###") || trimmed.startsWith("**[")) {
          val lower = trimmed.lowercase(Locale.ROOT)
          currentSection = when {
            lower.contains("சுருக்கம்") -> "summary"
            lower.contains("பலம்") || lower.contains("பலங்கள்") -> "strengths"
            lower.contains("செயல் திட்டம்") || lower.contains("உடனடி") -> "action"
            lower.contains("உறுப்பினர்") -> "member"
            lower.contains("நலவாரிய") -> "welfare"
            lower.contains("வாராந்திர") || lower.contains("மைல்கல்") -> "weekly"
            lower.contains("தலைமை") || lower.contains("குறிப்பு") -> "briefing"
            else -> ""
          }
          continue
        }

        if (trimmed.isBlank()) continue

        val cleanBullet = trimmed.removePrefix("-").removePrefix("*").removePrefix("•").trim()

        when (currentSection) {
          "summary" -> summary = if (summary.isEmpty()) cleanBullet else "$summary $cleanBullet"
          "strengths" -> if (cleanBullet.isNotBlank()) strengths.add(cleanBullet)
          "action" -> if (cleanBullet.isNotBlank()) actionPlan.add(cleanBullet)
          "member" -> if (cleanBullet.isNotBlank()) memberGrowth.add(cleanBullet)
          "welfare" -> if (cleanBullet.isNotBlank()) welfarePush.add(cleanBullet)
          "weekly" -> if (cleanBullet.isNotBlank()) weeklyRoadmap.add(cleanBullet)
          "briefing" -> adminBriefing = if (adminBriefing.isEmpty()) cleanBullet else "$adminBriefing $cleanBullet"
        }
      }

      if (summary.isNotBlank() || strengths.isNotEmpty() || actionPlan.isNotEmpty()) {
        return AiGeneratedAdvisory(
          executiveSummaryTamil = summary.ifBlank { "${leader.tamilName} அவர்களின் செயல்பாடுகள் ஆய்வு செய்யப்பட்டு ஆலோசனைகள் தொகுக்கப்பட்டுள்ளன." },
          strengthsReview = strengths.ifEmpty { leader.keyStrengths },
          priorityActionPlan = actionPlan.ifEmpty { listOf("ஒன்றிய/நகர அளவில் வாராந்திர உறுப்பினர்கள் சந்திப்பு நடத்துதல்", "நலவாரிய அட்டை நிலுவைகளை 10 நாட்களில் முடித்தல்") },
          memberGrowthStrategy = memberGrowth.ifEmpty { listOf("பெயிண்டிங் கடைகள் மற்றும் கட்டுமான தளங்களில் உறுப்பினர் விண்ணப்ப முகாம் அமைத்தல்") },
          welfareSchemePush = welfarePush.ifEmpty { listOf("TNUWWB & இ-ஷ்ரம் (e-Shram) திட்டங்களில் அனைத்து உறுப்பினர்களையும் பதிவு செய்தல்") },
          weeklyMilestones = weeklyRoadmap.ifEmpty { listOf("வாரம் 1: கிளை நிர்வாகிகள் கலந்தாய்வு", "வாரம் 2: நலவாரிய சிறப்பு முகாம்", "வாரம் 3: உறுப்பினர் சேர்க்கை ஆய்வு", "வாரம் 4: மாதாந்திர அறிக்கை சமர்ப்பித்தல்") },
          superAdminBriefingNote = adminBriefing.ifBlank { "நிர்வாகியின் செயல்பாடுகள் சீராக உள்ளன. அடுத்த மாத இலக்குகளை கண்காணிக்க பரிந்துரைக்கப்படுகிறது." }
        )
      }
    } catch (e: Exception) {
      Log.e(TAG, "Error in parsing structured advisory", e)
    }
    return null
  }

  // ==========================================================================
  // DATA-DRIVEN HEURISTIC ADVISORY (ACCURATE & PROFESSIONAL TAMIL)
  // ==========================================================================

  fun generateHeuristicAdvisory(leader: LeaderPerformanceProfile): AiGeneratedAdvisory {
    val tier = leader.tier
    val locationLabel = if (leader.unionOrCity.isNotBlank()) "${leader.district} (${leader.unionOrCity})" else leader.district

    val summary = when (tier) {
      PerformanceTier.EXEMPLARY -> "${leader.tamilName} அவர்கள் $locationLabel பகுதியில் முன்மாதிரியான சிறந்த தலைமையுடன் செயல்பட்டு வருகிறார். உறுப்பினர் சேர்க்கை மற்றும் நலவாரியப் பதிவு இலக்குகளை 90%க்கும் மேல் நிறைவேற்றி மாநில அளவில் முன்னிலையில் உள்ளார்."
      PerformanceTier.EXCELLENT -> "${leader.tamilName} அவர்கள் $locationLabel பொறுப்பில் சீரான முன்னேற்றத்துடன் சங்கப் பணிகளை ஆற்றி வருகிறார். மாதாந்திர கூட்டங்கள் மற்றும் குறைகள் தீர்ப்பில் சிறந்த பங்களிப்பை வழங்கியுள்ளார்."
      PerformanceTier.GOOD -> "${leader.tamilName} அவர்களின் செயல்பாடுகள் திருப்திகரமாக உள்ளன. நிர்ணயிக்கப்பட்ட உறுப்பினர் சேர்க்கை மற்றும் நலவாரிய இலக்குகளை எட்ட கூடுதல் களப்பணி தேவைப்படுகிறது."
      PerformanceTier.NEEDS_ATTENTION -> "$locationLabel பகுதியில் நிர்வாக செயல்பாடுகளை விரைவுபடுத்த வேண்டியுள்ளது. குறிப்பாக உறுப்பினர் சேர்க்கை மற்றும் மாதாந்திர நிர்வாக கூட்டங்களில் கூடுதல் கவனம் செலுத்த வேண்டும்."
      PerformanceTier.CRITICAL -> "$locationLabel பகுதியில் சங்க செயல்பாடுகள் பின்தங்கியுள்ளன. மாவட்ட மற்றும் மாநில தலைமை உடனடியாக தலையிட்டு புதிய செயல் திட்டத்தை அமல்படுத்த வேண்டியுள்ளது."
    }

    val strengths = mutableListOf<String>()
    if (leader.memberEnrollmentPct >= 80) strengths.add("உறுப்பினர் சேர்க்கை இலக்கில் ${leader.memberEnrollmentPct}% சாதனை படைத்துள்ளது.")
    if (leader.welfareEnrollmentPct >= 75) strengths.add("நலவாரிய அட்டை பதிவில் தீவிர விழிப்புணர்வு ஏற்படுத்தி ${leader.welfareBoardActual} தொழிலாளர்களுக்கு உதவியுள்ளார்.")
    if (leader.meetingAttendancePct >= 85) strengths.add("நிர்வாகிகள் மாதாந்திர கூட்டங்களில் ${leader.meetingAttendancePct}% வருகைப் பதிவுடன் நல்ல ஒருங்கிணைப்பு உள்ளது.")
    if (leader.grievanceResolutionPct >= 80) strengths.add("தொழிலாளர்களின் புகார்களில் ${leader.grievancesResolved} புகார்களுக்கு துரித தீர்வு கண்டுள்ளார்.")
    if (leader.jobPlacementsAssisted > 5) strengths.add("${leader.jobPlacementsAssisted} சக பெயிண்டர் தொழிலாளர்களுக்கு வேலைவாய்ப்பு பெற்று தந்துள்ளார்.")
    if (strengths.isEmpty()) strengths.add("சங்க விதிமுறைகளை பின்பற்றி நிர்வாகப் பொறுப்பைத் தொடர்ந்து வருகிறார்.")

    val actionPlan = mutableListOf<String>()
    if (leader.memberEnrollmentPct < 80) {
      actionPlan.add("பகுதியில் உள்ள முன்னணி பெயிண்ட் டீலர்கள் மற்றும் கடைகளில் சங்கம் சார்பில் உறுப்பினர் சேர்க்கை தகவல் பலகை வைத்தல்.")
    }
    if (leader.welfareEnrollmentPct < 80) {
      actionPlan.add("ஒவ்வொரு ஞாயிற்றுக்கிழமையும் இலவச நலவாரிய இணையதள பதிவு முகாம் (TNUWWB & e-Shram Camp) ஏற்பாடு செய்தல்.")
    }
    if (leader.monthlyMeetingsHeld < leader.monthlyMeetingsTarget) {
      actionPlan.add("மாதத்திற்கு தவறாமல் 4 வாராந்திர ஆலோசனைக் கூட்டங்களை நடத்தி பதிவேட்டில் பதிவு செய்தல்.")
    }
    actionPlan.add("இளைஞரணி நிர்வாகிகளை களமிறக்கி 18-35 வயதுடைய இளம் பெயிண்டர்களை சங்கத்தில் இணைத்தல்.")
    actionPlan.add("மாவட்ட மற்றும் மாநில தலைமை நடத்தும் வீடியோ கான்பிரன்ஸ் கூட்டங்களில் 100% தவறாமல் பங்கேற்றல்.")

    val memberGrowth = listOf(
      "பெரிய கட்டுமானத் தளங்கள் (Construction Sites) மற்றும் அபார்ட்மெண்ட் திட்டங்களுக்கு நேரில் சென்று சங்கத்தின் மருத்துவ உதவி & காப்பீடு பலன்களை விளக்குதல்.",
      "அங்கீகரிக்கப்பட்ட அடையாள அட்டை (TNPA ID Card) பெறுவதன் முக்கியத்துவத்தை விளக்கி புதிய விண்ணப்பங்களை சேகரித்தல்.",
      "முதியோர் ஓய்வூதியம் மற்றும் விபத்து நிவாரண வெற்றிக் கதைகளை துண்டுப் பிரசுரங்கள் வழியாக பகிர்தல்."
    )

    val welfarePush = listOf(
      "தமிழ்நாடு கட்டுமானத் தொழிலாளர்கள் நலவாரியத்தின் ₹5,00,000 விபத்து காப்பீடு மற்றும் ₹1,000 மாத ஓய்வூதிய நன்மைகளை எடுத்துரைத்தல்.",
      "குடும்பக் கல்வி உதவித்தொகை (10ம் & 12ம் வகுப்பு மாணவ மாணவிகளுக்கு ₹1,000 முதல் ₹8,000 வரை) விண்ணப்பிக்க சிறப்பு உதவி மையம் அமைத்தல்.",
      "இ-ஷ்ரம் (e-Shram) தேசிய போர்ட்டலில் ஆதார் இணைக்கப்பட்ட மொபைல் வழியாக 10 நிமிடத்தில் ஆன்லைன் பதிவு வழிகாட்டல்."
    )

    val weeklyRoadmap = listOf(
      "வாரம் 1: ஒன்றிய/நகர செயற்குழு கூட்டம் கூட்டி உறுப்பினர்கள் நிலுவை பட்டியல் ஆய்வு செய்தல்.",
      "வாரம் 2: முன்னணி பெயிண்ட் ஷாப்களில் சிறப்பு விழிப்புணர்வு & புதிய உறுப்பினர் சேர்க்கை முகாம்.",
      "வாரம் 3: நலவாரிய அட்டை விண்ணப்பங்களை அரசு இணையதளத்தில் சமர்ப்பித்து ரசீது வழங்குதல்.",
      "வாரம் 4: மாத சாதனை அறிக்கையை மாவட்டச் செயலாளரிடம் சமர்ப்பித்து அடுத்த மாத இலக்கு நிர்ணயித்தல்."
    )

    val briefing = when (tier) {
      PerformanceTier.EXEMPLARY, PerformanceTier.EXCELLENT -> "மாநில தலைமைக்கு: ${leader.tamilName} அவர்களின் உழைப்பு பாராட்டுக்குரியது. இவரை மண்டல அளவிலான பிற ஒன்றியங்களுக்கு வழிகாட்டியாக (Mentor) நியமிக்கலாம்."
      PerformanceTier.GOOD -> "மாநில தலைமைக்கு: நிர்வாகிக்கு தேவையான நலவாரிய படிவங்கள் மற்றும் அடையாள அட்டை ஸ்டாக்குகளை விரைந்து வழங்கினால் செயல்பாடு மேலும் உயரும்."
      else -> "மாநில தலைமைக்கு: மாவட்டச் செயலாளர் நேரடியாக நேரில் சென்று ஒன்றிய நிர்வாகிகளுடன் சிறப்பு ஆய்வு கூட்டம் நடத்த உடனடி உத்தரவிட பரிந்துரைக்கப்படுகிறது."
    }

    return AiGeneratedAdvisory(
      executiveSummaryTamil = summary,
      strengthsReview = strengths,
      priorityActionPlan = actionPlan,
      memberGrowthStrategy = memberGrowth,
      welfareSchemePush = welfarePush,
      weeklyMilestones = weeklyRoadmap,
      superAdminBriefingNote = briefing
    )
  }

  private fun generateHeuristicChatResponse(
    question: String,
    selectedLeader: LeaderPerformanceProfile?
  ): String {
    val q = question.lowercase(Locale.ROOT)
    return when {
      q.contains("உறுப்பினர்") || q.contains("சேர்க்கை") || q.contains("member") -> {
        """
        🎯 **உறுப்பினர் சேர்க்கையை விரைவுபடுத்துவதற்கான AI செயல் உத்திகள்:**
        
        1. **பெயிண்ட் டீலர்கள் முகாம்:** உங்கள் பகுதியில் உள்ள பெயிண்ட் கடைகளில் உரிமையாளர்களின் அனுமதியுடன் 'TNPA உறுப்பினர் சேர்க்கை படிவங்கள்' மற்றும் கையேடுகளை வைக்கவும்.
        2. **தளப் பார்வை (Site Visits):** பெரிய பில்டிங் தளங்களுக்கு காலை 8:30 அல்லது மாலை 5:30 மணிக்கு நேரில் சென்று சங்கத்தின் சட்ட உதவி & மருத்துவ காப்பீடு பலன்களை விளக்குங்கள்.
        3. **டிஜிட்டல் அடையாள அட்டை:** TNPA செயலி மூலம் உடனடியாக உருவாக்கப்படும் வண்ண அடையாள அட்டையை புதியவர்களுக்கு காண்பித்து நம்பிக்கை ஏற்படுத்துங்கள்.
        4. **இலக்கு:** ஒவ்வொரு நிர்வாகியும் வாரத்திற்கு குறைந்தபட்சம் 10 புதிய உறுப்பினர்களை இணைக்க இலக்கு நிர்ணயிக்கவும்.
        """.trimIndent()
      }
      q.contains("நலவாரிய") || q.contains("welfare") || q.contains("அட்டை") -> {
        """
        📋 **நலவாரிய பதிவு (TNUWWB & e-Shram) விழிப்புணர்வு வழிகாட்டி:**
        
        1. **தேவையான ஆவணங்கள் செக்லிஸ்ட்:** ஆதார் அட்டை நகல், குடும்ப அட்டை, வங்கி பாஸ்புக், பாஸ்போர்ட் அளவு புகைப்படம், தொழிலாளர் சான்றிதழ்.
        2. **ஞாயிறு உதவி முகாம்:** பொது இடங்களில் அல்லது சமுதாயக் கூடத்தில் மடிக்கணினி/ஸ்மார்ட்போன் மூலம் ஆன்லைன் பதிவு முகாம் நடத்துங்கள்.
        3. **அரசு சலுகைகள் விளக்கம்:**
           - இயற்கையான மரணம்: ₹50,000
           - விபத்து மரண உதவி: ₹5,00,000
           - பெண் குழந்தைகள் திருமண உதவி: ₹20,000 வரை
           - மாத ஓய்வூதியம்: ₹1,000
        4. விண்ணப்பித்த 15 நாட்களுக்குள் ஒப்புதல் நிலையை சரிபார்த்து தொழிலாளருக்கு தகவல் தெரிவிக்கவும்.
        """.trimIndent()
      }
      q.contains("கூட்டம்") || q.contains("meeting") || q.contains("வருகை") -> {
        """
        👥 **நிர்வாகிகள் மற்றும் உறுப்பினர்கள் கூட்டங்களை வெற்றிகரமாக நடத்தும் வழிமுறைகள்:**
        
        1. **முன்னறிவிப்பு (Advance Notice):** கூட்டத்திற்கு 3 நாட்களுக்கு முன்பே வாட்ஸ்அப் குழு மற்றும் தொலைபேசி அழைப்பு மூலம் நேரம் மற்றும் இடத்தை உறுதி செய்யவும்.
        2. **நேர மேலாண்மை:** கூட்டத்தை 45 நிமிடங்களுக்குள் திட்டமிட்டு தெளிவான நிகழ்ச்சி நிரலுடன் (Agenda) நடத்துங்கள்.
        3. **தீர்மானங்கள் & நடவடிக்கை:** கூட்டத்தில் விவாதிக்கப்பட்ட விஷயங்களை தீர்மானப் புத்தகத்தில் பதிவு செய்து மாவட்ட தலைமைக்கு அனுப்பவும்.
        4. **பாராட்டு முறை:** கடந்த மாதத்தில் அதிக உறுப்பினர்களை சேர்த்த நிர்வாகியை கூட்டத்தில் சால்வை அணிவித்து பாராட்டவும்.
        """.trimIndent()
      }
      else -> {
        """
        🤖 **TNPA AI தலைமை ஆலோசனைக் குறிப்பு:**
        
        - **களப்பணி முன்னுரிமை:** நிர்வாகத்தின் வெற்றி களத்தில் தொழிலாளர்களோடு நிற்பதிலேயே உள்ளது.
        - **ஒற்றுமை & தகவல் தொடர்பு:** ஒன்றியம் முதல் மாநிலம் வரை தினசரி தகவல் பரிமாற்றத்தை உறுதி செய்யுங்கள்.
        - **தொழிலாளர் குறைதீர்ப்பு:** எந்தவொரு பெயிண்டர் தொழிலாளிக்கும் வேலைத்தளத்தில் பிரச்சனை ஏற்பட்டால் முதல் நபராக சென்று உதவவும்.
        - **செயல்திறன் கண்காணிப்பு:** ஒவ்வொரு மாதமும் 25-ஆம் தேதிக்குள் தங்கள் பகுதி சாதனை அறிக்கையை செயலியில் புதுப்பியுங்கள்.
        
        _மேலும் குறிப்பிட்ட விவரங்களுக்கு 'உறுப்பினர் சேர்க்கை உத்தி', 'நலவாரிய முகாம்' அல்லது 'கூட்டங்கள் மேலாண்மை' என வினவலாம்._
        """.trimIndent()
      }
    }
  }
}
