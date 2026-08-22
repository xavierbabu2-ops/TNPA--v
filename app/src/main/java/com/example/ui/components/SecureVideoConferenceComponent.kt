package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.FrontHand
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PresentToAll
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FirebaseConferenceRealtimeManager
import com.example.data.OfficeBearerRepository
import com.example.model.AdminHierarchyLevel
import com.example.model.ConferenceChatMessage
import com.example.model.ConferenceParticipant
import com.example.model.ConferenceRoom
import com.example.model.ExecutiveVerificationResult
import com.example.model.MeetingResolution
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaLightBlue
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedPrimary
import com.example.ui.theme.TnpaRedSoft
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Secure Video Conferencing UI Component for TNPA Members and District/State Officials.
 * Backed by Firebase Realtime Database for live session presence, chat, and resolutions.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SecureVideoConferenceComponent(
  modifier: Modifier = Modifier,
  initialRoom: ConferenceRoom? = null,
  onLeaveMeeting: () -> Unit = {}
) {
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current

  // Firebase Realtime State
  val isFirebaseConnected by FirebaseConferenceRealtimeManager.isFirebaseConnected.collectAsState()
  val participants by FirebaseConferenceRealtimeManager.liveParticipants.collectAsState()
  val chatMessages by FirebaseConferenceRealtimeManager.liveChat.collectAsState()
  val resolutions by FirebaseConferenceRealtimeManager.liveResolutions.collectAsState()
  val securityLogs by FirebaseConferenceRealtimeManager.securityAuditLogs.collectAsState()
  val floatingReactions by FirebaseConferenceRealtimeManager.floatingReactions.collectAsState()

  // Conference Session & Auth State
  var currentRoom by remember { mutableStateOf(initialRoom ?: FirebaseConferenceRealtimeManager.activeRoom.value) }
  var authenticatedParticipant by remember { mutableStateOf<ConferenceParticipant?>(null) }
  var authToken by remember { mutableStateOf("") }
  var isInsideMeeting by remember { mutableStateOf(false) }

  // Security Gate Input State
  var mobileOrIdInput by remember { mutableStateOf("") }
  var passcodeInput by remember { mutableStateOf("") }
  var verificationError by remember { mutableStateOf<String?>(null) }

  // Stage View Modes: 0 -> Grid Mode, 1 -> Spotlight Mode, 2 -> Agenda & Presentation Mode
  var stageViewMode by remember { mutableIntStateOf(0) }
  var spotlightedParticipantId by remember { mutableStateOf<String?>(null) }

  // Slide-over Bottom Sheets
  var showChatAndResolutionsSheet by remember { mutableStateOf(false) }
  var showAttendanceRosterSheet by remember { mutableStateOf(false) }
  var showSecurityAuditSheet by remember { mutableStateOf(false) }
  var showNewResolutionDialog by remember { mutableStateOf(false) }
  var showLeaveConfirmDialog by remember { mutableStateOf(false) }

  // Chat / Resolution input
  var chatInputText by remember { mutableStateOf("") }
  var newResolutionTitle by remember { mutableStateOf("") }
  var newResolutionProposer by remember { mutableStateOf("") }

  // Meeting Duration Timer
  var meetingElapsedSeconds by remember { mutableIntStateOf(1680) }
  LaunchedEffect(isInsideMeeting) {
    if (isInsideMeeting) {
      while (true) {
        delay(1000)
        meetingElapsedSeconds++
      }
    }
  }

  val formattedTimer = remember(meetingElapsedSeconds) {
    val mins = (meetingElapsedSeconds % 3600) / 60
    val secs = meetingElapsedSeconds % 60
    val hrs = meetingElapsedSeconds / 3600
    if (hrs > 0) String.format(Locale.getDefault(), "%02d:%02d:%02d", hrs, mins, secs)
    else String.format(Locale.getDefault(), "%02d:%02d", mins, secs)
  }

  // Active speaker animation
  val activeSpeaker = remember(participants, spotlightedParticipantId) {
    participants.find { it.id == spotlightedParticipantId }
      ?: participants.find { it.isSpeaking }
      ?: participants.firstOrNull { it.isHost }
      ?: participants.firstOrNull()
  }

  // Main UI
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFF0B0F19)) // Premium Deep Slate Canvas
      .testTag("secure_video_conference_component")
  ) {
    if (!isInsideMeeting || authenticatedParticipant == null) {
      // ======================================================================
      // 1. SECURITY & AUTHORIZATION GATE (அங்கீகரிப்பு நுழைவாயில்)
      // ======================================================================
      SecurityAuthorizationGateView(
        room = currentRoom,
        mobileOrIdInput = mobileOrIdInput,
        onMobileOrIdChange = { mobileOrIdInput = it },
        passcodeInput = passcodeInput,
        onPasscodeChange = { passcodeInput = it },
        isFirebaseConnected = isFirebaseConnected,
        onQuickVerifyOfficial = { sampleId ->
          val result = FirebaseConferenceRealtimeManager.verifyAndAuthorizeOfficial(sampleId, targetRoom = currentRoom)
          when (result) {
            is ExecutiveVerificationResult.Success -> {
              authenticatedParticipant = result.participant
              authToken = result.token
              isInsideMeeting = true
              currentRoom?.let { room ->
                FirebaseConferenceRealtimeManager.connectToConferenceSession(room, result.participant)
              }
              Toast.makeText(context, "✅ வரவேற்பு ${result.participant.tamilName}! வீடியோ மாநாட்டில் இணைக்கப்பட்டீர்கள்.", Toast.LENGTH_SHORT).show()
            }
            is ExecutiveVerificationResult.Denied -> {
              verificationError = result.reasonTamil
            }
          }
        },
        onVerifyAndJoin = {
          val result = FirebaseConferenceRealtimeManager.verifyAndAuthorizeOfficial(mobileOrIdInput, targetRoom = currentRoom)
          when (result) {
            is ExecutiveVerificationResult.Success -> {
              authenticatedParticipant = result.participant
              authToken = result.token
              isInsideMeeting = true
              currentRoom?.let { room ->
                FirebaseConferenceRealtimeManager.connectToConferenceSession(room, result.participant)
              }
              Toast.makeText(context, "✅ அங்கீகரிக்கப்பட்டது: ${result.participant.tamilName}", Toast.LENGTH_SHORT).show()
            }
            is ExecutiveVerificationResult.Denied -> {
              verificationError = result.reasonTamil
            }
          }
        }
      )
    } else {
      // ======================================================================
      // 2. ACTIVE SECURE VIDEO CONFERENCING SESSION (நேரலை மாநாட்டு அரங்கு)
      // ======================================================================
      Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
          // 2.1. Top Secure Conference Header Bar
          ConferenceTopHeaderBar(
            room = currentRoom,
            participant = authenticatedParticipant!!,
            authToken = authToken,
            elapsedTimer = formattedTimer,
            isFirebaseConnected = isFirebaseConnected,
            participantsCount = participants.size,
            stageViewMode = stageViewMode,
            onStageViewChange = { stageViewMode = it },
            onShowAttendance = { showAttendanceRosterSheet = true },
            onShowSecurityLogs = { showSecurityAuditSheet = true }
          )

          // 2.2. Main Conference Stage (Grid / Spotlight / Agenda Presentation)
          Box(
            modifier = Modifier
              .weight(1f)
              .fillMaxWidth()
              .padding(horizontal = 8.dp, vertical = 6.dp)
          ) {
            when (stageViewMode) {
              0 -> {
                // Multi-Participant Dynamic Responsive Grid
                ConferenceVideoGridStage(
                  participants = participants,
                  myParticipantId = authenticatedParticipant!!.id,
                  onToggleSpotlight = { partId ->
                    spotlightedParticipantId = partId
                    stageViewMode = 1
                  }
                )
              }
              1 -> {
                // Speaker Spotlight Mode
                ConferenceSpotlightStage(
                  activeSpeaker = activeSpeaker,
                  allParticipants = participants,
                  myParticipantId = authenticatedParticipant!!.id,
                  onSelectParticipantForSpotlight = { partId ->
                    spotlightedParticipantId = partId
                  },
                  onSwitchToGrid = { stageViewMode = 0 }
                )
              }
              2 -> {
                // Screen Share & Official Agenda / Resolutions Presentation Mode
                ConferenceAgendaPresentationStage(
                  room = currentRoom,
                  activeSpeaker = activeSpeaker,
                  resolutions = resolutions,
                  onOpenNewResolutionDialog = { showNewResolutionDialog = true }
                )
              }
            }

            // Floating Animated Reactions Overlay
            FloatingReactionsCanvas(floatingReactions)
          }

          // 2.3. Bottom Glassmorphic Control Dock (கட்டுப்பாட்டு பலகை)
          ConferenceControlDock(
            myParticipant = participants.find { it.id == authenticatedParticipant!!.id } ?: authenticatedParticipant!!,
            onToggleMic = {
              val currentMic = authenticatedParticipant!!.isMicOn
              FirebaseConferenceRealtimeManager.updateMyStatus(
                roomId = currentRoom?.id ?: "CONF-001",
                participantId = authenticatedParticipant!!.id,
                isMicOn = !currentMic,
                isSpeaking = !currentMic
              )
              authenticatedParticipant = authenticatedParticipant!!.copy(isMicOn = !currentMic, isSpeaking = !currentMic)
            },
            onToggleVideo = {
              val currentVideo = authenticatedParticipant!!.isVideoOn
              FirebaseConferenceRealtimeManager.updateMyStatus(
                roomId = currentRoom?.id ?: "CONF-001",
                participantId = authenticatedParticipant!!.id,
                isVideoOn = !currentVideo
              )
              authenticatedParticipant = authenticatedParticipant!!.copy(isVideoOn = !currentVideo)
            },
            onToggleHandRaise = {
              val currentHand = authenticatedParticipant!!.isHandRaised
              FirebaseConferenceRealtimeManager.updateMyStatus(
                roomId = currentRoom?.id ?: "CONF-001",
                participantId = authenticatedParticipant!!.id,
                isHandRaised = !currentHand
              )
              authenticatedParticipant = authenticatedParticipant!!.copy(isHandRaised = !currentHand)
              Toast.makeText(context, if (!currentHand) "✋ கை உயர்த்தப்பட்டது (Hand Raised)" else "கை இறக்கப்பட்டது", Toast.LENGTH_SHORT).show()
            },
            onSendReaction = { emoji ->
              FirebaseConferenceRealtimeManager.triggerReaction(emoji)
            },
            onOpenChat = { showChatAndResolutionsSheet = true },
            onOpenAttendance = { showAttendanceRosterSheet = true },
            onHostMuteAll = {
              currentRoom?.id?.let { rId ->
                FirebaseConferenceRealtimeManager.hostMuteAll(rId, authenticatedParticipant!!.id)
                Toast.makeText(context, "🔇 அனைத்து உறுப்பினர்களும் மியூட் செய்யப்பட்டனர்", Toast.LENGTH_SHORT).show()
              }
            },
            onLeaveClicked = { showLeaveConfirmDialog = true }
          )
        }
      }
    }
  }

  // ==========================================================================
  // 3. DIALOGS & BOTTOM SHEETS
  // ==========================================================================

  // Verification Error Dialog
  if (verificationError != null) {
    AlertDialog(
      onDismissRequest = { verificationError = null },
      icon = { Icon(Icons.Default.Security, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(36.dp)) },
      title = { Text("அனுமதி மறுக்கப்பட்டது (Access Denied)", fontWeight = FontWeight.Bold, color = TnpaRedDark) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(verificationError ?: "", fontSize = 13.sp, lineHeight = 18.sp, color = TnpaJetBlack)
          HorizontalDivider()
          Text("உதவிக்கு: மாநில தலைமையகம் 7010131915 / 9789331681", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
        }
      },
      confirmButton = {
        Button(
          onClick = { verificationError = null },
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
        ) {
          Text("சரி", color = TnpaPureWhite)
        }
      }
    )
  }

  // Leave Conference Confirmation Dialog
  if (showLeaveConfirmDialog) {
    AlertDialog(
      onDismissRequest = { showLeaveConfirmDialog = false },
      icon = { Icon(Icons.Default.CallEnd, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(32.dp)) },
      title = { Text("மாநாட்டை விட்டு வெளியேறவா?", fontWeight = FontWeight.Bold) },
      text = {
        Text("நீங்கள் வீடியோ மாநாட்டை விட்டு வெளியேற விரும்புகிறீர்களா? அமர்வின் வருகைப் பதிவு Firebase-ல் சேமிக்கப்படும்.")
      },
      confirmButton = {
        Button(
          onClick = {
            showLeaveConfirmDialog = false
            currentRoom?.let { room ->
              authenticatedParticipant?.let { part ->
                FirebaseConferenceRealtimeManager.leaveConferenceSession(room.id, part.id)
              }
            }
            isInsideMeeting = false
            authenticatedParticipant = null
            onLeaveMeeting()
            Toast.makeText(context, "மாநாட்டிலிருந்து வெளியேறினீர்கள்", Toast.LENGTH_SHORT).show()
          },
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
        ) {
          Text("வெளியேறு (Leave)", color = TnpaPureWhite, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { showLeaveConfirmDialog = false }) {
          Text("தொடர்க (Stay)")
        }
      }
    )
  }

  // Chat & Resolutions Bottom Sheet
  if (showChatAndResolutionsSheet) {
    ConferenceChatAndResolutionsSheet(
      chatMessages = chatMessages,
      resolutions = resolutions,
      myParticipant = authenticatedParticipant,
      chatInput = chatInputText,
      onChatInputChange = { chatInputText = it },
      onSendMessage = {
        if (chatInputText.isNotBlank() && authenticatedParticipant != null && currentRoom != null) {
          val msg = ConferenceChatMessage(
            senderName = authenticatedParticipant!!.tamilName,
            senderDesignation = "${authenticatedParticipant!!.designation} (${authenticatedParticipant!!.district})",
            level = authenticatedParticipant!!.level,
            district = authenticatedParticipant!!.district,
            message = chatInputText.trim()
          )
          FirebaseConferenceRealtimeManager.sendChatMessage(currentRoom!!.id, msg)
          chatInputText = ""
        }
      },
      onProposeResolution = { showNewResolutionDialog = true },
      onDismiss = { showChatAndResolutionsSheet = false }
    )
  }

  // Attendance Roster Bottom Sheet
  if (showAttendanceRosterSheet) {
    ConferenceAttendanceRosterSheet(
      participants = participants,
      currentRoom = currentRoom,
      onDismiss = { showAttendanceRosterSheet = false }
    )
  }

  // Security Audit Log Sheet
  if (showSecurityAuditSheet) {
    ConferenceSecurityAuditSheet(
      securityLogs = securityLogs,
      authToken = authToken,
      isFirebaseConnected = isFirebaseConnected,
      onDismiss = { showSecurityAuditSheet = false }
    )
  }

  // New Resolution Creation Dialog
  if (showNewResolutionDialog) {
    AlertDialog(
      onDismissRequest = { showNewResolutionDialog = false },
      title = { Text("📜 புதிய மாநாட்டு தீர்மானம் முன்மொழிவு", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text("மாநில மாநாட்டில் நிறைவேற்றப்பட வேண்டிய தீர்மானத்தின் விவரத்தை உள்ளிடவும்:", fontSize = 12.sp, color = Color.Gray)
          OutlinedTextField(
            value = newResolutionTitle,
            onValueChange = { newResolutionTitle = it },
            label = { Text("தீர்மான உரை (Resolution in Tamil)") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
          )
          OutlinedTextField(
            value = newResolutionProposer.ifBlank { authenticatedParticipant?.tamilName ?: "" },
            onValueChange = { newResolutionProposer = it },
            label = { Text("முன்மொழிபவர் பெயர் & பதவி") },
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (newResolutionTitle.isNotBlank() && currentRoom != null) {
              val res = MeetingResolution(
                resolutionNo = resolutions.size + 1,
                titleTamil = newResolutionTitle.trim(),
                proposedBy = newResolutionProposer.ifBlank { authenticatedParticipant?.tamilName ?: "மாநில தலைமை" },
                secondedBy = "38 மாவட்ட நிர்வாகிகள் ஆமோதிப்பு",
                status = "ஏகமனதாக நிறைவேற்றப்பட்டது (Passed)",
                timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
              )
              FirebaseConferenceRealtimeManager.addResolution(currentRoom!!.id, res)
              newResolutionTitle = ""
              showNewResolutionDialog = false
              Toast.makeText(context, "✅ தீர்மானம் #${res.resolutionNo} நிறைவேற்றப்பட்டு அரட்டையில் பகிரப்பட்டது!", Toast.LENGTH_LONG).show()
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = TnpaGold)
        ) {
          Text("நிறைவேற்று (Pass Resolution)", color = TnpaJetBlack, fontWeight = FontWeight.Black)
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { showNewResolutionDialog = false }) {
          Text("ரத்து")
        }
      }
    )
  }
}

// ============================================================================
// 1. SECURITY & AUTHORIZATION GATE VIEW
// ============================================================================

@Composable
private fun SecurityAuthorizationGateView(
  room: ConferenceRoom?,
  mobileOrIdInput: String,
  onMobileOrIdChange: (String) -> Unit,
  passcodeInput: String,
  onPasscodeChange: (String) -> Unit,
  isFirebaseConnected: Boolean,
  onQuickVerifyOfficial: (String) -> Unit,
  onVerifyAndJoin: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(6.dp))

    // Top Emblem & Security Shield Badge
    Box(
      modifier = Modifier
        .size(64.dp)
        .clip(CircleShape)
        .background(Brush.radialGradient(listOf(TnpaRedPrimary, TnpaJetBlack))),
      contentAlignment = Alignment.Center
    ) {
      Icon(Icons.Default.Security, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(34.dp))
    }

    Text(
      text = "டிஎன்பிஏ நேரலை வீடியோ மாநாட்டு அரங்கு",
      fontSize = 17.sp,
      fontWeight = FontWeight.Black,
      color = TnpaPureWhite,
      textAlign = TextAlign.Center
    )

    Text(
      text = "அங்கீகரிக்கப்பட்ட மாநில, மண்டல, மாவட்ட & ஒன்றிய நிர்வாகிகளுக்கான பிரத்யேக வீடியோ அரங்கம் (Firebase RTDB Secured)",
      fontSize = 12.sp,
      color = Color(0xFF94A3B8),
      textAlign = TextAlign.Center,
      lineHeight = 16.sp
    )

    // Firebase Connection Status Pill
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .clip(RoundedCornerShape(20.dp))
        .background(if (isFirebaseConnected) Color(0xFF064E3B) else Color(0xFF78350F))
        .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
      Icon(
        if (isFirebaseConnected) Icons.Default.CloudDone else Icons.Default.Refresh,
        contentDescription = null,
        tint = if (isFirebaseConnected) Color(0xFF34D399) else Color(0xFFFBBF24),
        modifier = Modifier.size(13.dp)
      )
      Spacer(modifier = Modifier.width(5.dp))
      Text(
        text = if (isFirebaseConnected) "Firebase Realtime DB நேரலையில் உள்ளது" else "உள்ளூர் பாதுகாப்பு ஒத்திசைவு (Syncing)",
        fontSize = 11.sp,
        color = TnpaPureWhite,
        fontWeight = FontWeight.Bold
      )
    }

    // Active Meeting Details Card
    if (room != null) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        border = BorderStroke(1.dp, TnpaGold.copy(alpha = 0.6f))
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
                .background(TnpaRedPrimary)
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text("🔴 நேரலை அமர்வு", color = TnpaPureWhite, fontSize = 9.sp, fontWeight = FontWeight.Black)
            }

            Text("Room: ${room.meetingCode}", color = TnpaGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }

          Text(
            text = room.titleTamil,
            color = TnpaPureWhite,
            fontWeight = FontWeight.Black,
            fontSize = 14.sp,
            lineHeight = 19.sp
          )

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(13.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("தலைமை: ${room.hostName} (${room.hostRole})", fontSize = 11.sp, color = Color(0xFFCBD5E1))
          }
        }
      }
    }

    // Quick One-Tap Verification for Known Executives (முக்கிய நிர்வாகிகள் விரைவு நுழைவு)
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF131C2E)),
      border = BorderStroke(1.dp, Color(0xFF334155))
    ) {
      Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.VpnKey, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("நிர்வாகிகள் உடனடி சரிபார்ப்பு (Quick Official Entry):", fontSize = 12.sp, color = TnpaGold, fontWeight = FontWeight.Bold)
        }

        // Quick Official Chips
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          QuickOfficialButton(
            title = "சேவியர் பாபு",
            role = "பொதுச் செயலாளர்",
            badgeColor = TnpaRedPrimary,
            modifier = Modifier.weight(1f),
            onClick = { onQuickVerifyOfficial("7010131915") }
          )
          QuickOfficialButton(
            title = "ஆல்வின்",
            role = "மாநிலத் தலைவர்",
            badgeColor = Color(0xFFB91C1C),
            modifier = Modifier.weight(1f),
            onClick = { onQuickVerifyOfficial("9789331681") }
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          QuickOfficialButton(
            title = "சக்திவேல்",
            role = "மாநில பொருளாளர்",
            badgeColor = Color(0xFFD97706),
            modifier = Modifier.weight(1f),
            onClick = { onQuickVerifyOfficial("9080047281") }
          )
          QuickOfficialButton(
            title = "செல்வராஜ் (கோவை)",
            role = "மாவட்டத் தலைவர்",
            badgeColor = Color(0xFF059669),
            modifier = Modifier.weight(1f),
            onClick = { onQuickVerifyOfficial("9894123456") }
          )
        }
      }
    }

    // Manual Credential Entry Form
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
      border = BorderStroke(1.dp, Color(0xFF475569))
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("அல்லது உங்கள் பதிவு செய்யப்பட்ட தொலைபேசி எண்ணை உள்ளிடவும்:", fontSize = 12.sp, color = TnpaPureWhite, fontWeight = FontWeight.Bold)

        OutlinedTextField(
          value = mobileOrIdInput,
          onValueChange = onMobileOrIdChange,
          label = { Text("மொபைல் எண் / நிர்வாகி ID (10 Digits)") },
          placeholder = { Text("எ.கா. 9842156780 அல்லது TNPA-OB-004") },
          leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = TnpaGold) },
          colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TnpaPureWhite,
            unfocusedTextColor = TnpaPureWhite,
            focusedContainerColor = Color(0xFF0F172A),
            unfocusedContainerColor = Color(0xFF0F172A),
            focusedBorderColor = TnpaGold,
            unfocusedBorderColor = Color(0xFF475569)
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_conf_official_mobile")
        )

        Button(
          onClick = onVerifyAndJoin,
          modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .testTag("btn_verify_and_join_conference"),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TnpaGold)
        ) {
          Icon(Icons.Default.Security, contentDescription = null, tint = TnpaJetBlack, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("பாதுகாப்பாக மாநாட்டில் இணையவும் (Secure Enter)", color = TnpaJetBlack, fontWeight = FontWeight.Black, fontSize = 13.sp)
        }
      }
    }
  }
}

@Composable
private fun QuickOfficialButton(
  title: String,
  role: String,
  badgeColor: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .background(badgeColor.copy(alpha = 0.2f))
      .border(1.dp, badgeColor, RoundedCornerShape(8.dp))
      .clickable { onClick() }
      .padding(horizontal = 8.dp, vertical = 6.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(title, color = TnpaPureWhite, fontSize = 11.sp, fontWeight = FontWeight.Black)
      Text(role, color = TnpaGold, fontSize = 9.sp)
    }
  }
}

// ============================================================================
// 2. TOP CONFERENCE HEADER BAR
// ============================================================================

@Composable
private fun ConferenceTopHeaderBar(
  room: ConferenceRoom?,
  participant: ConferenceParticipant,
  authToken: String,
  elapsedTimer: String,
  isFirebaseConnected: Boolean,
  participantsCount: Int,
  stageViewMode: Int,
  onStageViewChange: (Int) -> Unit,
  onShowAttendance: () -> Unit,
  onShowSecurityLogs: () -> Unit
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(bottomStart = 14.dp, bottomEnd = 14.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0xFF111827)),
    border = BorderStroke(1.dp, Color(0xFF1F2937))
  ) {
    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Left Info
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(TnpaRedPrimary),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.VideoCall, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(18.dp))
          }
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = room?.titleTamil ?: "TNPA மாநில மாநாடு",
              color = TnpaPureWhite,
              fontWeight = FontWeight.Black,
              fontSize = 12.sp,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .clip(CircleShape)
                  .background(if (isFirebaseConnected) Color(0xFF22C55E) else Color(0xFFF59E0B))
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "RTDB நேரலை • $elapsedTimer • $participantsCount நிர்வாகிகள்",
                fontSize = 10.sp,
                color = Color(0xFF9CA3AF)
              )
            }
          }
        }

        // Right View Mode Switcher
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
          IconButton(
            onClick = { onStageViewChange(0) },
            modifier = Modifier
              .size(30.dp)
              .background(if (stageViewMode == 0) TnpaGold else Color(0xFF1F2937), CircleShape)
          ) {
            Icon(Icons.Default.GridView, contentDescription = "Grid View", tint = if (stageViewMode == 0) TnpaJetBlack else TnpaPureWhite, modifier = Modifier.size(16.dp))
          }

          IconButton(
            onClick = { onStageViewChange(1) },
            modifier = Modifier
              .size(30.dp)
              .background(if (stageViewMode == 1) TnpaGold else Color(0xFF1F2937), CircleShape)
          ) {
            Icon(Icons.Default.Person, contentDescription = "Spotlight View", tint = if (stageViewMode == 1) TnpaJetBlack else TnpaPureWhite, modifier = Modifier.size(16.dp))
          }

          IconButton(
            onClick = { onStageViewChange(2) },
            modifier = Modifier
              .size(30.dp)
              .background(if (stageViewMode == 2) TnpaGold else Color(0xFF1F2937), CircleShape)
          ) {
            Icon(Icons.Default.PresentToAll, contentDescription = "Agenda Presentation", tint = if (stageViewMode == 2) TnpaJetBlack else TnpaPureWhite, modifier = Modifier.size(16.dp))
          }

          IconButton(
            onClick = onShowSecurityLogs,
            modifier = Modifier
              .size(30.dp)
              .background(Color(0xFF1F2937), CircleShape)
          ) {
            Icon(Icons.Default.Shield, contentDescription = "Security Audit", tint = TnpaGold, modifier = Modifier.size(16.dp))
          }
        }
      }
    }
  }
}

// ============================================================================
// 3. STAGE VIEWS: (A) GRID (B) SPOTLIGHT (C) AGENDA PRESENTATION
// ============================================================================

@Composable
private fun ConferenceVideoGridStage(
  participants: List<ConferenceParticipant>,
  myParticipantId: String,
  onToggleSpotlight: (String) -> Unit
) {
  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(4.dp),
    horizontalArrangement = Arrangement.spacedBy(6.dp),
    verticalArrangement = Arrangement.spacedBy(6.dp)
  ) {
    items(participants) { participant ->
      VideoCellTile(
        participant = participant,
        isMe = participant.id == myParticipantId,
        onClick = { onToggleSpotlight(participant.id) }
      )
    }
  }
}

@Composable
private fun VideoCellTile(
  participant: ConferenceParticipant,
  isMe: Boolean,
  onClick: () -> Unit
) {
  val infiniteTransition = rememberInfiniteTransition(label = "speaking_pulse")
  val borderAlpha by infiniteTransition.animateFloat(
    initialValue = 0.4f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(tween(700, easing = LinearEasing), RepeatMode.Reverse),
    label = "alpha"
  )

  val isSpeaking = participant.isSpeaking && participant.isMicOn

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .aspectRatio(1.25f)
      .clickable { onClick() }
      .testTag("video_cell_${participant.id}"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
    border = if (isSpeaking) {
      BorderStroke(2.dp, TnpaGold.copy(alpha = borderAlpha))
    } else {
      BorderStroke(1.dp, Color(0xFF334155))
    }
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      // Camera Stream Simulation Canvas
      if (participant.isVideoOn) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.radialGradient(
                colors = listOf(
                  Color(participant.avatarColorHex).copy(alpha = 0.35f),
                  Color(0xFF0F172A)
                )
              )
            ),
          contentAlignment = Alignment.Center
        ) {
          // Video avatar portrait placeholder
          Box(
            modifier = Modifier
              .size(54.dp)
              .clip(CircleShape)
              .background(Color(participant.avatarColorHex)),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = participant.tamilName.take(2),
              color = TnpaPureWhite,
              fontWeight = FontWeight.Black,
              fontSize = 18.sp
            )
          }
        }
      } else {
        // Video Off State
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A)),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.VideocamOff, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text("கேமரா ஆஃப்", fontSize = 9.sp, color = Color.Gray)
          }
        }
      }

      // Hand Raised Floating Banner
      if (participant.isHandRaised) {
        Box(
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF59E0B))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.PanTool, contentDescription = null, tint = TnpaJetBlack, modifier = Modifier.size(11.dp))
            Spacer(modifier = Modifier.width(2.dp))
            Text("கை தூக்கியுள்ளார்", fontSize = 8.sp, color = TnpaJetBlack, fontWeight = FontWeight.Black)
          }
        }
      }

      // Host / State Official Badge
      if (participant.isHost || participant.level == AdminHierarchyLevel.STATE) {
        Box(
          modifier = Modifier
            .align(Alignment.TopStart)
            .padding(6.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(TnpaGold)
            .padding(horizontal = 5.dp, vertical = 2.dp)
        ) {
          Text("மாநில தலைமை", color = TnpaJetBlack, fontSize = 8.sp, fontWeight = FontWeight.Black)
        }
      }

      // Bottom Participant Info Pill
      Row(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .fillMaxWidth()
          .background(Color(0xCC000000))
          .padding(horizontal = 6.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = (if (isMe) "👤 (நான்) " else "") + participant.tamilName,
            color = TnpaPureWhite,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = "${participant.designation} • ${participant.district}",
            color = TnpaGold,
            fontSize = 8.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }

        // Mic Status Icon
        Icon(
          if (participant.isMicOn) Icons.Default.Mic else Icons.Default.MicOff,
          contentDescription = null,
          tint = if (participant.isMicOn) Color(0xFF22C55E) else Color(0xFFEF4444),
          modifier = Modifier.size(13.dp)
        )
      }
    }
  }
}

// 3.2. Spotlight Stage View
@Composable
private fun ConferenceSpotlightStage(
  activeSpeaker: ConferenceParticipant?,
  allParticipants: List<ConferenceParticipant>,
  myParticipantId: String,
  onSelectParticipantForSpotlight: (String) -> Unit,
  onSwitchToGrid: () -> Unit
) {
  Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
    // Main Spotlight Big Video
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
      border = BorderStroke(2.dp, TnpaGold)
    ) {
      Box(modifier = Modifier.fillMaxSize()) {
        if (activeSpeaker != null) {
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(
                Brush.radialGradient(
                  listOf(
                    Color(activeSpeaker.avatarColorHex).copy(alpha = 0.4f),
                    Color(0xFF020617)
                  )
                )
              ),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Box(
                modifier = Modifier
                  .size(90.dp)
                  .clip(CircleShape)
                  .background(Color(activeSpeaker.avatarColorHex)),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = activeSpeaker.tamilName.take(2),
                  color = TnpaPureWhite,
                  fontWeight = FontWeight.Black,
                  fontSize = 32.sp
                )
              }
              Spacer(modifier = Modifier.height(10.dp))
              Text(
                text = activeSpeaker.tamilName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = TnpaPureWhite
              )
              Text(
                text = "${activeSpeaker.designation} • ${activeSpeaker.district}",
                fontSize = 12.sp,
                color = TnpaGold
              )

              if (activeSpeaker.isSpeaking) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF065F46))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                  Icon(Icons.Default.VolumeUp, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("🎙️ பேச்சாளர் (Speaking)", color = TnpaPureWhite, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
              }
            }
          }
        }
      }
    }

    // Bottom Scrolling Filmstrip of all other officials
    LazyRow(
      modifier = Modifier
        .fillMaxWidth()
        .height(80.dp),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      items(allParticipants) { participant ->
        Card(
          modifier = Modifier
            .width(100.dp)
            .fillMaxHeight()
            .clickable { onSelectParticipantForSpotlight(participant.id) },
          shape = RoundedCornerShape(8.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
          border = if (participant.id == activeSpeaker?.id) BorderStroke(1.5.dp, TnpaGold) else null
        ) {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(4.dp)) {
              Text(participant.tamilName, fontSize = 9.sp, color = TnpaPureWhite, fontWeight = FontWeight.Bold, maxLines = 1)
              Text(participant.district, fontSize = 8.sp, color = TnpaGold, maxLines = 1)
            }
          }
        }
      }
    }
  }
}

// 3.3. Agenda & Resolutions Presentation Mode
@Composable
private fun ConferenceAgendaPresentationStage(
  room: ConferenceRoom?,
  activeSpeaker: ConferenceParticipant?,
  resolutions: List<MeetingResolution>,
  onOpenNewResolutionDialog: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    // Official Presentation Header Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
      border = BorderStroke(1.dp, TnpaGold)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("📋 மாநாட்டு நிகழ்ச்சி நிரல் & கோரிக்கைகள்", color = TnpaGold, fontWeight = FontWeight.Black, fontSize = 13.sp)
          Button(
            onClick = onOpenNewResolutionDialog,
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
            modifier = Modifier.height(28.dp)
          ) {
            Text("+ புதிய தீர்மானம்", fontSize = 10.sp, color = TnpaPureWhite, fontWeight = FontWeight.Bold)
          }
        }

        room?.agendaPoints?.forEach { point ->
          Text(text = point, color = TnpaPureWhite, fontSize = 12.sp, lineHeight = 17.sp)
        }
      }
    }

    // Passed Resolutions List
    Text("📜 நிறைவேற்றப்பட்ட அதிகாரப்பூர்வ தீர்மானங்கள் (${resolutions.size}):", fontSize = 13.sp, color = TnpaGold, fontWeight = FontWeight.Bold)

    resolutions.forEach { res ->
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        border = BorderStroke(1.dp, Color(0xFF334155))
      ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("தீர்மானம் #${res.resolutionNo}", color = TnpaGold, fontWeight = FontWeight.Black, fontSize = 11.sp)
            Text(res.status, color = Color(0xFF34D399), fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
          Text(res.titleTamil, color = TnpaPureWhite, fontSize = 12.sp, lineHeight = 16.sp)
          Text("முன்மொழிந்தவர்: ${res.proposedBy} • ${res.timestamp}", color = Color.Gray, fontSize = 9.sp)
        }
      }
    }
  }
}

// ============================================================================
// 4. FLOATING REACTIONS ANIMATION CANVAS
// ============================================================================

@Composable
private fun FloatingReactionsCanvas(reactions: List<Pair<String, Long>>) {
  Box(modifier = Modifier.fillMaxSize()) {
    reactions.takeLast(6).forEachIndexed { index, pair ->
      val emoji = pair.first
      val transition = rememberInfiniteTransition(label = "emoji_float")
      val offsetY by transition.animateFloat(
        initialValue = 0f,
        targetValue = -180f,
        animationSpec = infiniteRepeatable(tween(2500, easing = FastOutSlowInEasing)),
        label = "offset"
      )

      Text(
        text = emoji,
        fontSize = 28.sp,
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .offset(x = (-20 - (index * 18)).dp, y = offsetY.dp)
      )
    }
  }
}

// ============================================================================
// 5. BOTTOM GLASSMORPHIC CONTROL DOCK
// ============================================================================

@Composable
private fun ConferenceControlDock(
  myParticipant: ConferenceParticipant,
  onToggleMic: () -> Unit,
  onToggleVideo: () -> Unit,
  onToggleHandRaise: () -> Unit,
  onSendReaction: (String) -> Unit,
  onOpenChat: () -> Unit,
  onOpenAttendance: () -> Unit,
  onHostMuteAll: () -> Unit,
  onLeaveClicked: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp, vertical = 6.dp),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0xFF111827)),
    border = BorderStroke(1.dp, Color(0xFF374151))
  ) {
    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
      // Top Quick Emoji Reactions Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        listOf("👏", "👍", "🤝", "🎨", "🚩", "💡").forEach { emoji ->
          Box(
            modifier = Modifier
              .size(28.dp)
              .clip(CircleShape)
              .background(Color(0xFF1F2937))
              .clickable { onSendReaction(emoji) },
            contentAlignment = Alignment.Center
          ) {
            Text(emoji, fontSize = 14.sp)
          }
        }
      }

      // Main Meeting Action Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Mic Toggle
        IconButton(
          onClick = onToggleMic,
          modifier = Modifier
            .size(42.dp)
            .background(if (myParticipant.isMicOn) Color(0xFF22C55E) else Color(0xFF374151), CircleShape)
            .testTag("btn_conf_toggle_mic")
        ) {
          Icon(
            if (myParticipant.isMicOn) Icons.Default.Mic else Icons.Default.MicOff,
            contentDescription = "Mic Toggle",
            tint = TnpaPureWhite,
            modifier = Modifier.size(20.dp)
          )
        }

        // Video Camera Toggle
        IconButton(
          onClick = onToggleVideo,
          modifier = Modifier
            .size(42.dp)
            .background(if (myParticipant.isVideoOn) Color(0xFF3B82F6) else Color(0xFF374151), CircleShape)
            .testTag("btn_conf_toggle_video")
        ) {
          Icon(
            if (myParticipant.isVideoOn) Icons.Default.Videocam else Icons.Default.VideocamOff,
            contentDescription = "Video Toggle",
            tint = TnpaPureWhite,
            modifier = Modifier.size(20.dp)
          )
        }

        // Raise Hand
        IconButton(
          onClick = onToggleHandRaise,
          modifier = Modifier
            .size(42.dp)
            .background(if (myParticipant.isHandRaised) Color(0xFFF59E0B) else Color(0xFF374151), CircleShape)
            .testTag("btn_conf_raise_hand")
        ) {
          Icon(Icons.Default.PanTool, contentDescription = "Raise Hand", tint = if (myParticipant.isHandRaised) TnpaJetBlack else TnpaPureWhite, modifier = Modifier.size(20.dp))
        }

        // Chat & Resolutions Drawer
        IconButton(
          onClick = onOpenChat,
          modifier = Modifier
            .size(42.dp)
            .background(Color(0xFF374151), CircleShape)
            .testTag("btn_conf_chat")
        ) {
          Icon(Icons.Default.QuestionAnswer, contentDescription = "Chat", tint = TnpaGold, modifier = Modifier.size(20.dp))
        }

        // Attendance Roster
        IconButton(
          onClick = onOpenAttendance,
          modifier = Modifier
            .size(42.dp)
            .background(Color(0xFF374151), CircleShape)
            .testTag("btn_conf_attendance")
        ) {
          Icon(Icons.Default.Groups, contentDescription = "Participants", tint = Color(0xFF60A5FA), modifier = Modifier.size(20.dp))
        }

        // Host Mute All (Visible to State/Host Leaders)
        if (myParticipant.isHost || myParticipant.level == AdminHierarchyLevel.STATE) {
          IconButton(
            onClick = onHostMuteAll,
            modifier = Modifier
              .size(42.dp)
              .background(Color(0xFF7C2D12), CircleShape)
              .testTag("btn_conf_mute_all")
          ) {
            Icon(Icons.Default.VolumeOff, contentDescription = "Mute All", tint = TnpaGold, modifier = Modifier.size(20.dp))
          }
        }

        // Leave Meeting (Red End Call)
        IconButton(
          onClick = onLeaveClicked,
          modifier = Modifier
            .size(42.dp)
            .background(TnpaRedPrimary, CircleShape)
            .testTag("btn_conf_leave_meeting")
        ) {
          Icon(Icons.Default.CallEnd, contentDescription = "Leave Conference", tint = TnpaPureWhite, modifier = Modifier.size(20.dp))
        }
      }
    }
  }
}

// ============================================================================
// 6. CHAT & RESOLUTIONS BOTTOM SHEET
// ============================================================================

@Composable
private fun ConferenceChatAndResolutionsSheet(
  chatMessages: List<ConferenceChatMessage>,
  resolutions: List<MeetingResolution>,
  myParticipant: ConferenceParticipant?,
  chatInput: String,
  onChatInputChange: (String) -> Unit,
  onSendMessage: () -> Unit,
  onProposeResolution: () -> Unit,
  onDismiss: () -> Unit
) {
  var activeTab by remember { mutableIntStateOf(0) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.QuestionAnswer, contentDescription = null, tint = TnpaGold)
          Spacer(modifier = Modifier.width(6.dp))
          Text(if (activeTab == 0) "நேரலை அரட்டை (Chat)" else "தீர்மானங்கள் (Resolutions)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
        IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
          Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
        }
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .height(380.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        TabRow(selectedTabIndex = activeTab) {
          Tab(
            selected = activeTab == 0,
            onClick = { activeTab = 0 },
            text = { Text("அரட்டை (${chatMessages.size})", fontSize = 11.sp) }
          )
          Tab(
            selected = activeTab == 1,
            onClick = { activeTab = 1 },
            text = { Text("தீர்மானங்கள் (${resolutions.size})", fontSize = 11.sp) }
          )
        }

        if (activeTab == 0) {
          // Live Chat Feed
          LazyColumn(
            modifier = Modifier
              .weight(1f)
              .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            items(chatMessages) { msg ->
              Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                  containerColor = if (msg.isResolution) Color(0xFFFEF3C7) else Color(0xFFF1F5F9)
                )
              ) {
                Column(modifier = Modifier.padding(8.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Text(msg.senderName, fontWeight = FontWeight.Black, fontSize = 11.sp, color = TnpaRedDark)
                    Text(msg.time, fontSize = 9.sp, color = Color.Gray)
                  }
                  Text(msg.message, fontSize = 12.sp, color = TnpaJetBlack)
                }
              }
            }
          }

          // Input Bar
          Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
              value = chatInput,
              onValueChange = onChatInputChange,
              placeholder = { Text("செய்தி அனுப்பவும்...") },
              modifier = Modifier.weight(1f),
              maxLines = 2
            )
            Spacer(modifier = Modifier.width(6.dp))
            IconButton(
              onClick = onSendMessage,
              modifier = Modifier.background(TnpaGold, CircleShape)
            ) {
              Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = TnpaJetBlack, modifier = Modifier.size(18.dp))
            }
          }
        } else {
          // Resolutions List
          LazyColumn(
            modifier = Modifier
              .weight(1f)
              .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(resolutions) { res ->
              Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
              ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                  Text("தீர்மானம் #${res.resolutionNo}", fontWeight = FontWeight.Black, color = TnpaRedPrimary, fontSize = 12.sp)
                  Text(res.titleTamil, fontSize = 12.sp, color = TnpaJetBlack)
                  Text("முன்மொழிவு: ${res.proposedBy}", fontSize = 10.sp, color = Color.Gray)
                }
              }
            }
          }

          Button(
            onClick = {
              onDismiss()
              onProposeResolution()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = TnpaGold)
          ) {
            Text("+ புதிய தீர்மானம் முன்மொழி", color = TnpaJetBlack, fontWeight = FontWeight.Bold, fontSize = 12.sp)
          }
        }
      }
    },
    confirmButton = {}
  )
}

// ============================================================================
// 7. ATTENDANCE ROSTER SHEET
// ============================================================================

@Composable
private fun ConferenceAttendanceRosterSheet(
  participants: List<ConferenceParticipant>,
  currentRoom: ConferenceRoom?,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("👥 நிர்வாகிகளின் வருகைப் பதிவு (${participants.size})", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
          Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
        }
      }
    },
    text = {
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .height(350.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        items(participants) { participant ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(Color(participant.avatarColorHex)),
                contentAlignment = Alignment.Center
              ) {
                Text(participant.tamilName.take(1), color = TnpaPureWhite, fontWeight = FontWeight.Bold, fontSize = 13.sp)
              }
              Spacer(modifier = Modifier.width(8.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(participant.tamilName, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TnpaJetBlack)
                Text("${participant.designation} • ${participant.district}", fontSize = 10.sp, color = Color.DarkGray)
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(if (participant.level == AdminHierarchyLevel.STATE) TnpaRedPrimary else Color(0xFF0284C7))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(participant.level.labelTamil, color = TnpaPureWhite, fontSize = 8.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    },
    confirmButton = {
      Button(onClick = onDismiss) {
        Text("சரி")
      }
    }
  )
}

// ============================================================================
// 8. SECURITY AUDIT LOG SHEET
// ============================================================================

@Composable
private fun ConferenceSecurityAuditSheet(
  securityLogs: List<String>,
  authToken: String,
  isFirebaseConnected: Boolean,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    icon = { Icon(Icons.Default.Shield, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(32.dp)) },
    title = { Text("🛡️ பாதுகாப்பு மற்றும் அங்கீகார அறிக்கை", fontWeight = FontWeight.Bold, fontSize = 15.sp) },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .height(350.dp)
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Token card
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(8.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
        ) {
          Column(modifier = Modifier.padding(8.dp)) {
            Text("Session Verification Hash:", color = TnpaGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(authToken.ifBlank { "AUTH-SESSION-TOKEN-SECURED" }, color = TnpaPureWhite, fontSize = 11.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Firebase RTDB: ${if (isFirebaseConnected) "Connected (Realtime)" else "Local Sync Mode"}", color = Color(0xFF34D399), fontSize = 10.sp)
          }
        }

        Text("அங்கீகார தணிக்கை பதிவுகள் (Audit Logs):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)

        securityLogs.forEach { log ->
          Text(text = log, fontSize = 10.sp, color = TnpaJetBlack, lineHeight = 14.sp)
        }
      }
    },
    confirmButton = {
      Button(onClick = onDismiss) {
        Text("முடிந்தது")
      }
    }
  )
}
