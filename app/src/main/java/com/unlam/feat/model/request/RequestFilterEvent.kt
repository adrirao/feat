package com.unlam.feat.model.request

import java.time.LocalTime

data class RequestFilterEvent (
    val sportGenericId: Int?,
    val sportId: Int?,
    val dayId: Int?,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    val distance: Int?
    )