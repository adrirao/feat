package com.unlam.feat.model.response

import com.unlam.feat.model.*

data class ResponseDataSearchEvent(
    val event: Event,
    val playersConfirmed: List<Player>,
    val playersUser: List<Player>
    )