package com.unlam.feat.ui.view.event

import com.unlam.feat.ui.util.TypeClick


sealed class EventEvents {
    data class onClick(val typeClick: TypeClick, val value: Int? = null) : EventEvents()
    object DismissDialog: EventEvents()
}
