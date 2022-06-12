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
                                TypeValueChange.onValueChangeEmail,
                                it
                            )
                        )
                    }
                )
                FeatOutlinedTextField(
                    text = state.textVerifyEmail,
                    unFocusedColor = PurpleLight,
                    textLabel = stringResource(id = R.string.text_re_email),
                    onValueChange = {
                        onValueChange(
                            RegisterEvents.onValueChange(
                                TypeValueChange.onValueChangeReEmail,
                                it
                            )
                        )
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
                                TypeValueChange.onValueChangePassword,
                                it
                            )
                        )
                    },
                    isPasswordVisible = state.isVisiblePassword,
                    onPasswordToggleClick = {
                        onClick(RegisterEvents.onClick(TypeClick.toggledPassword))
                    }
                )
                FeatOutlinedTextField(
                    text = state.textVerifyPassword,
                    unFocusedColor = PurpleLight,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.None,
                    textLabel = stringResource(id = R.string.text_re_password),
                    onValueChange = {
                        onValueChange(
                            RegisterEvents.onValueChange(
                                TypeValueChange.onValueChangeRePassword,
                                it
                            )
                        )
                    },
                    isPasswordVisible = state.isVisibleRePassword,
                    onPasswordToggleClick = {
                        onClick(RegisterEvents.onClick(TypeClick.toggledRePassword))
                    },
                )
                FeatOutlinedButton(
                    modifier = Modifier.padding(horizontal = 100.dp),
                    textContent = stringResource(id = R.string.text_register),
                    onClick = {}
                )
            }
            ClickableText(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = AnnotatedString("Ya tienes cuenta? Clickeame."),
                style = TextStyle(color = GreenLight),
                onClick = {
                    onClick(RegisterEvents.onClick(TypeClick.goToLogin))
                }
            )
        }
    }
}