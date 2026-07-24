package com.fitrutina.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fitrutina.app.FitRutinaApplication
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel que gestiona las preferencias del usuario con DataStore.
 * Expone los valores como StateFlow para que la UI se recomponga automáticamente.
 */
class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesManager = (application as FitRutinaApplication).preferencesManager

    /**
     * StateFlow con el estado del modo oscuro.
     * stateIn convierte el Flow de DataStore en un StateFlow con valor inicial.
     */
    val isDarkMode: StateFlow<Boolean> = preferencesManager.isDarkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    /**
     * StateFlow con la unidad de peso seleccionada ("kg" o "lb").
     */
    val weightUnit: StateFlow<String> = preferencesManager.weightUnit
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "kg"
        )

    /** Actualiza la preferencia de modo oscuro */
    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setDarkMode(enabled)
        }
    }

    /** Actualiza la unidad de peso */
    fun setWeightUnit(unit: String) {
        viewModelScope.launch {
            preferencesManager.setWeightUnit(unit)
        }
    }
}
