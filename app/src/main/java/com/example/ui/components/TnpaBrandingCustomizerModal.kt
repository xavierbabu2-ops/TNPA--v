package com.example.ui.components

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.R
import com.example.data.OfficialAssetsManager
import com.example.ui.theme.*

/**
 * Interactive Dialog & BottomSheet to change / customize / upload the Real Official Logo and Flag of TNPA.
 * Supports:
 * 1. Uploading real images from Phone Gallery / Storage
 * 2. Entering custom Web Image URLs
 * 3. Resetting back to Official High-Definition Vector Logos & Flags
 * 4. Immediate real-time persistence across all screens and ID cards
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TnpaBrandingCustomizerModal(
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  val currentLogoUri by OfficialAssetsManager.logoUri.collectAsState()
  val currentFlagUri by OfficialAssetsManager.flagUri.collectAsState()

  var customLogoUrlInput by remember { mutableStateOf("") }
  var customFlagUrlInput by remember { mutableStateOf("") }
  var showUrlInputs by remember { mutableStateOf(false) }

  // Gallery Picker for Logo
  val logoPickerLauncher = rememberLauncherForActivityResult(
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
        OfficialAssetsManager.setLogo(uri)
        Toast.makeText(context, "✅ சங்கத்தின் லோகோ வெற்றிகரமாக மாற்றப்பட்டது!", Toast.LENGTH_SHORT).show()
      } catch (e: Exception) {
        OfficialAssetsManager.setLogo(uri)
      }
    }
  }

  // Gallery Picker for Flag
  val flagPickerLauncher = rememberLauncherForActivityResult(
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
        OfficialAssetsManager.setFlag(uri)
        Toast.makeText(context, "✅ சங்கத்தின் கொடி வெற்றிகரமாக மாற்றப்பட்டது!", Toast.LENGTH_SHORT).show()
      } catch (e: Exception) {
        OfficialAssetsManager.setFlag(uri)
      }
    }
  }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
          .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(TnpaRedPrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.Palette, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "லோகோ & கொடி மாற்றுதல்",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = TnpaJetBlack
              )
              Text(
                text = "சங்கத்தின் அதிகாரப்பூர்வ புகைப்படங்கள்",
                fontSize = 11.sp,
                color = Color.Gray
              )
            }
          }

          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TnpaJetBlack)
          }
        }

        HorizontalDivider(color = TnpaRedPrimary.copy(alpha = 0.2f))

        // ====================================================================
        // 1. OFFICIAL LOGO SECTION
        // ====================================================================
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = TnpaOffWhite),
          border = BorderStroke(1.dp, TnpaRedPrimary.copy(alpha = 0.3f))
        ) {
          Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Verified, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "1. சங்கத்தின் லோகோ (Association Logo)",
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  color = TnpaJetBlack
                )
              }

              if (!currentLogoUri.isNullOrBlank()) {
                TextButton(
                  onClick = {
                    OfficialAssetsManager.setLogo(null)
                    Toast.makeText(context, "அசல் லோகோவிற்கு மீட்டமைக்கப்பட்டது", Toast.LENGTH_SHORT).show()
                  },
                  contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text("மீட்டமை (Reset)", fontSize = 10.sp, color = TnpaRedPrimary, fontWeight = FontWeight.Bold)
                }
              }
            }

            // Logo Preview Box
            Box(
              modifier = Modifier
                .size(90.dp)
                .shadow(4.dp, CircleShape)
                .clip(CircleShape)
                .background(TnpaPureWhite)
                .border(2.5.dp, TnpaGold, CircleShape)
                .clickable { logoPickerLauncher.launch("image/*") },
              contentAlignment = Alignment.Center
            ) {
              if (!currentLogoUri.isNullOrBlank()) {
                AsyncImage(
                  model = currentLogoUri,
                  contentDescription = "Custom TNPA Logo",
                  modifier = Modifier.fillMaxSize(),
                  contentScale = ContentScale.Fit
                )
              } else {
                Image(
                  painter = painterResource(id = R.drawable.drawable_tnpa_logo),
                  contentDescription = "Official TNPA Logo",
                  modifier = Modifier.fillMaxSize(),
                  contentScale = ContentScale.Fit
                )
              }

              // Camera Icon Overlay
              Box(
                modifier = Modifier
                  .align(Alignment.BottomEnd)
                  .size(26.dp)
                  .clip(CircleShape)
                  .background(TnpaJetBlack)
                  .border(1.5.dp, TnpaGold, CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Icon(Icons.Default.PhotoCamera, contentDescription = "Edit Logo", tint = TnpaGold, modifier = Modifier.size(14.dp))
              }
            }

            Text(
              text = if (!currentLogoUri.isNullOrBlank()) "தனிப்பயன் லோகோ இயங்குகிறது (Custom Image Active)" else "அசல் சங்கம் லோகோ (Official Vector Logo)",
              fontSize = 10.sp,
              color = TnpaRedDark,
              fontWeight = FontWeight.Bold
            )

            // Buttons
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Button(
                onClick = { logoPickerLauncher.launch("image/*") },
                modifier = Modifier.weight(1f).height(38.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
              ) {
                Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("கேலரியில் தேர்ந்தெடுக்க", fontSize = 11.sp, color = TnpaPureWhite, fontWeight = FontWeight.Bold)
              }
            }
          }
        }

        // ====================================================================
        // 2. OFFICIAL FLAG SECTION
        // ====================================================================
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = TnpaOffWhite),
          border = BorderStroke(1.dp, TnpaRedPrimary.copy(alpha = 0.3f))
        ) {
          Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Flag, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "2. சங்கத்தின் கொடி (Association Flag)",
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  color = TnpaJetBlack
                )
              }

              if (!currentFlagUri.isNullOrBlank()) {
                TextButton(
                  onClick = {
                    OfficialAssetsManager.setFlag(null)
                    Toast.makeText(context, "அசல் கொடிக்கு மீட்டமைக்கப்பட்டது", Toast.LENGTH_SHORT).show()
                  },
                  contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text("மீட்டமை (Reset)", fontSize = 10.sp, color = TnpaRedPrimary, fontWeight = FontWeight.Bold)
                }
              }
            }

            // Flag Preview Box
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .shadow(3.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF0F172A))
                .border(1.5.dp, TnpaGold, RoundedCornerShape(10.dp))
                .clickable { flagPickerLauncher.launch("image/*") },
              contentAlignment = Alignment.Center
            ) {
              if (!currentFlagUri.isNullOrBlank()) {
                AsyncImage(
                  model = currentFlagUri,
                  contentDescription = "Custom TNPA Flag",
                  modifier = Modifier.fillMaxSize().padding(4.dp),
                  contentScale = ContentScale.Fit
                )
              } else {
                Image(
                  painter = painterResource(id = R.drawable.drawable_tnpa_flag),
                  contentDescription = "Official TNPA Flag",
                  modifier = Modifier.fillMaxSize().padding(4.dp),
                  contentScale = ContentScale.Fit
                )
              }

              // Edit Overlay Pill
              Box(
                modifier = Modifier
                  .align(Alignment.BottomEnd)
                  .padding(6.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(TnpaJetBlack.copy(alpha = 0.85f))
                  .padding(horizontal = 6.dp, vertical = 3.dp),
                contentAlignment = Alignment.Center
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(12.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("கொடி மாற்று", fontSize = 10.sp, color = TnpaGold, fontWeight = FontWeight.Bold)
                }
              }
            }

            Text(
              text = if (!currentFlagUri.isNullOrBlank()) "தனிப்பயன் கொடி இயங்குகிறது (Custom Flag Active)" else "சிவப்பு-வெள்ளை இருவர்ண சங்கக் கொடி (Official Silk Flag)",
              fontSize = 10.sp,
              color = TnpaRedDark,
              fontWeight = FontWeight.Bold
            )

            // Buttons
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Button(
                onClick = { flagPickerLauncher.launch("image/*") },
                modifier = Modifier.weight(1f).height(38.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TnpaJetBlack)
              ) {
                Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("கேலரியில் கொடியைத் தேர்வு செய்", fontSize = 11.sp, color = TnpaGold, fontWeight = FontWeight.Bold)
              }
            }
          }
        }

        // URL Input Accordion
        Column(modifier = Modifier.fillMaxWidth()) {
          TextButton(
            onClick = { showUrlInputs = !showUrlInputs },
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.Link, contentDescription = null, tint = TnpaRedDark, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (showUrlInputs) "இணைய URL மறைக்க" else "🌐 இணையதள Image URL மூலம் மாற்ற வேண்டுமா?",
              fontSize = 11.sp,
              color = TnpaRedDark,
              fontWeight = FontWeight.Bold
            )
          }

          if (showUrlInputs) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp))
                .padding(10.dp),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              OutlinedTextField(
                value = customLogoUrlInput,
                onValueChange = { customLogoUrlInput = it },
                label = { Text("Logo Image URL (https://...)") },
                placeholder = { Text("https://example.com/logo.png") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 12.sp)
              )
              Button(
                onClick = {
                  if (customLogoUrlInput.isNotBlank()) {
                    OfficialAssetsManager.setLogo(Uri.parse(customLogoUrlInput.trim()))
                    Toast.makeText(context, "Logo URL அமைக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
                    customLogoUrlInput = ""
                  }
                },
                modifier = Modifier.fillMaxWidth().height(36.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
              ) {
                Text("Logo URL சேமி", fontSize = 11.sp)
              }

              OutlinedTextField(
                value = customFlagUrlInput,
                onValueChange = { customFlagUrlInput = it },
                label = { Text("Flag Image URL (https://...)") },
                placeholder = { Text("https://example.com/flag.png") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 12.sp)
              )
              Button(
                onClick = {
                  if (customFlagUrlInput.isNotBlank()) {
                    OfficialAssetsManager.setFlag(Uri.parse(customFlagUrlInput.trim()))
                    Toast.makeText(context, "Flag URL அமைக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
                    customFlagUrlInput = ""
                  }
                },
                modifier = Modifier.fillMaxWidth().height(36.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TnpaJetBlack)
              ) {
                Text("Flag URL சேமி", fontSize = 11.sp, color = TnpaGold)
              }
            }
          }
        }

        // Close and Confirm Button
        Button(
          onClick = onDismiss,
          modifier = Modifier
            .fillMaxWidth()
            .height(46.dp),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen)
        ) {
          Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("சேமித்து முடி (Done & Apply Everywhere)", fontWeight = FontWeight.Black, color = TnpaPureWhite, fontSize = 13.sp)
        }
      }
    }
  }
}
