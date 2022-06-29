package com.unlam.feat.ui.view.home

import com.unlam.feat.model.Event
import com.unlam.feat.model.HomeEvent


data class HomeState(
    val message : String = "",
    val eventsSuggestedToday : List<Event>? = null,
    val eventsConfirmedForMy : List<HomeEvent> ?= null,
    val isLoading : Boolean = false
)
