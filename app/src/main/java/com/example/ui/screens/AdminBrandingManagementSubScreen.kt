package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.OfficialAssetsManager
import com.example.ui.components.TnpaBrandingCustomizerModal
import com.example.ui.theme.*

/**
 * SubScreen inside Admin Panel for managing Official Trade Union Branding:
 * - Official Emblem / Logo (High resolution real image replacement)
 * - Official Union Flag (Waving bicolor flag real image replacement)
 * - State President Official Photo replacement
 */
@Composable
fun AdminBrandingManagementSubScreen(
  topHeaderContent: @Composable () -> Unit,
  tabsContent: @Composable () -> Unit
) {
  val context = LocalContext.current
  val currentLogoUri by OfficialAssetsManager.logoUri.collectAsState()
  val currentFlagUri by OfficialAssetsManager.flagUri.collectAsState()
  val currentPresidentUri by OfficialAssetsManager.presidentPhotoUri.collectAsState()

  var showCustomizerModal by remember { mutableStateOf(false) }

  val logoPicker = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) { uri: Uri? ->
    uri?.let {
      OfficialAssetsManager.setCustomLogoUri(context, it.toString())
      Toast.makeText(context, "சங்க லோகோ வெற்றிகரமாக மாற்றப்பட்டது!", Toast.LENGTH_SHORT).show()
    }
  }

  val flagPicker = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) { uri: Uri? ->
    uri?.let {
      OfficialAssetsManager.setCustomFlagUri(context, it.toString())
      Toast.makeText(context, "சங்கக் கொடி வெற்றிகரமாக மாற்றப்பட்டது!", Toast.LENGTH_SHORT).show()
    }
  }

  val presidentPicker = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) { uri: Uri? ->
    uri?.let {
      OfficialAssetsManager.setCustomPresidentPhotoUri(context, it.toString())
      Toast.makeText(context, "மாநிலத் தலைவர் படம் வெற்றிகரமாக மாற்றப்பட்டது!", Toast.LENGTH_SHORT).show()
    }
  }

  if (showCustomizerModal) {
    TnpaBrandingCustomizerModal(onDismiss = { showCustomizerModal = false })
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(TnpaOffWhite)
      .verticalScroll(rememberScrollState())
      .padding(bottom = 32.dp)
  ) {
    topHeaderContent()
    tabsContent()

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Header Banner
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaJetBlack),
        border = BorderStroke(1.5.dp, TnpaGold)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(Icons.Default.Palette, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(24.dp))
            Text(
              text = "சங்க அதிகாரப்பூர்வ லோகோ & கொடி மேலாண்மை",
              color = TnpaPureWhite,
              fontSize = 15.sp,
              fontWeight = FontWeight.Black
            )
          }
          Text(
            text = "செயலியின் அனைத்துப் பகுதிகளிலும் காட்டப்படும் அதிகாரப்பூர்வ லோகோ, கொடி மற்றும் மாநிலத் தலைவர் படங்களை உண்மையான புகைப்படங்களாக மாற்ற இங்கே பதிவேற்றலாம்.",
            color = Color(0xFFCBD5E1),
            fontSize = 12.sp,
            lineHeight = 17.sp
          )
        }
      }

      // 1. Official Logo Card
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "1. சங்க அதிகாரப்பூர்வ லோகோ (Union Logo)",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = TnpaJetBlack
            )
            if (!currentLogoUri.isNullOrBlank()) {
              TextButton(
                onClick = {
                  OfficialAssetsManager.resetLogoToDefault(context)
                  Toast.makeText(context, "லோகோ இயல்புநிலைக்கு மாற்றப்பட்டது", Toast.LENGTH_SHORT).show()
                }
              ) {
                Text("மீட்டமை (Reset)", color = TnpaRedPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            Box(
              modifier = Modifier
                .size(90.dp)
                .shadow(4.dp, CircleShape)
                .clip(CircleShape)
                .background(TnpaPureWhite)
                .border(2.dp, TnpaGold, CircleShape)
                .clickable { logoPicker.launch("image/*") },
              contentAlignment = Alignment.Center
            ) {
              if (!currentLogoUri.isNullOrBlank()) {
                AsyncImage(
                  model = currentLogoUri,
                  contentDescription = "Current Logo",
                  modifier = Modifier.fillMaxSize(),
                  contentScale = ContentScale.Fit
                )
              } else {
                Image(
                  painter = painterResource(id = R.drawable.drawable_tnpa_logo),
                  contentDescription = "Default Logo",
                  modifier = Modifier.fillMaxSize(),
                  contentScale = ContentScale.Fit
                )
              }
            }

            Column(
              modifier = Modifier.weight(1f),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Text(
                text = if (!currentLogoUri.isNullOrBlank()) "உங்களின் தனிப்பயன் லோகோ இயங்குகிறது" else "இயல்புநிலை லோகோ இயங்குகிறது",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (!currentLogoUri.isNullOrBlank()) TnpaGreen else TnpaCharcoal
              )

              Button(
                onClick = { logoPicker.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().testTag("btn_admin_upload_logo")
              ) {
                Icon(Icons.Default.Image, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("புதிய லோகோ படம் ஏற்று", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TnpaPureWhite)
              }
            }
          }
        }
      }

      // 2. Official Flag Card
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "2. சங்கக் கொடி (Official Flag)",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = TnpaJetBlack
            )
            if (!currentFlagUri.isNullOrBlank()) {
              TextButton(
                onClick = {
                  OfficialAssetsManager.resetFlagToDefault(context)
                  Toast.makeText(context, "கொடி இயல்புநிலைக்கு மாற்றப்பட்டது", Toast.LENGTH_SHORT).show()
                }
              ) {
                Text("மீட்டமை (Reset)", color = TnpaRedPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            Box(
              modifier = Modifier
                .size(width = 110.dp, height = 75.dp)
                .shadow(4.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF0F172A))
                .border(1.5.dp, TnpaGold, RoundedCornerShape(10.dp))
                .clickable { flagPicker.launch("image/*") },
              contentAlignment = Alignment.Center
            ) {
              if (!currentFlagUri.isNullOrBlank()) {
                AsyncImage(
                  model = currentFlagUri,
                  contentDescription = "Current Flag",
                  modifier = Modifier.fillMaxSize().padding(4.dp),
                  contentScale = ContentScale.Fit
                )
              } else {
                Image(
                  painter = painterResource(id = R.drawable.drawable_tnpa_flag),
                  contentDescription = "Default Flag",
                  modifier = Modifier.fillMaxSize().padding(4.dp),
                  contentScale = ContentScale.Fit
                )
              }
            }

            Column(
              modifier = Modifier.weight(1f),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Text(
                text = if (!currentFlagUri.isNullOrBlank()) "உங்களின் தனிப்பயன் கொடி படம் இயங்குகிறது" else "இயல்புநிலை சங்கக் கொடி இயங்குகிறது",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (!currentFlagUri.isNullOrBlank()) TnpaGreen else TnpaCharcoal
              )

              Button(
                onClick = { flagPicker.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = TnpaJetBlack),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().testTag("btn_admin_upload_flag")
              ) {
                Icon(Icons.Default.Image, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("புதிய கொடி படம் ஏற்று", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TnpaPureWhite)
              }
            }
          }
        }
      }

      // 3. State President Photo Card
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "3. மாநிலத் தலைவர் புகைப்படம் (State President)",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = TnpaJetBlack
            )
            if (!currentPresidentUri.isNullOrBlank()) {
              TextButton(
                onClick = {
                  OfficialAssetsManager.resetPresidentPhotoToDefault(context)
                  Toast.makeText(context, "தலைவர் படம் இயல்புநிலைக்கு மாற்றப்பட்டது", Toast.LENGTH_SHORT).show()
                }
              ) {
                Text("மீட்டமை (Reset)", color = TnpaRedPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            Box(
              modifier = Modifier
                .size(80.dp)
                .shadow(4.dp, CircleShape)
                .clip(CircleShape)
                .background(TnpaPureWhite)
                .border(2.dp, TnpaGold, CircleShape)
                .clickable { presidentPicker.launch("image/*") },
              contentAlignment = Alignment.Center
            ) {
              if (!currentPresidentUri.isNullOrBlank()) {
                AsyncImage(
                  model = currentPresidentUri,
                  contentDescription = "Current President Photo",
                  modifier = Modifier.fillMaxSize(),
                  contentScale = ContentScale.Crop
                )
              } else {
                Image(
                  painter = painterResource(id = R.drawable.drawable_state_president),
                  contentDescription = "Default President Photo",
                  modifier = Modifier.fillMaxSize(),
                  contentScale = ContentScale.Crop
                )
              }
            }

            Column(
              modifier = Modifier.weight(1f),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Text(
                text = "எஸ். மைக்கேல் ஆல்வின் (மாநிலத் தலைவர்)",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TnpaJetBlack
              )

              Button(
                onClick = { presidentPicker.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = TnpaGold),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().testTag("btn_admin_upload_president")
              ) {
                Icon(Icons.Default.Edit, contentDescription = null, tint = TnpaJetBlack, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("தலைவர் புகைப்படம் மாற்று", fontSize = 12.sp, fontWeight = FontWeight.Black, color = TnpaJetBlack)
              }
            }
          }
        }
      }
    }
  }
}
