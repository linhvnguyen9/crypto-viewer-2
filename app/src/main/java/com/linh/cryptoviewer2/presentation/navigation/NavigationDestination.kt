package com.linh.cryptoviewer2.presentation.navigation

sealed class NavigationDestination(val route: String) {
    object Home: NavigationDestination("home")
    object Watchlist: NavigationDestination("watchlist")
}