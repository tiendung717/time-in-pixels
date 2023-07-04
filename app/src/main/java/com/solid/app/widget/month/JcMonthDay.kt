package com.solid.app.widget.month

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
fun JcMonthDayTile(pixel: Pixel?) {
    if (pixel == null) {
        JcMonthDayEmpty()
    } else {
        JcMonthDay(pixel = pixel)
    }
}

@Composable
private fun JcMonthDay(pixel: Pixel) {
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
private fun JcMonthDayEmpty() {
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