package com.unlam.feat.ui.view.config_profile

import java.time.LocalDate


data class ConfigProfileState(



    val lastName: String = "",
    val name: String = "",
    val dateOfBirth: LocalDate? = null,
    val nickname: String = "",
    val sex: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val address: String = "",
    val addressAlias: String = "",

    val lastNameError: GenericError? = null,
    val nameError: GenericError? = null,
    val dateOfBirthError: GenericError? = null,
    val nicknameError: GenericError? = null,
    val sexError: GenericError? = null,
    val latitudeError: GenericError? = null,
    val longitudeError: GenericError? = null,
    val addressError: GenericError? = null,
    val addressAliasError: GenericError? = null,


    val sexList: List<String> = listOf("Hombre", "Mujer", "Otro")
) {
    sealed class GenericError {
        object FieldEmpty : GenericError()
    }
}