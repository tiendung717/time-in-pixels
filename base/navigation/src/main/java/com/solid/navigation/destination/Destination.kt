package com.solid.navigation.destination

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

data class Destination(
    val route: String,
    val deepLinks: List<NavDeepLink> = emptyList(),
    val arguments: List<String> = emptyList(),
)

fun NavGraphBuilder.destination(
    destination: Destination,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.route,
        arguments = destination.arguments.map { navArgument(it) { defaultValue = "" } },
        content = content
    )
}