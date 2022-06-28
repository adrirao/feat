package com.unlam.feat.ui.view.home

import com.unlam.feat.model.Event


data class HomeState(
    val message : String = "",
    val eventsSuggestedToday : List<Event>? = null,
    val eventsConfirmedForMy : List<Event> ?= null,
    val isLoading : Boolean = false
)
