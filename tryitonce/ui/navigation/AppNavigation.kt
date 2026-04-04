package com.TRY.tryitonce.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.TRY.tryitonce.ui.auth.login.LoginScreen
import com.TRY.tryitonce.ui.auth.register.RegisterScreen
import com.TRY.tryitonce.ui.home.HomeScreen
import com.TRY.tryitonce.ui.home.HomeViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val isLoggedIn by homeViewModel.isLoggedIn.collectAsStateWithLifecycle()

    val startDestination = if (isLoggedIn) Screen.MAIN_GRAPH else Screen.AUTH_GRAPH

    NavHost(navController = navController, startDestination = startDestination) {

        navigation(startDestination = Screen.Login.route, route = Screen.AUTH_GRAPH) {

            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.MAIN_GRAPH) {
                            popUpTo(Screen.AUTH_GRAPH) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                )
            }

            composable(Screen.Register.route) {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate(Screen.MAIN_GRAPH) {
                            popUpTo(Screen.AUTH_GRAPH) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = { navController.popBackStack() },
                )
            }
        }

        navigation(startDestination = Screen.Home.route, route = Screen.MAIN_GRAPH) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onLogout = {
                        navController.navigate(Screen.AUTH_GRAPH) {
                            popUpTo(Screen.MAIN_GRAPH) { inclusive = true }
                        }
                    },
                )
            }
        }
    }
}
