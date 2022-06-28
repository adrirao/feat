package com.unlam.feat.ui.view.invitation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.unlam.feat.util.Result


@HiltViewModel
class InvitationViewModel
@Inject
constructor(
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
) : ViewModel() {

    private val _state = mutableStateOf(InvitationState())
    val state: State<InvitationState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getAllInvitationsForUser()
    }



    fun onEvent(event: InvitationEvent) {
        when (event) {
            is InvitationEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    error = ""
                )
            }
        }
    }

    fun getAllInvitationsForUser() {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getAllInvitationsForUser(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = InvitationState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = InvitationState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = InvitationState(events = (result.data ?: emptyList()))
                }
            }
        }.launchIn(viewModelScope)
    }

}

