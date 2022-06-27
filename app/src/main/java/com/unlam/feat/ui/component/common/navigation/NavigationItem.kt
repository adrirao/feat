package com.unlam.feat.ui.component.common.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val route: String,
    val icon: ImageVector? = null,
    val contentDescription: String? = null,
    val alertCount: Int? = null
)