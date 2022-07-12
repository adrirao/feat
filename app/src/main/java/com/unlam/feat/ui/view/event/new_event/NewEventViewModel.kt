package com.unlam.feat.ui.view.event.new_event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.model.request.RequestEvent
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.repository.FirebaseMessagingRepositoryImp
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import com.unlam.feat.util.Result

@HiltViewModel
class NewEventViewModel
@Inject
constructor(
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val firebaseMessagingRepository: FirebaseMessagingRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(NewEventState())
    val state: State<NewEventState> = _state

    init {
        getPeriodicities()
        getEvents()
    }

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
                    TypeValueChange.OnValueChangeEndTime -> {
                        _state.value = _state.value.copy(
                            endTime = LocalTime.parse(event.value)
                        )
                    }
                    TypeValueChange.OnValueChangePeriodicity -> {
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
                    TypeValueChange.OnValueChangeSportGeneric -> {
                        _state.value = _state.value.copy(
                            sportGeneric = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeTypeSport -> {
                        _state.value = _state.value.copy(
                            sport = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeCapacity -> {
                        _state.value = _state.value.copy(
                            capacity = event.value
                        )
                    }
                }
            }
            NewEventEvents.onClick(TypeClick.DismissDialog) -> {
                _state.value = _state.value.copy(
                    newEventMessage = null,
                    periodicityMessage = null
                )
            }
            NewEventEvents.onClick(TypeClick.Submit) -> {
                validateSportGeneric(_state.value.sportGeneric)
                validateSport(_state.value.sport)
                validateName(_state.value.name)
                validateDate(_state.value.date)
                validateStartTime(_state.value.startTime)
                validateEndTime(_state.value.endTime)
                validatePeriodicity(_state.value.periodicity)
                validateDescription(_state.value.description)
                validateOrganizer(_state.value.organizer)
                validateAddress(_state.value.address)
//                validateCapacity(_state.value.capacity)
                createEvent()
            }
        }
    }

    private fun validateSportGeneric(sportGeneric: String) {
        val trimmedSportGeneric = sportGeneric.trim()
        if (trimmedSportGeneric.isBlank()) {
            _state.value = _state.value.copy(
                sportGenericError = NewEventState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(sportGenericError = null)
    }

    private fun validateSport(sport: String) {
        val trimmedSport = sport.trim()
        if (trimmedSport.isBlank()) {
            _state.value = _state.value.copy(
                sportError = NewEventState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(sportError = null)
    }


    private fun validateCapacity(capacity: String) {
        val trimmedCapacity = capacity.trim()
        if (trimmedCapacity.isBlank()) {
            _state.value = _state.value.copy(
                capacityError = NewEventState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(capacityError = null)
    }

    private fun validateName(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                nameError = NewEventState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(nameError = null)
    }

    private fun validateDate(date: LocalDate?) {
        if (date == null) {
            _state.value = _state.value.copy(
                dateError = NewEventState.GenericError.FieldEmpty
            )
            return
        } else if (date.isBefore(LocalDate.now())) {
            _state.value = _state.value.copy(
                dateError = NewEventState.DateError.DateInvalid
            )
        }
        _state.value = _state.value.copy(dateError = null)
    }

    private fun validateStartTime(startTime: LocalTime?) {
        if (startTime == null) {
            _state.value = _state.value.copy(
                startTimeError = NewEventState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(startTimeError = null)
    }

    private fun validateEndTime(endTime: LocalTime?) {
        if (endTime == null) {
            _state.value = _state.value.copy(
                endTimeError = NewEventState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(endTimeError = null)
    }

    private fun validatePeriodicity(periodicity: String) {
        val trimmedPerioridicity = periodicity.trim()
        if (trimmedPerioridicity.isBlank()) {
            _state.value = _state.value.copy(
                periodicityError = NewEventState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(periodicityError = null)
    }

    private fun validateDescription(description: String) {
        val trimmedDescription = description.trim()
        if (trimmedDescription.isBlank()) {
            _state.value = _state.value.copy(
                descriptionError = NewEventState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(descriptionError = null)
    }

    private fun validateOrganizer(organizer: String) {
        val trimmedOrganizer = organizer.trim()
        if (trimmedOrganizer.isBlank()) {
            _state.value = _state.value.copy(
                organizerError = NewEventState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(organizerError = null)
    }

    private fun validateAddress(address: String) {
        val trimmedAddress = address.trim()
        if (trimmedAddress.isBlank()) {
            _state.value = _state.value.copy(
                addressError = NewEventState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(addressError = null)
    }

    private fun getEvents() {
        val uId: String = firebaseAuthRepository.getUserId()

        featRepository.getDataAddEvent(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = NewEventState(
                        error = result.message ?: "Error Inesperado"
                    )
                }
                is Result.Loading -> {
                    _state.value = NewEventState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = NewEventState(
                        periodicityList = result.data!!.periodicityList,
                        person = result.data.person,
                        sportGenericList = result.data.sportGenericList,
                        sportList = result.data.sportList
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun createEvent() {
        val name = if (_state.value.nameError == null) _state.value.name else return
//        val capacity = if (_state.value.capacityError == null) _state.value.capacity else return
        val date = if (_state.value.dateError == null) _state.value.date else return
        val startTime = if (_state.value.startTimeError == null) _state.value.startTime else return
        val endTime = if (_state.value.endTimeError == null) _state.value.endTime else return
        val description =
            if (_state.value.descriptionError == null) _state.value.description else return
        val periodicity =
            if (_state.value.periodicityError == null) _state.value.periodicity else return
        val latitude = if (_state.value.latitudeError == null) _state.value.latitude else return
        val longitude = if (_state.value.longitudeError == null) _state.value.longitude else return
        val state = if (_state.value.stateError == null) _state.value.state else return
        val sport = if (_state.value.sportError == null) _state.value.sport else return
        if (_state.value.sportGenericError == null) _state.value.sportGeneric else return
        val organizer = _state.value.person!!.id

        val request = RequestEvent(
            name = name,
            date = date.toString(),
            startTime = startTime.toString(),
            endTime = endTime.toString(),
            description = description,
            periodicity = periodicity,
            latitude = latitude,
            longitude = longitude,
            state = state,
            sport = sport,
            organizer = organizer,
            capacity = if(_state.value.capacity.isEmpty()) null else _state.value.capacity
        )

        featRepository.postEvent(request).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = NewEventState(
                        newEventMessage = NewEventState.NewEventMessage.NewEventError
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Result.Success -> {
                    _state.value = NewEventState(
                        newEventMessage = NewEventState.NewEventMessage.NewEventSuccess
                    )
                }
            }
        }.launchIn(viewModelScope)

    }

    fun getPeriodicities() {
        featRepository.getPeriodicities().onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = NewEventState(
                        periodicityMessage = NewEventState.PeriodicitiesMessage.PeriodicitiesError
                    )
                }
                is Result.Loading -> {
                    _state.value = NewEventState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = NewEventState(periodicityList = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

}