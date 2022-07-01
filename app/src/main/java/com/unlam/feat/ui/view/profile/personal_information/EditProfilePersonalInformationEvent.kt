package com.unlam.feat.ui.view.profile.personal_information

import java.time.LocalDate

sealed class EditProfilePersonalInformationEvent {
    data class EnteredNames(val value : String) : EditProfilePersonalInformationEvent()
    data class EnteredLastNames(val value: String) : EditProfilePersonalInformationEvent()
    data class EnteredBirthDate(val value: LocalDate) : EditProfilePersonalInformationEvent()
    data class EnteredSex(val value: String) : EditProfilePersonalInformationEvent()
    data class EnteredNickname(val value: String) : EditProfilePersonalInformationEvent()
}