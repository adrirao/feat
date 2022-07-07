package com.unlam.feat.ui.view.event.detail_event.suggestedPlayers

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.model.request.RequestFilterEvent
import com.unlam.feat.model.request.RequestFilterPlayers
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.event.detail_event.DetailEventState
import com.unlam.feat.ui.view.search.SearchEvent
import com.unlam.feat.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SuggestedPlayersViewModel
@Inject
constructor(
    val featRepository: FeatRepositoryImp,
    val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(SuggestedPlayersState())
    val state: State<SuggestedPlayersState> = _state
    var eventId: Int = 0

    init {
    }

    fun onEvent(event: SuggestedPlayersEvent) {
        when (event) {
            is SuggestedPlayersEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    error = ""
                )
            }
            is SuggestedPlayersEvent.ChangeDialog -> {
                _state.value = _state.value.copy(
                    showDialog = !_state.value.showDialog
                )
            }
            is SuggestedPlayersEvent.onValueChange -> {
                when (event.typeValueChange) {
                    TypeValueChange.OnValueChangeMinAge -> {
                        _state.value = _state.value.copy(
                            minAge = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeMaxAge -> {
                        _state.value = _state.value.copy(
                            maxAge = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeDistance -> {
                        _state.value = _state.value.copy(
                            distance = event.value
                        )
                    }
                }
            }
            is SuggestedPlayersEvent.RefreshData ->{
                getSuggestedPlayers(eventId)
            }
            SuggestedPlayersEvent.OnClick(TypeClick.Submit) ->{
                filterPlayers()
            }
        }
    }

    fun getSuggestedPlayers(idEvent: Int) {
        eventId = idEvent
        featRepository.getAllPlayersSuggestedForEvent(idEvent).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        SuggestedPlayersState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = SuggestedPlayersState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = SuggestedPlayersState(
                        players = result.data!!,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun filterPlayers(){
        var distance: String? = null
        if(state.value.distance != ""){
            distance = state.value.distance
        }
        var minAge: String? = null
        if(state.value.minAge != ""){
            minAge = state.value.minAge
        }
        var maxAge: String? = null
        if(state.value.maxAge != ""){
            maxAge = state.value.maxAge
        }

        val requestFilterPlayers = RequestFilterPlayers(
            eventId= state.value.idEvent,
            distance = distance?.toInt(),
            max_age = maxAge?.toInt(),
            min_age = minAge?.toInt()
        )

        if(requestFilterPlayers.distance == null && requestFilterPlayers.max_age == null && requestFilterPlayers.min_age == null){
            getSuggestedPlayers(eventId)
        }else {
            featRepository.filterPlayersForEvent(requestFilterPlayers).onEach { result ->
                when (result) {
                    is Result.Error -> {
                        _state.value =
                            SuggestedPlayersState(error = result.message ?: "Error Inesperado")
                    }
                    is Result.Loading -> {
                        _state.value = SuggestedPlayersState(isLoading = true)
                    }
                    is Result.Success -> {
                        _state.value = SuggestedPlayersState(
                            players = result.data?.players!!,
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }

    }
}