package com.unlam.feat.model.request

import java.time.LocalTime

data class RequestFilterPlayers (
    val distance: Int?,
    val min_age: Int?,
    val max_age: Int?,
        )