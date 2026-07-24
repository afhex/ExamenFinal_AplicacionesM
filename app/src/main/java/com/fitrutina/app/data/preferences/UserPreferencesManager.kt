package com.fitrutina.app.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Se define a nivel de archivo para garantizar una sola instancia del DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

/**
 * Manager que encapsula el acceso a DataStore para las preferencias del usuario.
 * Almacena configuraciones simples como el modo oscuro y la unidad de peso.
 */
class UserPreferencesManager(private val context: Context) {

    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val WEIGHT_UNIT_KEY = stringPreferencesKey("weight_unit")
    }

    /**
     * Flow que emite el estado actual del modo oscuro.
     * Por defecto es false (modo claro).
     */
    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    /**
     * Flow que emite la unidad de peso seleccionada.
     * Por defecto es "kg".
     */
    val weightUnit: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[WEIGHT_UNIT_KEY] ?: "kg"
    }

    /**
     * Guarda la preferencia de modo oscuro en DataStore.
     */
    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    /**
     * Guarda la unidad de peso seleccionada en DataStore.
     * @param unit "kg" o "lb"
     */
    suspend fun setWeightUnit(unit: String) {
        context.dataStore.edit { preferences ->
            preferences[WEIGHT_UNIT_KEY] = unit
        }
    }
}
