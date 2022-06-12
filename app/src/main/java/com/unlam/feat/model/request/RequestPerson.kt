package com.unlam.feat.model.request

import android.media.MediaSession2Service
import com.google.gson.annotations.SerializedName
import com.unlam.feat.model.User

data class RequestPerson(
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
    val notifications: Int,
    val willingDistance: Int
)