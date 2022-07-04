package com.unlam.feat.util

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import retrofit2.Response

fun <T, Y> logging(request: T, response: Response<Y>) {
    val TAG = "FeatLog"
    val gson = Gson()
    Log.e(TAG, "::::::::::::::::::: INIT LOG :::::::::::::::::::")
    Log.e(TAG, "Response => ${gson.toJson(response.raw())}")
    Log.e(TAG, "Request => ${gson.toJson(request)}")
    Log.e(TAG, "::::::::::::::::::: END LOG :::::::::::::::::::")
}

fun <T, Y> loggingMult(request: List<T>, response: List<Y>) {
    val TAG = "FeatLog"
    val gson = Gson()
    Log.e(TAG, "::::::::::::::::::: INIT LOG :::::::::::::::::::")
    Log.e(TAG, "Response => ${gson.toJson(response)}")
    Log.e(TAG, "Request => ${gson.toJson(request)}")
    Log.e(TAG, "::::::::::::::::::: END LOG :::::::::::::::::::")
}

fun logging(message:String){
    val TAG = "FeatLog"
    val gson = Gson()
    Log.e(TAG, "::::::::::::::::::: INIT LOG :::::::::::::::::::")
    Log.e(TAG, "Message => ${gson.toJson(message)}")
    Log.e(TAG, "::::::::::::::::::: END LOG :::::::::::::::::::")
}

@Composable
fun getAddress(latLng: LatLng): Address {
    val geocoder = Geocoder(LocalContext.current)
    val list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
    return list[0]
}

fun Context.openAppSystemSettings() {
    startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", packageName, null)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    })
}

