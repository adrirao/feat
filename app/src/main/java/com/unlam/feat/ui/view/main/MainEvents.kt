package com.unlam.feat.ui.view.main

import com.unlam.feat.ui.util.TypeClick

sealed class MainEvents{
    data class onClick(val typeClick: TypeClick) : MainEvents()
}
