package com.unlam.feat.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FeatSpacerMedium(){
    Spacer(modifier = Modifier.height(30.dp))
}

@Composable
fun FeatSpacerSmall(){
    Spacer(modifier = Modifier.height(15.dp))
}