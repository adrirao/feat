package com.unlam.feat.ui.view.config_profile

import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.GpsFixed
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.unlam.feat.R
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.component.common.PermissionFlow
import com.unlam.feat.ui.component.common.TakePicture
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.event.EventEvents
import com.unlam.feat.ui.view.profile.ProfileEvent
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalPagerApi::class, ExperimentalPermissionsApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun ConfigProfileScreen(
    state: ConfigProfileState,
    onEvent: (ConfigProfileEvents) -> Unit,
    onClick: (ConfigProfileEvents) -> Unit,
) {
    var imageUrl by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val image = ImageRequest.Builder(LocalContext.current)
        .data(state.image)
        .build()
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
    var isCameraSelected = false

    var isPermissionRequested by rememberSaveable { mutableStateOf(false) }

    var openMap by remember {
        mutableStateOf(false)
    }

    var idSport by remember {
        mutableStateOf(0)
    }

    val pagerState = rememberPagerState()

//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        imageUrl = uri
//    }



//    val cameraLauncher: ManagedActivityResultLauncher<Void?, Bitmap?> =
//        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
//            bitmap.value = it
//            onEvent(ConfigProfileEvents.UploadImage(it!!))
//
//        }
    val bottomSheetModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUrl = uri
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) {
        bitmap.value = it
        onEvent(ConfigProfileEvents.UploadImage(it!!))
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (isCameraSelected) {
                cameraLauncher.launch()
            } else {
                galleryLauncher.launch("image/*")
            }
            coroutineScope.launch {
                bottomSheetModalState.hide()
            }
        } else {
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }





    if (state.isSuccessSubmitData) {
        InfoDialog(
            title = "Creado con exito",
            desc = "Su perfil se configuro con exito",
            onDismiss = { onClick(ConfigProfileEvents.onClick(TypeClick.GoToHome)) })
    }

    if (state.isErrorSubmitData) {
        ErrorDialog(
            title = "Error",
            desc = state.error,
            enabledCancelButton = false,
            onDismiss = {
                onEvent(ConfigProfileEvents.SingOutUser)
                onClick(ConfigProfileEvents.onClick(TypeClick.GoToLogin))
                onEvent(ConfigProfileEvents.onClick(TypeClick.DismissDialog))
            }
        )
    }



    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoadingSubmitData) {
            FeatCircularProgress()
        } else if (state.takePhoto) {
            Box {

                imageUrl?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        LaunchedEffect(key1 = imageUrl) {
                            bitmap.value =
                                MediaStore.Images.Media.getBitmap(
                                    context.contentResolver,
                                    it
                                )
                            val source =
                                ImageDecoder.createSource(context.contentResolver, it)
                            bitmap.value = ImageDecoder.decodeBitmap(source)
                            onEvent(ConfigProfileEvents.UploadImage(bitmap.value!!))
                        }
                    } else {
                        LaunchedEffect(key1 = imageUrl) {
                            val source = ImageDecoder.createSource(context.contentResolver, it)
                            bitmap.value = ImageDecoder.decodeBitmap(source)

                            onEvent(ConfigProfileEvents.UploadImage(bitmap.value!!))
                        }
                    }
                }
                FeatHeader(text = "Por favor, elige una foto para tu perfil.")

                if (bitmap.value != null) {
                    Image(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .border(3.dp, PurpleLight, RoundedCornerShape(50))
                            .size(200.dp),
                        bitmap = bitmap.value!!.asImageBitmap(),
                        contentDescription = "Gallery Image",
                        contentScale = ContentScale.Crop
                    )
                } else {
                    if(state.image!!.isNotEmpty()){
                        SubcomposeAsyncImage(
                            model = state.image,
                            loading= {
                                FeatCircularProgress()
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .border(3.dp, PurpleLight, RoundedCornerShape(50))
                                .size(200.dp),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }else{
                        Image(
                            painter = painterResource(id = (R.drawable.ic_launcher_foreground)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .border(3.dp, PurpleLight, RoundedCornerShape(50))
                                .size(200.dp),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Row(){
                    FeatOutlinedButton(textContent = "Galeria") {
                        galleryLauncher.launch("image/*")
                    }
                    FeatOutlinedButton(textContent = "Camara") {
                        if (isPermissionRequested && cameraPermission.hasPermission) {
                            cameraLauncher.launch()
                            isPermissionRequested = false
                        }
                        if (!cameraPermission.hasPermission) {
                            cameraPermission.launchPermissionRequest()
                            isPermissionRequested = true
                        } else
                            cameraLauncher.launch()
                    }

                }
            }
        } else {
            if (idSport == 0) {
                HorizontalPager(
                    count = 5,
                    state = pagerState
                ) { position ->
                    when (position) {
                        0 -> PageOne(state, onEvent)
                        1 -> PageTwo(state, onEvent, openMap = { openMap = true })
                        2 -> PageThree(state, onEvent)
                        3 -> PageFour(state, onEvent)
                        4 -> PageFive(state, onEvent) { idSport = it }
                    }
                }
                HorizontalPagerIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    pagerState = pagerState
                )
            } else if (idSport != 0) {
                if (state.isLoading) {
                    FeatCircularProgress()
                } else {
                    when (idSport) {
                        1 -> {
                            SportDataSoccer(state, onEvent) { idSport = it }
                        }
                        2 -> {
                            SportDataPadel(state, onEvent) { idSport = it }
                        }
                        3 -> {
                            SportDataTennis(state, onEvent) { idSport = it }
                        }
                        4 -> {
                            SportDataBasketball(state, onEvent) { idSport = it }
                        }
                        5 -> {
                            SportDataRecreationalActivity(state, onEvent) { idSport = it }
                        }
                    }
                }

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
                    },
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PageTwo(
    state: ConfigProfileState,
    onEvent: (ConfigProfileEvents) -> Unit,
    openMap: () -> Unit
) {
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

    PermissionFlow(
        permissionState,
        shouldShowRationale,
        exactLocationPermission,
        locationPermissionDenied
    )

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
                            TypeValueChange.OnValueChangeAddressAlias,
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
                            val allPermissionsRevoked =
                                permissionState.permissions.size == permissionState.revokedPermissions.size
                            if (permissionState.allPermissionsGranted) {
                                openMap()
                            } else if (!permissionState.permissionRequested) {
                                permissionState.launchMultiplePermissionRequest()
                            } else if (!allPermissionsRevoked) {
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
                else -> ""
            },
            fontSize = 15.sp, color = MaterialTheme.colors.error
        )

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
            textLabel = "Distancia busqueda",
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
        FeatCheckbox(
            checked = state.notifications,
            label = "Notificaciones",
            onCheckedChange = {
                onEvent(
                    ConfigProfileEvents.onValueChange(
                        TypeValueChange.OnValueChangeNotifications, "", valueBooleanOpt = it
                    )
                )
            },
        )

    }
}

@Composable
private fun PageFive(
    state: ConfigProfileState,
    onEvent: (ConfigProfileEvents) -> Unit,
    onClick: (Int) -> Unit
) {
    FeatForm(
        modifier = Modifier.padding(10.dp),
        title = "Deportes:",
        page = "5/5"
    ) {
        Column {


            if (state.sportsList.isNotEmpty()) {
                state.sportsList.forEach { sportGeneric ->
                    FeatSportCard(
                        onClickCard = {
                            onClick(sportGeneric.id)
                            onEvent(
                                ConfigProfileEvents.onValueChange(
                                    TypeValueChange.OnValueChangeSelectSport,
                                    sportGeneric.id.toString()
                                )
                            )
                        },
                        sport = sportGeneric.description,
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        idSport = sportGeneric.id,
                        isSelected = when (sportGeneric.id) {
                            1 -> state.idSoccer != null
                            2 -> state.idPadel != null
                            3 -> state.idTennis != null
                            4 -> state.idBasketball != null
                            5 -> state.idRecreationalActivity != null
                            else -> false
                        }
                    )
                }
            }

            if (
                (!state.idBasketball.isNullOrEmpty() ||
                        !state.idSoccer.isNullOrEmpty() ||
                        !state.idPadel.isNullOrEmpty() ||
                        !state.idTennis.isNullOrEmpty() ||
                        !state.idRecreationalActivity.isNullOrEmpty())
                &&
                (state.saturdayIsChecked ||
                        state.mondayIsChecked ||
                        state.wednesdayIsChecked ||
                        state.thursdayIsChecked ||
                        state.fridayIsChecked ||
                        state.sundayIsChecked ||
                        state.tuesdayIsChecked)
            ) {
                FeatOutlinedButton(
                    contentColor = GreenColor,
                    backgroundColor = GreenColor90,
                    textColor = PurpleDark,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 10.dp),
                    textContent = "Guardar",
                    onClick = { onEvent(ConfigProfileEvents.onClick(TypeClick.Submit)) }
                )
            }
        }
    }
}



@Composable
private fun SportDataSoccer(
    state: ConfigProfileState,
    onEvent: (ConfigProfileEvents) -> Unit,
    onClick: (Int) -> Unit
) {
    onEvent(
        ConfigProfileEvents.onValueChange(
            TypeValueChange.OnValueChangeIdSoccer, "", "1"
        )
    )

    FeatForm(
        modifier = Modifier
            .padding(10.dp, 30.dp),
        title = "Preferencias del deporte",
        page = ""
    ) {

        Column {

            val optionsPosition: MutableList<String> = mutableListOf<String>()
            var descriptionValuePosition = ""
            state.positionList.forEach { positionList ->
                optionsPosition.add(positionList.description)
                if (positionList.id == state.positionIdSoccer) {
                    descriptionValuePosition = positionList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Posición",
                options = optionsPosition,
                selectedText = { positionText ->
                    state.positionList.forEach { position ->
                        if (position.description == positionText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangePositionSoccer, position.id.toString()
                            )
                        )
                    }
                },
                error = when (state.positionIdSoccerError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValuePosition
            )


            val optionsLevel: MutableList<String> = mutableListOf<String>()
            var descriptionValueLevel = ""
            state.levelList.forEach { levelList ->
                optionsLevel.add(levelList.description)
                if (levelList.id == state.levelIdSoccer) {
                    descriptionValueLevel = levelList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Nivel",
                options = optionsLevel,
                selectedText = { levelText ->
                    state.levelList.forEach { level ->
                        if (level.description == levelText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangeLevelSoccer, level.id.toString()
                            )
                        )
                    }
                },
                error = when (state.levelIdSoccerError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValueLevel
            )


            val optionsValuation: MutableList<String> = mutableListOf<String>()
            var descriptionValueValuation = ""
            state.valuationList.forEach { valuationList ->
                optionsValuation.add(valuationList.description)
                if (valuationList.id == state.valuationIdSoccer) {
                    descriptionValueValuation = valuationList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Grado de interés",
                options = optionsValuation,
                selectedText = { valuationText ->
                    state.valuationList.forEach { valuation ->
                        if (valuation.description == valuationText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangeValuationSoccer,
                                valuation.id.toString()
                            )
                        )
                    }
                },
                error = when (state.valuationIdSoccerError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValueValuation
            )

            FeatOutlinedTextField(
                textLabel = "Aptitudes",
                text = state.abilitiesSoccer,
                onValueChange = { abilities ->
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeAbilitiesSoccer, abilities
                        )
                    )
                },
                maxLines = 4,
                error = when (state.abilitiesSoccerError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                }
            )

            FeatSpacerMedium()


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FeatOutlinedButton(
                    textContent = "Cancelar",
                    contentColor = RedColor,
                    backgroundColor = RedColor20,
                    textColor = RedColor
                ) {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeIdSoccer, "", null
                        )
                    )
                    onClick(0)
                }

                FeatOutlinedButton(
                    textContent = "Aceptar",
                    contentColor = GreenColor,
                    backgroundColor = GreenColor90,
                    textColor = PurpleDark
                ) {
                    onEvent(
                        ConfigProfileEvents.onClick(
                            TypeClick.SaveSoccerData
                        )
                    )
                    if (state.valuationIdSoccer != null && state.levelIdSoccer != null &&
                        state.abilitiesSoccer != "" && state.positionIdSoccer != null
                    ) {
                        onClick(0)
                    }
                }


            }

        }
    }
}


@Composable
private fun SportDataBasketball(
    state: ConfigProfileState,
    onEvent: (ConfigProfileEvents) -> Unit,
    onClick: (Int) -> Unit
) {
    onEvent(
        ConfigProfileEvents.onValueChange(
            TypeValueChange.OnValueChangeIdBasketball, "", "4"
        )
    )

    FeatForm(
        modifier = Modifier
            .padding(10.dp, 30.dp),
        title = "Preferencias del deporte",
        page = ""
    ) {

        Column {

            val optionsPosition: MutableList<String> = mutableListOf<String>()
            var descriptionValuePosition = ""
            state.positionList.forEach { positionList ->
                optionsPosition.add(positionList.description)
                if (positionList.id == state.positionIdBasketball) {
                    descriptionValuePosition = positionList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Posición",
                options = optionsPosition,
                selectedText = { positionText ->
                    state.positionList.forEach { position ->
                        if (position.description == positionText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangePositionBasketball,
                                position.id.toString()
                            )
                        )
                    }
                },
                error = when (state.positionIdBasketballError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValuePosition
            )


            val optionsLevel: MutableList<String> = mutableListOf<String>()
            var descriptionValueLevel = ""
            state.levelList.forEach { levelList ->
                optionsLevel.add(levelList.description)
                if (levelList.id == state.levelIdBasketball) {
                    descriptionValueLevel = levelList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Nivel",
                options = optionsLevel,
                selectedText = { levelText ->
                    state.levelList.forEach { level ->
                        if (level.description == levelText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangeLevelBasketball, level.id.toString()
                            )
                        )
                    }
                },
                error = when (state.levelIdBasketballError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValueLevel
            )


            val optionsValuation: MutableList<String> = mutableListOf<String>()
            var descriptionValueValuation = ""
            state.valuationList.forEach { valuationList ->
                optionsValuation.add(valuationList.description)
                if (valuationList.id == state.valuationIdBasketball) {
                    descriptionValueValuation = valuationList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Grado de interés",
                options = optionsValuation,
                selectedText = { valuationText ->
                    state.valuationList.forEach { valuation ->
                        if (valuation.description == valuationText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangeValuationBasketball,
                                valuation.id.toString()
                            )
                        )
                    }
                },
                error = when (state.valuationIdBasketballError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValueValuation
            )

            FeatOutlinedTextField(
                textLabel = "Aptitudes",
                text = state.abilitiesBasketball,
                onValueChange = { abilities ->
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeAbilitiesBasketball, abilities
                        )
                    )
                },
                maxLines = 4,
                error = when (state.abilitiesBasketballError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                }
            )

            FeatSpacerMedium()


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FeatOutlinedButton(
                    textContent = "Cancelar",
                    contentColor = RedColor,
                    backgroundColor = RedColor20,
                    textColor = RedColor
                ) {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeIdBasketball, "", null
                        )
                    )
                    onClick(0)
                }

                FeatOutlinedButton(
                    textContent = "Aceptar",
                    contentColor = GreenColor,
                    backgroundColor = GreenColor90,
                    textColor = PurpleDark
                ) {
                    onEvent(
                        ConfigProfileEvents.onClick(
                            TypeClick.SaveBasketballData
                        )
                    )
                    if (state.valuationIdBasketball != null && state.levelIdBasketball != null &&
                        state.abilitiesBasketball != "" && state.positionIdBasketball != null
                    ) {
                        onClick(0)
                    }
                }


            }

        }
    }
}

@Composable
private fun SportDataPadel(
    state: ConfigProfileState,
    onEvent: (ConfigProfileEvents) -> Unit,
    onClick: (Int) -> Unit
) {
    onEvent(
        ConfigProfileEvents.onValueChange(
            TypeValueChange.OnValueChangeIdPadel, "", "2"
        )
    )

    FeatForm(
        modifier = Modifier
            .padding(10.dp, 30.dp),
        title = "Preferencias del deporte",
        page = ""
    ) {

        Column {

            val optionsPosition: MutableList<String> = mutableListOf<String>()
            var descriptionValuePosition = ""
            state.positionList.forEach { positionList ->
                optionsPosition.add(positionList.description)
                if (positionList.id == state.positionIdPadel) {
                    descriptionValuePosition = positionList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Posición",
                options = optionsPosition,
                selectedText = { positionText ->
                    state.positionList.forEach { position ->
                        if (position.description == positionText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangePositionPadel, position.id.toString()
                            )
                        )
                    }
                },
                error = when (state.positionIdPadelError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValuePosition
            )


            val optionsLevel: MutableList<String> = mutableListOf<String>()
            var descriptionValueLevel = ""
            state.levelList.forEach { levelList ->
                optionsLevel.add(levelList.description)
                if (levelList.id == state.levelIdPadel) {
                    descriptionValueLevel = levelList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Nivel",
                options = optionsLevel,
                selectedText = { levelText ->
                    state.levelList.forEach { level ->
                        if (level.description == levelText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangeLevelPadel, level.id.toString()
                            )
                        )
                    }
                },
                error = when (state.levelIdPadelError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValueLevel
            )


            val optionsValuation: MutableList<String> = mutableListOf<String>()
            var descriptionValueValuation = ""
            state.valuationList.forEach { valuationList ->
                optionsValuation.add(valuationList.description)
                if (valuationList.id == state.valuationIdPadel) {
                    descriptionValueValuation = valuationList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Grado de interés",
                options = optionsValuation,
                selectedText = { valuationText ->
                    state.valuationList.forEach { valuation ->
                        if (valuation.description == valuationText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangeValuationPadel,
                                valuation.id.toString()
                            )
                        )
                    }
                },
                error = when (state.valuationIdPadelError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValueValuation
            )

            FeatOutlinedTextField(
                textLabel = "Aptitudes",
                text = state.abilitiesPadel,
                onValueChange = { abilities ->
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeAbilitiesPadel, abilities
                        )
                    )
                },
                maxLines = 4,
                error = when (state.abilitiesPadelError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                }
            )

            FeatSpacerMedium()


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FeatOutlinedButton(
                    textContent = "Cancelar",
                    contentColor = RedColor,
                    backgroundColor = RedColor20,
                    textColor = RedColor
                ) {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeIdPadel, "", null
                        )
                    )
                    onClick(0)
                }

                FeatOutlinedButton(
                    textContent = "Aceptar",
                    contentColor = GreenColor,
                    backgroundColor = GreenColor90,
                    textColor = PurpleDark
                ) {
                    onEvent(
                        ConfigProfileEvents.onClick(
                            TypeClick.SavePadelData
                        )
                    )
                    if (state.valuationIdPadel != null && state.levelIdPadel != null &&
                        state.abilitiesPadel != "" && state.positionIdPadel != null
                    ) {
                        onClick(0)
                    }
                }


            }

        }
    }
}

@Composable
private fun SportDataTennis(
    state: ConfigProfileState,
    onEvent: (ConfigProfileEvents) -> Unit,
    onClick: (Int) -> Unit
) {
    onEvent(
        ConfigProfileEvents.onValueChange(
            TypeValueChange.OnValueChangeIdTennis, "", "3"
        )
    )

    FeatForm(
        modifier = Modifier
            .padding(10.dp, 30.dp),
        title = "Preferencias del deporte",
        page = ""
    ) {

        Column {

            val optionsPosition: MutableList<String> = mutableListOf<String>()
            var descriptionValuePosition = ""
            state.positionList.forEach { positionList ->
                optionsPosition.add(positionList.description)
                if (positionList.id == state.positionIdTennis) {
                    descriptionValuePosition = positionList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Posición",
                options = optionsPosition,
                selectedText = { positionText ->
                    state.positionList.forEach { position ->
                        if (position.description == positionText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangePositionTennis, position.id.toString()
                            )
                        )
                    }
                },
                error = when (state.positionIdTennisError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValuePosition
            )


            val optionsLevel: MutableList<String> = mutableListOf<String>()
            var descriptionValueLevel = ""
            state.levelList.forEach { levelList ->
                optionsLevel.add(levelList.description)
                if (levelList.id == state.levelIdTennis) {
                    descriptionValueLevel = levelList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Nivel",
                options = optionsLevel,
                selectedText = { levelText ->
                    state.levelList.forEach { level ->
                        if (level.description == levelText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangeLevelTennis, level.id.toString()
                            )
                        )
                    }
                },
                error = when (state.levelIdTennisError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValueLevel
            )


            val optionsValuation: MutableList<String> = mutableListOf<String>()
            var descriptionValueValuation = ""
            state.valuationList.forEach { valuationList ->
                optionsValuation.add(valuationList.description)
                if (valuationList.id == state.valuationIdTennis) {
                    descriptionValueValuation = valuationList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Grado de interés",
                options = optionsValuation,
                selectedText = { valuationText ->
                    state.valuationList.forEach { valuation ->
                        if (valuation.description == valuationText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangeValuationTennis,
                                valuation.id.toString()
                            )
                        )
                    }
                },
                error = when (state.valuationIdTennisError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValueValuation
            )

            FeatOutlinedTextField(
                textLabel = "Aptitudes",
                text = state.abilitiesTennis,
                onValueChange = { abilities ->
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeAbilitiesTennis, abilities
                        )
                    )
                },
                maxLines = 4,
                error = when (state.abilitiesTennisError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                }
            )

            FeatSpacerMedium()


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FeatOutlinedButton(
                    textContent = "Cancelar",
                    contentColor = RedColor,
                    backgroundColor = RedColor20,
                    textColor = RedColor
                ) {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeIdTennis, "", null
                        )
                    )
                    onClick(0)
                }

                FeatOutlinedButton(
                    textContent = "Aceptar",
                    contentColor = GreenColor,
                    backgroundColor = GreenColor90,
                    textColor = PurpleDark
                ) {
                    onEvent(
                        ConfigProfileEvents.onClick(
                            TypeClick.SaveTennisData
                        )
                    )
                    if (state.valuationIdTennis != null && state.levelIdTennis != null &&
                        state.abilitiesTennis != "" && state.positionIdTennis != null
                    ) {
                        onClick(0)
                    }
                }


            }

        }
    }
}

@Composable
private fun SportDataRecreationalActivity(
    state: ConfigProfileState,
    onEvent: (ConfigProfileEvents) -> Unit,
    onClick: (Int) -> Unit
) {
    onEvent(
        ConfigProfileEvents.onValueChange(
            TypeValueChange.OnValueChangeIdRecreationalActivity, "", "5"
        )
    )

    FeatForm(
        modifier = Modifier
            .padding(10.dp, 30.dp),
        title = "Preferencias del deporte",
        page = ""
    ) {

        Column {

            val optionsPosition: MutableList<String> = mutableListOf<String>()
            var descriptionValuePosition = ""
            state.positionList.forEach { positionList ->
                optionsPosition.add(positionList.description)
                if (positionList.id == state.positionIdRecreationalActivity) {
                    descriptionValuePosition = positionList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Posición",
                options = optionsPosition,
                selectedText = { positionText ->
                    state.positionList.forEach { position ->
                        if (position.description == positionText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangePositionRecreationalActivity,
                                position.id.toString()
                            )
                        )
                    }
                },
                error = when (state.positionIdRecreationalActivityError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValuePosition
            )


            val optionsLevel: MutableList<String> = mutableListOf<String>()
            var descriptionValueLevel = ""
            state.levelList.forEach { levelList ->
                optionsLevel.add(levelList.description)
                if (levelList.id == state.levelIdRecreationalActivity) {
                    descriptionValueLevel = levelList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Nivel",
                options = optionsLevel,
                selectedText = { levelText ->
                    state.levelList.forEach { level ->
                        if (level.description == levelText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangeLevelRecreationalActivity,
                                level.id.toString()
                            )
                        )
                    }
                },
                error = when (state.levelIdRecreationalActivityError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValueLevel
            )


            val optionsValuation: MutableList<String> = mutableListOf<String>()
            var descriptionValueValuation = ""
            state.valuationList.forEach { valuationList ->
                optionsValuation.add(valuationList.description)
                if (valuationList.id == state.valuationIdRecreationalActivity) {
                    descriptionValueValuation = valuationList.description
                }
            }
            FeatOutlinedDropDown(
                label = "Grado de interés",
                options = optionsValuation,
                selectedText = { valuationText ->
                    state.valuationList.forEach { valuation ->
                        if (valuation.description == valuationText) onEvent(
                            ConfigProfileEvents.onValueChange(
                                TypeValueChange.OnValueChangeValuationRecreationalActivity,
                                valuation.id.toString()
                            )
                        )
                    }
                },
                error = when (state.valuationIdRecreationalActivityError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                initialValue = descriptionValueValuation
            )

            FeatOutlinedTextField(
                textLabel = "Aptitudes",
                text = state.abilitiesRecreationalActivity,
                onValueChange = { abilities ->
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeAbilitiesRecreationalActivity, abilities
                        )
                    )
                },
                maxLines = 4,
                error = when (state.abilitiesRecreationalActivityError) {
                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                }
            )

            FeatSpacerMedium()


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FeatOutlinedButton(
                    textContent = "Cancelar",
                    contentColor = RedColor,
                    backgroundColor = RedColor20,
                    textColor = RedColor
                ) {
                    onEvent(
                        ConfigProfileEvents.onValueChange(
                            TypeValueChange.OnValueChangeIdRecreationalActivity, "", null
                        )
                    )
                    onClick(0)
                }

                FeatOutlinedButton(
                    textContent = "Aceptar",
                    contentColor = GreenColor,
                    backgroundColor = GreenColor90,
                    textColor = PurpleDark
                ) {
                    onEvent(
                        ConfigProfileEvents.onClick(
                            TypeClick.SaveRecreationalActivityData
                        )
                    )
                    if (state.valuationIdRecreationalActivity != null && state.levelIdRecreationalActivity != null &&
                        state.abilitiesRecreationalActivity != "" && state.positionIdRecreationalActivity != null
                    ) {
                        onClick(0)
                    }
                }


            }

        }
    }
}
