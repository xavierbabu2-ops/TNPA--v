package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GlobalGpsTrackerManager
import com.example.model.GpsTrackingStatus
import com.example.model.TrackedContactLocation
import com.example.ui.theme.*

@Composable
fun GlobalGpsRadarScreen() {
  val contacts by GlobalGpsTrackerManager.trackedContacts.collectAsState()
  val radarStatus by GlobalGpsTrackerManager.radarStatus.collectAsState()

  var inputNumber by remember { mutableStateOf("") }
  var inputName by remember { mutableStateOf("") }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(SovereignBackground)
      .padding(16.dp)
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Header
      Surface(
        color = SovereignSurface,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, SovereignBorderGlow),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.MyLocation, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                "குளோபல் ஜிபிஎஸ் லொகேஷன் ரேடார் (GPS Tracker)",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = SovereignTextPrimary
              )
              Text(
                "ஸேவியர்பாபுவின் தன்னாட்சி எண்ணியல் லொகேஷன் கண்காணிப்பு அமைப்பு",
                fontSize = 11.sp,
                color = SovereignTextSecondary
              )
            }
          }
          Spacer(modifier = Modifier.height(10.dp))
          Surface(
            color = SovereignBackground,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = "📡 ரேடார் நிலை: $radarStatus",
              fontSize = 12.sp,
              color = SovereignCyan,
              modifier = Modifier.padding(10.dp)
            )
          }
        }
      }

      // Input Card to Track Any Number
      Surface(
        color = SovereignSurfaceElevated,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            "புதிய தொலைபேசி எண்ணை ரேடாரில் சேர்க்கவும் (Track Number)",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = SovereignGold
          )
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            OutlinedTextField(
              value = inputNumber,
              onValueChange = { inputNumber = it },
              placeholder = { Text("தொலைபேசி எண் (எ.கா: +91 ...)", fontSize = 11.sp, color = SovereignTextMuted) },
              modifier = Modifier.weight(1f),
              singleLine = true,
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = SovereignTextPrimary,
                unfocusedTextColor = SovereignTextPrimary,
                focusedBorderColor = SovereignCyan,
                unfocusedBorderColor = SovereignBorder
              )
            )
            OutlinedTextField(
              value = inputName,
              onValueChange = { inputName = it },
              placeholder = { Text("பெயர் (விரும்பினால்)", fontSize = 11.sp, color = SovereignTextMuted) },
              modifier = Modifier.weight(1f),
              singleLine = true,
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = SovereignTextPrimary,
                unfocusedTextColor = SovereignTextPrimary,
                focusedBorderColor = SovereignCyan,
                unfocusedBorderColor = SovereignBorder
              )
            )
          }
          Button(
            onClick = {
              if (inputNumber.isNotBlank()) {
                GlobalGpsTrackerManager.trackPhoneNumber(inputNumber, inputName)
                inputNumber = ""
                inputName = ""
              }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = SovereignCyan)
          ) {
            Icon(Icons.Default.Radar, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("ஜிபிஎஸ் லொகேஷன் ரேடார் லாக் செய்", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
          }
        }
      }

      Text(
        "கண்காப்பில் உள்ள எண்கள் & நேரலை லொகேஷன் (Active Tracked Targets)",
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = SovereignTextPrimary
      )

      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(contacts) { contact ->
          GpsContactCard(contact = contact) {
            GlobalGpsTrackerManager.refreshLocation(contact.phoneNumber)
          }
        }
      }
    }
  }
}

@Composable
fun GpsContactCard(contact: TrackedContactLocation, onRefresh: () -> Unit) {
  Surface(
    color = SovereignSurface,
    shape = RoundedCornerShape(14.dp),
    border = BorderStroke(1.dp, SovereignBorderGlow),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
          Box(
            modifier = Modifier
              .size(40.dp)
              .clip(CircleShape)
              .background(SovereignCyan.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.PersonPinCircle,
              contentDescription = null,
              tint = SovereignCyan,
              modifier = Modifier.size(22.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = contact.name,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = SovereignTextPrimary
            )
            Text(
              text = contact.phoneNumber,
              fontSize = 12.sp,
              color = SovereignGold
            )
          }
        }

        IconButton(onClick = onRefresh) {
          Icon(Icons.Default.Refresh, contentDescription = "Refresh GPS", tint = SovereignCyan)
        }
      }

      Spacer(modifier = Modifier.height(10.dp))
      Divider(color = SovereignBorder, thickness = 0.5.dp)
      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          Text("முகவரி / பகுதி:", fontSize = 10.sp, color = SovereignTextSecondary)
          Text(contact.address, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
        }
        Column(horizontalAlignment = Alignment.End) {
          Text("துல்லியம் (Accuracy):", fontSize = 10.sp, color = SovereignTextSecondary)
          Text("±${contact.accuracyMeters} meters", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignEmerald)
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text("Lat/Lng: ${contact.latitude}, ${contact.longitude}", fontSize = 10.sp, color = SovereignTextMuted)
        Text("புதுப்பிக்கப்பட்டது: ${contact.lastUpdated}", fontSize = 10.sp, color = SovereignCyan)
      }
    }
  }
}
