package com.unlam.feat.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.unlam.feat.R
import com.unlam.feat.ui.theme.GreenLight
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.PurpleLight
import com.unlam.feat.ui.theme.PurpleMedium
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.jvm.internal.impl.types.checker.TypeRefinementSupport

@Composable
fun FeatOutlinedTextField(
    modifier: Modifier = Modifier,
    text: String,
    textLabel: String,
    enabled: Boolean = true,
    error: String = "",
    focusedColor: Color = GreenLight,
    unFocusedColor: Color = PurpleLight,
    shape: Shape = CircleShape,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
    isPasswordVisible: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    label: @Composable (() -> Unit)? = { Text(text = textLabel) },
    trailingIcon: @Composable() (() -> Unit)? = null,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
    ) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = { onValueChange(it) },
            shape = shape,
            label = label,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = unFocusedColor,
                unfocusedLabelColor = unFocusedColor,
                focusedBorderColor = focusedColor,
                focusedLabelColor = focusedColor,
                disabledBorderColor = unFocusedColor,
                disabledLabelColor = unFocusedColor
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            visualTransformation = if (!isPasswordVisible && isPasswordToggleDisplayed) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = if (isPasswordToggleDisplayed) {
                val icon: @Composable () -> Unit = {
                    IconButton(
                        onClick = {
                            onPasswordToggleClick(!isPasswordVisible)
                        },
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff

                            },
                            tint = if (isPasswordVisible) focusedColor else unFocusedColor,
                            contentDescription = if (isPasswordVisible) {
                                stringResource(id = R.string.text_password_visible_content_description)
                            } else {
                                stringResource(id = R.string.text_password_hidden_content_description)
                            }
                        )
                    }
                }
                icon
            } else trailingIcon,
            isError = error != "",
            enabled = enabled,
        )
        if (error.isNotEmpty()) {
            Text(
                text = error,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp)
            )
        }
    }
}

@Composable
fun FeatOutlinedDatePicker(
    date: LocalDate?,
    onValueChange: (LocalDate) -> Unit,
    textLabel: String = "",
    titlePicker: String = "Default"

) {
    val dialogState = rememberMaterialDialogState()
    val interactionSource = remember { MutableInteractionSource() }

    FeatOutlinedTextField(
        text = date?.toString()?.format(
            DateTimeFormatter.ofPattern(
                "dd LLLL yyyy"
            )
        )
            ?: " ",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                dialogState.show()
            },
        enabled = false,
        textLabel = textLabel,
        unFocusedColor = PurpleLight
    )
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Aceptar")
            negativeButton("Cancelar")
        },
    ) {
        datepicker(
            title = titlePicker
        ) { date ->
            onValueChange(date)

        }
    }
}

@Composable
fun FeatOutlinedTimePicker(
    modifier: Modifier = Modifier,
    time: LocalTime?,
    onValueChange: (LocalTime) -> Unit,
    label: String = "",
    titlePicker: String = "Default"

) {
    val dialogState = rememberMaterialDialogState()
    val interactionSource = remember { MutableInteractionSource() }

    FeatOutlinedTextField(
        text = time?.toString()?.format(
            DateTimeFormatter.ofPattern(
                "h:mm"
            )
        )
            ?: " ",
        onValueChange = {},
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                dialogState.show()
            },
        enabled = false,
        textLabel = label

    )
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Aceptar")
            negativeButton("Cancelar")
        },

        ) {
        timepicker(
            title = titlePicker
        ) { time ->
            onValueChange(time)

        }
    }
}

@Composable
fun FeatOutlinedDropDown(
    label: String = "FeatDropDown",
    options: List<String> = emptyList(),
    selectedText: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column() {
        FeatOutlinedTextField(
            text = selectedText,
            enabled = false,
            onValueChange = {
                selectedText = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            textLabel = label,
            trailingIcon = {
                Icon(
                    icon,
                    contentDescription = "contentDescription",
                    modifier = Modifier.clickable { expanded = !expanded },
                    tint = if (icon == Icons.Filled.KeyboardArrowDown) PurpleLight else GreenLight
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            options.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    selectedText(label)
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }

}