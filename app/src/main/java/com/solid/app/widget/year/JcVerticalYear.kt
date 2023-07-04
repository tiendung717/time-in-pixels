package com.solid.app.widget.year

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solid.data.domain.Pixel
import org.threeten.bp.LocalDate
import org.threeten.bp.Year
import org.threeten.bp.YearMonth

@Composable
fun JcVerticalYear(modifier: Modifier, year: Year, pixels: Map<LocalDate, Pixel>) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(31),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        (1..year.length()).forEach {
            val date = year.atDay(it)
            val yearMonth = YearMonth.of(date.year, date.month)
            when {
                date.isEqual(yearMonth.atEndOfMonth()) -> {
                    val emptyDays = 31 - yearMonth.lengthOfMonth()
                    item {
                        JcYearDayTile(pixel = pixels[date])
                    }
                    items(emptyDays) {
                        JcYearDayEmpty()
                    }
                }
                else -> {
                    item {
                        JcYearDayTile(pixel = pixels[date])
                    }
                }
            }
        }
    }
}
