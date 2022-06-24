package com.unlam.feat.model.request


import com.google.gson.annotations.SerializedName


data class RequestUpdatePerson(
    @SerializedName("min_age")
    val minAge: Int,
    @SerializedName("max_age")
    val maxAge: Int,
    val notifications: Int,
    @SerializedName("willing_distance")
    val willingDistance: Int,
    val id:Int
)