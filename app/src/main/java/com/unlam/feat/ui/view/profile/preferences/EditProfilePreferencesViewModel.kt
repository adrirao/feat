package com.unlam.feat.ui.view.profile.preferences

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.di.ResourcesProvider
import com.unlam.feat.model.request.RequestUpdatePerson
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.unlam.feat.util.Result


@HiltViewModel
class EditProfilePreferencesViewModel @Inject
constructor(
    private val resourcesProvider: ResourcesProvider,
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {


    private val _state = mutableStateOf(EditProfilePreferencesState())
    val state: State<EditProfilePreferencesState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getPreferences()
    }

    fun onEvent(event: EditProfilePreferencesEvent) {
        when (event) {
            is EditProfilePreferencesEvent.EnteredMinAge -> {
                _state.value = _state.value.copy(
                    minAge = event.value
                )
            }
            is EditProfilePreferencesEvent.EnteredMaxAge -> {
                _state.value = _state.value.copy(
                    maxAge = event.value
                )
            }
            is EditProfilePreferencesEvent.EnteredWillingDistance -> {
                _state.value = _state.value.copy(
                    willingDistance = event.value
                )
            }
            is EditProfilePreferencesEvent.EnteredNotifications -> {
                _state.value = _state.value.copy(
                    notifications = event.value
                )
            }
        }
    }

    fun getPreferences(){
        val uId = firebaseAuthRepository.getUserId();
        featRepository.getDetailProfile(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        EditProfilePreferencesState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = EditProfilePreferencesState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = EditProfilePreferencesState(
                        person = result.data!!.person,
                        minAge = result.data.person.minAge.toString(),
                        maxAge = result.data.person.maxAge.toString(),
                        willingDistance = result.data.person.willingDistance.toString(),
                        notifications = result.data.person.notifications.toBoolean()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updatePersonPreferences(){
        var notification = 0
        if(_state.value.notifications){
            notification = 1
        }
        val request = RequestUpdatePerson(
            id= _state.value.person!!.id,
            minAge = _state.value.minAge.toInt(),
            maxAge = _state.value.maxAge.toInt(),
            willingDistance = _state.value.willingDistance.toInt(),
            notifications =  notification,
        )

        featRepository.updatePerson(request).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = EditProfilePreferencesState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = EditProfilePreferencesState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = EditProfilePreferencesState(isUpdatedMessage = result.data)
                    _state.value = _state.value.copy(
                        isSuccessSubmitData = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


}