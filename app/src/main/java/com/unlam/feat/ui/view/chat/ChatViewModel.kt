package com.unlam.feat.ui.view.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.repository.FirebaseFirestoreRepositoryImp
import com.unlam.feat.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel
@Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val featRepository: FeatRepositoryImp,
    private val firebaseFirestoreRepository: FirebaseFirestoreRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(ChatState())
    val state: State<ChatState> = _state

    fun onValueChange(event: ChatEvents) {
        when (event) {
            is ChatEvents.OnChangeValue -> {
                _state.value = _state.value.copy(
                    textMessage = event.value
                )
            }
        }
    }

    fun onClick(click: ChatEvents.OnClick.TypeClick) {
        when (click) {
            is ChatEvents.OnClick.TypeClick.OnClickSendMessage -> {
                sendMessage(click.message)
            }
        }
    }

    private fun sendMessage(message: String) {
        val uId = firebaseAuthRepository.getUserId()
        val dataMessage = Message(
            from = uId,
            message = message
        )
        _state.value = _state.value.copy(
            textMessage = ""
        )
        if(message.trim().isNotEmpty()){
            firebaseFirestoreRepository.sendMessage(_state.value.event!!.id, message = dataMessage)
            getMessages()
        }
    }

    private fun getMessages() {
        viewModelScope.launch {
            firebaseFirestoreRepository.getMessageListFlow(_state.value.event!!.id).collect {
                _state.value = _state.value.copy(
                    messages = it,
                )
            }
        }
    }

    fun getEvent(idEvent: Int) {
        val idEvents = "48"
        featRepository.getEventById(idEvent).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = ChatState(event = result.data)
                    getMessages()
                }
                is Result.Error -> {

                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}