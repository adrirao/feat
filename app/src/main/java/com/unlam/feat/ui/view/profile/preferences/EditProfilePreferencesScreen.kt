package com.unlam.feat.ui.view.profile.preferences

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.R
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.YellowColor
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.config_profile.ConfigProfileEvents
import com.unlam.feat.ui.view.config_profile.ConfigProfileState

@Composable
fun EditProfilePreferencesScreen(
    state: EditProfilePreferencesState,
    onValueChange: (EditProfilePreferencesEvent) -> Unit,
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
            title = "Preferencias:",
            page = ""
        ) {
            FeatText(
                modifier = Modifier.padding(start = 20.dp),
                text = when (state.ageError) {
                    EditProfilePreferencesState.RangeAgeError.IsInvalidRange -> stringResource(R.string.invalid_range)
                    EditProfilePreferencesState.RangeAgeError.MinAgeEmpty -> stringResource(R.string.min_age_empty)
                    EditProfilePreferencesState.RangeAgeError.MaxAgeEmpty -> stringResource(R.string.max_age_empty)
                    EditProfilePreferencesState.RangeAgeError.FieldEmpty -> stringResource(R.string.text_field_empty)
                    else -> ""
                },
                fontSize = 15.sp, color = MaterialTheme.colors.error
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FeatOutlinedTextField(
                    modifier = Modifier.width(150.dp),
                    text = state.minAge.toString(),
                    keyboardType = KeyboardType.Number,
                    textLabel = "Edad minima",
                    onValueChange = { age ->
                        if (age.length <= 3 && ((age.toIntOrNull()) ?: 0) <= 150) {
                            onValueChange(
                                EditProfilePreferencesEvent.onValueChange(
                                    TypeValueChange.OnValueChangeMinAge, age.filter { it.isDigit() })
                            )

                        }
                    }
                )
                FeatOutlinedTextField(
                    modifier = Modifier.width(150.dp),
                    text = state.maxAge.toString(),
                    textLabel = "Edad maxima",
                    keyboardType = KeyboardType.Number,
                    onValueChange = { age ->
                        if (age.length <= 3 && ((age.toIntOrNull()) ?: 0) <= 150) {
                            onValueChange(
                                EditProfilePreferencesEvent.onValueChange(
                                    TypeValueChange.OnValueChangeMaxAge, age.filter { it.isDigit() })
                            )

                        }
                    }
                )
            }
            FeatOutlinedTextField(
                text = state.willingDistance.toString(),
                textLabel = "Distancia",
                keyboardType = KeyboardType.Number,
                onValueChange = { distance ->
                    if (distance.length <= 2 && ((distance.toIntOrNull()) ?: 0) <= 30) {
                        onValueChange(
                            EditProfilePreferencesEvent.onValueChange(
                                TypeValueChange.OnValueChangeWillingDistance, distance
                            )
                        )

                    }
                }
            )
            FeatCheckbox(
                checked = state.notifications,
                label = "Notificaciones",
                onCheckedChange = {
                    onValueChange(
                        EditProfilePreferencesEvent.onValueChange(
                            TypeValueChange.OnValueChangeNotifications, "", valueBooleanOpt = it
                        )
                    )
                },
            )
            FeatSpacerSmall()
            FeatOutlinedButton(
                modifier = Modifier.align(Alignment.End),
                textContent = "Modificar",
                contentColor = YellowColor,
                backgroundColor = YellowColor,
                textColor = PurpleDark,
                onClick = {
                    onValueChange(EditProfilePreferencesEvent.onClick(TypeClick.Submit))
                }
            )
        }
    }
}