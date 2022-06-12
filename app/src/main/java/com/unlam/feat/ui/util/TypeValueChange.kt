package com.unlam.feat.ui.util

sealed class TypeValueChange {
    object onValueChangeEmail : TypeValueChange()
    object onValueChangeReEmail : TypeValueChange()
    object onValueChangeRePassword : TypeValueChange()
    object onValueChangePassword : TypeValueChange()
}