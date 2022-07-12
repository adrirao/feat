package com.unlam.feat.ui.view.search

import com.unlam.feat.model.Day
import com.unlam.feat.model.Event
import com.unlam.feat.model.Player
import com.unlam.feat.model.Sport
import com.unlam.feat.ui.view.event.EventState
import com.unlam.feat.ui.view.event.new_event.NewEventState
import java.time.LocalTime

data class SearchState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val error: String = "",

    val dayId: String? = null,
    val timeStart: LocalTime? = null,
    val timeEnd: LocalTime? = null,
    val distance: String? = null,
    val sportId: String? = null,


    val sport: List<Player> = emptyList(),
    val sportList : List<Sport> = listOf(),
    val daysList : List<Day> = listOf(
        Day(id = 1, description="Domingo"),
        Day(id = 2, description="Lunes"),
        Day(id = 3, description="Martes"),
        Day(id = 4, description="Miércoles"),
        Day(id = 5, description="Jueves"),
        Day(id = 6, description="Viernes"),
        Day(id = 7, description="Sabado")
    ),

    val sportIdError : GenericError? = null,
    val distanceError : GenericError? = null,
    val dayIdError : GenericError? = null,
    val timeError : TimeError? = null,

    val showDialog: Boolean = false,

    val sportIsChecked: Boolean = false,
    val dayIsChecked: Boolean = false,
    val timeIsChecked: Boolean = false,
    val distanceIsChecked: Boolean = false,


){
    sealed class TimeError {
        object WrongTimeRange : TimeError()
        object StarTimeEmpty : TimeError()
        object EndTimeEmpty : TimeError()
        object TimeEmpty : TimeError()
    }
    sealed class GenericError {
        object FieldEmpty : GenericError()
    }
}