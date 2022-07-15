package com.unlam.feat.ui.view.profile.sport

import com.unlam.feat.model.*

data class EditProfileSportState (
    val personId: Int? = null,
    val isLoading: Boolean = false,
    val isSuccessSubmitData: Boolean = false,
    val isErrorSubmitData: Boolean = false,
    val error: String? = null,
    val levelList: List<Level> = emptyList(),
    val valuationList: List<Valuation> = emptyList(),
    val positionList: List<Position> = emptyList(),


    val playersUser: List<Player>? = emptyList(),
    val sportsList: List<SportGeneric>? = emptyList(),

    val sportId: String = "",
    val abilitiesSport:String = "",
    val positionIdSport:String? = null,
    val levelIdSport:String? = null,
    val valuationIdSport:String? = null,

    val sportIdError: GenericError? = null,
    val abilitiesSportError:GenericError? = null,
    val positionIdSportError:GenericError? = null,
    val levelIdSportError:GenericError? = null,
    val valuationIdSportError:GenericError? = null,

    ){
    sealed class GenericError {
        object FieldEmpty : GenericError()
    }
}