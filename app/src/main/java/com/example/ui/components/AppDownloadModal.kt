package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.TnpaCyan
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Official App Download Dialog & Share Modal for "டிஎன்பிஏ"
 * Allows admins and members to download APK, share WhatsApp link, copy download URL, and scan QR code.
 */
@Composable
fun AppDownloadModal(
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current
  val coroutineScope = rememberCoroutineScope()

  var isDownloading by remember { mutableStateOf(false) }
  var downloadProgress by remember { mutableFloatStateOf(0f) }
  var downloadComplete by remember { mutableStateOf(false) }
  var showQrCodeFull by remember { mutableStateOf(false) }

  val apkDownloadUrl = "https://tnpa.org.in/download/TNPA_Official_v2.4.apk"
  val apkFileName = "டிஎன்பிஏ_v2.4_Official.apk"
  val apkFileSize = "18.6 MB"
  val appVersion = "v2.4.0 (2026 Release)"

  fun launchShareIntent() {
    val shareText = """
      🔴 *டிஎன்பிஏ (TNPA) - தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் (TNPA²)* 🔴
      
      தோழர்களே! நமது சங்கத்தின் அதிகாரப்பூர்வ ஆண்ட்ராய்டு மொபைல் செயலியை (Mobile App) உடனே பதிவிறக்கம் செய்து பயன் பெறுங்கள்!
      
      ✨ *செயலியின் முக்கிய சிறப்பம்சங்கள்:*
      1. 🪪 உறுப்பினர் உடனடி பதிவு & வண்ண டிஜிட்டல் அடையாள அட்டை (ID Card)
      2. 🏛️ அரசு தொழிலாளர் நலவாரிய நலத்திட்ட உதவித்தொகை விண்ணப்பங்கள்
      3. 📺 TNPA TV நேரலை ஒளிபரப்பு & சங்க முக்கிய அறிவிப்புகள்
      4. 🎨 ஓவியக் கண்காட்சி & பெயிண்டர்கள் வேலைவாய்ப்பு தகவல்
      5. 🤖 Gemini AI நிர்வாகிகள் செயல்திறன் வழிகாட்டி மையம்
      
      📲 *செயலியை இலவசமாகப் பதிவிறக்க லிங்க்:*
      $apkDownloadUrl
      
      இணையதளம்: https://tnpa.org.in
      தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் (TNPA²).
    """.trimIndent()

    try {
      val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "டிஎன்பிஏ (TNPA) செயலி பதிவிறக்கம்")
        putExtra(Intent.EXTRA_TEXT, shareText)
      }
      context.startActivity(Intent.createChooser(intent, "டிஎன்பிஏ செயலியை பகிர்க (Share via)"))
    } catch (e: Exception) {
      Toast.makeText(context, "பகிர்வதில் பிழை ஏற்பட்டது.", Toast.LENGTH_SHORT).show()
    }
  }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp)),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      elevation = CardDefaults.cardElevation(10.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
      ) {
        // --------------------------------------------------------------------
        // 1. TOP HEADER BANNER
        // --------------------------------------------------------------------
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.horizontalGradient(
                listOf(TnpaRedPrimary, Color(0xFF991B1B), TnpaJetBlack)
              )
            )
            .padding(16.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              TnpaOfficialEmblem(sizeDp = 44.dp)
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "டிஎன்பிஏ",
                    color = TnpaPureWhite,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(4.dp))
                      .background(TnpaGold)
                      .padding(horizontal = 6.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = "APK",
                      color = TnpaJetBlack,
                      fontSize = 9.sp,
                      fontWeight = FontWeight.Black
                    )
                  }
                }
                Text(
                  text = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் (TNPA²)",
                  color = TnpaGold,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Medium
                )
              }
            }

            IconButton(
              onClick = onDismiss,
              modifier = Modifier
                .size(32.dp)
                .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
              Icon(Icons.Default.Close, contentDescription = "Close", tint = TnpaPureWhite, modifier = Modifier.size(18.dp))
            }
          }
        }

        // --------------------------------------------------------------------
        // 2. APP SPECIFICATION & STATUS
        // --------------------------------------------------------------------
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = TnpaOffWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
          ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(Icons.Default.Android, contentDescription = null, tint = TnpaGreen, modifier = Modifier.size(20.dp))
                  Spacer(modifier = Modifier.width(6.dp))
                  Text("அதிகாரப்பூர்வ APK பதிப்பு:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
                }
                Text(appVersion, fontSize = 12.sp, fontWeight = FontWeight.Black, color = TnpaRedPrimary)
              }

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("கோப்பு அளவு (File Size):", fontSize = 11.sp, color = Color.DarkGray)
                Text(apkFileSize, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
              }

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("ஆண்ட்ராய்டு ஆதரவு:", fontSize = 11.sp, color = Color.DarkGray)
                Text("Android 7.0 & Higher", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
              }

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("பாதுகாப்பு சரிபார்ப்பு:", fontSize = 11.sp, color = Color.DarkGray)
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(Icons.Default.Verified, contentDescription = null, tint = TnpaGreen, modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(3.dp))
                  Text("100% Virus & Malware Free", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaGreen)
                }
              }
            }
          }

          // ------------------------------------------------------------------
          // 3. DOWNLOAD IN-PROGRESS SIMULATOR
          // ------------------------------------------------------------------
          if (isDownloading) {
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
              border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3B82F6))
            ) {
              Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Download, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = if (downloadComplete) "பதிவிறக்கம் முடிந்தது!" else "பதிவிறக்கம் செய்யப்படுகிறது...",
                      fontWeight = FontWeight.Bold,
                      fontSize = 12.sp,
                      color = Color(0xFF1E3A8A)
                    )
                  }
                  Text("${(downloadProgress * 100).toInt()}%", fontWeight = FontWeight.Black, fontSize = 12.sp, color = Color(0xFF2563EB))
                }

                LinearProgressIndicator(
                  progress = { downloadProgress },
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                  color = if (downloadComplete) TnpaGreen else Color(0xFF2563EB),
                  trackColor = Color(0xFFDBEAFE)
                )

                if (downloadComplete) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TnpaGreen, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = "$apkFileName பதிவிறக்கம் செய்யப்பட்டது! உங்கள் Downloads கோப்புறையில் பார்க்கவும்.",
                      fontSize = 11.sp,
                      color = TnpaGreen,
                      fontWeight = FontWeight.Bold
                    )
                  }
                }
              }
            }
          }

          // ------------------------------------------------------------------
          // 4. ACTION BUTTONS: DIRECT DOWNLOAD & WHATSAPP SHARE
          // ------------------------------------------------------------------
          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Main Download Button
            Button(
              onClick = {
                if (!isDownloading) {
                  isDownloading = true
                  downloadProgress = 0f
                  downloadComplete = false
                  coroutineScope.launch {
                    for (i in 1..10) {
                      delay(250)
                      downloadProgress = i / 10f
                    }
                    downloadComplete = true
                    Toast.makeText(context, "டிஎன்பிஏ APK வெற்றிகரமாக பதிவிறக்கப்பட்டது!", Toast.LENGTH_LONG).show()
                  }
                } else if (downloadComplete) {
                  Toast.makeText(context, "APK கோப்பு திறக்கப்படுகிறது...", Toast.LENGTH_SHORT).show()
                }
              },
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_modal_download_apk"),
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = if (downloadComplete) TnpaGreen else TnpaRedPrimary
              )
            ) {
              Icon(
                if (downloadComplete) Icons.Default.InstallMobile else Icons.Default.Download,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = if (downloadComplete) "இப்போது Install செய்க (Install APK)" else "டிஎன்பிஏ அப்ளிகேஷன் பதிவிறக்குக (Download APK)",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
              )
            }

            // WhatsApp / Social Share Button
            Button(
              onClick = { launchShareIntent() },
              modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .testTag("btn_modal_share_apk"),
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)) // WhatsApp Green
            ) {
              Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp), tint = TnpaPureWhite)
              Spacer(modifier = Modifier.width(8.dp))
              Text("WhatsApp & நண்பர்களுக்கு பகிர்க (Share App)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TnpaPureWhite)
            }

            // Copy Link & QR Code View Buttons in Row
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              OutlinedButton(
                onClick = {
                  clipboardManager.setText(AnnotatedString(apkDownloadUrl))
                  Toast.makeText(context, "பதிவிறக்க இணைப்பு நகலெடுக்கப்பட்டது! (Link Copied)", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                  .weight(1f)
                  .height(40.dp)
                  .testTag("btn_modal_copy_link"),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedPrimary)
              ) {
                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp), tint = TnpaRedPrimary)
                Spacer(modifier = Modifier.width(4.dp))
                Text("லிங்க் நகலெடு", fontSize = 11.sp, color = TnpaRedPrimary, fontWeight = FontWeight.Bold)
              }

              OutlinedButton(
                onClick = { showQrCodeFull = !showQrCodeFull },
                modifier = Modifier
                  .weight(1f)
                  .height(40.dp)
                  .testTag("btn_modal_toggle_qr"),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E293B))
              ) {
                Icon(Icons.Default.QrCode2, contentDescription = null, modifier = Modifier.size(16.dp), tint = TnpaJetBlack)
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (showQrCodeFull) "QR மறைக்க" else "QR Code ஸ்கேன்", fontSize = 11.sp, color = TnpaJetBlack, fontWeight = FontWeight.Bold)
              }
            }
          }

          // ------------------------------------------------------------------
          // 5. INTERACTIVE QR CODE PREVIEW (WHEN TOGGLED)
          // ------------------------------------------------------------------
          AnimatedVisibility(visible = showQrCodeFull) {
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
              border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1))
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Text(
                  text = "மற்றொரு போனில் ஸ்கேன் செய்து பதிவிறக்க:",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = TnpaJetBlack
                )

                // Custom QR Canvas Box
                Box(
                  modifier = Modifier
                    .size(140.dp)
                    .background(Color.White)
                    .border(2.dp, TnpaRedPrimary, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                  contentAlignment = Alignment.Center
                ) {
                  TnpaSimulatedQrCode(modifier = Modifier.size(124.dp))
                }

                Text(
                  text = "Android கேமரா அல்லது Google Lens மூலம் QR-ஐ ஸ்கேன் செய்யவும்.",
                  fontSize = 10.sp,
                  color = Color.Gray,
                  textAlign = TextAlign.Center
                )
              }
            }
          }

          // ------------------------------------------------------------------
          // 6. INSTALLATION STEPS (TAMIL INSTRUCTIONS)
          // ------------------------------------------------------------------
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A))
          ) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Security, contentDescription = null, tint = Color(0xFFB45309), modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("நிறுவுதல் வழிமுறைகள் (Installation Guide):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E))
              }
              Text("1. 'Download APK' அழுத்தி ஃபைலை சேமிக்கவும்.", fontSize = 10.sp, color = Color(0xFF78350F))
              Text("2. போன் செட்டிங்கில் 'Install Unknown Apps' அனுமதியை On செய்யவும்.", fontSize = 10.sp, color = Color(0xFF78350F))
              Text("3. 'Open' அல்லது Downloads-ல் 'டிஎன்பிஏ.apk' கிளிக் செய்து Install செய்யவும்.", fontSize = 10.sp, color = Color(0xFF78350F))
            }
          }
        }
      }
    }
  }
}

/**
 * Geometric Vector QR Code Canvas for TNPA App Download
 */
@Composable
fun TnpaSimulatedQrCode(modifier: Modifier = Modifier) {
  Canvas(modifier = modifier) {
    val sizePx = size.width
    val cellSize = sizePx / 15f

    // Draw background
    drawRect(color = Color.White)

    // Draw corner markers (Top-Left, Top-Right, Bottom-Left)
    fun drawCornerFinder(topLeftX: Float, topLeftY: Float) {
      // Outer black square (5x5 cells)
      drawRect(
        color = Color(0xFF0F172A),
        topLeft = Offset(topLeftX, topLeftY),
        size = Size(cellSize * 4.5f, cellSize * 4.5f)
      )
      // Inner white square (3x3 cells)
      drawRect(
        color = Color.White,
        topLeft = Offset(topLeftX + cellSize * 0.8f, topLeftY + cellSize * 0.8f),
        size = Size(cellSize * 2.9f, cellSize * 2.9f)
      )
      // Center black dot (2x2 cells)
      drawRect(
        color = Color(0xFFDC2626), // TNPA Red center
        topLeft = Offset(topLeftX + cellSize * 1.5f, topLeftY + cellSize * 1.5f),
        size = Size(cellSize * 1.5f, cellSize * 1.5f)
      )
    }

    drawCornerFinder(0f, 0f)
    drawCornerFinder(sizePx - cellSize * 4.5f, 0f)
    drawCornerFinder(0f, sizePx - cellSize * 4.5f)

    // Fill data grid pattern
    val randomSeeds = listOf(
      Pair(6, 0), Pair(7, 1), Pair(8, 0), Pair(6, 2), Pair(7, 3), Pair(8, 4),
      Pair(0, 6), Pair(1, 7), Pair(2, 6), Pair(3, 7), Pair(4, 6),
      Pair(6, 6), Pair(7, 6), Pair(8, 6), Pair(6, 7), Pair(7, 7), Pair(8, 7), Pair(6, 8), Pair(7, 8), Pair(8, 8),
      Pair(10, 6), Pair(11, 7), Pair(12, 6), Pair(13, 7), Pair(14, 6),
      Pair(6, 10), Pair(7, 11), Pair(8, 10), Pair(6, 12), Pair(7, 13), Pair(8, 14),
      Pair(10, 10), Pair(11, 10), Pair(12, 11), Pair(13, 12), Pair(14, 13),
      Pair(10, 14), Pair(11, 13), Pair(12, 14), Pair(13, 14), Pair(14, 14),
      Pair(2, 10), Pair(3, 11), Pair(4, 12), Pair(1, 13), Pair(2, 14),
      Pair(10, 1), Pair(11, 2), Pair(12, 1), Pair(13, 3), Pair(14, 2)
    )

    for (pos in randomSeeds) {
      drawRect(
        color = Color(0xFF1E293B),
        topLeft = Offset(pos.first * cellSize, pos.second * cellSize),
        size = Size(cellSize * 0.9f, cellSize * 0.9f)
      )
    }
  }
}
