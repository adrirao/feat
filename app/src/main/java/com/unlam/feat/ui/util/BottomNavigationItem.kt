package com.unlam.feat.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.ui.theme.GreenColor
import com.unlam.feat.ui.theme.GreenLight
import com.unlam.feat.ui.theme.PurpleLight

@Composable
fun RowScope.BottomNavigationItem(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    contentDescription: String? = null,
    selected: Boolean = false,
    alertCount: Int? = null,
    selectedColor: Color = Color.Black,
    unselectedColor: Color = Color.Gray,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    if (alertCount != null && alertCount < 0) {
        throw IllegalArgumentException("Alert count can't be negative")
    }

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        BottomNavigationItem(
            selected = selected,
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            selectedContentColor = selectedColor,
            unselectedContentColor = unselectedColor,
            icon = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (icon != null) {
                        Icon(
                            imageVector = icon,
                            contentDescription = contentDescription,
                            modifier = Modifier
                                .clip(RoundedCornerShape(30))
                                .background(if (selected) GreenColor else Color.Transparent)
                                .padding(10.dp)
                                .align(Alignment.Center),
                        )
                    }
                    if (alertCount != null) {
                        val alertText = if (alertCount > 99) {
                            "99+"
                        } else {
                            alertCount.toString()
                        }
                        Text(
                            text = alertText,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .offset(12.dp)
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(Color.Red)
                        )
                    }
                }
            }
        )
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}