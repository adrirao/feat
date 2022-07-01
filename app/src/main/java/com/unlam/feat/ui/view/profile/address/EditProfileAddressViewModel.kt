package com.unlam.feat.ui.view.profile.address

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.model.request.RequestAddress
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
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

    init {
        val uId = firebaseAuthRepository.getUserId()
        getPerson(uId)
    }


    fun onEvent(event: EditProfileAddressEvent) {
        when (event) {
            is EditProfileAddressEvent.EnteredAddressAlias -> {
                _state.value = _state.value.copy(
                    addressAlias = event.value
                )
            }
            is EditProfileAddressEvent.EnteredAddressStreet -> {
                _state.value = _state.value.copy(
                    addressStreet = event.value
                )
            }
            is EditProfileAddressEvent.EnteredAddressNumber -> {
                _state.value = _state.value.copy(
                    addressNumber = event.value
                )
            }
            is EditProfileAddressEvent.EnteredAddressTown -> {
                _state.value = _state.value.copy(
                    addressTown = event.value
                )
            }
            is EditProfileAddressEvent.EnteredAddressZipCode -> {
                _state.value = _state.value.copy(
                    addressZipCode = event.value
                )
            }
            is EditProfileAddressEvent.EnteredAddressLatitude -> {
                _state.value = _state.value.copy(
                    addressLatitude = event.value
                )
            }
            is EditProfileAddressEvent.EnteredAddressLongitude -> {
                _state.value = _state.value.copy(
                    addressLongitude = event.value
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
            is EditProfileAddressEvent.SubmitData -> {
                validateStreetIsNotNUll(state.value.addressStreet)
                validateNumberIsNotNUll(state.value.addressNumber)
                validateTownIsNotNUll(state.value.addressTown)
                validateZipCodeIsNotNUll(state.value.addressZipCode)
                validateField()
                addAddress()

            }
        }
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
        val addressAlias = state.value.addressAlias
        val addressStreet =
            if (state.value.addressStreetError == null) _state.value.addressStreet else return
        val addressNumber =
            if (state.value.addressNumberError == null) _state.value.addressNumber else return
        val addressTown =
            if (state.value.addressTownError == null) _state.value.addressTown else return
        val addressZipCode =
            if (state.value.addressZipCodeError == null) _state.value.addressZipCode else return
        val addressLatitude =
            if (state.value.addressLatitude != "") _state.value.addressLatitude else return
        val addressLongitude =
            if (state.value.addressLongitude != "") _state.value.addressLongitude else return
        val personId = if (state.value.personId != null) _state.value.personId else return

        val request = RequestAddress(
            alias = addressAlias,
            street = addressStreet,
            number = addressNumber,
            town = addressTown,
            zipCode = addressZipCode,
            latitude = addressLatitude,
            logitude = addressLongitude,
            person = requireNotNull(personId)
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
                        error = result.message!!
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun validateField() {

        var fieldError: String = ""

        if (state.value.addressStreetError == EditProfileAddressState.AddressStreetError.FieldEmpty) fieldError += "Calle, "
        if (state.value.addressNumberError == EditProfileAddressState.AddressNumberError.FieldEmpty) fieldError += "Numero, "
        if (state.value.addressTownError == EditProfileAddressState.AddressTownError.FieldEmpty) fieldError += "Ciudad, "
        if (state.value.addressZipCodeError == EditProfileAddressState.AddressZipCodeError.FieldEmpty) fieldError += "Codigo postal"

        if (fieldError != "") {
            _state.value = _state.value.copy(
                fieldEmpty = fieldError
            )
        }
    }


    private fun validateStreetIsNotNUll(street: String) {
        val trimmedLastName = street.trim()
        if (trimmedLastName.isBlank()) {
            _state.value = _state.value.copy(
                addressStreetError = EditProfileAddressState.AddressStreetError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(addressStreetError = null)

    }

    private fun validateNumberIsNotNUll(number: String) {
        val trimmedLastName = number.trim()
        if (trimmedLastName.isBlank()) {
            _state.value = _state.value.copy(
                addressNumberError = EditProfileAddressState.AddressNumberError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(addressNumberError = null)

    }

    private fun validateTownIsNotNUll(town: String) {
        val trimmedLastName = town.trim()
        if (trimmedLastName.isBlank()) {
            _state.value = _state.value.copy(
                addressTownError = EditProfileAddressState.AddressTownError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(addressTownError = null)

    }

    private fun validateZipCodeIsNotNUll(zipCode: String) {
        val trimmedLastName = zipCode.trim()
        if (trimmedLastName.isBlank()) {
            _state.value = _state.value.copy(
                addressZipCodeError = EditProfileAddressState.AddressZipCodeError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(addressZipCodeError = null)

    }
}