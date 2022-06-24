package com.unlam.feat.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.unlam.feat.R
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.PurpleLight
import com.unlam.feat.ui.theme.PurpleMedium

@Composable
fun FeatCard(
    modifier: Modifier = Modifier,
    width: Dp = 175.dp,
    height: Dp = width,
    colorCard: Color = PurpleLight,
    urlImage: String = "",
    @DrawableRes painter: Int = R.drawable.ic_launcher_foreground,
    content: @Composable (BoxScope.() -> Unit)
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(20))
            .background(colorCard),
    ) {
        if (urlImage.isBlank()) {
            Image(
                painter = painterResource(id = painter),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            AsyncImage(
                model = urlImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        content()
    }
}

@Composable
fun FeatForm(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        backgroundColor = PurpleDark,
        elevation = 3.dp,
        shape = RoundedCornerShape(5),
        content = {
            Column(modifier = Modifier.padding(vertical = 40.dp)) {
                content()
            }
        }
    )
}