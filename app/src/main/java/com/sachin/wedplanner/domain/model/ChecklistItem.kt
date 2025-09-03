package com.sachin.wedplanner.domain.model

data class ChecklistItem(
    val id: String = "",
    val title: String = "",
    val done: Boolean = false
)