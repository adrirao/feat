package com.unlam.feat.ui.view.home.detail_event

import androidx.compose.runtime.MutableState
import com.unlam.feat.model.Event
import com.unlam.feat.model.Player
import com.unlam.feat.model.Qualification

data class DetailEventHomeState(
    val event: Event? = null,
    val players: List<Player>? = null,
    var qualifications: MutableState<MutableList<Qualification>>? = null,
    val error: String = "",
    val loading: Boolean = false,
    val successApply: Boolean = false,
    val successCancelApply: Boolean = false,
    val successQualifyApply : Boolean = false,
    val idPlayer:String? = null,
)