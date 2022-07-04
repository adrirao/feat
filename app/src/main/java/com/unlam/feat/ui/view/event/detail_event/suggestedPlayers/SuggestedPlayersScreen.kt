package com.unlam.feat.ui.view.event.detail_event.suggestedPlayers

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
import com.unlam.feat.ui.component.common.player.CardPlayer
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.YellowColor
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange

@Composable
fun SuggestedPlayers(
    state: SuggestedPlayersState,
    onClick: (SuggestedPlayersEvent) -> Unit,
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
            Row(){
                FeatOutlinedButton(
                    textContent = "Filtros",
                    contentColor = YellowColor,
                    backgroundColor = YellowColor,
                    textColor = PurpleDark,
                    onClick = {
                        onClick(SuggestedPlayersEvent.ChangeDialog)
                    }
                )
                FeatOutlinedButton(
                    textContent = "Reestablecer filtros",
                    contentColor = YellowColor,
                    backgroundColor = YellowColor,
                    textColor = PurpleDark,
                    onClick = {
                        onClick(SuggestedPlayersEvent.ChangeDialog)
                    }
                )
            }

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

    val suggestedPlayers: SuggestedPlayersViewModel = hiltViewModel()
    if(state.showDialog){
        FilerPlayers(state= state,onClick = onClick, onValueChange = {event -> suggestedPlayers.onEvent(event)})
    }
}

@Composable
fun FilerPlayers(
    state: SuggestedPlayersState,
    onClick: (SuggestedPlayersEvent) -> Unit,
    onValueChange: (SuggestedPlayersEvent.onValueChange) -> Unit,
){

    Dialog(
        onDismissRequest = {
            onClick(SuggestedPlayersEvent.ChangeDialog)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            FeatForm(
                modifier = Modifier.padding(10.dp),
                title = "Filtros:",
                page = ""
            ) {
                Row(){
                    FeatOutlinedTextField(
                        text = state.minAge,
                        textLabel = "Edad Mínima",
                        keyboardType = KeyboardType.Number,
                        onValueChange = {
                            onValueChange(
                                SuggestedPlayersEvent.onValueChange(
                                    TypeValueChange.OnValueChangeMinAge, it
                                )
                            )
                        }
                    )
                    FeatOutlinedTextField(
                        text = state.maxAge,
                        textLabel = "Edad Máxima",
                        keyboardType = KeyboardType.Number,
                        onValueChange = {
                            onValueChange(
                                SuggestedPlayersEvent.onValueChange(
                                    TypeValueChange.OnValueChangeMaxAge, it
                                )
                            )
                        }
                    )
                }

                FeatOutlinedTextField(
                    text = state.distance,
                    textLabel = "Distancia",
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        onValueChange(
                            SuggestedPlayersEvent.onValueChange(
                                TypeValueChange.OnValueChangeDistance, it
                            )
                        )
                    }
                )
                FeatOutlinedButton(
                    modifier = Modifier.align(Alignment.End),
                    textContent = "Filtrar",
                    contentColor = YellowColor,
                    backgroundColor = YellowColor,
                    textColor = PurpleDark,
                    onClick = {
                        onClick(SuggestedPlayersEvent.OnClick(TypeClick.Submit))
                    }
                )
            }
        }
    }
}