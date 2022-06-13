package com.unlam.feat.ui.view.register

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.unlam.feat.model.request.RequestUser
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private  val featRepository: FeatRepositoryImp
): ViewModel() {
    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    fun onEvent(event: RegisterEvents) {
        when (event) {
            is RegisterEvents.onValueChange -> {
                when (event.typeValueChange) {
                    TypeValueChange.onValueChangeEmail -> {
                        _state.value = _state.value.copy(
                            textEmail = event.value
                        )
                    }
                    TypeValueChange.onValueChangeReEmail -> {
                        _state.value = _state.value.copy(
                            textReEmail = event.value
                        )
                    }
                    TypeValueChange.onValueChangePassword -> {
                        _state.value = _state.value.copy(
                            textPassword = event.value
                        )
                    }
                    TypeValueChange.onValueChangeRePassword -> {
                        _state.value = _state.value.copy(
                            textRePassword = event.value
                        )
                    }
                }
            }
             RegisterEvents.onClick(TypeClick.toggledPassword) -> {
                _state.value = _state.value.copy(
                    isVisiblePassword = !_state.value.isVisiblePassword
                )
            }
            RegisterEvents.onClick(TypeClick.toggledRePassword) -> {
                _state.value = _state.value.copy(
                    isVisibleRePassword = !_state.value.isVisibleRePassword
                )
            }
            RegisterEvents.onClick(TypeClick.dismissDialog) -> {
                _state.value = _state.value.copy(
                    registrationMessage = null
                )
            }
            RegisterEvents.onClick(TypeClick.register) -> {
                validateEmail(_state.value.textEmail)
                validateReEmail(_state.value.textReEmail)
                validatePassword(_state.value.textPassword)
                validateRePassword(_state.value.textRePassword)
                registerUser()
            }
        }
    }

    private fun validateEmail(email:String) {
        val trimmedEmail = email.trim()
        if(trimmedEmail.isBlank()){
            _state.value = _state.value.copy(
                emailError = RegisterState.EmailError.FieldEmpty
            )
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.value = _state.value.copy(
                emailError = RegisterState.EmailError.InvalidEmail
            )
            return
        }
        _state.value = _state.value.copy(emailError = null)
    }

    private fun validateReEmail(reEmail:String) {
        val trimmedEmail = reEmail.trim()

        if(reEmail != _state.value.textEmail){
            _state.value = _state.value.copy(
                reEmailError = RegisterState.EmailError.DiffEmail
            )
            return
        }

        if(trimmedEmail.isBlank()){
            _state.value = _state.value.copy(
                reEmailError = RegisterState.EmailError.FieldEmpty
            )
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(reEmail).matches()) {
            _state.value = _state.value.copy(
                reEmailError = RegisterState.EmailError.InvalidEmail
            )
            return
        }
        _state.value = _state.value.copy(reEmailError = null)
    }

    private fun validatePassword(password: String) {
        if(password.isBlank()) {
            _state.value = _state.value.copy(
                passwordError = RegisterState.PasswordError.FieldEmpty
            )
            return
        }
        if(password.length < Constants.MIN_PASSWORD_LENGTH) {
            _state.value = _state.value.copy(
                passwordError = RegisterState.PasswordError.InputTooShort
            )
            return
        }
        val capitalLettersInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }
        if(!capitalLettersInPassword || !numberInPassword) {
            _state.value = _state.value.copy(
                passwordError = RegisterState.PasswordError.InvalidPassword
            )
            return
        }
        _state.value = _state.value.copy(passwordError = null)
    }

    private fun validateRePassword(rePassword: String) {
        if(rePassword.isBlank()) {
            _state.value = _state.value.copy(
                rePasswordError = RegisterState.PasswordError.FieldEmpty
            )
            return
        }
        if(rePassword != _state.value.textPassword){
            _state.value = _state.value.copy(
                rePasswordError = RegisterState.PasswordError.DiffPassword
            )
            return
        }
        if(rePassword.length < Constants.MIN_PASSWORD_LENGTH) {
            _state.value = _state.value.copy(
                rePasswordError = RegisterState.PasswordError.InputTooShort
            )
            return
        }
        val capitalLettersInPassword = rePassword.any { it.isUpperCase() }
        val numberInPassword = rePassword.any { it.isDigit() }
        if(!capitalLettersInPassword || !numberInPassword) {
            _state.value = _state.value.copy(
                rePasswordError = RegisterState.PasswordError.InvalidPassword
            )
            return
        }
        _state.value = _state.value.copy(rePasswordError = null)
    }

    private fun registerUser(){
        val email = if (_state.value.emailError == null) _state.value.textEmail else return
        val password = if (_state.value.passwordError == null) _state.value.textPassword else return
        val reEmail = if (_state.value.reEmailError == null) _state.value.textReEmail else return
        val rePassword = if (_state.value.rePasswordError == null) _state.value.textRePassword else return

        firebaseAuthRepository.register(email, password) { isSuccessRegistration, error ->
            if (isSuccessRegistration) {
                val uid = firebaseAuthRepository.getUserId()
                val request = RequestUser(uid = uid, email = email, "2")
                featRepository.createUser(request).onEach { result ->
                    when (result) {
                        is com.unlam.feat.util.Result.Success -> {
                            _state.value = _state.value.copy(
                                registrationMessage = RegisterState.RegistrationMessage.RegistrationSuccess
                            )
                        }
                        is com.unlam.feat.util.Result.Error -> {
                            Log.e("Rao", result.message.toString())
                            _state.value = _state.value.copy(
                                registrationMessage = RegisterState.RegistrationMessage.RegistrationError
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            } else if (error is FirebaseAuthUserCollisionException) {
                _state.value = _state.value.copy(
                    registrationMessage = RegisterState.RegistrationMessage.UserAlreadyExist
                )
            }
        }

    }
}