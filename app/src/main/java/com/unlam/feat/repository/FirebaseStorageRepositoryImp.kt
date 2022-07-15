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
        image.compress(Bitmap.CompressFormat.JPEG, 5, baos)
        val data = baos.toByteArray()
        val ref = firebaseStorage.getReference("images/${uId}.jpeg")
        ref.putBytes(data)
    }

//     override suspend fun getUris(uIds:List<ResponseUids>,isSuccess: (List<String>) -> Unit){
//        val uris:MutableList<String> = mutableListOf()
//        firebaseStorage.getReference("images/").listAll().await().also { listUriResult ->
//            uIds.forEach { uId ->
//                listUriResult.items.map { ref -> if(ref.name.contains(uId.uId)) ref.downloadUrl.await().also { uri -> uris.add(uri.toString()) }}
//            }
//            isSuccess(uris.toList())
//        }
//    }

    override fun getFile(uId: String, isSuccess: (Uri) -> Unit) {
        val ref = firebaseStorage.getReference("images/${uId}.jpeg")
        ref.downloadUrl.addOnSuccessListener {
            isSuccess(it)
        }.addOnFailureListener {
            isSuccess(Uri.EMPTY)
        }
    }
}