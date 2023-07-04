package com.solid.app.widget.month

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solid.app.widget.year.JcYearDayTile
import com.solid.data.viewmodel.PixelViewModel
import org.threeten.bp.YearMonth

@Composable
fun JcYearMonth(modifier: Modifier, yearMonth: YearMonth) {
    val viewModel = hiltViewModel<PixelViewModel>()
    val pixelFlow = remember {
        viewModel.getPixelsByMonth(yearMonth)
    }
    val pixels by pixelFlow.collectAsState(initial = emptyMap())

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(7),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        (1..yearMonth.lengthOfMonth()).forEach {
            val date = yearMonth.atDay(it)
            when {
                date.isEqual(yearMonth.atEndOfMonth()) -> {
                    item {
                        JcMonthDayTile(pixel = pixels[date])
                    }
                }
                else -> {
                    item {
                        JcMonthDayTile(pixel = pixels[date])
                    }
                }
            }
        }
    }
}