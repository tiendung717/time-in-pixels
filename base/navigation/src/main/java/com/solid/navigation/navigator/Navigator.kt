package com.solid.navigation.navigator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.solid.navigation.destination.Destination

@Composable
fun Navigator(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startingDestination: Destination,
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startingDestination.route,
        builder = builder
    )
}