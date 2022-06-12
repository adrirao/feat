package com.unlam.feat.ui.view.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.magnifier
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.ui.component.FeatCard
import com.unlam.feat.ui.component.FeatContent
import com.unlam.feat.ui.component.FeatOutlinedButton
import com.unlam.feat.ui.theme.GreenLight
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.blackTransparent

@ExperimentalFoundationApi
@Preview
@Composable
fun HomeScreen(

) {
    Column {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//                .background(PurpleMedium)
//                .shadow(elevation = 3.dp),
//        ){
//            IconButton(
//                onClick = {  },
//                modifier = Modifier.align(Alignment.TopStart)
//            ) {
//                Icon(painter = painterResource(id = R.drawable.ic_back_arrow), contentDescription = "")
//            }
//        }
        FeatContent {
            Column {
                Text(
                    text = "Eventos sugeridos de la semana:",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    fontSize = 18.sp
                )
                LazyRow(content = {
                    items(10) {
                        FeatCard(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Nombre evento",
                                    color = Color.White,
                                )
                                Text(
                                    text = "Horario",
                                    color = Color.White,
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(blackTransparent)
                                    .align(Alignment.BottomCenter)
                            ) {
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
            Divider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = PurpleDark
            )
            Column {

                Text(
                    text = "Eventos confirmados:",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    fontSize = 18.sp
                )
                LazyVerticalGrid(cells = GridCells.Fixed(2), content = {
                    items(20) {
                        FeatCard(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Nombre evento",
                                    color = Color.White,
                                )
                                Text(
                                    text = "Horario",
                                    color = Color.White,
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(blackTransparent)
                                    .align(Alignment.BottomCenter)
                            ) {
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
}