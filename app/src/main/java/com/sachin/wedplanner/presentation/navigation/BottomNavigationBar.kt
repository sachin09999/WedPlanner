package com.sachin.wedplanner.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute

data class BottomNavItem(
    val route: Routes,
    val label: String,
    val icon: ImageVector
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(Routes.HomeScreen, "Dashboard", Icons.Default.Home),
        BottomNavItem(Routes.AllVenuesScreen, "Venues", Icons.Default.List),
        BottomNavItem(Routes.ChecklistScreen, "Checklist", Icons.Default.Checklist)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route.toString(),
                onClick = {
                    if (currentRoute != item.route.toString()) {
                        navController.navigate(item.route)
                    }
                }
            )

        }
    }
}