package com.unlam.feat.ui.view.config_profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.util.TypeValueChange
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
                when(event.typeValueChange){
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
                    TypeValueChange.OnValueChangeAddressAlias-> {
                        _state.value = _state.value.copy(
                            addressAlias = event.value
                        )
                    }
                }
            }

        }
    }
}