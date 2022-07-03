package com.unlam.feat.ui.view.search

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    //val marker = mutableStateOf(com.unlam.feat.presentation.component.map.Marker())

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
            is SearchEvent.ChangeDialog -> {
                _state.value = _state.value.copy(
                    showDialog = !_state.value.showDialog
                )
            }
            is SearchEvent.onValueChange -> {
                when (event.typeValueChange) {
                    TypeValueChange.OnValueChangeSportGeneric -> {
                        _state.value = _state.value.copy(
                            sportGeneric = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeDay -> {
                        _state.value = _state.value.copy(
                            day = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeDistance -> {
                        _state.value = _state.value.copy(
                            distance = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeStartTime -> {
                        _state.value = _state.value.copy(
                            time_start = event.valueLocalTimeOpt
                        )
                    }
                    TypeValueChange.OnValueChangeEndTime -> {
                        _state.value = _state.value.copy(
                            time_end = event.valueLocalTimeOpt
                        )
                    }
                }
            }
            is SearchEvent.RefreshData ->{
                getEventsSuggestedForUser()
            }
            SearchEvent.onClick(TypeClick.Submit) ->{
                filterEvents()
            }
        }
    }

    fun getEventsSuggestedForUser() {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getEventsSuggestedForUser(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = SearchState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = SearchState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = _state.value.copy(events = result.data ?: emptyList(), isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getDataSearch(){
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
                    _state.value =
                        result.data?.let { SearchState(sport = it.players, events = it.events) }!!
                }
            }
        }.launchIn(viewModelScope)
    }

    fun filterEvents(){
        val uId = firebaseAuthRepository.getUserId()
        var distance: String? = null
        if(!state.value.distance.equals("")){
            distance = state.value.distance
        }

        val request = RequestFilterEvent(
            sportGenericId = state.value.sportGeneric?.toInt(),
            sportId = state.value.sportId?.toInt(),
            dayId = state.value.day?.toInt(),
            distance = distance?.toInt(),
            startTime = state.value.time_start,
            endTime = state.value.time_end
        )

        /*
        featRepository.getfilterEventForUser(uId,request).onEach { result ->
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
                        events = result.data!!
                    )
                }
            }
        }.launchIn(viewModelScope)*/

    }
}