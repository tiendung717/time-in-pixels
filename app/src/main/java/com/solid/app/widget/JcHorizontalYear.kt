package com.solid.app.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solid.data.viewmodel.PixelViewModel
import org.threeten.bp.Year
import org.threeten.bp.YearMonth

@Composable
fun JcHorizontalYear(modifier: Modifier, year: Year) {
    val viewModel = hiltViewModel<PixelViewModel>()
    val pixelFlow = remember {
        viewModel.getPixelsByYear(year)
    }
    val pixels by pixelFlow.collectAsState(initial = emptyMap())

    LazyHorizontalGrid(
        modifier = modifier,
        rows = GridCells.Fixed(31),
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
                        JcPixelTile(pixel = pixels[date])
                    }
                    items(emptyDays) {
                        JcEmptyPixel()
                    }
                }
                else -> {
                    item {
                        JcPixelTile(pixel = pixels[date])
                    }
                }
            }
        }
    }
}
