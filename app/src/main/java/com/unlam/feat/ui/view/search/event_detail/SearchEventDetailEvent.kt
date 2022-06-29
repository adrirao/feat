package com.unlam.feat.ui.view.search.event_detail


sealed class SearchEventDetailEvent {
    object DismissDialog: SearchEventDetailEvent()

    object ApplyEvent: SearchEventDetailEvent()
}