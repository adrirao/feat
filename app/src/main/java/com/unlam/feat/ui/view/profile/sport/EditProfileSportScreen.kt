package com.unlam.feat.ui.view.profile.sport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.YellowColor
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.config_profile.ConfigProfileEvents
import com.unlam.feat.ui.view.config_profile.ConfigProfileState
import com.unlam.feat.ui.view.profile.personal_information.EditPersonalInformationState
import com.unlam.feat.ui.view.profile.personal_information.EditProfilePersonalInformationEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun EditProfileSportScreen(
    state: EditProfileSportState,
    onValueChange: (EditProfileSportEvent) -> Unit,
    goToProfile: () -> Unit
) {

    if(state.isSuccessSubmitData){
        SuccessDialog(
            title = "Modificación Exitosa",
            desc = "Los datos se modificaron con exito",
            enabledCancelButton = false,
            onDismiss = goToProfile
        )
    }
    if(state.isErrorSubmitData){
        ErrorDialog(
            title = "Error en el servidor",
            desc = "No se puedo realizar la operacion, comuniquese con el administrador",
            enabledCancelButton = false,
            onDismiss = goToProfile
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        FeatForm(
            modifier = Modifier.padding(10.dp),
            title = "Deportes:",
            page = ""
        ) {


            val sports = mutableListOf<String>()
            state.sportsList?.map {
                sports.add(it.description)
            }
            val players = mutableListOf<String>()
            state.playersUser?.map {
                players.add(it.sport.description)
            }
            val listSports = mutableListOf<String>()
            sports.forEach { name ->
                if(!players.contains(name)){
                    listSports.add(name)
                }
            }


            FeatOutlinedDropDown(
                label = "Deporte",
                options = listSports,
                selectedText = { value ->
                    state.sportsList?.forEach {
                        if (it.description == value) onValueChange(
                            EditProfileSportEvent.onValueChange(
                                TypeValueChange.OnValueChangeTypeSport,
                                it.id.toString()
                            )
                        )
                        onValueChange(
                            EditProfileSportEvent.onValueChange(
                                TypeValueChange.OnValueChangeSelectSport,
                                it.id.toString()
                            )
                        )
                    }
                },
            )



            val optionsPosition: MutableList<String> = mutableListOf<String>()
            state.positionList.forEach { positionList ->
                optionsPosition.add(positionList.description)
            }
            FeatOutlinedDropDown(
                label = "Posición",
                options = optionsPosition,
                selectedText = { positionText ->
                    state.positionList.forEach { position ->
                        if (position.description == positionText) onValueChange(
                            EditProfileSportEvent.onValueChange(
                                TypeValueChange.OnValueChangePositionSport, position.id.toString()
                            )
                        )
                    }
                },
                error = when (state.positionIdSportError) {
                    EditProfileSportState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
            )




            val optionsLevel: MutableList<String> = mutableListOf<String>()
            state.levelList.forEach { levelList ->
                optionsLevel.add(levelList.description)
            }
            FeatOutlinedDropDown(
                label = "Nivel",
                options = optionsLevel,
                selectedText = { levelText ->
                    state.levelList.forEach { level ->
                        if (level.description == levelText) onValueChange(
                            EditProfileSportEvent.onValueChange(
                                TypeValueChange.OnValueChangeLevelSport, level.id.toString()
                            )
                        )
                    }
                },
//                error = when (state.) {
//                    EditProfileSportState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
//                    else -> ""
//                },
            )



            val optionsValuation: MutableList<String> = mutableListOf<String>()
            state.valuationList.forEach { valuationList ->
                optionsValuation.add(valuationList.description)
            }
            FeatOutlinedDropDown(
                label = "Valoración",
                options = optionsValuation,
                selectedText = { valuationText ->
                    state.valuationList.forEach { valuation ->
                        if (valuation.description == valuationText) onValueChange(
                            EditProfileSportEvent.onValueChange(
                                TypeValueChange.OnValueChangeValuationSport, valuation.id.toString()
                            )
                        )
                    }
                },
//                error = when (state.) {
//                    EditProfileSportState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
//                    else -> ""
//                },
            )



            FeatOutlinedTextField(
                textLabel = "Aptitudes",
                text = state.abilitiesSport,
                onValueChange = { abilities ->
                    onValueChange(
                        EditProfileSportEvent.onValueChange(
                            TypeValueChange.OnValueChangeAbilitiesSport, abilities
                        )
                    )
                },
//                maxLines = 4,
//                error = when (state.abilitiesSoccerError) {
//                    EditProfileSportState.GenericError.FieldEmpty -> stringResource(R.string.text_field_empty)
//                    else -> ""
//                }
            )

            FeatSpacerSmall()
            FeatOutlinedButton(
                modifier = Modifier.align(Alignment.End),
                textContent = "AGREGAR",
                contentColor = YellowColor,
                backgroundColor = YellowColor,
                textColor = PurpleDark,
                onClick = {
                    onValueChange(
                        EditProfileSportEvent.onClick(TypeClick.Submit)
                    )
                }
            )
        }
    }

}