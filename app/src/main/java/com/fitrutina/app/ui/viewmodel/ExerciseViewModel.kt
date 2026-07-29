package com.fitrutina.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fitrutina.app.FitRutinaApplication
import com.fitrutina.app.data.local.entity.FavoriteExercise
import com.fitrutina.app.data.remote.RetrofitClient
import com.fitrutina.app.data.remote.dto.ExerciseCategoryDto
import com.fitrutina.app.data.remote.dto.ExerciseDto
import com.fitrutina.app.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar los datos de ejercicios y categorías musculares.
 * Consume la API REST wger.de mediante Retrofit y expone estados reactivos con UiState.
 */
class ExerciseViewModel(application: Application) : AndroidViewModel(application) {

    private val database = (application as FitRutinaApplication).database
    private val favoriteDao = database.favoriteExerciseDao()
    private val apiService = RetrofitClient.apiService

    /** Flow con la lista de ejercicios favoritos guardados en Room */
    val favorites: Flow<List<FavoriteExercise>> = favoriteDao.getAllFavorites()

    /** Estado reactivo de las categorías musculares (Loading, Success, Error) */
    private val _categoriesState = MutableStateFlow<UiState<List<ExerciseCategoryDto>>>(UiState.Loading)
    val categoriesState: StateFlow<UiState<List<ExerciseCategoryDto>>> = _categoriesState.asStateFlow()

    /** Ejercicio seleccionado para ver su detalle */
    private val _selectedExercise = MutableStateFlow<ExerciseDto?>(null)
    val selectedExercise: StateFlow<ExerciseDto?> = _selectedExercise.asStateFlow()

    /** Estado reactivo de la lista de ejercicios filtrada por categoría */
    private val _exercisesState = MutableStateFlow<UiState<List<ExerciseDto>>>(UiState.Loading)
    val exercisesState: StateFlow<UiState<List<ExerciseDto>>> = _exercisesState.asStateFlow()

    fun selectExercise(exercise: ExerciseDto) {
        _selectedExercise.value = exercise
    }



    init {
        fetchCategories()
    }

    /**
     * Obtiene las categorías de ejercicios desde la API wger.de.
     */
    fun fetchCategories() {
        viewModelScope.launch {
            _categoriesState.value = UiState.Loading
            try {
                val response = apiService.getCategories()
                _categoriesState.value = UiState.Success(response.results)
            } catch (e: Exception) {
                _categoriesState.value = UiState.Error(
                    e.localizedMessage ?: "No se pudo conectar con el servidor de ejercicios."
                )
            }
        }
    }

    /**
     * Obtiene la lista de ejercicios filtrados por categoría desde Retrofit.
     */
    fun fetchExercisesByCategory(categoryId: Int) {
        viewModelScope.launch {
            _exercisesState.value = UiState.Loading
            try {
                val response = apiService.getExercisesByCategory(categoryId = categoryId)
                _exercisesState.value = UiState.Success(response.results)
            } catch (e: Exception) {
                _exercisesState.value = UiState.Error(
                    e.localizedMessage ?: "Error al cargar la lista de ejercicios."
                )
            }
        }
    }
}
