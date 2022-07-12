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
    object OnValueChangeSundayIsChecked: TypeValueChange()
    object OnValueChangeStartTimeSunday : TypeValueChange()
    object OnValueChangeEndTimeSunday : TypeValueChange()
    object OnValueChangeMondayIsChecked: TypeValueChange()
    object OnValueChangeStartTimeMonday : TypeValueChange()
    object OnValueChangeEndTimeMonday : TypeValueChange()
    object OnValueChangeTuesdayIsChecked: TypeValueChange()
    object OnValueChangeStartTimeTuesday : TypeValueChange()
    object OnValueChangeEndTimeTuesday : TypeValueChange()
    object OnValueChangeWednesdayIsChecked: TypeValueChange()
    object OnValueChangeStartTimeWednesday : TypeValueChange()
    object OnValueChangeEndTimeWednesday: TypeValueChange()
    object OnValueChangeThursdayIsChecked: TypeValueChange()
    object OnValueChangeStartTimeThursday : TypeValueChange()
    object OnValueChangeEndTimeThursday : TypeValueChange()
    object OnValueChangeFridayIsChecked: TypeValueChange()
    object OnValueChangeStartTimeFriday : TypeValueChange()
    object OnValueChangeEndTimeFriday : TypeValueChange()
    object OnValueChangeSaturdayIsChecked: TypeValueChange()
    object OnValueChangeStartTimeSaturday : TypeValueChange()
    object OnValueChangeEndTimeSaturday : TypeValueChange()
    object OnValueChangeMinAge : TypeValueChange()
    object OnValueChangeMaxAge : TypeValueChange()
    object OnValueChangeWillingDistance : TypeValueChange()
    object OnValueChangeNotifications : TypeValueChange()
    object OnValueChangeSelectSport : TypeValueChange()

    object OnValueChangeIdSoccer : TypeValueChange()
    object OnValueChangePositionSoccer : TypeValueChange()
    object OnValueChangeLevelSoccer : TypeValueChange()
    object OnValueChangeValuationSoccer : TypeValueChange()
    object OnValueChangeAbilitiesSoccer : TypeValueChange()

    object OnValueChangeIdBasketball : TypeValueChange()
    object OnValueChangePositionBasketball : TypeValueChange()
    object OnValueChangeLevelBasketball : TypeValueChange()
    object OnValueChangeValuationBasketball : TypeValueChange()
    object OnValueChangeAbilitiesBasketball : TypeValueChange()

    object OnValueChangeIdTennis : TypeValueChange()
    object OnValueChangePositionTennis : TypeValueChange()
    object OnValueChangeLevelTennis : TypeValueChange()
    object OnValueChangeValuationTennis : TypeValueChange()
    object OnValueChangeAbilitiesTennis : TypeValueChange()

    object OnValueChangeIdPadel: TypeValueChange()
    object OnValueChangePositionPadel : TypeValueChange()
    object OnValueChangeLevelPadel : TypeValueChange()
    object OnValueChangeValuationPadel : TypeValueChange()
    object OnValueChangeAbilitiesPadel : TypeValueChange()

    object OnValueChangeIdRecreationalActivity : TypeValueChange()
    object OnValueChangePositionRecreationalActivity : TypeValueChange()
    object OnValueChangeLevelRecreationalActivity : TypeValueChange()
    object OnValueChangeValuationRecreationalActivity : TypeValueChange()
    object OnValueChangeAbilitiesRecreationalActivity : TypeValueChange()
    object OnValueChangePickedImage: TypeValueChange()
    object OnValueChangeDay: TypeValueChange()
    object OnValueChangeDistance: TypeValueChange()

    object OnValueChangeSportIsChecked: TypeValueChange()
    object OnValueChangeDayIsChecked: TypeValueChange()
    object OnValueChangeTimeIsChecked: TypeValueChange()
    object OnValueChangeDistanceIsChecked: TypeValueChange()

    object OnValueChangeIdSport : TypeValueChange()
    object OnValueChangePositionSport : TypeValueChange()
    object OnValueChangeLevelSport : TypeValueChange()
    object OnValueChangeValuationSport : TypeValueChange()
    object OnValueChangeAbilitiesSport : TypeValueChange()



}