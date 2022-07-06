package com.unlam.feat.ui.component.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.unlam.feat.ui.component.ErrorDialog
import com.unlam.feat.util.openAppSystemSettings


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionFlow(
    permissionState: MultiplePermissionsState,
    shouldShowRationale: MutableState<Boolean>,
    exactLocationPermission: MutableState<Boolean>,
    locationPermissionDenied: MutableState<Boolean>,
) {
    val context = LocalContext.current
        if (shouldShowRationale.value) {
        ErrorDialog(
            title = "Permisos de ubicacion necesarios",
            desc = "Se necesitan los permisos de ubicacion para acceder a su ubicacion actual",
            enabledCancelButton = false,
            textContentAccept = "ACEPTAR",
        ) {
            shouldShowRationale.value = false
            permissionState.launchMultiplePermissionRequest()
        }
    } else if (exactLocationPermission.value) {
        ErrorDialog(
            title = "Se necesitan permisos de ubicacion exacta",
            desc = "Se necesitan los permisos de ubicacion exacta para acceder a su ubicacion actual",
            enabledCancelButton = false,
            textContentAccept = "ACEPTAR",
        ) {
            exactLocationPermission.value = false
            permissionState.launchMultiplePermissionRequest()
        }
    } else if (locationPermissionDenied.value) {
        ErrorDialog(
            title = "Se denegaron los permisos de ubicacion",
            desc = "Se han denegado los permisos de ubicacion, por favor acceder a configuraciones de la applicacion y otorgar los permisos",
            enabledCancelButton = false,
            textContentAccept = "Abrir Configuración",
            onDismiss = {
                locationPermissionDenied.value = false
                context.openAppSystemSettings()
            }
        )
    }
}