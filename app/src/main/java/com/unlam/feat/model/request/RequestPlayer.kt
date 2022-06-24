package com.unlam.feat.model.request

import com.unlam.feat.model.*

data class RequestPlayer(
    val abilities : String,
    val person: Int,
    val sport: Int,
    val position: Int?,
    val level: Int?,
    val valuation: Int
)
