package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SovereignEngine
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun SovereignControlScreen() {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabs = listOf(
    "⚙️ மாதிரி மேலாண்மை" to Icons.Default.Dns,
    "🧠 5-அடுக்கு நினைவகம்" to Icons.Default.Psychology,
    "🛡️ பாதுகாப்பு மேட்ரிக்ஸ்" to Icons.Default.AdminPanelSettings,
    "📜 AI அரசியல் சாசனம்" to Icons.Default.Gavel,
    "↩️ செயல்கள் & Rollback" to Icons.Default.Restore,
    "🧾 தணிக்கை (Audit Trail)" to Icons.Default.ReceiptLong,
    "📱 சாதன தொடர்ச்சி (Continuity)" to Icons.Default.Devices
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(SovereignBackground)
  ) {
    ScrollableTabRow(
      selectedTabIndex = selectedTab,
      containerColor = SovereignSurface,
      contentColor = SovereignCyan,
      edgePadding = 12.dp,
      divider = {}
    ) {
      tabs.forEachIndexed { index, (title, icon) ->
        Tab(
          selected = selectedTab == index,
          onClick = { selectedTab = index },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (selectedTab == index) SovereignCyan else SovereignTextMuted,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = title,
                color = if (selectedTab == index) SovereignTextPrimary else SovereignTextSecondary,
                fontSize = 12.sp,
                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
              )
            }
          }
        )
      }
    }

    Box(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
    ) {
      when (selectedTab) {
        0 -> ModelAgnosticSubScreen()
        1 -> StructuredMemorySubScreen()
        2 -> SafetyKernelSubScreen()
        3 -> ConstitutionSubScreen()
        4 -> ReversibleActionsSubScreen()
        5 -> AuditTrailSubScreen()
        6 -> DeviceContinuitySubScreen()
      }
    }
  }
}

// -------------------------------------------------------------
// 1. MODEL-AGNOSTIC ADAPTER MANAGER
// -------------------------------------------------------------
@Composable
fun ModelAgnosticSubScreen() {
  val currentProvider by SovereignEngine.providerIndependence.collectAsState()
  val justDoItMode by SovereignEngine.justDoItModeEnabled.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignCyan),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Dns, contentDescription = null, tint = SovereignCyan)
            Spacer(modifier = Modifier.width(8.dp))
            Text("⚙️ மாதிரி சார்பற்ற கட்டமைப்பு (Model-Agnostic)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          }
          Text(text = "எந்தவொரு ஒற்றை AI வழங்குநருக்கும் அடிபணியாமல், தேவைக்கேற்ப Local / Hybrid / Cloud மாதிரிகளை சுலபமாக மாற்றிக் கொள்ளலாம்.", fontSize = 11.sp, color = SovereignTextSecondary)
        }
      }
    }

    item {
      // Just Do It Mode Switch
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, if (justDoItMode) SovereignEmerald else SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(text = "\"Just Do It\" Mode (தன்னாட்சி இயக்கம்)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Text(text = "அங்கீகரிக்கப்பட்ட குறைந்த ஆபத்து பணிகளை உரிமையாளர் தலையீடு இன்றி உடனே முடிக்கும் முறை.", fontSize = 11.sp, color = SovereignTextSecondary)
          }
          Switch(
            checked = justDoItMode,
            onCheckedChange = { SovereignEngine.setJustDoItMode(it) },
            colors = SwitchDefaults.colors(checkedThumbColor = SovereignEmerald, checkedTrackColor = SovereignEmeraldDark)
          )
        }
      }
    }

    item {
      Text(text = "AI இயங்கும் தளத்தைத் தேர்ந்தெடுக்கவும்:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignSky)
    }

    items(ProviderIndependence.values()) { provider ->
      val isSelected = currentProvider == provider
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) SovereignCyanDark else SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) SovereignCyan else SovereignBorder),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { SovereignEngine.setProviderIndependence(provider) }
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(text = provider.titleTa, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Text(text = provider.titleEn, fontSize = 11.sp, color = SovereignTextSecondary)
          }
          if (isSelected) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(20.dp))
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 2. 5-LAYER STRUCTURED MEMORY
// -------------------------------------------------------------
@Composable
fun StructuredMemorySubScreen() {
  val memories by SovereignEngine.memoryRecords.collectAsState()
  var selectedLayer by remember { mutableStateOf<MemoryLayerType?>(null) }
  var searchQuery by remember { mutableStateOf("") }

  val filteredMemories = memories.filter { item ->
    (selectedLayer == null || item.layer == selectedLayer) &&
      (searchQuery.isBlank() || item.contentTamil.contains(searchQuery, ignoreCase = true) || item.titleTa.contains(searchQuery, ignoreCase = true))
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignPurple),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = SovereignPurple)
            Spacer(modifier = Modifier.width(8.dp))
            Text("🧠 5-அடுக்கு கட்டமைப்பு நினைவகம் (5-Layer Memory)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          }
          Text(text = "செயல்பாட்டு, நிகழ்வு, கருத்து, நடைமுறை மற்றும் திட்ட நினைவகங்களின் மறைகுறியாக்கப்பட்ட (AES-256) தன்னாட்சி தொகுப்பு.", fontSize = 11.sp, color = SovereignTextSecondary)
        }
      }
    }

    // Search Box
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("நினைவகத்தில் தேடவும் (உதா: குவாண்டம், டோகாமாக், பாணி)...", fontSize = 11.sp, color = SovereignTextMuted) },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = SovereignSurfaceElevated,
          unfocusedContainerColor = SovereignSurfaceElevated,
          focusedBorderColor = SovereignPurple,
          unfocusedBorderColor = SovereignBorder,
          focusedTextColor = SovereignTextPrimary,
          unfocusedTextColor = SovereignTextPrimary
        ),
        modifier = Modifier.fillMaxWidth()
      )
    }

    // Layer Filter Chips
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = if (selectedLayer == null) SovereignPurpleDark else SovereignSurfaceElevated,
          border = androidx.compose.foundation.BorderStroke(1.dp, if (selectedLayer == null) SovereignPurple else SovereignBorder),
          modifier = Modifier
            .padding(end = 6.dp)
            .clickable { selectedLayer = null }
        ) {
          Text("அனைத்தும்", fontSize = 11.sp, color = SovereignTextPrimary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
        }

        MemoryLayerType.values().forEach { layer ->
          val isSelected = selectedLayer == layer
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (isSelected) SovereignPurpleDark else SovereignSurfaceElevated,
            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) SovereignPurple else SovereignBorder),
            modifier = Modifier
              .padding(end = 6.dp)
              .clickable { selectedLayer = layer }
          ) {
            Text(layer.labelTa, fontSize = 11.sp, color = if (isSelected) SovereignPurple else SovereignTextSecondary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
          }
        }
      }
    }

    items(filteredMemories, key = { it.id }) { mem ->
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = SovereignSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(text = "🔒", fontSize = 11.sp)
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = mem.titleTa, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignPurple)
            }
            Text(text = "நம்பகத்தன்மை: ${(mem.confidenceScore * 100).toInt()}%", fontSize = 10.sp, color = SovereignEmerald, fontWeight = FontWeight.Bold)
          }

          Spacer(modifier = Modifier.height(4.dp))
          Text(text = mem.contentTamil, fontSize = 12.sp, color = SovereignTextPrimary, lineHeight = 17.sp)

          Spacer(modifier = Modifier.height(6.dp))
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = mem.layer.labelEn, fontSize = 10.sp, color = SovereignSky)
            Text(text = mem.timestamp, fontSize = 9.sp, color = SovereignTextMuted)
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 3. AI SAFETY KERNEL & PERMISSION MATRIX
// -------------------------------------------------------------
@Composable
fun SafetyKernelSubScreen() {
  val permissions by SovereignEngine.permissionRules.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignRose),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.AdminPanelSettings, contentDescription = null, tint = SovereignRose)
            Spacer(modifier = Modifier.width(8.dp))
            Text("🛡️ AI Safety Kernel & Permission Matrix", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          }
          Text(text = "சாதனங்கள், கோப்புகள், வங்கி மற்றும் தகவல்தொடர்புகளுக்கான 9-வகை அனுமதிகள் மற்றும் இடர் கொள்கைகள்.", fontSize = 11.sp, color = SovereignTextSecondary)
        }
      }
    }

    items(permissions, key = { "${it.action.name}-${it.entityCategory.name}" }) { rule ->
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = SovereignSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "${rule.entityCategory.labelTa} ➔ ${rule.action.labelTa}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Surface(shape = RoundedCornerShape(4.dp), color = SovereignSurface) {
              Text(text = if (rule.isAllowedAutonomous) "Just-Do-It" else "கட்டுப்படுத்தப்பட்டது", fontSize = 9.sp, color = SovereignCyan, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "அனுமதி வரம்பு: ${rule.allowedScopeDescriptionTa}", fontSize = 11.sp, color = SovereignTextSecondary)
          Text(text = if (rule.requiresConfirmation) "⚠️ உரிமையாளர் உறுதிப்படுத்தல் கட்டாயம்" else "✓ தானியங்கி அனுமதி", fontSize = 10.sp, color = if (rule.requiresConfirmation) SovereignAmber else SovereignEmerald)
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 4. REVERSIBLE ACTIONS & ROLLBACK
// -------------------------------------------------------------
@Composable
fun ReversibleActionsSubScreen() {
  val snapshots by SovereignEngine.reversibleSnapshots.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignAmber),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Restore, contentDescription = null, tint = SovereignAmber)
            Spacer(modifier = Modifier.width(8.dp))
            Text("↩️ Reversible Action Manager (மீட்டமைக்கும் மேலாளர்)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          }
          Text(text = "AI செய்யும் எந்தவொரு மாற்றத்தையும் (குறியீடு, உள்ளமைவு, ஆவணம்) ஒரே கிளிக்கில் முந்தைய நிலைக்கு மீட்டெடுக்கலாம்.", fontSize = 11.sp, color = SovereignTextSecondary)
        }
      }
    }

    items(snapshots, key = { it.snapshotId }) { snap ->
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = SovereignSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, if (!snap.isRollbackAvailable) SovereignBorder else SovereignAmber),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = snap.actionTitleTa, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            if (!snap.isRollbackAvailable) {
              Surface(shape = RoundedCornerShape(4.dp), color = SovereignSurface) {
                Text(text = "மீட்டெடுக்கப்பட்டது", fontSize = 9.sp, color = SovereignTextMuted, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
              }
            } else {
              Button(
                onClick = { SovereignEngine.rollbackSnapshot(snap.snapshotId) },
                colors = ButtonDefaults.buttonColors(containerColor = SovereignAmber),
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
              ) {
                Text(text = "Rollback", fontSize = 10.sp, color = Color.Black, fontWeight = FontWeight.Bold)
              }
            }
          }
          Text(text = "இலக்கு: ${snap.affectedTarget} • நேரம்: ${snap.timestamp}", fontSize = 10.sp, color = SovereignTextMuted)
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "முந்தைய நிலை: ${snap.previousStateSummary}", fontSize = 10.sp, color = SovereignSky)
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 5. PROVENANCE CHAIN & SHA-256 AUDIT TRAIL
// -------------------------------------------------------------
@Composable
fun AuditTrailSubScreen() {
  val auditLogs by SovereignEngine.auditLogs.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignSky),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.ReceiptLong, contentDescription = null, tint = SovereignSky)
            Spacer(modifier = Modifier.width(8.dp))
            Text("🧾 தணிக்கை மற்றும் ஆதார கையொப்பம் (Provenance)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          }
          Text(text = "AI எடுத்த ஒவ்வொரு முடிவுக்கும் முழுமையான தொடர் சங்கிலி மற்றும் SHA-256 கிரிப்டோகிராஃபிக் பதிவு.", fontSize = 11.sp, color = SovereignTextSecondary)
        }
      }
    }

    items(auditLogs, key = { it.id }) { log ->
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = SovereignSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = log.actionSummary, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
            Text(text = log.timestamp, fontSize = 9.sp, color = SovereignTextMuted)
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "இயக்கிய கருவி: ${log.toolTriggered}", fontSize = 10.sp, color = SovereignTextSecondary)
          Text(text = "முறை: ${log.executionMode}", fontSize = 10.sp, color = SovereignSky)
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "SHA-256: ${log.securityHash}", fontSize = 9.sp, color = SovereignEmerald, fontFamily = FontFamily.Monospace)
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 6. DEVICE CONTINUITY HUB
// -------------------------------------------------------------
@Composable
fun DeviceContinuitySubScreen() {
  val devices = DeviceContinuityStatus.values().toList()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignCyan),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Devices, contentDescription = null, tint = SovereignCyan)
            Spacer(modifier = Modifier.width(8.dp))
            Text("📱 Device Continuity Hub (சாதன தொடர்ச்சி)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          }
          Text(text = "Phone ➔ Workstation PC ➔ Vehicle ➔ Smart Hub ஆகிய சாதனங்களுக்கு இடையே தடையற்ற AI நினைவகம் மற்றும் செயல் தொடர்ச்சி.", fontSize = 11.sp, color = SovereignTextSecondary)
        }
      }
    }

    items(devices) { dev ->
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = SovereignSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, if (dev.isCurrent) SovereignEmerald else SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(if (dev.isCurrent) SovereignEmerald else SovereignTextMuted)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(text = dev.deviceName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
              Text(text = "சாதன வகை: ${dev.iconType}", fontSize = 10.sp, color = SovereignTextMuted)
            }
          }
          Text(text = dev.statusTa, fontSize = 10.sp, color = SovereignCyan, fontWeight = FontWeight.Medium)
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 7. PERSONAL AI CONSTITUTION SUB-SCREEN
// -------------------------------------------------------------
@Composable
fun ConstitutionSubScreen() {
  val constitutionRules by com.example.data.NextLevelIntelligenceEngine.constitutionRules.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(SovereignGold, SovereignCyan)))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Gavel, contentDescription = null, tint = SovereignGold, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("தனிநபர் AI அரசியல் சாசனம் (Policy Kernel)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignGold)
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            "அடிப்படை மாடல்கள் (Local LLM / Private Cloud) மாறினாலும், உரிமையாளரின் இந்த கொள்கைக் கரு எப்போதும் மாறாது. தனியுரிமை, வெளிப்படைத்தன்மை மற்றும் தன்னாட்சி விதிகளுக்கு AI எப்போதும் அடிபணிந்து நடக்கும்.",
            fontSize = 11.sp,
            color = SovereignTextSecondary,
            lineHeight = 15.sp
          )
        }
      }
    }

    items(constitutionRules) { rule ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(rule.titleTa, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
            Surface(
              color = if (rule.requiresExplicitConfirmation) SovereignGold.copy(alpha = 0.2f) else SovereignGreen.copy(alpha = 0.2f),
              shape = RoundedCornerShape(6.dp)
            ) {
              Text(
                if (rule.requiresExplicitConfirmation) "முன் அனுமதி கட்டாயம்" else "தன்னாட்சி அனுமதி",
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Bold,
                color = if (rule.requiresExplicitConfirmation) SovereignGold else SovereignGreen,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(rule.ruleDescriptionTa, fontSize = 11.5.sp, color = SovereignTextSecondary, lineHeight = 15.sp)
        }
      }
    }
  }
}
