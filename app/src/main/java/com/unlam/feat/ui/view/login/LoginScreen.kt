package com.unlam.feat.ui.view.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.GreenColor20
import com.unlam.feat.ui.theme.GreenLight
import com.unlam.feat.ui.theme.PurpleLight
import com.unlam.feat.ui.theme.GreenColor
import com.unlam.feat.ui.util.Screen
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange

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
                FeatText(text = "Login")
                FeatSpacerMedium()
                FeatForm {
                    FeatOutlinedTextField(
                        text = state.textEmail,
                        textLabel = "Email",
                        unFocusedColor = PurpleLight,
                        onValueChange = {
                            onValueChange(
                                LoginEvents.onValueChange(
                                    TypeValueChange.OnValueChangeEmail,
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
                                    TypeValueChange.OnValueChangePassword,
                                    it
                                )
                            )
                        },
                        isPasswordVisible = state.isVisiblePassword,
                        onPasswordToggleClick = {
                            onClick(LoginEvents.onClick(TypeClick.ToggledPassword))
                        },
                        error = when (state.passwordError) {
                            LoginState.PasswordError.InvalidPassword -> stringResource(R.string.text_invalid_password)
                            LoginState.PasswordError.FieldEmpty -> stringResource(R.string.text_field_empty)
                            LoginState.PasswordError.InputTooShort -> stringResource(R.string.text_input_too_short)
                            else -> ""
                        }
                    )
                    FeatSpacerSmall()
                    FeatOutlinedButton(
                        modifier = Modifier.align(Alignment.End),
                        width = 150.dp,
                        backgroundColor = GreenColor20,
                        contentColor = GreenColor,
                        textContent = "Ingresar",
                        onClick = {
                            onClick(LoginEvents.onClick(TypeClick.Login))
                        },
                        enabled = !state.isLoading
                    )
                }
            }
            ClickableText(
                modifier = Modifier.align(Alignment.BottomCenter),
                style = TextStyle(color = GreenLight),
                text = AnnotatedString("¿No tienes una cuenta?"),
                onClick = {
                    onClick(LoginEvents.onClick(TypeClick.GoToRegister))
                }
            )
        }
    }
}