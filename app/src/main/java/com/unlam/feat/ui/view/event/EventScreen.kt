package com.unlam.feat.ui.view.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.R
import com.unlam.feat.ui.component.FeatCard
import com.unlam.feat.ui.component.FeatContent
import com.unlam.feat.ui.component.FeatOutlinedButton
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.util.TypeClick

@Preview
@Composable
fun EventScreen(
    onClick: (EventEvents.onClick) -> Unit
) {
    FeatContent {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Tus eventos creados:",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .align(Alignment.CenterStart),
                    fontSize = 18.sp
                )
                IconButton(
                    onClick = {
                        onClick(EventEvents.onClick(TypeClick.GoToNewEvent))
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        tint = GreenLight,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
            LazyColumn(content = {
                items(30) {
                    FeatCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        urlImage = "https://img.freepik.com/vector-gratis/coleccion-futbolistas-planos_23-2149002218.jpg"
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(BlackTransparent30)
                                .padding(20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Nombre evento",
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = "Horario",
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BlackTransparent)
                                .align(Alignment.BottomCenter)
                        ) {
                            FeatOutlinedButton(
                                textContent = "Cancelar",
                                contentColor = ErrorColor,
                                modifier = Modifier.align(Alignment.BottomStart),
                                height = 30.dp,
                                width = 115.dp
                            ) {

                            }
                            FeatOutlinedButton(
                                textContent = "Info",
                                contentColor = GreenLight,
                                modifier = Modifier.align(Alignment.BottomEnd),
                                height = 30.dp,
                                width = 100.dp
                            ) {

                            }
                        }
                    }
                }
            })
        }
    }
}