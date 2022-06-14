package com.unlam.feat.ui.view.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.ui.component.FeatContent
import com.unlam.feat.ui.component.FeatOutlinedButton
import com.unlam.feat.ui.component.FeatOutlinedTextField
import com.unlam.feat.ui.theme.GreenLight
import com.unlam.feat.ui.theme.PurpleLight
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange

@Composable
fun RegisterScreen(
    state: RegisterState,
    onClick: (RegisterEvents.onClick) -> Unit,
    onValueChange: (RegisterEvents.onValueChange) -> Unit
) {
    FeatContent {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                FeatOutlinedTextField(
                    text = state.textEmail,
                    unFocusedColor = PurpleLight,
                    textLabel = stringResource(id = R.string.text_email),
                    onValueChange = {
                        onValueChange(
                            RegisterEvents.onValueChange(
                                TypeValueChange.OnValueChangeEmail,
                                it
                            )
                        )
                    },
                    error = when (state.emailError) {
                        RegisterState.EmailError.FieldEmpty -> {
                            stringResource(R.string.text_field_empty)
                        }
                        RegisterState.EmailError.InvalidEmail -> {
                            stringResource(R.string.text_ivalid_email)
                        }
                        else -> ""
                    }
                )
                FeatOutlinedTextField(
                    text = state.textReEmail,
                    unFocusedColor = PurpleLight,
                    textLabel = stringResource(id = R.string.text_re_email),
                    onValueChange = {
                        onValueChange(
                            RegisterEvents.onValueChange(
                                TypeValueChange.OnValueChangeReEmail,
                                it
                            )
                        )
                    },
                    error = when (state.reEmailError) {
                        RegisterState.EmailError.FieldEmpty -> {
                            stringResource(R.string.text_field_empty)
                        }
                        RegisterState.EmailError.InvalidEmail -> {
                            stringResource(R.string.text_ivalid_email)
                        }
                        RegisterState.EmailError.DiffEmail -> {
                            stringResource(R.string.text_diff_email)
                        }
                        else -> ""
                    }
                )
                FeatOutlinedTextField(
                    text = state.textPassword,
                    unFocusedColor = PurpleLight,
                    textLabel = stringResource(id = R.string.text_password),
                    keyboardType = KeyboardType.Password,
                    onValueChange = {
                        onValueChange(
                            RegisterEvents.onValueChange(
                                TypeValueChange.OnValueChangePassword,
                                it
                            )
                        )
                    },
                    isPasswordVisible = state.isVisiblePassword,
                    onPasswordToggleClick = {
                        onClick(RegisterEvents.onClick(TypeClick.ToggledPassword))
                    },
                    error = when (state.passwordError) {
                        RegisterState.PasswordError.InvalidPassword -> stringResource(R.string.text_invalid_password)
                        RegisterState.PasswordError.FieldEmpty -> stringResource(R.string.text_field_empty)
                        RegisterState.PasswordError.InputTooShort -> stringResource(R.string.text_input_too_short)
                        else -> ""
                    }
                )
                FeatOutlinedTextField(
                    text = state.textRePassword,
                    unFocusedColor = PurpleLight,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.None,
                    textLabel = stringResource(id = R.string.text_re_password),
                    onValueChange = {
                        onValueChange(
                            RegisterEvents.onValueChange(
                                TypeValueChange.OnValueChangeRePassword,
                                it
                            )
                        )
                    },
                    isPasswordVisible = state.isVisibleRePassword,
                    onPasswordToggleClick = {
                        onClick(RegisterEvents.onClick(TypeClick.ToggledRePassword))
                    },
                    error = when (state.rePasswordError) {
                        RegisterState.PasswordError.InvalidPassword -> stringResource(R.string.text_invalid_password)
                        RegisterState.PasswordError.FieldEmpty -> stringResource(R.string.text_field_empty)
                        RegisterState.PasswordError.InputTooShort -> stringResource(R.string.text_input_too_short)
                        RegisterState.PasswordError.DiffPassword -> stringResource(R.string.text_diff_password)
                        else -> ""
                    }
                )
                FeatOutlinedButton(
                    modifier = Modifier.padding(horizontal = 100.dp),
                    textContent = stringResource(id = R.string.text_register),
                    onClick = {
                        onClick(RegisterEvents.onClick(TypeClick.Register))
                    },
                    enabled = !state.isLoading
                )
            }
            ClickableText(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = AnnotatedString("Ya tienes cuenta? Clickeame."),
                style = TextStyle(color = GreenLight),
                onClick = {
                    onClick(RegisterEvents.onClick(TypeClick.GoToLogin))
                }
            )
        }
    }
}