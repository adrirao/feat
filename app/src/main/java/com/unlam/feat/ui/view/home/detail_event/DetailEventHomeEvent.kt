package com.unlam.feat.ui.view.home.detail_event

import com.unlam.feat.ui.view.search.event_detail.SearchEventDetailEvent

sealed class DetailEventHomeEvent {
    object ApplyEvent: DetailEventHomeEvent()
    object DismissDialog : DetailEventHomeEvent()
    object OnClick: DetailEventHomeEvent()
    data class OnClickCardPlayer(val idPlayer: Int) : DetailEventHomeEvent()
}