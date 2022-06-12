package com.unlam.feat.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.ui.theme.GreenLight
import com.unlam.feat.ui.theme.PurpleDark
import com.unlam.feat.ui.theme.PurpleLight
import com.unlam.feat.ui.theme.PurpleMedium

@Composable
fun FeatOutlinedTextField(
    modifier: Modifier = Modifier,
    text: String,
    textLabel: String,
    error: String = "",
    focusedColor: Color = GreenLight,
    unFocusedColor: Color = PurpleMedium,
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
                focusedLabelColor = focusedColor
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