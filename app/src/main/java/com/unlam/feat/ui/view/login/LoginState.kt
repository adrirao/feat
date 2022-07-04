package com.unlam.feat.ui.view.login

import com.unlam.feat.ui.view.register.RegisterState

data class LoginState(
    val textEmail: String = "",
    val textPassword: String = "",
    val isLoading: Boolean = false,
    val messageError: String = "",
    val isVisiblePassword: Boolean = false,
    val emailError: EmailError? = null,
    val passwordError: PasswordError? = null,
    val loginMessage: LoginMessage? = null,
    val isFirstLogin: Boolean? = null,
){
    sealed class EmailError{
        object FieldEmpty : EmailError()
        object InvalidEmail : EmailError()
    }

    sealed class PasswordError {
        object FieldEmpty : PasswordError()
        object InvalidPassword : PasswordError()
        object InputTooShort : PasswordError()
    }

    sealed class LoginMessage {
        object UserNotExist : LoginMessage()
        object InvalidCredentials : LoginMessage()
        object VerifyEmail: LoginMessage()
        object LoginSuccess : LoginMessage()
        object ApiConnectionError : LoginMessage()
    }
}
