package com.unlam.feat.ui.view.event.new_event

import android.Manifest
import android.location.Geocoder
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GpsFixed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.unlam.feat.R
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.component.common.PermissionFlow
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

    Column {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                count = 2,
                state = pagerState
            ) { position ->
                when (position) {
                    0 -> PageOne(state, onValueChange)
                    1 -> PageTwo(state, onValueChange, onClick, openMap = {
                        openMap = true
                    })
                }
            }
            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                pagerState = pagerState
            )
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

@Composable
private fun PageOne(
    state: NewEventState = NewEventState(),
    onValueChange: (NewEventEvents.onValueChange) -> Unit,
) {

    val sports = mutableListOf<String>()
    state.sportGenericList.map {
        sports.add(it.description)
    }

    val typeSport = mutableListOf<String>()
    if (state.sportGeneric.isNotEmpty()) {
        state.sportList.map {
            if (it.sportGeneric.id == state.sportGeneric.toInt())
                typeSport.add(it.description)
        }
    }

    FeatForm(
        modifier = Modifier.padding(10.dp),
        title = "Datos del nuevo evento:",
        page = "1/2"
    ) {
        Column {
            FeatOutlinedDropDown(
                label = "Deporte",
                selectedText = { value ->
                    state.sportGenericList.forEach {
                        if (it.description == value) {
                            onValueChange(
                                NewEventEvents.onValueChange(
                                    TypeValueChange.OnValueChangeSportGeneric, it.id.toString()
                                )
                            )
                        }
                    }
                },
                options = sports,
                error = when (state.sportGenericError) {
                    NewEventState.GenericError.FieldEmpty -> {
                        stringResource(id = R.string.text_field_empty)
                    }
                    else -> ""
                }
            )

            FeatOutlinedDropDown(
                label = "Tipo",
                options = typeSport,
                selectedText = { value ->
                    state.sportList.forEach {
                        if (it.description == value) onValueChange(
                            NewEventEvents.onValueChange(
                                TypeValueChange.OnValueChangeTypeSport,
                                it.id.toString()
                            )
                        )
                    }
                },
                error = when (state.sportError) {
                    NewEventState.GenericError.FieldEmpty -> {
                        stringResource(id = R.string.text_field_empty)
                    }
                    else -> ""
                }
            )

            FeatOutlinedTextField(
                text = state.name,
                textLabel = "Nombre del evento",
                maxLines = 1,
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
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PageTwo(
    state: NewEventState,
    onValueChange: (NewEventEvents.onValueChange) -> Unit,
    onClick: (NewEventEvents.onClick) -> Unit,
    openMap: () -> Unit
) {
    val periodicityList = mutableListOf<String>()
    state.periodicityList.map {
        periodicityList.add(it.description)
    }
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    val shouldShowRationale = remember {
        mutableStateOf(false)
    }
    val exactLocationPermission = remember {
        mutableStateOf(false)
    }
    val locationPermissionDenied = remember {
        mutableStateOf(false)
    }
    PermissionFlow(permissionState,
        shouldShowRationale,
        exactLocationPermission,
        locationPermissionDenied)

    FeatForm(
        modifier = Modifier.padding(10.dp),
        title = "Datos del nuevo evento:",
        page = "2/2"
    ) {
        Column {

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
                    NewEventState.DateError.DateInvalid -> stringResource(R.string.invalid_date)
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
                titlePicker = "Seleccione una hora de fin",
                error = when (state.endTimeError) {
                    NewEventState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                    else -> ""
                }
            )

            FeatOutlinedDropDown(
                label = "Perioricidad",
                options = periodicityList,
                selectedText = { value ->
                    state.periodicityList.forEach {
                        if (it.description == value) {
                            onValueChange(
                                NewEventEvents.onValueChange(
                                    TypeValueChange.OnValueChangePeriodicity,
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
                            val allPermissionsRevoked =
                                permissionState.permissions.size == permissionState.revokedPermissions.size
                            if (permissionState.allPermissionsGranted) {
                                openMap()
                            }else if (!permissionState.permissionRequested) {
                                permissionState.launchMultiplePermissionRequest()
                            }else if (!allPermissionsRevoked) {
                                exactLocationPermission.value = true
                            } else if (permissionState.shouldShowRationale) {
                                shouldShowRationale.value = true
                            } else if (allPermissionsRevoked) {
                                locationPermissionDenied.value = true
                            }
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
            FeatSpacerSmall()
            FeatOutlinedButton(
                modifier = Modifier.align(Alignment.End),
                textContent = "Aceptar",
                contentColor = GreenColor,
                backgroundColor = GreenColor90,
                textColor = PurpleDark
            ) {
                onClick(NewEventEvents.onClick(TypeClick.Submit))
            }
        }
    }
}