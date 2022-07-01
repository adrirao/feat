@file:OptIn(ExperimentalFoundationApi::class)

package com.unlam.feat.ui.component.common.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unlam.feat.R
import com.unlam.feat.ui.view.search.event_detail.SearchEventDetailViewModel
import com.unlam.feat.ui.view.event.detail_event.DetailEventViewModel
import com.unlam.feat.ui.component.ErrorDialog
import com.unlam.feat.ui.component.FeatCircularProgress
import com.unlam.feat.ui.component.InfoDialog
import com.unlam.feat.ui.component.SuccessDialog
import com.unlam.feat.ui.view.config_profile.ConfigProfileScreen
import com.unlam.feat.ui.view.config_profile.ConfigProfileViewModel
import com.unlam.feat.ui.util.Screen
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.view.event.EventEvents
import com.unlam.feat.ui.view.event.EventScreen
import com.unlam.feat.ui.view.event.EventViewModel
import com.unlam.feat.ui.view.event.detail_event.DetailEventEvent
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
import com.unlam.feat.ui.view.invitation.InvitationScreen
import com.unlam.feat.ui.view.invitation.InvitationViewModel
import com.unlam.feat.ui.view.invitation.detail_invitation.DetailInvitationEvent
import com.unlam.feat.ui.view.invitation.detail_invitation.DetailInvitationScreen
import com.unlam.feat.ui.view.invitation.detail_invitation.DetailInvitationViewModel
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
import com.unlam.feat.ui.view.search.SearchScreen
import com.unlam.feat.ui.view.search.SearchViewModel
import com.unlam.feat.ui.view.search.event_detail.DetailSearchEventScreen
import com.unlam.feat.ui.view.search.event_detail.SearchEventDetailEvent
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
        addRouteSearch(navController)
        addRouteProfile(navController)
        addRouteInvitation(navController)

        addRouteNewEvent(navController)
        addRouteDetailEventHome(navController)
        addRouteDetailEventMyEvent(navController)
        addRouteDetailSearchEvent(navController)
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

@OptIn(ExperimentalFoundationApi::class)
private fun NavGraphBuilder.addRouteHome(navController: NavHostController) {
    composable(Screen.Home.route) {
        val homeViewModel: HomeViewModel = hiltViewModel()

        val state by remember {
            homeViewModel.state
        }

        if (state.isLoading) {
            FeatCircularProgress()
        }

        if (state.eventsConfirmedForMy != null && state.eventsSuggestedToday != null) {
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
            onEvent = { event ->
                configProfileViewModel.onEvent(event)
            })


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
        val searchViewModel: SearchViewModel = hiltViewModel()
        val state = searchViewModel.state.value
        SearchScreen(
            state = state,
            onClick = searchViewModel::onEvent,
            onClickCard = {
                navController.navigate(Screen.SearchEventDetail.route + "/${it.id}")
            }
        )
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
            DetailEventHomeScreen(
                state,
                onClick = {}
            )
        }
    }
}

private fun NavGraphBuilder.addRouteDetailEventMyEvent(navController: NavHostController) {
    composable(
        route = Screen.DetailEventMyEvent.route + "/{idEvent}",
        arguments = Screen.DetailEventMyEvent.arguments ?: listOf()
    ) { it ->
        val idEvent = it.arguments?.getString("idEvent") ?: ""
        val detailEventViewModel: DetailEventViewModel = hiltViewModel()
        val state = detailEventViewModel.state.value

        LaunchedEffect(key1 = true) {
            detailEventViewModel.getDataDetailEvent(idEvent.toInt())
        }

        if (state.loading) {
            FeatCircularProgress()
        }

        if (state.successPlayer || state.successCancelEvent || state.successConfirmEvent) {
            SuccessDialog(
                title = state.successTitle,
                desc = state.successDescription
            ) {
                detailEventViewModel.onEvent(DetailEventEvent.DismissDialog)
                navController.popBackStack()
                navController.navigate(Screen.Events.route)
            }
        }

        if (state.error.isNotEmpty()) {
            ErrorDialog(
                title = stringResource(R.string.text_error),
                desc = stringResource(R.string.desc_error)
            ) {
                detailEventViewModel.onEvent(DetailEventEvent.DismissDialog)
            }
        }

        if (state.event != null && state.playersApplied != null && state.playersConfirmed != null && state.playersSuggested != null) {
            DetailEventMyEventScreen(
                state = state,
                onClick = { event ->
                    detailEventViewModel.onEvent(event)
                }
            )
        }
    }
}

private fun NavGraphBuilder.addRouteDetailSearchEvent(
    navController: NavHostController
) {
    composable(
        route = Screen.SearchEventDetail.route + "/{idEvent}",
        arguments = Screen.SearchEventDetail.arguments ?: listOf()
    ) {
        val idEvent = it.arguments?.getString("idEvent") ?: ""
        val searchEventDetailViewModel: SearchEventDetailViewModel = hiltViewModel()
        val state = searchEventDetailViewModel.state.value

        LaunchedEffect(key1 = true) {
            searchEventDetailViewModel.getDataDetailEvent(idEvent.toInt())
        }

        if (state.loading) {
            FeatCircularProgress()
        }

        if (state.error.isNotBlank()) {
            ErrorDialog(
                title = "Ocurrio un error",
                desc = "No se pudo procesar la solicitud",
                onDismiss = {
                    searchEventDetailViewModel.onEvent(SearchEventDetailEvent.DismissDialog)
                }
            )
        }
        if (state.success) {
            SuccessDialog(
                title = "Enhorabuena",
                desc = "Solicitud Enviada Correctamente!!",
                onDismiss = {
                    navController.popBackStack()
                    navController.navigate(Screen.Search.route)
                }
            )
        }

        if (state.event != null && state.playersConfirmed != null) {
            DetailSearchEventScreen(
                state = state,
                onClick = searchEventDetailViewModel::onEvent,
            )
        }
    }
}

private fun NavGraphBuilder.addRouteInvitation(navController: NavHostController) {
    composable(Screen.Invitation.route) {
        val invitationViewModel: InvitationViewModel = hiltViewModel()
        val state = invitationViewModel.state.value
        val isRefreshing = invitationViewModel.isRefreshing.collectAsState()

        InvitationScreen(
            state = state,
            onClick = invitationViewModel::onEvent,
            onClickCard = {
                navController.navigate(
                    Screen.DetailInvitation.route + "/${it}"
                )
            }
        )

    }
}

private fun NavGraphBuilder.addRouteDetailInvitation(
    navController: NavHostController,
) {
    composable(
        route = Screen.DetailInvitation.route + "/{idEvent}",
        arguments = Screen.DetailInvitation.arguments ?: listOf()
    ) {
        val idEvent = it.arguments?.getString("idEvent") ?: ""
        val detailInvitation: DetailInvitationViewModel = hiltViewModel()
        val state = detailInvitation.state.value

        LaunchedEffect(key1 = true) {
            detailInvitation.getDataDetailEvent(idEvent.toInt())
        }

        if (state.loading) {
            FeatCircularProgress()
        }

        if (state.error.isNotBlank()) {
            ErrorDialog(
                title = "Ocurrio un error",
                desc = "No se pudo procesar la solicitud, por favor, vuelva a intentarlo",
                onDismiss = {
                    detailInvitation.onEvent(DetailInvitationEvent.DismissDialog)
                }
            )
        }
        if (state.success) {
            SuccessDialog(
                title = state.successTitle,
                desc = state.successDescription,
                onDismiss = {
                    detailInvitation.onEvent(DetailInvitationEvent.DismissDialog)
                    navController.popBackStack()
                    navController.navigate(Screen.Invitation.route)
                }
            )
        }

        if (state.event != null && state.playersConfirmed != null) {
            DetailInvitationScreen(
                state = state,
                onClick = { event ->
                    if (event == DetailInvitationEvent.CancelInvitation) {
                        navController.popBackStack()
                        navController.navigate(Screen.Invitation.route)
                    } else {
                        detailInvitation.onEvent(event)
                    }
                },
            )
        }
    }
}