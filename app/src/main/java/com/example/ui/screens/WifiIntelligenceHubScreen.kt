package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.WifiConnectivityManager
import com.example.model.WifiNetworkItem
import com.example.ui.theme.*

@Composable
fun WifiIntelligenceHubScreen() {
  val networks by WifiConnectivityManager.networks.collectAsState()
  val status by WifiConnectivityManager.connectionStatus.collectAsState()

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
            Icon(Icons.Default.Wifi, contentDescription = null, tint = SovereignEmerald, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                "வைஃபை ஆட்டோ டிஸ்கவரி & ஸ்மார்ட் கனெக்டிவிட்டி",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = SovereignTextPrimary
              )
              Text(
                "ஸேவியர்பாபுவின் அங்கீகரிக்கப்பட்ட பாதுகாப்பான நெட்வொர்க் மேலாளர்",
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
              text = "🛡️ நிலை: $status",
              fontSize = 12.sp,
              color = SovereignCyan,
              modifier = Modifier.padding(10.dp)
            )
          }
        }
      }

      Text(
        "அருகிலுள்ள அங்கீகரிக்கப்பட்ட நெட்வொர்க்குகள் (Authorized Wi-Fi Discovery)",
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
        items(networks) { net ->
          WifiNetworkCard(net = net) {
            WifiConnectivityManager.connectToNetwork(net.ssid)
          }
        }
      }
    }
  }
}

@Composable
fun WifiNetworkCard(net: WifiNetworkItem, onConnect: () -> Unit) {
  Surface(
    color = SovereignSurface,
    shape = RoundedCornerShape(14.dp),
    border = BorderStroke(1.dp, if (net.isConnected) SovereignEmerald.copy(alpha = 0.6f) else SovereignBorder),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(if (net.isConnected) SovereignEmerald.copy(alpha = 0.2f) else SovereignSurfaceElevated),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = if (net.isAuthorized) Icons.Default.Lock else Icons.Default.WifiLock,
            contentDescription = null,
            tint = if (net.isConnected) SovereignEmerald else SovereignTextSecondary,
            modifier = Modifier.size(20.dp)
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
          Text(
            text = net.ssid,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = SovereignTextPrimary
          )
          Text(
            text = "${net.frequency} • ${net.signalStrength} • ${if (net.isAuthorized) "அங்கீகரிக்கப்பட்டது" else "அங்கீகரிக்கப்படவில்லை"}",
            fontSize = 11.sp,
            color = SovereignTextSecondary
          )
        }
      }

      Button(
        onClick = onConnect,
        colors = ButtonDefaults.buttonColors(
          containerColor = if (net.isConnected) SovereignEmerald else SovereignSurfaceElevated
        ),
        border = if (!net.isConnected) BorderStroke(1.dp, SovereignBorder) else null
      ) {
        Text(
          text = if (net.isConnected) "இணைக்கப்பட்டுள்ளது" else "இணை",
          fontSize = 11.sp,
          color = if (net.isConnected) Color.Black else SovereignTextPrimary,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}
