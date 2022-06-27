package com.unlam.feat.presentation.view.events.detail_event

import com.unlam.feat.model.Event
import com.unlam.feat.model.Player

data class DetailEventState(
    val loading: Boolean = false,
    val isLoading:Boolean = false,
    val error: String = "",
    val successPlayer: Boolean = false,
    val successCancelEvent: Boolean = false,
    val successConfirmEvent: Boolean = false,
    val successTitle:String = "",
    val successDescription:String = "",


    val event: Event? = null,
    val playersSuggested: List<Player>? = null,
    val playersConfirmed: List<Player>? = null,
    val playersApplied: List<Player>? = null,
    val idPlayer : Int? = null
)
