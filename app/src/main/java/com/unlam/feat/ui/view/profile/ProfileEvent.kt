package com.unlam.feat.ui.view.profile

import android.graphics.Bitmap

sealed class ProfileEvent {
    object DismissDialog : ProfileEvent()
    object SingOutUser : ProfileEvent()
    data class NavigateTo(val typeNavigate : TypeNavigate): ProfileEvent(){
        sealed class TypeNavigate{
            object NavigateToAddress : TypeNavigate()
            object NavigateToPersonalInfo : TypeNavigate()
            data class NavigateToPlayerInformation(val playerJson : String) : TypeNavigate()
            object NavigateToPreferences : TypeNavigate()
            object NavigateToLogin: TypeNavigate()

        }
    }
    data class UploadImage(val image: Bitmap) : ProfileEvent()
}