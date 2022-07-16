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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.unlam.feat.R
import com.unlam.feat.model.Qualification
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.component.common.event.DetailEvent
import com.unlam.feat.ui.component.common.event.FeatEventDetail
import com.unlam.feat.ui.component.common.event.NotFoundEvent
import com.unlam.feat.ui.component.common.event.NotFoundPlayer
import com.unlam.feat.ui.component.common.player.CardPlayer
import com.unlam.feat.ui.component.common.player.CardPlayerCalification
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.util.Screen
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.view.event.new_event.NewEventEvents
import com.unlam.feat.util.Constants
import com.unlam.feat.util.StateEvent

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailEventHomeScreen(
    state: DetailEventHomeState,
    qualifications: List<Qualification>,
    isOrganizer: Int = 0,
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
                    isOrganizer = isOrganizer,
                    onClick = { event ->
                        when (event) {
                            TypeClick.Event.TypleClickEvent.Confirm -> {
                                onClick(DetailEventHomeEvent.ApplyEvent)
                            }
                            TypeClick.Event.TypleClickEvent.Cancel -> {
                                onClick(DetailEventHomeEvent.CancelApplyEvent)
                            }
                        }
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
        } else if (pagerState.currentPage == 1 && descOrigen.uppercase() == StateEvent.FINALIZED) {
            FeatOutlinedButtonIcon(
                modifier = Modifier.align(Alignment.BottomEnd),
                icon = Icons.Outlined.Add,
                height = 50.dp,
                width = 50.dp,
                onClick = {
                    onClick(DetailEventHomeEvent.onClick(TypeClick.QualifyPlayers))
                },
                contentColor = YellowColor,
                backgroundColor = YellowColor90
            )
        }
    }

}

@Composable
fun PageOne(
    state: DetailEventHomeState,
    descOrigen: String,
    isOrganizer: Int = 0,
    onClick: (TypeClick.Event.TypleClickEvent) -> Unit
) {
    val event = state.event!!
    FeatEventDetail(
        event = event,
        stateEvent = descOrigen,
        isOrganizer = isOrganizer,
        onClick = onClick
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
                text = if (descOrigen.uppercase() != StateEvent.FINALIZED)
                    "Participantes del evento:" else "Califica a los participantes:",
                fontSize = MaterialTheme.typography.h6.fontSize,
                separator = true,
                verticalPadding = true
            )
            if (players.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp),
                    content = {
                        if (descOrigen.uppercase() != StateEvent.FINALIZED) {
                            items(players) { player ->
                                CardPlayer(
                                    player = player,
                                    content = {}
                                )
                            }
                        } else {
                            items(players) { player ->
                                var textObservation = remember { mutableStateOf(TextFieldValue()) }
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
                                                                observation = textObservation.value.text
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
                                                                textObservation.value.text
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
                                        FeatOutlinedTextField(
                                            text = textObservation.value.text,
                                            textLabel = "Observacion",
                                            onValueChange = {
                                                textObservation.value = TextFieldValue(it)
                                            }
                                        )
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

