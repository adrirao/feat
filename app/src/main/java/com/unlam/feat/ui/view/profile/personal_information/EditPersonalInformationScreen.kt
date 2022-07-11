package com.unlam.feat.ui.view.profile.personal_information


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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EditPersonalInformationScreen(
    state: EditPersonalInformationState,
    onValueChange: (EditProfilePersonalInformationEvent) -> Unit,
    goToProfile: () -> Unit
) {

    if(state.isSuccessSubmitData){
        InfoDialog(
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
            title = "Datos personales:",
            page = ""
        ) {
            FeatOutlinedTextField(
                text = state.names,
                textLabel = "Nombre",
                onValueChange = { onValueChange(
                    EditProfilePersonalInformationEvent.onValueChange(
                        TypeValueChange.OnValueChangeName,
                        it
                    )
                )},
                error = when (state.nameError) {
                    EditPersonalInformationState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                    else -> ""
                }
            )
            FeatOutlinedTextField(
                text = state.lastname,
                textLabel = "Apellido",
                onValueChange = {
                    onValueChange(
                        EditProfilePersonalInformationEvent.onValueChange(
                            TypeValueChange.OnValueChangeLastName,
                            it
                        )
                    )
                },
                error = when (state.lastnameError) {
                    EditPersonalInformationState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                    else -> ""
                }
            )
            FeatOutlinedTextField(
                text = state.nickname,
                textLabel = "Apodo",
                onValueChange = {
                    onValueChange(
                        EditProfilePersonalInformationEvent.onValueChange(
                            TypeValueChange.OnValueChangeNickname,
                            it
                        )
                    )
                },
                error = when (state.nicknameError) {
                    EditPersonalInformationState.GenericError.FieldEmpty -> stringResource(id = R.string.text_field_empty)
                    else -> ""
                }

            )
            FeatOutlinedDropDown(
                label = stringResource(R.string.sex),
                options = state.sexList,
                selectedText = { value ->
                    state.sexList.forEach {
                        if (it == value) onValueChange(
                            EditProfilePersonalInformationEvent.onValueChange(
                                TypeValueChange.OnValueChangeSex,
                                it
                            )
                        )
                    }
                },
                error = when (state.sexError) {
                    EditPersonalInformationState.GenericError.FieldEmpty -> {
                        stringResource(id = R.string.text_field_empty)
                    }
                    else -> ""
                }
            )
            FeatOutlinedDatePicker(
                date = if(state.birthDate != "") LocalDate.parse(state.birthDate?.substring(0, 10)?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) else LocalDate.now(),
                textLabel = "Nacimiento",
                onValueChange = {
                    onValueChange(
                        EditProfilePersonalInformationEvent.onValueChange(
                            TypeValueChange.OnValueChangeDate,
                            it.toString()
                        )
                    )
                },
                error = when (state.birthDateError) {
                    EditPersonalInformationState.DateError.DateInvalid -> stringResource(id = R.string.text_field_empty)
                    EditPersonalInformationState.DateError.IsNotOfLegalAge -> stringResource(R.string.is_not_of_legal_age)
                    else -> ""
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
                    onValueChange(
                        EditProfilePersonalInformationEvent.onClick(TypeClick.Submit)
                    )
                }
            )
        }
    }
}