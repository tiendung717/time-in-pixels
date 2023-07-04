package com.solid.app.widget.year

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
fun JcYearDayTile(pixel: Pixel?) {
    if (pixel == null) {
        JcYearDayEmpty()
    } else {
        JcYearDay(pixel = pixel)
    }
}

@Composable
private fun JcYearDay(pixel: Pixel) {
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
fun JcYearDayEmpty() {
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