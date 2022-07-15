package com.unlam.feat.ui.view.event

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.component.common.event.FeatEventCard
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.util.TypeClick

@Composable
fun EventScreen(
    state: EventState,
    onClick: (EventEvents.onClick) -> Unit,
    goToChat: (Int) -> Unit = {},
    onEvent:(EventEvents) -> Unit,
) {
    val events = state.events

    Column {
        FeatHeader(
            text = "Mis Eventos",
            fontSize = MaterialTheme.typography.h6.fontSize,
            icon = {
                IconButton(
                    onClick = {
                        onClick(EventEvents.onClick(TypeClick.GoToNewEvent))
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        tint = GreenColor,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        )
        LazyColumn(
            content = {
                items(events) { event ->
                    FeatEventCard(
                        modifier = Modifier
                            .padding(10.dp),
                        event = event,
                        showChat = true,
                        onClick = {
                            onClick(EventEvents.onClick(TypeClick.GoToDetailEvent, event.id))
                        },
                        goToChat = {
                            goToChat(it)
                        }
                    )
                }
            }
        )
    }
    if (state.isLoading) {
        FeatCircularProgress()
    }
}