package com.unlam.feat.model.player_detail

import com.unlam.feat.model.*

data class PlayerDetail(
    val id: Int,
    val abilities: String,
    val created:String,
    val sport: SportPlayerDetail,
    val position: PositionPlayerDetail,
    val level : LevelPLayerDetail,
    val valuation : Valuation,
)