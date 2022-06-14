package com.unlam.feat.ui.util

sealed class TypeClick {
    object GoToRegister : TypeClick()
    object GoToLogin : TypeClick()
    object GoToHome : TypeClick()
    object GoToInfoEvent : TypeClick()
    object GoToNewEvent : TypeClick()

    object Login : TypeClick()
    object Register : TypeClick()

    object DismissDialog : TypeClick()

    object ToggledPassword : TypeClick()
    object ToggledRePassword : TypeClick()
}
