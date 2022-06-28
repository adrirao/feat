package com.unlam.feat.ui.view.search

import com.unlam.feat.model.Event

data class SearchState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val error: String = ""
)