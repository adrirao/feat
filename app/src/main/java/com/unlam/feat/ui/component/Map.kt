package com.unlam.feat.ui.component

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.unlam.feat.R
import com.unlam.feat.util.getAddress
import com.unlam.feat.util.openAppSystemSettings


@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FeatMap(
    setLocation: (LatLng) -> Unit,
    failLocationPermissions: () -> Unit = {}
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val cameraPositionState = rememberCameraPositionState {}
    val context = LocalContext.current
    val enabled = remember {
        mutableStateOf(false)
    }
    val locationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    val gpsSensorManage =
        LocationServices.getFusedLocationProviderClient(context)

    val locationCallback =
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult?.locations.let {
                    if (it != null) {
                        var markerPosition = LatLng(it.last().latitude, it.last().longitude)
                        cameraPositionState.position =
                            CameraPosition.fromLatLngZoom(markerPosition, 16f)
                        gpsSensorManage.removeLocationUpdates(this).addOnCompleteListener {
                            enabled.value = true
                        }
                    }
                }
            }
        }

    val shouldShowRationale = remember {
        mutableStateOf(false)
    }
    val exactLocationPermission = remember {
        mutableStateOf(false)
    }
    val locationPermissionDenied = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {

        if (permissionState.allPermissionsGranted) {
            enabled.value = false
            gpsSensorManage.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } else {

            val allPermissionsRevoked = permissionState.permissions.size == permissionState.revokedPermissions.size

            if (!allPermissionsRevoked) {
                exactLocationPermission.value = true
            } else if (permissionState.shouldShowRationale) {
                shouldShowRationale.value = true
            }else if( allPermissionsRevoked && permissionState.permissionRequested){
                shouldShowRationale.value = true
            }
            else{
                locationPermissionDenied.value = true
            }
        }
    }

    if (shouldShowRationale.value) {
        ErrorDialog(
            title = "Permisos de ubicacion necesarios",
            desc = "Se necesitan los permisos de ubicacion para acceder a su ubicacion actual",
            enabledCancelButton = false,
            textContentAccept = "ACEPTAR",
        ) {
            shouldShowRationale.value = false
            permissionState.launchMultiplePermissionRequest()
            failLocationPermissions()
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
            failLocationPermissions()
        }
    } else if (locationPermissionDenied.value) {
        ErrorDialog(
            title = "Se denegaron los permisos de ubicacion",
            desc = "Se han denegado los permisos de ubicacion, por favor acceder a configuraciones de la applicacion y otorgar los permisos",
            enabledCancelButton = false,
            textContentAccept = "Abrir Configuración",
            onDismiss = {
                failLocationPermissions()
                locationPermissionDenied.value = false
                context.openAppSystemSettings()
            }
        )
    }



    if (enabled.value) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            IconButton(
                onClick = {
                    setLocation(cameraPositionState.position.target)
                },
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.logotipo),
                    contentDescription = "marker",
                )
            }
        }
    } else {
        FeatCircularProgress()
    }
}





