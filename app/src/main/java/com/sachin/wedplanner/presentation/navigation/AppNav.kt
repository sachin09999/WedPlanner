package com.sachin.wedplanner.presentation.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.firebase.auth.FirebaseAuth
import com.sachin.wedplanner.presentation.screens.AllVenuesScreen
import com.sachin.wedplanner.presentation.screens.HomeScreen
import com.sachin.wedplanner.presentation.screens.LoginScreen
import com.sachin.wedplanner.presentation.screens.RegisterScreen
import com.sachin.wedplanner.presentation.screens.VenueDetailScreen
import okhttp3.Route

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNav(
    firebaseAuth: FirebaseAuth
) {

    val navController = rememberNavController()

    val startScreen = if (firebaseAuth.currentUser != null) {
        Routes.HomeScreen
    } else {
        Routes.LoginScreen
    }
    

    Scaffold(
        bottomBar = {


            Log.d("Current Route", currentRoute.toString())
//            val isBottomBarVisible = when (currentRoute) {
//                is Routes.HomeScreen, is Routes.AllVenuesScreen, is Routes.ChecklistScreen -> true
//                else -> false
//            }
//            if (isBottomBarVisible) {
                BottomNavigationBar(navController = navController)
           // }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = startScreen
        ) {

            composable<Routes.HomeScreen> {
                HomeScreen(
                    onVenueCardClick = { venueId ->
                        navController.navigate(Routes.VenueDetailScreen(venueId))
                    },
                    onViewAllVenuesClick = {
                        navController.navigate(Routes.AllVenuesScreen)
                    }
                )
            }

            composable<Routes.LoginScreen> {
                LoginScreen(navController)
            }
            composable<Routes.RegisterScreen> {
                RegisterScreen(navController)
            }

            composable<Routes.VenueDetailScreen> { backStackEntry ->
                val venueDetailRoute = backStackEntry.toRoute<Routes.VenueDetailScreen>()
                VenueDetailScreen(
                    venueId = venueDetailRoute.venueId,
                    onBack = {
                        navController.popBackStack()
                    })
            }

            composable<Routes.AllVenuesScreen> {
                AllVenuesScreen(
                    onVenueClick = {
                        navController.navigate(Routes.VenueDetailScreen(it))
                    },
                    navController = navController
                )
            }

            composable<Routes.ChecklistScreen> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(text = "Checklist Screen (Under Construction)")
                }
            }

        }
    }

}