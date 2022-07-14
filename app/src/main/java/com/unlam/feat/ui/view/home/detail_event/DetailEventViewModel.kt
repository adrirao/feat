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
                    success = false
                )
            }
            is DetailEventHomeEvent.ApplyEvent -> {
                applyEvent()
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
                    loadQualificationsDefault(result.data!!.playersConfirmed)
                    val players = result.data!!.players
                    var playerId: String = ""

                    players.forEach { player ->
                        if (player.sport.id == result.data.event.sport.sportGeneric.id) {
                            playerId = player.id.toString()
                        }
                    }
                    var playersConfirmed = result.data.playersConfirmed
                    val playersUid = result.data.playersUids

                    firebaseStorageRepository.getUris(playersUid) { listUris ->
                        playersUid.forEach { player ->
                            val uri = listUris.find { uriFilter -> uriFilter.contains(player.uId) }
                            if(uri != null){
                                playersConfirmed.forEach { playerConfirmed ->
                                    if(player.playerId == playerConfirmed.id){
                                        playerConfirmed.uri = uri
                                    }
                                }
                            }
                        }
                        _state.value = DetailEventHomeState(
                            event = result.data!!.event,
                            players = playersConfirmed,
                            idPlayer = playerId
                        )
                    }




                }
            }
        }.launchIn(viewModelScope)
    }

    fun qualifyPlayers() {
        val idEvent = _state.value.event!!.id
        val qualifications = qualifications
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
                    Log.d("FeatLog", "Actualizado con exito.")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadQualificationsDefault(players: List<Player>): SnapshotStateList<Qualification> {
        players.forEach {
            _qualifications.add(Qualification(id = it.id))
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
            qualification.observation
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
                        success = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}