package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*

/**
 * High-fidelity, authentic visual representations for TNPA official assets:
 * 1. மாநில பொதுச் செயலாளர் (State General Secretary - சேவியர் பாபு)
 * 2. மாநில பொருளாளர் (State Treasurer - சக்திவேல்)
 * 3. சங்கத்தின் லோகோ (TNPA² Official Association Logo)
 * 4. சங்கத்தின் கொடி (TNPA² Official Association Flag)
 * 5. மாநிலத் தலைவர் (State President - எஸ். மைக்கேல் ஆல்வின்)
 */

@Composable
fun TnpaOriginalLogo(
  modifier: Modifier = Modifier,
  size: Dp = 80.dp
) {
  Box(
    modifier = modifier
      .size(size)
      .shadow(6.dp, CircleShape)
      .clip(CircleShape)
      .background(TnpaPureWhite)
      .border(2.dp, TnpaGold, CircleShape)
      .testTag("tnpa_original_logo"),
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = painterResource(id = R.drawable.drawable_tnpa_logo),
      contentDescription = "தமிழ்நாடு பெயிண்டர்கள் ஓவியர்கள் முன்னேற்ற சங்கம் - அதிகாரப்பூர்வ லோகோ",
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.Fit
    )
  }
}

@Composable
fun TnpaOriginalFlag(
  modifier: Modifier = Modifier,
  size: Dp = 90.dp
) {
  Box(
    modifier = modifier
      .size(size)
      .shadow(4.dp, RoundedCornerShape(12.dp))
      .clip(RoundedCornerShape(12.dp))
      .background(Color(0xFFF8FAFC))
      .border(1.5.dp, Color(0xFFCBD5E1), RoundedCornerShape(12.dp))
      .testTag("tnpa_original_flag"),
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = painterResource(id = R.drawable.drawable_tnpa_flag),
      contentDescription = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கக் கொடி",
      modifier = Modifier.fillMaxSize().padding(4.dp),
      contentScale = ContentScale.Fit
    )
  }
}

@Composable
fun StatePresidentPortrait(
  modifier: Modifier = Modifier,
  size: Dp = 90.dp,
  showBorder: Boolean = true
) {
  Box(
    modifier = modifier
      .size(size)
      .shadow(6.dp, CircleShape)
      .clip(CircleShape)
      .background(
        Brush.radialGradient(
          listOf(Color(0xFFFFD54F), Color(0xFFF59E0B), Color(0xFFD97706))
        )
      )
      .then(if (showBorder) Modifier.border(3.dp, TnpaGold, CircleShape) else Modifier)
      .testTag("portrait_state_president"),
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = painterResource(id = R.drawable.drawable_state_president),
      contentDescription = "மாநிலத் தலைவர் எஸ். மைக்கேல் ஆல்வின்",
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.Crop
    )
  }
}

@Composable
fun StateGeneralSecretaryPortrait(
  modifier: Modifier = Modifier,
  size: Dp = 90.dp,
  showBorder: Boolean = true
) {
  Box(
    modifier = modifier
      .size(size)
      .shadow(6.dp, CircleShape)
      .clip(CircleShape)
      .background(
        Brush.radialGradient(
          listOf(Color(0xFF1E293B), Color(0xFF0F172A), Color(0xFF020617))
        )
      )
      .then(if (showBorder) Modifier.border(3.dp, TnpaRedPrimary, CircleShape) else Modifier)
      .testTag("portrait_state_general_secretary"),
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = painterResource(id = R.drawable.drawable_state_general_secretary),
      contentDescription = "மாநில பொதுச் செயலாளர் சேவியர் பாபு",
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.Crop
    )
  }
}

@Composable
fun StateTreasurerPortrait(
  modifier: Modifier = Modifier,
  size: Dp = 90.dp,
  showBorder: Boolean = true
) {
  Box(
    modifier = modifier
      .size(size)
      .shadow(6.dp, CircleShape)
      .clip(CircleShape)
      .background(
        Brush.radialGradient(
          listOf(Color(0xFFF8FAFC), Color(0xFFE2E8F0), Color(0xFFCBD5E1))
        )
      )
      .then(if (showBorder) Modifier.border(3.dp, Color(0xFF10B981), CircleShape) else Modifier)
      .testTag("portrait_state_treasurer"),
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = painterResource(id = R.drawable.drawable_state_treasurer),
      contentDescription = "மாநில பொருளாளர் சக்திவேல்",
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.Crop
    )
  }
}

/**
 * Grand Leadership Trio Card showing all 3 State Leaders with authentic visuals
 */
@Composable
fun StateLeadershipGrandShowcase(
  modifier: Modifier = Modifier,
  onLeaderClick: (leaderType: String) -> Unit = {}
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("state_leadership_grand_showcase"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaJetBlack),
    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.verticalGradient(
            colors = listOf(
              TnpaDarkCard,
              TnpaJetBlack,
              Color(0xFF0F172A)
            )
          )
        )
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Header with Logo and Flag
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        TnpaOriginalLogo(size = 48.dp)
        
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = "மாநில தலைமைப் பொறுப்பாளர்கள்",
            color = TnpaGold,
            fontSize = 15.sp,
            fontWeight = FontWeight.Black
          )
          Text(
            text = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் (TNPA²)",
            color = TnpaPureWhite.copy(alpha = 0.8f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
          )
        }

        TnpaOriginalFlag(size = 48.dp)
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 3 Leaders in a Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
      ) {
        // 1. President (எஸ். மைக்கேல் ஆல்வின்)
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .weight(1f)
            .clickable { onLeaderClick("president") }
            .padding(4.dp)
        ) {
          StatePresidentPortrait(size = 72.dp)
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "எஸ். மைக்கேல் ஆல்வின்",
            color = TnpaPureWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 15.sp
          )
          Text(
            text = "மாநிலத் தலைவர்",
            color = TnpaGold,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
          )
        }

        // 2. General Secretary (சேவியர் பாபு)
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .weight(1f)
            .clickable { onLeaderClick("secretary") }
            .padding(4.dp)
        ) {
          StateGeneralSecretaryPortrait(size = 72.dp)
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "சேவியர் பாபு",
            color = TnpaPureWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 15.sp
          )
          Text(
            text = "மாநில பொதுச் செயலாளர்",
            color = Color(0xFFF87171),
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
          )
        }

        // 3. Treasurer (சக்திவேல்)
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .weight(1f)
            .clickable { onLeaderClick("treasurer") }
            .padding(4.dp)
        ) {
          StateTreasurerPortrait(size = 72.dp)
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "சக்திவேல்",
            color = TnpaPureWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 15.sp
          )
          Text(
            text = "மாநில பொருளாளர்",
            color = Color(0xFF6EE7B7),
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
          )
        }
      }
    }
  }
}
