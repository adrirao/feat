package com.unlam.feat.ui.view.profile

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.di.ResourcesProvider
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.repository.FirebaseStorageRepository
import com.unlam.feat.repository.FirebaseStorageRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.unlam.feat.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val firebaseStorageRepository: FirebaseStorageRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing


    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    error = ""
                )
            }
            is ProfileEvent.UploadImage -> {
                uploadImage(event.image)
            }
            is ProfileEvent.SingOutUser -> {
                _state.value = _state.value.copy(

                )
                firebaseAuthRepository.signOut()

            }
        }
    }


    fun getDetailProfile() {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getDetailProfile(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        ProfileState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = ProfileState(isLoading = true)
                }
                is Result.Success -> {
                    firebaseStorageRepository.getFile(uId, isSuccess = {uri ->
                        _state.value = ProfileState(
                            person = result.data!!.person,
                            availabilities = result.data.person.availabilities,
                            players = result.data.players,
                            addresses = result.data.addresses,
                            image = uri.toString()
                        )
                    })
                }
            }
        }.launchIn(viewModelScope)
    }

    private  fun uploadImage(image: Bitmap) {
        viewModelScope.launch {
            val uId = firebaseAuthRepository.getUserId()
            firebaseStorageRepository.putFile(image, uId)
        }
    }
}