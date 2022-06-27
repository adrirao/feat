package com.unlam.feat.ui.component.common.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CalendarViewMonth
import androidx.compose.material.icons.outlined.Directions
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.unlam.feat.R
import com.unlam.feat.model.Event
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.util.howToGet
import com.unlam.feat.util.getAddress
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DetailEvent(
    event: Event,
    content : @Composable ColumnScope.() -> Unit
) {

    val context = LocalContext.current
    val location = LatLng(event.latitude.toDouble(), event.longitude.toDouble())

    val date = LocalDate.parse(event.date.substring(0, 10)).format(
        DateTimeFormatter.ofPattern("dd/MM/yyyy")
    )
    val day = "${event.startTime.substring(0, 5)} - ${event.endTime.substring(0, 5)}"
    val stateEvent = event.state.toString().trim().uppercase()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        FeatCard(
            padding = 0.dp
        ) {
            Column {
                Box(
                    modifier = Modifier.height(200.dp),
                    content = {
                        Image(
                            painter = painterResource(id = R.drawable.road_map),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            BlackTransparent50,
                                            BlackTransparent90
                                        )
                                    )
                                )
                                .clickable {
                                    howToGet(location, context)
                                },
                            contentAlignment = Alignment.BottomStart
                        ) {
                            Column {
                                FeatText(
                                    modifier = Modifier.padding(10.dp),
                                    text = event.name,
                                    fontSize = MaterialTheme.typography.h6.fontSize,
                                    color = GreenColor,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Row(
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    FeatInfo(
                                        modifier = Modifier
                                            .weight(1f),
                                        textInfo = "$date $day",
                                        icon = Icons.Outlined.CalendarToday,
                                        colorText = Color.White,
                                        iconSize = 20.dp,
                                    )
                                }
                            }
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .padding(15.dp),
                    content = {
                        FeatSpacerSmall()

                        FeatText(
                            text = event.description,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            color = PurpleLight
                        )
                        FeatSpacerMedium()


                        FeatInfo(
                            textInfo = getAddress(
                                LatLng(
                                    event.latitude.toDouble(),
                                    event.longitude.toDouble()
                                )
                            ).getAddressLine(0),
                            icon = Icons.Outlined.Directions
                        )
                        FeatInfo(
                            textInfo = "${event.organizer.lastname}, ${event.organizer.names}",
                            icon = Icons.Outlined.Person
                        )
                        FeatInfo(
                            textInfo = event.periodicity.description,
                            icon = Icons.Outlined.CalendarViewMonth
                        )
                        FeatSpacerMedium()
                        content()
                    }
                )
            }
        }
    }
}