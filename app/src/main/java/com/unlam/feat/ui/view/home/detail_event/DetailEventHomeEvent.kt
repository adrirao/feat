package com.unlam.feat.ui.view.home.detail_event

sealed class DetailEventHomeEvent {
    object DismissDialog : DetailEventHomeEvent()
    object OnClick: DetailEventHomeEvent()
}