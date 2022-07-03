package com.unlam.feat.ui.view.event.detail_event.suggestedPlayers

import com.unlam.feat.model.Player

data class SuggestedPlayersState (
    val players: List<Player> = listOf(),
    val error: String = "",
    val isLoading: Boolean = false,

)