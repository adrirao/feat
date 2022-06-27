package com.unlam.feat.ui.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng

fun howToGet(latLong: LatLng, context: Context) {
    val intentUriNavigation = Uri.parse(
        "google.navigation:q=${latLong.latitude},${latLong.longitude}"
    )
    try {
        Intent(Intent.ACTION_VIEW, intentUriNavigation).apply {
            setPackage("com.google.android.apps.maps")
            ContextCompat.startActivity(context, this, null)
        }
    } catch (ignored: ActivityNotFoundException) {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("market://details?id=com.google.android.apps.maps")
        ).apply {
            ContextCompat.startActivity(context, this, null)
        }

    }
}