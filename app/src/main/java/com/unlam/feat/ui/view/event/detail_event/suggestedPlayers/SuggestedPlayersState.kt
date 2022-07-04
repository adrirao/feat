package com.unlam.feat.ui.view.event.detail_event.suggestedPlayers

import com.unlam.feat.model.Player

data class SuggestedPlayersState (
    val players: List<Player> = listOf(),
    val error: String = "",
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
    var idEvent: Int = 0,

    val minAge: String = "",
    val maxAge: String = "",
    val distance: String =""

)