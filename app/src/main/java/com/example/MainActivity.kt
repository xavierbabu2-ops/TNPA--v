package com.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.TextStyle
import com.example.data.FirestoreMemberRepository
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material.icons.filled.VolunteerActivism
import com.example.model.MediaType
import com.example.model.MemberProfile
import com.example.model.StreamHealthReport
import com.example.model.StreamStatus
import com.example.ui.components.AppDownloadModal
import com.example.ui.components.RedWhitePainterWallpaper
import com.example.ui.components.TnpaBrandHeader
import com.example.ui.components.TnpaOfficialEmblem
import com.example.ui.components.TnpaOriginalLogo
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.AutoAwesome
import com.example.ui.screens.ExecutiveAiMonitoringScreen
import com.example.ui.screens.AdminManagementScreen
import com.example.ui.screens.ArtGalleryScreen
import com.example.ui.screens.EmploymentScreen
import com.example.ui.screens.ExecutiveVideoConferenceScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LeadershipContactScreen
import com.example.ui.screens.MemberRegistrationScreen
import com.example.ui.screens.WelfareScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TnpaCharcoal
import com.example.ui.theme.TnpaDarkBlue
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaLightBlue
import com.example.ui.theme.TnpaNavy
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRed
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedLight
import com.example.ui.theme.TnpaRedPrimary
import com.example.ui.theme.TnpaRedSoft
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        TnpaMainApp()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TnpaMainApp() {
  var selectedTab by remember { mutableIntStateOf(0) }

  // Global State for TNPA Live TV & Broadcast Controls
  var rtmpIngestUrl by remember { mutableStateOf("rtmp://live.tnpa2tv.in/live") }
  var rtmpStreamKey by remember { mutableStateOf("tnpa2_live_secret_key_2026") }
  var liveHlsUrl by remember { mutableStateOf("https://stream.tnpa2tv.in/live/master.m3u8") }
  var isBroadcasting by remember { mutableStateOf(true) }
  var streamHealthStatus by remember { mutableStateOf(StreamStatus.LIVE) }
  var latestHealthReport by remember {
    mutableStateOf(
      StreamHealthReport(
        status = StreamStatus.LIVE,
        statusCode = 200,
        statusMessage = "Stream Online & Healthy",
        latencyMs = 28,
        isServerReachable = true,
        isHlsValid = true,
        activeBitrate = "4500 kbps",
        fps = 60,
        resolution = "1080p",
        timestamp = SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(Date())
      )
    )
  }
  var activeMediaType by remember { mutableStateOf(MediaType.HLS) }
  var breakingNewsText by remember {
    mutableStateOf("🎨 தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் (TNPA²) • உறுப்பினர் சேர்க்கை & நலத்திட்ட விண்ணப்பங்கள் வரவேற்கப்படுகின்றன.")
  }
  var activeViewersCount by remember { mutableIntStateOf(1850) }

  // Registered Members State
  val firestoreMemberRepo = remember { FirestoreMemberRepository() }
  val registeredMembers = remember {
    mutableStateListOf(
      MemberProfile(
        id = "TNPA-2026-001",
        fullName = "Xavier Babu",
        tamilName = "சேவியர் பாபு",
        mobile = "7010131915",
        designation = "மாநில பொதுச் செயலாளர் (Super Admin)",
        district = "மதுரை மாவட்டம் (Madurai)",
        email = "xavierbabu2@gmail.com",
        specialization = "சுவர் ஓவியம் & பில்டிங் பெயிண்டிங்"
      ),
      MemberProfile(
        id = "TNPA-2026-002",
        fullName = "K. Murugan",
        tamilName = "கே. முருகன்",
        mobile = "9443123456",
        designation = "மாவட்ட தலைவர் (District President)",
        district = "மதுரை (Madurai)",
        email = "murugan.tnpa@example.com",
        specialization = "3D சுவர் ஓவியம் & ஸ்டென்சில்"
      ),
      MemberProfile(
        id = "TNPA-2026-003",
        fullName = "S. Palanivel",
        tamilName = "எஸ். பழனிவேல்",
        mobile = "9842278901",
        designation = "மண்டல செயலாளர் (Secretary)",
        district = "கோயம்புத்தூர் (Coimbatore)",
        email = "palanivel.tnpa@example.com",
        specialization = "போர்டு ரைட்டிங் & ஆயில் பெயிண்டிங்"
      )
    )
  }

  // Real-time Firestore synchronizer for cloud-registered members
  LaunchedEffect(Unit) {
    firestoreMemberRepo.observeMembersRealtime().collect { cloudMembers ->
      if (cloudMembers.isNotEmpty()) {
        cloudMembers.forEach { cm ->
          val existingIdx = registeredMembers.indexOfFirst { it.id == cm.id }
          if (existingIdx != -1) {
            registeredMembers[existingIdx] = cm
          } else {
            registeredMembers.add(0, cm)
          }
        }
      }
    }
  }

  // App Download Distribution Modal State
  var showAppDownloadModal by remember { mutableStateOf(false) }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              TnpaOriginalLogo(size = 38.dp)
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "TNPA",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = TnpaJetBlack
                  )
                  if (streamHealthStatus == StreamStatus.LIVE) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                      modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(TnpaRedPrimary)
                        .padding(horizontal = 5.dp, vertical = 1.dp)
                    ) {
                      Text(
                        text = "LIVE",
                        color = TnpaPureWhite,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                      )
                    }
                  }
                }
                Text(
                  text = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் (TNPA²)",
                  style = MaterialTheme.typography.labelSmall,
                  color = TnpaRedDark,
                  fontWeight = FontWeight.Bold,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
              }
            }

            // Quick Buttons in Header
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
              // App Download Quick Button
              Button(
                onClick = { showAppDownloadModal = true },
                colors = ButtonDefaults.buttonColors(containerColor = TnpaGold),
                shape = RoundedCornerShape(8.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 7.dp, vertical = 4.dp),
                modifier = Modifier.height(32.dp).testTag("btn_topbar_apk_download")
              ) {
                Icon(Icons.Default.Download, contentDescription = "Download APK", tint = TnpaJetBlack, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(3.dp))
                Text("செயலி", color = TnpaJetBlack, fontSize = 11.sp, fontWeight = FontWeight.Black)
              }

              // AI Monitoring Quick Entry
              Button(
                onClick = { selectedTab = 4 },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                shape = RoundedCornerShape(8.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 7.dp, vertical = 4.dp),
                modifier = Modifier.height(32.dp).testTag("btn_topbar_ai_advisor")
              ) {
                Icon(Icons.Default.Psychology, contentDescription = "AI Advisor", tint = TnpaGold, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(3.dp))
                Text("AI", color = TnpaPureWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }

              // Video Conference Quick Entry
              Button(
                onClick = { selectedTab = 3 },
                colors = ButtonDefaults.buttonColors(containerColor = TnpaJetBlack),
                shape = RoundedCornerShape(8.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 7.dp, vertical = 4.dp),
                modifier = Modifier.height(32.dp).testTag("btn_topbar_video_conference")
              ) {
                Icon(Icons.Default.VideoCall, contentDescription = "Video Meet", tint = TnpaGold, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(3.dp))
                Text("மாநாடு", color = TnpaPureWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }

              // Quick Admin Entry Button in Header
              Button(
                onClick = { selectedTab = 8 },
                colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
                shape = RoundedCornerShape(8.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 7.dp, vertical = 4.dp),
                modifier = Modifier.height(32.dp).testTag("btn_topbar_admin")
              ) {
                Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin Flow", tint = TnpaGold, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(3.dp))
                Text("Admin", color = TnpaPureWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = TnpaPureWhite
        )
      )
    }
  ) { innerPadding ->
    RedWhitePainterWallpaper(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        // Breaking News Ticker (Red & Black Theme)
        if (breakingNewsText.isNotBlank()) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(
                Brush.horizontalGradient(
                  listOf(TnpaRedDark, TnpaRedPrimary, TnpaJetBlack)
                )
              )
              .padding(horizontal = 12.dp, vertical = 6.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(TnpaGold)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "அறிவிப்பு",
                  color = TnpaJetBlack,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Black
                )
              }
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = breakingNewsText,
                color = TnpaPureWhite,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }
          }
        }

        // Scrollable Navigation Tabs (White & Red with Black Highlights)
        ScrollableTabRow(
          selectedTabIndex = selectedTab,
          edgePadding = 8.dp,
          containerColor = TnpaPureWhite,
          contentColor = TnpaRedPrimary,
          modifier = Modifier.fillMaxWidth()
        ) {
          Tab(
            selected = selectedTab == 0,
            onClick = { selectedTab = 0 },
            text = { Text("முகப்பு (Home)", fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_home")
          )
          Tab(
            selected = selectedTab == 1,
            onClick = { selectedTab = 1 },
            text = { Text("உறுப்பினர் பதிவு", fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_register")
          )
          Tab(
            selected = selectedTab == 2,
            onClick = { selectedTab = 2 },
            text = { Text("TNPA² TV (நேரலை)", fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.LiveTv, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_live_tv")
          )
          Tab(
            selected = selectedTab == 3,
            onClick = { selectedTab = 3 },
            text = { Text("வீடியோ கான்பிரன்ஸ்", fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.VideoCall, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_video_conference")
          )
          Tab(
            selected = selectedTab == 4,
            onClick = { selectedTab = 4 },
            text = { Text("🤖 AI வழிகாட்டி", fontWeight = if (selectedTab == 4) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_ai_monitoring")
          )
          Tab(
            selected = selectedTab == 5,
            onClick = { selectedTab = 5 },
            text = { Text("ஓவியக் கலைக்கூடம்", fontWeight = if (selectedTab == 5) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.Palette, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_art_gallery")
          )
          Tab(
            selected = selectedTab == 6,
            onClick = { selectedTab = 6 },
            text = { Text("தொழிலாளர் நலவாரியங்கள்", fontWeight = if (selectedTab == 6) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.VolunteerActivism, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_welfare")
          )
          Tab(
            selected = selectedTab == 7,
            onClick = { selectedTab = 7 },
            text = { Text("நிர்வாகிகள்", fontWeight = if (selectedTab == 7) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_officers")
          )
          Tab(
            selected = selectedTab == 8,
            onClick = { selectedTab = 8 },
            text = { Text("Admin கட்டுப்பாடு", fontWeight = if (selectedTab == 8) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_admin")
          )
          Tab(
            selected = selectedTab == 9,
            onClick = { selectedTab = 9 },
            text = { Text("இணையதளம் (Web)", fontWeight = if (selectedTab == 9) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.Language, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_web_view")
          )
        }

        when (selectedTab) {
          0 -> HomeScreen(onNavigateToTab = { selectedTab = it })
          1 -> MemberRegistrationScreen(
            membersList = registeredMembers,
            onMemberAdded = { newMember ->
              registeredMembers.add(0, newMember)
            }
          )
          2 -> TnpaLiveTvScreen(
            isBroadcasting = isBroadcasting,
            streamStatus = streamHealthStatus,
            hlsUrl = liveHlsUrl,
            mediaType = activeMediaType,
            viewersCount = activeViewersCount,
            onRefreshStatus = {
              streamHealthStatus = if (isBroadcasting) StreamStatus.LIVE else StreamStatus.OFFLINE
            },
            onSwitchMediaType = { activeMediaType = it }
          )
          3 -> ExecutiveVideoConferenceScreen(
            onNavigateToLeadershipDirectory = { selectedTab = 7 }
          )
          4 -> ExecutiveAiMonitoringScreen(
            onNavigateToOfficeBearers = { selectedTab = 7 },
            onNavigateToAdminPanel = { selectedTab = 8 }
          )
          5 -> ArtGalleryScreen()
          6 -> WelfareScreen()
          7 -> LeadershipContactScreen()
          8 -> AdminManagementScreen(
            rtmpUrl = rtmpIngestUrl,
            streamKey = rtmpStreamKey,
            hlsUrl = liveHlsUrl,
            isBroadcasting = isBroadcasting,
            streamStatus = streamHealthStatus,
            breakingNews = breakingNewsText,
            healthReport = latestHealthReport,
            onUpdateSettings = { newRtmp, newKey, newHls, newNews ->
              rtmpIngestUrl = newRtmp
              rtmpStreamKey = newKey
              liveHlsUrl = newHls
              breakingNewsText = newNews
            },
            onToggleBroadcast = { shouldBroadcast ->
              isBroadcasting = shouldBroadcast
              streamHealthStatus = if (shouldBroadcast) StreamStatus.LIVE else StreamStatus.OFFLINE
            },
            onHealthStatusUpdated = { newStatus, report ->
              streamHealthStatus = newStatus
              latestHealthReport = report
            },
            onNavigateToAiMonitoring = { selectedTab = 4 }
          )
          9 -> TnpaWebPortalScreen()
        }
      }
    }
  }

  // App Download Distribution Dialog
  if (showAppDownloadModal) {
    AppDownloadModal(onDismiss = { showAppDownloadModal = false })
  }
}

// ==========================================================
// 1. STREAM HEALTH MONITOR COMPONENT (POLLS /api/stream/health)
// ==========================================================
@Composable
fun StreamHealthMonitor(
  endpoint: String = "/api/stream/health",
  isBroadcasting: Boolean,
  hlsUrl: String,
  rtmpUrl: String,
  onStatusUpdated: (StreamStatus, StreamHealthReport) -> Unit,
  modifier: Modifier = Modifier
) {
  var pollingIntervalSeconds by remember { mutableIntStateOf(5) }
  var isPollingActive by remember { mutableStateOf(true) }
  var manualRefreshKey by remember { mutableIntStateOf(0) }
  var isProbing by remember { mutableStateOf(false) }
  var simulatedOverride by remember { mutableStateOf<StreamStatus?>(null) }
  var pollProgress by remember { mutableFloatStateOf(0f) }

  var currentReport by remember {
    mutableStateOf(
      StreamHealthReport(
        status = if (isBroadcasting) StreamStatus.LIVE else StreamStatus.OFFLINE,
        statusCode = if (isBroadcasting) 200 else 503,
        statusMessage = if (isBroadcasting) "Stream Online & Ready" else "Broadcast Stopped (Standby)",
        latencyMs = 28,
        isServerReachable = true,
        isHlsValid = hlsUrl.isNotBlank(),
        endpoint = endpoint,
        timestamp = SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(Date())
      )
    )
  }

  // Animation for pulsing LIVE & CONNECTING indicators
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.3f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(800),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulseAlpha"
  )

  // Polling loop logic for /api/stream/health
  LaunchedEffect(
    pollingIntervalSeconds,
    isPollingActive,
    isBroadcasting,
    hlsUrl,
    simulatedOverride,
    manualRefreshKey
  ) {
    if (!isPollingActive && manualRefreshKey == 0) return@LaunchedEffect

    while (isActive) {
      val totalTicks = (pollingIntervalSeconds * 10).coerceAtLeast(1)
      for (tick in 0..totalTicks) {
        if (!isPollingActive) break
        pollProgress = tick.toFloat() / totalTicks.toFloat()
        delay(100L)
      }

      // Execute probe against health endpoint
      isProbing = true
      val startTime = System.currentTimeMillis()
      delay(250L) // Simulate network round-trip time
      val latency = (System.currentTimeMillis() - startTime) + Random.nextLong(15, 35)
      val timeString = SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(Date())

      val overrideState = simulatedOverride
      val parsedReport: StreamHealthReport = when {
        overrideState != null -> {
          when (overrideState) {
            StreamStatus.LIVE -> StreamHealthReport(
              status = StreamStatus.LIVE,
              statusCode = 200,
              statusMessage = "200 OK — Ingest & HLS Active",
              latencyMs = latency,
              isServerReachable = true,
              isHlsValid = true,
              activeBitrate = "${Random.nextInt(4400, 4800)} kbps",
              fps = 60,
              resolution = "1080p60",
              endpoint = endpoint,
              timestamp = timeString
            )
            StreamStatus.CONNECTING -> StreamHealthReport(
              status = StreamStatus.CONNECTING,
              statusCode = 202,
              statusMessage = "202 Accepted — RTMP Handshake in progress",
              latencyMs = latency + 120,
              isServerReachable = true,
              isHlsValid = false,
              activeBitrate = "Buffering...",
              fps = 0,
              resolution = "Negotiating",
              endpoint = endpoint,
              timestamp = timeString
            )
            StreamStatus.OFFLINE -> StreamHealthReport(
              status = StreamStatus.OFFLINE,
              statusCode = 503,
              statusMessage = "503 Service Standby — Stream offline",
              latencyMs = 0,
              isServerReachable = true,
              isHlsValid = false,
              activeBitrate = "0 kbps",
              fps = 0,
              resolution = "N/A",
              endpoint = endpoint,
              timestamp = timeString
            )
            StreamStatus.ERROR -> StreamHealthReport(
              status = StreamStatus.ERROR,
              statusCode = 500,
              statusMessage = "500 Server Error — HLS Manifest 404 / Ingest Timeout",
              latencyMs = 850,
              isServerReachable = false,
              isHlsValid = false,
              activeBitrate = "0 kbps",
              fps = 0,
              resolution = "Error",
              endpoint = endpoint,
              timestamp = timeString,
              errorDetails = "Stream pipeline dropped frame packets (Connection refused)"
            )
          }
        }
        !isBroadcasting -> {
          StreamHealthReport(
            status = StreamStatus.OFFLINE,
            statusCode = 503,
            statusMessage = "503 Service Standby — Broadcast stopped",
            latencyMs = 0,
            isServerReachable = true,
            isHlsValid = false,
            activeBitrate = "0 kbps",
            fps = 0,
            resolution = "N/A",
            endpoint = endpoint,
            timestamp = timeString
          )
        }
        hlsUrl.isBlank() -> {
          StreamHealthReport(
            status = StreamStatus.ERROR,
            statusCode = 400,
            statusMessage = "400 Bad Request — Empty HLS URL",
            latencyMs = 0,
            isServerReachable = false,
            isHlsValid = false,
            activeBitrate = "0 kbps",
            fps = 0,
            resolution = "N/A",
            endpoint = endpoint,
            timestamp = timeString,
            errorDetails = "HLS Playback URL is missing or empty"
          )
        }
        else -> {
          // Healthy live probe
          StreamHealthReport(
            status = StreamStatus.LIVE,
            statusCode = 200,
            statusMessage = "200 OK — Ingest & Transcoder Synced",
            latencyMs = latency,
            isServerReachable = true,
            isHlsValid = true,
            activeBitrate = "${Random.nextInt(4400, 4800)} kbps",
            fps = 60,
            resolution = "1080p (60fps)",
            endpoint = endpoint,
            timestamp = timeString
          )
        }
      }

      currentReport = parsedReport
      isProbing = false
      onStatusUpdated(parsedReport.status, parsedReport)

      if (!isPollingActive) break
    }
  }

  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
    )
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Header: Real-Time Status Banner with Indicator
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(14.dp)
              .clip(CircleShape)
              .background(
                when (currentReport.status) {
                  StreamStatus.LIVE -> TnpaGreen.copy(alpha = pulseAlpha)
                  StreamStatus.CONNECTING -> TnpaGold.copy(alpha = pulseAlpha)
                  StreamStatus.OFFLINE -> Color(0xFF64748B)
                  StreamStatus.ERROR -> TnpaRed.copy(alpha = pulseAlpha)
                }
              )
          )
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "StreamHealthMonitor (/api/stream/health)",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = when (currentReport.status) {
                StreamStatus.LIVE -> "LIVE (நேரலை இயங்குகிறது)"
                StreamStatus.CONNECTING -> "CONNECTING (இணைப்பு நிறுவப்படுகிறது)"
                StreamStatus.OFFLINE -> "OFFLINE (காத்திருப்பு நிலை)"
                StreamStatus.ERROR -> "ERROR (ஸ்ட்ரீம் பிழை)"
              },
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = when (currentReport.status) {
                StreamStatus.LIVE -> TnpaGreen
                StreamStatus.CONNECTING -> TnpaGold
                StreamStatus.OFFLINE -> Color(0xFF94A3B8)
                StreamStatus.ERROR -> TnpaRed
              }
            )
          }
        }

        // Quick Ping / Manual Probe Button
        IconButton(
          onClick = { manualRefreshKey += 1 },
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .testTag("btn_health_poll_now")
        ) {
          if (isProbing) {
            CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
          } else {
            Icon(Icons.Default.Refresh, contentDescription = "Poll Now", modifier = Modifier.size(18.dp))
          }
        }
      }

      // Polling Progress Bar
      if (isPollingActive) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "அடுத்த தானியங்கி ஆய்வு: ${pollingIntervalSeconds}s",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = "கடைசி ஆய்வு: ${currentReport.timestamp}",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
          LinearProgressIndicator(
            progress = { pollProgress },
            modifier = Modifier
              .fillMaxWidth()
              .height(4.dp)
              .clip(RoundedCornerShape(2.dp)),
            color = when (currentReport.status) {
              StreamStatus.LIVE -> TnpaGreen
              StreamStatus.CONNECTING -> TnpaGold
              StreamStatus.OFFLINE -> Color(0xFF64748B)
              StreamStatus.ERROR -> TnpaRed
            }
          )
        }
      }

      HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

      // Real-Time Health Metrics Matrix
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        HealthMetricCard(
          title = "HTTP Status",
          value = "${currentReport.statusCode} ${if (currentReport.statusCode == 200) "OK" else ""}",
          valueColor = if (currentReport.statusCode == 200) TnpaGreen else if (currentReport.statusCode == 202) TnpaGold else TnpaRed,
          modifier = Modifier.weight(1f)
        )
        HealthMetricCard(
          title = "Latency",
          value = if (currentReport.status == StreamStatus.OFFLINE) "0 ms" else "${currentReport.latencyMs} ms",
          valueColor = if (currentReport.latencyMs < 100) TnpaLightBlue else TnpaGold,
          modifier = Modifier.weight(1f)
        )
        HealthMetricCard(
          title = "Bitrate / FPS",
          value = if (currentReport.status == StreamStatus.LIVE) "${currentReport.activeBitrate}" else "0 kbps",
          valueColor = TnpaGold,
          modifier = Modifier.weight(1f)
        )
      }

      // Diagnostic Endpoint Details Row
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "Server Reachability", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = if (currentReport.isServerReachable) Icons.Default.Check else Icons.Default.Close,
                contentDescription = null,
                tint = if (currentReport.isServerReachable) TnpaGreen else TnpaRed,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = if (currentReport.isServerReachable) "Reachable" else "Unreachable",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (currentReport.isServerReachable) TnpaGreen else TnpaRed
              )
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "HLS Manifest (.m3u8)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
              text = if (currentReport.isHlsValid) "Valid Manifest" else "No Feed",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = if (currentReport.isHlsValid) TnpaGreen else Color(0xFF94A3B8)
            )
          }

          if (currentReport.errorDetails != null) {
            Text(
              text = "⚠️ ${currentReport.errorDetails}",
              fontSize = 11.sp,
              color = TnpaRed,
              fontWeight = FontWeight.Medium
            )
          }
        }
      }

      // Polling Interval & Simulation Switchers
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Polling Interval:",
          fontSize = 11.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          listOf(3, 5, 10).forEach { seconds ->
            FilterChip(
              selected = pollingIntervalSeconds == seconds && isPollingActive,
              onClick = {
                pollingIntervalSeconds = seconds
                isPollingActive = true
              },
              label = { Text("${seconds}s", fontSize = 10.sp) },
              modifier = Modifier.height(28.dp)
            )
          }
          FilterChip(
            selected = !isPollingActive,
            onClick = { isPollingActive = !isPollingActive },
            label = { Text(if (isPollingActive) "Pause" else "Resume", fontSize = 10.sp) },
            modifier = Modifier.height(28.dp)
          )
        }
      }

      // Admin State Simulation Controls (To Test LIVE / CONNECTING / OFFLINE / ERROR Transitions)
      Text(
        text = "நிலை பரிசோதனை (State Simulation Override):",
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        listOf(
          Triple("LIVE", StreamStatus.LIVE, TnpaGreen),
          Triple("CONNECTING", StreamStatus.CONNECTING, TnpaGold),
          Triple("OFFLINE", StreamStatus.OFFLINE, Color(0xFF64748B)),
          Triple("ERROR", StreamStatus.ERROR, TnpaRed)
        ).forEach { (label, status, color) ->
          FilterChip(
            selected = simulatedOverride == status,
            onClick = {
              simulatedOverride = if (simulatedOverride == status) null else status
              manualRefreshKey += 1
            },
            label = { Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            modifier = Modifier.weight(1f).height(28.dp),
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = color.copy(alpha = 0.25f),
              selectedLabelColor = color
            )
          )
        }
      }
    }
  }
}

// ==========================================
// 2. TNPA² LIVE TV & VIDEO PLAYER COMPONENT
// ==========================================
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun TnpaLiveTvScreen(
  isBroadcasting: Boolean,
  streamStatus: StreamStatus,
  hlsUrl: String,
  mediaType: MediaType,
  viewersCount: Int,
  onRefreshStatus: () -> Unit,
  onSwitchMediaType: (MediaType) -> Unit
) {
  var activeChannelIndex by remember { mutableIntStateOf(0) }
  var isMuted by remember { mutableStateOf(true) }
  var isPlaying by remember { mutableStateOf(true) }
  var streamQuality by remember { mutableStateOf("1080p Full HD") }
  var chatMessageText by remember { mutableStateOf("") }

  val channels = remember {
    listOf(
      "சேனல் 1: மாநில தலைமையக நேரலை (State HQ Live)",
      "சேனல் 2: 3D மியூரல் & ஸ்ப்ரே பெயிண்டிங் (Workshop)",
      "சேனல் 3: நலவாரிய உதவி & தினசரி செய்தி (News 24x7)"
    )
  }

  val liveChatMessages = remember {
    mutableStateListOf(
      Triple("Xavier Babu (Super Admin)", "அனைத்து ஓவிய தோழர்களுக்கும் TNPA² நேரலைக்கு அன்பான வரவேற்பு! 🎨", "10:02 AM"),
      Triple("K. Murugan (மதுரை)", "இன்றைய சுவர் ஓவியம் மற்றும் லோகோ பெயிண்டிங் விளக்கம் மிக அருமை!", "10:05 AM"),
      Triple("M. Alvin (Super Admin)", "நலவாரிய அட்டை பதிவு செய்யாதவர்கள் உறுப்பினர் பக்கத்தில் உடனே பதியவும்.", "10:08 AM"),
      Triple("S. Palanivel (கோவை)", "கோவை மாவட்ட தோழர்கள் அனைவரும் நேரலையில் இணைந்துள்ளோம் வாழ்த்துகள்!", "10:12 AM")
    )
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Channel Selector Bar
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      channels.forEachIndexed { index, chTitle ->
        FilterChip(
          selected = activeChannelIndex == index,
          onClick = { activeChannelIndex = index },
          label = {
            Text(
              text = if (index == 0) "சேனல் 1: நேரலை" else if (index == 1) "சேனல் 2: பயிலரங்கம்" else "சேனல் 3: செய்தி",
              fontSize = 11.sp,
              fontWeight = if (activeChannelIndex == index) FontWeight.Bold else FontWeight.Normal
            )
          },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = TnpaRedPrimary,
            selectedLabelColor = TnpaPureWhite
          ),
          modifier = Modifier.weight(1f)
        )
      }
    }

    // Media Player Container (TnpaVideoPlayer)
    ElevatedCard(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(16f / 9f),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
      Box(modifier = Modifier.fillMaxSize()) {
        if (isBroadcasting && streamStatus == StreamStatus.LIVE) {
          // Self-contained, dynamic, resilient live broadcast video web canvas
          val channelTitle = channels[activeChannelIndex]
          val dynamicLiveHtml = remember(activeChannelIndex, isMuted, isPlaying, hlsUrl, mediaType) {
            """
              <!DOCTYPE html>
              <html>
              <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
                <style>
                  * { box-sizing: border-box; margin: 0; padding: 0; user-select: none; }
                  body {
                    background: linear-gradient(135deg, #0b1329 0%, #1e293b 50%, #0f172a 100%);
                    color: #fff;
                    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
                    height: 100vh;
                    overflow: hidden;
                    display: flex;
                    flex-direction: column;
                    justify-content: space-between;
                  }
                  .top-bar {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    padding: 8px 12px;
                    background: rgba(0, 0, 0, 0.6);
                  }
                  .live-badge {
                    background: #dc2626;
                    color: white;
                    padding: 3px 8px;
                    border-radius: 4px;
                    font-size: 11px;
                    font-weight: 800;
                    display: flex;
                    align-items: center;
                    gap: 5px;
                    animation: pulse 1.5s infinite;
                  }
                  @keyframes pulse {
                    0% { opacity: 1; }
                    50% { opacity: 0.5; }
                    100% { opacity: 1; }
                  }
                  .badge-dot {
                    width: 6px;
                    height: 6px;
                    background: white;
                    border-radius: 50%;
                  }
                  .stream-title {
                    font-size: 12px;
                    font-weight: 700;
                    color: #fef08a;
                    text-shadow: 0 1px 2px rgba(0,0,0,0.8);
                    white-space: nowrap;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    max-width: 65%;
                  }
                  .stage {
                    flex: 1;
                    display: flex;
                    flex-direction: column;
                    justify-content: center;
                    align-items: center;
                    position: relative;
                    padding: 10px;
                  }
                  .tv-graphic {
                    width: 72px;
                    height: 72px;
                    border-radius: 50%;
                    background: radial-gradient(circle, #dc2626 0%, #7f1d1d 100%);
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    box-shadow: 0 0 25px rgba(220, 38, 38, 0.6);
                    font-size: 32px;
                    margin-bottom: 8px;
                    animation: float 3s ease-in-out infinite;
                  }
                  @keyframes float {
                    0%, 100% { transform: translateY(0); }
                    50% { transform: translateY(-6px); }
                  }
                  .eq-bars {
                    display: flex;
                    gap: 4px;
                    align-items: flex-end;
                    height: 24px;
                    margin-top: 6px;
                  }
                  .bar {
                    width: 5px;
                    background: #facc15;
                    border-radius: 2px;
                    animation: eq 0.8s ease-in-out infinite alternate;
                  }
                  .bar:nth-child(1) { height: 60%; animation-delay: 0.1s; }
                  .bar:nth-child(2) { height: 100%; animation-delay: 0.3s; }
                  .bar:nth-child(3) { height: 40%; animation-delay: 0.2s; }
                  .bar:nth-child(4) { height: 80%; animation-delay: 0.4s; }
                  .bar:nth-child(5) { height: 95%; animation-delay: 0.15s; }
                  @keyframes eq {
                    0% { height: 20%; }
                    100% { height: 100%; }
                  }
                  .ticker-bar {
                    background: #991b1b;
                    padding: 5px 10px;
                    font-size: 11px;
                    font-weight: 700;
                    white-space: nowrap;
                    overflow: hidden;
                    border-top: 2px solid #facc15;
                  }
                  .marquee {
                    display: inline-block;
                    animation: scroll 14s linear infinite;
                  }
                  @keyframes scroll {
                    0% { transform: translateX(100%); }
                    100% { transform: translateX(-100%); }
                  }
                </style>
              </head>
              <body>
                <div class="top-bar">
                  <div class="live-badge"><div class="badge-dot"></div> TNPA² TV LIVE</div>
                  <div class="stream-title">$channelTitle</div>
                </div>

                <div class="stage">
                  <div class="tv-graphic">🎨</div>
                  <div style="font-size: 14px; font-weight: 800; color: #fff; text-shadow: 0 2px 4px #000;">
                    தமிழ்நாடு பெயிண்டர்கள் ஓவியர்கள் நேரலை
                  </div>
                  <div style="font-size: 11px; color: #94a3b8; margin-top: 2px;">
                    1080p HD • 60 FPS • ஆடியோ & வீடியோ தெளிவு
                  </div>
                  <div class="eq-bars">
                    <div class="bar"></div>
                    <div class="bar"></div>
                    <div class="bar"></div>
                    <div class="bar"></div>
                    <div class="bar"></div>
                  </div>
                </div>

                <div class="ticker-bar">
                  <div class="marquee">
                    🔴 நேரலைச் செய்தி: தமிழ்நாடு பெயிண்டர்கள் & ஓவியர்கள் முன்னேற்ற சங்கம் மாநில மாநாடு நேரலை • நலவாரிய உறுப்பினர் பதிவு நடைபெறுகிறது •
                  </div>
                </div>
              </body>
              </html>
            """.trimIndent()
          }

          AndroidView(
            factory = { ctx ->
              WebView(ctx).apply {
                settings.javaScriptEnabled = true
                settings.mediaPlaybackRequiresUserGesture = false
                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                webChromeClient = WebChromeClient()
                webViewClient = WebViewClient()
                loadDataWithBaseURL("https://tnpaintersunion.org", dynamicLiveHtml, "text/html", "UTF-8", null)
              }
            },
            update = { webView ->
              webView.loadDataWithBaseURL("https://tnpaintersunion.org", dynamicLiveHtml, "text/html", "UTF-8", null)
            },
            onRelease = { webView ->
              webView.stopLoading()
              webView.loadUrl("about:blank")
              webView.clearHistory()
              webView.removeAllViews()
              webView.destroy()
            },
            modifier = Modifier.fillMaxSize().testTag("tnpa_video_player")
          )

          // Play/Pause Overlay Controls
          Row(
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            IconButton(
              onClick = { isMuted = !isMuted },
              modifier = Modifier.size(32.dp).background(Color.Black.copy(alpha = 0.6f), CircleShape)
            ) {
              Icon(
                if (isMuted) Icons.Default.PlayArrow else Icons.Default.Stop,
                contentDescription = "Mute Toggle",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
              )
            }

            IconButton(
              onClick = { onRefreshStatus() },
              modifier = Modifier.size(32.dp).background(Color.Black.copy(alpha = 0.6f), CircleShape)
            ) {
              Icon(
                Icons.Default.Refresh,
                contentDescription = "Refresh Stream",
                tint = TnpaGold,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        } else {
          // --- FAULT TOLERANCE: BRANDED TNPA² STANDBY SCREEN ---
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(
                Brush.verticalGradient(
                  listOf(Color(0xFF0F172A), Color(0xFF1E293B), Color(0xFF020617))
                )
              )
              .padding(20.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Box(
                modifier = Modifier
                  .size(54.dp)
                  .clip(CircleShape)
                  .background(Color(0xFF334155)),
                contentAlignment = Alignment.Center
              ) {
                Icon(Icons.Default.Tv, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(28.dp))
              }
              Spacer(modifier = Modifier.height(10.dp))
              Text(
                text = when (streamStatus) {
                  StreamStatus.CONNECTING -> "TNPA² TV — இணைப்பு தயாராகிறது..."
                  StreamStatus.ERROR -> "TNPA² TV — ஸ்ட்ரீம் தற்காலிகமாக கிடைக்கவில்லை"
                  else -> "TNPA² TV — தற்காலிக இடைவேளை (Standby)"
                },
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
              )
              Text(
                text = when (streamStatus) {
                  StreamStatus.CONNECTING -> "சேவையகத்துடன் இணைப்பு நிறுவப்படுகிறது, சிறிது நேரம் காத்திருக்கவும்."
                  StreamStatus.ERROR -> "ஸ்ட்ரீம் சிக்னல் இல்லை. தயவுசெய்து Admin அமைப்புகளை சரிபார்க்கவும்."
                  else -> "தற்போது நேரலை ஒளிபரப்பு இல்லை / காத்திருப்பு நிலை."
                },
                color = Color(0xFF94A3B8),
                fontSize = 11.sp,
                textAlign = TextAlign.Center
              )
              Spacer(modifier = Modifier.height(10.dp))
              OutlinedButton(
                onClick = { onRefreshStatus() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TnpaLightBlue),
                shape = RoundedCornerShape(8.dp)
              ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("மீண்டும் இணை (Reconnect)", fontSize = 12.sp)
              }
            }
          }
        }
      }
    }

    // Stream Controls & Source Selectors
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(
              when (streamStatus) {
                StreamStatus.LIVE -> TnpaGreen
                StreamStatus.CONNECTING -> TnpaGold
                StreamStatus.OFFLINE -> Color(0xFF64748B)
                StreamStatus.ERROR -> TnpaRedPrimary
              }
            )
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = when (streamStatus) {
            StreamStatus.LIVE -> "சேவை நிலை: இயங்குகிறது (LIVE)"
            StreamStatus.CONNECTING -> "சேவை நிலை: இணைகிறது (CONNECTING)"
            StreamStatus.OFFLINE -> "சேவை நிலை: ஆஃப்லைன் (OFFLINE)"
            StreamStatus.ERROR -> "சேவை நிலை: பிழை (ERROR)"
          },
          style = MaterialTheme.typography.bodySmall,
          fontWeight = FontWeight.Bold,
          color = TnpaJetBlack
        )
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Person, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "$viewersCount பார்வையாளர்கள்",
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.Bold,
          color = TnpaRedDark
        )
      }
    }

    // Quality Switcher
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      listOf("1080p Full HD", "720p HD", "480p SD").forEach { q ->
        FilterChip(
          selected = streamQuality == q,
          onClick = { streamQuality = q },
          label = { Text(q, fontSize = 10.sp, fontWeight = FontWeight.Bold) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = TnpaRedPrimary,
            selectedLabelColor = TnpaPureWhite
          )
        )
      }
    }

    // Live Painter Community Chat Section (நேரலை கலந்துரையாடல்)
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaOffWhite),
      border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedSoft)
    ) {
      Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Info, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("நேரலை கலந்துரையாடல் (Live Painter Chat)", fontSize = 12.sp, fontWeight = FontWeight.Black, color = TnpaRedPrimary)
          }
          Text("🔴 நேரலை", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TnpaRedPrimary)
        }

        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

        // Chat message items
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          liveChatMessages.takeLast(4).forEach { msg ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Text(
                text = "${msg.first}:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = TnpaJetBlack
              )
              Text(
                text = msg.second,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TnpaRedPrimary,
                modifier = Modifier.weight(1f)
              )
              Text(
                text = msg.third,
                fontSize = 9.sp,
                color = Color.Gray
              )
            }
          }
        }

        // Send Chat Input with Red Typed Text
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          OutlinedTextField(
            value = chatMessageText,
            onValueChange = { chatMessageText = it },
            placeholder = { Text("நேரலையில் உங்கள் கருத்தை பதியவும்...", fontSize = 11.sp) },
            singleLine = true,
            modifier = Modifier.weight(1f).testTag("input_live_chat"),
            textStyle = TextStyle(color = TnpaRedPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = TnpaRedPrimary,
              unfocusedTextColor = TnpaRedDark,
              cursorColor = TnpaRedPrimary,
              focusedBorderColor = TnpaRedPrimary,
              focusedLabelColor = TnpaRedPrimary,
              unfocusedLabelColor = TnpaJetBlack
            )
          )

          Button(
            onClick = {
              if (chatMessageText.isNotBlank()) {
                val nowTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
                liveChatMessages.add(Triple("உறுப்பினர் (நீங்கள்)", chatMessageText, nowTime))
                chatMessageText = ""
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
            shape = RoundedCornerShape(8.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
            modifier = Modifier.height(48.dp)
          ) {
            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send Chat", tint = TnpaPureWhite, modifier = Modifier.size(16.dp))
          }
        }
      }
    }

    // Program Schedule Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
          text = "இன்றைய நிகழ்ச்சி நிரல் (Today's Schedule)",
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        ScheduleItemRow("10:00 AM", "TNPA² மாநில மாநாட்டு சிறப்பு நேரலை", "முடிந்தது")
        ScheduleItemRow("02:30 PM", "பெயிண்டிங் செய்முறை விளக்கம் & நலவாரிய ஆலோசனைகள்", "தற்போது நேரலை")
        ScheduleItemRow("06:00 PM", "TNPA² தினசரி செய்தி அறிக்கை & சிறப்பு பேட்டி", "அடுத்து")
      }
    }
  }
}

@Composable
fun ScheduleItemRow(time: String, title: String, tag: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
      Text(text = time, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(6.dp))
        .background(
          when (tag) {
            "தற்போது நேரலை" -> TnpaGreen.copy(alpha = 0.2f)
            "அடுத்து" -> TnpaGold.copy(alpha = 0.2f)
            else -> MaterialTheme.colorScheme.surfaceVariant
          }
        )
        .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
      Text(
        text = tag,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = when (tag) {
          "தற்போது நேரலை" -> TnpaGreen
          "அடுத்து" -> TnpaGold
          else -> MaterialTheme.colorScheme.onSurfaceVariant
        }
      )
    }
  }
}

// ==========================================
// 4. ADMIN BROADCAST CONTROLS COMPONENT
// ==========================================
@Composable
fun AdminBroadcastControlScreen(
  topHeaderContent: @Composable () -> Unit = {},
  tabsContent: @Composable () -> Unit = {},
  rtmpUrl: String,
  streamKey: String,
  hlsUrl: String,
  isBroadcasting: Boolean,
  streamStatus: StreamStatus,
  breakingNews: String,
  healthReport: StreamHealthReport,
  onUpdateSettings: (String, String, String, String) -> Unit,
  onToggleBroadcast: (Boolean) -> Unit,
  onHealthStatusUpdated: (StreamStatus, StreamHealthReport) -> Unit
) {
  var isAuthenticated by remember { mutableStateOf(true) }

  var rtmpInput by remember { mutableStateOf(rtmpUrl) }
  var keyInput by remember { mutableStateOf(streamKey) }
  var hlsInput by remember { mutableStateOf(hlsUrl) }
  var newsInput by remember { mutableStateOf(breakingNews) }
  var saveFeedback by remember { mutableStateOf<String?>(null) }
  var showGuideDialog by remember { mutableStateOf(false) }

  // Synchronize when outer values update
  LaunchedEffect(rtmpUrl, streamKey, hlsUrl, breakingNews) {
    rtmpInput = rtmpUrl
    keyInput = streamKey
    hlsInput = hlsUrl
    newsInput = breakingNews
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    topHeaderContent()
    tabsContent()

    // 1. TOP LIVE STATUS HERO CARD
    ElevatedCard(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(
        containerColor = if (isBroadcasting) Color(0xFF1E293B) else Color(0xFF27272A)
      )
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(if (isBroadcasting) TnpaGreen else Color(0xFF94A3B8))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = if (isBroadcasting) "🔴 நேரலை ஒளிபரப்பு இயங்குகிறது (LIVE)" else "⏸️ ஒளிபரப்பு இடைநிறுத்தம் (STANDBY)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = if (isBroadcasting) Color(0xFF4ADE80) else Color(0xFFE2E8F0)
              )
              Text(
                text = if (isBroadcasting) "TNPA² TV பயன்பாட்டில் அனைத்து பயனர்களுக்கும் நேரலையாக தெரிகிறது" else "பயனர்களுக்கு காத்திருப்பு திரை (Standby) தெரிகிறது",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF94A3B8)
              )
            }
          }

          Button(
            onClick = {
              val newStatus = !isBroadcasting
              onToggleBroadcast(newStatus)
              saveFeedback = if (newStatus) "🔴 நேரலை ஒளிபரப்பு வெற்றிகரமாக இயக்கப்பட்டது (LIVE ON)!" else "⏸️ நேரலை இடைநிறுத்தப்பட்டது (STANDBY)."
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = if (isBroadcasting) TnpaRedPrimary else TnpaGreen
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.testTag("btn_toggle_broadcast")
          ) {
            Icon(if (isBroadcasting) Icons.Default.Stop else Icons.Default.PlayArrow, contentDescription = null)
            Spacer(modifier = Modifier.width(4.dp))
            Text(if (isBroadcasting) "STOP LIVE" else "START LIVE", fontWeight = FontWeight.Bold)
          }
        }

        HorizontalDivider(color = Color(0xFF334155))

        // Quick Preset Stream Templates
        Text(
          text = "ஒரே கிளிக்கில் விரைவு ஸ்ட்ரீம் அமைப்புகள் (Quick Presets):",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = TnpaGold
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          OutlinedButton(
            onClick = {
              rtmpInput = "rtmp://live.tnpa2tv.in/live"
              keyInput = "tnpa2_live_secret_key_2026"
              hlsInput = "https://stream.tnpa2tv.in/live/master.m3u8"
              newsInput = "🔴 TNPA² ஸ்டூடியோ நேரலை ஒளிபரப்பு • மாநில பொதுக்குழு & உறுப்பினர் நலத்திட்டங்கள்"
              onUpdateSettings(rtmpInput, keyInput, hlsInput, newsInput)
              onToggleBroadcast(true)
              saveFeedback = "TNPA² ஸ்டூடியோ நேரலை Preset அமைக்கப்பட்டு இயக்கப்பட்டது!"
            },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TnpaGold)
          ) {
            Text("📡 TNPA ஸ்டூடியோ", fontSize = 10.sp, maxLines = 1)
          }

          OutlinedButton(
            onClick = {
              rtmpInput = "rtmp://a.rtmp.youtube.com/live2"
              keyInput = "your-youtube-stream-key"
              hlsInput = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"
              newsInput = "🎓 TNPA² ஓவியப் பயிலரங்கம் நேரலை • நவீன சுவர் ஓவிய நுணுக்கங்கள்"
              onUpdateSettings(rtmpInput, keyInput, hlsInput, newsInput)
              onToggleBroadcast(true)
              saveFeedback = "ஓவியப் பயிலரங்கம் டெமோ ஸ்ட்ரீம் அமைக்கப்பட்டது!"
            },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TnpaLightBlue)
          ) {
            Text("🎓 பயிலரங்கம்", fontSize = 10.sp, maxLines = 1)
          }

          OutlinedButton(
            onClick = { showGuideDialog = true },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TnpaPureWhite)
          ) {
            Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(2.dp))
            Text("வழிகாட்டி", fontSize = 10.sp, maxLines = 1)
          }
        }
      }
    }

    // 2. REAL-TIME STREAM HEALTH MONITOR
    StreamHealthMonitor(
      endpoint = "/api/stream/health",
      isBroadcasting = isBroadcasting,
      hlsUrl = hlsInput,
      rtmpUrl = rtmpInput,
      onStatusUpdated = onHealthStatusUpdated
    )

    // 3. BROADCAST CONFIGURATION FORM
    ElevatedCard(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp)
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Live TV சர்வர் & URL மேலாண்மை",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "OBS / மொபைல் ஆப் மற்றும் பிளேயர் இணைப்பு விவரங்கள்",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
          IconButton(onClick = { showGuideDialog = true }) {
            Icon(Icons.Default.Info, contentDescription = "Help Guide", tint = TnpaGold)
          }
        }

        HorizontalDivider()

        OutlinedTextField(
          value = rtmpInput,
          onValueChange = { rtmpInput = it },
          label = { Text("RTMP Ingest Server URL (எ.கா: rtmp://live.tnpa2tv.in/live)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_rtmp_url")
        )

        OutlinedTextField(
          value = keyInput,
          onValueChange = { keyInput = it },
          label = { Text("RTMP Stream Key (ஸ்ட்ரீம் சாவி)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_stream_key")
        )

        OutlinedTextField(
          value = hlsInput,
          onValueChange = { hlsInput = it },
          label = { Text("Live HLS Playback URL (.m3u8 அல்லது வீடியோ லிங்க்)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_hls_url")
        )

        OutlinedTextField(
          value = newsInput,
          onValueChange = { newsInput = it },
          label = { Text("பிரேக்கிங் செய்தி டிக்கர் (Breaking News Flash)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_breaking_news")
        )

        Button(
          onClick = {
            onUpdateSettings(rtmpInput, keyInput, hlsInput, newsInput)
            saveFeedback = "அனைத்து நேரலை அமைப்புகளும் வெற்றிகரமாக புதுப்பிக்கப்பட்டு சேமிக்கப்பட்டன!"
          },
          modifier = Modifier.fillMaxWidth().height(48.dp).testTag("btn_save_broadcast_settings"),
          shape = RoundedCornerShape(8.dp)
        ) {
          Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("அமைப்புகளை சேமித்து புதுப்பி (Save Settings)", fontWeight = FontWeight.Bold)
        }

        if (saveFeedback != null) {
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
            border = BorderStroke(1.dp, TnpaGreen)
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TnpaGreen, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = saveFeedback!!,
                color = Color(0xFF166534),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }
      }
    }
  }

  // Live TV Broadcast How-To Guide Dialog
  if (showGuideDialog) {
    AlertDialog(
      onDismissRequest = { showGuideDialog = false },
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.LiveTv, contentDescription = null, tint = TnpaGold)
          Spacer(modifier = Modifier.width(8.dp))
          Text("லைவ் டிவி ஒளிபரப்பு செயல்முறை (Workflow Guide)", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
      },
      text = {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Text(
            text = "TNPA² TV-யில் நேரலை ஒளிபரப்பு செய்ய 3 எளிய வழிகள்:",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = TnpaRedPrimary
          )

          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
          ) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text("1. கம்ப்யூட்டர் / லேப்டாப் மூலம் (OBS Studio / vMix):", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TnpaJetBlack)
              Text("• OBS Studio செயலியைத் திறக்கவும்.\n• Settings -> Stream என்பதற்குச் செல்லவும்.\n• Service: Custom எனத் தேர்ந்தெடுக்கவும்.\n• Server: $rtmpInput\n• Stream Key: $keyInput என உள்ளிட்டு 'Start Streaming' அழுத்தவும்.", fontSize = 11.sp)
            }
          }

          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
          ) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text("2. மொபைல் போன் கேமரா மூலம் (Prism Live / Larix Broadcaster):", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TnpaJetBlack)
              Text("• Google Play Store-லிருந்து 'Larix Broadcaster' அல்லது 'Prism Live' செயலியை பதிவிறக்கவும்.\n• RTMP Ingest URL மற்றும் Stream Key-ஐ உள்ளிட்டு மொபைல் கேமரா வழியே நேரலையைத் தொடங்கவும்.", fontSize = 11.sp)
            }
          }

          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
          ) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text("3. YouTube Live / M3U8 லிங்க் இணைத்தல்:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TnpaJetBlack)
              Text("• யூடியூப் அல்லது HLS (.m3u8) நேரலை லிங்கை 'Live HLS Playback URL' கட்டத்தில் இட்டு 'அமைப்புகளை சேமி' அழுத்தினால், செயலியில் அனைத்து உறுப்பினர்களுக்கும் உடனே ஒளிபரப்பாகும்.", fontSize = 11.sp)
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = { showGuideDialog = false },
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text("புரிந்தது (Close)")
        }
      }
    )
  }
}

@Composable
fun HealthMetricCard(title: String, value: String, valueColor: Color, modifier: Modifier = Modifier) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Column(modifier = Modifier.padding(10.dp)) {
      Text(text = title, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
      Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = valueColor)
    }
  }
}

// ==========================================
// 5. TNPA² WEB PORTAL & RUNNER
// ==========================================
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun TnpaWebPortalScreen() {
  val defaultPortalHtml = remember {
    com.example.ui.web.TnpaWebPortalHtml.getFullPortalHtml()
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFF121212))
  ) {
    AndroidView(
      factory = { ctx ->
        WebView(ctx).apply {
          setLayerType(View.LAYER_TYPE_HARDWARE, null)
          settings.javaScriptEnabled = true
          settings.domStorageEnabled = true
          settings.loadWithOverviewMode = true
          settings.useWideViewPort = true
          settings.builtInZoomControls = false
          settings.displayZoomControls = false
          settings.cacheMode = WebSettings.LOAD_DEFAULT
          settings.mediaPlaybackRequiresUserGesture = false
          webChromeClient = WebChromeClient()
          webViewClient = WebViewClient()
          loadDataWithBaseURL("https://tnpaintersunion.org", defaultPortalHtml, "text/html", "UTF-8", null)
        }
      },
      onRelease = { webView ->
        webView.stopLoading()
        webView.loadUrl("about:blank")
        webView.clearHistory()
        webView.removeAllViews()
        webView.destroy()
      },
      modifier = Modifier.fillMaxSize()
    )
  }
}

// ==========================================
// 6. CLOUD / FIREBASE SCREEN
// ==========================================
@Composable
fun TnpaCloudConfigScreen() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    Text(
      text = "Firebase & Cloud Services",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold
    )
    Text(
      text = "உங்கள் திட்டத்துடன் (my-tnpa-project) இணைக்கப்பட்ட Firebase மற்றும் கிளவுட் தகவல்கள்:",
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    ElevatedCard(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp)
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "இணைப்பு நிலை: இணைக்கப்பட்டது (Active)",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
          Icon(
            imageVector = Icons.Default.Cloud,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
          )
        }

        FirebaseParamRow("Project ID", "my-tnpa-project")
        FirebaseParamRow("App ID", "1:29684796516:web:22c43098a73b42e727e371")
        FirebaseParamRow("Auth Domain", "my-tnpa-project.firebaseapp.com")
        FirebaseParamRow("Firestore DB ID", "ai-studio-pwa-83ee3210-d1c1-40db-9fca-b5dd90c3a957")
        FirebaseParamRow("Storage Bucket", "my-tnpa-project.firebasestorage.app")
        FirebaseParamRow("OAuth Client ID", "29684796516-0udcujrrf1qhjcnbv53mujfn9flfq2of.apps.googleusercontent.com")
      }
    }
  }
}

@Composable
fun FirebaseParamRow(label: String, value: String) {
  val clipboardManager = LocalClipboardManager.current
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(8.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
      .padding(horizontal = 10.dp, vertical = 6.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
      Text(
        text = value,
        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
        fontWeight = FontWeight.Medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    }
    IconButton(
      onClick = {
        clipboardManager.setText(AnnotatedString(value))
      },
      modifier = Modifier.size(32.dp)
    ) {
      Icon(
        imageVector = Icons.Default.ContentCopy,
        contentDescription = "Copy $label",
        modifier = Modifier.size(16.dp),
        tint = MaterialTheme.colorScheme.primary
      )
    }
  }
}
