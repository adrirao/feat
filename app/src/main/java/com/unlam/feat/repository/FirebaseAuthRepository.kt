package com.unlam.feat.repository

interface FirebaseAuthRepository {
    fun authenticate(email: String, password: String, isRegistered: (Boolean, Exception?) -> Unit)
    fun register(email: String, password: String, isSuccessRegistration: (Boolean, Exception?) -> Unit)
    fun isLogged(): Boolean
    fun signOut()
    fun getUserId(): String
}