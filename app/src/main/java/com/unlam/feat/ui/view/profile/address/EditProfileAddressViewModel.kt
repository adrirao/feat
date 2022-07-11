package com.unlam.feat.ui.view.profile.address

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.model.request.RequestAddress
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.util.TypeValueChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.unlam.feat.util.Result


@HiltViewModel
class EditProfileAddressViewModel @Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val featRepository: FeatRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(EditProfileAddressState())
    val state: State<EditProfileAddressState> = _state

    fun onEvent(event: EditProfileAddressEvent) {
        when (event) {
            is EditProfileAddressEvent.EnteredAddressAlias -> {
                _state.value = _state.value.copy(
                    addressAlias = event.value
                )
            }
            is EditProfileAddressEvent.EnteredAddressStreet -> {
                _state.value = _state.value.copy(
                    address = event.value
                )
            }
            is EditProfileAddressEvent.ShowAlertPermission -> {
                _state.value = _state.value.copy(
                    showAlertPermission = event.value,
                    titleAlert = event.title,
                    descriptionAlert = event.description
                )
            }
            is EditProfileAddressEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    showAlertPermission = false,
                    addressStreetError = null,
                    addressNumberError = null,
                    addressTownError = null,
                    addressZipCodeError = null,
                    fieldEmpty = ""
                )
            }
            is EditProfileAddressEvent.SingOutUser -> {
                firebaseAuthRepository.signOut()
            }
            is EditProfileAddressEvent.onValueChange -> {
                when(event.typeValueChange){
                    TypeValueChange.OnValueChangePosition -> {
                        _state.value = _state.value.copy(
                            latitude = event.value,
                            longitude = event.valueOpt!!
                        )
                    }
                    TypeValueChange.OnValueChangeAddress -> {
                        _state.value = _state.value.copy(
                            address = event.value
                        )
                    }
                }
            }
            is EditProfileAddressEvent.SubmitData -> {
                addAddress()
            }
        }
    }

    fun setIdPerson(idPerson:Int){
        _state.value = _state.value.copy(
            personId = idPerson
        )
    }

    private fun getPerson(uId: String) {
        featRepository.getPerson(uId).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = EditProfileAddressState(personId = result.data?.id)

                }
                is Result.Loading -> {
                    _state.value = EditProfileAddressState(isLoading = true)

                }
                is Result.Error -> {
                    _state.value = EditProfileAddressState(
                        personError = result.message ?: "Error Inesperado"
                    )
                }
            }

        }.launchIn(viewModelScope)
    }


    private fun addAddress() {
        val request = RequestAddress(
            alias = _state.value.addressAlias,
            street = _state.value.address,
            number = "1",
            town = "1",
            zipCode = "1",
            latitude = _state.value.latitude,
            longitude = _state.value.longitude,
            person = requireNotNull(_state.value.personId)
        )

        featRepository.addAddress(request).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        isSuccessSubmitData = true
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message!!,
                        isErrorSubmitData = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}