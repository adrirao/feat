package com.unlam.feat.ui.view.profile.personal_information

import android.icu.text.DateTimePatternGenerator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.YellowColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EditPersonalInformationScreen(
    state: EditPersonalInformationState,
    onValueChange: (EditProfilePersonalInformationEvent) -> Unit,
    updatePerson: () -> Unit
) {
    val person = state.person!!

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        FeatForm(
            modifier = Modifier.padding(10.dp),
            title = "Datos personales:",
            page = ""
        ) {
            FeatOutlinedTextField(
                text = person.names,
                textLabel = "Nombre",
                onValueChange = { onValueChange(EditProfilePersonalInformationEvent.EnteredNames(it)) }
            )
            FeatOutlinedTextField(
                text = person.lastname,
                textLabel = "Apellido",
                onValueChange = {
                    onValueChange(
                        EditProfilePersonalInformationEvent.EnteredLastNames(it)
                    )
                }
            )
            FeatOutlinedTextField(
                text = person.nickname,
                textLabel = "Apodo",
                onValueChange = {
                    onValueChange(
                        EditProfilePersonalInformationEvent.EnteredNickname(it)
                    )
                }
            )
            FeatOutlinedDatePicker(
                date = LocalDate.parse(person.birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")),
                textLabel = "Nacimiento",
                onValueChange = {
                    onValueChange(
                        EditProfilePersonalInformationEvent.EnteredBirthDate(it)
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
                    updatePerson()
                }
            )
        }
    }
}