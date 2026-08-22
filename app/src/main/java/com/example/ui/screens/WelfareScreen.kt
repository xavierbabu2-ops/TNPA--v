package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AdminApprovalRepository
import com.example.data.GovtType
import com.example.data.GovtWelfareRepository
import com.example.data.GovtWelfareScheme
import com.example.data.WorkerOccupation
import com.example.ui.theme.TnpaCharcoal
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaNavy
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedPrimary
import com.example.ui.theme.TnpaRedSoft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelfareScreen() {
  val context = LocalContext.current

  // Top Section Sub-Tab Navigation
  // 0: All Schemes, 1: Central Govt, 2: Tamil Nadu Govt, 3: Painters, 4: Artists, 5: Eligibility Checker, 6: Status Tracker, 7: Documents
  var selectedSubTab by remember { mutableIntStateOf(0) }
  var selectedOccupationFilter by remember { mutableStateOf(WorkerOccupation.ALL) }
  var searchQuery by remember { mutableStateOf("") }
  var selectedSchemeForDetail by remember { mutableStateOf<GovtWelfareScheme?>(null) }
  var showStatusHelpModal by remember { mutableStateOf(false) }

  // Eligibility Checker State
  var checkOccupation by remember { mutableStateOf(WorkerOccupation.PAINTER) }
  var checkAgeText by remember { mutableStateOf("32") }
  var checkDistrict by remember { mutableStateOf("சென்னை (Chennai)") }
  var checkIncomeText by remember { mutableStateOf("12000") }
  var checkHasTnCard by remember { mutableStateOf(false) }
  var checkHasEShram by remember { mutableStateOf(false) }
  var eligibilityEvaluationResult by remember { mutableStateOf<GovtWelfareRepository.EligibilityResult?>(null) }

  // Filter schemes based on active tab & filters
  val displayedSchemes = remember(selectedSubTab, selectedOccupationFilter, searchQuery) {
    when (selectedSubTab) {
      0 -> GovtWelfareRepository.getSchemesByFilters(GovtType.ALL, selectedOccupationFilter, searchQuery)
      1 -> GovtWelfareRepository.getSchemesByFilters(GovtType.CENTRAL, selectedOccupationFilter, searchQuery)
      2 -> GovtWelfareRepository.getSchemesByFilters(GovtType.TAMIL_NADU, selectedOccupationFilter, searchQuery)
      3 -> GovtWelfareRepository.getSchemesByFilters(GovtType.ALL, WorkerOccupation.PAINTER, searchQuery)
      4 -> GovtWelfareRepository.getSchemesByFilters(GovtType.ALL, WorkerOccupation.ARTIST, searchQuery)
      else -> GovtWelfareRepository.getSchemesByFilters(GovtType.ALL, selectedOccupationFilter, searchQuery)
    }
  }

  // Safe launcher helper for official government links
  fun openOfficialGovtUrl(url: String, portalName: String) {
    try {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
      context.startActivity(intent)
      Toast.makeText(context, "அதிகாரப்பூர்வ $portalName தளத்திற்கு செல்கிறது...", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
      Toast.makeText(context, "இணையப்பக்கத்தை திறக்க முடியவில்லை: $url", Toast.LENGTH_LONG).show()
    }
  }

  // Share scheme information helper
  fun shareSchemeDetails(scheme: GovtWelfareScheme) {
    try {
      val shareText = buildString {
        appendLine("🏛️ ${scheme.titleTamil}")
        appendLine("(${scheme.titleEnglish})")
        appendLine("அரசு: ${scheme.govtType.labelTamil}")
        appendLine("துறை: ${scheme.boardDepartment}")
        appendLine("முக்கிய பலன்: ${scheme.highlightAmount}")
        appendLine("தகுதி: ${scheme.ageRange} | ${scheme.incomeCriteria}")
        appendLine("\nஅதிகாரப்பூர்வ விண்ணப்ப முகவரி:")
        appendLine(scheme.officialApplyUrl)
        appendLine("\n(வழங்குவது: தமிழ்நாடு பெயிண்டர்கள் ஓவியர்கள் முன்னேற்ற சங்கம் - TNPA)")
      }
      val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
      }
      context.startActivity(Intent.createChooser(sendIntent, "நலத்திட்ட தகவலை பகிர்க"))
    } catch (e: Exception) {
      Toast.makeText(context, "பகிர முடியவில்லை", Toast.LENGTH_SHORT).show()
    }
  }

  // View Mode: Expanded (Full Display) vs Compact
  var isExpandedViewMode by remember { mutableStateOf(true) }
  var isBannerCollapsed by remember { mutableStateOf(false) }
  var expandedSchemeIds by remember { mutableStateOf(setOf<String>()) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(TnpaOffWhite)
  ) {
    // Single Unified LazyColumn for full-screen display real estate
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .testTag("welfare_schemes_lazy_column"),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // 1. Top Header Banner (Collapsible to maximize screen space)
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 6.dp),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
          border = BorderStroke(1.dp, TnpaRedSoft)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .background(Brush.horizontalGradient(listOf(TnpaRedPrimary, TnpaRedDark, TnpaJetBlack)))
              .padding(if (isBannerCollapsed) 10.dp else 14.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(4.dp))
                      .background(TnpaGold)
                      .padding(horizontal = 6.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = "அரசு நலவாரியங்கள்",
                      color = TnpaJetBlack,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Black
                    )
                  }
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "நேரடி அரசு விண்ணப்பம்",
                    color = TnpaPureWhite.copy(alpha = 0.9f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                  )
                }

                Spacer(modifier = Modifier.height(3.dp))
                Text(
                  text = "தொழிலாளர் நலவாரியங்கள் & சமூக பாதுகாப்பு",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Black,
                  color = TnpaPureWhite,
                  fontSize = if (isBannerCollapsed) 13.sp else 15.sp
                )
                if (!isBannerCollapsed) {
                  Text(
                    text = "மத்திய அரசு + தமிழ்நாடு அரசு அதிகாரப்பூர்வ நலத்திட்டங்கள் நேரடி இணைப்பு",
                    fontSize = 11.sp,
                    color = Color(0xFFFEE2E2),
                    lineHeight = 15.sp
                  )
                }
              }

              IconButton(
                onClick = { isBannerCollapsed = !isBannerCollapsed },
                modifier = Modifier
                  .size(34.dp)
                  .clip(CircleShape)
                  .background(TnpaPureWhite.copy(alpha = 0.2f))
              ) {
                Text(
                  text = if (isBannerCollapsed) "▼" else "▲",
                  color = TnpaPureWhite,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            if (!isBannerCollapsed) {
              Spacer(modifier = Modifier.height(6.dp))
              // Zero Fee / Privacy Trust Pill
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Black.copy(alpha = 0.25f))
                .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.Shield, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "🔒 நேரடி அரசு போர்டல் இணைப்பு • TNPA அதிகாரப்பூர்வ வழிகாட்டுதல்",
                  color = TnpaPureWhite,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }

      // 2. Sub-Tabs Navigation
      item {
        ScrollableTabRow(
          selectedTabIndex = selectedSubTab,
          edgePadding = 10.dp,
          containerColor = TnpaPureWhite,
          contentColor = TnpaRedPrimary,
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(10.dp))
        ) {
          Tab(
            selected = selectedSubTab == 0,
            onClick = { selectedSubTab = 0 },
            text = { Text("அனைத்தும் (${GovtWelfareRepository.schemesList.size})", fontSize = 11.sp, fontWeight = if (selectedSubTab == 0) FontWeight.Black else FontWeight.Normal) },
            icon = { Icon(Icons.Default.VolunteerActivism, contentDescription = null, modifier = Modifier.size(16.dp)) },
            modifier = Modifier.testTag("tab_welfare_all")
          )
          Tab(
            selected = selectedSubTab == 1,
            onClick = { selectedSubTab = 1 },
            text = { Text("மத்திய அரசு", fontSize = 11.sp, fontWeight = if (selectedSubTab == 1) FontWeight.Black else FontWeight.Normal) },
            icon = { Icon(Icons.Default.AccountBalance, contentDescription = null, modifier = Modifier.size(16.dp)) },
            modifier = Modifier.testTag("tab_welfare_central")
          )
          Tab(
            selected = selectedSubTab == 2,
            onClick = { selectedSubTab = 2 },
            text = { Text("தமிழ்நாடு அரசு", fontSize = 11.sp, fontWeight = if (selectedSubTab == 2) FontWeight.Black else FontWeight.Normal) },
            icon = { Icon(Icons.Default.AccountBalance, contentDescription = null, modifier = Modifier.size(16.dp)) },
            modifier = Modifier.testTag("tab_welfare_tn")
          )
          Tab(
            selected = selectedSubTab == 3,
            onClick = {
              selectedSubTab = 3
              selectedOccupationFilter = WorkerOccupation.PAINTER
            },
            text = { Text("பெயிண்டர்கள்", fontSize = 11.sp, fontWeight = if (selectedSubTab == 3) FontWeight.Black else FontWeight.Normal) },
            icon = { Icon(Icons.Default.Brush, contentDescription = null, modifier = Modifier.size(16.dp)) },
            modifier = Modifier.testTag("tab_welfare_painters")
          )
          Tab(
            selected = selectedSubTab == 4,
            onClick = {
              selectedSubTab = 4
              selectedOccupationFilter = WorkerOccupation.ARTIST
            },
            text = { Text("ஓவியர்கள் / Artists", fontSize = 11.sp, fontWeight = if (selectedSubTab == 4) FontWeight.Black else FontWeight.Normal) },
            icon = { Icon(Icons.Default.Palette, contentDescription = null, modifier = Modifier.size(16.dp)) },
            modifier = Modifier.testTag("tab_welfare_artists")
          )
          Tab(
            selected = selectedSubTab == 5,
            onClick = { selectedSubTab = 5 },
            text = { Text("எனக்கு என்ன திட்டம்?", fontSize = 11.sp, fontWeight = if (selectedSubTab == 5) FontWeight.Black else FontWeight.Normal) },
            icon = { Icon(Icons.AutoMirrored.Filled.Help, contentDescription = null, modifier = Modifier.size(16.dp)) },
            modifier = Modifier.testTag("tab_welfare_checker")
          )
          Tab(
            selected = selectedSubTab == 6,
            onClick = { selectedSubTab = 6 },
            text = { Text("விண்ணப்ப நிலை", fontSize = 11.sp, fontWeight = if (selectedSubTab == 6) FontWeight.Black else FontWeight.Normal) },
            icon = { Icon(Icons.Default.TrackChanges, contentDescription = null, modifier = Modifier.size(16.dp)) },
            modifier = Modifier.testTag("tab_welfare_status")
          )
          Tab(
            selected = selectedSubTab == 7,
            onClick = { selectedSubTab = 7 },
            text = { Text("தேவையான ஆவணங்கள்", fontSize = 11.sp, fontWeight = if (selectedSubTab == 7) FontWeight.Black else FontWeight.Normal) },
            icon = { Icon(Icons.Default.FactCheck, contentDescription = null, modifier = Modifier.size(16.dp)) },
            modifier = Modifier.testTag("tab_welfare_documents")
          )
        }
      }

      // 3. Main Content Rendering
      if (selectedSubTab in 0..4) {
        // Occupation Filter Chips + Search + Display Mode Bar
        item {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            // Occupation Filter Chips
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 2.dp),
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              WorkerOccupation.values().forEach { occupation ->
                val isSelected = selectedOccupationFilter == occupation
                FilterChip(
                  selected = isSelected,
                  onClick = { selectedOccupationFilter = occupation },
                  label = {
                    Text(
                      text = "${occupation.iconTag} ${occupation.labelTamil}",
                      fontSize = 11.sp,
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                  },
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = TnpaRedPrimary,
                    selectedLabelColor = TnpaPureWhite,
                    containerColor = TnpaPureWhite,
                    labelColor = TnpaJetBlack
                  ),
                  border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = if (isSelected) TnpaRedPrimary else Color(0xFFCBD5E1),
                    selectedBorderColor = TnpaRedPrimary,
                    borderWidth = 1.dp
                  )
                )
              }
            }

            // Search Bar & Display Mode Toggle
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("திட்டம் / நலவாரியம் / தொகை தேட...", fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(18.dp)) },
                trailingIcon = {
                  if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                      Icon(Icons.Default.Close, contentDescription = "Clear", modifier = Modifier.size(16.dp))
                    }
                  }
                },
                singleLine = true,
                modifier = Modifier
                  .weight(1f)
                  .testTag("input_search_welfare"),
                shape = RoundedCornerShape(10.dp)
              )

              // Display Area Mode Toggle Button
              Button(
                onClick = { isExpandedViewMode = !isExpandedViewMode },
                colors = ButtonDefaults.buttonColors(
                  containerColor = if (isExpandedViewMode) TnpaRedPrimary else TnpaJetBlack
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                  .height(52.dp)
                  .testTag("btn_toggle_view_mode"),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
              ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Icon(
                    if (isExpandedViewMode) Icons.Default.FactCheck else Icons.Default.Assignment,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = TnpaPureWhite
                  )
                  Text(
                    text = if (isExpandedViewMode) "முழு பார்வை" else "சுருக்க பார்வை",
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = TnpaPureWhite
                  )
                }
              }
            }

            // Summary Header with Expand All / Collapse All option
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "காட்டப்படும் அரசு திட்டங்கள்: ${displayedSchemes.size}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = TnpaJetBlack
              )

              Text(
                text = "✨ விரிவான டிஸ்ப்ளே முறையில் உள்ளது",
                fontSize = 10.5.sp,
                color = TnpaGreen,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        if (displayedSchemes.isEmpty()) {
          item {
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = TnpaPureWhite)
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Icon(Icons.Default.FindInPage, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(40.dp))
                Text(
                  text = "தேடலுக்குரிய நலத்திட்டங்கள் கிடைக்கவில்லை",
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  color = TnpaJetBlack
                )
                Text(
                  text = "வேறு தொழில் அல்லது தேடல் வார்த்தையை மாற்றி முயற்சிக்கவும்.",
                  fontSize = 11.sp,
                  color = Color.Gray
                )
                Button(
                  onClick = {
                    selectedOccupationFilter = WorkerOccupation.ALL
                    searchQuery = ""
                  },
                  colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary)
                ) {
                  Text("அனைத்து திட்டங்களையும் காட்டு", fontSize = 11.sp)
                }
              }
            }
          }
        } else {
          items(displayedSchemes, key = { it.id }) { scheme ->
            val isSchemeExpanded = isExpandedViewMode || expandedSchemeIds.contains(scheme.id)
            Box(modifier = Modifier.padding(horizontal = 10.dp)) {
              WelfareSchemeCard(
                scheme = scheme,
                isExpanded = isSchemeExpanded,
                onToggleExpand = {
                  expandedSchemeIds = if (expandedSchemeIds.contains(scheme.id)) {
                    expandedSchemeIds - scheme.id
                  } else {
                    expandedSchemeIds + scheme.id
                  }
                },
                onApplyClick = {
                  AdminApprovalRepository.submitWelfareApplication(
                    schemeId = scheme.id,
                    schemeTitleTamil = scheme.titleTamil,
                    govtTypeLabel = scheme.govtType.labelTamil,
                    memberId = "TNPA-APP-" + (1000..9999).random(),
                    applicantName = "விண்ணப்பதாரர்",
                    mobile = "9842100000",
                    district = "திருச்சிராப்பள்ளி (Tiruchirappalli)",
                    occupation = scheme.applicableOccupations.firstOrNull()?.labelTamil ?: "பெயிண்டர்",
                    monthlyIncome = 12000,
                    officialPortalUrl = scheme.officialApplyUrl,
                    officialPortalName = scheme.officialPortalName
                  )
                  openOfficialGovtUrl(scheme.officialApplyUrl, scheme.officialPortalName)
                },
                onStatusClick = { openOfficialGovtUrl(scheme.officialStatusUrl, scheme.officialPortalName) },
                onShareClick = { shareSchemeDetails(scheme) },
                onViewFullDetails = { selectedSchemeForDetail = scheme }
              )
            }
          }
        }

        item {
          Spacer(modifier = Modifier.height(40.dp))
        }
      } else if (selectedSubTab == 5) {
        item {
          EligibilityCheckerSection(
            checkOccupation = checkOccupation,
            onOccupationChange = { checkOccupation = it },
            checkAgeText = checkAgeText,
            onAgeChange = { checkAgeText = it },
            checkDistrict = checkDistrict,
            onDistrictChange = { checkDistrict = it },
            checkIncomeText = checkIncomeText,
            onIncomeChange = { checkIncomeText = it },
            checkHasTnCard = checkHasTnCard,
            onHasTnCardChange = { checkHasTnCard = it },
            checkHasEShram = checkHasEShram,
            onHasEShramChange = { checkHasEShram = it },
            result = eligibilityEvaluationResult,
            onEvaluate = {
              val age = checkAgeText.toIntOrNull() ?: 30
              val income = checkIncomeText.toIntOrNull() ?: 12000
              eligibilityEvaluationResult = GovtWelfareRepository.evaluateEligibility(
                occupation = checkOccupation,
                age = age,
                monthlyIncome = income,
                hasTnWelfareCard = checkHasTnCard,
                hasEShram = checkHasEShram
              )
            },
            onApplyScheme = { scheme ->
              openOfficialGovtUrl(scheme.officialApplyUrl, scheme.officialPortalName)
            }
          )
        }
      } else if (selectedSubTab == 6) {
        item {
          ApplicationStatusTrackerSection(
            onOpenTnuwwb = {
              openOfficialGovtUrl("https://tnuwwb.tn.gov.in/portal/", "TNUWWB Portal")
            },
            onOpenEShram = {
              openOfficialGovtUrl("https://register.eshram.gov.in/#/user/self-registration", "e-Shram Portal")
            },
            onOpenMaanDhan = {
              openOfficialGovtUrl("https://maandhan.in/", "PM-SYM Maandhan Portal")
            }
          )
        }
      } else if (selectedSubTab == 7) {
        item {
          DocumentsChecklistSection(
            onApplyClick = {
              selectedSubTab = 0
            }
          )
        }
      }
    }
  }

  // Scheme Full Detail Modal
  if (selectedSchemeForDetail != null) {
    val scheme = selectedSchemeForDetail!!
    AlertDialog(
      onDismissRequest = { selectedSchemeForDetail = null },
      title = {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.AccountBalance, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = scheme.titleTamil,
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = TnpaJetBlack
            )
          }
          IconButton(onClick = { selectedSchemeForDetail = null }, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Close")
          }
        }
      },
      text = {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Govt Badge
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(if (scheme.govtType == GovtType.TAMIL_NADU) TnpaRedPrimary else TnpaNavy)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = scheme.govtType.labelTamil,
                color = TnpaPureWhite,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Text(
              text = "சரிபார்க்கப்பட்டது: ${scheme.lastVerifiedDate}",
              fontSize = 10.sp,
              color = TnpaGreen,
              fontWeight = FontWeight.Bold
            )
          }

          Text(
            text = "அரசுத் துறை / வாரியம்: ${scheme.boardDepartment}",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = TnpaCharcoal
          )

          // Highlight box
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
            border = BorderStroke(1.dp, TnpaGold),
            shape = RoundedCornerShape(8.dp)
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text("முக்கிய நிதி பயன் (Key Benefit):", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
              Text(
                text = scheme.highlightAmount,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = TnpaRedDark
              )
            }
          }

          // Eligibility criteria
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            shape = RoundedCornerShape(8.dp)
          ) {
            Column(
              modifier = Modifier.padding(10.dp),
              verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Text("📋 தகுதி வரம்புகள் (Eligibility):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
              Text("• வயது: ${scheme.ageRange}", fontSize = 11.sp, color = TnpaCharcoal)
              Text("• வருமான வரம்பு: ${scheme.incomeCriteria}", fontSize = 11.sp, color = TnpaCharcoal)
              Text("• பதிவு வகை: ${scheme.requiredRegistration}", fontSize = 11.sp, color = TnpaCharcoal)
            }
          }

          // Verified Benefits
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            shape = RoundedCornerShape(8.dp)
          ) {
            Column(
              modifier = Modifier.padding(10.dp),
              verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Text("🎁 உறுதிப்படுத்தப்பட்ட அரசு பலன்கள்:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaGreen)
              scheme.verifiedBenefits.forEach { benefit ->
                Text(
                  text = benefit,
                  fontSize = 11.sp,
                  color = TnpaCharcoal,
                  lineHeight = 16.sp
                )
              }
            }
          }

          // Required Documents
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            shape = RoundedCornerShape(8.dp)
          ) {
            Column(
              modifier = Modifier.padding(10.dp),
              verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Text("📑 தேவையான ஆவணங்கள்:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
              scheme.requiredDocuments.forEach { doc ->
                Text("✔️ $doc", fontSize = 11.sp, color = TnpaCharcoal)
              }
            }
          }

          // How to Apply
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            shape = RoundedCornerShape(8.dp)
          ) {
            Column(
              modifier = Modifier.padding(10.dp),
              verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Text("🚀 விண்ணப்பிக்கும் முறை:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
              scheme.howToApplySteps.forEach { step ->
                Text(step, fontSize = 11.sp, color = TnpaCharcoal)
              }
            }
          }

          // Disclaimer
          Text(
            text = "⚠️ ${scheme.officialDisclaimer}",
            fontSize = 9.sp,
            color = Color.Gray,
            lineHeight = 13.sp
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            openOfficialGovtUrl(scheme.officialApplyUrl, scheme.officialPortalName)
            selectedSchemeForDetail = null
          },
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
          shape = RoundedCornerShape(8.dp)
        ) {
          Icon(Icons.Default.Launch, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("அரசு தளத்தில் விண்ணப்பிக்கவும்", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        OutlinedButton(
          onClick = { selectedSchemeForDetail = null },
          shape = RoundedCornerShape(8.dp)
        ) {
          Text("மூடுக", fontSize = 11.sp)
        }
      }
    )
  }
}

// ------------------------------------------------------------------------------------------------
// COMPONENT 1: Scheme Card (Spacious Large Display with Full Inline Information)
// ------------------------------------------------------------------------------------------------
@Composable
fun WelfareSchemeCard(
  scheme: GovtWelfareScheme,
  isExpanded: Boolean = true,
  onToggleExpand: () -> Unit = {},
  onApplyClick: () -> Unit,
  onStatusClick: () -> Unit,
  onShareClick: () -> Unit,
  onViewFullDetails: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("card_scheme_${scheme.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = BorderStroke(1.5.dp, if (scheme.govtType == GovtType.TAMIL_NADU) TnpaRedSoft else Color(0xFFCBD5E1)),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // Top row: Govt Badge & Tag & Expand Toggle & Share
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(if (scheme.govtType == GovtType.TAMIL_NADU) TnpaRedPrimary else TnpaNavy)
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = scheme.govtType.labelTamil,
              color = TnpaPureWhite,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(Color(0xFFF1F5F9))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = scheme.tag,
              color = TnpaCharcoal,
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(
            onClick = onShareClick,
            modifier = Modifier.size(30.dp)
          ) {
            Icon(Icons.Default.Share, contentDescription = "Share", tint = TnpaRedDark, modifier = Modifier.size(18.dp))
          }
          IconButton(
            onClick = onToggleExpand,
            modifier = Modifier.size(30.dp)
          ) {
            Text(
              text = if (isExpanded) "▲" else "▼",
              color = TnpaCharcoal,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      // Scheme Large Title
      Text(
        text = scheme.titleTamil,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Black,
        color = TnpaJetBlack,
        fontSize = 15.sp,
        lineHeight = 20.sp
      )

      Text(
        text = "🏛️ ${scheme.boardDepartment}",
        fontSize = 11.5.sp,
        color = TnpaRedDark,
        fontWeight = FontWeight.Bold
      )

      // Key highlight amount box (Large Golden Card)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
        border = BorderStroke(1.2.dp, TnpaGold)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "முக்கிய அரசு உதவித் தொகை:",
                fontSize = 9.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = TnpaCharcoal
              )
              Text(
                text = scheme.highlightAmount,
                color = TnpaJetBlack,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black
              )
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(if (scheme.isFeeFree) TnpaGreen.copy(alpha = 0.15f) else TnpaRedSoft)
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = if (scheme.isFeeFree) "இலவச அரசு திட்டம்" else "அரசு கட்டணம்",
              color = if (scheme.isFeeFree) TnpaGreen else TnpaRedDark,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Text(
        text = scheme.shortDescription,
        fontSize = 12.sp,
        color = TnpaCharcoal,
        lineHeight = 17.sp
      )

      // Expanded Details View (Displaying Full Information on Screen)
      if (isExpanded) {
        // Verified Benefits List
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
          border = BorderStroke(1.dp, Color(0xFFBBF7D0))
        ) {
          Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TnpaGreen, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "🎁 அரசு வழங்கும் உறுதிப்படுத்தப்பட்ட பலன்கள்:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TnpaGreen
              )
            }
            scheme.verifiedBenefits.forEach { benefit ->
              Text(
                text = "• $benefit",
                fontSize = 11.5.sp,
                color = TnpaJetBlack,
                lineHeight = 16.sp
              )
            }
          }
        }

        // Eligibility Criteria
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(6.dp))
              .background(Color(0xFFF8FAFC))
              .border(0.5.dp, Color(0xFFCBD5E1), RoundedCornerShape(6.dp))
              .padding(horizontal = 8.dp, vertical = 6.dp)
          ) {
            Column {
              Text(text = "🎂 வயது வரம்பு", fontSize = 9.sp, color = TnpaCharcoal, fontWeight = FontWeight.Bold)
              Text(text = scheme.ageRange, fontSize = 11.sp, color = TnpaJetBlack, fontWeight = FontWeight.Black)
            }
          }

          Box(
            modifier = Modifier
              .weight(1.2f)
              .clip(RoundedCornerShape(6.dp))
              .background(Color(0xFFF8FAFC))
              .border(0.5.dp, Color(0xFFCBD5E1), RoundedCornerShape(6.dp))
              .padding(horizontal = 8.dp, vertical = 6.dp)
          ) {
            Column {
              Text(text = "🆔 பதிவு தகுதி", fontSize = 9.sp, color = TnpaCharcoal, fontWeight = FontWeight.Bold)
              Text(text = scheme.requiredRegistration, fontSize = 10.5.sp, color = TnpaJetBlack, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
          }
        }

        // Required Documents Section
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(8.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
          border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
          Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("📑 தேவையான ஆவணங்கள்:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
            scheme.requiredDocuments.take(3).forEach { doc ->
              Text("✔️ $doc", fontSize = 10.5.sp, color = TnpaCharcoal)
            }
            if (scheme.requiredDocuments.size > 3) {
              Text("... மற்றும் பிற ஆவணங்கள்", fontSize = 10.sp, color = Color.Gray)
            }
          }
        }
      } else {
        // Quick Eligibility Pills for compact mode
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(Color(0xFFF8FAFC))
              .border(0.5.dp, Color(0xFFCBD5E1), RoundedCornerShape(4.dp))
              .padding(horizontal = 6.dp, vertical = 3.dp)
          ) {
            Text(text = "🎂 ${scheme.ageRange}", fontSize = 9.5.sp, color = TnpaCharcoal, fontWeight = FontWeight.SemiBold)
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(Color(0xFFF8FAFC))
              .border(0.5.dp, Color(0xFFCBD5E1), RoundedCornerShape(4.dp))
              .padding(horizontal = 6.dp, vertical = 3.dp)
          ) {
            Text(text = "🆔 ${scheme.requiredRegistration}", fontSize = 9.5.sp, color = TnpaCharcoal, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
          }
        }
      }

      HorizontalDivider(color = Color(0xFFF1F5F9))

      // Action Buttons Row: Official Direct Apply, Status, Details
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Direct Official Apply Button
        Button(
          onClick = onApplyClick,
          modifier = Modifier
            .weight(1.4f)
            .height(42.dp)
            .testTag("btn_apply_${scheme.id}"),
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
          shape = RoundedCornerShape(10.dp),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
        ) {
          Icon(Icons.Default.Launch, contentDescription = null, tint = TnpaPureWhite, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "விண்ணப்பிக்க (Apply)",
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Bold,
            color = TnpaPureWhite
          )
        }

        // Official Status Button
        OutlinedButton(
          onClick = onStatusClick,
          modifier = Modifier
            .weight(1f)
            .height(42.dp)
            .testTag("btn_status_${scheme.id}"),
          shape = RoundedCornerShape(10.dp),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp)
        ) {
          Text(
            text = "நிலை (Status)",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TnpaJetBlack
          )
        }

        // View Full Details Link
        TextButton(
          onClick = onViewFullDetails,
          modifier = Modifier.height(42.dp)
        ) {
          Text("விவரம் >", fontSize = 11.5.sp, color = TnpaRedDark, fontWeight = FontWeight.Bold)
        }
      }

      // Official portal source tagline
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "அரசு தளம்: ${scheme.officialPortalName}",
          fontSize = 9.5.sp,
          color = Color.Gray,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Text(
          text = "சரிபார்க்கப்பட்டது: ${scheme.lastVerifiedDate}",
          fontSize = 9.5.sp,
          color = TnpaGreen,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}

// ------------------------------------------------------------------------------------------------
// COMPONENT 2: Interactive Eligibility Checker
// ------------------------------------------------------------------------------------------------
@Composable
fun EligibilityCheckerSection(
  checkOccupation: WorkerOccupation,
  onOccupationChange: (WorkerOccupation) -> Unit,
  checkAgeText: String,
  onAgeChange: (String) -> Unit,
  checkDistrict: String,
  onDistrictChange: (String) -> Unit,
  checkIncomeText: String,
  onIncomeChange: (String) -> Unit,
  checkHasTnCard: Boolean,
  onHasTnCardChange: (Boolean) -> Unit,
  checkHasEShram: Boolean,
  onHasEShramChange: (Boolean) -> Unit,
  result: GovtWelfareRepository.EligibilityResult?,
  onEvaluate: () -> Unit,
  onApplyScheme: (GovtWelfareScheme) -> Unit
) {
  val districts = listOf(
    "சென்னை (Chennai)", "கோயம்புத்தூர் (Coimbatore)", "மதுரை (Madurai)",
    "திருச்சிராப்பள்ளி (Tiruchirappalli)", "சேலம் (Salem)", "திருநெல்வேலி (Tirunelveli)",
    "ஈரோடு (Erode)", "வேலூர் (Vellore)", "தூத்துக்குடி (Thoothukudi)",
    "திண்டுக்கல் (Dindigul)", "தஞ்சாவூர் (Thanjavur)", "காஞ்சிபுரம் (Kanchipuram)",
    "விழுப்புரம் (Villupuram)", "கன்னியாகுமரி (Kanyakumari)", "அனைத்து மாவட்டங்கள் (All Districts)"
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      border = BorderStroke(1.dp, TnpaRedSoft)
    ) {
      Column(
        modifier = Modifier.padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.AutoMirrored.Filled.Help, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(22.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "எனக்கு என்ன நலத்திட்டம் கிடைக்கும்?",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Black,
              color = TnpaJetBlack
            )
            Text(
              text = "தொழில், வயது மற்றும் விவரங்களின்படி பொருத்தமான அரசு திட்டங்களைக் கண்டறியவும்",
              fontSize = 11.sp,
              color = Color.Gray
            )
          }
        }

        HorizontalDivider(color = Color(0xFFF1F5F9))

        // 1. Occupation Selection
        Text(text = "1. உங்கள் முதன்மைத் தொழில் என்ன? *", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          listOf(
            WorkerOccupation.PAINTER,
            WorkerOccupation.ARTIST,
            WorkerOccupation.CONSTRUCTION,
            WorkerOccupation.DECORATOR,
            WorkerOccupation.UNORGANISED
          ).forEach { occ ->
            val isSelected = checkOccupation == occ
            FilterChip(
              selected = isSelected,
              onClick = { onOccupationChange(occ) },
              label = { Text("${occ.iconTag} ${occ.labelTamil}", fontSize = 10.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = TnpaRedPrimary,
                selectedLabelColor = TnpaPureWhite
              )
            )
          }
        }

        // 2. Age Input
        Text(text = "2. உங்கள் வயது (Age in Years) *", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
        OutlinedTextField(
          value = checkAgeText,
          onValueChange = { if (it.length <= 2 && it.all { c -> c.isDigit() }) onAgeChange(it) },
          label = { Text("வயது (16 - 70)") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_check_age"),
          shape = RoundedCornerShape(8.dp)
        )

        // 3. Monthly Income
        Text(text = "3. தோராய மாதாந்திர வருமானம் (Monthly Income in ₹)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
        OutlinedTextField(
          value = checkIncomeText,
          onValueChange = { if (it.length <= 6 && it.all { c -> c.isDigit() }) onIncomeChange(it) },
          label = { Text("ரூபாய் (உதா: ₹12,000)") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_check_income"),
          shape = RoundedCornerShape(8.dp)
        )

        // 4. Existing Registrations Checklist
        Text(text = "4. ஏற்கனவே உள்ள அரசுப் பதிவுகள் (இருந்தால் தேர்வு செய்க):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
          shape = RoundedCornerShape(8.dp)
        ) {
          Column(modifier = Modifier.padding(8.dp)) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable { onHasTnCardChange(!checkHasTnCard) }
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              RadioButton(
                selected = checkHasTnCard,
                onClick = { onHasTnCardChange(!checkHasTnCard) },
                colors = RadioButtonDefaults.colors(selectedColor = TnpaRedPrimary)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("தமிழ்நாடு நலவாரிய அட்டை (TNUWWB Member ID) உள்ளது", fontSize = 11.sp, color = TnpaJetBlack)
            }

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable { onHasEShramChange(!checkHasEShram) }
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              RadioButton(
                selected = checkHasEShram,
                onClick = { onHasEShramChange(!checkHasEShram) },
                colors = RadioButtonDefaults.colors(selectedColor = TnpaNavy)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("மத்திய அரசு e-Shram 12 இலக்க அட்டை உள்ளது", fontSize = 11.sp, color = TnpaJetBlack)
            }
          }
        }

        // Evaluate Button
        Button(
          onClick = onEvaluate,
          modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .testTag("btn_evaluate_eligibility"),
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
          shape = RoundedCornerShape(8.dp)
        ) {
          Icon(Icons.Default.FactCheck, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("🔍 எனது தகுதியைச் சரிபார்க்க (Check Eligibility)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    // Results Box
    if (result != null) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("card_eligibility_result"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
        border = BorderStroke(2.dp, Color(result.status.badgeColorHex))
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
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(Color(result.status.badgeColorHex))
                .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text(
                text = result.status.labelTamil,
                color = TnpaPureWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black
              )
            }

            Text(
              text = "${result.matchedSchemes.size} பொருத்தமான திட்டங்கள்",
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
              color = TnpaCharcoal
            )
          }

          Text(
            text = result.explanationTamil,
            fontSize = 12.sp,
            color = TnpaJetBlack,
            fontWeight = FontWeight.SemiBold
          )

          // Strict Safety Disclaimer Requirement
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
            border = BorderStroke(1.dp, TnpaRedSoft),
            shape = RoundedCornerShape(8.dp)
          ) {
            Row(
              modifier = Modifier.padding(8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.Info, contentDescription = null, tint = TnpaRedDark, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = result.guidanceTamil,
                fontSize = 10.sp,
                color = TnpaRedDark,
                lineHeight = 14.sp
              )
            }
          }

          Text(
            text = "பரிந்துரைக்கப்படும் நேரடி அரசு திட்டங்கள்:",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = TnpaJetBlack
          )

          result.matchedSchemes.forEach { scheme ->
            Card(
              modifier = Modifier.fillMaxWidth(),
              colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
              border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
              shape = RoundedCornerShape(8.dp)
            ) {
              Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = scheme.titleTamil,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = TnpaJetBlack,
                    modifier = Modifier.weight(1f)
                  )
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(4.dp))
                      .background(if (scheme.govtType == GovtType.TAMIL_NADU) TnpaRedPrimary else TnpaNavy)
                      .padding(horizontal = 6.dp, vertical = 2.dp)
                  ) {
                    Text(scheme.govtType.labelTamil, color = TnpaPureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                  }
                }

                Text(text = "💰 ${scheme.highlightAmount}", fontSize = 11.sp, color = TnpaRedDark, fontWeight = FontWeight.Bold)

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.End
                ) {
                  Button(
                    onClick = { onApplyScheme(scheme) },
                    colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp)
                  ) {
                    Icon(Icons.Default.Launch, contentDescription = null, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("அரசு போர்ட்டலில் விண்ணப்பிக்க", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                  }
                }
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(30.dp))
  }
}

// ------------------------------------------------------------------------------------------------
// COMPONENT 3: Application Status Tracker Guide
// ------------------------------------------------------------------------------------------------
@Composable
fun ApplicationStatusTrackerSection(
  onOpenTnuwwb: () -> Unit,
  onOpenEShram: () -> Unit,
  onOpenMaanDhan: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      border = BorderStroke(1.dp, TnpaRedSoft)
    ) {
      Column(
        modifier = Modifier.padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.TrackChanges, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(24.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "அரசு விண்ணப்ப நிலை அறிதல் (Application Status)",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Black,
              color = TnpaJetBlack
            )
            Text(
              text = "அதிகாரப்பூர்வ அரசு தளங்களில் உங்கள் விண்ணப்பத்தின் நிலையை உடனுக்குடன் அறியலாம்",
              fontSize = 11.sp,
              color = Color.Gray
            )
          }
        }

        HorizontalDivider(color = Color(0xFFF1F5F9))

        // 1. TNUWWB Status Guide & Launcher
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
          border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
          Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "1. தமிழ்நாடு நலவாரிய விண்ணப்ப நிலை (TNUWWB)",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = TnpaJetBlack
              )
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(TnpaRedPrimary)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text("TN Govt", color = TnpaPureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
              }
            }

            Text(
              text = "• புதிய பதிவு நிலை (New Registration Status)\n• கோரிக்கை நிதி உதவி நிலை (Claim Status)\n• புதுப்பித்தல் நிலை (Renewal Status)",
              fontSize = 11.sp,
              color = TnpaCharcoal,
              lineHeight = 16.sp
            )

            Text(
              text = "📌 தேவைப்படுவது: விண்ணப்ப எண் (Application No) அல்லது பதிவுசெய்த கைபேசி எண்.",
              fontSize = 10.sp,
              color = TnpaRedDark,
              fontWeight = FontWeight.SemiBold
            )

            Button(
              onClick = onOpenTnuwwb,
              colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth().testTag("btn_track_tnuwwb")
            ) {
              Icon(Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("TNUWWB போர்ட்டலில் நிலையை பார்க்கவும்", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }

        // 2. e-Shram Status Guide & Launcher
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
          border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
          Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "2. மத்திய அரசு e-Shram UAN அட்டை நிலை / புதுப்பித்தல்",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = TnpaJetBlack
              )
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(TnpaNavy)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text("Central Govt", color = TnpaPureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
              }
            }

            Text(
              text = "• UAN அட்டை பதிவிறக்கம் (Download UAN Card)\n• சுயவிவரம் புதுப்பித்தல் (Update Profile / Bank Account)\n• e-Shram பதிவு நிலை சரிபார்த்தல்",
              fontSize = 11.sp,
              color = TnpaCharcoal,
              lineHeight = 16.sp
            )

            Text(
              text = "📌 தேவைப்படுவது: ஆதார் எண் மற்றும் ஆதார் இணைக்கப்பட்ட மொபைல் OTP.",
              fontSize = 10.sp,
              color = TnpaNavy,
              fontWeight = FontWeight.SemiBold
            )

            Button(
              onClick = onOpenEShram,
              colors = ButtonDefaults.buttonColors(containerColor = TnpaNavy),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth().testTag("btn_track_eshram")
            ) {
              Icon(Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("e-Shram போர்ட்டலில் அட்டை / நிலை பெறுக", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }

        // 3. PM-SYM Status
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
          border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
          Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = "3. PM-SYM ஓய்வூதிய அட்டை & சந்தா நிலை",
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              color = TnpaJetBlack
            )
            Text(
              text = "மாதாந்திர சந்தா பற்று விவரம் மற்றும் ஓய்வூதிய அட்டை நகல் பெற maandhan.in போர்ட்டலை அணுகலாம்.",
              fontSize = 11.sp,
              color = TnpaCharcoal
            )
            OutlinedButton(
              onClick = onOpenMaanDhan,
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth().testTag("btn_track_maandhan")
            ) {
              Text("PM-SYM போர்ட்டலுக்கு செல்க", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(30.dp))
  }
}

// ------------------------------------------------------------------------------------------------
// COMPONENT 4: Documents Checklist Section
// ------------------------------------------------------------------------------------------------
@Composable
fun DocumentsChecklistSection(
  onApplyClick: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      border = BorderStroke(1.dp, TnpaRedSoft)
    ) {
      Column(
        modifier = Modifier.padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.FactCheck, contentDescription = null, tint = TnpaRedPrimary, modifier = Modifier.size(24.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "தேவையான ஆவணங்கள் சரிபார்ப்பு பட்டியல்",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Black,
              color = TnpaJetBlack
            )
            Text(
              text = "அரசு நலவாரிய விண்ணப்பத்திற்கு முன்கூட்டியே தயார் செய்ய வேண்டியவை",
              fontSize = 11.sp,
              color = Color.Gray
            )
          }
        }

        HorizontalDivider(color = Color(0xFFF1F5F9))

        val commonDocs = listOf(
          "1. ஆதார் அட்டை (Aadhaar Card) — அசல் மற்றும் நகல் (மொபைல் எண் இணைக்கப்பட்டிருக்க வேண்டும்)",
          "2. குடும்ப அட்டை / ஸ்மார்ட் ரேஷன் கார்டு (Smart Ration Card)",
          "3. தேசியமயமாக்கப்பட்ட அல்லது கூட்டுறவு வங்கி சேமிப்பு கணக்கு பாஸ்புக் (Bank Passbook with clear IFSC)",
          "4. தொழில் சான்றிதழ் (TNPA சங்க உறுப்பினர் அட்டை / கிராம நிர்வாக அலுவலர் (VAO) சான்று / ஒப்பந்ததாரர் சான்று)",
          "5. வயது சான்று (பள்ளி சான்றிதழ் / ஆதார் அட்டை / ஓட்டுநர் உரிமம் / வாக்காளர் அட்டை)",
          "6. பாஸ்போர்ட் அளவு சமீபத்திய புகைப்படங்கள் (Passport Photos - 2)",
          "7. வாரிசுதாரர் (Nominee) ஆதார் அட்டை விவரங்கள்"
        )

        commonDocs.forEach { doc ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(Color(0xFFF8FAFC))
              .padding(10.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TnpaGreen, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = doc, fontSize = 11.sp, color = TnpaJetBlack, lineHeight = 16.sp)
          }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(8.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
          border = BorderStroke(1.dp, TnpaGold)
        ) {
          Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Security, contentDescription = null, tint = TnpaGold, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "🔒 தமிழ்நாடு பெயிண்டர்கள் & ஓவியர்கள் சங்கம் (TNPA) உறுப்பினர்களுக்கு தொழில் சான்றிதழ் மற்றும் பதிவு வழிகாட்டுதல் இலவசமாக வழங்கப்படுகிறது.",
              fontSize = 11.sp,
              color = TnpaJetBlack,
              fontWeight = FontWeight.SemiBold,
              lineHeight = 15.sp
            )
          }
        }

        Button(
          onClick = onApplyClick,
          modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TnpaRedPrimary),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text("திட்டங்களைப் பார்த்து விண்ணப்பிக்கவும்", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    Spacer(modifier = Modifier.height(30.dp))
  }
}
