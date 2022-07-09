package com.unlam.feat.ui.view.event.detail_event

import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.view.profile.ProfileEvent


sealed class DetailEventEvent {
    object DismissDialog : DetailEventEvent()

    object CancelEvent : DetailEventEvent()
    object ConfirmEvent : DetailEventEvent()

//    object KickPlayer : DetailEventEvent()
    data class KickPlayer(val userId: Int) : DetailEventEvent()

//    object RejectPlayer : DetailEventEvent()
    data class RejectPlayer(val userId: Int) : DetailEventEvent()

    //    object AcceptPlayer: DetailEventEvent()
    data class AcceptPlayer(val userId: Int) : DetailEventEvent()

//    object InvitePlayer : DetailEventEvent()
    data class InvitePlayer(val userId: Int) : DetailEventEvent()


    data class NavigateTo(val typeNavigate : TypeNavigate): DetailEventEvent(){
        sealed class TypeNavigate{
            data class NavigateToSuggestedPlayers(val userId: Int?) : TypeNavigate()
        }
    }


}