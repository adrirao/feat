package com.unlam.feat.ui.view.profile.personal_information

import com.unlam.feat.model.Person
import java.time.LocalDate

data class EditPersonalInformationState (
    val isLoading: Boolean = false,
    val error: String = "",
    val isUpdatedMessage: String? = "",
    val isSuccessSubmitData: Boolean = false,
    val isErrorSubmitData: Boolean = false,
    val sexList: List<String> = listOf("Hombre", "Mujer", "Otro"),

    val personId: String = "",
    val names: String = "",
    val lastname: String = "",
    val nickname: String = "",
    val birthDate: String = "",
    val sex: String= "",

    val lastnameError: GenericError? = null,
    val nameError: GenericError? = null,
    val nicknameError: GenericError? = null,
    val sexError: GenericError? = null,
    val birthDateError: DateError? = null,
){
    sealed class GenericError {
        object FieldEmpty : GenericError()
    }
    sealed class DateError{
        object DateInvalid : DateError()
        object IsNotOfLegalAge : DateError()
    }

}