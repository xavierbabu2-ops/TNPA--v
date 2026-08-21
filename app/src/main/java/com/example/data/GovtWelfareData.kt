package com.example.data

enum class GovtType(val labelTamil: String, val labelEnglish: String) {
  ALL("அனைத்து அரசுகள்", "All Governments"),
  TAMIL_NADU("தமிழ்நாடு அரசு", "Tamil Nadu Government"),
  CENTRAL("மத்திய அரசு (India)", "Central Government")
}

enum class WorkerOccupation(val labelTamil: String, val labelEnglish: String, val iconTag: String) {
  ALL("அனைத்து தொழில்கள்", "All Trades", "🌐"),
  PAINTER("பெயிண்டர் (Painter)", "Building & Commercial Painter", "🖌️"),
  ARTIST("ஓவியர் / Artist", "Fine Arts, Portraits & Sculptor", "🎨"),
  CONSTRUCTION("கட்டுமானத் தொழிலாளர்", "Mason & Construction", "🧱"),
  DECORATOR("அலங்காரப் பணியாளர்", "POP & Stage Decorator", "🎪"),
  UNORGANISED("அமைப்புசாரா தொழிலாளர்கள்", "Unorganised Manual Workers", "🛠️")
}

data class GovtWelfareScheme(
  val id: String,
  val titleTamil: String,
  val titleEnglish: String,
  val govtType: GovtType,
  val boardDepartment: String,
  val applicableOccupations: List<WorkerOccupation>,
  val tag: String,
  val highlightAmount: String,
  val shortDescription: String,
  val ageRange: String,
  val minAge: Int = 18,
  val maxAge: Int = 60,
  val incomeCriteria: String,
  val maxMonthlyIncome: Int? = null,
  val requiredRegistration: String,
  val requiredDocuments: List<String>,
  val verifiedBenefits: List<String>,
  val howToApplySteps: List<String>,
  val officialApplyUrl: String,
  val officialStatusUrl: String,
  val officialPortalName: String,
  val lastVerifiedDate: String = "August 2026",
  val isFeeFree: Boolean = true,
  val officialDisclaimer: String = "அரசாங்கத்தால் நிர்ணயிக்கப்பட்ட அதிகாரப்பூர்வ விதிமுறைகளின்படி தகுதி இறுதி செய்யப்படும்."
)

object GovtWelfareRepository {

  val schemesList: List<GovtWelfareScheme> = listOf(
    // 1. Tamil Nadu Construction Workers Welfare Board (TNCWWB) - Specifically for Painters & Construction
    GovtWelfareScheme(
      id = "TN-CW-01",
      titleTamil = "தமிழ்நாடு கட்டுமானத் தொழிலாளர்கள் நலவாரியம் (பெயிண்டர் பிரிவு)",
      titleEnglish = "Tamil Nadu Construction Workers Welfare Board (Painting & BOCW)",
      govtType = GovtType.TAMIL_NADU,
      boardDepartment = "தொழிலாளர் நலன் மற்றும் திறன் மேம்பாட்டுத் துறை, தமிழ்நாடு அரசு",
      applicableOccupations = listOf(WorkerOccupation.PAINTER, WorkerOccupation.CONSTRUCTION, WorkerOccupation.DECORATOR),
      tag = "முக்கிய தமிழ்நாடு வாரியம் (TNCWWB)",
      highlightAmount = "விபத்து நிதி ₹5,00,000 | ஓய்வூதியம் ₹1,000",
      shortDescription = "சுவர், கட்டிடம், விளம்பர பலகை பெயிண்டிங் செய்யும் தொழிலாளர்கள் மற்றும் கட்டுமானப் பணியாளர்களுக்கான அரசின் விரிவான சமூகப் பாதுகாப்பு நலவாரியம்.",
      ageRange = "18 முதல் 60 வயது வரை",
      minAge = 18,
      maxAge = 60,
      incomeCriteria = "அமைப்புசாரா உடலுழைப்பு/கட்டுமானத் தொழிலாளி (வருமான உச்சவரம்பு இல்லை)",
      maxMonthlyIncome = null,
      requiredRegistration = "TNUWWB உறுப்பினர் பதிவு எண் (TNUWWB Worker ID)",
      requiredDocuments = listOf(
        "ஆதார் அட்டை (Aadhaar Card)",
        "குடும்ப அட்டை / ஸ்மார்ட் கார்டு (Ration Card)",
        "வங்கி சேமிப்பு கணக்கு புத்தகம் (Bank Passbook with IFSC)",
        "தொழில் சான்று (சங்க உறுப்பினர் சான்றிதழ் / VAO சான்று / Employer Certificate)",
        "வயது சான்று (பள்ளி சான்றிதழ் / வாக்காளர் அட்டை / ஆதார்)",
        "பாஸ்போர்ட் அளவு புகைப்படம் (Passport Photo)",
        "வாரிசுதாரர் ஆதார் விவரங்கள் (Nominee Aadhaar Details)"
      ),
      verifiedBenefits = listOf(
        "🛡️ பணியிட விபத்து மரண நிவாரணம்: ₹5,00,000",
        "🏥 கடுமையான விபத்து நிரந்தர ஊன நிவாரணம்: ₹1,00,000 முதல் ₹5,00,000 வரை",
        "🕊️ இயற்கை மரண உதவித்தொகை: ₹50,000",
        "💐 ஈமச்சடங்கு செலவு உதவி: ₹5,000",
        "👴 60 வயது பூர்த்தியடைந்த உறுப்பினர்களுக்கு மாதாந்திர முதியோர் ஓய்வூதியம்: ₹1,000 / மாதம்",
        "💍 உறுப்பினரின் திருமண உதவி: ₹20,000 (மகளிர் மற்றும் ஆண் தொழிலாளர்களுக்கு)",
        "🤱 மகளிர் தொழிலாளர் மகப்பேறு உதவி: ₹18,000 (2 பிரசவங்களுக்கு) + கருக்கலைப்பு உதவி ₹9,000",
        "🎓 10-ஆம் வகுப்பு பொதுத்தேர்வு தேர்ச்சி ஊக்கத்தொகை: ₹1,000 (அரசு பள்ளி மாணவர்கள் ₹3,000)",
        "🎓 12-ஆம் வகுப்பு பொதுத்தேர்வு தேர்ச்சி ஊக்கத்தொகை: ₹1,500 (அரசு பள்ளி மாணவர்கள் ₹5,000)",
        "🏛️ பட்டப்படிப்பு / தொழிற்கல்வி / பாலிடெக்னிக் படிப்பு உதவி: ₹4,000 முதல் ₹12,000 வரை",
        "🩺 மருத்துவ & பொறியியல் உயர்கல்வி உதவி: ₹25,000 முதல் ₹50,000 வரை",
        "👓 இலவச கண் கண்ணாடி உதவித்தொகை: ₹1,000 வரை",
        "🏡 கட்டுமானத் தொழிலாளர் சொந்த வீடு கட்டும் மானியம் (BOCW Housing Subsidy / Kalaignar Kanavu Illam)"
      ),
      howToApplySteps = listOf(
        "1. TNUWWB அதிகாரப்பூர்வ இணையதளம் (tnuwwb.tn.gov.in) செல்க.",
        "2. 'New Registration / புதிய பதிவு' என்பதைத் தேர்வு செய்க.",
        "3. தொழில் பிரிவில் 'Painting / பெயிண்டிங்' அல்லது 'Construction / கட்டுமானம்' என்பதை தேர்ந்தெடுக்கவும்.",
        "4. ஆதார் மற்றும் மொபைல் எண் உள்ளிட்டு OTP பெறவும்.",
        "5. சான்றிதழ்கள் (தொழில் சான்று, வங்கி பாஸ்புக்) பதிவேற்றி சமர்ப்பிக்கவும்.",
        "6. தொழிலாளர் உதவி ஆணையர் (சமூக பாதுகாப்பு திட்டம்) சரிபார்த்து ID அட்டை வழங்குவார்."
      ),
      officialApplyUrl = "https://tnuwwb.tn.gov.in/portal/",
      officialStatusUrl = "https://tnuwwb.tn.gov.in/portal/",
      officialPortalName = "TNUWWB Official Portal (tnuwwb.tn.gov.in)",
      lastVerifiedDate = "August 2026",
      isFeeFree = true
    ),

    // 2. Tamil Nadu Artists Welfare Board (தமிழ்நாடு நாட்டுப்புறக் கலைஞர்கள் & ஓவியர்கள் நலவாரியம்)
    GovtWelfareScheme(
      id = "TN-ART-02",
      titleTamil = "தமிழ்நாடு நாட்டுப்புறக் கலைஞர்கள் & ஓவியர்கள் நலவாரியம்",
      titleEnglish = "Tamil Nadu Folk Artists & Painters/Artists Welfare Board",
      govtType = GovtType.TAMIL_NADU,
      boardDepartment = "கலை பண்பாட்டுத் துறை (Directorate of Art and Culture), தமிழ்நாடு அரசு",
      applicableOccupations = listOf(WorkerOccupation.ARTIST),
      tag = "ஓவியர்கள் & கலைஞர்கள் வாரியம் (Artists Board)",
      highlightAmount = "ஓய்வூதியம் ₹3,00,00 / மாதம் | மரண உதவி ₹50,000",
      shortDescription = "சுவர் ஓவியர்கள், உருவப்பட ஓவியர்கள், சித்திரக்காரர்கள், சிற்பிகள் மற்றும் நாட்டுப்புறக் கலைஞர்களுக்கான அதிகாரப்பூர்வ தமிழ்நாடு அரசு நலவாரியத் திட்டம்.",
      ageRange = "18 முதல் 60 வயது வரை",
      minAge = 18,
      maxAge = 60,
      incomeCriteria = "ஓவியக்கலை / சிற்பக்கலையை முழுநேர அல்லது பகுதிநேர தொழிலாகக் கொண்டவர்கள்",
      maxMonthlyIncome = null,
      requiredRegistration = "தமிழ்நாடு கலை பண்பாட்டுத்துறை நலவாரிய பதிவு (Artists Welfare ID)",
      requiredDocuments = listOf(
        "ஆதார் அட்டை (Aadhaar Card)",
        "ஓவியர் தொழில் அடையாள அட்டை / சங்க உறுப்பினர் அட்டை (TNPA Member Card)",
        "வரைந்த ஓவியங்கள் / கலைப்படைப்புகள் புகைப்படங்கள் (Art Portfolio / Samples)",
        "வங்கி சேமிப்பு கணக்கு புத்தகம் (Bank Passbook)",
        "குடும்ப அட்டை (Ration Card)",
        "வயது சான்று மற்றும் புகைப்படங்கள்"
      ),
      verifiedBenefits = listOf(
        "🎨 நலிந்த மூத்த ஓவியர்கள் மற்றும் கலைஞர்களுக்கான மாதாந்திர ஓய்வூதியம்: ₹3,000 / மாதம்",
        "🛡️ விபத்து மரண நிவாரண உதவித்தொகை: ₹2,00,000",
        "🕊️ இயற்கை மரண உதவி: ₹50,000 + ஈமச்சடங்கு நிதி: ₹5,000",
        "💍 ஓவியர் மற்றும் கலைஞர் குடும்ப திருமண உதவித்தொகை: ₹10,000 முதல் ₹20,000 வரை",
        "🎓 கலைஞர் வாரிசுகளுக்கு பள்ளி மற்றும் கல்லூரி கல்வி உதவித்தொகை",
        "🖌️ ஓவியக் கலைப் பொருட்கள், தூரிகை, கேன்வாஸ் மற்றும் வண்ணங்கள் வாங்க மானிய உதவி",
        "🏆 மாநில அளவிலான கலை அங்கீகார விருதுகள் மற்றும் கண்காட்சி வாய்ப்புகள்"
      ),
      howToApplySteps = listOf(
        "1. தமிழ்நாடு கலை பண்பாட்டுத்துறை அதிகாரப்பூர்வ இணையதளம் (artandculture.tn.gov.in) அல்லது மாவட்ட மண்டல கலை பண்பாட்டு அலுவலகத்தை அணுகவும்.",
        "2. ஓவியக் கலைஞர் நலவாரிய உறுப்பினர் பதிவு படிவத்தைப் பூர்த்தி செய்யவும்.",
        "3. நீங்கள் வரைந்த ஓவியங்களின் புகைப்படங்கள் மற்றும் TNPA சங்க உறுப்பினர் சான்றை இணைக்கவும்.",
        "4. மாவட்ட கலை பண்பாட்டு அலுவலர் ஒப்புதல் அளித்தவுடன் நலவாரிய அடையாள அட்டை வழங்கப்படும்."
      ),
      officialApplyUrl = "https://artandculture.tn.gov.in/",
      officialStatusUrl = "https://artandculture.tn.gov.in/",
      officialPortalName = "TN Art and Culture Dept Portal (artandculture.tn.gov.in)",
      lastVerifiedDate = "August 2026",
      isFeeFree = true
    ),

    // 3. Tamil Nadu Manual Workers Social Security and Welfare Board
    GovtWelfareScheme(
      id = "TN-MW-03",
      titleTamil = "தமிழ்நாடு உடலுழைப்புத் தொழிலாளர்கள் சமூகப் பாதுகாப்பு நலவாரியம்",
      titleEnglish = "Tamil Nadu Manual Workers Social Security & Welfare Board",
      govtType = GovtType.TAMIL_NADU,
      boardDepartment = "தமிழ்நாடு அமைப்புசாரா தொழிலாளர்கள் நல வாரியம் (TNUWWB)",
      applicableOccupations = listOf(WorkerOccupation.UNORGANISED, WorkerOccupation.DECORATOR, WorkerOccupation.PAINTER),
      tag = "அமைப்புசாரா தொழிலாளர்கள் வாரியம்",
      highlightAmount = "விபத்து நிதி ₹2,00,000 | ஓய்வூதியம் ₹1,000",
      shortDescription = "அலங்காரப் பணியாளர்கள், ஸ்டேஜ் டெக்கரேட்டர்கள் மற்றும் அமைப்புசாரா உடலுழைப்பு தொழிலாளர்களுக்கான அரசு சமூகப் பாதுகாப்புத் திட்டம்.",
      ageRange = "18 முதல் 60 வயது வரை",
      minAge = 18,
      maxAge = 60,
      incomeCriteria = "அமைப்புசாரா உடலுழைப்புத் தொழிலாளர்கள்",
      maxMonthlyIncome = null,
      requiredRegistration = "TNUWWB உடலுழைப்பு தொழிலாளர் அடையாள அட்டை",
      requiredDocuments = listOf(
        "ஆதார் அட்டை",
        "வங்கி கணக்கு புத்தகம்",
        "குடும்ப அட்டை",
        "உடலுழைப்பு தொழில் சான்று",
        "பாஸ்போர்ட் புகைப்படம்"
      ),
      verifiedBenefits = listOf(
        "🛡️ விபத்து மரண இழப்பீடு: ₹2,00,000",
        "🕊️ இயற்கை மரண உதவி: ₹30,000 + ஈமச்சடங்கு: ₹5,000",
        "👴 மாதாந்திர முதியோர் ஓய்வூதியம்: ₹1,000/மாதம் (60 வயதிற்கு மேல்)",
        "💍 திருமண உதவி: ₹10,000 முதல் ₹20,000 வரை",
        "🎓 மாணவ, மாணவியருக்கு கல்வி உதவித்தொகை"
      ),
      howToApplySteps = listOf(
        "1. tnuwwb.tn.gov.in போர்ட்டலில் New Registration பகுதிக்கு செல்லவும்.",
        "2. Manual Workers Board என்பதை தேர்வு செய்து விவரங்களை உள்ளிடவும்.",
        "3. கிராம நிர்வாக அலுவலர் அல்லது சங்க சான்று சமர்ப்பித்து ஒப்புதல் பெறவும்."
      ),
      officialApplyUrl = "https://tnuwwb.tn.gov.in/portal/",
      officialStatusUrl = "https://tnuwwb.tn.gov.in/portal/",
      officialPortalName = "TNUWWB Portal (tnuwwb.tn.gov.in)",
      lastVerifiedDate = "August 2026",
      isFeeFree = true
    ),

    // 4. Central Govt: e-Shram (NDUW)
    GovtWelfareScheme(
      id = "CEN-ESHRAM-04",
      titleTamil = "இ-ஷ்ரம் (e-Shram) தேசிய அமைப்புசாரா தொழிலாளர் அட்டை",
      titleEnglish = "e-Shram National Database of Unorganised Workers (NDUW)",
      govtType = GovtType.CENTRAL,
      boardDepartment = "தொழிலாளர் மற்றும் வேலைவாய்ப்பு அமைச்சகம் (MoL&E), இந்திய அரசு",
      applicableOccupations = listOf(WorkerOccupation.PAINTER, WorkerOccupation.ARTIST, WorkerOccupation.CONSTRUCTION, WorkerOccupation.DECORATOR, WorkerOccupation.UNORGANISED),
      tag = "மத்திய அரசு தேசிய அட்டை (National UAN)",
      highlightAmount = "இலவச UAN அட்டை + ₹2,00,000 விபத்து காப்பீடு",
      shortDescription = "மத்திய அரசின் அனைத்து சமூகப் பாதுகாப்புத் திட்டங்கள், விபத்து காப்பீடு மற்றும் நேரடி பணப் பலன்களைப் பெற அமைப்புசாரா தொழிலாளர்களுக்கான 12 இலக்க தேசிய அடையாள அட்டை.",
      ageRange = "16 முதல் 59 வயது வரை",
      minAge = 16,
      maxAge = 59,
      incomeCriteria = "EPFO / ESIC உறுப்பினர் அல்லாத, வருமான வரி செலுத்தாத எவரும் விண்ணப்பிக்கலாம்",
      maxMonthlyIncome = null,
      requiredRegistration = "e-Shram 12-Digit Universal Account Number (UAN Card)",
      requiredDocuments = listOf(
        "ஆதார் அட்டை (Aadhaar Card with linked active Mobile)",
        "வங்கி கணக்கு எண் மற்றும் IFSC குறியீடு (Bank Account Details)",
        "தொழில் வகை / NCO Code (Painting / Construction / Arts & Craft)",
        "தற்போதைய முகவரி விவரங்கள்"
      ),
      verifiedBenefits = listOf(
        "🆔 இந்தியா முழுவதும் செல்லுபடியாகும் 12 இலக்க தனித்துவ தேசிய அடையாள அட்டை (UAN Card)",
        "🛡️ விபத்து மரணம் மற்றும் முழு நிரந்தர ஊனத்திற்கு ₹2,00,000 இலவச காப்பீடு (PMSBY Cover)",
        "🏥 பகுதி விபத்து ஊனத்திற்கு ₹1,00,000 காப்பீட்டு உதவி",
        "🌐 மத்திய அரசின் அவசரகால தேசிய நிவாரண நிதிகள் நேரடியாக வங்கி கணக்கிற்கு (DBT) வரவு",
        "📲 PM-SYM ஓய்வூதியம் மற்றும் பிற மத்திய அரசு திட்டங்களை எளிதாக இணைக்கும் வசதி"
      ),
      howToApplySteps = listOf(
        "1. register.eshram.gov.in போர்ட்டலுக்கு நேரடியாக செல்லவும்.",
        "2. ஆதார் இணைக்கப்பட்ட மொபைல் எண் மற்றும் கேப்ட்சா உள்ளிடவும்.",
        "3. பெறப்பட்ட OTP ஐ சரிபார்க்கவும்.",
        "4. தொழில் பிரிவில் 'Painter' அல்லது 'Artist' அல்லது 'Construction' என்பதைத் தேர்ந்தெடுக்கவும்.",
        "5. வங்கி விவரங்களைச் சமர்ப்பித்து உடனடியாக 12-இலக்க UAN அட்டையை பதிவிறக்கம் செய்க."
      ),
      officialApplyUrl = "https://register.eshram.gov.in/#/user/self-registration",
      officialStatusUrl = "https://register.eshram.gov.in/#/user/self-registration",
      officialPortalName = "Official e-Shram Portal (register.eshram.gov.in)",
      lastVerifiedDate = "August 2026",
      isFeeFree = true
    ),

    // 5. Central Govt: PM-SYM (Pradhan Mantri Shram Yogi Maan-dhan)
    GovtWelfareScheme(
      id = "CEN-PMSYM-05",
      titleTamil = "பிரதமர் ஷ்ரம் யோகி மான்-தன் (PM-SYM) ஓய்வூதியத் திட்டம்",
      titleEnglish = "Pradhan Mantri Shram Yogi Maan-dhan Pension Scheme",
      govtType = GovtType.CENTRAL,
      boardDepartment = "Ministry of Labour and Employment & LIC of India",
      applicableOccupations = listOf(WorkerOccupation.PAINTER, WorkerOccupation.ARTIST, WorkerOccupation.CONSTRUCTION, WorkerOccupation.DECORATOR, WorkerOccupation.UNORGANISED),
      tag = "உத்தரவாத ஓய்வூதியம் (Guaranteed Pension)",
      highlightAmount = "வாழ்நாள் ஓய்வூதியம் ₹3,000 / மாதம் (60 வயதிற்குப் பின்)",
      shortDescription = "பெயிண்டர்கள், ஓவியர்கள் மற்றும் அமைப்புசாரா தொழிலாளர்கள் 60 வயதுக்குப் பிறகு கௌரவமாக வாழ மாதம் ₹3,000 உத்தரவாத அரசு ஓய்வூதியம் வழங்கும் மத்திய அரசு திட்டம்.",
      ageRange = "18 முதல் 40 வயது வரை (நுழைவு வயது)",
      minAge = 18,
      maxAge = 40,
      incomeCriteria = "மாத வருமானம் ₹15,000 அல்லது அதற்குக் குறைவாக உள்ள அமைப்புசாரா தொழிலாளர்கள்",
      maxMonthlyIncome = 15000,
      requiredRegistration = "PM-SYM Pension ID Card / CSC e-Governance",
      requiredDocuments = listOf(
        "ஆதார் அட்டை (Aadhaar Card)",
        "சேமிப்பு வங்கி கணக்கு புத்தகம் / ஜன் தன் கணக்கு (Savings Bank / Jan Dhan Account with Auto-Debit)",
        "கைபேசி எண் (Mobile Number)"
      ),
      verifiedBenefits = listOf(
        "👴 60 வயது பூர்த்தியடைந்தவுடன் வாழ்நாள் முழுவதும் மாதம் ₹3,000 உத்தரவாத ஓய்வூதியம்",
        "🤝 50:50 அரசு பங்களிப்பு: தொழிலாளி செலுத்தும் மாதாந்திர சந்தாவுக்கு (₹55 - ₹200) சமமாக மத்திய அரசும் தன் பங்கை செலுத்தும்",
        "👩‍👧 தொழிலாளர் மறைவுக்குப் பின் வாழ்க்கைத் துணைவருக்கு (மனைவி/கணவர்) 50% குடும்ப ஓய்வூதியம் (₹1,500/மாதம்)",
        "🏛️ இந்திய ஆயுள் காப்பீட்டுக் கழகம் (LIC) மூலம் நிர்வகிக்கப்படும் 100% பாதுகாப்பான அரசு திட்டம்"
      ),
      howToApplySteps = listOf(
        "1. maandhan.in அதிகாரப்பூர்வ தளத்திற்கு செல்லவும் அல்லது அருகில் உள்ள CSC மையத்தை அணுகவும்.",
        "2. ஆதார் மற்றும் சேமிப்பு வங்கி கணக்கு விவரங்களை உள்ளிடவும்.",
        "3. ஆரம்ப சந்தா தொகையை செலுத்தி தானியங்கி பற்று (Auto-Debit) ஆணையை உறுதி செய்க.",
        "4. உடனடியாக PM-SYM ஓய்வூதிய அடையாள அட்டையை பதிவிறக்கம் செய்து கொள்ளலாம்."
      ),
      officialApplyUrl = "https://maandhan.in/",
      officialStatusUrl = "https://maandhan.in/",
      officialPortalName = "Official Maan-dhan Portal (maandhan.in)",
      lastVerifiedDate = "August 2026",
      isFeeFree = false,
      officialDisclaimer = "மாதாந்திர சந்தா தொகை ₹55 முதல் ₹200 வரை (தொழிலாளியின் நுழைவு வயதைப் பொறுத்து). சம பங்கு மத்திய அரசால் நேரடியாக செலுத்தப்படும்."
    ),

    // 6. Central Govt: PMSBY (Pradhan Mantri Suraksha Bima Yojana)
    GovtWelfareScheme(
      id = "CEN-PMSBY-06",
      titleTamil = "பிரதமர் சுரக்ஷா பீமா விபத்து காப்பீட்டுத் திட்டம் (PMSBY)",
      titleEnglish = "Pradhan Mantri Suraksha Bima Yojana (Accident Cover)",
      govtType = GovtType.CENTRAL,
      boardDepartment = "Department of Financial Services, Ministry of Finance, Govt of India",
      applicableOccupations = listOf(WorkerOccupation.ALL, WorkerOccupation.PAINTER, WorkerOccupation.CONSTRUCTION),
      tag = "குறைந்த கட்டண விபத்து காப்பீடு",
      highlightAmount = "விபத்து நிதி ₹2,00,000 (வருட சந்தா வெறும் ₹20)",
      shortDescription = "உயரமான கட்டிடங்களில் பணிபுரியும் பெயிண்டர்கள் மற்றும் தொழிலாளர்களுக்கு வெறும் ₹20 வருட கட்டணத்தில் ₹2 லட்சம் விபத்து காப்பீடு வழங்கும் திட்டம்.",
      ageRange = "18 முதல் 70 வயது வரை",
      minAge = 18,
      maxAge = 70,
      incomeCriteria = "சேமிப்பு வங்கி கணக்கு உள்ள அனைத்து குடிமக்களும்",
      maxMonthlyIncome = null,
      requiredRegistration = "Bank Linked PMSBY Enrollment",
      requiredDocuments = listOf("ஆதார் அட்டை", "சேமிப்பு வங்கிக் கணக்கு", "தானியங்கி பற்று (Auto-Debit) ஒப்புதல் படிவம்"),
      verifiedBenefits = listOf(
        "🛡️ விபத்து மரணத்திற்கு ₹2,00,000 இழப்பீடு",
        "🏥 இரு கண்கள் / இரு கைகள் / இரு கால்கள் முழுமையாக இழக்கும் நிரந்தர ஊனத்திற்கு ₹2,00,000",
        "🩹 ஒரு கண் அல்லது ஒரு கை/கால் இழப்பிற்கு ₹1,00,000 இழப்பீட்டுத் தொகை",
        "💳 வருடத்திற்கு வெறும் ₹20 மட்டுமே வங்கிக் கணக்கிலிருந்து தானாக கழிக்கப்படும்"
      ),
      howToApplySteps = listOf(
        "1. உங்கள் சேமிப்பு வங்கி கிளை அல்லது இணைய வங்கி (Net Banking) மூலம் விண்ணப்பிக்கலாம்.",
        "2. jansuraksha.gov.in போர்ட்டலில் படிவத்தை பதிவிறக்கம் செய்து வங்கியில் சமர்ப்பிக்கலாம்."
      ),
      officialApplyUrl = "https://jansuraksha.gov.in/",
      officialStatusUrl = "https://jansuraksha.gov.in/",
      officialPortalName = "Jan Suraksha Portal (jansuraksha.gov.in)",
      lastVerifiedDate = "August 2026",
      isFeeFree = false,
      officialDisclaimer = "அரசு நிர்ணயித்த வருடாந்திர பிரீமியம் ₹20 மட்டுமே."
    ),

    // 7. Central Govt: PMJJBY (Pradhan Mantri Jeevan Jyoti Bima Yojana)
    GovtWelfareScheme(
      id = "CEN-PMJJBY-07",
      titleTamil = "பிரதமர் ஜீவன் ஜோதி ஆயுள் காப்பீட்டுத் திட்டம் (PMJJBY)",
      titleEnglish = "Pradhan Mantri Jeevan Jyoti Bima Yojana (Life Cover)",
      govtType = GovtType.CENTRAL,
      boardDepartment = "Department of Financial Services, Ministry of Finance, Govt of India",
      applicableOccupations = listOf(WorkerOccupation.ALL, WorkerOccupation.PAINTER, WorkerOccupation.ARTIST, WorkerOccupation.CONSTRUCTION, WorkerOccupation.UNORGANISED),
      tag = "ஆயுள் காப்பீடு (Life Insurance)",
      highlightAmount = "இயற்கை / விபத்து மரண நிதி ₹2,00,000",
      shortDescription = "எந்தவொரு காரணத்தினால் ஏற்படும் மரணத்திற்கும் குடும்பத்திற்கு ₹2,00,000 நிதி பாதுகாப்பு அளிக்கும் மத்திய அரசின் குறைந்த பிரீமியம் ஆயுள் காப்பீடு.",
      ageRange = "18 முதல் 50 வயது வரை",
      minAge = 18,
      maxAge = 50,
      incomeCriteria = "சேமிப்பு வங்கி கணக்கு வைத்துள்ள எவரும்",
      maxMonthlyIncome = null,
      requiredRegistration = "Bank Linked PMJJBY Policy",
      requiredDocuments = listOf("ஆதார் அட்டை", "சேமிப்பு வங்கி கணக்கு புத்தகம்", "மொபைல் எண்"),
      verifiedBenefits = listOf(
        "🕊️ எந்தக் காரணத்தினாலும் ஏற்படும் மரணத்திற்கு தொழிலாளியின் குடும்பத்திற்கு ₹2,00,000 ஆயுள் காப்பீடு",
        "💳 ஆண்டிற்கு ₹436 மட்டுமே வங்கி கணக்கில் இருந்து தானாக கழிக்கப்படும்"
      ),
      howToApplySteps = listOf(
        "1. உங்கள் வங்கி கிளையை அணுகி PMJJBY ஒப்புதல் படிவத்தை சமர்ப்பிக்கவும்.",
        "2. அல்லது வங்கியின் அதிகாரப்பூர்வ நெட் பேங்கிங் / மொபைல் ஆப் வழியாக நேரடியாக இணைத்துக் கொள்ளலாம்."
      ),
      officialApplyUrl = "https://jansuraksha.gov.in/",
      officialStatusUrl = "https://jansuraksha.gov.in/",
      officialPortalName = "Jan Suraksha Portal (jansuraksha.gov.in)",
      lastVerifiedDate = "August 2026",
      isFeeFree = false,
      officialDisclaimer = "அரசு நிர்ணயித்த வருடாந்திர பிரீமியம் ₹436 மட்டுமே."
    )
  )

  fun getSchemesByFilters(
    govtType: GovtType = GovtType.ALL,
    occupation: WorkerOccupation = WorkerOccupation.ALL,
    searchQuery: String = ""
  ): List<GovtWelfareScheme> {
    return schemesList.filter { scheme ->
      val matchesGovt = (govtType == GovtType.ALL || scheme.govtType == govtType)
      val matchesOccupation = (occupation == WorkerOccupation.ALL ||
          scheme.applicableOccupations.contains(WorkerOccupation.ALL) ||
          scheme.applicableOccupations.contains(occupation))
      val matchesQuery = if (searchQuery.isBlank()) true else {
        scheme.titleTamil.contains(searchQuery, ignoreCase = true) ||
            scheme.titleEnglish.contains(searchQuery, ignoreCase = true) ||
            scheme.boardDepartment.contains(searchQuery, ignoreCase = true) ||
            scheme.tag.contains(searchQuery, ignoreCase = true)
      }
      matchesGovt && matchesOccupation && matchesQuery
    }
  }

  // Eligibility Evaluation Engine
  data class EligibilityResult(
    val status: EligibilityStatus,
    val matchedSchemes: List<GovtWelfareScheme>,
    val explanationTamil: String,
    val guidanceTamil: String
  )

  enum class EligibilityStatus(val labelTamil: String, val badgeColorHex: Long) {
    ELIGIBLE("முழு தகுதியானது (Eligible)", 0xFF16A34A),
    POSSIBLY_ELIGIBLE("சாத்தியமான தகுதி (Possibly Eligible)", 0xFFD97706),
    NEEDS_MORE_INFO("கூடுதல் தகவல் தேவை (More Info Required)", 0xFF2563EB),
    NOT_ELIGIBLE("தகுதி பொருந்தவில்லை (Not Eligible)", 0xFFDC2626)
  }

  fun evaluateEligibility(
    occupation: WorkerOccupation,
    age: Int,
    monthlyIncome: Int,
    hasTnWelfareCard: Boolean,
    hasEShram: Boolean
  ): EligibilityResult {
    val matched = schemesList.filter { scheme ->
      val occupationMatches = scheme.applicableOccupations.contains(WorkerOccupation.ALL) ||
          scheme.applicableOccupations.contains(occupation)
      val ageMatches = age in scheme.minAge..scheme.maxAge
      val incomeMatches = scheme.maxMonthlyIncome == null || monthlyIncome <= scheme.maxMonthlyIncome
      occupationMatches && ageMatches && incomeMatches
    }

    return if (matched.isNotEmpty()) {
      EligibilityResult(
        status = EligibilityStatus.POSSIBLY_ELIGIBLE,
        matchedSchemes = matched,
        explanationTamil = "உங்கள் வயது ($age), தொழில் (${occupation.labelTamil}) மற்றும் வருமானம் ஆகியவற்றின் அடிப்படையில் ${matched.size} அரசு நலத்திட்டங்கள் சாத்தியமானவையாக கண்டறியப்பட்டுள்ளன.",
        guidanceTamil = "சாத்தியமான தகுதி (Possibly Eligible) — இறுதி தகுதி சம்பந்தப்பட்ட அரசுத் துறை மற்றும் நலவாரிய அதிகாரிகளால் சரிபார்க்கப்பட்டு தீர்மானிக்கப்படும்."
      )
    } else {
      EligibilityResult(
        status = EligibilityStatus.NEEDS_MORE_INFO,
        matchedSchemes = emptyList(),
        explanationTamil = "உள்ளிடப்பட்ட விவரங்களுக்கு நேரடி திட்டங்கள் பொருந்தவில்லை. வயது வரம்பு (18-60) அல்லது தொழிலை மறுஆய்வு செய்யவும்.",
        guidanceTamil = "அருகில் உள்ள தொழிலாளர் நலவாரிய அலுவலகம் அல்லது பொது சேவை மையத்தை (CSC) அணுகவும்."
      )
    }
  }
}
