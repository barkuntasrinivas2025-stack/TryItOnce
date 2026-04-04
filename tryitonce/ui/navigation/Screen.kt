package com.TRY.tryitonce.ui.navigation

sealed class Screen(val route: String) {
    data object Login    : Screen("login")
    data object Register : Screen("register")
    data object Home     : Screen("home")

    companion object {
        const val AUTH_GRAPH = "auth_graph"
        const val MAIN_GRAPH = "main_graph"
    }
}
