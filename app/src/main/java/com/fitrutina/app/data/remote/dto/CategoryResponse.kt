package com.fitrutina.app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Respuesta paginada de categorías desde la API wger.de
 */
data class CategoryResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<ExerciseCategoryDto>
)
