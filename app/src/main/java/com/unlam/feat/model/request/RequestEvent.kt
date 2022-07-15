package com.unlam.feat.model.request

import com.google.gson.annotations.SerializedName
import com.unlam.feat.model.Periodicity
import com.unlam.feat.model.Player
import com.unlam.feat.model.Sport
import com.unlam.feat.model.State

data class RequestEvent(
    val name: String ,
    val date: String ,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String,
    val description: String ,
    val latitude:String ,
    val longitude:String ,
    val sport: String,
    val state: String,
    val periodicity: String ,
    val organizer: Int,
    val capacity : String? = null
)
