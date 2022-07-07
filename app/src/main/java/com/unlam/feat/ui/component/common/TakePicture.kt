package com.unlam.feat.ui.component.common

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.unlam.feat.R
import com.unlam.feat.ui.component.FeatCircularProgress
import com.unlam.feat.ui.theme.PurpleLight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
 fun TakePicture() {
    val context = LocalContext.current
    val bottomSheetModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val image: String? = ""

    var isCameraSelected = false
    var imageUri: Uri? = null
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        bitmap.value = null
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { btm: Bitmap? ->
        bitmap.value = btm
        imageUri = null
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (isCameraSelected) {
                cameraLauncher.launch()
            } else {
                galleryLauncher.launch("image/*")
            }
            coroutineScope.launch {
                bottomSheetModalState.hide()
            }
        } else {
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }
    var imageUrl by remember { mutableStateOf<Uri?>(null) }



    ModalBottomSheetLayout(
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colors.primary.copy(0.08f))
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Add Photo!",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        color = MaterialTheme.colors.primary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                            .background(MaterialTheme.colors.primary)
                    )
                    Text(
                        text = "Take Photo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                when (PackageManager.PERMISSION_GRANTED) {
                                    ContextCompat.checkSelfPermission(
                                        context, Manifest.permission.CAMERA
                                    ) -> {
                                        cameraLauncher.launch()
                                        coroutineScope.launch {
                                            bottomSheetModalState.hide()
                                        }
                                    }
                                    else -> {
                                        isCameraSelected = true
                                        permissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                }
                            }
                            .padding(15.dp),
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                    Divider(
                        modifier = Modifier
                            .height(0.5.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray)
                    )
                    Text(
                        text = "Choose from Gallery",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                when (PackageManager.PERMISSION_GRANTED) {
                                    ContextCompat.checkSelfPermission(
                                        context, Manifest.permission.READ_EXTERNAL_STORAGE
                                    ) -> {
                                        galleryLauncher.launch("image/*")
                                        coroutineScope.launch {
                                            bottomSheetModalState.hide()
                                        }
                                    }
                                    else -> {
                                        isCameraSelected = false
                                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                    }
                                }
                            }
                            .padding(15.dp),
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                    Divider(
                        modifier = Modifier
                            .height(0.5.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray)
                    )
                    Text(
                        text = "Cancel",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                coroutineScope.launch {
                                    bottomSheetModalState.hide()
                                }
                            }
                            .padding(15.dp),
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
        },
        sheetState = bottomSheetModalState,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
    ) {if (bitmap.value != null) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .border(3.dp, PurpleLight, RoundedCornerShape(50))
                .size(200.dp),
            bitmap = bitmap.value!!.asImageBitmap(),
            contentDescription = "Gallery Image",
            contentScale = ContentScale.Crop
        )
    } else {
        if(image!!.isNotEmpty()){
            SubcomposeAsyncImage(
                model = image,
                loading= {
                    FeatCircularProgress()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .border(3.dp, PurpleLight, RoundedCornerShape(50))
                    .size(200.dp),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }else{
            Image(
                painter = painterResource(id = (R.drawable.ic_launcher_foreground)),
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .border(3.dp, PurpleLight, RoundedCornerShape(50))
                    .size(200.dp),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
    }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (!bottomSheetModalState.isVisible) {
                            bottomSheetModalState.show()
                        } else {
                            bottomSheetModalState.hide()
                        }
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Take Picture",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }


//    imageUri?.let {
//        if (!isCameraSelected) {
//            bitmap = if (Build.VERSION.SDK_INT < 28) {
//                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
//            } else {
//                val source = ImageDecoder.createSource(context.contentResolver, it)
//                ImageDecoder.decodeBitmap(source)
//            }
//        }
//
//        bitmap?.let { btm ->
//            Image(
//                bitmap = btm.asImageBitmap(),
//                contentDescription = "Image",
//                alignment = Alignment.TopCenter,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(0.45f)
//                    .padding(top = 10.dp),
//                contentScale = ContentScale.Fit
//            )
//        }
//    }
//
//    bitmap?.let { btm ->
//        Image(
//            bitmap = btm.asImageBitmap(),
//            contentDescription = "Image",
//            alignment = Alignment.TopCenter,
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(0.45f)
//                .padding(top = 10.dp),
//            contentScale = ContentScale.Fit
//        )
//    }
}


