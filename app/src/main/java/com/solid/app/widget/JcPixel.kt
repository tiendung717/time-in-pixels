package com.solid.app.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.solid.data.domain.Pixel

@Composable
fun JcPixel(modifier: Modifier, pixel: Pixel) {
    Box(modifier = modifier.background(color = pixel.mood.color))
}

@Composable
fun JcEmptyPixel(modifier: Modifier, color: Color) {
    Box(modifier = modifier.background(color = color))
}