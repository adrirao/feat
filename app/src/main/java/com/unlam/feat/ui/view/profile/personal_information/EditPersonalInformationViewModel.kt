package com.unlam.feat.ui.view.profile.personal_information

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.di.ResourcesProvider
import com.unlam.feat.model.request.RequestUpdatePersonPersonalInformation
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.config_profile.ConfigProfileEvents
import com.unlam.feat.ui.view.config_profile.ConfigProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import com.unlam.feat.util.Result

@HiltViewModel
class EditPersonalInformationViewModel @Inject constructor(
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(EditPersonalInformationState())
    val state: State<EditPersonalInformationState> = _state

    private val _isRefreshing = MutableStateFlow(false)

    init {
        getPerson()
    }

    fun onEvent(event: EditProfilePersonalInformationEvent) {
        when (event) {
            is EditProfilePersonalInformationEvent.onValueChange -> {
                when (event.typeValueChange){
                    TypeValueChange.OnValueChangeLastName -> {
                        _state.value = _state.value.copy(
                            lastname = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeName -> {
                        _state.value = _state.value.copy(
                            names = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeDate -> {
                        _state.value = _state.value.copy(
                            birthDate = event.value
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
                }
            }
            EditProfilePersonalInformationEvent.onClick(TypeClick.Submit) -> {
                validateLastName(_state.value.lastname)
                validateName(_state.value.names)
                validateDateOfBirth(_state.value.birthDate)
                validateNickname(_state.value.nickname)
                validateSex(_state.value.sex)
                updatePerson()
            }
        }
    }

    fun updatePerson(){
        val name = if (_state.value.nameError == null) _state.value.names else return
        val lastName = if (_state.value.lastnameError == null) _state.value.lastname else return
        val dateOfBirth =
            if (_state.value.birthDateError == null) _state.value.birthDate else return
        val nickname = if (_state.value.nicknameError == null) _state.value.nickname else return
        val sex = if (_state.value.sexError == null) _state.value.sex else return

        val request = RequestUpdatePersonPersonalInformation(
            id= _state.value.personId.toInt(),
            names = name,
            lastname = lastName,
            birthDate = dateOfBirth,
            sex = sex,
            nickname = nickname,
        )

        featRepository.updatePersonPersonalInformation(request).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        isErrorSubmitData = true,
                        error = result.message ?: "Error Inesperado"
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        isSuccessSubmitData = true,
                        isLoading = false,
                        isUpdatedMessage = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPerson(){

        val uId = firebaseAuthRepository.getUserId();
        featRepository.getPerson(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        EditPersonalInformationState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = EditPersonalInformationState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = EditPersonalInformationState(
                        personId = result.data?.id.toString(),
                        names = result.data?.names.toString(),
                        lastname = result.data?.lastname.toString(),
                        birthDate = result.data?.birthDate.toString(),
                        nickname = result.data?.nickname.toString(),
                        sex = result.data?.sex.toString(),
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun validateLastName(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                lastnameError = EditPersonalInformationState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(lastnameError = null)
    }
    private fun validateName(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                nameError = EditPersonalInformationState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(nameError = null)
    }
    private fun validateDateOfBirth(dateString: String?) {
        val date = LocalDate.parse(dateString?.substring(0, 10)?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        if (date == null) {
            _state.value = _state.value.copy(
                birthDateError = EditPersonalInformationState.DateError.DateInvalid
            )
            return
        } else if (!date.isBefore(LocalDate.now().minusYears(16))) {
            _state.value = _state.value.copy(
                birthDateError = EditPersonalInformationState.DateError.IsNotOfLegalAge
            )
            return
        }
        _state.value = _state.value.copy(birthDateError = null)
    }
    private fun validateNickname(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                nicknameError = EditPersonalInformationState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(nicknameError = null)
    }
    private fun validateSex(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                sexError = EditPersonalInformationState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(sexError = null)
    }
}