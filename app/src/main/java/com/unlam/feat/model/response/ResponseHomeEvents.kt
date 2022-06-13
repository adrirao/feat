package com.unlam.feat.model.response

import com.unlam.feat.model.Event

data class ResponseHomeEvents(
    val eventsSuggestedToday: List<Event>,
    val eventsConfirmedForMy: List<Event>,
)