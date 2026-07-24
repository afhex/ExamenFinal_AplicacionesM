package com.fitrutina.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de Room que representa una foto de progreso del usuario.
 * Almacena la URI de la foto, la fecha y una nota opcional.
 */
@Entity(tableName = "progress_photos")
data class ProgressPhoto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val photoUri: String,
    val date: Long = System.currentTimeMillis(),
    val note: String? = null
)
