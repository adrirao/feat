package com.unlam.feat.ui.view.event.detail_event.suggestedPlayers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unlam.feat.ui.component.FeatOutlinedButton
import com.unlam.feat.ui.component.FeatText
import com.unlam.feat.ui.component.common.event.NotFoundEvent
import com.unlam.feat.ui.component.common.player.CardPlayer
import com.unlam.feat.ui.theme.RedColor
import com.unlam.feat.ui.theme.RedColor20
import com.unlam.feat.ui.view.event.detail_event.DetailEventEvent
import com.unlam.feat.ui.view.event.detail_event.DetailEventState

@Composable
fun SuggestedPlayers(
    state: SuggestedPlayersState,
) {
    val players = state.players!!
    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            FeatText(
                text = "Sugerencias de participantes:",
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