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
import com.example.ui.theme.*

/**
 * Cybernetic Deep-Space Holographic Wallpaper for பாபு (Babu - Personal Sovereign AI)
 */
@Composable
fun BabuCyberGridWallpaper(
  modifier: Modifier = Modifier,
  content: @Composable BoxScope.() -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            SovereignBackground,
            SovereignSurface,
            SovereignBackground
          )
        )
      )
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val w = size.width
      val h = size.height

      // Top-Right Cyan Quantum Wave
      val pathTop = Path().apply {
        moveTo(w * 0.3f, 0f)
        cubicTo(w * 0.6f, h * 0.05f, w * 0.7f, h * 0.15f, w, h * 0.10f)
        lineTo(w, 0f)
        close()
      }
      drawPath(
        path = pathTop,
        brush = Brush.linearGradient(
          colors = listOf(SovereignCyan.copy(alpha = 0.08f), SovereignViolet.copy(alpha = 0.04f)),
          start = Offset(w * 0.3f, 0f),
          end = Offset(w, h * 0.10f)
        ),
        style = Fill
      )

      // Bottom-Left Purple Nebula Wave
      val pathBottom = Path().apply {
        moveTo(0f, h * 0.85f)
        cubicTo(w * 0.3f, h * 0.82f, w * 0.5f, h * 0.95f, w * 0.8f, h)
        lineTo(0f, h)
        close()
      }
      drawPath(
        path = pathBottom,
        brush = Brush.linearGradient(
          colors = listOf(SovereignPurple.copy(alpha = 0.06f), SovereignCyan.copy(alpha = 0.02f)),
          start = Offset(0f, h * 0.85f),
          end = Offset(w * 0.8f, h)
        )
      )

      // Quantum Energy Nodes
      drawCircle(
        color = SovereignCyan.copy(alpha = 0.08f),
        radius = 14f,
        center = Offset(w * 0.88f, h * 0.18f)
      )
      drawCircle(
        color = SovereignPurple.copy(alpha = 0.06f),
        radius = 8f,
        center = Offset(w * 0.92f, h * 0.22f)
      )
      drawCircle(
        color = SovereignEmerald.copy(alpha = 0.06f),
        radius = 12f,
        center = Offset(w * 0.08f, h * 0.65f)
      )
    }

    content()
  }
}
