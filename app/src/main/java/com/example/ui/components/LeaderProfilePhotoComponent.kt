package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.SubcomposeAsyncImage
import com.example.model.AdminHierarchyLevel
import com.example.ui.theme.TnpaCharcoal
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedPrimary
import com.example.ui.theme.TnpaRedSoft

/**
 * Standard Leader Photo URLs and High-Quality Portrait Fallbacks.
 */
object LeaderPhotoAssets {
  // Curated professional leader photos & verified portraits
  const val MICHAEL_ALVIN_PHOTO = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=500&auto=format&fit=crop&q=80"
  const val XAVIER_BABU_PHOTO = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=500&auto=format&fit=crop&q=80"
  const val SAKTHIVEL_PHOTO = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=500&auto=format&fit=crop&q=80"
  const val DISTRICT_PRESIDENT_DEFAULT = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=500&auto=format&fit=crop&q=80"
  const val DISTRICT_SECRETARY_DEFAULT = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=500&auto=format&fit=crop&q=80"
  const val YOUTH_WING_DEFAULT = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=500&auto=format&fit=crop&q=80"

  fun getLocalDrawableForLeader(name: String, designation: String = ""): Int? {
    val search = "$name $designation".lowercase()
    return when {
      search.contains("ஆல்வின்") || search.contains("alvin") || (search.contains("தலைவர்") && !search.contains("மாவட்ட")) || (search.contains("president") && !search.contains("district")) -> {
        com.example.R.drawable.drawable_state_president
      }
      search.contains("சேவியர்") || search.contains("xavier") || search.contains("பொதுச் செயலாளர்") || search.contains("general secretary") -> {
        com.example.R.drawable.drawable_state_general_secretary
      }
      search.contains("சக்திவேல்") || search.contains("sakthivel") || search.contains("பொருளாளர்") || search.contains("treasurer") -> {
        com.example.R.drawable.drawable_state_treasurer
      }
      else -> null
    }
  }

  fun getSuggestedPhotoForName(name: String, level: AdminHierarchyLevel? = null): String {
    return when {
      name.contains("ஆல்வின்", ignoreCase = true) || name.contains("Alvin", ignoreCase = true) -> MICHAEL_ALVIN_PHOTO
      name.contains("சேவியர்", ignoreCase = true) || name.contains("Xavier", ignoreCase = true) -> XAVIER_BABU_PHOTO
      name.contains("சக்திவேல்", ignoreCase = true) || name.contains("Sakthivel", ignoreCase = true) -> SAKTHIVEL_PHOTO
      level == AdminHierarchyLevel.DISTRICT_YOUTH || level == AdminHierarchyLevel.UNION_YOUTH || level == AdminHierarchyLevel.CITY_YOUTH -> YOUTH_WING_DEFAULT
      level == AdminHierarchyLevel.DISTRICT -> DISTRICT_PRESIDENT_DEFAULT
      else -> DISTRICT_SECRETARY_DEFAULT
    }
  }
}

/**
 * Reusable Leader Profile Photo View with:
 * - Dynamic photo loading via Coil
 * - Gold / Red crest ring
 * - Tap-to-enlarge High-Res preview modal
 * - High-contrast Tamil Initials fallback
 */
@Composable
fun LeaderProfilePhotoView(
  photoUrl: String?,
  fullName: String,
  tamilName: String,
  designation: String,
  level: AdminHierarchyLevel? = null,
  district: String = "தமிழ்நாடு (Tamil Nadu)",
  mobile: String = "",
  size: Dp = 56.dp,
  isTopLeader: Boolean = false,
  enableEnlargeOnClick: Boolean = true,
  modifier: Modifier = Modifier
) {
  var showFullPhotoModal by remember { mutableStateOf(false) }

  // Resolved local drawable or photo URL
  val localDrawableId = LeaderPhotoAssets.getLocalDrawableForLeader(fullName.ifBlank { tamilName }, designation)
  val isCustomUploadedPhoto = !photoUrl.isNullOrBlank() && !photoUrl.startsWith("https://images.unsplash.com")
  val effectivePhotoUrl = if (isCustomUploadedPhoto) {
    photoUrl
  } else if (localDrawableId != null) {
    null // Handled directly via localDrawableId
  } else if (!photoUrl.isNullOrBlank()) {
    photoUrl
  } else {
    LeaderPhotoAssets.getSuggestedPhotoForName(fullName.ifBlank { tamilName }, level)
  }

  val ringColor = when {
    isTopLeader -> TnpaGold
    level == AdminHierarchyLevel.STATE -> TnpaRedPrimary
    level == AdminHierarchyLevel.ZONE -> Color(0xFF0284C7)
    level == AdminHierarchyLevel.DISTRICT -> Color(0xFF16A34A)
    else -> TnpaGold
  }

  Box(
    modifier = modifier
      .size(size)
      .clip(CircleShape)
      .background(
        Brush.linearGradient(
          colors = listOf(Color(0xFF1F2937), Color(0xFF111827))
        )
      )
      .border(
        width = if (isTopLeader) 2.5.dp else 1.8.dp,
        brush = Brush.sweepGradient(
          listOf(ringColor, TnpaGold, ringColor)
        ),
        shape = CircleShape
      )
      .then(
        if (enableEnlargeOnClick) {
          Modifier.clickable { showFullPhotoModal = true }
        } else Modifier
      ),
    contentAlignment = Alignment.Center
  ) {
    if (isCustomUploadedPhoto && !photoUrl.isNullOrBlank()) {
      SubcomposeAsyncImage(
        model = photoUrl,
        contentDescription = "Photo of $tamilName",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .fillMaxSize()
          .clip(CircleShape),
        loading = {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
              modifier = Modifier.size(size * 0.4f),
              color = TnpaGold,
              strokeWidth = 2.dp
            )
          }
        },
        error = {
          if (localDrawableId != null) {
            Image(
              painter = androidx.compose.ui.res.painterResource(id = localDrawableId),
              contentDescription = "Photo of $tamilName",
              contentScale = ContentScale.Crop,
              modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
            )
          } else {
            LeaderFallbackAvatar(tamilName = tamilName, fullName = fullName, size = size, isTopLeader = isTopLeader)
          }
        }
      )
    } else if (localDrawableId != null) {
      Image(
        painter = androidx.compose.ui.res.painterResource(id = localDrawableId),
        contentDescription = "Photo of $tamilName",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .fillMaxSize()
          .clip(CircleShape)
      )
    } else if (!effectivePhotoUrl.isNullOrBlank()) {
      SubcomposeAsyncImage(
        model = effectivePhotoUrl,
        contentDescription = "Photo of $tamilName",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .fillMaxSize()
          .clip(CircleShape),
        loading = {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
              modifier = Modifier.size(size * 0.4f),
              color = TnpaGold,
              strokeWidth = 2.dp
            )
          }
        },
        error = {
          LeaderFallbackAvatar(tamilName = tamilName, fullName = fullName, size = size, isTopLeader = isTopLeader)
        }
      )
    } else {
      LeaderFallbackAvatar(tamilName = tamilName, fullName = fullName, size = size, isTopLeader = isTopLeader)
    }

    // Top Leader Mini Crown / Star Indicator
    if (isTopLeader) {
      Box(
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .size(size * 0.35f)
          .clip(CircleShape)
          .background(TnpaGold)
          .border(1.dp, TnpaJetBlack, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          Icons.Default.Verified,
          contentDescription = "Verified Leader",
          tint = TnpaJetBlack,
          modifier = Modifier.size(size * 0.25f)
        )
      }
    }
  }

  // Full Screen Zoom & Official Badge Modal
  if (showFullPhotoModal) {
    LeaderPhotoBadgeModal(
      photoUrl = photoUrl ?: "",
      fullName = fullName,
      tamilName = tamilName,
      designation = designation,
      level = level,
      district = district,
      mobile = mobile,
      onDismiss = { showFullPhotoModal = false }
    )
  }
}

@Composable
private fun LeaderFallbackAvatar(
  tamilName: String,
  fullName: String,
  size: Dp,
  isTopLeader: Boolean
) {
  val initials = when {
    fullName.isNotBlank() -> fullName.split(" ").take(2).mapNotNull { it.firstOrNull()?.uppercase() }.joinToString("")
    tamilName.isNotBlank() -> tamilName.take(2)
    else -> "TN"
  }.ifBlank { "TN" }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Brush.linearGradient(
          colors = listOf(Color(0xFF374151), Color(0xFF111827))
        )
      ),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = initials,
      color = if (isTopLeader) TnpaGold else TnpaPureWhite,
      fontSize = (size.value * 0.35f).sp,
      fontWeight = FontWeight.Black
    )
  }
}

/**
 * High-Definition Leadership Badge & Photo Modal (முழுத்திரை புகைப்பட காட்சி).
 */
@Composable
fun LeaderPhotoBadgeModal(
  photoUrl: String,
  fullName: String,
  tamilName: String,
  designation: String,
  level: AdminHierarchyLevel? = null,
  district: String,
  mobile: String,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.92f)
        .clip(RoundedCornerShape(20.dp))
        .background(Color(0xFF0F172A))
        .border(2.dp, TnpaGold, RoundedCornerShape(20.dp)),
      color = Color(0xFF0F172A),
      tonalElevation = 8.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Top Bar: Sangam Title & Close
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            TnpaOfficialEmblem(sizeDp = 28.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் (TNPA²)",
                color = TnpaPureWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black
              )
              Text(
                text = "அதிகாரப்பூர்வ நிர்வாகி புகைப்பட அட்டை",
                color = TnpaGold,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier
              .size(32.dp)
              .background(Color(0xFF334155), CircleShape)
          ) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TnpaPureWhite, modifier = Modifier.size(18.dp))
          }
        }

        HorizontalDivider(color = Color(0xFF334155))

        // Large Profile Photo with Gold Laurel Frame
        val localModalResId = LeaderPhotoAssets.getLocalDrawableForLeader(fullName.ifBlank { tamilName }, designation)
        val isCustomPhoto = photoUrl.isNotBlank() && !photoUrl.startsWith("https://images.unsplash.com")
        Box(
          modifier = Modifier
            .size(170.dp)
            .clip(CircleShape)
            .background(Color(0xFF1E293B))
            .border(4.dp, Brush.sweepGradient(listOf(TnpaGold, TnpaRedPrimary, TnpaGold)), CircleShape)
            .shadow(12.dp, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          if (isCustomPhoto) {
            SubcomposeAsyncImage(
              model = photoUrl,
              contentDescription = "Photo of $tamilName",
              contentScale = ContentScale.Crop,
              modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
              loading = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                  CircularProgressIndicator(color = TnpaGold)
                }
              },
              error = {
                if (localModalResId != null) {
                  Image(
                    painter = androidx.compose.ui.res.painterResource(id = localModalResId),
                    contentDescription = "Photo of $tamilName",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                      .fillMaxSize()
                      .clip(CircleShape)
                  )
                } else {
                  Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                  ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(60.dp))
                    Text(tamilName.take(6), color = TnpaPureWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                  }
                }
              }
            )
          } else if (localModalResId != null) {
            Image(
              painter = androidx.compose.ui.res.painterResource(id = localModalResId),
              contentDescription = "Photo of $tamilName",
              contentScale = ContentScale.Crop,
              modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
            )
          } else if (photoUrl.isNotBlank()) {
            SubcomposeAsyncImage(
              model = photoUrl,
              contentDescription = "Photo of $tamilName",
              contentScale = ContentScale.Crop,
              modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
              loading = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                  CircularProgressIndicator(color = TnpaGold)
                }
              },
              error = {
                Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center
                ) {
                  Icon(Icons.Default.Person, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(60.dp))
                  Text(tamilName.take(6), color = TnpaPureWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
              }
            )
          } else {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Icon(Icons.Default.Person, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(60.dp))
              Text(tamilName.take(6), color = TnpaPureWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }
        }

        // Leader Name & Designation
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = tamilName.ifBlank { fullName },
              color = TnpaPureWhite,
              fontSize = 18.sp,
              fontWeight = FontWeight.Black,
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
              Icons.Default.Verified,
              contentDescription = "Verified Leader",
              tint = Color(0xFF22C55E),
              modifier = Modifier.size(20.dp)
            )
          }

          if (fullName.isNotBlank() && fullName != tamilName) {
            Text(
              text = fullName,
              color = Color(0xFF94A3B8),
              fontSize = 13.sp,
              fontWeight = FontWeight.SemiBold
            )
          }

          // Designation Pill
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(TnpaRedPrimary)
              .padding(horizontal = 14.dp, vertical = 4.dp)
          ) {
            Text(
              text = designation,
              color = TnpaPureWhite,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }

          // Level & District
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 4.dp)
          ) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = district,
              color = Color(0xFFE2E8F0),
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }

        // Action Buttons: Call, WhatsApp, Share
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          if (mobile.isNotBlank()) {
            Button(
              onClick = {
                val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${mobile.replace(" ", "")}"))
                context.startActivity(callIntent)
              },
              modifier = Modifier
                .weight(1f)
                .height(42.dp),
              colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen),
              shape = RoundedCornerShape(10.dp)
            ) {
              Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("அழைக்க", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
              onClick = {
                try {
                  val cleanPhone = mobile.replace("+91", "").replace(" ", "").trim()
                  val url = "https://api.whatsapp.com/send?phone=91$cleanPhone"
                  val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                  context.startActivity(intent)
                } catch (e: Exception) {}
              },
              modifier = Modifier
                .weight(1f)
                .height(42.dp),
              border = BorderStroke(1.dp, Color(0xFF22C55E)),
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF22C55E))
            ) {
              Text("வாட்ஸ்அப்", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}
