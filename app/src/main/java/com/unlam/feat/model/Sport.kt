package com.unlam.feat.model

data class Sport(
    val id: Int,
    val description: String,
    val capacity: Int?,
    val substitute: Int?,
    val sportGeneric:SportGeneric
)