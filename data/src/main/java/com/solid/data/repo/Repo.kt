package com.solid.data.repo

import com.solid.common.network.safeApiCall
import com.solid.data.database.AppDatabase
import com.solid.data.domain.Mood
import com.solid.data.domain.Pixel
import com.solid.data.mapper.PixelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate
import org.threeten.bp.Year
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Repo @Inject constructor(
    private val db: AppDatabase,
    private val pixelMapper: PixelMapper
) {

    suspend fun createMockData(year: Year) = safeApiCall {
        val pixels = mutableListOf<Pixel>()
        val startDate = year.atDay(1)
        val endDate = year.atDay(year.length())

        var date: LocalDate = startDate
        while (date.isBefore(endDate) || date.isEqual(endDate)) {
            pixels.add(
                Pixel(
                    date = date,
                    note = "$date",
                    mood = listOf(Mood.Positive, Mood.Negative).random()
                )
            )
            date = date.plusDays(1)
        }

        db.daoPixel().save(pixelMapper.fromDomains(pixels))
    }

    fun getAllPixels(): Flow<List<Pixel>> {
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