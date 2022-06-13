package com.unlam.feat.ui.view.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.ui.component.FeatContent
import com.unlam.feat.ui.component.FeatOutlinedButton
import com.unlam.feat.ui.component.FeatOutlinedTextField
import com.unlam.feat.ui.theme.PurpleLight
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.register.RegisterState

@Composable
fun LoginScreen(
    state: LoginState,
    onValueChange: (LoginEvents.onValueChange) -> Unit,
    onClick: (LoginEvents.onClick) -> Unit
) {
    FeatContent {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                FeatOutlinedTextField(
                    text = state.textEmail,
                    textLabel = "Email",
                    unFocusedColor = PurpleLight,
                    onValueChange = {
                        onValueChange(
                            LoginEvents.onValueChange(
                                TypeValueChange.onValueChangeEmail,
                                it
                            )
                        )
                    },
                    error = when (state.emailError) {
                        LoginState.EmailError.FieldEmpty -> {
                            stringResource(R.string.text_field_empty)
                        }
                        LoginState.EmailError.InvalidEmail -> {
                            stringResource(R.string.text_ivalid_email)
                        }
                        else -> ""
                    }
                )
                FeatOutlinedTextField(
                    text = state.textPassword,
                    unFocusedColor = PurpleLight,
                    keyboardType = KeyboardType.Password,
                    textLabel = "Password",
                    onValueChange = {
                        onValueChange(
                            LoginEvents.onValueChange(
                                TypeValueChange.onValueChangePassword,
                                it
                            )
                        )
                    },
                    isPasswordVisible = state.isVisiblePassword,
                    onPasswordToggleClick = {
                        onClick(LoginEvents.onClick(TypeClick.toggledPassword))
                    },
                    error = when (state.passwordError) {
                        LoginState.PasswordError.InvalidPassword -> stringResource(R.string.text_invalid_password)
                        LoginState.PasswordError.FieldEmpty -> stringResource(R.string.text_field_empty)
                        LoginState.PasswordError.InputTooShort -> stringResource(R.string.text_input_too_short)
                        else -> ""
                    }
                )
                FeatOutlinedButton(
                    modifier = Modifier.padding(horizontal = 100.dp),
                    textContent = "Ingresar",
                    onClick = {
                        onClick(LoginEvents.onClick(TypeClick.login))
                    }
                )

            }
            ClickableText(
                modifier = Modifier.align(Alignment.BottomCenter),
                style = TextStyle(color = PurpleLight),
                text = AnnotatedString("No tienes una cuenta?"),
                onClick = {
                    onClick(LoginEvents.onClick(TypeClick.goToRegister))
                }
            )
        }
    }
}