package com.unlam.feat.ui.view.home.detail_event

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.unlam.feat.R
import com.unlam.feat.model.Qualification
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.component.common.event.DetailEvent
import com.unlam.feat.ui.component.common.event.NotFoundEvent
import com.unlam.feat.ui.component.common.player.CardPlayer
import com.unlam.feat.ui.component.common.player.CardPlayerCalification
import com.unlam.feat.ui.theme.*
import com.unlam.feat.util.Constants

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailEventHomeScreen(
    state: DetailEventHomeState,
    qualifications: List<Qualification>,
    descOrigen: String,
    onClick: (DetailEventHomeEvent) -> Unit,
    changeQualification: (Qualification) -> Unit
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
                    descOrigen = descOrigen,
                    onClick = {
                        onClick(DetailEventHomeEvent.OnClick)
                    }
                )
                1 -> PageTwo(
                    state,
                    qualifications,
                    descOrigen,
                    changeQualification = changeQualification,
                    onClick = { onClick(DetailEventHomeEvent.OnClickCardPlayer(it)) }
                )
            }
        }


        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            pagerState = pagerState
        )

        if (pagerState.currentPage == 0) {
            FeatOutlinedButtonIcon(
                modifier = Modifier.align(Alignment.BottomEnd),
                icon = Icons.Outlined.PlayArrow,
                height = 50.dp,
                width = 50.dp,
                onClick = { nextPage = true },
                contentColor = PurpleMedium,
                backgroundColor = PurpleMedium20
            )
        } else if (pagerState.currentPage == 1 && descOrigen == Constants.StateEvent.CONFIRMED) {
            FeatOutlinedButtonIcon(
                modifier = Modifier.align(Alignment.BottomEnd),
                icon = Icons.Outlined.Add,
                height = 50.dp,
                width = 50.dp,
                onClick = {
                    onClick(DetailEventHomeEvent.OnClick)
                },
                contentColor = YellowColor,
                backgroundColor = YellowColor20
            )
        }
    }

}

@Composable
fun PageOne(
    state: DetailEventHomeState,
    descOrigen: String,
    onClick: () -> Unit
) {
    val event = state.event!!
    DetailEvent(
        event = event,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                content = {
                    if (descOrigen != Constants.StateEvent.FINALIZED || descOrigen != Constants.StateEvent.CONFIRMED) {
                        FeatOutlinedButton(
                            modifier = Modifier.weight(1f),
                            contentColor = GreenColor,
                            backgroundColor = GreenColor90,
                            textColor = PurpleDark,
                            textContent = "Participar",
                            onClick = { onClick() }
                        )
                    }
                }
            )
        }
    )

}

@Composable
fun PageTwo(
    state: DetailEventHomeState,
    qualifications: List<Qualification>,
    descOrigen: String,
    changeQualification: (Qualification) -> Unit,
    onClick: (Int) -> Unit
) {
    val players = state.players!!

    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            FeatText(
                text = if (descOrigen != Constants.StateEvent.FINALIZED)
                    "Participantes del evento:" else "Califica a los perticipantes:",
                fontSize = MaterialTheme.typography.h6.fontSize,
                separator = true,
                verticalPadding = true
            )
            if (players.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp),
                    content = {
                        if (descOrigen == Constants.StateEvent.FINALIZED) {
                            items(players) { player ->
                                CardPlayer(
                                    player = player,
                                    content = {}
                                )
                            }
                        } else {
                            items(players) { player ->
                                val qualification =
                                    qualifications.find { qualificationFiltered -> qualificationFiltered.id == player.id }
                                CardPlayerCalification(
                                    modifier = Modifier.clickable {
                                          onClick(player.id)
                                    },
                                    player = player,
                                    content = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(.75f),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {

                                            Surface(
                                                modifier = Modifier
                                                    .border(
                                                        2.dp,
                                                        if (qualification!!.liked) GreenColor else Color.Transparent,
                                                        RoundedCornerShape(50)
                                                    )
                                                    .clickable {
                                                        changeQualification(
                                                            Qualification(
                                                                player.id,
                                                                liked = true,
                                                                ""
                                                            )
                                                        )
                                                    },
                                                shape = RoundedCornerShape(50),
                                                color = if (qualification.liked) GreenColor20 else Color.Transparent
                                            ) {
                                                Icon(
                                                    modifier = Modifier
                                                        .size(50.dp)
                                                        .padding(10.dp),
                                                    painter = painterResource(id = R.drawable.like),
                                                    contentDescription = null,
                                                    tint = if (qualification.liked) GreenColor else PurpleLight,
                                                )
                                            }

                                            Surface(
                                                modifier = Modifier
                                                    .border(
                                                        2.dp,
                                                        if (!qualification.liked) RedColor else Color.Transparent,
                                                        RoundedCornerShape(50)
                                                    )
                                                    .clickable {
                                                        changeQualification(
                                                            Qualification(
                                                                player.id,
                                                                liked = false,
                                                                ""
                                                            )
                                                        )
                                                    },
                                                shape = RoundedCornerShape(50),
                                                color = if (!qualification.liked) RedColor20 else Color.Transparent,
                                            ) {
                                                Icon(
                                                    modifier = Modifier
                                                        .size(50.dp)
                                                        .padding(10.dp),
                                                    painter = painterResource(id = R.drawable.dislike),
                                                    contentDescription = null,
                                                    tint = if (!qualification.liked) RedColor else PurpleLight,
                                                )
                                            }
                                        }
                                    }
                                )
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

