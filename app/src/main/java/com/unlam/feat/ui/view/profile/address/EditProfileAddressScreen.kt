import android.Manifest
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
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.unlam.feat.R
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.component.common.PermissionFlow
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.PurpleLight
import com.unlam.feat.ui.theme.YellowColor
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.profile.address.EditProfileAddressEvent
import com.unlam.feat.ui.view.profile.address.EditProfileAddressState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EditProfileAddressScreen(
    state: EditProfileAddressState,
    onEvent: (EditProfileAddressEvent) -> Unit,
    onClick : () -> Unit
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

    var openMap by remember {
        mutableStateOf(false)
    }

    PermissionFlow(
        permissionState,
        shouldShowRationale,
        exactLocationPermission,
        locationPermissionDenied
    )

    if(!openMap){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            FeatForm(
                modifier = Modifier.padding(10.dp),
                title = "Direcciónes:",
                page = ""
            ) {
                Column {
                    FeatOutlinedTextField(
                        text = state.addressAlias,
                        textLabel = stringResource(R.string.alias),
                        onValueChange = {
                            onEvent(
                                EditProfileAddressEvent.EnteredAddressAlias(it)
                            )
                        },
                        error = when (state.errorAlias) {
                            EditProfileAddressState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
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
                                        openMap = true
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
                        error = when (state.errorAddress) {
                            EditProfileAddressState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                            else -> ""
                        }
                    )
                    FeatSpacerSmall()
                    FeatOutlinedButton(
                        modifier = Modifier.align(Alignment.End),
                        textContent = "Modificar",
                        contentColor = YellowColor,
                        backgroundColor = YellowColor,
                        textColor = PurpleDark,
                        onClick = {
                            onClick()
                        }
                    )
                }
            }
        }
    }

    if (openMap) {
        FeatMap(
            setLocation = {
                onEvent(
                    EditProfileAddressEvent.onValueChange(
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
            EditProfileAddressEvent.onValueChange(
                TypeValueChange.OnValueChangeAddress,
                address[0].getAddressLine(0)
            )
        )
    }
}