package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SovereignEngine
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun ScienceAndMathScreen() {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabs = listOf(
    "🔬 AI Research Scientist" to Icons.Default.Science,
    "📚 Personal Learning Engine" to Icons.Default.School,
    "⚛️ குவாண்டம் சர்க்யூட்" to Icons.Default.Memory,
    "📐 கணித மூளை (Math)" to Icons.Default.Calculate,
    "🔬 இயற்பியல் ஆய்வகம்" to Icons.Default.Biotech,
    "🌐 அறிவுக் கட்டமைப்பு" to Icons.Default.Hub
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
        0 -> AiResearchScientistSubScreen()
        1 -> PersonalLearningSubScreen()
        2 -> QuantumCircuitSubScreen()
        3 -> MathBrainSubScreen()
        4 -> PhysicsLabSubScreen()
        5 -> KnowledgeGraphSubScreen()
      }
    }
  }
}

// -------------------------------------------------------------
// 1. AI RESEARCH SCIENTIST MODE
// -------------------------------------------------------------
@Composable
fun AiResearchScientistSubScreen() {
  val researchProjects by SovereignEngine.researchProjects.collectAsState()
  var newResearchTopic by remember { mutableStateOf("") }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      // Header Card
      Surface(
        shape = RoundedCornerShape(14.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignEmerald),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Science, contentDescription = null, tint = SovereignEmerald)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "🔬 AI Research Scientist Pipeline",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = SovereignTextPrimary
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "உண்மை ≠ கருதுகோள் ≠ சிமுலேஷன் ≠ பரிசோதனை ஆதாரம்\nஎன்ற அறிவியல் வகைப்படுத்தலுடன் முழுமையான ஆய்வு சுழற்சி.",
            fontSize = 11.sp,
            color = SovereignTextSecondary,
            lineHeight = 16.sp
          )
          Spacer(modifier = Modifier.height(10.dp))

          Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
              value = newResearchTopic,
              onValueChange = { newResearchTopic = it },
              placeholder = { Text("ஆராய்ச்சி தலைப்பு / புதிய பிரச்சனை உள்ளிடவும்...", fontSize = 12.sp, color = SovereignTextMuted) },
              shape = RoundedCornerShape(12.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SovereignSurfaceElevated,
                unfocusedContainerColor = SovereignSurfaceElevated,
                focusedBorderColor = SovereignEmerald,
                unfocusedBorderColor = SovereignBorder,
                focusedTextColor = SovereignTextPrimary,
                unfocusedTextColor = SovereignTextPrimary
              ),
              modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
              onClick = {
                if (newResearchTopic.isNotBlank()) {
                  SovereignEngine.sendUserPrompt("ஆராய்ச்சி: $newResearchTopic", MultimodalInputType.TEXT)
                  newResearchTopic = ""
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = SovereignEmerald),
              shape = RoundedCornerShape(12.dp)
            ) {
              Text("ஆய்வு செய்", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
          }
        }
      }
    }

    items(researchProjects, key = { it.id }) { project ->
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
            Text(
              text = project.problemTitleTa,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = SovereignTextPrimary
            )
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = SovereignEmeraldDark.copy(alpha = 0.4f),
              border = androidx.compose.foundation.BorderStroke(1.dp, SovereignEmerald)
            ) {
              Text(
                text = "🟢 " + project.overallConfidence.labelEn,
                fontSize = 10.sp,
                color = SovereignEmerald,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
          Text(text = "துறை: ${project.targetDomain}", fontSize = 11.sp, color = SovereignTextMuted)

          Spacer(modifier = Modifier.height(12.dp))
          Text(text = "📌 5-நிலை அறிவியல் பைப்லைன் சுழற்சி:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignSky)

          Spacer(modifier = Modifier.height(8.dp))
          project.steps.forEach { step ->
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = SovereignSurfaceElevated,
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
            ) {
              Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.Top
              ) {
                Surface(
                  shape = RoundedCornerShape(4.dp),
                  color = Color(step.claimType.badgeColor).copy(alpha = 0.2f),
                  border = androidx.compose.foundation.BorderStroke(1.dp, Color(step.claimType.badgeColor))
                ) {
                  Text(
                    text = step.claimType.labelTa,
                    fontSize = 9.sp,
                    color = Color(step.claimType.badgeColor),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                  )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = "${step.stepNumber}. ${step.phaseNameTa}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SovereignTextPrimary
                  )
                  Text(
                    text = step.descriptionTa,
                    fontSize = 10.sp,
                    color = SovereignTextSecondary,
                    lineHeight = 15.sp
                  )
                  Text(
                    text = "வெளியீடு: ${step.outputSnippet}",
                    fontSize = 10.sp,
                    color = SovereignCyan,
                    fontFamily = FontFamily.Monospace
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = SovereignEmeraldDark.copy(alpha = 0.2f),
            border = androidx.compose.foundation.BorderStroke(1.dp, SovereignEmerald),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text(
                text = "🏆 சரிபார்க்கப்பட்ட புதிய தீர்வு (Verified Proposal):",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = SovereignEmerald
              )
              Text(
                text = project.verifiedProposalTa,
                fontSize = 12.sp,
                color = SovereignTextPrimary,
                lineHeight = 17.sp
              )
            }
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 2. PERSONAL LEARNING ENGINE
// -------------------------------------------------------------
@Composable
fun PersonalLearningSubScreen() {
  val tracks by SovereignEngine.learningTracks.collectAsState()
  var selectedTrackId by remember { mutableStateOf(tracks.firstOrNull()?.id ?: "") }
  val activeTrack = tracks.find { it.id == selectedTrackId } ?: tracks.firstOrNull()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(14.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignPurple),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.School, contentDescription = null, tint = SovereignPurple)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "📚 Personal Learning Engine (கற்றல் ஆசான்)",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = SovereignTextPrimary
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "உங்கள் அறிவு நிலையை மதிப்பிட்டு (Assessment), கருத்தாக்கம் ➔ காட்சி ➔ சிமுலேஷன் ➔ வினாடி வினா மூலம் உங்களுக்குக் கற்றுக்கொடுக்கும் நுண்ணறிவு.",
            fontSize = 11.sp,
            color = SovereignTextSecondary,
            lineHeight = 16.sp
          )
        }
      }
    }

    // Track Selector Chips
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
      ) {
        tracks.forEach { track ->
          val isSelected = track.id == selectedTrackId
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isSelected) SovereignPurpleDark else SovereignSurfaceElevated,
            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) SovereignPurple else SovereignBorder),
            modifier = Modifier
              .padding(end = 8.dp)
              .clickable { selectedTrackId = track.id }
          ) {
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
              Text(
                text = track.subjectTa,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) SovereignPurple else SovereignTextPrimary
              )
              Text(
                text = "நிலை: ${track.currentLevel.labelTa} • ${track.progressPercent}% நிறைவு",
                fontSize = 10.sp,
                color = SovereignTextMuted
              )
            }
          }
        }
      }
    }

    if (activeTrack != null) {
      items(activeTrack.lessons, key = { it.lessonNumber }) { lesson ->
        var selectedQuizOption by remember { mutableStateOf<Int?>(null) }
        var showQuizResult by remember { mutableStateOf(false) }

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = SovereignSurface,
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (lesson.isCompleted) SovereignEmerald else SovereignBorder
          ),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "பாடம் ${lesson.lessonNumber}: ${lesson.titleTa}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = SovereignTextPrimary
              )
              if (lesson.isCompleted) {
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = SovereignEmeraldDark.copy(alpha = 0.4f),
                  border = androidx.compose.foundation.BorderStroke(1.dp, SovereignEmerald)
                ) {
                  Text(
                    text = "✓ முடிந்தது",
                    fontSize = 10.sp,
                    color = SovereignEmerald,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "💡 கருத்து விளக்கம்:",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = SovereignCyan
            )
            Text(
              text = lesson.conceptExplanationTa,
              fontSize = 12.sp,
              color = SovereignTextPrimary,
              lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "🌍 நிஜ உலக உதாரணம்: ${lesson.realWorldExampleTa}",
              fontSize = 11.sp,
              color = SovereignTextSecondary,
              lineHeight = 16.sp
            )

            if (lesson.interactiveSimulationFormula != null) {
              Spacer(modifier = Modifier.height(6.dp))
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = SovereignSurfaceElevated,
                modifier = Modifier.fillMaxWidth()
              ) {
                Text(
                  text = "📐 சமன்பாடு: ${lesson.interactiveSimulationFormula}",
                  fontSize = 11.sp,
                  color = SovereignEmerald,
                  fontFamily = FontFamily.Monospace,
                  modifier = Modifier.padding(8.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = "❓ பயிற்சி வினாடி வினா: ${lesson.quizQuestionTa}",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = SovereignSky
            )

            Spacer(modifier = Modifier.height(6.dp))
            lesson.quizOptions.forEachIndexed { optIndex, optionText ->
              val isChosen = selectedQuizOption == optIndex
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (isChosen) SovereignSkyDark else SovereignSurfaceElevated,
                border = androidx.compose.foundation.BorderStroke(
                  1.dp,
                  if (isChosen) SovereignSky else SovereignBorder
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 6.dp)
                  .clickable {
                    selectedQuizOption = optIndex
                    showQuizResult = true
                    if (optIndex == lesson.correctOptionIndex) {
                      SovereignEngine.completeLearningLesson(activeTrack.id, lesson.lessonNumber)
                    }
                  }
              ) {
                Text(
                  text = "${optIndex + 1}) $optionText",
                  fontSize = 11.sp,
                  color = if (isChosen) SovereignSky else SovereignTextPrimary,
                  fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Normal,
                  modifier = Modifier.padding(8.dp)
                )
              }
            }

            if (showQuizResult && selectedQuizOption != null) {
              val isCorrect = selectedQuizOption == lesson.correctOptionIndex
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = if (isCorrect) "🎉 மிகச் சரி! பாடம் நிறைவு பெற்றது." else "❌ தவறான விடை. மீண்டும் முயற்சிக்கவும்.",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (isCorrect) SovereignEmerald else SovereignRose
              )
            }
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 3. QUANTUM CIRCUIT SUB SCREEN
// -------------------------------------------------------------
@Composable
fun QuantumCircuitSubScreen() {
  val circuit by SovereignEngine.quantumCircuit.collectAsState()

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
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Memory, contentDescription = null, tint = SovereignCyan)
              Spacer(modifier = Modifier.width(8.dp))
              Text("⚛️ Quantum Circuit Simulator", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            }
            TextButton(onClick = { SovereignEngine.resetQuantumCircuit() }) {
              Text("மீட்டமை (|00⟩)", fontSize = 11.sp, color = SovereignRose)
            }
          }
          Text(text = "க்யூபிட் மேற்பொருந்துதல் (Superposition) & என்டாங்கிள்மென்ட் (Bell State) நேரலை சிமுலேஷன்.", fontSize = 11.sp, color = SovereignTextSecondary)

          Spacer(modifier = Modifier.height(14.dp))

          // Wire Q0
          Text(text = "Qubit 0 (|q₀⟩):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignSky)
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(shape = RoundedCornerShape(4.dp), color = SovereignCyanDark) {
              Text("|0⟩", color = SovereignCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(6.dp))
            }
            Box(modifier = Modifier.width(16.dp).height(2.dp).background(SovereignBorder))
            circuit.gatesOnQ0.forEach { gate ->
              Surface(shape = RoundedCornerShape(6.dp), color = SovereignSurfaceElevated, border = androidx.compose.foundation.BorderStroke(1.dp, SovereignCyan)) {
                Text(gate.symbol, color = SovereignCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
              }
              Box(modifier = Modifier.width(16.dp).height(2.dp).background(SovereignBorder))
            }
          }

          // Wire Q1
          Text(text = "Qubit 1 (|q₁⟩):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignPurple)
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(shape = RoundedCornerShape(4.dp), color = SovereignPurpleDark) {
              Text("|0⟩", color = SovereignPurple, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(6.dp))
            }
            Box(modifier = Modifier.width(16.dp).height(2.dp).background(SovereignBorder))
            circuit.gatesOnQ1.forEach { gate ->
              Surface(shape = RoundedCornerShape(6.dp), color = SovereignSurfaceElevated, border = androidx.compose.foundation.BorderStroke(1.dp, SovereignPurple)) {
                Text(gate.symbol, color = SovereignPurple, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
              }
              Box(modifier = Modifier.width(16.dp).height(2.dp).background(SovereignBorder))
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(text = "கேட் சேர்க்கவும் (Add Gates):", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SovereignTextMuted)
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Button(
              onClick = { SovereignEngine.applyQuantumGate(QuantumGateType.HADAMARD, true) },
              colors = ButtonDefaults.buttonColors(containerColor = SovereignCyan),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.padding(end = 6.dp),
              contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text("+H on Q0", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Button(
              onClick = { SovereignEngine.applyQuantumGate(QuantumGateType.CNOT, false) },
              colors = ButtonDefaults.buttonColors(containerColor = SovereignPurple),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.padding(end = 6.dp),
              contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text("+CNOT on Q1", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Button(
              onClick = { SovereignEngine.applyQuantumGate(QuantumGateType.PAULI_X, true) },
              colors = ButtonDefaults.buttonColors(containerColor = SovereignEmerald),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.padding(end = 6.dp),
              contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text("+X (Flip)", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.height(14.dp))
          Surface(shape = RoundedCornerShape(8.dp), color = SovereignSurfaceElevated, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text(text = "📊 குவாண்டம் நிலை & நிகழ்தகவு:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
              Text(text = circuit.entanglementState, fontSize = 11.sp, color = SovereignTextPrimary)
              Spacer(modifier = Modifier.height(6.dp))
              Text(text = "P(|00⟩) = ${(circuit.probability00 * 100).toInt()}%   |   P(|11⟩) = ${(circuit.probability11 * 100).toInt()}%", fontSize = 12.sp, color = SovereignEmerald, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 4. MATHEMATICS BRAIN SUB SCREEN
// -------------------------------------------------------------
@Composable
fun MathBrainSubScreen() {
  val derivations by SovereignEngine.mathDerivations.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    items(derivations, key = { it.id }) { item ->
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(text = item.problemTitle, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          Text(text = "பிரிவு: ${item.category} • ${item.verificationMethod}", fontSize = 10.sp, color = SovereignTextMuted)

          Spacer(modifier = Modifier.height(10.dp))
          Surface(shape = RoundedCornerShape(8.dp), color = SovereignSurfaceElevated, modifier = Modifier.fillMaxWidth()) {
            Text(text = "தொடக்க சமன்பாடு: ${item.initialExpression}", fontSize = 12.sp, color = SovereignCyan, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(text = "படிபடியான நிரூபண வழிகள்:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignSky)
          item.steps.forEach { step ->
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.Top) {
              Text(text = "${step.stepNumber}.", fontSize = 11.sp, color = SovereignSky, fontWeight = FontWeight.Bold)
              Spacer(modifier = Modifier.width(6.dp))
              Column {
                Text(text = step.formula, fontSize = 11.sp, color = SovereignTextPrimary, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                Text(text = step.explanationTa, fontSize = 10.sp, color = SovereignTextSecondary)
              }
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Surface(shape = RoundedCornerShape(8.dp), color = SovereignEmeraldDark.copy(alpha = 0.3f), border = androidx.compose.foundation.BorderStroke(1.dp, SovereignEmerald), modifier = Modifier.fillMaxWidth()) {
            Text(text = "✓ இறுதி விடை: ${item.finalResult}", fontSize = 12.sp, color = SovereignEmerald, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 5. PHYSICS LAB SUB SCREEN
// -------------------------------------------------------------
@Composable
fun PhysicsLabSubScreen() {
  val experiments by SovereignEngine.physicsExperiments.collectAsState()
  var frequencySlider by remember { mutableFloatStateOf(2.5f) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    items(experiments, key = { it.id }) { exp ->
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SovereignSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(text = exp.titleTa, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          Text(text = "துறை: ${exp.field}", fontSize = 10.sp, color = SovereignTextMuted)

          Spacer(modifier = Modifier.height(8.dp))
          Text(text = "💡 கருதுகோள்: ${exp.hypothesis}", fontSize = 11.sp, color = SovereignTextSecondary)
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "📐 சமன்பாடு: ${exp.mathematicalEquation}", fontSize = 11.sp, color = SovereignCyan, fontFamily = FontFamily.Monospace)

          Spacer(modifier = Modifier.height(10.dp))
          Text(text = "அதிர்வெண் கட்டுப்படுத்தி (ω): ${String.format("%.1f", frequencySlider)} rad/s", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignSky)
          Slider(
            value = frequencySlider,
            onValueChange = { frequencySlider = it },
            valueRange = 0.5f..5.0f,
            colors = SliderDefaults.colors(thumbColor = SovereignCyan, activeTrackColor = SovereignCyan)
          )

          // Live Waveform Canvas
          Canvas(
            modifier = Modifier
              .fillMaxWidth()
              .height(90.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(SovereignSurfaceElevated)
              .border(1.dp, SovereignBorder, RoundedCornerShape(8.dp))
          ) {
            val midY = size.height / 2
            val points = 100
            for (i in 0 until points - 1) {
              val x1 = (i.toFloat() / points) * size.width
              val x2 = ((i + 1).toFloat() / points) * size.width
              val normX1 = (x1 - size.width / 2) / 30f
              val normX2 = (x2 - size.width / 2) / 30f

              val y1 = midY - (Math.exp(-0.5 * normX1 * normX1 * (frequencySlider / 2f)) * (size.height * 0.4f)).toFloat()
              val y2 = midY - (Math.exp(-0.5 * normX2 * normX2 * (frequencySlider / 2f)) * (size.height * 0.4f)).toFloat()

              drawLine(
                color = Color(0xFF10B981),
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = 3f
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(text = "✓ முடிவுகள்: ${exp.observedResultTa}", fontSize = 11.sp, color = SovereignTextPrimary)
        }
      }
    }
  }
}

// -------------------------------------------------------------
// 6. KNOWLEDGE GRAPH SUB SCREEN
// -------------------------------------------------------------
@Composable
fun KnowledgeGraphSubScreen() {
  val nodes by SovereignEngine.knowledgeGraphNodes.collectAsState()

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
          Text(text = "🌐 Universal Knowledge Graph", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
          Text(text = "இயற்பியல் ↔ கணிதம் ↔ கணினி ↔ ரோபோட்டிக்ஸ் ஆகிய துறைகளுக்கு இடையே உள்ள குறுக்குத் தொடர்புகள்.", fontSize = 11.sp, color = SovereignTextSecondary)
        }
      }
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
            Text(text = node.labelTa, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
            Surface(shape = RoundedCornerShape(4.dp), color = SovereignCyanDark) {
              Text(text = node.domain, fontSize = 9.sp, color = SovereignCyan, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }
          }
          Text(text = node.crossDomainInsightTa, fontSize = 11.sp, color = SovereignTextSecondary)
        }
      }
    }
  }
}
