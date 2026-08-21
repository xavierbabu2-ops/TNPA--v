package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FirestoreDistrictInitializer
import com.example.data.OfficeBearerRepository
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.example.model.AdminHierarchyLevel
import com.example.model.AdminRole
import com.example.model.AppointmentAuditLog
import com.example.model.HierarchyOfficeBearer
import com.example.model.TamilNaduMasterData
import com.example.ui.components.TnpaOfficialEmblem
import com.example.ui.theme.TnpaCharcoal
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedPrimary
import com.example.ui.theme.TnpaRedSoft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeadershipContactScreen() {
  val context = LocalContext.current
  val bearers by OfficeBearerRepository.bearers.collectAsState()
  val auditLogs by OfficeBearerRepository.auditLogs.collectAsState()

  // Hierarchy Level Tab Selection (8 Levels)
  var selectedLevel by remember { mutableStateOf(AdminHierarchyLevel.STATE) }

  // Search & Filter State
  var searchQuery by remember { mutableStateOf("") }
  var selectedDistrictFilter by remember { mutableStateOf("அனைத்து மாவட்டங்கள் (All)") }
  var showActiveOnly by remember { mutableStateOf(true) }

  // Admin Permission Role Simulator (Super Admin by default, can simulate State/District Admin)
  var currentAdminRole by remember { mutableStateOf(AdminRole.SUPER_ADMIN) }
  var currentAdminDistrict by remember { mutableStateOf("மதுரை (Madurai)") }
  var currentAdminName by remember { mutableStateOf("சேவியர் பாபு (மாநில பொதுச் செயலாளர்)") }

  // Dialog States
  var showAddDialog by remember { mutableStateOf(false) }
  var showTransferDialog by remember { mutableStateOf(false) }
  var bearerToTransfer by remember { mutableStateOf<HierarchyOfficeBearer?>(null) }
  var showAuditLogsDialog by remember { mutableStateOf(false) }
  var showDistrictHierarchyDialog by remember { mutableStateOf(false) }
  var isSyncingFirestore by remember { mutableStateOf(false) }
  var syncProgressText by remember { mutableStateOf("") }
  val coroutineScope = rememberCoroutineScope()

  // Filtered Bearers computation
  val filteredBearers = remember(bearers, selectedLevel, searchQuery, selectedDistrictFilter, showActiveOnly) {
    bearers.filter { bearer ->
      val matchesLevel = bearer.level == selectedLevel
      val matchesStatus = if (showActiveOnly) bearer.isActive else true
      val matchesDistrict = if (selectedDistrictFilter == "அனைத்து மாவட்டங்கள் (All)") {
        true
      } else {
        bearer.district.contains(selectedDistrictFilter.split(" ").first(), ignoreCase = true)
      }
      val matchesSearch = if (searchQuery.isBlank()) {
        true
      } else {
        bearer.tamilName.contains(searchQuery, ignoreCase = true) ||
          bearer.fullName.contains(searchQuery, ignoreCase = true) ||
          bearer.designation.contains(searchQuery, ignoreCase = true) ||
          bearer.mobile.contains(searchQuery) ||
          bearer.unionName.contains(searchQuery, ignoreCase = true) ||
          bearer.cityName.contains(searchQuery, ignoreCase = true)
      }

      matchesLevel && matchesStatus && matchesDistrict && matchesSearch
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
  ) {
    // ========================================================================
    // 1. TOP BRAND HEADER & CONTROL BAR
    // ========================================================================
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 8.dp),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      border = androidx.compose.foundation.BorderStroke(1.5.dp, TnpaRedSoft)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(Brush.horizontalGradient(listOf(TnpaJetBlack, Color(0xFF2A0808), TnpaRedPrimary)))
          .padding(14.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            TnpaOfficialEmblem(sizeDp = 42.dp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "நிர்வாகிகள் (Office Bearers)",
                color = TnpaPureWhite,
                fontSize = 17.sp,
                fontWeight = FontWeight.Black
              )
              Text(
                text = "மாநிலம் முதல் ஒன்றியம் & இளைஞரணி வரை",
                color = TnpaGold,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          // Action Pills (Audit Log, Cloud Init & Add Appointment)
          Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
            // Firestore 38 Districts Initialization Button
            IconButton(
              onClick = {
                if (!isSyncingFirestore) {
                  isSyncingFirestore = true
                  syncProgressText = "38 மாவட்டங்கள் Firestore-ல் தொடங்குதல்..."
                  coroutineScope.launch {
                    val result = FirestoreDistrictInitializer.initializeAllDistrictsToFirestore { current, total, name ->
                      syncProgressText = "($current/$total) $name சேர்க்கப்படுகிறது..."
                    }
                    isSyncingFirestore = false
                    result.onSuccess { count ->
                      Toast.makeText(context, "✅ $count மாவட்டங்கள், ஒன்றியங்கள், நகரங்கள் & இளைஞரணி Firestore-ல் வெற்றிகரமாக இணைக்கப்பட்டது!", Toast.LENGTH_LONG).show()
                    }.onFailure { err ->
                      Toast.makeText(context, "❌ Firestore Sync பிழை: ${err.message}", Toast.LENGTH_LONG).show()
                    }
                  }
                }
              },
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(TnpaGold.copy(alpha = 0.2f))
                .border(1.dp, TnpaGold, CircleShape)
            ) {
              if (isSyncingFirestore) {
                CircularProgressIndicator(modifier = Modifier.size(18.dp), color = TnpaGold, strokeWidth = 2.dp)
              } else {
                Icon(Icons.Default.CloudSync, contentDescription = "Sync 38 Districts to Firestore", tint = TnpaGold, modifier = Modifier.size(18.dp))
              }
            }

            IconButton(
              onClick = { showAuditLogsDialog = true },
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(TnpaGold.copy(alpha = 0.2f))
                .border(1.dp, TnpaGold, CircleShape)
            ) {
              Icon(Icons.Default.History, contentDescription = "Audit History", tint = TnpaGold, modifier = Modifier.size(18.dp))
            }

            Button(
              onClick = { showAddDialog = true },
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(containerColor = TnpaGold),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
              modifier = Modifier.height(36.dp)
            ) {
              Icon(Icons.Default.Add, contentDescription = null, tint = TnpaJetBlack, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("நியமனம்", color = TnpaJetBlack, fontSize = 11.sp, fontWeight = FontWeight.Black)
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Role-Based Authorization Switcher & Safety Badge
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(TnpaJetBlack.copy(alpha = 0.6f))
            .border(1.dp, Color(0xFF475569), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Icon(Icons.Default.Security, contentDescription = null, tint = TnpaGreen, modifier = Modifier.size(15.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "அதிகாரம்: ${currentAdminRole.labelEnglish}",
              color = TnpaPureWhite,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(if (currentAdminRole == AdminRole.SUPER_ADMIN) TnpaRedPrimary else Color(0xFF334155))
                .clickable { currentAdminRole = AdminRole.SUPER_ADMIN }
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text("Super Admin", color = TnpaPureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(if (currentAdminRole == AdminRole.DISTRICT_ADMIN) TnpaGold else Color(0xFF334155))
                .clickable { currentAdminRole = AdminRole.DISTRICT_ADMIN }
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text("District Admin", color = if (currentAdminRole == AdminRole.DISTRICT_ADMIN) TnpaJetBlack else TnpaPureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // ========================================================================
    // 2. 8 ADMINISTRATIVE HIERARCHY LEVEL TABS (SCROLLABLE)
    // ========================================================================
    ScrollableTabRow(
      selectedTabIndex = selectedLevel.orderIndex - 1,
      edgePadding = 10.dp,
      containerColor = TnpaPureWhite,
      contentColor = TnpaRedPrimary,
      modifier = Modifier.fillMaxWidth()
    ) {
      AdminHierarchyLevel.values().forEach { level ->
        Tab(
          selected = selectedLevel == level,
          onClick = { selectedLevel = level },
          text = {
            Text(
              text = "${level.iconEmoji} ${level.labelTamil}",
              fontWeight = if (selectedLevel == level) FontWeight.Black else FontWeight.Normal,
              fontSize = 12.sp
            )
          },
          modifier = Modifier.testTag("tab_level_${level.id}")
        )
      }
    }

    // ========================================================================
    // 3. SEARCH & DISTRICT FILTER BAR
    // ========================================================================
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 6.dp),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Search Input
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("பெயர், பதவி, மொபைல் எண், ஒன்றியம்...", fontSize = 12.sp) },
          leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(18.dp)) },
          trailingIcon = {
            if (searchQuery.isNotBlank()) {
              IconButton(onClick = { searchQuery = "" }) {
                Icon(Icons.Default.Close, contentDescription = "Clear", modifier = Modifier.size(16.dp))
              }
            }
          },
          singleLine = true,
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(8.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TnpaRedPrimary,
            unfocusedBorderColor = Color(0xFFCBD5E1)
          )
        )

        // District Dropdown & Status Filter Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // 38 Districts Selector Dropdown
          var districtExpanded by remember { mutableStateOf(false) }
          ExposedDropdownMenuBox(
            expanded = districtExpanded,
            onExpandedChange = { districtExpanded = it },
            modifier = Modifier.weight(1.5f)
          ) {
            OutlinedTextField(
              value = selectedDistrictFilter,
              onValueChange = {},
              readOnly = true,
              trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = districtExpanded) },
              modifier = Modifier.menuAnchor().fillMaxWidth(),
              shape = RoundedCornerShape(8.dp),
              textStyle = androidx.compose.ui.text.TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TnpaRedPrimary,
                unfocusedBorderColor = Color(0xFFCBD5E1)
              )
            )

            ExposedDropdownMenu(
              expanded = districtExpanded,
              onDismissRequest = { districtExpanded = false },
              modifier = Modifier.height(280.dp)
            ) {
              DropdownMenuItem(
                text = { Text("அனைத்து மாவட்டங்கள் (All)", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                onClick = {
                  selectedDistrictFilter = "அனைத்து மாவட்டங்கள் (All)"
                  districtExpanded = false
                }
              )
              TamilNaduMasterData.DISTRICTS_38.forEach { dist ->
                DropdownMenuItem(
                  text = { Text(dist, fontSize = 12.sp) },
                  onClick = {
                    selectedDistrictFilter = dist
                    districtExpanded = false
                  }
                )
              }
            }
          }

          // Active / All Toggle Chip
          FilterChip(
            selected = showActiveOnly,
            onClick = { showActiveOnly = !showActiveOnly },
            label = { Text(if (showActiveOnly) "செயலில் உள்ளோர்" else "அனைத்தும்", fontSize = 10.5.sp) },
            leadingIcon = {
              Icon(
                if (showActiveOnly) Icons.Default.CheckCircle else Icons.Default.FilterList,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = TnpaGreen.copy(alpha = 0.15f),
              selectedLabelColor = Color(0xFF15803D),
              selectedLeadingIconColor = Color(0xFF15803D)
            )
          )
        }
      }
    }

    // ========================================================================
    // 4. LIST OF OFFICE BEARERS (CARDS)
    // ========================================================================
    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(horizontal = 12.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "${selectedLevel.iconEmoji} ${selectedLevel.labelTamil} (${filteredBearers.size})",
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = TnpaJetBlack
          )
          Text(
            text = "38 மாவட்ட தலைமை கட்டமைப்பு",
            fontSize = 10.sp,
            color = Color(0xFF64748B),
            fontWeight = FontWeight.Bold
          )
        }
      }

      if (filteredBearers.isEmpty()) {
        item {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 20.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Icon(Icons.Default.Info, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(36.dp))
              Text(
                text = "பொறுப்பாளர்கள் விவரம் இல்லை",
                fontWeight = FontWeight.Black,
                fontSize = 15.sp,
                color = TnpaJetBlack
              )
              Text(
                text = "தேர்ந்தெடுக்கப்பட்ட பிரிவில் / மாவட்டத்தில் இன்னும் பொறுப்பாளர்கள் நியமிக்கப்படவில்லை. Super Admin / State Admin மூலம் 'நியமனம்' பொத்தானை அழுத்தி புதிய பொறுப்பாளரை நியமிக்கலாம்.",
                fontSize = 12.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
              )
              Button(
                onClick = { showAddDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
                shape = RoundedCornerShape(8.dp)
              ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("இப்போது நியமிக்க", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      } else {
        items(filteredBearers, key = { it.id }) { bearer ->
          OfficeBearerCard(
            bearer = bearer,
            onCallClick = { phone ->
              if (phone.isNotBlank()) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                context.startActivity(intent)
              } else {
                Toast.makeText(context, "தொடர்பு எண் வழங்கப்படவில்லை", Toast.LENGTH_SHORT).show()
              }
            },
            onWhatsAppClick = { phone ->
              if (phone.isNotBlank()) {
                try {
                  val cleanPhone = phone.replace("+91", "").replace(" ", "").trim()
                  val url = "https://api.whatsapp.com/send?phone=91$cleanPhone"
                  val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                  context.startActivity(intent)
                } catch (e: Exception) {
                  Toast.makeText(context, "WhatsApp திறக்க முடியவில்லை", Toast.LENGTH_SHORT).show()
                }
              }
            },
            onTransferClick = {
              bearerToTransfer = bearer
              showTransferDialog = true
            },
            onToggleStatusClick = {
              val result = OfficeBearerRepository.toggleStatus(
                bearerId = bearer.id,
                adminName = currentAdminName,
                adminRole = currentAdminRole,
                adminDistrict = currentAdminDistrict
              )
              result.onSuccess { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
              }.onFailure { err ->
                Toast.makeText(context, "❌ ${err.message}", Toast.LENGTH_LONG).show()
              }
            },
            onDeleteClick = {
              val result = OfficeBearerRepository.deleteBearer(
                bearerId = bearer.id,
                adminName = currentAdminName,
                adminRole = currentAdminRole,
                adminDistrict = currentAdminDistrict
              )
              result.onSuccess { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
              }.onFailure { err ->
                Toast.makeText(context, "❌ ${err.message}", Toast.LENGTH_LONG).show()
              }
            }
          )
        }
      }

      item { Spacer(modifier = Modifier.height(30.dp)) }
    }
  }

  // ==========================================================================
  // 5. ADD / APPOINT NEW OFFICE BEARER DIALOG
  // ==========================================================================
  if (showAddDialog) {
    AddOfficeBearerDialog(
      initialLevel = selectedLevel,
      currentAdminRole = currentAdminRole,
      currentAdminDistrict = currentAdminDistrict,
      currentAdminName = currentAdminName,
      onDismiss = { showAddDialog = false },
      onAppointed = { newBearer, reason ->
        val result = OfficeBearerRepository.addOfficeBearer(
          newBearer = newBearer,
          adminName = currentAdminName,
          adminRole = currentAdminRole,
          adminDistrict = currentAdminDistrict,
          reason = reason
        )
        result.onSuccess { msg ->
          Toast.makeText(context, "✅ $msg", Toast.LENGTH_SHORT).show()
          showAddDialog = false
        }.onFailure { err ->
          Toast.makeText(context, "❌ ${err.message}", Toast.LENGTH_LONG).show()
        }
      }
    )
  }

  // ==========================================================================
  // 6. TRANSFER / CHANGE OFFICE BEARER DIALOG
  // ==========================================================================
  if (showTransferDialog && bearerToTransfer != null) {
    TransferOfficeBearerDialog(
      oldBearer = bearerToTransfer!!,
      currentAdminRole = currentAdminRole,
      currentAdminDistrict = currentAdminDistrict,
      currentAdminName = currentAdminName,
      onDismiss = {
        showTransferDialog = false
        bearerToTransfer = null
      },
      onTransferred = { newBearerData, reason ->
        val result = OfficeBearerRepository.transferOfficeBearer(
          oldBearerId = bearerToTransfer!!.id,
          newBearerData = newBearerData,
          adminName = currentAdminName,
          adminRole = currentAdminRole,
          adminDistrict = currentAdminDistrict,
          reason = reason
        )
        result.onSuccess { msg ->
          Toast.makeText(context, "✅ $msg", Toast.LENGTH_SHORT).show()
          showTransferDialog = false
          bearerToTransfer = null
        }.onFailure { err ->
          Toast.makeText(context, "❌ ${err.message}", Toast.LENGTH_LONG).show()
        }
      }
    )
  }

  // ==========================================================================
  // 7. AUDIT & APPOINTMENT HISTORY DIALOG
  // ==========================================================================
  if (showAuditLogsDialog) {
    AuditLogsDialog(
      auditLogs = auditLogs,
      onDismiss = { showAuditLogsDialog = false }
    )
  }
}

// ============================================================================
// OFFICE BEARER CARD COMPONENT
// ============================================================================
@Composable
fun OfficeBearerCard(
  bearer: HierarchyOfficeBearer,
  onCallClick: (String) -> Unit,
  onWhatsAppClick: (String) -> Unit,
  onTransferClick: () -> Unit,
  onToggleStatusClick: () -> Unit,
  onDeleteClick: () -> Unit
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = androidx.compose.foundation.BorderStroke(
      width = if (bearer.isActive) 1.5.dp else 1.dp,
      color = if (bearer.isActive) TnpaRedSoft else Color(0xFFE2E8F0)
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      // Header: Avatar, Name, Designation & Status
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
          // Avatar Circle with Gold border
          Box(
            modifier = Modifier
              .size(52.dp)
              .clip(CircleShape)
              .background(Brush.linearGradient(listOf(TnpaJetBlack, TnpaRedPrimary)))
              .border(2.dp, if (bearer.isActive) TnpaGold else Color.Gray, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            val initials = if (bearer.fullName.isNotBlank()) bearer.fullName.take(2).uppercase() else "TN"
            Text(
              text = initials,
              color = TnpaPureWhite,
              fontSize = 16.sp,
              fontWeight = FontWeight.Black
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = bearer.tamilName.ifBlank { bearer.fullName },
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = TnpaJetBlack
              )
              if (bearer.isActive) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                  Icons.Default.Verified,
                  contentDescription = "Verified Bearer",
                  tint = TnpaGreen,
                  modifier = Modifier.size(16.dp)
                )
              }
            }

            if (bearer.fullName.isNotBlank() && bearer.tamilName != bearer.fullName) {
              Text(
                text = bearer.fullName,
                fontSize = 11.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.SemiBold
              )
            }

            // Designation Badge
            Box(
              modifier = Modifier
                .padding(top = 2.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(if (bearer.isActive) TnpaRedPrimary else Color(0xFF64748B))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = bearer.designation,
                color = TnpaPureWhite,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        // Active / Inactive Status Tag
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (bearer.isActive) Color(0xFFDCFCE7) else Color(0xFFF1F5F9))
            .border(1.dp, if (bearer.isActive) Color(0xFF86EFAC) else Color(0xFFCBD5E1), RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp)
        ) {
          Text(
            text = if (bearer.isActive) "Active" else "Inactive",
            color = if (bearer.isActive) Color(0xFF166534) else Color(0xFF64748B),
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Black
          )
        }
      }

      HorizontalDivider(color = Color(0xFFF1F5F9))

      // Jurisdiction & Details
      Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.LocationOn, contentDescription = null, tint = TnpaRedDark, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = buildString {
              append(bearer.district)
              if (bearer.unionName.isNotBlank()) append(" • ஒன்றியம்: ${bearer.unionName}")
              if (bearer.cityName.isNotBlank()) append(" • நகரம்: ${bearer.cityName}")
              if (bearer.zone.isNotBlank()) append(" • ${bearer.zone.split(" ").first()}")
            },
            fontSize = 11.sp,
            color = TnpaJetBlack,
            fontWeight = FontWeight.Medium
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "📅 பொறுப்பு: ${bearer.startDate}${if (bearer.endDate != null) " - ${bearer.endDate}" else " (தற்போது)"}",
            fontSize = 10.sp,
            color = Color(0xFF64748B)
          )
          Text(
            text = "ID: ${bearer.id}",
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Gray
          )
        }
      }

      // Contact Buttons & Admin Actions
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Direct Call Button & WhatsApp Button (Only shown if mobile exists)
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          if (bearer.mobile.isNotBlank()) {
            Button(
              onClick = { onCallClick(bearer.mobile) },
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp),
              modifier = Modifier.height(34.dp)
            ) {
              Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("அழைக்க", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
              onClick = { onWhatsAppClick(bearer.mobile) },
              shape = RoundedCornerShape(8.dp),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp),
              modifier = Modifier.height(34.dp)
            ) {
              Text("💬 WhatsApp", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF166534))
            }
          }
        }

        // Admin Actions (Transfer, Status, Delete)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          IconButton(
            onClick = onTransferClick,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(Icons.Default.SwapHoriz, contentDescription = "Transfer Position", tint = TnpaRedPrimary, modifier = Modifier.size(18.dp))
          }

          IconButton(
            onClick = onToggleStatusClick,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              if (bearer.isActive) Icons.Default.Close else Icons.Default.CheckCircle,
              contentDescription = "Toggle Status",
              tint = if (bearer.isActive) Color(0xFFD97706) else TnpaGreen,
              modifier = Modifier.size(18.dp)
            )
          }

          IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFF94A3B8), modifier = Modifier.size(18.dp))
          }
        }
      }
    }
  }
}

// ============================================================================
// ADD OFFICE BEARER DIALOG
// ============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOfficeBearerDialog(
  initialLevel: AdminHierarchyLevel,
  currentAdminRole: AdminRole,
  currentAdminDistrict: String,
  currentAdminName: String,
  onDismiss: () -> Unit,
  onAppointed: (HierarchyOfficeBearer, String) -> Unit
) {
  var level by remember { mutableStateOf(initialLevel) }
  var district by remember { mutableStateOf(if (level == AdminHierarchyLevel.STATE) "தமிழ்நாடு முழுவதும் (All TN)" else TamilNaduMasterData.DISTRICTS_38.first()) }
  var zone by remember { mutableStateOf(TamilNaduMasterData.ZONES_LIST.first()) }
  var unionName by remember { mutableStateOf("") }
  var cityName by remember { mutableStateOf("") }

  var designation by remember { mutableStateOf("") }
  var tamilName by remember { mutableStateOf("") }
  var fullName by remember { mutableStateOf("") }
  var mobile by remember { mutableStateOf("") }
  var altPhone by remember { mutableStateOf("") }
  var appointmentReason by remember { mutableStateOf("சங்க விதிகளின்படி புதிய பொறுப்பாளர் நியமனம்") }

  // Designation suggestions based on level
  val designationSuggestions = when (level) {
    AdminHierarchyLevel.STATE -> TamilNaduMasterData.STATE_POSTS
    AdminHierarchyLevel.ZONE -> TamilNaduMasterData.ZONE_POSTS
    AdminHierarchyLevel.DISTRICT -> TamilNaduMasterData.DISTRICT_POSTS
    AdminHierarchyLevel.UNION -> TamilNaduMasterData.UNION_POSTS
    AdminHierarchyLevel.CITY -> TamilNaduMasterData.CITY_POSTS
    AdminHierarchyLevel.DISTRICT_YOUTH, AdminHierarchyLevel.UNION_YOUTH, AdminHierarchyLevel.CITY_YOUTH -> TamilNaduMasterData.YOUTH_WING_POSTS
  }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = TnpaRedPrimary)
        Spacer(modifier = Modifier.width(8.dp))
        Text("புதிய பொறுப்பாளர் நியமனம்", fontSize = 16.sp, fontWeight = FontWeight.Black)
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Level Dropdown
        var levelExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
          expanded = levelExpanded,
          onExpandedChange = { levelExpanded = it }
        ) {
          OutlinedTextField(
            value = "${level.iconEmoji} ${level.labelTamil}",
            onValueChange = {},
            readOnly = true,
            label = { Text("நிர்வாக நிலை (Level)") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = levelExpanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
          )
          ExposedDropdownMenu(expanded = levelExpanded, onDismissRequest = { levelExpanded = false }) {
            AdminHierarchyLevel.values().forEach { l ->
              DropdownMenuItem(
                text = { Text("${l.iconEmoji} ${l.labelTamil}") },
                onClick = {
                  level = l
                  levelExpanded = false
                }
              )
            }
          }
        }

        // District Dropdown (For non-state levels)
        if (level != AdminHierarchyLevel.STATE) {
          var distExpanded by remember { mutableStateOf(false) }
          ExposedDropdownMenuBox(
            expanded = distExpanded,
            onExpandedChange = { distExpanded = it }
          ) {
            OutlinedTextField(
              value = district,
              onValueChange = {},
              readOnly = true,
              label = { Text("மாவட்டம் (38 Districts Master)") },
              trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = distExpanded) },
              modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
              expanded = distExpanded,
              onDismissRequest = { distExpanded = false },
              modifier = Modifier.height(240.dp)
            ) {
              TamilNaduMasterData.DISTRICTS_38.forEach { d ->
                DropdownMenuItem(
                  text = { Text(d) },
                  onClick = {
                    district = d
                    distExpanded = false
                  }
                )
              }
            }
          }
        }

        // Union / City Name Input if applicable
        if (level == AdminHierarchyLevel.UNION || level == AdminHierarchyLevel.UNION_YOUTH) {
          OutlinedTextField(
            value = unionName,
            onValueChange = { unionName = it },
            label = { Text("ஒன்றியம் பெயர் (Union Name)") },
            placeholder = { Text("எ.கா: மேலூர் ஒன்றியம்") },
            modifier = Modifier.fillMaxWidth()
          )
        }

        if (level == AdminHierarchyLevel.CITY || level == AdminHierarchyLevel.CITY_YOUTH) {
          OutlinedTextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("நகரம் பெயர் (City / Town Name)") },
            placeholder = { Text("எ.கா: மதுரை மாநகரம்") },
            modifier = Modifier.fillMaxWidth()
          )
        }

        // Designation (with suggestions)
        OutlinedTextField(
          value = designation,
          onValueChange = { designation = it },
          label = { Text("பதவி (Designation)") },
          placeholder = { Text("எ.கா: மாவட்டத் தலைவர்") },
          modifier = Modifier.fillMaxWidth()
        )

        // Quick Suggestions
        Text("பதவிப் பரிந்துரைகள்:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Row(
          modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          designationSuggestions.take(4).forEach { sug ->
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFE2E8F0))
                .clickable { designation = sug }
                .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
              Text(sug.split("(").first().trim(), fontSize = 9.5.sp, color = TnpaJetBlack)
            }
          }
        }

        // Names
        OutlinedTextField(
          value = tamilName,
          onValueChange = { tamilName = it },
          label = { Text("பொறுப்பாளர் பெயர் (தமிழ்)*") },
          placeholder = { Text("எ.கா: கே. முருகன்") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = fullName,
          onValueChange = { fullName = it },
          label = { Text("ஆங்கிலப் பெயர் (Full Name)") },
          placeholder = { Text("எ.கா: K. Murugan") },
          modifier = Modifier.fillMaxWidth()
        )

        // Phone Numbers
        OutlinedTextField(
          value = mobile,
          onValueChange = { mobile = it },
          label = { Text("மொபைல் எண் (10 இலக்கம்)*") },
          placeholder = { Text("98421XXXXX") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = altPhone,
          onValueChange = { altPhone = it },
          label = { Text("மாற்று தொடர்பு எண் (Optional)") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = appointmentReason,
          onValueChange = { appointmentReason = it },
          label = { Text("நியமனக் குறிப்பு / தீர்மானம்") },
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          val newBearer = HierarchyOfficeBearer(
            fullName = fullName.ifBlank { tamilName },
            tamilName = tamilName.ifBlank { fullName },
            designation = designation,
            level = level,
            district = district,
            zone = if (level == AdminHierarchyLevel.ZONE) zone else "",
            unionName = unionName,
            cityName = cityName,
            mobile = mobile,
            altPhone = altPhone,
            appointedByAdmin = "$currentAdminName ($currentAdminRole)",
            isActive = true
          )
          onAppointed(newBearer, appointmentReason)
        },
        colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
        shape = RoundedCornerShape(8.dp)
      ) {
        Text("நியமிக்க (Save)")
      }
    },
    dismissButton = {
      OutlinedButton(onClick = onDismiss, shape = RoundedCornerShape(8.dp)) {
        Text("ரத்து")
      }
    }
  )
}

// ============================================================================
// TRANSFER / CHANGE OFFICE BEARER DIALOG
// ============================================================================
@Composable
fun TransferOfficeBearerDialog(
  oldBearer: HierarchyOfficeBearer,
  currentAdminRole: AdminRole,
  currentAdminDistrict: String,
  currentAdminName: String,
  onDismiss: () -> Unit,
  onTransferred: (HierarchyOfficeBearer, String) -> Unit
) {
  var newTamilName by remember { mutableStateOf("") }
  var newFullName by remember { mutableStateOf("") }
  var newMobile by remember { mutableStateOf("") }
  var newAltPhone by remember { mutableStateOf("") }
  var transferReason by remember { mutableStateOf("காலமுறை நிர்வாக மாற்றம் / புதிய பொறுப்பாளர் நியமனம்") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = TnpaGold)
        Spacer(modifier = Modifier.width(8.dp))
        Text("பொறுப்பு மாற்றம் (Transfer)", fontSize = 16.sp, fontWeight = FontWeight.Black)
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Current Bearer Notice
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(8.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
          border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedSoft)
        ) {
          Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("தற்போதைய பொறுப்பாளர் (Current):", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TnpaRedDark)
            Text("${oldBearer.tamilName} (${oldBearer.fullName})", fontSize = 13.sp, fontWeight = FontWeight.Black)
            Text("பதவி: ${oldBearer.designation} • ${oldBearer.district}", fontSize = 11.sp, color = TnpaJetBlack)
          }
        }

        Text("புதிய பொறுப்பாளர் விவரங்கள்:", fontSize = 12.sp, fontWeight = FontWeight.Black, color = TnpaJetBlack)

        OutlinedTextField(
          value = newTamilName,
          onValueChange = { newTamilName = it },
          label = { Text("புதிய நபர் பெயர் (தமிழ்)*") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = newFullName,
          onValueChange = { newFullName = it },
          label = { Text("புதிய நபர் பெயர் (ஆங்கிலம்)") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = newMobile,
          onValueChange = { newMobile = it },
          label = { Text("மொபைல் எண்*") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = newAltPhone,
          onValueChange = { newAltPhone = it },
          label = { Text("மாற்று தொடர்பு எண்") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = transferReason,
          onValueChange = { transferReason = it },
          label = { Text("மாற்றத்திற்கான காரணம் (Audit Reason)*") },
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          val newBearer = HierarchyOfficeBearer(
            fullName = newFullName.ifBlank { newTamilName },
            tamilName = newTamilName.ifBlank { newFullName },
            designation = oldBearer.designation,
            level = oldBearer.level,
            district = oldBearer.district,
            zone = oldBearer.zone,
            unionName = oldBearer.unionName,
            cityName = oldBearer.cityName,
            mobile = newMobile,
            altPhone = newAltPhone,
            appointedByAdmin = "$currentAdminName ($currentAdminRole)"
          )
          onTransferred(newBearer, transferReason)
        },
        colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen),
        shape = RoundedCornerShape(8.dp)
      ) {
        Text("மாற்றம் செய்க (Transfer)")
      }
    },
    dismissButton = {
      OutlinedButton(onClick = onDismiss, shape = RoundedCornerShape(8.dp)) {
        Text("ரத்து")
      }
    }
  )
}

// ============================================================================
// AUDIT LOGS DIALOG (COMPLETE APPOINTMENT & TRANSFER HISTORY)
// ============================================================================
@Composable
fun AuditLogsDialog(
  auditLogs: List<AppointmentAuditLog>,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.History, contentDescription = null, tint = TnpaGold)
        Spacer(modifier = Modifier.width(8.dp))
        Text("ஆடிட் வரலாறு (Audit & History)", fontSize = 16.sp, fontWeight = FontWeight.Black)
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .height(380.dp)
      ) {
        Text(
          text = "பதவிகள் நியமனம் மற்றும் மாற்றங்களின் முழுப் பதிவு:",
          fontSize = 11.sp,
          color = Color.Gray,
          modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          items(auditLogs) { log ->
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(8.dp),
              colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
              border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
              Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(4.dp))
                      .background(TnpaRedPrimary)
                      .padding(horizontal = 6.dp, vertical = 2.dp)
                  ) {
                    Text(log.actionType, color = TnpaPureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                  }
                  Text(log.timestamp, fontSize = 9.sp, color = Color.Gray)
                }

                Text("பதவி: ${log.positionName} (${log.jurisdiction})", fontSize = 11.sp, fontWeight = FontWeight.Black)
                if (log.previousBearerName != "-") {
                  Text("முந்தையவர்: ${log.previousBearerName}", fontSize = 10.5.sp, color = Color(0xFFDC2626))
                }
                Text("புதிய நபர்: ${log.newBearerName}", fontSize = 10.5.sp, color = Color(0xFF16A34A), fontWeight = FontWeight.Bold)
                Text("மாற்றிய Admin: ${log.changedByAdmin} (${log.adminRole})", fontSize = 10.sp, color = Color.DarkGray)
                Text("காரணம்: ${log.reason}", fontSize = 10.sp, color = Color(0xFF475569))
              }
            }
          }
        }
      }
    },
    confirmButton = {
      Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = TnpaJetBlack)) {
        Text("மூடுக (Close)")
      }
    }
  )
}
