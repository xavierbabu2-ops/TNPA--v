package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SovereignEngine
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun ProjectInventorScreen() {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabs = listOf(
    "💡 திட்ட கண்டுபிடிப்பு (Inventor)" to Icons.Default.Lightbulb,
    "🌍 தனிநபர் உலக மாதிரி (World Model)" to Icons.Default.Public,
    "🤖 டிஜிட்டல் ட்வின் (Digital Twin)" to Icons.Default.Sensors,
    "🧩 திறன் அட்டவணை (Capabilities)" to Icons.Default.Extension,
    "🛡️ ஆதார சரிபார்ப்பு மையம்" to Icons.Default.FactCheck
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(SovereignBackground)
  ) {
    ScrollableTabRow(
      selectedTabIndex = selectedTab,
      containerColor = SovereignSurface,
      contentColor = SovereignCyan,
      edgePadding = 12.dp,
      divider = {}
    ) {
      tabs.forEachIndexed { index, (title, icon) ->
        Tab(
          selected = selectedTab == index,
          onClick = { selectedTab = index },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (selectedTab == index) SovereignCyan else SovereignTextMuted,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = title,
                color = if (selectedTab == index) SovereignTextPrimary else SovereignTextSecondary,
                fontSize = 12.sp,
                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
              )
            }
          }
        )
      }
    }

    Box(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
    ) {
      when (selectedTab) {
        0 -> ProjectInventorSubScreen()
        1 -> PersonalWorldModelSubScreen()
        2 -> DigitalTwinSubScreen()
        3 -> CapabilityCatalogSubScreen()
        4 -> EvidenceResearchSubScreen()
      }
    }
  }
}

// -------------------------------------------------------------
// 1. AI PROJECT INVENTOR
// -------------------------------------------------------------
@Composable
fun ProjectInventorSubScreen() {
  val projects by SovereignEngine.inventedProjects.collectAsState()
  var projectGoal by remember { mutableStateOf("") }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignCyan),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null, tint = SovereignCyan)
            Spacer(modifier = Modifier.width(8.dp))
            Text("💡 புதிய திட்ட கண்டுபிடிப்பாளர் (Project Inventor)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          }
          Text(text = "பிரச்சனை விளக்கம் ➔ கணினி கட்டமைப்பு ➔ BOM & செலவு (INR) ➔ புரோட்டோடைப் வரைபடம் ➔ ஃபார்ம்வேர் குறியீடு.", fontSize = 11.sp, color = SovereignTextSecondary)

          Spacer(modifier = Modifier.height(10.dp))
          Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
              value = projectGoal,
              onValueChange = { projectGoal = it },
              placeholder = { Text("உதாரணம்: IoT விவசாய மண் சென்சார் & சோலார் நீர் பாசனம்...", fontSize = 11.sp, color = SovereignTextMuted) },
              shape = RoundedCornerShape(12.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SovereignSurfaceElevated,
                unfocusedContainerColor = SovereignSurfaceElevated,
                focusedBorderColor = SovereignCyan,
                unfocusedBorderColor = SovereignBorder,
                focusedTextColor = SovereignTextPrimary,
                unfocusedTextColor = SovereignTextPrimary
              ),
              modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
              onClick = {
                if (projectGoal.isNotBlank()) {
                  SovereignEngine.sendUserPrompt("திட்டம் உருவாக்கு: $projectGoal", MultimodalInputType.TEXT)
                  projectGoal = ""
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = SovereignCyan),
              shape = RoundedCornerShape(12.dp)
            ) {
              Text("உருவாக்கு", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
          }
        }
      }
    }

    items(projects, key = { it.id }) { p ->
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(text = p.titleTa, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          Text(text = p.problemStatement, fontSize = 11.sp, color = SovereignTextSecondary)

          Spacer(modifier = Modifier.height(10.dp))
          Text(text = "🏗️ கணினி கட்டமைப்பு:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignSky)
          Text(text = p.architecturalApproach, fontSize = 11.sp, color = SovereignTextPrimary)

          Spacer(modifier = Modifier.height(10.dp))
          Text(text = "📦 பாகங்களின் பட்டியல் & மதிப்பீட்டு செலவு (BOM):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignEmerald)
          p.billOfMaterials.forEach { item ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(text = "• ${item.nameTa} (x${item.quantity})", fontSize = 11.sp, color = SovereignTextPrimary)
              Text(text = "₹${item.estimatedCostInr.toInt()}", fontSize = 11.sp, color = SovereignEmerald, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(text = "📅 புரோட்டோடைப் வரைபடம் (Roadmap):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignPurple)
          p.prototypePhases.forEach { phase ->
            Text(text = "• $phase", fontSize = 11.sp, color = SovereignTextSecondary, modifier = Modifier.padding(vertical = 2.dp))
          }

          Spacer(modifier = Modifier.height(10.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = SovereignSurfaceElevated,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text(text = "💻 C++ Edge AI ஃபார்ம்வேர் துணுக்கு:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
              Text(text = p.codeSnippet, fontSize = 10.sp, color = SovereignCyan, fontFamily = FontFamily.Monospace)
            }
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 2. PERSONAL WORLD MODEL & GRAPH
// -------------------------------------------------------------
@Composable
fun PersonalWorldModelSubScreen() {
  val nodes by SovereignEngine.worldNodes.collectAsState()
  val edges by SovereignEngine.worldEdges.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignCyan),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Public, contentDescription = null, tint = SovereignCyan)
            Spacer(modifier = Modifier.width(8.dp))
            Text("🌍 Personal World Model (தனிநபர் உலக வரைபடம்)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          }
          Text(
            text = "AI உங்கள் சாதனங்கள், வழக்கங்கள், திட்டங்கள், பாதுகாப்பு விதிகள் மற்றும் உறவுகளை ஒரு முழுமையான வரைபடமாக (Semantic Graph) உணர்ந்து செயல்படுகிறது.",
            fontSize = 11.sp,
            color = SovereignTextSecondary,
            lineHeight = 16.sp
          )
        }
      }
    }

    item {
      Text(text = "📌 உலக மாதிரி முனைகள் (World Nodes):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignSky)
    }

    items(nodes, key = { it.id }) { node ->
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = SovereignSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = node.nameTa, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Surface(shape = RoundedCornerShape(4.dp), color = SovereignCyanDark) {
              Text(
                text = node.category.labelEn,
                fontSize = 9.sp,
                color = SovereignCyan,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = node.contextSnippet, fontSize = 11.sp, color = SovereignTextSecondary)
          Text(text = "இணைக்கப்பட்ட சாதனம்: ${node.boundDevice ?: "அனைத்தும்"} • பாதுகாப்பு: ${node.securityTier.labelTa}", fontSize = 9.sp, color = SovereignEmerald)
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(6.dp))
      Text(text = "🔗 முனைகளின் தொடர்பு இணைப்புகள் (Relationships):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignPurple)
    }

    items(edges, key = { "${it.sourceNodeId}->${it.targetNodeId}" }) { edge ->
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(10.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(text = "${edge.sourceNodeId} ➔ [${edge.relationshipTa}] ➔ ${edge.targetNodeId}", fontSize = 11.sp, color = SovereignTextPrimary, fontWeight = FontWeight.Medium)
          Text(text = edge.relationshipEn, fontSize = 10.sp, color = SovereignEmerald, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 3. DIGITAL TWIN SUB SCREEN
// -------------------------------------------------------------
@Composable
fun DigitalTwinSubScreen() {
  val twin by SovereignEngine.digitalTwin.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignEmerald),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Sensors, contentDescription = null, tint = SovereignEmerald)
            Spacer(modifier = Modifier.width(8.dp))
            Text("🤖 Personal Digital Twin Sandbox", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          }
          Text(text = "சாதனங்கள், கார்கள் மற்றும் மின் உபகரணங்களை நிஜத்தில் இயக்கும் முன் Virtual Sandbox-ல் பாதுகாப்பாக இயக்கி சோதிக்கும் வசதி.", fontSize = 11.sp, color = SovereignTextSecondary)
          Spacer(modifier = Modifier.height(10.dp))
          Text(text = "செயலில் உள்ள காட்சி: ${twin.activeScenario}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignSky)
          Text(text = twin.riskAssessment, fontSize = 11.sp, color = SovereignEmerald)
        }
      }
    }

    item {
      Text(text = "மெய்நிகர் சாதனங்களின் நேரலை நிலை (Virtual Sandbox):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
    }

    items(twin.devices, key = { it.id }) { dev ->
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = dev.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Surface(shape = RoundedCornerShape(4.dp), color = SovereignEmeraldDark) {
              Text(text = dev.status, fontSize = 9.sp, color = SovereignEmerald, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }
          }
          Text(text = "சாதன வகை: ${dev.type}", fontSize = 10.sp, color = SovereignTextMuted)
          Spacer(modifier = Modifier.height(6.dp))
          Text(text = "📊 டெலிமெட்ரி: ${dev.telemetryValue}", fontSize = 11.sp, color = SovereignCyan, fontFamily = FontFamily.Monospace)
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 4. CAPABILITY DISCOVERY CATALOG
// -------------------------------------------------------------
@Composable
fun CapabilityCatalogSubScreen() {
  val capabilities by SovereignEngine.capabilities.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignCyan),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(text = "🧩 Capability Discovery Catalog", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
          Text(text = "கணினியில் உள்ள அனைத்து தன்னாட்சி திறன்கள், உள்ளீட்டு/வெளியீட்டு வகைகள் மற்றும் பாதுகாப்பு அடுக்குகள்.", fontSize = 11.sp, color = SovereignTextSecondary)
        }
      }
    }

    items(capabilities, key = { it.id }) { cap ->
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = SovereignSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = cap.nameTa, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Surface(shape = RoundedCornerShape(4.dp), color = SovereignCyanDark) {
              Text(text = cap.riskTier.labelEn, fontSize = 9.sp, color = SovereignCyan, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }
          }
          Text(text = cap.descriptionTa, fontSize = 11.sp, color = SovereignTextSecondary)
          Spacer(modifier = Modifier.height(4.dp))
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "முகவர்: ${cap.mappedAgent.titleTa}", fontSize = 9.sp, color = SovereignTextMuted)
            Text(text = if (cap.isOfflineReady) "🟢 Offline Ready" else "🟡 Hybrid", fontSize = 9.sp, color = if (cap.isOfflineReady) SovereignEmerald else SovereignAmber)
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 5. EVIDENCE RESEARCH SUB SCREEN
// -------------------------------------------------------------
@Composable
fun EvidenceResearchSubScreen() {
  val researchProjects by SovereignEngine.researchProjects.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignEmerald),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.FactCheck, contentDescription = null, tint = SovereignEmerald)
            Spacer(modifier = Modifier.width(8.dp))
            Text("🛡️ Evidence-First Research & Verification", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          }
          Text(text = "தவறான தகவல்கள் இன்றி பல மூலங்களை ஆராய்ந்து ஆதாரத்தை சரிபார்க்கும் நுட்பம்.", fontSize = 11.sp, color = SovereignTextSecondary)
        }
      }
    }

    items(researchProjects, key = { it.id }) { item ->
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(item.overallConfidence.colorHex)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = item.problemTitleTa, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Surface(shape = RoundedCornerShape(4.dp), color = Color(item.overallConfidence.colorHex).copy(alpha = 0.2f)) {
              Text(text = item.overallConfidence.symbol + " " + item.overallConfidence.labelTa, fontSize = 10.sp, color = Color(item.overallConfidence.colorHex), fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(text = item.verifiedProposalTa, fontSize = 11.sp, color = SovereignTextSecondary)

          Spacer(modifier = Modifier.height(8.dp))
          Text(text = "ஆதாரங்கள்: " + item.citationSources.joinToString(", "), fontSize = 10.sp, color = SovereignSky, fontFamily = FontFamily.Monospace)
        }
      }
    }
  }
}
