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
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.unlam.feat.R
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NewEventScreen(
    state: NewEventState = NewEventState(),
    onValueChange: (NewEventEvents.onValueChange) -> Unit,
    onClick: (NewEventEvents.onClick) -> Unit
) {
    var openMap by remember {
        mutableStateOf(false)
    }

    val pagerState = rememberPagerState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            count = 2,
            state = pagerState
        ) { position ->
            when (position) {
                0 -> {
                    FeatForm {
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
                                error = when (state.nameError) {
                                    NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                                    else -> ""
                                }
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
                                error = when (state.dateError) {
                                    NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                                    else -> ""
                                }
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
                                titlePicker = "Seleccione una hora de inicio",
                                error = when (state.startTimeError) {
                                    NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                                    else -> ""
                                }
                            )
                            FeatOutlinedTimePicker(
                                time = state.endTime,
                                label = "Hora Fin",
                                onValueChange = {
                                    onValueChange(
                                        NewEventEvents.onValueChange(
                                            TypeValueChange.OnValueChangeEndTime,
                                            it.toString()
                                        )
                                    )
                                },
                                titlePicker = "Seleccione una hora de inicio",
                                error = when (state.endTimeError) {
                                    NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                                    else -> ""
                                }
                            )
                        }
                    }
                }
                1 -> FeatForm {
                    Column {
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
                            },
                            error = when (state.periodicityError) {
                                NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                                else -> ""
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
                            onValueChange = {},
                            error = when (state.addressError) {
                                NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                                else -> ""
                            }
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
                            },
                            error = when (state.descriptionError) {
                                NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                                else -> ""
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
                            },
                            error = when (state.organizerError) {
                                NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                                else -> ""
                            }
                        )
                        FeatSpacerSmall()
                        FeatOutlinedButton(
                            modifier = Modifier.align(Alignment.End),
                            textContent = "Aceptar",
                            contentColor = SuccessColor,
                            backgroundColor = GreenColor20
                        ) {
                            onClick(NewEventEvents.onClick(TypeClick.Submit))
                        }
                    }
                }
            }
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            pagerState = pagerState
        )
    }

//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Column {
//            FeatOutlinedTextField(
//                text = state.name,
//                textLabel = "Nombre del evento",
//                onValueChange = {
//                    onValueChange(
//                        NewEventEvents.onValueChange(
//                            TypeValueChange.OnValueChangeName,
//                            it
//                        )
//                    )
//                },
//                error = when (state.nameError) {
//                    NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
//                    else -> ""
//                }
//            )
//            FeatOutlinedDatePicker(
//                date = state.date,
//                textLabel = "Dia",
//                onValueChange = {
//                    onValueChange(
//                        NewEventEvents.onValueChange(
//                            TypeValueChange.OnValueChangeDate,
//                            it.toString()
//                        )
//                    )
//                },
//                titlePicker = "Seleccione una fecha",
//                error = when (state.dateError) {
//                    NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
//                    else -> ""
//                }
//            )
//            FeatOutlinedTimePicker(
//                time = state.startTime,
//                label = "Hora inicio",
//                onValueChange = {
//                    onValueChange(
//                        NewEventEvents.onValueChange(
//                            TypeValueChange.OnValueChangeStartTime,
//                            it.toString()
//                        )
//                    )
//                },
//                titlePicker = "Seleccione una hora de inicio",
//                error = when (state.startTimeError) {
//                    NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
//                    else -> ""
//                }
//            )
//
//            FeatOutlinedTimePicker(
//                time = state.endTime,
//                label = "Hora Fin",
//                onValueChange = {
//                    onValueChange(
//                        NewEventEvents.onValueChange(
//                            TypeValueChange.OnValueChangeEndTime,
//                            it.toString()
//                        )
//                    )
//                },
//                titlePicker = "Seleccione una hora de inicio",
//                error = when (state.endTimeError) {
//                    NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
//                    else -> ""
//                }
//            )
//
//            val periodicityList = mutableListOf<String>()
//            state.periodicityList.map {
//                periodicityList.add(it.description)
//            }
//
//            FeatOutlinedDropDown(
//                label = "Perioricidad",
//                options = periodicityList,
//                selectedText = { value ->
//                    state.periodicityList.forEach {
//                        if (it.description == value) {
//                            onValueChange(
//                                NewEventEvents.onValueChange(
//                                    TypeValueChange.OnValueChangePerioridicity,
//                                    it.id.toString()
//                                )
//                            )
//                        }
//                    }
//                },
//                error = when (state.periodicityError) {
//                    NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
//                    else -> ""
//                }
//            )
//
//            FeatOutlinedTextField(
//                text = state.address,
//                textLabel = "Ubicacion",
//                enabled = false,
//                trailingIcon = {
//                    Icon(
//                        imageVector = Icons.Outlined.GpsFixed,
//                        contentDescription = "Ubicacion",
//                        modifier = Modifier.clickable {
//                            openMap = true
//                        },
//                        tint = PurpleLight,
//                    )
//                },
//                onValueChange = {},
//                error = when (state.addressError) {
//                    NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
//                    else -> ""
//                }
//            )
//
//            FeatOutlinedTextField(
//                text = state.description,
//                textLabel = "Descripcion",
//                onValueChange = {
//                    onValueChange(
//                        NewEventEvents.onValueChange(
//                            TypeValueChange.OnValueChangeDescription,
//                            it
//                        )
//                    )
//                },
//                error = when (state.descriptionError) {
//                    NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
//                    else -> ""
//                }
//            )
//
//            FeatOutlinedTextField(
//                text = state.organizer,
//                textLabel = "Organizador",
//                onValueChange = {
//                    onValueChange(
//                        NewEventEvents.onValueChange(
//                            TypeValueChange.OnValueChangeOrganizer,
//                            it
//                        )
//                    )
//                },
//                error = when (state.organizerError) {
//                    NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
//                    else -> ""
//                }
//            )
//        }
//        Row(
//            modifier = Modifier.align(Alignment.BottomCenter)
//        ) {
//
//            FeatOutlinedButton(
//                modifier = Modifier.fillMaxWidth(0.5f),
//                textContent = "Cancelar",
//                contentColor = ErrorColor
//            ) {
//                onClick(NewEventEvents.onClick(TypeClick.GoToEvent))
//            }
//            FeatOutlinedButton(
//                textContent = "Aceptar",
//                contentColor = GreenLight
//            ) {
//                onClick(NewEventEvents.onClick(TypeClick.Submit))
//            }
//        }
//    }

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