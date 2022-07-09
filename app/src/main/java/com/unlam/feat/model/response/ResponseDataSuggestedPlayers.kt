package com.unlam.feat.model.response

import com.unlam.feat.model.Person
import com.unlam.feat.model.Player

data class ResponseDataSuggestedPlayers (
    val players: List<Player>?,
    val person: Person?
        )