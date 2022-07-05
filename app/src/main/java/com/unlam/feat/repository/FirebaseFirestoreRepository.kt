package com.unlam.feat.repository

interface FirebaseFirestoreRepository {
    fun createChatEvent(eventId: Int)
}