package com.unlam.feat.ui.view.search

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.model.Person
import com.unlam.feat.model.request.RequestEvent
import com.unlam.feat.model.request.RequestFilterEvent
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
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
    lateinit var person : Person;

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
                    TypeValueChange.OnValueChangeSportGeneric -> {
                        _state.value = _state.value.copy(
                            sportGeneric = if(event.value == "") null else event.value
                        )

                    }
                    TypeValueChange.OnValueChangeDay -> {
                        _state.value = _state.value.copy(
                            day = if(event.value == "") null else event.value
                        )
                    }
                    TypeValueChange.OnValueChangeStartTime -> {
                        _state.value = _state.value.copy(
                            time_start = if(event.value == "") null else event.valueLocalTimeOpt
                        )
                    }
                    TypeValueChange.OnValueChangeEndTime -> {
                        _state.value = _state.value.copy(
                            time_end = if(event.value == "") null else event.valueLocalTimeOpt
                        )
                    }
                    TypeValueChange.OnValueChangeDistance -> {
                        _state.value = _state.value.copy(
                            distance = if(event.value == "") null else event.value
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
                            distanceIsChecked =  event.valueBooleanOpt!!,
                        )
                    }
                    else -> {}
                }
            }
            is SearchEvent.RefreshData ->{
                getDataSearch()
            }
            SearchEvent.OnClick(TypeClick.Submit) ->{
                filterEvents()

            }
        }
    }


    private fun getDataSearch(){
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
                    _state.value = SearchState(sport = result.data?.players ?: listOf() , events = result.data?.events ?: listOf())
                    person = result.data?.person!!
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun filterEvents(){

        var distance: String? = person.willingDistance.toString();
        if(state.value.distance != ""){
            distance = state.value.distance
        }
        var sportGeneric: String? = "0";
        if(state.value.sportGeneric != null){
            sportGeneric = state.value.sportGeneric
        }
        var dayId: String? = "0";
        if(state.value.day != null){
            dayId = state.value.day
        }
        var startTime: LocalTime? = LocalTime.of(1,0,0);
        if(state.value.time_start != null){
            startTime = state.value.time_start
        }
        var endTime: LocalTime? = LocalTime.of(23,59,59);
        if(state.value.time_end !== null){
            endTime = state.value.time_end
        }

        val uId = firebaseAuthRepository.getUserId()

            val request = RequestFilterEvent(
                uid=uId,
                sportGenericId = sportGeneric?.toInt(),
                dayId = dayId?.toInt(),
                distance = distance?.toInt(),
                startTime = startTime.toString(),
                endTime = endTime.toString()
            )


        if(request.sportGenericId == null && request.dayId == null && request.distance == null
            && request.startTime == null && request.endTime == null){
            getDataSearch()
        }else {
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
                        _state.value =
                            SearchState(events = result.data?.events!!, sport = result.data.players)
                        person = result.data.person
                    }
                }
            }.launchIn(viewModelScope)
        }

    }
}