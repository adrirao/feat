package com.unlam.feat.ui.view.invitation

import com.unlam.feat.ui.util.TypeClick


sealed class InvitationEvent {
    object DismissDialog : InvitationEvent()
    data class onClick(val typeValue: TypeClick, val idEvent: Int? = null) : InvitationEvent()
}