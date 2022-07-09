package com.unlam.feat.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.unlam.feat.ui.theme.FeatTheme
import com.unlam.feat.ui.util.Screen
import com.unlam.feat.ui.view.FeatApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            ProvideWindowInsets {
                FeatTheme {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    FeatApp(
                        navController = navController,
                        showBottomBar = navBackStackEntry?.destination?.route in listOf(
                            Screen.Profile.route,
                            Screen.Events.route,
                            Screen.Search.route,
                            Screen.Invitation.route,
                            Screen.Home.route,
                        ),
                    )
                }
            }
        }
    }
}