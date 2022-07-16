package com.unlam.feat.model

import com.google.gson.annotations.SerializedName

data class HomeEvent(
    val id: Int,
    val name: String,
    val date: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String,
    val latitude: String,
    val longitude: String,
    @SerializedName("state_desc")
    val stateDesc: String,
    @SerializedName("sport_desc")
    val sportDesc: String,
    val origen: String,
    @SerializedName("is_organizer")
    val isOrganizer: Int,
    val eventQualified : Int
)
