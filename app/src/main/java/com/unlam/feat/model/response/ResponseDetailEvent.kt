package com.unlam.feat.model.response

import com.unlam.feat.model.Event
import com.unlam.feat.model.Player
import com.unlam.feat.model.PlayerApplyDetail

data class ResponseDetailEvent(
    val event: Event,
    val playersSuggested: List<Player>,
    val playersApplied : List<PlayerApplyDetail>,
    val playersConfirmed: List<Player>,
    val players : List<Player>,
    val playersUids : List<ResponseUids>
)

