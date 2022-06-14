package com.unlam.feat.ui.view.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.ui.component.FeatCard
import com.unlam.feat.ui.component.FeatCircularProgress
import com.unlam.feat.ui.component.FeatContent
import com.unlam.feat.ui.component.FeatOutlinedButton
import com.unlam.feat.ui.theme.*
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.util.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    state: HomeState,
    onClick: (HomeEvents) -> Unit
) {
    val eventsConfirmed = state.eventsConfirmedForMy
    Column {
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
                            modifier = Modifier.padding(10.dp),
                            urlImage = Constants.ImageSport.PADEL
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
                    items(eventsConfirmed) { event ->
                        val date = LocalDate.parse(event.date.substring(0, 10)).format(
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        )
                        FeatCard(
                            modifier = Modifier.padding(10.dp),
                            urlImage = Constants.ImageSport.FUTBOL
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(BlackTransparent30)
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = event.name,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Text(
                                    text = "$date ${
                                        event.startTime.substring(
                                            0,
                                            5
                                        )
                                    } - ${event.endTime.substring(0, 5)}",
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
                                    textContent = "Info",
                                    contentColor = GreenLight,
                                    modifier = Modifier.align(Alignment.BottomEnd),
                                    height = 30.dp,
                                    width = 100.dp
                                ) {
                                    onClick(HomeEvents.onClick(TypeClick.GoToInfoEvent))
                                }
                            }
                        }
                    }
                })
            }
        }
    }
    if (state.isLoading) {
        FeatCircularProgress()
    }
}