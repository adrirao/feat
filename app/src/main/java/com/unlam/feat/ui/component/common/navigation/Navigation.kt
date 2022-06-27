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
import com.unlam.feat.ui.view.event.detail_event.DetailEventViewModel
import com.unlam.feat.ui.component.ErrorDialog
import com.unlam.feat.ui.component.FeatCircularProgress
import com.unlam.feat.ui.component.InfoDialog
import com.unlam.feat.ui.component.SuccessDialog
import com.unlam.feat.ui.view.config_profile.ConfigProfileEvents
import com.unlam.feat.ui.view.config_profile.ConfigProfileScreen
import com.unlam.feat.ui.view.config_profile.ConfigProfileState
import com.unlam.feat.ui.view.config_profile.ConfigProfileViewModel
import com.unlam.feat.ui.util.Screen
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.view.event.EventEvents
import com.unlam.feat.ui.view.event.EventScreen
import com.unlam.feat.ui.view.event.EventViewModel
import com.unlam.feat.ui.view.event.detail_event.DetailEventMyEventScreen
import com.unlam.feat.ui.view.event.new_event.NewEventEvents
import com.unlam.feat.ui.view.event.new_event.NewEventScreen
import com.unlam.feat.ui.view.event.new_event.NewEventState
import com.unlam.feat.ui.view.event.new_event.NewEventViewModel
import com.unlam.feat.ui.view.home.HomeEvents
import com.unlam.feat.ui.view.home.HomeScreen
import com.unlam.feat.ui.view.home.HomeViewModel
import com.unlam.feat.ui.view.home.detail_event.DetailEventHomeScreen
import com.unlam.feat.ui.view.home.detail_event.DetailEventHomeViewModel
import com.unlam.feat.ui.view.login.LoginEvents
import com.unlam.feat.ui.view.login.LoginScreen
import com.unlam.feat.ui.view.login.LoginState
import com.unlam.feat.ui.view.login.LoginViewModel
import com.unlam.feat.ui.view.main.MainEvents
import com.unlam.feat.ui.view.main.MainScreen
import com.unlam.feat.ui.view.profile.ProfileScreen
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
        addRouteConfigProfile(navController)

        addRouteMain(navController)
        addRouteHome(navController)
        addRouteEvent(navController)
        addRouteNewEvent(navController)
        addRouteSearch(navController)

        addRouteProfile(navController)

        addRouteDetailEventHome(navController)
        addRouteDetailEventMyEvent(navController)
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
        HomeScreen(
            state,
            onClick = { event ->
                when (event) {
                    is HomeEvents.onClick -> {
                        if (event.typeClick == TypeClick.GoToDetailEvent) {
                            navController.navigate(
                                route = Screen.DetailEventHome.route + "/${event.idEvent}"
                            )
                        }
                    }
                }
            }
        )
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

private fun NavGraphBuilder.addRouteConfigProfile(navController: NavHostController) {
    composable(Screen.ConfigProfile.route) {
        val configProfileViewModel: ConfigProfileViewModel = hiltViewModel()
        val state by remember {
            configProfileViewModel.state
        }

        ConfigProfileScreen(
            state = state,
            onValueChange = { event ->
                configProfileViewModel.onEvent(event)
            },
            onClick = {})


    }
}

private fun NavGraphBuilder.addRouteEvent(navController: NavHostController) {
        composable(Screen.Events.route) {
            val eventViewModel: EventViewModel = hiltViewModel()
            val state by remember {
                eventViewModel.state
            }

        EventScreen(
            state = state,
            onClick = { event ->
                when (event) {
                    EventEvents.onClick(TypeClick.GoToNewEvent) -> {
                        navController.navigate(Screen.NewEvent.route)
                    }
                    else -> {
                        if (event.typeClick == TypeClick.GoToDetailEvent) {
                            navController.navigate(Screen.DetailEventMyEvent.route + "/${event.value}")
                        }
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

private fun NavGraphBuilder.addRouteProfile(navController: NavHostController) {
    composable(Screen.Profile.route) {
        ProfileScreen()
    }
}

private fun NavGraphBuilder.addRouteDetailEventHome(navController: NavHostController) {
    composable(
        route = Screen.DetailEventHome.route + "/{idEvent}",
        arguments = Screen.DetailEventHome.arguments ?: listOf()
    ) {
        val idEvent = it.arguments?.getString("idEvent") ?: ""
        val detailEventHomeViewModel: DetailEventHomeViewModel = hiltViewModel()
        val state by remember { detailEventHomeViewModel.state }

        LaunchedEffect(key1 = true) {
            detailEventHomeViewModel.getDataDetailEvent(idEvent.toInt())
        }

        if (state.loading) {
            FeatCircularProgress()
        }

        if (state.event != null && state.players != null) {
            DetailEventHomeScreen(state)
        }
    }
}

private fun NavGraphBuilder.addRouteDetailEventMyEvent(navController: NavHostController) {
    composable(
        route = Screen.DetailEventMyEvent.route + "/{idEvent}",
        arguments = Screen.DetailEventMyEvent.arguments ?: listOf()
    ) {
        val idEvent = it.arguments?.getString("idEvent") ?: ""
        val detailEventViewModel: DetailEventViewModel = hiltViewModel()
        val state = detailEventViewModel.state.value

        LaunchedEffect(key1 = true) {
            detailEventViewModel.getDataDetailEvent(idEvent.toInt())
        }

        if (state.loading) {
            FeatCircularProgress()
        }

        if (state.event != null && state.playersApplied != null && state.playersConfirmed != null && state.playersSuggested != null) {
            DetailEventMyEventScreen(
                state = state,
            )
        }
    }
}
