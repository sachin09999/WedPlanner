package com.sachin.wedplanner.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sachin.wedplanner.domain.model.Venue

@Composable
fun FilterSection(
    onBudgetSelected: (String) -> Unit,
    onCapacitySelected: (String) -> Unit
) {
    val budgetOptions = listOf(
        "All Budgets",
        "< ₹20 Lakhs",
        "₹20-50 Lakhs",
        "₹50-100 Lakhs",
        "> ₹100 Lakhs"
    )
    val capacityOptions = listOf(
        "All Capacities",
        "< 250",
        "250-500",
        "500-1000",
        "> 1000"
    )

    var selectedBudget by remember { mutableStateOf(budgetOptions[0]) }
    var selectedCapacity by remember { mutableStateOf(capacityOptions[0]) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Budget Filter Chips
        Text(text = "Budget", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(budgetOptions) { option ->
                FilterChip(
                    text = option,
                    isSelected = option == selectedBudget,
                    onClick = {
                        selectedBudget = option
                        onBudgetSelected(option)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Capacity Filter Chips
        Text(text = "Capacity", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(capacityOptions) { option ->
                FilterChip(
                    text = option,
                    isSelected = option == selectedCapacity,
                    onClick = {
                        selectedCapacity = option
                        onCapacitySelected(option)
                    }
                )
            }
        }
    }
}

@Composable
fun FilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFFD72660) else Color.LightGray.copy(alpha = 0.5f)
    val textColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}