package com.unlam.feat.ui.view.search

sealed class SearchEvent {
    object DismissDialog: SearchEvent()
}