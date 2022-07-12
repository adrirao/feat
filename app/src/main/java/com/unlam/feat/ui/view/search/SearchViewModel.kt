package com.unlam.feat.ui.view.search

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.R
import com.unlam.feat.model.Person
import com.unlam.feat.model.request.RequestEvent
import com.unlam.feat.model.request.RequestFilterEvent
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.config_profile.ConfigProfileState
import com.unlam.feat.ui.view.event.new_event.NewEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.unlam.feat.util.Result
import java.time.LocalTime

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    lateinit var person: Person;

    init {
        getDataSearch()
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    error = ""
                )
            }
            is SearchEvent.onValueChange -> {
                when (event.typeValueChange) {
                    TypeValueChange.OnValueChangeSportId -> {
                        _state.value = _state.value.copy(
                            sportId = event.value
                        )

                    }
                    TypeValueChange.OnValueChangeDay -> {
                        _state.value = _state.value.copy(
                            dayId = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeStartTime -> {
                        _state.value = _state.value.copy(
                            timeStart = event.valueLocalTimeOpt
                        )
                    }
                    TypeValueChange.OnValueChangeEndTime -> {
                        _state.value = _state.value.copy(
                            timeEnd = event.valueLocalTimeOpt
                        )
                    }
                    TypeValueChange.OnValueChangeDistance -> {
                        _state.value = _state.value.copy(
                            distance = if (event.value == "") null else event.value
                        )
                    }
                    TypeValueChange.OnValueChangeSportIsChecked -> {
                        _state.value = _state.value.copy(
                            sportIsChecked = event.valueBooleanOpt!!,
                        )
                    }
                    TypeValueChange.OnValueChangeDayIsChecked -> {
                        _state.value = _state.value.copy(
                            dayIsChecked = event.valueBooleanOpt!!,
                        )
                    }
                    TypeValueChange.OnValueChangeTimeIsChecked -> {
                        _state.value = _state.value.copy(
                            timeIsChecked = event.valueBooleanOpt!!,
                        )
                    }
                    TypeValueChange.OnValueChangeDistanceIsChecked -> {
                        _state.value = _state.value.copy(
                            distanceIsChecked = event.valueBooleanOpt!!,
                        )
                    }
                    else -> {}
                }
            }
            is SearchEvent.RefreshData -> {
                getDataSearch()
            }
            SearchEvent.OnClick(TypeClick.Submit) -> {
                _state.value = _state.value.copy(
                    sportIdError = validateNoEmptyFieldIsChecked(
                        _state.value.sportId,
                        _state.value.sportIsChecked
                    ),
                    distanceError = validateNoEmptyFieldIsChecked(
                        _state.value.distance,
                        _state.value.distanceIsChecked
                    ),
                    dayIdError = validateNoEmptyFieldIsChecked(
                        _state.value.dayId,
                        _state.value.dayIsChecked
                    ),
                    timeError = validateDayIsValidRange(
                        _state.value.timeStart,
                        _state.value.timeEnd,
                        _state.value.timeIsChecked
                    ),
                )
                filterEvents()
            }
        }
    }


    private fun getDataSearch() {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getSearchEvent(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = SearchState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = SearchState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = SearchState(
                        sport = result.data?.players ?: listOf(),
                        events = result.data?.events ?: listOf()
                    )
                    person = result.data?.person!!
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun filterEvents() {

        val sportId = if (_state.value.sportIdError == null) _state.value.sportId else return
        val distance = if (_state.value.distanceError == null) _state.value.distance else return
        val dayId = if (_state.value.dayIdError == null) _state.value.dayId else return
        val startTime = if (_state.value.timeError == null) _state.value.timeStart else return
        val endTime = if (_state.value.timeError == null) _state.value.timeEnd else return

        val uId = firebaseAuthRepository.getUserId()

        val request = RequestFilterEvent(
            uid = uId,
            sportGenericId = sportId,
            dayId = dayId,
            distance = distance,
            startTime = if(startTime == null) null else startTime.toString(),
            endTime = if(endTime == null) null else endTime.toString(),
        )



        featRepository.getFilterSearchEvent(uId, request).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = SearchState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        events = result.data?.events!!,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun validateDayIsValidRange(
        dayStar: LocalTime?,
        dayEnd: LocalTime?,
        dayIsChecked: Boolean
    ): SearchState.TimeError? {
        if (dayIsChecked) {
            if (dayEnd == null && dayStar == null) {
                return SearchState.TimeError.TimeEmpty
            }
            if (dayStar == null) {
                return SearchState.TimeError.StarTimeEmpty
            }
            if (dayEnd == null) {
                return SearchState.TimeError.EndTimeEmpty
            }
            if (dayStar.isAfter(dayEnd)) {
                return SearchState.TimeError.WrongTimeRange
            }
        }
        return null
    }

    private fun validateNoEmptyFieldIsChecked(
        field: String?,
        isChecked: Boolean
    ): SearchState.GenericError? {
        if (isChecked) {
            val trimmedField = field?.trim() ?: ""
            if (trimmedField.isBlank()) {
                return SearchState.GenericError.FieldEmpty
            }
        }
        return null
    }


}