package com.example.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


object Destinations {
    const val HOME = "home"
    const val DETAIL = "detail/{message}"
    const val PROFILE = "profile"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destinations.HOME) {
        composable(Destinations.PROFILE) {
            ProfileScreen(navController)
        }
        composable(Destinations.HOME) {
            HomeScreen(navController)
        }
        composable(
            route = Destinations.DETAIL,
            arguments = listOf(navArgument("message") { type = NavType.StringType })
        ) { backStackEntry ->
            DetailScreen(backStackEntry.arguments?.getString("message")
            )
        }
        composable("not_found") {
            NotFoundScreen(navController)
        }
    }
}