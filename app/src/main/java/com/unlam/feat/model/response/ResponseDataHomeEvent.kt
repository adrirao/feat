package com.unlam.feat.model.response

import com.unlam.feat.model.*


data class ResponseDataHomeEvent (
    val eventOfTheWeek: List<Event>,
    val eventConfirmedOrApplied: List<HomeEvent>,
)
