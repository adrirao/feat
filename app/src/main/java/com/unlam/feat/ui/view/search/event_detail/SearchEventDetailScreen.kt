package com.unlam.feat.ui.view.search.event_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
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
import com.unlam.feat.ui.component.common.event.NotFoundEvent
import com.unlam.feat.ui.component.common.player.CardPlayer
import com.unlam.feat.ui.theme.GreenColor
import com.unlam.feat.ui.theme.GreenColor20
import com.unlam.feat.ui.theme.PurpleMedium
import com.unlam.feat.ui.theme.PurpleMedium20

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailSearchEventScreen(
    state: SearchEventDetailState,
    onClick: (SearchEventDetailEvent) -> Unit
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
                        onClick(SearchEventDetailEvent.ApplyEvent)
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
        }
    }

}

@Composable
fun PageOne(
    state: SearchEventDetailState,
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
                    FeatOutlinedButton(
                        modifier = Modifier,
                        textColor = GreenColor,
                        contentColor = GreenColor,
                        backgroundColor = GreenColor20,
                        textContent = "Participar",
                        height = 45.dp,
                        onClick = { onClick() }
                    )
                }
            )
        }
    )

}

@Composable
fun PageTwo(
    state: SearchEventDetailState
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
                                player = player,
                                content = {}
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
