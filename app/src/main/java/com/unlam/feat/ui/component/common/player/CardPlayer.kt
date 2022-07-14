package com.unlam.feat.ui.component.common.player

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    uri : String = "",
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
                    .align(Alignment.CenterVertically),
                contentScale= ContentScale.Crop,
                model = uri,
//                model = "https://firebasestorage.googleapis.com/v0/b/feat-6ca32.appspot.com/o/images%2F7Pjlj6cEIjcdsZgIrT0UkP2JXVm1.jpeg?alt=media&token=38e80a71-0b08-41e1-a025-a8c24affa5f6",
                contentDescription = "",
                loading = { FeatCircularProgress() })
//            Icon(
//                modifier = Modifier
//                    .weight(1f)
//                    .align(Alignment.CenterVertically),
//                imageVector = Icons.Outlined.Person,
//                contentDescription = null,
//                tint = PurpleLight
//            )
            Column(
                modifier = Modifier.weight(3f)
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
            Icon(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                tint = PurpleLight
            )
            Column(
                modifier = Modifier.weight(3f)
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
    player: PlayerApplyDetail,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    FeatCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        Row {
            Icon(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                tint = PurpleLight
            )
            Column(
                modifier = Modifier.weight(3f)
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
                content()
            }
        }
    }
}