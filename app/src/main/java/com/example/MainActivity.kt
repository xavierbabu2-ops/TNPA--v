package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SovereignTopHeader
import com.example.ui.screens.*
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      SovereignTheme(darkTheme = true) {
        SovereignMainApp()
      }
    }
  }
}

@Composable
fun SovereignMainApp() {
  var activeWorkspaceIndex by remember { mutableIntStateOf(0) }
  var showHandsFreeOrbModal by remember { mutableStateOf(false) }

  val workspaces = listOf(
    WorkspaceTabItem("முழு நுண்ணறிவு", "Omni AI", Icons.Default.AutoAwesome, "tab_omni_intelligence"),
    WorkspaceTabItem("வீடு & IoT", "IoT Hub", Icons.Default.Router, "tab_iot_hub"),
    WorkspaceTabItem("வைஃபை லேயர்", "Wi-Fi Hub", Icons.Default.Wifi, "tab_wifi_hub"),
    WorkspaceTabItem("ஜிபிஎஸ் ரேடார்", "GPS Radar", Icons.Default.MyLocation, "tab_gps_radar"),
    WorkspaceTabItem("விழிப்புணர்வு", "Awareness", Icons.Default.Assistant, "tab_personal_awareness"),
    WorkspaceTabItem("படைப்பாற்றல்", "Studios", Icons.Default.Palette, "tab_creative_studios"),
    WorkspaceTabItem("அறிவியல் & கணிதம்", "Science & Math", Icons.Default.Science, "tab_science_math"),
    WorkspaceTabItem("திட்ட கண்டுபிடிப்பு", "Inventor", Icons.Default.Lightbulb, "tab_project_inventor"),
    WorkspaceTabItem("தன்னாட்சி பெட்டகம்", "Sovereign Vault", Icons.Default.Shield, "tab_sovereign_vault")
  )

  Scaffold(
    topBar = {
      SovereignTopHeader(
        onOpenSettings = { activeWorkspaceIndex = 5 },
        onOpenHandsFreeOrb = { showHandsFreeOrbModal = true }
      )
    },
    bottomBar = {
      Surface(
        color = SovereignSurface,
        tonalElevation = 10.dp,
        modifier = Modifier
          .fillMaxWidth()
          .shadow(16.dp)
      ) {
        NavigationBar(
          containerColor = SovereignSurface,
          contentColor = SovereignCyan,
          tonalElevation = 0.dp,
          modifier = Modifier.navigationBarsPadding()
        ) {
          workspaces.forEachIndexed { index, item ->
            val isSelected = activeWorkspaceIndex == index
            NavigationBarItem(
              selected = isSelected,
              onClick = { activeWorkspaceIndex = index },
              icon = {
                Icon(
                  imageVector = item.icon,
                  contentDescription = item.labelTa,
                  tint = if (isSelected) SovereignCyan else SovereignTextMuted,
                  modifier = Modifier.size(20.dp)
                )
              },
              label = {
                Text(
                  text = item.labelTa,
                  fontSize = 8.5.sp,
                  maxLines = 1,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) SovereignCyan else SovereignTextSecondary
                )
              },
              colors = NavigationBarItemDefaults.colors(
                indicatorColor = SovereignSurfaceHover
              ),
              modifier = Modifier.testTag(item.testTag)
            )
          }
        }
      }
    },
    containerColor = SovereignBackground
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (activeWorkspaceIndex) {
        0 -> OmniIntelligenceScreen(
          onNavigateToStudio = { activeWorkspaceIndex = 5 },
          onNavigateToScience = { activeWorkspaceIndex = 6 },
          onNavigateToProjects = { activeWorkspaceIndex = 7 }
        )
        1 -> UniversalIoTControlHubScreen()
        2 -> WifiIntelligenceHubScreen()
        3 -> GlobalGpsRadarScreen()
        4 -> PersonalAwarenessScreen()
        5 -> CreativeStudiosScreen()
        6 -> ScienceAndMathScreen()
        7 -> ProjectInventorScreen()
        8 -> SovereignControlScreen()
      }
    }
  }

  // Hands-Free Fiery Voice Orb Mode Modal
  if (showHandsFreeOrbModal) {
    androidx.compose.ui.window.Dialog(
      onDismissRequest = { showHandsFreeOrbModal = false },
      properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
    ) {
      Box(modifier = Modifier.fillMaxSize()) {
        HandsFreeVoiceCoreScreen(onDismiss = { showHandsFreeOrbModal = false })
      }
    }
  }
}

data class WorkspaceTabItem(
  val labelTa: String,
  val labelEn: String,
  val icon: androidx.compose.ui.graphics.vector.ImageVector,
  val testTag: String
)
