package com.unlam.feat.ui.view.home.detail_event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.repository.FeatRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.unlam.feat.util.Result

@HiltViewModel
class DetailEventHomeViewModel
@Inject
constructor(
    val featRepository: FeatRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(DetailEventHomeState())
    val state: State<DetailEventHomeState> = _state

    fun onEvent(event: DetailEventHomeEvent) {
        when (event) {
            is DetailEventHomeEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    loading = false
                )
            }
        }
    }

    fun getDataDetailEvent(idEvent: Int){

        featRepository.getDataSearchEvent(idEvent, "").onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        DetailEventHomeState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = DetailEventHomeState(loading = true)
                }
                is Result.Success -> {
                    _state.value = DetailEventHomeState(event = result.data!!.event, players = result.data.playersConfirmed)
                }
            }
        }.launchIn(viewModelScope)
    }
}