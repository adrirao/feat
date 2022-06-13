@file:OptIn(ExperimentalFoundationApi::class)

package com.unlam.feat.ui.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unlam.feat.R
import com.unlam.feat.ui.component.ErrorDialog
import com.unlam.feat.ui.component.InfoDialog
import com.unlam.feat.ui.component.SuccessDialog
import com.unlam.feat.ui.view.home.HomeScreen
import com.unlam.feat.ui.view.home.HomeViewModel
import com.unlam.feat.ui.view.login.LoginEvents
import com.unlam.feat.ui.view.login.LoginScreen
import com.unlam.feat.ui.view.login.LoginViewModel
import com.unlam.feat.ui.view.main.MainEvents
import com.unlam.feat.ui.view.main.MainScreen
import com.unlam.feat.ui.view.register.RegisterEvents
import com.unlam.feat.ui.view.register.RegisterScreen
import com.unlam.feat.ui.view.register.RegisterState
import com.unlam.feat.ui.view.register.RegisterViewModel

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        //init app
        addRouteSplash(navController)
        addRouteLogin(navController)
        addRouteRegister(navController)

        addRouteMain(navController)
        addRouteHome(navController)
        addRouteEvent(navController)
    }
}

private fun NavGraphBuilder.addRouteSplash(navController: NavHostController) {
    composable(Screen.Main.route) {

    }
}

private fun NavGraphBuilder.addRouteMain(navController: NavHostController) {
    composable(Screen.Main.route) {
        MainScreen(
            onClick = { event ->
                when (event) {
                    MainEvents.onClick(TypeClick.goToLogin) -> {
                        navController.navigate(Screen.Login.route)
                    }
                    MainEvents.onClick(TypeClick.goToRegister) -> {
                        navController.navigate(Screen.Register.route)
                    }
                }
            }
        )
    }
}

private fun NavGraphBuilder.addRouteHome(navController: NavHostController) {
    composable(Screen.Home.route) {
        val homeViewModel: HomeViewModel = hiltViewModel()
        val state by remember {
            homeViewModel.state
        }
        HomeScreen(state, onClick = {})
    }
}

private fun NavGraphBuilder.addRouteLogin(navController: NavHostController) {
    composable(Screen.Login.route) {
        val loginViewModel: LoginViewModel = viewModel()
        val state = loginViewModel.state.value

        LoginScreen(
            state = state,
            onValueChange = { event ->
                loginViewModel.onEvent(event)
            },
            onClick = { event ->
                when (event) {
                    LoginEvents.onClick(TypeClick.goToRegister) -> {
                        navController.navigate(Screen.Register.route)
                    }
                    LoginEvents.onClick(TypeClick.goToHome) -> {
                        navController.navigate(Screen.Home.route)
                    }
                    else -> loginViewModel.onEvent(event)
                }
            }
        )
    }
}

private fun NavGraphBuilder.addRouteRegister(navController: NavHostController) {
    composable(Screen.Register.route) {
        val registerViewModel: RegisterViewModel = hiltViewModel()
        val state by remember {
            registerViewModel.state
        }

        when (state.registrationMessage) {
            RegisterState.RegistrationMessage.UserAlreadyExist -> {
                InfoDialog(
                    title = stringResource(R.string.text_user_already_exist),
                    desc = stringResource(R.string.desc_user_already_exist)
                ) {
                    registerViewModel.onEvent(RegisterEvents.onClick(TypeClick.dismissDialog))
                }
            }
            RegisterState.RegistrationMessage.RegistrationSuccess -> {
                SuccessDialog(
                    title = stringResource(R.string.text_user_created),
                    desc = stringResource(R.string.desc_user_created)
                ) {
                    registerViewModel.onEvent(RegisterEvents.onClick(TypeClick.dismissDialog))
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                }
            }
            RegisterState.RegistrationMessage.RegistrationError -> {
                ErrorDialog(
                    title = stringResource(R.string.text_error),
                    desc = stringResource(R.string.desc_error)
                ) {
                    registerViewModel.onEvent(RegisterEvents.onClick(TypeClick.dismissDialog))
                }
            }
            else -> {}
        }

        RegisterScreen(
            state = state,
            onValueChange = { event ->
                registerViewModel.onEvent(event)
            },
            onClick = { event ->
                when (event) {
                    RegisterEvents.onClick(TypeClick.goToLogin) -> {
                        navController.navigate(Screen.Login.route)
                    }
                    else -> {
                        registerViewModel.onEvent(event)
                    }
                }
            }
        )
    }
}

private fun NavGraphBuilder.addRouteEvent(navController: NavHostController) {
    composable(Screen.Events.route) {

    }
}
