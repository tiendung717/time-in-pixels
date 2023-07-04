package com.solid.app.widget.year

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.solid.data.viewmodel.PixelViewModel
import org.threeten.bp.Year

@Composable
fun JcYear(modifier: Modifier, year: Year, verticalDisplay: Boolean) {
    val viewModel = hiltViewModel<PixelViewModel>()
    val pixelFlow = remember {
        viewModel.getPixelsByYear(year)
    }
    val pixels by pixelFlow.collectAsState(initial = emptyMap())

    if (verticalDisplay) {
        JcVerticalYear(modifier = modifier, year = year, pixels = pixels)
    } else {
        JcHorizontalYear(modifier = modifier, year = year, pixels = pixels)
    }
}