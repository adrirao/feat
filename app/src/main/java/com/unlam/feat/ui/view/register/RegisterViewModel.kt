package com.unlam.feat.ui.view.register

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.messaging.FirebaseMessaging
import com.unlam.feat.model.request.RequestUser
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.repository.FirebaseMessagingRepositoryImp
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.util.Constants
import com.unlam.feat.util.Result
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
                    TypeValueChange.OnValueChangeEmail -> {
                        _state.value = _state.value.copy(
                            textEmail = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeReEmail -> {
                        _state.value = _state.value.copy(
                            textReEmail = event.value
                        )
                    }
                    TypeValueChange.OnValueChangePassword -> {
                        _state.value = _state.value.copy(
                            textPassword = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeRePassword -> {
                        _state.value = _state.value.copy(
                            textRePassword = event.value
                        )
                    }
                }
            }
             RegisterEvents.onClick(TypeClick.ToggledPassword) -> {
                _state.value = _state.value.copy(
                    isVisiblePassword = !_state.value.isVisiblePassword
                )
            }
            RegisterEvents.onClick(TypeClick.ToggledRePassword) -> {
                _state.value = _state.value.copy(
                    isVisibleRePassword = !_state.value.isVisibleRePassword
                )
            }
            RegisterEvents.onClick(TypeClick.DismissDialog) -> {
                _state.value = _state.value.copy(
                    registrationMessage = null
                )
            }
            RegisterEvents.onClick(TypeClick.Register) -> {
                _state.value = _state.value.copy(
                    isLoading = true
                )
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
                emailError = RegisterState.EmailError.FieldEmpty,
                isLoading = false
            )
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.value = _state.value.copy(
                emailError = RegisterState.EmailError.InvalidEmail,
                isLoading = false
            )
            return
        }
        _state.value = _state.value.copy(emailError = null)
    }

    private fun validateReEmail(reEmail:String) {
        val trimmedEmail = reEmail.trim()

        if(reEmail != _state.value.textEmail){
            _state.value = _state.value.copy(
                reEmailError = RegisterState.EmailError.DiffEmail,
                isLoading = false
            )
            return
        }

        if(trimmedEmail.isBlank()){
            _state.value = _state.value.copy(
                reEmailError = RegisterState.EmailError.FieldEmpty,
                isLoading = false
            )
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(reEmail).matches()) {
            _state.value = _state.value.copy(
                reEmailError = RegisterState.EmailError.InvalidEmail,
                isLoading = false
            )
            return
        }
        _state.value = _state.value.copy(reEmailError = null)
    }

    private fun validatePassword(password: String) {
        if(password.isBlank()) {
            _state.value = _state.value.copy(
                passwordError = RegisterState.PasswordError.FieldEmpty,
                isLoading = false
            )
            return
        }
        if(password.length < Constants.MIN_PASSWORD_LENGTH) {
            _state.value = _state.value.copy(
                passwordError = RegisterState.PasswordError.InputTooShort,
                isLoading = false
            )
            return
        }
        val capitalLettersInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }
        if(!capitalLettersInPassword || !numberInPassword) {
            _state.value = _state.value.copy(
                passwordError = RegisterState.PasswordError.InvalidPassword,
                isLoading = false
            )
            return
        }
        _state.value = _state.value.copy(passwordError = null)
    }

    private fun validateRePassword(rePassword: String) {
        if(rePassword.isBlank()) {
            _state.value = _state.value.copy(
                rePasswordError = RegisterState.PasswordError.FieldEmpty,
                isLoading = false
            )
            return
        }
        if(rePassword != _state.value.textPassword){
            _state.value = _state.value.copy(
                rePasswordError = RegisterState.PasswordError.DiffPassword,
                isLoading = false
            )
            return
        }
        if(rePassword.length < Constants.MIN_PASSWORD_LENGTH) {
            _state.value = _state.value.copy(
                rePasswordError = RegisterState.PasswordError.InputTooShort,
                isLoading = false
            )
            return
        }
        val capitalLettersInPassword = rePassword.any { it.isUpperCase() }
        val numberInPassword = rePassword.any { it.isDigit() }
        if(!capitalLettersInPassword || !numberInPassword) {
            _state.value = _state.value.copy(
                rePasswordError = RegisterState.PasswordError.InvalidPassword,
                isLoading = false
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
            val firebaseMessaging = FirebaseMessagingRepositoryImp()
            firebaseMessaging.getToken { token ->
                if (isSuccessRegistration) {
                    val uid = firebaseAuthRepository.getUserId()
                    val request = RequestUser(uid = uid, email = email, "2",token)
                    featRepository.createUser(request).onEach { result ->
                        when (result) {
                            is Result.Loading -> {
                                _state.value = _state.value.copy(
                                    isLoading = true
                                )
                            }
                            is Result.Success -> {
                                _state.value = _state.value.copy(
                                    registrationMessage = RegisterState.RegistrationMessage.RegistrationSuccess,
                                    isLoading = false
                                )
                            }
                            is Result.Error -> {
                                _state.value = _state.value.copy(
                                    registrationMessage = RegisterState.RegistrationMessage.RegistrationError,
                                    isLoading = false
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
                } else if (error is FirebaseAuthUserCollisionException) {
                    _state.value = _state.value.copy(
                        registrationMessage = RegisterState.RegistrationMessage.UserAlreadyExist,
                        isLoading = false
                    )
                }
            }
        }

    }
}