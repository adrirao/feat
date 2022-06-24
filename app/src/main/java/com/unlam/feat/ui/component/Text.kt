package com.unlam.feat.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.unlam.feat.ui.theme.PurpleLight
import com.unlam.feat.ui.theme.GreenColor

@Composable
fun FeatText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = MaterialTheme.typography.h2.fontSize,
    color: Color = Color.White,
    separator: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    verticalPadding: Boolean = false
) {
    if (verticalPadding) FeatSpacerSmall()
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
    if (separator)
        Divider(
            modifier = Modifier.padding(vertical = 10.dp).shadow(8.dp),
            color = GreenColor,
        )
    if (verticalPadding) FeatSpacerSmall()
}