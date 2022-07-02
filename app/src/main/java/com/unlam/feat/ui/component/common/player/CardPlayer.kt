package com.unlam.feat.ui.component.common.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.unlam.feat.model.Player
import com.unlam.feat.model.PlayerApplyDetail
import com.unlam.feat.ui.component.FeatCard
import com.unlam.feat.ui.component.FeatInfo
import com.unlam.feat.ui.component.FeatSpacerSmall
import com.unlam.feat.ui.theme.PurpleLight

@Composable
fun CardPlayer(
    player: Player,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    FeatCard(
        modifier = Modifier.fillMaxWidth(),
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
                    textInfo = "Nombre: ${player.person.names} ${player.person.lastname ?: ""}",
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
                FeatInfo(
                    textInfo = "Apodo: ${player.person.nickname} ${player.person.lastname}",
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
fun CardPlayerDetail(
    player: PlayerApplyDetail,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    FeatCard(
        modifier = Modifier.fillMaxWidth(),
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