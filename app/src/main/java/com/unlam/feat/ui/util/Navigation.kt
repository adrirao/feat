@file:OptIn(ExperimentalFoundationApi::class)

package com.unlam.feat.ui.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unlam.feat.R
import com.unlam.feat.ui.component.ErrorDialog
import com.unlam.feat.ui.component.InfoDialog
import com.unlam.feat.ui.component.SuccessDialog
import com.unlam.feat.ui.view.event.EventEvents
import com.unlam.feat.ui.view.event.EventScreen
import com.unlam.feat.ui.view.event.new_event.NewEventEvents
import com.unlam.feat.ui.view.event.new_event.NewEventScreen
import com.unlam.feat.ui.view.event.new_event.NewEventState
import com.unlam.feat.ui.view.event.new_event.NewEventViewModel
import com.unlam.feat.ui.view.home.HomeScreen
import com.unlam.feat.ui.view.home.HomeViewModel
import com.unlam.feat.ui.view.login.LoginEvents
import com.unlam.feat.ui.view.login.LoginScreen
import com.unlam.feat.ui.view.login.LoginState
import com.unlam.feat.ui.view.login.LoginViewModel
import com.unlam.feat.ui.view.main.MainEvents
import com.unlam.feat.ui.view.main.MainScreen
import com.unlam.feat.ui.view.register.RegisterEvents
import com.unlam.feat.ui.view.register.RegisterScreen
import com.unlam.feat.ui.view.register.RegisterState
import com.unlam.feat.ui.view.register.RegisterViewModel
import com.unlam.feat.ui.view.splash.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        //init app
        addRouteSplash(navController)
        addRouteLogin(navController)
        addRouteRegister(navController)

        addRouteMain(navController)
        addRouteHome(navController)
        addRouteEvent(navController)
        addRouteNewEvent(navController)
        addRouteSearch(navController)
    }
}

private fun NavGraphBuilder.addRouteSplash(navController: NavHostController) {
    composable(Screen.Splash.route) {
        SplashScreen(navController)
    }
}

private fun NavGraphBuilder.addRouteMain(navController: NavHostController) {
    composable(Screen.Main.route) {
        MainScreen(
            onClick = { event ->
                when (event) {
                    MainEvents.onClick(TypeClick.GoToLogin) -> {
                        navController.popBackStack()
                        navController.navigate(Screen.Login.route)
                    }
                    MainEvents.onClick(TypeClick.GoToRegister) -> {
                        navController.popBackStack()
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
        HomeScreen(state, onClick = {

        })
    }
}

private fun NavGraphBuilder.addRouteLogin(navController: NavHostController) {
    composable(Screen.Login.route) {
        val loginViewModel: LoginViewModel = hiltViewModel()
        val state by remember {
            loginViewModel.state
        }

        when (state.loginMessage) {
            LoginState.LoginMessage.UserNotExist -> {
                ErrorDialog(
                    title = stringResource(R.string.text_user_not_exist),
                    desc = stringResource(R.string.desc_user_not_exist)
                ) {
                    loginViewModel.onEvent(LoginEvents.onClick(TypeClick.DismissDialog))
                }
            }
            LoginState.LoginMessage.InvalidCredentials -> {
                ErrorDialog(
                    title = stringResource(R.string.text_invalid_credential),
                    desc = stringResource(R.string.desc_invalid_credential)
                ) {
                    loginViewModel.onEvent(LoginEvents.onClick(TypeClick.DismissDialog))
                }
            }
            LoginState.LoginMessage.VerifyEmail -> {
                InfoDialog(
                    title = stringResource(R.string.text_verify_email),
                    desc = stringResource(R.string.desc_verify_email)
                ) {
                    loginViewModel.onEvent(LoginEvents.onClick(TypeClick.DismissDialog))
                }
            }
            LoginState.LoginMessage.LoginSuccess -> {
                loginViewModel.onEvent(LoginEvents.onClick(TypeClick.DismissDialog))
                navController.popBackStack(Screen.Login.route, inclusive = true)
                navController.navigate(Screen.Home.route)
            }
        }

        LoginScreen(
            state = state,
            onValueChange = { event ->
                loginViewModel.onEvent(event)
            },
            onClick = { event ->
                when (event) {
                    LoginEvents.onClick(TypeClick.GoToRegister) -> {
                        navController.navigate(Screen.Register.route)
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
                    registerViewModel.onEvent(RegisterEvents.onClick(TypeClick.DismissDialog))
                }
            }
            RegisterState.RegistrationMessage.RegistrationSuccess -> {
                SuccessDialog(
                    title = stringResource(R.string.text_user_created),
                    desc = stringResource(R.string.desc_user_created)
                ) {
                    registerViewModel.onEvent(RegisterEvents.onClick(TypeClick.DismissDialog))
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                }
            }
            RegisterState.RegistrationMessage.RegistrationError -> {
                ErrorDialog(
                    title = stringResource(R.string.text_error),
                    desc = stringResource(R.string.desc_error)
                ) {
                    registerViewModel.onEvent(RegisterEvents.onClick(TypeClick.DismissDialog))
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
                    RegisterEvents.onClick(TypeClick.GoToLogin) -> {
                        navController.popBackStack()
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
        EventScreen(
            onClick = { event ->
                when (event) {
                    EventEvents.onClick(TypeClick.GoToNewEvent) -> {
                        navController.navigate(Screen.NewEvent.route)
                    }
                }
            }
        )
    }
}

private fun NavGraphBuilder.addRouteNewEvent(navController: NavHostController) {
    composable(Screen.NewEvent.route) {
        val newEventViewModel: NewEventViewModel = hiltViewModel()
        val state by remember {
            newEventViewModel.state
        }

        when (state.newEventMessage) {
            NewEventState.NewEventMessage.NewEventError -> {
                ErrorDialog(
                    title = stringResource(R.string.text_error),
                    desc = stringResource(R.string.desc_error)
                ) {
                    newEventViewModel.onEvent(NewEventEvents.onClick(TypeClick.DismissDialog))
                }
            }
            NewEventState.NewEventMessage.NewEventSuccess -> {
                SuccessDialog(
                    title = stringResource(R.string.text_event_created),
                    desc = stringResource(R.string.desc_event_created)
                ) {
                    newEventViewModel.onEvent(NewEventEvents.onClick(TypeClick.DismissDialog))
                    navController.popBackStack()
                    navController.navigate(Screen.Events.route)
                }
            }
            else -> {}
        }

        NewEventScreen(
            state = state,
            onValueChange = { event ->
                newEventViewModel.onEvent(event)
            },
            onClick = { event ->
                when (event) {
                    NewEventEvents.onClick(TypeClick.GoToEvent) -> {
                        navController.popBackStack()
                        navController.navigate(Screen.Events.route)
                    }
                    NewEventEvents.onClick(TypeClick.Submit) -> {
                        newEventViewModel.onEvent(event)
                    }
                }
            }
        )
    }
}

private fun NavGraphBuilder.addRouteSearch(navController: NavHostController) {
    composable(Screen.Search.route) {
        Button(onClick = {
            Firebase.auth.signOut()
            navController.popBackStack(Screen.Home.route, inclusive = true)
            navController.navigate(Screen.Login.route)
        }) {

        }
    }
}
