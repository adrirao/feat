package com.unlam.feat.ui.view.config_profile

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.unlam.feat.R
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.event.new_event.NewEventEvents
import com.unlam.feat.ui.view.profile.preferences.EditProfilePreferencesEvent


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ConfigProfileScreen(
    state: ConfigProfileState,
    onEvent: (ConfigProfileEvents) -> Unit,
) {
    var openMap by remember {
        mutableStateOf(false)
    }

    val pagerState = rememberPagerState()


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            count = 4,
            state = pagerState
        ) { position ->
            when (position) {
                0 -> PageOne(state, onEvent)
                1 -> PageTwo(state, onEvent, openMap = {
                    openMap = true
                })
                2 -> PageThree(state, onEvent)
                3 -> PageFour(state, onEvent)
            }
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            pagerState = pagerState
        )

    }
    if (openMap) {
        FeatMap(
            setLocation = {
                onEvent(
                    ConfigProfileEvents.onValueChange(
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
        onEvent(
            ConfigProfileEvents.onValueChange(
                TypeValueChange.OnValueChangeAddress,
                address[0].getAddressLine(0)
            )
        )
    }

}

@Composable
private fun PageOne(
    state: ConfigProfileState,
    onValueChange: (ConfigProfileEvents) -> Unit,
) {
    FeatForm(
        modifier = Modifier.padding(10.dp),
        title = "Datos personales:",
        page = "1/5"
    ) {

        Column {

            FeatOutlinedTextField(
                text = state.lastName,
                textLabel = stringResource(R.string.name),
                onValueChange = {
                    onValueChange(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeLastName,
                            it
                        )
                    )
                },
                error = when (state.lastNameError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                    else -> ""
                }
            )

            FeatOutlinedTextField(
                text = state.name,
                textLabel = stringResource(R.string.last_name),
                onValueChange = {
                    onValueChange(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeName,
                            it
                        )
                    )
                },
                error = when (state.nameError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                    else -> ""
                }
            )

            FeatOutlinedDatePicker(
                date = state.dateOfBirth,
                textLabel = stringResource(R.string.day),
                onValueChange = {
                    onValueChange(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeDate,
                            it.toString()
                        )
                    )
                },
                titlePicker = stringResource(R.string.select_date),
                error = when (state.dateOfBirthError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                    ConfigProfileState.DateError.DateInvalid -> stringResource(id = R.string.invalid_date)
                    ConfigProfileState.DateError.IsNotOfLegalAge -> stringResource(R.string.is_not_of_legal_age)
                    else -> ""
                }
            )

            FeatOutlinedTextField(
                text = state.nickname,
                textLabel = stringResource(R.string.nickname),
                onValueChange = {
                    onValueChange(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeNickname,
                            it
                        )
                    )
                },
                error = when (state.nicknameError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                    else -> ""
                }
            )

            FeatOutlinedDropDown(
                label = stringResource(R.string.sex),
                options = state.sexList,
                selectedText = { value ->
                    state.sexList.forEach {
                        if (it == value) onValueChange(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangeSex,
                                it
                            )
                        )
                    }
                },
                error = when (state.sexError) {
                    ConfigProfileState.GenericError.FieldEmpty -> {
                        stringResource(id = R.string.text_field_empty)
                    }
                    else -> ""
                }
            )


        }
    }

}

@Composable
private fun PageTwo(
    state: ConfigProfileState,
    onEvent: (ConfigProfileEvents) -> Unit,
    openMap: () -> Unit
) {
    FeatForm(
        modifier = Modifier.padding(10.dp),
        title = "Direcciónes:",
        page = "2/5"
    ) {
        Column {

            FeatOutlinedTextField(
                text = state.addressAlias,
                textLabel = stringResource(R.string.alias),
                onValueChange = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeLastName,
                            it
                        )
                    )
                },
                error = when (state.addressAliasError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                    else -> ""
                }
            )

            FeatOutlinedTextField(
                text = state.address,
                textLabel = stringResource(R.string.location),
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.GpsFixed,
                        contentDescription = stringResource(R.string.location),
                        modifier = Modifier.clickable {
                            openMap()
                        },
                        tint = PurpleLight,
                    )
                },
                onValueChange = {},
                error = when (state.addressError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                    else -> ""
                }
            )
        }
    }
}

@Composable
private fun PageThree(
    state: ConfigProfileState,
    onEvent: (ConfigProfileEvents) -> Unit,
) {
    FeatForm(
        modifier = Modifier.padding(10.dp),
        title = "Disponibilidad:",
        page = "3/5"
    ) {
        Column {

            FeatAvailabilityCheckBoxPickerTime(
                checked = state.sundayIsChecked,
                onCheckedChange = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeSundayIsChecked, "", valueBooleanOpt = it
                        )
                    )
                },
                label = stringResource(R.string.sunday),
                state.startTimeSunday,
                state.endTimeSunday,
                onValueChangeStartTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeStartTimeSunday, "", valueLocalTimeOpt = it
                        )
                    )
                },
                onValueChangeEndTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeEndTimeSunday, "", valueLocalTimeOpt = it
                        )
                    )
                },
                titlePickerStart = stringResource(R.string.select_start_time),
                titlePickerEnd = stringResource(R.string.select_end_time),
                error = when (state.sundayError) {
                    ConfigProfileState.DayError.WrongTimeRange -> stringResource(R.string.wrong_time_range)
                    ConfigProfileState.DayError.StarTimeEmpty -> stringResource(R.string.star_time_empty)
                    ConfigProfileState.DayError.EndTimeEmpty -> stringResource(R.string.end_time_empty)
                    ConfigProfileState.DayError.TimeEmpty -> stringResource(R.string.time_empty)
                    else -> ""
                }
            )

            FeatAvailabilityCheckBoxPickerTime(
                checked = state.mondayIsChecked,
                onCheckedChange = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeMondayIsChecked, "", valueBooleanOpt = it
                        )
                    )
                },
                label = stringResource(R.string.monday),
                state.startTimeMonday,
                state.endTimeMonday,
                onValueChangeStartTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeStartTimeMonday, "", valueLocalTimeOpt = it
                        )
                    )
                },
                onValueChangeEndTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeEndTimeMonday, "", valueLocalTimeOpt = it
                        )
                    )
                },
                titlePickerStart = stringResource(R.string.select_start_time),
                titlePickerEnd = stringResource(R.string.select_end_time),
                error = when (state.mondayError) {
                    ConfigProfileState.DayError.WrongTimeRange -> stringResource(R.string.wrong_time_range)
                    ConfigProfileState.DayError.StarTimeEmpty -> stringResource(R.string.star_time_empty)
                    ConfigProfileState.DayError.EndTimeEmpty -> stringResource(R.string.end_time_empty)
                    ConfigProfileState.DayError.TimeEmpty -> stringResource(R.string.time_empty)
                    else -> ""
                }
            )

            FeatAvailabilityCheckBoxPickerTime(
                checked = state.tuesdayIsChecked,
                onCheckedChange = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeTuesdayIsChecked, "", valueBooleanOpt = it
                        )
                    )
                },
                label = stringResource(R.string.tuesday),
                state.startTimeTuesday,
                state.endTimeTuesday,
                onValueChangeStartTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeStartTimeTuesday,
                            "",
                            valueLocalTimeOpt = it
                        )
                    )
                },
                onValueChangeEndTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeEndTimeTuesday, "", valueLocalTimeOpt = it
                        )
                    )
                },
                titlePickerStart = stringResource(R.string.select_start_time),
                titlePickerEnd = stringResource(R.string.select_end_time),
                error = when (state.tuesdayError) {
                    ConfigProfileState.DayError.WrongTimeRange -> stringResource(R.string.wrong_time_range)
                    ConfigProfileState.DayError.StarTimeEmpty -> stringResource(R.string.star_time_empty)
                    ConfigProfileState.DayError.EndTimeEmpty -> stringResource(R.string.end_time_empty)
                    ConfigProfileState.DayError.TimeEmpty -> stringResource(R.string.time_empty)
                    else -> ""
                }
            )

            FeatAvailabilityCheckBoxPickerTime(
                checked = state.wednesdayIsChecked,
                onCheckedChange = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeWednesdayIsChecked,
                            "",
                            valueBooleanOpt = it
                        )
                    )
                },
                label = stringResource(R.string.wednesday),
                state.startTimeWednesday,
                state.endTimeWednesday,
                onValueChangeStartTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeStartTimeWednesday,
                            "",
                            valueLocalTimeOpt = it
                        )
                    )
                },
                onValueChangeEndTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeEndTimeWednesday,
                            "",
                            valueLocalTimeOpt = it
                        )
                    )
                },
                titlePickerStart = stringResource(R.string.select_start_time),
                titlePickerEnd = stringResource(R.string.select_end_time),
                error = when (state.wednesdayError) {
                    ConfigProfileState.DayError.WrongTimeRange -> stringResource(R.string.wrong_time_range)
                    ConfigProfileState.DayError.StarTimeEmpty -> stringResource(R.string.star_time_empty)
                    ConfigProfileState.DayError.EndTimeEmpty -> stringResource(R.string.end_time_empty)
                    ConfigProfileState.DayError.TimeEmpty -> stringResource(R.string.time_empty)
                    else -> ""
                }
            )

            FeatAvailabilityCheckBoxPickerTime(
                checked = state.thursdayIsChecked,
                onCheckedChange = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeThursdayIsChecked, "", valueBooleanOpt = it
                        )
                    )
                },
                label = stringResource(R.string.thursday),
                state.startTimeThursday,
                state.endTimeThursday,
                onValueChangeStartTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeStartTimeThursday,
                            "",
                            valueLocalTimeOpt = it
                        )
                    )
                },
                onValueChangeEndTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeEndTimeThursday, "", valueLocalTimeOpt = it
                        )
                    )
                },
                titlePickerStart = stringResource(R.string.select_start_time),
                titlePickerEnd = stringResource(R.string.select_end_time),
                error = when (state.thursdayError) {
                    ConfigProfileState.DayError.WrongTimeRange -> stringResource(R.string.wrong_time_range)
                    ConfigProfileState.DayError.StarTimeEmpty -> stringResource(R.string.star_time_empty)
                    ConfigProfileState.DayError.EndTimeEmpty -> stringResource(R.string.end_time_empty)
                    ConfigProfileState.DayError.TimeEmpty -> stringResource(R.string.time_empty)
                    else -> ""
                }
            )

            FeatAvailabilityCheckBoxPickerTime(
                checked = state.fridayIsChecked,
                onCheckedChange = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeFridayIsChecked, "", valueBooleanOpt = it
                        )
                    )
                },
                label = stringResource(R.string.friday),
                state.startTimeFriday,
                state.endTimeFriday,
                onValueChangeStartTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeStartTimeFriday, "", valueLocalTimeOpt = it
                        )
                    )
                },
                onValueChangeEndTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeEndTimeFriday, "", valueLocalTimeOpt = it
                        )
                    )
                },
                titlePickerStart = stringResource(R.string.select_start_time),
                titlePickerEnd = stringResource(R.string.select_end_time),
                error = when (state.fridayError) {
                    ConfigProfileState.DayError.WrongTimeRange -> stringResource(R.string.wrong_time_range)
                    ConfigProfileState.DayError.StarTimeEmpty -> stringResource(R.string.star_time_empty)
                    ConfigProfileState.DayError.EndTimeEmpty -> stringResource(R.string.end_time_empty)
                    ConfigProfileState.DayError.TimeEmpty -> stringResource(R.string.time_empty)
                    else -> ""
                }
            )

            FeatAvailabilityCheckBoxPickerTime(
                checked = state.saturdayIsChecked,
                onCheckedChange = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeSaturdayIsChecked, "", valueBooleanOpt = it
                        )
                    )
                },
                label = stringResource(R.string.saturday),
                state.startTimeSaturday,
                state.endTimeSaturday,
                onValueChangeStartTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeStartTimeSaturday,
                            "",
                            valueLocalTimeOpt = it
                        )
                    )
                },
                onValueChangeEndTime = {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeEndTimeSaturday, "", valueLocalTimeOpt = it
                        )
                    )
                },
                titlePickerStart = stringResource(R.string.select_start_time),
                titlePickerEnd = stringResource(R.string.select_end_time),
                error = when (state.saturdayError) {
                    ConfigProfileState.DayError.WrongTimeRange -> stringResource(R.string.wrong_time_range)
                    ConfigProfileState.DayError.StarTimeEmpty -> stringResource(R.string.star_time_empty)
                    ConfigProfileState.DayError.EndTimeEmpty -> stringResource(R.string.end_time_empty)
                    ConfigProfileState.DayError.TimeEmpty -> stringResource(R.string.time_empty)
                    else -> ""
                }
            )
        }
    }
}

@Composable
private fun PageFour(
    state: ConfigProfileState,
    onEvent: (ConfigProfileEvents) -> Unit,
) {
    FeatForm(
        modifier = Modifier.padding(10.dp),
        title = "Preferencias:",
        page = "4/5"
    ) {
        FeatText(
            modifier = Modifier.padding(start = 20.dp),
            text = when (state.ageError) {
            ConfigProfileState.RangeAgeError.IsInvalidRange -> stringResource(R.string.invalid_range)
            ConfigProfileState.RangeAgeError.MinAgeEmpty -> stringResource(R.string.min_age_empty)
            ConfigProfileState.RangeAgeError.MaxAgeEmpty -> stringResource(R.string.max_age_empty)
            ConfigProfileState.RangeAgeError.FieldEmpty -> stringResource(R.string.text_field_empty)
            else -> "" },
            fontSize = 15.sp, color = MaterialTheme.colors.error)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            FeatOutlinedTextField(
                modifier = Modifier.width(150.dp),
                text = state.minAge,
                keyboardType = KeyboardType.Number,
                textLabel = "Edad minima",
                onValueChange = { age ->
                    if (age.length <= 3 && ((age.toIntOrNull()) ?: 0) <= 150) {
                        onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangeMinAge, age.filter { it.isDigit() })
                        )

                    }
                },
            )
            FeatOutlinedTextField(
                modifier = Modifier.width(150.dp),
                text = state.maxAge,
                textLabel = "Edad maxima",
                keyboardType = KeyboardType.Number,
                onValueChange = { age ->
                    if (age.length <= 3 && ((age.toIntOrNull()) ?: 0) <= 150) {
                        onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangeMaxAge, age.filter { it.isDigit() })
                        )

                    }
                }
            )
        }
        FeatOutlinedTextField(
            text = state.willingDistance,
            textLabel = "Distancia",
            keyboardType = KeyboardType.Number,
            onValueChange = { distance ->
                if (distance.length <= 2 && ((distance.toIntOrNull()) ?: 0) <= 30) {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeWillingDistance, distance
                        )
                    )

                }
            },
            error = when (state.willingDistanceError) {
                ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                else -> ""
            }
        )

        FeatOutlinedButton(
//            modifier = Modifier.align(Alignment.End),
            textContent = "ACEPTAR",
            contentColor = YellowColor,
            backgroundColor = YellowColor,
            textColor = PurpleDark,
            onClick = {
                onEvent(
                    ConfigProfileEvents.onClick(
                        TypeClick.Submit
                    )
                )
            }
        )

    }
}