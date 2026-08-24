package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val SovereignDarkColorScheme: ColorScheme = darkColorScheme(
  primary = SovereignCyan,
  onPrimary = SovereignBackground,
  primaryContainer = SovereignSurfaceElevated,
  onPrimaryContainer = SovereignCyan,
  secondary = SovereignPurple,
  onSecondary = SovereignBackground,
  secondaryContainer = SovereignSurfaceHover,
  onSecondaryContainer = SovereignTextPrimary,
  tertiary = SovereignGold,
  onTertiary = SovereignBackground,
  background = SovereignBackground,
  onBackground = SovereignTextPrimary,
  surface = SovereignSurface,
  onSurface = SovereignTextPrimary,
  surfaceVariant = SovereignSurfaceElevated,
  onSurfaceVariant = SovereignTextSecondary,
  outline = SovereignBorder,
  outlineVariant = SovereignBorderGlow
)

val SovereignLightColorScheme: ColorScheme = lightColorScheme(
  primary = SovereignSky,
  onPrimary = Color.White,
  primaryContainer = Color(0xFFE0F2FE),
  onPrimaryContainer = Color(0xFF0369A1),
  secondary = SovereignPurple,
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFF3E8FF),
  onSecondaryContainer = Color(0xFF6B21A8),
  tertiary = SovereignGold,
  onTertiary = Color.White,
  background = Color(0xFFF8FAFC),
  onBackground = Color(0xFF0F172A),
  surface = Color.White,
  onSurface = Color(0xFF0F172A),
  surfaceVariant = Color(0xFFF1F5F9),
  onSurfaceVariant = Color(0xFF475569),
  outline = Color(0xFFCBD5E1),
  outlineVariant = SovereignSky
)

@Composable
fun SovereignTheme(
  darkTheme: Boolean = true, // Default to deep space futuristic dark theme
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) SovereignDarkColorScheme else SovereignLightColorScheme
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

@Composable
fun TnpaTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  SovereignTheme(darkTheme = true, content = content)
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  SovereignTheme(darkTheme = true, content = content)
}



