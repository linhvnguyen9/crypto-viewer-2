package com.linh.cryptoviewer2.presentation.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NavigatorImpl @Inject constructor(): Navigator {
    override val startDestination: NavigationDestination = NavigationDestination.Home

    private val _commands = MutableStateFlow(NavigationCommand(startDestination))
    override val commands: StateFlow<NavigationCommand>
        get() = _commands

    override fun navigate(command: NavigationCommand) {
        _commands.value = command
    }

    override fun navigateToHome() {
        _commands.value = NavigationCommand(NavigationDestination.Home)
    }

    override fun navigateToWatchlist() {
        _commands.value = NavigationCommand(NavigationDestination.Watchlist)
    }
}