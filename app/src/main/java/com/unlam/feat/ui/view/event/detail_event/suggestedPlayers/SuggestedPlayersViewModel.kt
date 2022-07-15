package com.unlam.feat.ui.view.event.detail_event.suggestedPlayers

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.model.Person
import com.unlam.feat.model.request.RequestCreateInvitation
import com.unlam.feat.model.request.RequestFilterEvent
import com.unlam.feat.model.request.RequestFilterPlayers
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.repository.FirebaseStorageRepositoryImp
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
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val firebaseStorageRepository: FirebaseStorageRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(SuggestedPlayersState())
    val state: State<SuggestedPlayersState> = _state

    fun onEvent(event: SuggestedPlayersEvent) {
        when (event) {
            is SuggestedPlayersEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    error = ""
                )
            }
            is SuggestedPlayersEvent.OnClick.Invite -> {
                _state.value = _state.value.copy(
                    idPlayer = event.idPlayer
                )
                invitePlayer()
            }
        }
    }

    fun getSuggestedPlayers(idEvent: Int) {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getAllPlayersSuggestedForEvent(idEvent, uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        SuggestedPlayersState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = SuggestedPlayersState(isLoading = true)
                }
                is Result.Success -> {

                    var playersSuggested = result.data!!.players
                    val playersPhotoUrl = result.data!!.playersPhotoUrl


                    playersPhotoUrl.forEach { player ->
                        playersSuggested?.forEach { playersSuggested ->
                            if (player.playerId == playersSuggested.id) {
                                playersSuggested.photoUrl = player.photoUrl
                            }
                        }
                    }

                        _state.value = _state.value.copy(
                            idEvent = idEvent,
                            players = result.data?.players!!,
                            isLoading = false
                        )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun invitePlayer() {

        val requestCreateInvitation = RequestCreateInvitation(
            playerId = state.value.idPlayer.toString(),
            eventId = _state.value.idEvent,
            origin = "O"
        )

        featRepository.createInvitation(requestCreateInvitation).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successPlayer = true,
                        successTitle = "Invitacion enviada",
                        successDescription = "La invitacion se ha enviado con exito",
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message!!
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}