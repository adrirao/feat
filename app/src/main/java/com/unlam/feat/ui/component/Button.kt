package com.unlam.feat.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.ui.theme.LightTransparent
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.PurpleLight
import java.util.*

@Composable
fun FeatOutlinedButton(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = PurpleLight,
    border: BorderStroke = BorderStroke(2.dp, contentColor),
    height: Dp = 50.dp,
    width: Dp? = null,
    enabled: Boolean = true,
    textContent: String,
    textColor: Color = contentColor,
    textAlign: TextAlign = TextAlign.Center,
    textWeight: FontWeight = FontWeight.Bold,
    fontSize: TextUnit? = 10.sp,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = {
            onClick()
        },
        modifier = if (width == null) {
            modifier
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .height(height)
        } else {
            modifier
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .height(height)
                .width(width)
        },
        shape = shape,
        border = border,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = if (enabled) backgroundColor else LightTransparent,
            contentColor = contentColor,
        ),
        enabled = enabled
    ) {
        Text(
            text = textContent.uppercase(),
            color = textColor,
            textAlign = textAlign,
            fontWeight = textWeight,
            style = if (height >= 50.dp || fontSize != null) {
                MaterialTheme.typography.body1.copy(
                    color = textColor
                )
            } else TextStyle(fontSize = fontSize!!)

        )
    }
}


@Composable
fun FeatOutlinedButtonIcon(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = PurpleLight,
    border: BorderStroke = BorderStroke(2.dp, contentColor),
    height: Dp = 50.dp,
    width: Dp? = null,
    enabled: Boolean = true,
    icon: ImageVector,
    textContent: String = "",
    textColor: Color = contentColor,
    iconColor: Color = PurpleDark,
    textAlign: TextAlign = TextAlign.Center,
    textWeight: FontWeight = FontWeight.Bold,
    buttonElevation: ButtonElevation? = null,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = {
            onClick()
        },
        modifier = if (width == null) {
            modifier
//                .padding(horizontal = 20.dp, vertical = 5.dp)
//                .height(height)
        } else {
            modifier
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .height(height)
                .width(width)
        },
        shape = shape,
        border = border,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = if (enabled) backgroundColor else LightTransparent,
            contentColor = contentColor,
        ),
        enabled = enabled,
        elevation = buttonElevation
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = iconColor)
        if (textContent.isNotEmpty()) {
            Text(
                text = textContent.uppercase(),
                color = textColor,
                textAlign = textAlign,
                fontWeight = textWeight,
                style = if (height >= 50.dp) {
                    MaterialTheme.typography.h6.copy(
                        color = textColor
                    )
                } else TextStyle(fontSize = 10.sp)

            )
        }
    }
}