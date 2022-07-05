package com.unlam.feat.repository

import com.unlam.feat.ui.view.chat.Message
import com.unlam.feat.util.Result
import kotlinx.coroutines.flow.Flow

interface FirebaseFirestoreRepository {
    fun createChatEvent(eventId: Int)
    fun sendMessage(eventId: Int, message: Message)
    fun getMessages(eventId: Int): Flow<Result<List<Message>>>
}