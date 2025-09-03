package com.sachin.wedplanner.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Venue(
    val id: String = "",
    val name: String = "",
    val location: String = "",
    val price: String = "",
    val capacity: String = "",
    val imageUrl: String = ""
)