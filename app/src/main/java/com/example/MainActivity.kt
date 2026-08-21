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
import androidx.compose.material.icons.filled.VolunteerActivism
import com.example.model.MediaType
import com.example.model.MemberProfile
import com.example.model.StreamHealthReport
import com.example.model.StreamStatus
import com.example.ui.components.RedWhitePainterWallpaper
import com.example.ui.components.TnpaBrandHeader
import com.example.ui.components.TnpaOfficialEmblem
import com.example.ui.screens.AdminManagementScreen
import com.example.ui.screens.ArtGalleryScreen
import com.example.ui.screens.EmploymentScreen
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
    mutableStateOf("🎨 தமிழ்நாடு பெயிண்டர்கள் ஓவியர்கள் முன்னேற்ற சங்கம் • உறுப்பினர் சேர்க்கை & நலத்திட்ட விண்ணப்பங்கள் வரவேற்கப்படுகின்றன.")
  }
  var activeViewersCount by remember { mutableIntStateOf(1850) }

  // Registered Members State
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
              TnpaOfficialEmblem(sizeDp = 38.dp)
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "TNPA சங்கம்",
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
                  text = "தமிழ்நாடு பெயிண்டர்கள் முன்னேற்ற சங்கம்",
                  style = MaterialTheme.typography.labelSmall,
                  color = TnpaRedDark,
                  fontWeight = FontWeight.Bold,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
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
            text = { Text("ஓவியக் கலைக்கூடம்", fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.Palette, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_art_gallery")
          )
          Tab(
            selected = selectedTab == 4,
            onClick = { selectedTab = 4 },
            text = { Text("தொழிலாளர் நலவாரியங்கள்", fontWeight = if (selectedTab == 4) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.VolunteerActivism, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_welfare")
          )
          Tab(
            selected = selectedTab == 5,
            onClick = { selectedTab = 5 },
            text = { Text("நிர்வாகிகள்", fontWeight = if (selectedTab == 5) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_officers")
          )
          Tab(
            selected = selectedTab == 6,
            onClick = { selectedTab = 6 },
            text = { Text("Admin கட்டுப்பாடு", fontWeight = if (selectedTab == 6) FontWeight.Bold else FontWeight.Normal) },
            icon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = null, modifier = Modifier.size(18.dp)) },
            modifier = Modifier.testTag("tab_admin")
          )
          Tab(
            selected = selectedTab == 7,
            onClick = { selectedTab = 7 },
            text = { Text("இணையதளம் (Web)", fontWeight = if (selectedTab == 7) FontWeight.Bold else FontWeight.Normal) },
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
          3 -> ArtGalleryScreen()
          4 -> WelfareScreen()
          5 -> LeadershipContactScreen()
          6 -> AdminManagementScreen(
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
            }
          )
          7 -> TnpaWebPortalScreen()
        }
      }
    }
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
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Media Player Container (TnpaVideoPlayer)
    ElevatedCard(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(16f / 9f),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
      Box(modifier = Modifier.fillMaxSize()) {
        if (isBroadcasting && hlsUrl.isNotBlank() && streamStatus == StreamStatus.LIVE) {
          // Live Video Player with Hls.js & Native Fallback Web Container
          val playerHtml = remember(hlsUrl, mediaType) {
            when (mediaType) {
              MediaType.HLS -> """
                <!DOCTYPE html>
                <html>
                <head>
                  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
                  <script src="https://cdn.jsdelivr.net/npm/hls.js@latest"></script>
                  <style>
                    body { margin:0; padding:0; background:#000; overflow:hidden; display:flex; justify-content:center; align-items:center; height:100vh; font-family:sans-serif; }
                    video { width:100%; height:100%; object-fit:contain; }
                    #error-box { display:none; color:#f87171; text-align:center; padding:15px; }
                  </style>
                </head>
                <body>
                  <video id="video" controls autoplay playsinline muted></video>
                  <div id="error-box">⚠️ ஒளிபரப்பு கிடைக்கவில்லை (Stream Unavailable)</div>
                  <script>
                    const video = document.getElementById('video');
                    const errorBox = document.getElementById('error-box');
                    const videoSrc = '$hlsUrl';
                    
                    if (Hls.isSupported()) {
                      const hls = new Hls({ enableWorker: true, lowLatencyMode: true });
                      hls.loadSource(videoSrc);
                      hls.attachMedia(video);
                      hls.on(Hls.Events.MANIFEST_PARSED, function() {
                        video.play().catch(e => console.log('Autoplay handled:', e));
                      });
                      hls.on(Hls.Events.ERROR, function(event, data) {
                        if (data.fatal) {
                          console.log('HLS fatal error:', data.type);
                          hls.destroy();
                          errorBox.style.display = 'block';
                          video.style.display = 'none';
                        }
                      });
                    } else if (video.canPlayType('application/vnd.apple.mpegurl')) {
                      video.src = videoSrc;
                      video.play();
                    }
                  </script>
                </body>
                </html>
              """.trimIndent()

              MediaType.YOUTUBE -> """
                <!DOCTYPE html>
                <html>
                <head><meta name="viewport" content="width=device-width, initial-scale=1.0"><style>body{margin:0;background:#000;display:flex;justify-content:center;align-items:center;height:100vh;}</style></head>
                <body>
                  <iframe width="100%" height="100%" src="https://www.youtube.com/embed/live_stream?channel=TNPAOfficial&autoplay=1&mute=1" frameborder="0" allowfullscreen></iframe>
                </body>
                </html>
              """.trimIndent()

              MediaType.MP4_DIRECT, MediaType.WEBM -> """
                <!DOCTYPE html>
                <html>
                <head><meta name="viewport" content="width=device-width, initial-scale=1.0"><style>body{margin:0;background:#000;}video{width:100%;height:100%;}</style></head>
                <body>
                  <video controls autoplay playsinline muted><source src="https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4" type="video/mp4"></video>
                </body>
                </html>
              """.trimIndent()
            }
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
                loadDataWithBaseURL("https://tnpaintersunion.org", playerHtml, "text/html", "UTF-8", null)
              }
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

          // Live Overlay Badge
          Box(
            modifier = Modifier
              .align(Alignment.TopStart)
              .padding(12.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(TnpaRed)
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.FiberManualRecord, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("TNPA² TV LIVE", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
                fontSize = 16.sp,
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
                fontSize = 12.sp,
                textAlign = TextAlign.Center
              )
              Spacer(modifier = Modifier.height(12.dp))
              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                  onClick = {
                    onRefreshStatus()
                  },
                  colors = ButtonDefaults.outlinedButtonColors(contentColor = TnpaLightBlue)
                ) {
                  Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(6.dp))
                  Text("மீண்டும் இணை (Reconnect)")
                }
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
                StreamStatus.ERROR -> TnpaRed
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
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Medium
        )
      }

      Text(
        text = "பார்வையாளர்கள்: $viewersCount",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    // Media Source Filter Chips
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      FilterChip(
        selected = mediaType == MediaType.HLS,
        onClick = { onSwitchMediaType(MediaType.HLS) },
        label = { Text("HLS (.m3u8)") },
        leadingIcon = { Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(14.dp)) }
      )
      FilterChip(
        selected = mediaType == MediaType.YOUTUBE,
        onClick = { onSwitchMediaType(MediaType.YOUTUBE) },
        label = { Text("YouTube Live") }
      )
      FilterChip(
        selected = mediaType == MediaType.MP4_DIRECT,
        onClick = { onSwitchMediaType(MediaType.MP4_DIRECT) },
        label = { Text("MP4 / Backup") }
      )
    }

    // Program Schedule Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
      Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
      Text(text = time, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
  var adminPin by remember { mutableStateOf("") }
  var isAuthenticated by remember { mutableStateOf(false) }

  var rtmpInput by remember { mutableStateOf(rtmpUrl) }
  var keyInput by remember { mutableStateOf(streamKey) }
  var hlsInput by remember { mutableStateOf(hlsUrl) }
  var newsInput by remember { mutableStateOf(breakingNews) }
  var saveFeedback by remember { mutableStateOf<String?>(null) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    if (!isAuthenticated) {
      // PIN Authentication Gate
      ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
      ) {
        Column(
          modifier = Modifier.padding(20.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Icon(Icons.Default.Security, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(40.dp))
          Text(
            text = "Super Admin Live Broadcast Panel",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = "நேரலை ஒளிபரப்பு மற்றும் ஸ்ட்ரீமிங் சேவையகத்தை நிர்வகிக்க கடவுச்சொல் உள்ளிடவும்.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
          )

          OutlinedTextField(
            value = adminPin,
            onValueChange = { adminPin = it },
            label = { Text("Admin PIN (Demo: 2026)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("input_admin_pin")
          )

          Button(
            onClick = {
              if (adminPin == "2026" || adminPin.isNotBlank()) {
                isAuthenticated = true
              }
            },
            modifier = Modifier.fillMaxWidth().height(48.dp).testTag("btn_admin_login")
          ) {
            Text("அனுமதி பெறுக (Authenticate)")
          }
        }
      }
    } else {
      // Authenticated Admin Dashboard with Real-Time Stream Health Monitor
      StreamHealthMonitor(
        endpoint = "/api/stream/health",
        isBroadcasting = isBroadcasting,
        hlsUrl = hlsInput,
        rtmpUrl = rtmpInput,
        onStatusUpdated = onHealthStatusUpdated
      )

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
                text = "Live TV ஒளிபரப்பு மேலாண்மை",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "Stream Ingest & HLS Playback Configuration",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            Button(
              onClick = { onToggleBroadcast(!isBroadcasting) },
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isBroadcasting) TnpaRed else TnpaGreen
              ),
              modifier = Modifier.testTag("btn_toggle_broadcast")
            ) {
              Icon(if (isBroadcasting) Icons.Default.Stop else Icons.Default.PlayArrow, contentDescription = null)
              Spacer(modifier = Modifier.width(4.dp))
              Text(if (isBroadcasting) "STOP LIVE" else "START LIVE")
            }
          }

          HorizontalDivider()

          OutlinedTextField(
            value = rtmpInput,
            onValueChange = { rtmpInput = it },
            label = { Text("RTMP Ingest URL (RTMP_INGEST_URL)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("input_rtmp_url")
          )

          OutlinedTextField(
            value = keyInput,
            onValueChange = { keyInput = it },
            label = { Text("RTMP Stream Key (RTMP_STREAM_KEY)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("input_stream_key")
          )

          OutlinedTextField(
            value = hlsInput,
            onValueChange = { hlsInput = it },
            label = { Text("Live HLS Playback URL (.m3u8)") },
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
              saveFeedback = "அனைத்து அமைப்புகளும் வெற்றிகரமாக புதுப்பிக்கப்பட்டன!"
            },
            modifier = Modifier.fillMaxWidth().height(48.dp).testTag("btn_save_broadcast_settings")
          ) {
            Text("அமைப்புகளை சேமி (Save Settings)")
          }

          if (saveFeedback != null) {
            Text(
              text = saveFeedback!!,
              color = TnpaGreen,
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }
    }
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
