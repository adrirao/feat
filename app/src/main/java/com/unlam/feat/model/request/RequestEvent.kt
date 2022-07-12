package com.unlam.feat.model.request

import com.google.gson.annotations.SerializedName
import com.unlam.feat.model.Periodicity
import com.unlam.feat.model.Player
import com.unlam.feat.model.Sport
import com.unlam.feat.model.State

data class RequestEvent(
    val name: String = "test",
    val date: String = "2022-06-15",
    @SerializedName("start_time")
    val startTime: String = "20",
    @SerializedName("end_time")
    val endTime: String = "21",
    val description: String = "test",
    val latitude:String = "-32.2222",
    val longitude:String = "-52.3334",
    val sport: String = "1",
    val state: String = "1",
    val periodicity: String = "1",
    val organizer: Int,
    val capacity : String? = null
)
