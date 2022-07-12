package com.unlam.feat.ui.view.profile.sport

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.view.profile.address.EditProfileAddressState
import com.unlam.feat.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EditProfileSportViewModel @Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val featRepository: FeatRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(EditProfileSportState())
    val state: State<EditProfileSportState> = _state

    init {
        getPlayersByUserSportList()
    }

    fun onEvent(event: EditProfileSportEvent) {

    }




    private fun getPlayersByUserSportList() {
        val uId = firebaseAuthRepository.getUserId()

        featRepository.getPlayersUserAndSportList(uId).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = EditProfileSportState(
                        playersUser = result.data?.players,
                        sportsList = result.data?.sportGenericList)

                }
                is Result.Loading -> {
                    _state.value = EditProfileSportState(isLoading = true)

                }
                is Result.Error -> {
                    _state.value = EditProfileSportState(
                        error = result.message ?: "Error Inesperado"
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getDataSportScreen(sportGenericId: Int) {
        featRepository.getDataSportScreen(sportGenericId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        state.value.copy(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }
                is Result.Success -> {
                    _state.value = state.value.copy(
                        levelList = result.data!!.levelList,
                        positionList = result.data.positionList,
                        valuationList = result.data.valuationList,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}