package com.unlam.feat.model

import com.google.gson.annotations.SerializedName

data class Event(
    val id: Int,
    val name: String,
    val date: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String,
    val description: String,
    val latitude: String,
    val longitude: String,
    val created: String,
    val sport: Sport,
    val state: State,
    val periodicity: Periodicity,
    val organizer: Person
)
