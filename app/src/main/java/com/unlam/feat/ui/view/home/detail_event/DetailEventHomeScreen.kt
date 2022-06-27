package com.unlam.feat.ui.view.home.detail_event

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.maps.model.LatLng
import com.unlam.feat.R
import com.unlam.feat.model.Player
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.util.howToGet
import com.unlam.feat.ui.view.home.component.FeatInfo
import com.unlam.feat.util.Constants
import com.unlam.feat.util.getAddress
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailEventHomeScreen(
    state: DetailEventHomeState,
) {

    val pagerState = rememberPagerState()
    var nextPage by remember { mutableStateOf(false) }

    if (nextPage) {
        LaunchedEffect(true) {
            pagerState.animateScrollToPage(1)
            nextPage = false
        }
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            count = 2,
            state = pagerState
        ) { position ->
            when (position) {
                0 -> PageOne(
                    state = state,
                    onClick = {
                        nextPage = true
                    }
                )
                1 -> PageTwo(state)
            }
        }


        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            pagerState = pagerState
        )
    }

}

@Composable
fun PageOne(
    state: DetailEventHomeState,
    onClick: () -> Unit
) {

    val event = state.event!!

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

        FeatOutlinedButtonIcon(
            modifier = Modifier.align(Alignment.BottomEnd),
            icon = Icons.Outlined.PlayArrow,
            height = 50.dp,
            width = 50.dp,
            onClick = { onClick() },
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
                    painter = painterResource(
                        id = when (event.sport.description) {
                            Constants.Sports.SOCCER_5, Constants.Sports.SOCCER_6,
                            Constants.Sports.SOCCER_7, Constants.Sports.SOCCER_9,
                            Constants.Sports.SOCCER_11 -> R.drawable.futbol_court
                            Constants.Sports.BASKETBALL -> R.drawable.basketball_court
                            Constants.Sports.TENNIS_SINGLE,
                            Constants.Sports.TENNIS_DOUBLES -> R.drawable.tennis_court
                            Constants.Sports.PADDLE_SINGLE,
                            Constants.Sports.PADDLE_DOUBLE -> R.drawable.padel_court
                            Constants.Sports.RECREATIONAL_ACTIVITY -> R.drawable.recreational_activity
                            else -> R.drawable.soccer
                        }
                    ),
                    contentDescription = null,
                )
                FeatSpacerSmall()
                FeatText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = event.name,
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
                FeatInfo(
                    textInfo = date,
                    icon = Icons.Outlined.CalendarToday
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
                    icon = Icons.Outlined.Directions
                )
                FeatInfo(
                    textInfo = "${event.organizer.names} ${event.organizer.lastname}",
                    icon = Icons.Outlined.Person
                )
                FeatInfo(
                    textInfo = event.periodicity.description,
                    icon = Icons.Outlined.CalendarViewMonth
                )
                FeatSpacerMedium()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    FeatOutlinedButton(
                        modifier = Modifier,
                        textColor = YellowColor,
                        contentColor = YellowColor,
                        backgroundColor = YellowColor20,
                        textContent = "Como llegar",
                        height = 45.dp,
                        onClick = {
                            howToGet(location, context)
                        }
                    )
                    FeatOutlinedButton(
                        modifier = Modifier,
                        textColor = GreenColor,
                        contentColor = GreenColor,
                        backgroundColor = GreenColor20,
                        textContent = "Participar",
                        height = 45.dp,
                        onClick = {
                            howToGet(location, context)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PageTwo(
    state: DetailEventHomeState
) {
    val players = state.players!!
    Box {
//        FeatOutlinedButtonIcon(
//            modifier = Modifier.align(Alignment.BottomEnd),
//            icon = Icons.Outlined.PersonAdd, onClick = {},
//            height = 70.dp,
//            width = 70.dp,
//            contentColor = GreenColor,
//            backgroundColor = GreenColor20,
//            textColor = GreenColor,
//        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
//            FeatText(
//                text = "Jugadores postulados para el evento:",
//                fontSize = MaterialTheme.typography.h6.fontSize,
//                separator = true,
//                verticalPadding = true
//            )
//
//            LazyColumn(
//                modifier = Modifier
//                    .padding(10.dp),
//                content = {
//                    items(players) { player ->
//                        PlayerCard(player = player) {
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.End
//                            ) {
//                                FeatOutlinedButton(
//                                    textContent = "Rechazar",
//                                    height = 40.dp,
//                                    onClick = {},
//                                    contentColor = ErrorColor,
//                                    backgroundColor = RedColor20,
//                                    textColor = ErrorColor
//                                )
//                                FeatOutlinedButton(
//                                    textContent = "Aceptar",
//                                    height = 40.dp,
//                                    onClick = {},
//                                    contentColor = GreenColor,
//                                    backgroundColor = GreenColor20,
//                                    textColor = GreenColor
//                                )
//
//                            }
//                        }
//                    }
//                }
//            )

            FeatText(
                text = "Participantes del evento:",
                fontSize = MaterialTheme.typography.h6.fontSize,
                separator = true,
                verticalPadding = true
            )

            LazyColumn(
                modifier = Modifier
                    .padding(10.dp),
                content = {
                    items(players) { player ->
                        PlayerCard(player = player) {
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.End
//                            ) {
//                                FeatOutlinedButton(
//                                    textContent = "Expulsar",
//                                    height = 40.dp,
//                                    onClick = {},
//                                    contentColor = ErrorColor,
//                                    backgroundColor = RedColor20,
//                                    textColor = ErrorColor
//                                )
//                            }
                        }

                    }
                }
            )
        }
    }
}

@Composable
fun PlayerCard(
    player: Player,
    content: @Composable ColumnScope.() -> Unit
) {
    FeatCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row {
            Icon(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                tint = PurpleLight
            )
            Column(
                modifier = Modifier.weight(3f)
            ) {
                FeatInfo(
                    textInfo = "Nombre: ${player.person.names} ${player.person.lastname}",
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
                FeatInfo(
                    textInfo = "Apodo: ${player.person.nickname} ${player.person.lastname}",
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
                FeatInfo(
                    textInfo = "Habilidad: ${player.abilities}",
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
                FeatInfo(
                    textInfo = "Posicion: ${player.position.description}",
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
                FeatInfo(
                    textInfo = "Nivel: ${player.level.description}",
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
                FeatSpacerSmall()
                content()
            }
        }
    }
}