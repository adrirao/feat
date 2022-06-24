package com.unlam.feat.ui.view.event.new_event

import com.unlam.feat.model.Periodicity
import com.unlam.feat.model.Person
import com.unlam.feat.model.Sport
import com.unlam.feat.model.SportGeneric
import java.time.LocalDate
import java.time.LocalTime

data class NewEventState(
    val isCreatedMessage: String? = "",
    val error: String = "",
    val isLoading: Boolean = false,
    val periodicityList: List<Periodicity> = listOf(),
    val person : Person? = null,
    val sportGenericList: List<SportGeneric> = listOf(),
    val sportList : List<Sport> = listOf(),
    val address: String = "",

    val name: String = "",
    val date: LocalDate? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val description: String = "",
    val periodicity: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val state: String = "",
    val sport: String = "",
    val organizer: String = "",
    val sportGeneric: String = "",

    val nameError: GenericError? = null,
    val dateError: GenericError? = null,
    val startTimeError: GenericError? = null,
    val endTimeError: GenericError? = null,
    val descriptionError: GenericError? = null,
    val periodicityError: GenericError? = null,
    val latitudeError: GenericError? = null,
    val longitudeError: GenericError? = null,
    val stateError: GenericError? = null,
    val sportError: GenericError? = null,
    val organizerError: GenericError? = null,
    val addressError: GenericError? = null,
    val sportGenericError : GenericError? = null,

    val newEventMessage: NewEventMessage? = null,
    val periodicityMessage : PeriodicitiesMessage? = null
) {
    sealed class GenericError {
        object FieldEmpty : GenericError()
    }

    sealed class NewEventMessage {
        object NewEventSuccess : NewEventMessage()
        object NewEventError : NewEventMessage()
    }

    sealed class PeriodicitiesMessage {
        object PeriodicitiesError : PeriodicitiesMessage()
    }
}
