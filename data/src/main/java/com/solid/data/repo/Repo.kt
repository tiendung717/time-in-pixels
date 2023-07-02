package com.solid.data.repo

import com.solid.common.network.safeApiCall
import com.solid.data.database.AppDatabase
import com.solid.data.domain.Pixel
import com.solid.data.mapper.PixelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(
    private val db: AppDatabase,
    private val pixelMapper: PixelMapper
) {

    fun getAllPixels() : Flow<List<Pixel>> {
        return db.daoPixel().getAll().map {
            pixelMapper.fromEntities(it)
        }
    }

    fun getPixels(startDate: LocalDate, endDate: LocalDate): Flow<List<Pixel>> {
        return db.daoPixel().getTimeInPixel(
            start = startDate,
            end = endDate
        ).map {
            pixelMapper.fromEntities(it)
        }
    }

    suspend fun savePixel(pixel: Pixel) = safeApiCall {
        db.daoPixel().save(pixelMapper.fromDomain(pixel))
    }

    suspend fun updatePixel(pixel: Pixel) = safeApiCall {
        db.daoPixel().update(pixelMapper.fromDomain(pixel))
    }

    suspend fun deletePixel(pixel: Pixel) = safeApiCall {
        db.daoPixel().delete(pixelMapper.fromDomain(pixel))
    }

    suspend fun deleteByDate(date: LocalDate) = safeApiCall {
        db.daoPixel().delete(date)
    }
}