package com.fitrutina.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fitrutina.app.FitRutinaApplication
import com.fitrutina.app.data.local.entity.FavoriteExercise
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel para gestionar los datos de ejercicios.
 * En Fase 1: solo expone los favoritos desde Room.
 * En Fase 2: se agregará la carga desde la API.
 * En Fase 3: se conectará a través del Repository.
 */
class ExerciseViewModel(application: Application) : AndroidViewModel(application) {

    private val database = (application as FitRutinaApplication).database
    private val favoriteDao = database.favoriteExerciseDao()

    /** Flow con la lista de ejercicios favoritos guardados en Room */
    val favorites: Flow<List<FavoriteExercise>> = favoriteDao.getAllFavorites()
}
