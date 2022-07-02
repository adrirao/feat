package com.unlam.feat.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseMessagingRepositoryImp
@Inject
constructor(
    private val firebaseMessaging: FirebaseMessaging
) : FirebaseMessagingRepository {
    override fun getToken(isSuccess: (String) -> Unit) {
        firebaseMessaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            // Log and toast
            isSuccess(token.toString())
        })
    }
}