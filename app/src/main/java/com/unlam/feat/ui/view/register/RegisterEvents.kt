package com.unlam.feat.ui.view.register

import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange

sealed class RegisterEvents {
    data class onClick(val typeClick: TypeClick) : RegisterEvents()
    data class onValueChange(val typeValueChange: TypeValueChange, val value: String) :
        RegisterEvents()
}

