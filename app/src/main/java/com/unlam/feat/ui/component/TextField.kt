package com.unlam.feat.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.unlam.feat.R
import com.unlam.feat.ui.theme.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun FeatOutlinedTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    text: String,
    textLabel: String,
    enabled: Boolean = true,
    maxLines: Int = 3,
    error: String = "",
    focusedColor: Color = GreenLight,
    unFocusedColor: Color = PurpleLight,
    shape: Shape = CircleShape,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
    isPasswordVisible: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    label: @Composable (() -> Unit)? = { Text(text = textLabel) },
    trailingIcon: @Composable (() -> Unit)? = null,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp),
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = text,
            onValueChange = { onValueChange(it) },
            shape = shape,
            label = label,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = if (error != "") MaterialTheme.colors.error else unFocusedColor,
                unfocusedLabelColor = if (error != "") MaterialTheme.colors.error else unFocusedColor,
                focusedBorderColor = if (error != "") MaterialTheme.colors.error else focusedColor,
                focusedLabelColor = if (error != "") MaterialTheme.colors.error else focusedColor,
                disabledBorderColor = if (error != "") MaterialTheme.colors.error else unFocusedColor,
                disabledLabelColor = if (error != "") MaterialTheme.colors.error else unFocusedColor
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
            maxLines = maxLines
        )
        if (error.isNotEmpty()) {
            Text(
                text = error,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.End,
                modifier = if(trailingIcon == null ){Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp)
                    }else{
                    Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterEnd)
                        .padding(end = 40.dp)
                    }
            )
        }
    }
}

@Composable
fun FeatOutlinedDatePicker(
    date: LocalDate?,
    onValueChange: (LocalDate) -> Unit,
    textLabel: String = "",
    titlePicker: String = "Default",
    error: String = ""
) {
    val dialogState = rememberMaterialDialogState()
    val interactionSource = remember { MutableInteractionSource() }

    FeatOutlinedTextField(
        text = date?.toString()?.format(
            DateTimeFormatter.ofPattern(
                "dd LLLL yyyy"
            )
        )
            ?: "",
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
        unFocusedColor = PurpleLight,
        error = error
    )
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(
                text = "Aceptar",
                textStyle = TextStyle(color = GreenColor)
            )
            negativeButton(
                text = "Cancelar",
                textStyle = TextStyle(color = GreenColor)
            )
        },
    ) {
        datepicker(
            title = titlePicker,
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = GreenColor,
                dateActiveBackgroundColor = GreenColor,

                ),
        ) { date ->
            onValueChange(date)
        }
    }
}

@Composable
fun FeatOutlinedTimePicker(
    modifier: Modifier = Modifier.fillMaxWidth(),
    time: LocalTime?,
    onValueChange: (LocalTime) -> Unit,
    label: String = "",
    titlePicker: String = "Default",
    error: String = ""
) {
    val dialogState = rememberMaterialDialogState()
    val interactionSource = remember { MutableInteractionSource() }

    FeatOutlinedTextField(
        text = time?.toString()?.format(
            DateTimeFormatter.ofPattern(
                "h:mm"
            )
        )
            ?: "",
        onValueChange = {},
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                dialogState.show()
            },
        enabled = false,
        textLabel = label,
        error = error
    )
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(
                text = "Aceptar",
                textStyle = TextStyle(color = GreenColor)
            )
            negativeButton(
                text = "Cancelar",
                textStyle = TextStyle(color = GreenColor)
            )
        },

        ) {
        timepicker(
            title = titlePicker,
            is24HourClock = true,
            colors = TimePickerDefaults.colors(
                activeBackgroundColor = GreenColor,
                borderColor = GreenColor,
                selectorColor = GreenColor
            )
        ) { time ->
            onValueChange(time)
        }
    }
}

@Composable
fun FeatOutlinedDropDown(
    label: String,
    options: List<String>,
    selectedText: (String) -> Unit,
    error: String = ""
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
            },
            error = error
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