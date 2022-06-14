package com.unlam.feat.ui.view.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.ui.component.FeatContent
import com.unlam.feat.ui.component.FeatOutlinedButton
import com.unlam.feat.ui.component.FeatOutlinedTextField
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.PurpleLight
import com.unlam.feat.ui.theme.PurpleMedium
import com.unlam.feat.ui.util.TypeClick

@Composable
fun MainScreen(
    onClick: (MainEvents.onClick) -> Unit
) {
    FeatContent(
        verticalArrangement = Arrangement.Bottom
    ) {
        FeatOutlinedButton(
            textContent = stringResource(id = R.string.text_login),
            onClick = {
                onClick(MainEvents.onClick(TypeClick.GoToLogin))
            }
        )
        FeatOutlinedButton(
            textContent = stringResource(id = R.string.text_register),
            contentColor = Color.Transparent,
            textColor = PurpleLight,
            onClick = {
                onClick(MainEvents.onClick(TypeClick.GoToRegister))
            }
        )
    }
}

@Composable
fun MainScreenWithAnimation() {
    var isVisible by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PurpleLight,
                        PurpleMedium,
                        PurpleDark
                    )
                )
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FeatOutlinedButton(
                textContent = stringResource(R.string.text_login),
                onClick = { isVisible = !isVisible }
            )
            FeatOutlinedButton(
                textContent = stringResource(R.string.text_register),
                border = BorderStroke(3.dp, Color.Transparent),
                textColor = PurpleLight,
                textWeight = FontWeight.Light,
                onClick = {}
            )
        }

        AnimatedVisibility(
            visible = isVisible,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .height(300.dp),
            enter = slideInVertically(
                initialOffsetY = { with(density) { 300.dp.roundToPx() } },
            ),
            exit = slideOutVertically(
                targetOffsetY = { with(density) { 300.dp.roundToPx() } },
            )
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                    .background(PurpleLight)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    var text by remember {
                        mutableStateOf("")
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        IconButton(
                            onClick = {
                                isVisible = false
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add),
                                contentDescription = "",
                                tint = PurpleMedium,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }

                    FeatOutlinedTextField(
                        text = text,
                        textLabel = "Email",
                        onValueChange = {
                            text = it
                        }
                    )
                    FeatOutlinedButton(
                        textContent = stringResource(R.string.text_get_in),
                        contentColor = PurpleMedium,
                        onClick = {
                            isVisible = false
                        }
                    )
                }

            }
        }

    }
}