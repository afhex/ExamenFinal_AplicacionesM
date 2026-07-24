package com.fitrutina.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fitrutina.app.data.local.entity.FavoriteExercise
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operaciones CRUD sobre los ejercicios favoritos.
 * Usa Flow para observar cambios en tiempo real.
 */
@Dao
interface FavoriteExerciseDao {

    @Query("SELECT * FROM favorite_exercises ORDER BY name ASC")
    fun getAllFavorites(): Flow<List<FavoriteExercise>>

    @Query("SELECT * FROM favorite_exercises WHERE id = :exerciseId")
    suspend fun getFavoriteById(exerciseId: Int): FavoriteExercise?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(exercise: FavoriteExercise)

    @Delete
    suspend fun deleteFavorite(exercise: FavoriteExercise)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_exercises WHERE id = :exerciseId)")
    fun isFavorite(exerciseId: Int): Flow<Boolean>
}
