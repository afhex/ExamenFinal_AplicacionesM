package com.fitrutina.app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO que representa un ejercicio proveniente de la API wger.de
 */
data class ExerciseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val categoryId: Int
)
