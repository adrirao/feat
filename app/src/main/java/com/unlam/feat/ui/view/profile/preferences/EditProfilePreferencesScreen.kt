package com.unlam.feat.ui.view.profile.preferences

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.YellowColor

@Composable
fun EditProfilePreferencesScreen(
    state: EditProfilePreferencesState,
    onValueChange: (EditProfilePreferencesEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        val person = state.person!!
        FeatForm(
            modifier = Modifier.padding(10.dp),
            title = "Preferencias:",
            page = ""
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FeatOutlinedTextField(
                    modifier = Modifier.width(150.dp),
                    text = person.minAge.toString(),
                    keyboardType = KeyboardType.Number,
                    textLabel = "Edad minima",
                    onValueChange = { onValueChange(EditProfilePreferencesEvent.EnteredMinAge(it)) }
                )
                FeatOutlinedTextField(
                    modifier = Modifier.width(150.dp),
                    text = person.maxAge.toString(),
                    textLabel = "Edad maxima",
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        onValueChange(
                            EditProfilePreferencesEvent.EnteredMaxAge(it)
                        )
                    }
                )
            }
            FeatOutlinedTextField(
                text = person.willingDistance.toString(),
                textLabel = "Distancia",
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    onValueChange(
                        EditProfilePreferencesEvent.EnteredWillingDistance(it)
                    )
                }
            )
            FeatSpacerSmall()
            FeatOutlinedButton(
                modifier = Modifier.align(Alignment.End),
                textContent = "Modificar",
                contentColor = YellowColor,
                backgroundColor = YellowColor,
                textColor = PurpleDark,
                onClick = {

                }
            )
        }
    }
}