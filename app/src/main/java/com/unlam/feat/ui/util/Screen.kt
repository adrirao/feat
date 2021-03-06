package com.unlam.feat.ui.util

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>? = null
) {
    object Main : Screen("main_screen")

    object Splash : Screen("splash_screen")
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object ConfigProfile : Screen("config_profile_screen")


    object Profile : Screen("profile_screen")
    object Events : Screen("events_screen")
    object Home : Screen("home_screen")
    object SearchList : Screen("search_list_screen")
    object SearchEventDetail : Screen("search_event_detail", listOf(
        navArgument("idEvent") { type = NavType.StringType }
    ))

    object Search : Screen("search_screen")
    object Invitation : Screen("invite_screen")

    object DetailInvitation : Screen("invitation_detail_screen", listOf(
        navArgument("idEvent") { type = NavType.StringType }
    ))

    object NewEvent : Screen("event_new_screen")
    object DetailEventHome : Screen("event_detail_home_screen", listOf(
        navArgument("idEvent") { type = NavType.StringType },
        navArgument("descOrigen") { type = NavType.StringType },
        navArgument("isOrganizer") { type = NavType.IntType },
    ))

    object DetailEventMyEvent : Screen("event_detail_my_event_screen", listOf(
        navArgument("idEvent") { type = NavType.StringType }
    ))

    object SuggestedPlayers : Screen("suggested_players", listOf(
        navArgument("idEvent") { type = NavType.StringType }
    ))

    object Chat : Screen(
        "chat_screen",
        listOf(navArgument("idEvent") { type = NavType.IntType })
    )

    object EditProfileAddress : Screen(
        "edit_profile_address_screen",
        listOf(navArgument("idPerson") { type = NavType.IntType })
    )
    object EditProfileSports : Screen("edit_profile_sport_screen")

    object EditProfilePersonalInformation : Screen("edit_personal_information_screen")
    object EditProfilePreferences : Screen("edit_profile_preferences_screen")
    object InfoPlayer : Screen(
        "info_player_screen",
        listOf(navArgument("idPlayer") { type = NavType.StringType })
    )

}