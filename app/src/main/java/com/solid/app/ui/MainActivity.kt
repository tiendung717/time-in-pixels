package com.solid.app.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.solid.app.navigation.AppNavigator
import com.solid.app.widget.bottombar.JcBottomBar
import com.solid.theme.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainApp() {
        val navController = rememberNavController()
        val darkTheme = isSystemInDarkTheme()
        AppTheme(useDarkTheme = darkTheme) {
            val systemUi = rememberSystemUiController()
            val backgroundColor = MaterialTheme.colorScheme.background
            LaunchedEffect(Unit) {
                systemUi.setSystemBarsColor(
                    color = backgroundColor,
                    darkIcons = !darkTheme
                )
            }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    JcBottomBar(
                        modifier = Modifier.fillMaxWidth(),
                        navController = navController
                    )
                }
            ) {
                AppNavigator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    navController = navController
                )
            }
        }
    }
}