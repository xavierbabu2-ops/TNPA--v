package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HybridVoiceManager
import com.example.model.*
import com.example.ui.theme.*
import kotlin.math.sin

@Composable
fun HybridVoiceStudio(
  onDismiss: () -> Unit = {}
) {
  val voiceConfig by HybridVoiceManager.voiceConfig.collectAsState()
  val activeEmotion by HybridVoiceManager.activeEmotionCue.collectAsState()
  val samples by HybridVoiceManager.spokenSamples.collectAsState()
  val isPlaying by HybridVoiceManager.isPlayingSample.collectAsState()

  var testUtterance by remember { mutableStateOf("வணக்கம் பாபு! உங்களின் குரல் அடித்தளமும் என் ரோபோடிக் அதிர்வும் இணைந்து இயங்குகிறது.") }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(SovereignBackground)
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Top Bar
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            "ஹைப்ரிட் குரல் ஆய்வகம் (Hybrid Voice Architecture)",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = SovereignTextPrimary
          )
          Text(
            "உரிமையாளர் குரல் (Layer A) + AI ரோபோடிக் அதிர்வு (Layer B)",
            fontSize = 11.sp,
            color = SovereignTextMuted
          )
        }
        IconButton(onClick = onDismiss) {
          Icon(Icons.Default.Close, contentDescription = "Close", tint = SovereignTextSecondary)
        }
      }
    }

    // 1. Voice Identity Architecture Box
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(SovereignCyanDark, SovereignPurpleDark)))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("இரட்டை அடுக்கு குரல் கட்டமைப்பு (2-Layer Vocal Architecture)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
            Surface(
              color = SovereignGreenDark.copy(alpha = 0.3f),
              shape = RoundedCornerShape(6.dp)
            ) {
              Text("✓ Voiceprint Verified", color = SovereignGreen, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            // Layer A Box
            Surface(
              color = SovereignSurfaceDark,
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Text("Layer A: மனிதத் தன்மை", fontSize = 10.sp, color = SovereignGold, fontWeight = FontWeight.Bold)
                Text("உரிமையாளர் குரல்", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text("இயற்கை தமிழ் உச்சரிப்பு, உணர்ச்சி இணைப்பு மற்றும் பிரத்யேக ரிதம்.", fontSize = 9.5.sp, color = SovereignTextMuted, lineHeight = 13.sp)
              }
            }

            // Layer B Box
            Surface(
              color = SovereignSurfaceDark,
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Text("Layer B: AI நுண்ணறிவு", fontSize = 10.sp, color = SovereignCyan, fontWeight = FontWeight.Bold)
                Text("ரோபோடிக் சிந்தடிக் லேயர்", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text("தெளிவான கிரிஸ்டலின் அதிர்வு, அதிநவீன தொழில்நுட்பத் தெளிவு.", fontSize = 9.5.sp, color = SovereignTextMuted, lineHeight = 13.sp)
              }
            }
          }
        }
      }
    }

    // 2. Real-time Mixing Ratio Slider
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("குரல் கலவை விகிதம் (Hybrid Ratio):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Text(
              "${voiceConfig.customOwnerPercentage}% மனிதன்  |  ${voiceConfig.customAiPercentage}% AI",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = SovereignCyan
            )
          }

          Spacer(modifier = Modifier.height(8.dp))
          Slider(
            value = voiceConfig.customOwnerPercentage.toFloat(),
            onValueChange = { HybridVoiceManager.setCustomMixRatio(it.toInt()) },
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
              thumbColor = SovereignCyan,
              activeTrackColor = SovereignCyan,
              inactiveTrackColor = SovereignPurpleDark
            ),
            modifier = Modifier.testTag("slider_hybrid_voice_mix")
          )

          Spacer(modifier = Modifier.height(8.dp))
          Text("முன்னமைக்கப்பட்ட சுயவிவரங்கள் (Mix Profiles):", fontSize = 10.5.sp, color = SovereignTextMuted)
          Spacer(modifier = Modifier.height(6.dp))
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            VoiceMixProfile.values().forEach { profile ->
              val isSelected = voiceConfig.activeProfile == profile
              Surface(
                color = if (isSelected) SovereignCyanDark.copy(alpha = 0.4f) else SovereignSurfaceDark,
                shape = RoundedCornerShape(8.dp),
                border = if (isSelected) BorderStroke(1.dp, SovereignCyan) else null,
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable { HybridVoiceManager.setMixProfile(profile) }
              ) {
                Row(
                  modifier = Modifier.padding(10.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Column {
                    Text(profile.labelTa, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = if (isSelected) SovereignCyan else SovereignTextPrimary)
                    Text(profile.descriptionTa, fontSize = 9.5.sp, color = SovereignTextSecondary)
                  }
                  Text("${profile.ownerVoicePercentage}/${profile.aiVoicePercentage}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignGold)
                }
              }
            }
          }
        }
      }
    }

    // 3. Dynamic Context Mixing & Vocal Emotion Cues
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text("சூழல் விழிப்புணர்வு ஆட்டோ-மிக்ஸிங் (Context Auto-Mix)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
              Text("உரையாடல், அறிவியல் விளக்கம், எச்சரிக்கைகளுக்கு ஏற்ப கலவை மாறும்.", fontSize = 9.5.sp, color = SovereignTextMuted)
            }
            Switch(
              checked = voiceConfig.dynamicContextMixingEnabled,
              onCheckedChange = { HybridVoiceManager.toggleDynamicContextMixing() },
              colors = SwitchDefaults.colors(checkedThumbColor = SovereignCyan, checkedTrackColor = SovereignCyanDark)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text("உணர்ச்சி வினவல் நிலை (Vocal Emotion Cue):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            VocalEmotionCue.values().take(3).forEach { cue ->
              FilterChip(
                selected = activeEmotion == cue,
                onClick = { HybridVoiceManager.setEmotionCue(cue) },
                label = { Text(cue.labelTa, fontSize = 9.5.sp) },
                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = SovereignPurpleDark, selectedLabelColor = Color.White)
              )
            }
          }
        }
      }
    }

    // 4. Interactive Voice Waveform & Barge-In Demo
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text("நேரலை ஹைப்ரிட் பேச்சுத் தொகுப்பி (Synthesize & Barge-in)", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = testUtterance,
            onValueChange = { testUtterance = it },
            placeholder = { Text("பேச வேண்டிய வாக்கியத்தை உள்ளிடவும்...", fontSize = 11.sp) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = SovereignSurfaceDark,
              unfocusedContainerColor = SovereignSurfaceDark,
              focusedBorderColor = SovereignCyan,
              unfocusedBorderColor = SovereignBorder
            ),
            shape = RoundedCornerShape(8.dp)
          )

          Spacer(modifier = Modifier.height(10.dp))
          // Visual Waveform Animation
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(44.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(SovereignSurfaceDark),
            contentAlignment = Alignment.Center
          ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
              val width = size.width
              val height = size.height
              val barCount = 36
              val barWidth = width / (barCount * 1.5f)

              for (i in 0 until barCount) {
                val factor = if (isPlaying) (sin(i * 0.4f + System.currentTimeMillis() * 0.005f) * 0.4f + 0.5f).toFloat() else 0.15f
                val barHeight = height * factor
                val x = i * (barWidth * 1.5f) + 10f
                val y = (height - barHeight) / 2f
                drawRoundRect(
                  color = if (i % 2 == 0) SovereignCyan else SovereignPurple,
                  topLeft = Offset(x, y),
                  size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                  cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = {
                HybridVoiceManager.synthesizeHybridUtterance(testUtterance)
              },
              colors = ButtonDefaults.buttonColors(containerColor = SovereignCyan),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("குரல் இயக்கு (Play Hybrid Voice)", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            Button(
              onClick = { HybridVoiceManager.triggerBargeInInterrupt() },
              colors = ButtonDefaults.buttonColors(containerColor = SovereignRedDark),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(Icons.Default.PanTool, contentDescription = null, tint = SovereignRed, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("«ஒரு நிமிஷம்» (Barge-In)", color = SovereignRed, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 5. Recent Spoken Voice Samples
    item {
      Text("சமீபத்திய ஹைப்ரிட் குரல் பதிவுகள்:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
    }

    items(samples) { sample ->
      Surface(
        color = SovereignSurfaceDark,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(sample.contextLabel, fontSize = 10.sp, color = SovereignCyan, fontWeight = FontWeight.Bold)
            if (sample.isBargeInTriggered) {
              Text("🛑 உரிமையாளர் குறுக்கீட்டால் நிறுத்தப்பட்டது", fontSize = 9.sp, color = SovereignRed, fontWeight = FontWeight.Bold)
            } else {
              Text("${sample.simulatedDurationSec}s", fontSize = 10.sp, color = SovereignTextMuted)
            }
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text("«${sample.phraseTa}»", fontSize = 11.5.sp, color = SovereignTextSecondary)
        }
      }
    }
  }
}
