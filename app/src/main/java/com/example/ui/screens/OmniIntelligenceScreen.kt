package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SovereignEngine
import com.example.model.*
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun OmniIntelligenceScreen(
  onNavigateToStudio: () -> Unit = {},
  onNavigateToScience: () -> Unit = {},
  onNavigateToProjects: () -> Unit = {}
) {
  val messages by SovereignEngine.chatMessages.collectAsState()
  val cognitiveStage by SovereignEngine.cognitiveStage.collectAsState()
  val voiceProfile by SovereignEngine.tamilVoiceProfile.collectAsState()
  val agentStates by SovereignEngine.agentStates.collectAsState()

  var inputPrompt by remember { mutableStateOf("") }
  var showVoiceSettingsModal by remember { mutableStateOf(false) }
  var showVoiceLabModal by remember { mutableStateOf(false) }
  var showCognitiveDetailsModal by remember { mutableStateOf(false) }
  var showNextLevelHubModal by remember { mutableStateOf(false) }
  var showHybridVoiceModal by remember { mutableStateOf(false) }
  var selectedInputType by remember { mutableStateOf(MultimodalInputType.TEXT) }

  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()

  val suggestionChips = listOf(
    "🔮 அடுத்த தலைமுறை நுண்ணறிவு மையம் (Next-Level Intelligence)",
    "🤖 ஹைப்ரிட் குரல் ஆய்வகம் (Owner + AI Voice Mixer)",
    "🎙️ அந்த project-ஐ open பண்ணி நேத்து விட்ட இடத்துல இருந்து continue பண்ணு",
    "🔬 பிளாஸ்மா காந்த கட்டுப்பாட்டுக்கு ஒரு புதிய தீர்வு கண்டுபிடி",
    "📚 Quantum physics ஆரம்பத்திலிருந்து எளிமையாகக் கற்றுக்கொடு",
    "🎨 3D ஹாலோகிராபிக் குவாண்டம் வரைபடம் உருவாக்கு",
    "🎬 30-நொடி அறிவியல் வீடியோ பைப்லைன் தயார் செய்",
    "💡 IoT சோலார் சென்சாருக்கு C++ ஃபார்ம்வேர் மற்றும் BOM உருவாக்கு"
  )

  LaunchedEffect(messages.size) {
    if (messages.isNotEmpty()) {
      listState.animateScrollToItem(messages.size - 1)
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(SovereignBackground)
  ) {
    // 1. Cognitive Architecture 10-Stage Pipeline Visualizer Bar
    CognitivePipelineBanner(
      currentStage = cognitiveStage,
      onClick = { showCognitiveDetailsModal = true }
    )

    // 2. Tamil Voice Intelligence & Agent Quick Status Bar
    TamilVoiceIntelligenceDock(
      voiceProfile = voiceProfile,
      onToggleListening = {
        SovereignEngine.toggleVoiceListening()
        if (voiceProfile.isListening) {
          // If stopping listening, send the transcript
          if (voiceProfile.lastSpokenTranscriptTa.isNotBlank()) {
            SovereignEngine.sendUserPrompt(voiceProfile.lastSpokenTranscriptTa, MultimodalInputType.TAMIL_VOICE)
          }
        }
      },
      onOpenVoiceSettings = { showVoiceSettingsModal = true },
      onOpenVoiceLab = { showVoiceLabModal = true }
    )

    // 3. Quick Intent Shortcuts Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(SovereignSurface)
        .padding(vertical = 6.dp)
        .horizontalScroll(rememberScrollState()),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Spacer(modifier = Modifier.width(12.dp))
      Text(
        text = "⚡ INTENTS:",
        fontSize = 9.sp,
        fontWeight = FontWeight.Black,
        color = SovereignSky,
        letterSpacing = 1.sp
      )
      Spacer(modifier = Modifier.width(8.dp))

      suggestionChips.forEach { chipText ->
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = SovereignSurfaceElevated,
          border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
          modifier = Modifier
            .padding(end = 8.dp)
            .clickable {
              if (chipText.contains("அடுத்த தலைமுறை")) {
                showNextLevelHubModal = true
              } else if (chipText.contains("ஹைப்ரிட் குரல்")) {
                showHybridVoiceModal = true
              } else {
                inputPrompt = chipText
                SovereignEngine.sendUserPrompt(chipText, MultimodalInputType.TEXT)
              }
            }
        ) {
          Text(
            text = chipText,
            color = SovereignTextPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
          )
        }
      }
      Spacer(modifier = Modifier.width(8.dp))
    }

    // 4. Chat Message Feed with Evidence, Provenance & Rollback Cards
    LazyColumn(
      state = listState,
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(horizontal = 12.dp),
      contentPadding = PaddingValues(top = 10.dp, bottom = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      items(messages, key = { it.id }) { msg ->
        SovereignChatMessageCard(
          message = msg,
          onNavigateToStudio = onNavigateToStudio,
          onNavigateToScience = onNavigateToScience,
          onNavigateToProjects = onNavigateToProjects,
          onRollback = { snapshotId ->
            SovereignEngine.rollbackSnapshot(snapshotId)
          }
        )
      }
    }

    // 5. Bottom Multimodal Input Bar
    Surface(
      color = SovereignSurface,
      tonalElevation = 8.dp,
      modifier = Modifier
        .fillMaxWidth()
        .shadow(12.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 8.dp)
      ) {
        // Active Listening Waveform animation
        AnimatedVisibility(
          visible = voiceProfile.isListening,
          enter = fadeIn(),
          exit = fadeOut()
        ) {
          Surface(
            color = SovereignEmeraldDark.copy(alpha = 0.3f),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SovereignEmerald),
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 8.dp)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(SovereignEmerald)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                  Text(
                    text = "தமிழ்க் குரல் பதிவு செய்யப்படுகிறது... (Tamil Voice Listening)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SovereignEmerald
                  )
                  Text(
                    text = "\"${voiceProfile.lastSpokenTranscriptTa}\"",
                    fontSize = 12.sp,
                    color = SovereignTextPrimary,
                    fontWeight = FontWeight.Medium
                  )
                }
              }
              Button(
                onClick = {
                  SovereignEngine.toggleVoiceListening()
                  SovereignEngine.sendUserPrompt(voiceProfile.lastSpokenTranscriptTa, MultimodalInputType.TAMIL_VOICE)
                },
                colors = ButtonDefaults.buttonColors(containerColor = SovereignEmerald),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
              ) {
                Text("அனுப்பு (Send)", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
              }
            }
          }
        }

        // Input type chips (Voice, Text, Vision, Sensor, Doc, Code)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          verticalAlignment = Alignment.CenterVertically
        ) {
          MultimodalInputType.values().take(6).forEach { type ->
            val isSelected = selectedInputType == type
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (isSelected) SovereignCyanDark else SovereignSurfaceElevated,
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (isSelected) SovereignCyan else SovereignBorder
              ),
              modifier = Modifier
                .padding(end = 6.dp)
                .clickable {
                  selectedInputType = type
                  if (type == MultimodalInputType.TAMIL_VOICE) {
                    SovereignEngine.toggleVoiceListening()
                  }
                }
            ) {
              Text(
                text = type.labelTa,
                fontSize = 10.sp,
                color = if (isSelected) SovereignCyan else SovereignTextSecondary,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Main Text Box with Send & Voice Action
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Voice Mic Button
          IconButton(
            onClick = {
              selectedInputType = MultimodalInputType.TAMIL_VOICE
              SovereignEngine.toggleVoiceListening()
            },
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(if (voiceProfile.isListening) SovereignEmerald else SovereignSurfaceElevated)
              .border(1.dp, if (voiceProfile.isListening) SovereignEmerald else SovereignBorder, CircleShape)
          ) {
            Icon(
              imageVector = if (voiceProfile.isListening) Icons.Default.Mic else Icons.Default.MicNone,
              contentDescription = "Voice Input",
              tint = if (voiceProfile.isListening) Color.Black else SovereignCyan,
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          // Text Field
          OutlinedTextField(
            value = inputPrompt,
            onValueChange = { inputPrompt = it },
            placeholder = {
              Text(
                "தமிழில் பேசலாம் / தட்டச்சு செய்யலாம்... (Tamil/English/Tanglish)",
                fontSize = 12.sp,
                color = SovereignTextMuted
              )
            },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = SovereignSurfaceElevated,
              unfocusedContainerColor = SovereignSurfaceElevated,
              focusedBorderColor = SovereignCyan,
              unfocusedBorderColor = SovereignBorder,
              focusedTextColor = SovereignTextPrimary,
              unfocusedTextColor = SovereignTextPrimary
            ),
            modifier = Modifier
              .weight(1f)
              .testTag("omni_input_field"),
            maxLines = 3
          )

          Spacer(modifier = Modifier.width(8.dp))

          // Send Button
          IconButton(
            onClick = {
              if (inputPrompt.isNotBlank()) {
                val text = inputPrompt
                inputPrompt = ""
                SovereignEngine.sendUserPrompt(text, selectedInputType)
              }
            },
            enabled = inputPrompt.isNotBlank(),
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(if (inputPrompt.isNotBlank()) SovereignCyan else SovereignSurfaceElevated)
              .testTag("send_prompt_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.Send,
              contentDescription = "Send",
              tint = if (inputPrompt.isNotBlank()) Color.Black else SovereignTextMuted,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }

  // Voice Settings Modal
  if (showVoiceSettingsModal) {
    TamilVoiceSettingsModal(
      profile = voiceProfile,
      onDismiss = { showVoiceSettingsModal = false },
      onSelectStyle = { SovereignEngine.setVoiceStyle(it) }
    )
  }

  // Voice Intelligence Lab Modal
  if (showVoiceLabModal) {
    com.example.ui.components.TamilVoiceIntelligenceLabDialog(
      onDismiss = { showVoiceLabModal = false }
    )
  }

  // Cognitive Pipeline Details Modal
  if (showCognitiveDetailsModal) {
    CognitiveArchitectureModal(
      currentStage = cognitiveStage,
      onDismiss = { showCognitiveDetailsModal = false }
    )
  }

  // Next-Level Intelligence Hub Modal
  if (showNextLevelHubModal) {
    androidx.compose.ui.window.Dialog(
      onDismissRequest = { showNextLevelHubModal = false },
      properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
    ) {
      Box(modifier = Modifier.fillMaxSize()) {
        NextLevelIntelligenceHub(onDismiss = { showNextLevelHubModal = false })
      }
    }
  }

  // Hybrid Voice Studio Modal
  if (showHybridVoiceModal) {
    androidx.compose.ui.window.Dialog(
      onDismissRequest = { showHybridVoiceModal = false },
      properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
    ) {
      Box(modifier = Modifier.fillMaxSize()) {
        HybridVoiceStudio(onDismiss = { showHybridVoiceModal = false })
      }
    }
  }
}

// -------------------------------------------------------------
// COGNITIVE PIPELINE TOP BANNER
// -------------------------------------------------------------
@Composable
fun CognitivePipelineBanner(
  currentStage: CognitivePipelineStage,
  onClick: () -> Unit
) {
  Surface(
    color = SovereignSurfaceElevated,
    border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(if (currentStage != CognitivePipelineStage.IDLE) SovereignCyan else SovereignEmerald)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "🧠 COGNITIVE LOOP:",
          fontSize = 9.sp,
          fontWeight = FontWeight.Black,
          color = SovereignSky,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = currentStage.labelTa,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = SovereignCyan
        )
      }
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "முழு சுழற்சி விவரம் ➔",
          fontSize = 10.sp,
          color = SovereignTextSecondary
        )
      }
    }
  }
}

// -------------------------------------------------------------
// TAMIL VOICE INTELLIGENCE DOCK
// -------------------------------------------------------------
@Composable
fun TamilVoiceIntelligenceDock(
  voiceProfile: TamilVoiceProfile,
  onToggleListening: () -> Unit,
  onOpenVoiceSettings: () -> Unit,
  onOpenVoiceLab: () -> Unit = {}
) {
  Surface(
    color = SovereignSurface,
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onOpenVoiceLab() }
      ) {
        Icon(
          imageVector = Icons.Default.RecordVoiceOver,
          contentDescription = "Voice Style",
          tint = SovereignEmerald,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
          Text(
            text = "தமிழ் குரல்: ${voiceProfile.activeVoiceStyle.labelTa}",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = SovereignTextPrimary
          )
          Text(
            text = "Tanglish • Normalization • Router Engine",
            fontSize = 9.sp,
            color = SovereignTextMuted
          )
        }
      }

      Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = SovereignCyanDark.copy(alpha = 0.4f),
          border = androidx.compose.foundation.BorderStroke(1.dp, SovereignCyan.copy(alpha = 0.5f)),
          modifier = Modifier.clickable { onOpenVoiceLab() }
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Mic,
              contentDescription = "Voice Lab",
              tint = SovereignCyan,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = "குரல் ஆய்வகம்",
              fontSize = 10.sp,
              color = SovereignCyan,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = SovereignSurfaceElevated,
          border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
          modifier = Modifier.clickable { onOpenVoiceSettings() }
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Tune,
              contentDescription = "Tune Voice",
              tint = SovereignTextSecondary,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = "பாணி",
              fontSize = 10.sp,
              color = SovereignTextSecondary,
              fontWeight = FontWeight.Medium
            )
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// CHAT MESSAGE CARD (WITH EVIDENCE, PROVENANCE & ROLLBACK)
// -------------------------------------------------------------
@Composable
fun SovereignChatMessageCard(
  message: OmniChatMessage,
  onNavigateToStudio: () -> Unit,
  onNavigateToScience: () -> Unit,
  onNavigateToProjects: () -> Unit,
  onRollback: (String) -> Unit
) {
  val isOwner = message.sender == MessageSender.OWNER

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = if (isOwner) Arrangement.End else Arrangement.Start
  ) {
    if (!isOwner) {
      Box(
        modifier = Modifier
          .size(34.dp)
          .clip(CircleShape)
          .background(
            Brush.linearGradient(
              listOf(SovereignCyan, SovereignPurple)
            )
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.AutoAwesome,
          contentDescription = "AI",
          tint = Color.Black,
          modifier = Modifier.size(18.dp)
        )
      }
      Spacer(modifier = Modifier.width(8.dp))
    }

    Column(
      modifier = Modifier.widthIn(max = 320.dp),
      horizontalAlignment = if (isOwner) Alignment.End else Alignment.Start
    ) {
      // Sender Header & Stage Tag
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 3.dp)
      ) {
        Text(
          text = if (isOwner) "உரிமையாளர் (Owner)" else "Sovereign AI Kernel",
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          color = if (isOwner) SovereignSky else SovereignCyan
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = message.timestamp,
          fontSize = 9.sp,
          color = SovereignTextMuted
        )
      }

      // Bubble Body
      Surface(
        shape = RoundedCornerShape(
          topStart = if (isOwner) 16.dp else 4.dp,
          topEnd = if (isOwner) 4.dp else 16.dp,
          bottomStart = 16.dp,
          bottomEnd = 16.dp
        ),
        color = if (isOwner) SovereignSkyDark.copy(alpha = 0.6f) else SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(
          1.dp,
          if (isOwner) SovereignSky.copy(alpha = 0.4f) else SovereignBorder
        ),
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          // Tool or Agent Banner
          if (message.activeAgent != null) {
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = SovereignSurfaceElevated,
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.SmartToy,
                  contentDescription = null,
                  tint = SovereignCyan,
                  modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = message.activeAgent.titleTa,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = SovereignCyan
                )
              }
            }
          }

          // Main Text
          Text(
            text = message.text,
            fontSize = 13.sp,
            lineHeight = 19.sp,
            color = SovereignTextPrimary,
            fontWeight = FontWeight.Normal
          )

          // Media Preview (Images/3D/Video)
          if (message.mediaPreviewUrl != null) {
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
              model = message.mediaPreviewUrl,
              contentDescription = "Visual Preview",
              contentScale = ContentScale.Crop,
              modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, SovereignBorder, RoundedCornerShape(8.dp))
            )
          }

          // Evidence Citation Box
          if (message.evidence != null) {
            Spacer(modifier = Modifier.height(10.dp))
            EvidenceBadgeCard(evidence = message.evidence)
          }

          // Rollback / Undo Button
          if (message.rollbackSnapshotId != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "🛡️ Snapshot சேமிக்கப்பட்டது",
                fontSize = 9.sp,
                color = SovereignTextMuted
              )
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = SovereignAmberDark.copy(alpha = 0.3f),
                border = androidx.compose.foundation.BorderStroke(1.dp, SovereignAmber),
                modifier = Modifier.clickable { onRollback(message.rollbackSnapshotId) }
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = Icons.Default.Undo,
                    contentDescription = "Undo",
                    tint = SovereignAmber,
                    modifier = Modifier.size(12.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "மீட்டமை (Rollback)",
                    fontSize = 10.sp,
                    color = SovereignAmber,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
          }

          // Quick Action Navigation Button
          when (message.mediaType) {
            "image", "video" -> {
              Spacer(modifier = Modifier.height(8.dp))
              Button(
                onClick = onNavigateToStudio,
                colors = ButtonDefaults.buttonColors(containerColor = SovereignCyan),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 6.dp)
              ) {
                Text("படைப்பாற்றல் கூடத்தில் திற ➔", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
              }
            }
            "research", "math" -> {
              Spacer(modifier = Modifier.height(8.dp))
              Button(
                onClick = onNavigateToScience,
                colors = ButtonDefaults.buttonColors(containerColor = SovereignEmerald),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 6.dp)
              ) {
                Text("அறிவியல் & கணித ஆய்வகம் செல் ➔", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
              }
            }
            "code", "learning" -> {
              Spacer(modifier = Modifier.height(8.dp))
              Button(
                onClick = onNavigateToProjects,
                colors = ButtonDefaults.buttonColors(containerColor = SovereignSky),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 6.dp)
              ) {
                Text("திட்டங்கள் & டிஜிட்டல் ட்வின் செல் ➔", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// EVIDENCE CITATION CARD
// -------------------------------------------------------------
@Composable
fun EvidenceBadgeCard(evidence: EvidenceCitation) {
  Surface(
    shape = RoundedCornerShape(8.dp),
    color = SovereignSurfaceElevated,
    border = androidx.compose.foundation.BorderStroke(1.dp, Color(evidence.confidence.colorHex)),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(10.dp)) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = evidence.confidence.symbol, fontSize = 12.sp)
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = evidence.confidence.labelTa,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(evidence.confidence.colorHex)
          )
        }
        Text(
          text = evidence.claimCategory.labelTa,
          fontSize = 9.sp,
          color = SovereignTextSecondary,
          fontWeight = FontWeight.Medium
        )
      }

      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = evidence.evidenceSummary,
        fontSize = 11.sp,
        color = SovereignTextPrimary,
        lineHeight = 16.sp
      )

      Spacer(modifier = Modifier.height(6.dp))
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.Link,
          contentDescription = null,
          tint = SovereignSky,
          modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "ஆதாரங்கள்: " + evidence.sources.joinToString(", "),
          fontSize = 9.sp,
          color = SovereignSky,
          fontFamily = FontFamily.Monospace
        )
      }
    }
  }
}

// -------------------------------------------------------------
// TAMIL VOICE SETTINGS MODAL
// -------------------------------------------------------------
@Composable
fun TamilVoiceSettingsModal(
  profile: TamilVoiceProfile,
  onDismiss: () -> Unit,
  onSelectStyle: (VoiceStyle) -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text("சரி (OK)", color = SovereignCyan, fontWeight = FontWeight.Bold)
      }
    },
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Default.RecordVoiceOver, contentDescription = null, tint = SovereignEmerald)
        Spacer(modifier = Modifier.width(8.dp))
        Text("தமிழ் குரல் நுண்ணறிவு அமைப்புகள்", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
      }
    },
    text = {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = "AI உங்கள் பேச்சை இயல்பாகப் புரிந்து கொள்ளும் வகையில் வடிவமைக்கப்பட்டுள்ளது. சூழலுக்கு ஏற்ப குரல் பாணியைத் தேர்வு செய்யலாம்:",
          fontSize = 12.sp,
          color = SovereignTextSecondary,
          lineHeight = 17.sp
        )
        Spacer(modifier = Modifier.height(12.dp))

        VoiceStyle.values().forEach { style ->
          val isSelected = profile.activeVoiceStyle == style
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (isSelected) SovereignEmeraldDark.copy(alpha = 0.3f) else SovereignSurfaceElevated,
            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) SovereignEmerald else SovereignBorder),
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 6.dp)
              .clickable { onSelectStyle(style) }
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                Text(
                  text = style.labelTa,
                  fontSize = 12.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) SovereignEmerald else SovereignTextPrimary
                )
                Text(
                  text = "வேகம்: ${style.speedFactor}x • சுருதி: ${style.pitchHz}Hz",
                  fontSize = 10.sp,
                  color = SovereignTextMuted
                )
              }
              if (isSelected) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SovereignEmerald, modifier = Modifier.size(16.dp))
              }
            }
          }
        }
      }
    },
    containerColor = SovereignSurface,
    shape = RoundedCornerShape(16.dp)
  )
}

// -------------------------------------------------------------
// COGNITIVE ARCHITECTURE MODAL
// -------------------------------------------------------------
@Composable
fun CognitiveArchitectureModal(
  currentStage: CognitivePipelineStage,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text("மூடு (Close)", color = SovereignCyan, fontWeight = FontWeight.Bold)
      }
    },
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Default.Hub, contentDescription = null, tint = SovereignCyan)
        Spacer(modifier = Modifier.width(8.dp))
        Text("10-அடுக்கு அறிவாற்றல் சுழற்சி (Cognitive OS)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
      }
    },
    text = {
      LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
          Text(
            text = "AI பார்க்கும் ➔ புரிந்துகொள்ளும் ➔ நினைவில் வைக்கும் ➔ யோசிக்கும் ➔ திட்டமிடும் ➔ சோதிக்கும் ➔ செயல்படுத்தும் ➔ கற்றுக்கொள்ளும்:",
            fontSize = 11.sp,
            color = SovereignTextSecondary,
            lineHeight = 16.sp
          )
          Spacer(modifier = Modifier.height(10.dp))
        }
        items(CognitivePipelineStage.values().filter { it != CognitivePipelineStage.IDLE }) { stage ->
          val isActive = currentStage == stage
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (isActive) SovereignCyanDark else SovereignSurfaceElevated,
            border = androidx.compose.foundation.BorderStroke(1.dp, if (isActive) SovereignCyan else SovereignBorder),
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 6.dp)
          ) {
            Row(
              modifier = Modifier.padding(8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(22.dp)
                  .clip(CircleShape)
                  .background(if (isActive) SovereignCyan else SovereignBorder),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = stage.stageNumber.toString(),
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isActive) Color.Black else SovereignTextSecondary
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = stage.labelTa,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isActive) SovereignCyan else SovereignTextPrimary
                )
                Text(
                  text = stage.labelEn,
                  fontSize = 9.sp,
                  color = SovereignTextMuted
                )
              }
            }
          }
        }
      }
    },
    containerColor = SovereignSurface,
    shape = RoundedCornerShape(16.dp)
  )
}
