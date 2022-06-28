package com.unlam.feat.ui.view.invitation.detail_invitation



sealed class DetailInvitationEvent {

    object DismissDialog : DetailInvitationEvent()
    object ConfirmInvitation : DetailInvitationEvent()
    object CancelInvitation : DetailInvitationEvent()
}