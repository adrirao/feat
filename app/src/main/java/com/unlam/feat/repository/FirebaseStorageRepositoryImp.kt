package com.unlam.feat.repository

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseStorageRepositoryImp
@Inject
constructor(
    private val firebaseStorage: FirebaseStorage
) : FirebaseStorageRepository {
    override fun putFile(image: Bitmap, uId: String) {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 60, baos)
        val data = baos.toByteArray()
        val ref = firebaseStorage.getReference("images/${uId}.jpeg")
        ref.putBytes(data)
    }

    override fun getFile(uId: String, isSuccess: (Uri) -> Unit) {
        val ref = firebaseStorage.getReference("images/${uId}.jpeg")
        ref.downloadUrl.addOnSuccessListener {
            isSuccess(it)
        }.addOnFailureListener {
            isSuccess(Uri.EMPTY)
        }
    }
}