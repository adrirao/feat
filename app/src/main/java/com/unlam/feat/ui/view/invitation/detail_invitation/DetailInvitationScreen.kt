package com.unlam.feat.ui.view.invitation.detail_invitation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.unlam.feat.ui.component.FeatOutlinedButton
import com.unlam.feat.ui.component.FeatOutlinedButtonIcon
import com.unlam.feat.ui.component.FeatText
import com.unlam.feat.ui.component.common.event.DetailEvent
import com.unlam.feat.ui.component.common.event.FeatEventDetail
import com.unlam.feat.ui.component.common.event.NotFoundEvent
import com.unlam.feat.ui.component.common.player.CardPlayer
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.util.StateEvent


@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailInvitationScreen(
    state: DetailInvitationState,
    onClick: (DetailInvitationEvent) -> Unit,
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
                count = 2,
                state = pagerState
            ) { position ->
                when (position) {
                    0 -> PageOne(
                        state = state,
                        onClick = onClick
                    )
                    1 -> PageTwo(
                        state,
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
                    icon = Icons.Outlined.PersonAdd, onClick = { nextPage = true },
                    height = 70.dp,
                    width = 70.dp,
                    contentColor = GreenColor,
                    backgroundColor = GreenColor20,
                    textColor = GreenColor,
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
    state: DetailInvitationState,
    onClick: (DetailInvitationEvent) -> Unit
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
            stateEvent = StateEvent.INVITED,
            onClick = {
                when(it){
                    TypeClick.Event.TypleClickEvent.Confirm -> {
                        onClick(DetailInvitationEvent.ConfirmInvitation)
                    }
                    TypeClick.Event.TypleClickEvent.Cancel -> {
                        onClick(DetailInvitationEvent.CancelInvitation)
                    }
                }
            }
        )
    }
}

@Composable
fun PageTwo(
    state: DetailInvitationState,
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
                            CardPlayer(
                                player = player
                            )
                        }
                    }
                )
            } else {
                NotFoundEvent()
            }

        }
    }
}

