package com.kelompok3.oceana.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kelompok3.oceana.ui.screen.auth.AuthViewModel
import com.kelompok3.oceana.ui.screen.auth.login.LoginScreen
import com.kelompok3.oceana.ui.screen.auth.register.RegisterScreen
import com.kelompok3.oceana.ui.screen.home.HomeScreen
import com.kelompok3.oceana.ui.screen.MarineLife.MarineLifeScreen
import com.kelompok3.oceana.ui.screen.MarineLife.TheReefScreen
import com.kelompok3.oceana.ui.screen.profile.ProfileScreen

object OceanaRoute {
    const val HOME        = "home"
    const val EXPLORE     = "explore"
    const val ATLANTIS    = "atlantis"
    const val ATLANTIS_DETAIL = "atlantis_detail"
    const val MARINE_LIFE = "marine_life"
    const val THE_REEF    = "the_reef"        // ← Route baru
    const val AUTH        = "auth"
    const val LOGIN       = "login"
    const val REGISTER    = "register"
    const val PROFILE     = "profile"
}

@Composable
fun OceanaNavGraph() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController    = navController,
        startDestination = OceanaRoute.LOGIN
    ) {
        composable(OceanaRoute.LOGIN) {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(OceanaRoute.REGISTER) {
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(OceanaRoute.HOME) {
            HomeScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(OceanaRoute.PROFILE) {
            ProfileScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Bagas — Marine Life
        composable(OceanaRoute.MARINE_LIFE) {
            MarineLifeScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // The Reef — Chapter 1 of Marine Life
        composable(OceanaRoute.THE_REEF) {
            TheReefScreen(navController = navController)
        }

        // Abi — Atlantis
        composable(OceanaRoute.ATLANTIS) {
            com.kelompok3.oceana.ui.screen.Atlantis.AtlantisScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(OceanaRoute.ATLANTIS_DETAIL) {
            com.kelompok3.oceana.ui.screen.Atlantis.AtlantisDetailScreen(
                navController = navController
            )
        }
    }
}