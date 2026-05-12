package com.kelompok3.oceana.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kelompok3.oceana.ui.screen.home.HomeScreen

object OceanaRoute {
    const val HOME    = "home"
    const val EXPLORE = "explore"
    const val ATLANTIS = "atlantis"
    const val MARINE_LIFE = "marine_life"
    const val AUTH    = "auth"
    const val PROFILE = "profile"
}

@Composable
fun OceanaNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = OceanaRoute.HOME
    ) {
        composable(OceanaRoute.HOME) {
            HomeScreen()
        }

        // Abi — Atlantis
        // composable(OceanaRoute.ATLANTIS) {
        //     AtlantisScreen(navController = navController)
        // }

        // Bagas — Marine Life
        // composable(OceanaRoute.MARINE_LIFE) {
        //     MarineLifeScreen(navController = navController)
        // }

        // Thoriq — Auth & Profile
        // composable(OceanaRoute.AUTH) {
        //     AuthScreen(navController = navController)
        // }
        // composable(OceanaRoute.PROFILE) {
        //     ProfileScreen(navController = navController)
        // }
    }
}