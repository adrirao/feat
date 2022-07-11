package com.unlam.feat.ui.view.chat

import com.unlam.feat.model.Event
import com.unlam.feat.model.Person
import java.util.*

data class ChatState(
    val textMessage: String = "",
    val message: Message? = null,
    val isLoading: Boolean = false,
    val event: Event? = null,
    val messages: List<Message> = listOf(),
    val errorEvent: ErrorChat? = null,
    val errorChat: ErrorChat? = null,
    val person: Person? = null
) {
    sealed class ErrorChat {
        object UnknowError : ErrorChat()
    }

    sealed class ErrorEvent {
        object NotFound : ErrorEvent()
    }
}

data class Message(
    val dob: Date = Date(),
    val message: String = "",
    val from: String = ""
)