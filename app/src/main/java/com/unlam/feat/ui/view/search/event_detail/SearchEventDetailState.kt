package com.unlam.feat.ui.view.search.event_detail

import com.unlam.feat.model.Event
import com.unlam.feat.model.Player

data class SearchEventDetailState(
    val loading: Boolean = false,
    val error: String = "",
    val event: Event? = null,
    val playersConfirmed: List<Player>? = null,
    val success: Boolean = false,
    val idPlayer:String? = null
)