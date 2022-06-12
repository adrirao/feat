package com.unlam.feat.ui.util

sealed class TypeClick {
    object goToRegister : TypeClick()
    object goToLogin : TypeClick()
    object goToHome : TypeClick()
    object toggledPassword : TypeClick()
    object toggledRePassword : TypeClick()
}
