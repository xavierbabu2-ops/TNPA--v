package com.example.ui.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.R
import com.example.data.OfficialAssetsManager
import com.example.ui.theme.*

/**
 * Authentic, real visual representations for TNPA official assets:
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
  val customLogoUri by OfficialAssetsManager.logoUri.collectAsState()

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
    if (!customLogoUri.isNullOrBlank()) {
      AsyncImage(
        model = customLogoUri,
        contentDescription = "தமிழ்நாடு பெயிண்டர்கள் ஓவியர்கள் முன்னேற்ற சங்கம் - அதிகாரப்பூர்வ லோகோ",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Fit
      )
    } else {
      Image(
        painter = painterResource(id = R.drawable.drawable_tnpa_logo),
        contentDescription = "தமிழ்நாடு பெயிண்டர்கள் ஓவியர்கள் முன்னேற்ற சங்கம் - அதிகாரப்பூர்வ லோகோ",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Fit
      )
    }
  }
}

@Composable
fun TnpaOriginalFlag(
  modifier: Modifier = Modifier,
  size: Dp = 90.dp
) {
  val customFlagUri by OfficialAssetsManager.flagUri.collectAsState()

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
    if (!customFlagUri.isNullOrBlank()) {
      AsyncImage(
        model = customFlagUri,
        contentDescription = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கக் கொடி",
        modifier = Modifier.fillMaxSize().padding(4.dp),
        contentScale = ContentScale.Fit
      )
    } else {
      Image(
        painter = painterResource(id = R.drawable.drawable_tnpa_flag),
        contentDescription = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கக் கொடி",
        modifier = Modifier.fillMaxSize().padding(4.dp),
        contentScale = ContentScale.Fit
      )
    }
  }
}

@Composable
fun StatePresidentPortrait(
  modifier: Modifier = Modifier,
  size: Dp = 90.dp,
  showBorder: Boolean = true
) {
  val customPresidentUri by OfficialAssetsManager.presidentPhotoUri.collectAsState()

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
    if (!customPresidentUri.isNullOrBlank()) {
      AsyncImage(
        model = customPresidentUri,
        contentDescription = "மாநிலத் தலைவர் எஸ். மைக்கேல் ஆல்வின்",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
      )
    } else {
      Image(
        painter = painterResource(id = R.drawable.drawable_state_president),
        contentDescription = "மாநிலத் தலைவர் எஸ். மைக்கேல் ஆல்வின்",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
      )
    }
  }
}

@Composable
fun StateGeneralSecretaryPortrait(
  modifier: Modifier = Modifier,
  size: Dp = 90.dp,
  showBorder: Boolean = true
) {
  val customGenSecUri by OfficialAssetsManager.generalSecPhotoUri.collectAsState()

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
    if (!customGenSecUri.isNullOrBlank()) {
      AsyncImage(
        model = customGenSecUri,
        contentDescription = "மாநில பொதுச் செயலாளர் சேவியர் பாபு",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
      )
    } else {
      Image(
        painter = painterResource(id = R.drawable.drawable_state_general_secretary),
        contentDescription = "மாநில பொதுச் செயலாளர் சேவியர் பாபு",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
      )
    }
  }
}

@Composable
fun StateTreasurerPortrait(
  modifier: Modifier = Modifier,
  size: Dp = 90.dp,
  showBorder: Boolean = true
) {
  val customTreasurerUri by OfficialAssetsManager.treasurerPhotoUri.collectAsState()

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
    if (!customTreasurerUri.isNullOrBlank()) {
      AsyncImage(
        model = customTreasurerUri,
        contentDescription = "மாநில பொருளாளர் சக்திவேல்",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
      )
    } else {
      Image(
        painter = painterResource(id = R.drawable.drawable_state_treasurer),
        contentDescription = "மாநில பொருளாளர் சக்திவேல்",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
      )
    }
  }
}

/**
 * Grand Leadership Trio Card showing all 3 State Leaders with authentic visuals and photo upload options
 */
@Composable
fun StateLeadershipGrandShowcase(
  modifier: Modifier = Modifier,
  onLeaderClick: (leaderType: String) -> Unit = {}
) {
  val context = LocalContext.current
  var selectedUploadTarget by remember { mutableStateOf<String?>(null) }

  // Photo Picker Launcher for Leaders, Logo, Flag
  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) { uri: Uri? ->
    if (uri != null) {
      try {
        try {
          context.contentResolver.takePersistableUriPermission(
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
          )
        } catch (_: Exception) {}

        when (selectedUploadTarget) {
          "president" -> {
            OfficialAssetsManager.setPresidentPhoto(uri)
            Toast.makeText(context, "✅ மாநிலத் தலைவர் அசல் புகைப்படம் புதுப்பிக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
          }
          "secretary" -> {
            OfficialAssetsManager.setGeneralSecPhoto(uri)
            Toast.makeText(context, "✅ மாநில பொதுச் செயலாளர் அசல் புகைப்படம் புதுப்பிக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
          }
          "treasurer" -> {
            OfficialAssetsManager.setTreasurerPhoto(uri)
            Toast.makeText(context, "✅ மாநில பொருளாளர் அசல் புகைப்படம் புதுப்பிக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
          }
          "logo" -> {
            OfficialAssetsManager.setLogo(uri)
            Toast.makeText(context, "✅ சங்கத்தின் அசல் லோகோ புதுப்பிக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
          }
          "flag" -> {
            OfficialAssetsManager.setFlag(uri)
            Toast.makeText(context, "✅ சங்கத்தின் அசல் கொடி புதுப்பிக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
          }
        }
      } catch (e: Exception) {
        Toast.makeText(context, "புகைப்படம் இணைக்கப்பட்டது", Toast.LENGTH_SHORT).show()
      }
    }
  }

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
        Box(
          modifier = Modifier.clickable {
            selectedUploadTarget = "logo"
            photoPickerLauncher.launch("image/*")
          }
        ) {
          TnpaOriginalLogo(size = 48.dp)
        }
        
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

        Box(
          modifier = Modifier.clickable {
            selectedUploadTarget = "flag"
            photoPickerLauncher.launch("image/*")
          }
        ) {
          TnpaOriginalFlag(size = 48.dp)
        }
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
            .clickable {
              selectedUploadTarget = "president"
              onLeaderClick("president")
            }
            .padding(4.dp)
        ) {
          Box {
            StatePresidentPortrait(size = 72.dp)
            Box(
              modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(20.dp)
                .clip(CircleShape)
                .background(TnpaJetBlack)
                .border(1.dp, TnpaGold, CircleShape)
                .clickable {
                  selectedUploadTarget = "president"
                  photoPickerLauncher.launch("image/*")
                },
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.PhotoCamera, contentDescription = "Upload", tint = TnpaGold, modifier = Modifier.size(12.dp))
            }
          }
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
            .clickable {
              selectedUploadTarget = "secretary"
              onLeaderClick("secretary")
            }
            .padding(4.dp)
        ) {
          Box {
            StateGeneralSecretaryPortrait(size = 72.dp)
            Box(
              modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(20.dp)
                .clip(CircleShape)
                .background(TnpaJetBlack)
                .border(1.dp, TnpaRedPrimary, CircleShape)
                .clickable {
                  selectedUploadTarget = "secretary"
                  photoPickerLauncher.launch("image/*")
                },
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.PhotoCamera, contentDescription = "Upload", tint = TnpaPureWhite, modifier = Modifier.size(12.dp))
            }
          }
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
            .clickable {
              selectedUploadTarget = "treasurer"
              onLeaderClick("treasurer")
            }
            .padding(4.dp)
        ) {
          Box {
            StateTreasurerPortrait(size = 72.dp)
            Box(
              modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(20.dp)
                .clip(CircleShape)
                .background(TnpaJetBlack)
                .border(1.dp, Color(0xFF10B981), CircleShape)
                .clickable {
                  selectedUploadTarget = "treasurer"
                  photoPickerLauncher.launch("image/*")
                },
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.PhotoCamera, contentDescription = "Upload", tint = Color(0xFF10B981), modifier = Modifier.size(12.dp))
            }
          }
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

      Spacer(modifier = Modifier.height(8.dp))

      // Quick Upload Real Photos Prompt
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(Color(0xFF1E293B).copy(alpha = 0.6f))
          .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("உண்மையான புகைப்படங்களை மாற்ற:", color = TnpaPureWhite, fontSize = 10.sp, fontWeight = FontWeight.Medium)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          Button(
            onClick = {
              selectedUploadTarget = "president"
              photoPickerLauncher.launch("image/*")
            },
            colors = ButtonDefaults.buttonColors(containerColor = TnpaGold),
            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
            modifier = Modifier.height(26.dp),
            shape = RoundedCornerShape(4.dp)
          ) {
            Text("ஆல்வின்", fontSize = 9.sp, color = TnpaJetBlack, fontWeight = FontWeight.Bold)
          }

          Button(
            onClick = {
              selectedUploadTarget = "secretary"
              photoPickerLauncher.launch("image/*")
            },
            colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
            modifier = Modifier.height(26.dp),
            shape = RoundedCornerShape(4.dp)
          ) {
            Text("சேவியர்", fontSize = 9.sp, color = TnpaPureWhite, fontWeight = FontWeight.Bold)
          }

          Button(
            onClick = {
              selectedUploadTarget = "treasurer"
              photoPickerLauncher.launch("image/*")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
            modifier = Modifier.height(26.dp),
            shape = RoundedCornerShape(4.dp)
          ) {
            Text("சக்திவேல்", fontSize = 9.sp, color = TnpaPureWhite, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

