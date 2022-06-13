package com.unlam.feat.ui.view.home

import com.unlam.feat.ui.util.TypeClick

sealed class HomeEvents {
    data class onClick(val typeClick: TypeClick) : HomeEvents()
}