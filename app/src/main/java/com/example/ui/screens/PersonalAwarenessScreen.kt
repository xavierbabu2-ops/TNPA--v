package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AwarenessVoiceResponse
import com.example.data.PersonalAwarenessEngine
import com.example.model.*
import com.example.ui.theme.*
import kotlinx.coroutines.launch

/**
 * PERSONAL COMMUNICATION, AWARENESS, NAVIGATION & PROACTIVE ASSISTANT
 * 
 * Comprehensive Sovereign System unifying:
 * 1. Personal Communication Hub (WhatsApp, Messenger, Instagram, SMS, Email, Phone Calls)
 * 2. Incoming Message Prioritization & Intent Understanding (Meeting, Task, Reply, Spam)
 * 3. Call Screening & Anti-Spam (VIP, Known, Repeat caller, Unknown, Robocall)
 * 4. Location Awareness & Owner Privacy Zones (Home, Work, Frequent, Do-Not-Store)
 * 5. Multi-Route Navigation & Proactive Traffic Departure Intelligence
 * 6. Daily Situation Synthesis & Proactive Personal Assistance
 * 7. Strict Owner Voice Verification & Zero-Leakage Policy
 */
@Composable
fun PersonalAwarenessScreen(
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val coroutineScope = rememberCoroutineScope()

  val tabs = listOf(
    "சூழ்நிலை & விழிப்புணர்வு" to Icons.Default.Explore,
    "தகவல் தொடர்பு மையம்" to Icons.AutoMirrored.Filled.Chat,
    "அழைப்பு ஸ்கிரீனிங்" to Icons.Default.Call,
    "வழிசெலுத்தல் & இருப்பிடம்" to Icons.Default.Navigation,
    "அடுத்த தலைமுறை நுண்ணறிவு" to Icons.Default.Psychology,
    "சோஷியல் மீடியா & ட்ரெண்ட்" to Icons.Default.Share,
    "தணிக்கை & தனியுரிமை" to Icons.Default.Security
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(SovereignBackground)
  ) {
    // 1. Module Top Banner with Sovereign Principles
    Surface(
      color = SovereignSurface,
      border = BorderStroke(1.dp, SovereignBorder),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
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
                .background(SovereignCyanDark),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Assistant,
                contentDescription = null,
                tint = SovereignCyan,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "தனிநபர் விழிப்புணர்வு & தகவல் தொடர்பு தளம்",
                color = SovereignTextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "Communication • Awareness • Navigation • Proactive Assistant",
                color = SovereignCyan,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }

          // Sovereign Privacy Badge
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = SovereignEmerald.copy(alpha = 0.15f),
            border = BorderStroke(1.dp, SovereignEmerald.copy(alpha = 0.4f))
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.VerifiedUser,
                contentDescription = null,
                tint = SovereignEmerald,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Official APIs & OS Only",
                color = SovereignEmerald,
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }

    // 2. Navigation Tabs
    ScrollableTabRow(
      selectedTabIndex = selectedTab,
      containerColor = SovereignSurfaceElevated,
      contentColor = SovereignCyan,
      edgePadding = 8.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      tabs.forEachIndexed { index, (label, icon) ->
        val isSelected = selectedTab == index
        Tab(
          selected = isSelected,
          onClick = { selectedTab = index },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) SovereignCyan else SovereignTextMuted,
                modifier = Modifier.size(15.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = label,
                fontSize = 11.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) SovereignCyan else SovereignTextSecondary
              )
            }
          },
          modifier = Modifier.testTag("awareness_tab_$index")
        )
      }
    }

    // 3. Tab Contents
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
      when (selectedTab) {
        0 -> DaySituationAndProactiveTab()
        1 -> UnifiedCommunicationHubTab()
        2 -> CallIntelligenceTab()
        3 -> LocationAndNavigationTab()
        4 -> NextLevelIntelligenceHub()
        5 -> SocialContentStudioScreen()
        6 -> PrivacyAndAuditLedgerTab()
      }
    }
  }
}

// ============================================================================
// TAB 1: DAY SITUATION & PROACTIVE ASSISTANT LOOP
// ============================================================================

@Composable
private fun DaySituationAndProactiveTab() {
  val daySituation by PersonalAwarenessEngine.daySituation.collectAsState()
  val proactiveLevel by PersonalAwarenessEngine.proactiveLevel.collectAsState()
  val locationContext by PersonalAwarenessEngine.locationContext.collectAsState()

  var testVoiceQuery by remember { mutableStateOf("எனக்கு என்ன message வந்திருக்கு?") }
  var isOwnerVerified by remember { mutableStateOf(true) }
  var lastVoiceResponse by remember { mutableStateOf<AwarenessVoiceResponse?>(null) }

  val presetVoiceQueries = listOf(
    "🎙️ எனக்கு என்ன message வந்திருக்கு?",
    "🎙️ இன்று வந்த messages-ல முக்கியமானது என்ன?",
    "🎙️ நான் இப்போது எங்கே இருக்கிறேன்?",
    "🎙️ நான் எங்கே போகணும்?",
    "🎙️ அங்கே எப்படி போகணும்?",
    "🎙️ யாரு கால் பண்றாங்க?",
    "🎙️ முரளி எனக்கு கடைசியாக எப்போது message பண்ணினார்?",
    "🎙️ அவருக்கு நான் busy-ஆ இருக்கேன் என்று சொல்லு"
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Proactive Situation Card ("என்னுடைய current situation என்ன?")
    Card(
      colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
      shape = RoundedCornerShape(16.dp),
      border = BorderStroke(1.dp, SovereignCyan.copy(alpha = 0.4f)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Radar, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "தற்போதைய முழுமையான சூழ்நிலை (Day Situation)",
              color = SovereignCyan,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
          BadgePill(daySituation.currentTimestamp, SovereignTextMuted)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Situation Grid
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          SituationFactBox(
            title = "நான் எங்கே இருக்கிறேன்?",
            value = daySituation.locationSummaryTa,
            icon = Icons.Default.LocationOn,
            tint = SovereignEmerald,
            modifier = Modifier.weight(1f)
          )
          SituationFactBox(
            title = "அடுத்த சந்திப்பு / இலக்கு",
            value = "${daySituation.nextEventTime} - ${daySituation.nextScheduledEventTa}",
            icon = Icons.Default.Event,
            tint = SovereignGold,
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Messages & Calls overview
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          SituationFactBox(
            title = "முக்கிய செய்திகள் (Messages)",
            value = "${daySituation.totalMessagesToday} செய்திகள் • ${daySituation.criticalMessagesToday} முக்கியம் • ${daySituation.pendingReplyCountToday} பதில் தேவை",
            icon = Icons.Default.ChatBubble,
            tint = SovereignCyan,
            modifier = Modifier.weight(1f)
          )
          SituationFactBox(
            title = "அழைப்பு எச்சரிக்கைகள் (Calls)",
            value = "1 விஐபி அழைப்பு (ரவி) • 1 ரோபோகால் ஸ்பேம் பிளாக்",
            icon = Icons.AutoMirrored.Filled.PhoneCallback,
            tint = SovereignPurple,
            modifier = Modifier.weight(1f)
          )
        }

        // Proactive Traffic & Departure Warning
        daySituation.proactiveTravelAlertTa?.let { alert ->
          Spacer(modifier = Modifier.height(10.dp))
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = SovereignGold.copy(alpha = 0.12f),
            border = BorderStroke(1.dp, SovereignGold.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = SovereignGold, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text("முன்முயற்சி பயண விழிப்பூட்டல் (Proactive Travel Alert):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignGold)
                Text(alert, fontSize = 11.sp, color = SovereignTextPrimary)
              }
            }
          }
        }
      }
    }

    // Proactive Assistance Level Control
    Card(
      colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
      shape = RoundedCornerShape(14.dp),
      border = BorderStroke(1.dp, SovereignBorder)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "முன்முயற்சி விழிப்புணர்வு முறை (Proactive Assistance):",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = SovereignTextPrimary
          )
          BadgePill(proactiveLevel.labelEn, SovereignCyan)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          ProactiveAssistanceLevel.values().forEach { level ->
            val isSelected = proactiveLevel == level
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (isSelected) SovereignCyanDark else SovereignSurface,
              border = BorderStroke(1.dp, if (isSelected) SovereignCyan else SovereignBorder),
              modifier = Modifier.clickable { PersonalAwarenessEngine.setProactiveLevel(level) }
            ) {
              Text(
                text = level.labelTa,
                fontSize = 10.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) SovereignCyan else SovereignTextSecondary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
              )
            }
          }
        }
      }
    }

    // Interactive Tamil Voice Query & Awareness Testing Dock
    Card(
      colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
      shape = RoundedCornerShape(16.dp),
      border = BorderStroke(1.dp, SovereignBorder)
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Mic, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("குரல் வழி விழிப்புணர்வு வினவல் (Natural Voice Ask):", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
          }

          // Owner vs Non-Owner Simulation Toggle
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = if (isOwnerVerified) "உரிமையாளர் குரல்" else "அறியப்படாத குரல்",
              fontSize = 10.sp,
              color = if (isOwnerVerified) SovereignEmerald else SovereignRose,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(4.dp))
            Switch(
              checked = isOwnerVerified,
              onCheckedChange = { isOwnerVerified = it },
              colors = SwitchDefaults.colors(
                checkedThumbColor = SovereignEmerald,
                checkedTrackColor = SovereignEmerald.copy(alpha = 0.3f),
                uncheckedThumbColor = SovereignRose,
                uncheckedTrackColor = SovereignRose.copy(alpha = 0.3f)
              ),
              modifier = Modifier.height(24.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = testVoiceQuery,
          onValueChange = { testVoiceQuery = it },
          modifier = Modifier.fillMaxWidth(),
          textStyle = LocalTextStyle.current.copy(color = SovereignTextPrimary, fontSize = 13.sp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SovereignCyan,
            unfocusedBorderColor = SovereignBorder,
            cursorColor = SovereignCyan
          ),
          placeholder = { Text("தமிழில் கேட்கவும் (உதா: எனக்கு என்ன message வந்திருக்கு?)...", color = SovereignTextMuted, fontSize = 11.5.sp) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Preset Prompt Chips
        Text("மாதிரி விழிப்புணர்வுக் கேள்விகள் (Preset Prompts):", fontSize = 10.5.sp, color = SovereignTextMuted)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          presetVoiceQueries.forEach { preset ->
            val clean = preset.removePrefix("🎙️ ")
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = SovereignSurface,
              border = BorderStroke(0.5.dp, SovereignBorder),
              modifier = Modifier.clickable {
                testVoiceQuery = clean
                lastVoiceResponse = PersonalAwarenessEngine.processVoiceAwarenessQuery(clean, isOwnerVerified)
              }
            ) {
              Text(
                text = preset,
                fontSize = 10.sp,
                color = SovereignTextSecondary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
          onClick = {
            lastVoiceResponse = PersonalAwarenessEngine.processVoiceAwarenessQuery(testVoiceQuery, isOwnerVerified)
          },
          modifier = Modifier.fillMaxWidth(),
          colors = ButtonDefaults.buttonColors(containerColor = SovereignCyanDark),
          shape = RoundedCornerShape(10.dp)
        ) {
          Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = SovereignCyan)
          Spacer(modifier = Modifier.width(6.dp))
          Text("இயற்கையான தமிழில் பதில் பெறு (Ask Personal Assistant)", color = SovereignCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }

        // Voice Response Card
        lastVoiceResponse?.let { resp ->
          Spacer(modifier = Modifier.height(12.dp))
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (resp.isSuccess) SovereignEmerald.copy(alpha = 0.1f) else SovereignRose.copy(alpha = 0.12f),
            border = BorderStroke(1.dp, if (resp.isSuccess) SovereignEmerald else SovereignRose),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = if (resp.isSuccess) Icons.Default.RecordVoiceOver else Icons.Default.Block,
                    contentDescription = null,
                    tint = if (resp.isSuccess) SovereignEmerald else SovereignRose,
                    modifier = Modifier.size(18.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = resp.category,
                    color = if (resp.isSuccess) SovereignEmerald else SovereignRose,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
                BadgePill(if (resp.isSuccess) "Verified Owner" else "Unauthorized / Blocked", if (resp.isSuccess) SovereignEmerald else SovereignRose)
              }

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = "«“${resp.spokenTamilText}”»",
                color = SovereignTextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 19.sp
              )

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = "Audit Hash: ${resp.provenanceHash.take(28)}...",
                color = SovereignTextMuted,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun SituationFactBox(
  title: String,
  value: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  tint: Color,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(10.dp),
    color = SovereignSurface,
    border = BorderStroke(0.5.dp, SovereignBorder),
    modifier = modifier
  ) {
    Column(modifier = Modifier.padding(10.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(title, fontSize = 10.sp, color = SovereignTextMuted, fontWeight = FontWeight.Medium)
      }
      Spacer(modifier = Modifier.height(4.dp))
      Text(value, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
    }
  }
}

// ============================================================================
// TAB 2: UNIFIED COMMUNICATION HUB (MESSAGES INTELLIGENCE)
// ============================================================================

@Composable
private fun UnifiedCommunicationHubTab() {
  val messages by PersonalAwarenessEngine.unifiedMessages.collectAsState()
  val selectedPlatform by PersonalAwarenessEngine.selectedPlatformFilter.collectAsState()
  val selectedPriority by PersonalAwarenessEngine.selectedPriorityFilter.collectAsState()

  val filteredMessages = messages.filter { msg ->
    (selectedPlatform == null || msg.platform == selectedPlatform) &&
    (selectedPriority == null || msg.priority == selectedPriority)
  }

  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    // Platform Filters Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (selectedPlatform == null) SovereignCyanDark else SovereignSurfaceElevated,
        border = BorderStroke(1.dp, if (selectedPlatform == null) SovereignCyan else SovereignBorder),
        modifier = Modifier.clickable { PersonalAwarenessEngine.setPlatformFilter(null) }
      ) {
        Text(
          text = "அனைத்து சேவைகளும் (All)",
          fontSize = 11.sp,
          fontWeight = if (selectedPlatform == null) FontWeight.Bold else FontWeight.Normal,
          color = if (selectedPlatform == null) SovereignCyan else SovereignTextSecondary,
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
      }

      CommunicationPlatform.values().forEach { platform ->
        val isSel = selectedPlatform == platform
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = if (isSel) Color(platform.brandColorHex).copy(alpha = 0.25f) else SovereignSurfaceElevated,
          border = BorderStroke(1.dp, if (isSel) Color(platform.brandColorHex) else SovereignBorder),
          modifier = Modifier.clickable { PersonalAwarenessEngine.setPlatformFilter(platform) }
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(Color(platform.brandColorHex))
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
              text = platform.labelEn,
              fontSize = 11.sp,
              fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
              color = if (isSel) SovereignTextPrimary else SovereignTextSecondary
            )
          }
        }
      }
    }

    // Priority Filters Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (selectedPriority == null) SovereignCyanDark else SovereignSurface,
        border = BorderStroke(1.dp, if (selectedPriority == null) SovereignCyan else SovereignBorder),
        modifier = Modifier.clickable { PersonalAwarenessEngine.setPriorityFilter(null) }
      ) {
        Text(
          text = "அனைத்து முன்னுரிமைகளும்",
          fontSize = 10.5.sp,
          color = if (selectedPriority == null) SovereignCyan else SovereignTextMuted,
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
      }

      MessagePriority.values().forEach { prio ->
        val isSel = selectedPriority == prio
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = if (isSel) Color(prio.badgeColorHex).copy(alpha = 0.25f) else SovereignSurface,
          border = BorderStroke(1.dp, if (isSel) Color(prio.badgeColorHex) else SovereignBorder),
          modifier = Modifier.clickable { PersonalAwarenessEngine.setPriorityFilter(prio) }
        ) {
          Text(
            text = prio.labelEn,
            fontSize = 10.5.sp,
            color = if (isSel) Color(prio.badgeColorHex) else SovereignTextMuted,
            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }
    }

    // Messages List
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(filteredMessages, key = { it.id }) { message ->
        UnifiedMessageCard(message = message)
      }
    }
  }
}

@Composable
private fun UnifiedMessageCard(message: UnifiedMessage) {
  var showRepliesModal by remember { mutableStateOf(false) }

  Card(
    colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
    shape = RoundedCornerShape(14.dp),
    border = BorderStroke(
      1.dp,
      if (message.isSpamOrSuspicious) SovereignRose
      else if (message.priority == MessagePriority.CRITICAL) SovereignRose.copy(alpha = 0.6f)
      else SovereignBorder
    ),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      // 1. Header with Platform & Priority
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
              .background(Color(message.platform.brandColorHex))
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = message.platform.labelEn,
            color = Color(message.platform.brandColorHex),
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "• ${message.senderName}",
            color = SovereignTextPrimary,
            fontSize = 12.5.sp,
            fontWeight = FontWeight.Bold
          )
        }

        BadgePill(message.priority.labelEn, Color(message.priority.badgeColorHex))
      }

      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "நேரம்: ${message.timestamp} • ${message.senderHandle}",
        fontSize = 10.sp,
        color = SovereignTextMuted
      )

      Spacer(modifier = Modifier.height(6.dp))

      // Raw Message Content
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = SovereignSurface,
        border = BorderStroke(0.5.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "\"${message.rawContent}\"",
          fontSize = 12.sp,
          color = SovereignTextPrimary,
          modifier = Modifier.padding(8.dp)
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Normalized Tamil Summary & Intent
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "AI சுருக்கம்: ${message.normalizedTamilSummary}",
          fontSize = 11.5.sp,
          color = SovereignCyan,
          fontWeight = FontWeight.Medium
        )
      }

      // Spam / Phishing Evidence Warning
      if (message.isSpamOrSuspicious && message.spamRiskEvidence != null) {
        Spacer(modifier = Modifier.height(6.dp))
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = SovereignRose.copy(alpha = 0.15f),
          border = BorderStroke(1.dp, SovereignRose),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = SovereignRose, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "பாதுகாப்பு எச்சரிக்கை: ${message.spamRiskEvidence}",
              color = SovereignRose,
              fontSize = 10.5.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Action Buttons (Reply, Task, Calendar)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        if (message.suggestedTamilReplies.isNotEmpty()) {
          Button(
            onClick = { showRepliesModal = true },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = SovereignCyanDark),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Icon(Icons.AutoMirrored.Filled.Reply, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("பதில் அனுப்பு", color = SovereignCyan, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
          }
        }

        if (message.intentType == MessageIntentType.TASK_ACTION_REQUEST || message.linkedTaskDraft != null) {
          Button(
            onClick = { PersonalAwarenessEngine.convertMessageToTask(message.id) },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = SovereignPurple.copy(alpha = 0.2f)),
            border = BorderStroke(1.dp, SovereignPurple),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Icon(Icons.Default.CheckCircleOutline, contentDescription = null, tint = SovereignPurple, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(if (message.linkedTaskDraft?.isCreated == true) "Task உருவானது" else "Task உருவாக்கு", color = SovereignPurple, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
          }
        }

        if (message.intentType == MessageIntentType.MEETING_REQUEST || message.linkedCalendarDraft != null) {
          Button(
            onClick = { PersonalAwarenessEngine.convertMessageToCalendar(message.id) },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = SovereignGold.copy(alpha = 0.2f)),
            border = BorderStroke(1.dp, SovereignGold),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Icon(Icons.Default.CalendarToday, contentDescription = null, tint = SovereignGold, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(if (message.linkedCalendarDraft?.isApproved == true) "Calendar சேர்க்கப்பட்டது" else "Calendar சேர்", color = SovereignGold, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
          }
        }
      }

      // Quick Reply Options Dialog
      if (showRepliesModal) {
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = SovereignSurface,
          border = BorderStroke(1.dp, SovereignCyan.copy(alpha = 0.4f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(10.dp)) {
            Text("தயாரான தமிழ் பதில்கள் (Draft Replies):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
            Spacer(modifier = Modifier.height(6.dp))

            message.suggestedTamilReplies.forEach { reply ->
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = SovereignSurfaceElevated,
                border = BorderStroke(0.5.dp, SovereignBorder),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 3.dp)
                  .clickable {
                    PersonalAwarenessEngine.sendDraftReply(message.id, reply)
                    showRepliesModal = false
                  }
              ) {
                Text(
                  text = "🗣️ \"$reply\"",
                  fontSize = 11.sp,
                  color = SovereignTextPrimary,
                  modifier = Modifier.padding(8.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text("Tanglish Replies:", fontSize = 10.sp, color = SovereignTextMuted)
            message.suggestedTanglishReplies.forEach { tanglish ->
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = SovereignSurfaceElevated,
                border = BorderStroke(0.5.dp, SovereignBorder),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 2.dp)
                  .clickable {
                    PersonalAwarenessEngine.sendDraftReply(message.id, tanglish)
                    showRepliesModal = false
                  }
              ) {
                Text(
                  text = "💬 \"$tanglish\"",
                  fontSize = 10.5.sp,
                  color = SovereignTextSecondary,
                  modifier = Modifier.padding(6.dp)
                )
              }
            }
          }
        }
      }
    }
  }
}

// ============================================================================
// TAB 3: CALL INTELLIGENCE & SCREENING
// ============================================================================

@Composable
private fun CallIntelligenceTab() {
  val callRecords by PersonalAwarenessEngine.callRecords.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Call Screening Philosophy Banner
    Surface(
      shape = RoundedCornerShape(14.dp),
      color = SovereignSurfaceElevated,
      border = BorderStroke(1.dp, SovereignCyan.copy(alpha = 0.4f)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.PhoneInTalk, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("அழைப்பு நுண்ணறிவு & ஸ்கிரீனிங் கொள்கை", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          "\"தொடர்பாளர் பெயர் முகவரிப் புத்தகத்தில் இருந்தால் மட்டுமே பெயரைச் சொல்லும்; அறியப்படாத எண்களுக்கு ஊகித்து பெயர் உருவாக்காது. பலமுறை அழைத்தவர் மற்றும் ஸ்பேம் ரோபோகால்கள் தனித்தனியாக வகைப்படுத்தப்படும்.\"",
          fontSize = 11.sp,
          color = SovereignTextSecondary
        )
      }
    }

    // Call Records List
    callRecords.forEach { call ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
          1.dp,
          if (call.callCategory == CallCategory.SUSPECTED_SPAM_SCAM) SovereignRose
          else if (call.callCategory == CallCategory.KNOWN_VIP) SovereignEmerald
          else SovereignBorder
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(Color(call.callCategory.colorHex).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = if (call.callCategory == CallCategory.SUSPECTED_SPAM_SCAM) Icons.Default.Block else Icons.Default.Phone,
                  contentDescription = null,
                  tint = Color(call.callCategory.colorHex),
                  modifier = Modifier.size(18.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = call.callerVerifiedName ?: "அறியப்படாத எண் (Unknown Number)",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (call.callerVerifiedName != null) SovereignTextPrimary else SovereignTextSecondary
                )
                Text(
                  text = "${call.callerNumber} • ${call.timestamp}",
                  fontSize = 10.sp,
                  color = SovereignTextMuted
                )
              }
            }

            BadgePill(call.callCategory.labelEn, Color(call.callCategory.colorHex))
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Screening Insight
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = SovereignSurface,
            border = BorderStroke(0.5.dp, SovereignBorder),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(8.dp)) {
              Text(
                text = "ஸ்கிரீனிங் பகுப்பாய்வு: ${call.screeningInsightTa}",
                fontSize = 11.sp,
                color = SovereignTextPrimary
              )
              if (call.repeatedCallsCountToday > 1) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "⚠️ இன்று ${call.repeatedCallsCountToday}-வது முறையாக அழைக்கிறார்.",
                  fontSize = 10.5.sp,
                  color = SovereignGold,
                  fontWeight = FontWeight.Bold
                )
              }
              if (call.spamEvidenceReasonTa != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "🚨 ஸ்பேம் ஆதாரம் (${(call.spamConfidenceScore * 100).toInt()}%): ${call.spamEvidenceReasonTa}",
                  fontSize = 10.5.sp,
                  color = SovereignRose,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "பரிந்துரை: ${call.recommendedActionTa}",
            fontSize = 11.sp,
            color = SovereignCyan,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }
  }
}

// ============================================================================
// TAB 4: LOCATION & MULTI-ROUTE NAVIGATION ASSISTANT
// ============================================================================

@Composable
private fun LocationAndNavigationTab() {
  val locContext by PersonalAwarenessEngine.locationContext.collectAsState()
  val navPlan by PersonalAwarenessEngine.activeNavigation.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Current Location & Privacy Zone Box
    Card(
      colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
      shape = RoundedCornerShape(16.dp),
      border = BorderStroke(1.dp, SovereignCyan.copy(alpha = 0.4f)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.MyLocation, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("தற்போதைய இருப்பிடம் (Location Context):", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
          }
          BadgePill(locContext.placeCategory.labelEn, SovereignEmerald)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "${locContext.currentPlaceNameTa}, ${locContext.currentAreaNameTa}",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = SovereignTextPrimary
        )

        Text(
          text = "Coordinates: ${locContext.approximateCoordinates.first}, ${locContext.approximateCoordinates.second} • Updated: ${locContext.lastUpdatedTime}",
          fontSize = 10.sp,
          color = SovereignTextMuted,
          fontFamily = FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "அருகிலுள்ள இடங்கள்: ${locContext.nearbyLandmarksTa.joinToString(" • ")}",
          fontSize = 11.sp,
          color = SovereignTextSecondary
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Privacy Zones Selector
        Text("இருப்பிட தனியுரிமை மண்டலம் (Location Privacy Zone):", fontSize = 10.5.sp, color = SovereignTextMuted)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          LocationPrivacyZone.values().forEach { zone ->
            val isSel = locContext.privacyZone == zone
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (isSel) SovereignCyanDark else SovereignSurface,
              border = BorderStroke(1.dp, if (isSel) SovereignCyan else SovereignBorder),
              modifier = Modifier.clickable { PersonalAwarenessEngine.setLocationPrivacyZone(zone) }
            ) {
              Text(
                text = zone.labelTa,
                fontSize = 10.sp,
                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                color = if (isSel) SovereignCyan else SovereignTextSecondary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
              )
            }
          }
        }
      }
    }

    // Proactive Departure Alert
    Surface(
      shape = RoundedCornerShape(14.dp),
      color = SovereignGold.copy(alpha = 0.15f),
      border = BorderStroke(1.dp, SovereignGold),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(Icons.Default.AccessTime, contentDescription = null, tint = SovereignGold, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text("முன்முயற்சி புறப்பாடு வழிகாட்டல் (Proactive Departure):", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = SovereignGold)
          Text(navPlan.proactiveDepartureAlertTa, fontSize = 11.5.sp, color = SovereignTextPrimary)
        }
      }
    }

    // Multi-Route Intelligence Comparison Box
    Card(
      colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
      shape = RoundedCornerShape(16.dp),
      border = BorderStroke(1.dp, SovereignBorder)
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.AutoMirrored.Filled.AltRoute, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("சிறந்த வழித்தட ஒப்பீடு (Multi-Route Intelligence):", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
          }
          Text("${navPlan.originPlaceTa} ➔ ${navPlan.destinationPlaceTa}", fontSize = 10.sp, color = SovereignTextMuted)
        }

        Spacer(modifier = Modifier.height(10.dp))

        navPlan.routes.forEach { route ->
          val isSelected = navPlan.selectedRouteId == route.routeId
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isSelected) SovereignCyanDark else SovereignSurface,
            border = BorderStroke(1.5.dp, if (isSelected) SovereignCyan else SovereignBorder),
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp)
              .clickable { PersonalAwarenessEngine.selectRoute(route.routeId) }
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = when (route.travelMode) {
                      TravelMode.TWO_WHEELER -> Icons.Default.TwoWheeler
                      TravelMode.WALKING -> Icons.AutoMirrored.Filled.DirectionsWalk
                      TravelMode.PUBLIC_TRANSIT -> Icons.Default.DirectionsBus
                      else -> Icons.Default.DirectionsCar
                    },
                    contentDescription = null,
                    tint = if (isSelected) SovereignCyan else SovereignTextSecondary,
                    modifier = Modifier.size(18.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = route.routeTitleTa,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) SovereignTextPrimary else SovereignTextSecondary
                  )
                }

                BadgePill(
                  text = "${route.estimatedMinutes} நிமிடங்கள் (${route.distanceKm} km)",
                  color = if (route.trafficLevel == TrafficLevel.CLEAR) SovereignEmerald else if (route.trafficLevel == TrafficLevel.MODERATE) SovereignGold else SovereignRose
                )
              }

              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "ஒப்பீட்டு காரணம்: ${route.comparisonRationaleTa}",
                fontSize = 11.sp,
                color = if (isSelected) SovereignCyan else SovereignTextMuted,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
              )
            }
          }
        }
      }
    }
  }
}

// ============================================================================
// TAB 5: PRIVACY CONTROLS & AUDITABLE PROVENANCE LEDGER
// ============================================================================

@Composable
private fun PrivacyAndAuditLedgerTab() {
  val auditLogs by PersonalAwarenessEngine.auditLedger.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Privacy Principles Box
    Card(
      colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
      shape = RoundedCornerShape(14.dp),
      border = BorderStroke(1.dp, SovereignEmerald.copy(alpha = 0.4f))
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Lock, contentDescription = null, tint = SovereignEmerald, modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("தன்னாட்சி தனியுரிமை விதிகள் (Sovereign Privacy Architecture):", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignEmerald)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text("1. பாதுகாப்பற்ற Password சேமிப்பு இல்லை: அதிகாரப்பூர்வ OS / OAuth2 அனுமதி மட்டுமே.", fontSize = 11.sp, color = SovereignTextSecondary)
        Text("2. Zero Identity Fabrication: தொடர்பாளர் பெயர் உறுதியாகத் தெரியாவிடில் AI ஊகிக்காது.", fontSize = 11.sp, color = SovereignTextSecondary)
        Text("3. உரிமையாளர் குரல் அனுமதி கட்டாயம்: விருந்தினர்/பிற நபருக்கு தனிப்பட்ட செய்திகள் பூட்டப்படும்.", fontSize = 11.sp, color = SovereignTextSecondary)
        Text("4. கிரிப்டோகிராஃபிக் தணிக்கை: ஒவ்வொரு தகவலும் SHA-256 மூலம் கையொப்பமிடப்படும்.", fontSize = 11.sp, color = SovereignTextSecondary)
      }
    }

    // Audit Logs
    Text(
      text = "தகவல் தொடர்பு & விழிப்புணர்வு தணிக்கைப் பதிவேடு (Audit Ledger):",
      fontSize = 12.5.sp,
      fontWeight = FontWeight.Bold,
      color = SovereignCyan
    )

    auditLogs.forEach { log ->
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = SovereignSurfaceElevated,
        border = BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = log.requestedCommandTa,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = SovereignTextPrimary
            )
            BadgePill(log.timestamp, SovereignEmerald)
          }

          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "பேச்சாளர்: ${log.verifiedSpeaker}",
            fontSize = 10.5.sp,
            color = SovereignCyan
          )
          Text(
            text = "செயல்பாடு: ${log.platformActionExecuted} • கொள்கை: ${log.authorizationPolicyAppliedTa}",
            fontSize = 10.sp,
            color = SovereignTextSecondary
          )
          Text(
            text = "நிலை: ${log.executionStatusTa}",
            fontSize = 10.sp,
            color = SovereignEmerald
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "SHA-256 Provenance: ${log.cryptographicProvenanceHash}",
            fontSize = 8.5.sp,
            color = SovereignTextMuted,
            fontFamily = FontFamily.Monospace
          )
        }
      }
    }
  }
}

@Composable
private fun BadgePill(text: String, color: Color) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(6.dp))
      .background(color.copy(alpha = 0.18f))
      .padding(horizontal = 7.dp, vertical = 2.5.dp)
  ) {
    Text(text = text, color = color, fontSize = 9.5.sp, fontWeight = FontWeight.Bold)
  }
}
