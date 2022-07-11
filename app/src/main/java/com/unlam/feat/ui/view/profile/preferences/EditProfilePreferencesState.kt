package com.unlam.feat.ui.view.profile.preferences

import com.unlam.feat.model.Person


data class EditProfilePreferencesState (
    val minAge: String = "",
    val maxAge: String = "",
    val notifications: Boolean = false,
    val willingDistance: String = "1",
    val isSuccessSubmitData: Boolean = false,
    val isErrorSubmitData: Boolean = false,

    val person: Person? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val isUpdatedMessage: String? = "",
    val ageError: RangeAgeError? = null,

    ){
    sealed class RangeAgeError {
        object IsInvalidRange : RangeAgeError()
        object MinAgeEmpty : RangeAgeError()
        object MaxAgeEmpty : RangeAgeError()
        object FieldEmpty : RangeAgeError()
    }
    sealed class GenericError {
        object FieldEmpty : GenericError()
    }
}