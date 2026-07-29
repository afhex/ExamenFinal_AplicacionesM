package com.fitrutina.app

import android.app.Application
import com.fitrutina.app.data.local.AppDatabase
import com.fitrutina.app.data.preferences.UserPreferencesManager
import com.fitrutina.app.data.remote.RetrofitClient
import com.fitrutina.app.data.repository.ExerciseRepository
import com.fitrutina.app.data.repository.ExerciseRepositoryImpl

/**
 * Clase Application que inicializa las dependencias globales de la app.
 * Provee el repositorio central como fuente única de verdad.
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

    /** Repositorio central que combina Room y Retrofit */
    val exerciseRepository: ExerciseRepository by lazy {
        ExerciseRepositoryImpl(
            apiService = RetrofitClient.apiService,
            favoriteDao = database.favoriteExerciseDao()
        )
    }
}
