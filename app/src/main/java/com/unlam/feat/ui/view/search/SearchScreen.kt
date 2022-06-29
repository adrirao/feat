package com.unlam.feat.ui.view.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unlam.feat.model.Event
import com.unlam.feat.ui.component.ErrorDialog
import com.unlam.feat.ui.component.FeatCircularProgress
import com.unlam.feat.ui.component.FeatHeader
import com.unlam.feat.ui.component.FeatText
import com.unlam.feat.ui.component.common.event.FeatEventCard

@Composable
fun SearchScreen(
    state: SearchState,
    onClick: (SearchEvent) -> Unit,
    onClickCard: (Event) -> Unit
) {
    val events = state.events

    Column {
        FeatText(
            text = "Buscar eventos:",
            fontSize = MaterialTheme.typography.h6.fontSize,
            separator = true,
            verticalPadding = true
        )
        LazyColumn(
            content = {
                items(events) { event ->
                    FeatEventCard(
                        modifier = Modifier
                            .padding(10.dp)
                            .height(220.dp),
                        event = event,
                        onClick = {
                            onClickCard(event)
                        }
                    )
                }
            }
        )
    }
    if (state.error.isNotEmpty()) {
        ErrorDialog(
            title = "Error mis eventos",
            desc = "Error al obtener mis eventos, por favor pruebe nuevamente o contactese con el administrador",
            onDismiss = {
                onClick(SearchEvent.DismissDialog)
            }
        )
    }
    if (state.isLoading) {
        FeatCircularProgress()
    }
}