package com.unlam.feat.ui.view.event.detail_event.suggestedPlayers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.unlam.feat.R
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.component.common.event.NotFoundEvent
import com.unlam.feat.ui.component.common.event.NotFoundPlayer
import com.unlam.feat.ui.component.common.player.CardPlayer
import com.unlam.feat.ui.theme.GreenColor
import com.unlam.feat.ui.theme.GreenColor90
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.YellowColor
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.event.detail_event.DetailEventEvent

@Composable
fun SuggestedPlayers(
    state: SuggestedPlayersState,
    onClick: (SuggestedPlayersEvent) -> Unit,
    onClickQualification: (Int) -> Unit
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
                            CardPlayer(
                                modifier = Modifier.clickable {
                                    onClickQualification(player.id)
                                },
                                player = player
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    FeatOutlinedButton(
                                        modifier = Modifier.weight(1f),
                                        textContent = "Invitar",
                                        onClick = {
                                            onClick(SuggestedPlayersEvent.OnClick.Invite(player.id))
                                        },
                                        contentColor = GreenColor,
                                        backgroundColor = GreenColor90,
                                        textColor = PurpleDark,
                                    )
                                }
                            }

                        }
                    }
                )
            } else {
                NotFoundPlayer()
            }

        }
    }

}