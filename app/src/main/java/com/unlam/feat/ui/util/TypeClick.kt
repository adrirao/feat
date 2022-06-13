package com.unlam.feat.ui.util

sealed class TypeClick {
    object goToRegister : TypeClick()
    object goToLogin : TypeClick()
    object goToHome : TypeClick()
    object goToInfoEvent : TypeClick()

    object register : TypeClick()

    object dismissDialog : TypeClick()

    object toggledPassword : TypeClick()
    object toggledRePassword : TypeClick()
}
