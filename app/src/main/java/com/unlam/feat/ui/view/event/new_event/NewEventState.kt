package com.unlam.feat.ui.view.event.new_event

import com.unlam.feat.model.Periodicity
import java.time.LocalDate
import java.time.LocalTime

data class NewEventState(
    val isCreatedMessage: String? = "",
    val error: String = "",
    val isLoading: Boolean = false,
    val periodicityList: List<Periodicity> = listOf(),
    val address: String = " ",

    val name: String = "",
    val date: LocalDate? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val description: String = "",
    val periodicity: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val state: String = "1",
    val sport: String = "",
    val organizer: String = "1"
)
