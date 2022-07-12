package com.unlam.feat.ui.view.profile.address

import com.unlam.feat.ui.util.TypeValueChange

sealed class EditProfileAddressEvent{
    data class EnteredAddressAlias(val value: String) : EditProfileAddressEvent()
    data class EnteredAddressStreet(val value: String) : EditProfileAddressEvent()
    data class ShowAlertPermission(val value: Boolean,val title:String,val description:String): EditProfileAddressEvent()
    object DismissDialog: EditProfileAddressEvent()
    object SubmitData: EditProfileAddressEvent()
    object SingOutUser: EditProfileAddressEvent()
    data class onValueChange(
        val typeValueChange: TypeValueChange,
        val value: String,
        val valueOpt: String? = null
    ) : EditProfileAddressEvent()
}