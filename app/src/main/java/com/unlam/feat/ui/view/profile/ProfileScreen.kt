package com.unlam.feat.ui.view.profile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.unlam.feat.R
import com.unlam.feat.model.Player
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.*
import kotlinx.coroutines.coroutineScope
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ProfileScreen(
    state: ProfileState,
    navigateTo: (ProfileEvent.NavigateTo.TypeNavigate) -> Unit,
    uploadImage: (ProfileEvent.UploadImage) -> Unit
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
    val addresses = state.addresses!!
    val date = LocalDate.parse(person.birthDate.substring(0, 10)).format(
        DateTimeFormatter.ofPattern("dd/MM/yyyy")
    )
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
                                uploadImage(ProfileEvent.UploadImage(bitmap.value!!))
                            }
                        } else {
                            LaunchedEffect(key1 = imageUrl) {
                                val source = ImageDecoder.createSource(context.contentResolver, it)
                                bitmap.value = ImageDecoder.decodeBitmap(source)

                                uploadImage(ProfileEvent.UploadImage(bitmap.value!!))
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
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(15.dp)
                                .clip(RoundedCornerShape(50))
                                .clickable {
                                    launcher.launch("image/*")
                                }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(PurpleDark)
                                    .padding(10.dp)
                                    .size(30.dp),
                                imageVector = Icons.Outlined.CameraAlt,
                                tint = GreenColor,
                                contentDescription = null
                            )
                        }
                    } else {
                        SubcomposeAsyncImage(
                            model = state.image,
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
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(15.dp)
                                .clip(RoundedCornerShape(50))
                                .clickable {
                                    launcher.launch("image/*")
                                }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(PurpleDark)
                                    .padding(10.dp)
                                    .size(30.dp),
                                imageVector = Icons.Outlined.CameraAlt,
                                tint = GreenColor,
                                contentDescription = null
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
            }
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                FeatCard(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Column {
                        FeatText(
                            text = "Mis datos:",
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            fontWeight = FontWeight.Bold,
                            separator = true
                        )
                        FeatInfo(textInfo = "Nombre: ${person.names}")
                        FeatInfo(textInfo = "Apellido: ${person.lastname}")
                        FeatInfo(textInfo = "Sexo: ${person.sex}")
                        FeatInfo(textInfo = "Nacimiento: $date")
                    }
                    FeatOutlinedButtonIcon(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        icon = Icons.Outlined.EditNote,
                        shape = RoundedCornerShape(50),
                        height = 50.dp,
                        width = 50.dp,
                        backgroundColor = GreenColor90,
                        contentColor = GreenColor,
                        textColor = PurpleDark,
                        onClick = {
                            navigateTo(ProfileEvent.NavigateTo.TypeNavigate.NavigateToPersonalInfo)
                        }
                    )
                }
                FeatCard(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Column {
                        FeatText(
                            text = "Disponibilidad:",
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            fontWeight = FontWeight.Bold,
                            separator = true
                        )
                        person.availabilities.forEach { availability ->
                            FeatInfo(
                                textInfo = "${availability.day.description}: ${
                                    availability.startTime.substring(
                                        0..4
                                    )
                                } - ${availability.endTime.substring(0..4)}"
                            )
                        }
                    }
                }
                FeatCard(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Column {
                        FeatText(
                            text = "Preferencias:",
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            fontWeight = FontWeight.Bold,
                            separator = true
                        )
                        FeatInfo(textInfo = "Rango Edad: min.${person.minAge} - max.${person.maxAge}")
                        FeatInfo(textInfo = "Distancia: ${person.willingDistance}km")
                    }
                    FeatOutlinedButtonIcon(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        icon = Icons.Outlined.EditNote,
                        shape = RoundedCornerShape(50),
                        height = 50.dp,
                        width = 50.dp,
                        backgroundColor = GreenColor90,
                        contentColor = GreenColor,
                        textColor = PurpleDark,
                        onClick = {
                            navigateTo(ProfileEvent.NavigateTo.TypeNavigate.NavigateToPreferencies)
                        }
                    )
                }
                FeatCard(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Column {
                        FeatText(
                            text = "Direcciones:",
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            fontWeight = FontWeight.Bold,
                            separator = true
                        )
                        addresses.forEach { address ->
                            FeatInfo(
                                modifier = Modifier.padding(end = 50.dp),
                                textInfo = "- ${address.alias} ${address.street} ${address.number} ${address.town} ${address.street} "
                            )
                        }
                    }
                    FeatOutlinedButtonIcon(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        icon = Icons.Outlined.EditNote,
                        shape = RoundedCornerShape(50),
                        height = 50.dp,
                        width = 50.dp,
                        backgroundColor = GreenColor90,
                        contentColor = GreenColor,
                        textColor = PurpleDark,
                        onClick = {
                            navigateTo(ProfileEvent.NavigateTo.TypeNavigate.NavigateToAddress)
                        }
                    )
                }
                FeatCard(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Column {
                        FeatText(
                            text = "Deportes:",
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            fontWeight = FontWeight.Bold,
                            separator = true
                        )
                        FeatInfo(
                            modifier = Modifier.padding(end = 50.dp),
                            textInfo = "Futbol"
                        )
                        FeatInfo(textInfo = "Tenis")
                    }
                    FeatOutlinedButtonIcon(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        icon = Icons.Outlined.EditNote,
                        shape = RoundedCornerShape(50),
                        height = 50.dp,
                        width = 50.dp,
                        backgroundColor = GreenColor90,
                        contentColor = GreenColor,
                        textColor = PurpleDark,
                        onClick = {
//                            val moshi =
//                                Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
//                            val jsonAdapter = moshi.adapter(Player::class.java).lenient()
//                            val playerJson = jsonAdapter.toJson(player)
                            navigateTo(
                                ProfileEvent.NavigateTo.TypeNavigate.NavigateToPlayerInformation(
                                    ""
                                )
                            )
                        }
                    )
                }
                FeatSpacerMedium()
                FeatOutlinedButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    textContent = "Cerrar sesion",
                    backgroundColor = Color.Transparent,
                    contentColor = Color.Transparent,
                    textColor = RedColor40,
                    onClick = {}
                )
            }
        }
    }
}