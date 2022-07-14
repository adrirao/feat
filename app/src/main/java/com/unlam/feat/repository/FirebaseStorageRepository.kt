package com.unlam.feat.repository

import android.graphics.Bitmap
import android.net.Uri

interface FirebaseStorageRepository {
    fun putFile(image: Bitmap, uId: String)
    fun getFile(uId: String,isSuccess: (Uri) -> Unit)
    suspend fun getUris(uIds:List<String>,isSuccess: (List<Uri>) -> Unit)
}