package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.NextLevelIntelligenceEngine
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun NextLevelIntelligenceHub(
  onDismiss: () -> Unit = {}
) {
  val predictedIntents by NextLevelIntelligenceEngine.predictedIntents.collectAsState()
  val visualInsights by NextLevelIntelligenceEngine.visualInsights.collectAsState()
  val ambientSounds by NextLevelIntelligenceEngine.ambientSounds.collectAsState()
  val presence by NextLevelIntelligenceEngine.presenceContext.collectAsState()
  val habits by NextLevelIntelligenceEngine.habitInsights.collectAsState()
  val departureChecklist by NextLevelIntelligenceEngine.departureChecklist.collectAsState()
  val lifeCommands by NextLevelIntelligenceEngine.lifeCommands.collectAsState()
  val decisionWhyRecords by NextLevelIntelligenceEngine.decisionWhyRecords.collectAsState()
  val contradictions by NextLevelIntelligenceEngine.contradictions.collectAsState()
  val thinkSessions by NextLevelIntelligenceEngine.thinkWithMeSessions.collectAsState()
  val constitutionRules by NextLevelIntelligenceEngine.constitutionRules.collectAsState()
  val skillTree by NextLevelIntelligenceEngine.skillTree.collectAsState()
  val opportunityRadar by NextLevelIntelligenceEngine.opportunityRadar.collectAsState()
  val ideaGraveyard by NextLevelIntelligenceEngine.ideaGraveyard.collectAsState()

  var selectedTab by remember { mutableIntStateOf(0) }

  val tabs = listOf(
    "🔮 நோக்க முன்கணிப்பு" to Icons.Default.Psychology,
    "👁️ AI Eyes & Ears" to Icons.Default.Sensors,
    "🧭 பழக்கம் & மறதி ரேடார்" to Icons.Default.Checklist,
    "⚡ லைஃப் கமாண்ட்" to Icons.Default.FlashOn,
    "⚖️ முடிவு 'Why?' & விவாதம்" to Icons.Default.Balance,
    "📜 AI அரசியல் சாசனம்" to Icons.Default.Gavel,
    "🌳 ஸ்கில் ட்ரீ & வாய்ப்புகள்" to Icons.Default.AccountTree
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(SovereignBackground)
  ) {
    // Header
    Surface(
      color = SovereignSurface,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(30.dp)
              .clip(CircleShape)
              .background(Brush.linearGradient(listOf(SovereignCyan, SovereignPurple))),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text("அடுத்த தலைமுறை தனிநபர் நுண்ணறிவு மையம்", fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Text("Intent Prediction • AI Eyes/Ears • Life Commands • Personal Constitution", fontSize = 9.5.sp, color = SovereignTextMuted)
          }
        }
        IconButton(onClick = onDismiss) {
          Icon(Icons.Default.Close, contentDescription = "Close", tint = SovereignTextSecondary)
        }
      }
    }

    // Scrollable Tabs
    ScrollableTabRow(
      selectedTabIndex = selectedTab,
      containerColor = SovereignSurfaceDark,
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
              Icon(icon, contentDescription = null, modifier = Modifier.size(14.dp), tint = if (selectedTab == index) SovereignCyan else SovereignTextMuted)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                title,
                fontSize = 11.sp,
                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                color = if (selectedTab == index) SovereignTextPrimary else SovereignTextSecondary
              )
            }
          }
        )
      }
    }

    // Tab Body
    Box(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
    ) {
      when (selectedTab) {
        0 -> IntentPredictionSection(intents = predictedIntents)
        1 -> VisionAndAudioSection(insights = visualInsights, sounds = ambientSounds, presence = presence)
        2 -> HabitAndDepartureSection(habits = habits, checklist = departureChecklist)
        3 -> LifeCommandSection(commands = lifeCommands)
        4 -> DecisionWhyAndDebateSection(decisions = decisionWhyRecords, contradictions = contradictions, thinkSessions = thinkSessions)
        5 -> ConstitutionSection(rules = constitutionRules)
        6 -> SkillTreeAndOpportunitySection(skills = skillTree, opportunities = opportunityRadar, graveyard = ideaGraveyard)
      }
    }
  }
}

// ----------------------------------------------------------------------------
// 1. INTENT PREDICTION SECTION
// ----------------------------------------------------------------------------
@Composable
private fun IntentPredictionSection(
  intents: List<PredictedIntentSuggestion>
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Text(
        "🔮 நோக்க முன்கணிப்பு என்ஜின் (Intent Prediction Engine)",
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = SovereignCyan
      )
      Text(
        "நீங்கள் முழு வாக்கியத்தை முடிக்கும் முன்பே, நேரம், காலண்டர், இருப்பிடம் மற்றும் பழக்கங்கள் அடிப்படையில் துல்லியமாக யூகித்து உதவும்.",
        fontSize = 10.5.sp,
        color = SovereignTextMuted
      )
    }

    items(intents) { item ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("கண்டறியப்பட்ட சூழல் (Context Trigger):", fontSize = 10.5.sp, color = SovereignGold, fontWeight = FontWeight.Bold)
            Surface(
              color = SovereignCyanDark.copy(alpha = 0.4f),
              shape = RoundedCornerShape(6.dp)
            ) {
              Text("துல்லியம்: ${(item.confidenceScore * 100).toInt()}%", fontSize = 9.sp, color = SovereignCyan, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }
          }

          Text(item.triggerContext, fontSize = 11.5.sp, color = SovereignTextSecondary)

          Spacer(modifier = Modifier.height(8.dp))
          Surface(
            color = SovereignSurfaceHover,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text("முன்கணிக்கப்பட்ட நோக்கம் (Predicted Intent):", fontSize = 10.sp, color = SovereignTextMuted)
              Text(item.predictedIntentTa, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
              Spacer(modifier = Modifier.height(4.dp))
              Text("🤖 பரிந்துரைக்கப்படும் உதவி: ${item.suggestedActionTa}", fontSize = 11.5.sp, color = SovereignCyan)
            }
          }
        }
      }
    }
  }
}

// ----------------------------------------------------------------------------
// 2. AI EYES & EARS SECTION
// ----------------------------------------------------------------------------
@Composable
private fun VisionAndAudioSection(
  insights: List<VisualSceneInsight>,
  sounds: List<DetectedAmbientSound>,
  presence: PresenceContext
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      // Presence status
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(10.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text("தற்போதைய இருப்பிடம் & சூழல்:", fontSize = 10.sp, color = SovereignTextMuted)
            Text(presence.primaryLocationLabel, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Text("ஒலி அளவு: ${presence.acousticNoiseLevel} • வெளிச்சம்: ${presence.ambientLightingState}", fontSize = 9.5.sp, color = SovereignTextSecondary)
          }
          Icon(Icons.Default.Sensors, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(24.dp))
        }
      }
    }

    item {
      Text("👁️ AI Eyes - கேமரா நேரலை பழுது ஆய்வு (Visual Diagnostics)", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
    }

    items(insights) { ins ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(ins.sceneTitleTa, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          Spacer(modifier = Modifier.height(4.dp))
          Text("கண்டறியப்பட்ட பாகங்கள்: ${ins.detectedComponents.joinToString(", ")}", fontSize = 10.5.sp, color = SovereignTextMuted)

          Spacer(modifier = Modifier.height(8.dp))
          Surface(
            color = SovereignSurfaceHover,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text("காரணம் (Root Cause Diagnosis):", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = SovereignGold)
              Text(ins.rootCauseDiagnosisTa, fontSize = 11.5.sp, color = SovereignTextPrimary)
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text("பழுதுநீக்கும் படிகள் (AR Guided Repair):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          ins.repairStepsTa.forEach { step ->
            Text("• $step", fontSize = 11.sp, color = SovereignTextSecondary)
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(6.dp))
      Text("👂 AI Ears - 360° சுற்றுச்சூழல் ஒலி விழிப்புணர்வு (Sound Awareness)", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
    }

    items(sounds) { snd ->
      Surface(
        color = SovereignSurfaceDark,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(Color(snd.soundType.colorHex).copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.VolumeUp, contentDescription = null, tint = Color(snd.soundType.colorHex), modifier = Modifier.size(16.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column(modifier = Modifier.weight(1f)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(snd.soundType.labelTa, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
              Text(snd.timestamp, fontSize = 9.sp, color = SovereignTextMuted)
            }
            Text(snd.contextAdviceTa, fontSize = 10.5.sp, color = SovereignTextSecondary)
          }
        }
      }
    }
  }
}

// ----------------------------------------------------------------------------
// 3. HABIT & DEPARTURE FORGETFULNESS RADAR
// ----------------------------------------------------------------------------
@Composable
private fun HabitAndDepartureSection(
  habits: List<PersonalHabitInsight>,
  checklist: List<DepartureChecklistItem>
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Text("🧭 \"நான் எதையாவது மறக்கிறேனா?\" புறப்பாடு ரேடார் (Departure Radar)", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
      Text("வானிலை, சந்திப்புகள் மற்றும் முக்கிய ஆவணங்கள் அடிப்படையில் ஆட்டோ-செக்லிஸ்ட்.", fontSize = 10.5.sp, color = SovereignTextMuted)
    }

    items(checklist) { item ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { NextLevelIntelligenceEngine.toggleDepartureItem(item.id) }
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Checkbox(
            checked = item.isChecked,
            onCheckedChange = { NextLevelIntelligenceEngine.toggleDepartureItem(item.id) },
            colors = CheckboxDefaults.colors(checkedColor = SovereignCyan)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(item.itemNameTa, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (item.isChecked) SovereignTextMuted else SovereignTextPrimary)
            Text(item.reasonTa, fontSize = 10.5.sp, color = SovereignTextSecondary)
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(6.dp))
      Text("🧠 பழக்க நுண்ணறிவு & தானியங்கி ஷார்ட்கட்கள் (Habit Intelligence)", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
    }

    items(habits) { habit ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text(habit.patternNameTa, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          Text("நிகழ்வு: ${habit.frequencyDescription}", fontSize = 10.5.sp, color = SovereignTextMuted)
          Spacer(modifier = Modifier.height(6.dp))
          Surface(
            color = SovereignSurfaceHover,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(habit.proposedShortcutTa, fontSize = 11.sp, color = SovereignCyan, modifier = Modifier.padding(8.dp))
          }
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("சேமிக்கப்படும் நேரம்: ${habit.estimatedTimeSavedMinutesPerWeek} நிமிடம்/வாரம்", fontSize = 10.sp, color = SovereignGold, fontWeight = FontWeight.Bold)
            if (!habit.isAdopted) {
              Button(
                onClick = { NextLevelIntelligenceEngine.adoptHabitShortcut(habit.id) },
                colors = ButtonDefaults.buttonColors(containerColor = SovereignCyanDark),
                shape = RoundedCornerShape(6.dp)
              ) {
                Text("ஷார்ட்கட் உருவாக்கு", fontSize = 10.5.sp, color = SovereignCyan, fontWeight = FontWeight.Bold)
              }
            } else {
              Text("✓ ஷார்ட்கட் இயங்குகிறது", fontSize = 10.sp, color = SovereignGreen, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}

// ----------------------------------------------------------------------------
// 4. LIFE COMMAND SECTION
// ----------------------------------------------------------------------------
@Composable
private fun LifeCommandSection(
  commands: List<LifeCommandWorkflow>
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Text("⚡ லைஃப் கமாண்ட் ஆர்க்கெஸ்ட்ரேஷன் (Life Command Orchestrator)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
      Text("ஒரே கட்டளையில் பல துறை பணிகளை தன்னாட்சியாக ஒருங்கிணைத்து முடிக்கும் முறை.", fontSize = 10.5.sp, color = SovereignTextMuted)
    }

    items(commands) { cmd ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(cmd.commandNameTa, fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          Text("கட்டளை: ${cmd.originalPrompt}", fontSize = 11.sp, color = SovereignCyan)

          Spacer(modifier = Modifier.height(10.dp))
          Text("செயலாக்க நிலைகள் (${cmd.currentExecutingStepIndex}/${cmd.totalStepsCount}):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignGold)

          Spacer(modifier = Modifier.height(6.dp))
          cmd.executionStepsTa.forEach { step ->
            Text(step, fontSize = 11.sp, color = SovereignTextSecondary)
          }

          Spacer(modifier = Modifier.height(10.dp))
          Button(
            onClick = { NextLevelIntelligenceEngine.executeNextLifeCommandStep(cmd.id) },
            colors = ButtonDefaults.buttonColors(containerColor = SovereignCyanDark),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("அடுத்த படிநிலையை செயல்படுத்து (Next Step)", color = SovereignCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

// ----------------------------------------------------------------------------
// 5. DECISION "WHY?" MEMORY & THINK-WITH-ME SECTION
// ----------------------------------------------------------------------------
@Composable
private fun DecisionWhyAndDebateSection(
  decisions: List<DecisionWhyRecord>,
  contradictions: List<ContradictionConflict>,
  thinkSessions: List<ThinkWithMeSession>
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Text("⚖️ 'Why?' முடிவு நினைவகம் & முரண்பாடு கண்டறிதல் (Decision Memory & Contradiction Radar)", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
    }

    // Contradictions
    items(contradictions) { conf ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, SovereignGold)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = SovereignGold, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("முரண்பாடு கண்டறியப்பட்டது (Contradiction Alert):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignGold)
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text("தற்போதைய கோரிக்கை: ${conf.currentStatementTa}", fontSize = 11.sp, color = SovereignTextPrimary)
          Text("கடந்த விருப்பம்: ${conf.pastConflictingStatementTa} (${conf.pastDateOrContext})", fontSize = 10.5.sp, color = SovereignTextMuted)
          Spacer(modifier = Modifier.height(6.dp))
          Text("AI வினா: ${conf.aiClarificationQueryTa}", fontSize = 11.5.sp, color = SovereignCyan)
        }
      }
    }

    // Decision Why Records
    items(decisions) { dec ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(dec.projectOrTopicName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          Text("தேதி: ${dec.decisionTimestamp}", fontSize = 10.sp, color = SovereignTextMuted)

          Spacer(modifier = Modifier.height(6.dp))
          Text("எடுக்கப்பட்ட முடிவு: ${dec.decisionSummaryTa}", fontSize = 11.5.sp, color = SovereignCyan, fontWeight = FontWeight.Bold)

          Spacer(modifier = Modifier.height(6.dp))
          Surface(
            color = SovereignSurfaceHover,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text("காரணம் (Why Rationale):", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = SovereignGold)
              Text(dec.whyRationaleTa, fontSize = 11.sp, color = SovereignTextSecondary)
              Spacer(modifier = Modifier.height(4.dp))
              Text("நிராகரிக்கப்பட்ட மாற்றுவழிகள்: ${dec.rejectedAlternativesTa.joinToString(", ")}", fontSize = 10.sp, color = SovereignTextMuted)
            }
          }
        }
      }
    }

    // Think-With-Me Sessions
    items(thinkSessions) { ses ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text("🤔 \"என்னுடன் சிந்தி\" & சாத்தானின் வழக்கறிஞர் பார்வை (Devil's Advocate)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignPurple)
          Text(ses.problemTitleTa, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)

          Spacer(modifier = Modifier.height(8.dp))
          Surface(
            color = SovereignSurfaceDark,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text("⚠️ சாத்தானின் வழக்கறிஞர் எச்சரிக்கை:", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = SovereignRed)
              Text(ses.devilsAdvocateCritiqueTa, fontSize = 11.sp, color = SovereignTextSecondary)
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text("💡 ஒருங்கிணைந்த சமநிலை பரிந்துரை: ${ses.synthesizedBalancedRecommendationTa}", fontSize = 11.5.sp, color = SovereignGreen, fontWeight = FontWeight.Medium)
        }
      }
    }
  }
}

// ----------------------------------------------------------------------------
// 6. PERSONAL AI CONSTITUTION SECTION
// ----------------------------------------------------------------------------
@Composable
private fun ConstitutionSection(
  rules: List<ConstitutionRule>
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Text("📜 தனிநபர் AI அரசியல் சாசனம் (Personal AI Constitution Kernel)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignGold)
      Text("எந்த மாடல் மாறினாலும் மாறாத உரிமையாளரின் நிரந்தர பாதுகாப்புக் கொள்கைகள்.", fontSize = 10.5.sp, color = SovereignTextMuted)
    }

    items(rules) { rule ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(rule.titleTa, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
            Surface(
              color = if (rule.requiresExplicitConfirmation) SovereignGold.copy(alpha = 0.2f) else SovereignGreen.copy(alpha = 0.2f),
              shape = RoundedCornerShape(6.dp)
            ) {
              Text(
                if (rule.requiresExplicitConfirmation) "முன் அனுமதி கட்டாயம்" else "தன்னாட்சி அனுமதி",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = if (rule.requiresExplicitConfirmation) SovereignGold else SovereignGreen,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(rule.ruleDescriptionTa, fontSize = 11.5.sp, color = SovereignTextSecondary, lineHeight = 15.sp)
        }
      }
    }
  }
}

// ----------------------------------------------------------------------------
// 7. SKILL TREE & OPPORTUNITY RADAR SECTION
// ----------------------------------------------------------------------------
@Composable
private fun SkillTreeAndOpportunitySection(
  skills: List<SkillTreeNode>,
  opportunities: List<OpportunityRadarItem>,
  graveyard: List<IdeaGraveyardItem>
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Text("🌳 கற்றல் ஸ்கில் ட்ரீ (Personal Learning Skill Tree)", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
    }

    items(skills) { sk ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(sk.skillNameTa, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Text("${sk.masteryPercentage}% அடைவு", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignGreen)
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text("துணைத் திறன்கள்: ${sk.subSkills.joinToString(" • ")}", fontSize = 10.5.sp, color = SovereignTextMuted)
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(6.dp))
      Text("📡 வாய்ப்பு ரேடார் (Opportunity Radar - Project Intersections)", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignGold)
    }

    items(opportunities) { opp ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text(opp.titleTa, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          Text("இணைப்பு: ${opp.intersectingFactors.joinToString(" + ")}", fontSize = 10.5.sp, color = SovereignCyan)
          Spacer(modifier = Modifier.height(6.dp))
          Text(opp.potentialInnovationTa, fontSize = 11.sp, color = SovereignTextSecondary)
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(6.dp))
      Text("⚰️ யோசனை மறுமலர்ச்சி கல்லறை (Idea Graveyard & Revival)", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignPurple)
    }

    items(graveyard) { idg ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text(idg.originalIdeaTitleTa, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          Text("முன்பு முடங்கிய காரணம்: ${idg.originalBlockerReasonTa}", fontSize = 10.5.sp, color = SovereignTextMuted)
          Spacer(modifier = Modifier.height(6.dp))
          Text("🚀 இப்போது ஏன் சாத்தியம்?: ${idg.whyPracticalNowTa}", fontSize = 11.sp, color = SovereignGreen, fontWeight = FontWeight.Medium)
        }
      }
    }
  }
}
