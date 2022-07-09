package com.unlam.feat.ui.view.info_player

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.repository.FirebaseFirestoreRepositoryImp
import com.unlam.feat.repository.FirebaseStorageRepositoryImp
import com.unlam.feat.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class InfoPlayerViewModel
@Inject
constructor(
    private val firebaseStorageRepository: FirebaseStorageRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val featRepository: FeatRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(InfoPlayerState())
    val state: State<InfoPlayerState> = _state

    fun getInfoPlayer(idPlayer: String) {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.findAllQualificationsByPlayer(idPlayer, uId).onEach { result ->
            when (result) {
                is Result.Success -> {
                    firebaseStorageRepository.getFile(uId, isSuccess = { uri ->
                        _state.value = InfoPlayerState(
                            qualifications = result.data!!.qualifications,
                            person = result.data.person,
                            image = uri.toString()
                        )
                    })
                }
                is Result.Error -> {
                    _state.value = InfoPlayerState(
                        error = result.message.toString()
                    )
                }
                is Result.Loading -> {
                    _state.value = InfoPlayerState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}