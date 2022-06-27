package com.unlam.feat.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.unlam.feat.ui.theme.*

@Composable
fun SuccessDialog(
    title: String,
    desc: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .width(500.dp)
        ) {
            Column(
                modifier = Modifier
                    .size(500.dp)
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Box(
                    modifier = Modifier
                        .width(500.dp)
                        .background(
                            color = PurpleMedium,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = title.uppercase(),
                            style = TextStyle(
                                color = PurpleLight,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = desc,
                            style = TextStyle(
                                color = PurpleLight,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            FeatOutlinedButton(
                                textContent = "Cancelar",
                                contentColor = ErrorColor,
                                backgroundColor = RedColor20,
                                textColor = ErrorColor,
                                onClick = onDismiss,
                                width = 100.dp,
                                height = 40.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            FeatOutlinedButton(
                                textContent = "Aceptar",
                                contentColor = GreenColor,
                                backgroundColor = GreenColor20,
                                textColor = GreenColor,
                                onClick = onDismiss,
                                width = 100.dp,
                                height = 40.dp
                            )
                        }
                    }
                }
            }
            SuccessHeader(
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.TopCenter)
                    .border(
                        border = BorderStroke(width = 5.dp, color = Color.White),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview
@Composable
fun ErrorDialog(
    title: String = "",
    desc: String = "",
    onDismiss: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .width(500.dp)
        ) {
            Column(
                modifier = Modifier
                    .size(500.dp)
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Box(
                    modifier = Modifier
                        .width(500.dp)
                        .background(
                            color = PurpleMedium,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = title.uppercase(),
                            style = TextStyle(
                                color = PurpleLight,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = desc,
                            style = TextStyle(
                                color = PurpleLight,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            FeatOutlinedButton(
                                textContent = "Cancelar",
                                contentColor = ErrorColor,
                                backgroundColor = RedColor20,
                                textColor = ErrorColor,
                                onClick = onDismiss,
                                width = 100.dp,
                                height = 40.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            FeatOutlinedButton(
                                textContent = "Aceptar",
                                contentColor = GreenColor,
                                backgroundColor = GreenColor20,
                                textColor = GreenColor,
                                onClick = onDismiss,
                                width = 100.dp,
                                height = 40.dp
                            )
                        }
                    }
                }
            }
            ErrorHeader(
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.TopCenter)
                    .border(
                        border = BorderStroke(width = 5.dp, color = Color.White),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun InfoDialog(
    title: String,
    desc: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .width(500.dp)
        ) {
            Column(
                modifier = Modifier
                    .size(500.dp)
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Box(
                    modifier = Modifier
                        .width(500.dp)
                        .background(
                            color = PurpleMedium,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = title.uppercase(),
                            style = TextStyle(
                                color = PurpleLight,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = desc,
                            style = TextStyle(
                                color = PurpleLight,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            FeatOutlinedButton(
                                textContent = "Cancelar",
                                contentColor = ErrorColor,
                                backgroundColor = RedColor20,
                                textColor = ErrorColor,
                                onClick = onDismiss,
                                width = 100.dp,
                                height = 40.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            FeatOutlinedButton(
                                textContent = "Aceptar",
                                contentColor = GreenColor,
                                backgroundColor = GreenColor20,
                                textColor = GreenColor,
                                onClick = onDismiss,
                                width = 100.dp,
                                height = 40.dp
                            )
                        }
                    }
                }
            }
            InfoHeader(
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.TopCenter)
                    .border(
                        border = BorderStroke(width = 5.dp, color = Color.White),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun SuccessHeader(modifier: Modifier) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(com.unlam.feat.R.raw.success))
    val progress by animateLottieCompositionAsState(composition = composition)

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier
    )
}

@Composable
fun ErrorHeader(modifier: Modifier) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(com.unlam.feat.R.raw.error))
    val progress by animateLottieCompositionAsState(composition = composition)

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier
    )
}

@Composable
fun InfoHeader(modifier: Modifier) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(com.unlam.feat.R.raw.info))
    val progress by animateLottieCompositionAsState(composition = composition)

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier
    )
}