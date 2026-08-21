package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AdminApprovalRepository
import com.example.model.JobCategory
import com.example.model.JobPostingItem
import com.example.model.PredefinedAdminPosts
import com.example.model.WorkSeekerItem
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EmploymentScreen(
  onNavigateToTab: ((Int) -> Unit)? = null
) {
  val context = LocalContext.current
  var selectedTab by remember { mutableIntStateOf(0) }
  var refreshTrigger by remember { mutableIntStateOf(0) }

  // District filter for job listings
  var selectedDistrictFilter by remember { mutableStateOf("அனைத்து மாவட்டங்களும்") }
  var selectedGovtFilter by remember { mutableStateOf<Boolean?>(null) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(TnpaOffWhite)
  ) {
    // 1. Top Header Banner (Red & Black with Gold Accent)
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(Brush.horizontalGradient(listOf(TnpaRedDark, TnpaRedPrimary, TnpaJetBlack)))
        .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          TnpaOfficialEmblem(sizeDp = 42.dp)
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "வேலைவாய்ப்பு வழிகாட்டி மையம்",
              color = TnpaPureWhite,
              fontSize = 15.sp,
              fontWeight = FontWeight.Black
            )
            Text(
              text = "அரசு & தனியார் பெயிண்டிங் வேலைகள் • நேரடி தொழிலாளர் இணைப்பு",
              color = TnpaGold,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(TnpaGold)
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = "TNPA CAREERS",
            color = TnpaJetBlack,
            fontSize = 9.sp,
            fontWeight = FontWeight.Black
          )
        }
      }
    }

    // 2. Navigation Tabs
    ScrollableTabRow(
      selectedTabIndex = selectedTab,
      edgePadding = 12.dp,
      containerColor = TnpaPureWhite,
      contentColor = TnpaRedPrimary,
      modifier = Modifier.fillMaxWidth()
    ) {
      Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        text = { Text("வேலைவாய்ப்புகள்", fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) },
        icon = { Icon(Icons.Default.Work, contentDescription = null, modifier = Modifier.size(16.dp)) },
        modifier = Modifier.testTag("tab_job_listings")
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = { Text("வேலை தேடுகிறேன்", fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) },
        icon = { Icon(Icons.Default.PersonSearch, contentDescription = null, modifier = Modifier.size(16.dp)) },
        modifier = Modifier.testTag("tab_work_seeker")
      )
      Tab(
        selected = selectedTab == 2,
        onClick = { selectedTab = 2 },
        text = { Text("ஆட்கள் தேவை (Post Job)", fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal) },
        icon = { Icon(Icons.Default.PostAdd, contentDescription = null, modifier = Modifier.size(16.dp)) },
        modifier = Modifier.testTag("tab_post_job")
      )
      Tab(
        selected = selectedTab == 3,
        onClick = { selectedTab = 3 },
        text = { Text("தொழிலாளர்கள் பட்டியல்", fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal) },
        icon = { Icon(Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(16.dp)) },
        modifier = Modifier.testTag("tab_artisans_list")
      )
    }

    HorizontalDivider(color = TnpaRedSoft)

    // Tab Content
    Box(modifier = Modifier.weight(1f)) {
      when (selectedTab) {
        0 -> JobListingsSubScreen(
          districtFilter = selectedDistrictFilter,
          isGovtFilter = selectedGovtFilter,
          onDistrictFilterChange = { selectedDistrictFilter = it },
          onGovtFilterChange = { selectedGovtFilter = it },
          onPostJobClick = { selectedTab = 2 },
          refreshTrigger = refreshTrigger
        )
        1 -> WorkSeekerApplicationSubScreen(
          onSubmittedSuccess = {
            refreshTrigger++
            selectedTab = 3
          }
        )
        2 -> EmployerJobPostingSubScreen(
          onJobSubmitted = {
            refreshTrigger++
            selectedTab = 0
          }
        )
        3 -> AvailableArtisansSubScreen(
          districtFilter = selectedDistrictFilter,
          onDistrictFilterChange = { selectedDistrictFilter = it },
          refreshTrigger = refreshTrigger
        )
      }
    }
  }
}

// ============================================================================
// 1. JOB LISTINGS SUB-SCREEN (PUBLIC APPROVED JOBS ONLY)
// ============================================================================
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun JobListingsSubScreen(
  districtFilter: String,
  isGovtFilter: Boolean?,
  onDistrictFilterChange: (String) -> Unit,
  onGovtFilterChange: (Boolean?) -> Unit,
  onPostJobClick: () -> Unit,
  refreshTrigger: Int
) {
  val context = LocalContext.current
  val approvedJobs = remember(districtFilter, isGovtFilter, refreshTrigger) {
    AdminApprovalRepository.getPublicApprovedJobs(
      districtFilter = if (districtFilter == "அனைத்து மாவட்டங்களும்") null else districtFilter,
      isGovtFilter = isGovtFilter
    )
  }

  var districtDropdownExpanded by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Top Info & Filters Header
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedSoft)
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "நேரடி வேலை வாய்ப்புகள்",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = TnpaRedDark
            )

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(TnpaGreen.copy(alpha = 0.15f))
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "${approvedJobs.size} வேலைகள் தயார்",
                color = TnpaGreen,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Text(
            text = "மாநில தலைமையால் சரிபார்க்கப்பட்டு வெளியிடப்பட்ட அதிகாரப்பூர்வ வேலைவாய்ப்புகள்.",
            fontSize = 11.sp,
            color = TnpaCharcoal
          )

          // District Dropdown Filter
          ExposedDropdownMenuBox(
            expanded = districtDropdownExpanded,
            onExpandedChange = { districtDropdownExpanded = !districtDropdownExpanded }
          ) {
            OutlinedTextField(
              value = districtFilter,
              onValueChange = {},
              readOnly = true,
              label = { Text("மாவட்டம் வாரியாக தேடுக") },
              trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = districtDropdownExpanded) },
              modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .testTag("dropdown_filter_job_district"),
              shape = RoundedCornerShape(10.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TnpaRedPrimary,
                unfocusedBorderColor = TnpaRedSoft
              )
            )

            ExposedDropdownMenu(
              expanded = districtDropdownExpanded,
              onDismissRequest = { districtDropdownExpanded = false }
            ) {
              DropdownMenuItem(
                text = { Text("அனைத்து மாவட்டங்களும் (All)") },
                onClick = {
                  onDistrictFilterChange("அனைத்து மாவட்டங்களும்")
                  districtDropdownExpanded = false
                }
              )
              PredefinedAdminPosts.TAMIL_NADU_DISTRICTS.forEach { dist ->
                DropdownMenuItem(
                  text = { Text(dist) },
                  onClick = {
                    onDistrictFilterChange(dist)
                    districtDropdownExpanded = false
                  }
                )
              }
            }
          }

          // Filter Chips (All, Govt, Private)
          FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            FilterChip(
              selected = isGovtFilter == null,
              onClick = { onGovtFilterChange(null) },
              label = { Text("அனைத்து வேலைகள்") },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = TnpaRedPrimary,
                selectedLabelColor = TnpaPureWhite
              )
            )
            FilterChip(
              selected = isGovtFilter == true,
              onClick = { onGovtFilterChange(true) },
              label = { Text("🏛️ அரசு திட்டங்கள் (Govt)") },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = TnpaRedDark,
                selectedLabelColor = TnpaPureWhite
              )
            )
            FilterChip(
              selected = isGovtFilter == false,
              onClick = { onGovtFilterChange(false) },
              label = { Text("🏢 தனியார் நிறுவனங்கள்") },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = TnpaJetBlack,
                selectedLabelColor = TnpaPureWhite
              )
            )
          }
        }
      }
    }

    if (approvedJobs.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = TnpaPureWhite)
        ) {
          Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(Icons.Default.Info, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(36.dp))
            Text(
              text = "தேர்ந்தெடுக்கப்பட்ட மாவட்டத்தில் தற்போது வேலைகள் இல்லை.",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center
            )
            Text(
              text = "வேலை வழங்குபவரா? புதிய அறிவிப்பை உடனே சமர்ப்பிக்கவும்.",
              fontSize = 11.sp,
              color = TnpaCharcoal,
              textAlign = TextAlign.Center
            )
            Button(
              onClick = onPostJobClick,
              colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("வேலை அறிவிப்பு வெளியிட (Post Job)")
            }
          }
        }
      }
    } else {
      items(approvedJobs, key = { it.id }) { job ->
        JobPostingCard(job = job)
      }
    }
  }
}

@Composable
fun JobPostingCard(job: JobPostingItem) {
  val context = LocalContext.current
  var showDetailsDialog by remember { mutableStateOf(false) }

  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (job.isGovtJob) TnpaGold else TnpaRedSoft
    )
  ) {
    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
      // Top Badges Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (job.isGovtJob) Color(0xFF1E3A8A) else TnpaRedDark)
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = if (job.isGovtJob) "🏛️ அரசு பணி" else "🏢 தனியார் வேலை",
            color = TnpaPureWhite,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(TnpaGold.copy(alpha = 0.2f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = "👥 ${job.workersNeeded} தொழிலாளர்கள் தேவை",
            color = TnpaJetBlack,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      // Title & Employer
      Text(
        text = job.jobTitle,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Black,
        color = TnpaJetBlack
      )

      Text(
        text = "நிறுவனம் / வழங்குநர்: ${job.employerName}",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = TnpaRedDark
      )

      // Location & Wage
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
          Icon(Icons.Default.LocationOn, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(text = job.district, fontSize = 11.sp, color = TnpaCharcoal, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Schedule, contentDescription = null, tint = TnpaGreen, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(text = job.workDuration, fontSize = 11.sp, color = TnpaGreen, fontWeight = FontWeight.Bold)
        }
      }

      // Wage Box
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(TnpaRedSoft.copy(alpha = 0.5f))
          .padding(horizontal = 10.dp, vertical = 6.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "சம்பளம் / தினக்கூலி:",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TnpaRedDark
          )
          Text(
            text = job.dailyWageOrSalary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = TnpaJetBlack
          )
        }
      }

      Text(
        text = "தேவைப்படும் திறன்: ${job.requiredSkills}",
        fontSize = 11.sp,
        color = TnpaCharcoal,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      HorizontalDivider(color = TnpaRedSoft)

      // Action Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedButton(
          onClick = { showDetailsDialog = true },
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = TnpaRedDark)
        ) {
          Text("முழு விவரம்", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }

        Button(
          onClick = {
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${job.contactMobile}"))
            context.startActivity(callIntent)
          },
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen)
        ) {
          Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("தொடர்புகொள்ள", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }

  if (showDetailsDialog) {
    AlertDialog(
      onDismissRequest = { showDetailsDialog = false },
      title = {
        Text(
          text = job.jobTitle,
          fontWeight = FontWeight.Black,
          color = TnpaRedDark
        )
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text("🏛️ நிறுவனம்: ${job.employerName}", fontWeight = FontWeight.Bold)
          Text("📍 இடம்: ${job.workLocation} (${job.district})")
          Text("🛠️ பணி வகை: ${job.workType}")
          Text("👥 தேவையான ஆட்கள்: ${job.workersNeeded} நபர்கள்")
          Text("⏱️ கால அளவு: ${job.workDuration}")
          Text("💰 கூலி விகிதம்: ${job.dailyWageOrSalary}", fontWeight = FontWeight.Bold, color = TnpaGreen)
          Text("🎨 தேவையான திறன்கள்: ${job.requiredSkills}")
          Text("📞 தொடர்பு எண்: ${job.contactMobile}")
          if (job.contactEmail.isNotBlank()) {
            Text("✉️ மின்னஞ்சல்: ${job.contactEmail}")
          }
          Text("✅ சரிபார்க்கப்பட்டது: ${job.approvedByAdminName ?: "Super Admin"}", fontSize = 11.sp, color = TnpaCharcoal)
        }
      },
      confirmButton = {
        Button(
          onClick = {
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${job.contactMobile}"))
            context.startActivity(callIntent)
            showDetailsDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen)
        ) {
          Text("அழைக்க (Call)")
        }
      },
      dismissButton = {
        TextButton(onClick = { showDetailsDialog = false }) {
          Text("மூடு (Close)")
        }
      }
    )
  }
}

// ============================================================================
// 2. WORK SEEKER APPLICATION SUB-SCREEN
// ============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkSeekerApplicationSubScreen(
  onSubmittedSuccess: () -> Unit
) {
  val context = LocalContext.current

  var nameInput by remember { mutableStateOf("") }
  var districtInput by remember { mutableStateOf(PredefinedAdminPosts.TAMIL_NADU_DISTRICTS[0]) }
  var experienceInput by remember { mutableStateOf("5") }
  var specializationInput by remember { mutableStateOf("சுவர் ஓவியம் & பில்டிங் பெயிண்டிங்") }
  var skillsInput by remember { mutableStateOf("") }
  var mobileInput by remember { mutableStateOf("") }
  var workPrefInput by remember { mutableStateOf("தினக்கூலி / ஒப்பந்த பணி") }
  var availabilityInput by remember { mutableStateOf("உடனடியாக (Immediate)") }

  var districtDropdownExpanded by remember { mutableStateOf(false) }
  var isSubmitting by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedSoft)
    ) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(TnpaRedPrimary),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.PersonSearch, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(20.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "வேலை தேடும் தொழிலாளர் பதிவு",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = TnpaRedDark
            )
            Text(
              text = "உங்கள் விவரங்களை பதிவு செய்தால் வேலை வழங்குநர்கள் நேரடியாக தொடர்புகொள்வர்.",
              fontSize = 11.sp,
              color = TnpaCharcoal
            )
          }
        }

        HorizontalDivider(color = TnpaRedSoft)

        OutlinedTextField(
          value = nameInput,
          onValueChange = { nameInput = it },
          label = { Text("உங்கள் முழுப் பெயர் (Full Name)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_seeker_name"),
          shape = RoundedCornerShape(10.dp)
        )

        // District Dropdown
        ExposedDropdownMenuBox(
          expanded = districtDropdownExpanded,
          onExpandedChange = { districtDropdownExpanded = !districtDropdownExpanded }
        ) {
          OutlinedTextField(
            value = districtInput,
            onValueChange = {},
            readOnly = true,
            label = { Text("உங்கள் மாவட்டம் (District)") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = districtDropdownExpanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth().testTag("input_seeker_district"),
            shape = RoundedCornerShape(10.dp)
          )

          ExposedDropdownMenu(
            expanded = districtDropdownExpanded,
            onDismissRequest = { districtDropdownExpanded = false }
          ) {
            PredefinedAdminPosts.TAMIL_NADU_DISTRICTS.forEach { dist ->
              DropdownMenuItem(
                text = { Text(dist) },
                onClick = {
                  districtInput = dist
                  districtDropdownExpanded = false
                }
              )
            }
          }
        }

        OutlinedTextField(
          value = mobileInput,
          onValueChange = { if (it.length <= 10) mobileInput = it },
          label = { Text("கைபேசி எண் (10-Digit Mobile Number)") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_seeker_mobile"),
          shape = RoundedCornerShape(10.dp)
        )

        OutlinedTextField(
          value = experienceInput,
          onValueChange = { experienceInput = it },
          label = { Text("அனுபவம் ஆண்டுகள் (Years of Experience)") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_seeker_exp"),
          shape = RoundedCornerShape(10.dp)
        )

        OutlinedTextField(
          value = specializationInput,
          onValueChange = { specializationInput = it },
          label = { Text("முதன்மையான பெயிண்டிங் துறை (Specialization)") },
          modifier = Modifier.fillMaxWidth().testTag("input_seeker_spec"),
          shape = RoundedCornerShape(10.dp)
        )

        OutlinedTextField(
          value = skillsInput,
          onValueChange = { skillsInput = it },
          label = { Text("தெரிந்த கூடுதல் திறன்கள் (Wall Putty, Texture, Wood Polish...)") },
          modifier = Modifier.fillMaxWidth().testTag("input_seeker_skills"),
          shape = RoundedCornerShape(10.dp)
        )

        Button(
          onClick = {
            if (nameInput.isBlank() || mobileInput.length < 10) {
              Toast.makeText(context, "தயவுசெய்து பெயர் மற்றும் சரியான கைபேசி எண் உள்ளிடவும்.", Toast.LENGTH_SHORT).show()
              return@Button
            }

            isSubmitting = true
            val seeker = WorkSeekerItem(
              seekerName = nameInput.trim(),
              district = districtInput,
              experienceYears = experienceInput.toIntOrNull() ?: 1,
              paintingSpecialization = specializationInput.trim(),
              skills = skillsInput.ifBlank { "பொதுவான பெயிண்டிங் பணிகள்" },
              contactMobile = mobileInput.trim(),
              workPreference = workPrefInput,
              availability = availabilityInput
            )

            AdminApprovalRepository.submitWorkSeeker(seeker)
            isSubmitting = false
            Toast.makeText(context, "உங்கள் விவரங்கள் வெற்றிகரமாக பதிவு செய்யப்பட்டன!", Toast.LENGTH_LONG).show()
            onSubmittedSuccess()
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("btn_submit_seeker"),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
        ) {
          Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text("வேலை தேடுவோர் பட்டியலில் சேர்க்க (Submit Profile)", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

// ============================================================================
// 3. EMPLOYER JOB POSTING SUB-SCREEN (AWAITING SUPER ADMIN APPROVAL)
// ============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployerJobPostingSubScreen(
  onJobSubmitted: () -> Unit
) {
  val context = LocalContext.current

  var companyNameInput by remember { mutableStateOf("") }
  var categoryInput by remember { mutableStateOf(JobCategory.PAINTING_CONTRACTOR) }
  var jobTitleInput by remember { mutableStateOf("") }
  var districtInput by remember { mutableStateOf(PredefinedAdminPosts.TAMIL_NADU_DISTRICTS[0]) }
  var locationInput by remember { mutableStateOf("") }
  var workTypeInput by remember { mutableStateOf("தினக்கூலி (Daily Wage)") }
  var workersCountInput by remember { mutableStateOf("5") }
  var skillsInput by remember { mutableStateOf("") }
  var experienceInput by remember { mutableStateOf("2") }
  var mobileInput by remember { mutableStateOf("") }
  var emailInput by remember { mutableStateOf("") }
  var wageInput by remember { mutableStateOf("₹900 / நாள்") }
  var durationInput by remember { mutableStateOf("15 நாட்கள்") }

  var districtDropdownExpanded by remember { mutableStateOf(false) }
  var categoryDropdownExpanded by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Crucial Super Admin Verification Notice Box
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
      border = androidx.compose.foundation.BorderStroke(1.5.dp, TnpaGold)
    ) {
      Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
        Icon(Icons.Default.Shield, contentDescription = null, tint = Color(0xFFB45309), modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Text(
            text = "Super Admin ஒப்புதல் கட்டாயம் (Mandatory Admin Approval)",
            fontWeight = FontWeight.Black,
            fontSize = 12.sp,
            color = Color(0xFF78350F)
          )
          Text(
            text = "நீங்கள் சமர்ப்பிக்கும் வேலை அறிவிப்பு உடனே பொதுத்தளத்தில் தெரியாது. மாநில பொதுச் செயலாளர் (Super Admin) சரிபார்த்து ஒப்புதல் அளித்த பின்பே இணையதளத்தில் வெளியாகும்.",
            fontSize = 11.sp,
            color = Color(0xFF92400E),
            lineHeight = 16.sp
          )
        }
      }
    }

    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedSoft)
    ) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
          text = "பெயிண்டிங் வேலைக்கு ஆட்கள் தேவை பதிவு",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = TnpaRedDark
        )

        HorizontalDivider(color = TnpaRedSoft)

        OutlinedTextField(
          value = companyNameInput,
          onValueChange = { companyNameInput = it },
          label = { Text("நிறுவனம் / ஒப்பந்ததாரர் / உரிமையாளர் பெயர்") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_post_company_name"),
          shape = RoundedCornerShape(10.dp)
        )

        // Category Dropdown
        ExposedDropdownMenuBox(
          expanded = categoryDropdownExpanded,
          onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded }
        ) {
          OutlinedTextField(
            value = categoryInput.labelTamil,
            onValueChange = {},
            readOnly = true,
            label = { Text("நிறுவன வகை (Company Type)") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth().testTag("input_post_category"),
            shape = RoundedCornerShape(10.dp)
          )

          ExposedDropdownMenu(
            expanded = categoryDropdownExpanded,
            onDismissRequest = { categoryDropdownExpanded = false }
          ) {
            JobCategory.values().forEach { cat ->
              DropdownMenuItem(
                text = { Text(cat.labelTamil) },
                onClick = {
                  categoryInput = cat
                  categoryDropdownExpanded = false
                }
              )
            }
          }
        }

        OutlinedTextField(
          value = jobTitleInput,
          onValueChange = { jobTitleInput = it },
          label = { Text("பணியின் தலைப்பு (Job Title e.g. Apartment Exterior Painting)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_post_job_title"),
          shape = RoundedCornerShape(10.dp)
        )

        // District Dropdown
        ExposedDropdownMenuBox(
          expanded = districtDropdownExpanded,
          onExpandedChange = { districtDropdownExpanded = !districtDropdownExpanded }
        ) {
          OutlinedTextField(
            value = districtInput,
            onValueChange = {},
            readOnly = true,
            label = { Text("மாவட்டம் (Work District)") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = districtDropdownExpanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth().testTag("input_post_district"),
            shape = RoundedCornerShape(10.dp)
          )

          ExposedDropdownMenu(
            expanded = districtDropdownExpanded,
            onDismissRequest = { districtDropdownExpanded = false }
          ) {
            PredefinedAdminPosts.TAMIL_NADU_DISTRICTS.forEach { dist ->
              DropdownMenuItem(
                text = { Text(dist) },
                onClick = {
                  districtInput = dist
                  districtDropdownExpanded = false
                }
              )
            }
          }
        }

        OutlinedTextField(
          value = locationInput,
          onValueChange = { locationInput = it },
          label = { Text("வேலை நடக்கும் துல்லியமான முகவரி / பகுதி") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_post_location"),
          shape = RoundedCornerShape(10.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          OutlinedTextField(
            value = workersCountInput,
            onValueChange = { workersCountInput = it },
            label = { Text("தேவைப்படும் ஆட்கள் எண்ணிக்கை") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f).testTag("input_post_workers_count"),
            shape = RoundedCornerShape(10.dp)
          )

          OutlinedTextField(
            value = durationInput,
            onValueChange = { durationInput = it },
            label = { Text("வேலை கால அளவு") },
            modifier = Modifier.weight(1f).testTag("input_post_duration"),
            shape = RoundedCornerShape(10.dp)
          )
        }

        OutlinedTextField(
          value = wageInput,
          onValueChange = { wageInput = it },
          label = { Text("சம்பளம் / தினக்கூலி விகிதம் (Wage Info)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_post_wage"),
          shape = RoundedCornerShape(10.dp)
        )

        OutlinedTextField(
          value = skillsInput,
          onValueChange = { skillsInput = it },
          label = { Text("தேவைப்படும் முக்கிய திறன்கள் & பணிகள்") },
          modifier = Modifier.fillMaxWidth().testTag("input_post_skills"),
          shape = RoundedCornerShape(10.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          OutlinedTextField(
            value = mobileInput,
            onValueChange = { if (it.length <= 10) mobileInput = it },
            label = { Text("தொடர்பு எண் (Mobile)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.weight(1f).testTag("input_post_mobile"),
            shape = RoundedCornerShape(10.dp)
          )

          OutlinedTextField(
            value = emailInput,
            onValueChange = { emailInput = it },
            label = { Text("மின்னஞ்சல் (Email)") },
            modifier = Modifier.weight(1f).testTag("input_post_email"),
            shape = RoundedCornerShape(10.dp)
          )
        }

        Button(
          onClick = {
            if (companyNameInput.isBlank() || jobTitleInput.isBlank() || mobileInput.length < 10) {
              Toast.makeText(context, "தயவுசெய்து நிறுவனம், பணி மற்றும் தொடர்பு எண் உள்ளிடவும்.", Toast.LENGTH_SHORT).show()
              return@Button
            }

            val newJob = JobPostingItem(
              employerName = companyNameInput.trim(),
              companyType = categoryInput,
              jobTitle = jobTitleInput.trim(),
              district = districtInput,
              workLocation = locationInput.ifBlank { districtInput },
              workType = workTypeInput,
              workersNeeded = workersCountInput.toIntOrNull() ?: 1,
              requiredSkills = skillsInput.ifBlank { "பொதுவான பெயிண்டிங் திறன்கள்" },
              experienceRequiredYears = experienceInput.toIntOrNull() ?: 1,
              contactMobile = mobileInput.trim(),
              contactEmail = emailInput.trim(),
              dailyWageOrSalary = wageInput.trim(),
              workDuration = durationInput.trim(),
              isGovtJob = categoryInput == JobCategory.GOVERNMENT_PROJECT
            )

            AdminApprovalRepository.submitJobPosting(newJob)
            Toast.makeText(
              context,
              "வேலை அறிவிப்பு வெற்றிகரமாக சமர்ப்பிக்கப்பட்டது! Super Admin ஒப்புதலுக்குப் பின் வெளியாகும்.",
              Toast.LENGTH_LONG
            ).show()
            onJobSubmitted()
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("btn_submit_job_posting"),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
        ) {
          Icon(Icons.Default.AddBusiness, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text("வேலை அறிவிப்பை சமர்ப்பிக்க (Submit for Approval)", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

// ============================================================================
// 4. AVAILABLE ARTISANS & WORKERS DIRECTORY
// ============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailableArtisansSubScreen(
  districtFilter: String,
  onDistrictFilterChange: (String) -> Unit,
  refreshTrigger: Int
) {
  val context = LocalContext.current
  val seekers = remember(districtFilter, refreshTrigger) {
    AdminApprovalRepository.getWorkSeekers(
      districtFilter = if (districtFilter == "அனைத்து மாவட்டங்களும்") null else districtFilter
    )
  }

  var districtDropdownExpanded by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedSoft)
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = "பணிக்குத் தயாராக உள்ள ஓவிய & பெயிண்டிங் கலைஞர்கள்",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = TnpaRedDark
          )

          ExposedDropdownMenuBox(
            expanded = districtDropdownExpanded,
            onExpandedChange = { districtDropdownExpanded = !districtDropdownExpanded }
          ) {
            OutlinedTextField(
              value = districtFilter,
              onValueChange = {},
              readOnly = true,
              label = { Text("மாவட்டம் வாரியாக தேடுக") },
              trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = districtDropdownExpanded) },
              modifier = Modifier.menuAnchor().fillMaxWidth(),
              shape = RoundedCornerShape(10.dp)
            )

            ExposedDropdownMenu(
              expanded = districtDropdownExpanded,
              onDismissRequest = { districtDropdownExpanded = false }
            ) {
              DropdownMenuItem(
                text = { Text("அனைத்து மாவட்டங்களும் (All)") },
                onClick = {
                  onDistrictFilterChange("அனைத்து மாவட்டங்களும்")
                  districtDropdownExpanded = false
                }
              )
              PredefinedAdminPosts.TAMIL_NADU_DISTRICTS.forEach { dist ->
                DropdownMenuItem(
                  text = { Text(dist) },
                  onClick = {
                    onDistrictFilterChange(dist)
                    districtDropdownExpanded = false
                  }
                )
              }
            }
          }
        }
      }
    }

    if (seekers.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = TnpaPureWhite)
        ) {
          Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text("தேர்ந்தெடுக்கப்பட்ட மாவட்டத்தில் கலைஞர்கள் பட்டியல் இல்லை.")
          }
        }
      }
    } else {
      items(seekers, key = { it.id }) { seeker ->
        ArtisanProfileCard(seeker = seeker)
      }
    }
  }
}

@Composable
fun ArtisanProfileCard(seeker: WorkSeekerItem) {
  val context = LocalContext.current

  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedSoft)
  ) {
    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
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
            Icon(Icons.Default.Person, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(18.dp))
          }
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(text = seeker.seekerName, fontWeight = FontWeight.Black, fontSize = 13.sp)
            Text(text = seeker.district, fontSize = 10.sp, color = TnpaRedDark, fontWeight = FontWeight.Bold)
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(TnpaGreen.copy(alpha = 0.15f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(text = seeker.availability, color = TnpaGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
      }

      Text(
        text = "🎨 முதன்மை துறை: ${seeker.paintingSpecialization}",
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = TnpaJetBlack
      )

      Text(
        text = "🛠️ கூடுதல் திறன்கள்: ${seeker.skills}",
        fontSize = 11.sp,
        color = TnpaCharcoal
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = "⭐ அனுபவம்: ${seeker.experienceYears} ஆண்டுகள்", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaGold)

        Button(
          onClick = {
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${seeker.contactMobile}"))
            context.startActivity(callIntent)
          },
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TnpaGreen)
        ) {
          Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("வேலைக்கு அழைக்க", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
