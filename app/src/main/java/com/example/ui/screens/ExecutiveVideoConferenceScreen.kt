package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ExecutiveConferenceRepository
import com.example.model.ConferenceRoom
import com.example.ui.components.SecureVideoConferenceComponent
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedPrimary

/**
 * Screen hosting the TNPA Executive Video Conferencing System.
 * Integrates SecureVideoConferenceComponent with Room Selection and Firebase Realtime Database management.
 */
@Composable
fun ExecutiveVideoConferenceScreen(
  onNavigateToLeadershipDirectory: () -> Unit = {}
) {
  val rooms by ExecutiveConferenceRepository.rooms.collectAsState()
  var selectedRoomIndex by remember { mutableIntStateOf(0) }
  val activeRoom = rooms.getOrNull(selectedRoomIndex) ?: rooms.firstOrNull()

  var showCreateRoomDialog by remember { mutableStateOf(false) }
  var newRoomTitle by remember { mutableStateOf("") }
  var newRoomAgenda by remember { mutableStateOf("") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFF0B0F19))
      .testTag("executive_video_conference_screen")
  ) {
    // 1. Top Conference Room Navigation Bar
    ElevatedCard(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF111827))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              listOf(Color(0xFF111827), Color(0xFF1F2937))
            )
          )
          .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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
                .background(TnpaRedPrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                Icons.Default.VideoCall,
                contentDescription = null,
                tint = TnpaGold,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "டிஎன்பிஏ மாநில மாநாட்டு மையம்",
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = TnpaPureWhite
              )
              Text(
                text = "மாநில • மண்டல • மாவட்ட வீடியோ அரங்கம் (Firebase RTDB)",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = TnpaGold
              )
            }
          }

          Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(
              onClick = onNavigateToLeadershipDirectory,
              modifier = Modifier
                .size(32.dp)
                .background(Color(0xFF374151), CircleShape)
            ) {
              Icon(Icons.Default.ContactPhone, contentDescription = "Directory", tint = TnpaGold, modifier = Modifier.size(16.dp))
            }

            IconButton(
              onClick = { showCreateRoomDialog = true },
              modifier = Modifier
                .size(32.dp)
                .background(TnpaRedPrimary, CircleShape)
            ) {
              Icon(Icons.Default.Add, contentDescription = "Create Room", tint = TnpaPureWhite, modifier = Modifier.size(16.dp))
            }
          }
        }

        // Room Selector Tabs
        ScrollableTabRow(
          selectedTabIndex = selectedRoomIndex,
          containerColor = Color.Transparent,
          contentColor = TnpaGold,
          edgePadding = 4.dp,
          divider = {}
        ) {
          rooms.forEachIndexed { index, room ->
            val isSelected = selectedRoomIndex == index
            Tab(
              selected = isSelected,
              onClick = { selectedRoomIndex = index },
              text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  if (room.isLive) {
                    Box(
                      modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF22C55E))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                  }
                  Text(
                    text = room.titleTamil.take(20) + "...",
                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal,
                    fontSize = 11.sp,
                    color = if (isSelected) TnpaGold else Color(0xFF9CA3AF)
                  )
                }
              }
            )
          }
        }
      }
    }

    // 2. Embedded Secure Video Conferencing Component
    Box(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
    ) {
      SecureVideoConferenceComponent(
        initialRoom = activeRoom,
        onLeaveMeeting = {}
      )
    }
  }

  // 3. Create New Meeting Room Dialog
  if (showCreateRoomDialog) {
    AlertDialog(
      onDismissRequest = { showCreateRoomDialog = false },
      title = { Text("🏛️ புதிய வீடியோ மாநாட்டு அரங்கு உருவாக்கு", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          OutlinedTextField(
            value = newRoomTitle,
            onValueChange = { newRoomTitle = it },
            label = { Text("மாநாட்டின் தலைப்பு (Meeting Title in Tamil)") },
            placeholder = { Text("எ.கா. அவசர செயற்குழு கலந்தாய்வு") },
            modifier = Modifier.fillMaxWidth()
          )

          OutlinedTextField(
            value = newRoomAgenda,
            onValueChange = { newRoomAgenda = it },
            label = { Text("நிகழ்ச்சி நிரல் / கோரிக்கைகள்") },
            placeholder = { Text("1. நலவாரிய பதிவு\n2. உறுப்பினர் சேர்க்கை") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (newRoomTitle.isNotBlank()) {
              val newRoom = ConferenceRoom(
                titleTamil = newRoomTitle.trim(),
                titleEnglish = "TNPA Executive High-Level Conference",
                meetingCode = "TNPA-${(1000..9999).random()}",
                hostName = "சேவியர் பாபு (மாநில தலைமை)",
                hostRole = "Super Admin",
                isLive = true,
                agendaPoints = newRoomAgenda.split("\n").filter { it.isNotBlank() }.ifEmpty {
                  listOf("1. 38 மாவட்ட நிர்வாகிகள் ஒருங்கிணைப்பு", "2. நலவாரிய நலத்திட்டங்கள் விரைவுபடுத்தல்")
                }
              )
              ExecutiveConferenceRepository.createConferenceRoom(newRoom)
              showCreateRoomDialog = false
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = TnpaGold)
        ) {
          Text("உருவாக்கு (Create Room)", color = TnpaJetBlack, fontWeight = FontWeight.Black)
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { showCreateRoomDialog = false }) {
          Text("ரத்து")
        }
      }
    )
  }
}
