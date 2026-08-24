package com.example.model

import java.util.UUID

/**
 * PLATFORM-AWARE CONTENT OPTIMIZATION & AUTO-PUBLISHING ENGINE
 * Instagram & Facebook Trend Discovery, Algorithm Signal Intelligence, Multi-Variant Generation,
 * Ethical Hook Structuring, Policy Guardrails, and Continuous Learning Loop.
 */

enum class SocialPlatform(
  val id: String,
  val labelTa: String,
  val labelEn: String,
  val brandColorHex: Long,
  val supportedFormats: List<String>
) {
  INSTAGRAM(
    "instagram",
    "இன்ஸ்டாகிராம் (Instagram)",
    "Instagram",
    0xFFE1306C,
    listOf("Reels (Shorts)", "Carousels (Slides)", "Single Post", "Stories")
  ),
  FACEBOOK(
    "facebook",
    "பேஸ்புக் (Facebook)",
    "Facebook",
    0xFF1877F2,
    listOf("Discussion Posts", "Reels (Video)", "Photo Album", "Group Sharing")
  )
}

enum class TrendVelocity(
  val labelTa: String,
  val labelEn: String,
  val colorHex: Long,
  val badgeText: String
) {
  EMERGING("வளர்ந்து வரும் புதிய தலைப்பு (Emerging)", "Emerging Early", 0xFF38BDF8, "🌱 EMERGING"),
  GROWING("வேகமாக உயரும் ட்ரெண்ட் (Rapid Growth)", "Fast Growing", 0xFF10B981, "🚀 GROWING"),
  STABLE("நிலையான ஈடுபாடு (Stable Popular)", "Stable", 0xFFF59E0B, "⚖️ STABLE"),
  SATURATED("அதிக நெரிசல் / முதிர்ச்சி (Saturated)", "Saturated / Crowded", 0xFFEF4444, "⚠️ SATURATED"),
  DECLINING("குறையும் ஆர்வம் (Declining)", "Declining Interest", 0xFF94A3B8, "📉 DECLINING")
}

enum class ContentGoal(
  val labelTa: String,
  val labelEn: String,
  val optimalStructure: String
) {
  AWARENESS("விழிப்புணர்வு & பிராண்ட் (Awareness)", "Brand Awareness", "Strong Visual Hook → Core Concept → Broad Relevance"),
  EDUCATION("கல்வி & புதிய தகவல் (Education)", "Deep Education", "Curiosity Question → Step-by-Step Breakdown → Actionable Takeaway"),
  COMMUNITY_DISCUSSION("கருத்து விவாதம் (Community Discussion)", "Community Engagement", "Relatable Dilemma → Owner Perspective → Open Question CTA"),
  LEAD_GENERATION("திட்ட / சேவை அறிமுகம் (Lead & Project)", "Project / Showcase", "Real-World Problem → Sovereign Solution → Architecture Highlight"),
  ENTERTAINMENT_CREATIVE("கலை & படைப்பாற்றல் (Creative Showcase)", "Creative Entertainment", "Visual Spectacle → Behind-the-Scenes Craft → Emotional Audio")
}

data class TrendSignal(
  val id: String = UUID.randomUUID().toString(),
  val topicNameTa: String,
  val topicNameEn: String,
  val platform: SocialPlatform,
  val velocity: TrendVelocity,
  val growthRatePercentage: Int,
  val estimatedAudienceFit: Float, // 0.0 to 1.0
  val contentOpportunityScore: Int, // 0 to 100
  val relevantHashtags: List<String>,
  val publicSignalSource: String,
  val competitorSaturationNote: String
)

data class ContentStructureHook(
  val hookType: String,
  val hookTextTa: String,
  val hookTextEn: String,
  val visualFirstFramePlan: String,
  val ethicalRating: String = "100% Truthful (No Clickbait)"
)

data class ContentVariant(
  val variantId: String,
  val variantName: String,
  val styleDescriptionTa: String,
  val hook: ContentStructureHook,
  val bodyTextTa: String,
  val keyTakeawayTa: String,
  val callToActionTa: String,
  val recommendedAudioVibe: String,
  val estimatedEngagementRate: String
)

data class SocialPostDraft(
  val id: String = UUID.randomUUID().toString(),
  val originalIdeaPrompt: String,
  val targetPlatform: SocialPlatform,
  val goal: ContentGoal,
  val primaryTrend: TrendSignal?,
  val selectedVariant: ContentVariant,
  val allVariants: List<ContentVariant>,
  val optimizedCaptionTa: String,
  val selectedHashtags: List<String>,
  val candidatePostingWindow: String,
  val windowReasoning: String,
  val opportunityScore: Int, // 0 to 100
  val policyComplianceCheck: PolicyCheckResult,
  val publishingMode: PublishingMode = PublishingMode.APPROVAL_REQUIRED,
  val status: PostPublishStatus = PostPublishStatus.READY_FOR_REVIEW,
  val scheduledTime: String? = null
)

enum class PublishingMode(val labelTa: String, val labelEn: String) {
  MANUAL("கைமுறை வெளியீடு (Manual Owner Publish)", "Manual"),
  APPROVAL_REQUIRED("உரிமையாளர் ஒப்புதல் தேவை (Owner Approval First)", "Approval Mode"),
  SCHEDULED_AUTO("அங்கீகரிக்கப்பட்ட நேர அட்டவணை (Scheduled Auto)", "Scheduled Auto"),
  LOW_RISK_AUTONOMOUS("விதிகளுக்குட்பட்ட தன்னாட்சி வெளியீடு (Rule-Based Auto)", "Low-Risk Auto")
}

enum class PostPublishStatus(val labelTa: String, val labelEn: String, val colorHex: Long) {
  DRAFT("வரைவு (Draft)", "Draft", 0xFF94A3B8),
  READY_FOR_REVIEW("மதிப்பாய்வுக்கு தயார் (Ready)", "Ready for Review", 0xFF38BDF8),
  SCHEDULED("திட்டமிடப்பட்டது (Scheduled)", "Scheduled", 0xFFF59E0B),
  PUBLISHED("வெற்றிகரமாக வெளியிடப்பட்டது (Published)", "Published", 0xFF10B981),
  REJECTED("ரத்து செய்யப்பட்டது (Declined)", "Declined", 0xFFEF4444)
}

data class PolicyCheckResult(
  val isCompliant: Boolean,
  val safetyScore: Float, // 0.0 to 1.0
  val copyrightClearance: Boolean = true,
  val noClickbaitVerified: Boolean = true,
  val noSpamPatternVerified: Boolean = true,
  val policyNoteTa: String
)

data class SocialCommentItem(
  val id: String = UUID.randomUUID().toString(),
  val authorName: String,
  val platform: SocialPlatform,
  val postTitle: String,
  val commentText: String,
  val sentiment: String, // Positive, Question, Constructive, Spam
  val isSpamOrBot: Boolean,
  val suggestedReplyTa: String,
  val isReplied: Boolean = false
)

data class PostMortemAnalytics(
  val postId: String,
  val postTitle: String,
  val platform: SocialPlatform,
  val viewsOrImpressions: Int,
  val reachCount: Int,
  val engagementRatePercent: Float,
  val retentionRatePercent: Float,
  val saveCount: Int,
  val shareCount: Int,
  val whatWorkedTa: String,
  val whatNeedsImprovementTa: String,
  val lessonLearnedTa: String
)
