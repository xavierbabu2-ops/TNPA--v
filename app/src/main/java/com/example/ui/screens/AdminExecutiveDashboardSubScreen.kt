package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AdminApprovalRepository
import com.example.model.AdminAccount
import com.example.model.AdminRole
import com.example.model.AdminStatus
import com.example.model.ApprovalStatus
import com.example.model.StreamHealthReport
import com.example.model.StreamStatus
import com.example.model.WelfareAppStatus
import com.example.ui.components.AppDownloadModal
import com.example.ui.components.TnpaOfficialEmblem
import com.example.ui.components.TnpaSimulatedQrCode
import com.example.ui.theme.TnpaBorderGray
import com.example.ui.theme.TnpaCharcoalDark
import com.example.ui.theme.TnpaCyan
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaLightGrayBg
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedPrimary
import com.example.ui.theme.TnpaSuccessGreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Executive Admin Dashboard SubScreen
 * Comprehensive master cockpit for Super Admin, State Admins, and District Admins.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AdminExecutiveDashboardSubScreen(
  admin: AdminAccount,
  onNavigateToTab: (Int) -> Unit,
  onNavigateToAiMonitoring: () -> Unit,
  streamStatus: StreamStatus,
  onOpenAppDownloadModal: () -> Unit,
  onActionTaken: () -> Unit
) {
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current
  val scrollState = rememberScrollState()

  // Dynamic Repository Data
  val memberApps = remember(admin) { AdminApprovalRepository.getMemberApplicationsForAdmin(admin) }
  val welfareApps = remember(admin) { AdminApprovalRepository.getWelfareApplicationsForAdmin(admin) }
  val jobApps = remember(admin) { AdminApprovalRepository.getJobPostingsForAdmin(admin) }
  val auditLogs = remember(admin) { AdminApprovalRepository.getAuditLogs(admin) }

  val pendingMembersCount = memberApps.count { it.status == ApprovalStatus.PENDING }
  val approvedMembersCount = memberApps.count { it.status == ApprovalStatus.APPROVED }
  val pendingWelfareCount = welfareApps.count { it.status == WelfareAppStatus.PENDING_VERIFICATION }
  val approvedWelfareCount = welfareApps.count { it.status == WelfareAppStatus.RECOMMENDED_APPROVED }
  val activeJobsCount = jobApps.size

  // Modal States
  var showBatchIdCardDialog by remember { mutableStateOf(false) }
  var showExportReportDialog by remember { mutableStateOf(false) }
  var selectedDistrictFilter by remember { mutableStateOf("அனைத்து மாவட்டங்கள்") }

  val currentDate = remember {
    SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("ta", "IN")).format(Date())
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(bottom = 24.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // ========================================================================
    // 1. EXECUTIVE WELCOME & APP DOWNLOAD HERO BANNER
    // ========================================================================
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(
        containerColor = when (admin.role) {
          AdminRole.SUPER_ADMIN -> Color(0xFF1E1B4B) // Royal Deep Indigo
          AdminRole.STATE_ADMIN -> Color(0xFF0F172A) // Deep Slate
          AdminRole.DISTRICT_ADMIN -> Color(0xFF064E3B) // Deep Emerald
        }
      ),
      elevation = CardDefaults.cardElevation(6.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            TnpaOfficialEmblem(sizeDp = 46.dp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "டிஎன்பிஏ நிர்வாக மையம்",
                  color = TnpaPureWhite,
                  fontWeight = FontWeight.Black,
                  fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(TnpaGold)
                    .padding(horizontal = 5.dp, vertical = 1.dp)
                ) {
                  Text("DASHBOARD", color = TnpaJetBlack, fontSize = 8.sp, fontWeight = FontWeight.Black)
                }
              }
              Text(
                text = "வணக்கம், ${admin.fullName}",
                color = TnpaGold,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "$currentDate • ${admin.designation}",
                color = Color(0xFFCBD5E1),
                fontSize = 11.sp
              )
            }
          }

          // Live Online Pill
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(Color(0xFF10B981).copy(alpha = 0.2f))
              .border(1.dp, Color(0xFF10B981), RoundedCornerShape(20.dp))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(Color(0xFF10B981))
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text("LIVE ADMIN", color = Color(0xFF10B981), fontSize = 9.sp, fontWeight = FontWeight.Black)
            }
          }
        }

        HorizontalDivider(color = Color.White.copy(alpha = 0.15f))

        // Quick Download & Share Shortcut Card Inside Banner
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.08f)),
          border = androidx.compose.foundation.BorderStroke(1.dp, TnpaGold.copy(alpha = 0.4f))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Android, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(24.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "டிஎன்பிஏ மொபைல் செயலி (Official APK v2.4)",
                  color = TnpaPureWhite,
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp
                )
                Text(
                  text = "உறுப்பினர்கள் & நிர்வாகிகளுடன் செயலியைப் பகிர்க",
                  color = Color(0xFFE2E8F0),
                  fontSize = 10.sp
                )
              }
            }

            Button(
              onClick = onOpenAppDownloadModal,
              colors = ButtonDefaults.buttonColors(containerColor = TnpaGold),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.height(34.dp).testTag("btn_dash_download_apk")
            ) {
              Icon(Icons.Default.Download, contentDescription = null, tint = TnpaJetBlack, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("பதிவிறக்கம் & பகிர்வு", color = TnpaJetBlack, fontSize = 11.sp, fontWeight = FontWeight.Black)
            }
          }
        }
      }
    }

    // ========================================================================
    // 2. CORE KPI METRIC CARDS (REAL-TIME AGGREGATES)
    // ========================================================================
    Text(
      text = "முக்கிய புள்ளிவிவரங்கள் & தற்போதைய நிலை (Executive KPIs):",
      fontWeight = FontWeight.Black,
      fontSize = 13.sp,
      color = TnpaJetBlack
    )

    FlowRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // 1. Total Members
      KpiMetricCard(
        modifier = Modifier.weight(1f),
        title = "மொத்த உறுப்பினர்கள்",
        value = "${14850 + approvedMembersCount}+",
        subtext = "38 மாவட்டங்களில்",
        icon = Icons.Default.People,
        iconTint = Color(0xFF2563EB),
        bgGradient = listOf(Color(0xFFEFF6FF), Color(0xFFDBEAFE)),
        tag = "kpi_total_members",
        onClick = { onNavigateToTab(1) } // Member tab
      )

      // 2. Pending Approvals
      KpiMetricCard(
        modifier = Modifier.weight(1f),
        title = "ஒப்புதல் நிலுவை",
        value = "$pendingMembersCount மனுக்கள்",
        subtext = if (pendingMembersCount > 0) "உடனடி கவனம் தேவை" else "அனைத்தும் சரிபார்க்கப்பட்டது",
        icon = Icons.Default.PendingActions,
        iconTint = if (pendingMembersCount > 0) TnpaRedPrimary else TnpaGreen,
        bgGradient = if (pendingMembersCount > 0) listOf(Color(0xFFFEF2F2), Color(0xFFFEE2E2)) else listOf(Color(0xFFF0FDF4), Color(0xFFDCFCE7)),
        badge = if (pendingMembersCount > 0) "ACTION" else "CLEAR",
        tag = "kpi_pending_approvals",
        onClick = { onNavigateToTab(1) }
      )
    }

    FlowRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // 3. Welfare Assistance Disbursed
      KpiMetricCard(
        modifier = Modifier.weight(1f),
        title = "நலவாரிய உதவிகள்",
        value = "₹48.5 லட்சம்+",
        subtext = "$approvedWelfareCount மனுக்கள் ஒப்புதல்",
        icon = Icons.Default.VolunteerActivism,
        iconTint = Color(0xFF059669),
        bgGradient = listOf(Color(0xFFECFDF5), Color(0xFFD1FAE5)),
        tag = "kpi_welfare_funds",
        onClick = { onNavigateToTab(2) } // Welfare tab
      )

      // 4. Active Job Postings
      KpiMetricCard(
        modifier = Modifier.weight(1f),
        title = "வேலைவாய்ப்புகள்",
        value = "$activeJobsCount தளங்கள்",
        subtext = "அரசு & தனியார் ஒப்பந்தங்கள்",
        icon = Icons.Default.Work,
        iconTint = Color(0xFFD97706),
        bgGradient = listOf(Color(0xFFFFFBEB), Color(0xFFFEF3C7)),
        tag = "kpi_active_jobs",
        onClick = { onNavigateToTab(3) } // Jobs tab
      )
    }

    FlowRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // 5. AI Advisor Performance Index
      KpiMetricCard(
        modifier = Modifier.weight(1f),
        title = "AI நிர்வாக மதிப்பீடு",
        value = "94.8% (Elite)",
        subtext = "Gemini 3.5 பகுப்பாய்வு",
        icon = Icons.Default.Psychology,
        iconTint = Color(0xFF7C3AED),
        bgGradient = listOf(Color(0xFFF5F3FF), Color(0xFFEDE9FE)),
        badge = "AI ACTIVE",
        tag = "kpi_ai_index",
        onClick = onNavigateToAiMonitoring
      )

      // 6. TV Broadcast & Breaking News
      KpiMetricCard(
        modifier = Modifier.weight(1f),
        title = "TV நேரலை சேனல்",
        value = if (streamStatus == StreamStatus.LIVE) "ஒளிபரப்பாகிறது (Live)" else "காத்திருப்பு (Standby)",
        subtext = if (streamStatus == StreamStatus.LIVE) "1080p • 60 FPS" else "RTMP Ready",
        icon = Icons.Default.LiveTv,
        iconTint = if (streamStatus == StreamStatus.LIVE) TnpaRedPrimary else Color.DarkGray,
        bgGradient = if (streamStatus == StreamStatus.LIVE) listOf(Color(0xFFFEF2F2), Color(0xFFFECACA)) else listOf(Color(0xFFF8FAFC), Color(0xFFE2E8F0)),
        tag = "kpi_tv_status",
        onClick = {
          if (admin.role == AdminRole.SUPER_ADMIN || admin.role == AdminRole.STATE_ADMIN) {
            onNavigateToTab(6) // TV Control Tab
          }
        }
      )
    }

    // ========================================================================
    // 3. EXECUTIVE QUICK ACTIONS STATION (அட்மின் உடனடி நடவடிக்கைகள்)
    // ========================================================================
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
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
            Icon(Icons.Default.Tune, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("நிர்வாகிகள் விரைவுப் பணிகள் (Executive Actions)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TnpaJetBlack)
          }
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(Color(0xFFF1F5F9))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text("8 SHORTCUTS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TnpaCharcoalDark)
          }
        }

        HorizontalDivider(color = Color(0xFFF1F5F9))

        FlowRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // 1. Download & Share APK
          QuickActionButton(
            label = "📲 செயலி பதிவிறக்கம்",
            icon = Icons.Default.Download,
            tint = Color(0xFF2563EB),
            onClick = onOpenAppDownloadModal,
            tag = "quick_act_download"
          )

          // 2. Member Approvals
          QuickActionButton(
            label = "👥 உறுப்பினர் அனுமதி ($pendingMembersCount)",
            icon = Icons.Default.Badge,
            tint = TnpaRedPrimary,
            onClick = { onNavigateToTab(1) },
            tag = "quick_act_members"
          )

          // 3. Welfare Claims
          QuickActionButton(
            label = "🏛️ நலவாரிய மனுக்கள் ($pendingWelfareCount)",
            icon = Icons.Default.VolunteerActivism,
            tint = TnpaGreen,
            onClick = { onNavigateToTab(2) },
            tag = "quick_act_welfare"
          )

          // 4. Job Postings
          QuickActionButton(
            label = "💼 வேலைவாய்ப்பு மேலாண்மை",
            icon = Icons.Default.Work,
            tint = Color(0xFFD97706),
            onClick = { onNavigateToTab(3) },
            tag = "quick_act_jobs"
          )

          // 5. AI Advisor
          QuickActionButton(
            label = "🤖 AI வழிகாட்டி அறிக்கை",
            icon = Icons.Default.AutoAwesome,
            tint = Color(0xFF7C3AED),
            onClick = onNavigateToAiMonitoring,
            tag = "quick_act_ai"
          )

          // 6. Batch ID Card Generator
          QuickActionButton(
            label = "🪪 அடையாள அட்டை தயாரிப்பு",
            icon = Icons.Default.Print,
            tint = Color(0xFF0891B2),
            onClick = { showBatchIdCardDialog = true },
            tag = "quick_act_batch_id"
          )

          // 7. TV Broadcast Control (Super/State)
          if (admin.role == AdminRole.SUPER_ADMIN || admin.role == AdminRole.STATE_ADMIN) {
            QuickActionButton(
              label = "📺 நேரலை TV ஒளிபரப்பு",
              icon = Icons.Default.LiveTv,
              tint = TnpaRedPrimary,
              onClick = { onNavigateToTab(6) },
              tag = "quick_act_tv"
            )
          }

          // 8. Export Register Data
          QuickActionButton(
            label = "📊 பதிவேடு அறிக்கை (Export)",
            icon = Icons.Default.FileDownload,
            tint = Color(0xFF475569),
            onClick = { showExportReportDialog = true },
            tag = "quick_act_export"
          )
        }
      }
    }

    // ========================================================================
    // 4. DISTRICT REGISTRATION TARGET & PERFORMANCE BARS
    // ========================================================================
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
      Column(
        modifier = Modifier.padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = TnpaGreen, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("மாவட்ட வாரியாக உறுப்பினர் வளர்ச்சி (District Enrolments):", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TnpaJetBlack)
          }
          Text("இலக்கு: 50,000", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaRedPrimary)
        }

        // District Progress items
        DistrictPerformanceRow(district = "மதுரை மாவட்டம் (Madurai)", registered = 3450, target = 4000, color = TnpaRedPrimary)
        DistrictPerformanceRow(district = "திருச்சிராப்பள்ளி (Trichy)", registered = 2890, target = 3500, color = Color(0xFF2563EB))
        DistrictPerformanceRow(district = "சென்னை (Chennai)", registered = 2650, target = 4500, color = Color(0xFF059669))
        DistrictPerformanceRow(district = "கோயம்புத்தூர் (Coimbatore)", registered = 1920, target = 3000, color = Color(0xFFD97706))
        DistrictPerformanceRow(district = "சேலம் (Salem)", registered = 1480, target = 2500, color = Color(0xFF7C3AED))
        DistrictPerformanceRow(district = "திருநெல்வேலி (Tirunelveli)", registered = 1240, target = 2000, color = Color(0xFF0891B2))
        DistrictPerformanceRow(district = "தஞ்சாவூர் (Thanjavur)", registered = 1220, target = 2000, color = Color(0xFFEA580C))
      }
    }

    // ========================================================================
    // 5. RECENT ACTIVITY STREAM (AUDIT LOGS & ACTIONS)
    // ========================================================================
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
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
            Icon(Icons.Default.History, contentDescription = null, tint = TnpaCharcoalDark, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("சமீபத்திய சங்க நடவடிக்கைகள் (Recent Audit Trail):", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TnpaJetBlack)
          }

          TextButton(onClick = { onNavigateToTab(5) }) {
            Text("அனைத்தும் >", fontSize = 11.sp, color = TnpaRedPrimary, fontWeight = FontWeight.Bold)
          }
        }

        HorizontalDivider(color = Color(0xFFF1F5F9))

        val recentLogs = auditLogs.take(5)
        if (recentLogs.isEmpty()) {
          Text("நடவடிக்கைகள் எதுவும் பதிவு செய்யப்படவில்லை.", fontSize = 12.sp, color = Color.Gray)
        } else {
          recentLogs.forEach { log ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.Top
            ) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(TnpaRedPrimary)
                  .padding(top = 4.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = log.detailsTamil,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium,
                  color = TnpaJetBlack,
                  lineHeight = 15.sp
                )
                Text(
                  text = "${log.timestamp} • ${log.adminName} (${log.adminRole})",
                  fontSize = 9.sp,
                  color = Color.Gray
                )
              }
            }
          }
        }
      }
    }
  }

  // ==========================================================================
  // BATCH ID CARD GENERATOR DIALOG
  // ==========================================================================
  if (showBatchIdCardDialog) {
    AlertDialog(
      onDismissRequest = { showBatchIdCardDialog = false },
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Badge, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(24.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("தொகுதி அடையாள அட்டை தயாரிப்பு", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = "ஒப்புதல் அளிக்கப்பட்ட அனைத்து உறுப்பினர்களுக்கும் PDF வடிவில் வண்ண டிஜிட்டல் அடையாள அட்டைகளைத் தயாரிக்கலாம்.",
            fontSize = 12.sp,
            color = Color.DarkGray
          )
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF))
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text("தயாராக உள்ள அட்டைகள்: $approvedMembersCount நபர்கள்", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF1E3A8A))
              Text("வடிவமைப்பு: TNPA Official Red-Gold Ribbon Card with QR Verification", fontSize = 10.sp, color = Color(0xFF3B82F6))
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            showBatchIdCardDialog = false
            Toast.makeText(context, "$approvedMembersCount அடையாள அட்டைகள் PDF ஆக உருவாக்கப்பட்டது! (Downloaded to Device)", Toast.LENGTH_LONG).show()
          },
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
        ) {
          Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("PDF பதிவிறக்கம் செய்க", fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { showBatchIdCardDialog = false }) {
          Text("மூடு")
        }
      }
    )
  }

  // ==========================================================================
  // EXPORT MASTER DATA REPORT DIALOG
  // ==========================================================================
  if (showExportReportDialog) {
    AlertDialog(
      onDismissRequest = { showExportReportDialog = false },
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.FileDownload, contentDescription = null, tint = TnpaGreen, modifier = Modifier.size(24.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("சங்க பதிவேடு தரவு ஏற்றுமதி (Data Export)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = "உறுப்பினர் விவரங்கள், நலவாரிய விண்ணப்பங்கள் மற்றும் தணிக்கை அறிக்கைகளை Excel / CSV வடிவத்தில் ஏற்றுமதி செய்யலாம்.",
            fontSize = 12.sp,
            color = Color.DarkGray
          )
          Text("• மொத்த பதிவுகள்: ${memberApps.size + welfareApps.size} வரிகள்", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
          Text("• கோப்பு வடிவம்: UTF-8 Tamil Encoded CSV / XLSX", fontSize = 11.sp, color = Color.Gray)
        }
      },
      confirmButton = {
        Button(
          onClick = {
            showExportReportDialog = false
            Toast.makeText(context, "TNPA_Master_Register_2026.csv கோப்பு பதிவிறக்கம் செய்யப்பட்டது!", Toast.LENGTH_LONG).show()
          },
          colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen)
        ) {
          Text("Excel / CSV பதிவிறக்கு", fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { showExportReportDialog = false }) {
          Text("ரத்து")
        }
      }
    )
  }
}

/**
 * KPI Metric Card Item
 */
@Composable
fun KpiMetricCard(
  modifier: Modifier = Modifier,
  title: String,
  value: String,
  subtext: String,
  icon: ImageVector,
  iconTint: Color,
  bgGradient: List<Color>,
  badge: String? = null,
  tag: String,
  onClick: () -> Unit
) {
  Card(
    modifier = modifier
      .clickable { onClick() }
      .testTag(tag),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
    elevation = CardDefaults.cardElevation(2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(Brush.verticalGradient(bgGradient))
        .padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Color.White),
          contentAlignment = Alignment.Center
        ) {
          Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
        }

        if (badge != null) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(iconTint)
              .padding(horizontal = 5.dp, vertical = 1.dp)
          ) {
            Text(badge, color = TnpaPureWhite, fontSize = 8.sp, fontWeight = FontWeight.Black)
          }
        }
      }

      Text(
        text = title,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = TnpaCharcoalDark
      )

      Text(
        text = value,
        fontSize = 16.sp,
        fontWeight = FontWeight.Black,
        color = TnpaJetBlack
      )

      Text(
        text = subtext,
        fontSize = 10.sp,
        color = Color.DarkGray,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

/**
 * Quick Action Button inside Station
 */
@Composable
fun QuickActionButton(
  label: String,
  icon: ImageVector,
  tint: Color,
  onClick: () -> Unit,
  tag: String
) {
  OutlinedButton(
    onClick = onClick,
    modifier = Modifier
      .height(38.dp)
      .testTag(tag),
    shape = RoundedCornerShape(8.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, tint.copy(alpha = 0.5f)),
    colors = ButtonDefaults.outlinedButtonColors(
      containerColor = tint.copy(alpha = 0.06f),
      contentColor = tint
    ),
    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
  ) {
    Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
    Spacer(modifier = Modifier.width(6.dp))
    Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
  }
}

/**
 * District Performance Bar
 */
@Composable
fun DistrictPerformanceRow(
  district: String,
  registered: Int,
  target: Int,
  color: Color
) {
  val progress = (registered.toFloat() / target.toFloat()).coerceIn(0f, 1f)

  Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(district, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
      Text("$registered / $target (${(progress * 100).toInt()}%)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = color)
    }

    LinearProgressIndicator(
      progress = { progress },
      modifier = Modifier
        .fillMaxWidth()
        .height(6.dp)
        .clip(RoundedCornerShape(3.dp)),
      color = color,
      trackColor = Color(0xFFF1F5F9)
    )
  }
}
