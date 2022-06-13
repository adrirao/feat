package com.unlam.feat.ui.view.home

import com.unlam.feat.model.Event


data class HomeState(
    val message : String = "",
    val eventsSuggestedToday : List<Event> = emptyList(),
    val eventsConfirmedForMy : List<Event> = emptyList(),
    val isLoading : Boolean = false
)
