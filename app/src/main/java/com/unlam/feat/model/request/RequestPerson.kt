package com.unlam.feat.model.request


import com.google.gson.annotations.SerializedName


data class RequestPerson(
    val lastname: String,
    val names: String,
    @SerializedName("birth_date")
    val birthDate: String,
    val sex: String,
    @SerializedName("min_age")
    val minAge: Int? = null,
    @SerializedName("max_age")
    val maxAge: Int?= null,
    val nickname: String?,
    val userUid: String,
    val notifications: Int?= null,
    @SerializedName("willing_distance")
    val willingDistance: Int?= null
)