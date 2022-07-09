package com.unlam.feat.model.response

import com.unlam.feat.model.Person
import com.unlam.feat.model.Qualification

data class ResponseInfoPlayer(
    val qualifications: List<Qualification>? = null,
    val person: Person? = null
)
