package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ExecutiveAiMonitoringRepository
import com.example.model.AdminHierarchyLevel
import com.example.model.AiChatMessage
import com.example.model.AiGeneratedAdvisory
import com.example.model.ChatSender
import com.example.model.LeaderPerformanceProfile
import com.example.model.PerformanceTier
import com.example.ui.theme.TnpaBorderGray
import com.example.ui.theme.TnpaCharcoalDark
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGoldDark
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaLightGrayBg
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedPrimary
import com.example.ui.theme.TnpaSuccessGreen
import kotlinx.coroutines.launch

@Composable
fun ExecutiveAiMonitoringScreen(
  onNavigateToOfficeBearers: (() -> Unit)? = null,
  onNavigateToAdminPanel: (() -> Unit)? = null
) {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()

  val leaderProfiles by ExecutiveAiMonitoringRepository.leaderProfiles.collectAsState()
  val dashboardSummary by ExecutiveAiMonitoringRepository.dashboardSummary.collectAsState()
  val selectedLeader by ExecutiveAiMonitoringRepository.selectedLeader.collectAsState()
  val isAiAnalyzing by ExecutiveAiMonitoringRepository.isAiAnalyzing.collectAsState()
  val chatMessages by ExecutiveAiMonitoringRepository.chatMessages.collectAsState()

  var selectedSubTab by remember { mutableIntStateOf(0) }
  var searchQuery by remember { mutableStateOf("") }
  var selectedLevelFilter by remember { mutableStateOf<AdminHierarchyLevel?>(null) }
  var selectedTierFilter by remember { mutableStateOf<PerformanceTier?>(null) }

  // Edit KPI dialog state
  var editingLeader by remember { mutableStateOf<LeaderPerformanceProfile?>(null) }
  var editMembersActual by remember { mutableStateOf("") }
  var editWelfareActual by remember { mutableStateOf("") }
  var editMeetingsHeld by remember { mutableStateOf("") }

  // Filtered Leader list
  val filteredLeaders = remember(leaderProfiles, searchQuery, selectedLevelFilter, selectedTierFilter) {
    leaderProfiles.filter { leader ->
      val matchesSearch = searchQuery.isBlank() ||
          leader.tamilName.contains(searchQuery, ignoreCase = true) ||
          leader.fullName.contains(searchQuery, ignoreCase = true) ||
          leader.district.contains(searchQuery, ignoreCase = true) ||
          leader.unionOrCity.contains(searchQuery, ignoreCase = true) ||
          leader.designation.contains(searchQuery, ignoreCase = true)

      val matchesLevel = selectedLevelFilter == null || leader.level == selectedLevelFilter
      val matchesTier = selectedTierFilter == null || leader.tier == selectedTierFilter

      matchesSearch && matchesLevel && matchesTier
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(TnpaOffWhite)
  ) {
    // ========================================================================
    // 1. AI HERO HEADER
    // ========================================================================
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.verticalGradient(
            colors = listOf(TnpaJetBlack, Color(0xFF0F172A), TnpaCharcoalDark)
          )
        )
        .padding(16.dp)
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(TnpaRedPrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                Icons.Default.Psychology,
                contentDescription = null,
                tint = TnpaGold,
                modifier = Modifier.size(24.dp)
              )
            }

            Column {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                  text = "TNPA AI நிர்வாக வழிகாட்டி",
                  color = TnpaPureWhite,
                  fontSize = 17.sp,
                  fontWeight = FontWeight.Black
                )
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF2563EB))
                    .padding(horizontal = 5.dp, vertical = 2.dp)
                ) {
                  Text("Gemini 3.5 AI", color = TnpaPureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }
              }
              Text(
                text = "மாநில • மண்டல • மாவட்ட • ஒன்றிய நிர்வாகிகள் கண்காணிப்பு & உத்திகள்",
                color = TnpaGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }

          if (isAiAnalyzing) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = TnpaGold,
                strokeWidth = 2.dp
              )
              Text("AI பகுப்பாய்வு...", color = TnpaGold, fontSize = 10.sp)
            }
          }
        }

        // Mini KPI Summary Bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Stat 1
          AiHeaderStatCard(
            modifier = Modifier.weight(1f),
            title = "சராசரி ஸ்கோர்",
            value = "${dashboardSummary.stateAverageScore}/100",
            subtext = "மாநில தரம்",
            accentColor = TnpaGold
          )

          // Stat 2
          AiHeaderStatCard(
            modifier = Modifier.weight(1f),
            title = "முன்னணி மாவட்டம்",
            value = dashboardSummary.topPerformingDistrict.split(" ").first(),
            subtext = "93% இலக்கு",
            accentColor = TnpaSuccessGreen
          )

          // Stat 3
          AiHeaderStatCard(
            modifier = Modifier.weight(1f),
            title = "கண்காணிப்பில்",
            value = "${dashboardSummary.totalMonitoredLeaders} நிர்வாகிகள்",
            subtext = "8 நிலைகள்",
            accentColor = Color(0xFF60A5FA)
          )
        }
      }
    }

    // ========================================================================
    // 2. SUB-TABS (4 DISTINCT SECTIONS)
    // ========================================================================
    ScrollableTabRow(
      selectedTabIndex = selectedSubTab,
      containerColor = TnpaJetBlack,
      contentColor = TnpaPureWhite,
      edgePadding = 12.dp,
      divider = { HorizontalDivider(color = Color(0xFF334155), thickness = 1.dp) }
    ) {
      Tab(
        selected = selectedSubTab == 0,
        onClick = { selectedSubTab = 0 },
        text = { Text("📊 நிர்வாகிகள் ஸ்கோர்", fontSize = 12.sp, fontWeight = if (selectedSubTab == 0) FontWeight.Bold else FontWeight.Normal) },
        modifier = Modifier.testTag("tab_ai_leaders_list")
      )
      Tab(
        selected = selectedSubTab == 1,
        onClick = { selectedSubTab = 1 },
        text = { Text("🤖 AI ஆய்வு & அறிக்கை", fontSize = 12.sp, fontWeight = if (selectedSubTab == 1) FontWeight.Bold else FontWeight.Normal) },
        modifier = Modifier.testTag("tab_ai_deep_advisory")
      )
      Tab(
        selected = selectedSubTab == 2,
        onClick = { selectedSubTab = 2 },
        text = { Text("💬 AI ஆலோசனைக் குழு", fontSize = 12.sp, fontWeight = if (selectedSubTab == 2) FontWeight.Bold else FontWeight.Normal) },
        modifier = Modifier.testTag("tab_ai_chat_advisor")
      )
      Tab(
        selected = selectedSubTab == 3,
        onClick = { selectedSubTab = 3 },
        text = { Text("🏆 மாவட்ட தரவரிசை", fontSize = 12.sp, fontWeight = if (selectedSubTab == 3) FontWeight.Bold else FontWeight.Normal) },
        modifier = Modifier.testTag("tab_ai_district_leaderboard")
      )
    }

    // ========================================================================
    // 3. TAB CONTENT ROUTING
    // ========================================================================
    when (selectedSubTab) {
      0 -> LeaderKpiListTab(
        leaders = filteredLeaders,
        searchQuery = searchQuery,
        onSearchChange = { searchQuery = it },
        selectedLevel = selectedLevelFilter,
        onSelectLevel = { selectedLevelFilter = it },
        selectedTier = selectedTierFilter,
        onSelectTier = { selectedTierFilter = it },
        onViewAiAdvisory = { leader ->
          ExecutiveAiMonitoringRepository.selectLeader(leader)
          selectedSubTab = 1
        },
        onEditKpis = { leader ->
          editingLeader = leader
          editMembersActual = leader.memberEnrollmentActual.toString()
          editWelfareActual = leader.welfareBoardActual.toString()
          editMeetingsHeld = leader.monthlyMeetingsHeld.toString()
        }
      )

      1 -> DeepAiAdvisoryTab(
        selectedLeader = selectedLeader,
        allLeaders = leaderProfiles,
        isAiAnalyzing = isAiAnalyzing,
        onSelectLeader = { ExecutiveAiMonitoringRepository.selectLeader(it) },
        onRefreshAdvisory = {
          coroutineScope.launch {
            ExecutiveAiMonitoringRepository.requestAiAdvisoryForSelectedLeader()
          }
        },
        onCopyReport = { text ->
          val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
          clipboard.setPrimaryClip(ClipData.newPlainText("TNPA AI Advisory", text))
          Toast.makeText(context, "AI ஆலோசனை அறிக்கை நகலெடுக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
        },
        onShareReport = { text ->
          val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "TNPA AI நிர்வாகிகள் ஆலோசனை அறிக்கை")
            putExtra(Intent.EXTRA_TEXT, text)
          }
          context.startActivity(Intent.createChooser(intent, "அறிக்கையைப் பகிரவும்"))
        }
      )

      2 -> InteractiveAiAdvisorTab(
        chatMessages = chatMessages,
        selectedLeader = selectedLeader,
        onSendMessage = { message ->
          coroutineScope.launch {
            ExecutiveAiMonitoringRepository.sendChatMessage(message)
          }
        }
      )

      3 -> DistrictLeaderboardTab(
        leaders = leaderProfiles,
        dashboardSummary = dashboardSummary,
        onSelectLeader = { leader ->
          ExecutiveAiMonitoringRepository.selectLeader(leader)
          selectedSubTab = 1
        }
      )
    }
  }

  // ==========================================================================
  // EDIT KPI MODAL DIALOG
  // ==========================================================================
  if (editingLeader != null) {
    val currentLeader = editingLeader!!
    AlertDialog(
      onDismissRequest = { editingLeader = null },
      title = {
        Text(
          text = "அளவீடுகள் திருத்தம்: ${currentLeader.tamilName}",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = TnpaRedPrimary
        )
      },
      text = {
        Column(
          modifier = Modifier.verticalScroll(rememberScrollState()),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Text(
            text = "${currentLeader.designation} (${currentLeader.district})",
            fontSize = 12.sp,
            color = Color(0xFF64748B)
          )

          OutlinedTextField(
            value = editMembersActual,
            onValueChange = { editMembersActual = it.filter { ch -> ch.isDigit() } },
            label = { Text("உறுப்பினர் சேர்க்கை சாதனை (இலக்கு: ${currentLeader.memberEnrollmentTarget})") },
            modifier = Modifier.fillMaxWidth().testTag("input_edit_members")
          )

          OutlinedTextField(
            value = editWelfareActual,
            onValueChange = { editWelfareActual = it.filter { ch -> ch.isDigit() } },
            label = { Text("நலவாரிய அட்டை பதிவு (இலக்கு: ${currentLeader.welfareBoardTarget})") },
            modifier = Modifier.fillMaxWidth().testTag("input_edit_welfare")
          )

          OutlinedTextField(
            value = editMeetingsHeld,
            onValueChange = { editMeetingsHeld = it.filter { ch -> ch.isDigit() } },
            label = { Text("நடத்திய மாதாந்திர கூட்டங்கள் (இலக்கு: ${currentLeader.monthlyMeetingsTarget})") },
            modifier = Modifier.fillMaxWidth().testTag("input_edit_meetings")
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            val mem = editMembersActual.toIntOrNull() ?: currentLeader.memberEnrollmentActual
            val wlf = editWelfareActual.toIntOrNull() ?: currentLeader.welfareBoardActual
            val mtg = editMeetingsHeld.toIntOrNull() ?: currentLeader.monthlyMeetingsHeld

            ExecutiveAiMonitoringRepository.updateLeaderMetrics(
              leaderId = currentLeader.id,
              newEnrollmentActual = mem,
              newWelfareActual = wlf,
              newMeetingsHeld = mtg
            )
            Toast.makeText(context, "அளவீடுகள் புதுப்பிக்கப்பட்டு AI ஸ்கோர் கணக்கிடப்பட்டது!", Toast.LENGTH_SHORT).show()
            editingLeader = null
          },
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
        ) {
          Text("சேமி & கணக்கிடு", color = TnpaPureWhite)
        }
      },
      dismissButton = {
        TextButton(onClick = { editingLeader = null }) {
          Text("ரத்து")
        }
      }
    )
  }
}

// ============================================================================
// TAB 1: LEADER KPI LIST & SEARCH
// ============================================================================

@Composable
private fun LeaderKpiListTab(
  leaders: List<LeaderPerformanceProfile>,
  searchQuery: String,
  onSearchChange: (String) -> Unit,
  selectedLevel: AdminHierarchyLevel?,
  onSelectLevel: (AdminHierarchyLevel?) -> Unit,
  selectedTier: PerformanceTier?,
  onSelectTier: (PerformanceTier?) -> Unit,
  onViewAiAdvisory: (LeaderPerformanceProfile) -> Unit,
  onEditKpis: (LeaderPerformanceProfile) -> Unit
) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Search input
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchChange,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("input_ai_leader_search"),
        placeholder = { Text("நிர்வாகி பெயர், மாவட்டம், ஒன்றியம், பதவி தேட...", fontSize = 13.sp) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TnpaRedPrimary) },
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = TnpaPureWhite,
          unfocusedContainerColor = TnpaPureWhite,
          focusedBorderColor = TnpaRedPrimary,
          unfocusedBorderColor = TnpaBorderGray
        )
      )
    }

    // Level Filter Chips
    item {
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text("பதவி நிலை வடிகட்டி (Hierarchy Filter):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaCharcoalDark)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          LevelFilterChip(
            label = "அனைத்தும் (${leaders.size})",
            isSelected = selectedLevel == null,
            onClick = { onSelectLevel(null) }
          )
          AdminHierarchyLevel.entries.forEach { level ->
            LevelFilterChip(
              label = "${level.iconEmoji} ${level.labelTamil}",
              isSelected = selectedLevel == level,
              onClick = { onSelectLevel(if (selectedLevel == level) null else level) }
            )
          }
        }
      }
    }

    // Performance Tier Filter Chips
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        TierFilterChip(
          label = "அனைத்து தரவரிசை",
          isSelected = selectedTier == null,
          colorHex = 0xFF475569,
          onClick = { onSelectTier(null) }
        )
        PerformanceTier.entries.forEach { tier ->
          TierFilterChip(
            label = "${tier.gradeBadge} ${tier.labelTamil.split(" ").first()}",
            isSelected = selectedTier == tier,
            colorHex = tier.colorHex,
            onClick = { onSelectTier(if (selectedTier == tier) null else tier) }
          )
        }
      }
    }

    // Leaders list count
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "கண்காணிக்கப்படும் நிர்வாகிகள் (${leaders.size})",
          fontSize = 13.sp,
          fontWeight = FontWeight.Black,
          color = TnpaCharcoalDark
        )
        Text(
          text = "AI தானியங்கி மதிப்பீடு",
          fontSize = 11.sp,
          color = TnpaRedPrimary,
          fontWeight = FontWeight.Bold
        )
      }
    }

    // Leader Cards
    if (leaders.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = TnpaPureWhite)
        ) {
          Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(Icons.Default.FilterList, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(36.dp))
            Text("தேடலுக்குரிய நிர்வாகிகள் கண்டறியப்படவில்லை.", fontSize = 13.sp, color = Color.Gray)
          }
        }
      }
    } else {
      items(leaders, key = { it.id }) { leader ->
        LeaderPerformanceCard(
          leader = leader,
          onViewAdvisory = { onViewAiAdvisory(leader) },
          onEditKpi = { onEditKpis(leader) }
        )
      }
    }
  }
}

@Composable
private fun LeaderPerformanceCard(
  leader: LeaderPerformanceProfile,
  onViewAdvisory: () -> Unit,
  onEditKpi: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("card_leader_ai_${leader.id}"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = BorderStroke(1.dp, Color(leader.tier.colorHex).copy(alpha = 0.4f)),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier.padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // Header: Avatar, Name, Score Badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(Color(leader.tier.colorHex).copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = leader.level.iconEmoji,
              fontSize = 20.sp
            )
          }

          Column {
            Text(
              text = leader.tamilName,
              fontSize = 14.sp,
              fontWeight = FontWeight.Black,
              color = TnpaCharcoalDark,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
            Text(
              text = leader.designation,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = TnpaRedPrimary
            )
            Text(
              text = "${leader.district} ${if (leader.unionOrCity.isNotBlank()) "• ${leader.unionOrCity}" else ""}",
              fontSize = 10.sp,
              color = Color(0xFF64748B)
            )
          }
        }

        // AI Score Circle
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
          Box(
            modifier = Modifier
              .size(46.dp)
              .clip(CircleShape)
              .background(Color(leader.tier.colorHex)),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "${leader.overallAiScore}",
                color = TnpaPureWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black
              )
              Text(
                text = leader.tier.gradeBadge,
                color = TnpaPureWhite,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
          Text(
            text = "AI ஸ்கோர்",
            fontSize = 8.sp,
            color = Color(0xFF64748B),
            fontWeight = FontWeight.Bold
          )
        }
      }

      HorizontalDivider(color = TnpaBorderGray.copy(alpha = 0.6f))

      // Metric Progress Bars
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        MetricProgressBarRow(
          label = "உறுப்பினர் சேர்க்கை",
          actual = leader.memberEnrollmentActual,
          target = leader.memberEnrollmentTarget,
          pct = leader.memberEnrollmentPct,
          barColor = Color(0xFF2563EB)
        )

        MetricProgressBarRow(
          label = "நலவாரிய அட்டை பதிவு",
          actual = leader.welfareBoardActual,
          target = leader.welfareBoardTarget,
          pct = leader.welfareEnrollmentPct,
          barColor = TnpaSuccessGreen
        )

        MetricProgressBarRow(
          label = "மாதாந்திர கூட்டங்கள்",
          actual = leader.monthlyMeetingsHeld,
          target = leader.monthlyMeetingsTarget,
          pct = ((leader.monthlyMeetingsHeld.toDouble() / leader.monthlyMeetingsTarget.coerceAtLeast(1)) * 100).toInt(),
          barColor = TnpaGoldDark
        )
      }

      // Action Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Button(
          onClick = onViewAdvisory,
          modifier = Modifier
            .weight(1f)
            .height(38.dp)
            .testTag("btn_view_ai_advisory_${leader.id}"),
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF0F172A),
            contentColor = TnpaGold
          )
        ) {
          Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("AI ஆலோசனை அறிக்கை", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }

        OutlinedButton(
          onClick = onEditKpi,
          modifier = Modifier
            .height(38.dp)
            .testTag("btn_edit_kpi_${leader.id}"),
          shape = RoundedCornerShape(8.dp),
          border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
          contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
          Icon(Icons.Default.Edit, contentDescription = "Edit", tint = TnpaCharcoalDark, modifier = Modifier.size(14.dp))
        }
      }
    }
  }
}

@Composable
private fun MetricProgressBarRow(
  label: String,
  actual: Int,
  target: Int,
  pct: Int,
  barColor: Color
) {
  Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(label, fontSize = 10.sp, color = TnpaCharcoalDark, fontWeight = FontWeight.Medium)
      Text("$actual / $target ($pct%)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = barColor)
    }
    LinearProgressIndicator(
      progress = { (pct / 100f).coerceIn(0f, 1f) },
      modifier = Modifier
        .fillMaxWidth()
        .height(5.dp)
        .clip(RoundedCornerShape(3.dp)),
      color = barColor,
      trackColor = Color(0xFFE2E8F0)
    )
  }
}

// ============================================================================
// TAB 2: DEEP AI ADVISORY DOSSIER
// ============================================================================

@Composable
private fun DeepAiAdvisoryTab(
  selectedLeader: LeaderPerformanceProfile?,
  allLeaders: List<LeaderPerformanceProfile>,
  isAiAnalyzing: Boolean,
  onSelectLeader: (LeaderPerformanceProfile) -> Unit,
  onRefreshAdvisory: () -> Unit,
  onCopyReport: (String) -> Unit,
  onShareReport: (String) -> Unit
) {
  if (selectedLeader == null) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
      contentAlignment = Alignment.Center
    ) {
      Text("தயவுசெய்து ஒரு நிர்வாகியைத் தேர்ந்தெடுக்கவும்.", fontSize = 14.sp, color = Color.Gray)
    }
    return
  }

  val advisory = selectedLeader.cachedAdvisory ?: ExecutiveAiMonitoringRepository.leaderProfiles.value
    .find { it.id == selectedLeader.id }?.cachedAdvisory

  val fullReportText = remember(selectedLeader, advisory) {
    buildString {
      appendLine("🏛️ தமிழ்நாடு பெயிண்டர்கள் முன்னேற்ற சங்கம் (TNPA²)")
      appendLine("🤖 AI நிர்வாகிகள் செயல்திறன் & ஆலோசனைக் குறிப்பு")
      appendLine("==========================================")
      appendLine("நிர்வாகி: ${selectedLeader.tamilName} (${selectedLeader.fullName})")
      appendLine("பொறுப்பு: ${selectedLeader.designation}")
      appendLine("மாவட்டம்/ஒன்றியம்: ${selectedLeader.district} • ${selectedLeader.unionOrCity}")
      appendLine("AI செயல்திறன் ஸ்கோர்: ${selectedLeader.overallAiScore}/100 (${selectedLeader.tier.labelTamil})")
      appendLine("உறுப்பினர் சேர்க்கை: ${selectedLeader.memberEnrollmentActual}/${selectedLeader.memberEnrollmentTarget} (${selectedLeader.memberEnrollmentPct}%)")
      appendLine("நலவாரிய அட்டை பதிவு: ${selectedLeader.welfareBoardActual}/${selectedLeader.welfareBoardTarget} (${selectedLeader.welfareEnrollmentPct}%)")
      appendLine()
      if (advisory != null) {
        appendLine("📌 [செயல்பாட்டு சுருக்கம்]")
        appendLine(advisory.executiveSummaryTamil)
        appendLine()
        appendLine("💪 [முக்கிய பலங்கள்]")
        advisory.strengthsReview.forEach { appendLine("• $it") }
        appendLine()
        appendLine("⚡ [உடனடி செயல் திட்டம்]")
        advisory.priorityActionPlan.forEach { appendLine("• $it") }
        appendLine()
        appendLine("👥 [உறுப்பினர் பெருக்க உத்திகள்]")
        advisory.memberGrowthStrategy.forEach { appendLine("• $it") }
        appendLine()
        appendLine("📋 [நலவாரிய பதிவு வழிகாட்டுதல்]")
        advisory.welfareSchemePush.forEach { appendLine("• $it") }
        appendLine()
        appendLine("📅 [4-வார மைல்கல் இலக்குகள்]")
        advisory.weeklyMilestones.forEach { appendLine("• $it") }
        appendLine()
        appendLine("🏛️ [மாநில தலைமைக்கான அறிக்கை]")
        appendLine(advisory.superAdminBriefingNote)
      }
    }
  }

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Leader Switcher Carousel
    item {
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text("நிர்வாகியை மாற்றவும்:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(allLeaders) { leader ->
            val isSelected = leader.id == selectedLeader.id
            Card(
              modifier = Modifier
                .clickable { onSelectLeader(leader) }
                .testTag("chip_select_leader_${leader.id}"),
              shape = RoundedCornerShape(8.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (isSelected) TnpaRedPrimary else TnpaPureWhite
              ),
              border = BorderStroke(1.dp, if (isSelected) TnpaGold else TnpaBorderGray)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Text(leader.level.iconEmoji, fontSize = 12.sp)
                Text(
                  text = leader.tamilName.split(" ").first(),
                  fontSize = 11.sp,
                  fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal,
                  color = if (isSelected) TnpaPureWhite else TnpaCharcoalDark
                )
              }
            }
          }
        }
      }
    }

    // Selected Leader Overview Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaJetBlack),
        border = BorderStroke(1.5.dp, TnpaGold)
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
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = selectedLeader.tamilName,
                color = TnpaPureWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black
              )
              Text(
                text = selectedLeader.designation,
                color = TnpaGold,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${selectedLeader.district} ${if (selectedLeader.unionOrCity.isNotBlank()) "• ${selectedLeader.unionOrCity}" else ""}",
                color = Color(0xFFCBD5E1),
                fontSize = 11.sp
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(selectedLeader.tier.colorHex))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = "${selectedLeader.overallAiScore}/100 (${selectedLeader.tier.gradeBadge})",
                color = TnpaPureWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black
              )
            }
          }

          // Action Toolbar
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = onRefreshAdvisory,
              modifier = Modifier
                .weight(1f)
                .height(38.dp)
                .testTag("btn_refresh_ai_advisory"),
              enabled = !isAiAnalyzing,
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = TnpaRedPrimary,
                contentColor = TnpaPureWhite
              )
            ) {
              if (isAiAnalyzing) {
                CircularProgressIndicator(modifier = Modifier.size(14.dp), color = TnpaPureWhite, strokeWidth = 2.dp)
                Spacer(modifier = Modifier.width(4.dp))
                Text("ஆய்வு நடக்கிறது...", fontSize = 11.sp)
              } else {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("மீண்டும் AI ஆய்வு செய்", fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
            }

            IconButton(
              onClick = { onCopyReport(fullReportText) },
              modifier = Modifier
                .size(38.dp)
                .background(Color(0xFF334155), shape = RoundedCornerShape(8.dp))
                .testTag("btn_copy_ai_report")
            ) {
              Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = TnpaGold, modifier = Modifier.size(18.dp))
            }

            IconButton(
              onClick = { onShareReport(fullReportText) },
              modifier = Modifier
                .size(38.dp)
                .background(Color(0xFF334155), shape = RoundedCornerShape(8.dp))
                .testTag("btn_share_ai_report")
            ) {
              Icon(Icons.Default.Share, contentDescription = "Share", tint = TnpaGold, modifier = Modifier.size(18.dp))
            }
          }
        }
      }
    }

    // Detailed Advisory Content
    if (advisory != null) {
      // 1. Executive Summary
      item {
        AdvisorySectionCard(
          icon = Icons.Default.Info,
          title = "📌 ஒட்டுமொத்த செயல்பாட்டு சுருக்கம்",
          accentColor = Color(0xFF2563EB)
        ) {
          Text(
            text = advisory.executiveSummaryTamil,
            fontSize = 12.sp,
            color = TnpaCharcoalDark,
            lineHeight = 18.sp
          )
        }
      }

      // 2. Strengths
      item {
        AdvisorySectionCard(
          icon = Icons.Default.CheckCircle,
          title = "💪 கண்டறியப்பட்ட முக்கிய பலங்கள்",
          accentColor = TnpaSuccessGreen
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            advisory.strengthsReview.forEach { strength ->
              BulletPointItem(text = strength, dotColor = TnpaSuccessGreen)
            }
          }
        }
      }

      // 3. Priority Action Plan
      item {
        AdvisorySectionCard(
          icon = Icons.Default.TrendingUp,
          title = "⚡ உடனடி கள செயல் திட்டம் (Priority Actions)",
          accentColor = TnpaRedPrimary
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            advisory.priorityActionPlan.forEach { action ->
              BulletPointItem(text = action, dotColor = TnpaRedPrimary)
            }
          }
        }
      }

      // 4. Member Growth Strategy
      item {
        AdvisorySectionCard(
          icon = Icons.Default.People,
          title = "👥 புதிய உறுப்பினர் சேர்க்கை பெருக்க உத்திகள்",
          accentColor = Color(0xFF9333EA)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            advisory.memberGrowthStrategy.forEach { tip ->
              BulletPointItem(text = tip, dotColor = Color(0xFF9333EA))
            }
          }
        }
      }

      // 5. Welfare Push
      item {
        AdvisorySectionCard(
          icon = Icons.Default.VolunteerActivism,
          title = "📋 நலவாரிய அட்டை பதிவு வழிகாட்டுதல்",
          accentColor = TnpaGoldDark
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            advisory.welfareSchemePush.forEach { welfare ->
              BulletPointItem(text = welfare, dotColor = TnpaGoldDark)
            }
          }
        }
      }

      // 6. 4-Week Roadmap
      item {
        AdvisorySectionCard(
          icon = Icons.Default.Speed,
          title = "📅 அடுத்த 4-வார மைல்கல் இலக்குகள்",
          accentColor = Color(0xFF0284C7)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            advisory.weeklyMilestones.forEach { milestone ->
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(6.dp))
                  .background(Color(0xFFF1F5F9))
                  .padding(8.dp)
              ) {
                Text(milestone, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = TnpaCharcoalDark)
              }
            }
          }
        }
      }

      // 7. Super Admin Briefing
      item {
        AdvisorySectionCard(
          icon = Icons.Default.Verified,
          title = "🏛️ மாநில தலைமைக்கான பிரத்யேக அறிக்கை",
          accentColor = TnpaJetBlack
        ) {
          Text(
            text = advisory.superAdminBriefingNote,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            lineHeight = 18.sp
          )
        }
      }
    }
  }
}

@Composable
private fun AdvisorySectionCard(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  accentColor: Color,
  content: @Composable () -> Unit
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = BorderStroke(1.dp, TnpaBorderGray),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Column(
      modifier = Modifier.padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(16.dp))
        Text(title, fontSize = 12.sp, fontWeight = FontWeight.Black, color = TnpaCharcoalDark)
      }
      HorizontalDivider(color = TnpaBorderGray.copy(alpha = 0.5f))
      content()
    }
  }
}

@Composable
private fun BulletPointItem(text: String, dotColor: Color) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(6.dp),
    verticalAlignment = Alignment.Top
  ) {
    Box(
      modifier = Modifier
        .padding(top = 5.dp)
        .size(6.dp)
        .clip(CircleShape)
        .background(dotColor)
    )
    Text(
      text = text,
      fontSize = 11.sp,
      color = TnpaCharcoalDark,
      lineHeight = 16.sp,
      modifier = Modifier.weight(1f)
    )
  }
}

// ============================================================================
// TAB 3: INTERACTIVE AI STRATEGY ADVISOR (CHAT)
// ============================================================================

@Composable
private fun InteractiveAiAdvisorTab(
  chatMessages: List<AiChatMessage>,
  selectedLeader: LeaderPerformanceProfile?,
  onSendMessage: (String) -> Unit
) {
  var inputText by remember { mutableStateOf("") }
  val listState = rememberLazyListState()

  LaunchedEffect(chatMessages.size) {
    if (chatMessages.isNotEmpty()) {
      listState.animateScrollToItem(chatMessages.size - 1)
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(TnpaOffWhite)
  ) {
    // Top Context Banner
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFE2E8F0))
        .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = if (selectedLeader != null) "தற்போதைய சூழல்: ${selectedLeader.tamilName} (${selectedLeader.district})" else "பொதுவான மாநில தலைமை வழிகாட்டல்",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = TnpaCharcoalDark
        )
        Text("AI Live Active", fontSize = 10.sp, color = TnpaSuccessGreen, fontWeight = FontWeight.Bold)
      }
    }

    // Chat Message List
    LazyColumn(
      state = listState,
      modifier = Modifier
        .weight(1f)
        .padding(horizontal = 12.dp),
      contentPadding = PaddingValues(vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(chatMessages, key = { it.id }) { msg ->
        ChatMessageBubble(
          message = msg,
          onChipClick = { onSendMessage(it) }
        )
      }
    }

    // Input Box & Suggested Chips
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(TnpaPureWhite)
        .padding(10.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      // Suggested prompts row
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        val quickSuggestions = listOf(
          "உறுப்பினர் சேர்க்கை உத்தி",
          "நலவாரிய அட்டை முகாம் ஏற்பாடு",
          "நிர்வாகிகள் கூட்டம் வழிகாட்டல்",
          "பின்தங்கிய ஒன்றியம் முன்னேற்றம்"
        )
        quickSuggestions.forEach { prompt ->
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(16.dp))
              .background(Color(0xFFF1F5F9))
              .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(16.dp))
              .clickable { onSendMessage(prompt) }
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Text(prompt, fontSize = 10.sp, color = TnpaCharcoalDark, fontWeight = FontWeight.Medium)
          }
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = inputText,
          onValueChange = { inputText = it },
          placeholder = { Text("நிர்வாக ஆலோசனையை கேட்கவும்...", fontSize = 12.sp) },
          modifier = Modifier
            .weight(1f)
            .height(50.dp)
            .testTag("input_ai_chat"),
          shape = RoundedCornerShape(24.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TnpaRedPrimary,
            unfocusedBorderColor = TnpaBorderGray
          ),
          singleLine = true
        )

        IconButton(
          onClick = {
            if (inputText.isNotBlank()) {
              val t = inputText
              inputText = ""
              onSendMessage(t)
            }
          },
          modifier = Modifier
            .size(46.dp)
            .background(TnpaRedPrimary, CircleShape)
            .testTag("btn_send_ai_chat")
        ) {
          Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = TnpaGold, modifier = Modifier.size(20.dp))
        }
      }
    }
  }
}

@Composable
private fun ChatMessageBubble(
  message: AiChatMessage,
  onChipClick: (String) -> Unit
) {
  val isUser = message.sender == ChatSender.USER
  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
  ) {
    Card(
      shape = RoundedCornerShape(
        topStart = 14.dp,
        topEnd = 14.dp,
        bottomStart = if (isUser) 14.dp else 2.dp,
        bottomEnd = if (isUser) 2.dp else 14.dp
      ),
      colors = CardDefaults.cardColors(
        containerColor = if (isUser) TnpaRedPrimary else TnpaPureWhite
      ),
      border = if (isUser) null else BorderStroke(1.dp, TnpaBorderGray),
      elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
      modifier = Modifier.fillMaxWidth(0.92f)
    ) {
      Column(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (isUser) "நிர்வாகி (You)" else "🤖 TNPA AI வழிகாட்டி",
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            color = if (isUser) TnpaGold else TnpaRedPrimary
          )
          Text(
            text = message.timestamp,
            fontSize = 9.sp,
            color = if (isUser) TnpaPureWhite.copy(alpha = 0.7f) else Color.Gray
          )
        }

        Text(
          text = message.textTamil,
          fontSize = 12.sp,
          color = if (isUser) TnpaPureWhite else TnpaCharcoalDark,
          lineHeight = 17.sp
        )

        // Suggestion Chips if provided by AI
        if (!isUser && message.suggestedPromptChips.isNotEmpty()) {
          HorizontalDivider(color = TnpaBorderGray.copy(alpha = 0.5f))
          Text("தொடர்புடைய ஆலோசனைகள்:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            message.suggestedPromptChips.forEach { chip ->
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(12.dp))
                  .background(Color(0xFFEFF6FF))
                  .border(1.dp, Color(0xFF93C5FD), RoundedCornerShape(12.dp))
                  .clickable { onChipClick(chip) }
                  .padding(horizontal = 8.dp, vertical = 3.dp)
              ) {
                Text(chip, fontSize = 9.sp, color = Color(0xFF1D4ED8), fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }
  }
}

// ============================================================================
// TAB 4: DISTRICT LEADERBOARD & STATE TARGETS
// ============================================================================

@Composable
private fun DistrictLeaderboardTab(
  leaders: List<LeaderPerformanceProfile>,
  dashboardSummary: com.example.model.AiMonitoringDashboardSummary,
  onSelectLeader: (LeaderPerformanceProfile) -> Unit
) {
  val districtLeaders = leaders.filter { it.level == AdminHierarchyLevel.DISTRICT }
    .sortedByDescending { it.overallAiScore }

  val unionLeaders = leaders.filter { it.level == AdminHierarchyLevel.UNION }
    .sortedByDescending { it.overallAiScore }

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // State Target Progress Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
        border = BorderStroke(1.dp, TnpaBorderGray)
      ) {
        Column(
          modifier = Modifier.padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Text(
            text = "தமிழ்நாடு மாநில ஒட்டுமொத்த இலக்கு சாதனை",
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = TnpaCharcoalDark
          )

          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("மொத்த உறுப்பினர் சேர்க்கை", fontSize = 11.sp, color = Color(0xFF64748B))
              Text(
                "${dashboardSummary.totalMembersEnrolledStateWide} / ${dashboardSummary.totalStateEnrollmentGoal} (74%)",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = TnpaRedPrimary
              )
            }
            LinearProgressIndicator(
              progress = { 0.74f },
              modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
              color = TnpaRedPrimary,
              trackColor = Color(0xFFF1F5F9)
            )
          }

          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("நலவாரிய அட்டை விநியோகம்", fontSize = 11.sp, color = Color(0xFF64748B))
              Text(
                "${dashboardSummary.totalWelfareCardsStateWide} / 12,000 (74%)",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = TnpaSuccessGreen
              )
            }
            LinearProgressIndicator(
              progress = { 0.74f },
              modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
              color = TnpaSuccessGreen,
              trackColor = Color(0xFFF1F5F9)
            )
          }
        }
      }
    }

    // District Leaderboard Header
    item {
      Text(
        text = "🏢 மாவட்ட வாரியான செயல்திறன் தரவரிசை (District Leaderboard)",
        fontSize = 13.sp,
        fontWeight = FontWeight.Black,
        color = TnpaCharcoalDark
      )
    }

    items(districtLeaders.take(8)) { leader ->
      LeaderboardRankRow(
        leader = leader,
        rank = districtLeaders.indexOf(leader) + 1,
        onClick = { onSelectLeader(leader) }
      )
    }

    // Union Leaderboard Header
    item {
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "🌾 ஒன்றிய வாரியான செயல்திறன் தரவரிசை (Union Leaderboard)",
        fontSize = 13.sp,
        fontWeight = FontWeight.Black,
        color = TnpaCharcoalDark
      )
    }

    items(unionLeaders.take(8)) { leader ->
      LeaderboardRankRow(
        leader = leader,
        rank = unionLeaders.indexOf(leader) + 1,
        onClick = { onSelectLeader(leader) }
      )
    }
  }
}

@Composable
private fun LeaderboardRankRow(
  leader: LeaderPerformanceProfile,
  rank: Int,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() },
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = BorderStroke(1.dp, TnpaBorderGray)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // Rank Badge
      Box(
        modifier = Modifier
          .size(30.dp)
          .clip(CircleShape)
          .background(
            when (rank) {
              1 -> TnpaGold
              2 -> Color(0xFF94A3B8)
              3 -> Color(0xFFB45309)
              else -> Color(0xFFE2E8F0)
            }
          ),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "#$rank",
          fontSize = 11.sp,
          fontWeight = FontWeight.Black,
          color = if (rank <= 3) TnpaPureWhite else TnpaCharcoalDark
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "${leader.tamilName} • ${leader.district.split(" ").first()}",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = TnpaCharcoalDark
        )
        Text(
          text = "${leader.designation} (${leader.memberEnrollmentActual}/${leader.memberEnrollmentTarget} சேர்க்கை)",
          fontSize = 10.sp,
          color = Color(0xFF64748B)
        )
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(Color(leader.tier.colorHex).copy(alpha = 0.15f))
          .padding(horizontal = 6.dp, vertical = 3.dp)
      ) {
        Text(
          text = "${leader.overallAiScore}/100",
          fontSize = 11.sp,
          fontWeight = FontWeight.Black,
          color = Color(leader.tier.colorHex)
        )
      }
    }
  }
}

// ============================================================================
// REUSABLE HELPER COMPONENTS
// ============================================================================

@Composable
private fun AiHeaderStatCard(
  modifier: Modifier = Modifier,
  title: String,
  value: String,
  subtext: String,
  accentColor: Color
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(8.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
  ) {
    Column(
      modifier = Modifier.padding(8.dp),
      verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
      Text(title, fontSize = 9.sp, color = Color(0xFF94A3B8))
      Text(value, fontSize = 12.sp, fontWeight = FontWeight.Black, color = accentColor, maxLines = 1)
      Text(subtext, fontSize = 8.sp, color = TnpaPureWhite.copy(alpha = 0.7f))
    }
  }
}

@Composable
private fun LevelFilterChip(
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(16.dp))
      .background(if (isSelected) TnpaRedPrimary else TnpaPureWhite)
      .border(1.dp, if (isSelected) TnpaGold else TnpaBorderGray, RoundedCornerShape(16.dp))
      .clickable { onClick() }
      .padding(horizontal = 10.dp, vertical = 5.dp)
  ) {
    Text(
      text = label,
      fontSize = 10.sp,
      fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal,
      color = if (isSelected) TnpaPureWhite else TnpaCharcoalDark
    )
  }
}

@Composable
private fun TierFilterChip(
  label: String,
  isSelected: Boolean,
  colorHex: Long,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(16.dp))
      .background(if (isSelected) Color(colorHex) else TnpaPureWhite)
      .border(1.dp, if (isSelected) TnpaPureWhite else Color(colorHex).copy(alpha = 0.4f), RoundedCornerShape(16.dp))
      .clickable { onClick() }
      .padding(horizontal = 10.dp, vertical = 4.dp)
  ) {
    Text(
      text = label,
      fontSize = 10.sp,
      fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal,
      color = if (isSelected) TnpaPureWhite else Color(colorHex)
    )
  }
}
