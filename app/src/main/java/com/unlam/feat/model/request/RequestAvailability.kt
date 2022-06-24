package com.unlam.feat.model.request

import com.google.gson.annotations.SerializedName

data class RequestAvailability(
    @SerializedName("start_time1")
    val startTime1: String?,
    @SerializedName("end_time1")
    val endTime1: String?,
    @SerializedName("start_time2")
    val startTime2: String?,
    @SerializedName("end_time2")
    val endTime2: String?,
    @SerializedName("start_time3")
    val startTime3: String?,
    @SerializedName("end_time3")
    val endTime3: String?,
    @SerializedName("start_time4")
    val startTime4: String?,
    @SerializedName("end_time4")
    val endTime4: String?,
    @SerializedName("start_time5")
    val startTime5: String?,
    @SerializedName("end_time5")
    val endTime5: String?,
    @SerializedName("start_time6")
    val startTime6: String?,
    @SerializedName("end_time6")
    val endTime6: String?,
    @SerializedName("start_time7")
    val startTime7: String?,
    @SerializedName("end_time7")
    val endTime7: String?,

    val person: Int,

)
