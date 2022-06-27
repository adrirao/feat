package com.unlam.feat.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.unlam.feat.R
import com.unlam.feat.model.Event
import com.unlam.feat.ui.theme.*

@Composable
fun FeatCard(
    modifier: Modifier = Modifier,
    width: Dp? = null,
    height: Dp? = width,
    colorCard: Color = PurpleDark,
    urlImage: String = "",
    @DrawableRes painter: Int? = null,
    padding: Dp = 15.dp,
    content: @Composable (BoxScope.() -> Unit)
) {
    Card(
        modifier = if (width != null && height != null) {
            modifier.height(height)
        } else {
            modifier
        },
        backgroundColor = colorCard,
        elevation = 3.dp,
        shape = RoundedCornerShape(10)
    ) {
        if (urlImage.isBlank() && painter != null) {
            Image(
                painter = painterResource(id = painter),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        } else if (urlImage.isNotBlank()) {
            AsyncImage(
                model = urlImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier.padding(padding)
        ) {
            content()
        }
    }
}

@Composable
fun FeatForm(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        backgroundColor = PurpleDark,
        elevation = 3.dp,
        shape = RoundedCornerShape(5),
        content = {
            Column(modifier = Modifier.padding(vertical = 40.dp)) {
                content()
            }
        }
    )
}

@Preview
@Composable
fun PreviewCard(

) {
    val event: Event
    FeatContent {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            FeatOutlinedButtonIcon(
                modifier = Modifier.align(Alignment.BottomEnd),
                icon = Icons.Outlined.PlayArrow,
                height = 50.dp,
                width = 50.dp,
                onClick = {},
                contentColor = PurpleMedium,
                backgroundColor = PurpleMedium20
            )
            FeatCard(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.tennis),
                        contentDescription = null,
                    )
                    FeatText(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Evento",
                        fontSize = MaterialTheme.typography.h4.fontSize,
                        color = GreenColor
                    )
                    FeatText(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = MaterialTheme.typography.body1.fontSize,
                        color = PurpleLight
                    )
                    FeatSpacerMedium()
                    FeatInfo(textInfo = "Dia", icon = Icons.Outlined.CalendarToday)
                    FeatInfo(textInfo = "Horario", icon = Icons.Outlined.Timer)
                    FeatInfo(textInfo = "Direccion", icon = Icons.Outlined.Directions)
                    FeatInfo(textInfo = "Organizador", icon = Icons.Outlined.Person)
                    FeatInfo(textInfo = "Perioridicidad", icon = Icons.Outlined.CalendarViewMonth)
                    FeatSpacerMedium()
                    FeatOutlinedButton(
                        modifier = Modifier.align(Alignment.End),
                        textColor = YellowColor,
                        contentColor = YellowColor,
                        backgroundColor = YellowColor20,
                        textContent = "Como llegar",
                        height = 45.dp,
                        onClick = {}
                    )
                }
            }
        }
    }
}