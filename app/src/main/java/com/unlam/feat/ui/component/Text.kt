package com.unlam.feat.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.ui.theme.PurpleLight
import com.unlam.feat.ui.theme.GreenColor
import com.unlam.feat.ui.theme.GreenLight

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
            modifier = Modifier
                .padding(vertical = 10.dp)
                .shadow(8.dp),
            color = GreenColor,
        )
    if (verticalPadding) FeatSpacerSmall()
}

@Composable
fun FeatHeader(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = MaterialTheme.typography.h2.fontSize,
    color: Color = Color.White,
    maxLines: Int = Int.MAX_VALUE,
    icon: @Composable (() -> Unit)? = null,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = modifier,
                text = text,
                fontSize = fontSize,
                color = color,
                maxLines = maxLines,
                overflow = overflow
            )
            if (icon != null) {
                icon()
            }
        }
        Divider(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .shadow(8.dp),
            color = GreenColor,
        )
    }
}