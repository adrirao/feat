package com.unlam.feat.ui.component


import android.annotation.SuppressLint
import android.os.Looper
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.unlam.feat.R



@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FeatMap(
    setLocation: (LatLng) -> Unit,
) {

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

    gpsSensorManage.requestLocationUpdates(
        locationRequest,
        locationCallback,
        Looper.getMainLooper()
    )

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





