package com.unlam.feat.ui.view.event.detail_event

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.unlam.feat.presentation.view.events.detail_event.DetailEventState
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.component.common.event.DetailEvent
import com.unlam.feat.ui.component.common.event.NotFoundEvent
import com.unlam.feat.ui.component.common.player.CardPlayer
import com.unlam.feat.ui.theme.*

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
                icon = Icons.Outlined.PersonAdd, onClick = { nextPage = true },
                height = 70.dp,
                width = 70.dp,
                contentColor = GreenColor,
                backgroundColor = GreenColor20,
                textColor = GreenColor,
            )
        }else{
            FeatOutlinedButtonIcon(
                modifier = Modifier.align(Alignment.BottomEnd),
                icon = Icons.Outlined.PlayArrow,
                height = 50.dp,
                width = 50.dp,
                onClick = { nextPage = true },
                contentColor = PurpleMedium,
                backgroundColor = PurpleMedium20
            )
        }

    }

}

@Composable
fun PageOne(
    state: DetailEventState,
) {
    val event = state.event!!
    DetailEvent(
        event = event,
        content = {
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

            if (players.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp),
                    content = {
                        items(players) { player ->
                            CardPlayer(player = player) {
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
            } else {
                NotFoundEvent()
            }

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

            if (players.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp),
                    content = {
                        items(players) { player ->
                            CardPlayer(player = player) {
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
            } else {
                NotFoundEvent()
            }
        }
    }
}