package com.unlam.feat.ui.view.profile.sport

import com.unlam.feat.ui.util.TypeValueChange


sealed class EditProfileSportEvent {
    object DismissDialog: EditProfileSportEvent()
    object SubmitData: EditProfileSportEvent()
    object SingOutUser: EditProfileSportEvent()
    data class onValueChange(
        val typeValueChange: TypeValueChange,
        val value: String,
        val valueOpt: String? = null
    ) : EditProfileSportEvent()
}