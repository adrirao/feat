package com.unlam.feat.ui.view.profile

import com.unlam.feat.model.Address
import com.unlam.feat.model.Availability
import com.unlam.feat.model.Person
import com.unlam.feat.model.Player


data class ProfileState (
    val isLoading: Boolean = false,
    val error: String = "",
    val isUpdatedMessage: String? = "",

    val person: Person? = null,
    val addresses: List<Address>? = emptyList(),
    val players: List<Player>? = emptyList(),
    val availabilities: List<Availability>? = emptyList(),

)