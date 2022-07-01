package com.unlam.feat.ui.view.config_profile

import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import java.time.LocalTime


sealed class ConfigProfileEvents {
    data class onClick(val typeClick: TypeClick) : ConfigProfileEvents()
    data class onValueChange(
        val typeValueChange: TypeValueChange,
        val value: String,
        val valueOpt: String? = null,
        val valueBooleanOpt: Boolean? = null,
        val valueLocalTimeOpt: LocalTime? = null
    ) :
        ConfigProfileEvents()
}