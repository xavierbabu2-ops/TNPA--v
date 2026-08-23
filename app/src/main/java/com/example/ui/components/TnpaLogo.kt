package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.TnpaCyan
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedLight
import com.example.ui.theme.TnpaRedPrimary

/**
 * Official Logo Emblem for தமிழ்நாடு பெயிண்டர்கள் முன்னேற்ற சங்கம் (TN PA²)
 * Features:
 * - Bold red circular outer ring
 * - Center white disc with powerful black raised fist holding a paintbrush & paint roller
 * - Bold "TN PA²" branding
 */
@Composable
fun TnpaOfficialEmblem(
  modifier: Modifier = Modifier,
  sizeDp: Dp = 56.dp,
  showBorder: Boolean = true
) {
  Box(
    modifier = modifier
      .size(sizeDp)
      .shadow(elevation = 6.dp, shape = CircleShape)
      .clip(CircleShape)
      .background(TnpaRedPrimary)
      .then(
        if (showBorder) Modifier.border(2.dp, TnpaGold, CircleShape) else Modifier
      ),
    contentAlignment = Alignment.Center
  ) {
    // Red Outer Border with Ring
    Box(
      modifier = Modifier
        .size(sizeDp * 0.76f)
        .clip(CircleShape)
        .background(TnpaPureWhite)
        .border(1.dp, TnpaJetBlack.copy(alpha = 0.2f), CircleShape),
      contentAlignment = Alignment.Center
    ) {
      // Canvas rendering the Raised Fist holding Paint Brush & Roller
      Canvas(modifier = Modifier.size(sizeDp * 0.58f)) {
        val w = size.width
        val h = size.height

        // 1. Paint Roller Handle (Metal Wire)
        val rollerWirePath = Path().apply {
          moveTo(w * 0.48f, h * 0.42f)
          lineTo(w * 0.55f, h * 0.32f)
          lineTo(w * 0.65f, h * 0.34f)
        }
        drawPath(rollerWirePath, color = TnpaJetBlack, style = Stroke(width = w * 0.045f))

        // Paint Roller Cylinder (Top Right)
        val rollerCylinderPath = Path().apply {
          moveTo(w * 0.60f, h * 0.26f)
          lineTo(w * 0.78f, h * 0.36f)
          lineTo(w * 0.72f, h * 0.45f)
          lineTo(w * 0.54f, h * 0.35f)
          close()
        }
        drawPath(rollerCylinderPath, color = TnpaJetBlack, style = Fill)

        // 2. Paint Brush (Diagonal from top left into fist)
        // Brush Handle
        val brushHandlePath = Path().apply {
          moveTo(w * 0.34f, h * 0.22f)
          lineTo(w * 0.46f, h * 0.38f)
        }
        drawPath(brushHandlePath, color = TnpaJetBlack, style = Stroke(width = w * 0.05f))

        // Brush Bristles & Ferrule
        val brushHeadPath = Path().apply {
          moveTo(w * 0.26f, h * 0.12f)
          lineTo(w * 0.38f, h * 0.24f)
          lineTo(w * 0.30f, h * 0.28f)
          lineTo(w * 0.18f, h * 0.16f)
          close()
        }
        drawPath(brushHeadPath, color = TnpaJetBlack, style = Fill)

        // 3. Strong Raised Fist & Forearm (Center Black Silhouette)
        val fistPath = Path().apply {
          // Forearm base
          moveTo(w * 0.40f, h * 0.88f)
          lineTo(w * 0.60f, h * 0.88f)
          lineTo(w * 0.62f, h * 0.58f)
          // Knuckles
          lineTo(w * 0.66f, h * 0.46f)
          lineTo(w * 0.56f, h * 0.40f)
          lineTo(w * 0.44f, h * 0.42f)
          lineTo(w * 0.36f, h * 0.48f)
          // Thumb wrap
          lineTo(w * 0.34f, h * 0.58f)
          lineTo(w * 0.38f, h * 0.68f)
          lineTo(w * 0.40f, h * 0.88f)
          close()
        }
        drawPath(fistPath, color = TnpaJetBlack, style = Fill)

        // Subtle highlight strokes on knuckles for definition
        drawLine(
          color = TnpaPureWhite,
          start = Offset(w * 0.44f, h * 0.48f),
          end = Offset(w * 0.44f, h * 0.58f),
          strokeWidth = w * 0.025f
        )
        drawLine(
          color = TnpaPureWhite,
          start = Offset(w * 0.52f, h * 0.46f),
          end = Offset(w * 0.52f, h * 0.57f),
          strokeWidth = w * 0.025f
        )
        drawLine(
          color = TnpaPureWhite,
          start = Offset(w * 0.60f, h * 0.48f),
          end = Offset(w * 0.60f, h * 0.59f),
          strokeWidth = w * 0.025f
        )
      }
    }
  }
}

/**
 * Official Flag Component of தமிழ்நாடு பெயிண்டர்கள் முன்னேற்ற சங்கம் (TN PA²)
 * Features:
 * - Metallic Silver Pole with spherical finial
 * - Diagonal Bicolor Banner: Top-Left Red, Bottom-Right Silk White
 * - Central Circular TNPA² Seal
 */
@Composable
fun TnpaOfficialFlagBanner(
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .shadow(4.dp, RoundedCornerShape(12.dp)),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaJetBlack),
    border = androidx.compose.foundation.BorderStroke(1.dp, TnpaGold.copy(alpha = 0.6f))
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Flagpole + Diagonal Flag Canvas
      Box(
        modifier = Modifier
          .size(width = 110.dp, height = 75.dp)
          .clip(RoundedCornerShape(6.dp))
          .background(Color(0xFF0F172A)),
        contentAlignment = Alignment.Center
      ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
          val w = size.width
          val h = size.height

          // Silver Flag Pole (Left)
          drawLine(
            brush = Brush.verticalGradient(
              listOf(Color(0xFFE2E8F0), Color(0xFF94A3B8), Color(0xFF475569))
            ),
            start = Offset(w * 0.12f, h * 0.05f),
            end = Offset(w * 0.12f, h * 0.95f),
            strokeWidth = 5f
          )
          // Pole top finial (Golden sphere)
          drawCircle(
            color = TnpaGold,
            radius = 4.5f,
            center = Offset(w * 0.12f, h * 0.06f)
          )

          // Flag Fabric Rectangle (Attached to pole)
          val flagLeft = w * 0.14f
          val flagRight = w * 0.92f
          val flagTop = h * 0.10f
          val flagBottom = h * 0.85f

          // Top-Left Half: Vibrant Red Triangle
          val redPath = Path().apply {
            moveTo(flagLeft, flagTop)
            lineTo(flagRight, flagTop)
            lineTo(flagLeft, flagBottom)
            close()
          }
          drawPath(redPath, color = TnpaRedPrimary, style = Fill)

          // Bottom-Right Half: Pure Silk White Triangle
          val whitePath = Path().apply {
            moveTo(flagRight, flagTop)
            lineTo(flagRight, flagBottom)
            lineTo(flagLeft, flagBottom)
            close()
          }
          drawPath(whitePath, color = TnpaPureWhite, style = Fill)

          // Flag Outline Border
          drawRect(
            color = Color(0xFF1E293B),
            topLeft = Offset(flagLeft, flagTop),
            size = Size(flagRight - flagLeft, flagBottom - flagTop),
            style = Stroke(width = 1.2f)
          )

          // Center Circular TNPA² Emblem on the flag
          val centerFlagX = (flagLeft + flagRight) / 2
          val centerFlagY = (flagTop + flagBottom) / 2
          val emblemRadius = (flagBottom - flagTop) * 0.28f

          // Outer Gold/Red ring of center emblem
          drawCircle(
            color = TnpaGold,
            radius = emblemRadius + 2f,
            center = Offset(centerFlagX, centerFlagY)
          )
          drawCircle(
            color = TnpaRedPrimary,
            radius = emblemRadius,
            center = Offset(centerFlagX, centerFlagY)
          )
          drawCircle(
            color = TnpaPureWhite,
            radius = emblemRadius * 0.72f,
            center = Offset(centerFlagX, centerFlagY)
          )
          // Black Fist dot representation
          drawCircle(
            color = TnpaJetBlack,
            radius = emblemRadius * 0.45f,
            center = Offset(centerFlagX, centerFlagY)
          )
        }
      }

      // Flag Description Text
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(3.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(3.dp))
              .background(TnpaRedPrimary)
              .padding(horizontal = 5.dp, vertical = 1.dp)
          ) {
            Text(
              text = "சங்கக் கொடி",
              color = TnpaPureWhite,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold
            )
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "TN PA² OFFICIAL FLAG",
            color = TnpaGold,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black
          )
        }

        Text(
          text = "நமது சங்கம் நமது உரிமை",
          color = TnpaGold,
          fontSize = 13.sp,
          fontWeight = FontWeight.Black
        )

        Text(
          text = "தமிழ்நாடு ஓவியர்கள் & பெயிண்டர்கள் முன்னேற்ற சங்கம் (TN PA²)",
          color = TnpaPureWhite,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          lineHeight = 15.sp
        )
      }
    }
  }
}

/**
 * Full Association Header Brand with Title and Subtitle in Red, White and Black
 */
@Composable
fun TnpaBrandHeader(
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    TnpaOfficialEmblem(sizeDp = 44.dp)
    Spacer(modifier = Modifier.width(10.dp))
    Column {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "TN PA² சங்கம்",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Black,
          color = TnpaJetBlack
        )
        Spacer(modifier = Modifier.width(6.dp))
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(TnpaRedPrimary)
            .padding(horizontal = 5.dp, vertical = 1.dp)
        ) {
          Text(
            text = "பதிவுபெற்றது",
            color = TnpaPureWhite,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
      Text(
        text = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் (TNPA²)",
        style = MaterialTheme.typography.labelSmall,
        color = TnpaRedDark,
        fontWeight = FontWeight.SemiBold,
        maxLines = 1
      )
    }
  }
}

