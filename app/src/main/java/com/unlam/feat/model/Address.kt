package com.unlam.feat.model

import com.google.gson.annotations.SerializedName

data class Address(
    val id: Int,
    val alias: String,
    val street: String,
    val number: String,
    val town: String,
    @SerializedName("zip_code")
    val zipCode: String,
    val latitude: String,
    val longitude: String,
)
