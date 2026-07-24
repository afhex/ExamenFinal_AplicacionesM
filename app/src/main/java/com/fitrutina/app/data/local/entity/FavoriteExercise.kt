package com.fitrutina.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de Room que representa un ejercicio guardado como favorito.
 * Se almacena en la base de datos local para acceso offline.
 */
@Entity(tableName = "favorite_exercises")
data class FavoriteExercise(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val imageUrl: String?
)
