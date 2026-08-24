package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.automirrored.filled.VolumeUp
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
import com.example.data.SocialContentEngine
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun SocialContentStudioScreen() {
  val trendSignals by SocialContentEngine.trendSignals.collectAsState()
  val postDrafts by SocialContentEngine.postDrafts.collectAsState()
  val comments by SocialContentEngine.commentInbox.collectAsState()
  val postMortems by SocialContentEngine.postMortems.collectAsState()

  var selectedSubTab by remember { mutableIntStateOf(0) }
  var userIdeaPrompt by remember { mutableStateOf("") }
  var selectedPlatform by remember { mutableStateOf(SocialPlatform.INSTAGRAM) }
  var selectedGoal by remember { mutableStateOf(ContentGoal.EDUCATION) }
  var selectedTrend by remember { mutableStateOf<TrendSignal?>(trendSignals.firstOrNull()) }
  var showGeneratedModal by remember { mutableStateOf(false) }

  val tabs = listOf(
    "🔥 ட்ரெண்ட் & அல்காரிதம்" to Icons.Default.TrendingUp,
    "✍️ மல்டி-வேரியன்ட் உருவாக்கு" to Icons.Default.AutoAwesome,
    "📋 வெளியீட்டு மேலாண்மை" to Icons.Default.Schedule,
    "💬 கமெண்ட் அசிஸ்டெண்ட்" to Icons.AutoMirrored.Filled.Chat,
    "📊 கற்றல் & போஸ்ட்-மார்ட்டம்" to Icons.Default.Analytics
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(SovereignBackground)
  ) {
    // Header Banner
    Surface(
      color = SovereignSurface,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(Brush.linearGradient(listOf(Color(0xFFE1306C), Color(0xFF1877F2)))),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.Share, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              "சமூக வலைத்தள உள்ளடக்க தன்னாட்சி மையம் (Instagram & Facebook)",
              fontSize = 13.5.sp,
              fontWeight = FontWeight.Bold,
              color = SovereignTextPrimary
            )
            Text(
              "Trend Discovery • Platform-Aware Optimization • Multi-Variant • Candidate Windows • Ethical Hooks",
              fontSize = 10.sp,
              color = SovereignTextMuted
            )
          }
        }
      }
    }

    // Scrollable Sub-tabs
    ScrollableTabRow(
      selectedTabIndex = selectedSubTab,
      containerColor = SovereignSurfaceDark,
      contentColor = SovereignCyan,
      edgePadding = 12.dp,
      divider = {}
    ) {
      tabs.forEachIndexed { index, (label, icon) ->
        Tab(
          selected = selectedSubTab == index,
          onClick = { selectedSubTab = index },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(icon, contentDescription = null, modifier = Modifier.size(15.dp), tint = if (selectedSubTab == index) SovereignCyan else SovereignTextMuted)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                label,
                fontSize = 11.5.sp,
                fontWeight = if (selectedSubTab == index) FontWeight.Bold else FontWeight.Normal,
                color = if (selectedSubTab == index) SovereignTextPrimary else SovereignTextSecondary
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
      when (selectedSubTab) {
        0 -> TrendDiscoveryTab(
          trends = trendSignals,
          onSelectTrendToCreate = { trend ->
            selectedTrend = trend
            selectedPlatform = trend.platform
            selectedSubTab = 1
          }
        )
        1 -> MultiVariantCreatorTab(
          ideaPrompt = userIdeaPrompt,
          onPromptChange = { userIdeaPrompt = it },
          platform = selectedPlatform,
          onPlatformChange = { selectedPlatform = it },
          goal = selectedGoal,
          onGoalChange = { selectedGoal = it },
          selectedTrend = selectedTrend,
          onGenerate = {
            SocialContentEngine.createMultiVariantDraft(
              ideaPrompt = if (userIdeaPrompt.isBlank()) "Explain On-Device AI with zero cloud dependency in Tamil" else userIdeaPrompt,
              platform = selectedPlatform,
              goal = selectedGoal,
              selectedTrend = selectedTrend
            )
            selectedSubTab = 2
          }
        )
        2 -> PublishingManagementTab(
          drafts = postDrafts,
          onApproveSchedule = { draftId, mode ->
            SocialContentEngine.approveAndSchedulePost(draftId, mode)
          }
        )
        3 -> CommentAssistantTab(
          comments = comments,
          onReply = { commentId ->
            SocialContentEngine.replyToComment(commentId)
          }
        )
        4 -> PostMortemAnalyticsTab(
          analytics = postMortems
        )
      }
    }
  }
}

// ----------------------------------------------------------------------------
// 1. TREND DISCOVERY TAB
// ----------------------------------------------------------------------------
@Composable
private fun TrendDiscoveryTab(
  trends: List<TrendSignal>,
  onSelectTrendToCreate: (TrendSignal) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(SovereignCyanDark, SovereignPurpleDark)))
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = SovereignGold, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("அல்காரிதம் வெளிப்படைத்தன்மை தத்துவம் (Algorithm Policy):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignGold)
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            "«“அல்காரிதத்தை ஏமாற்றுவது அல்லது bypass செய்வது நமது நோக்கமல்ல. அதிகாரப்பூர்வ தகவல்கள், தற்போதைய சமிக்ஞைகள் மற்றும் உங்கள் சொந்த பார்வையாளர்களின் உண்மையான செயல்திறன் தரவு மூலம் அதிக மதிப்பு மற்றும் எங்கேஜ்மென்ட் தரும் உள்ளடக்கத்தை உருவாக்குவதே நமது தத்துவம்.”»",
            fontSize = 11.sp,
            color = SovereignTextSecondary,
            lineHeight = 15.sp
          )
        }
      }
    }

    items(trends) { trend ->
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
            Row(verticalAlignment = Alignment.CenterVertically) {
              Surface(
                color = Color(trend.platform.brandColorHex),
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(trend.platform.labelEn, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
              }
              Spacer(modifier = Modifier.width(8.dp))
              Surface(
                color = Color(trend.velocity.colorHex).copy(alpha = 0.2f),
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(trend.velocity.badgeText, color = Color(trend.velocity.colorHex), fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
              }
            }

            Surface(
              color = SovereignCyanDark.copy(alpha = 0.5f),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text("வாய்ப்பு ஸ்கோர்: ${trend.contentOpportunityScore}/100", color = SovereignCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(trend.topicNameTa, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          Text(trend.topicNameEn, fontSize = 11.sp, color = SovereignTextMuted)

          Spacer(modifier = Modifier.height(6.dp))
          Text(trend.competitorSaturationNote, fontSize = 11.sp, color = SovereignTextSecondary)

          Spacer(modifier = Modifier.height(8.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(trend.relevantHashtags) { tag ->
              Surface(
                color = SovereignSurfaceHover,
                shape = RoundedCornerShape(4.dp)
              ) {
                Text(tag, color = SovereignCyan, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Button(
            onClick = { onSelectTrendToCreate(trend) },
            colors = ButtonDefaults.buttonColors(containerColor = SovereignCyanDark),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.Create, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("இந்த ட்ரெண்ட்டிற்கு உகந்த பதிவு உருவாக்கு (Create Post)", color = SovereignCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

// ----------------------------------------------------------------------------
// 2. MULTI-VARIANT CREATOR TAB
// ----------------------------------------------------------------------------
@Composable
private fun MultiVariantCreatorTab(
  ideaPrompt: String,
  onPromptChange: (String) -> Unit,
  platform: SocialPlatform,
  onPlatformChange: (SocialPlatform) -> Unit,
  goal: ContentGoal,
  onGoalChange: (ContentGoal) -> Unit,
  selectedTrend: TrendSignal?,
  onGenerate: () -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Text("1. இலக்கு தளம் (Target Platform):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
      Spacer(modifier = Modifier.height(6.dp))
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SocialPlatform.values().forEach { p ->
          FilterChip(
            selected = platform == p,
            onClick = { onPlatformChange(p) },
            label = { Text(p.labelTa, fontSize = 11.sp) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = Color(p.brandColorHex).copy(alpha = 0.3f),
              selectedLabelColor = Color.White
            )
          )
        }
      }
    }

    item {
      Text("2. உள்ளடக்கத்தின் நோக்கம் (Content Goal):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
      Spacer(modifier = Modifier.height(6.dp))
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        ContentGoal.values().forEach { g ->
          Card(
            colors = CardDefaults.cardColors(containerColor = if (goal == g) SovereignCyanDark.copy(alpha = 0.4f) else SovereignSurface),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onGoalChange(g) }
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text(g.labelTa, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (goal == g) SovereignCyan else SovereignTextPrimary)
              Text("கட்டமைப்பு: ${g.optimalStructure}", fontSize = 10.sp, color = SovereignTextSecondary)
            }
          }
        }
      }
    }

    item {
      Text("3. உங்கள் அசல் யோசனை (Owner Unique Perspective):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
      Spacer(modifier = Modifier.height(6.dp))
      OutlinedTextField(
        value = ideaPrompt,
        onValueChange = onPromptChange,
        placeholder = { Text("எ.கா: இணையம் இல்லாமலேயே உங்கள் போனில் AI இயங்கும் ரகசியம்...", color = SovereignTextMuted, fontSize = 12.sp) },
        modifier = Modifier
          .fillMaxWidth()
          .height(100.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = SovereignSurface,
          unfocusedContainerColor = SovereignSurface,
          focusedBorderColor = SovereignCyan,
          unfocusedBorderColor = SovereignBorder
        ),
        shape = RoundedCornerShape(10.dp)
      )
    }

    item {
      if (selectedTrend != null) {
        Surface(
          color = SovereignSurfaceHover,
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = SovereignGreen, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("இணைக்கப்பட்ட ட்ரெண்ட்: ${selectedTrend.topicNameTa}", fontSize = 11.sp, color = SovereignGreen, fontWeight = FontWeight.Medium)
          }
        }
      }
    }

    item {
      Button(
        onClick = onGenerate,
        colors = ButtonDefaults.buttonColors(containerColor = SovereignCyan),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("btn_generate_social_variants")
      ) {
        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.Black)
        Spacer(modifier = Modifier.width(8.dp))
        Text("3 மல்டி-வேரியன்ட் வரைவுகள் உருவாக்கு (Generate Multi-Variants)", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}

// ----------------------------------------------------------------------------
// 3. PUBLISHING MANAGEMENT TAB
// ----------------------------------------------------------------------------
@Composable
private fun PublishingManagementTab(
  drafts: List<SocialPostDraft>,
  onApproveSchedule: (String, PublishingMode) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(drafts) { draft ->
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
            Surface(
              color = Color(draft.targetPlatform.brandColorHex),
              shape = RoundedCornerShape(6.dp)
            ) {
              Text(draft.targetPlatform.labelEn, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }

            Surface(
              color = Color(draft.status.colorHex).copy(alpha = 0.2f),
              shape = RoundedCornerShape(6.dp)
            ) {
              Text(draft.status.labelTa, color = Color(draft.status.colorHex), fontSize = 9.5.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text("🎯 தேர்ந்தெடுக்கப்பட்ட வேரியன்ட்:", fontSize = 11.sp, color = SovereignTextMuted)
          Text(draft.selectedVariant.variantName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)

          Spacer(modifier = Modifier.height(8.dp))
          Surface(
            color = SovereignSurfaceHover,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text("ஹூக் (Ethical Opening Hook):", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = SovereignGold)
              Text("«${draft.selectedVariant.hook.hookTextTa}»", fontSize = 11.5.sp, color = SovereignTextPrimary, fontWeight = FontWeight.Medium)
              Spacer(modifier = Modifier.height(4.dp))
              Text("📹 காட்சித் திட்டம்: ${draft.selectedVariant.hook.visualFirstFramePlan}", fontSize = 10.sp, color = SovereignTextSecondary)
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text("📝 உகந்த கேப்ஷன் (Optimized Caption):", fontSize = 11.sp, color = SovereignTextMuted)
          Text(draft.optimizedCaptionTa, fontSize = 11.sp, color = SovereignTextSecondary, lineHeight = 15.sp)

          Spacer(modifier = Modifier.height(8.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AccessTime, contentDescription = null, tint = SovereignPurple, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("சிறந்த வெளியீட்டு நேரம்: ${draft.candidatePostingWindow}", fontSize = 10.5.sp, color = SovereignPurple, fontWeight = FontWeight.Bold)
          }

          Spacer(modifier = Modifier.height(12.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = { onApproveSchedule(draft.id, PublishingMode.APPROVAL_REQUIRED) },
              colors = ButtonDefaults.buttonColors(containerColor = SovereignCyanDark),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("ஒப்புதல் அளி (Approve)", color = SovereignCyan, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
            }

            Button(
              onClick = { onApproveSchedule(draft.id, PublishingMode.SCHEDULED_AUTO) },
              colors = ButtonDefaults.buttonColors(containerColor = SovereignGreenDark),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("அட்டவணைப்படுத்து (Schedule)", color = SovereignGreen, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}

// ----------------------------------------------------------------------------
// 4. COMMENT ASSISTANT TAB
// ----------------------------------------------------------------------------
@Composable
private fun CommentAssistantTab(
  comments: List<SocialCommentItem>,
  onReply: (String) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(12.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    items(comments) { item ->
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
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(item.authorName, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
              Spacer(modifier = Modifier.width(6.dp))
              Surface(
                color = if (item.isSpamOrBot) SovereignRed.copy(alpha = 0.2f) else SovereignCyanDark.copy(alpha = 0.3f),
                shape = RoundedCornerShape(4.dp)
              ) {
                Text(item.sentiment, color = if (item.isSpamOrBot) SovereignRed else SovereignCyan, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
              }
            }

            if (item.isReplied) {
              Text("✓ பதிலளிக்கப்பட்டது", fontSize = 10.sp, color = SovereignGreen, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.height(4.dp))
          Text("பதிவு: ${item.postTitle}", fontSize = 10.sp, color = SovereignTextMuted)
          Spacer(modifier = Modifier.height(6.dp))
          Text("«${item.commentText}»", fontSize = 11.5.sp, color = SovereignTextSecondary)

          Spacer(modifier = Modifier.height(8.dp))
          Surface(
            color = SovereignSurfaceHover,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(8.dp)) {
              Text("AI பரிந்துரைக்கும் தமிழ் பதில் வரைவு:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
              Text(item.suggestedReplyTa, fontSize = 11.sp, color = SovereignTextPrimary)
            }
          }

          if (!item.isReplied && !item.isSpamOrBot) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
              onClick = { onReply(item.id) },
              colors = ButtonDefaults.buttonColors(containerColor = SovereignCyanDark),
              shape = RoundedCornerShape(6.dp),
              modifier = Modifier.align(Alignment.End)
            ) {
              Icon(Icons.AutoMirrored.Filled.Reply, contentDescription = null, tint = SovereignCyan, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("பதில் அனுப்பு (Send Reply)", color = SovereignCyan, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}

// ----------------------------------------------------------------------------
// 5. POST-MORTEM & CONTINUOUS LEARNING TAB
// ----------------------------------------------------------------------------
@Composable
private fun PostMortemAnalyticsTab(
  analytics: List<PostMortemAnalytics>
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(analytics) { item ->
      Card(
        colors = CardDefaults.cardColors(containerColor = SovereignSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(item.postTitle, fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = SovereignTextPrimary)
          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text("பார்வைகள்", fontSize = 9.5.sp, color = SovereignTextMuted)
              Text("${item.viewsOrImpressions}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
            }
            Column {
              Text("ரீச் (Reach)", fontSize = 9.5.sp, color = SovereignTextMuted)
              Text("${item.reachCount}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignPurple)
            }
            Column {
              Text("தக்கவைப்பு", fontSize = 9.5.sp, color = SovereignTextMuted)
              Text("${item.retentionRatePercent}%", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignGreen)
            }
            Column {
              Text("சேமிப்புகள்", fontSize = 9.5.sp, color = SovereignTextMuted)
              Text("${item.saveCount}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SovereignGold)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Surface(
            color = SovereignSurfaceHover,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text("✅ எது சிறப்பாக வேலை செய்தது?", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = SovereignGreen)
              Text(item.whatWorkedTa, fontSize = 11.sp, color = SovereignTextSecondary)

              Spacer(modifier = Modifier.height(6.dp))
              Text("⚠️ எது முன்னேற்றப்பட வேண்டும்?", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = SovereignGold)
              Text(item.whatNeedsImprovementTa, fontSize = 11.sp, color = SovereignTextSecondary)

              Spacer(modifier = Modifier.height(6.dp))
              Text("🧠 தொடர் கற்றல் பாடம் (Lesson for Next Content):", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = SovereignCyan)
              Text(item.lessonLearnedTa, fontSize = 11.sp, color = SovereignTextPrimary)
            }
          }
        }
      }
    }
  }
}
