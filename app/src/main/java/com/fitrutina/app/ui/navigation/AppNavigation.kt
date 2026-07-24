package com.fitrutina.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fitrutina.app.ui.screens.FavoritesScreen
import com.fitrutina.app.ui.screens.HomeScreen
import com.fitrutina.app.ui.screens.SettingsScreen
import com.fitrutina.app.ui.viewmodel.ExerciseViewModel
import com.fitrutina.app.ui.viewmodel.SettingsViewModel

/**
 * Composable principal que define la navegación de la app.
 * Incluye un Scaffold con BottomNavigationBar y un NavHost con las rutas.
 */
@Composable
fun AppNavigation(
    exerciseViewModel: ExerciseViewModel,
    settingsViewModel: SettingsViewModel
) {
    val navController = rememberNavController()
    val screens = listOf(Screen.Home, Screen.Favorites, Screen.Settings)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(viewModel = exerciseViewModel)
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(viewModel = exerciseViewModel)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(viewModel = settingsViewModel)
            }
        }
    }
}
