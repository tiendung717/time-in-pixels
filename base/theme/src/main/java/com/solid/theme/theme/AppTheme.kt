package com.solid.theme.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.solid.theme.colors.LocalColors
import com.solid.theme.colors.MaterialDarkColors
import com.solid.theme.colors.MaterialLightColors
import com.solid.theme.di.fontFamily
import com.solid.theme.di.themeColor

@Composable
fun AppTheme(
    useDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val fontFamily = remember { context.fontFamily() }
    val themeColors = remember { context.themeColor() }

    val colorCollection by remember(useDarkTheme) {
        mutableStateOf(if (useDarkTheme) themeColors.dark else themeColors.light)
    }

    CompositionLocalProvider(LocalColors provides colorCollection) {
        MaterialTheme(
            colorScheme = if (useDarkTheme) MaterialDarkColors else MaterialLightColors,
            content = content,
            typography = MaterialTheme.typography.copy(
                displayLarge = MaterialTheme.typography.displayLarge.copy(
                    fontFamily = fontFamily
                ),
                displayMedium = MaterialTheme.typography.displayMedium.copy(
                    fontFamily = fontFamily
                ),
                displaySmall = MaterialTheme.typography.displaySmall.copy(
                    fontFamily = fontFamily
                ),
                headlineLarge = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = fontFamily
                ),
                headlineMedium = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = fontFamily
                ),
                headlineSmall = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = fontFamily
                ),
                titleLarge = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = fontFamily
                ),
                titleMedium = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = fontFamily
                ),
                titleSmall = MaterialTheme.typography.titleSmall.copy(
                    fontFamily = fontFamily
                ),
                bodyLarge = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = fontFamily
                ),
                bodyMedium = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = fontFamily
                ),
                bodySmall = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = fontFamily
                ),
                labelLarge = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = fontFamily
                ),
                labelMedium = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = fontFamily
                ),
                labelSmall = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = fontFamily
                )
            )
        )
    }
}