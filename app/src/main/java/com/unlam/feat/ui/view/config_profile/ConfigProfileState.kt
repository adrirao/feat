package com.unlam.feat.ui.view.config_profile

import java.time.LocalDate
import java.time.LocalTime


data class ConfigProfileState(

    val sexList: List<String> = listOf("Hombre", "Mujer", "Otro"),
    val sundayIsChecked: Boolean = false,
    val mondayIsChecked: Boolean = false,
    val tuesdayIsChecked: Boolean = false,
    val wednesdayIsChecked: Boolean = false,
    val thursdayIsChecked: Boolean = false,
    val fridayIsChecked: Boolean = false,
    val saturdayIsChecked: Boolean = false,



    val lastName: String = "",
    val name: String = "",
    val dateOfBirth: LocalDate? = null,
    val nickname: String = "",
    val sex: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val address: String = "",
    val addressAlias: String = "",
    val startTimeSunday: LocalTime? = null,
    val endTimeSunday: LocalTime? = null,
    val startTimeMonday: LocalTime? = null,
    val endTimeMonday: LocalTime? = null,
    val startTimeTuesday: LocalTime? = null,
    val endTimeTuesday: LocalTime? = null,
    val startTimeWednesday: LocalTime? = null,
    val endTimeWednesday: LocalTime? = null,
    val startTimeThursday: LocalTime? = null,
    val endTimeThursday: LocalTime? = null,
    val startTimeFriday: LocalTime? = null,
    val endTimeFriday: LocalTime? = null,
    val startTimeSaturday: LocalTime? = null,
    val endTimeSaturday: LocalTime? = null,
    val minAge: String = "",
    val maxAge: String = "",
    val notifications: Boolean = false,
    val willingDistance: String = "1",



    val lastNameError: GenericError? = null,
    val nameError: GenericError? = null,
    val dateOfBirthError: GenericError? = null,
    val nicknameError: GenericError? = null,
    val sexError: GenericError? = null,
    val latitudeError: GenericError? = null,
    val longitudeError: GenericError? = null,
    val addressError: GenericError? = null,
    val addressAliasError: GenericError? = null,
    val sundayError: DayError? = null,
    val mondayError: DayError? = null,
    val tuesdayError: DayError? = null,
    val wednesdayError: DayError? = null,
    val thursdayError: DayError? = null,
    val fridayError: DayError? = null,
    val saturdayError: DayError? = null,
    val ageError: RangeAgeError? = null,
    val willingDistanceError: GenericError? = null,



    ) {
    sealed class GenericError {
        object FieldEmpty : GenericError()
    }
    sealed class DateError{
        object DateInvalid : GenericError()
        object IsNotOfLegalAge : GenericError()
    }

    sealed class DayError {
        object WrongTimeRange : DayError()
        object StarTimeEmpty : DayError()
        object EndTimeEmpty : DayError()
        object TimeEmpty : DayError()
    }

    sealed class RangeAgeError {
        object IsInvalidRange : RangeAgeError()
        object MinAgeEmpty : RangeAgeError()
        object MaxAgeEmpty : RangeAgeError()
        object FieldEmpty : RangeAgeError()
    }
}