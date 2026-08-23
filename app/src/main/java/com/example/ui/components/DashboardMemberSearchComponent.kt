package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.SubcomposeAsyncImage
import com.example.data.FirestoreMemberRepository
import com.example.data.OfficeBearerRepository
import com.example.model.AdminHierarchyLevel
import com.example.model.MemberProfile
import com.example.ui.theme.TnpaCharcoal
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedPrimary
import com.example.ui.theme.TnpaRedSoft

/**
 * Unified Searchable Member Item for the Dashboard.
 */
data class DashboardSearchableMember(
  val id: String,
  val fullName: String,
  val tamilName: String,
  val district: String,
  val designation: String,
  val specialization: String,
  val mobile: String,
  val photoUrl: String? = null,
  val experienceYears: Int = 8,
  val bloodGroup: String = "O+",
  val isOfficeBearer: Boolean = false,
  val status: String = "செயலில் உள்ளது (Active)"
)

/**
 * Curated master directory of members & office bearers across Tamil Nadu districts.
 */
object MasterMemberDirectoryData {
  val DEFAULT_MEMBERS = listOf(
    DashboardSearchableMember(
      id = "TNPA-M-2026-001",
      fullName = "S. Michael Alvin",
      tamilName = "எஸ். மைக்கேல் ஆல்வின்",
      district = "மதுரை மாவட்டம் (Madurai)",
      designation = "மாநிலத் தலைவர் (State President)",
      specialization = "நிர்வாகம் & அமைப்புசாரா தொழிலாளர் நலன்",
      mobile = "9789331681",
      photoUrl = LeaderPhotoAssets.MICHAEL_ALVIN_PHOTO,
      experienceYears = 22,
      bloodGroup = "O+",
      isOfficeBearer = true
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-002",
      fullName = "Xavier Babu",
      tamilName = "சேவியர் பாபு",
      district = "மதுரை மாவட்டம் (Madurai)",
      designation = "மாநில பொதுச் செயலாளர் (General Secretary)",
      specialization = "தொழிற்சங்க வழிகாட்டி & நிர்வாகம்",
      mobile = "7010131915",
      photoUrl = LeaderPhotoAssets.XAVIER_BABU_PHOTO,
      experienceYears = 20,
      bloodGroup = "B+",
      isOfficeBearer = true
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-003",
      fullName = "Sakthivel",
      tamilName = "சக்திவேல்",
      district = "திருச்சிராப்பள்ளி (Tiruchirappalli)",
      designation = "மாநில பொருளாளர் (State Treasurer)",
      specialization = "கட்டிட பெயிண்டிங் & நிதி மேலாண்மை",
      mobile = "9080047281",
      photoUrl = LeaderPhotoAssets.SAKTHIVEL_PHOTO,
      experienceYears = 18,
      bloodGroup = "A+",
      isOfficeBearer = true
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-081",
      fullName = "Karthikeyan Muthu",
      tamilName = "கார்த்திகேயன் முத்து",
      district = "திருச்சிராப்பள்ளி (Tiruchirappalli)",
      designation = "மாஸ்டர் பெயிண்டர் (Master Painter)",
      specialization = "சுவர் ஓவியம் & பில்டிங் பெயிண்டிங்",
      mobile = "9843152431",
      photoUrl = LeaderPhotoAssets.DISTRICT_PRESIDENT_DEFAULT,
      experienceYears = 10,
      bloodGroup = "B+",
      isOfficeBearer = false
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-082",
      fullName = "Murugesan Arumugam",
      tamilName = "முருகேசன் ஆறுமுகம்",
      district = "மதுரை (Madurai)",
      designation = "ஓவியக் கலைஞர் (Fine Artist)",
      specialization = "கோவில் சித்திரம் & உருவப்படம் (Temple Art)",
      mobile = "9442167890",
      photoUrl = LeaderPhotoAssets.DISTRICT_SECRETARY_DEFAULT,
      experienceYears = 14,
      bloodGroup = "O+",
      isOfficeBearer = false
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-083",
      fullName = "Venkatesh Raman",
      tamilName = "வெங்கடேஷ் ராமன்",
      district = "சென்னை (Chennai)",
      designation = "அலங்காரப் பெயிண்டர் (Decorator)",
      specialization = "3D சுவர் கலை & டெக்ஸ்சர் பெயிண்டிங்",
      mobile = "9789123450",
      photoUrl = LeaderPhotoAssets.YOUTH_WING_DEFAULT,
      experienceYears = 7,
      bloodGroup = "A+",
      isOfficeBearer = false
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-077",
      fullName = "Selvaraj Chinnasamy",
      tamilName = "செல்வராஜ் சின்னசாமி",
      district = "கோயம்புத்தூர் (Coimbatore)",
      designation = "தொழில்துறை பெயிண்டர் (Industrial Painter)",
      specialization = "ஸ்பிரே பெயிண்டிங் & மெட்டல் கோட்டிங்",
      mobile = "9842112233",
      photoUrl = LeaderPhotoAssets.DISTRICT_PRESIDENT_DEFAULT,
      experienceYears = 12,
      bloodGroup = "O+",
      isOfficeBearer = false
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-088",
      fullName = "R. Sundaramoorthy",
      tamilName = "ஆர். சுந்தரமூர்த்தி",
      district = "திருச்சிராப்பள்ளி (Tiruchirappalli)",
      designation = "மாவட்டத் தலைவர் (District President)",
      specialization = "கட்டுமான ஆலோசனை & வண்ணக்கலை",
      mobile = "9442987654",
      photoUrl = LeaderPhotoAssets.DISTRICT_PRESIDENT_DEFAULT,
      experienceYears = 16,
      bloodGroup = "AB+",
      isOfficeBearer = true
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-092",
      fullName = "S. Ganesan",
      tamilName = "எஸ். கணேசன்",
      district = "மதுரை (Madurai)",
      designation = "மாவட்டச் செயலாளர் (District Secretary)",
      specialization = "அமைப்பாளர் & பில்டிங் ஒப்பந்ததாரர்",
      mobile = "9842198765",
      photoUrl = LeaderPhotoAssets.DISTRICT_SECRETARY_DEFAULT,
      experienceYears = 15,
      bloodGroup = "O+",
      isOfficeBearer = true
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-095",
      fullName = "K. Pandian",
      tamilName = "கே. பாண்டியன்",
      district = "சேலம் (Salem)",
      designation = "மாவட்ட தலைவர் (District President)",
      specialization = "வாகன பெயிண்டிங் & ஏர்பிரஷ்",
      mobile = "9842711223",
      photoUrl = LeaderPhotoAssets.DISTRICT_PRESIDENT_DEFAULT,
      experienceYears = 11,
      bloodGroup = "B+",
      isOfficeBearer = true
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-101",
      fullName = "A. Anthony Raj",
      tamilName = "அந்தோணி ராஜ்",
      district = "திருநெல்வேலி (Tirunelveli)",
      designation = "இளைஞரணி செயலாளர் (Youth Wing Sec)",
      specialization = "போர்டு ரைட்டிங் & ஃப்ளெக்ஸ் டிசைனிங்",
      mobile = "9443811234",
      photoUrl = LeaderPhotoAssets.YOUTH_WING_DEFAULT,
      experienceYears = 8,
      bloodGroup = "A+",
      isOfficeBearer = true
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-104",
      fullName = "M. Manickam",
      tamilName = "எம். மாணிக்கம்",
      district = "தஞ்சாவூர் (Thanjavur)",
      designation = "பாரம்பரிய சுவர் ஓவியர் (Heritage Artist)",
      specialization = "தஞ்சாவூர் ஓவியம் & கோவில் பொலிவு",
      mobile = "9443156789",
      photoUrl = LeaderPhotoAssets.DISTRICT_SECRETARY_DEFAULT,
      experienceYears = 19,
      bloodGroup = "O+",
      isOfficeBearer = false
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-108",
      fullName = "P. Veeramani",
      tamilName = "பி. வீரமணி",
      district = "திண்டுக்கல் (Dindigul)",
      designation = "உட்புற அலங்கார பெயிண்டர் (Interior Painter)",
      specialization = "வால்பேப்பர் & ராயல் பிளே டெக்ஸ்சர்",
      mobile = "9843212345",
      photoUrl = LeaderPhotoAssets.DISTRICT_PRESIDENT_DEFAULT,
      experienceYears = 9,
      bloodGroup = "B+",
      isOfficeBearer = false
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-112",
      fullName = "T. Sivakumar",
      tamilName = "டி. சிவக்குமார்",
      district = "ஈரோடு (Erode)",
      designation = "ஸ்பிரே டெக்னீசியன் (Spray Specialist)",
      specialization = "மர பாலிஷ் & பர்னிச்சர் பெயிண்டிங்",
      mobile = "9842512399",
      photoUrl = LeaderPhotoAssets.YOUTH_WING_DEFAULT,
      experienceYears = 13,
      bloodGroup = "O+",
      isOfficeBearer = false
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-115",
      fullName = "N. Balamurugan",
      tamilName = "என். பாலமுருகன்",
      district = "வேலூர் (Vellore)",
      designation = "மாவட்ட ஒருங்கிணைப்பாளர் (Coordinator)",
      specialization = "கட்டுமான ஒப்பந்தம் & மேற்பார்வை",
      mobile = "9443312388",
      photoUrl = LeaderPhotoAssets.DISTRICT_SECRETARY_DEFAULT,
      experienceYears = 14,
      bloodGroup = "A+",
      isOfficeBearer = true
    ),
    DashboardSearchableMember(
      id = "TNPA-M-2026-120",
      fullName = "G. Vijayakumar",
      tamilName = "ஜி. விஜயகுமார்",
      district = "கன்னியாகுமரி (Kanyakumari)",
      designation = "கடற்கரை பகுதி சிறப்பு பெயிண்டர்",
      specialization = "வெதர் புரூஃப் & வாட்டர்புரூபிங்",
      mobile = "9443912377",
      photoUrl = LeaderPhotoAssets.DISTRICT_PRESIDENT_DEFAULT,
      experienceYears = 11,
      bloodGroup = "AB+",
      isOfficeBearer = false
    )
  )

  val TOP_DISTRICT_FILTERS = listOf(
    "அனைத்தும் (All)",
    "மதுரை",
    "சென்னை",
    "திருச்சிராப்பள்ளி",
    "கோயம்புத்தூர்",
    "சேலம்",
    "திருநெல்வேலி",
    "தஞ்சாவூர்",
    "திண்டுக்கல்",
    "ஈரோடு",
    "வேலூர்",
    "விருதுநகர்",
    "கன்னியாகுமரி",
    "தூத்துக்குடி",
    "கடலூர்",
    "விழுப்புரம்",
    "திருப்பூர்"
  )

  val CATEGORY_FILTERS = listOf(
    "அனைத்து பிரிவுகள் (All)",
    "நிர்வாகிகள் (Office Bearers)",
    "உறுப்பினர்கள் (Members)",
    "மாஸ்டர் பெயிண்டர்",
    "ஓவியக் கலைஞர்"
  )
}

/**
 * High-performance, intuitive Member & District Search Bar Component on the Dashboard.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashboardMemberSearchBarAndDirectory(
  onNavigateToMemberRegistration: () -> Unit,
  onNavigateToLeadership: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current

  var searchQuery by remember { mutableStateOf("") }
  var selectedDistrict by remember { mutableStateOf("அனைத்தும் (All)") }
  var selectedCategory by remember { mutableStateOf("அனைத்து பிரிவுகள் (All)") }
  var selectedMemberForIdCard by remember { mutableStateOf<DashboardSearchableMember?>(null) }
  var showAllResults by remember { mutableStateOf(false) }

  // Observe live members from Firestore repo if available
  val firestoreRepo = remember { FirestoreMemberRepository() }
  val liveFirestoreMembers = remember { mutableStateOf<List<MemberProfile>>(emptyList()) }

  LaunchedEffect(Unit) {
    try {
      firestoreRepo.observeMembersRealtime().collect { list ->
        liveFirestoreMembers.value = list
      }
    } catch (e: Exception) {
      // Offline fallback
    }
  }

  // Combine Master directory + live firestore members + Office bearers
  val combinedMembers = remember(liveFirestoreMembers.value) {
    val liveList = liveFirestoreMembers.value.map { mem ->
      DashboardSearchableMember(
        id = mem.id,
        fullName = mem.fullName,
        tamilName = mem.tamilName,
        district = mem.district,
        designation = mem.designation,
        specialization = mem.specialization,
        mobile = mem.mobile,
        photoUrl = mem.photoUri,
        experienceYears = mem.experienceYears,
        bloodGroup = mem.bloodGroup,
        isOfficeBearer = false,
        status = mem.status
      )
    }

    val defaultList = MasterMemberDirectoryData.DEFAULT_MEMBERS
    // Merge without duplicates by ID
    val seenIds = mutableSetOf<String>()
    val result = mutableListOf<DashboardSearchableMember>()

    liveList.forEach { if (seenIds.add(it.id)) result.add(it) }
    defaultList.forEach { if (seenIds.add(it.id)) result.add(it) }
    result
  }

  // Filtered members based on search query, district filter, and category filter
  val filteredMembers = remember(searchQuery, selectedDistrict, selectedCategory, combinedMembers) {
    val queryTrimmed = searchQuery.trim().lowercase()

    combinedMembers.filter { member ->
      // District filter match
      val districtMatch = if (selectedDistrict == "அனைத்தும் (All)") {
        true
      } else {
        val cleanSelected = selectedDistrict.substringBefore(" (").trim()
        member.district.contains(cleanSelected, ignoreCase = true)
      }

      // Category filter match
      val categoryMatch = when (selectedCategory) {
        "அனைத்து பிரிவுகள் (All)" -> true
        "நிர்வாகிகள் (Office Bearers)" -> member.isOfficeBearer
        "உறுப்பினர்கள் (Members)" -> !member.isOfficeBearer
        "மாஸ்டர் பெயிண்டர்" -> member.designation.contains("மாஸ்டர்", ignoreCase = true) || member.specialization.contains("மாஸ்டர்", ignoreCase = true)
        "ஓவியக் கலைஞர்" -> member.designation.contains("ஓவிய", ignoreCase = true) || member.specialization.contains("ஓவிய", ignoreCase = true)
        else -> true
      }

      // Text Search match (Name, Tamil name, district, mobile, ID, specialization, designation)
      val queryMatch = if (queryTrimmed.isEmpty()) {
        true
      } else {
        member.fullName.lowercase().contains(queryTrimmed) ||
            member.tamilName.lowercase().contains(queryTrimmed) ||
            member.district.lowercase().contains(queryTrimmed) ||
            member.mobile.contains(queryTrimmed) ||
            member.id.lowercase().contains(queryTrimmed) ||
            member.designation.lowercase().contains(queryTrimmed) ||
            member.specialization.lowercase().contains(queryTrimmed)
      }

      districtMatch && categoryMatch && queryMatch
    }
  }

  // Limit displayed items initially to keep dashboard sleek, allow expansion
  val displayedMembers = if (showAllResults || searchQuery.isNotBlank() || selectedDistrict != "அனைத்தும் (All)") {
    filteredMembers
  } else {
    filteredMembers.take(4)
  }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 14.dp),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = BorderStroke(1.5.dp, TnpaRedSoft),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // 1. Header with Badge & Title
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(TnpaRedPrimary.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              Icons.Default.Search,
              contentDescription = null,
              tint = TnpaRedPrimary,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "உறுப்பினர்கள் தேடல் & பட்டியல்",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Black,
              color = TnpaJetBlack
            )
            Text(
              text = "Quick Member & District Finder",
              fontSize = 10.sp,
              color = Color(0xFF64748B),
              fontWeight = FontWeight.Medium
            )
          }
        }

        // Live Count Badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(TnpaGreen.copy(alpha = 0.15f))
            .border(1.dp, TnpaGreen.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = "${filteredMembers.size} நபர்கள்",
            color = TnpaGreen,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black
          )
        }
      }

      // 2. High-Visibility Search Input Bar
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("dashboard_member_search_input"),
        placeholder = {
          Text(
            text = "பெயர் அல்லது மாவட்டம் உள்ளிடவும் (e.g. கார்த்திகேயன், மதுரை)",
            fontSize = 12.sp,
            color = Color(0xFF94A3B8)
          )
        },
        leadingIcon = {
          Icon(
            Icons.Default.Search,
            contentDescription = "Search",
            tint = TnpaRedPrimary,
            modifier = Modifier.size(20.dp)
          )
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(
              onClick = { searchQuery = "" },
              modifier = Modifier.testTag("btn_clear_member_search")
            ) {
              Icon(
                Icons.Default.Clear,
                contentDescription = "Clear",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = TnpaRedPrimary,
          unfocusedBorderColor = Color(0xFFCBD5E1),
          focusedContainerColor = TnpaOffWhite,
          unfocusedContainerColor = TnpaOffWhite
        )
      )

      // 3. Quick District Filter Chips (Horizontal Scroll)
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "மாவட்ட வாரியாக வடிகட்டுக (Filter by District):",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TnpaCharcoal
          )
          if (selectedDistrict != "அனைத்தும் (All)") {
            TextButton(
              onClick = { selectedDistrict = "அனைத்தும் (All)" },
              modifier = Modifier.height(26.dp)
            ) {
              Text("Clear District", fontSize = 10.sp, color = TnpaRedPrimary, fontWeight = FontWeight.Bold)
            }
          }
        }

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          MasterMemberDirectoryData.TOP_DISTRICT_FILTERS.forEach { district ->
            val isSelected = selectedDistrict == district
            FilterChip(
              selected = isSelected,
              onClick = { selectedDistrict = district },
              label = {
                Text(
                  text = district,
                  fontSize = 11.sp,
                  fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium
                )
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = TnpaRedPrimary,
                selectedLabelColor = TnpaPureWhite,
                containerColor = Color(0xFFF1F5F9),
                labelColor = TnpaJetBlack
              ),
              border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFE2E8F0)),
              shape = RoundedCornerShape(8.dp)
            )
          }
        }
      }

      // 4. Quick Category Filter Chips
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        MasterMemberDirectoryData.CATEGORY_FILTERS.forEach { cat ->
          val isSelected = selectedCategory == cat
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(if (isSelected) TnpaGold else Color(0xFFF1F5F9))
              .border(
                1.dp,
                if (isSelected) Color(0xFFD97706) else Color(0xFFE2E8F0),
                RoundedCornerShape(6.dp)
              )
              .clickable { selectedCategory = cat }
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = cat,
              color = if (isSelected) TnpaJetBlack else Color(0xFF475569),
              fontSize = 10.5.sp,
              fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold
            )
          }
        }
      }

      HorizontalDivider(color = Color(0xFFF1F5F9))

      // 5. Search Results List / Empty State
      if (filteredMembers.isEmpty()) {
        // Zero Search Result Card
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Box(
            modifier = Modifier
              .size(48.dp)
              .clip(CircleShape)
              .background(Color(0xFFFEF2F2)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              Icons.Default.Person,
              contentDescription = null,
              tint = TnpaRedPrimary,
              modifier = Modifier.size(24.dp)
            )
          }
          Text(
            text = "உறுப்பினர்கள் எவரும் கண்டறியப்படவில்லை",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = TnpaJetBlack
          )
          Text(
            text = "‘$searchQuery’ அல்லது ‘$selectedDistrict’ அடிப்படையில் பதிவு எதுவும் இல்லை. புதிய உறுப்பினரை பதிவு செய்ய கீழேயுள்ள பொத்தானை அழுத்தவும்.",
            fontSize = 11.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
          )

          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 4.dp)
          ) {
            OutlinedButton(
              onClick = {
                searchQuery = ""
                selectedDistrict = "அனைத்தும் (All)"
                selectedCategory = "அனைத்து பிரிவுகள் (All)"
              },
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("வடிகட்டியை மீட்டமைக்க (Reset)", fontSize = 11.sp)
            }

            Button(
              onClick = onNavigateToMemberRegistration,
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
            ) {
              Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("புதிய உறுப்பினர் பதிவு", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      } else {
        // Display Member Cards
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          displayedMembers.forEach { member ->
            DashboardMemberResultCard(
              member = member,
              onViewIdCard = { selectedMemberForIdCard = member }
            )
          }

          // Show More / Show Less Button
          if (filteredMembers.size > 4 && searchQuery.isBlank() && selectedDistrict == "அனைத்தும் (All)") {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Center
            ) {
              TextButton(
                onClick = { showAllResults = !showAllResults },
                modifier = Modifier.testTag("btn_toggle_more_members")
              ) {
                Text(
                  text = if (showAllResults) "குறைவாக காட்டுக ▲" else "அனைத்து ${filteredMembers.size} உறுப்பினர்களையும் காண்க ▼",
                  color = TnpaRedPrimary,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }

      // Bottom Quick Action Bar
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Button(
          onClick = onNavigateToMemberRegistration,
          modifier = Modifier
            .weight(1f)
            .height(38.dp)
            .testTag("btn_dashboard_add_member"),
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
        ) {
          Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(15.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("புதிய உறுப்பினர் சேர்க்கை", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
        }

        OutlinedButton(
          onClick = onNavigateToLeadership,
          modifier = Modifier
            .weight(1f)
            .height(38.dp)
            .testTag("btn_dashboard_view_leaders"),
          shape = RoundedCornerShape(8.dp),
          border = BorderStroke(1.dp, TnpaJetBlack),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = TnpaJetBlack)
        ) {
          Icon(Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(15.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("நிர்வாகிகள் பட்டியல்", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }

  // Digital ID Card Preview Dialog
  selectedMemberForIdCard?.let { member ->
    DashboardMemberDigitalIdDialog(
      member = member,
      onDismiss = { selectedMemberForIdCard = null },
      onNavigateToRegistration = onNavigateToMemberRegistration
    )
  }
}

/**
 * Individual Member Item Card in the Search Result List.
 */
@Composable
fun DashboardMemberResultCard(
  member: DashboardSearchableMember,
  onViewIdCard: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaOffWhite),
    border = BorderStroke(
      width = if (member.isOfficeBearer) 1.5.dp else 1.dp,
      color = if (member.isOfficeBearer) TnpaGold else Color(0xFFE2E8F0)
    )
  ) {
    Column(
      modifier = Modifier.padding(10.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          // Profile Photo / Laurel Ring
          LeaderProfilePhotoView(
            photoUrl = member.photoUrl,
            fullName = member.fullName,
            tamilName = member.tamilName,
            designation = member.designation,
            level = if (member.isOfficeBearer) AdminHierarchyLevel.STATE else AdminHierarchyLevel.DISTRICT,
            district = member.district,
            mobile = member.mobile,
            size = 46.dp,
            isTopLeader = member.isOfficeBearer,
            enableEnlargeOnClick = true
          )

          Spacer(modifier = Modifier.width(10.dp))

          Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = member.tamilName.ifBlank { member.fullName },
                fontWeight = FontWeight.Black,
                fontSize = 13.5.sp,
                color = TnpaJetBlack,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
              if (member.isOfficeBearer) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                  Icons.Default.Verified,
                  contentDescription = "Verified Officer",
                  tint = Color(0xFF22C55E),
                  modifier = Modifier.size(15.dp)
                )
              }
            }

            if (member.fullName.isNotBlank() && member.fullName != member.tamilName) {
              Text(
                text = member.fullName,
                fontSize = 10.5.sp,
                color = Color(0xFF64748B),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }

            // Designation & Specialization
            Text(
              text = member.designation,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = if (member.isOfficeBearer) TnpaRedDark else TnpaJetBlack
            )
          }
        }

        // District Tag
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (member.isOfficeBearer) TnpaGold.copy(alpha = 0.2f) else Color(0xFFE2E8F0))
            .border(
              1.dp,
              if (member.isOfficeBearer) TnpaGold else Color(0xFFCBD5E1),
              RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              Icons.Default.LocationOn,
              contentDescription = null,
              tint = if (member.isOfficeBearer) Color(0xFFB45309) else Color(0xFF475569),
              modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
              text = member.district.substringBefore(" (").replace("மாவட்டம்", "").trim(),
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = if (member.isOfficeBearer) Color(0xFF92400E) else TnpaJetBlack
            )
          }
        }
      }

      // Specialization pill & Member ID
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFFEDE9FE))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = "🎨 ${member.specialization.take(24)}",
            fontSize = 9.5.sp,
            color = Color(0xFF5B21B6),
            fontWeight = FontWeight.SemiBold
          )
        }

        Text(
          text = "ID: ${member.id}",
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF64748B)
        )
      }

      // Action Buttons (Call, WhatsApp, View ID)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        // Call Button
        Button(
          onClick = {
            if (member.mobile.isNotBlank()) {
              val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${member.mobile.replace(" ", "")}"))
              context.startActivity(callIntent)
            }
          },
          modifier = Modifier
            .weight(1f)
            .height(32.dp),
          shape = RoundedCornerShape(6.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 4.dp, vertical = 2.dp)
        ) {
          Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(13.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("அழைக்க", fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
        }

        // WhatsApp Button
        OutlinedButton(
          onClick = {
            try {
              val cleanPhone = member.mobile.replace("+91", "").replace(" ", "").trim()
              val url = "https://api.whatsapp.com/send?phone=91$cleanPhone"
              val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
              context.startActivity(intent)
            } catch (e: Exception) {
              Toast.makeText(context, "WhatsApp open error", Toast.LENGTH_SHORT).show()
            }
          },
          modifier = Modifier
            .weight(1f)
            .height(32.dp),
          shape = RoundedCornerShape(6.dp),
          border = BorderStroke(1.dp, Color(0xFF22C55E)),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF22C55E)),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 4.dp, vertical = 2.dp)
        ) {
          Text("வாட்ஸ்அப்", fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
        }

        // Digital ID Card Dialog Button
        OutlinedButton(
          onClick = onViewIdCard,
          modifier = Modifier
            .weight(1.2f)
            .height(32.dp),
          shape = RoundedCornerShape(6.dp),
          border = BorderStroke(1.dp, TnpaRedPrimary),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = TnpaRedPrimary),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 4.dp, vertical = 2.dp)
        ) {
          Icon(Icons.Default.QrCode2, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("டிஜிட்டல் ID", fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

/**
 * High-definition Digital ID Card Modal for quick viewing from search results.
 */
@Composable
fun DashboardMemberDigitalIdDialog(
  member: DashboardSearchableMember,
  onDismiss: () -> Unit,
  onNavigateToRegistration: () -> Unit
) {
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.92f)
        .clip(RoundedCornerShape(20.dp))
        .background(Color(0xFF0F172A))
        .border(2.dp, TnpaGold, RoundedCornerShape(20.dp)),
      color = Color(0xFF0F172A),
      tonalElevation = 8.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Top Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            TnpaOfficialEmblem(sizeDp = 28.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் (TNPA²)",
                color = TnpaPureWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black
              )
              Text(
                text = "உறுப்பினர் டிஜிட்டல் அடையாள அட்டை (Smart ID)",
                color = TnpaGold,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier
              .size(30.dp)
              .background(Color(0xFF334155), CircleShape)
          ) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TnpaPureWhite, modifier = Modifier.size(16.dp))
          }
        }

        HorizontalDivider(color = Color(0xFF334155))

        // ID Card Surface Body
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
          border = BorderStroke(1.5.dp, TnpaGold)
        ) {
          Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            // Header on card
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(TnpaRedPrimary)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text("TNPA SMART ID", color = TnpaPureWhite, fontSize = 9.sp, fontWeight = FontWeight.Black)
              }

              Text(
                text = member.id,
                color = TnpaJetBlack,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black
              )
            }

            // Member Info Row
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              LeaderProfilePhotoView(
                photoUrl = member.photoUrl,
                fullName = member.fullName,
                tamilName = member.tamilName,
                designation = member.designation,
                level = if (member.isOfficeBearer) AdminHierarchyLevel.STATE else AdminHierarchyLevel.DISTRICT,
                size = 64.dp,
                isTopLeader = member.isOfficeBearer,
                enableEnlargeOnClick = false
              )

              Spacer(modifier = Modifier.width(12.dp))

              Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                  text = member.tamilName,
                  fontWeight = FontWeight.Black,
                  fontSize = 14.sp,
                  color = TnpaJetBlack
                )
                Text(
                  text = member.fullName,
                  fontSize = 11.sp,
                  color = Color(0xFF475569)
                )
                Text(
                  text = member.designation,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = TnpaRedDark
                )
                Text(
                  text = "📍 ${member.district}",
                  fontSize = 10.5.sp,
                  color = Color(0xFF334155),
                  fontWeight = FontWeight.Medium
                )
              }
            }

            HorizontalDivider(color = Color(0xFFE2E8F0))

            // Details Grid
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                Text("தொழில் பிரிவு / Skill:", fontSize = 9.sp, color = Color.Gray)
                Text(member.specialization.take(20), fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
              }
              Column {
                Text("அனுபவம்:", fontSize = 9.sp, color = Color.Gray)
                Text("${member.experienceYears} ஆண்டுகள்", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
              }
              Column {
                Text("இரத்த வகை:", fontSize = 9.sp, color = Color.Gray)
                Text(member.bloodGroup, fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = TnpaRedPrimary)
              }
            }

            // QR Code Simulation Strip
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFFF8FAFC))
                .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(6.dp))
                .padding(6.dp),
              contentAlignment = Alignment.Center
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
              ) {
                Icon(Icons.Default.QrCode2, contentDescription = null, tint = TnpaJetBlack, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "QR குறியீடு: தமிழ்நாடு அரசு தொழிற்சங்க சட்டப்படி சரிபார்க்கப்பட்டது",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = TnpaGreen
                )
              }
            }
          }
        }

        // Action Buttons: Share & Copy ID
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedButton(
            onClick = {
              clipboardManager.setText(AnnotatedString("${member.tamilName} (${member.id}) - TNPA Painter Association, ${member.district}. Mobile: ${member.mobile}"))
              Toast.makeText(context, "உறுப்பினர் விவரங்கள் நகலெடுக்கப்பட்டது (Copied)", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.weight(1f).height(40.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color(0xFF94A3B8)),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TnpaPureWhite)
          ) {
            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(15.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("நகல் (Copy)", fontSize = 11.sp)
          }

          Button(
            onClick = {
              val shareText = "🏷️ தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் (TNPA²) - உறுப்பினர் அட்டை\nபெயர்: ${member.tamilName}\nஉறுப்பினர் எண்: ${member.id}\nபதவி: ${member.designation}\nமாவட்டம்: ${member.district}\nமொபைல்: ${member.mobile}\nபதிவெண்: TNMDUJCLMDUTU-50-26-0044"
              val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
              }
              context.startActivity(Intent.createChooser(sendIntent, "உறுப்பினர் ID பகிருங்கள்"))
            },
            modifier = Modifier.weight(1f).height(40.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TnpaGold, contentColor = TnpaJetBlack)
          ) {
            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(15.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("பகிர்வு (Share)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
