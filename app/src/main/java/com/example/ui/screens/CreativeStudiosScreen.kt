package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SovereignEngine
import com.example.model.*
import com.example.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CreativeStudiosScreen() {
  var selectedStudioTab by remember { mutableIntStateOf(0) }
  val studioTabs = listOf(
    "🎨 பட ஸ்டுடியோ" to Icons.Default.Image,
    "🎬 வீடியோ பைப்லைன்" to Icons.Default.VideoLibrary,
    "📱 சோஷியல் மீடியா" to Icons.Default.Share,
    "🤖 ஹைப்ரிட் குரல்" to Icons.Default.GraphicEq,
    "🌐 3D ஹாலோகிராம்" to Icons.Default.ViewInAr
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(SovereignBackground)
  ) {
    // Studio Sub-Tab Bar
    ScrollableTabRow(
      selectedTabIndex = selectedStudioTab,
      containerColor = SovereignSurface,
      contentColor = SovereignCyan,
      edgePadding = 12.dp,
      divider = {}
    ) {
      studioTabs.forEachIndexed { index, (title, icon) ->
        Tab(
          selected = selectedStudioTab == index,
          onClick = { selectedStudioTab = index },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (selectedStudioTab == index) SovereignCyan else SovereignTextMuted,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = title,
                color = if (selectedStudioTab == index) SovereignTextPrimary else SovereignTextSecondary,
                fontSize = 12.sp,
                fontWeight = if (selectedStudioTab == index) FontWeight.Bold else FontWeight.Medium
              )
            }
          }
        )
      }
    }

    // Studio Body
    Box(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
    ) {
      when (selectedStudioTab) {
        0 -> ImageStudioSubScreen()
        1 -> VideoPipelineSubScreen()
        2 -> SocialContentStudioScreen()
        3 -> HybridVoiceStudio()
        4 -> SpatialHologramSubScreen()
      }
    }
  }
}

// -------------------------------------------------------------
// 1. IMAGE STUDIO
// -------------------------------------------------------------

@Composable
fun ImageStudioSubScreen() {
  val projects by SovereignEngine.imageProjects.collectAsState()
  var customPrompt by remember { mutableStateOf("") }
  var selectedStyle by remember { mutableStateOf(ImageStyle.FUTURISTIC_CONCEPT) }
  var selectedRatio by remember { mutableStateOf("16:9") }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Generation Control Card
    item {
      Surface(
        color = SovereignSurfaceElevated,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorderGlow.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Palette, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "படைப்பாற்றல் பட உலை (Image Generator)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = SovereignTextPrimary
              )
            }
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(SovereignEmerald.copy(alpha = 0.2f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text("Local-First AI", color = SovereignEmerald, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = customPrompt,
            onValueChange = { customPrompt = it },
            placeholder = {
              Text("நீங்கள் கற்பனை செய்யும் காட்சியை தமிழில் விவரியுங்கள்...", color = SovereignTextMuted, fontSize = 12.sp)
            },
            modifier = Modifier.fillMaxWidth().testTag("input_image_studio_prompt"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = SovereignSurface,
              unfocusedContainerColor = SovereignSurface,
              focusedBorderColor = SovereignCyan,
              unfocusedBorderColor = SovereignBorder,
              focusedTextColor = SovereignTextPrimary,
              unfocusedTextColor = SovereignTextPrimary
            ),
            maxLines = 3
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Style Selector Chips
          Text("பாணி (Style):", fontSize = 11.sp, color = SovereignSky, fontWeight = FontWeight.SemiBold)
          Spacer(modifier = Modifier.height(4.dp))
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            ImageStyle.values().forEach { style ->
              val isSelected = selectedStyle == style
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) SovereignPurple else SovereignSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) SovereignCyan else SovereignBorder),
                modifier = Modifier.clickable { selectedStyle = style }
              ) {
                Text(
                  text = style.labelTa,
                  color = if (isSelected) Color.White else SovereignTextSecondary,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Medium,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Button(
            onClick = {
              if (customPrompt.isNotBlank()) {
                SovereignEngine.sendUserPrompt("படம் உருவாக்கு: $customPrompt", MultimodalInputType.IMAGE)
                customPrompt = ""
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = SovereignCyan),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().testTag("btn_generate_image_asset")
          ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = SovereignBackground, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("உருவாக்கு & சரிபார் (Generate & Verify)", color = SovereignBackground, fontSize = 12.sp, fontWeight = FontWeight.Black)
          }
        }
      }
    }

    // Projects Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "உருவாக்கப்பட்ட படங்கள் (${projects.size})",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = SovereignTextPrimary
        )
        Text("Total High-Res 4K", fontSize = 10.sp, color = SovereignTextMuted)
      }
    }

    // Projects List
    items(projects, key = { it.id }) { item ->
      Surface(
        shape = RoundedCornerShape(14.dp),
        color = SovereignSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(200.dp)
          ) {
            AsyncImage(
              model = item.generatedImageUrl,
              contentDescription = item.title,
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )

            // Quality Badge
            Box(
              modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(SovereignBackground.copy(alpha = 0.85f))
                .border(1.dp, SovereignEmerald, RoundedCornerShape(6.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text("QA Score ${item.qualityScore}% ✓", color = SovereignEmerald, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }

            // Aspect Ratio badge
            Box(
              modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(SovereignSurface.copy(alpha = 0.85f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(item.style.labelTa, color = SovereignCyan, fontSize = 9.sp, fontWeight = FontWeight.SemiBold)
            }
          }

          Column(modifier = Modifier.padding(12.dp)) {
            Text(item.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(item.promptExpansionTamil, fontSize = 11.sp, color = SovereignTextSecondary, lineHeight = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("Prompt: ${item.prompt.take(45)}...", fontSize = 9.sp, color = SovereignTextMuted)
              Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                IconButton(
                  onClick = {},
                  modifier = Modifier.size(28.dp).clip(CircleShape).background(SovereignSurface)
                ) {
                  Icon(Icons.Default.Download, contentDescription = "Download", tint = SovereignSky, modifier = Modifier.size(14.dp))
                }
                IconButton(
                  onClick = {},
                  modifier = Modifier.size(28.dp).clip(CircleShape).background(SovereignSurface)
                ) {
                  Icon(Icons.Default.Share, contentDescription = "Share", tint = SovereignPurple, modifier = Modifier.size(14.dp))
                }
              }
            }
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 2. VIDEO PIPELINE STUDIO
// -------------------------------------------------------------

@Composable
fun VideoPipelineSubScreen() {
  val videoProjects by SovereignEngine.videoProjects.collectAsState()
  val activeProject = videoProjects.firstOrNull()

  if (activeProject == null) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      Text("வீடியோ திட்டங்கள் எதுவும் இல்லை", color = SovereignTextMuted)
    }
    return
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Pipeline Overview Card
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = SovereignSurfaceElevated,
      border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorderGlow.copy(alpha = 0.5f)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Movie, contentDescription = null, tint = SovereignPurple, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(activeProject.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          }
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(SovereignEmerald.copy(alpha = 0.2f))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text("${activeProject.targetDurationSec} Sec 4K", color = SovereignEmerald, fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 6 Pipeline Steps Flow
        Text("முழுமையான வீடியோ பைப்லைன் நிலைகள்:", fontSize = 11.sp, color = SovereignSky, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          listOf("1.யோசனை", "2.திரைக்கதை", "3.ஸ்டோரிபோர்டு", "4.இயக்கம்", "5.குரல் & இசை", "6.ரெண்டர்").forEachIndexed { i, step ->
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = SovereignSurface,
              border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SovereignEmerald, modifier = Modifier.size(11.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(step, color = SovereignTextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("📜 தமிழ் திரைக்கதை:", fontSize = 11.sp, color = SovereignGold, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(2.dp))
        Text(activeProject.scriptTamil, fontSize = 11.sp, color = SovereignTextSecondary, lineHeight = 16.sp)

        Spacer(modifier = Modifier.height(8.dp))
        Text("🎵 பின்னணி ஆடியோ ட்ராக்: ${activeProject.audioTrackName}", fontSize = 10.sp, color = SovereignCyan)
      }
    }

    // 6-Frame Storyboard Visual Grid
    Text("ஸ்டோரிபோர்டு பிரேம்கள் (6-Frame Visual Sequence):", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)

    activeProject.storyboardFrames.forEach { frame ->
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(90.dp, 65.dp)
              .clip(RoundedCornerShape(8.dp))
          ) {
            AsyncImage(
              model = frame.frameImageUrl,
              contentDescription = null,
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
            Box(
              modifier = Modifier
                .align(Alignment.BottomStart)
                .background(SovereignBackground.copy(alpha = 0.8f))
                .padding(horizontal = 4.dp, vertical = 1.dp)
            ) {
              Text(frame.timestampSec, color = SovereignCyan, fontSize = 8.sp, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text("காட்சி #${frame.frameNumber}: ${frame.visualDescriptionTa}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Spacer(modifier = Modifier.height(2.dp))
            Text("🎙️ வசனம்: ${frame.voiceoverScript}", fontSize = 10.sp, color = SovereignTextSecondary, lineHeight = 14.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text("🎬 கேமரா இயக்கம்: ${frame.motionDirective}", fontSize = 9.sp, color = SovereignSky)
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 3. AUDIO & VOICE STUDIO
// -------------------------------------------------------------

@Composable
fun AudioStudioSubScreen() {
  val audioProjects by SovereignEngine.audioProjects.collectAsState()
  var inputSpeechText by remember { mutableStateOf("") }
  var selectedVoice by remember { mutableStateOf("Sovereign Tamil Neutral") }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = SovereignSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorderGlow.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Mic, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("குரல் தொகுப்பு உலை (Speech Synthesis Lab)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          }

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = inputSpeechText,
            onValueChange = { inputSpeechText = it },
            placeholder = { Text("குரலாக மாற்ற வேண்டிய வாக்கியங்களை உள்ளிடுக...", color = SovereignTextMuted, fontSize = 12.sp) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = SovereignSurface,
              unfocusedContainerColor = SovereignSurface,
              focusedBorderColor = SovereignCyan,
              unfocusedBorderColor = SovereignBorder,
              focusedTextColor = SovereignTextPrimary,
              unfocusedTextColor = SovereignTextPrimary
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          Button(
            onClick = {
              if (inputSpeechText.isNotBlank()) {
                SovereignEngine.sendUserPrompt("குரல் தொகுப்பு: $inputSpeechText", MultimodalInputType.TAMIL_VOICE)
                inputSpeechText = ""
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = SovereignPurple),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("குரல் உருவாக்கு (Synthesize Voice)", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    item {
      Text("உருவாக்கப்பட்ட ஆடியோ ட்ராக்குகள்:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
    }

    items(audioProjects, key = { it.id }) { audio ->
      Surface(
        shape = RoundedCornerShape(12.dp),
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
            Text(audio.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Text("${audio.durationSec}s", fontSize = 10.sp, color = SovereignCyan, fontWeight = FontWeight.Bold)
          }

          Spacer(modifier = Modifier.height(4.dp))
          Text("\"${audio.spokenText}\"", fontSize = 11.sp, color = SovereignTextSecondary)

          Spacer(modifier = Modifier.height(10.dp))

          // Waveform Canvas Visualizer
          Canvas(
            modifier = Modifier
              .fillMaxWidth()
              .height(36.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(SovereignSurface)
          ) {
            val step = size.width / (audio.waveformData.size + 1)
            audio.waveformData.forEachIndexed { i, amp ->
              val barH = size.height * amp
              drawLine(
                color = SovereignCyan,
                start = Offset((i + 1) * step, size.height / 2 - barH / 2),
                end = Offset((i + 1) * step, size.height / 2 + barH / 2),
                strokeWidth = 6f
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("🎙️ ${audio.voiceName} • ${audio.soundscapeType}", fontSize = 9.sp, color = SovereignSky)
            Icon(Icons.Default.PlayCircle, contentDescription = "Play", tint = SovereignCyan, modifier = Modifier.size(22.dp))
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 4. SPATIAL & 3D HOLOGRAPHIC STUDIO
// -------------------------------------------------------------

@Composable
fun SpatialHologramSubScreen() {
  val models by SovereignEngine.spatialModels.collectAsState()
  var selectedModelIndex by remember { mutableIntStateOf(0) }
  val activeModel = models.getOrElse(selectedModelIndex) { models.first() }

  var rotX by remember { mutableFloatStateOf(25f) }
  var rotY by remember { mutableFloatStateOf(45f) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Model Selector Tabs
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      models.forEachIndexed { index, m ->
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = if (selectedModelIndex == index) SovereignSurfaceHover else SovereignSurfaceElevated,
          border = androidx.compose.foundation.BorderStroke(1.dp, if (selectedModelIndex == index) SovereignCyan else SovereignBorder),
          modifier = Modifier.clickable { selectedModelIndex = index }
        ) {
          Text(
            text = m.name,
            color = if (selectedModelIndex == index) SovereignCyan else SovereignTextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
          )
        }
      }
    }

    // 3D Holographic Canvas
    Surface(
      shape = RoundedCornerShape(18.dp),
      color = SovereignSurface,
      border = androidx.compose.foundation.BorderStroke(1.5.dp, SovereignBorderGlow),
      modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
    ) {
      Box(modifier = Modifier.fillMaxSize()) {
        // Simulated 3D Interactive Canvas
        Canvas(
          modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
              detectDragGestures { _, dragAmount ->
                rotY += dragAmount.x * 0.5f
                rotX += dragAmount.y * 0.5f
              }
            }
        ) {
          val centerX = size.width / 2
          val centerY = size.height / 2
          val radius = 90f

          // Draw Hologram Wireframe Circles & Nodes
          drawCircle(
            color = SovereignCyan.copy(alpha = 0.25f),
            radius = radius * 1.3f,
            center = Offset(centerX, centerY),
            style = Stroke(width = 2f)
          )

          // 3D Nodes based on rotX & rotY
          val angleRadY = Math.toRadians(rotY.toDouble())
          val angleRadX = Math.toRadians(rotX.toDouble())

          val numRings = if (activeModel.isExplodedView) 6 else 3
          val ringSpread = if (activeModel.isExplodedView) 35f else 15f

          for (i in -numRings..numRings) {
            val zOffset = (i * ringSpread * cos(angleRadX)).toFloat()
            val yPos = centerY + (i * ringSpread * sin(angleRadX)).toFloat()

            drawCircle(
              brush = Brush.radialGradient(
                colors = listOf(SovereignCyan.copy(alpha = 0.6f), SovereignPurple.copy(alpha = 0.1f)),
                center = Offset(centerX, yPos),
                radius = radius - Math.abs(i) * 6f
              ),
              center = Offset(centerX, yPos),
              radius = (radius - Math.abs(i) * 6f).coerceAtLeast(15f),
              style = Stroke(width = if (activeModel.isExplodedView) 3f else 1.5f)
            )
          }

          // Central glowing nucleus
          drawCircle(
            brush = Brush.radialGradient(listOf(Color.White, SovereignCyan, Color.Transparent)),
            radius = 18f,
            center = Offset(centerX, centerY)
          )
        }

        // Top Overlay Info
        Column(
          modifier = Modifier
            .align(Alignment.TopStart)
            .padding(12.dp)
        ) {
          Text(activeModel.name, fontSize = 14.sp, fontWeight = FontWeight.Black, color = SovereignTextPrimary)
          Text("Category: ${activeModel.category} • ${activeModel.polygonCount}", fontSize = 10.sp, color = SovereignSky)
          Text("Rotate: விரலால் தொட்டு 360° சுழற்றிப் பார்க்கலாம்", fontSize = 9.sp, color = SovereignTextMuted)
        }

        // Bottom Controls: Explode View & AR mode
        Row(
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(12.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = { SovereignEngine.toggleHologramExplodedView(activeModel.id) },
            colors = ButtonDefaults.buttonColors(
              containerColor = if (activeModel.isExplodedView) SovereignPurple else SovereignSurfaceElevated
            ),
            shape = RoundedCornerShape(8.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SovereignCyan)
          ) {
            Icon(Icons.Default.Splitscreen, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (activeModel.isExplodedView) "ஒருங்கிணை (Assemble)" else "பாகங்களைப் பிரி (Exploded View)",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }
        }
      }
    }

    // Core Parts List
    Text("முக்கிய பாகங்கள் மற்றும் பகுப்பாய்வு:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
    activeModel.coreParts.forEachIndexed { idx, part ->
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = SovereignSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(SovereignCyan)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("பகுதி ${idx + 1}: $part", fontSize = 11.sp, color = SovereignTextPrimary, fontWeight = FontWeight.Medium)
          }
          Text("Verified Model", fontSize = 9.sp, color = SovereignEmerald, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
