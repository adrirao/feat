package com.unlam.feat.ui.view.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.R
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.view.home.component.FeatEventCard
import com.unlam.feat.util.Constants

@Composable
fun EventScreen(
    state : EventState,
    onClick: (EventEvents.onClick) -> Unit
) {
    val events = state.events

    Column {
        FeatHeader(
            text = "Mis Eventos",
            fontSize = MaterialTheme.typography.h6.fontSize,
            onClick = {
                onClick(EventEvents.onClick(TypeClick.GoToNewEvent))
            }
        )
        LazyColumn(
            content = {
                items(events){event ->
                    FeatEventCard(
                        modifier = Modifier
                            .padding(10.dp)
                            .height(220.dp),
                        event = event,
                        onClick = {
                            EventEvents.onClick(TypeClick.GoToDetailEvent,event.id)
                        }
                    )
                }
            }
        )
    }
    if(state.eventsError != null){
        ErrorDialog(
            title = "Error mis eventos",
            desc = "Error al obtener mis eventos, por favor pruebe nuevamente o contactese con el administrador",
            onDismiss = {
                onClick(EventEvents.onClick(TypeClick.DismissDialog))
            }
        )
    }
    if (state.isLoading) {
        FeatCircularProgress()
    }
}