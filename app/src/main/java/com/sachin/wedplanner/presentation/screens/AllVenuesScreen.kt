package com.sachin.wedplanner.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sachin.wedplanner.domain.model.Venue
import com.sachin.wedplanner.presentation.AppViewModel
import com.sachin.wedplanner.presentation.utils.FilterSection
import com.sachin.wedplanner.presentation.utils.VenueListCard
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllVenuesScreen(
    viewModel: AppViewModel = hiltViewModel(),
    onVenueClick: (String) -> Unit,
    navController : NavController
) {
    val venuesState by viewModel.venueState.collectAsStateWithLifecycle()

    // Filter states
    var selectedBudget by remember { mutableStateOf("All Budgets") }
    var selectedCapacity by remember { mutableStateOf("All Capacities") }


    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                Icon(
                    Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    modifier = Modifier.clickable{
                        navController.popBackStack()
                    }
                )
            },
                title = {
                    Text(
                        text = "All Venues",
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        color = Color(0xFFD72660)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFF0F3) // Match your theme
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Your filter section
            FilterSection(
                onBudgetSelected = { budget -> selectedBudget = budget },
                onCapacitySelected = { capacity -> selectedCapacity = capacity }
            )

            when {
                venuesState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFFD72660))
                    }
                }
                venuesState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: ${venuesState.error}",
                            fontSize = 16.sp,
                            color = Color.Red
                        )
                    }
                }
                else -> {
                    // Data is successfully loaded, now apply filters
                    val filteredVenues = venuesState.venues.filter { venue ->
                        passesBudgetFilter(venue.price, selectedBudget) && passesCapacityFilter(venue.capacity, selectedCapacity)
                    }

                    if (filteredVenues.isEmpty()) {
                        // This message is shown only when data has loaded but no items match the filter.
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No venues found matching your criteria.",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    } else {
                        VenueListAll(
                            venues = filteredVenues,
                            onVenueClick = { venue -> onVenueClick(venue.id) }
                        )
                    }
                }
            }
        }
    }
}

// ... (Rest of the code, including VenueListAll, passesBudgetFilter, and passesCapacityFilter)

@Composable
fun VenueListAll(venues: List<Venue>, onVenueClick: (Venue) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        items(venues) { venue ->
            VenueListCard(venue = venue, onClick = { onVenueClick(venue) })
        }
    }
}

// Helper function to check if a venue passes the budget filter
private fun passesBudgetFilter(price: String, filter: String): Boolean {
    if (filter == "All Budgets") return true

    val priceInLakhs = price.substringAfterLast(" - ").replace(" Lakhs", "").toFloatOrNull() ?: return false

    return when (filter) {
        "< ₹20 Lakhs" -> priceInLakhs < 20
        "₹20-50 Lakhs" -> priceInLakhs in 20f..50f
        "₹50-100 Lakhs" -> priceInLakhs in 50f..100f
        "> ₹100 Lakhs" -> priceInLakhs > 100
        else -> false
    }
}

// Helper function to check if a venue passes the capacity filter
private fun passesCapacityFilter(capacity: String, filter: String): Boolean {
    if (filter == "All Capacities") return true

    val capacityInt = capacity.toIntOrNull() ?: return false

    return when (filter) {
        "< 250" -> capacityInt < 250
        "250-500" -> capacityInt in 250..500
        "500-1000" -> capacityInt in 500..1000
        "> 1000" -> capacityInt > 1000
        else -> false
    }
}