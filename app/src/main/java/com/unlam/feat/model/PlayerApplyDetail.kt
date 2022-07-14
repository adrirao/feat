package com.unlam.feat.model

import com.google.gson.annotations.SerializedName
import com.unlam.feat.model.*

data class PlayerApplyDetail(
    @SerializedName("player_id")
    val id: Int,
    @SerializedName("player_abilities")
    val abilities: String,
    @SerializedName("player_calification")
    val qualification: String,
    val lastname: String,
    val names: String,
    val sex: String,
    val nickname: String,
    @SerializedName("position_desc")
    val position: String,
    @SerializedName("level_desc")
    val level: String,
    val origin: String,
    var uri: String = ""
    )