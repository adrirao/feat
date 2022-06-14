package com.unlam.feat.ui.view.event.new_event

import com.unlam.feat.ui.util.TypeValueChange

sealed class NewEventEvents {
    data class onValueChange(
        val typeValueChange: TypeValueChange,
        val value: String,
        val valueOpt: String? = null
    ) :
        NewEventEvents()
}