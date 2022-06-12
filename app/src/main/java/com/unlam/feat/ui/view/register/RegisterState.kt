package com.unlam.feat.ui.view.register

data class RegisterState(
    var textEmail : String = "",
    val textPassword : String = "",
    var textVerifyEmail:String = "",
    val textVerifyPassword:String = "",
    val isVisiblePassword : Boolean = false,
    val isVisibleRePassword : Boolean = false,
)
