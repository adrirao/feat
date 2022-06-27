package com.unlam.feat.util

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson

fun <T, Y>print(request: T, response: Y) {
    val TAG = "FeatDebugger"
    val gson = Gson()
    Log.e(TAG, "::::::::::::::::::: INIT LOG :::::::::::::::::::")
    Log.e(TAG, "Response => ${gson.toJson(response)}")
    Log.e(TAG, "Request => ${request.toString()}")
    Log.e(TAG, "::::::::::::::::::: END LOG :::::::::::::::::::")
}

@Composable
fun getAddress(latLng: LatLng): Address {
    val geocoder = Geocoder(LocalContext.current)
    val list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
    return list[0]
}