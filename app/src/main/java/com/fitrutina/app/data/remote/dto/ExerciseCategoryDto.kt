package com.fitrutina.app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO que representa una categoría muscular proveniente de la API wger.de
 */
data class ExerciseCategoryDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
