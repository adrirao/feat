package com.unlam.feat.ui.util

sealed class TypeValueChange {
    object OnValueChangeEmail : TypeValueChange()
    object OnValueChangeReEmail : TypeValueChange()
    object OnValueChangeRePassword : TypeValueChange()
    object OnValueChangePassword : TypeValueChange()
    object OnValueChangeName : TypeValueChange()
    object OnValueChangeDate : TypeValueChange()
    object OnValueChangeStartTime : TypeValueChange()
    object OnValueChangeEndTime : TypeValueChange()
    object OnValueChangePerioridicity : TypeValueChange()
    object OnValueChangeAddress : TypeValueChange()
    object OnValueChangePosition : TypeValueChange()
    object OnValueChangeDescription : TypeValueChange()
    object OnValueChangeOrganizer : TypeValueChange()
}