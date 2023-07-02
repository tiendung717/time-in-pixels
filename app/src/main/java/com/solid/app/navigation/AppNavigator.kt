package com.solid.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.solid.app.ui.home.ScreenHome
import com.solid.app.ui.setting.ScreenSettings
import com.solid.navigation.destination.destination
import com.solid.navigation.navigator.Navigator


@Composable
fun AppNavigator(navController: NavHostController) {
    Navigator(
        navController = navController,
        startingDestination = Screens.Home
    ) {
        destination(Screens.Home) {
            ScreenHome()
        }

        destination(Screens.Settings) {
            ScreenSettings()
        }
    }
}