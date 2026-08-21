package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ArtItem
import com.example.ui.components.TnpaOfficialEmblem
import com.example.ui.theme.TnpaCharcoal
import com.example.ui.theme.TnpaCyan
import com.example.ui.theme.TnpaGold
import com.example.ui.theme.TnpaGreen
import com.example.ui.theme.TnpaJetBlack
import com.example.ui.theme.TnpaOffWhite
import com.example.ui.theme.TnpaPureWhite
import com.example.ui.theme.TnpaRedDark
import com.example.ui.theme.TnpaRedLight
import com.example.ui.theme.TnpaRedPrimary
import com.example.ui.theme.TnpaRedSoft

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArtGalleryScreen() {
  var selectedCategory by remember { mutableStateOf("அனைத்தும் (All)") }
  var selectedArtForDetail by remember { mutableStateOf<ArtItem?>(null) }

  val artItems = remember {
    mutableStateListOf(
      ArtItem(
        id = "ART-01",
        title = "பாரம்பரிய தஞ்சை பெரிய கோவில் பிரம்மாண்ட சுவர் ஓவியம்",
        artistName = "சுந்தரம் (Sundaram)",
        district = "தஞ்சாவூர் (Thanjavur)",
        category = "சுவர் ஓவியம்",
        likesCount = 640,
        description = "15x10 அடி பரப்பில் அக்ரிலிக் எக்ஸ்டீரியர் எமல்ஷன் & ரெட்-ஒயிட் வாஷ் முறையில் வரையப்பட்ட பிரம்மாண்ட தஞ்சை பெரிய கோவில் கோபுர சுவர் சித்திரம்.",
        medium = "அக்ரிலிக் எக்ஸ்டீரியர் எமல்ஷன்",
        dimensions = "15 x 10 அடி"
      ),
      ArtItem(
        id = "ART-02",
        title = "திருவள்ளுவர் சிலையும் மாமல்லபுர கடற்கரையும் கேன்வாஸ் ஓவியம்",
        artistName = "மணிமாறன் (Manimaran)",
        district = "சென்னை (Chennai)",
        category = "உருவப்படம்",
        likesCount = 478,
        description = "எண்ணெய் வண்ண ஓவியம் (Oil on Canvas) - TNPA மாநில மாநாட்டு கலைக் கண்காட்சியில் முதல் பரிசு பெற்ற ஓவியப் படைப்பு.",
        medium = "ஆயில் பெயிண்ட் (Oil on Canvas)",
        dimensions = "4 x 3 அடி"
      ),
      ArtItem(
        id = "ART-03",
        title = "3D பிரம்மாண்ட நீர்வீழ்ச்சி & பாறை சுவர் கலை (3D Waterfall Mural)",
        artistName = "செல்வராஜ் (Selvaraj)",
        district = "திருச்சிராப்பள்ளி (Trichy)",
        category = "3D ஆர்ட்",
        likesCount = 890,
        description = "வீட்டின் வரவேற்பறை சுவரில் வரையப்பட்ட தத்ரூபமான 3D காட்சி - பார்க்கும்போது உண்மை நீர்வீழ்ச்சி ஓடுவது போன்ற பிரமிப்பைத் தரும்.",
        medium = "3D ஸ்டென்சில் & அக்ரிலிக் ஸ்ப்ரே",
        dimensions = "12 x 8 அடி"
      ),
      ArtItem(
        id = "ART-04",
        title = "மதுரை மீனாட்சி அம்மன் கோவில் பாரம்பரிய சுதை ஓவியம்",
        artistName = "கார்த்திகேயன் (Karthikeyan)",
        district = "மதுரை (Madurai)",
        category = "கோவில் சித்திரம்",
        likesCount = 752,
        description = "பாரம்பரிய கோவில் சுவர்களில் இயற்கை மூலிகை வண்ணங்கள் மற்றும் எனாமல் பூச்சு கலந்து வரையப்பட்ட தெய்வீக சுதை கலை.",
        medium = "கோவில் வண்ணக் கலவை & எனாமல்",
        dimensions = "20 x 12 அடி"
      ),
      ArtItem(
        id = "ART-05",
        title = "நவீன வணிக நிறுவன நியான் லெட்டரிங் & போர்டு ரைட்டிங்",
        artistName = "விஜயகுமார் (Vijayakumar)",
        district = "கோயம்புத்தூர் (Coimbatore)",
        category = "போர்டு ரைட்டிங்",
        likesCount = 389,
        description = "கைகளால் வரையப்பட்ட நேர்த்தியான தமிழ் மற்றும் ஆங்கில எழுத்துருக்கள் (Hand Lettering & Commercial Sign Art).",
        medium = "ஆயில் எனாமல் & ரிஃப்ளெக்டிவ் பெயிண்ட்",
        dimensions = "10 x 4 அடி"
      ),
      ArtItem(
        id = "ART-06",
        title = "இயற்கை வனவிலங்கு பாதுகாப்பு 3D பில்டிங் மியூரல்",
        artistName = "ஆர். தினேஷ் (R. Dinesh)",
        district = "சேலம் (Salem)",
        category = "3D ஆர்ட்",
        likesCount = 520,
        description = "பள்ளி கட்டிட சுவரில் மாணவர்களுக்காக வரையப்பட்ட 3D காடு மற்றும் விலங்குகள் விழிப்புணர்வு ஓவியம்.",
        medium = "வெதர் ப்ரூப் எமல்ஷன் & அக்ரிலிக்",
        dimensions = "25 x 10 அடி"
      )
    )
  }

  val categories = listOf(
    "அனைத்தும் (All)",
    "சுவர் ஓவியம்",
    "உருவப்படம்",
    "3D ஆர்ட்",
    "கோவில் சித்திரம்",
    "போர்டு ரைட்டிங்"
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Header Banner
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
      border = androidx.compose.foundation.BorderStroke(1.dp, TnpaRedSoft)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(Brush.horizontalGradient(listOf(TnpaRedDark, TnpaRedPrimary, TnpaJetBlack)))
          .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          TnpaOfficialEmblem(sizeDp = 42.dp)
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "TNPA ஓவியக் கலைக்கூடம் (Art Gallery)",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Black,
              color = TnpaPureWhite
            )
            Text(
              text = "தமிழ்நாடு ஓவியர்களின் கைவண்ணத்தில் உருவான கலைப்படைப்புகள்",
              fontSize = 11.sp,
              color = Color(0xFFFEE2E2)
            )
          }
        }

        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(TnpaGold),
          contentAlignment = Alignment.Center
        ) {
          Icon(Icons.Default.Palette, contentDescription = null, tint = TnpaJetBlack, modifier = Modifier.size(20.dp))
        }
      }
    }

    // Category Filter Chips
    ScrollableTabRow(
      selectedTabIndex = categories.indexOf(selectedCategory).coerceAtLeast(0),
      edgePadding = 4.dp,
      containerColor = Color.Transparent,
      divider = {}
    ) {
      categories.forEach { cat ->
        FilterChip(
          selected = selectedCategory == cat,
          onClick = { selectedCategory = cat },
          label = {
            Text(
              text = cat,
              fontSize = 11.sp,
              fontWeight = if (selectedCategory == cat) FontWeight.Bold else FontWeight.Normal
            )
          },
          modifier = Modifier.padding(horizontal = 4.dp).testTag("filter_chip_$cat"),
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = TnpaRedPrimary,
            selectedLabelColor = TnpaPureWhite,
            containerColor = TnpaPureWhite,
            labelColor = TnpaJetBlack
          ),
          border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selectedCategory == cat,
            borderColor = if (selectedCategory == cat) TnpaRedDark else TnpaJetBlack.copy(alpha = 0.2f)
          )
        )
      }
    }

    // Gallery List
    val filteredList = if (selectedCategory == "அனைத்தும் (All)") {
      artItems
    } else {
      artItems.filter { it.category == selectedCategory }
    }

    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(filteredList, key = { it.id }) { item ->
        ArtWorkCard(
          item = item,
          onLike = {
            val idx = artItems.indexOfFirst { it.id == item.id }
            if (idx != -1) {
              artItems[idx] = item.copy(likesCount = item.likesCount + 1)
            }
          },
          onSelect = { selectedArtForDetail = item }
        )
      }
    }
  }

  // Artwork Detailed Inspection Dialog
  if (selectedArtForDetail != null) {
    val art = selectedArtForDetail!!
    androidx.compose.ui.window.Dialog(onDismissRequest = { selectedArtForDetail = null }) {
      Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, TnpaRedPrimary)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "ஓவியர் படைப்பு விவரம்",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Black,
              color = TnpaJetBlack
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(TnpaRedPrimary)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(text = art.category, color = TnpaPureWhite, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }

          Text(
            text = art.title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TnpaRedDark
          )

          HorizontalDivider(color = TnpaRedSoft)

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text("ஓவியக் கலைஞர்:", fontSize = 11.sp, color = Color.Gray)
              Text(art.artistName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TnpaJetBlack)
            }
            Column(horizontalAlignment = Alignment.End) {
              Text("மாவட்டம்:", fontSize = 11.sp, color = Color.Gray)
              Text(art.district, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TnpaRedPrimary)
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text("பயன்படுத்திய வண்ணங்கள்:", fontSize = 11.sp, color = Color.Gray)
              Text(art.medium, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TnpaJetBlack)
            }
            Column(horizontalAlignment = Alignment.End) {
              Text("அளவு:", fontSize = 11.sp, color = Color.Gray)
              Text(art.dimensions, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TnpaJetBlack)
            }
          }

          Text(
            text = art.description,
            fontSize = 12.sp,
            color = TnpaCharcoal,
            lineHeight = 18.sp
          )

          Button(
            onClick = { selectedArtForDetail = null },
            modifier = Modifier.fillMaxWidth().height(44.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TnpaJetBlack)
          ) {
            Text("மூடுக (Close)", color = TnpaPureWhite, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

@Composable
fun ArtWorkCard(
  item: ArtItem,
  onLike: () -> Unit,
  onSelect: () -> Unit
) {
  var isLiked by remember { mutableStateOf(false) }

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onSelect() }
      .testTag("art_card_${item.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = TnpaPureWhite),
    border = androidx.compose.foundation.BorderStroke(1.5.dp, TnpaJetBlack.copy(alpha = 0.15f))
  ) {
    Column(
      modifier = Modifier.padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // Header: Artist info and Category Badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(CircleShape)
              .background(TnpaJetBlack),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Brush,
              contentDescription = null,
              tint = TnpaPureWhite,
              modifier = Modifier.size(18.dp)
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = item.artistName,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TnpaJetBlack
              )
              Spacer(modifier = Modifier.width(4.dp))
              Icon(Icons.Default.Verified, contentDescription = null, tint = TnpaGreen, modifier = Modifier.size(14.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.LocationOn, contentDescription = null, tint = TnpaRedDark, modifier = Modifier.size(12.dp))
              Spacer(modifier = Modifier.width(2.dp))
              Text(
                text = item.district,
                fontSize = 11.sp,
                color = TnpaRedDark,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(TnpaRedSoft)
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = item.category,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = TnpaRedPrimary
          )
        }
      }

      // Visual Canvas Art Representation in Red, White and Black
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(140.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(Brush.linearGradient(listOf(TnpaRedPrimary, TnpaRedDark, TnpaJetBlack)))
          .padding(14.dp),
        contentAlignment = Alignment.BottomStart
      ) {
        // Dynamic Art Canvas Graphic
        Canvas(modifier = Modifier.fillMaxSize()) {
          val w = size.width
          val h = size.height

          // Art stroke waves
          val wave1 = Path().apply {
            moveTo(0f, h * 0.6f)
            cubicTo(w * 0.25f, h * 0.3f, w * 0.65f, h * 0.85f, w, h * 0.5f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
          }
          drawPath(
            path = wave1,
            brush = Brush.verticalGradient(listOf(TnpaGold.copy(alpha = 0.5f), TnpaRedDark))
          )

          // Radiant Sun / Art Light
          drawCircle(color = TnpaPureWhite.copy(alpha = 0.85f), radius = 22f, center = Offset(w * 0.8f, h * 0.3f))
        }

        Column {
          Text(
            text = item.title,
            color = TnpaPureWhite,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black
          )
          Text(
            text = "🎨 ${item.medium} • ${item.dimensions}",
            color = TnpaGold,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      // Description
      Text(
        text = item.description,
        fontSize = 12.sp,
        color = TnpaJetBlack,
        lineHeight = 17.sp
      )

      HorizontalDivider(color = TnpaRedSoft)

      // Actions: Like, Details & Share
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          modifier = Modifier
            .clickable {
              if (!isLiked) {
                isLiked = true
                onLike()
              }
            }
            .padding(4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Like",
            tint = if (isLiked) TnpaRedPrimary else TnpaJetBlack,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "${item.likesCount} பாராட்டுக்கள் (Likes)",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (isLiked) TnpaRedPrimary else TnpaJetBlack
          )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(TnpaRedSoft)
              .clickable { onSelect() }
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(text = "விவரம் (Details)", color = TnpaRedPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(TnpaJetBlack)
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Text(text = "பகிர்க (Share)", color = TnpaPureWhite, fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
