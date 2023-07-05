package com.solid.app.widget.bottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Equalizer
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.solid.app.R
import com.solid.app.navigation.Screens
import com.solid.navigation.destination.Destination
import com.solid.navigation.navigator.navigateTo

sealed class BottomNavigationItem(val icon: ImageVector, @StringRes val title: Int, val destination: Destination) {
    companion object {
        fun bottomBarItems() = listOf(Today, Month, Year, Stats, Settings)
    }

    object Today: BottomNavigationItem(Icons.Rounded.CheckCircle, R.string.bottom_today, Screens.Today)
    object Month: BottomNavigationItem(Icons.Rounded.CalendarMonth, R.string.bottom_month, Screens.Month)
    object Year: BottomNavigationItem(Icons.Rounded.Apps, R.string.bottom_year, Screens.Year)
    object Stats: BottomNavigationItem(Icons.Rounded.Equalizer, R.string.bottom_stats, Screens.Stats)
    object Settings: BottomNavigationItem(Icons.Rounded.Settings, R.string.bottom_settings, Screens.Settings)
}

@Composable
fun JcBottomBar(modifier: Modifier, navController: NavController) {
    val bottomNavigationItems = remember { BottomNavigationItem.bottomBarItems() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar by remember {
        derivedStateOf {
            navBackStackEntry?.destination?.route in bottomNavigationItems.map { it.destination.route }
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = showBottomBar,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight / 2 }
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight }
        ) + fadeOut()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            bottomNavigationItems.forEach {
                val isSelected = navBackStackEntry?.destination?.route == it.destination.route
                BottomBarItem(
                    bottomNavigationItem = it,
                    isSelected = isSelected,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    selectedContentColor = MaterialTheme.colorScheme.tertiary,
                    onClick = { navItem ->
                        navController.navigateTo(navItem.destination)
                    }
                )
            }
        }
    }
}


@Composable
private fun RowScope.BottomBarItem(
    bottomNavigationItem: BottomNavigationItem,
    isSelected: Boolean,
    contentColor: Color,
    selectedContentColor: Color,
    onClick: (BottomNavigationItem) -> Unit,
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable { onClick(bottomNavigationItem) }
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = bottomNavigationItem.icon,
            contentDescription = "",
            tint = if (isSelected) selectedContentColor else contentColor
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(id = bottomNavigationItem.title),
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) selectedContentColor else contentColor
        )
    }
}