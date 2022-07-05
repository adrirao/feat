package com.unlam.feat.ui.view.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unlam.feat.ui.component.*
import com.unlam.feat.ui.theme.*
import java.text.SimpleDateFormat

@Composable
fun ChatScreen(
    state: ChatState,
    onEvent: (ChatEvents) -> Unit,
    onClick: (ChatEvents.OnClick.TypeClick) -> Unit
) {
    val messagesList = state.messages
    val event = state.event
    val user = Firebase.auth.uid

    FeatContent {
        FeatHeader(
            text = event!!.name,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true,
            contentPadding = rememberInsetsPaddingValues(insets = LocalWindowInsets.current.systemBars),
            content = {
                items(messagesList) { message ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = when (message.from) {
                            user -> Alignment.End
                            else -> Alignment.Start
                        }
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(.8f)
                                .align(if (message.from == user) Alignment.End else Alignment.Start)
                                .padding(vertical = 2.dp),
                            backgroundColor = if (message.from == user) PurpleMedium else PurpleDark,
                            shape = if (message.from == user) RoundedCornerShape(
                                topStart = 20f,
                                bottomStart = 20f,
                                topEnd = 20f
                            ) else RoundedCornerShape(
                                topEnd = 20f,
                                bottomEnd = 20f,
                                topStart = 20f
                            ),
                            elevation = 0.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(vertical = 10.dp, horizontal = 20.dp)
                            ) {
                                if (message.from != user) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 4.dp),
                                        text = "@${message.from}",
                                        fontSize = MaterialTheme.typography.body2.fontSize,
                                        textAlign = TextAlign.End,
                                        color = GreenColor,
                                    )
                                }
                                Box {
                                    Text(
                                        modifier = Modifier.align(if (message.from == user) Alignment.BottomStart else Alignment.BottomEnd),
                                        text = SimpleDateFormat("hh:mm").format(message.dob),
                                        textAlign = if (message.from == user) TextAlign.Start else TextAlign.End,
                                        fontSize = 10.sp
                                    )
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        text = message.message,
                                        textAlign = if (message.from == user) TextAlign.End else TextAlign.Start,
                                        fontSize = MaterialTheme.typography.body1.fontSize
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FeatOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    paddingValues = PaddingValues(0.dp),
                    text = state.textMessage,
                    onValueChange = { onEvent(ChatEvents.OnChangeValue(it)) },
                    textLabel = "Mensaje...",
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable {
                                onClick(ChatEvents.OnClick.TypeClick.OnClickSendMessage(state.textMessage))
                            },
                            imageVector = Icons.Outlined.Send,
                            contentDescription = null,
                            tint = GreenColor
                        )
                    }
                )
            }
        }
    }
}

private data class Chat(
    val name: String,
    val id: String,
    val users: List<String>
)

