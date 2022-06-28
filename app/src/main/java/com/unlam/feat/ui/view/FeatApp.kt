package com.unlam.feat.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.unlam.feat.ui.component.FeatContent
import com.unlam.feat.ui.component.common.navigation.BottomNavigationItem
import com.unlam.feat.ui.component.common.navigation.Navigation
import com.unlam.feat.ui.component.common.navigation.NavigationItem
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
            icon = Icons.Outlined.MailOutline,
            contentDescription = "Invite",
            alertCount = 99
        ),
    )
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsWithImePadding(),
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth(),
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
        FeatContent(
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            content = {
                Navigation(
                    navController = navController,
                )
            }
        )
    }
}

