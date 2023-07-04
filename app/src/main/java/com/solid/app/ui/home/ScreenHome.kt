package com.solid.app.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.solid.app.widget.JcHorizontalYear
import com.solid.app.widget.JcVerticalYear
import com.solid.data.viewmodel.PixelViewModel
import org.threeten.bp.Year

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHome() {
    val pixelViewModel = hiltViewModel<PixelViewModel>()
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            JcVerticalYear(
                modifier = Modifier.fillMaxSize(),
                year = Year.now()
            )
        }
    }
}