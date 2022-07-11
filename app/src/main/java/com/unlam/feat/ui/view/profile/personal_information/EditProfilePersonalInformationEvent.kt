package com.unlam.feat.ui.view.profile.personal_information

import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import java.time.LocalTime

sealed class EditProfilePersonalInformationEvent {
    data class onClick(val typeClick: TypeClick) : EditProfilePersonalInformationEvent()
    data class onValueChange(
        val typeValueChange: TypeValueChange,
        val value: String,
        val valueOpt: String? = null,
        val valueBooleanOpt: Boolean? = null,
        val valueLocalTimeOpt: LocalTime? = null,
    ): EditProfilePersonalInformationEvent()
}