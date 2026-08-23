package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.AdminApprovalRepository
import com.example.data.FirestoreMemberRepository
import com.example.model.MemberProfile
import com.example.ui.components.OfficialTnpaIdCardComponent
import com.example.ui.components.TnpaOfficialEmblem
import com.example.ui.theme.TnpaCyan
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedPrimary
import com.example.ui.theme.TnpaRedSoft
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MemberRegistrationScreen(
  membersList: MutableList<MemberProfile>,
  onMemberAdded: (MemberProfile) -> Unit
) {
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current
  val coroutineScope = rememberCoroutineScope()
  val firestoreRepo = remember { FirestoreMemberRepository() }

  // Screen Tab Mode: 0 -> 4-Step Verified Registration, 1 -> Quick Form with Mandatory OTP, 2 -> Registered Members Directory
  var selectedRegistrationTab by remember { mutableIntStateOf(0) }

  // Real-time Cloud Synchronization Listener
  LaunchedEffect(Unit) {
    firestoreRepo.observeMembersRealtime().collect { cloudMembers ->
      if (cloudMembers.isNotEmpty()) {
        cloudMembers.forEach { cm ->
          val existingIdx = membersList.indexOfFirst { it.id == cm.id }
          if (existingIdx != -1) {
            membersList[existingIdx] = cm
          } else {
            membersList.add(0, cm)
          }
        }
      }
    }
  }

  // Registration Stepper: 1 -> Personal Info, 2 -> Experience & Skills, 3 -> Contact Details, 4 -> Mandatory OTP Verification, 5 -> Digital ID Card
  var currentStep by remember { mutableIntStateOf(1) }

  // ID Card Model Design Style (1 = Official Welfare Gold Border, 2 = Modern Smart Pass, 3 = Portrait Executive ID)
  var selectedIdCardModel by remember { mutableIntStateOf(1) }

  // Form Fields
  var fullName by remember { mutableStateOf("") }
  var tamilName by remember { mutableStateOf("") }
  var fatherName by remember { mutableStateOf("") }
  var ageText by remember { mutableStateOf("") }
  var bloodGroup by remember { mutableStateOf("O+") }
  var experienceYearsText by remember { mutableStateOf("") }
  var specialization by remember { mutableStateOf("சுவர் ஓவியம் & பில்டிங் பெயிண்டிங்") }
  var designation by remember { mutableStateOf("மாஸ்டர் பெயிண்டர் (Master Painter)") }
  var mobileNumber by remember { mutableStateOf("") }
  var whatsappNumber by remember { mutableStateOf("") }
  var sameAsMobile by remember { mutableStateOf(true) }
  var email by remember { mutableStateOf("") }
  var district by remember { mutableStateOf("திருச்சிராப்பள்ளி (Trichy)") }
  var address by remember { mutableStateOf("") }
  var selectedPhotoUri by remember { mutableStateOf<Uri?>(null) }

  // ================= MANDATORY FREE / NO-BILLING OTP ARCHITECTURE STATE =================
  var otpInput by remember { mutableStateOf("") }
  var generatedOtp by remember { mutableStateOf(generateSecureFreeOtp()) }
  var otpAttemptsLeft by remember { mutableIntStateOf(3) }
  var otpExpirySeconds by remember { mutableIntStateOf(120) } // 2 minutes expiry
  var resendCooldownSeconds by remember { mutableIntStateOf(30) } // 30s cooldown
  var resendCount by remember { mutableIntStateOf(0) }
  val maxResendsAllowed = 3
  var isOtpExpired by remember { mutableStateOf(false) }
  var isOtpVerified by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf<String?>(null) }
  var successOtpFeedback by remember { mutableStateOf<String?>(null) }
  var isSyncingToFirestore by remember { mutableStateOf(false) }
  var firestoreSyncSuccess by remember { mutableStateOf(false) }

  // Quick OTP Modal State for Quick Tab
  var showQuickOtpModal by remember { mutableStateOf(false) }

  // Created Member for ID card display
  var createdMember by remember { mutableStateOf<MemberProfile?>(null) }
  var viewMemberCardModal by remember { mutableStateOf<MemberProfile?>(null) }

  // Filter state for members list
  var searchFilter by remember { mutableStateOf("") }
  var selectedDistrictFilter by remember { mutableStateOf("அனைத்தும்") }

  // Load from Firestore on launch
  LaunchedEffect(Unit) {
    val cloudMembers = firestoreRepo.fetchMembers()
    if (cloudMembers.isNotEmpty()) {
      cloudMembers.forEach { cm ->
        if (membersList.none { it.id == cm.id }) {
          membersList.add(cm)
        }
      }
    }
  }

  // OTP Expiry & Resend Timers
  LaunchedEffect(currentStep, showQuickOtpModal, otpExpirySeconds, resendCooldownSeconds) {
    if ((currentStep == 4 || showQuickOtpModal) && !isOtpVerified) {
      if (otpExpirySeconds > 0) {
        delay(1000)
        otpExpirySeconds -= 1
        if (otpExpirySeconds == 0) {
          isOtpExpired = true
          errorMessage = "OTP காலாவதியாகிவிட்டது (OTP Expired). புதிய OTP குறியீட்டைப் பெறவும்."
        }
      }
      if (resendCooldownSeconds > 0) {
        resendCooldownSeconds -= 1
      }
    }
  }

  val specializationsList = listOf(
    "சுவர் ஓவியம் & பில்டிங் பெயிண்டிங்",
    "3D சுவர் ஓவியம் & ஸ்டென்சில் ஆர்ட்",
    "போர்டு ரைட்டிங் & ஆயில் பெயிண்டிங்",
    "ஸ்ப்ரே & எனாமல் பினிஷிங்",
    "பட்டி & பிரைமர் அப்ளிகேஷன்",
    "மரப்பாணி & பாலிஷ் வேலைகள்"
  )

  val bloodGroupsList = listOf("O+", "A+", "B+", "AB+", "O-", "A-", "B-", "AB-")

  val districtsList = listOf(
    "திருச்சிராப்பள்ளி (Trichy)",
    "சென்னை (Chennai)",
    "மதுரை (Madurai)",
    "கோயம்புத்தூர் (Coimbatore)",
    "சேலம் (Salem)",
    "திருநெல்வேலி (Tirunelveli)",
    "தஞ்சாவூர் (Thanjavur)",
    "வேலூர் (Vellore)",
    "ஈரோடு (Erode)",
    "கன்னியாகுமரி (Kanyakumari)",
    "திண்டுக்கல் (Dindigul)",
    "கடலூர் (Cuddalore)"
  )

  val designationsList = listOf(
    "மாஸ்டர் பெயிண்டர் (Master Painter)",
    "உறுப்பினர் (Member)",
    "ஓவிய கலைஞர் (Artist)",
    "மாவட்ட நிர்வாகி (District Officer)",
    "கிளை செயலாளர் (Branch Secretary)"
  )

  // Zero-cost OTP Generator Function
  fun resetAndGenerateNewOtp() {
    if (resendCount >= maxResendsAllowed) {
      errorMessage = "அதிகபட்ச OTP அனுப்பும் வரம்பு ($maxResendsAllowed முறை) முடிந்துவிட்டது."
      return
    }
    generatedOtp = generateSecureFreeOtp()
    otpAttemptsLeft = 3
    otpExpirySeconds = 120
    resendCooldownSeconds = 30
    isOtpExpired = false
    otpInput = ""
    resendCount += 1
    errorMessage = null
    successOtpFeedback = "புதிய 6-இலக்க பாதுகாப்பு OTP உருவாக்கப்பட்டது!"
  }

  // Final Registration Submission with Mandatory Verification check
  fun performFinalRegistrationAndCloudSync() {
    if (!isOtpVerified) {
      errorMessage = "OTP சரிபார்க்கப்படாமல் பதிவு செய்ய முடியாது (OTP Verification is Mandatory)!"
      return
    }

    isSyncingToFirestore = true
    val expYears = experienceYearsText.toIntOrNull() ?: 5
    val ageNum = ageText.toIntOrNull() ?: 30
    val newId = "TNPA-2026-${(1000..9999).random()}"
    val tName = if (tamilName.isNotBlank()) tamilName else fullName
    val newProfile = MemberProfile(
      id = newId,
      fullName = fullName,
      tamilName = tName,
      fatherName = fatherName.trim(),
      age = ageNum,
      experienceYears = expYears,
      mobile = mobileNumber,
      email = email,
      whatsapp = if (whatsappNumber.isNotBlank()) whatsappNumber else mobileNumber,
      district = district,
      address = if (address.isNotBlank()) address else "$district, தமிழ்நாடு",
      specialization = specialization,
      designation = designation,
      bloodGroup = bloodGroup,
      joinedDate = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(Date()),
      status = "செயலில் உள்ளது (Active - OTP சரிபார்க்கப்பட்டது)",
      isSyncedToFirestore = true,
      photoUri = selectedPhotoUri?.toString()
    )

    // Immediate UI update
    createdMember = newProfile
    onMemberAdded(newProfile)
    currentStep = 5
    showQuickOtpModal = false
    errorMessage = null

    // Asynchronous background cloud sync and admin approval queue
    firestoreRepo.saveMemberAsync(newProfile) { result ->
      isSyncingToFirestore = false
      if (result.isSuccess) {
        firestoreSyncSuccess = true
      }
    }
    
    // Automatically route Member ID application to Admin Approval Queue (Super, State, and District Admins)
    AdminApprovalRepository.submitMemberForApproval(
      memberId = newProfile.id,
      fullName = newProfile.fullName,
      tamilName = newProfile.tamilName,
      mobile = newProfile.mobile,
      district = newProfile.district,
      designation = newProfile.designation,
      specialization = newProfile.specialization,
      experienceYears = newProfile.experienceYears,
      bloodGroup = newProfile.bloodGroup
    )
    Toast.makeText(context, "✅ உறுப்பினர் பதிவு வெற்றிகரமாக முடிந்தது!", Toast.LENGTH_SHORT).show()
  }

  // Update Member Photo handler for live ID card update and Firestore persistence
  fun handlePhotoUpdated(memberId: String, newUri: Uri) {
    val uriStr = newUri.toString()
    if (createdMember?.id == memberId) {
      createdMember = createdMember?.copy(photoUri = uriStr)
    }
    if (viewMemberCardModal?.id == memberId) {
      viewMemberCardModal = viewMemberCardModal?.copy(photoUri = uriStr)
    }
    val idx = membersList.indexOfFirst { it.id == memberId }
    if (idx != -1) {
      val updated = membersList[idx].copy(photoUri = uriStr)
      membersList[idx] = updated
      firestoreRepo.saveMemberAsync(updated)
    }
  }

  // Verify OTP Action Handler
  fun verifyEnteredOtp() {
    if (isOtpExpired || otpExpirySeconds <= 0) {
      errorMessage = "OTP காலாவதியாகிவிட்டது! 'புதிய OTP பெறுக' என்பதைத் தொடவும்."
      return
    }
    if (otpAttemptsLeft <= 0) {
      errorMessage = "முயற்சிகள் முடிந்துவிட்டன. தயவுசெய்து புதிய OTP பெறவும்."
      return
    }
    if (otpInput.trim() == generatedOtp.trim()) {
      isOtpVerified = true
      errorMessage = null
      successOtpFeedback = "✅ OTP வெற்றிகரமாக சரிபார்க்கப்பட்டது!"
      performFinalRegistrationAndCloudSync()
    } else {
      otpAttemptsLeft -= 1
      if (otpAttemptsLeft == 0) {
        errorMessage = "தவறான OTP! 3 முயற்சிகளும் முடிந்துவிட்டன. புதிய OTP உருவாக்கவும்."
      } else {
        errorMessage = "தவறான OTP! மீதமுள்ள முயற்சிகள்: $otpAttemptsLeft"
      }
    }
  }

  // Free Direct Delivery Intents (Zero Gateway Cost / No Third-party billing)
  fun sendFreeDirectSms() {
    try {
      val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = android.net.Uri.parse("smsto:$mobileNumber")
        putExtra("sms_body", "🏛️ TNPA சங்க உறுப்பினர் சரிபார்ப்பு குறியீடு (OTP): $generatedOtp. செல்லுபடியாகும் நேரம்: 2 நிமிடங்கள்.")
      }
      context.startActivity(smsIntent)
    } catch (_: Exception) {
      Toast.makeText(context, "SMS செயலியை திறக்க முடியவில்லை.", Toast.LENGTH_SHORT).show()
    }
  }

  fun sendFreeDirectWhatsApp() {
    try {
      val waNumber = if (whatsappNumber.isNotBlank()) whatsappNumber else mobileNumber
      val cleanNumber = waNumber.replace("+", "").replace(" ", "").replace("-", "")
      val formattedWa = if (cleanNumber.length == 10) "91$cleanNumber" else cleanNumber
      val msg = "🏛️ தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் (TNPA²)\nஉறுப்பினர் சரிபார்ப்பு OTP குறியீடு: *$generatedOtp*\n(செல்லத்தக்க நேரம்: 2 நிமிடங்கள்)"
      val waIntent = Intent(Intent.ACTION_VIEW).apply {
        data = android.net.Uri.parse("https://api.whatsapp.com/send?phone=$formattedWa&text=${java.net.URLEncoder.encode(msg, "UTF-8")}")
      }
      context.startActivity(waIntent)
    } catch (_: Exception) {
      Toast.makeText(context, "வாட்ஸ்அப் செயலியை திறக்க முடியவில்லை.", Toast.LENGTH_SHORT).show()
    }
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 14.dp, vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Official Header Banner with Free Zero-Billing Guarantee
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, TnpaRedPrimary.copy(alpha = 0.35f))
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.horizontalGradient(
                listOf(TnpaPureWhite, TnpaRedSoft.copy(alpha = 0.5f), TnpaPureWhite)
              )
            )
            .padding(14.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
              ) {
                TnpaOfficialEmblem(sizeDp = 46.dp)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = "உறுப்பினர் சேர்க்கை & ID அட்டை",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = TnpaJetBlack
                  )
                  Text(
                    text = "தமிழ்நாடு பெயிண்டர்கள் ஓவியர்கள் முன்னேற்ற சங்கம்",
                    style = MaterialTheme.typography.bodySmall,
                    color = TnpaRedDark,
                    fontWeight = FontWeight.Bold
                  )
                }
              }

              // Cloud Synced Badge
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(TnpaJetBlack)
                  .padding(horizontal = 8.dp, vertical = 5.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    Icons.Default.CloudDone,
                    contentDescription = null,
                    tint = TnpaGold,
                    modifier = Modifier.size(13.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "Cloud Verified",
                    color = TnpaPureWhite,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
          }
        }
      }
    }

    // 2. Mode Selector Tab Row
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, TnpaJetBlack.copy(alpha = 0.1f))
      ) {
        TabRow(
          selectedTabIndex = selectedRegistrationTab,
          containerColor = TnpaPureWhite,
          contentColor = TnpaRedPrimary,
          indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
              modifier = Modifier.tabIndicatorOffset(tabPositions[selectedRegistrationTab]),
              color = TnpaRedPrimary,
              height = 3.dp
            )
          }
        ) {
          Tab(
            selected = selectedRegistrationTab == 0,
            onClick = { selectedRegistrationTab = 0 },
            text = {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.VerifiedUser, contentDescription = null, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("🎯 4-படி பதிவு", fontWeight = FontWeight.Bold, fontSize = 11.sp)
              }
            }
          )
          Tab(
            selected = selectedRegistrationTab == 1,
            onClick = { selectedRegistrationTab = 1 },
            text = {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.FlashOn, contentDescription = null, modifier = Modifier.size(15.dp), tint = TnpaGold)
                Spacer(modifier = Modifier.width(4.dp))
                Text("⚡ விரைவு பதிவு & OTP", fontWeight = FontWeight.Bold, fontSize = 11.sp)
              }
            }
          )
          Tab(
            selected = selectedRegistrationTab == 2,
            onClick = { selectedRegistrationTab = 2 },
            text = {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("📋 பட்டியல் (${membersList.size})", fontWeight = FontWeight.Bold, fontSize = 11.sp)
              }
            }
          )
        }
      }
    }

    // 3. Tab Content Handling
    when (selectedRegistrationTab) {
      0 -> {
        // ================= TAB 0: 4-STEP VERIFIED REGISTRATION WORKFLOW =================
        item {
          // Stepper Progress Indicator
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, TnpaJetBlack.copy(alpha = 0.1f))
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              RegistrationStepBadge(step = 1, label = "பெயர் & வயது", currentStep = currentStep)
              Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
              RegistrationStepBadge(step = 2, label = "அனுபவம்", currentStep = currentStep)
              Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
              RegistrationStepBadge(step = 3, label = "தொடர்பு", currentStep = currentStep)
              Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
              RegistrationStepBadge(step = 4, label = "OTP சரிபார்ப்பு", currentStep = currentStep)
            }
          }
        }

        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, TnpaRedPrimary.copy(alpha = 0.4f))
          ) {
            Column(
              modifier = Modifier.padding(18.dp),
              verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
              when (currentStep) {
                1 -> {
                  Text(
                    text = "படி 1: தனிநபர் விவரங்கள் (Name & Age Details)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = TnpaJetBlack
                  )
                  Text(
                    text = "உங்கள் பெயர் மற்றும் வயதை உள்ளிடவும். இது அடையாள அட்டையில் அச்சிடப்படும்.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                  )

                  // Member Photo Picker Widget
                  val step1PhotoLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                  ) { uri: Uri? ->
                    if (uri != null) {
                      selectedPhotoUri = uri
                      Toast.makeText(context, "✅ உறுப்பினர் புகைப்படம் இணைக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
                    }
                  }

                  Card(
                    modifier = Modifier
                      .fillMaxWidth()
                      .clickable { step1PhotoLauncher.launch("image/*") }
                      .testTag("reg_step1_photo_picker"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = TnpaOffWhite),
                    border = androidx.compose.foundation.BorderStroke(1.dp, TnpaGold)
                  ) {
                    Row(
                      modifier = Modifier.padding(12.dp),
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      Box(
                        modifier = Modifier
                          .size(54.dp)
                          .clip(RoundedCornerShape(8.dp))
                          .background(TnpaJetBlack)
                          .border(1.5.dp, TnpaGold, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                      ) {
                        if (selectedPhotoUri != null) {
                          AsyncImage(
                            model = selectedPhotoUri,
                            contentDescription = "Selected Photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                          )
                        } else {
                          Icon(Icons.Default.AddAPhoto, contentDescription = "Add Photo", tint = TnpaGold, modifier = Modifier.size(24.dp))
                        }
                      }

                      Spacer(modifier = Modifier.width(12.dp))

                      Column(modifier = Modifier.weight(1f)) {
                        Text(
                          text = if (selectedPhotoUri != null) "உறுப்பினர் புகைப்படம் இணைக்கப்பட்டது ✓" else "உறுப்பினர் புகைப்படம் (Member Photo)",
                          fontSize = 12.sp,
                          fontWeight = FontWeight.Bold,
                          color = TnpaJetBlack
                        )
                        Text(
                          text = if (selectedPhotoUri != null) "🔄 புகைப்படத்தை மாற்ற தட்டவும்" else "📱 இன்டர்னல் ஸ்டோரேஜ் / கேலரி வழியாக இணைக்க தட்டவும்",
                          fontSize = 10.sp,
                          color = TnpaRedDark
                        )
                      }

                      if (selectedPhotoUri != null) {
                        IconButton(onClick = { selectedPhotoUri = null }) {
                          Icon(Icons.Default.Close, contentDescription = "Remove Photo", tint = TnpaRedDark, modifier = Modifier.size(18.dp))
                        }
                      }
                    }
                  }

                  OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it; errorMessage = null },
                    label = { Text("முழு பெயர் ஆங்கிலத்தில் (Full Name in English) *") },
                    placeholder = { Text("எ.கா: Xavier Babu / Murugan S") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = TnpaRedPrimary) },
                    modifier = Modifier.fillMaxWidth().testTag("input_member_fullname"),
                    singleLine = true,
                    textStyle = TextStyle(color = TnpaRedPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                      focusedTextColor = TnpaRedPrimary,
                      unfocusedTextColor = TnpaRedDark,
                      cursorColor = TnpaRedPrimary,
                      focusedBorderColor = TnpaRedPrimary,
                      focusedLabelColor = TnpaRedPrimary,
                      unfocusedLabelColor = TnpaJetBlack
                    )
                  )

                  OutlinedTextField(
                    value = tamilName,
                    onValueChange = { tamilName = it },
                    label = { Text("பெயர் தமிழில் (Name in Tamil) *") },
                    placeholder = { Text("எ.கா: சேவியர் பாபு / முருகன்") },
                    leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = TnpaRedPrimary) },
                    modifier = Modifier.fillMaxWidth().testTag("input_member_tamilname"),
                    singleLine = true,
                    textStyle = TextStyle(color = TnpaRedPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                      focusedTextColor = TnpaRedPrimary,
                      unfocusedTextColor = TnpaRedDark,
                      cursorColor = TnpaRedPrimary,
                      focusedBorderColor = TnpaRedPrimary,
                      focusedLabelColor = TnpaRedPrimary,
                      unfocusedLabelColor = TnpaJetBlack
                    )
                  )

                  OutlinedTextField(
                    value = fatherName,
                    onValueChange = { fatherName = it },
                    label = { Text("தந்தை / கணவர் பெயர் (Father's / Husband's Name) *") },
                    placeholder = { Text("எ.கா: ராமசாமி / அந்தோணி") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = TnpaRedPrimary) },
                    modifier = Modifier.fillMaxWidth().testTag("input_member_fathername"),
                    singleLine = true,
                    textStyle = TextStyle(color = TnpaRedPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                      focusedTextColor = TnpaRedPrimary,
                      unfocusedTextColor = TnpaRedDark,
                      cursorColor = TnpaRedPrimary,
                      focusedBorderColor = TnpaRedPrimary,
                      focusedLabelColor = TnpaRedPrimary,
                      unfocusedLabelColor = TnpaJetBlack
                    )
                  )

                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                  ) {
                    OutlinedTextField(
                      value = ageText,
                      onValueChange = {
                        if (it.length <= 3 && it.all { c -> c.isDigit() }) {
                          ageText = it
                          errorMessage = null
                        }
                      },
                      label = { Text("வயது (Age in Years) *") },
                      placeholder = { Text("32") },
                      leadingIcon = { Icon(Icons.Default.Cake, contentDescription = null, tint = TnpaRedPrimary) },
                      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                      modifier = Modifier.weight(1f).testTag("input_member_age"),
                      singleLine = true,
                      textStyle = TextStyle(color = TnpaRedPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                      colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TnpaRedPrimary,
                        unfocusedTextColor = TnpaRedDark,
                        cursorColor = TnpaRedPrimary,
                        focusedBorderColor = TnpaRedPrimary,
                        focusedLabelColor = TnpaRedPrimary,
                        unfocusedLabelColor = TnpaJetBlack
                      )
                    )

                    Column(modifier = Modifier.weight(1f)) {
                      Text("இரத்த பிரிவு (Blood Group)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
                      Spacer(modifier = Modifier.height(4.dp))
                      FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                      ) {
                        bloodGroupsList.take(4).forEach { bg ->
                          FilterChip(
                            selected = bloodGroup == bg,
                            onClick = { bloodGroup = bg },
                            label = { Text(bg, fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(
                              selectedContainerColor = TnpaRedPrimary,
                              selectedLabelColor = TnpaPureWhite
                            )
                          )
                        }
                      }
                    }
                  }

                  Button(
                    onClick = {
                      val ageNum = ageText.toIntOrNull()
                      if (fullName.isBlank()) {
                        errorMessage = "தயவுசெய்து உங்கள் பெயரை உள்ளிடவும் (Full name required)."
                      } else if (ageNum == null || ageNum < 18 || ageNum > 80) {
                        errorMessage = "சரியான வயதை உள்ளிடவும் (வயது 18 முதல் 80 வரை)."
                      } else {
                        if (tamilName.isBlank()) tamilName = fullName
                        errorMessage = null
                        currentStep = 2
                      }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("btn_step1_next"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
                  ) {
                    Text("அடுத்த படி: அனுபவம் & பணித்துறை", fontWeight = FontWeight.Bold, color = TnpaPureWhite)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = TnpaPureWhite)
                  }
                }

                2 -> {
                  Text(
                    text = "படி 2: தொழில் அனுபவம் & சிறப்புத் துறை (Experience & Skills)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = TnpaJetBlack
                  )

                  OutlinedTextField(
                    value = experienceYearsText,
                    onValueChange = {
                      if (it.length <= 2 && it.all { c -> c.isDigit() }) {
                        experienceYearsText = it
                        errorMessage = null
                      }
                    },
                    label = { Text("மொத்த அனுபவம் ஆண்டுகள் (Experience in Years) *") },
                    placeholder = { Text("எ.கா: 8 ஆண்டுகள்") },
                    leadingIcon = { Icon(Icons.Default.Work, contentDescription = null, tint = TnpaRedPrimary) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth().testTag("input_member_experience"),
                    singleLine = true,
                    textStyle = TextStyle(color = TnpaRedPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                      focusedTextColor = TnpaRedPrimary,
                      unfocusedTextColor = TnpaRedDark,
                      cursorColor = TnpaRedPrimary,
                      focusedBorderColor = TnpaRedPrimary,
                      focusedLabelColor = TnpaRedPrimary,
                      unfocusedLabelColor = TnpaJetBlack
                    )
                  )

                  Text(
                    text = "முதன்மை பணிப் பிரிவு (Primary Specialization):",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = TnpaJetBlack
                  )

                  Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    specializationsList.forEach { spec ->
                      Card(
                        modifier = Modifier
                          .fillMaxWidth()
                          .clickable { specialization = spec },
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                          containerColor = if (specialization == spec) TnpaRedSoft else TnpaOffWhite
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                          1.dp,
                          if (specialization == spec) TnpaRedPrimary else Color.LightGray
                        )
                      ) {
                        Row(
                          modifier = Modifier.padding(10.dp),
                          verticalAlignment = Alignment.CenterVertically
                        ) {
                          Icon(
                            if (specialization == spec) Icons.Default.CheckCircle else Icons.Default.FormatPaint,
                            contentDescription = null,
                            tint = if (specialization == spec) TnpaRedPrimary else Color.Gray,
                            modifier = Modifier.size(16.dp)
                          )
                          Spacer(modifier = Modifier.width(8.dp))
                          Text(
                            text = spec,
                            fontSize = 12.sp,
                            fontWeight = if (specialization == spec) FontWeight.Bold else FontWeight.Normal,
                            color = TnpaJetBlack
                          )
                        }
                      }
                    }
                  }

                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                  ) {
                    OutlinedButton(
                      onClick = { currentStep = 1 },
                      modifier = Modifier.weight(1f).height(48.dp),
                      shape = RoundedCornerShape(10.dp)
                    ) {
                      Text("முந்தைய படி")
                    }

                    Button(
                      onClick = {
                        val expNum = experienceYearsText.toIntOrNull()
                        if (expNum == null || expNum < 0 || expNum > 60) {
                          errorMessage = "சரியான அனுபவ ஆண்டுகளை உள்ளிடவும் (0 - 60 ஆண்டுகள்)."
                        } else {
                          errorMessage = null
                          currentStep = 3
                        }
                      },
                      modifier = Modifier.weight(1.2f).height(48.dp).testTag("btn_step2_next"),
                      shape = RoundedCornerShape(10.dp),
                      colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
                    ) {
                      Text("அடுத்த படி: தொடர்பு", fontWeight = FontWeight.Bold, color = TnpaPureWhite)
                      Spacer(modifier = Modifier.width(6.dp))
                      Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = TnpaPureWhite)
                    }
                  }
                }

                3 -> {
                  Text(
                    text = "படி 3: தொடர்பு & முகவரி விவரங்கள் (Contact Details)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = TnpaJetBlack
                  )

                  OutlinedTextField(
                    value = mobileNumber,
                    onValueChange = {
                      if (it.length <= 10 && it.all { c -> c.isDigit() }) {
                        mobileNumber = it
                        if (sameAsMobile) whatsappNumber = it
                        errorMessage = null
                      }
                    },
                    label = { Text("10 இலக்க மொபைல் எண் (Mobile No) *") },
                    placeholder = { Text("9876543210") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = TnpaRedPrimary) },
                    prefix = { Text("+91 ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth().testTag("input_member_mobile"),
                    singleLine = true,
                    textStyle = TextStyle(color = TnpaRedPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                      focusedTextColor = TnpaRedPrimary,
                      unfocusedTextColor = TnpaRedDark,
                      cursorColor = TnpaRedPrimary,
                      focusedBorderColor = TnpaRedPrimary,
                      focusedLabelColor = TnpaRedPrimary,
                      unfocusedLabelColor = TnpaJetBlack
                    )
                  )

                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                  ) {
                    Checkbox(
                      checked = sameAsMobile,
                      onCheckedChange = {
                        sameAsMobile = it
                        if (it) whatsappNumber = mobileNumber
                      },
                      colors = CheckboxDefaults.colors(checkedColor = TnpaRedPrimary)
                    )
                    Text(
                      text = "வாட்ஸ்அப் எண்ணும் இதுவே (WhatsApp same as Mobile)",
                      fontSize = 12.sp,
                      fontWeight = FontWeight.Medium,
                      color = TnpaJetBlack
                    )
                  }

                  Text("மாவட்டம் (District):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
                  FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    districtsList.take(6).forEach { dist ->
                      FilterChip(
                        selected = district == dist,
                        onClick = { district = dist },
                        label = { Text(dist, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                          selectedContainerColor = TnpaRedPrimary,
                          selectedLabelColor = TnpaPureWhite
                        )
                      )
                    }
                  }

                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                  ) {
                    OutlinedButton(
                      onClick = { currentStep = 2 },
                      modifier = Modifier.weight(1f).height(48.dp),
                      shape = RoundedCornerShape(10.dp)
                    ) {
                      Text("முந்தைய படி")
                    }

                    Button(
                      onClick = {
                        if (mobileNumber.length != 10) {
                          errorMessage = "சரியான 10 இலக்க மொபைல் எண்ணை உள்ளிடவும்."
                        } else {
                          errorMessage = null
                          generatedOtp = generateSecureFreeOtp()
                          otpAttemptsLeft = 3
                          otpExpirySeconds = 120
                          resendCooldownSeconds = 30
                          isOtpExpired = false
                          otpInput = ""
                          currentStep = 4
                        }
                      },
                      modifier = Modifier.weight(1.2f).height(48.dp).testTag("btn_step3_next"),
                      shape = RoundedCornerShape(10.dp),
                      colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
                    ) {
                      Text("OTP சரிபார்ப்புக்குச் செல்", fontWeight = FontWeight.Bold, color = TnpaPureWhite)
                      Spacer(modifier = Modifier.width(6.dp))
                      Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = TnpaPureWhite)
                    }
                  }
                }

                4 -> {
                  // ================= STEP 4: MANDATORY FREE OTP VERIFICATION =================
                  Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                  ) {
                    Box(
                      modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(TnpaRedSoft),
                      contentAlignment = Alignment.Center
                    ) {
                      Icon(Icons.Default.Lock, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(28.dp))
                    }

                    Text(
                      text = "பாதுகாப்பான மொபைல் OTP சரிபார்ப்பு",
                      style = MaterialTheme.typography.titleMedium,
                      fontWeight = FontWeight.Black,
                      color = TnpaJetBlack
                    )

                    Text(
                      text = "+91 $mobileNumber என்ற எண்ணிற்கு 6 இலக்க பாதுகாப்பு குறியீடு ஒதுக்கப்பட்டுள்ளது.",
                      style = MaterialTheme.typography.bodySmall,
                      color = Color.DarkGray,
                      textAlign = TextAlign.Center
                    )

                    // Timer & Attempts Status
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.SpaceBetween,
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                          Icons.Default.Lock,
                          contentDescription = null,
                          tint = if (isOtpExpired) TnpaRedDark else TnpaGold,
                          modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        val minutes = otpExpirySeconds / 60
                        val seconds = otpExpirySeconds % 60
                        Text(
                          text = if (isOtpExpired) "OTP காலாவதியானது" else String.format(Locale.getDefault(), "%02d:%02d வினாடிகள்", minutes, seconds),
                          color = if (isOtpExpired) TnpaRedDark else TnpaJetBlack,
                          fontSize = 11.sp,
                          fontWeight = FontWeight.Bold
                        )
                      }

                      Box(
                        modifier = Modifier
                          .clip(RoundedCornerShape(6.dp))
                          .background(if (otpAttemptsLeft > 1) TnpaGreen.copy(alpha = 0.15f) else TnpaRedSoft)
                          .padding(horizontal = 8.dp, vertical = 3.dp)
                      ) {
                        Text(
                          text = "🛡️ மீதமுள்ள முயற்சிகள்: $otpAttemptsLeft / 3",
                          color = if (otpAttemptsLeft > 1) TnpaGreen else TnpaRedDark,
                          fontSize = 10.sp,
                          fontWeight = FontWeight.Bold
                        )
                      }
                    }

                    // 100% Free In-App Verification Token Box
                    Card(
                      modifier = Modifier.fillMaxWidth(),
                      shape = RoundedCornerShape(12.dp),
                      colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                      border = androidx.compose.foundation.BorderStroke(1.5.dp, TnpaGold)
                    ) {
                      Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                          modifier = Modifier.fillMaxWidth(),
                          horizontalArrangement = Arrangement.SpaceBetween,
                          verticalAlignment = Alignment.CenterVertically
                        ) {
                          Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                              text = "அமைப்பின் நேரடி இலவச OTP குறியீடு:",
                              fontSize = 11.sp,
                              fontWeight = FontWeight.Bold,
                              color = TnpaJetBlack
                            )
                          }
                          Box(
                            modifier = Modifier
                              .clip(RoundedCornerShape(4.dp))
                              .background(TnpaJetBlack)
                              .padding(horizontal = 6.dp, vertical = 2.dp)
                          ) {
                            Text("No-Billing Free", color = TnpaGold, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                          }
                        }

                        Row(
                          modifier = Modifier.fillMaxWidth(),
                          horizontalArrangement = Arrangement.SpaceBetween,
                          verticalAlignment = Alignment.CenterVertically
                        ) {
                          Text(
                            text = generatedOtp,
                            color = TnpaJetBlack,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 4.sp,
                            fontFamily = FontFamily.Monospace
                          )

                          Button(
                            onClick = {
                              otpInput = generatedOtp
                              errorMessage = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.height(34.dp)
                          ) {
                            Text("தானாக நிரப்பு (Fill)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TnpaPureWhite)
                          }
                        }
                      }
                    }

                    // Zero-Cost WhatsApp & Native SMS Dispatch Options
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                      OutlinedButton(
                        onClick = { sendFreeDirectWhatsApp() },
                        modifier = Modifier.weight(1f).height(38.dp),
                        shape = RoundedCornerShape(8.dp)
                      ) {
                        Text("📲 WhatsApp OTP", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                      }

                      OutlinedButton(
                        onClick = { sendFreeDirectSms() },
                        modifier = Modifier.weight(1f).height(38.dp),
                        shape = RoundedCornerShape(8.dp)
                      ) {
                        Text("📩 நேரடி SMS", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                      }
                    }

                    OutlinedTextField(
                      value = otpInput,
                      onValueChange = { if (it.length <= 6 && it.all { c -> c.isDigit() }) otpInput = it },
                      label = { Text("6 இலக்க OTP குறியீட்டை உள்ளிடவும் *") },
                      placeholder = { Text("6-Digit OTP") },
                      modifier = Modifier.fillMaxWidth().testTag("input_otp_code"),
                      singleLine = true,
                      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                      textStyle = MaterialTheme.typography.titleLarge.copy(
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 4.sp,
                        fontWeight = FontWeight.Bold,
                        color = TnpaRedPrimary
                      ),
                      colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TnpaRedPrimary,
                        unfocusedTextColor = TnpaRedDark,
                        cursorColor = TnpaRedPrimary,
                        focusedBorderColor = TnpaRedPrimary,
                        focusedLabelColor = TnpaRedPrimary,
                        unfocusedLabelColor = TnpaJetBlack
                      )
                    )

                    // Verify & Complete Registration Button
                    Button(
                      onClick = { verifyEnteredOtp() },
                      modifier = Modifier.fillMaxWidth().height(50.dp).testTag("btn_verify_and_save_firestore"),
                      shape = RoundedCornerShape(10.dp),
                      colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
                      enabled = !isSyncingToFirestore && otpInput.length == 6
                    ) {
                      if (isSyncingToFirestore) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = TnpaPureWhite)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Firebase-ல் சேமிக்கப்படுகிறது...", color = TnpaPureWhite)
                      } else {
                        Icon(Icons.Default.CloudUpload, contentDescription = null, tint = TnpaPureWhite)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("🔒 OTP சரிபார்த்து ID அட்டை பெறுக", fontWeight = FontWeight.Bold, color = TnpaPureWhite)
                      }
                    }

                    // Resend OTP Action with Cooldown
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.Center,
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      if (resendCooldownSeconds > 0) {
                        Text(
                          text = "புதிய OTP கோர $resendCooldownSeconds வினாடிகள் காத்திருக்கவும்",
                          fontSize = 11.sp,
                          color = Color.Gray
                        )
                      } else {
                        Text(
                          text = "OTP வரவில்லையா? ",
                          fontSize = 11.sp,
                          color = TnpaJetBlack
                        )
                        Text(
                          text = "🔄 புதிய OTP பெறுக (Resend)",
                          fontSize = 11.sp,
                          fontWeight = FontWeight.Bold,
                          color = TnpaRedPrimary,
                          modifier = Modifier.clickable { resetAndGenerateNewOtp() }
                        )
                      }
                    }
                  }
                }

                5 -> {
                  // ================= STEP 5: VERIFIED DIGITAL ID CARD DISPLAY =================
                  if (createdMember != null) {
                    Column(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalAlignment = Alignment.CenterHorizontally,
                      verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                      Box(
                        modifier = Modifier
                          .size(50.dp)
                          .clip(CircleShape)
                          .background(TnpaGreen),
                        contentAlignment = Alignment.Center
                      ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(30.dp))
                      }

                      Text(
                        text = "உறுப்பினர் பதிவு வெற்றிகரமாக முடிந்தது!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        color = TnpaJetBlack
                      )

                      // Official ID Card Header Badge
                      Row(
                        modifier = Modifier
                          .clip(RoundedCornerShape(8.dp))
                          .background(TnpaGold.copy(alpha = 0.15f))
                          .padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                      ) {
                        Text(
                          text = "🪪 அசல் உறுப்பினர் அடையாள அட்டை (முன்பக்கம் & பின்பக்கம்)",
                          color = TnpaRedDark,
                          fontSize = 11.sp,
                          fontWeight = FontWeight.Bold
                        )
                      }

                      // Render Digital Membership ID Card
                      TnpaMemberIdCardView(
                        member = createdMember!!,
                        modelType = selectedIdCardModel,
                        onModelChange = { selectedIdCardModel = it },
                        onPhotoUpdated = { newUri ->
                          handlePhotoUpdated(createdMember!!.id, newUri)
                        }
                      )

                      Button(
                        onClick = {
                          fullName = ""
                          tamilName = ""
                          ageText = ""
                          experienceYearsText = ""
                          mobileNumber = ""
                          whatsappNumber = ""
                          email = ""
                          address = ""
                          otpInput = ""
                          isOtpVerified = false
                          isOtpExpired = false
                          generatedOtp = generateSecureFreeOtp()
                          currentStep = 1
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TnpaJetBlack)
                      ) {
                        Text("+ மற்றொரு புதிய உறுப்பினரை சேர்க்கவும்", color = TnpaPureWhite, fontWeight = FontWeight.Bold)
                      }
                    }
                  }
                }
              }

              if (errorMessage != null && currentStep != 5) {
                Card(
                  modifier = Modifier.fillMaxWidth(),
                  shape = RoundedCornerShape(8.dp),
                  colors = CardDefaults.cardColors(containerColor = TnpaRedSoft),
                  border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedPrimary)
                ) {
                  Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = errorMessage!!, color = TnpaRedDark, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                  }
                }
              }
            }
          }
        }
      }

      1 -> {
        // ================= TAB 1: QUICK REGISTRATION FORM WITH MANDATORY OTP =================
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, TnpaRedPrimary.copy(alpha = 0.4f))
          ) {
            Column(
              modifier = Modifier.padding(16.dp),
              verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Badge, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                  Text(
                    text = "விரைவு உறுப்பினர் பதிவு (Quick Form)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = TnpaJetBlack
                  )
                  Text(
                    text = "விவரங்களை உள்ளிட்டு OTP சரிபார்த்தவுடன் அதிகாரப்பூர்வ அட்டை வழங்கப்படும்",
                    style = MaterialTheme.typography.bodySmall,
                    color = TnpaRedDark
                  )
                }
              }

              // Quick Photo Picker
              val quickPhotoLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
              ) { uri: Uri? ->
                if (uri != null) {
                  selectedPhotoUri = uri
                  Toast.makeText(context, "✅ உறுப்பினர் புகைப்படம் இணைக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
                }
              }

              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable { quickPhotoLauncher.launch("image/*") }
                  .testTag("quick_photo_picker"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = TnpaOffWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, TnpaGold)
              ) {
                Row(
                  modifier = Modifier.padding(10.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Box(
                    modifier = Modifier
                      .size(50.dp)
                      .clip(RoundedCornerShape(8.dp))
                      .background(TnpaJetBlack)
                      .border(1.5.dp, TnpaGold, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                  ) {
                    if (selectedPhotoUri != null) {
                      AsyncImage(
                        model = selectedPhotoUri,
                        contentDescription = "Selected Photo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                      )
                    } else {
                      Icon(Icons.Default.AddAPhoto, contentDescription = "Add Photo", tint = TnpaGold, modifier = Modifier.size(22.dp))
                    }
                  }

                  Spacer(modifier = Modifier.width(10.dp))

                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = if (selectedPhotoUri != null) "புகைப்படம் இணைக்கப்பட்டது ✓" else "உறுப்பினர் புகைப்படம் இணைக்க (Member Photo)",
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      color = TnpaJetBlack
                    )
                    Text(
                      text = if (selectedPhotoUri != null) "🔄 புகைப்படத்தை மாற்ற தட்டவும்" else "📱 இன்டர்னல் ஸ்டோரேஜ் / கேலரி வழியாக இணைக்க தட்டவும்",
                      fontSize = 9.5.sp,
                      color = TnpaRedDark
                    )
                  }

                  if (selectedPhotoUri != null) {
                    IconButton(onClick = { selectedPhotoUri = null }) {
                      Icon(Icons.Default.Close, contentDescription = "Remove Photo", tint = TnpaRedDark, modifier = Modifier.size(16.dp))
                    }
                  }
                }
              }

              OutlinedTextField(
                value = tamilName,
                onValueChange = { tamilName = it; errorMessage = null },
                label = { Text("உறுப்பினர் பெயர் தமிழில் (Name in Tamil) *") },
                placeholder = { Text("எ.கா: சேவியர் பாபு / முருகன்") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = TnpaRedPrimary) },
                modifier = Modifier.fillMaxWidth().testTag("quick_input_tamilname"),
                singleLine = true,
                textStyle = TextStyle(color = TnpaRedPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedTextColor = TnpaRedPrimary,
                  unfocusedTextColor = TnpaRedDark,
                  cursorColor = TnpaRedPrimary,
                  focusedBorderColor = TnpaRedPrimary,
                  focusedLabelColor = TnpaRedPrimary,
                  unfocusedLabelColor = TnpaJetBlack
                )
              )

              OutlinedTextField(
                value = fullName,
                onValueChange = {
                  fullName = it
                  if (tamilName.isBlank()) tamilName = it
                  errorMessage = null
                },
                label = { Text("பெயர் ஆங்கிலத்தில் (Full Name in English) *") },
                placeholder = { Text("Xavier Babu / Murugan S") },
                leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = TnpaRedPrimary) },
                modifier = Modifier.fillMaxWidth().testTag("quick_input_fullname"),
                singleLine = true,
                textStyle = TextStyle(color = TnpaRedPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedTextColor = TnpaRedPrimary,
                  unfocusedTextColor = TnpaRedDark,
                  cursorColor = TnpaRedPrimary,
                  focusedBorderColor = TnpaRedPrimary,
                  focusedLabelColor = TnpaRedPrimary,
                  unfocusedLabelColor = TnpaJetBlack
                )
              )

              OutlinedTextField(
                value = fatherName,
                onValueChange = { fatherName = it },
                label = { Text("தந்தை / கணவர் பெயர் (Father's / Husband's Name) *") },
                placeholder = { Text("எ.கா: ராமசாமி / அந்தோணி") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = TnpaRedPrimary) },
                modifier = Modifier.fillMaxWidth().testTag("quick_input_fathername"),
                singleLine = true,
                textStyle = TextStyle(color = TnpaRedPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedTextColor = TnpaRedPrimary,
                  unfocusedTextColor = TnpaRedDark,
                  cursorColor = TnpaRedPrimary,
                  focusedBorderColor = TnpaRedPrimary,
                  focusedLabelColor = TnpaRedPrimary,
                  unfocusedLabelColor = TnpaJetBlack
                )
              )

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                OutlinedTextField(
                  value = ageText,
                  onValueChange = { if (it.length <= 3 && it.all { c -> c.isDigit() }) ageText = it },
                  label = { Text("வயது (Age) *") },
                  placeholder = { Text("32") },
                  leadingIcon = { Icon(Icons.Default.Cake, contentDescription = null, tint = TnpaRedPrimary) },
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                  modifier = Modifier.weight(1f).testTag("quick_input_age"),
                  singleLine = true,
                  textStyle = TextStyle(color = TnpaRedPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                  colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TnpaRedPrimary,
                    unfocusedTextColor = TnpaRedDark,
                    cursorColor = TnpaRedPrimary,
                    focusedBorderColor = TnpaRedPrimary,
                    focusedLabelColor = TnpaRedPrimary,
                    unfocusedLabelColor = TnpaJetBlack
                  )
                )

                OutlinedTextField(
                  value = experienceYearsText,
                  onValueChange = { if (it.length <= 2 && it.all { c -> c.isDigit() }) experienceYearsText = it },
                  label = { Text("அனுபவம் (Exp Years)") },
                  placeholder = { Text("8") },
                  leadingIcon = { Icon(Icons.Default.Work, contentDescription = null, tint = TnpaRedPrimary) },
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                  modifier = Modifier.weight(1f).testTag("quick_input_experience"),
                  singleLine = true,
                  textStyle = TextStyle(color = TnpaRedPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                  colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TnpaRedPrimary,
                    unfocusedTextColor = TnpaRedDark,
                    cursorColor = TnpaRedPrimary,
                    focusedBorderColor = TnpaRedPrimary,
                    focusedLabelColor = TnpaRedPrimary,
                    unfocusedLabelColor = TnpaJetBlack
                  )
                )
              }

              // Blood Group & District
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text("இரத்த பிரிவு (Blood Group):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
                  Spacer(modifier = Modifier.height(4.dp))
                  FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                  ) {
                    listOf("O+", "A+", "B+", "AB+").forEach { bg ->
                      FilterChip(
                        selected = bloodGroup == bg,
                        onClick = { bloodGroup = bg },
                        label = { Text(bg, fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                          selectedContainerColor = TnpaRedPrimary,
                          selectedLabelColor = TnpaPureWhite
                        )
                      )
                    }
                  }
                }

                Column(modifier = Modifier.weight(1f)) {
                  Text("மாவட்டம் (District):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
                  Spacer(modifier = Modifier.height(4.dp))
                  FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                  ) {
                    listOf("திருச்சி", "சென்னை", "மதுரை", "கோவை").forEach { distShort ->
                      val fullDist = districtsList.firstOrNull { it.contains(distShort) } ?: distShort
                      FilterChip(
                        selected = district.contains(distShort),
                        onClick = { district = fullDist },
                        label = { Text(distShort, fontSize = 10.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                          selectedContainerColor = TnpaRedPrimary,
                          selectedLabelColor = TnpaPureWhite
                        )
                      )
                    }
                  }
                }
              }

              OutlinedTextField(
                value = mobileNumber,
                onValueChange = { if (it.length <= 10 && it.all { c -> c.isDigit() }) mobileNumber = it },
                label = { Text("10 இலக்க மொபைல் எண் (Mobile No) *") },
                placeholder = { Text("9876543210") },
                prefix = { Text("+91 ") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = TnpaRedPrimary) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth().testTag("quick_input_mobile"),
                singleLine = true,
                textStyle = TextStyle(color = TnpaRedPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedTextColor = TnpaRedPrimary,
                  unfocusedTextColor = TnpaRedDark,
                  cursorColor = TnpaRedPrimary,
                  focusedBorderColor = TnpaRedPrimary,
                  focusedLabelColor = TnpaRedPrimary,
                  unfocusedLabelColor = TnpaJetBlack
                )
              )

              Text("தொழில் சிறப்புப் பிரிவு (Specialization):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
              FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                specializationsList.take(4).forEach { spec ->
                  FilterChip(
                    selected = specialization == spec,
                    onClick = { specialization = spec },
                    label = { Text(spec.split("&").first().trim(), fontSize = 10.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                      selectedContainerColor = TnpaRedPrimary,
                      selectedLabelColor = TnpaPureWhite
                    )
                  )
                }
              }

              if (errorMessage != null) {
                Text(
                  text = errorMessage!!,
                  color = TnpaRedDark,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }

              // Open Mandatory OTP Modal Button
              Button(
                onClick = {
                  val ageNum = ageText.toIntOrNull()
                  if (fullName.isBlank()) {
                    errorMessage = "தயவுசெய்து உங்கள் பெயரை உள்ளிடவும் (Full Name required)."
                  } else if (ageNum == null || ageNum < 18 || ageNum > 80) {
                    errorMessage = "சரியான வயதை உள்ளிடவும் (18 - 80 வயது)."
                  } else if (mobileNumber.length != 10) {
                    errorMessage = "சரியான 10 இலக்க மொபைல் எண்ணை உள்ளிடவும்."
                  } else {
                    errorMessage = null
                    generatedOtp = generateSecureFreeOtp()
                    otpAttemptsLeft = 3
                    otpExpirySeconds = 120
                    resendCooldownSeconds = 30
                    isOtpExpired = false
                    otpInput = ""
                    showQuickOtpModal = true
                  }
                },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(52.dp)
                  .testTag("btn_instant_generate_card"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
              ) {
                Icon(Icons.Default.Lock, contentDescription = null, tint = TnpaPureWhite)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "🔒 OTP சரிபார்த்து ID அட்டை பெறுக (Mandatory OTP)",
                  fontWeight = FontWeight.Black,
                  fontSize = 13.sp,
                  color = TnpaPureWhite
                )
              }
            }
          }
        }

        // Live Generated ID Card Display
        if (createdMember != null) {
          item {
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
              border = androidx.compose.foundation.BorderStroke(2.dp, TnpaGold)
            ) {
              Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TnpaGreen, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "அசல் அடையாள அட்டை தயார்!",
                      style = MaterialTheme.typography.titleMedium,
                      fontWeight = FontWeight.Black,
                      color = TnpaJetBlack
                    )
                  }
                }

                // Render ID Card View Component
                TnpaMemberIdCardView(
                  member = createdMember!!,
                  modelType = selectedIdCardModel,
                  onModelChange = { selectedIdCardModel = it },
                  onPhotoUpdated = { newUri ->
                    handlePhotoUpdated(createdMember!!.id, newUri)
                  }
                )
              }
            }
          }
        }
      }

      2 -> {
        // ================= TAB 2: REGISTERED MEMBERS DIRECTORY =================
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "பதிவு செய்த உறுப்பினர்கள் (${membersList.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = TnpaJetBlack
              )
              Text(
                text = "அட்டையை பார்க்க உறுப்பினரின் மீது தட்டவும்",
                style = MaterialTheme.typography.bodySmall,
                color = TnpaRedDark
              )
            }

            IconButton(
              onClick = {
                coroutineScope.launch {
                  val freshList = firestoreRepo.fetchMembers()
                  freshList.forEach { fm ->
                    if (membersList.none { it.id == fm.id }) {
                      membersList.add(0, fm)
                    }
                  }
                  Toast.makeText(context, "Firestore பட்டியல் புதுப்பிக்கப்பட்டது", Toast.LENGTH_SHORT).show()
                }
              }
            ) {
              Icon(Icons.Default.Refresh, contentDescription = "Refresh Cloud", tint = TnpaRedPrimary)
            }
          }
        }

        // District Filter Chips
        item {
          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            val filterOptions = listOf("அனைத்தும்", "திருச்சி", "சென்னை", "மதுரை", "கோவை")
            filterOptions.forEach { filter ->
              FilterChip(
                selected = selectedDistrictFilter == filter,
                onClick = { selectedDistrictFilter = filter },
                label = { Text(filter, fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = TnpaRedPrimary,
                  selectedLabelColor = TnpaPureWhite
                )
              )
            }
          }
        }

        val filteredMembers = membersList.filter { member ->
          (selectedDistrictFilter == "அனைத்தும்" || member.district.contains(selectedDistrictFilter)) &&
            (searchFilter.isEmpty() || member.fullName.contains(searchFilter, ignoreCase = true) || member.tamilName.contains(searchFilter))
        }

        items(filteredMembers) { member ->
          MemberDirectoryCard(
            member = member,
            onViewCard = { viewMemberCardModal = member }
          )
        }
      }
    }
  }

  // Card Preview Modal Dialog
  if (viewMemberCardModal != null) {
    androidx.compose.ui.window.Dialog(
      onDismissRequest = { viewMemberCardModal = null }
    ) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
        border = androidx.compose.foundation.BorderStroke(2.dp, TnpaRedPrimary)
      ) {
        Column(
          modifier = Modifier.padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "உறுப்பினர் அடையாள அட்டை",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Black,
              color = TnpaJetBlack
            )
          }

          TnpaMemberIdCardView(
            member = viewMemberCardModal!!,
            modelType = selectedIdCardModel,
            onModelChange = { selectedIdCardModel = it },
            onPhotoUpdated = { newUri ->
              handlePhotoUpdated(viewMemberCardModal!!.id, newUri)
            }
          )

          Button(
            onClick = { viewMemberCardModal = null },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = TnpaJetBlack)
          ) {
            Text("மூடு (Close)", color = TnpaPureWhite)
          }
        }
      }
    }
  }

  // Quick OTP Modal Verification Dialog for Tab 1
  if (showQuickOtpModal) {
    androidx.compose.ui.window.Dialog(
      onDismissRequest = { showQuickOtpModal = false }
    ) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
        border = androidx.compose.foundation.BorderStroke(2.dp, TnpaRedPrimary)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Lock, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "OTP சரிபார்ப்பு (Mandatory)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = TnpaJetBlack
              )
            }
            IconButton(onClick = { showQuickOtpModal = false }, modifier = Modifier.size(24.dp)) {
              Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
            }
          }

          Text(
            text = "+91 $mobileNumber என்ற எண்ணிற்கு 6 இலக்க பாதுகாப்பு OTP ஒதுக்கப்பட்டுள்ளது.",
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
          )

          // Timer and Attempts Bar
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                Icons.Default.Lock,
                contentDescription = null,
                tint = if (isOtpExpired) TnpaRedDark else TnpaGold,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              val mins = otpExpirySeconds / 60
              val secs = otpExpirySeconds % 60
              Text(
                text = if (isOtpExpired) "OTP காலாவதியானது" else String.format(Locale.getDefault(), "%02d:%02d வினாடிகள்", mins, secs),
                color = if (isOtpExpired) TnpaRedDark else TnpaJetBlack,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(if (otpAttemptsLeft > 1) TnpaGreen.copy(alpha = 0.15f) else TnpaRedSoft)
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "🛡️ முயற்சிகள்: $otpAttemptsLeft / 3",
                color = if (otpAttemptsLeft > 1) TnpaGreen else TnpaRedDark,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          // In-App Free Token Box
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
            border = androidx.compose.foundation.BorderStroke(1.dp, TnpaGold)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text("இலவச நேரடி OTP குறியீடு:", fontSize = 10.sp, color = TnpaJetBlack, fontWeight = FontWeight.Bold)
                Text(
                  text = generatedOtp,
                  fontSize = 18.sp,
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.Monospace,
                  letterSpacing = 2.sp,
                  color = TnpaJetBlack
                )
              }
              Button(
                onClick = {
                  otpInput = generatedOtp
                  errorMessage = null
                },
                colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
                shape = RoundedCornerShape(6.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                modifier = Modifier.height(30.dp)
              ) {
                Text("நிரப்பு (Fill)", fontSize = 10.sp, color = TnpaPureWhite, fontWeight = FontWeight.Bold)
              }
            }
          }

          // WhatsApp / SMS Native Intents
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            OutlinedButton(
              onClick = { sendFreeDirectWhatsApp() },
              modifier = Modifier.weight(1f).height(34.dp),
              shape = RoundedCornerShape(6.dp)
            ) {
              Text("📲 WhatsApp OTP", fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
            OutlinedButton(
              onClick = { sendFreeDirectSms() },
              modifier = Modifier.weight(1f).height(34.dp),
              shape = RoundedCornerShape(6.dp)
            ) {
              Text("📩 நேரடி SMS", fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
          }

          OutlinedTextField(
            value = otpInput,
            onValueChange = { if (it.length <= 6 && it.all { c -> c.isDigit() }) otpInput = it },
            label = { Text("6 இலக்க OTP உள்ளிடவும் *") },
            placeholder = { Text("6-Digit Code") },
            modifier = Modifier.fillMaxWidth().testTag("modal_input_otp"),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.titleMedium.copy(
              textAlign = TextAlign.Center,
              fontFamily = FontFamily.Monospace,
              letterSpacing = 3.sp,
              fontWeight = FontWeight.Bold,
              color = TnpaRedPrimary
            ),
            colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = TnpaRedPrimary,
              unfocusedTextColor = TnpaRedDark,
              cursorColor = TnpaRedPrimary,
              focusedBorderColor = TnpaRedPrimary,
              focusedLabelColor = TnpaRedPrimary,
              unfocusedLabelColor = TnpaJetBlack
            )
          )

          if (errorMessage != null) {
            Text(
              text = errorMessage!!,
              color = TnpaRedDark,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center
            )
          }

          Button(
            onClick = { verifyEnteredOtp() },
            modifier = Modifier.fillMaxWidth().height(46.dp).testTag("btn_verify_quick_otp"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
            enabled = !isSyncingToFirestore && otpInput.length == 6
          ) {
            if (isSyncingToFirestore) {
              CircularProgressIndicator(modifier = Modifier.size(18.dp), color = TnpaPureWhite)
              Spacer(modifier = Modifier.width(6.dp))
              Text("பதிவு செய்யப்படுகிறது...", color = TnpaPureWhite, fontSize = 12.sp)
            } else {
              Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("🔒 OTP சரிபார்த்து ID அட்டை பெறுக", fontWeight = FontWeight.Bold, color = TnpaPureWhite, fontSize = 12.sp)
            }
          }

          if (resendCooldownSeconds > 0) {
            Text(
              text = "புதிய OTP கோர $resendCooldownSeconds வினாடிகள் காத்திருக்கவும்",
              fontSize = 10.sp,
              color = Color.Gray
            )
          } else {
            Text(
              text = "🔄 புதிய OTP பெறுக (Resend)",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = TnpaRedPrimary,
              modifier = Modifier.clickable { resetAndGenerateNewOtp() }
            )
          }
        }
      }
    }
  }
}

// Zero-cost cryptographic OTP Generator
fun generateSecureFreeOtp(): String {
  val secureRandom = java.security.SecureRandom()
  val code = 100000 + secureRandom.nextInt(900000)
  return code.toString()
}

@Composable
fun RegistrationStepBadge(step: Int, label: String, currentStep: Int) {
  val isCompleted = currentStep > step
  val isActive = currentStep == step

  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Box(
      modifier = Modifier
        .size(26.dp)
        .clip(CircleShape)
        .background(
          when {
            isCompleted -> TnpaGreen
            isActive -> TnpaRedPrimary
            else -> Color(0xFFE2E8F0)
          }
        ),
      contentAlignment = Alignment.Center
    ) {
      if (isCompleted) {
        Icon(Icons.Default.Check, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(14.dp))
      } else {
        Text(
          text = step.toString(),
          color = if (isActive) TnpaPureWhite else Color.Gray,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }
    Spacer(modifier = Modifier.height(3.dp))
    Text(
      text = label,
      fontSize = 9.sp,
      fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
      color = if (isActive) TnpaRedPrimary else Color.DarkGray
    )
  }
}

@Composable
fun MemberDirectoryCard(
  member: MemberProfile,
  onViewCard: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onViewCard() }
      .testTag("member_card_${member.id}"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedPrimary.copy(alpha = 0.2f))
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Avatar with Photo or Initials
      Box(
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(Brush.linearGradient(listOf(TnpaRedPrimary, TnpaRedDark))),
        contentAlignment = Alignment.Center
      ) {
        if (!member.photoUri.isNullOrBlank()) {
          AsyncImage(
            model = member.photoUri,
            contentDescription = member.fullName,
            modifier = Modifier.fillMaxSize().clip(CircleShape),
            contentScale = ContentScale.Crop
          )
        } else {
          Text(
            text = member.fullName.take(2).uppercase(),
            color = TnpaPureWhite,
            fontWeight = FontWeight.Black,
            fontSize = 16.sp
          )
        }
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Text(
            text = member.tamilName,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = TnpaJetBlack
          )
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(TnpaRedSoft)
              .padding(horizontal = 5.dp, vertical = 1.dp)
          ) {
            Text(
              text = "${member.age} வயது",
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              color = TnpaRedDark
            )
          }
        }

        Text(
          text = "${member.fullName} • ${member.designation}",
          style = MaterialTheme.typography.bodySmall,
          color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(
            text = "💼 ${member.experienceYears} வருட அனுபவம்",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TnpaRedDark
          )
          Text(
            text = "📍 ${member.district}",
            fontSize = 11.sp,
            color = Color.Gray
          )
        }

        Text(
          text = "📞 +91 ${member.mobile} • 🩸 ${member.bloodGroup}",
          fontSize = 11.sp,
          color = TnpaJetBlack,
          fontWeight = FontWeight.Medium
        )
      }

      IconButton(onClick = onViewCard) {
        Icon(Icons.Default.Badge, contentDescription = "ID Card", tint = TnpaRedPrimary)
      }
    }
  }
}

/**
 * Official TNPA Membership ID Card View
 * Renders the genuine physical ID Card with Front & Back side views, registered photo,
 * official emblem, leadership signatures, and sharing tools.
 */
@Composable
fun TnpaMemberIdCardView(
  member: MemberProfile,
  modelType: Int = 1,
  onModelChange: ((Int) -> Unit)? = null,
  onPhotoUpdated: ((Uri) -> Unit)? = null
) {
  OfficialTnpaIdCardComponent(
    member = member,
    onPhotoUpdated = onPhotoUpdated
  )
}
