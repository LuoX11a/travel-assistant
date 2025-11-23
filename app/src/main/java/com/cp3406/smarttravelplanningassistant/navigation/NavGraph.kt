package com.cp3406.smarttravelplanningassistant.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cp3406.smarttravelplanningassistant.ui.screens.expenses.ExpenseScreen
import com.cp3406.smarttravelplanningassistant.ui.screens.itinerary.ItineraryScreen
import com.cp3406.smarttravelplanningassistant.ui.screens.trips.TripListScreen
import com.cp3406.smarttravelplanningassistant.ui.viewmodel.MainViewModel

sealed class BottomNavItem(val route: String, val label: String) {
    object Trips : BottomNavItem("trips", "Trips")
    object Itinerary : BottomNavItem("itinerary", "Itinerary")
    object Expenses : BottomNavItem("expenses", "Expenses")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val mainViewModel: MainViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Trips.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Trips.route) {
            TripListScreen(
                viewModel = mainViewModel
            )
        }
        composable(BottomNavItem.Itinerary.route) {
            ItineraryScreen(
                viewModel = mainViewModel
            )
        }
        composable(BottomNavItem.Expenses.route) {
            ExpenseScreen(
                viewModel = mainViewModel
            )
        }
    }
}
