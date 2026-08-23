package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlinx.coroutines.launch

data class NavigationTabItem(
  val index: Int,
  val titleTamil: String,
  val icon: ImageVector,
  val badgeText: String? = null,
  val badgeColor: Color? = null,
  val testTag: String
)

/**
 * Enhanced, ultra-responsive scrollable navigation bar for TNPA App.
 * Solves scrolling friction by offering:
 * - Smooth momentum scrolling with rememberScrollState
 * - Auto-scroll centering when tab changes
 * - Left and Right fast navigation arrow chips
 * - High-contrast active capsule indicators
 */
@Composable
fun TnpaTopNavBar(
  selectedTab: Int,
  onTabSelected: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()
  val coroutineScope = rememberCoroutineScope()

  val tabs = remember {
    listOf(
      NavigationTabItem(0, "முகப்பு (Home)", Icons.Default.Home, null, null, "tab_home"),
      NavigationTabItem(1, "உறுப்பினர் பதிவு", Icons.Default.Badge, "ID CARD", TnpaGold, "tab_register"),
      NavigationTabItem(2, "TNPA² TV", Icons.Default.LiveTv, "LIVE", TnpaRedPrimary, "tab_live_tv"),
      NavigationTabItem(3, "வீடியோ கான்பிரன்ஸ்", Icons.Default.VideoCall, "MEET", TnpaGreen, "tab_video_conference"),
      NavigationTabItem(4, "🤖 AI வழிகாட்டி", Icons.Default.Psychology, "AI", Color(0xFF6366F1), "tab_ai_monitoring"),
      NavigationTabItem(5, "ஓவியக் கலைக்கூடம்", Icons.Default.Palette, null, null, "tab_art_gallery"),
      NavigationTabItem(6, "நலவாரியங்கள்", Icons.Default.VolunteerActivism, "GOVT", TnpaCyan, "tab_welfare"),
      NavigationTabItem(7, "நிர்வாகிகள்", Icons.Default.People, "8 LEVELS", TnpaRedDark, "tab_officers"),
      NavigationTabItem(8, "Admin கட்டுப்பாடு", Icons.Default.AdminPanelSettings, "ADMIN", TnpaGold, "tab_admin"),
      NavigationTabItem(9, "இணையதளம் (Web)", Icons.Default.Language, null, null, "tab_web_view")
    )
  }

  // Smoothly scroll to active tab on change
  LaunchedEffect(selectedTab) {
    val approxTabWidthPx = 360
    val targetScroll = (selectedTab * approxTabWidthPx - 200).coerceAtLeast(0)
    scrollState.animateScrollTo(targetScroll)
  }

  Surface(
    modifier = modifier.fillMaxWidth(),
    color = TnpaPureWhite,
    shadowElevation = 4.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp, horizontal = 4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Left Scroll Arrow (if can scroll back)
      if (scrollState.value > 10) {
        IconButton(
          onClick = {
            coroutineScope.launch {
              scrollState.animateScrollTo((scrollState.value - 300).coerceAtLeast(0))
            }
          },
          modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(TnpaRedSoft)
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Scroll Left",
            tint = TnpaRedPrimary,
            modifier = Modifier.size(16.dp)
          )
        }
        Spacer(modifier = Modifier.width(4.dp))
      }

      // Horizontal Scrollable Tab List
      Row(
        modifier = Modifier
          .weight(1f)
          .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Spacer(modifier = Modifier.width(4.dp))

        tabs.forEach { tab ->
          val isSelected = selectedTab == tab.index

          val containerBg = if (isSelected) {
            Brush.horizontalGradient(listOf(TnpaRedPrimary, TnpaRedDark))
          } else {
            Brush.horizontalGradient(listOf(TnpaPureWhite, TnpaOffWhite))
          }

          val borderColor = if (isSelected) TnpaGold else Color(0xFFE2E8F0)

          Box(
            modifier = Modifier
              .shadow(if (isSelected) 4.dp else 1.dp, RoundedCornerShape(12.dp))
              .clip(RoundedCornerShape(12.dp))
              .background(containerBg)
              .border(
                BorderStroke(if (isSelected) 1.5.dp else 1.dp, borderColor),
                RoundedCornerShape(12.dp)
              )
              .clickable { onTabSelected(tab.index) }
              .padding(horizontal = 12.dp, vertical = 7.dp)
              .testTag(tab.testTag),
            contentAlignment = Alignment.Center
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Icon(
                imageVector = tab.icon,
                contentDescription = tab.titleTamil,
                tint = if (isSelected) TnpaGold else TnpaRedPrimary,
                modifier = Modifier.size(17.dp)
              )

              Text(
                text = tab.titleTamil,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                color = if (isSelected) TnpaPureWhite else TnpaJetBlack
              )

              if (tab.badgeText != null) {
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(tab.badgeColor ?: TnpaGold)
                    .padding(horizontal = 5.dp, vertical = 1.dp)
                ) {
                  Text(
                    text = tab.badgeText,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Black,
                    color = if (tab.badgeColor == TnpaRedPrimary || tab.badgeColor == TnpaRedDark) TnpaPureWhite else TnpaJetBlack
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.width(4.dp))
      }

      // Right Scroll Arrow
      if (scrollState.value < scrollState.maxValue - 10) {
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(
          onClick = {
            coroutineScope.launch {
              scrollState.animateScrollTo((scrollState.value + 300).coerceAtMost(scrollState.maxValue))
            }
          },
          modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(TnpaRedSoft)
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Scroll Right",
            tint = TnpaRedPrimary,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }
  }
}
