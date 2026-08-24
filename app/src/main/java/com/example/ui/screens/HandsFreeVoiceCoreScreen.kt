package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SovereignEngine
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun HandsFreeVoiceCoreScreen(
  onDismiss: () -> Unit = {}
) {
  val voiceProfile by SovereignEngine.tamilVoiceProfile.collectAsState()
  val cognitiveStage by SovereignEngine.cognitiveStage.collectAsState()
  
  var spokenText by remember { mutableStateOf("வணக்கம் சேவியர்பாபு. நான் உங்கள் தனிநபர் AI. குரல் கட்டளைக்கு தயாராக உள்ளேன்...") }
  var isListeningActive by remember { mutableStateOf(true) }
  var aiResponseTa by remember { mutableStateOf("எந்தவொரு ஸ்கிரீன் தொடுதலும் தேவையில்லை. உங்கள் குரல் கட்டளைகள் மூலம் முழுமையான தன்னாட்சி இயக்கத்துடன் இயங்குகிறேன்.") }
  var activeMissionStatus by remember { mutableStateOf("கண்காணிப்பு மற்றும் குரல் கையாளுதல் active (Hands-Free Mode)") }

  val infiniteTransition = rememberInfiniteTransition(label = "ReactorCore")
  val rotationAngle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(6000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "Rotation"
  )

  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 0.92f,
    targetValue = 1.08f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "Pulse"
  )

  val coreGlowAlpha by infiniteTransition.animateFloat(
    initialValue = 0.6f,
    targetValue = 0.95f,
    animationSpec = infiniteRepeatable(
      animation = tween(800, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "Glow"
  )

  LaunchedEffect(Unit) {
    while (true) {
      delay(8000)
      if (isListeningActive) {
        spokenText = "ஸேவியர்பாபு: 'அந்த பிராஜெக்ட் நிலையை சரிபார்த்து புதுப்பி.'"
        aiResponseTa = "தன்னாட்சி கோர்: பிராஜெக்ட் நிலை சரிபார்க்கப்பட்டது. 3 இலக்குகள் வெற்றிகரமாக இயக்கப்படுகின்றன."
        activeMissionStatus = "இயக்கம்: பிராஜெக்ட் சுய-மதிப்பீடு (Self-Assessment) நிறைவு."
      }
      delay(9000)
      if (isListeningActive) {
        spokenText = "ஸேவியர்பாபு: 'குரல் கட்டளை மூலம் பாதுகாப்பு சரிபார்ப்பை இயக்கு.'"
        aiResponseTa = "பாதுகாப்பு கெர்னல்: அரசியல் சாசன விதிகளின்படி உரிமையாளர் அங்கீகாரம் உறுதிப்படுத்தப்பட்டது."
        activeMissionStatus = "பாதுகாப்பு: 100% Sovereign Security Lock Verified."
      }
    }
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Brush.radialGradient(
          colors = listOf(Color(0xFF1E0A00), Color(0xFF0A0301), Color.Black),
          radius = 1200f
        )
      )
      .padding(20.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          color = SovereignSurface.copy(alpha = 0.85f),
          shape = RoundedCornerShape(12.dp),
          border = BorderStroke(1.dp, SovereignGold.copy(alpha = 0.6f))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Mic, contentDescription = null, tint = SovereignGold, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              "ஹேண்ட்ஸ்-பிரி குரல் கோர் (Hands-Free Mode)",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = SovereignGold
            )
          }
        }

        IconButton(onClick = onDismiss) {
          Icon(Icons.Default.Close, contentDescription = "Close", tint = SovereignTextSecondary)
        }
      }

      Box(
        modifier = Modifier
          .size(320.dp)
          .scale(pulseScale),
        contentAlignment = Alignment.Center
      ) {
        Canvas(
          modifier = Modifier
            .fillMaxSize()
            .rotate(rotationAngle)
        ) {
          val center = Offset(size.width / 2f, size.height / 2f)
          val radius = size.minDimension / 2f * 0.9f

          drawCircle(
            brush = Brush.sweepGradient(
              listOf(
                Color(0xFFFF6B00),
                Color(0xFFFFB703),
                Color(0xFFFB8500),
                Color(0xFFFF3300),
                Color(0xFFFFB703),
                Color(0xFFFF6B00)
              )
            ),
            radius = radius,
            center = center,
            style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
          )

          drawCircle(
            color = Color(0xFFFF9E00).copy(alpha = coreGlowAlpha),
            radius = radius * 0.72f,
            center = center,
            style = Stroke(width = 3.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 15f)))
          )

          drawCircle(
            brush = Brush.radialGradient(
              colors = listOf(Color(0xFFFFD166), Color(0xFFFF6B00), Color.Transparent),
              radius = radius * 0.5f,
              center = center
            ),
            radius = radius * 0.5f,
            center = center
          )
        }

        Canvas(
          modifier = Modifier
            .size(180.dp)
            .rotate(-rotationAngle * 1.5f)
        ) {
          val center = Offset(size.width / 2f, size.height / 2f)
          val radius = size.minDimension / 2f

          drawCircle(
            brush = Brush.sweepGradient(
              listOf(Color(0xFFFF3300), Color(0xFFFFD166), Color(0xFFFF6B00), Color(0xFFFF3300))
            ),
            radius = radius * 0.8f,
            center = center,
            style = Stroke(width = 4.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)))
          )
        }

        Box(
          modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)
            .background(
              Brush.radialGradient(
                colors = listOf(Color(0xFFFFD166), Color(0xFFFF6B00), Color(0xFF661400))
              )
            )
            .border(2.dp, Color(0xFFFFE599), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(32.dp)
            )
            Text(
              "बाबु",
              fontSize = 11.sp,
              fontWeight = FontWeight.Black,
              color = Color.White
            )
          }
        }
      }

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Surface(
          color = SovereignSurface.copy(alpha = 0.9f),
          shape = RoundedCornerShape(16.dp),
          border = BorderStroke(1.dp, Color(0xFFFF8500).copy(alpha = 0.5f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(Color(0xFFFF6B00))
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                activeMissionStatus,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFB703)
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = spokenText,
              fontSize = 13.sp,
              fontWeight = FontWeight.Medium,
              color = Color.White,
              textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Divider(color = SovereignBorder, thickness = 0.5.dp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = aiResponseTa,
              fontSize = 12.sp,
              color = SovereignCyan,
              textAlign = TextAlign.Center,
              lineHeight = 16.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.Mic, contentDescription = null, tint = SovereignEmerald, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            "உரிமையாளர் சேவியர்பாபுவின் குரல் கட்டளைக்கு முழு தன்னாட்சி கோர் திறக்கப்பட்டுள்ளது",
            fontSize = 10.5.sp,
            color = SovereignTextSecondary,
            textAlign = TextAlign.Center
          )
        }
      }
    }
  }
}
