package com.unlam.feat.ui.view.search

import android.util.Log
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
import com.unlam.feat.model.Day
import com.unlam.feat.model.Event
import com.unlam.feat.model.Player
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.component.common.event.FeatEventCard
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.YellowColor
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.event.new_event.NewEventEvents

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
        Row(){
            FeatOutlinedButton(
                textContent = "Filtros",
                contentColor = YellowColor,
                backgroundColor = YellowColor,
                textColor = PurpleDark,
                onClick = {
                    onClick(SearchEvent.ChangeDialog)
                }
            )
            FeatOutlinedButton(
                textContent = "Reestablecer filtros",
                contentColor = YellowColor,
                backgroundColor = YellowColor,
                textColor = PurpleDark,
                onClick = {
                    onClick(SearchEvent.RefreshData)
                }
            )
        }
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

    val searchViewModel: SearchViewModel = hiltViewModel()
    if(state.showDialog){
        FilerEvents(state= state,onClick = onClick, onValueChange = {event -> searchViewModel.onEvent(event)})
    }
}

@Composable
fun FilerEvents(
    state: SearchState,
    onClick: (SearchEvent) -> Unit,
    onValueChange: (SearchEvent.onValueChange) -> Unit,
){

    var sportList = mutableListOf<String>()

    state.sport.map {
        sportList.add(it.sport.description)
    }

    val days = mutableListOf<String>()
    days.add("Domingo");
    days.add("Lunes");
    days.add("Martes");
    days.add("Miércoles");
    days.add("Jueves");
    days.add("Viernes");
    days.add("Sábado");


    Dialog(
        onDismissRequest = {
            onClick(SearchEvent.ChangeDialog)
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
                FeatOutlinedDropDown(
                    label = "Deporte",
                    selectedText = { value ->
                        state.sport.forEach {
                            if (it.sport.description == value) {
                                onValueChange(
                                    SearchEvent.onValueChange(
                                        TypeValueChange.OnValueChangeSportGeneric, it.id.toString()
                                    )
                                )
                            }
                        }
                    },
                    options = sportList,
                    error = ""
                )
                FeatOutlinedDropDown(
                    label = "Día",
                    selectedText = { value ->
                        state.daysList.forEach {
                            if (it.description == value) {
                                onValueChange(
                                    SearchEvent.onValueChange(
                                        TypeValueChange.OnValueChangeDay, it.id.toString()
                                    )
                                )
                            }
                        }
                    },
                    options = days,
                    error = ""
                )
                Row() {
                    FeatOutlinedTimePicker(
                        modifier = Modifier.width(100.dp),
                        time = state.time_start,
                        onValueChange = { onValueChange(
                            SearchEvent.onValueChange(
                                TypeValueChange.OnValueChangeStartTime, "", valueLocalTimeOpt = it
                            )
                        ) },
                        label = stringResource(R.string.start_time),
                        titlePicker = stringResource(R.string.select_start_time),
                        error = "",
                        isErrorVisible = false
                    )
                    FeatOutlinedTimePicker(
                        modifier = Modifier.width(100.dp),
                        time = state.time_end,
                        onValueChange = { onValueChange(
                            SearchEvent.onValueChange(
                                TypeValueChange.OnValueChangeEndTime, "", valueLocalTimeOpt = it
                            )
                        ) },
                        label = stringResource(R.string.end_time),
                        titlePicker = stringResource(R.string.select_end_time),
                        error = "",
                        isErrorVisible = false
                    )
                }
                    FeatOutlinedTextField(
                        text = state.distance,
                        textLabel = "Distancia",
                        keyboardType = KeyboardType.Number,
                        onValueChange = {
                            onValueChange(
                                SearchEvent.onValueChange(
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
                        onClick(SearchEvent.OnClick(TypeClick.Submit))
                        onClick(SearchEvent.ChangeDialog)
                    }
                )
            }
        }
    }
}