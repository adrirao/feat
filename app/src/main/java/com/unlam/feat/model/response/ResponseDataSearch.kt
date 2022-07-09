package com.unlam.feat.model.response

import com.unlam.feat.model.Event
import com.unlam.feat.model.Person
import com.unlam.feat.model.Player

data class ResponseDataSearch (
    val events: List<Event>,
    val players: List<Player>,
    val person: Person
    )