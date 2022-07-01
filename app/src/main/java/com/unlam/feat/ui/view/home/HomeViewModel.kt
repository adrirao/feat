package com.unlam.feat.ui.view.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.repository.FirebaseMessagingRepositoryImp
import com.unlam.feat.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        getEvents()
    }

    private fun getEvents() {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getDataHomeEvent(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = HomeState(message = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = HomeState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = HomeState(
                        eventsSuggestedToday = result.data!!.eventOfTheWeek,
                        eventsConfirmedForMy = result.data.eventConfirmedOrApplied
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}