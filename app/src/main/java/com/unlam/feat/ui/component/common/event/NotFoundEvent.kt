package com.unlam.feat.ui.component.common.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.unlam.feat.ui.component.FeatText
import com.unlam.feat.ui.theme.PurpleMedium

@Composable
fun NotFoundEvent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FeatText(
            text = "No se encontraron eventos.",
            fontSize = MaterialTheme.typography.body1.fontSize,
            color = PurpleMedium
        )
    }
}

@Composable
fun NotFoundPlayer() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FeatText(
            text = "No se encontraron participantes.",
            fontSize = MaterialTheme.typography.body1.fontSize,
            color = PurpleMedium
        )
    }
}

@Composable
fun NotFoundInvitation() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FeatText(
            text = "No se encontraron invitaciones.",
            fontSize = MaterialTheme.typography.body1.fontSize,
            color = PurpleMedium
        )
    }
}