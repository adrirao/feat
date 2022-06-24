package com.unlam.feat.ui.view.event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.R
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.util.TypeClick
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.unlam.feat.util.Result

@HiltViewModel
class EventViewModel
@Inject
constructor(
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(EventState())
    val state: State<EventState> = _state

    init {
        getEvents()
    }

    private fun getEvents() {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getEventsCreatedByUser(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        EventState(
                            eventsError = EventState.GenericError.UnknowError
                        )
                }
                is Result.Loading -> {
                    _state.value = EventState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = EventState(events = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}