package com.fitrutina.app.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.fitrutina.app.FitRutinaApplication
import com.fitrutina.app.data.local.entity.FavoriteExercise
import com.fitrutina.app.data.remote.RetrofitClient
import com.fitrutina.app.data.remote.dto.ExerciseCategoryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar los datos de ejercicios y categorías musculares.
 * Consume la API REST wger.de mediante Retrofit y expone el estado a la UI.
 */
class ExerciseViewModel(application: Application) : AndroidViewModel(application) {

    private val database = (application as FitRutinaApplication).database
    private val favoriteDao = database.favoriteExerciseDao()
    private val apiService = RetrofitClient.apiService

    /** Flow con la lista de ejercicios favoritos guardados en Room */
    val favorites: Flow<List<FavoriteExercise>> = favoriteDao.getAllFavorites()

    /** Lista de categorías musculares obtenidas desde la API */
    private val _categories = MutableStateFlow<List<ExerciseCategoryDto>>(emptyList())
    val categories: StateFlow<List<ExerciseCategoryDto>> = _categories.asStateFlow()

    init {
        fetchCategories()
    }

    /**
     * Obtiene las categorías de ejercicios desde la API wger.de usando Corrutinas.
     */
    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = apiService.getCategories()
                _categories.value = response.results
            } catch (e: Exception) {
                // En caso de error de red, mantiene la lista previa
                _categories.value = emptyList()
            }
        }
    }
}

