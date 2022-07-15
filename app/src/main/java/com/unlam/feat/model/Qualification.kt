package com.unlam.feat.model

data class Qualification(
    var id: Int,
    var liked: Boolean = true,
    var observation: String = "",
    var qualifier: Int = 0
)