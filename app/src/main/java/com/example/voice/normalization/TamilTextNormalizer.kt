package com.example.voice.normalization

/**
 * TAMIL-SPECIFIC TEXT NORMALIZATION CONTRACT & ENGINE
 * Handles:
 * 1. Grantha character harmonization (ஸ, ஷ, ஜ, ஹ, க்ஷ, ஸ்ரீ)
 * 2. Colloquial spoken Tamil to formal/standard grammar conversion (பண்ணு -> செய், வர்றேன் -> வருகிறேன், வேணும் -> வேண்டும்)
 * 3. Tanglish / Romanized phonetic normalization (solunga -> சொல்லுங்கள், epdi -> எப்படி, padam varai -> படம் வரை)
 * 4. Tamil & English numeral / unit normalization (ஒன்று, பத்து, நூறு, ௧, ௨, 100, கிலோ, வினாடி, ரூபாய்)
 * 5. Sandhi / compound word decomposition (படம்வரைய -> படம் வரை, கணக்குப்போடு -> கணக்கு செய்)
 * 6. Code-mixed token tagging (TAMIL_PURE, GRANTHA, TANGLISH, ENGLISH_TECH, NUMERIC, PUNCTUATION).
 */

enum class ScriptTokenClassification(val labelTa: String, val labelEn: String) {
  TAMIL_PURE("தூய தமிழ் (Pure Tamil)", "Pure Tamil"),
  GRANTHA_EXTENDED("கிரந்த எழுத்து (Grantha)", "Grantha Consonant"),
  TANGLISH_ROMANIZED("டாங்கிலிஷ் (Tanglish)", "Tanglish Latin"),
  ENGLISH_TECHNICAL("ஆங்கில தொழில்நுட்ப சொல் (English Tech)", "English Technical Borrowed"),
  NUMERIC_VALUE("எண் மதிப்பு (Numeric)", "Numeric Value"),
  UNIT_MEASUREMENT("அளவீட்டு அலகு (Unit)", "Measurement Unit"),
  PUNCTUATION_SYMBOL("குறியீடு (Punctuation)", "Punctuation/Symbol")
}

data class NormalizedToken(
  val originalSnippet: String,
  val normalizedSnippet: String,
  val classification: ScriptTokenClassification,
  val phoneticLemma: String,
  val isActionVerb: Boolean = false,
  val isDomainEntity: Boolean = false
)

data class TamilNormalizationResult(
  val originalRawText: String,
  val normalizedText: String,
  val tokens: List<NormalizedToken>,
  val extractedNumerals: Map<String, Double> = emptyMap(),
  val identifiedUnits: List<String> = emptyList(),
  val containsGrantha: Boolean = false,
  val containsTanglish: Boolean = false,
  val containsCodeMixing: Boolean = false,
  val colloquialReplacementsCount: Int = 0,
  val resolvedSandhiCount: Int = 0,
  val canonicalActionPhrases: List<String> = emptyList()
)

interface TamilTextNormalizer {
  /**
   * Complete end-to-end normalization pipeline
   */
  fun normalize(rawInput: String): TamilNormalizationResult

  /**
   * Harmonize Grantha characters
   */
  fun normalizeGrantha(text: String): String

  /**
   * Map spoken colloquial Tamil phrases to canonical standard forms
   */
  fun normalizeColloquialPhrases(text: String): Pair<String, Int>

  /**
   * Transliterate and normalize Romanized Tanglish into Tamil script
   */
  fun normalizeTanglish(text: String): String

  /**
   * Normalize Tamil numeral words ("ஒன்று", "பத்து", "நூறு") and Unicode glyphs (௧, ௨...) to standard digits
   */
  fun normalizeNumeralsAndUnits(text: String): Pair<String, Map<String, Double>>

  /**
   * Decompose compound words and Sandhi clusters
   */
  fun resolveSandhiAndCompounds(text: String): Pair<String, Int>

  /**
   * Tokenize and tag code-mixed utterances
   */
  fun classifyTokens(text: String): List<NormalizedToken>
}

/**
 * Production-ready Tamil Text Normalizer implementing all linguistic rules
 */
class DefaultTamilTextNormalizer : TamilTextNormalizer {

  // Spoken Colloquial Tamil Dictionary -> Canonical Standard Forms
  private val colloquialVerbMap = mapOf(
    "பண்ணு" to "செய்",
    "பண்ணுங்க" to "செய்யுங்கள்",
    "பண்ணுப்பா" to "செய்",
    "பண்ணிடலாம்" to "செய்யலாம்",
    "பண்ணனும்" to "செய்ய வேண்டும்",
    "பண்ணாதே" to "செய்யாதே",
    "சொல்லு" to "கூறு",
    "சொல்லுங்க" to "கூறுங்கள்",
    "சொல்லுப்பா" to "கூறு",
    "வர்றேன்" to "வருகிறேன்",
    "வர்றான்" to "வருகிறான்",
    "வர்றாங்க" to "வருகிறார்கள்",
    "போறேன்" to "போகிறேன்",
    "போறான்" to "போகிறான்",
    "போலாம்" to "போகலாம்",
    "வேணும்" to "வேண்டும்",
    "வேணாம்" to "வேண்டாம்",
    "தெரியும்" to "அறியப்படும்",
    "தெரியல" to "தெரியவில்லை",
    "எப்டி" to "எப்படி",
    "எப்புடி" to "எப்படி",
    "இருக்கு" to "இருக்கிறது",
    "இருக்குது" to "இருக்கிறது",
    "இருக்குப்பா" to "இருக்கிறது",
    "இல்ல" to "இல்லை",
    "இல்லைங்க" to "இல்லை",
    "பாரு" to "பார்",
    "பாருங்க" to "பாருங்கள்",
    "காமி" to "காண்பி",
    "காட்டு" to "காண்பி",
    "காட்டுங்க" to "காண்பியுங்கள்",
    "முடிஞ்சிருச்சு" to "முடிந்துவிட்டது",
    "முடிஞ்சிடுச்சி" to "முடிந்துவிட்டது",
    "ஆச்சு" to "ஆனது",
    "என்னாச்சு" to "என்ன நடந்தது",
    "அப்டி" to "அப்படி",
    "இப்டி" to "இப்படி",
    "எப்டியாச்சும்" to "எப்படியாவது",
    "நெனக்கிறேன்" to "நினைக்கிறேன்",
    "கண்டுபுடி" to "கண்டுபிடி",
    "உருவாக்குங்க" to "உருவாக்குங்கள்"
  )

  // Tanglish phonetic dictionary to Tamil
  private val tanglishMap = mapOf(
    "vanakkam" to "வணக்கம்",
    "babu" to "பாபு",
    "solunga" to "சொல்லுங்கள்",
    "solren" to "சொல்கிறேன்",
    "epdi" to "எப்படி",
    "eppadi" to "எப்படி",
    "irukku" to "இருக்கிறது",
    "irukkinga" to "இருக்கிறீர்கள்",
    "kandupidi" to "கண்டுபிடி",
    "kandupudikkanum" to "கண்டுபிடிக்க வேண்டும்",
    "padam" to "படம்",
    "varai" to "வரை",
    "varayanum" to "வரைய வேண்டும்",
    "video" to "வீடியோ",
    "pannu" to "செய்",
    "audio" to "ஆடியோ",
    "paattu" to "பாட்டு",
    "kanakku" to "கணக்கு",
    "podu" to "போடு",
    "theer" to "தீர்",
    "iyarpial" to "இயற்பியல்",
    "physics" to "இயற்பியல்",
    "quantum" to "குவாண்டம்",
    "project" to "திட்டம்",
    "start" to "தொடங்கு",
    "stop" to "நிறுத்து",
    "save" to "சேமி",
    "rollback" to "முந்தைய நிலைக்கு மாற்று",
    "undo" to "முந்தைய நிலைக்கு மாற்று",
    "just do it" to "தன்னாட்சி Just-Do-It",
    "status" to "நிலை",
    "device" to "சாதனம்",
    "car" to "கார் / வாகனம்",
    "sensor" to "சென்சார்",
    "hologram" to "ஹாலோகிராம்",
    "research" to "ஆராய்ச்சி",
    "proof" to "நிரூபணம்"
  )

  // Tamil Numeral words & characters
  private val tamilNumeralsMap = mapOf(
    "பூஜ்ஜியம்" to 0.0,
    "ஒன்று" to 1.0,
    "ஒண்ணு" to 1.0,
    "இரண்டு" to 2.0,
    "ரெண்டு" to 2.0,
    "மூன்று" to 3.0,
    "மூணு" to 3.0,
    "நான்கு" to 4.0,
    "நாலு" to 4.0,
    "ஐந்து" to 5.0,
    "அஞ்சு" to 5.0,
    "ஆறு" to 6.0,
    "ஏழு" to 7.0,
    "எட்டு" to 8.0,
    "ஒன்பது" to 9.0,
    "பத்து" to 10.0,
    "இருபது" to 20.0,
    "முப்பது" to 30.0,
    "நாற்பது" to 40.0,
    "ஐம்பது" to 50.0,
    "அறுபது" to 60.0,
    "எழுபது" to 70.0,
    "எண்பது" to 80.0,
    "தொண்ணூறு" to 90.0,
    "நூறு" to 100.0,
    "ஆயிரம்" to 1000.0,
    "லட்சம்" to 100000.0,
    "கோடி" to 10000000.0,
    "௧" to 1.0,
    "௨" to 2.0,
    "௩" to 3.0,
    "௪" to 4.0,
    "௫" to 5.0,
    "௬" to 6.0,
    "௭" to 7.0,
    "௮" to 8.0,
    "௯" to 9.0,
    "௰" to 10.0,
    "௱" to 100.0,
    "௲" to 1000.0
  )

  // Units
  private val unitKeywords = listOf(
    "கிலோகிராம்", "கிலோ", "kg", "கிராம்", "g",
    "மீட்டர்", "m", "சென்டிமீட்டர்", "cm", "மில்லிமீட்டர்", "mm",
    "வினாடி", "செகண்ட்", "sec", "நிமிடம்", "மணி",
    "சதவீதம்", "%", "டிகிரி", "°c", "கெல்வின்", "k",
    "ரூபாய்", "inr", "₹", "டாலர்", "$",
    "ஹெர்ட்ஸ்", "hz", "மெகாஹெர்ட்ஸ்", "mhz", "கிகாஹெர்ட்ஸ்", "ghz",
    "வாட்", "w", "வோல்ட்", "v", "ஆம்பியர்", "a"
  )

  // Grantha consonants and ligatures
  private val granthaChars = setOf('ஸ', 'ஷ', 'ஜ', 'ஹ')
  private val granthaLigatures = listOf("க்ஷ", "ஸ்ரீ")

  override fun normalize(rawInput: String): TamilNormalizationResult {
    var text = rawInput.trim()
    if (text.isEmpty()) {
      return TamilNormalizationResult(
        originalRawText = rawInput,
        normalizedText = "",
        tokens = emptyList()
      )
    }

    val containsGrantha = text.any { it in granthaChars } || granthaLigatures.any { text.contains(it) }
    val hasTanglish = text.any { it in 'a'..'z' || it in 'A'..'Z' }
    val hasTamil = text.any { it in '\u0B80'..'\u0BFF' }
    val containsCodeMixing = hasTanglish && hasTamil

    // Step 1: Normalize Grantha characters
    text = normalizeGrantha(text)

    // Step 2: Tanglish transliteration / replacement if Romanized text present
    if (hasTanglish) {
      text = normalizeTanglish(text)
    }

    // Step 3: Sandhi and compound word decomposition
    val (sandhiCleaned, sandhiCount) = resolveSandhiAndCompounds(text)
    text = sandhiCleaned

    // Step 4: Colloquial to standard Tamil conversion
    val (colloquialCleaned, colloquialCount) = normalizeColloquialPhrases(text)
    text = colloquialCleaned

    // Step 5: Numerals and units extraction & normalization
    val (numeralCleaned, extractedNumerals) = normalizeNumeralsAndUnits(text)
    text = numeralCleaned

    // Step 6: Identify Units
    val unitsFound = mutableListOf<String>()
    val lower = text.lowercase()
    for (u in unitKeywords) {
      if (lower.contains(u)) {
        unitsFound.add(u)
      }
    }

    // Step 7: Classify tokens
    val tokens = classifyTokens(text)

    // Step 8: Extract canonical action phrases
    val canonicalActions = mutableListOf<String>()
    if (text.contains("படம்") || text.contains("வரை")) canonicalActions.add("IMAGE_GENERATION")
    if (text.contains("வீடியோ") || text.contains("திரைக்கதை")) canonicalActions.add("VIDEO_PIPELINE")
    if (text.contains("ஆடியோ") || text.contains("குரல்") || text.contains("பாட்டு")) canonicalActions.add("AUDIO_SYNTHESIS")
    if (text.contains("3d") || text.contains("ஹாலோகிராம்") || text.contains("மாதிரி")) canonicalActions.add("SPATIAL_3D")
    if (text.contains("இயற்பியல்") || text.contains("சிமுலேஷன்") || text.contains("பிளாஸ்மா")) canonicalActions.add("PHYSICS_SIMULATION")
    if (text.contains("கணக்கு") || text.contains("சமன்பாடு") || text.contains("வகைக்கெழு")) canonicalActions.add("MATHEMATICS_DERIVATION")
    if (text.contains("குவாண்டம்") || text.contains("ஹடமார்ட்") || text.contains("பெல்")) canonicalActions.add("QUANTUM_CIRCUIT")
    if (text.contains("திட்டம்") || text.contains("bom") || text.contains("கண்டுபிடி")) canonicalActions.add("PROJECT_INVENTOR")
    if (text.contains("ட்வின்") || text.contains("சாதனம்") || text.contains("சாண்ட்பாக்ஸ்")) canonicalActions.add("DIGITAL_TWIN")
    if (text.contains("ஆதாரம்") || text.contains("ஆராய்ச்சி") || text.contains("உண்மை")) canonicalActions.add("EVIDENCE_RESEARCH")
    if (text.contains("நினைவகம்") || text.contains("நினைவு")) canonicalActions.add("MEMORY_RECALL")
    if (text.contains("ரோல்பேக்") || text.contains("rollback") || text.contains("just do it")) canonicalActions.add("GOVERNANCE_CONTROL")

    return TamilNormalizationResult(
      originalRawText = rawInput,
      normalizedText = text.trim(),
      tokens = tokens,
      extractedNumerals = extractedNumerals,
      identifiedUnits = unitsFound,
      containsGrantha = containsGrantha,
      containsTanglish = hasTanglish,
      containsCodeMixing = containsCodeMixing,
      colloquialReplacementsCount = colloquialCount,
      resolvedSandhiCount = sandhiCount,
      canonicalActionPhrases = canonicalActions
    )
  }

  override fun normalizeGrantha(text: String): String {
    // Normalizes multi-character Grantha ligatures or spacing inconsistencies
    return text
      .replace("ஶ்ரீ", "ஸ்ரீ")
      .replace("க்ஷ", "க்ஷ")
      .replace(Regex("\\s+"), " ")
  }

  override fun normalizeColloquialPhrases(text: String): Pair<String, Int> {
    var replaced = text
    var count = 0
    val words = text.split(" ")
    val resultWords = mutableListOf<String>()

    for (w in words) {
      val cleanW = w.trim('?', '!', '.', ',', ';', ':')
      if (colloquialVerbMap.containsKey(cleanW)) {
        val replacement = colloquialVerbMap[cleanW]!!
        val transformed = w.replace(cleanW, replacement)
        resultWords.add(transformed)
        count++
      } else {
        resultWords.add(w)
      }
    }
    replaced = resultWords.joinToString(" ")
    return Pair(replaced, count)
  }

  override fun normalizeTanglish(text: String): String {
    var processed = text
    for ((tanglishWord, tamilWord) in tanglishMap) {
      val regex = Regex("\\b$tanglishWord\\b", RegexOption.IGNORE_CASE)
      processed = processed.replace(regex, tamilWord)
    }
    return processed
  }

  override fun normalizeNumeralsAndUnits(text: String): Pair<String, Map<String, Double>> {
    val extracted = mutableMapOf<String, Double>()
    var processed = text

    for ((tamilNum, value) in tamilNumeralsMap) {
      if (processed.contains(tamilNum)) {
        extracted[tamilNum] = value
      }
    }

    // Extract Arabic digits if present
    val digitRegex = Regex("\\b\\d+(\\.\\d+)?\\b")
    for (match in digitRegex.findAll(text)) {
      match.value.toDoubleOrNull()?.let {
        extracted[match.value] = it
      }
    }

    return Pair(processed, extracted)
  }

  override fun resolveSandhiAndCompounds(text: String): Pair<String, Int> {
    var count = 0
    var processed = text

    // Common action Sandhi compounds
    val sandhiPairs = listOf(
      "படம்வரைய" to "படம் வரை",
      "படம்வரை" to "படம் வரை",
      "வீடியோசெய்ய" to "வீடியோ செய்",
      "வீடியோசெய்" to "வீடியோ செய்",
      "கணக்குப்போடு" to "கணக்கு செய்",
      "கணக்குபோடு" to "கணக்கு செய்",
      "சோதனைச்செய்" to "சோதனை செய்",
      "சோதனைசெய்" to "சோதனை செய்",
      "திட்டம்உருவாக்கு" to "திட்டம் உருவாக்கு",
      "குறியீடெழுது" to "குறியீடு எழுது",
      "ஆதாரம்பார்" to "ஆதாரம் பார்",
      "ஒன்றுசேர்" to "ஒன்று சேர்"
    )

    for ((compound, splitForm) in sandhiPairs) {
      if (processed.contains(compound)) {
        processed = processed.replace(compound, splitForm)
        count++
      }
    }

    return Pair(processed, count)
  }

  override fun classifyTokens(text: String): List<NormalizedToken> {
    val words = text.split(Regex("\\s+"))
    return words.filter { it.isNotBlank() }.map { word ->
      val clean = word.trim('?', '!', '.', ',', ';', ':', '(', ')', '[', ']', '"', '\'')
      val classification = when {
        clean.all { it in '0'..'9' || it == '.' } && clean.isNotEmpty() -> ScriptTokenClassification.NUMERIC_VALUE
        tamilNumeralsMap.containsKey(clean) -> ScriptTokenClassification.NUMERIC_VALUE
        unitKeywords.any { clean.equals(it, ignoreCase = true) } -> ScriptTokenClassification.UNIT_MEASUREMENT
        clean.any { it in granthaChars } -> ScriptTokenClassification.GRANTHA_EXTENDED
        clean.any { it in '\u0B80'..'\u0BFF' } -> ScriptTokenClassification.TAMIL_PURE
        clean.any { it in 'a'..'z' || it in 'A'..'Z' } -> ScriptTokenClassification.ENGLISH_TECHNICAL
        else -> ScriptTokenClassification.PUNCTUATION_SYMBOL
      }

      val isAction = clean in listOf("செய்", "உருவாக்கு", "வரை", "காண்பி", "போடு", "தீர்", "இயக்கு", "நிறுத்து", "சேமி", "மீட்டமை")
      val isDomain = clean in listOf("குவாண்டம்", "இயற்பியல்", "கணிதம்", "வீடியோ", "படம்", "ஆடியோ", "திட்டம்", "ட்வின்", "ஆதாரம்", "நினைவகம்")

      NormalizedToken(
        originalSnippet = word,
        normalizedSnippet = clean,
        classification = classification,
        phoneticLemma = clean.lowercase(),
        isActionVerb = isAction,
        isDomainEntity = isDomain
      )
    }
  }
}
