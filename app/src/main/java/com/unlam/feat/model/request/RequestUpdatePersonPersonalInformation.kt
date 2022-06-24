package com.unlam.feat.model.request


import com.google.gson.annotations.SerializedName


data class RequestUpdatePersonPersonalInformation(
    val lastname: String,
    val names: String,
    @SerializedName("birth_date")
    val birthDate: String,
    val sex: String,
    val nickname: String?,
    val id:Int
)