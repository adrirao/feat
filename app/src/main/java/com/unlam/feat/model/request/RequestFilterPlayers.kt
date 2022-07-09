package com.unlam.feat.model.request

data class RequestFilterPlayers (
    val eventId: Int,
    val distance: Int?,
    val min_age: Int?,
    val max_age: Int?,
)