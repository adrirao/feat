package com.unlam.feat.ui.view.config_profile

import android.location.Geocoder
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
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
import com.unlam.feat.R
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.PurpleLight
import com.unlam.feat.ui.util.TypeValueChange


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ConfigProfileScreen(
    state: ConfigProfileState,
    onValueChange: (ConfigProfileEvents.onValueChange) -> Unit,
    onClick: (ConfigProfileEvents.onClick) -> Unit
) {
    var openMap by remember {
        mutableStateOf(false)
    }

    val pagerState = rememberPagerState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            count = 3,
            state = pagerState
        ) { position ->
            when (position) {
                0 -> PageOne(state, onValueChange)
                1 -> PageTwo(state, onValueChange, onClick, openMap = {
                    openMap = true
                })
                2 -> PageThree(state, onValueChange, onClick)
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
                onValueChange(
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
        onValueChange(
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
    onValueChange: (ConfigProfileEvents.onValueChange) -> Unit,
) {
    FeatForm(
        modifier = Modifier.padding(10.dp)
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
    onValueChange: (ConfigProfileEvents.onValueChange) -> Unit,
    onClick: (ConfigProfileEvents.onClick) -> Unit,
    openMap: () -> Unit
) {
    FeatForm(
        modifier = Modifier.padding(10.dp)
    ) {
        Column {

            FeatOutlinedTextField(
                text = state.addressAlias,
                textLabel = stringResource(R.string.alias),
                onValueChange = {
                    onValueChange(
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
    onValueChange: (ConfigProfileEvents.onValueChange) -> Unit,
    onClick: (ConfigProfileEvents.onClick) -> Unit,
) {
    FeatForm(
        modifier = Modifier.padding(10.dp)
    ) {
        Column {

            FeatAvailabilityCheckBoxPickerTime(
                checked = state.sundayIsChecked,
                onCheckedChange = {
                    onValueChange(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeSundayIsChecked, "", valueBooleanOpt = it
                        )
                    )
                },
                label = "Domingo",
                state.startTimeSunday,
                state.endTimeSunday,
                onValueChangeStartTime = {},
                onValueChangeEndTime = {},

            )



        }
    }
}