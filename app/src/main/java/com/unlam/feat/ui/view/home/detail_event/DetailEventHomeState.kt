package com.unlam.feat.ui.view.home.detail_event

import com.unlam.feat.model.Event
import com.unlam.feat.model.Player

data class DetailEventHomeState(
    val event : Event? = null,
    val players: List<Player>? = null,
    val error: String = "",
    val loading: Boolean = false,
)