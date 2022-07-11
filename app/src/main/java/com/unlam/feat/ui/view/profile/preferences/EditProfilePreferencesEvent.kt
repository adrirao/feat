package com.unlam.feat.ui.view.profile.preferences

import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import java.time.LocalTime

sealed class EditProfilePreferencesEvent {
    data class onClick(val typeClick: TypeClick) : EditProfilePreferencesEvent()
    data class onValueChange(
        val typeValueChange: TypeValueChange,
        val value: String,
        val valueOpt: String? = null,
        val valueBooleanOpt: Boolean? = null,
        val valueLocalTimeOpt: LocalTime? = null,
    ): EditProfilePreferencesEvent()
}