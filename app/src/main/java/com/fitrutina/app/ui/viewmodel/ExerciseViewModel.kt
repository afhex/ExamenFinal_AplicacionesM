package com.fitrutina.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fitrutina.app.FitRutinaApplication
import com.fitrutina.app.data.local.entity.FavoriteExercise
import com.fitrutina.app.data.local.entity.ProgressPhoto
import com.fitrutina.app.data.remote.dto.ExerciseCategoryDto
import com.fitrutina.app.data.remote.dto.ExerciseDto
import com.fitrutina.app.data.repository.ExerciseRepository
import com.fitrutina.app.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel principal de la aplicación que cumple estrictamente con Clean Architecture.
 * El ViewModel NUNCA accede directamente a los DAOs de Room ni al cliente de Retrofit.
 * Todo el flujo de datos transita obligatoriamente a través de [ExerciseRepository].
 */
class ExerciseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExerciseRepository = (application as FitRutinaApplication).exerciseRepository

    /** Flow observable de ejercicios favoritos persistidos en Room vía Repository */
    val favorites: Flow<List<FavoriteExercise>> = repository.getFavoriteExercises()

    /** Flow observable de fotos de progreso del usuario */
    val progressPhotos: Flow<List<ProgressPhoto>> = repository.getProgressPhotos()

    /** Estado reactivo de las categorías musculares (Loading, Success, Error) */
    private val _categoriesState = MutableStateFlow<UiState<List<ExerciseCategoryDto>>>(UiState.Loading)
    val categoriesState: StateFlow<UiState<List<ExerciseCategoryDto>>> = _categoriesState.asStateFlow()

    /** Ejercicio actualmente seleccionado */
    private val _selectedExercise = MutableStateFlow<ExerciseDto?>(null)
    val selectedExercise: StateFlow<ExerciseDto?> = _selectedExercise.asStateFlow()

    /** Estado reactivo de la lista de ejercicios filtrados */
    private val _exercisesState = MutableStateFlow<UiState<List<ExerciseDto>>>(UiState.Loading)
    val exercisesState: StateFlow<UiState<List<ExerciseDto>>> = _exercisesState.asStateFlow()

    fun selectExercise(exercise: ExerciseDto) {
        _selectedExercise.value = exercise
    }

    init {
        fetchCategories()
    }

    /**
     * Obtiene las categorías de ejercicios a través de [ExerciseRepository].
     */
    fun fetchCategories() {
        viewModelScope.launch {
            _categoriesState.value = UiState.Loading
            try {
                val categories = repository.getCategories()
                _categoriesState.value = UiState.Success(categories)
            } catch (e: Exception) {
                _categoriesState.value = UiState.Error(
                    e.localizedMessage ?: "No se pudo conectar con el servidor de ejercicios."
                )
            }
        }
    }

    /**
     * Obtiene la lista de ejercicios filtrados por categoría a través de [ExerciseRepository].
     */
    fun fetchExercisesByCategory(categoryId: Int) {
        viewModelScope.launch {
            _exercisesState.value = UiState.Loading
            try {
                val exercises = repository.getExercisesByCategory(categoryId = categoryId)
                _exercisesState.value = UiState.Success(exercises)
            } catch (e: Exception) {
                _exercisesState.value = UiState.Error(
                    e.localizedMessage ?: "Error al cargar la lista de ejercicios."
                )
            }
        }
    }

    /**
     * Consulta el estado de favorito a través de [ExerciseRepository].
     */
    fun isFavorite(exerciseId: Int): Flow<Boolean> {
        return repository.isFavorite(exerciseId)
    }

    /**
     * Guarda o elimina un ejercicio de favoritos a través de [ExerciseRepository].
     */
    fun toggleFavorite(exercise: ExerciseDto, categoryName: String, isCurrentlyFavorite: Boolean) {
        viewModelScope.launch {
            val favoriteEntity = FavoriteExercise(
                id = exercise.id,
                name = exercise.name,
                description = exercise.description,
                category = categoryName,
                imageUrl = null
            )
            if (isCurrentlyFavorite) {
                repository.removeFavorite(favoriteEntity)
            } else {
                repository.addFavorite(favoriteEntity)
            }
        }
    }

    /**
     * Elimina una entidad de favorito directamente desde [ExerciseRepository].
     */
    fun removeFavorite(favorite: FavoriteExercise) {
        viewModelScope.launch {
            repository.removeFavorite(favorite)
        }
    }

    /**
     * Guarda una foto de progreso en Room a través de [ExerciseRepository].
     */
    fun saveProgressPhoto(photoUri: String, note: String?) {
        viewModelScope.launch {
            val photo = ProgressPhoto(
                photoUri = photoUri,
                note = note
            )
            repository.addProgressPhoto(photo)
        }
    }

    /**
     * Elimina una foto de progreso de Room a través de [ExerciseRepository].
     */
    fun deleteProgressPhoto(photo: ProgressPhoto) {
        viewModelScope.launch {
            repository.deleteProgressPhoto(photo)
        }
    }
}
