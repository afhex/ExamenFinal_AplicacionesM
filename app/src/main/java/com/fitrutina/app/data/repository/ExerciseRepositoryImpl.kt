package com.fitrutina.app.data.repository

import com.fitrutina.app.data.local.dao.FavoriteExerciseDao
import com.fitrutina.app.data.local.dao.ProgressPhotoDao
import com.fitrutina.app.data.local.entity.FavoriteExercise
import com.fitrutina.app.data.local.entity.ProgressPhoto
import com.fitrutina.app.data.remote.api.WgerApiService
import com.fitrutina.app.data.remote.dto.ExerciseCategoryDto
import com.fitrutina.app.data.remote.dto.ExerciseDto
import kotlinx.coroutines.flow.Flow

/**
 * Implementación concreta del patrón Repositorio.
 * Coordina las llamadas entre el servicio Retrofit (API) y el DAO de Room (Base de Datos).
 */
class ExerciseRepositoryImpl(
    private val apiService: WgerApiService,
    private val favoriteDao: FavoriteExerciseDao,
    private val progressPhotoDao: ProgressPhotoDao
) : ExerciseRepository {

    override suspend fun getCategories(): List<ExerciseCategoryDto> {
        val response = apiService.getCategories()
        return response.results
    }

    override suspend fun getExercisesByCategory(categoryId: Int): List<ExerciseDto> {
        val response = apiService.getExercisesByCategory(categoryId = categoryId)
        return response.results
    }

    override fun getFavoriteExercises(): Flow<List<FavoriteExercise>> {
        return favoriteDao.getAllFavorites()
    }

    override suspend fun addFavorite(exercise: FavoriteExercise) {
        favoriteDao.insertFavorite(exercise)
    }

    override suspend fun removeFavorite(exercise: FavoriteExercise) {
        favoriteDao.deleteFavorite(exercise)
    }

    override fun isFavorite(exerciseId: Int): Flow<Boolean> {
        return favoriteDao.isFavorite(exerciseId)
    }

    override fun getProgressPhotos(): Flow<List<ProgressPhoto>> {
        return progressPhotoDao.getAllPhotos()
    }

    override suspend fun addProgressPhoto(photo: ProgressPhoto) {
        progressPhotoDao.insertPhoto(photo)
    }

    override suspend fun deleteProgressPhoto(photo: ProgressPhoto) {
        progressPhotoDao.deletePhoto(photo)
    }
}
