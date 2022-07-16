package com.unlam.feat.model.response

import com.google.gson.annotations.SerializedName


data class ResponsePerson(
    val id: Int,
    val lastname: String,
    val names: String,
    @SerializedName("birth_date")
    val birthDate: String,
    val sex: String,
    @SerializedName("min_age")
    val minAge: Int,
    @SerializedName("max_age")
    val maxAge: Int,
    val nickname: String,
    val userUid: String,
    val notifications: String,
    @SerializedName("willing_distance")
    val willingDistance: Int,
    @SerializedName("calification")
    val qualification: String
)
