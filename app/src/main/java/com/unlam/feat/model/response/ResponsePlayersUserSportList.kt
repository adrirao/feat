package com.unlam.feat.model.response

import com.unlam.feat.model.Person
import com.unlam.feat.model.Player
import com.unlam.feat.model.SportGeneric

data class ResponsePlayersUserSportList(
    val players : List<Player>,
    val sportGenericList: List<SportGeneric>,
    val person : Person
)