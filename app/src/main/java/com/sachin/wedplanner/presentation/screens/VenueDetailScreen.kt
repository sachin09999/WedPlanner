package com.sachin.wedplanner.presentation.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.sachin.wedplanner.R
import com.sachin.wedplanner.domain.model.Venue
import com.sachin.wedplanner.presentation.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenueDetailScreen(
    venueId: String,
    viewModel: AppViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val venuesState by viewModel.venueState.collectAsStateWithLifecycle()
    var venue by remember { mutableStateOf<Venue?>(null) }

    // Use LaunchedEffect to find the venue once the venues list is available
    LaunchedEffect(venuesState.venues) {
        if (!venuesState.isLoading && venuesState.error == null) {
            venue = venuesState.venues.find { it.id == venueId }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = venue?.name ?: "Venue Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD72660),
                    scrolledContainerColor = Color(0xFFD72660)
                )
            )
        }
    ) { paddingValues ->
        // Handle different states
        when {
            venuesState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFD72660))
                }
            }
            venuesState.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${venuesState.error}", color = Color.Red, fontSize = 16.sp)
                }
            }
            venue == null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Venue not found.", fontSize = 16.sp, color = Color.Gray)
                }
            }
            else -> {
                // Pass the non-null venue object to a separate composable
                VenueDetailsContent(
                    venue = venue,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun VenueDetailsContent(venue: Venue?, modifier: Modifier = Modifier) {
    // This composable is only called when venue is not null
    // It's a good practice to separate content for better readability
    if (venue == null) return // Safety check, though the parent `when` should handle this

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Hero Image
        AsyncImage(
            model = venue.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp),
            contentScale = ContentScale.Crop
        )

        // Venue Details Section
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color.White,
            shadowElevation = 4.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = venue.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "Location",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = venue.location,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.rupee),
                        contentDescription = "Price",
                        tint = Color(0xFFD72660),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = venue.price,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.width(24.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.people),
                        contentDescription = "Capacity",
                        tint = Color(0xFFD72660),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${venue.capacity} guests",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "About this Venue",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "The Grand Imperial is a premier wedding destination known for its breathtaking architecture and sprawling gardens. With elegant banquet halls and top-tier services, it provides the perfect backdrop for your dream wedding. Our team is dedicated to crafting a personalized experience, ensuring every detail is perfect for your special day.",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}