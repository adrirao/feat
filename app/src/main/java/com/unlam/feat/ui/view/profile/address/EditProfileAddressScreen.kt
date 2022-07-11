import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GpsFixed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.unlam.feat.R
import com.unlam.feat.ui.component.FeatForm
import com.unlam.feat.ui.component.FeatOutlinedTextField
import com.unlam.feat.ui.component.common.PermissionFlow
import com.unlam.feat.ui.theme.PurpleLight
import com.unlam.feat.ui.view.profile.address.EditProfileAddressEvent
import com.unlam.feat.ui.view.profile.address.EditProfileAddressState
import com.unlam.feat.ui.view.profile.personal_information.EditProfilePersonalInformationEvent

@OptIn(ExperimentalPermissionsApi::class)
@Composable
 fun EditProfileAddressScreen(
    state: EditProfileAddressState,
    onEvent: (EditProfileAddressEvent) -> Unit,
    openMap: () -> Unit,
    onValueChange: (EditProfilePersonalInformationEvent) -> Unit,
    goToProfile: () -> Unit
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
        title = "Direcciónes:"
    ) {
        Column {
            FeatOutlinedTextField(
                text = state.addressAlias,
                textLabel = stringResource(R.string.alias),
                onValueChange = {
//                    onEvent(
//                        ConfigProfileEvents.onValueChange(
//                            TypeValueChange.OnValueChangeAddressAlias,
//                            it
//                        )
//                    )
                },
//                error = when (state.addressAlias) {
//                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
//                    else -> ""
//                }
            )
            FeatOutlinedTextField(
                text = state.addressAlias,
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
//                error = when (state.addressError) {
//                    ConfigProfileState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
//                    else -> ""
//                }
            )
        }
    }
}