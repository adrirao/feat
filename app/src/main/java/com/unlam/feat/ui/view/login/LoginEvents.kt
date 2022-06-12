package com.unlam.feat.ui.view.login

import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange

sealed class LoginEvents {
    data class onClick(val typeClick: TypeClick) : LoginEvents()
    data class onValueChange(val typeValueChange: TypeValueChange, val value: String) :
        LoginEvents()
}