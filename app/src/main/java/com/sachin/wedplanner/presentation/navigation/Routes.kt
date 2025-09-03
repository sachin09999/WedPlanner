package com.sachin.wedplanner.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    object LoginScreen : Routes()

    @Serializable
    object RegisterScreen : Routes()

    @Serializable
    object HomeScreen : Routes()

    @Serializable
    data class VenueDetailScreen(val venueId: String) : Routes()

    @Serializable
    object AllVenuesScreen : Routes()

    @Serializable
    object ChecklistScreen : Routes()


}