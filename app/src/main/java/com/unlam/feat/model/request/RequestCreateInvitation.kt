package com.unlam.feat.model.request

data class RequestCreateInvitation(
    val playerId: String,
    val eventId: Int,
    val origin: String
)