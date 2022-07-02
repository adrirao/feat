package com.unlam.feat.model.response

import com.unlam.feat.model.Level
import com.unlam.feat.model.Person
import com.unlam.feat.model.Position
import com.unlam.feat.model.Valuation

data class ResponseDataSport (
val positionList: List<Position>  = emptyList(),
val levelList: List<Level> = emptyList(),
val valuationList: List<Valuation> = emptyList(),
)
