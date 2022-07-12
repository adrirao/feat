package com.unlam.feat.ui.view.profile.sport

import com.unlam.feat.model.*

data class EditProfileSportState (
    val personId: Int? = null,
    val isLoading: Boolean = false,
    val isSuccessSubmitData: Boolean = false,
    val isErrorSubmitData: Boolean = false,
    val error: String? = null,
    val playersUser: List<Player>? = emptyList(),
    val sportsList: List<SportGeneric>? = emptyList(),
    val levelList: List<Level> = emptyList(),
    val valuationList: List<Valuation> = emptyList(),
    val positionList: List<Position> = emptyList(),

    )