package com.fitrutina.app

import android.app.Application
import com.fitrutina.app.data.local.AppDatabase
import com.fitrutina.app.data.preferences.UserPreferencesManager

/**
 * Clase Application que inicializa las dependencias globales de la app.
 * Provee acceso singleton a la base de datos y al manager de preferencias.
 */
class FitRutinaApplication : Application() {

    /** Base de datos Room - se inicializa de forma lazy */
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    /** Manager de preferencias con DataStore - se inicializa de forma lazy */
    val preferencesManager: UserPreferencesManager by lazy {
        UserPreferencesManager(this)
    }
}
