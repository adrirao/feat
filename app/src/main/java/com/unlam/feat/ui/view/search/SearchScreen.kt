package com.unlam.feat.ui.view.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.unlam.feat.R
import com.unlam.feat.model.Event
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.component.common.event.FeatEventCard
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
        backgroundColor= Color.Transparent,
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent,
        sheetPeekHeight = 40.dp,
        sheetContent = {
           SheetContent(sheetState,scope,state,onEvent)
        },

    ) {
        if (state.isLoading) {
            FeatCircularProgress()
        }else{
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



//    val searchViewModel: SearchViewModel = hiltViewModel()
//    if(state.showDialog){
//        FilerEvents(state= state,onClick = onClick, onValueChange = {event -> searchViewModel.onEvent(event)})
//    }
}


@Composable
fun FilerEvents(
    state: SearchState,
    onClick: (SearchEvent) -> Unit,
    onEvent: (SearchEvent) -> Unit
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
                                onEvent(
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
                                onEvent(
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
                        onValueChange = {
                            onEvent(
                                SearchEvent.onValueChange(
                                    TypeValueChange.OnValueChangeStartTime,
                                    "",
                                    valueLocalTimeOpt = it
                                )
                            )
                        },
                        label = stringResource(R.string.start_time),
                        titlePicker = stringResource(R.string.select_start_time),
                        error = "",
                        isErrorVisible = false
                    )
                    FeatOutlinedTimePicker(
                        modifier = Modifier.width(100.dp),
                        time = state.time_end,
                        onValueChange = {
                            onEvent(
                                SearchEvent.onValueChange(
                                    TypeValueChange.OnValueChangeEndTime, "", valueLocalTimeOpt = it
                                )
                            )
                        },
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
                        onEvent(
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SheetContent(
    sheetState:BottomSheetState,
    scope: CoroutineScope,
    state: SearchState,
    onEvent: (SearchEvent) -> Unit
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

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors= ButtonDefaults.buttonColors(Color.Transparent),
                elevation= ButtonDefaults.elevation(0.dp),
                onClick = {
                    scope.launch {
                        if(sheetState.isCollapsed) {
                            sheetState.expand()
                        } else {
                            sheetState.collapse()
                        }
                    }
                }) {
                Icon(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(PurpleDark)
                        .padding(0.dp)
                        .size(25.dp),
                    imageVector = Icons.Outlined.FilterAlt,
                    tint = GreenColor90,
                    contentDescription = null
                )
                FeatText(
                    text = "Filtros",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    fontSize = 20.sp,
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
            ){
                FeatCheckbox(
                    checked = state.sportIsChecked,
                    onCheckedChange ={

                    })
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
                    enabled = state.sportIsChecked
                )
            }

            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(PurpleMedium)
            )
            FeatText(
                text = "Galeria",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .padding(15.dp),
                color = PurpleLight,
                fontSize = 18.sp,
            )
        }
    }
}

