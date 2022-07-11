package com.unlam.feat.ui.view.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.model.Event
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.component.common.event.FeatEventCard
import com.unlam.feat.ui.component.common.event.NotFoundEvent
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    state: SearchState,
    onClick: (SearchEvent) -> Unit,
    onClickCard: (Event) -> Unit,
    onEvent: (SearchEvent) -> Unit
) {
    val events = state.events

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        backgroundColor = Color.Transparent,
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            SheetContent(state, onEvent) {
                if (it) {
                    scope.launch {
                        if (sheetState.isCollapsed) {
                            sheetState.expand()
                        } else {
                            sheetState.collapse()
                        }
                    }
                }
            }
        },

        ) {
        if (state.isLoading) {
            FeatCircularProgress()
        } else {
            Column {

                FeatHeader(
                    text = "Buscar eventos:",
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    icon = {
                        IconButton(
                            modifier = Modifier
                                .padding(end = 10.dp, bottom = 5.dp)
                                .clip(RoundedCornerShape(45))
                                .background(GreenColor),

                            onClick = {
                                scope.launch {
                                    if (sheetState.isCollapsed) {
                                        sheetState.expand()
                                    } else {
                                        sheetState.collapse()
                                    }
                                }
                            },
                        ) {
                            Icon(
                                imageVector =  Icons.Outlined.FilterAlt,
                                contentDescription = null,
                                tint = PurpleMedium,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                )

               }
                if(events.isNotEmpty()){
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
                }else{
                    NotFoundEvent()
                }

            }
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

    }




@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SheetContent(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    onClickFilter: (Boolean) -> Unit
) {
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


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(PurpleDark)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                FeatText(
                    text = "Filtros",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 15.dp, end = 5.dp, bottom = 5.dp),
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    color = Color.White,
                )
            }
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .background(GreenColor)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FeatCheckbox(
                    checked = state.sportIsChecked,
                    onCheckedChange = {
                        onEvent(
                            SearchEvent.onValueChange(
                                TypeValueChange.OnValueChangeSportIsChecked,
                                "",
                                valueBooleanOpt = it
                            )
                        )
                        if (!it) {
                            onEvent(
                                SearchEvent.onValueChange(
                                    TypeValueChange.OnValueChangeSportGeneric, ""
                                )
                            )
                        }

                    }
                )
                FeatOutlinedDropDown(
                    label = "Deporte",
                    selectedText = { value ->
                        state.sport.forEach {
                            if (it.sport.description == value) {
                                onEvent(
                                    SearchEvent.onValueChange(
                                        TypeValueChange.OnValueChangeSportGeneric, it.id.toString()
                                    )
                                )
                            }
                        }
                    },
                    options = sportList,
                    error = "",
                    enabled = state.sportIsChecked,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FeatCheckbox(
                    checked = state.dayIsChecked,
                    onCheckedChange = {
                        onEvent(
                            SearchEvent.onValueChange(
                                TypeValueChange.OnValueChangeDayIsChecked,
                                "",
                                valueBooleanOpt = it
                            )
                        )
                        if (!it) {
                            onEvent(
                                SearchEvent.onValueChange(
                                    TypeValueChange.OnValueChangeDay, ""
                                )
                            )
                        }

                    }
                )
                FeatOutlinedDropDown(
                    label = "Día",
                    selectedText = { value ->
                        state.daysList.forEach {
                            if (it.description == value) {
                                onEvent(
                                    SearchEvent.onValueChange(
                                        TypeValueChange.OnValueChangeDay, it.id.toString()
                                    )
                                )
                            }
                        }
                    },
                    options = days,
                    error = "",
                    enabled = state.dayIsChecked,
                )
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FeatCheckbox(
                    checked = state.timeIsChecked,
                    onCheckedChange = {
                        onEvent(
                            SearchEvent.onValueChange(
                                TypeValueChange.OnValueChangeTimeIsChecked,
                                "",
                                valueBooleanOpt = it
                            )
                        )
                        if (!it) {
                            onEvent(
                                SearchEvent.onValueChange(
                                    TypeValueChange.OnValueChangeStartTime, ""
                                )
                            )
                            onEvent(
                                SearchEvent.onValueChange(
                                    TypeValueChange.OnValueChangeEndTime, ""
                                )
                            )
                        }

                    }
                )
                Row {
                    FeatOutlinedTimePicker(
                        modifier = Modifier.width(120.dp),
                        time = state.time_start,
                        onValueChange = {
                            onEvent(
                                SearchEvent.onValueChange(
                                    TypeValueChange.OnValueChangeStartTime,
                                    "ok",
                                    valueLocalTimeOpt = it
                                )
                            )
                        },
                        label = stringResource(R.string.start_time),
                        titlePicker = stringResource(R.string.select_start_time),
                        error = "",
                        isErrorVisible = false,
                        enabled = state.timeIsChecked
                    )
                    FeatOutlinedTimePicker(
                        modifier = Modifier.width(150.dp),
                        time = state.time_end,
                        onValueChange = {
                            onEvent(
                                SearchEvent.onValueChange(
                                    TypeValueChange.OnValueChangeEndTime,
                                    "ok",
                                    valueLocalTimeOpt = it
                                )
                            )
                        },
                        label = stringResource(R.string.end_time),
                        titlePicker = stringResource(R.string.select_end_time),
                        error = "",
                        isErrorVisible = false,
                        enabled = state.timeIsChecked
                    )
                }

            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FeatCheckbox(
                    checked = state.distanceIsChecked,
                    onCheckedChange = {
                        onEvent(
                            SearchEvent.onValueChange(
                                TypeValueChange.OnValueChangeDistanceIsChecked,
                                "",
                                valueBooleanOpt = it
                            )
                        )
                        if (!it) {
                            onEvent(
                                SearchEvent.onValueChange(
                                    TypeValueChange.OnValueChangeDistance, ""
                                )
                            )
                        }

                    }

                )
                FeatOutlinedTextField(
                    text = state.distance ?: "",
                    textLabel = "Distancia",
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        onEvent(
                            SearchEvent.onValueChange(
                                TypeValueChange.OnValueChangeDistance, it
                            )
                        )
                    },
                    enabled = state.distanceIsChecked
                )
            }
            FeatOutlinedButton(
                modifier = Modifier.align(Alignment.End),
                textContent = "Filtrar",
                contentColor = GreenColor,
                backgroundColor = GreenColor,
                textColor = PurpleDark,
                onClick = {
                    onEvent(
                        SearchEvent.OnClick(TypeClick.Submit)
                    )
                    onClickFilter(true)
                }
            )
        }


    }
}



