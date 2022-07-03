package com.unlam.feat.ui.view.event.detail_event.suggestedPlayers

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.view.event.detail_event.DetailEventState
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

    init {

    }

    fun getSuggestedPlayers(idEvent: Int) {
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
                        players = result.data!!
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}