package com.sachin.wedplanner.domain.usecase

import com.sachin.wedplanner.data.repo.Repo
import jakarta.inject.Inject

class FetchVenueUseCase @Inject constructor(
    private val repo : Repo
) {
    fun fetchVenue() = repo.fetchVenues()
}