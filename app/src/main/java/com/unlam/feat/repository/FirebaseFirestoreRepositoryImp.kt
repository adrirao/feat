package com.unlam.feat.repository

import com.google.firebase.firestore.*
import com.unlam.feat.ui.view.chat.Message
import com.unlam.feat.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseFirestoreRepositoryImp
@Inject
constructor(
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseFirestoreRepository {
    override fun createChatEvent(eventId: Int) {
        firebaseFirestore.collection("chats").document(eventId.toString())
    }

    override fun sendMessage(eventId: Int, message: Message) {
        firebaseFirestore.collection("chats").document(eventId.toString()).collection("messages")
            .document()
            .set(message)
    }

    override fun getMessages(eventId: Int): Flow<Result<List<Message>>> = flow {
        emit(Result.Loading())
        val chatRef = firebaseFirestore.collection("chats").document(eventId.toString())

        emit(
            Result.Success(data = chatRef.collection("messages")
                .orderBy("dob", Query.Direction.DESCENDING)
                .get().await().documents.mapNotNull { messages ->
                    messages.toObject(Message::class.java)
                }
            )
        )

    }.catch { error ->
        error.message?.let { errorMessage ->
            emit(Result.Error(message = errorMessage))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getMessageListFlow(eventId: Int): Flow<List<Message>> {
        val chatRef = firebaseFirestore.collection("chats").document(eventId.toString())
        return chatRef.collection("messages").getDataFlow { querySnapshot ->
            querySnapshot?.documents?.map {
                getMessageListFromSnapshot(it)
            } ?: listOf()
        }
    }

    private fun getMessageListFromSnapshot(documentSnapshot: DocumentSnapshot): Message {
        return documentSnapshot.toObject(Message::class.java)!!
    }

    @ExperimentalCoroutinesApi
    fun CollectionReference.getQuerySnapshotFlow(): Flow<QuerySnapshot?> {
        return callbackFlow {
            val listenerRegistration =
                orderBy(
                    "dob",
                    Query.Direction.DESCENDING
                ).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        cancel(
                            message = "error fetching collection data at path - $path",
                            cause = firebaseFirestoreException
                        )
                        return@addSnapshotListener
                    }
                    this.trySend(querySnapshot).isSuccess
                }
            awaitClose {
                listenerRegistration.remove()
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun <T> CollectionReference.getDataFlow(mapper: (QuerySnapshot?) -> T): Flow<T> {
        return getQuerySnapshotFlow()
            .map {
                return@map mapper(it)
            }
    }

}