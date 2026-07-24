package com.fitrutina.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fitrutina.app.ui.navigation.AppNavigation
import com.fitrutina.app.ui.theme.FitRutinaTheme
import com.fitrutina.app.ui.viewmodel.ExerciseViewModel
import com.fitrutina.app.ui.viewmodel.SettingsViewModel

/**
 * Activity principal de la aplicación.
 * Configura el tema basado en las preferencias del usuario
 * y muestra la navegación principal.
 */
class MainActivity : ComponentActivity() {

    // ViewModels creados con el delegado by viewModels()
    private val exerciseViewModel: ExerciseViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Observar la preferencia de modo oscuro desde DataStore
            val isDarkMode by settingsViewModel.isDarkMode.collectAsStateWithLifecycle()

            FitRutinaTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        exerciseViewModel = exerciseViewModel,
                        settingsViewModel = settingsViewModel
                    )
                }
            }
        }
    }
}
