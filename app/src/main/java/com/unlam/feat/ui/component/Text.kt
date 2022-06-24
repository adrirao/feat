package com.unlam.feat.ui.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import com.unlam.feat.ui.theme.PurpleLight

@Composable
fun FeatText(
    text : String,
    fontSize: TextUnit = MaterialTheme.typography.h2.fontSize,
    color : Color = PurpleLight
){
    Text(
        text = text,
        fontSize = fontSize,
        color = color
    )
}