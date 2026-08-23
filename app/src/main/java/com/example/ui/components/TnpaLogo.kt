package com.example.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.OfficialAssetsManager
import com.example.ui.theme.*

/**
 * Official Logo Emblem for தமிழ்நாடு பெயிண்டர்கள் முன்னேற்ற சங்கம் (TN PA²)
 * Displays real high-resolution official trade union logo or custom user-uploaded logo with real-time updates.
 */
@Composable
fun TnpaOfficialEmblem(
  modifier: Modifier = Modifier,
  sizeDp: Dp = 56.dp,
  showBorder: Boolean = true,
  enableCustomizerClick: Boolean = false,
  onClick: (() -> Unit)? = null
) {
  val customLogoUri by OfficialAssetsManager.logoUri.collectAsState()
  var showCustomizerDialog by remember { mutableStateOf(false) }

  if (showCustomizerDialog) {
    TnpaBrandingCustomizerModal(onDismiss = { showCustomizerDialog = false })
  }

  Box(
    modifier = modifier
      .size(sizeDp)
      .shadow(elevation = 6.dp, shape = CircleShape)
      .clip(CircleShape)
      .background(TnpaPureWhite)
      .then(
        if (showBorder) Modifier.border(2.dp, TnpaGold, CircleShape) else Modifier
      )
      .then(
        if (enableCustomizerClick) Modifier.clickable { showCustomizerDialog = true }
        else if (onClick != null) Modifier.clickable { onClick() }
        else Modifier
      )
      .testTag("tnpa_official_emblem"),
    contentAlignment = Alignment.Center
  ) {
    if (!customLogoUri.isNullOrBlank()) {
      AsyncImage(
        model = customLogoUri,
        contentDescription = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்க லோகோ",
        modifier = Modifier.fillMaxSize().clip(CircleShape),
        contentScale = ContentScale.Fit
      )
    } else {
      Image(
        painter = painterResource(id = R.drawable.drawable_tnpa_logo),
        contentDescription = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்க லோகோ",
        modifier = Modifier.fillMaxSize().clip(CircleShape),
        contentScale = ContentScale.Fit
      )
    }

    if (enableCustomizerClick) {
      Box(
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .size((sizeDp.value * 0.35f).dp.coerceAtLeast(14.dp))
          .clip(CircleShape)
          .background(TnpaJetBlack)
          .border(1.dp, TnpaGold, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          Icons.Default.PhotoCamera,
          contentDescription = "Edit Logo",
          tint = TnpaGold,
          modifier = Modifier.size((sizeDp.value * 0.22f).dp.coerceAtLeast(9.dp))
        )
      }
    }
  }
}

/**
 * Official Flag Component of தமிழ்நாடு பெயிண்டர்கள் முன்னேற்ற சங்கம் (TN PA²)
 * Displays real official waving bicolor flag or custom user-uploaded flag.
 */
@Composable
fun TnpaOfficialFlagBanner(
  modifier: Modifier = Modifier,
  onCustomizeClick: (() -> Unit)? = null
) {
  val customFlagUri by OfficialAssetsManager.flagUri.collectAsState()
  var showCustomizerDialog by remember { mutableStateOf(false) }

  if (showCustomizerDialog) {
    TnpaBrandingCustomizerModal(onDismiss = { showCustomizerDialog = false })
  }

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
      // Flag Visual Box
      Box(
        modifier = Modifier
          .size(width = 110.dp, height = 75.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(Color(0xFF0F172A))
          .border(1.5.dp, TnpaGold.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
          .clickable {
            if (onCustomizeClick != null) onCustomizeClick()
            else showCustomizerDialog = true
          },
        contentAlignment = Alignment.Center
      ) {
        if (!customFlagUri.isNullOrBlank()) {
          AsyncImage(
            model = customFlagUri,
            contentDescription = "TNPA Official Flag",
            modifier = Modifier.fillMaxSize().padding(3.dp),
            contentScale = ContentScale.Fit
          )
        } else {
          Image(
            painter = painterResource(id = R.drawable.drawable_tnpa_flag),
            contentDescription = "TNPA Official Flag",
            modifier = Modifier.fillMaxSize().padding(3.dp),
            contentScale = ContentScale.Fit
          )
        }

        // Camera badge
        Box(
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(3.dp)
            .size(18.dp)
            .clip(CircleShape)
            .background(TnpaJetBlack.copy(alpha = 0.85f))
            .border(1.dp, TnpaGold, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(Icons.Default.PhotoCamera, contentDescription = "Change Flag", tint = TnpaGold, modifier = Modifier.size(10.dp))
        }
      }

      // Flag Description Text
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(3.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
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

          Text(
            text = "🔄 மாற்றுக",
            color = TnpaGold,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
              if (onCustomizeClick != null) onCustomizeClick()
              else showCustomizerDialog = true
            }
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
  modifier: Modifier = Modifier,
  onLogoClick: (() -> Unit)? = null
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    TnpaOfficialEmblem(sizeDp = 44.dp, onClick = onLogoClick)
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
