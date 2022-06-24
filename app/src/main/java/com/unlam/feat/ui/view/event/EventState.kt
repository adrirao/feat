package com.unlam.feat.ui.view.event

import com.unlam.feat.model.Event

data class EventState(
    val isLoading : Boolean = false,

    val events : List<Event> = listOf(),

    val eventsError : GenericError?  = null
){
    sealed class GenericError{
        object UnknowError: GenericError()
    }
}
