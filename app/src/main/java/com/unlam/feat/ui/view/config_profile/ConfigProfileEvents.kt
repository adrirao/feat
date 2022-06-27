package com.unlam.feat.ui.view.config_profile

import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange


sealed class ConfigProfileEvents {
    data class onClick(val typeClick: TypeClick) : ConfigProfileEvents()
    data class onValueChange(
        val typeValueChange: TypeValueChange,
        val value: String,
        val valueOpt: String? = null
    ) :
        ConfigProfileEvents()
}