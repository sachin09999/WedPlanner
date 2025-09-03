package com.sachin.wedplanner.domain.usecase

import com.sachin.wedplanner.data.repo.Repo
import jakarta.inject.Inject

class FetchBannerUseCase @Inject constructor(
    private val repo : Repo
) {
    fun fetchBanner() = repo.fetchBanners()
}