package com.unlam.feat.ui.view.login

data class LoginState(
    val textEmail: String = "",
    val textPassword: String = "",
    val isLoading: Boolean = false,
    val messageError: String = "",
    val isVisiblePassword: Boolean = false,
)
