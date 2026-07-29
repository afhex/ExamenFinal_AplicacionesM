package com.fitrutina.app.data.repository

import com.fitrutina.app.data.local.entity.FavoriteExercise
import com.fitrutina.app.data.remote.dto.ExerciseCategoryDto
import com.fitrutina.app.data.remote.dto.ExerciseDto
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz del Repositorio que actúa como fuente única de verdad para la aplicación.
 * Abstrae las fuentes de datos remotas (Retrofit) y locales (Room).
 */
interface ExerciseRepository {
    /** Obtiene las categorías musculares desde la API remota */
    suspend fun getCategories(): List<ExerciseCategoryDto>

    /** Obtiene los ejercicios por categoría desde la API remota */
    suspend fun getExercisesByCategory(categoryId: Int): List<ExerciseDto>

    /** Obtiene el flujo en tiempo real de ejercicios favoritos guardados en Room */
    fun getFavoriteExercises(): Flow<List<FavoriteExercise>>

    /** Guarda un ejercicio en favoritos (Room) */
    suspend fun addFavorite(exercise: FavoriteExercise)

    /** Elimina un ejercicio de favoritos (Room) */
    suspend fun removeFavorite(exercise: FavoriteExercise)

    /** Verifica si un ejercicio es favorito */
    fun isFavorite(exerciseId: Int): Flow<Boolean>
}
