package com.unlam.feat.ui.view.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.unlam.feat.model.*
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.component.common.event.FeatEventCard
import com.unlam.feat.ui.component.common.event.FeatEventCardHome
import com.unlam.feat.ui.component.common.event.NotFoundEvent
import com.unlam.feat.ui.theme.PurpleDarkAlt
import com.unlam.feat.ui.theme.PurpleMedium
import com.unlam.feat.ui.util.TypeClick
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@OptIn(ExperimentalPagerApi::class)
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    state: HomeState,
    onClick: (HomeEvents) -> Unit
) {
    val eventsConfirmed = state.eventsConfirmedForMy ?: listOf()
    val eventsSuggested = state.eventsSuggestedToday ?: listOf()

    val pageState = rememberPagerState()

    Column {
        Column {
            if (eventsSuggested.isNotEmpty()) {
                LaunchedEffect(Unit) {
                    while (true) {
                        yield()
                        delay(2000)
                        tween<Float>(600)
                        pageState.animateScrollToPage(
                            page = (pageState.currentPage + 1) % (pageState.pageCount)
                        )
                    }
                }
                FeatText(
                    text = "Eventos Sugeridos de la semana:",
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    separator = true,
                    verticalPadding = true
                )
                if (eventsSuggested.isNotEmpty()) {
                    HorizontalPager(
                        count = eventsSuggested.size,
                        state = pageState,
                        content = { page ->
                            val event = eventsSuggested[page]
                            FeatEventCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .padding(horizontal = 10.dp),
                                new = true,
                                colorCard = PurpleDarkAlt,
                                event = event,
                                onClick = {
                                    onClick(HomeEvents.onClick(TypeClick.GoToDetailEvent, event.id))
                                }
                            )
                        }
                    )
                } else {
                    FeatText(text = "No se encontraron eventos.")
                }
            }
        }

        Column {
            FeatText(
                text = "Eventos postulados:",
                fontSize = MaterialTheme.typography.h6.fontSize,
                separator = true,
                verticalPadding = true
            )
            if (eventsConfirmed.isNotEmpty()) {
                LazyColumn(content = {
                    items(eventsConfirmed) { event ->
                        FeatEventCardHome(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(horizontal = 10.dp),
                            event = event,
                            onClick = {
                                onClick(HomeEvents.onClick(TypeClick.GoToDetailEvent, event.id))
                            }
                        )
                    }
                })
            } else {
                NotFoundEvent()
            }
        }
    }

}