package com.unlam.feat.ui.view.config_profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.event.new_event.NewEventEvents
import com.unlam.feat.ui.view.event.new_event.NewEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ConfigProfileViewModel @Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val featRepository: FeatRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(ConfigProfileState())
    val state: State<ConfigProfileState> = _state

    fun onEvent(event: ConfigProfileEvents) {
        when (event) {
            is ConfigProfileEvents.onValueChange -> {
                when (event.typeValueChange) {
                    TypeValueChange.OnValueChangeLastName -> {
                        _state.value = _state.value.copy(
                            lastName = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeName -> {
                        _state.value = _state.value.copy(
                            name = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeDate -> {
                        _state.value = _state.value.copy(
                            dateOfBirth = LocalDate.parse(event.value)
                        )
                    }
                    TypeValueChange.OnValueChangeNickname -> {
                        _state.value = _state.value.copy(
                            nickname = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeSex -> {
                        _state.value = _state.value.copy(
                            sex = event.value
                        )
                    }
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
                    TypeValueChange.OnValueChangeAddressAlias -> {
                        _state.value = _state.value.copy(
                            addressAlias = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeSundayIsChecked -> {
                        _state.value = _state.value.copy(
                            sundayIsChecked = event.valueBooleanOpt!!
                        )
                    }

                }
            }
            ConfigProfileEvents.onClick(TypeClick.DismissDialog) -> {
                _state.value = _state.value.copy(

                )
            }
            ConfigProfileEvents.onClick(TypeClick.Submit) -> {
                validateLastName(_state.value.lastName)
                validateName(_state.value.name)
                validateDateOfBirth(_state.value.dateOfBirth)
                validateNickname(_state.value.nickname)
                validateSex(_state.value.sex)
                validateAddress(_state.value.address)
                validateAddressAlias(_state.value.addressAlias)

            }

        }
    }

    private fun validateLastName(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                lastNameError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        }
    }

    private fun validateName(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                nameError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        }
    }

    private fun validateDateOfBirth(date: LocalDate?) {
        if (date == null) {
            _state.value = _state.value.copy(
                dateOfBirthError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        } else if (date.isBefore(LocalDate.now())) {
            _state.value = _state.value.copy(
                dateOfBirthError = ConfigProfileState.DateError.DateInvalid
            )
        }
    }

    private fun validateNickname(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                nicknameError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        }
    }

    private fun validateSex(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                sexError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        }
    }

    private fun validateAddress(address: String) {
        val trimmedAddress = address.trim()
        if (trimmedAddress.isBlank()) {
            _state.value = _state.value.copy(
                addressError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        }
    }

    private fun validateAddressAlias(address: String) {
        val trimmedAddress = address.trim()
        if (trimmedAddress.isBlank()) {
            _state.value = _state.value.copy(
                addressAliasError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        }
    }

}