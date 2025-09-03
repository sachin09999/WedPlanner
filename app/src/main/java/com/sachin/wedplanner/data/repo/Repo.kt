package com.sachin.wedplanner.data.repo

import com.sachin.wedplanner.common.ResultState
import com.sachin.wedplanner.domain.model.Banner
import com.sachin.wedplanner.domain.model.Venue
import kotlinx.coroutines.flow.Flow

interface Repo {
    fun registerUser(name: String, email: String, password: String): Flow<ResultState<String>>
    fun loginUser(email: String, password: String): Flow<ResultState<String>>
    fun fetchVenues(): Flow<ResultState<List<Venue>>>
    fun fetchBanners(): Flow<ResultState<List<Banner>>>

}