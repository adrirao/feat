package com.unlam.feat.ui.view.info_player

import com.unlam.feat.model.Person
import com.unlam.feat.model.Qualification

data class InfoPlayerState(
    val qualifications: List<Qualification>? = listOf(),
    val person: Person? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val image: String = "",
)
