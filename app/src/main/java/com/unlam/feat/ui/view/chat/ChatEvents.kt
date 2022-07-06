package com.unlam.feat.ui.view.chat

sealed class ChatEvents {
    data class OnChangeValue(val value: String) : ChatEvents()
    data class OnClick(val typeClick: TypeClick) : ChatEvents() {
        sealed class TypeClick {
            data class OnClickSendMessage(val message: String) : TypeClick()
        }
    }
}
