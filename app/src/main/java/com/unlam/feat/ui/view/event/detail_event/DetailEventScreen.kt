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
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.component.common.event.DetailEvent
import com.unlam.feat.ui.component.common.event.FeatEventDetail
import com.unlam.feat.ui.component.common.event.NotFoundEvent
import com.unlam.feat.ui.component.common.event.NotFoundPlayer
import com.unlam.feat.ui.component.common.player.CardPlayer
import com.unlam.feat.ui.component.common.player.CardPlayerDetail
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.util.StateEvent


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailEventMyEventScreen(
    state: DetailEventState,
    onClick: (DetailEventEvent) -> Unit,
    navigateTo: (DetailEventEvent.NavigateTo.TypeNavigate) -> Unit
) {

    val pagerState = rememberPagerState()
    var nextPage by remember { mutableStateOf(false) }

    if (nextPage) {
        LaunchedEffect(true) {
            pagerState.animateScrollToPage(1)
            nextPage = false
        }
    }

    Column {
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
                        onClick = onClick
                    )
                    1 -> PageTwo(
                        state,
                        onClick = onClick
                    )
                    2 -> PageTree(
                        state,
                        onClick = onClick
                    )
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
                    icon = Icons.Outlined.PersonAdd,
                    onClick = {
                        navigateTo(
                            DetailEventEvent.NavigateTo.TypeNavigate.NavigateToSuggestedPlayers(
                                state.event?.id
                            )
                        )
                    },
                    height = 70.dp,
                    width = 70.dp,
                    contentColor = GreenColor,
                    backgroundColor = GreenColor90,
                    textColor = PurpleDark,
                )
            } else {
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

}

@Composable
fun PageOne(
    state: DetailEventState,
    onClick: (DetailEventEvent) -> Unit
) {
    val event = state.event!!
    Column(modifier = Modifier.fillMaxWidth()) {
        FeatText(
            text = "Detalle del evento:",
            fontSize = MaterialTheme.typography.h6.fontSize,
            separator = true,
            verticalPadding = true
        )
        FeatEventDetail(
            event = event,
            stateEvent = if (event.state.description == StateEvent.CREATED) StateEvent.CREATED else StateEvent.CONFIRMED,
            onClick = {
                when (it) {
                    TypeClick.Event.TypleClickEvent.Confirm -> {
                        onClick(DetailEventEvent.ConfirmEvent)
                    }
                    TypeClick.Event.TypleClickEvent.Cancel -> {
                        onClick(DetailEventEvent.CancelEvent)
                    }
                }
            }
        )
    }
}

@Composable
fun PageTwo(
    state: DetailEventState,
    onClick: (DetailEventEvent) -> Unit
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
                                        onClick = {
                                            onClick(DetailEventEvent.KickPlayer(player.id))
                                        },
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
                NotFoundPlayer()
            }

        }
    }
}

@Composable
fun PageTree(
    state: DetailEventState,
    onClick: (DetailEventEvent) -> Unit
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
                            CardPlayerDetail(
                                player = player
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    if (player.origin.uppercase() == StateEvent.PLAYER_POSTULATED) {
                                        FeatOutlinedButton(
                                            textContent = "Rechazar",
                                            height = 40.dp,
                                            onClick = {
                                                onClick(DetailEventEvent.RejectPlayer(player.id))
                                            },
                                            contentColor = RedColor,
                                            backgroundColor = RedColor90,
                                            textColor = PurpleDark
                                        )
                                        FeatOutlinedButton(
                                            textContent = "Aceptar",
                                            height = 40.dp,
                                            onClick = {
                                                onClick(DetailEventEvent.AcceptPlayer(player.id))
                                            },
                                            contentColor = GreenColor,
                                            backgroundColor = GreenColor90,
                                            textColor = PurpleDark
                                        )
                                    } else if (player.origin.uppercase() == StateEvent.INVITED) {
                                        FeatOutlinedButton(
                                            textContent = "Cancelar",
                                            height = 40.dp,
                                            onClick = {
                                                onClick(DetailEventEvent.RejectPlayer(player.id))
                                            },
                                            contentColor = RedColor,
                                            backgroundColor = RedColor90,
                                            textColor = PurpleDark
                                        )
                                    }

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
