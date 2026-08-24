package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SovereignEngine
import com.example.model.CognitivePipelineStage
import com.example.model.ProviderIndependence
import com.example.ui.theme.*

@Composable
fun SovereignTopHeader(
  onOpenSettings: () -> Unit = {},
  onOpenHandsFreeOrb: () -> Unit = {}
) {
  val cognitiveStage by SovereignEngine.cognitiveStage.collectAsState()
  val provider by SovereignEngine.providerIndependence.collectAsState()
  val justDoItMode by SovereignEngine.justDoItModeEnabled.collectAsState()

  Surface(
    color = SovereignSurface,
    modifier = Modifier
      .fillMaxWidth()
      .shadow(8.dp),
    tonalElevation = 6.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        // Logo & Title
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.clickable { onOpenHandsFreeOrb() }
        ) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(CircleShape)
              .background(
                Brush.linearGradient(
                  colors = listOf(Color(0xFFFF6B00), Color(0xFFFFB703), Color(0xFFFF3300))
                )
              )
              .border(1.5.dp, Color(0xFFFFE599), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Mic,
              contentDescription = "Hands-Free Voice Core",
              tint = Color.White,
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "ஸேவியர்பாபு (XAVIER BABU)",
                color = SovereignTextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
              )
              Spacer(modifier = Modifier.width(6.dp))
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(Color(0xFFFF6B00).copy(alpha = 0.25f))
                  .border(1.dp, Color(0xFFFFB703).copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                  .padding(horizontal = 4.dp, vertical = 1.dp)
              ) {
                Text(
                  text = "VOICE ORB",
                  color = Color(0xFFFFB703),
                  fontSize = 8.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
            Text(
              text = "குரல் கட்டளை கோர் (Voice-First)",
              color = SovereignTextMuted,
              fontSize = 10.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }

        // Provider Badge & Hands-Free / Settings Trigger
        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = onOpenHandsFreeOrb,
            modifier = Modifier
              .size(34.dp)
              .clip(CircleShape)
              .background(Color(0xFFFF6B00).copy(alpha = 0.2f))
              .border(1.dp, Color(0xFFFF8500), CircleShape)
          ) {
            Icon(
              imageVector = Icons.Default.Mic,
              contentDescription = "Hands-Free Voice Mode",
              tint = Color(0xFFFFB703),
              modifier = Modifier.size(16.dp)
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          IconButton(
            onClick = onOpenSettings,
            modifier = Modifier
              .size(34.dp)
              .clip(CircleShape)
              .background(SovereignSurfaceElevated)
              .border(1.dp, SovereignBorder, CircleShape)
          ) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = "Security Vault",
              tint = SovereignCyan,
              modifier = Modifier.size(16.dp)
            )
          }
        }
      }

      // Live Cognitive Pipeline Indicator (when executing)
      AnimatedVisibility(
        visible = cognitiveStage != CognitivePipelineStage.IDLE,
        enter = fadeIn(),
        exit = fadeOut()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              CircularProgressIndicator(
                strokeWidth = 2.dp,
                color = SovereignCyan,
                modifier = Modifier.size(12.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "சுழற்சி நிலை: ${cognitiveStage.labelTa}",
                color = SovereignCyan,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
            }
            Text(
              text = cognitiveStage.labelEn,
              color = SovereignTextMuted,
              fontSize = 9.sp
            )
          }
        }
      }
    }
  }
}
