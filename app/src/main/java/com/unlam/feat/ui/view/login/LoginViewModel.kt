package com.unlam.feat.ui.view.login

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
) : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.onValueChange -> {
                when (event.typeValueChange) {
                    TypeValueChange.OnValueChangeEmail -> {
                        _state.value = _state.value.copy(
                            textEmail = event.value
                        )
                    }
                    TypeValueChange.OnValueChangePassword -> {
                        _state.value = _state.value.copy(
                            textPassword = event.value
                        )
                    }
                }
            }
            LoginEvents.onClick(TypeClick.ToggledPassword) -> {
                _state.value = _state.value.copy(
                    isVisiblePassword = !_state.value.isVisiblePassword
                )
            }
            LoginEvents.onClick(TypeClick.DismissDialog) -> {
                _state.value = _state.value.copy(
                    loginMessage = null
                )
            }
            LoginEvents.onClick(TypeClick.Login) -> {
                _state.value = _state.value.copy(
                    isLoading = true
                )
                validateEmail(_state.value.textEmail)
                validatePassword(_state.value.textPassword)
                loginUser()
            }
        }
    }

    private fun validateEmail(email: String) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank()) {
            _state.value = _state.value.copy(
                emailError = LoginState.EmailError.FieldEmpty,
                isLoading = false
            )
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.value = _state.value.copy(
                emailError = LoginState.EmailError.InvalidEmail,
                isLoading = false
            )
            return
        }
        _state.value = _state.value.copy(
            emailError = null,
            isLoading = false
        )
    }

    private fun validatePassword(password: String) {
        if (password.isBlank()) {
            _state.value = _state.value.copy(
                passwordError = LoginState.PasswordError.FieldEmpty
            )
            return
        }
        if (password.length < Constants.MIN_PASSWORD_LENGTH) {
            _state.value = _state.value.copy(
                passwordError = LoginState.PasswordError.InputTooShort,
                isLoading = false
            )
            return
        }
        val capitalLettersInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }
        if (!capitalLettersInPassword || !numberInPassword) {
            _state.value = _state.value.copy(
                passwordError = LoginState.PasswordError.InvalidPassword,
                isLoading = false
            )
            return
        }
        _state.value = _state.value.copy(
            passwordError = null,
            isLoading = false
        )
    }

    private fun loginUser() {
        var email = if (_state.value.emailError == null) _state.value.textEmail else return
        var password = if (_state.value.passwordError == null) _state.value.textPassword else return
        viewModelScope.launch {
            firebaseAuthRepository.authenticate(email, password) { isLogged, error ->
                if (isLogged) {
                    _state.value = _state.value.copy(
                        loginMessage = LoginState.LoginMessage.LoginSuccess,
                        isLoading = false
                    )
                } else {
                    when (error) {
                        is FirebaseAuthInvalidUserException -> {
                            _state.value = _state.value.copy(
                                loginMessage = LoginState.LoginMessage.UserNotExist,
                                isLoading = false
                            )
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            _state.value = _state.value.copy(
                                loginMessage = LoginState.LoginMessage.InvalidCredentials,
                                isLoading = false
                            )
                        }
                        else -> {
                            _state.value = _state.value.copy(
                                loginMessage = LoginState.LoginMessage.VerifyEmail,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}