package com.unlam.feat.model

import com.google.gson.annotations.SerializedName

data class Person(
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
    val userUid: User,
    val notifications: String,
    @SerializedName("willing_distance")
    val willingDistance: Int,
    @SerializedName("availability")
    val availabilities: List<Availability>
)
