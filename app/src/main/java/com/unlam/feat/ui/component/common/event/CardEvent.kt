package com.unlam.feat.ui.component.common.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Directions
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.unlam.feat.R
import com.unlam.feat.model.Event
import com.unlam.feat.ui.component.FeatCard
import com.unlam.feat.ui.component.FeatInfo
import com.unlam.feat.ui.component.FeatSpacerSmall
import com.unlam.feat.ui.component.FeatText
import com.unlam.feat.ui.theme.GreenColor
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.YellowColor
import com.unlam.feat.util.Constants
import com.unlam.feat.util.getAddress
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun FeatEventCard(
    modifier: Modifier = Modifier,
    event: Event,
    onClick: () -> Unit
) {
    val date = LocalDate.parse(event!!.date.substring(0, 10)).format(
        DateTimeFormatter.ofPattern("dd/MM/yyyy")
    )
    val day = "${event.startTime.substring(0, 5)} - ${event.endTime.substring(0, 5)}"
    val stateEvent = event.state.toString().trim().uppercase()

    FeatCard(
        modifier = modifier,
        content = {
            Column(
                modifier = Modifier.clickable { onClick() }
            ) {
                FeatText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = event.name.uppercase(),
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    color = GreenColor,
                    fontWeight = FontWeight.ExtraBold
                )
                Row(
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        Image(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            painter = painterResource(
                                id = when (event.sport.description) {
                                    Constants.Sports.SOCCER_5, Constants.Sports.SOCCER_6,
                                    Constants.Sports.SOCCER_7, Constants.Sports.SOCCER_9,
                                    Constants.Sports.SOCCER_11 -> R.drawable.soccer
                                    Constants.Sports.BASKETBALL -> R.drawable.basketball
                                    Constants.Sports.TENNIS_SINGLE,
                                    Constants.Sports.TENNIS_DOUBLES -> R.drawable.tennis
                                    Constants.Sports.PADDLE_SINGLE,
                                    Constants.Sports.PADDLE_DOUBLE -> R.drawable.padel
                                    Constants.Sports.RECREATIONAL_ACTIVITY -> R.drawable.recreational_activity
                                    else -> R.drawable.soccer
                                }
                            ),
                            contentDescription = null
                        )
                        Column(
                            modifier = Modifier
                                .weight(3f)
                                .fillMaxHeight()
                                .padding(start = 20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {

                            FeatInfo(
                                textInfo = date,
                                icon = Icons.Outlined.CalendarToday,
                            )

                            FeatInfo(
                                textInfo = day,
                                icon = Icons.Outlined.Timer
                            )

                            FeatInfo(
                                textInfo = getAddress(
                                    LatLng(
                                        event.latitude.toDouble(),
                                        event.longitude.toDouble()
                                    )
                                ).getAddressLine(0),
                                icon = Icons.Outlined.Directions,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            FeatSpacerSmall()
                            var infoState = ""
                            var color: Color = Color.Transparent
                            if (stateEvent.isNotEmpty()) {
                                if (stateEvent == stringResource(R.string.value_aplicated)) {
                                    infoState = "Pendiente aplicacion"
                                    color = YellowColor
                                } else if (stateEvent == "CONFIRMADO") {
                                    infoState = "Confirmado"
                                    color = GreenColor
                                }
                                if (infoState.isNotEmpty()) {
                                    Card(
                                        modifier= Modifier.align(Alignment.End),
                                        shape = RoundedCornerShape(30),
                                        backgroundColor = color,
                                        content = {
                                            Text(
                                                modifier = Modifier.padding(5.dp),
                                                text = infoState,
                                                color = PurpleDark
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    )
}