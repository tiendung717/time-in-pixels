package com.solid.app.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.solid.data.domain.Pixel

@Composable
fun JcPixelTile(pixel: Pixel?) {
    if (pixel == null) {
        JcEmptyPixel()
    } else {
        JcPixel(pixel = pixel)
    }
}

@Composable
fun JcPixel(pixel: Pixel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(
                color = pixel.mood.color,
                shape = RoundedCornerShape(2.dp)
            )
    )
}

@Composable
fun JcEmptyPixel() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(2.dp)
            )
    )
}