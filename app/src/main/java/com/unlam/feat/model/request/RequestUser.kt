package com.unlam.feat.model.request

import com.google.gson.annotations.SerializedName

data class RequestUser(
    val uid: String,
    val email: String,
    @SerializedName("user_type")
    val userType: String
)
