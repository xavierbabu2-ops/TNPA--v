package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedLight
import com.example.ui.theme.TnpaRedPrimary

/**
 * Artistic Red & White Wallpaper with paint brush strokes and black accents.
 * Designed for "தமிழ்நாடு பெயிண்டர்கள் ஓவியர்கள் முன்னேற்ற சங்கம்"
 */
@Composable
fun RedWhitePainterWallpaper(
  modifier: Modifier = Modifier,
  content: @Composable BoxScope.() -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            TnpaPureWhite,
            Color(0xFFFFF1F2), // Soft Rose White
            TnpaOffWhite,
            Color(0xFFFFE4E6),
            TnpaPureWhite
          )
        )
      )
  ) {
    // Dynamic Artistic Canvas with Paint Splatters & Brush Strokes in Red, White and Black
    Canvas(modifier = Modifier.fillMaxSize()) {
      val w = size.width
      val h = size.height

      // Top-Right Crimson Brush Wave
      val pathTop = Path().apply {
        moveTo(w * 0.4f, 0f)
        cubicTo(w * 0.7f, h * 0.05f, w * 0.6f, h * 0.15f, w, h * 0.12f)
        lineTo(w, 0f)
        close()
      }
      drawPath(
        path = pathTop,
        brush = Brush.linearGradient(
          colors = listOf(TnpaRedPrimary.copy(alpha = 0.08f), TnpaRedDark.copy(alpha = 0.15f)),
          start = Offset(w * 0.4f, 0f),
          end = Offset(w, h * 0.12f)
        ),
        style = Fill
      )

      // Center Diagonal Painter Texture Wave
      val pathMid = Path().apply {
        moveTo(0f, h * 0.35f)
        cubicTo(w * 0.3f, h * 0.32f, w * 0.7f, h * 0.42f, w, h * 0.38f)
        lineTo(w, h * 0.44f)
        cubicTo(w * 0.6f, h * 0.48f, w * 0.2f, h * 0.40f, 0f, h * 0.42f)
        close()
      }
      drawPath(
        path = pathMid,
        brush = Brush.horizontalGradient(
          colors = listOf(
            TnpaRedLight.copy(alpha = 0.05f),
            TnpaPureWhite.copy(alpha = 0.4f),
            TnpaRedPrimary.copy(alpha = 0.08f)
          )
        )
      )

      // Bottom-Left Scarlet Paint Splash Curve
      val pathBottom = Path().apply {
        moveTo(0f, h * 0.78f)
        cubicTo(w * 0.35f, h * 0.82f, w * 0.45f, h * 0.95f, w * 0.8f, h)
        lineTo(0f, h)
        close()
      }
      drawPath(
        path = pathBottom,
        brush = Brush.linearGradient(
          colors = listOf(TnpaRedPrimary.copy(alpha = 0.07f), TnpaJetBlack.copy(alpha = 0.04f)),
          start = Offset(0f, h * 0.78f),
          end = Offset(w * 0.8f, h)
        )
      )

      // Decorative Artist Paint Droplets (Red, Gold & Black subtle accents)
      drawCircle(
        color = TnpaRedPrimary.copy(alpha = 0.12f),
        radius = 16f,
        center = Offset(w * 0.88f, h * 0.22f)
      )
      drawCircle(
        color = TnpaJetBlack.copy(alpha = 0.06f),
        radius = 9f,
        center = Offset(w * 0.92f, h * 0.25f)
      )
      drawCircle(
        color = TnpaRedDark.copy(alpha = 0.09f),
        radius = 22f,
        center = Offset(w * 0.08f, h * 0.62f)
      )
    }

    // Main App Content Layer
    content()
  }
}
