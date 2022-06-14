package com.unlam.feat.ui.view.register

data class RegisterState(
    val textEmail: String = "",
    val textPassword: String = "",
    val textReEmail: String = "",
    val textRePassword: String = "",

    val isVisiblePassword: Boolean = false,
    val isVisibleRePassword: Boolean = false,
    val isLoading : Boolean = false,

    val emailError: EmailError? = null,
    val reEmailError: EmailError? = null,
    val passwordError: PasswordError? = null,
    val rePasswordError: PasswordError? = null,
    val registrationMessage: RegistrationMessage? = null,
) {
    sealed class EmailError {
        object FieldEmpty : EmailError()
        object InvalidEmail : EmailError()
        object DiffEmail: EmailError()
    }

    sealed class PasswordError {
        object FieldEmpty : PasswordError()
        object InvalidPassword : PasswordError()
        object InputTooShort : PasswordError()
        object DiffPassword : PasswordError()
    }

    sealed class RegistrationMessage {
        object RegistrationSuccess : RegistrationMessage()
        object UserAlreadyExist : RegistrationMessage()
        object RegistrationError : RegistrationMessage()
    }
}
