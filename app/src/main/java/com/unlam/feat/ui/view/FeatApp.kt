package com.unlam.feat.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.unlam.feat.ui.util.Navigation
import com.unlam.feat.ui.view.main.MainScreen

@Composable
fun FeatApp(
    navController: NavHostController
) {
    Scaffold {
        Box(
            modifier = Modifier
                .padding(
                    bottom = it.calculateBottomPadding()
                )
                .statusBarsPadding()
                .navigationBarsWithImePadding(),
            content = {
                Navigation(
                    navController = navController
                )
            }
        )
    }
}

