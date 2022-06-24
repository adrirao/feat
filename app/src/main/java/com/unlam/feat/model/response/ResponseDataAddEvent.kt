package com.unlam.feat.model.response

import com.unlam.feat.model.*

data class ResponseDataAddEvent (
    val person: Person? = null,
    val periodicityList: List<Periodicity>,
    val sportList: List<Sport>,
    val sportGenericList: List<SportGeneric>
)