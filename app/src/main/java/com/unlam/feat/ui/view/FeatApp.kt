package com.unlam.feat.ui.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.unlam.feat.ui.util.BottomNavigationItem
import com.unlam.feat.ui.util.Navigation
import com.unlam.feat.ui.util.NavigationItem
import com.unlam.feat.ui.util.Screen

@Composable
fun FeatApp(
    navController: NavHostController,
    showBottomBar: Boolean = false,
    navigationItems: List<NavigationItem> = listOf(
        NavigationItem(
            route = Screen.Profile.route,
            icon = Icons.Outlined.Person,
            contentDescription = "Profile"
        ),
        NavigationItem(
            route = Screen.Events.route,
            icon = Icons.Outlined.Event,
            contentDescription = "Events",
        ),
        NavigationItem(
            route = Screen.Home.route,
            icon = Icons.Outlined.Home,
            contentDescription = "Home",
        ),
        NavigationItem(
            route = Screen.Search.route,
            icon = Icons.Outlined.Search,
            contentDescription = "Search"
        ),
        NavigationItem(
            route = Screen.Invite.route,
            icon = Icons.Outlined.Sms,
            contentDescription = "Invite",
            alertCount = 99
        ),
    )
) {
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    backgroundColor = MaterialTheme.colors.surface,
                    cutoutShape = CircleShape,
                ) {
                    BottomNavigation(
                        backgroundColor = MaterialTheme.colors.surface,
                        elevation = 0.dp
                    ) {
                        navigationItems.forEach { item ->
                            BottomNavigationItem(
                                icon = item.icon,
                                contentDescription = item.contentDescription,
                                selected = item.route == navController.currentDestination?.route,
                                alertCount = item.alertCount,
                                enabled = item.icon != null
                            ) {
                                if (navController.currentDestination?.route != item.route) {
                                    navController.navigate(item.route)
                                }
                            }
                        }
                    }
                }
            }
        }
    ) {
        Box(
            modifier = if(navController.currentDestination?.route in listOf(
                    Screen.Profile.route,
                    Screen.Events.route,
                    Screen.Search.route,
                    Screen.Invite.route,
                    Screen.Home.route,
                )){
                Modifier
                .padding(
                    bottom = it.calculateBottomPadding()
                )} else {
                Modifier
                    .statusBarsPadding()
                    .navigationBarsWithImePadding()
                    .padding(
                        bottom = it.calculateBottomPadding()
                    )
                        },
            content = {
                Navigation(
                    navController = navController,
                )
            }
        )
    }
}

