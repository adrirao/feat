package com.unlam.feat.ui.view.event.new_event

import android.location.Geocoder
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GpsFixed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.ErrorColor
import com.unlam.feat.ui.theme.GreenLight
import com.unlam.feat.ui.theme.PurpleLight
import com.unlam.feat.ui.util.TypeValueChange

@Composable
fun NewEventScreen(
    state: NewEventState = NewEventState(),
    onValueChange: (NewEventEvents.onValueChange) -> Unit
) {
    var openMap by remember {
        mutableStateOf(false)
    }
    FeatContent(
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                FeatOutlinedTextField(
                    text = state.name,
                    textLabel = "Nombre del evento",
                    onValueChange = {
                        onValueChange(
                            NewEventEvents.onValueChange(
                                TypeValueChange.OnValueChangeName,
                                it
                            )
                        )
                    },
                    unFocusedColor = PurpleLight
                )
                FeatOutlinedDatePicker(
                    date = state.date,
                    textLabel = "Dia",
                    onValueChange = {
                        onValueChange(
                            NewEventEvents.onValueChange(
                                TypeValueChange.OnValueChangeDate,
                                it.toString()
                            )
                        )
                    },
                    titlePicker = "Seleccione una fecha",
                )
                FeatOutlinedTimePicker(
                    time = state.startTime,
                    label = "Hora inicio",
                    onValueChange = {
                        onValueChange(
                            NewEventEvents.onValueChange(
                                TypeValueChange.OnValueChangeStartTime,
                                it.toString()
                            )
                        )
                    },
                    titlePicker = "Seleccione una hora de inicio"
                )

                val periodicityList = mutableListOf<String>()
                state.periodicityList.map {
                    periodicityList.add(it.description)
                }

                FeatOutlinedDropDown(
                    label = "Perioricidad",
                    options = periodicityList,
                    selectedText = { value ->
                        state.periodicityList.forEach {
                            if (it.description == value) {
                                onValueChange(
                                    NewEventEvents.onValueChange(
                                        TypeValueChange.OnValueChangePerioridicity,
                                        it.id.toString()
                                    )
                                )
                            }
                        }
                    }
                )

                FeatOutlinedTextField(
                    text = state.address,
                    textLabel = "Ubicacion",
                    enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.GpsFixed,
                            contentDescription = "Ubicacion",
                            modifier = Modifier.clickable {
                                openMap = true
                            },
                            tint = PurpleLight,
                        )
                    },
                    onValueChange = {}
                )

                FeatOutlinedTextField(
                    text = state.description,
                    textLabel = "Descripcion",
                    onValueChange = {
                        onValueChange(
                            NewEventEvents.onValueChange(
                                TypeValueChange.OnValueChangeDescription,
                                it
                            )
                        )
                    }
                )

                FeatOutlinedTextField(
                    text = state.organizer,
                    textLabel = "Organizador",
                    onValueChange = {
                        onValueChange(
                            NewEventEvents.onValueChange(
                                TypeValueChange.OnValueChangeOrganizer,
                                it
                            )
                        )
                    }
                )
            }
            Row(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {

                FeatOutlinedButton(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    textContent = "Cancelar",
                    contentColor = ErrorColor
                ) {

                }
                FeatOutlinedButton(
                    textContent = "Aceptar",
                    contentColor = GreenLight
                ) {

                }
            }
        }
    }
    if (openMap) {
        FeatMap(
            setLocation = {
                onValueChange(
                    NewEventEvents.onValueChange(
                        TypeValueChange.OnValueChangePosition,
                        it.latitude.toString(),
                        it.longitude.toString()
                    )
                )
                openMap = false
            }
        )
    }
    if (state.latitude.isNotEmpty() && state.longitude.isNotEmpty()) {
        val address = Geocoder(LocalContext.current).getFromLocation(
            state.latitude.toDouble(),
            state.longitude.toDouble(),
            1
        )
        onValueChange(
            NewEventEvents.onValueChange(
                TypeValueChange.OnValueChangeAddress,
                address[0].getAddressLine(0)
            )
        )
    }
}