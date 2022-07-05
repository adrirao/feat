package com.unlam.feat.ui.view.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unlam.feat.ui.component.FeatContent
import com.unlam.feat.ui.component.FeatHeader
import com.unlam.feat.ui.component.FeatOutlinedButtonIcon
import com.unlam.feat.ui.theme.GreenColor
import com.unlam.feat.ui.theme.PurpleDark
import java.util.*

@Preview
@Composable
fun ChatScreen() {
    val db = Firebase.firestore
    val chatId = "ab12204e-7600-42f0-a53b-5076b3718c13"
    val messagesList = remember { mutableStateOf(listOf<Message>()) }
    val user = "nicolas"
//    val chat = Chat(
//        name = "Evento de papi",
//        id = chatId,
//        users = listOf("nahu","nico","mati","adrian")
//    )
//
//    db.collection("chats").document(chatId).set(chat)
    val chatRef = db.collection("chats").document(chatId)
    chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
        .get()
        .addOnSuccessListener { messages ->
            val listMessage = messages.toObjects(Message::class.java)
            messagesList.value = listMessage
        }
    chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
        .addSnapshotListener { messages, error ->
            if (error == null) {
                messages?.let {
                    val listMessage = messages.toObjects(Message::class.java)
                    messagesList.value = listMessage
                }
            }
        }
    FeatContent {
        FeatHeader(text = "Nombre Evento", fontSize = MaterialTheme.typography.h6.fontSize)
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true,
            contentPadding = rememberInsetsPaddingValues(insets = LocalWindowInsets.current.systemBars),
            content = {
                items(messagesList.value) { message ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                            .background(if (message.from == user) GreenColor else PurpleDark)
                            .padding(10.dp),
                        contentAlignment = if (message.from == user) Alignment.BottomEnd else Alignment.BottomStart
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = message.message,
                            textAlign = if (message.from == user) TextAlign.End else TextAlign.Start
                        )
                    }
                }
            }
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Transparent
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val text = remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(value = text.value, onValueChange = { text.value = it })
                FeatOutlinedButtonIcon(
                    icon = Icons.Outlined.Send,
                    onClick = {
                        val message = Message(
                            message = text.value.text,
                            from = "nicolas"
                        )
                        db.collection("chats").document(chatId).collection("messages").document()
                            .set(message)
//                        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
//                            .get()
//                            .addOnSuccessListener { messages ->
//                                val listMessage = messages.toObjects(Message::class.java)
//                                messagesList.value = listMessage
//                            }
                        text.value = TextFieldValue()
                    },
                    contentColor = GreenColor,
                    textColor = GreenColor
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

private data class Message(
    val dob: Date = Date(),
    val message: String = "",
    val from: String = ""
)