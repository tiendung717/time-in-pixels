package com.solid.app.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.threeten.bp.Year

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JcYear(modifier: Modifier, year: Year) {
    LazyColumn(
        modifier = modifier,
        content = {
            stickyHeader {

            }
            (0..31).forEach {

            }
        }
    )
}
