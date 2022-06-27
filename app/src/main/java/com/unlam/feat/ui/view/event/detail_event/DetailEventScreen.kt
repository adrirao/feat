package com.unlam.feat.ui.view.event.detail_event

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.maps.model.LatLng
import com.unlam.feat.R
import com.unlam.feat.model.Player
import com.unlam.feat.presentation.view.events.detail_event.DetailEventState
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
fun DetailEventMyEventScreen(
    state: DetailEventState,
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
            count = 3,
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
                2 -> PageTree(state)
            }
        }


        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            pagerState = pagerState
        )

        if (pagerState.currentPage != 0) {
            FeatOutlinedButtonIcon(
                modifier = Modifier.align(Alignment.BottomEnd),
                icon = Icons.Outlined.PersonAdd, onClick = {},
                height = 70.dp,
                width = 70.dp,
                contentColor = GreenColor,
                backgroundColor = GreenColor20,
                textColor = GreenColor,
            )
        }

    }

}

@Composable
fun PageOne(
    state: DetailEventState,
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
                            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            FeatOutlinedButton(
                                textContent = "Cancelar",
                                onClick = {},
                                contentColor = RedColor,
                                backgroundColor = RedColor20,
                                textColor = RedColor,
                                height = 46.dp
                            )
                            FeatOutlinedButton(
                                textContent = "Confirmar",
                                onClick = {},
                                contentColor = GreenColor,
                                backgroundColor = GreenColor20,
                                textColor = GreenColor,
                                height = 46.dp
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PageTwo(
    state: DetailEventState
) {
    val players = state.playersConfirmed!!
    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

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
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                FeatOutlinedButton(
                                    textContent = "Expulsar",
                                    height = 40.dp,
                                    onClick = {},
                                    contentColor = RedColor,
                                    backgroundColor = RedColor20,
                                    textColor = RedColor
                                )
                            }
                        }

                    }
                }
            )
        }
    }
}

@Composable
fun PageTree(
    state: DetailEventState
) {
    val players = state.playersApplied!!
    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            FeatText(
                text = "Postulados a participar en el evento:",
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
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                FeatOutlinedButton(
                                    textContent = "Rechazar",
                                    height = 40.dp,
                                    onClick = {},
                                    contentColor = RedColor,
                                    backgroundColor = RedColor20,
                                    textColor = RedColor
                                )
                                FeatOutlinedButton(
                                    textContent = "Aceptar",
                                    height = 40.dp,
                                    onClick = {},
                                    contentColor = GreenColor,
                                    backgroundColor = GreenColor20,
                                    textColor = GreenColor
                                )
                            }
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