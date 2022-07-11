package com.unlam.feat.ui.view.profile.address

import com.unlam.feat.model.Person

data class EditProfileAddressState(
    val addressAlias: String = "",
    val address: String = "",
    val addressNumber: String = "",
    val addressTown: String = "",
    val addressZipCode: String = "",
    val addressLatitude: String = "",
    val addressLongitude: String = "",
    val personId: Int? = null,
    val person: Person? = null,
    val errorAlias: GenericError? = null,
    val errorAddress: GenericError? = null,
    val latitude: String = "",
    val longitude: String = "",
    val idPlayer: String = "",

    val personError: String? = "",
    val addressStreetError: AddressStreetError? = null,
    val addressNumberError: AddressNumberError? = null,
    val addressTownError: AddressTownError? = null,
    val addressZipCodeError: AddressZipCodeError? = null,
    val fieldEmpty: String = "",

    val isLoading: Boolean = false,
    val isSuccessSubmitData: Boolean = false,
    val isErrorSubmitData: Boolean = false,
    val error: String? = null,

    val showAlertPermission: Boolean = false,
    val titleAlert: String = "",
    val descriptionAlert: String = ""
) {
    //TODO refactorizar si solo se va a validar si el campo esta vacio o no.

    sealed class GenericError {
        object FieldEmpty : GenericError()
    }

    sealed class AddressStreetError {
        object FieldEmpty : AddressStreetError()
    }

    sealed class AddressNumberError {
        object FieldEmpty : AddressNumberError()
    }


    sealed class AddressTownError {
        object FieldEmpty : AddressTownError()
    }

    sealed class AddressZipCodeError {
        object FieldEmpty : AddressZipCodeError()
    }

}