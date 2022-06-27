package com.unlam.feat.ui.util

sealed class TypeValueChange {
    object OnValueChangeEmail : TypeValueChange()
    object OnValueChangeReEmail : TypeValueChange()
    object OnValueChangeRePassword : TypeValueChange()
    object OnValueChangePassword : TypeValueChange()
    object OnValueChangeLastName : TypeValueChange()
    object OnValueChangeName : TypeValueChange()
    object OnValueChangeNickname : TypeValueChange()
    object OnValueChangeSex : TypeValueChange()
    object OnValueChangeDate : TypeValueChange()
    object OnValueChangeAddressAlias : TypeValueChange()
    object OnValueChangeStartTime : TypeValueChange()
    object OnValueChangeEndTime : TypeValueChange()
    object OnValueChangePeriodicity : TypeValueChange()
    object OnValueChangeAddress : TypeValueChange()
    object OnValueChangePosition : TypeValueChange()
    object OnValueChangeDescription : TypeValueChange()
    object OnValueChangeOrganizer : TypeValueChange()
    object OnValueChangeSportGeneric : TypeValueChange()
    object OnValueChangeTypeSport : TypeValueChange()
}