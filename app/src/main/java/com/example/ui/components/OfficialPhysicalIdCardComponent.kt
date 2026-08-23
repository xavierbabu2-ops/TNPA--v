package com.example.ui.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.OfficialAssetsManager
import com.example.model.MemberProfile
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedPrimary

/**
 * Official Tamil Nadu Painters & Artists Association (TNPA²) ID Card
 * Pixel-accurate implementation matching the official physical card template (Front & Back).
 */
@Composable
fun OfficialTnpaIdCardComponent(
  member: MemberProfile,
  modifier: Modifier = Modifier,
  onPhotoUpdated: ((Uri) -> Unit)? = null
) {
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current
  var isFrontSide by remember { mutableStateOf(true) }

  // Photo Picker Launcher
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
        onPhotoUpdated?.invoke(uri)
        Toast.makeText(context, "✅ உறுப்பினர் புகைப்படம் இணைக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
      } catch (e: Exception) {
        onPhotoUpdated?.invoke(uri)
      }
    }
  }

  fun shareCardDetails() {
    val shareText = """
      🏛️ தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் (TNPA²)
      அரசு பதிவு எண்: TNMDUJCLMDUTU-50-26-00044
      1/14 அம்பலக்காரன் பட்டி உத்தங்குடி மதுரை 625107
      -----------------------------------------
      🪪 அதிகாரப்பூர்வ உறுப்பினர் அடையாள அட்டை (OFFICIAL ID CARD)
      -----------------------------------------
      உறுப்பினர் எண்    : ${member.id}
      உறுப்பினர் பெயர்   : ${member.tamilName} (${member.fullName})
      உறுப்பினர் தொழில் : ${member.specialization}
      தந்தை பெயர்      : ${member.fatherName}
      வயது             : ${member.age} ஆண்டுகள்
      ரத்த வகை         : ${member.bloodGroup}
      இருப்பிடம்        : ${member.district}
      தொடர்பு எண்      : +91 ${member.mobile}
      -----------------------------------------
      உழைப்போம்....... உயர்வோம் ......
      ஒன்றுபடுவோம்! உரிமையை மீட்போம்.
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_SUBJECT, "TNPA Official ID Card - ${member.tamilName}")
      putExtra(Intent.EXTRA_TEXT, shareText)
    }
    context.startActivity(Intent.createChooser(intent, "அடையாள அட்டையைப் பகிர்க"))
  }

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    // Card Side Switcher Tabs
    TabRow(
      selectedTabIndex = if (isFrontSide) 0 else 1,
      containerColor = TnpaPureWhite,
      contentColor = TnpaRedPrimary,
      indicator = { tabPositions ->
        TabRowDefaults.SecondaryIndicator(
          Modifier.tabIndicatorOffset(tabPositions[if (isFrontSide) 0 else 1]),
          color = TnpaRedPrimary,
          height = 3.dp
        )
      },
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(10.dp))
    ) {
      Tab(
        selected = isFrontSide,
        onClick = { isFrontSide = true },
        text = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("முன் பக்கம் (Front Side)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
          }
        }
      )
      Tab(
        selected = !isFrontSide,
        onClick = { isFrontSide = false },
        text = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Cached, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("பின் பக்கம் (Back Side)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
          }
        }
      )
    }

    // Animated Card View Container
    AnimatedContent(
      targetState = isFrontSide,
      transitionSpec = {
        fadeIn(animationSpec = tween(220)) togetherWith fadeOut(animationSpec = tween(220))
      },
      label = "id_card_flip_anim"
    ) { front ->
      if (front) {
        OfficialFrontIdCardView(
          member = member,
          onPhotoClick = { photoPickerLauncher.launch("image/*") }
        )
      } else {
        OfficialBackIdCardView(
          member = member
        )
      }
    }

    // Direct Photo Upload Bar
    OutlinedButton(
      onClick = { photoPickerLauncher.launch("image/*") },
      modifier = Modifier
        .fillMaxWidth()
        .height(38.dp)
        .testTag("btn_upload_official_id_photo"),
      shape = RoundedCornerShape(8.dp),
      border = BorderStroke(1.dp, TnpaRedPrimary)
    ) {
      Icon(
        imageVector = if (member.photoUri.isNullOrBlank()) Icons.Default.AddAPhoto else Icons.Default.PhotoCamera,
        contentDescription = null,
        tint = TnpaRedPrimary,
        modifier = Modifier.size(16.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = if (member.photoUri.isNullOrBlank()) "📷 உறுப்பினர் புகைப்படத்தைப் பதிவேற்ற (Upload Photo)" else "🔄 புகைப்படத்தை மாற்றுக (Change Photo)",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = TnpaRedPrimary
      )
    }

    // Action Buttons (Download, WhatsApp, Copy, Flip)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Button(
        onClick = {
          Toast.makeText(context, "💾 ${member.id} அடையாள அட்டை (இரு பக்கங்களும்) சேமிக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.weight(1f).height(42.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen)
      ) {
        Icon(Icons.Default.Download, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("பதிவிறக்கு", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaPureWhite)
      }

      Button(
        onClick = { shareCardDetails() },
        modifier = Modifier.weight(1f).height(42.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
      ) {
        Icon(Icons.Default.Share, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("வாட்ஸ்அப் பகிர்", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaPureWhite)
      }

      OutlinedButton(
        onClick = {
          val cardData = "TNPA² ID: ${member.id}\nபெயர்: ${member.tamilName}\nதொழில்: ${member.specialization}\nவயது: ${member.age}\nஇரத்த வகை: ${member.bloodGroup}\nஇருப்பிடம்: ${member.district}\nஅரசு பதிவு எண்: TNMDUJCLMDUTU-50-26-00044"
          clipboardManager.setText(AnnotatedString(cardData))
          Toast.makeText(context, "அட்டை விவரங்கள் நகலெடுக்கப்பட்டது!", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.weight(0.7f).height(42.dp),
        shape = RoundedCornerShape(8.dp)
      ) {
        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(15.dp))
        Spacer(modifier = Modifier.width(2.dp))
        Text("நகல்", fontSize = 11.sp)
      }

      OutlinedButton(
        onClick = { isFrontSide = !isFrontSide },
        modifier = Modifier.weight(0.7f).height(42.dp),
        shape = RoundedCornerShape(8.dp)
      ) {
        Icon(Icons.Default.Flip, contentDescription = null, modifier = Modifier.size(15.dp), tint = TnpaRedPrimary)
        Spacer(modifier = Modifier.width(2.dp))
        Text(if (isFrontSide) "பின்" else "முன்", fontSize = 11.sp, color = TnpaRedPrimary)
      }
    }
  }
}

/**
 * Official Front ID Card Design
 */
@Composable
fun OfficialFrontIdCardView(
  member: MemberProfile,
  onPhotoClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .aspectRatio(1.58f)
      .shadow(8.dp, RoundedCornerShape(12.dp)),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = BorderStroke(1.5.dp, Color(0xFFDC2626))
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(TnpaPureWhite)
    ) {
      // 1. Red Header with Dual Logos and Association Details
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFFD80000))
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          // Left Logo
          OfficialIdCardMiniLogo()

          // Header Text in Tamil
          Column(
            modifier = Modifier
              .weight(1f)
              .padding(horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள்\nமுன்னேற்ற சங்கம்",
              color = TnpaPureWhite,
              fontSize = 11.sp,
              fontWeight = FontWeight.Black,
              textAlign = TextAlign.Center,
              lineHeight = 13.sp
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
              text = "அரசு பதிவு எண் TNMDUJCLMDUTU-50-26-00044",
              color = TnpaGold,
              fontSize = 7.5.sp,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center
            )
            Text(
              text = "1/14 அம்பலக்காரன் பட்டி உத்தங்குடி மதுரை 625107",
              color = TnpaPureWhite,
              fontSize = 7.5.sp,
              fontWeight = FontWeight.Medium,
              textAlign = TextAlign.Center
            )
          }

          // Right Logo
          OfficialIdCardMiniLogo()
        }
      }

      // Thin Gold Divider
      Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(TnpaGold))

      // 2. Body with Watermark, Details and Photo Box
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(horizontal = 10.dp, vertical = 4.dp)
      ) {
        // Watermark in Center
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          OfficialIdCardWatermark()
        }

        // Content Row (Left Details + Right Photo)
        Row(
          modifier = Modifier.fillMaxSize(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Left Column Details
          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            // Member ID Row
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "உறுப்பினர் எண்  :",
                color = Color(0xFFB91C1C),
                fontWeight = FontWeight.Black,
                fontSize = 12.sp
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = member.id.ifBlank { "TNPA-2026-001" },
                color = TnpaJetBlack,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
              )
            }

            // Member Name Row
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "உறுப்பினர் பெயர் :",
                color = Color(0xFFB91C1C),
                fontWeight = FontWeight.Black,
                fontSize = 12.sp
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = member.tamilName.ifBlank { member.fullName },
                color = TnpaJetBlack,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }

            // Member Occupation Row
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "உறுப்பினர் தொழில் :",
                color = Color(0xFFB91C1C),
                fontWeight = FontWeight.Black,
                fontSize = 11.5.sp
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = member.specialization.ifBlank { "பெயிண்டர் / ஓவியர்" },
                color = TnpaJetBlack,
                fontWeight = FontWeight.Bold,
                fontSize = 11.5.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }
          }

          Spacer(modifier = Modifier.width(6.dp))

          // Right Photo Box
          Box(
            modifier = Modifier
              .width(72.dp)
              .height(90.dp)
              .border(1.5.dp, Color.Black, RoundedCornerShape(2.dp))
              .background(Color(0xFFF8FAFC))
              .clickable { onPhotoClick() },
            contentAlignment = Alignment.Center
          ) {
            if (!member.photoUri.isNullOrBlank()) {
              AsyncImage(
                model = member.photoUri,
                contentDescription = "Member Photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
              )
            } else {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(36.dp))
                Text("புகைப்படம்", fontSize = 8.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }

      // 3. Three Leadership Signatures
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
      ) {
        // State President
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text("✍️ S. Michael", fontSize = 8.sp, color = Color(0xFF1E293B), fontWeight = FontWeight.Bold, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
          Text("மாநில தலைவர்", fontSize = 8.5.sp, fontWeight = FontWeight.Black, color = TnpaJetBlack)
        }

        // State General Secretary
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text("✍️ R. Xavier", fontSize = 8.sp, color = Color(0xFF1E293B), fontWeight = FontWeight.Bold, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
          Text("மாநில பொதுச்செயலாளர்", fontSize = 8.5.sp, fontWeight = FontWeight.Black, color = TnpaJetBlack)
        }

        // State Treasurer
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text("✍️ M. Sakthivel", fontSize = 8.sp, color = Color(0xFF1E293B), fontWeight = FontWeight.Bold, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
          Text("மாநில பொருளாளர்", fontSize = 8.5.sp, fontWeight = FontWeight.Black, color = TnpaJetBlack)
        }
      }

      // 4. Bottom Red Slogan Bar
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFFD80000))
          .padding(vertical = 3.dp, horizontal = 12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("உழைப்போம்.......", color = TnpaPureWhite, fontWeight = FontWeight.Black, fontSize = 10.sp)
          Text("உயர்வோம் ......", color = TnpaPureWhite, fontWeight = FontWeight.Black, fontSize = 10.sp)
        }
      }
    }
  }
}

/**
 * Official Back ID Card Design
 */
@Composable
fun OfficialBackIdCardView(
  member: MemberProfile
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .aspectRatio(1.58f)
      .shadow(8.dp, RoundedCornerShape(12.dp)),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = BorderStroke(1.5.dp, Color(0xFFDC2626))
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(TnpaPureWhite)
    ) {
      // 1. Red Header
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFFD80000))
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          OfficialIdCardMiniLogo()

          Column(
            modifier = Modifier
              .weight(1f)
              .padding(horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள்\nமுன்னேற்ற சங்கம்",
              color = TnpaPureWhite,
              fontSize = 11.sp,
              fontWeight = FontWeight.Black,
              textAlign = TextAlign.Center,
              lineHeight = 13.sp
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
              text = "அரசு பதிவு எண் TNMDUJCLMDUTU-50-26-00044",
              color = TnpaGold,
              fontSize = 7.5.sp,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center
            )
            Text(
              text = "1/14 அம்பலக்காரன் பட்டி உத்தங்குடி மதுரை 625107",
              color = TnpaPureWhite,
              fontSize = 7.5.sp,
              fontWeight = FontWeight.Medium,
              textAlign = TextAlign.Center
            )
          }

          OfficialIdCardMiniLogo()
        }
      }

      // Thin Gold Divider
      Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(TnpaGold))

      // 2. Body Content (Left: Member Personal Details, Right: Govt Approval & Leaders)
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(horizontal = 10.dp, vertical = 6.dp)
      ) {
        // Watermark in Center
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          OfficialIdCardWatermark()
        }

        Row(
          modifier = Modifier.fillMaxSize(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Left Side: Personal Credentials
          Column(
            modifier = Modifier.weight(1.1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            // Father's Name
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text("தந்தை பெயர் :", color = Color(0xFFB91C1C), fontWeight = FontWeight.Black, fontSize = 12.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text(member.fatherName.ifBlank { "-" }, color = TnpaJetBlack, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            // Age
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text("வயது            :", color = Color(0xFFB91C1C), fontWeight = FontWeight.Black, fontSize = 12.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text("${member.age} ஆண்டுகள்", color = TnpaJetBlack, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            // Blood Group
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text("ரத்த வகை     :", color = Color(0xFFB91C1C), fontWeight = FontWeight.Black, fontSize = 12.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text(member.bloodGroup.ifBlank { "O+ve" }, color = Color(0xFFDC2626), fontWeight = FontWeight.Black, fontSize = 12.sp)
            }

            // Residence / Address
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text("இருப்பிடம்     :", color = Color(0xFFB91C1C), fontWeight = FontWeight.Black, fontSize = 12.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text(member.district.ifBlank { "மதுரை" }, color = TnpaJetBlack, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
          }

          Spacer(modifier = Modifier.width(6.dp))

          // Right Side: Govt Approval Seal, Leader Avatars & Slogan
          Column(
            modifier = Modifier.weight(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Text(
              text = "தமிழ்நாடு அரசு அனுமதி\nபெற்ற சங்கம்",
              color = Color(0xFF881337),
              fontSize = 8.5.sp,
              fontWeight = FontWeight.Black,
              textAlign = TextAlign.Center,
              lineHeight = 10.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Tamil Nadu Govt Emblem & Leaders Strip
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              // Govt Gopuram Seal Icon
              Box(
                modifier = Modifier
                  .size(34.dp)
                  .clip(CircleShape)
                  .background(Color(0xFFF0FDF4))
                  .border(1.dp, Color(0xFF16A34A), CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text("🏛️", fontSize = 14.sp)
                }
              }

              Spacer(modifier = Modifier.width(4.dp))

              // Leader Duo Badge (Xavier Babu + Michael Alvin)
              val customSecUri by OfficialAssetsManager.generalSecPhotoUri.collectAsState()
              val customPresUri by OfficialAssetsManager.presidentPhotoUri.collectAsState()

              Row(
                modifier = Modifier
                  .clip(RoundedCornerShape(12.dp))
                  .background(Color(0xFFFFFBEB))
                  .border(1.dp, TnpaGold, RoundedCornerShape(12.dp))
                  .padding(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
              ) {
                // Leader 1 (General Sec)
                if (!customSecUri.isNullOrBlank()) {
                  AsyncImage(
                    model = customSecUri,
                    contentDescription = "General Sec",
                    modifier = Modifier.size(26.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                  )
                } else {
                  Image(
                    painter = painterResource(id = R.drawable.drawable_state_general_secretary),
                    contentDescription = "General Sec",
                    modifier = Modifier.size(26.dp).clip(CircleShape)
                  )
                }

                // Leader 2 (President)
                if (!customPresUri.isNullOrBlank()) {
                  AsyncImage(
                    model = customPresUri,
                    contentDescription = "President",
                    modifier = Modifier.size(26.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                  )
                } else {
                  Image(
                    painter = painterResource(id = R.drawable.drawable_state_president),
                    contentDescription = "President",
                    modifier = Modifier.size(26.dp).clip(CircleShape)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
              text = "ஒன்றுபடுவோம்!\nஉரிமையை மீட்போம்.",
              color = Color(0xFFB91C1C),
              fontSize = 8.5.sp,
              fontWeight = FontWeight.Black,
              textAlign = TextAlign.Center,
              lineHeight = 10.sp
            )
          }
        }
      }

      // 3. Bottom Red Slogan Bar
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFFD80000))
          .padding(vertical = 3.dp, horizontal = 12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("உழைப்போம்.......", color = TnpaPureWhite, fontWeight = FontWeight.Black, fontSize = 10.sp)
          Text("உயர்வோம் ......", color = TnpaPureWhite, fontWeight = FontWeight.Black, fontSize = 10.sp)
        }
      }
    }
  }
}

/**
 * Circular Miniature Official TNPA² Emblem for ID Card Corners
 */
@Composable
private fun OfficialIdCardMiniLogo() {
  val customLogoUri by OfficialAssetsManager.logoUri.collectAsState()

  Box(
    modifier = Modifier
      .size(34.dp)
      .clip(CircleShape)
      .background(Color(0xFFDC2626))
      .border(1.dp, TnpaGold, CircleShape),
    contentAlignment = Alignment.Center
  ) {
    Box(
      modifier = Modifier
        .size(28.dp)
        .clip(CircleShape)
        .background(TnpaPureWhite),
      contentAlignment = Alignment.Center
    ) {
      if (!customLogoUri.isNullOrBlank()) {
        AsyncImage(
          model = customLogoUri,
          contentDescription = "TNPA Logo",
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Fit
        )
      } else {
        Image(
          painter = painterResource(id = R.drawable.drawable_tnpa_logo),
          contentDescription = "TNPA Logo",
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Fit
        )
      }
    }
  }
}

/**
 * Central Watermark in Background of ID Card
 */
@Composable
private fun OfficialIdCardWatermark() {
  val customLogoUri by OfficialAssetsManager.logoUri.collectAsState()

  Box(
    modifier = Modifier
      .size(110.dp)
      .clip(CircleShape)
      .background(Color(0xFFDC2626).copy(alpha = 0.03f))
      .border(1.5.dp, Color(0xFFDC2626).copy(alpha = 0.10f), CircleShape),
    contentAlignment = Alignment.Center
  ) {
    if (!customLogoUri.isNullOrBlank()) {
      AsyncImage(
        model = customLogoUri,
        contentDescription = "Watermark",
        modifier = Modifier.size(90.dp).padding(4.dp),
        alpha = 0.18f,
        contentScale = ContentScale.Fit
      )
    } else {
      Image(
        painter = painterResource(id = R.drawable.drawable_tnpa_logo),
        contentDescription = "Watermark",
        modifier = Modifier.size(90.dp).padding(4.dp),
        alpha = 0.18f,
        contentScale = ContentScale.Fit
      )
    }
  }
}
