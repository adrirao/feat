package com.unlam.feat.repository

interface FirebaseMessagingRepository {
    fun getToken(isSuccess: (String) -> Unit)
}