@file:OptIn(ExperimentalFoundationApi::class)

package com.unlam.feat.ui.component.common.navigation

import EditProfileAddressScreen
import android.os.Build
import androidx.annotation.RequiresApi
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
import com.unlam.feat.ui.view.chat.ChatScreen
import com.unlam.feat.ui.view.chat.ChatViewModel
import com.unlam.feat.ui.view.config_profile.ConfigProfileEvents
import com.unlam.feat.ui.view.event.EventEvents
import com.unlam.feat.ui.view.event.EventScreen
import com.unlam.feat.ui.view.event.EventViewModel
import com.unlam.feat.ui.view.event.detail_event.DetailEventEvent
import com.unlam.feat.ui.view.event.detail_event.suggestedPlayers.SuggestedPlayers
import com.unlam.feat.ui.view.event.detail_event.suggestedPlayers.SuggestedPlayersViewModel
import com.unlam.feat.ui.view.event.new_event.NewEventEvents
import com.unlam.feat.ui.view.event.new_event.NewEventScreen
import com.unlam.feat.ui.view.event.new_event.NewEventState
import com.unlam.feat.ui.view.event.new_event.NewEventViewModel
import com.unlam.feat.ui.view.home.HomeEvents
import com.unlam.feat.ui.view.home.HomeScreen
import com.unlam.feat.ui.view.home.HomeViewModel
import com.unlam.feat.ui.view.home.detail_event.DetailEventHomeEvent
import com.unlam.feat.ui.view.home.detail_event.DetailEventHomeScreen
import com.unlam.feat.ui.view.home.detail_event.DetailEventHomeViewModel
import com.unlam.feat.ui.view.info_player.InfoPlayerScreen
import com.unlam.feat.ui.view.info_player.InfoPlayerViewModel
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
import com.unlam.feat.ui.view.profile.ProfileEvent
import com.unlam.feat.ui.view.profile.ProfileScreen
import com.unlam.feat.ui.view.profile.ProfileViewModel
import com.unlam.feat.ui.view.profile.personal_information.EditPersonalInformationScreen
import com.unlam.feat.ui.view.profile.personal_information.EditPersonalInformationViewModel
import com.unlam.feat.ui.view.profile.preferences.EditProfilePreferencesScreen
import com.unlam.feat.ui.view.profile.preferences.EditProfilePreferencesViewModel
import com.unlam.feat.ui.view.register.RegisterEvents
import com.unlam.feat.ui.view.register.RegisterScreen
import com.unlam.feat.ui.view.register.RegisterState
import com.unlam.feat.ui.view.register.RegisterViewModel
import com.unlam.feat.ui.view.event.detail_event.DetailEventMyEventScreen
import com.unlam.feat.ui.view.profile.address.EditProfileAddressEvent
import com.unlam.feat.ui.view.profile.address.EditProfileAddressViewModel
import com.unlam.feat.ui.view.search.SearchScreen
import com.unlam.feat.ui.view.search.SearchViewModel
import com.unlam.feat.ui.view.search.event_detail.DetailSearchEventScreen
import com.unlam.feat.ui.view.search.event_detail.SearchEventDetailEvent
import com.unlam.feat.ui.view.splash.SplashScreen

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        //init app
        addRouteSplash(navController)
        addRouteLogin(navController)
        addRouteRegister(navController)
        addRouteConfigProfile(navController)
        addRouteChat(navController)

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
        addRouteSuggestedPlayers(navController)

        addRouteEditPersonalInformation(navController)
        addRouteEditPreferences(navController)
        addRouteDetailInvitation(navController)
        addRouteEditAddress(navController)
        addRouteInfoPlayer(navController)
    }
}

private fun NavGraphBuilder.addRouteSplash(navController: NavHostController) {
    composable(Screen.Splash.route) {
        SplashScreen(navController)
    }
}

private fun NavGraphBuilder.addRouteChat(navController: NavHostController) {
    composable(
        route = Screen.Chat.route + "/{idEvent}",
        arguments = Screen.Chat.arguments ?: listOf()
    ) {
        val chatViewModel: ChatViewModel = hiltViewModel()
        val idEvent = it.arguments?.getInt("idEvent") ?: 0
        val state by remember {
            chatViewModel.state
        }

        LaunchedEffect(true) {
            chatViewModel.getEvent(idEvent)
        }

        if (state.isLoading) {
            FeatCircularProgress()
        }

        if (state.event != null && state.person != null) {
            ChatScreen(state = state,
                onEvent = {
                    chatViewModel.onValueChange(it)
                },
                onClick = {
                    chatViewModel.onClick(it)
                }
            )
        }
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
                                    route = Screen.DetailEventHome.route + "/${event.idEvent}/${event.descOrigen}"
                                )
                            }
                        }
                    }
                },
                goToChat = {
                    navController.navigate(Screen.Chat.route + "/${it}")
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
                    desc = stringResource(R.string.desc_user_not_exist),
                    enabledCancelButton = false
                ) {
                    loginViewModel.onEvent(LoginEvents.onClick(TypeClick.DismissDialog))
                }
            }
            LoginState.LoginMessage.ApiConnectionError -> {
                ErrorDialog(
                    title = stringResource(R.string.error_api_connection),
                    desc = stringResource(R.string.error_description_api_connection),
                    enabledCancelButton = false
                ) {
                    loginViewModel.onEvent(LoginEvents.onClick(TypeClick.DismissDialog))
                }
            }
            LoginState.LoginMessage.InvalidCredentials -> {
                ErrorDialog(
                    title = stringResource(R.string.text_invalid_credential),
                    desc = stringResource(R.string.desc_invalid_credential),
                    enabledCancelButton = false
                ) {
                    loginViewModel.onEvent(LoginEvents.onClick(TypeClick.DismissDialog))
                }
            }
            LoginState.LoginMessage.VerifyEmail -> {
                InfoDialog(
                    title = stringResource(R.string.text_verify_email),
                    desc = stringResource(R.string.desc_verify_email),
                    enabledCancelButton = false
                ) {
                    loginViewModel.onEvent(LoginEvents.onClick(TypeClick.DismissDialog))
                }
            }
            LoginState.LoginMessage.LoginSuccess -> {
                loginViewModel.onEvent(LoginEvents.onClick(TypeClick.DismissDialog))
                if (state.isFirstLogin != null) {
                    if (!state.isFirstLogin!!) {
                        navController.popBackStack(Screen.Login.route, inclusive = true)
                        navController.navigate(Screen.Home.route)
                    } else if (state.isFirstLogin!!) {
                        navController.popBackStack(Screen.Login.route, inclusive = true)
                        navController.navigate(Screen.ConfigProfile.route)
                    }
                }
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
                    desc = stringResource(R.string.desc_user_already_exist),
                    enabledCancelButton = false
                ) {
                    registerViewModel.onEvent(RegisterEvents.onClick(TypeClick.DismissDialog))
                }
            }
            RegisterState.RegistrationMessage.RegistrationSuccess -> {
                SuccessDialog(
                    title = stringResource(R.string.text_user_created),
                    desc = stringResource(R.string.desc_user_created),
                    enabledCancelButton = false
                ) {
                    registerViewModel.onEvent(RegisterEvents.onClick(TypeClick.DismissDialog))
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                }
            }
            RegisterState.RegistrationMessage.RegistrationError -> {
                ErrorDialog(
                    title = stringResource(R.string.text_error),
                    desc = stringResource(R.string.desc_error),
                    enabledCancelButton = false
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

@RequiresApi(Build.VERSION_CODES.P)
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
            },
            onClick = { event ->
                when (event) {
                    ConfigProfileEvents.onClick(TypeClick.GoToLogin) -> {
                        navController.popBackStack()
                        navController.navigate(Screen.Login.route)
                    }
                    ConfigProfileEvents.onClick(TypeClick.GoToHome) -> {
                        navController.popBackStack()
                        navController.navigate(Screen.Home.route)
                    }
                }

            }
        )


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
            },
            goToChat = {
                navController.navigate(Screen.Chat.route + "/${it}")
            }
        )
    }
}

private fun NavGraphBuilder.addRouteSuggestedPlayers(navController: NavHostController) {
    composable(
        route = Screen.SuggestedPlayers.route + "/{idEvent}",
        arguments = Screen.SuggestedPlayers.arguments ?: listOf()
    ) {
        val idEvent = it.arguments?.getString("idEvent") ?: ""
        val suggestedPlayerViewModel: SuggestedPlayersViewModel = hiltViewModel()
        val state by remember {
            suggestedPlayerViewModel.state
        }
        LaunchedEffect(key1 = true) {
            state.idEvent = idEvent.toInt()
            suggestedPlayerViewModel.getSuggestedPlayers(idEvent.toInt())
        }

        if (state.isLoading) {
            FeatCircularProgress()
        }

        if (state.successPlayer) {
            SuccessDialog(
                title = state.successTitle,
                desc = state.successDescription,
                onDismiss = {
                    navController.popBackStack()
                    navController.navigate(Screen.Events.route)
                },
                enabledCancelButton = false
            )
        }

        SuggestedPlayers(
            state,
            onClick = suggestedPlayerViewModel::onEvent
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
                    desc = stringResource(R.string.desc_error),
                    enabledCancelButton = false
                ) {
                    newEventViewModel.onEvent(NewEventEvents.onClick(TypeClick.DismissDialog))
                }
            }
            NewEventState.NewEventMessage.NewEventSuccess -> {
                SuccessDialog(
                    title = stringResource(R.string.text_event_created),
                    desc = stringResource(R.string.desc_event_created),
                    enabledCancelButton = false
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
            },
            onEvent = { event ->
                searchViewModel.onEvent(event)
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.P)
private fun NavGraphBuilder.addRouteInfoPlayer(navController: NavHostController) {
    composable(
        route = Screen.InfoPlayer.route + "/{idPlayer}",
        arguments = Screen.InfoPlayer.arguments ?: listOf()
    ) {
        val idPlayer = it.arguments?.getString("idPlayer")
        val infoPlayerViewModel: InfoPlayerViewModel = hiltViewModel()
        val state by remember {
            infoPlayerViewModel.state
        }

        LaunchedEffect(true) {
            infoPlayerViewModel.getInfoPlayer(idPlayer!!)
        }

        if (state.isLoading) {
            FeatCircularProgress()
        }

        if (state.person != null && state.qualifications != null) {
            InfoPlayerScreen(state = state)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
private fun NavGraphBuilder.addRouteProfile(navController: NavHostController) {
    composable(Screen.Profile.route) {
        val profileViewModel: ProfileViewModel = hiltViewModel()
        val state by remember {
            profileViewModel.state
        }
        LaunchedEffect(true) {
            profileViewModel.getDetailProfile()
        }

        if (state.isLoading) {
            FeatCircularProgress()
        }
        if (state.person != null && state.availabilities != null) {
            ProfileScreen(
                state = state,
                navigateTo = { typeNavigate ->
                    if (typeNavigate == ProfileEvent.NavigateTo.TypeNavigate.NavigateToPersonalInfo) {
                        navController.navigate(Screen.EditProfilePersonalInformation.route)
                    } else if (typeNavigate == ProfileEvent.NavigateTo.TypeNavigate.NavigateToPreferences) {
                        navController.navigate(Screen.EditProfilePreferences.route)
                    } else if (typeNavigate == ProfileEvent.NavigateTo.TypeNavigate.NavigateToLogin) {
                        navController.navigate(Screen.Login.route)
                    } else if (typeNavigate == ProfileEvent.NavigateTo.TypeNavigate.NavigateToAddress) {
                        navController.navigate(Screen.EditProfileAddress.route + "/${state.person!!.id}")
                    }
                },
                uploadImage = profileViewModel::onEvent,
                onClick = { profileViewModel.onEvent(it) }
            )
        }

    }
}

private fun NavGraphBuilder.addRouteDetailEventHome(navController: NavHostController) {
    composable(
        route = Screen.DetailEventHome.route + "/{idEvent}/{descOrigen}",
        arguments = Screen.DetailEventHome.arguments ?: listOf()
    ) {
        val idEvent = it.arguments?.getString("idEvent") ?: ""
        val descOrigen = it.arguments?.getString("descOrigen") ?: ""
        val detailEventHomeViewModel: DetailEventHomeViewModel = hiltViewModel()
        val state by remember { detailEventHomeViewModel.state }
        val qualifications = detailEventHomeViewModel.qualifications

        LaunchedEffect(key1 = true) {
            detailEventHomeViewModel.getDataDetailEvent(idEvent.toInt())
        }

        if (state.loading) {
            FeatCircularProgress()
        }

        if (state.event != null && state.players != null) {
            DetailEventHomeScreen(
                state,
                qualifications,
                descOrigen = descOrigen,
                onClick = { event ->
                    when (event) {
                        is DetailEventHomeEvent.OnClickCardPlayer -> {
                            navController.navigate(Screen.InfoPlayer.route + "/${event.idPlayer}")
                        }
                        else -> detailEventHomeViewModel.qualifyPlayers()
                    }
                },
                changeQualification = {
                    detailEventHomeViewModel.updateOneItem(it)
                }
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
                desc = state.successDescription,
                enabledCancelButton = false
            ) {
                detailEventViewModel.onEvent(DetailEventEvent.DismissDialog)
                navController.popBackStack()
                navController.navigate(Screen.Events.route)
            }
        }

        if (state.error.isNotEmpty()) {
            ErrorDialog(
                title = stringResource(R.string.text_error),
                desc = stringResource(R.string.desc_error),
                enabledCancelButton = false
            ) {
                detailEventViewModel.onEvent(DetailEventEvent.DismissDialog)
            }
        }

        if (state.event != null && state.playersApplied != null && state.playersConfirmed != null && state.playersSuggested != null) {

            DetailEventMyEventScreen(
                state = state,
                onClick = { event ->
                    detailEventViewModel.onEvent(event)
                },
                navigateTo = {
                    navController.navigate(Screen.SuggestedPlayers.route + "/${state.event.id}")
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
                },
                enabledCancelButton = false
            )
        }
        if (state.success) {
            SuccessDialog(
                title = "Enhorabuena",
                desc = "Solicitud Enviada Correctamente!!",
                onDismiss = {
                    navController.popBackStack()
                    navController.navigate(Screen.Search.route)
                },
                enabledCancelButton = false
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
                },
                enabledCancelButton = false,
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
                },
                enabledCancelButton = false,
            )
        }

        if (state.event != null && state.playersConfirmed != null) {
            DetailInvitationScreen(
                state = state,
                onClick = { event ->
                    detailInvitation.onEvent(event)
                },
            )
        }
    }
}

private fun NavGraphBuilder.addRouteEditPersonalInformation(
    navController: NavHostController,
) {
    composable(
        route = Screen.EditProfilePersonalInformation.route,
    ) {
        val editPersonalInformationViewModel: EditPersonalInformationViewModel = hiltViewModel()
        val state by remember { editPersonalInformationViewModel.state }

        if (state.isLoading) {
            FeatCircularProgress()
        }

        if (state.personId != "null") {
            EditPersonalInformationScreen(
                state = state,
                onValueChange = editPersonalInformationViewModel::onEvent,
                goToProfile = {
                    navController.popBackStack()
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

    }
}

private fun NavGraphBuilder.addRouteEditPreferences(
    navController: NavHostController,
) {
    composable(
        route = Screen.EditProfilePreferences.route,
    ) {
        val editProfilePreferencesViewModel: EditProfilePreferencesViewModel = hiltViewModel()
        val state by remember { editProfilePreferencesViewModel.state }

        if (state.isLoading) {
            FeatCircularProgress()
        }

        if (state.person != null) {
            EditProfilePreferencesScreen(
                state = state,
                onValueChange = editProfilePreferencesViewModel::onEvent,
                goToProfile = {
                    navController.popBackStack()
                    navController.navigate(Screen.Profile.route)
                }
            )
        }
    }
}

private fun NavGraphBuilder.addRouteEditAddress(
    navController: NavHostController,
) {
    composable(
        route = Screen.EditProfileAddress.route + "/{idPerson}",
        arguments = Screen.EditProfileAddress.arguments ?: listOf()
    ) {
        val idPerson = it.arguments?.getInt("idPerson") ?: 0
        val editProfileAddressViewModel: EditProfileAddressViewModel = hiltViewModel()
        val state by remember { editProfileAddressViewModel.state }
        editProfileAddressViewModel.setIdPerson(idPerson)

        if (state.isLoading) {
            FeatCircularProgress()
        }

        if (state.isSuccessSubmitData) {
            SuccessDialog(
                title = "Direccion agregada con exito",
                desc = "Tu direccion fue agregada con exito!",
                onDismiss = {
                    navController.popBackStack()
                    navController.navigate(Screen.Profile.route)
                },
                enabledCancelButton = false
            )
        }
        if (state.isErrorSubmitData) {
            ErrorDialog(
                title = "No se puedo agregar la dirección",
                desc = "Hubo un problema al agregar la direccion, por favor, vuelve a intentarlo",
                onDismiss = {
                    navController.popBackStack()
                    navController.navigate(Screen.Profile.route)
                },
                enabledCancelButton = false
            )
        }

        EditProfileAddressScreen(
            state = state,
            onEvent = editProfileAddressViewModel::onEvent,
            onClick = { editProfileAddressViewModel.onEvent(EditProfileAddressEvent.SubmitData) }
        )
    }
}