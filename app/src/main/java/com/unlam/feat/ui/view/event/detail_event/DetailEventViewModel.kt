package com.unlam.feat.ui.view.event.detail_event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.model.request.RequestCreateInvitation
import com.unlam.feat.model.request.RequestEventApply
import com.unlam.feat.model.request.RequestEventState
import com.unlam.feat.presentation.view.events.detail_event.DetailEventEvent
import com.unlam.feat.presentation.view.events.detail_event.DetailEventState
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.unlam.feat.util.Result
import com.unlam.feat.util.print

@HiltViewModel
class DetailEventViewModel
@Inject
constructor(
    val featRepository: FeatRepositoryImp,
    val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(DetailEventState())
    val state: State<DetailEventState> = _state


    fun onEvent(event: DetailEventEvent) {
        when (event) {
            is DetailEventEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "",
                    successPlayer = false,
                    successConfirmEvent = false
                )
            }
            is DetailEventEvent.CancelEvent -> {
                cancelEvent()
            }
            is DetailEventEvent.ConfirmEvent -> {
                confirmEvent()
            }
            is DetailEventEvent.KickPlayer -> {
                _state.value = _state.value.copy(
                    idPlayer = event.userId
                )
                kickPlayer()
            }
            is DetailEventEvent.RejectPlayer -> {
                _state.value = _state.value.copy(
                    idPlayer = event.userId
                )
                rejectPlayer()
            }
            is DetailEventEvent.AcceptPlayer -> {
                _state.value = _state.value.copy(
                    idPlayer = event.userId
                )
                acceptPlayer()
            }
            is DetailEventEvent.InvitePlayer -> {
                _state.value = _state.value.copy(
                    idPlayer = event.userId
                )
                invitePlayer()
            }

        }
    }

    private fun kickPlayer() {
        val requestkickPlayer = RequestEventApply(
            playerId = state.value.idPlayer.toString(),
            eventId = state.value.event!!.id
        )

        featRepository.setKickApply(requestkickPlayer).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successPlayer = true,
                        successTitle = "Jugador explulsado",
                        successDescription = "El jugador ha sido expulsado con exito",
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = "Algo malo ocurrio!"
                    )
                    print(requestkickPlayer,result)
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun invitePlayer() {

        val requestCreateInvitation = RequestCreateInvitation(
            playerId = state.value.idPlayer.toString(),
            eventId = _state.value.event!!.id,
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


    private fun rejectPlayer() {
        val requestEventApply = RequestEventApply(
            playerId = state.value.idPlayer.toString(),
            eventId = state.value.event!!.id
        )

        featRepository.setDeniedApply(requestEventApply).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successPlayer = true,
                        successTitle = "Jugador rechazado",
                        successDescription = "El jugador ha sido rechazado con exito",
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

    private fun acceptPlayer() {
        val requestEventApply = RequestEventApply(
            playerId = state.value.idPlayer.toString(),
            eventId = state.value.event!!.id
        )
        featRepository.setAcceptedApply(requestEventApply).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successPlayer = true,
                        successTitle = "Jugador aceptado",
                        successDescription = "El jugador ha sido aceptado con exito",
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

    private fun confirmEvent() {
        val requestEventState = RequestEventState(
            id = state.value.event!!.id
        )
        featRepository.setConfirmed(requestEventState).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successConfirmEvent = true,
                        successTitle = "Evento confirmado",
                        successDescription = "El evento se ha confirmado con exito"
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

    private fun cancelEvent() {
        val requestEventState = RequestEventState(
            id = state.value.event!!.id
        )
        featRepository.setCanceled(requestEventState).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successCancelEvent = true,
                        successTitle = "Evento cancelado",
                        successDescription = "El evento se ha cancelado con exito"
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


    fun refreshData() {
        val uId = firebaseAuthRepository.getUserId()
        val idEvent = state.value.event!!.id
        featRepository.getDataDetailEvent(idEvent, uId = uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        _state.value.copy(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        event = result.data!!.event,
                        playersApplied = result.data.playersApplied,
                        playersConfirmed = result.data.playersConfirmed,
                        playersSuggested = result.data.playersSuggested,
                        isLoading = false,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    fun getDataDetailEvent(idEvent: Int) {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getDataDetailEvent(idEvent,uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        DetailEventState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = DetailEventState(loading = true)
                }
                is Result.Success -> {
                    _state.value = DetailEventState(
                        event = result.data!!.event,
                        playersApplied = result.data.playersApplied,
                        playersConfirmed = result.data.playersConfirmed,
                        playersSuggested = result.data.playersSuggested
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}