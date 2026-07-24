package com.fitrutina.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fitrutina.app.data.local.dao.FavoriteExerciseDao
import com.fitrutina.app.data.local.dao.ProgressPhotoDao
import com.fitrutina.app.data.local.entity.FavoriteExercise
import com.fitrutina.app.data.local.entity.ProgressPhoto

/**
 * Base de datos principal de la aplicación.
 * Contiene las tablas de ejercicios favoritos y fotos de progreso.
 */
@Database(
    entities = [FavoriteExercise::class, ProgressPhoto::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteExerciseDao(): FavoriteExerciseDao
    abstract fun progressPhotoDao(): ProgressPhotoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fitrutina_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
