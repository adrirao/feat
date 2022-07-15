package com.unlam.feat.ui.view.event.detail_event

import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.view.profile.ProfileEvent


sealed class DetailEventEvent {
    object DismissDialog : DetailEventEvent()

    object CancelEvent : DetailEventEvent()
    object ConfirmEvent : DetailEventEvent()
    object FinalizeEvent: DetailEventEvent()

//    object KickPlayer : DetailEventEvent()
    data class KickPlayer(val userId: String) : DetailEventEvent()

//    object RejectPlayer : DetailEventEvent()
    data class RejectPlayer(val userId: String) : DetailEventEvent()

    //    object AcceptPlayer: DetailEventEvent()
    data class AcceptPlayer(val userId: String) : DetailEventEvent()

//    object InvitePlayer : DetailEventEvent()
    data class InvitePlayer(val userId: String) : DetailEventEvent()


    data class NavigateTo(val typeNavigate : TypeNavigate): DetailEventEvent(){
        sealed class TypeNavigate{
            data class NavigateToSuggestedPlayers(val userId: String?) : TypeNavigate()
        }
    }


}