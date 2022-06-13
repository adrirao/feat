package com.unlam.feat.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepositoryImp
@Inject
constructor(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthRepository {
    override fun authenticate(
        email: String,
        password: String,
        isRegistered: (Boolean, Exception?) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (userVerifiedMail()) {
                        isRegistered(true, null)
                    } else {
                        signOut()
                        isRegistered(false, Exception("Verify email."))
                    }
                } else {
                    isRegistered(false, task.exception)
                }
            }
    }

    override fun register(
        email: String,
        password: String,
        isSuccessRegistration: (Boolean, Exception?) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> {
                        firebaseAuth.currentUser!!.sendEmailVerification()
                            .addOnCompleteListener {
                                isSuccessRegistration(true, null)
                            }
                    }
                    task.exception is FirebaseAuthUserCollisionException -> {
                        isSuccessRegistration(false, task.exception)
                    }
                    else -> {
                        isSuccessRegistration(false, Exception("Registration has failed."))
                    }
                }
            }
    }

    override fun isLogged(): Boolean {
        return firebaseAuth.currentUser?.uid != null && userVerifiedMail()
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun getUserId(): String {
        return firebaseAuth.currentUser?.uid ?: ""
    }

    private fun userVerifiedMail(): Boolean {
        return firebaseAuth.currentUser!!.isEmailVerified
    }

}