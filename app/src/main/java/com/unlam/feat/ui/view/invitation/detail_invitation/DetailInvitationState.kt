package com.unlam.feat.ui.view.invitation.detail_invitation

import com.unlam.feat.model.Event
import com.unlam.feat.model.Player

data class DetailInvitationState(
    val event: Event? = null,
    val players: List<Player>? = null,
    val error: String = "",
    val loading: Boolean = false,
    val playersConfirmed: List<Player>? = null,
    val success: Boolean = false,
    val successTitle: String = "",
    val successDescription: String = "",
    val idPlayer: String? = null,
    val completeCapacity: Boolean = false,
    val completeTitle: String = "",
    val completeDescription: String = ""
)