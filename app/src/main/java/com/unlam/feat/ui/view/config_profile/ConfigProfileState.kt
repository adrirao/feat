package com.unlam.feat.ui.view.config_profile

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.unlam.feat.model.Level
import com.unlam.feat.model.Position
import com.unlam.feat.model.SportGeneric
import com.unlam.feat.model.Valuation
import java.time.LocalDate
import java.time.LocalTime


data class ConfigProfileState(

    val sportsList: List<SportGeneric> = emptyList(),
    val levelList: List<Level> = emptyList(),
    val valuationList: List<Valuation> = emptyList(),
    val positionList: List<Position> = emptyList(),
    //TODO Pasar a string
    val sexList: List<String> = listOf("Hombre", "Mujer", "Otro"),
    val sundayIsChecked: Boolean = false,
    val mondayIsChecked: Boolean = false,
    val tuesdayIsChecked: Boolean = false,
    val wednesdayIsChecked: Boolean = false,
    val thursdayIsChecked: Boolean = false,
    val fridayIsChecked: Boolean = false,
    val saturdayIsChecked: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingSubmitData: Boolean = false,
    val isSuccessSubmitData:Boolean = false,
    val isErrorSubmitData: Boolean = false,
    val takePhoto:Boolean = false,
    val image: String? = "",
    val formError: Boolean = false,

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

    val idSoccer:String? = null,
    val abilitiesSoccer:String = "",
    val positionIdSoccer:Int? = null,
    val levelIdSoccer:Int? = null,
    val valuationIdSoccer:Int? = null,

    val idBasketball:String? = null,
    val abilitiesBasketball:String = "",
    val positionIdBasketball:Int? = null,
    val levelIdBasketball:Int? = null,
    val valuationIdBasketball:Int? = null,

    val idPadel:String? = null,
    val abilitiesPadel:String = "",
    val positionIdPadel:Int? = null,
    val levelIdPadel:Int? = null,
    val valuationIdPadel:Int? = null,

    val idTennis:String? = null,
    val abilitiesTennis:String = "",
    val positionIdTennis:Int? = null,
    val levelIdTennis:Int? = null,
    val valuationIdTennis:Int? = null,

    val idRecreationalActivity:String? = null,
    val abilitiesRecreationalActivity:String = "",
    val positionIdRecreationalActivity:Int? = null,
    val levelIdRecreationalActivity:Int? = null,
    val valuationIdRecreationalActivity:Int? = null,



    val error: String = "",
    val lastNameError: GenericError? = null,
    val nameError: GenericError? = null,
    val dateOfBirthError: DateError? = null,
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

    val abilitiesSoccerError:GenericError? = null,
    val positionIdSoccerError:GenericError? = null,
    val levelIdSoccerError:GenericError? = null,
    val valuationIdSoccerError:GenericError? = null,

    val positionIdBasketballError:GenericError? = null,
    val levelIdBasketballError:GenericError? = null,
    val valuationIdBasketballError:GenericError? = null,
    val abilitiesBasketballError:GenericError? = null,

    val positionIdPadelError:GenericError? = null,
    val levelIdPadelError:GenericError? = null,
    val valuationIdPadelError:GenericError? = null,
    val abilitiesPadelError:GenericError? = null,

    val positionIdTennisError:GenericError? = null,
    val levelIdTennisError:GenericError? = null,
    val valuationIdTennisError:GenericError? = null,
    val abilitiesTennisError:GenericError? = null,

    val positionIdRecreationalActivityError:GenericError? = null,
    val levelIdRecreationalActivityError:GenericError? = null,
    val valuationIdRecreationalActivityError:GenericError? = null,
    val abilitiesRecreationalActivityError:GenericError? = null,



    ) {
    sealed class GenericError {
        object FieldEmpty : GenericError()
    }
    sealed class DateError{
        object FieldEmpty : DateError()
        object IsNotOfLegalAge : DateError()
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