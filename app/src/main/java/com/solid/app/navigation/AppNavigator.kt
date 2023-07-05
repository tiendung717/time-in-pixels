package com.solid.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.solid.app.ui.home.ScreenToday
import com.solid.app.ui.setting.ScreenSettings
import com.solid.navigation.destination.destination
import com.solid.navigation.navigator.Navigator


@Composable
fun AppNavigator(modifier: Modifier, navController: NavHostController) {
    Navigator(
        modifier = modifier,
        navController = navController,
        startingDestination = Screens.Today
    ) {
        destination(Screens.Today) {
            ScreenToday()
        }

        destination(Screens.Month) {
            ScreenToday()
        }

        destination(Screens.Year) {
            ScreenToday()
        }

        destination(Screens.Stats) {
            ScreenToday()
        }

        destination(Screens.Settings) {
            ScreenSettings()
        }
    }
}