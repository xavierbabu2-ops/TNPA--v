package com.example.data

import android.util.Log
import com.example.model.AdminHierarchyLevel
import com.example.model.HierarchyOfficeBearer
import com.example.model.TamilNaduMasterData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

/**
 * Data structure representing detailed administrative units for each of Tamil Nadu's 38 districts.
 */
data class DistrictStructuralData(
  val districtCode: String,
  val tamilName: String,
  val englishName: String,
  val headquarters: String,
  val zone: String,
  val unions: List<String>,
  val cities: List<String>,
  val youthWingUnits: List<String>
)

object FirestoreDistrictInitializer {

  private val firestore: FirebaseFirestore by lazy {
    FirebaseFirestore.getInstance()
  }

  // 38 Districts with official Unions (ஒன்றியங்கள்), Cities (நகரங்கள்), and Youth Wings (இளைஞரணி அமைப்புகள்)
  val DISTRICT_STRUCTURES: List<DistrictStructuralData> = listOf(
    DistrictStructuralData(
      districtCode = "MADURAI",
      tamilName = "மதுரை",
      englishName = "Madurai",
      headquarters = "மதுரை (HQ)",
      zone = "தென் மண்டலம் (South Zone)",
      unions = listOf("மதுரை கிழக்கு", "மதுரை மேற்கு", "மேலூர்", "வாடிப்பட்டி", "உசிலம்பட்டி", "திருமங்கலம்", "கல்லுப்பட்டி", "சேடப்பட்டி", "கொட்டாம்பட்டி", "செல்லம்பட்டி", "திருப்பரங்குன்றம்", "அலங்காநல்லூர்", "டி.கல்லுப்பட்டி"),
      cities = listOf("மதுரை மாநகரம்", "மேலூர் நகராட்சி", "திருமங்கலம் நகராட்சி", "உசிலம்பட்டி நகராட்சி", "வாடிப்பட்டி பேரூராட்சி", "சோழவந்தான் பேரூராட்சி"),
      youthWingUnits = listOf("மதுரை மாவட்ட இளைஞரணி", "மதுரை மாநகர இளைஞரணி", "மேலூர் ஒன்றிய இளைஞரணி", "திருமங்கலம் ஒன்றிய இளைஞரணி", "உசிலம்பட்டி ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "CHENNAI",
      tamilName = "சென்னை",
      englishName = "Chennai",
      headquarters = "சென்னை (HQ)",
      zone = "சென்னை பெருநகர மண்டலம் (Chennai Metro Zone)",
      unions = listOf("சென்னை வடக்கு", "சென்னை தெற்கு", "சென்னை மத்திய", "சென்னை கிழக்கு", "சென்னை மேற்கு"),
      cities = listOf("ராயபுரம்", "திருவொற்றியூர்", "அண்ணா நகர்", "தி.நகர்", "அடையாறு", "கோடம்பாக்கம்", "அம்பத்தூர்", "ஆலந்தூர்", "பெருங்குடி", "சோழிங்கநல்லூர்"),
      youthWingUnits = listOf("சென்னை மாவட்ட இளைஞரணி", "வட சென்னை இளைஞரணி", "தென் சென்னை இளைஞரணி", "மத்திய சென்னை இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "TRICHY",
      tamilName = "திருச்சிராப்பள்ளி",
      englishName = "Tiruchirappalli",
      headquarters = "திருச்சி",
      zone = "மத்திய & டெல்டா மண்டலம் (Central & Delta Zone)",
      unions = listOf("அந்தநல்லூர்", "மணிகண்டம்", "லால்குடி", "மண்ணச்சநல்லூர்", "முசிறி", "தொட்டியம்", "துறையூர்", "உப்பிலியாபுரம்", "திருவெறும்பூர்", "மருங்காபுரி", "மணப்பாறை", "வையம்பட்டி"),
      cities = listOf("திருச்சி மாநகரம்", "மணப்பாறை நகராட்சி", "துறையூர் நகராட்சி", "துவாக்குடி நகராட்சி"),
      youthWingUnits = listOf("திருச்சி மாவட்ட இளைஞரணி", "திருச்சி மாநகர இளைஞரணி", "மணப்பாறை ஒன்றிய இளைஞரணி", "லால்குடி ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "COIMBATORE",
      tamilName = "கோயம்புத்தூர்",
      englishName = "Coimbatore",
      headquarters = "கோயம்புத்தூர்",
      zone = "மேற்கு மண்டலம் (West Zone)",
      unions = listOf("காரமடை", "பெரியநாயக்கன்பாளையம்", "அன்னூர்", "சர்க்கார் சாமக்குளம்", "தொண்டாமுத்தூர்", "மாதம்பட்டி", "மதுக்கரை", "கிணத்துக்கடவு", "பொள்ளாச்சி வடக்கு", "பொள்ளாச்சி தெற்கு", "ஆனைமலை", "சுல்தான்பேட்டை"),
      cities = listOf("கோவை மாநகரம்", "பொள்ளாச்சி நகராட்சி", "மேட்டுப்பாளையம் நகராட்சி", "வால்பாறை நகராட்சி", "காரமடை நகராட்சி"),
      youthWingUnits = listOf("கோவை மாவட்ட இளைஞரணி", "கோவை மாநகர இளைஞரணி", "பொள்ளாச்சி ஒன்றிய இளைஞரணி", "மேட்டுப்பாளையம் நகர இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "SALEM",
      tamilName = "சேலம்",
      englishName = "Salem",
      headquarters = "சேலம்",
      zone = "மேற்கு மண்டலம் (West Zone)",
      unions = listOf("சேலம்", "பனமரத்துப்பட்டி", "வீரபாண்டி", "வலப்பாடி", "ஏற்காடு", "ஆத்தூர்", "பெத்தநாயக்கன்பாளையம்", "தலைவாசல்", "கங்கவல்லி", "மேட்டூர்", "கொளத்தூர்", "மேச்சேரி", "நங்கவள்ளி", "தாரமங்கலம்", "ஓமலூர்", "காடையாம்பட்டி", "சங்ககிரி", "மகுடஞ்சாவடி", "எடப்பாடி", "கொங்கணாபுரம்"),
      cities = listOf("சேலம் மாநகரம்", "ஆத்தூர் நகராட்சி", "மேட்டூர் நகராட்சி", "எடப்பாடி நகராட்சி", "நரசிங்கபுரம் நகராட்சி"),
      youthWingUnits = listOf("சேலம் மாவட்ட இளைஞரணி", "சேலம் மாநகர இளைஞரணி", "ஆத்தூர் ஒன்றிய இளைஞரணி", "மேட்டூர் நகர இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "TIRUNELVELI",
      tamilName = "திருநெல்வேலி",
      englishName = "Tirunelveli",
      headquarters = "திருநெல்வேலி",
      zone = "தென் மண்டலம் (South Zone)",
      unions = listOf("பாளையங்கோட்டை", "மானூர்", "சேரன்மகாதேவி", "அம்பாசமுத்திரம்", "பாபநாசம்", "களக்காடு", "நாங்குநேரி", "ராதாபுரம்", "வள்ளியூர்"),
      cities = listOf("நெல்லை மாநகரம்", "அம்பாசமுத்திரம் நகராட்சி", "விக்கிரமசிங்கபுரம் நகராட்சி", "களக்காடு நகராட்சி"),
      youthWingUnits = listOf("நெல்லை மாவட்ட இளைஞரணி", "நெல்லை மாநகர இளைஞரணி", "அம்பாசமுத்திரம் ஒன்றிய இளைஞரணி", "வள்ளியூர் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "THOOTHUKUDI",
      tamilName = "தூத்துக்குடி",
      englishName = "Thoothukudi",
      headquarters = "தூத்துக்குடி",
      zone = "தென் மண்டலம் (South Zone)",
      unions = listOf("தூத்துக்குடி", "கருங்குளம்", "ஸ்ரீவைகுண்டம்", "ஆழ்வார்திருநகரி", "திருச்செந்தூர்", "உடன்குடி", "சாத்தான்குளம்", "கோவில்பட்டி", "கயத்தாறு", "ஓட்டப்பிடாரம்", "விளாத்திகுளம்", "புதூர்"),
      cities = listOf("தூத்துக்குடி மாநகரம்", "கோவில்பட்டி நகராட்சி", "திருச்செந்தூர் நகராட்சி", "காயல்பட்டினம் நகராட்சி"),
      youthWingUnits = listOf("தூத்துக்குடி மாவட்ட இளைஞரணி", "கோவில்பட்டி ஒன்றிய இளைஞரணி", "திருச்செந்தூர் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "ERODE",
      tamilName = "ஈரோடு",
      englishName = "Erode",
      headquarters = "ஈரோடு",
      zone = "மேற்கு மண்டலம் (West Zone)",
      unions = listOf("ஈரோடு", "மொடக்குறிச்சி", "கொடுமுடி", "பெருந்துறை", "சென்னிமலை", "பவானி", "அம்மாபேட்டை", "அந்தியூர்", "கோபிசெட்டிபாளையம்", "டி.என்.பாளையம்", "நம்பியூர்", "சத்தியமங்கலம்", "பவானிசாகர்", "தாளவாடி"),
      cities = listOf("ஈரோடு மாநகரம்", "கோபிசெட்டிபாளையம் நகராட்சி", "பவானி நகராட்சி", "சத்தியமங்கலம் நகராட்சி", "புஞ்சை புளியம்பட்டி நகராட்சி"),
      youthWingUnits = listOf("ஈரோடு மாவட்ட இளைஞரணி", "கோபி ஒன்றிய இளைஞரணி", "சத்தியமங்கலம் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "TIRUPPUR",
      tamilName = "திருப்பூர்",
      englishName = "Tiruppur",
      headquarters = "திருப்பூர்",
      zone = "மேற்கு மண்டலம் (West Zone)",
      unions = listOf("திருப்பூர்", "அவிநாசி", "ஊத்துக்குளி", "பல்லடம்", "பொங்கலூர்", "காங்கேயம்", "வெள்ளக்கோவில்", "தாராபுரம்", "மூலனூர்", "குண்டடம்", "உடுமலைப்பேட்டை", "மடத்துக்குளம்", "குடிமங்கலம்"),
      cities = listOf("திருப்பூர் மாநகரம்", "பல்லடம் நகராட்சி", "தாராபுரம் நகராட்சி", "உடுமலைப்பேட்டை நகராட்சி", "காங்கேயம் நகராட்சி", "வெள்ளக்கோவில் நகராட்சி"),
      youthWingUnits = listOf("திருப்பூர் மாவட்ட இளைஞரணி", "பல்லடம் ஒன்றிய இளைஞரணி", "உடுமலை ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "DINDIGUL",
      tamilName = "திண்டுக்கல்",
      englishName = "Dindigul",
      headquarters = "திண்டுக்கல்",
      zone = "தென் மண்டலம் (South Zone)",
      unions = listOf("திண்டுக்கல்", "ரெட்டியார்சத்திரம்", "சாணார்பட்டி", "நத்தம்", "ஆத்தூர்", "நிலக்கோட்டை", "வத்தலகுண்டு", "பழனி", "ஒட்டன்சத்திரம்", "தொப்பம்பட்டி", "வேடசந்தூர்", "வடமதுரை", "குஜிலியம்பாறை", "கொடைக்கானல்"),
      cities = listOf("திண்டுக்கல் மாநகரம்", "பழனி நகராட்சி", "கொடைக்கானல் நகராட்சி", "ஒட்டன்சத்திரம் நகராட்சி"),
      youthWingUnits = listOf("திண்டுக்கல் மாவட்ட இளைஞரணி", "பழனி நகர இளைஞரணி", "நிலக்கோட்டை ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "THANJAVUR",
      tamilName = "தஞ்சாவூர்",
      englishName = "Thanjavur",
      headquarters = "தஞ்சாவூர்",
      zone = "மத்திய & டெல்டா மண்டலம் (Central & Delta Zone)",
      unions = listOf("தஞ்சாவூர்", "பூதலூர்", "திருவையாறு", "ஒரத்தநாடு", "திருவோணம்", "கும்பகோணம்", "திருவிடைமருதூர்", "திருப்பனந்தாள்", "பாபநாசம்", "அம்மாபேட்டை", "பட்டுக்கோட்டை", "மதுக்கூர்", "பேராவூரணி", "சேதுபாவாசத்திரம்"),
      cities = listOf("தஞ்சாவூர் மாநகரம்", "கும்பகோணம் மாநகரம்", "பட்டுக்கோட்டை நகராட்சி", "திருவையாறு பேரூராட்சி"),
      youthWingUnits = listOf("தஞ்சை மாவட்ட இளைஞரணி", "கும்பகோணம் மாநகர இளைஞரணி", "பட்டுக்கோட்டை ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "VELLORE",
      tamilName = "வேலூர்",
      englishName = "Vellore",
      headquarters = "வேலூர்",
      zone = "வட மண்டலம் (North Zone)",
      unions = listOf("வேலூர்", "காட்பாடி", "கே.வி.குப்பம்", "குடியாத்தம்", "பேரணாம்பட்டு", "அணைக்கட்டு", "கணியம்பாடி"),
      cities = listOf("வேலூர் மாநகரம்", "குடியாத்தம் நகராட்சி", "பேரணாம்பட்டு நகராட்சி"),
      youthWingUnits = listOf("வேலூர் மாவட்ட இளைஞரணி", "வேலூர் மாநகர இளைஞரணி", "குடியாத்தம் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "TIRUPATHUR",
      tamilName = "திருப்பத்தூர்",
      englishName = "Tirupathur",
      headquarters = "திருப்பத்தூர்",
      zone = "வட மண்டலம் (North Zone)",
      unions = listOf("திருப்பத்தூர்", "ஜோலார்பேட்டை", "கந்திலி", "நாட்டறம்பள்ளி", "வாணியம்பாடி", "ஆலங்காயம்", "மாதனூர்"),
      cities = listOf("திருப்பத்தூர் நகராட்சி", "வாணியம்பாடி நகராட்சி", "ஆம்பூர் நகராட்சி", "ஜோலார்பேட்டை நகராட்சி"),
      youthWingUnits = listOf("திருப்பத்தூர் மாவட்ட இளைஞரணி", "ஆம்பூர் நகர இளைஞரணி", "வாணியம்பாடி ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "RANIPET",
      tamilName = "இராணிப்பேட்டை",
      englishName = "Ranipet",
      headquarters = "ராணிப்பேட்டை",
      zone = "வட மண்டலம் (North Zone)",
      unions = listOf("வாலாஜா", "சோளிங்கர்", "ஆற்காடு", "திமிரி", "அரக்கோணம்", "நெமிலி", "காவேரிப்பாக்கம்"),
      cities = listOf("ராணிப்பேட்டை நகராட்சி", "ஆற்காடு நகராட்சி", "அரக்கோணம் நகராட்சி", "வாலாஜாபேட்டை நகராட்சி"),
      youthWingUnits = listOf("ராணிப்பேட்டை மாவட்ட இளைஞரணி", "அரக்கோணம் நகர இளைஞரணி", "சோளிங்கர் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "TIRUVANNAMALAI",
      tamilName = "திருவண்ணாமலை",
      englishName = "Tiruvannamalai",
      headquarters = "திருவண்ணாமலை",
      zone = "வட மண்டலம் (North Zone)",
      unions = listOf("திருவண்ணாமலை", "துரிஞ்சாபுரம்", "கீழ்பெண்ணாத்தூர்", "போளூர்", "கலசப்பாக்கம்", "செங்கம்", "புதுப்பாளையம்", "ஜமுனாமரத்தூர்", "ஆரணி", "மேற்கு ஆரணி", "செய்யாறு", "அனக்காவூர்", "வெம்பாக்கம்", "வந்தவாசி", "தெள்ளாறு", "பெரணமல்லூர்"),
      cities = listOf("திருவண்ணாமலை நகராட்சி", "ஆரணி நகராட்சி", "செய்யாறு நகராட்சி", "வந்தவாசி நகராட்சி"),
      youthWingUnits = listOf("திருவண்ணாமலை மாவட்ட இளைஞரணி", "ஆரணி நகர இளைஞரணி", "செய்யாறு ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "VILLUPURAM",
      tamilName = "விழுப்புரம்",
      englishName = "Villupuram",
      headquarters = "விழுப்புரம்",
      zone = "வட மண்டலம் (North Zone)",
      unions = listOf("விழுப்புரம்", "கோலியனூர்", "கண்டமங்கலம்", "விக்கிரவாண்டி", "காணை", "வானூர்", "மரக்காணம்", "திண்டிவனம்", "ஒலக்கூர்", "மயிலம்", "செஞ்சி", "வல்லம்", "மேல்மலையனூர்"),
      cities = listOf("விழுப்புரம் நகராட்சி", "திண்டிவனம் நகராட்சி"),
      youthWingUnits = listOf("விழுப்புரம் மாவட்ட இளைஞரணி", "திண்டிவனம் நகர இளைஞரணி", "செஞ்சி ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "KALLAKURICHI",
      tamilName = "கள்ளக்குறிச்சி",
      englishName = "Kallakurichi",
      headquarters = "கள்ளக்குறிச்சி",
      zone = "வட மண்டலம் (North Zone)",
      unions = listOf("கள்ளக்குறிச்சி", "சின்னசேலம்", "தியாகதுருகம்", "ரிஷிவந்தியம்", "சங்கராபுரம்", "கல்வராயன்மலை", "உளுந்தூர்பேட்டை", "திருநாவலூர்", "திருவெண்ணெய்நல்லூர்"),
      cities = listOf("கள்ளக்குறிச்சி நகராட்சி", "உளுந்தூர்பேட்டை நகராட்சி", "சின்னசேலம் பேரூராட்சி"),
      youthWingUnits = listOf("கள்ளக்குறிச்சி மாவட்ட இளைஞரணி", "உளுந்தூர்பேட்டை ஒன்றிய இளைஞரணி", "சங்கராபுரம் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "CUDDALORE",
      tamilName = "கடலூர்",
      englishName = "Cuddalore",
      headquarters = "கடலூர்",
      zone = "வட மண்டலம் (North Zone)",
      unions = listOf("கடலூர்", "குறிஞ்சிப்பாடி", "பண்ருட்டி", "அண்ணாகிராமம்", "விருத்தாசலம்", "கம்மாபுரம்", "நல்லூர்", "மங்களூர்", "சிதம்பரம்", "பரங்கிப்பேட்டை", "குமராட்சி", "காட்டுமன்னார்கோவில்", "ஸ்ரீமுஷ்ணம்"),
      cities = listOf("கடலூர் மாநகரம்", "சிதம்பரம் நகராட்சி", "பண்ருட்டி நகராட்சி", "விருத்தாசலம் நகராட்சி", "நெய்வேலி நகரியம்"),
      youthWingUnits = listOf("கடலூர் மாவட்ட இளைஞரணி", "சிதம்பரம் நகர இளைஞரணி", "விருத்தாசலம் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "MAYILADUTHURAI",
      tamilName = "மயிலாடுதுறை",
      englishName = "Mayiladuthurai",
      headquarters = "மயிலாடுதுறை",
      zone = "மத்திய & டெல்டா மண்டலம் (Central & Delta Zone)",
      unions = listOf("மயிலாடுதுறை", "குத்தாலம்", "செம்பன Survivors", "செம்பனார்கோவில்", "சீர்காழி", "கொள்ளிடம்"),
      cities = listOf("மயிலாடுதுறை நகராட்சி", "சீர்காழி நகராட்சி"),
      youthWingUnits = listOf("மயிலாடுதுறை மாவட்ட இளைஞரணி", "சீர்காழி நகர இளைஞரணி", "குத்தாலம் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "NAGAPATTINAM",
      tamilName = "நாகப்பட்டினம்",
      englishName = "Nagapattinam",
      headquarters = "நாகப்பட்டினம்",
      zone = "மத்திய & டெல்டா மண்டலம் (Central & Delta Zone)",
      unions = listOf("நாகப்பட்டினம்", "திருமருகல்", "கீழ்வேளூர்", "கீழையூர்", "தலைஞாயிறு", "வேதாரண்யம்"),
      cities = listOf("நாகப்பட்டினம் நகராட்சி", "வேதாரண்யம் நகராட்சி"),
      youthWingUnits = listOf("நாகப்பட்டினம் மாவட்ட இளைஞரணி", "வேதாரண்யம் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "TIRUVARUR",
      tamilName = "திருவாரூர்",
      englishName = "Tiruvarur",
      headquarters = "திருவாரூர்",
      zone = "மத்திய & டெல்டா மண்டலம் (Central & Delta Zone)",
      unions = listOf("திருவாரூர்", "குடவாசல்", "நன்னிலம்", "கொரடாச்சேரி", "மன்னார்குடி", "நீடாமங்கலம்", "கோட்டூர்", "திருத்துறைப்பூண்டி", "முத்துப்பேட்டை", "வலங்கைமான்"),
      cities = listOf("திருவாரூர் நகராட்சி", "மன்னார்குடி நகராட்சி", "திருத்துறைப்பூண்டி நகராட்சி"),
      youthWingUnits = listOf("திருவாரூர் மாவட்ட இளைஞரணி", "மன்னார்குடி நகர இளைஞரணி", "நன்னிலம் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "PUDUKKOTTAI",
      tamilName = "புதுக்கோட்டை",
      englishName = "Pudukkottai",
      headquarters = "புதுக்கோட்டை",
      zone = "மத்திய & டெல்டா மண்டலம் (Central & Delta Zone)",
      unions = listOf("புதுக்கோட்டை", "குன்றாண்டார்கோவில்", "கந்தர்வக்கோட்டை", "கறம்பக்குடி", "திருவரங்குளம்", "அன்னவாசல்", "விராலிமலை", "பொன்னமராவதி", "திருமயம்", "அரிமளம்", "ஆலங்குடி", "அறந்தாங்கி", "ஆவுடையார்கோவில்", "மணமேல்குடி"),
      cities = listOf("புதுக்கோட்டை மாநகரம்", "அறந்தாங்கி நகராட்சி"),
      youthWingUnits = listOf("புதுக்கோட்டை மாவட்ட இளைஞரணி", "அறந்தாங்கி நகர இளைஞரணி", "விராலிமலை ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "SIVAGANGAI",
      tamilName = "சிவகங்கை",
      englishName = "Sivagangai",
      headquarters = "சிவகங்கை",
      zone = "தென் மண்டலம் (South Zone)",
      unions = listOf("சிவகங்கை", "காளையார்கோவில்", "மானாமதுரை", "இளையான்குடி", "திருப்புவனம்", "காரைக்குடி", "சாக்கோட்டை", "கண்ணங்குடி", "தேவகோட்டை", "சிங்கம்புணரி", "திருப்பத்தூர்", "எஸ்.புதூர்"),
      cities = listOf("சிவகங்கை நகராட்சி", "காரைக்குடி மாநகரம்", "தேவகோட்டை நகராட்சி", "மானாமதுரை நகராட்சி"),
      youthWingUnits = listOf("சிவகங்கை மாவட்ட இளைஞரணி", "காரைக்குடி நகர இளைஞரணி", "மானாமதுரை ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "RAMANATHAPURAM",
      tamilName = "இராமநாதபுரம்",
      englishName = "Ramanathapuram",
      headquarters = "ராமநாதபுரம்",
      zone = "தென் மண்டலம் (South Zone)",
      unions = listOf("இராமநாதபுரம்", "திருப்புல்லாணி", "மண்டபம்", "ராமேஸ்வரம்", "பரமக்குடி", "போகலூர்", "நயினார்கோவில்", "முதுகுளத்தூர்", "கடலாடி", "கமுதி", "திருவாடனை", "ஆர்.எஸ்.மங்கலம்"),
      cities = listOf("ராமநாதபுரம் நகராட்சி", "பரமக்குடி நகராட்சி", "ராமேஸ்வரம் நகராட்சி", "கீழக்கரை நகராட்சி"),
      youthWingUnits = listOf("ராமநாதபுரம் மாவட்ட இளைஞரணி", "பரமக்குடி நகர இளைஞரணி", "ராமேஸ்வரம் நகர இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "VIRUDHUNAGAR",
      tamilName = "விருதுநகர்",
      englishName = "Virudhunagar",
      headquarters = "விருதுநகர்",
      zone = "தென் மண்டலம் (South Zone)",
      unions = listOf("விருதுநகர்", "சிவகாசி", "வெம்பக்கோட்டை", "சாத்தூர்", "அருப்புக்கோட்டை", "காரியாபட்டி", "திருச்சுழி", "ஸ்ரீவில்லிபுத்தூர்", "ராஜபாளையம்", "வத்திராயிருப்பு", "நரிக்குடி"),
      cities = listOf("சிவகாசி மாநகரம்", "விருதுநகர் நகராட்சி", "ராஜபாளையம் நகராட்சி", "அருப்புக்கோட்டை நகராட்சி", "சாத்தூர் நகராட்சி", "ஸ்ரீவில்லிபுத்தூர் நகராட்சி"),
      youthWingUnits = listOf("விருதுநகர் மாவட்ட இளைஞரணி", "சிவகாசி மாநகர இளைஞரணி", "ராஜபாளையம் நகர இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "TENKASI",
      tamilName = "தென்காசி",
      englishName = "Tenkasi",
      headquarters = "தென்காசி",
      zone = "தென் மண்டலம் (South Zone)",
      unions = listOf("தென்காசி", "செங்கோட்டை", "கடையநல்லூர்", "சங்கரன்கோவில்", "மேலநீலிதநல்லூர்", "குறுவிகுளம்", "வாசுதேவநல்லூர்", "ஆலங்குளம்", "கீழப்பாவூர்"),
      cities = listOf("தென்காசி நகராட்சி", "செங்கோட்டை நகராட்சி", "கடையநல்லூர் நகராட்சி", "சங்கரன்கோவில் நகராட்சி", "புளியங்குடி நகராட்சி"),
      youthWingUnits = listOf("தென்காசி மாவட்ட இளைஞரணி", "சங்கரன்கோவில் நகர இளைஞரணி", "ஆலங்குளம் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "KANNIYAKUMARI",
      tamilName = "கன்னியாகுமரி",
      englishName = "Kanniyakumari",
      headquarters = "நாகர்கோவில்",
      zone = "தென் மண்டலம் (South Zone)",
      unions = listOf("அகத்தீஸ்வரம்", "தோவாளை", "ராஜாகமங்கலம்", "குருந்தன்கோடு", "திருவட்டார்", "தக்கலை", "முஞ்சிறை", "கிள்ளியூர்", "மேல்புறம்"),
      cities = listOf("நாகர்கோவில் மாநகரம்", "பத்மநாபபுரம் நகராட்சி", "குளச்சல் நகராட்சி", "குழித்துறை நகராட்சி"),
      youthWingUnits = listOf("கன்னியாகுமரி மாவட்ட இளைஞரணி", "நாகர்கோவில் மாநகர இளைஞரணி", "தக்கலை ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "THENI",
      tamilName = "தேனி",
      englishName = "Theni",
      headquarters = "தேனி அல்லிநகரம்",
      zone = "தென் மண்டலம் (South Zone)",
      unions = listOf("தேனி", "பெரியகுளம்", "ஆண்டிபட்டி", "கதம்பூர்", "போடிநாயக்கனூர்", "சின்னமனூர்", "உத்தமபாளையம்", "கம்பம்", "நெடுங்கண்டம்"),
      cities = listOf("தேனி அல்லிநகரம் நகராட்சி", "போடிநாயக்கனூர் நகராட்சி", "பெரியகுளம் நகராட்சி", "கம்பம் நகராட்சி", "சின்னமனூர் நகராட்சி"),
      youthWingUnits = listOf("தேனி மாவட்ட இளைஞரணி", "போடி நகர இளைஞரணி", "கம்பம் நகர இளைஞரணி", "ஆண்டிபட்டி ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "KARUR",
      tamilName = "கரூர்",
      englishName = "Karur",
      headquarters = "கரூர்",
      zone = "மத்திய & டெல்டா மண்டலம் (Central & Delta Zone)",
      unions = listOf("கரூர்", "தான்தோன்றி", "அரவக்குறிச்சி", "க.பரமத்தி", "குளித்தலை", "தோகைமலை", "கிருஷ்ணராயபுரம்", "கடவூர்"),
      cities = listOf("கரூர் மாநகரம்", "குளித்தலை நகராட்சி", "புகழூர் நகராட்சி"),
      youthWingUnits = listOf("கரூர் மாவட்ட இளைஞரணி", "கரூர் மாநகர இளைஞரணி", "குளித்தலை ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "ARIYALUR",
      tamilName = "அரியலூர்",
      englishName = "Ariyalur",
      headquarters = "அரியலூர்",
      zone = "மத்திய & டெல்டா மண்டலம் (Central & Delta Zone)",
      unions = listOf("அரியலூர்", "திருமானூர்", "செந்துறை", "ஜெயங்கொண்டம்", "ஆண்டிமடம்", "தா.பழூர்"),
      cities = listOf("அரியலூர் நகராட்சி", "ஜெயங்கொண்டம் நகராட்சி"),
      youthWingUnits = listOf("அரியலூர் மாவட்ட இளைஞரணி", "ஜெயங்கொண்டம் நகர இளைஞரணி", "செந்துறை ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "PERAMBALUR",
      tamilName = "பெரம்பலூர்",
      englishName = "Perambalur",
      headquarters = "பெரம்பலூர்",
      zone = "மத்திய & டெல்டா மண்டலம் (Central & Delta Zone)",
      unions = listOf("பெரம்பலூர்", "வேப்பூர்", "வேப்பந்தட்டை", "ஆலத்தூர்"),
      cities = listOf("பெரம்பலூர் நகராட்சி", "லப்பைக்குடிகாடு பேரூராட்சி", "குரும்பலூர் பேரூராட்சி"),
      youthWingUnits = listOf("பெரம்பலூர் மாவட்ட இளைஞரணி", "பெரம்பலூர் நகர இளைஞரணி", "வேப்பூர் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "NAMAKKAL",
      tamilName = "நாமக்கல்",
      englishName = "Namakkal",
      headquarters = "நாமக்கல்",
      zone = "மேற்கு மண்டலம் (West Zone)",
      unions = listOf("நாமக்கல்", "மோகனூர்", "பரமத்தி", "கபிலர்மலை", "எருமப்பட்டி", "சேந்தமங்கலம்", "கொல்லிமலை", "ராசிபுரம்", "வெண்ணந்தூர்", "நாமகிரிப்பேட்டை", "திருச்செங்கோடு", "எலச்சிப்பாளையம்", "மல்லசமுத்திரம்", "புதுச்சத்திரம்", "காளப்பநாயக்கன்பட்டி"),
      cities = listOf("நாமக்கல் மாநகரம்", "திருச்செங்கோடு நகராட்சி", "ராசிபுரம் நகராட்சி", "குமாரபாளையம் நகராட்சி"),
      youthWingUnits = listOf("நாமக்கல் மாவட்ட இளைஞரணி", "திருச்செங்கோடு நகர இளைஞரணி", "ராசிபுரம் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "DHARMAPURI",
      tamilName = "தர்மபுரி",
      englishName = "Dharmapuri",
      headquarters = "தர்மபுரி",
      zone = "வட மண்டலம் (North Zone)",
      unions = listOf("தர்மபுரி", "நல்லம்பள்ளி", "பென்னாகரம்", "ஏரியூர்", "பாலக்கோடு", "காரிமங்கலம்", "மொரப்பூர்", "அரூர்", "பாப்பிரெட்டிப்பட்டி", "கடத்தூர்"),
      cities = listOf("தர்மபுரி நகராட்சி", "அரூர் பேரூராட்சி", "பாலக்கோடு பேரூராட்சி"),
      youthWingUnits = listOf("தர்மபுரி மாவட்ட இளைஞரணி", "அரூர் ஒன்றிய இளைஞரணி", "பாலக்கோடு ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "KRISHNAGIRI",
      tamilName = "கிருஷ்ணகிரி",
      englishName = "Krishnagiri",
      headquarters = "கிருஷ்ணகிரி",
      zone = "வட மண்டலம் (North Zone)",
      unions = listOf("கிருஷ்ணகிரி", "பர்கூர்", "காவேரிப்பட்டினம்", "வேப்பனப்பள்ளி", "ஓசூர்", "சூளகிரி", "கெலமங்கலம்", "தளி", "உத்தங்கரை", "மத்தூர்"),
      cities = listOf("ஓசூர் மாநகரம்", "கிருஷ்ணகிரி நகராட்சி"),
      youthWingUnits = listOf("கிருஷ்ணகிரி மாவட்ட இளைஞரணி", "ஓசூர் மாநகர இளைஞரணி", "பர்கூர் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "KANCHIPURAM",
      tamilName = "காஞ்சிபுரம்",
      englishName = "Kanchipuram",
      headquarters = "காஞ்சிபுரம்",
      zone = "சென்னை பெருநகர மண்டலம் (Chennai Metro Zone)",
      unions = listOf("காஞ்சிபுரம்", "வாலாஜாபாத்", "உத்திரமேரூர்", "ஸ்ரீபெரும்புதூர்", "குன்றத்தூர்"),
      cities = listOf("காஞ்சிபுரம் மாநகரம்", "ஸ்ரீபெரும்புதூர் பேரூராட்சி", "உத்திரமேரூர் பேரூராட்சி"),
      youthWingUnits = listOf("காஞ்சிபுரம் மாவட்ட இளைஞரணி", "ஸ்ரீபெரும்புதூர் ஒன்றிய இளைஞரணி", "வாலாஜாபாத் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "CHENGALPATTU",
      tamilName = "செங்கல்பட்டு",
      englishName = "Chengalpattu",
      headquarters = "செங்கல்பட்டு",
      zone = "சென்னை பெருநகர மண்டலம் (Chennai Metro Zone)",
      unions = listOf("செங்கல்பட்டு", "காட்டாங்கொளத்தூர்", "திருப்போரூர்", "திருக்கழுக்குன்றம்", "மதுராந்தகம்", "சித்தாமூர்", "லத்தூர்", "அச்சரப்பாக்கம்"),
      cities = listOf("தாம்பரம் மாநகரம்", "செங்கல்பட்டு நகராட்சி", "மதுராந்தகம் நகராட்சி", "மறைமலைநகர் நகராட்சி"),
      youthWingUnits = listOf("செங்கல்பட்டு மாவட்ட இளைஞரணி", "தாம்பரம் மாநகர இளைஞரணி", "மதுராந்தகம் ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "TIRUVALLUR",
      tamilName = "திருவள்ளூர்",
      englishName = "Tiruvallur",
      headquarters = "திருவள்ளூர்",
      zone = "சென்னை பெருநகர மண்டலம் (Chennai Metro Zone)",
      unions = listOf("திருவள்ளூர்", "பூந்தமல்லி", "வில்லிவாக்கம்", "புழல்", "மீஞ்சூர்", "சோழவரம்", "கும்மிடிப்பூண்டி", "எல்லாபுரம்", "கடம்த்தூர்", "திருவாலங்காடு", "திருத்தணி", "பள்ளிப்பட்டு", "ஆர்.கே.பேட்டை"),
      cities = listOf("ஆவடி மாநகரம்", "திருவள்ளூர் நகராட்சி", "பூந்தமல்லி நகராட்சி", "திருத்தணி நகராட்சி"),
      youthWingUnits = listOf("திருவள்ளூர் மாவட்ட இளைஞரணி", "ஆவடி மாநகர இளைஞரணி", "திருத்தணி ஒன்றிய இளைஞரணி")
    ),
    DistrictStructuralData(
      districtCode = "NILGIRIS",
      tamilName = "நீலகிரி",
      englishName = "Nilgiris",
      headquarters = "உதகமண்டலம் (ஊட்டி)",
      zone = "மேற்கு மண்டலம் (West Zone)",
      unions = listOf("உதகமண்டலம்", "குன்னூர்", "கோத்தகிரி", "கூடலூர்"),
      cities = listOf("உதகமண்டலம் (ஊட்டி) நகராட்சி", "குன்னூர் நகராட்சி", "கூடலூர் நகராட்சி", "நெல்லியாளம் நகராட்சி"),
      youthWingUnits = listOf("நீலகிரி மாவட்ட இளைஞரணி", "ஊட்டி நகர இளைஞரணி", "கூடலூர் ஒன்றிய இளைஞரணி")
    )
  )

  /**
   * Initializes the Firestore `tnpa_districts` root collection with all 38 districts.
   * For each district document, sets:
   * - Top-level fields: tamilName, englishName, headquarters, zone, unionsCount, citiesCount, youthWingUnitsCount
   * - Embedded lists / arrays for offline-first instant retrieval
   * - Sub-collections: `unions`, `cities`, `youth_wings`, and `office_bearers`
   */
  suspend fun initializeAllDistrictsToFirestore(
    onProgress: (Int, Int, String) -> Unit = { _, _, _ -> }
  ): Result<Int> {
    return try {
      val total = DISTRICT_STRUCTURES.size
      var count = 0

      DISTRICT_STRUCTURES.forEachIndexed { index, struct ->
        val districtDocRef = firestore.collection("tnpa_districts").document(struct.districtCode)

        // 1. Root District Document Meta
        val districtMeta = hashMapOf(
          "districtCode" to struct.districtCode,
          "tamilName" to struct.tamilName,
          "englishName" to struct.englishName,
          "headquarters" to struct.headquarters,
          "zone" to struct.zone,
          "unions" to struct.unions,
          "cities" to struct.cities,
          "youthWingUnits" to struct.youthWingUnits,
          "unionsCount" to struct.unions.size,
          "citiesCount" to struct.cities.size,
          "youthWingUnitsCount" to struct.youthWingUnits.size,
          "updatedAt" to System.currentTimeMillis()
        )
        districtDocRef.set(districtMeta, SetOptions.merge()).await()

        // 2. Sub-Collection: Unions (ஒன்றியங்கள்)
        struct.unions.forEach { unionName ->
          val unionDoc = districtDocRef.collection("unions").document(unionName.replace(" ", "_"))
          unionDoc.set(
            hashMapOf(
              "unionName" to unionName,
              "districtCode" to struct.districtCode,
              "districtName" to struct.tamilName,
              "type" to "UNION",
              "isActive" to true,
              "updatedAt" to System.currentTimeMillis()
            ),
            SetOptions.merge()
          ).await()
        }

        // 3. Sub-Collection: Cities (நகரங்கள் / நகராட்சிகள்)
        struct.cities.forEach { cityName ->
          val cityDoc = districtDocRef.collection("cities").document(cityName.replace(" ", "_"))
          cityDoc.set(
            hashMapOf(
              "cityName" to cityName,
              "districtCode" to struct.districtCode,
              "districtName" to struct.tamilName,
              "type" to "CITY",
              "isActive" to true,
              "updatedAt" to System.currentTimeMillis()
            ),
            SetOptions.merge()
          ).await()
        }

        // 4. Sub-Collection: Youth Wing Units (இளைஞரணி அமைப்புகள்)
        struct.youthWingUnits.forEach { youthWingName ->
          val youthDoc = districtDocRef.collection("youth_wings").document(youthWingName.replace(" ", "_"))
          youthDoc.set(
            hashMapOf(
              "youthWingName" to youthWingName,
              "districtCode" to struct.districtCode,
              "districtName" to struct.tamilName,
              "type" to "YOUTH_WING",
              "isActive" to true,
              "updatedAt" to System.currentTimeMillis()
            ),
            SetOptions.merge()
          ).await()
        }

        count++
        onProgress(count, total, "${struct.tamilName} (${struct.englishName})")
        Log.d("FirestoreDistrictInit", "[$count/$total] Initialized district: ${struct.englishName}")
      }

      Result.success(count)
    } catch (e: Exception) {
      Log.e("FirestoreDistrictInit", "Error initializing Firestore districts: ${e.message}", e)
      Result.failure(e)
    }
  }

  /**
   * Syncs a hierarchy office bearer to the district's `office_bearers` sub-collection in Firestore.
   */
  suspend fun syncBearerToFirestore(bearer: HierarchyOfficeBearer): Result<String> {
    return try {
      val districtCode = DISTRICT_STRUCTURES.find {
        bearer.district.contains(it.tamilName, ignoreCase = true) ||
          bearer.district.contains(it.englishName, ignoreCase = true)
      }?.districtCode ?: "STATE_HQ"

      val docRef = if (bearer.level == AdminHierarchyLevel.STATE) {
        firestore.collection("tnpa_state_leadership").document(bearer.id)
      } else {
        firestore.collection("tnpa_districts")
          .document(districtCode)
          .collection("office_bearers")
          .document(bearer.id)
      }

      val data = hashMapOf(
        "id" to bearer.id,
        "fullName" to bearer.fullName,
        "tamilName" to bearer.tamilName,
        "designation" to bearer.designation,
        "level" to bearer.level.id,
        "levelTamil" to bearer.level.labelTamil,
        "district" to bearer.district,
        "zone" to bearer.zone,
        "unionName" to bearer.unionName,
        "cityName" to bearer.cityName,
        "mobile" to bearer.mobile,
        "altPhone" to bearer.altPhone,
        "startDate" to bearer.startDate,
        "endDate" to bearer.endDate,
        "isActive" to bearer.isActive,
        "appointedByAdmin" to bearer.appointedByAdmin,
        "notes" to bearer.notes,
        "updatedAt" to System.currentTimeMillis()
      )

      docRef.set(data, SetOptions.merge()).await()
      Result.success(docRef.path)
    } catch (e: Exception) {
      Log.e("FirestoreDistrictInit", "Failed to sync bearer ${bearer.id}: ${e.message}", e)
      Result.failure(e)
    }
  }
}
