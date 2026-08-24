package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.example.data.IoTHomeVehicleRegistry
import com.example.model.DeviceType
import com.example.model.SmartDevice
import com.example.ui.theme.*

@Composable
fun UniversalIoTControlHubScreen() {
  val devices by IoTHomeVehicleRegistry.devices.collectAsState()
  var voiceCommandInput by remember { mutableStateOf("") }
  var executionLog by remember { mutableStateOf("குரல் கட்டளைகள் மற்றும் ஆட்டோமேஷன் தயாராக உள்ளது (Voice, TV, Bike, Car, Sensors)") }

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
            Icon(Icons.Default.Router, contentDescription = null, tint = SovereignGold, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                "யுனிவர்சல் ஹோம், வெஹிக்கிள் & IoT கண்ட்ரோல் சிஸ்டம்",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = SovereignTextPrimary
              )
              Text(
                "ஸேவியர்பாபுவின் தனிநபர் ஒட்டுமொத்த எக்கோசிஸ்டம்",
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
              text = "⚡ இயக்கம்: $executionLog",
              fontSize = 12.sp,
              color = SovereignCyan,
              modifier = Modifier.padding(10.dp)
            )
          }
        }
      }

      // Voice Simulation Bar for Testing Universal Commands
      Surface(
        color = SovereignSurfaceElevated,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          OutlinedTextField(
            value = voiceCommandInput,
            onValueChange = { voiceCommandInput = it },
            placeholder = { Text("எ.கா: 'TV on பண்ணு', 'பைக்கில் பெட்ரோல் எவ்வளவு?'", fontSize = 12.sp, color = SovereignTextMuted) },
            modifier = Modifier.weight(1f),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = SovereignTextPrimary,
              unfocusedTextColor = SovereignTextPrimary,
              focusedBorderColor = SovereignGold,
              unfocusedBorderColor = SovereignBorder
            )
          )
          Spacer(modifier = Modifier.width(8.dp))
          Button(
            onClick = {
              val cmd = voiceCommandInput.trim().lowercase()
              if (cmd.contains("tv") && cmd.contains("on")) {
                IoTHomeVehicleRegistry.updateDevicePower("tv_hall", true)
                executionLog = "AI: சோனி பிராவியா டிவி ஆன் செய்யப்பட்டது."
              } else if (cmd.contains("tv") && cmd.contains("off")) {
                IoTHomeVehicleRegistry.updateDevicePower("tv_hall", false)
                executionLog = "AI: சோனி பிராவியா டிவி ஆஃப் செய்யப்பட்டது."
              } else if (cmd.contains("petrol") || cmd.contains("fuel") || cmd.contains("பைக்")) {
                executionLog = "AI: உங்கள் பைக்கில் (Yamaha R15) பெட்ரோல் அளவு சுமார் 78% உள்ளது."
              } else if (cmd.contains("light") || cmd.contains("hall")) {
                IoTHomeVehicleRegistry.updateDevicePower("light_hall", true)
                executionLog = "AI: ஹால் லைட் ஆன் செய்யப்பட்டது."
              } else {
                executionLog = "AI: கட்டளை புரிந்து கொள்ளப்பட்டது. எக்கோசிஸ்டம் செயல்படுத்தப்பட்டது: '$voiceCommandInput'"
              }
              voiceCommandInput = ""
            },
            colors = ButtonDefaults.buttonColors(containerColor = SovereignGold)
          ) {
            Icon(Icons.Default.Mic, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("கட்டளை", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
          }
        }
      }

      // Devices Grid / List
      Text(
        "இணைக்கப்பட்ட சாதனங்கள் (Connected Devices & Telemetry)",
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
        items(devices) { device ->
          DeviceCard(device = device) {
            val nextState = !device.isPoweredOn
            IoTHomeVehicleRegistry.updateDevicePower(device.id, nextState)
            executionLog = "${device.name} நிலை மாற்றப்பட்டது: ${if (nextState) "ON" else "OFF"}"
          }
        }
      }
    }
  }
}

@Composable
fun DeviceCard(device: SmartDevice, onTogglePower: () -> Unit) {
  Surface(
    color = SovereignSurface,
    shape = RoundedCornerShape(14.dp),
    border = BorderStroke(1.dp, if (device.isPoweredOn) SovereignGold.copy(alpha = 0.5f) else SovereignBorder),
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
            .background(if (device.isPoweredOn) SovereignGold.copy(alpha = 0.2f) else SovereignSurfaceElevated),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = when (device.type) {
              DeviceType.SMART_TV -> Icons.Default.Tv
              DeviceType.MOTORCYCLE -> Icons.Default.TwoWheeler
              DeviceType.CAR -> Icons.Default.DirectionsCar
              DeviceType.LIGHT -> Icons.Default.Lightbulb
              DeviceType.AC -> Icons.Default.AcUnit
              DeviceType.DOOR_SENSOR -> Icons.Default.DoorFront
              DeviceType.TEMPERATURE_SENSOR -> Icons.Default.Thermostat
              else -> Icons.Default.Devices
            },
            contentDescription = null,
            tint = if (device.isPoweredOn) SovereignGold else SovereignTextSecondary,
            modifier = Modifier.size(22.dp)
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
          Text(
            text = device.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = SovereignTextPrimary
          )
          Text(
            text = "${device.location} • ${device.statusText}",
            fontSize = 11.sp,
            color = SovereignTextSecondary
          )
        }
      }

      Switch(
        checked = device.isPoweredOn,
        onCheckedChange = { onTogglePower() },
        colors = SwitchDefaults.colors(
          checkedThumbColor = Color.Black,
          checkedTrackColor = SovereignGold
        )
      )
    }
  }
}
