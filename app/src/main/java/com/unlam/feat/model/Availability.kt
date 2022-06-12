package com.unlam.feat.model

import com.google.gson.annotations.SerializedName

data class Availability(
    val id: Int,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String,
    val person: Person,
    val day: Day
)
