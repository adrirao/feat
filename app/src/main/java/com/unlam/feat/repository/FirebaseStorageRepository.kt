package com.unlam.feat.repository

import android.graphics.Bitmap
import android.net.Uri
import com.unlam.feat.model.response.ResponseUids

interface FirebaseStorageRepository {
    fun putFile(image: Bitmap, uId: String)
    fun getFile(uId: String,isSuccess: (Uri) -> Unit)
    suspend fun getUris(uIds:List<ResponseUids>,isSuccess: (List<String>) -> Unit)
}