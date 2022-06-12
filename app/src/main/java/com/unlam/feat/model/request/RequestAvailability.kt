package com.unlam.feat.model.request

import com.google.gson.annotations.SerializedName

data class RequestAvailability(
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String,
    val person: Int,
    val day: Int
)
