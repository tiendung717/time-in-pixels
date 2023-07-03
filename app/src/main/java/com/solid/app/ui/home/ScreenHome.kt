package com.solid.app.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.solid.data.viewmodel.PixelViewModel
import org.threeten.bp.Year

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHome() {
    val pixelViewModel = hiltViewModel<PixelViewModel>()
    Scaffold(modifier = Modifier.fillMaxSize()) {

        LaunchedEffect(key1 = Unit) {
            pixelViewModel.createMockData(Year.now())
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "2023 in pixels",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}