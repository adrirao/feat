package com.unlam.feat.ui.view.search

import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.event.new_event.NewEventEvents
import java.time.LocalTime

sealed class SearchEvent {
    object DismissDialog: SearchEvent()
    object ChangeDialog: SearchEvent()
    data class onValueChange(
        val typeValueChange: TypeValueChange,
        val value: String,
        val valueOpt: String? = null,
        val valueLocalTimeOpt: LocalTime? = null
    ): SearchEvent()
    data class onClick (val typeClick: TypeClick) : SearchEvent()
}