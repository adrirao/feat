package com.unlam.feat.model.response

import com.unlam.feat.model.Event
import com.unlam.feat.model.Player

data class ResponseDetailEvent(
    val event: Event,
    val playersSuggested: List<Player>,
    val playersApplied : List<Player>,
    val playersConfirmed: List<Player>,
)