package com.unlam.feat.ui.view.event.detail_event.suggestedPlayers

import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange

sealed class SuggestedPlayersEvent {
    object DismissDialog : SuggestedPlayersEvent()
    object ChangeDialog : SuggestedPlayersEvent()
    object RefreshData : SuggestedPlayersEvent()
    data class onValueChange(
        val typeValueChange: TypeValueChange,
        val value: String,
        val valueOpt: String? = null,
    ) : SuggestedPlayersEvent()

    object OnClick : SuggestedPlayersEvent() {
        data class Invite(val idPlayer: Int) : SuggestedPlayersEvent()
    }
}