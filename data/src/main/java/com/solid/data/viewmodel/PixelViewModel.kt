package com.solid.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solid.data.domain.Mood
import com.solid.data.domain.Pixel
import com.solid.data.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.Year
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PixelViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

    fun createMockData(year: Year) {
        viewModelScope.launch {
            repo.createMockData(year)
        }
    }

    fun getAllPixels() = repo.getAllPixels()

    fun getPixelsByYear(year: Year): Flow<Map<LocalDate, Pixel>> {
        return repo.getPixels(
            startDate = year.atDay(1),
            endDate = year.atDay(year.length())
        ).map { it.associateBy { it.date } }
    }

    fun getPixelsByMonth(month: YearMonth): Flow<List<Pixel>> {
        return repo.getPixels(
            startDate = month.atDay(1),
            endDate = month.atEndOfMonth()
        )
    }

    fun savePixel(date: LocalDate, note: String, mood: Mood) {
        viewModelScope.launch {
            repo.savePixel(
                pixel = Pixel(date, note, mood)
            )
        }
    }

    fun updatePixel(pixel: Pixel) {
        viewModelScope.launch {
            repo.savePixel(pixel)
        }
    }

    fun deletePixel(pixel: Pixel) {
        viewModelScope.launch {
            repo.deletePixel(pixel)
        }
    }

    fun delete(date: LocalDate) {
        viewModelScope.launch {
            repo.deleteByDate(date)
        }
    }
}