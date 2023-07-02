package com.solid.app.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.solid.app.navigation.AppNavigator
import com.solid.theme.theme.AppTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }

    @Composable
    private fun MainApp() {
        val navController = rememberNavController()

        AppTheme(useDarkTheme = isSystemInDarkTheme()) {
            AppNavigator(
                navController = navController
            )
        }
    }
}