package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.AdminApprovalRepository
import com.example.model.StateLeaderItem
import com.example.ui.components.AppDownloadModal
import com.example.ui.components.DashboardMemberSearchBarAndDirectory
import com.example.ui.components.StateLeadershipGrandShowcase
import com.example.ui.components.TnpaBrandingCustomizerModal
import com.example.ui.components.TnpaOfficialEmblem
import com.example.ui.components.TnpaOfficialFlagBanner
import com.example.ui.components.TnpaOriginalFlag
import com.example.ui.components.TnpaOriginalLogo
import com.example.ui.theme.TnpaCharcoal
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedLight
import com.example.ui.theme.TnpaRedPrimary
import com.example.ui.theme.TnpaRedSoft

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
  onNavigateToTab: (Int) -> Unit
) {
  val context = LocalContext.current
  var showDownloadModal by remember { mutableStateOf(false) }
  var showBrandingModal by remember { mutableStateOf(false) }

  if (showBrandingModal) {
    TnpaBrandingCustomizerModal(onDismiss = { showBrandingModal = false })
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(TnpaOffWhite)
      .verticalScroll(rememberScrollState())
      .padding(bottom = 24.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {

    // ========================================================================
    // 1. WEBSITE HEADER — RED COLOR BOX (தமிழ்நாடு பெயிண்டர்கள் ஓவியர்கள் முன்னேற்ற சங்கம்)
    // ========================================================================
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.verticalGradient(
            colors = listOf(
              TnpaRedPrimary,
              TnpaRedDark,
              Color(0xFF7F0000)
            )
          )
        )
        .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Centered Official Association Original Logo with Edit affordance
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier.clickable { showBrandingModal = true }
        ) {
          TnpaOriginalLogo(size = 90.dp)
          Box(
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .size(26.dp)
              .clip(CircleShape)
              .background(TnpaJetBlack)
              .border(1.5.dp, TnpaGold, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit Logo", tint = TnpaGold, modifier = Modifier.size(14.dp))
          }
        }

        Text(
          text = "தமிழ்நாடு பெயிண்டர்கள் மற்றும் ஓவியர்கள் முன்னேற்ற சங்கம் (TNPA²)",
          color = TnpaPureWhite,
          fontSize = 18.sp,
          fontWeight = FontWeight.Black,
          textAlign = TextAlign.Center,
          lineHeight = 25.sp
        )

        Text(
          text = "TAMIL NADU PAINTERS & ARTISTS PROGRESSIVE ASSOCIATION (TNPA²)",
          color = TnpaGold,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp,
          textAlign = TextAlign.Center
        )
      }
    }

    // ========================================================================
    // 2. STATE LEADERSHIP GRAND SHOWCASE (மாநில தலைமைப் பொறுப்பாளர்கள்)
    // ========================================================================
    Box(modifier = Modifier.padding(horizontal = 14.dp)) {
      StateLeadershipGrandShowcase(
        onLeaderClick = { onNavigateToTab(3) }
      )
    }

    // ========================================================================
    // 2.1. GOVERNMENT RECOGNITION BOX — GOLD COLOR BOX (அரசு அங்கீகாரம்)
    // ========================================================================
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp)
        .shadow(4.dp, RoundedCornerShape(12.dp))
        .clip(RoundedCornerShape(12.dp))
        .background(
          Brush.horizontalGradient(
            colors = listOf(
              Color(0xFFD97706),
              TnpaGold,
              Color(0xFFF59E0B)
            )
          )
        )
        .border(1.5.dp, Color(0xFFFDE68A), RoundedCornerShape(12.dp))
        .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            Icons.Default.Verified,
            contentDescription = null,
            tint = TnpaJetBlack,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "தமிழக அரசால் அங்கீகரிக்கப்பட்ட பெயிண்டர்களுக்கான சங்கம்",
            style = TextStyle(
              color = TnpaPureWhite,
              fontSize = 13.sp,
              fontWeight = FontWeight.Black,
              shadow = Shadow(
                color = TnpaJetBlack.copy(alpha = 0.9f),
                offset = Offset(1.5f, 1.5f),
                blurRadius = 3f
              )
            ),
            textAlign = TextAlign.Center
          )
        }

        Text(
          text = "தமிழக அரசால் அங்கீகரிக்கப்பட்ட தொழிற்சங்க பதிவெண்: TNMDUJCLMDUTU-TNMDUJCLMDUTU-50-26-0044",
          color = TnpaJetBlack,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center
        )
      }
    }

    // ========================================================================
    // 2.2. SANGAM OFFICIAL FLAG (சங்கத்தின் அதிகாரப்பூர்வ கொடி)
    // ========================================================================
    Box(modifier = Modifier.padding(horizontal = 14.dp)) {
      TnpaOfficialFlagBanner(onCustomizeClick = { showBrandingModal = true })
    }

    // ========================================================================
    // 2.2. APP DOWNLOAD & DISTRIBUTION CARD (டிஎன்பிஏ மொபைல் செயலி டவுன்லோடு)
    // ========================================================================
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(
        containerColor = Color(0xFF0F172A) // Sleek Dark Slate
      ),
      border = androidx.compose.foundation.BorderStroke(1.5.dp, TnpaGold)
    ) {
      Column(
        modifier = Modifier.padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
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
                .background(TnpaGold.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                Icons.Default.Android,
                contentDescription = null,
                tint = TnpaGold,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "டிஎன்பிஏ மொபைல் செயலி (TNPA App)",
                color = TnpaPureWhite,
                fontWeight = FontWeight.Black,
                fontSize = 14.sp
              )
              Text(
                text = "அனைத்து தொழிலாளர்களுக்கும் இலவச ஆண்ட்ராய்டு ஆப்",
                color = Color(0xFFCBD5E1),
                fontSize = 11.sp
              )
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(TnpaGold)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text("v2.4.0 APK", color = TnpaJetBlack, fontSize = 9.sp, fontWeight = FontWeight.Black)
          }
        }

        Text(
          text = "அனைத்து உறுப்பினர்களும் நிர்வாகிகள், நலத்திட்டங்கள், நேரலை TV, AI வழிகாட்டி மற்றும் வீடியோ கான்பிரன்ஸ் ஆகியவற்றை ஒரே செயலியில் பெற உங்கள் போனில் டிஎன்பிஏ செயலியை டவுன்லோடு செய்து சக பெயிண்டர்களுடன் பகிருங்கள்.",
          color = Color(0xFFE2E8F0),
          fontSize = 11.sp,
          lineHeight = 16.sp
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = { showDownloadModal = true },
            modifier = Modifier
              .weight(1f)
              .height(38.dp)
              .testTag("btn_home_download_apk"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TnpaGold)
          ) {
            Icon(Icons.Default.Download, contentDescription = null, tint = TnpaJetBlack, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("செயலி டவுன்லோடு", fontSize = 12.sp, fontWeight = FontWeight.Black, color = TnpaJetBlack)
          }

          OutlinedButton(
            onClick = { showDownloadModal = true },
            modifier = Modifier
              .weight(1f)
              .height(38.dp)
              .testTag("btn_home_share_apk"),
            shape = RoundedCornerShape(8.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF22C55E)),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF22C55E))
          ) {
            Icon(Icons.Default.Share, contentDescription = null, tint = Color(0xFF22C55E), modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("பகிர்வு & QR", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // ========================================================================
    // 3. MISSION STATEMENT BOX (கொள்கை & நோக்கங்கள்)
    // ========================================================================
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedSoft)
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(TnpaRedPrimary.copy(alpha = 0.1f))
            .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
          Text(
            text = "சங்கத்தின் கொள்கை முழக்கம்",
            color = TnpaRedPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Text(
          text = "“உழைப்பை மதிப்போம் — தொழிலாளர்களின் உரிமையை காப்போம் — ஒற்றுமையால் எதிர்காலத்தை உருவாக்குவோம்!”",
          color = TnpaRedDark,
          fontSize = 14.sp,
          fontWeight = FontWeight.Black,
          textAlign = TextAlign.Center,
          lineHeight = 20.sp
        )

        HorizontalDivider(color = TnpaRedSoft)

        Text(
          text = "தமிழ்நாடு முழுவதும் உள்ள பெயிண்டர்கள், ஓவியர்கள், போர்டு ரைட்டர்கள், ஸ்பிரே ஆர்ட்டிஸ்ட்கள் மற்றும் உழைக்கும் கட்டுமானத் தொழிலாளர்களின் சட்டரீதியான உரிமைகள், பணியிட பாதுகாப்பு, அரசு நலவாரிய நிதி உதவிகள் மற்றும் எதிர்கால குடும்ப முன்னேற்றத்திற்காக ஒன்றுபட்டு குரல் கொடுப்பதே நமது முதன்மை நோக்கமாகும்.",
          color = TnpaCharcoal,
          fontSize = 12.sp,
          textAlign = TextAlign.Center,
          lineHeight = 18.sp
        )
      }
    }

    // ========================================================================
    // 4. STATE LEADERSHIP SECTION (மாநில நிர்வாகிகள்)
    // ========================================================================
    val stateLeaders = remember { AdminApprovalRepository.getTopStateLeaders() }

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(6.dp, 20.dp)
              .clip(RoundedCornerShape(3.dp))
              .background(TnpaRedPrimary)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "மாநில நிர்வாகிகள்",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Black,
              color = TnpaJetBlack
            )
            Text(
              text = "State Office Bearers & Leadership",
              fontSize = 10.sp,
              color = TnpaCharcoal,
              fontWeight = FontWeight.Medium
            )
          }
        }

        TextButton(
          onClick = { onNavigateToTab(7) }, // Leadership Tab (Index 7)
          modifier = Modifier.testTag("btn_view_all_leaders")
        ) {
          Text("அனைவரும் >", fontSize = 12.sp, color = TnpaRedPrimary, fontWeight = FontWeight.Bold)
        }
      }

      // 3 முக்கிய மாநில நிர்வாகிகள் Cards
      stateLeaders.forEach { leader ->
        StateLeaderProfileCard(leader = leader)
      }

      // ========================================================================
      // 4.1 EXECUTIVE VIDEO CONFERENCE CARD (மாநில • மண்டல • மாவட்ட • நகர • ஒன்றிய கான்பிரன்ஸ்)
      // ========================================================================
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, TnpaGold)
      ) {
        Column(
          modifier = Modifier.padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(34.dp)
                  .clip(CircleShape)
                  .background(TnpaRedPrimary),
                contentAlignment = Alignment.Center
              ) {
                Icon(Icons.Default.VideoCall, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(20.dp))
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "நிர்வாகிகள் வீடியோ கான்பிரன்ஸ்",
                  fontWeight = FontWeight.Black,
                  fontSize = 13.sp,
                  color = TnpaPureWhite
                )
                Text(
                  text = "மாநில • மண்டல • மாவட்ட • நகர • ஒன்றிய மாநாடு",
                  fontSize = 10.sp,
                  color = TnpaGold,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(TnpaRedPrimary)
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text("🔴 LIVE ROOM", color = TnpaPureWhite, fontSize = 8.sp, fontWeight = FontWeight.Black)
            }
          }

          Text(
            text = "38 மாவட்ட, மண்டல, நகர மற்றும் ஒன்றிய பதிவு செய்யப்பட்ட நிர்வாகிகள் அனைவரும் ஒரே நேரத்தில் வீடியோ ஆலோசனைக் கூட்டத்தில் பங்கேற்கலாம்.",
            color = Color(0xFFE2E8F0),
            fontSize = 11.sp,
            lineHeight = 16.sp
          )

          Button(
            onClick = { onNavigateToTab(3) }, // Video Conference Tab (Index 3)
            modifier = Modifier
              .fillMaxWidth()
              .height(42.dp)
              .testTag("btn_home_join_video_conference"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = TnpaRedPrimary,
              contentColor = TnpaPureWhite
            )
          ) {
            Icon(Icons.Default.Videocam, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("வீடியோ கான்பிரன்சில் இணைய (Join Meeting)", fontSize = 12.sp, fontWeight = FontWeight.Black)
          }
        }
      }

      // ========================================================================
      // 4.2 AI EXECUTIVE PERFORMANCE MONITORING & STRATEGY CARD (AI நிர்வாக வழிகாட்டி மையம்)
      // ========================================================================
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B4B)), // Deep Indigo
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF818CF8))
      ) {
        Column(
          modifier = Modifier.padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(34.dp)
                  .clip(CircleShape)
                  .background(Color(0xFF4F46E5)),
                contentAlignment = Alignment.Center
              ) {
                Icon(Icons.Default.Psychology, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(20.dp))
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                  Text(
                    text = "AI நிர்வாகிகள் வழிகாட்டி மையம்",
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp,
                    color = TnpaPureWhite
                  )
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(4.dp))
                      .background(Color(0xFF2563EB))
                      .padding(horizontal = 4.dp, vertical = 1.dp)
                  ) {
                    Text("AI 3.5", color = TnpaPureWhite, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                  }
                }
                Text(
                  text = "செயல்திறன் கண்காணிப்பு & கள ஆலோசனைகள்",
                  fontSize = 10.sp,
                  color = Color(0xFFA5B4FC),
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFF10B981))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text("🤖 AI ACTIVE", color = TnpaPureWhite, fontSize = 8.sp, fontWeight = FontWeight.Black)
            }
          }

          Text(
            text = "ஒவ்வொரு மாநிலம், மாவட்டம், ஒன்றியம், நகர நிர்வாகிகளின் உறுப்பினர் சேர்க்கை, நலவாரியப் பதிவு மற்றும் கூட்டங்களை AI மூலம் துல்லியமாக கண்காணித்து கள ஆலோசனைகளை பெறலாம்.",
            color = Color(0xFFE0E7FF),
            fontSize = 11.sp,
            lineHeight = 16.sp
          )

          Button(
            onClick = { onNavigateToTab(4) }, // AI Monitoring Tab (Index 4)
            modifier = Modifier
              .fillMaxWidth()
              .height(42.dp)
              .testTag("btn_home_ai_monitoring"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = Color(0xFF4F46E5),
              contentColor = TnpaPureWhite
            )
          ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("AI வழிகாட்டி & அறிக்கைகளை பார்க்க (Open AI Advisor)", fontSize = 12.sp, fontWeight = FontWeight.Black)
          }
        }
      }
    }

    // ========================================================================
    // 5. DASHBOARD MEMBER & DISTRICT SEARCH BAR (உறுப்பினர்கள் தேடல் & பட்டியல்)
    // ========================================================================
    DashboardMemberSearchBarAndDirectory(
      onNavigateToMemberRegistration = { onNavigateToTab(1) },
      onNavigateToLeadership = { onNavigateToTab(7) }
    )

    // ========================================================================
    // 5.1 MEMBER SERVICES QUICK TILES (உறுப்பினர் சேவைகள்)
    // ========================================================================
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(6.dp, 20.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(TnpaGold)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "உறுப்பினர் சேவைகள் (Member Services)",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Black,
          color = TnpaJetBlack
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        HomeFeatureTile(
          title = "புதிய உறுப்பினர் பதிவு",
          subtitle = "சங்கத்தில் இணைய விண்ணப்பிக்க",
          icon = Icons.Default.Badge,
          badgeText = "உடனடி பதிவு",
          bgGradient = listOf(TnpaRedPrimary, TnpaRedDark),
          modifier = Modifier.weight(1f),
          onClick = { onNavigateToTab(1) } // Member Registration
        )

        HomeFeatureTile(
          title = "டிஜிட்டல் QR ID Card",
          subtitle = "அடையாள அட்டை பெற & பதிவிறக்க",
          icon = Icons.Default.WorkspacePremium,
          badgeText = "Smart ID",
          bgGradient = listOf(TnpaJetBlack, Color(0xFF262626)),
          modifier = Modifier.weight(1f),
          onClick = { onNavigateToTab(1) }
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        HomeFeatureTile(
          title = "விண்ணப்ப நிலை",
          subtitle = "ஒப்புதல் நிலை சரிபார்க்க",
          icon = Icons.Default.CheckCircle,
          badgeText = "Status Check",
          bgGradient = listOf(Color(0xFF0F766E), Color(0xFF115E59)),
          modifier = Modifier.weight(1f),
          onClick = { onNavigateToTab(1) }
        )

        HomeFeatureTile(
          title = "உறுப்பினர் விவரங்கள்",
          subtitle = "தொழில் விவர புதுப்பிப்பு",
          icon = Icons.Default.Person,
          badgeText = "Directory",
          bgGradient = listOf(Color(0xFF1E3A8A), Color(0xFF172554)),
          modifier = Modifier.weight(1f),
          onClick = { onNavigateToTab(1) }
        )
      }
    }

    // ========================================================================
    // 6. WELFARE SCHEMES SECTION (தொழிலாளர் நலவாரிய சேவைகள்)
    // ========================================================================
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedSoft)
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
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(TnpaGold.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.VolunteerActivism, contentDescription = null, tint = Color(0xFFB45309), modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "தொழிலாளர் நலவாரிய உதவிகள்",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = TnpaRedDark
              )
              Text(
                text = "மத்திய & தமிழக அரசு நலத்திட்டங்கள்",
                fontSize = 11.sp,
                color = TnpaCharcoal
              )
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(TnpaGreen.copy(alpha = 0.15f))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text("₹5,00,000 வரை", color = TnpaGreen, fontSize = 10.sp, fontWeight = FontWeight.Black)
          }
        }

        HorizontalDivider(color = TnpaRedSoft)

        // Scheme Highlights
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          SchemeMiniPill("🛡️ விபத்து மரண நிதி: ₹5 லட்சம்", Modifier.weight(1f))
          SchemeMiniPill("🎓 கல்வி உதவி: ₹1,000 - ₹8,000", Modifier.weight(1f))
        }
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          SchemeMiniPill("💍 திருமண நிதி: ₹20,000", Modifier.weight(1f))
          SchemeMiniPill("👵 முதியோர் ஓய்வூதியம்: ₹1,500/மாதம்", Modifier.weight(1f))
        }

        Button(
          onClick = { onNavigateToTab(6) }, // Welfare Tab (Index 6)
          modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .testTag("btn_home_welfare"),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
        ) {
          Icon(Icons.Default.AccountBalance, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("நலத்திட்ட விண்ணப்ப வழிகாட்டி பார்க்க", fontWeight = FontWeight.Bold)
        }
      }
    }

    // ========================================================================
    // 7. TNPA TV & LIVE STREAMING SECTION (டிவி & நேரலை ஒளிபரப்பு)
    // ========================================================================
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaJetBlack)
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
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LiveTv, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "TNPA² TV & நேரலை",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Black,
              color = TnpaPureWhite
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(TnpaRedPrimary)
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.FiberManualRecord, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(10.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("LIVE STREAM", color = TnpaPureWhite, fontSize = 9.sp, fontWeight = FontWeight.Black)
            }
          }
        }

        Text(
          text = "பெயிண்டிங் நுட்பங்கள், 3D டெக்ஸ்சர் பயிற்சி வகுப்புகள், மாநில சங்க மாநாட்டு நேரலை மற்றும் தொழிலாளர் விழிப்புணர்வு நிகழ்ச்சிகள்.",
          color = Color(0xFFD1D5DB),
          fontSize = 11.sp,
          lineHeight = 16.sp
        )

        Button(
          onClick = { onNavigateToTab(2) }, // TV Tab (Index 2)
          modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .testTag("btn_home_tv"),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = TnpaRedPrimary,
            contentColor = TnpaPureWhite
          )
        ) {
          Icon(Icons.Default.PlayArrow, contentDescription = null)
          Spacer(modifier = Modifier.width(6.dp))
          Text("நேரலை TV ஒளிபரப்பை பார்க்க (Watch Live)", fontWeight = FontWeight.Bold)
        }
      }
    }

    // ========================================================================
    // 8. EMPLOYMENT & JOBS SECTION (வேலைவாய்ப்புகள்)
    // ========================================================================
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF1E3A8A))
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(Color(0xFF1E3A8A)),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.Work, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "வேலைவாய்ப்பு மையம்",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1E3A8A)
              )
              Text(
                text = "அரசு & தனியார் பெயிண்டிங் வேலைகள்",
                fontSize = 11.sp,
                color = TnpaCharcoal
              )
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(TnpaGold)
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text("JOB PORTAL", color = TnpaJetBlack, fontSize = 9.sp, fontWeight = FontWeight.Black)
          }
        }

        Text(
          text = "ஒப்பந்ததாரர்கள் ஆட்கள் தேடவும், ஓவிய மற்றும் பெயிண்டிங் கலைஞர்கள் நேரடி வேலைவாய்ப்பு பெறவும் உருவாக்கப்பட்ட அதிகாரப்பூர்வ தளம்.",
          fontSize = 11.sp,
          color = TnpaCharcoal,
          lineHeight = 16.sp
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = { onNavigateToTab(7) }, // Leadership / Directory Tab (Index 7)
            modifier = Modifier.weight(1f).testTag("btn_home_jobs"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A))
          ) {
            Text("வேலைகள் பார்க்க", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }

          OutlinedButton(
            onClick = { onNavigateToTab(7) },
            modifier = Modifier.weight(1f).testTag("btn_home_post_job"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TnpaRedDark)
          ) {
            Text("ஆட்கள் தேவை பதிவு", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // ========================================================================
    // 9. QUICK CONTACT & ADMIN CONTROL (நிர்வாக கட்டுப்பாடு & தலைமையகம்)
    // ========================================================================
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "சங்க தலைமை அலுவலகம் & உதவி மையம்",
              fontWeight = FontWeight.Black,
              fontSize = 13.sp,
              color = TnpaPureWhite
            )
            Text(
              text = "மதுரை தலைமையகம் & 38 மாவட்ட கிளைகள்",
              fontSize = 10.sp,
              color = TnpaGold
            )
          }

          OutlinedButton(
            onClick = { onNavigateToTab(8) }, // Admin Management Tab (Index 8)
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TnpaGold),
            border = androidx.compose.foundation.BorderStroke(1.dp, TnpaGold),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.height(34.dp).testTag("btn_home_admin_gate")
          ) {
            Icon(Icons.Default.AdminPanelSettings, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Admin Login", fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
        }

        HorizontalDivider(color = Color(0xFF334155))

        Text(
          text = "📍 மாநில தலைமை அலுவலகம்: அம்பலக்காரன் பட்டி, உத்தங்குடி போஸ்ட், மேலூர் மெயின் ரோடு, மதுரை - 625107\n📞 மாநிலத் தலைவர் (எஸ். மைக்கேல் ஆல்வின் - மதுரை மாவட்டம்): +91 97893 31681\n📞 பொதுச் செயலாளர் / Super Admin (சேவியர் பாபு - மதுரை மாவட்டம்): +91 70101 31915\n✉️ மின்னஞ்சல்: xavierbabu2@gmail.com",
          color = Color(0xFF94A3B8),
          fontSize = 11.sp,
          lineHeight = 16.sp
        )
      }
    }
  }

  // App Download Modal
  if (showDownloadModal) {
    AppDownloadModal(onDismiss = { showDownloadModal = false })
  }
}

// ============================================================================
// HELPER COMPOSABLE CARDS FOR HOMEPAGE
// ============================================================================

@Composable
fun StateLeaderProfileCard(
  leader: StateLeaderItem,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val badgeColor = Color(leader.badgeThemeColorHex)
  val safeName = leader.fullNameTamil.ifBlank { leader.designationTamil }
  val safeMobile = leader.mobileNumber.ifBlank { "+91 94431 23456" }
  val safeLocation = leader.location.ifBlank { "தமிழ்நாடு தலைமையகம்" }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("card_state_leader_${leader.id}"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = androidx.compose.foundation.BorderStroke(
      width = if (leader.isTopLeader) 1.5.dp else 1.dp,
      color = if (leader.isTopLeader) TnpaGold else TnpaRedSoft
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        // High-Definition Leader Profile Photo with Laurel Ring & Zoom Modal on Tap
        com.example.ui.components.LeaderProfilePhotoView(
          photoUrl = leader.photoUrl,
          fullName = safeName,
          tamilName = safeName,
          designation = "${leader.designationTamil} (${leader.designationEnglish})",
          level = com.example.model.AdminHierarchyLevel.STATE,
          district = safeLocation,
          mobile = safeMobile,
          size = 54.dp,
          isTopLeader = leader.isTopLeader,
          enableEnlargeOnClick = true
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Leader Details Column
        Column(
          verticalArrangement = Arrangement.spacedBy(3.dp),
          modifier = Modifier.weight(1f)
        ) {
          // Designation Badge
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(badgeColor.copy(alpha = 0.12f))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "${leader.designationTamil} (${leader.designationEnglish})",
              color = badgeColor,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          }

          // Full Name
          Text(
            text = safeName,
            fontWeight = FontWeight.Black,
            fontSize = 13.sp,
            color = TnpaJetBlack,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )

          // Location
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.LocationOn,
              contentDescription = null,
              tint = TnpaRedPrimary,
              modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
              text = safeLocation,
              fontSize = 10.sp,
              color = TnpaCharcoal,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          }

          // Mobile Number Clickable Preview
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .clickable {
                val callIntent = Intent(
                  Intent.ACTION_DIAL,
                  Uri.parse("tel:${safeMobile.replace(" ", "").replace("-", "")}")
                )
                context.startActivity(callIntent)
              }
          ) {
            Icon(
              imageVector = Icons.Default.Phone,
              contentDescription = null,
              tint = TnpaGreen,
              modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = safeMobile,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = TnpaGreen,
              maxLines = 1
            )
          }
        }
      }

      Spacer(modifier = Modifier.width(8.dp))

      // Direct Dial Call Button (📞 அழைக்க)
      Button(
        onClick = {
          val callIntent = Intent(
            Intent.ACTION_DIAL,
            Uri.parse("tel:${safeMobile.replace(" ", "").replace("-", "")}")
          )
          context.startActivity(callIntent)
        },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp),
        modifier = Modifier
          .height(36.dp)
          .testTag("btn_call_leader_${leader.id}")
      ) {
        Icon(Icons.Default.Call, contentDescription = "Call", modifier = Modifier.size(13.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("அழைக்க", fontSize = 11.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}

@Composable
fun LeaderCard(
  designation: String,
  name: String,
  location: String,
  mobile: String,
  badgeColor: Color,
  isTopLeader: Boolean
) {
  StateLeaderProfileCard(
    leader = StateLeaderItem(
      id = name.hashCode().toString(),
      designationTamil = designation,
      designationEnglish = "",
      fullNameTamil = name,
      mobileNumber = mobile,
      location = location,
      badgeThemeColorHex = badgeColor.value.toLong(),
      isTopLeader = isTopLeader
    )
  )
}

@Composable
fun HomeFeatureTile(
  title: String,
  subtitle: String,
  icon: ImageVector,
  badgeText: String,
  bgGradient: List<Color>,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Card(
    modifier = modifier
      .clickable { onClick() },
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedSoft)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(Brush.linearGradient(bgGradient)),
          contentAlignment = Alignment.Center
        ) {
          Icon(icon, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(18.dp))
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(TnpaGold.copy(alpha = 0.2f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(badgeText, color = TnpaJetBlack, fontSize = 9.sp, fontWeight = FontWeight.Bold)
        }
      }

      Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = TnpaJetBlack,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )

      Text(
        text = subtitle,
        fontSize = 10.sp,
        color = TnpaCharcoal,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

@Composable
fun SchemeMiniPill(text: String, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .background(TnpaOffWhite)
      .border(1.dp, TnpaRedSoft, RoundedCornerShape(8.dp))
      .padding(horizontal = 8.dp, vertical = 6.dp)
  ) {
    Text(
      text = text,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      color = TnpaJetBlack,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis
    )
  }
}
