package com.unlam.feat.ui.view.invitation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unlam.feat.ui.component.ErrorDialog
import com.unlam.feat.ui.component.FeatCircularProgress
import com.unlam.feat.ui.component.FeatText
import com.unlam.feat.ui.component.common.event.FeatEventCard
import com.unlam.feat.ui.component.common.event.NotFoundEvent
import com.unlam.feat.ui.util.TypeClick


@Composable
fun InvitationScreen(
    state: InvitationState,
    onClick: (InvitationEvent) -> Unit,
    onClickCard: (Int) -> Unit
) {
    val events = state.events

    Column {
        FeatText(
            text = "Invitaciones:",
            fontSize = MaterialTheme.typography.h6.fontSize,
            separator = true,
            verticalPadding = true
        )
        if (events.isNotEmpty()) {
            LazyColumn(
                content = {
                    items(events) { event ->
                        FeatEventCard(
                            modifier = Modifier
                                .padding(10.dp),
                            event = event,
                            onClick = {
                                onClickCard(event.id)
                            }
                        )
                    }
                }
            )
        } else {
            NotFoundEvent()
        }
    }
    if (state.error.isNotEmpty()) {
        ErrorDialog(
            title = "Error mis eventos",
            desc = "Error al obtener mis eventos, por favor pruebe nuevamente o contactese con el administrador",
            onDismiss = {
                onClick(InvitationEvent.onClick(TypeClick.DismissDialog))
            }
        )
    }
    if (state.isLoading) {
        FeatCircularProgress()
    }
}