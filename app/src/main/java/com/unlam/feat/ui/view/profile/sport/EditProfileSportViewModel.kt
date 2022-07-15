package com.unlam.feat.ui.view.profile.sport

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.model.request.RequestPlayer
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.config_profile.ConfigProfileEvents
import com.unlam.feat.ui.view.profile.address.EditProfileAddressState
import com.unlam.feat.ui.view.profile.preferences.EditProfilePreferencesEvent
import com.unlam.feat.ui.view.search.SearchState
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
        when (event) {
            is EditProfileSportEvent.onValueChange -> {
                when (event.typeValueChange) {
                    TypeValueChange.OnValueChangeSelectSport -> {
                        getDataSportScreen(event.value.toInt())
                    }
                    TypeValueChange.OnValueChangeTypeSport -> {
                        _state.value = _state.value.copy(
                            sportId = event.value
                        )
                    }
                    TypeValueChange.OnValueChangePositionSport -> {
                        _state.value = _state.value.copy(
                            positionIdSport = event.value.toInt()
                        )
                    }
                    TypeValueChange.OnValueChangeLevelSport -> {
                        _state.value = _state.value.copy(
                            levelIdSport = event.value.toInt()
                        )
                    }
                    TypeValueChange.OnValueChangeValuationSport -> {
                        _state.value = _state.value.copy(
                            valuationIdSport = event.value.toInt()
                        )
                    }
                    TypeValueChange.OnValueChangeAbilitiesSport -> {
                        _state.value = _state.value.copy(
                            abilitiesSport = event.value
                        )
                    }
                }
            }
            is EditProfileSportEvent.onClick -> {
                when (event.typeClick) {
                    TypeClick.Submit -> {
                        createPlayer()
                    }
                }
            }
        }
    }


    private fun getPlayersByUserSportList() {
        val uId = firebaseAuthRepository.getUserId()

        featRepository.getPlayersUserAndSportList(uId).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = EditProfileSportState(
                        playersUser = result.data?.players,
                        sportsList = result.data?.sportGenericList,
                        personId = result.data?.person?.id
                    )

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

    private fun createPlayer() {
        val req = RequestPlayer(
            abilities = _state.value.abilitiesSport,
            sport = _state.value.sportId.toInt(),
            person = _state.value.personId!!,
            level = _state.value.levelIdSport,
            position = _state.value.positionIdSport,
            valuation = _state.value.valuationIdSport!!.toInt()
        )
        featRepository.createPlayer(req).onEach { result ->
            when (result) {
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Result.Success -> {
                    _state.value = EditProfileSportState(
                        isSuccessSubmitData = true
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        isErrorSubmitData = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}