package com.sachin.wedplanner.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.sachin.wedplanner.R
import com.sachin.wedplanner.domain.model.Venue
import com.sachin.wedplanner.presentation.AppViewModel
import com.sachin.wedplanner.presentation.utils.BannerSlideshow
import com.sachin.wedplanner.presentation.utils.VenueCard
import com.sachin.wedplanner.presentation.utils.VenueCarousel

@Composable
fun HomeScreen(
    userName: String = "Priya",
    viewModel: AppViewModel = hiltViewModel(),
    onVenueCardClick: (String) -> Unit = {},
    onViewAllVenuesClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var search by remember { mutableStateOf("") }
    val bannersState by viewModel.bannersState.collectAsStateWithLifecycle()
    val venuesState by viewModel.venueState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF0F3)) // very light blush
    ) {
        // Top pink wavy background and bubbles, as before...
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            val w = size.width
            val h = size.height
            drawPath(
                path = Path().apply {
                    moveTo(0f, h * 0.7f)
                    cubicTo(w * 0.25f, h * 0.9f, w * 0.75f, h * 0.5f, w, h * 0.78f)
                    lineTo(w, 0f)
                    lineTo(0f, 0f)
                    close()
                },
                color = Color(0xFFFFC3CC)
            )
            val bubbleColor = Color(0xFFFFD6DC).copy(alpha = 0.6f)
            drawCircle(bubbleColor, radius = w * 0.07f, center = Offset(w * 0.15f, h * 0.35f))
            drawCircle(bubbleColor, radius = w * 0.04f, center = Offset(w * 0.7f, h * 0.22f))
            drawCircle(bubbleColor, radius = w * 0.05f, center = Offset(w * 0.55f, h * 0.55f))
            drawCircle(bubbleColor, radius = w * 0.025f, center = Offset(w * 0.85f, h * 0.43f))
        }

        // Foreground content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            // Welcome header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Good Morning,", color = Color(0xFF020202), fontSize = 18.sp)
                    Text(
                        userName,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 34.sp,
                        color = Color(0xFF000000)
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(52.dp)
                        .clickable{}
                )
            }

            Spacer(Modifier.height(24.dp))

            // Feature horizontal scroll row (with images and labels)
            val vendorFeatures = listOf(
                VendorFeature("Venue", R.drawable.venue),
                VendorFeature("Makeup", R.drawable.makeup),
                VendorFeature("Photographers", R.drawable.photographer),
                VendorFeature("Decor", R.drawable.decor),
                VendorFeature("Catering", R.drawable.catering),
                VendorFeature("Henna Artist", R.drawable.heena),
                VendorFeature("Bridal Wear", R.drawable.bridal),
                VendorFeature("Car Rental", R.drawable.car)
            )

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Search Vendors and Categories") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                shape = RoundedCornerShape(50.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            VendorGrid(vendorFeatures, modifier = Modifier.fillMaxWidth())
            BannerSlideshow(banners = bannersState.banners)
            // Here is the VenueList section
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Popular Venues",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                TextButton(onClick = { onViewAllVenuesClick() }) {
                    Text("View All", color = Color(0xFFD72660))
                }
            }
            VenueCarousel(
                venues = venuesState.venues,
                onVenueClick = { venueId -> onVenueCardClick(venueId) }
            )
        }

    }
}

data class VendorFeature(val label: String, val imageRes: Int)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VendorGrid(
    features: List<VendorFeature>,
    modifier: Modifier = Modifier,
    onFeatureClick: (VendorFeature) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        items(features) { feature ->
            Column(
                modifier = Modifier
                    .width(70.dp)
                    .clickable { onFeatureClick(feature) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = feature.imageRes),
                    contentDescription = feature.label,
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(
                    feature.label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2
                )
            }
        }
    }
}




