package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.AdminBroadcastControlScreen
import com.example.data.AdminApprovalRepository
import com.example.model.AdminAccount
import com.example.model.AdminRole
import com.example.model.AdminStatus
import com.example.model.ApprovalStatus
import com.example.model.AuditLogEntry
import com.example.model.JobPostingItem
import com.example.model.JobPostingStatus
import com.example.model.MemberApprovalItem
import com.example.model.PredefinedAdminPosts
import com.example.model.StreamHealthReport
import com.example.model.StreamStatus
import com.example.model.WelfareAppStatus
import com.example.model.WelfareApplicationItem
import com.example.ui.components.AppDownloadModal
import com.example.ui.components.TnpaOfficialEmblem
import com.example.ui.screens.AdminExecutiveDashboardSubScreen
import com.example.ui.theme.TnpaCyan
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedPrimary
import com.example.ui.theme.TnpaRedSoft

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AdminManagementScreen(
  rtmpUrl: String,
  streamKey: String,
  hlsUrl: String,
  isBroadcasting: Boolean,
  streamStatus: StreamStatus,
  breakingNews: String,
  healthReport: StreamHealthReport?,
  onNavigateToAiMonitoring: () -> Unit = {},
  onUpdateSettings: (String, String, String, String) -> Unit,
  onToggleBroadcast: (Boolean) -> Unit,
  onHealthStatusUpdated: (StreamStatus, StreamHealthReport) -> Unit
) {
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current

  // Auth State
  var currentLoggedInAdmin by remember { mutableStateOf<AdminAccount?>(null) }
  var showPasswordSetupDialog by remember { mutableStateOf(false) }
  var pendingSetupAdminId by remember { mutableStateOf<String?>(null) }

  // Refresh trigger state
  var refreshCount by remember { mutableIntStateOf(0) }

  if (currentLoggedInAdmin == null) {
    // ------------------------------------------------------------------------
    // 1. ADMIN SECURE LOGIN GATE
    // ------------------------------------------------------------------------
    AdminLoginView(
      onLoginSuccess = { account, requiresPasswordSetup ->
        if (requiresPasswordSetup) {
          pendingSetupAdminId = account.id
          showPasswordSetupDialog = true
        } else {
          currentLoggedInAdmin = account
        }
      }
    )
  } else {
    // ------------------------------------------------------------------------
    // 2. AUTHORIZED ADMIN DASHBOARD (ROLE SPECIFIC)
    // ------------------------------------------------------------------------
    val admin = currentLoggedInAdmin!!

    AdminAuthorizedDashboardView(
      admin = admin,
      onLogout = {
        currentLoggedInAdmin = null
        Toast.makeText(context, "பாதுகாப்பாக வெளியேறினீர்கள் (Logged Out)", Toast.LENGTH_SHORT).show()
      },
      onAdminUpdated = { refreshCount++ },
      onNavigateToAiMonitoring = onNavigateToAiMonitoring,
      // Broadcast props for Super Admin
      rtmpUrl = rtmpUrl,
      streamKey = streamKey,
      hlsUrl = hlsUrl,
      isBroadcasting = isBroadcasting,
      streamStatus = streamStatus,
      breakingNews = breakingNews,
      healthReport = healthReport,
      onUpdateSettings = onUpdateSettings,
      onToggleBroadcast = onToggleBroadcast,
      onHealthStatusUpdated = onHealthStatusUpdated
    )
  }

  // --------------------------------------------------------------------------
  // FIRST TIME PASSWORD SETUP DIALOG (FOR NEW SETUP KEY LOGINS)
  // --------------------------------------------------------------------------
  if (showPasswordSetupDialog && pendingSetupAdminId != null) {
    FirstTimePasswordSetupDialog(
      adminId = pendingSetupAdminId!!,
      onComplete = { updatedAccount ->
        showPasswordSetupDialog = false
        pendingSetupAdminId = null
        currentLoggedInAdmin = updatedAccount
        Toast.makeText(context, "புதிய கடவுச்சொல் வெற்றிகரமாக அமைக்கப்பட்டது! (Password Configured)", Toast.LENGTH_LONG).show()
      },
      onDismiss = {
        showPasswordSetupDialog = false
        pendingSetupAdminId = null
      }
    )
  }
}

// ============================================================================
// ADMIN LOGIN VIEW
// ============================================================================

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AdminLoginView(
  onLoginSuccess: (AdminAccount, Boolean) -> Unit
) {
  val context = LocalContext.current
  var usernameInput by remember { mutableStateOf("") }
  var passwordInput by remember { mutableStateOf("") }
  var isPasswordVisible by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf<String?>(null) }
  var isLoading by remember { mutableStateOf(false) }

  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(TnpaOffWhite)
      .verticalScroll(scrollState)
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Official Header Banner
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaRedPrimary),
      elevation = CardDefaults.cardElevation(6.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        TnpaOfficialEmblem(modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = "தமிழ்நாடு ஓவியர்கள் & பெயிண்டர்கள் நலச் சங்கம்",
          color = TnpaGold,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center
        )
        Text(
          text = "நிர்வாகி மேலாண்மை & ஒப்புதல் கட்டுப்பாட்டு மையம்",
          color = Color.White,
          fontSize = 17.sp,
          fontWeight = FontWeight.Black,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(top = 4.dp)
        )
        Text(
          text = "Admin Management & Multi-Level Approval Control System",
          color = TnpaOffWhite.copy(alpha = 0.9f),
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium
        )
      }
    }

    // Role Hierarchy & Super Admin Credentials Display Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(TnpaRedPrimary, TnpaGold)))
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Shield, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "சூப்பர் அட்மின் மற்றும் நிர்வாகிகள் பாஸ்வேர்டு பட்டியல்:",
            color = TnpaJetBlack,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(8.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
          border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedSoft)
        ) {
          Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
              text = "👑 Super Admin 1 (பொதுச் செயலாளர் - சேவியர் பாபு):",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = TnpaRedDark
            )
            Text(
              text = "• Username: superadmin  |  Password: SuperAdmin@2026",
              fontSize = 11.sp,
              fontWeight = FontWeight.Black,
              fontFamily = FontFamily.Monospace,
              color = TnpaJetBlack
            )
            HorizontalDivider(color = Color(0xFFFECACA), modifier = Modifier.padding(vertical = 2.dp))
            Text(
              text = "👑 Super Admin 2 (மாநிலத் தலைவர் - மைக்கேல் ஆல்வின்):",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFFB45309)
            )
            Text(
              text = "• Username: state.president  |  Password: President@2026",
              fontSize = 11.sp,
              fontWeight = FontWeight.Black,
              fontFamily = FontFamily.Monospace,
              color = TnpaJetBlack
            )
            HorizontalDivider(color = Color(0xFFFECACA), modifier = Modifier.padding(vertical = 2.dp))
            Text(
              text = "🏛️ State Treasurer (மாநில பொருளாளர் - சக்திவேல்):",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF1E3A8A)
            )
            Text(
              text = "• Username: state.treasurer  |  Setup Key: TNPA-ADM-7842-TRZ",
              fontSize = 11.sp,
              fontWeight = FontWeight.Black,
              fontFamily = FontFamily.Monospace,
              color = TnpaJetBlack
            )
            HorizontalDivider(color = Color(0xFFFECACA), modifier = Modifier.padding(vertical = 2.dp))
            Text(
              text = "🏢 District Admin (திருச்சி மாவட்டத் தலைவர்):",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF15803D)
            )
            Text(
              text = "• Username: trichy.president  |  Password: Trichy@2026",
              fontSize = 11.sp,
              fontWeight = FontWeight.Black,
              fontFamily = FontFamily.Monospace,
              color = TnpaJetBlack
            )
          }
        }
      }
    }

    // Login Form Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(4.dp)
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Text(
          text = "நிர்வாகி உள்நுழைவு (Admin Login)",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = TnpaRedDark
        )
        Text(
          text = "பயனர்பெயர் மற்றும் உங்கள் கடவுச்சொல் அல்லது ஒருமுறை அமைவுக் குறியீட்டை (One-Time Setup Key) உள்ளிடவும்.",
          fontSize = 12.sp,
          color = Color.Gray,
          modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        // Error message if any
        if (errorMessage != null) {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2)),
            shape = RoundedCornerShape(8.dp)
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = errorMessage!!,
                color = Color(0xFF991B1B),
                fontSize = 12.sp
              )
            }
          }
        }

        // Username Field
        OutlinedTextField(
          value = usernameInput,
          onValueChange = {
            usernameInput = it
            errorMessage = null
          },
          label = { Text("பயனர்பெயர் (Username)") },
          leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = TnpaRedPrimary) },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("admin_username_field"),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
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

        Spacer(modifier = Modifier.height(12.dp))

        // Password / One-time Setup Key Field
        OutlinedTextField(
          value = passwordInput,
          onValueChange = {
            passwordInput = it
            errorMessage = null
          },
          label = { Text("கடவுச்சொல் / One-Time Setup Key") },
          leadingIcon = { Icon(Icons.Default.Key, contentDescription = null, tint = TnpaGold) },
          trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
              Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = null
              )
            }
          },
          visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("admin_password_field"),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
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

        Spacer(modifier = Modifier.height(18.dp))

        // Login Button
        Button(
          onClick = {
            if (usernameInput.isBlank() || passwordInput.isBlank()) {
              errorMessage = "பயனர்பெயர் மற்றும் கடவுச்சொல்லை உள்ளிடவும்."
              return@Button
            }
            isLoading = true
            when (val result = AdminApprovalRepository.authenticateAdmin(usernameInput, passwordInput)) {
              is AdminApprovalRepository.LoginResult.Success -> {
                errorMessage = null
                onLoginSuccess(result.account, result.requiresPasswordSetup)
              }
              is AdminApprovalRepository.LoginResult.Error -> {
                errorMessage = result.messageTamil
              }
            }
            isLoading = false
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("admin_login_submit_btn"),
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
          shape = RoundedCornerShape(10.dp)
        ) {
          Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("பாதுகாப்பாக உள்நுழைக (Secure Login)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Quick Test / Demo Accounts Selector (For Easy System Evaluation)
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Tune, contentDescription = null, tint = TnpaRedDark, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "பரிசோதனை விரைவுக் கணக்குகள் (Quick Role Demo):",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TnpaJetBlack
          )
        }
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Super Admin 1: State General Secretary
          FilterChip(
            selected = usernameInput == "superadmin",
            onClick = {
              usernameInput = "superadmin"
              passwordInput = "SuperAdmin@2026"
              errorMessage = null
            },
            label = { Text("Super Admin 1 (சேவியர் பாபு - பொதுச் செயலாளர்)", fontSize = 11.sp) },
            leadingIcon = { Icon(Icons.Default.VerifiedUser, contentDescription = null, modifier = Modifier.size(14.dp), tint = TnpaRedPrimary) }
          )

          // Super Admin 2: State President
          FilterChip(
            selected = usernameInput == "state.president",
            onClick = {
              usernameInput = "state.president"
              passwordInput = "President@2026"
              errorMessage = null
            },
            label = { Text("Super Admin 2 (மைக்கேல் ஆல்வின் - தலைவர்)", fontSize = 11.sp) },
            leadingIcon = { Icon(Icons.Default.Shield, contentDescription = null, modifier = Modifier.size(14.dp), tint = TnpaGold) }
          )

          // District Admin Quick Fill
          FilterChip(
            selected = usernameInput == "trichy.president",
            onClick = {
              usernameInput = "trichy.president"
              passwordInput = "Trichy@2026"
              errorMessage = null
            },
            label = { Text("District Admin (திருச்சி தலைவர்)", fontSize = 11.sp) },
            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp), tint = TnpaGreen) }
          )

          // New Admin with One-Time Setup Key
          FilterChip(
            selected = usernameInput == "state.treasurer",
            onClick = {
              usernameInput = "state.treasurer"
              passwordInput = "TNPA-ADM-7842-TRZ"
              errorMessage = null
            },
            label = { Text("One-Time Setup Key Login (புதிய நிர்வாகி)", fontSize = 11.sp) },
            leadingIcon = { Icon(Icons.Default.Key, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color(0xFF2563EB)) }
          )
        }
      }
    }
  }
}

// ============================================================================
// FIRST-TIME PASSWORD SETUP DIALOG (FOR ONBOARDING WITH SETUP KEY)
// ============================================================================

@Composable
fun FirstTimePasswordSetupDialog(
  adminId: String,
  onComplete: (AdminAccount) -> Unit,
  onDismiss: () -> Unit
) {
  var newPassword by remember { mutableStateOf("") }
  var confirmPassword by remember { mutableStateOf("") }
  var errorMsg by remember { mutableStateOf<String?>(null) }
  var isVisible by remember { mutableStateOf(false) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Key, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("முதல்முறை கடவுச்சொல் அமைவு", fontWeight = FontWeight.Bold, fontSize = 16.sp)
      }
    },
    text = {
      Column {
        Text(
          text = "பாதுகாப்புக் கொள்கையின்படி, Super Admin வழங்கிய One-Time Setup Key சரிபார்க்கப்பட்டது. உங்கள் கணக்கிற்கு புதிய பாதுகாப்பான நிரந்தர கடவுச்சொல்லை அமைக்கவும்.",
          fontSize = 12.sp,
          color = Color.DarkGray,
          lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (errorMsg != null) {
          Text(text = errorMsg!!, color = Color(0xFFDC2626), fontSize = 11.sp, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(6.dp))
        }

        OutlinedTextField(
          value = newPassword,
          onValueChange = {
            newPassword = it
            errorMsg = null
          },
          label = { Text("புதிய கடவுச்சொல் (New Password)") },
          visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = confirmPassword,
          onValueChange = {
            confirmPassword = it
            errorMsg = null
          },
          label = { Text("கடவுச்சொல் உறுதிப்படுத்தல் (Confirm)") },
          visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
          trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
              Icon(if (isVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility, contentDescription = null)
            }
          },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))
        Text("• குறைந்தபட்சம் 6 எழுத்துகள் இருக்க வேண்டும்.", fontSize = 11.sp, color = Color.Gray)
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (newPassword.length < 6) {
            errorMsg = "கடவுச்சொல் குறைந்தபட்சம் 6 எழுத்துகள் இருக்க வேண்டும்."
            return@Button
          }
          if (newPassword != confirmPassword) {
            errorMsg = "இரண்டு கடவுச்சொற்களும் பொருந்தவில்லை."
            return@Button
          }

          val result = AdminApprovalRepository.completeFirstTimePasswordSetup(adminId, newPassword)
          result.onSuccess { updated ->
            onComplete(updated)
          }.onFailure { err ->
            errorMsg = err.message ?: "பிழை ஏற்பட்டது."
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
      ) {
        Text("கடவுச்சொல்லை சேமி & செயல்படுத்து", fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      OutlinedButton(onClick = onDismiss) {
        Text("ரத்து செய்")
      }
    }
  )
}

// ============================================================================
// AUTHORIZED ADMIN DASHBOARD (SUPER, STATE & DISTRICT)
// ============================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAuthorizedDashboardView(
  admin: AdminAccount,
  onLogout: () -> Unit,
  onAdminUpdated: () -> Unit,
  onNavigateToAiMonitoring: () -> Unit = {},
  rtmpUrl: String,
  streamKey: String,
  hlsUrl: String,
  isBroadcasting: Boolean,
  streamStatus: StreamStatus,
  breakingNews: String,
  healthReport: StreamHealthReport?,
  onUpdateSettings: (String, String, String, String) -> Unit,
  onToggleBroadcast: (Boolean) -> Unit,
  onHealthStatusUpdated: (StreamStatus, StreamHealthReport) -> Unit
) {
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current

  // Selected Dashboard Tab
  // 0 -> Executive Dashboard, 1 -> Member Approvals, 2 -> Welfare Approvals, 3 -> Job Approvals, 4 -> Admin Hierarchy (Super Admin), 5 -> Audit Logs, 6 -> Live Stream Control (Super/State)
  var selectedTab by remember { mutableIntStateOf(0) }

  // Admin Management Modals (Super Admin)
  var showCreateAdminModal by remember { mutableStateOf(false) }
  var showResetAccessModal by remember { mutableStateOf<AdminAccount?>(null) }
  var showChangeDistrictModal by remember { mutableStateOf<AdminAccount?>(null) }
  var generatedSetupKeyNotification by remember { mutableStateOf<Pair<String, String>?>(null) }
  var showAppDownloadModal by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(TnpaOffWhite)
  ) {
    // Top Bar with Admin Identity Badge & Logout
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
      colors = CardDefaults.cardColors(
        containerColor = when (admin.role) {
          AdminRole.SUPER_ADMIN -> TnpaRedPrimary
          AdminRole.STATE_ADMIN -> Color(0xFF1E293B)
          AdminRole.DISTRICT_ADMIN -> Color(0xFF0F766E)
        }
      ),
      elevation = CardDefaults.cardElevation(4.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = when (admin.role) {
                AdminRole.SUPER_ADMIN -> Icons.Default.VerifiedUser
                AdminRole.STATE_ADMIN -> Icons.Default.Shield
                AdminRole.DISTRICT_ADMIN -> Icons.Default.LocationOn
              },
              contentDescription = null,
              tint = TnpaGold,
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = admin.fullName,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
              )
              Text(
                text = "${admin.designation} ${if (admin.assignedDistrict != null) "• ${admin.assignedDistrict}" else ""}",
                color = TnpaGold,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }

          // Top Header Action Buttons
          Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
            // APK Download & Distribution Shortcut Button
            Button(
              onClick = { showAppDownloadModal = true },
              colors = ButtonDefaults.buttonColors(containerColor = TnpaGold),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.height(34.dp).testTag("btn_admin_top_apk_download")
            ) {
              Icon(Icons.Default.Download, contentDescription = null, tint = TnpaJetBlack, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("APK", fontSize = 11.sp, fontWeight = FontWeight.Black, color = TnpaJetBlack)
            }

            // Logout Button
            OutlinedButton(
              onClick = onLogout,
              colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
              border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(Color.White, Color.LightGray))),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.height(34.dp)
            ) {
              Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("வெளியேறு", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }

        // Role Scope Pill
        Spacer(modifier = Modifier.height(8.dp))
        Card(
          shape = RoundedCornerShape(6.dp),
          colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.25f))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Security, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = when (admin.role) {
                AdminRole.SUPER_ADMIN -> "முழுமையான கட்டுப்பாட்டு அதிகாரம் (Full Super Admin Master Authority)"
                AdminRole.STATE_ADMIN -> "மாநில அளவிலான கண்காணிப்பு & ஒப்புதல் அதிகாரம் (State-Wide 38 Districts Scope)"
                AdminRole.DISTRICT_ADMIN -> "மாவட்டத் தரவு தனிமைப்படுத்தல்: ${admin.assignedDistrict} மட்டும்"
              },
              color = Color.White,
              fontSize = 11.sp,
              fontWeight = FontWeight.Normal
            )
          }
        }
      }
    }

    // Role-Adaptive Tabs
    ScrollableTabRow(
      selectedTabIndex = selectedTab,
      containerColor = Color.White,
      contentColor = TnpaRedPrimary,
      edgePadding = 12.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        text = { Text("📊 டேஷ்போர்டு", fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) },
        icon = { Icon(Icons.Default.Dashboard, contentDescription = null, modifier = Modifier.size(16.dp)) },
        modifier = Modifier.testTag("admin_tab_dashboard")
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = { Text("உறுப்பினர் ஒப்புதல்", fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) },
        icon = { Icon(Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(16.dp)) },
        modifier = Modifier.testTag("admin_tab_members")
      )
      Tab(
        selected = selectedTab == 2,
        onClick = { selectedTab = 2 },
        text = { Text("நலத்திட்ட ஒப்புதல்", fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal) },
        icon = { Icon(Icons.Default.VolunteerActivism, contentDescription = null, modifier = Modifier.size(16.dp)) },
        modifier = Modifier.testTag("admin_tab_welfare")
      )
      Tab(
        selected = selectedTab == 3,
        onClick = { selectedTab = 3 },
        text = { Text("வேலைவாய்ப்பு ஒப்புதல்", fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal) },
        icon = { Icon(Icons.Default.Work, contentDescription = null, modifier = Modifier.size(16.dp)) },
        modifier = Modifier.testTag("admin_tab_jobs")
      )
      if (admin.role == AdminRole.SUPER_ADMIN) {
        Tab(
          selected = selectedTab == 4,
          onClick = { selectedTab = 4 },
          text = { Text("நிர்வாகிகள் கட்டமைப்பு", fontWeight = if (selectedTab == 4) FontWeight.Bold else FontWeight.Normal) },
          icon = { Icon(Icons.Default.AccountTree, contentDescription = null, modifier = Modifier.size(16.dp)) },
          modifier = Modifier.testTag("admin_tab_hierarchy")
        )
      }
      Tab(
        selected = selectedTab == 5,
        onClick = { selectedTab = 5 },
        text = { Text("Audit Logs", fontWeight = if (selectedTab == 5) FontWeight.Bold else FontWeight.Normal) },
        icon = { Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(16.dp)) },
        modifier = Modifier.testTag("admin_tab_audit")
      )
      if (admin.role == AdminRole.SUPER_ADMIN || admin.role == AdminRole.STATE_ADMIN) {
        Tab(
          selected = selectedTab == 6,
          onClick = { selectedTab = 6 },
          text = { Text("TV நேரலை கட்டுப்பாடு", fontWeight = if (selectedTab == 6) FontWeight.Bold else FontWeight.Normal) },
          icon = { Icon(Icons.Default.LiveTv, contentDescription = null, modifier = Modifier.size(16.dp)) },
          modifier = Modifier.testTag("admin_tab_tv")
        )
      }
    }

    // Main Content Panel
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)
    ) {
      when (selectedTab) {
        0 -> AdminExecutiveDashboardSubScreen(
          admin = admin,
          onNavigateToTab = { selectedTab = it },
          onNavigateToAiMonitoring = onNavigateToAiMonitoring,
          streamStatus = streamStatus,
          onOpenAppDownloadModal = { showAppDownloadModal = true },
          onActionTaken = onAdminUpdated
        )
        1 -> MemberApprovalsSubScreen(admin = admin, onActionTaken = onAdminUpdated)
        2 -> WelfareApprovalsSubScreen(admin = admin, onActionTaken = onAdminUpdated)
        3 -> JobApprovalsSubScreen(admin = admin, onActionTaken = onAdminUpdated)
        4 -> {
          if (admin.role == AdminRole.SUPER_ADMIN) {
            AdminHierarchyManagementSubScreen(
              admin = admin,
              onCreateAdminClick = { showCreateAdminModal = true },
              onResetAccessClick = { showResetAccessModal = it },
              onChangeDistrictClick = { showChangeDistrictModal = it },
              onStatusChange = { targetId, status ->
                AdminApprovalRepository.updateAdminStatus(admin, targetId, status)
                onAdminUpdated()
              }
            )
          } else {
            Text("அனுமதியில்லை (Unauthorized).")
          }
        }
        5 -> AuditLogsSubScreen(admin = admin)
        6 -> {
          // Live TV Broadcast Control Screen
          AdminBroadcastControlScreen(
            rtmpUrl = rtmpUrl,
            streamKey = streamKey,
            hlsUrl = hlsUrl,
            isBroadcasting = isBroadcasting,
            streamStatus = streamStatus,
            breakingNews = breakingNews,
            healthReport = healthReport ?: StreamHealthReport(
              status = streamStatus,
              statusCode = 200,
              statusMessage = "Stream Normal",
              latencyMs = 30,
              isServerReachable = true,
              isHlsValid = true,
              activeBitrate = "4500 kbps",
              fps = 60,
              resolution = "1080p",
              timestamp = "Live"
            ),
            onUpdateSettings = onUpdateSettings,
            onToggleBroadcast = onToggleBroadcast,
            onHealthStatusUpdated = onHealthStatusUpdated
          )
        }
      }
    }
  }

  // App Download Distribution Modal
  if (showAppDownloadModal) {
    AppDownloadModal(onDismiss = { showAppDownloadModal = false })
  }

  // --------------------------------------------------------------------------
  // SUPER ADMIN MODALS
  // --------------------------------------------------------------------------

  // Create Admin Modal
  if (showCreateAdminModal) {
    CreateAdminModalDialog(
      callingAdmin = admin,
      onDismiss = { showCreateAdminModal = false },
      onAdminCreated = { newAdmin, setupKey ->
        showCreateAdminModal = false
        generatedSetupKeyNotification = Pair(newAdmin.fullName, setupKey)
        onAdminUpdated()
      }
    )
  }

  // Generated Setup Key Notification Modal
  if (generatedSetupKeyNotification != null) {
    val (name, key) = generatedSetupKeyNotification!!
    AlertDialog(
      onDismissRequest = { generatedSetupKeyNotification = null },
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TnpaGreen)
          Spacer(modifier = Modifier.width(8.dp))
          Text("One-Time Setup Key உருவாக்கப்பட்டது!", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
      },
      text = {
        Column {
          Text(
            text = "நிர்வாகி: $name\n\nஇந்த One-Time Setup Key-ஐ சம்பந்தப்பட்ட நிர்வாகியிடம் பாதுகாப்பாக வழங்கவும். அவர் முதல் முறை உள்நுழைந்து தனது புதிய கடவுச்சொல்லை அமைப்பார்.",
            fontSize = 13.sp
          )
          Spacer(modifier = Modifier.height(12.dp))
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
            shape = RoundedCornerShape(8.dp)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = key,
                fontWeight = FontWeight.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace,
                color = Color(0xFF92400E)
              )
              IconButton(onClick = {
                clipboardManager.setText(AnnotatedString(key))
                Toast.makeText(context, "Setup Key நகலெடுக்கப்பட்டது (Copied)", Toast.LENGTH_SHORT).show()
              }) {
                Icon(Icons.Default.ContentCopy, contentDescription = null, tint = Color(0xFF92400E))
              }
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = { generatedSetupKeyNotification = null },
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
        ) {
          Text("சரி (Done)")
        }
      }
    )
  }

  // Reset Access Modal
  if (showResetAccessModal != null) {
    val target = showResetAccessModal!!
    AlertDialog(
      onDismissRequest = { showResetAccessModal = null },
      title = { Text("அணுகல் மீட்டமைப்பு (Reset Access)") },
      text = {
        Text("நிர்வாகி ${target.fullName} (${target.designation}) கணக்கிற்கு புதிய One-Time Setup Key உருவாக்க விரும்புகிறீர்களா? பழைய கடவுச்சொல் செல்லாததாக்கப்படும்.")
      },
      confirmButton = {
        Button(
          onClick = {
            val result = AdminApprovalRepository.resetAdminAccess(admin, target.id)
            result.onSuccess { newKey ->
              showResetAccessModal = null
              generatedSetupKeyNotification = Pair(target.fullName, newKey)
              onAdminUpdated()
            }.onFailure {
              Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
        ) {
          Text("புதிய Setup Key உருவாக்கு")
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { showResetAccessModal = null }) {
          Text("ரத்து")
        }
      }
    )
  }

  // Change District Modal
  if (showChangeDistrictModal != null) {
    val target = showChangeDistrictModal!!
    var selectedNewDistrict by remember { mutableStateOf(PredefinedAdminPosts.TAMIL_NADU_DISTRICTS.first()) }

    AlertDialog(
      onDismissRequest = { showChangeDistrictModal = null },
      title = { Text("மாவட்ட ஒதுக்கீடு மாற்றம்") },
      text = {
        Column {
          Text("நிர்வாகி: ${target.fullName} (${target.designation})\nதற்போதைய மாவட்டம்: ${target.assignedDistrict}")
          Spacer(modifier = Modifier.height(12.dp))
          Text("புதிய மாவட்டத்தைத் தேர்ந்தெடுக்கவும்:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(6.dp))

          // District selector simplified
          LazyColumn(modifier = Modifier.height(200.dp)) {
            items(PredefinedAdminPosts.TAMIL_NADU_DISTRICTS) { dist ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable { selectedNewDistrict = dist }
                  .background(if (selectedNewDistrict == dist) Color(0xFFE2E8F0) else Color.Transparent)
                  .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                if (selectedNewDistrict == dist) {
                  Icon(Icons.Default.Check, contentDescription = null, tint = TnpaGreen, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(6.dp))
                }
                Text(text = dist, fontSize = 12.sp, fontWeight = if (selectedNewDistrict == dist) FontWeight.Bold else FontWeight.Normal)
              }
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            val result = AdminApprovalRepository.changeDistrictAssignment(admin, target.id, selectedNewDistrict)
            result.onSuccess {
              showChangeDistrictModal = null
              Toast.makeText(context, "மாவட்டம் மாற்றப்பட்டது", Toast.LENGTH_SHORT).show()
              onAdminUpdated()
            }.onFailure {
              Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
        ) {
          Text("சேமி")
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { showChangeDistrictModal = null }) {
          Text("ரத்து")
        }
      }
    )
  }
}

// ============================================================================
// SUB-SCREEN 1: MEMBER APPROVALS (ONE AUTHORIZED APPROVAL RULE)
// ============================================================================

@Composable
fun MemberApprovalsSubScreen(
  admin: AdminAccount,
  onActionTaken: () -> Unit
) {
  val context = LocalContext.current
  val applications = remember(admin) {
    AdminApprovalRepository.getMemberApplicationsForAdmin(admin)
  }

  var filterStatus by remember { mutableStateOf<ApprovalStatus?>(null) }
  var rejectionDialogAppId by remember { mutableStateOf<String?>(null) }
  var rejectionReason by remember { mutableStateOf("") }

  val filteredApps = applications.filter {
    filterStatus == null || it.status == filterStatus
  }

  Column(modifier = Modifier.fillMaxSize()) {
    // Header & Filter Chips
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      shape = RoundedCornerShape(10.dp)
    ) {
      Column(modifier = Modifier.padding(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "உறுப்பினர் விண்ணப்பங்கள் (${filteredApps.size})",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = TnpaJetBlack
          )
          Text(
            text = "விதி: 1 Authorized Approval = Final",
            fontSize = 11.sp,
            color = TnpaGreen,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          FilterChip(
            selected = filterStatus == null,
            onClick = { filterStatus = null },
            label = { Text("அனைத்தும்", fontSize = 11.sp) }
          )
          FilterChip(
            selected = filterStatus == ApprovalStatus.PENDING,
            onClick = { filterStatus = ApprovalStatus.PENDING },
            label = { Text("நிலுவை (Pending)", fontSize = 11.sp) }
          )
          FilterChip(
            selected = filterStatus == ApprovalStatus.APPROVED,
            onClick = { filterStatus = ApprovalStatus.APPROVED },
            label = { Text("ஒப்புதல் அளிக்கப்பட்டது", fontSize = 11.sp) }
          )
        }
      }
    }

    if (filteredApps.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(32.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(Icons.Default.Badge, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(48.dp))
          Spacer(modifier = Modifier.height(8.dp))
          Text("விண்ணப்பங்கள் ஏதுமில்லை", color = Color.Gray, fontSize = 13.sp)
        }
      }
    } else {
      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
      ) {
        items(filteredApps, key = { it.applicationId }) { app ->
          MemberApprovalItemCard(
            item = app,
            admin = admin,
            onApprove = {
              val res = AdminApprovalRepository.approveMemberApplication(admin, app.applicationId)
              res.onSuccess {
                Toast.makeText(context, "${app.fullName} விண்ணப்பத்திற்கு ஒப்புதல் அளிக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
                onActionTaken()
              }.onFailure {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
              }
            },
            onReject = {
              rejectionDialogAppId = app.applicationId
            }
          )
        }
      }
    }
  }

  // Rejection Dialog
  if (rejectionDialogAppId != null) {
    AlertDialog(
      onDismissRequest = { rejectionDialogAppId = null },
      title = { Text("விண்ணப்பத்தை நிராகரித்தல்") },
      text = {
        Column {
          Text("நிராகரிப்பிற்கான காரணத்தைக் குறிப்பிடவும்:")
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = rejectionReason,
            onValueChange = { rejectionReason = it },
            placeholder = { Text("சான்றிதழ் / விபரம் முழுமையடையவில்லை...") },
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (rejectionReason.isNotBlank()) {
              AdminApprovalRepository.rejectMemberApplication(admin, rejectionDialogAppId!!, rejectionReason)
              rejectionDialogAppId = null
              rejectionReason = ""
              onActionTaken()
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626))
        ) {
          Text("நிராகரி")
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { rejectionDialogAppId = null }) {
          Text("ரத்து")
        }
      }
    )
  }
}

@Composable
fun MemberApprovalItemCard(
  item: MemberApprovalItem,
  admin: AdminAccount,
  onApprove: () -> Unit,
  onReject: () -> Unit
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(2.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "${item.fullName} (${item.tamilName})",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TnpaJetBlack
          )
          Text(
            text = "Member ID: ${item.memberId} • App ID: ${item.applicationId}",
            fontSize = 11.sp,
            color = Color.Gray,
            fontFamily = FontFamily.Monospace
          )
        }

        // Status Badge
        Card(
          shape = RoundedCornerShape(6.dp),
          colors = CardDefaults.cardColors(
            containerColor = Color(item.status.colorHex).copy(alpha = 0.15f)
          )
        ) {
          Text(
            text = item.status.labelTamil,
            color = Color(item.status.colorHex),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE2E8F0))

      // Member Details Grid
      Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(1f)) {
          Text("மாவட்டம்:", fontSize = 11.sp, color = Color.Gray)
          Text(item.district, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TnpaRedDark)
          Spacer(modifier = Modifier.height(4.dp))
          Text("பதவி / பிரிவு:", fontSize = 11.sp, color = Color.Gray)
          Text(item.designation, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
        Column(modifier = Modifier.weight(1f)) {
          Text("தொலைபேசி:", fontSize = 11.sp, color = Color.Gray)
          Text(item.mobile, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
          Spacer(modifier = Modifier.height(4.dp))
          Text("அனுபவம் & ரத்த வகை:", fontSize = 11.sp, color = Color.Gray)
          Text("${item.experienceYears} வருடங்கள் • ${item.bloodGroup}", fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
      }

      // If already approved/rejected, show audit badge
      if (item.status != ApprovalStatus.PENDING) {
        Spacer(modifier = Modifier.height(8.dp))
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(6.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
        ) {
          Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Verified, contentDescription = null, tint = Color(item.status.colorHex), modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "${if (item.status == ApprovalStatus.APPROVED) "ஒப்புதல் வழங்கியவர்" else "நிராகரித்தவர்"}: ${item.approvedByAdminName} [${item.approvedByRole}] • ${item.approvedAt ?: ""}",
              fontSize = 10.sp,
              color = Color.DarkGray
            )
          }
        }
      }

      // Actions if Pending
      if (item.status == ApprovalStatus.PENDING) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = onApprove,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen),
            shape = RoundedCornerShape(8.dp)
          ) {
            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("ஒப்புதல் அளி (Approve)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }

          OutlinedButton(
            onClick = onReject,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDC2626)),
            shape = RoundedCornerShape(8.dp)
          ) {
            Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("நிராகரி", fontSize = 12.sp)
          }
        }
      }
    }
  }
}

// ============================================================================
// SUB-SCREEN 2: WELFARE SCHEME APPROVALS (CENTRAL + TN GOVT)
// ============================================================================

@Composable
fun WelfareApprovalsSubScreen(
  admin: AdminAccount,
  onActionTaken: () -> Unit
) {
  val context = LocalContext.current
  val applications = remember(admin) {
    AdminApprovalRepository.getWelfareApplicationsForAdmin(admin)
  }

  var filterSchemeType by remember { mutableStateOf<String?>(null) }

  val filteredWelfare = applications.filter {
    filterSchemeType == null || it.govtTypeLabel == filterSchemeType
  }

  Column(modifier = Modifier.fillMaxSize()) {
    // Header & Disclaimer Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
      shape = RoundedCornerShape(10.dp)
    ) {
      Column(modifier = Modifier.padding(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.VolunteerActivism, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "அரசு நலவாரிய விண்ணப்பங்கள் சரிபார்ப்பு (${filteredWelfare.size})",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = Color(0xFF1E3A8A)
          )
        }
        Text(
          text = "அறிவிப்பு: நமது அமைப்பின் உள் சரிபார்ப்பு/பரிந்துரைக்குப் பின் விண்ணப்பம் அதிகாரப்பூர்வ அரசு போர்ட்டலில் பூர்த்தி செய்யப்படும்.",
          fontSize = 11.sp,
          color = Color(0xFF1D4ED8),
          modifier = Modifier.padding(top = 4.dp)
        )
      }
    }

    if (filteredWelfare.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(32.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(Icons.Default.AssignmentTurnedIn, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(48.dp))
          Spacer(modifier = Modifier.height(8.dp))
          Text("நலத்திட்ட விண்ணப்பங்கள் ஏதுமில்லை", color = Color.Gray, fontSize = 13.sp)
        }
      }
    } else {
      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
      ) {
        items(filteredWelfare, key = { it.welfareAppId }) { item ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = item.schemeTitleTamil,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TnpaJetBlack
                  )
                  Text(
                    text = "${item.govtTypeLabel} • Ref ID: ${item.welfareAppId}",
                    fontSize = 11.sp,
                    color = TnpaGold,
                    fontWeight = FontWeight.Bold
                  )
                }

                Card(
                  shape = RoundedCornerShape(6.dp),
                  colors = CardDefaults.cardColors(containerColor = Color(item.status.colorHex).copy(alpha = 0.15f))
                ) {
                  Text(
                    text = item.status.labelTamil,
                    color = Color(item.status.colorHex),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                  )
                }
              }

              HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE2E8F0))

              Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                  Text("விண்ணப்பதாரர்:", fontSize = 11.sp, color = Color.Gray)
                  Text("${item.applicantName} (${item.memberId})", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                  Spacer(modifier = Modifier.height(4.dp))
                  Text("மாவட்டம் & தொழில்:", fontSize = 11.sp, color = Color.Gray)
                  Text("${item.district} • ${item.occupation}", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
                Column(modifier = Modifier.weight(1f)) {
                  Text("தொலைபேசி:", fontSize = 11.sp, color = Color.Gray)
                  Text(item.mobile, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                  Spacer(modifier = Modifier.height(4.dp))
                  Text("மாத வருமானம்:", fontSize = 11.sp, color = Color.Gray)
                  Text("₹${item.monthlyIncome}/மாதம்", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TnpaGreen)
                }
              }

              if (item.status != WelfareAppStatus.PENDING_VERIFICATION) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                  modifier = Modifier.fillMaxWidth(),
                  shape = RoundedCornerShape(6.dp),
                  colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
                ) {
                  Text(
                    text = "சரிபார்த்தவர்: ${item.approvedByAdminName} (${item.approvedByRole}) • ${item.approvedAt}",
                    fontSize = 10.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(6.dp)
                  )
                }
              }

              if (item.status == WelfareAppStatus.PENDING_VERIFICATION) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  Button(
                    onClick = {
                      AdminApprovalRepository.approveWelfareApplication(admin, item.welfareAppId)
                      Toast.makeText(context, "நலத்திட்ட விண்ணப்பம் சரிபார்க்கப்பட்டு ஒப்புதல் வழங்கப்பட்டது!", Toast.LENGTH_SHORT).show()
                      onActionTaken()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen),
                    shape = RoundedCornerShape(8.dp)
                  ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("உள் ஒப்புதல் அளி", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                  }

                  OutlinedButton(
                    onClick = {
                      AdminApprovalRepository.rejectWelfareApplication(admin, item.welfareAppId, "தகுதி சான்றுகள் போதாது")
                      Toast.makeText(context, "விண்ணப்பம் நிராகரிக்கப்பட்டது.", Toast.LENGTH_SHORT).show()
                      onActionTaken()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDC2626)),
                    shape = RoundedCornerShape(8.dp)
                  ) {
                    Text("நிராகரி", fontSize = 12.sp)
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

// ============================================================================
// SUB-SCREEN 3: ADMIN HIERARCHY & SETUP (SUPER ADMIN ONLY)
// ============================================================================

@Composable
fun AdminHierarchyManagementSubScreen(
  admin: AdminAccount,
  onCreateAdminClick: () -> Unit,
  onResetAccessClick: (AdminAccount) -> Unit,
  onChangeDistrictClick: (AdminAccount) -> Unit,
  onStatusChange: (String, AdminStatus) -> Unit
) {
  val superCount = AdminApprovalRepository.getSuperAdminCount()
  val stateCount = AdminApprovalRepository.getStateAdminCount()
  val districtCount = AdminApprovalRepository.getDistrictAdminCount()
  val allAdmins = AdminApprovalRepository.getAllAdmins()

  var filterRole by remember { mutableStateOf<AdminRole?>(null) }

  val filteredAdmins = allAdmins.filter {
    filterRole == null || it.role == filterRole
  }

  Column(modifier = Modifier.fillMaxSize()) {
    // Capacity Overview Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaRedDark),
      shape = RoundedCornerShape(12.dp)
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "அதிகார படிநிலை & வரம்பு (Hierarchy & Limits)",
            color = TnpaGold,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )

          Button(
            onClick = onCreateAdminClick,
            colors = ButtonDefaults.buttonColors(containerColor = TnpaGold),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.height(34.dp)
          ) {
            Icon(Icons.Default.PersonAdd, contentDescription = null, tint = TnpaJetBlack, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("புதிய நிர்வாகி", fontSize = 11.sp, color = TnpaJetBlack, fontWeight = FontWeight.Bold)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.12f)),
            shape = RoundedCornerShape(8.dp)
          ) {
            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
              Text("Super Admins", color = TnpaOffWhite, fontSize = 10.sp)
              Text("$superCount / 2", color = Color.White, fontWeight = FontWeight.Black, fontSize = 16.sp)
            }
          }

          Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.12f)),
            shape = RoundedCornerShape(8.dp)
          ) {
            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
              Text("State Admins", color = TnpaOffWhite, fontSize = 10.sp)
              Text("$stateCount / 7 Max", color = TnpaGold, fontWeight = FontWeight.Black, fontSize = 16.sp)
            }
          }

          Card(
            modifier = Modifier.weight(1.2f),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.12f)),
            shape = RoundedCornerShape(8.dp)
          ) {
            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
              Text("District Admins", color = TnpaOffWhite, fontSize = 10.sp)
              Text("$districtCount / 114 Max", color = Color(0xFF86EFAC), fontWeight = FontWeight.Black, fontSize = 16.sp)
            }
          }
        }
      }
    }

    // Role Filter Chips
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      FilterChip(
        selected = filterRole == null,
        onClick = { filterRole = null },
        label = { Text("அனைத்து நிர்வாகிகள் (${allAdmins.size})", fontSize = 11.sp) }
      )
      FilterChip(
        selected = filterRole == AdminRole.STATE_ADMIN,
        onClick = { filterRole = AdminRole.STATE_ADMIN },
        label = { Text("State Admins ($stateCount/7)", fontSize = 11.sp) }
      )
      FilterChip(
        selected = filterRole == AdminRole.DISTRICT_ADMIN,
        onClick = { filterRole = AdminRole.DISTRICT_ADMIN },
        label = { Text("District Admins ($districtCount/114)", fontSize = 11.sp) }
      )
    }

    // Admin List
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      items(filteredAdmins, key = { it.id }) { item ->
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(2.dp)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = item.fullName,
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp,
                  color = TnpaJetBlack
                )
                Text(
                  text = "${item.designation} ${if (item.assignedDistrict != null) "• ${item.assignedDistrict}" else ""}",
                  fontSize = 12.sp,
                  color = TnpaRedDark,
                  fontWeight = FontWeight.SemiBold
                )
                Text(
                  text = "Username: ${item.username} • Phone: ${item.mobileNumber}",
                  fontSize = 11.sp,
                  color = Color.Gray
                )
              }

              Card(
                shape = RoundedCornerShape(6.dp),
                colors = CardDefaults.cardColors(
                  containerColor = Color(item.status.badgeColorHex).copy(alpha = 0.15f)
                )
              ) {
                Text(
                  text = item.status.labelTamil,
                  color = Color(item.status.badgeColorHex),
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
              }
            }

            // Pending Setup Notice & One-time key if available
            if (item.status == AdminStatus.PENDING_VERIFICATION && item.oneTimeSetupKey != null) {
              Spacer(modifier = Modifier.height(8.dp))
              Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                shape = RoundedCornerShape(6.dp)
              ) {
                Row(
                  modifier = Modifier.padding(8.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(Icons.Default.Key, contentDescription = null, tint = Color(0xFF92400E), modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Setup Key: ${item.oneTimeSetupKey}",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = Color(0xFF92400E)
                  )
                }
              }
            }

            // Management Action Buttons (for non-Super Admins)
            if (item.role != AdminRole.SUPER_ADMIN) {
              HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE2E8F0))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                // Reset Access Button
                OutlinedButton(
                  onClick = { onResetAccessClick(item) },
                  modifier = Modifier.weight(1f),
                  shape = RoundedCornerShape(6.dp),
                  contentPadding = androidx.compose.foundation.layout.PaddingValues(4.dp)
                ) {
                  Icon(Icons.Default.LockReset, contentDescription = null, modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("Reset Key", fontSize = 10.sp)
                }

                // Change District (District Admins only)
                if (item.role == AdminRole.DISTRICT_ADMIN) {
                  OutlinedButton(
                    onClick = { onChangeDistrictClick(item) },
                    modifier = Modifier.weight(1.2f),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(4.dp)
                  ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("மாவட்டம் மாற்று", fontSize = 10.sp)
                  }
                }

                // Suspend / Activate Toggle
                if (item.status == AdminStatus.ACTIVE || item.status == AdminStatus.PENDING_VERIFICATION) {
                  Button(
                    onClick = { onStatusChange(item.id, AdminStatus.SUSPENDED) },
                    modifier = Modifier.weight(1.1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(4.dp)
                  ) {
                    Text("இடைநிறுத்து", fontSize = 10.sp)
                  }
                } else if (item.status == AdminStatus.SUSPENDED) {
                  Button(
                    onClick = { onStatusChange(item.id, AdminStatus.ACTIVE) },
                    modifier = Modifier.weight(1.1f),
                    colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(4.dp)
                  ) {
                    Text("செயல்படுத்து", fontSize = 10.sp)
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

// ============================================================================
// CREATE ADMIN MODAL DIALOG (SUPER ADMIN ONLY)
// ============================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAdminModalDialog(
  callingAdmin: AdminAccount,
  onDismiss: () -> Unit,
  onAdminCreated: (AdminAccount, String) -> Unit
) {
  val context = LocalContext.current

  var selectedRole by remember { mutableStateOf(AdminRole.STATE_ADMIN) }
  var fullName by remember { mutableStateOf("") }
  var username by remember { mutableStateOf("") }
  var mobileNumber by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var selectedDistrict by remember { mutableStateOf(PredefinedAdminPosts.TAMIL_NADU_DISTRICTS.first()) }
  var selectedDesignation by remember {
    mutableStateOf(PredefinedAdminPosts.STATE_ADMIN_POSTS.first())
  }
  var errorMsg by remember { mutableStateOf<String?>(null) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.PersonAdd, contentDescription = null, tint = TnpaRedPrimary)
        Spacer(modifier = Modifier.width(8.dp))
        Text("புதிய நிர்வாகி உருவாக்கம்", fontWeight = FontWeight.Bold, fontSize = 16.sp)
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
      ) {
        if (errorMsg != null) {
          Text(text = errorMsg!!, color = Color(0xFFDC2626), fontSize = 12.sp, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(8.dp))
        }

        // Role Selector (State Admin or District Admin only)
        Text("நிர்வாகி வகை (Role):", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          FilterChip(
            selected = selectedRole == AdminRole.STATE_ADMIN,
            onClick = {
              selectedRole = AdminRole.STATE_ADMIN
              selectedDesignation = PredefinedAdminPosts.STATE_ADMIN_POSTS.first()
            },
            label = { Text("State Admin (Max 7)", fontSize = 11.sp) }
          )
          FilterChip(
            selected = selectedRole == AdminRole.DISTRICT_ADMIN,
            onClick = {
              selectedRole = AdminRole.DISTRICT_ADMIN
              selectedDesignation = PredefinedAdminPosts.DISTRICT_ADMIN_POSTS.first()
            },
            label = { Text("District Admin (Max 114)", fontSize = 11.sp) }
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Designation Selector from Predefined fixed list
        Text("அதிகாரப்பூர்வ பதவி (Designation):", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        val postsList = if (selectedRole == AdminRole.STATE_ADMIN) {
          PredefinedAdminPosts.STATE_ADMIN_POSTS
        } else {
          PredefinedAdminPosts.DISTRICT_ADMIN_POSTS
        }

        postsList.forEach { post ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { selectedDesignation = post }
              .background(if (selectedDesignation == post) Color(0xFFE2E8F0) else Color.Transparent)
              .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              if (selectedDesignation == post) Icons.Default.CheckCircle else Icons.Default.Person,
              contentDescription = null,
              tint = if (selectedDesignation == post) TnpaGreen else Color.Gray,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = post, fontSize = 11.sp, fontWeight = if (selectedDesignation == post) FontWeight.Bold else FontWeight.Normal)
          }
        }

        // If District Admin, show district selector
        if (selectedRole == AdminRole.DISTRICT_ADMIN) {
          Spacer(modifier = Modifier.height(8.dp))
          Text("ஒதுக்கப்படும் மாவட்டம் (District):", fontSize = 12.sp, fontWeight = FontWeight.Bold)

          var expandedDistrictDropdown by remember { mutableStateOf(false) }

          ExposedDropdownMenuBox(
            expanded = expandedDistrictDropdown,
            onExpandedChange = { expandedDistrictDropdown = !expandedDistrictDropdown }
          ) {
            OutlinedTextField(
              value = selectedDistrict,
              onValueChange = {},
              readOnly = true,
              trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDistrictDropdown) },
              modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
              shape = RoundedCornerShape(8.dp)
            )
            ExposedDropdownMenu(
              expanded = expandedDistrictDropdown,
              onDismissRequest = { expandedDistrictDropdown = false }
            ) {
              PredefinedAdminPosts.TAMIL_NADU_DISTRICTS.forEach { dist ->
                DropdownMenuItem(
                  text = { Text(dist, fontSize = 12.sp) },
                  onClick = {
                    selectedDistrict = dist
                    expandedDistrictDropdown = false
                  }
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Full Name
        OutlinedTextField(
          value = fullName,
          onValueChange = { fullName = it },
          label = { Text("முழுப் பெயர் (Full Name)") },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Username
        OutlinedTextField(
          value = username,
          onValueChange = { username = it },
          label = { Text("பயனர்பெயர் (Username)") },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mobile
        OutlinedTextField(
          value = mobileNumber,
          onValueChange = { mobileNumber = it },
          label = { Text("தொலைபேசி எண் (Mobile)") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email
        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          label = { Text("மின்னஞ்சல் (Email - விருப்பத்தேர்வு)") },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(8.dp)
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (fullName.isBlank() || username.isBlank() || mobileNumber.isBlank()) {
            errorMsg = "பெயர், பயனர்பெயர் மற்றும் தொலைபேசி எண்ணை உள்ளிடவும்."
            return@Button
          }

          val result = AdminApprovalRepository.createAdminAccount(
            callingAdmin = callingAdmin,
            username = username,
            fullName = fullName,
            role = selectedRole,
            designation = selectedDesignation,
            assignedDistrict = if (selectedRole == AdminRole.DISTRICT_ADMIN) selectedDistrict else null,
            mobileNumber = mobileNumber,
            email = email
          )

          result.onSuccess { (newAdmin, key) ->
            onAdminCreated(newAdmin, key)
          }.onFailure { err ->
            errorMsg = err.message ?: "நிர்வாகி உருவாக்குவதில் பிழை."
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
      ) {
        Text("உருவாக்கு & Setup Key வழங்கு", fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      OutlinedButton(onClick = onDismiss) {
        Text("ரத்து")
      }
    }
  )
}

// ============================================================================
// SUB-SCREEN 4: AUDIT LOGS TRAIL
// ============================================================================

@Composable
fun AuditLogsSubScreen(admin: AdminAccount) {
  val logs = remember(admin) {
    AdminApprovalRepository.getAuditLogs(admin)
  }

  Column(modifier = Modifier.fillMaxSize()) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      shape = RoundedCornerShape(10.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.History, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("பாதுகாப்பு தணிக்கை பதிவுகள் (Audit Trail)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        Badge(containerColor = Color(0xFFE2E8F0)) {
          Text("${logs.size} பதிவுகள்", color = TnpaJetBlack, fontSize = 11.sp, modifier = Modifier.padding(4.dp))
        }
      }
    }

    if (logs.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(32.dp),
        contentAlignment = Alignment.Center
      ) {
        Text("பதிவுகள் ஏதுமில்லை", color = Color.Gray, fontSize = 13.sp)
      }
    } else {
      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
      ) {
        items(logs, key = { it.logId }) { entry ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(1.dp)
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = entry.actionType.labelTamil,
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = TnpaRedDark
                )
                Text(
                  text = entry.timestamp,
                  fontSize = 10.sp,
                  color = Color.Gray,
                  fontFamily = FontFamily.Monospace
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = entry.detailsTamil,
                fontSize = 12.sp,
                color = TnpaJetBlack
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "செயல் புரிந்தவர்: ${entry.adminName} [${entry.adminRole}]",
                fontSize = 10.sp,
                color = Color(0xFF64748B)
              )
            }
          }
        }
      }
    }
  }
}

// ============================================================================
// JOB POSTING APPROVALS SUBSCREEN
// ============================================================================

@Composable
fun JobApprovalsSubScreen(
  admin: AdminAccount,
  onActionTaken: () -> Unit
) {
  val context = LocalContext.current
  var selectedFilter by remember { mutableStateOf<JobPostingStatus?>(null) }
  var rejectionDialogJob by remember { mutableStateOf<JobPostingItem?>(null) }
  var rejectionReasonInput by remember { mutableStateOf("") }

  val allJobs = AdminApprovalRepository.getAllJobPostings(admin)
  val displayedJobs = if (selectedFilter == null) allJobs else allJobs.filter { it.status == selectedFilter }
  val pendingCount = allJobs.count { it.status == JobPostingStatus.PENDING_APPROVAL }

  Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
    // Header & Filter Chips
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Work, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "வேலைவாய்ப்பு விளம்பர ஒப்புதல் மையம்",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = TnpaJetBlack
            )
          }

          if (pendingCount > 0) {
            Badge(containerColor = TnpaRedPrimary) {
              Text("$pendingCount நிலுவை", color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(2.dp))
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Status Filter
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          FilterChip(
            selected = selectedFilter == null,
            onClick = { selectedFilter = null },
            label = { Text("அனைத்தும் (${allJobs.size})", fontSize = 10.sp) }
          )
          FilterChip(
            selected = selectedFilter == JobPostingStatus.PENDING_APPROVAL,
            onClick = { selectedFilter = JobPostingStatus.PENDING_APPROVAL },
            label = { Text("ஒப்புதல் தேவை ($pendingCount)", fontSize = 10.sp, fontWeight = FontWeight.Bold) }
          )
          FilterChip(
            selected = selectedFilter == JobPostingStatus.APPROVED_ACTIVE,
            onClick = { selectedFilter = JobPostingStatus.APPROVED_ACTIVE },
            label = { Text("செயலில் உள்ளவை", fontSize = 10.sp) }
          )
        }
      }
    }

    if (displayedJobs.isEmpty()) {
      Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
        Text("விளம்பரங்கள் எதுவும் இல்லை.", color = Color.Gray, fontSize = 13.sp)
      }
    } else {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(displayedJobs, key = { it.id }) { job ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
          ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                      when (job.status) {
                        JobPostingStatus.PENDING_APPROVAL -> Color(0xFFFEF3C7)
                        JobPostingStatus.APPROVED_ACTIVE -> Color(0xFFDCFCE7)
                        JobPostingStatus.REJECTED -> Color(0xFFFEE2E2)
                        JobPostingStatus.FILLED, JobPostingStatus.EXPIRED -> Color(0xFFF1F5F9)
                      }
                    )
                    .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                  Text(
                    text = job.status.labelTamil,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = when (job.status) {
                      JobPostingStatus.PENDING_APPROVAL -> Color(0xFFB45309)
                      JobPostingStatus.APPROVED_ACTIVE -> Color(0xFF15803D)
                      JobPostingStatus.REJECTED -> Color(0xFFB91C1C)
                      JobPostingStatus.FILLED, JobPostingStatus.EXPIRED -> Color(0xFF475569)
                    }
                  )
                }

                Text(text = "ID: ${job.id}", fontSize = 10.sp, color = Color.Gray, fontFamily = FontFamily.Monospace)
              }

              Text(text = job.jobTitle, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
              Text(text = "நிறுவனம் / ஒப்பந்ததாரர்: ${job.employerName} (${job.contactMobile})", fontSize = 11.sp, color = TnpaRedDark)
              Text(text = "📍 இடம்: ${job.district} (${job.workLocation}) | ஊதியம்: ${job.dailyWageOrSalary}", fontSize = 11.sp, color = Color(0xFF334155))
              Text(text = "தேவைப்படும் ஆட்கள்: ${job.workersNeeded} நபர்கள் | பிரிவு: ${job.companyType.labelTamil}", fontSize = 11.sp, color = Color(0xFF64748B))

              if (job.moderationRemarks != null) {
                Text(text = "குறிப்பு: ${job.moderationRemarks}", fontSize = 11.sp, color = Color(0xFFB91C1C), fontWeight = FontWeight.Medium)
              }

              HorizontalDivider(color = Color(0xFFF1F5F9))

              // Admin Actions (Super Admin / Authorized Admins)
              if (job.status == JobPostingStatus.PENDING_APPROVAL) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  Button(
                    onClick = {
                      AdminApprovalRepository.approveJobPosting(admin, job.id, "விவரங்கள் சரிபார்க்கப்பட்டு வெளியிட அனுமதி அளிக்கப்பட்டது.")
                      onActionTaken()
                      Toast.makeText(context, "வேலைவாய்ப்பு உடனடியாக வெளியிடப்பட்டது!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.weight(1f).height(36.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen)
                  ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("ஒப்புதல் அளி (Approve)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                  }

                  OutlinedButton(
                    onClick = {
                      rejectionDialogJob = job
                      rejectionReasonInput = ""
                    },
                    modifier = Modifier.weight(1f).height(36.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDC2626))
                  ) {
                    Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("நிராகரி (Reject)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                  }
                }
              } else if (job.status == JobPostingStatus.APPROVED_ACTIVE) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.End
                ) {
                  OutlinedButton(
                    onClick = {
                      AdminApprovalRepository.closeJobPosting(admin, job.id)
                      onActionTaken()
                    },
                    modifier = Modifier.height(34.dp),
                    shape = RoundedCornerShape(8.dp)
                  ) {
                    Text("ஆட்கள் எடுக்கப்பட்டுவிட்டது (Mark Filled)", fontSize = 10.sp)
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  // Rejection Dialog
  if (rejectionDialogJob != null) {
    AlertDialog(
      onDismissRequest = { rejectionDialogJob = null },
      title = { Text("வேலை விளம்பரம் நிராகரிப்பு", fontWeight = FontWeight.Bold, fontSize = 14.sp) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text("நிராகரிப்பதற்கான காரணத்தை உள்ளிடவும்:", fontSize = 12.sp)
          OutlinedTextField(
            value = rejectionReasonInput,
            onValueChange = { rejectionReasonInput = it },
            label = { Text("காரணம் (Reason)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            AdminApprovalRepository.rejectJobPosting(
              admin,
              rejectionDialogJob!!.id,
              if (rejectionReasonInput.isNotBlank()) rejectionReasonInput else "போதிய விவரங்கள் இல்லை."
            )
            rejectionDialogJob = null
            onActionTaken()
          },
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626))
        ) {
          Text("நிராகரி")
        }
      },
      dismissButton = {
        TextButton(onClick = { rejectionDialogJob = null }) {
          Text("ரத்து")
        }
      }
    )
  }
}

