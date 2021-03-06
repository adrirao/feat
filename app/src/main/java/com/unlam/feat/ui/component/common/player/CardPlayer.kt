package com.unlam.feat.ui.component.common.player

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.unlam.feat.model.Player
import com.unlam.feat.model.PlayerApplyDetail
import com.unlam.feat.ui.component.FeatCard
import com.unlam.feat.ui.component.FeatCircularProgress
import com.unlam.feat.ui.component.FeatInfo
import com.unlam.feat.ui.component.FeatSpacerSmall
import com.unlam.feat.ui.theme.PurpleLight

@Composable
fun CardPlayer(
    modifier: Modifier = Modifier,
    player: Player,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    FeatCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        Row {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .size(200.dp)
                    .clip(RoundedCornerShape(20)),
                contentScale= ContentScale.Crop,
                model = player.photoUrl,
                contentDescription = "",
                loading = { FeatCircularProgress() })
            Column(
                modifier = Modifier.weight(2f)
            ) {
                FeatInfo(
                    textInfo = "Nombre: ${player.person.names} ${player.person.lastname ?: ""}",
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
                FeatInfo(
                    textInfo = "Apodo: ${player.person.nickname}",
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
                FeatInfo(
                    textInfo = "Habilidad: ${player.abilities}",
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
                FeatInfo(
                    textInfo = "Posicion: ${player.position.description}",
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
                FeatInfo(
                    textInfo = "Nivel: ${player.level.description}",
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
                FeatSpacerSmall()
                content()
            }
        }
    }
}

@Composable
fun CardPlayerCalification(
    modifier: Modifier = Modifier,
    player: Player,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    FeatCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        Row {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .size(200.dp)
                    .clip(RoundedCornerShape(20)),
                contentScale= ContentScale.Crop,
                model = player.photoUrl,
                contentDescription = "",
                loading = { FeatCircularProgress() })
            Column(
                modifier = Modifier.weight(2f)
            ) {
                FeatInfo(
                    textInfo = "Nombre: ${player.person.names} ${player.person.lastname}",
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
                FeatInfo(
                    textInfo = "Apodo: ${player.person.nickname}",
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
                FeatSpacerSmall()
                content()
            }
        }
    }
}

@Composable
fun CardPlayerDetail(
    modifier: Modifier = Modifier,
    player: PlayerApplyDetail,
    content: @Composable (ColumnScope.() -> Unit) = {}
) {
    FeatCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        Column {
            Row {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .size(200.dp)
                        .clip(RoundedCornerShape(20)),
                    contentScale= ContentScale.Crop,
                    model = player.photoUrl,
                    contentDescription = "",
                    loading = { FeatCircularProgress() })
                Column(
                    modifier = Modifier.weight(2f)
                ) {
                    FeatInfo(
                        textInfo = "Nombre: ${player.names} ${player.lastname ?: ""}",
                        fontSize = MaterialTheme.typography.body1.fontSize
                    )
                    FeatInfo(
                        textInfo = "Apodo: ${player.nickname} ${player.lastname}",
                        fontSize = MaterialTheme.typography.body1.fontSize
                    )
                    FeatInfo(
                        textInfo = "Habilidad: ${player.abilities}",
                        fontSize = MaterialTheme.typography.body1.fontSize
                    )
                    FeatInfo(
                        textInfo = "Posicion: ${player.position}",
                        fontSize = MaterialTheme.typography.body1.fontSize
                    )
                    FeatInfo(
                        textInfo = "Nivel: ${player.level}",
                        fontSize = MaterialTheme.typography.body1.fontSize
                    )
                    FeatSpacerSmall()
                }
            }
            content()
        }
    }
}