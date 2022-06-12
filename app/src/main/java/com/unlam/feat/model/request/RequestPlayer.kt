package com.unlam.feat.model.request

import com.unlam.feat.model.*

data class RequestPlayer(
    val abilities : Int,
    val person: Person,
    val sport: Sport,
    val position: Position,
    val level: Level,
    val valuation : Valuation
)
