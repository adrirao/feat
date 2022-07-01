package com.unlam.feat.ui.view.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.unlam.feat.R
import com.unlam.feat.ui.component.FeatContent
import com.unlam.feat.ui.util.Screen
import com.unlam.feat.util.Constants
import kotlinx.coroutines.*

@Composable
fun SplashScreen(
    navController: NavController,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main,
    viewModel: SplashViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = true) {
        viewModel.state.collect() {
            if (it.isAuthenticate == true && it.isFirstLogin == true) {
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            } else if(it.isAuthenticate == true && it.isFirstLogin == false){
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            } else if (it.isAuthenticate == false) {
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            }
        }
    }

    Content(coroutineDispatcher, viewModel)
}

@Composable
fun Content(
    coroutineDispatcher: CoroutineDispatcher,
    viewModel: SplashViewModel
) {
    val scale = remember {
        Animatable(0f)
    }

    val overshootInterpolator = remember {
        OvershootInterpolator(2f)
    }

    LaunchedEffect(key1 = true) {
        withContext(coroutineDispatcher) {
            scale.animateTo(
                targetValue = 0.5f,
                animationSpec = tween(
                    durationMillis = 1500,
                    easing = {
                        overshootInterpolator.getInterpolation(it)
                    }
                )
            )
            delay(Constants.SPLASH_SCREEN_DURATION)
            viewModel.onEvent(SplashEvent.Authenticate)
        }
    }

    FeatContent(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_isologotype_3),
            contentDescription = "feat",
            modifier = Modifier
                .scale(scale.value)
        )
    }
}