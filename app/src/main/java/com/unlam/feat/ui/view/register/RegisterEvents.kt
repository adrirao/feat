package com.unlam.feat.ui.view.register

import com.unlam.feat.ui.util.TypeClick

sealed class RegisterEvents {
    data class onClick(val typeClick: TypeClick) : RegisterEvents()
    data class onValueChange(val typeValueChange: TypeValueChange, val value: String) :
        RegisterEvents()
}

sealed class TypeValueChange {
    object onValueChangeEmail : TypeValueChange()
    object onValueChangeReEmail : TypeValueChange()
    object onValueChangeRePassword : TypeValueChange()
    object onValueChangePassword : TypeValueChange()
}