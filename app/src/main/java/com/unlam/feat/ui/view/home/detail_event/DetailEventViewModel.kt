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
import com.unlam.feat.model.request.RequestQualifyPlayers
import com.unlam.feat.repository.FeatRepositoryImp
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
    val featRepository: FeatRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(DetailEventHomeState())
    val state: State<DetailEventHomeState> = _state

    private val _qualifications = mutableStateListOf<Qualification>()
    val qualifications: List<Qualification> = _qualifications

    fun onEvent(event: DetailEventHomeEvent) {
        when (event) {
            is DetailEventHomeEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    loading = false
                )
            }
        }
    }

    fun getDataDetailEvent(idEvent: Int) {

        featRepository.getDataDetailEvent(idEvent, "").onEach { result ->
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
                    _state.value = DetailEventHomeState(
                        event = result.data!!.event,
                        players = result.data.playersConfirmed,
                    )
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
}