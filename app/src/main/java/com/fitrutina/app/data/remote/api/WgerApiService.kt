package com.fitrutina.app.data.remote.api

import com.fitrutina.app.data.remote.dto.CategoryResponse
import com.fitrutina.app.data.remote.dto.ExerciseResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz de Retrofit para consumir los endpoints de la API pública wger.de
 */
interface WgerApiService {

    /**
     * Obtiene la lista de categorías musculares.
     */
    @GET("exercisecategory/")
    suspend fun getCategories(): CategoryResponse

    /**
     * Obtiene los ejercicios filtrados por categoría.
     * @param categoryId ID de la categoría muscular
     * @param limit Límite de resultados a retornar
     */
    @GET("exercise/")
    suspend fun getExercisesByCategory(
        @Query("category") categoryId: Int,
        @Query("limit") limit: Int = 30
    ): ExerciseResponse
}
