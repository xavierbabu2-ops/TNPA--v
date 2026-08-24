package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RiskTier
import com.example.ui.theme.*
import com.example.voice.PipelineStageStatus
import com.example.voice.TamilVoiceIntelligencePipeline
import com.example.voice.diarization.IdentifiedSpeakerType
import com.example.voice.intent.VoiceIntentType
import com.example.voice.normalization.ScriptTokenClassification
import com.example.voice.security.SpoofThreatType
import com.example.voice.stt.TamilDialectRegion
import kotlinx.coroutines.launch

/**
 * OWNER-ONLY TAMIL VOICE INTELLIGENCE & SECURITY LAB
 * Interactive visual inspector demonstrating:
 * 1. Multi-Speaker Separation & Target Speaker Isolation (Owner vs Guests vs TV Audio)
 * 2. Biometric Voiceprint Verification (512-dim Cosine, Pitch F0, Formants)
 * 3. Anti-Spoofing & Liveness Defense (Replay attack, AI Voice Cloning, Deepfake TTS)
 * 4. Zero-Leakage Owner Authorization Gate & Fallback Policy
 * 5. Tamil Linguistic Normalization & Decoupled Intent Execution
 */
@Composable
fun TamilVoiceIntelligenceLabDialog(
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier
) {
  val coroutineScope = rememberCoroutineScope()
  val pipeline = remember { TamilVoiceIntelligencePipeline.INSTANCE }
  val activeSession by pipeline.pipelineState.collectAsState()
  val history by pipeline.interactionHistory.collectAsState()

  var testInputText by remember { mutableStateOf("பாபு fusion plasma physics simulation பண்ணு") }
  var selectedDialect by remember { mutableStateOf(TamilDialectRegion.TAMIL_NADU) }
  var simulatedSpeaker by remember { mutableStateOf(IdentifiedSpeakerType.OWNER_PRIMARY) }
  var simulatedThreat by remember { mutableStateOf(SpoofThreatType.NONE) }
  var activeTab by remember { mutableStateOf(0) } // 0: Live Pipeline, 1: Owner Security & Liveness, 2: Normalization, 3: Intent Taxonomy, 4: Logs

  val samplePhrases = listOf(
    "பாபு fusion plasma physics simulation பண்ணு",
    "ஹாலோகிராபிக் குவாண்டம் வரைபடம் ஒன்னு வரை",
    "ஒரு 6-frame வீடியோ ஸ்டோரிபோர்டு script உருவாக்கு",
    "bell state quantum entanglement செக் பண்ணு",
    "என் தனிப்பட்ட குறிப்புகளைத் திறந்து காட்டு",
    "முந்தைய நிலைக்கு rollback பண்ணிடு",
    "vanakkam babu solar panel project kandupidi"
  )

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .fillMaxHeight(0.92f),
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    color = SovereignSurface,
    tonalElevation = 16.dp,
    border = BorderStroke(1.dp, SovereignCyan.copy(alpha = 0.4f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
      // 1. Header with Close Button
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
              .background(SovereignCyan.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Security,
              contentDescription = null,
              tint = SovereignCyan,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "உரிமையாளர் தமிழ் குரல் பாதுகாப்பு ஆய்வகம்",
              color = SovereignTextPrimary,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Owner-Only Voice • Speaker Separation • Anti-Spoof • Biometrics",
              color = SovereignTextMuted,
              fontSize = 10.5.sp
            )
          }
        }
        IconButton(onClick = onDismiss) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = SovereignTextSecondary
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 2. Navigation Tabs
      ScrollableTabRow(
        selectedTabIndex = activeTab,
        containerColor = SovereignSurfaceElevated,
        contentColor = SovereignCyan,
        edgePadding = 0.dp,
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
      ) {
        Tab(
          selected = activeTab == 0,
          onClick = { activeTab = 0 },
          text = { Text("நேரலை பைப்லைன்", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = activeTab == 1,
          onClick = { activeTab = 1 },
          text = { Text("குரல் பாதுகாப்பு (Anti-Spoof)", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = activeTab == 2,
          onClick = { activeTab = 2 },
          text = { Text("தமிழ் Normalization", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = activeTab == 3,
          onClick = { activeTab = 3 },
          text = { Text("Intent மேப்பிங்", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = activeTab == 4,
          onClick = { activeTab = 4 },
          text = { Text("Router Logs", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Content based on tab
      when (activeTab) {
        0 -> LivePipelineTab(
          testInputText = testInputText,
          onTextChange = { testInputText = it },
          samplePhrases = samplePhrases,
          selectedDialect = selectedDialect,
          onDialectChange = { selectedDialect = it },
          simulatedSpeaker = simulatedSpeaker,
          onSpeakerChange = { simulatedSpeaker = it },
          simulatedThreat = simulatedThreat,
          onThreatChange = { simulatedThreat = it },
          activeSession = activeSession,
          onRunPipeline = {
            coroutineScope.launch {
              pipeline.processVoiceUtterance(testInputText, simulatedSpeaker, simulatedThreat)
            }
          }
        )
        1 -> OwnerVoiceSecurityTab(
          simulatedSpeaker = simulatedSpeaker,
          onSpeakerChange = { simulatedSpeaker = it },
          simulatedThreat = simulatedThreat,
          onThreatChange = { simulatedThreat = it },
          activeSession = activeSession,
          onTestAuth = {
            coroutineScope.launch {
              pipeline.processVoiceUtterance(testInputText, simulatedSpeaker, simulatedThreat)
            }
          }
        )
        2 -> NormalizationDeepDiveTab(testInputText = testInputText)
        3 -> IntentTaxonomyTab()
        4 -> RouterAndLogsTab(history = history)
      }
    }
  }
}

@Composable
private fun LivePipelineTab(
  testInputText: String,
  onTextChange: (String) -> Unit,
  samplePhrases: List<String>,
  selectedDialect: TamilDialectRegion,
  onDialectChange: (TamilDialectRegion) -> Unit,
  simulatedSpeaker: IdentifiedSpeakerType,
  onSpeakerChange: (IdentifiedSpeakerType) -> Unit,
  simulatedThreat: SpoofThreatType,
  onThreatChange: (SpoofThreatType) -> Unit,
  activeSession: com.example.voice.VoicePipelineSession,
  onRunPipeline: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Spoken Input Box
    Card(
      colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
      shape = RoundedCornerShape(14.dp),
      border = BorderStroke(1.dp, SovereignBorder)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Text(
          text = "1. குரல் / உரை உள்ளீடு (Spoken Utterance):",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = SovereignCyan
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = testInputText,
          onValueChange = onTextChange,
          modifier = Modifier.fillMaxWidth(),
          textStyle = LocalTextStyle.current.copy(color = SovereignTextPrimary, fontSize = 13.sp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SovereignCyan,
            unfocusedBorderColor = SovereignBorder,
            cursorColor = SovereignCyan
          ),
          placeholder = {
            Text("தமிழிலோ அல்லது Tanglish-லோ குரல் வாக்கியத்தை உள்ளிடவும்...", color = SovereignTextMuted, fontSize = 12.sp)
          }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Speaker Persona Selection
        Text("குரல் பேச்சாளர் அடையாளம் (Speaker Identity):", fontSize = 11.sp, color = SovereignTextMuted)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          listOf(
            IdentifiedSpeakerType.OWNER_PRIMARY,
            IdentifiedSpeakerType.GUEST_SPEAKER_A,
            IdentifiedSpeakerType.TELEVISION_MEDIA,
            IdentifiedSpeakerType.UNKNOWN_SPEAKER
          ).forEach { spk ->
            val isSel = simulatedSpeaker == spk
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (isSel) (if (spk.isAuthorizedOwner) SovereignCyanDark else Color(0xFF3E1F1F)) else SovereignSurface,
              border = BorderStroke(1.dp, if (isSel) (if (spk.isAuthorizedOwner) SovereignCyan else SovereignRose) else SovereignBorder),
              modifier = Modifier.clickable { onSpeakerChange(spk) }
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = if (spk.isAuthorizedOwner) Icons.Default.VerifiedUser else Icons.Default.PersonOutline,
                  contentDescription = null,
                  tint = if (spk.isAuthorizedOwner) SovereignCyan else SovereignRose,
                  modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = spk.labelTa.take(18),
                  fontSize = 10.sp,
                  fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSel) SovereignTextPrimary else SovereignTextSecondary
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Quick Preset Prompts
        Text("மாதிரி குரல் கட்டளைகள் (Preset Commands):", fontSize = 11.sp, color = SovereignTextMuted)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          samplePhrases.take(4).forEach { phrase ->
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = SovereignSurface,
              border = BorderStroke(0.5.dp, SovereignBorder),
              modifier = Modifier.clickable { onTextChange(phrase) }
            ) {
              Text(
                text = phrase.take(24) + "...",
                fontSize = 10.5.sp,
                color = SovereignTextSecondary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Run Action Button
        Button(
          onClick = onRunPipeline,
          modifier = Modifier.fillMaxWidth(),
          colors = ButtonDefaults.buttonColors(containerColor = SovereignCyanDark),
          shape = RoundedCornerShape(10.dp)
        ) {
          Icon(Icons.Default.PlayArrow, contentDescription = null, tint = SovereignCyan)
          Spacer(modifier = Modifier.width(6.dp))
          Text("குரல் பைப்லைனை இயக்கு (Process Voice Intent)", color = SovereignCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
      }
    }

    // Pipeline Active Visualizer
    PipelineStagesIndicator(activeSession.currentStage, activeSession.isOwnerAuthorized)

    // Security Gate Banner
    activeSession.authorizationEvaluation?.let { auth ->
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (auth.isActionPermitted) SovereignEmerald.copy(alpha = 0.12f) else SovereignRose.copy(alpha = 0.15f),
        border = BorderStroke(1.dp, if (auth.isActionPermitted) SovereignEmerald else SovereignRose),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = if (auth.isActionPermitted) Icons.Default.Shield else Icons.Default.Block,
            contentDescription = null,
            tint = if (auth.isActionPermitted) SovereignEmerald else SovereignRose,
            modifier = Modifier.size(28.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = if (auth.isActionPermitted) "உரிமையாளர் சரிபார்ப்பு வெற்றி (Authorized)" else "அனுமதி மறுக்கப்பட்டது (Unauthorized)",
              color = if (auth.isActionPermitted) SovereignEmerald else SovereignRose,
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            )
            Text(
              text = auth.secureSpokenResponseTa,
              color = SovereignTextPrimary,
              fontSize = 11.sp
            )
            Text(
              text = "Biometric: ${(auth.ownerBiometricConfidence * 100).toInt()}% • Liveness: ${(auth.livenessConfidence * 100).toInt()}%",
              color = SovereignTextMuted,
              fontSize = 9.5.sp
            )
          }
        }
      }
    }

    // Normalization & Intent Cards
    activeSession.normalizationResult?.let { nRes ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, SovereignBorder)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text("2. சீரமைக்கப்பட்ட தமிழ் (Normalized Tamil):", fontSize = 11.sp, color = SovereignCyan, fontWeight = FontWeight.Bold)
          Text(nRes.normalizedText, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = SovereignTextPrimary)
        }
      }
    }

    activeSession.parsedIntent?.let { intent ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, SovereignBorder)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("3. பிரித்தறியப்பட்ட Intent:", fontSize = 11.sp, color = SovereignCyan, fontWeight = FontWeight.Bold)
            BadgePill("Confidence ${(intent.confidenceScore * 100).toInt()}%", SovereignEmerald)
          }
          Text(intent.intentType.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary, fontFamily = FontFamily.Monospace)
          Text("Agent: ${intent.targetAgentRole.titleTa} (${intent.targetAgentRole.titleEn})", fontSize = 11.sp, color = SovereignTextSecondary)
        }
      }
    }

    // Execution Result
    activeSession.executionResult?.let { exec ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, if (exec.isSuccess) SovereignEmerald else SovereignRose)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text("4. Sovereign Action Execution & Provenance:", fontSize = 11.sp, color = SovereignEmerald, fontWeight = FontWeight.Bold)
          Text(exec.statusMessageTa, fontSize = 12.sp, color = SovereignTextPrimary, fontWeight = FontWeight.Medium)
          Spacer(modifier = Modifier.height(4.dp))
          Text("Spoken Audio Feedback: \"${exec.spokenVoiceResponseTa}\"", fontSize = 11.sp, color = SovereignCyan)
          Text("SHA-256 Hash: ${exec.provenanceHash.take(32)}...", fontSize = 9.sp, color = SovereignTextMuted, fontFamily = FontFamily.Monospace)
        }
      }
    }
  }
}

@Composable
private fun OwnerVoiceSecurityTab(
  simulatedSpeaker: IdentifiedSpeakerType,
  onSpeakerChange: (IdentifiedSpeakerType) -> Unit,
  simulatedThreat: SpoofThreatType,
  onThreatChange: (SpoofThreatType) -> Unit,
  activeSession: com.example.voice.VoicePipelineSession,
  onTestAuth: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Zero-Trust Security Philosophy Banner
    Surface(
      shape = RoundedCornerShape(12.dp),
      color = SovereignSurfaceElevated,
      border = BorderStroke(1.dp, SovereignCyan.copy(alpha = 0.5f)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Lock, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("முழுமையான உரிமையாளர் அங்கீகாரக் கொள்கை (Owner-Only Zero-Trust)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          "\"எல்லோருடைய குரலையும் கேட்க முடியும்; ஆனால் உரிமையாளரின் சரிபார்க்கப்பட்ட குரலை மட்டுமே தனிப்பட்ட கட்டளையாக ஏற்க வேண்டும்.\"",
          fontSize = 11.sp,
          color = SovereignTextSecondary
        )
      }
    }

    // Speaker Separation & Isolation Box
    Card(
      colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
      shape = RoundedCornerShape(12.dp),
      border = BorderStroke(1.dp, SovereignBorder)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Text("1. பேச்சாளர் பிரிப்பு மற்றும் தேர்வு (Speaker Diarization):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
        Spacer(modifier = Modifier.height(6.dp))

        IdentifiedSpeakerType.values().forEach { spk ->
          val isSel = simulatedSpeaker == spk
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (isSel) (if (spk.isAuthorizedOwner) SovereignCyanDark else Color(0xFF4A1A1A)) else SovereignSurface,
            border = BorderStroke(1.dp, if (isSel) (if (spk.isAuthorizedOwner) SovereignCyan else SovereignRose) else SovereignBorder),
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 3.dp)
              .clickable { onSpeakerChange(spk) }
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(spk.labelTa, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = if (isSel) SovereignTextPrimary else SovereignTextSecondary)
                Text(spk.labelEn, fontSize = 9.5.sp, color = SovereignTextMuted)
              }
              BadgePill(
                text = if (spk.isAuthorizedOwner) "உரிமையாளர் (Authorized)" else "அனுமதி இல்லை (Blocked)",
                color = if (spk.isAuthorizedOwner) SovereignEmerald else SovereignRose
              )
            }
          }
        }
      }
    }

    // Anti-Spoofing & Liveness Attack Simulator
    Card(
      colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
      shape = RoundedCornerShape(12.dp),
      border = BorderStroke(1.dp, SovereignBorder)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Text("2. போலிக்குரல் மற்றும் மறுபதிவுத் தடுப்பு (Anti-Spoofing & Liveness):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignRose)
        Spacer(modifier = Modifier.height(6.dp))

        SpoofThreatType.values().forEach { threat ->
          val isSel = simulatedThreat == threat
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (isSel) (if (threat == SpoofThreatType.NONE) SovereignCyanDark else Color(0xFF4A1A1A)) else SovereignSurface,
            border = BorderStroke(1.dp, if (isSel) (if (threat == SpoofThreatType.NONE) SovereignEmerald else SovereignRose) else SovereignBorder),
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 3.dp)
              .clickable { onThreatChange(threat) }
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(threat.labelTa, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (isSel) SovereignTextPrimary else SovereignTextSecondary)
                Text(threat.labelEn, fontSize = 9.5.sp, color = SovereignTextMuted)
              }
              BadgePill(threat.severityLevel, if (threat.severityLevel == "SAFE") SovereignEmerald else SovereignRose)
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
          onClick = onTestAuth,
          modifier = Modifier.fillMaxWidth(),
          colors = ButtonDefaults.buttonColors(containerColor = SovereignSurfaceElevated),
          border = BorderStroke(1.dp, SovereignCyan),
          shape = RoundedCornerShape(10.dp)
        ) {
          Icon(Icons.Default.Fingerprint, contentDescription = null, tint = SovereignCyan)
          Spacer(modifier = Modifier.width(6.dp))
          Text("உரிமையாளர் குரல் சோதனை (Verify Voice Biometrics)", color = SovereignCyan, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    // Biometric Radar & Spectral Telemetry Box
    Card(
      colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
      shape = RoundedCornerShape(12.dp),
      border = BorderStroke(1.dp, SovereignBorder)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Text("3. உயிரியளவியல் அளவீடுகள் (Biometrics & Spectral Telemetry):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
        Spacer(modifier = Modifier.height(8.dp))

        val bio = activeSession.biometricResult
        val live = activeSession.livenessResult

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          MetricBox(
            title = "Voiceprint Cosine",
            value = "${((bio?.biometricSimilarityScore ?: 0.99f) * 100).toInt()}%",
            color = if ((bio?.biometricSimilarityScore ?: 0.99f) >= 0.88f) SovereignEmerald else SovereignRose,
            modifier = Modifier.weight(1f)
          )
          MetricBox(
            title = "Liveness Score",
            value = "${((live?.livenessConfidenceScore ?: 0.99f) * 100).toInt()}%",
            color = if ((live?.livenessConfidenceScore ?: 0.99f) >= 0.85f) SovereignEmerald else SovereignRose,
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          MetricBox(
            title = "Pitch F0 Deviation",
            value = "${(bio?.pitchDeviationPercent ?: 2.1f).toInt()}%",
            color = SovereignTextPrimary,
            modifier = Modifier.weight(1f)
          )
          MetricBox(
            title = "Phase Continuity",
            value = "${((live?.phaseContinuityScore ?: 0.98f) * 100).toInt()}%",
            color = SovereignCyan,
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "Cryptographic Voiceprint Hash: ${bio?.signatureHash?.take(24) ?: "e3b0c44298fc1c149afbf4c8996fb924"}...",
          fontSize = 9.sp,
          color = SovereignTextMuted,
          fontFamily = FontFamily.Monospace
        )
      }
    }
  }
}

@Composable
private fun MetricBox(title: String, value: String, color: Color, modifier: Modifier = Modifier) {
  Surface(
    shape = RoundedCornerShape(8.dp),
    color = SovereignSurface,
    border = BorderStroke(0.5.dp, SovereignBorder),
    modifier = modifier
  ) {
    Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
      Text(title, fontSize = 9.sp, color = SovereignTextMuted)
      Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = color)
    }
  }
}

@Composable
private fun PipelineStagesIndicator(stage: PipelineStageStatus, isOwnerAuthorized: Boolean) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = SovereignSurfaceElevated,
    border = BorderStroke(1.dp, SovereignBorder),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(10.dp)) {
      Text(
        text = "பைப்லைன் நிலை (Pipeline Stage):",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = SovereignTextMuted
      )
      Spacer(modifier = Modifier.height(6.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        listOf(
          PipelineStageStatus.SPEAKER_DIARIZATION to "Diarization",
          PipelineStageStatus.BIOMETRIC_VERIFICATION to "Voiceprint",
          PipelineStageStatus.LIVENESS_ANALYSIS to "Anti-Spoof",
          PipelineStageStatus.SECURITY_AUTHORIZATION to "Auth Gate",
          PipelineStageStatus.NORMALIZING_TEXT to "Normalize",
          PipelineStageStatus.DETECTING_INTENT to "Intent",
          PipelineStageStatus.EXECUTING_SUBSYSTEM to "Execute"
        ).forEach { (st, label) ->
          val isActive = stage == st
          val isDone = stage.ordinal > st.ordinal
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = when {
              isActive -> SovereignCyanDark
              isDone -> SovereignEmerald.copy(alpha = 0.15f)
              else -> SovereignSurface
            },
            border = BorderStroke(
              1.dp,
              when {
                isActive -> SovereignCyan
                isDone -> SovereignEmerald
                else -> SovereignBorder
              }
            )
          ) {
            Text(
              text = label,
              fontSize = 9.5.sp,
              fontWeight = if (isActive || isDone) FontWeight.Bold else FontWeight.Normal,
              color = when {
                isActive -> SovereignCyan
                isDone -> SovereignEmerald
                else -> SovereignTextMuted
              },
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
private fun NormalizationDeepDiveTab(testInputText: String) {
  val normalizer = remember { com.example.voice.normalization.DefaultTamilTextNormalizer() }
  val result = remember(testInputText) { normalizer.normalize(testInputText) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    Card(
      colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
      shape = RoundedCornerShape(12.dp),
      border = BorderStroke(1.dp, SovereignBorder)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Text("மூல உரை (Raw Input):", fontSize = 11.sp, color = SovereignTextMuted)
        Text(result.originalRawText, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
        Spacer(modifier = Modifier.height(6.dp))
        Text("சீரமைக்கப்பட்ட உரை (Normalized Canonical):", fontSize = 11.sp, color = SovereignCyan, fontWeight = FontWeight.Bold)
        Text(result.normalizedText, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignEmerald)
      }
    }

    // Token Classifications
    Card(
      colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
      shape = RoundedCornerShape(12.dp),
      border = BorderStroke(1.dp, SovereignBorder)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Text("சொல் வகைப்பாடு (Token Classification Tags):", fontSize = 11.sp, color = SovereignCyan, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        result.tokens.forEach { token ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(token.normalizedSnippet, fontSize = 12.sp, color = SovereignTextPrimary, fontWeight = FontWeight.Medium)
            BadgePill(token.classification.labelTa, getClassificationColor(token.classification))
          }
        }
      }
    }

    // Normalization Rules Reference
    Card(
      colors = CardDefaults.cardColors(containerColor = SovereignSurfaceElevated),
      shape = RoundedCornerShape(12.dp),
      border = BorderStroke(1.dp, SovereignBorder)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Text("தமிழ் Normalization விதிகள் (Engine Rules):", fontSize = 11.sp, color = SovereignCyan, fontWeight = FontWeight.Bold)
        RuleRow("1. கிரந்த சீரமைப்பு (Grantha)", "ஸ, ஷ, ஜ, ஹ, க்ஷ, ஸ்ரீ சீரான அகர வரிசைக்கு மாற்றப்படுகின்றன.")
        RuleRow("2. பேச்சு வழக்கு (Colloquial)", "பண்ணு ➔ செய், வர்றேன் ➔ வருகிறேன், வேணும் ➔ வேண்டும், எப்டி ➔ எப்படி.")
        RuleRow("3. டாங்கிலிஷ் (Tanglish)", "solunga ➔ சொல்லுங்கள், kandupidi ➔ கண்டுபிடி, varai ➔ வரை.")
        RuleRow("4. எண்கள் & அலகுகள்", "௧, ௨, ஒன்று ➔ 1, 2 மற்றும் kg, sec, INR, %, Hz, °C.")
        RuleRow("5. புணர்ச்சி & கூட்டுச்சொல் (Sandhi)", "படம்வரைய ➔ படம் வரை, கணக்குப்போடு ➔ கணக்கு செய்.")
      }
    }
  }
}

@Composable
private fun IntentTaxonomyTab() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Text(
      "Intent வகைப்பாடு (Voice Intent Taxonomy decoupled from Execution):",
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      color = SovereignCyan
    )

    VoiceIntentType.values().forEach { intentType ->
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = SovereignSurfaceElevated,
        border = BorderStroke(1.dp, SovereignBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(intentType.name, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignCyan, fontFamily = FontFamily.Monospace)
            BadgePill("Risk: ${intentType.defaultRiskTier.labelEn}", Color(intentType.defaultRiskTier.colorHex))
          }
          Text(intentType.labelTa, fontSize = 12.sp, color = SovereignTextPrimary, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 2.dp))
          Text("இயக்கும் முகவர்: ${intentType.defaultAgent.titleTa}", fontSize = 10.sp, color = SovereignTextMuted)
        }
      }
    }
  }
}

@Composable
private fun RouterAndLogsTab(history: List<com.example.voice.VoicePipelineSession>) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    Text(
      "Action Router தணிக்கை மற்றும் வரலாற்றுப் பதிவுகள் (Audit Logs):",
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      color = SovereignCyan
    )

    if (history.isEmpty()) {
      Text("வரலாற்றுப் பதிவுகள் எதுவும் இல்லை.", fontSize = 11.sp, color = SovereignTextMuted)
    } else {
      history.forEach { session ->
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = SovereignSurfaceElevated,
          border = BorderStroke(1.dp, SovereignBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(10.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("\"${session.rawInputTranscript}\"", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
              Text("${session.processingTimeMs} ms", fontSize = 10.sp, color = SovereignEmerald, fontWeight = FontWeight.Bold)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              BadgePill(
                text = if (session.isOwnerAuthorized) "Owner Verified" else "Unauthorized / Blocked",
                color = if (session.isOwnerAuthorized) SovereignEmerald else SovereignRose
              )
            }
            session.parsedIntent?.let {
              Text("Intent: ${it.intentType.name} (${it.targetAgentRole.titleEn})", fontSize = 11.sp, color = SovereignCyan)
            }
            session.executionResult?.let {
              Text("Status: ${it.statusMessageTa}", fontSize = 11.sp, color = SovereignTextSecondary)
              Text("Provenance: ${it.provenanceHash.take(24)}...", fontSize = 9.sp, color = SovereignTextMuted, fontFamily = FontFamily.Monospace)
            }
          }
        }
      }
    }
  }
}

private fun getClassificationColor(classification: ScriptTokenClassification): Color {
  return when (classification) {
    ScriptTokenClassification.TAMIL_PURE -> SovereignEmerald
    ScriptTokenClassification.GRANTHA_EXTENDED -> SovereignCyan
    ScriptTokenClassification.TANGLISH_ROMANIZED -> SovereignPurple
    ScriptTokenClassification.ENGLISH_TECHNICAL -> SovereignCyanDark
    ScriptTokenClassification.NUMERIC_VALUE -> SovereignGold
    ScriptTokenClassification.UNIT_MEASUREMENT -> Color(0xFF4CAF50)
    ScriptTokenClassification.PUNCTUATION_SYMBOL -> SovereignTextMuted
  }
}

@Composable
private fun RuleRow(title: String, desc: String) {
  Column(modifier = Modifier.padding(vertical = 4.dp)) {
    Text(title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
    Text(desc, fontSize = 10.sp, color = SovereignTextMuted)
  }
}

@Composable
private fun BadgePill(text: String, color: Color) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(6.dp))
      .background(color.copy(alpha = 0.2f))
      .padding(horizontal = 6.dp, vertical = 2.dp)
  ) {
    Text(text = text, color = color, fontSize = 9.sp, fontWeight = FontWeight.Bold)
  }
}
