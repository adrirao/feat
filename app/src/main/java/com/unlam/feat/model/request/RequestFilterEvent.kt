package com.unlam.feat.model.request

import java.time.LocalTime

data class RequestFilterEvent (
    val uid: String,
    val sportGenericId: Int?,
    val dayId: Int?,
    val startTime: String?,
    val endTime: String?,
    val distance: Int?
    )