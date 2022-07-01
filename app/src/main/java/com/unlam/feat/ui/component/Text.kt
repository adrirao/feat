package com.unlam.feat.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
    fontWeight : FontWeight? = null,
    fontFamily: FontFamily? = null,
    fontStyle: FontStyle? = null,
    verticalPadding: Boolean = false
) {
    if (verticalPadding) FeatSpacerSmall()
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        fontStyle = fontStyle
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
                overflow = overflow,
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

@Composable
fun FeatInfo(
    modifier: Modifier = Modifier,
    textInfo: String,
    colorText: Color? = null,
    fontSize: TextUnit? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    contentArrangement: Arrangement.Horizontal =  Arrangement.Start,
    contentAlignment : Alignment.Vertical =  Alignment.CenterVertically,
    @DrawableRes painter: Int? = null,
    icon: ImageVector? = null,
    iconColor: Color = GreenColor,
    iconSize: Dp = 25.dp
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp),
        horizontalArrangement = contentArrangement,
        verticalAlignment = contentAlignment
    ) {
        if(painter != null){
            Image(
                painter = painterResource(id = painter),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(iconSize)
                    .weight(1f)
            )
        }else if(icon != null){
            Icon(
                imageVector = icon ,
                contentDescription = null,
                modifier = Modifier
                    .size(iconSize)
                    .align(contentAlignment),
                tint = iconColor
            )
        }
        FeatText(
            modifier = Modifier.weight(4f).padding(horizontal = 10.dp),
            text = textInfo,
            color = colorText ?: PurpleLight,
            fontSize = fontSize ?: MaterialTheme.typography.body1.fontSize,
            maxLines = maxLines,
            overflow = overflow
        )
    }
}
