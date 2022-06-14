package com.unlam.feat.ui.view.event.new_event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.unlam.feat.ui.util.TypeValueChange
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class NewEventViewModel
@Inject
constructor(

) : ViewModel() {
    private val _state = mutableStateOf(NewEventState())
    val state: State<NewEventState> = _state

    fun onEvent(event: NewEventEvents) {
        when (event) {
            is NewEventEvents.onValueChange -> {
                when (event.typeValueChange) {
                    TypeValueChange.OnValueChangeName -> {
                        _state.value = _state.value.copy(
                            name = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeDate -> {
                        _state.value = _state.value.copy(
                            date = LocalDate.parse(event.value)
                        )
                    }
                    TypeValueChange.OnValueChangeStartTime -> {
                        _state.value = _state.value.copy(
                            startTime = LocalTime.parse(event.value)
                        )
                    }
                    TypeValueChange.OnValueChangePerioridicity -> {
                        _state.value = _state.value.copy(
                            periodicity = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeDescription -> {
                        _state.value = _state.value.copy(
                            description = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeOrganizer -> {
                        _state.value = _state.value.copy(
                            organizer = event.value
                        )
                    }
                    TypeValueChange.OnValueChangePosition -> {
                        _state.value = _state.value.copy(
                            latitude = event.value,
                            longitude = event.valueOpt!!
                        )
                    }
                    TypeValueChange.OnValueChangeAddress -> {
                        _state.value = _state.value.copy(
                            address = event.value
                        )
                    }
                }
            }
        }
    }
}