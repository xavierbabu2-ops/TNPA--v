package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// TNPA Custom Color Schemes based on Brand Identity:
// Primary Red (#E60000), Secondary White (#FFFFFF), Accent Black (#1A1A1A)

val TnpaLightColorScheme: ColorScheme = lightColorScheme(
  primary = TnpaRedPrimary,            // Primary Red #E60000
  onPrimary = TnpaPureWhite,          // Secondary White #FFFFFF
  primaryContainer = TnpaRedSoft,
  onPrimaryContainer = TnpaRedDark,
  secondary = TnpaPureWhite,          // Secondary White #FFFFFF
  onSecondary = TnpaJetBlack,         // Accent Black #1A1A1A
  secondaryContainer = TnpaOffWhite,
  onSecondaryContainer = TnpaJetBlack,
  tertiary = TnpaJetBlack,            // Accent Black #1A1A1A
  onTertiary = TnpaPureWhite,
  background = TnpaOffWhite,
  onBackground = TnpaJetBlack,
  surface = TnpaPureWhite,
  onSurface = TnpaRedPrimary,         // Red typed text color
  surfaceVariant = TnpaRedSoft,
  onSurfaceVariant = TnpaRedDark,
  outline = TnpaRedPrimary.copy(alpha = 0.35f),
  outlineVariant = TnpaBlackAccent.copy(alpha = 0.15f)
)

val TnpaDarkColorScheme: ColorScheme = darkColorScheme(
  primary = TnpaRedLight,             // Bright Red on Dark
  onPrimary = TnpaPureWhite,
  primaryContainer = TnpaRedDark,
  onPrimaryContainer = TnpaPureWhite,
  secondary = TnpaPureWhite,          // Secondary White #FFFFFF
  onSecondary = TnpaJetBlack,
  secondaryContainer = TnpaDarkCard,
  onSecondaryContainer = TnpaPureWhite,
  tertiary = TnpaJetBlack,            // Accent Black #1A1A1A
  onTertiary = TnpaPureWhite,
  background = TnpaJetBlack,          // Accent Black #1A1A1A
  onBackground = TnpaPureWhite,
  surface = TnpaDarkCard,
  onSurface = TnpaRedLight,           // Red typed text color
  surfaceVariant = TnpaBlackAccent,
  onSurfaceVariant = TnpaRedSoft,
  outline = TnpaRedPrimary,
  outlineVariant = TnpaCharcoal
)

@Composable
fun TnpaTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) TnpaDarkColorScheme else TnpaLightColorScheme
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  TnpaTheme(darkTheme = darkTheme, content = content)
}


