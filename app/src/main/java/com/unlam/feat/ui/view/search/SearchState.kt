package com.unlam.feat.ui.view.search

import com.unlam.feat.model.Day
import com.unlam.feat.model.Event
import com.unlam.feat.model.Player
import com.unlam.feat.model.Sport
import com.unlam.feat.ui.view.event.new_event.NewEventState
import java.time.LocalTime

data class SearchState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val error: String = "",
    val time_start: LocalTime? = null,
    val time_end: LocalTime? = null,

    val sportGeneric: String? = null,
    val sportId: String? = null,
    val day: String? = null,
    val distance: String? = null,

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

    val sportGenericError : NewEventState.GenericError? = null,
    val showDialog: Boolean = false,

    val sportIsChecked: Boolean = false,
    val dayIsChecked: Boolean = false,
    val timeIsChecked: Boolean = false,
    val distanceIsChecked: Boolean = false,
)