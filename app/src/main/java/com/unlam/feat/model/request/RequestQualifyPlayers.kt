package com.unlam.feat.model.request

import com.unlam.feat.model.Qualification

data class RequestQualifyPlayers(
    val eventId: String,
    val players: List<Qualification>
)