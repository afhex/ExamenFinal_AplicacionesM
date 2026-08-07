package com.fitrutina.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Define las rutas de navegación de la app.
 * Cada pantalla tiene una ruta, un título y un ícono opcional para el BottomNavBar.
 */
sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    data object Home : Screen(
        route = "home",
        title = "Inicio",
        icon = Icons.Default.Home
    )

    data object Favorites : Screen(
        route = "favorites",
        title = "Favoritos",
        icon = Icons.Default.FavoriteBorder
    )

    data object AddProgress : Screen(
        route = "add_progress",
        title = "Progreso",
        icon = Icons.Default.CameraAlt
    )

    data object Settings : Screen(
        route = "settings",
        title = "Ajustes",
        icon = Icons.Default.Settings
    )

    data object ExerciseList : Screen(
        route = "exercise_list/{categoryId}/{categoryName}",
        title = "Ejercicios",
        icon = Icons.Default.FitnessCenter
    ) {
        fun createRoute(categoryId: Int, categoryName: String): String {
            return "exercise_list/$categoryId/$categoryName"
        }
    }

    data object ExerciseDetail : Screen(
        route = "exercise_detail/{categoryName}",
        title = "Detalle de Ejercicio",
        icon = Icons.Default.Info
    ) {
        fun createRoute(categoryName: String): String {
            return "exercise_detail/$categoryName"
        }
    }
}
