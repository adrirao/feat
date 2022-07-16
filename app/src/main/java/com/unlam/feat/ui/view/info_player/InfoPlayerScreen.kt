package com.unlam.feat.ui.view.info_player

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.unlam.feat.R
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.view.profile.ProfileEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun InfoPlayerScreen(
    state: InfoPlayerState,
) {
    var imageUrl by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val image = ImageRequest.Builder(LocalContext.current)
        .data(state.image)
        .build()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUrl = uri
    }
    val person = state.person!!
    val qualifications = state.qualifications!!

    FeatContent {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    imageUrl?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            LaunchedEffect(key1 = imageUrl) {
                                bitmap.value =
                                    MediaStore.Images.Media.getBitmap(
                                        context.contentResolver,
                                        it
                                    )
                                val source =
                                    ImageDecoder.createSource(context.contentResolver, it)
                                bitmap.value = ImageDecoder.decodeBitmap(source)
                            }
                        } else {
                            LaunchedEffect(key1 = imageUrl) {
                                val source = ImageDecoder.createSource(context.contentResolver, it)
                                bitmap.value = ImageDecoder.decodeBitmap(source)
                            }
                        }
                    }

                    if (bitmap.value != null) {
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
                        if (state.image!!.isNotEmpty()) {
                            SubcomposeAsyncImage(
                                model = state.image,
                                loading = {
                                    FeatCircularProgress()
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .border(3.dp, PurpleLight, RoundedCornerShape(50))
                                    .size(200.dp),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        } else {
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

                FeatText(
                    text = "${person.names} ${person.lastname}",
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = FontWeight.ExtraBold
                )
                FeatText(
                    text = person.nickname,
                    fontSize = MaterialTheme.typography.body2.fontSize,
                    fontStyle = FontStyle.Italic
                )
                FeatText(
                    text = "Puntaje: ${person.qualification}",
                    fontSize = MaterialTheme.typography.body2.fontSize,
                    fontStyle = FontStyle.Italic,
                    color = if(person.qualification.toInt() > 50) GreenColor else if (person.qualification.toInt() in 21..50) Color.Yellow else RedColor
                )
            }
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                FeatCard(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Column {
                        FeatText(
                            text = "Comentarios:",
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            fontWeight = FontWeight.Bold,
                            separator = true
                        )
                        qualifications.forEach { qualification ->
                            FeatInfo(
                                textInfo = "${qualification.observation}",
                                painter = if (qualification.liked) R.drawable.like else R.drawable.dislike,
                                iconColor = if (qualification.liked) GreenColor else RedColor
                            )
                        }
                    }
                }
            }
        }
    }

}