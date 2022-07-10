package com.unlam.feat.ui.view.search

import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.event.new_event.NewEventEvents
import java.time.LocalTime

sealed class SearchEvent {
    object DismissDialog: SearchEvent()
    object RefreshData: SearchEvent()
    data class onValueChange(
        val typeValueChange: TypeValueChange,
        val value: String,
        val valueOpt: String? = null,
        val valueLocalTimeOpt: LocalTime? = null,
        val valueBooleanOpt: Boolean? = null
    ): SearchEvent()
    data class OnClick (val typeClick: TypeClick) : SearchEvent()
}