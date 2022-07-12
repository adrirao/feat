package com.unlam.feat.model.request

import java.time.LocalTime

data class RequestFilterEvent (
    val uid: String,
    val sportGenericId: String?,
    val dayId: String?,
    val startTime: String?,
    val endTime: String?,
    val distance: String?
    )