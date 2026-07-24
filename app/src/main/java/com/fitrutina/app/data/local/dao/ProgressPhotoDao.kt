package com.fitrutina.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.fitrutina.app.data.local.entity.ProgressPhoto
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operaciones sobre las fotos de progreso.
 */
@Dao
interface ProgressPhotoDao {

    @Query("SELECT * FROM progress_photos ORDER BY date DESC")
    fun getAllPhotos(): Flow<List<ProgressPhoto>>

    @Insert
    suspend fun insertPhoto(photo: ProgressPhoto)

    @Delete
    suspend fun deletePhoto(photo: ProgressPhoto)
}
