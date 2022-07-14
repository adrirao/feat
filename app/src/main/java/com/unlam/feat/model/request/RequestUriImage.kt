package com.unlam.feat.model.request

import com.google.gson.annotations.SerializedName

data class RequestUriImage(
    val uId : String,
    @SerializedName("photo_url")
    val photoUrl : String
)
