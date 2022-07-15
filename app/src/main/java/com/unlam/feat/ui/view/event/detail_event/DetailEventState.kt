package com.unlam.feat.ui.view.event.detail_event

import com.unlam.feat.model.Event
import com.unlam.feat.model.Player
import com.unlam.feat.model.PlayerApplyDetail

data class DetailEventState(
    val loading: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
    val successPlayer: Boolean = false,
    val successCancelEvent: Boolean = false,
    val successConfirmEvent: Boolean = false,
    val successFinalizedEvent: Boolean = false,
    val successTitle: String = "",
    val successDescription: String = "",
    val completeCapacity: Boolean = false,
    val completeTitle: String = "",
    val completeDescription: String = "",

    val event: Event? = null,
    val playersSuggested: List<Player>? = null,
    val playersConfirmed: List<Player>? = null,
    val playersApplied: List<PlayerApplyDetail>? = null,
    val idPlayer: Int? = null
)
