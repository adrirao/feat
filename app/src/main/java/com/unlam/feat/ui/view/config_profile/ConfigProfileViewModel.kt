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
import java.time.LocalTime
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
                    TypeValueChange.OnValueChangeStartTimeSunday -> {
                        _state.value = _state.value.copy(
                            startTimeSunday = event.valueLocalTimeOpt
                        )

                    }
                    TypeValueChange.OnValueChangeEndTimeSunday -> {
                        _state.value = _state.value.copy(
                            endTimeSunday = event.valueLocalTimeOpt
                        )
                    }
                    TypeValueChange.OnValueChangeMondayIsChecked -> {
                        _state.value = _state.value.copy(
                            mondayIsChecked = event.valueBooleanOpt!!
                        )
                    }
                    TypeValueChange.OnValueChangeStartTimeMonday -> {
                        _state.value = _state.value.copy(
                            startTimeMonday = event.valueLocalTimeOpt
                        )

                    }
                    TypeValueChange.OnValueChangeEndTimeMonday -> {

                        _state.value = _state.value.copy(
                            endTimeMonday = event.valueLocalTimeOpt
                        )

                    }
                    TypeValueChange.OnValueChangeTuesdayIsChecked -> {
                        _state.value = _state.value.copy(
                            tuesdayIsChecked = event.valueBooleanOpt!!
                        )
                    }
                    TypeValueChange.OnValueChangeStartTimeTuesday -> {

                        _state.value = _state.value.copy(
                            startTimeTuesday = event.valueLocalTimeOpt
                        )

                    }
                    TypeValueChange.OnValueChangeEndTimeTuesday -> {

                        _state.value = _state.value.copy(
                            endTimeTuesday = event.valueLocalTimeOpt
                        )

                    }
                    TypeValueChange.OnValueChangeWednesdayIsChecked -> {
                        _state.value = _state.value.copy(
                            wednesdayIsChecked = event.valueBooleanOpt!!
                        )
                    }
                    TypeValueChange.OnValueChangeStartTimeWednesday -> {

                        _state.value = _state.value.copy(
                            startTimeWednesday = event.valueLocalTimeOpt
                        )

                    }
                    TypeValueChange.OnValueChangeEndTimeWednesday -> {

                        _state.value = _state.value.copy(
                            endTimeWednesday = event.valueLocalTimeOpt
                        )

                    }
                    TypeValueChange.OnValueChangeThursdayIsChecked -> {
                        _state.value = _state.value.copy(
                            thursdayIsChecked = event.valueBooleanOpt!!
                        )
                    }
                    TypeValueChange.OnValueChangeStartTimeThursday -> {

                        _state.value = _state.value.copy(
                            startTimeThursday = event.valueLocalTimeOpt
                        )

                    }
                    TypeValueChange.OnValueChangeEndTimeThursday -> {

                        _state.value = _state.value.copy(
                            endTimeThursday = event.valueLocalTimeOpt
                        )

                    }
                    TypeValueChange.OnValueChangeFridayIsChecked -> {
                        _state.value = _state.value.copy(
                            fridayIsChecked = event.valueBooleanOpt!!
                        )
                    }
                    TypeValueChange.OnValueChangeStartTimeFriday -> {

                        _state.value = _state.value.copy(
                            startTimeFriday = event.valueLocalTimeOpt
                        )

                    }
                    TypeValueChange.OnValueChangeEndTimeFriday -> {

                        _state.value = _state.value.copy(
                            endTimeFriday = event.valueLocalTimeOpt
                        )

                    }
                    TypeValueChange.OnValueChangeSaturdayIsChecked -> {
                        _state.value = _state.value.copy(
                            saturdayIsChecked = event.valueBooleanOpt!!
                        )
                    }
                    TypeValueChange.OnValueChangeStartTimeSaturday -> {

                        _state.value = _state.value.copy(
                            startTimeSaturday = event.valueLocalTimeOpt
                        )

                    }
                    TypeValueChange.OnValueChangeEndTimeSaturday -> {

                        _state.value = _state.value.copy(
                            endTimeSaturday = event.valueLocalTimeOpt
                        )

                    }
                    TypeValueChange.OnValueChangeMinAge -> {
                        _state.value = _state.value.copy(
                            minAge = event.value
                        )

                    }
                    TypeValueChange.OnValueChangeMaxAge -> {
                        _state.value = _state.value.copy(
                            maxAge = event.value
                        )

                    }
                    TypeValueChange.OnValueChangeWillingDistance -> {
                        _state.value = _state.value.copy(
                            willingDistance = event.value
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
                _state.value = _state.value.copy(
                    sundayError = validateDayIsValidRange(
                        state.value.startTimeSunday,
                        state.value.endTimeSunday,
                        state.value.sundayIsChecked
                    ),
                    mondayError = validateDayIsValidRange(
                        state.value.startTimeMonday,
                        state.value.endTimeMonday,
                        state.value.mondayIsChecked
                    ),
                    tuesdayError = validateDayIsValidRange(
                        state.value.startTimeTuesday,
                        state.value.endTimeTuesday,
                        state.value.tuesdayIsChecked
                    ),
                    wednesdayError = validateDayIsValidRange(
                        state.value.startTimeWednesday,
                        state.value.endTimeWednesday,
                        state.value.wednesdayIsChecked
                    ),
                    thursdayError = validateDayIsValidRange(
                        state.value.startTimeThursday,
                        state.value.endTimeThursday,
                        state.value.thursdayIsChecked
                    ),
                    fridayError = validateDayIsValidRange(
                        state.value.startTimeFriday,
                        state.value.endTimeFriday,
                        state.value.fridayIsChecked
                    ),
                    saturdayError = validateDayIsValidRange(
                        state.value.startTimeSaturday,
                        state.value.endTimeSaturday,
                        state.value.saturdayIsChecked
                    ),

                )
                validateAgeIsNotEmptyAndValid(_state.value.minAge,_state.value.maxAge)
                validateWillingDistance(_state.value.willingDistance)
            }

        }
    }

    private fun validateWillingDistance(willingDistance: String) {
        val trimmedName = willingDistance.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                willingDistanceError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        }
    }
    private fun validateAgeIsNotEmptyAndValid(minAge: String, maxAge: String) {
        val trimmedMinAge = minAge.trim()
        val trimmedMaxAge = maxAge.trim()

        if (trimmedMaxAge.isBlank() && trimmedMinAge.isBlank()) {
            _state.value = _state.value.copy(
                ageError = ConfigProfileState.RangeAgeError.FieldEmpty
            )
            return
        }
        if (trimmedMinAge.isBlank()) {
            _state.value = _state.value.copy(
                ageError = ConfigProfileState.RangeAgeError.MinAgeEmpty
            )
            return
        }
        if (trimmedMaxAge.isBlank()) {
            _state.value = _state.value.copy(
                ageError = ConfigProfileState.RangeAgeError.MaxAgeEmpty
            )
            return
        }

        if (minAge.toInt() > maxAge.toInt()) {
            _state.value = _state.value.copy(
                ageError = ConfigProfileState.RangeAgeError.IsInvalidRange
            )
            return
        }

        _state.value = _state.value.copy(ageError = null)

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

    private fun validateDayIsValidRange(
        dayStar: LocalTime?,
        dayEnd: LocalTime?,
        dayIsChecked: Boolean
    ): ConfigProfileState.DayError? {
        if (dayIsChecked) {
            if (dayEnd == null && dayStar == null) {
                return ConfigProfileState.DayError.TimeEmpty
            }
            if (dayStar == null) {
                return ConfigProfileState.DayError.StarTimeEmpty
            }
            if (dayEnd == null) {
                return ConfigProfileState.DayError.EndTimeEmpty
            }
            if (dayStar.isAfter(dayEnd)) {
                return ConfigProfileState.DayError.WrongTimeRange
            }
        }
        return null
    }

}