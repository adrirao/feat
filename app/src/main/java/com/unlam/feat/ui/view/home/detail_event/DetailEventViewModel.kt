package com.unlam.feat.ui.view.home.detail_event

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.model.Player
import com.unlam.feat.model.Qualification
import com.unlam.feat.model.request.RequestCreateInvitation
import com.unlam.feat.model.request.RequestEventApply
import com.unlam.feat.model.request.RequestQualifyPlayers
import com.unlam.feat.repository.*
import com.unlam.feat.ui.view.search.event_detail.SearchEventDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.unlam.feat.util.Result
import kotlinx.coroutines.launch

@HiltViewModel
class DetailEventHomeViewModel
@Inject
constructor(
    val featRepository: FeatRepositoryImp,
    val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    val firebaseStorageRepository: FirebaseStorageRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(DetailEventHomeState())
    val state: State<DetailEventHomeState> = _state

    private val _qualifications = mutableStateListOf<Qualification>()
    val qualifications: List<Qualification> = _qualifications

    fun onEvent(event: DetailEventHomeEvent) {
        when (event) {
            is DetailEventHomeEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    loading = false,
                    error = "",
                    successApply = false,
                    successCancelApply = false,
                    successQualifyApply = false
                )
            }
            is DetailEventHomeEvent.ApplyEvent -> {
                applyEvent()
            }
            is DetailEventHomeEvent.CancelApplyEvent -> {
                cancelApplyEvent()
            }
            is DetailEventHomeEvent.QualificationApplyEvent -> {
                qualifyPlayers()
            }
        }
    }


    fun getDataDetailEvent(idEvent: Int) {

        val uId = firebaseAuthRepository.getUserId()
        featRepository.getDataDetailEvent(idEvent, uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        DetailEventHomeState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = DetailEventHomeState(loading = true)
                }
                is Result.Success -> {
                    val players = result.data!!.players
                    var playerId: String = ""

                    players.forEach { player ->
                        if (player.sport.id == result.data.event.sport.sportGeneric.id) {
                            playerId = player.id.toString()
                        }
                    }

                    var playersConfirmed = result.data.playersConfirmed
                    val playersPhotoUrl = result.data.playersPhotoUrl
                    var playerFiltered: MutableList<Player> = mutableListOf()

                    if (result.data.event.state.description == "Evento Terminado") {
                        loadQualificationsDefault(result.data!!.playersConfirmed, playerId.toInt())

                        playersPhotoUrl.forEach { player ->
                            playersConfirmed.forEach { playerConfirmed ->
                                if (player.playerId == playerConfirmed.id) {
                                    playerConfirmed.photoUrl = player.photoUrl
                                }
                            }
                        }

                        playersConfirmed.forEach { player ->
                            if (player.id != playerId.toInt()) {
                                playerFiltered.add(player)
                            }
                        }
                    }

                    _state.value = DetailEventHomeState(
                        event = result.data!!.event,
                        players = if (result.data.event.state.description == "Evento Terminado") playerFiltered.toList() else playersConfirmed,
                        idPlayer = playerId
                    )

                }
            }
        }.launchIn(viewModelScope)
    }

    fun qualifyPlayers() {
        val idEvent = _state.value.event!!.id
        val qualifications = qualifications
        if(qualifications.isNotEmpty()){


        val requestQualify = RequestQualifyPlayers(
            eventId = idEvent.toString(),
            players = qualifications.toList()
        )
        featRepository.qualifyPlayers(requestQualify).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        DetailEventHomeState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = DetailEventHomeState(loading = true)
                }
                is Result.Success -> {
                    _state.value = DetailEventHomeState(
                        successQualifyApply = true
                    )
                }
            }
        }.launchIn(viewModelScope)
        }
    }

    private fun loadQualificationsDefault(
        players: List<Player>,
        playerId: Int
    ): SnapshotStateList<Qualification> {
        players.forEach {
            if (playerId != it.id) {
                _qualifications.add(Qualification(id = it.id))
            }
        }
        return _qualifications
    }

    fun updateOneItem(qualification: Qualification) {
        var index = 0
        _qualifications.forEach {
            if (it.id == qualification.id) {
                return@forEach
            }
            index++
        }
        _qualifications[index] = _qualifications[index].copy(
            qualification.id,
            qualification.liked,
            qualification.observation,
            qualifier = _state.value.idPlayer!!.toInt()
        )
    }

    fun changeQualification(qualification: Qualification) {
        viewModelScope.launch {
            val qualifications = _state.value.qualifications
            qualifications!!.value.forEach { q ->
                if (q.id == qualification.id) {
                    q.liked = qualification.liked
                }
            }

            _state.value = DetailEventHomeState(
                qualifications = qualifications,
                event = _state.value.event,
                players = _state.value.players,
            )
        }
    }

    private fun cancelApplyEvent() {
        val requestEventApply = RequestEventApply(
            playerId = state.value.idPlayer.toString(),
            eventId = state.value.event!!.id
        )

        featRepository.setDeniedApply(requestEventApply).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successCancelApply = true
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        loading = true
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

    private fun applyEvent() {

        val request = RequestCreateInvitation(
            playerId = _state.value.idPlayer!!,
            eventId = _state.value.event!!.id,
            origin = "P"
        )

        featRepository.createInvitation(request).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message!!
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        loading = true
                    )
                }
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successApply = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}