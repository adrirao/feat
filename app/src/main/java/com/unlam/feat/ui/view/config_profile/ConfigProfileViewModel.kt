package com.unlam.feat.ui.view.config_profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.R
import com.unlam.feat.model.request.RequestPersonTransaction
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.event.new_event.NewEventState
import com.unlam.feat.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    init {
        getSportList()
    }

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
                    TypeValueChange.OnValueChangeSelectSport -> {
                        getDataSportScreen(event.value.toInt())
                    }
                    TypeValueChange.OnValueChangePositionSoccer -> {
                        _state.value = _state.value.copy(
                            positionIdSoccer = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeLevelSoccer -> {
                        _state.value = _state.value.copy(
                            levelIdSoccer = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeValuationSoccer -> {
                        _state.value = _state.value.copy(
                            valuationIdSoccer = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeAbilitiesSoccer -> {
                        _state.value = _state.value.copy(
                            abilitiesSoccer = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeIdSoccer -> {
                        if (event.valueOpt == null) {
                            _state.value = _state.value.copy(
                                idSoccer = event.valueOpt,
                                positionIdSoccerError = event.valueOpt,
                                levelIdSoccerError = event.valueOpt,
                                valuationIdSoccerError = event.valueOpt,
                                abilitiesSoccerError = event.valueOpt,
                            )
                        } else {
                            _state.value = _state.value.copy(
                                idSoccer = event.valueOpt
                            )
                        }
                    }

                    TypeValueChange.OnValueChangePositionBasketball -> {
                        _state.value = _state.value.copy(
                            positionIdBasketball = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeLevelBasketball -> {
                        _state.value = _state.value.copy(
                            levelIdBasketball = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeValuationBasketball -> {
                        _state.value = _state.value.copy(
                            valuationIdBasketball = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeAbilitiesBasketball -> {
                        _state.value = _state.value.copy(
                            abilitiesBasketball = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeIdBasketball -> {
                        if (event.valueOpt == null) {
                            _state.value = _state.value.copy(
                                idBasketball = event.valueOpt,
                                positionIdBasketballError = event.valueOpt,
                                levelIdBasketballError = event.valueOpt,
                                valuationIdBasketballError = event.valueOpt,
                                abilitiesBasketballError = event.valueOpt,
                            )
                        } else {
                            _state.value = _state.value.copy(
                                idBasketball = event.valueOpt
                            )
                        }
                    }

                    TypeValueChange.OnValueChangePositionPadel -> {
                        _state.value = _state.value.copy(
                            positionIdPadel = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeLevelPadel -> {
                        _state.value = _state.value.copy(
                            levelIdPadel = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeValuationPadel -> {
                        _state.value = _state.value.copy(
                            valuationIdPadel = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeAbilitiesPadel -> {
                        _state.value = _state.value.copy(
                            abilitiesPadel = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeIdPadel -> {
                        if (event.valueOpt == null) {
                            _state.value = _state.value.copy(
                                idPadel = event.valueOpt,
                                positionIdPadelError = event.valueOpt,
                                levelIdPadelError = event.valueOpt,
                                valuationIdPadelError = event.valueOpt,
                                abilitiesPadelError = event.valueOpt,
                            )
                        } else {
                            _state.value = _state.value.copy(
                                idPadel = event.valueOpt
                            )
                        }
                    }

                    TypeValueChange.OnValueChangePositionTennis -> {
                        _state.value = _state.value.copy(
                            positionIdTennis = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeLevelTennis -> {
                        _state.value = _state.value.copy(
                            levelIdTennis = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeValuationTennis -> {
                        _state.value = _state.value.copy(
                            valuationIdTennis = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeAbilitiesTennis -> {
                        _state.value = _state.value.copy(
                            abilitiesTennis = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeIdTennis -> {
                        if (event.valueOpt == null) {
                            _state.value = _state.value.copy(
                                idTennis = event.valueOpt,
                                positionIdTennisError = event.valueOpt,
                                levelIdTennisError = event.valueOpt,
                                valuationIdTennisError = event.valueOpt,
                                abilitiesTennisError = event.valueOpt,
                            )
                        } else {
                            _state.value = _state.value.copy(
                                idTennis = event.valueOpt
                            )
                        }
                    }

                    TypeValueChange.OnValueChangePositionRecreationalActivity -> {
                        _state.value = _state.value.copy(
                            positionIdRecreationalActivity = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeLevelRecreationalActivity -> {
                        _state.value = _state.value.copy(
                            levelIdRecreationalActivity = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeValuationRecreationalActivity -> {
                        _state.value = _state.value.copy(
                            valuationIdRecreationalActivity = event.value.toIntOrNull()
                        )
                    }
                    TypeValueChange.OnValueChangeAbilitiesRecreationalActivity -> {
                        _state.value = _state.value.copy(
                            abilitiesRecreationalActivity = event.value
                        )
                    }
                    TypeValueChange.OnValueChangeIdRecreationalActivity -> {
                        if (event.valueOpt == null) {
                            _state.value = _state.value.copy(
                                idRecreationalActivity = event.valueOpt,
                                positionIdRecreationalActivityError = event.valueOpt,
                                levelIdRecreationalActivityError = event.valueOpt,
                                valuationIdRecreationalActivityError = event.valueOpt,
                                abilitiesRecreationalActivityError = event.valueOpt,
                            )
                        } else {
                            _state.value = _state.value.copy(
                                idRecreationalActivity = event.valueOpt
                            )
                        }
                    }

                }
            }
            ConfigProfileEvents.onClick(TypeClick.DismissDialog) -> {
                _state.value = _state.value.copy(
                    isErrorSubmitData = false
                )
            }
            ConfigProfileEvents.onClick(TypeClick.SaveSoccerData) -> {
                if (_state.value.idSoccer != null) {
                    _state.value = _state.value.copy(
                        positionIdSoccerError = validateFieldIsNotEmpty(_state.value.positionIdSoccer.toString()),
                        levelIdSoccerError = validateFieldIsNotEmpty(_state.value.levelIdSoccer.toString()),
                        valuationIdSoccerError = validateFieldIsNotEmpty(_state.value.valuationIdSoccer.toString()),
                        abilitiesSoccerError = validateFieldIsNotEmpty(_state.value.abilitiesSoccer)
                    )
                }
            }
            ConfigProfileEvents.onClick(TypeClick.SaveBasketballData) -> {
                if (_state.value.idBasketball != null) {
                    _state.value = _state.value.copy(
                        positionIdBasketballError = validateFieldIsNotEmpty(_state.value.positionIdBasketball.toString()),
                        levelIdBasketballError = validateFieldIsNotEmpty(_state.value.levelIdBasketball.toString()),
                        valuationIdBasketballError = validateFieldIsNotEmpty(_state.value.valuationIdBasketball.toString()),
                        abilitiesBasketballError = validateFieldIsNotEmpty(_state.value.abilitiesBasketball)
                    )
                }
            }
            ConfigProfileEvents.onClick(TypeClick.SavePadelData) -> {
                if (_state.value.idPadel != null) {
                    _state.value = _state.value.copy(
                        positionIdPadelError = validateFieldIsNotEmpty(_state.value.positionIdPadel.toString()),
                        levelIdPadelError = validateFieldIsNotEmpty(_state.value.levelIdPadel.toString()),
                        valuationIdPadelError = validateFieldIsNotEmpty(_state.value.valuationIdPadel.toString()),
                        abilitiesPadelError = validateFieldIsNotEmpty(_state.value.abilitiesPadel)
                    )
                }
            }
            ConfigProfileEvents.onClick(TypeClick.SaveTennisData) -> {
                if (_state.value.idTennis != null) {
                    _state.value = _state.value.copy(
                        positionIdTennisError = validateFieldIsNotEmpty(_state.value.positionIdTennis.toString()),
                        levelIdTennisError = validateFieldIsNotEmpty(_state.value.levelIdTennis.toString()),
                        valuationIdTennisError = validateFieldIsNotEmpty(_state.value.valuationIdTennis.toString()),
                        abilitiesTennisError = validateFieldIsNotEmpty(_state.value.abilitiesTennis)
                    )
                }
            }
            ConfigProfileEvents.onClick(TypeClick.SaveRecreationalActivityData) -> {
                if (_state.value.idRecreationalActivity != null) {
                    _state.value = _state.value.copy(
                        positionIdRecreationalActivityError = validateFieldIsNotEmpty(_state.value.positionIdRecreationalActivity.toString()),
                        levelIdRecreationalActivityError = validateFieldIsNotEmpty(_state.value.levelIdRecreationalActivity.toString()),
                        valuationIdRecreationalActivityError = validateFieldIsNotEmpty(_state.value.valuationIdRecreationalActivity.toString()),
                        abilitiesRecreationalActivityError = validateFieldIsNotEmpty(_state.value.abilitiesRecreationalActivity)
                    )
                }
            }
            ConfigProfileEvents.onClick(TypeClick.Submit) -> {
                validateLastName(_state.value.lastName)
                validateName(_state.value.name)
                validateDateOfBirth(_state.value.dateOfBirth)
                validateNickname(_state.value.nickname)
                validateSex(_state.value.sex)
                validateAddress(_state.value.address)
                validateAddressAlias(_state.value.addressAlias)
                validateAgeIsNotEmptyAndValid(_state.value.minAge, _state.value.maxAge)
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
                if (_state.value.idSoccer != null) {
                    _state.value = _state.value.copy(
                        positionIdSoccerError = validateFieldIsNotEmpty(_state.value.positionIdSoccer.toString()),
                        levelIdSoccerError = validateFieldIsNotEmpty(_state.value.levelIdSoccer.toString()),
                        valuationIdSoccerError = validateFieldIsNotEmpty(_state.value.valuationIdSoccer.toString()),
                        abilitiesSoccerError = validateFieldIsNotEmpty(_state.value.abilitiesSoccer)
                    )
                }
                if (_state.value.idBasketball != null) {
                    _state.value = _state.value.copy(
                        positionIdBasketballError = validateFieldIsNotEmpty(_state.value.positionIdBasketball.toString()),
                        levelIdBasketballError = validateFieldIsNotEmpty(_state.value.levelIdBasketball.toString()),
                        valuationIdBasketballError = validateFieldIsNotEmpty(_state.value.valuationIdBasketball.toString()),
                        abilitiesBasketballError = validateFieldIsNotEmpty(_state.value.abilitiesBasketball)
                    )
                }
                if (_state.value.idPadel != null) {
                    _state.value = _state.value.copy(
                        positionIdPadelError = validateFieldIsNotEmpty(_state.value.positionIdPadel.toString()),
                        levelIdPadelError = validateFieldIsNotEmpty(_state.value.levelIdPadel.toString()),
                        valuationIdPadelError = validateFieldIsNotEmpty(_state.value.valuationIdPadel.toString()),
                        abilitiesPadelError = validateFieldIsNotEmpty(_state.value.abilitiesPadel)
                    )
                }
                if (_state.value.idTennis != null) {
                    _state.value = _state.value.copy(
                        positionIdTennisError = validateFieldIsNotEmpty(_state.value.positionIdTennis.toString()),
                        levelIdTennisError = validateFieldIsNotEmpty(_state.value.levelIdTennis.toString()),
                        valuationIdTennisError = validateFieldIsNotEmpty(_state.value.valuationIdTennis.toString()),
                        abilitiesTennisError = validateFieldIsNotEmpty(_state.value.abilitiesTennis)
                    )
                }
                if (_state.value.idRecreationalActivity != null) {
                    _state.value = _state.value.copy(
                        positionIdRecreationalActivityError = validateFieldIsNotEmpty(_state.value.positionIdRecreationalActivity.toString()),
                        levelIdRecreationalActivityError = validateFieldIsNotEmpty(_state.value.levelIdRecreationalActivity.toString()),
                        valuationIdRecreationalActivityError = validateFieldIsNotEmpty(_state.value.valuationIdRecreationalActivity.toString()),
                        abilitiesRecreationalActivityError = validateFieldIsNotEmpty(_state.value.abilitiesRecreationalActivity)
                    )
                }

                createPerson()


            }

        }
    }


    private fun createPerson() {
        val name = if (_state.value.nameError == null) _state.value.name else return
        val lastName = if (_state.value.lastNameError == null) _state.value.lastName else return
        val dateOfBirth =
            if (_state.value.dateOfBirthError == null) _state.value.dateOfBirth else return
        val nickname = if (_state.value.nicknameError == null) _state.value.nickname else return
        val sex = if (_state.value.sexError == null) _state.value.sex else return
        val latitude = if (_state.value.latitudeError == null) _state.value.latitude else return
        val longitude = if (_state.value.longitudeError == null) _state.value.longitude else return
        val address = if (_state.value.addressError == null) _state.value.address else return
        val addressAlias =
            if (_state.value.addressAliasError == null) _state.value.addressAlias else return
        val startTimeSunday = if (state.value.sundayError == null) {
            if (state.value.startTimeSunday != null) _state.value.startTimeSunday.toString() else null
        } else return
        val endTimeSunday = if (state.value.sundayError == null) {
            if (state.value.endTimeSunday != null) _state.value.endTimeSunday.toString() else null
        } else return
        val startTimeMonday = if (state.value.mondayError == null) {
            if (state.value.startTimeMonday != null) _state.value.startTimeMonday.toString() else null
        } else return
        val endTimeMonday = if (state.value.mondayError == null) {
            if (state.value.endTimeMonday != null) _state.value.endTimeMonday.toString() else null
        } else return
        val startTimeTuesday = if (state.value.tuesdayError == null) {
            if (state.value.startTimeTuesday != null) _state.value.startTimeTuesday.toString() else null
        } else return
        val endTimeTuesday = if (state.value.tuesdayError == null) {
            if (state.value.endTimeTuesday != null) _state.value.endTimeTuesday.toString() else null
        } else return
        val startTimeWednesday = if (state.value.wednesdayError == null) {
            if (state.value.startTimeWednesday != null) _state.value.startTimeWednesday.toString() else null
        } else return
        val endTimeWednesday = if (state.value.wednesdayError == null) {
            if (state.value.endTimeWednesday != null) _state.value.endTimeWednesday.toString() else null
        } else return
        val startTimeThursday = if (state.value.thursdayError == null) {
            if (state.value.startTimeThursday != null) _state.value.startTimeThursday.toString() else null
        } else return
        val endTimeThursday = if (state.value.thursdayError == null) {
            if (state.value.endTimeThursday != null) _state.value.endTimeThursday.toString() else null
        } else return
        val startTimeFriday = if (state.value.fridayError == null) {
            if (state.value.startTimeFriday != null) _state.value.startTimeFriday.toString() else null
        } else return
        val endTimeFriday = if (state.value.fridayError == null) {
            if (state.value.endTimeFriday != null) _state.value.endTimeFriday.toString() else null
        } else return
        val startTimeSaturday = if (state.value.saturdayError == null) {
            if (state.value.startTimeSaturday != null) _state.value.startTimeSaturday.toString() else null
        } else return
        val endTimeSaturday = if (state.value.saturdayError == null) {
            if (state.value.endTimeSaturday != null) _state.value.endTimeSaturday.toString() else null
        } else return
        val minAge = if (_state.value.ageError == null) _state.value.minAge else return
        val maxAge = if (_state.value.ageError == null) _state.value.maxAge else return
        val notifications =
            if (_state.value.notifications != null) _state.value.notifications else return
        val willingDistance =
            if (_state.value.willingDistanceError == null) _state.value.willingDistance else return


        val abilitiesSoccer = if (state.value.abilitiesSoccerError == null) {
            if (state.value.idSoccer != null) _state.value.abilitiesSoccer else null
        } else return
        val positionIdSoccer = if (state.value.positionIdSoccerError == null) {
            if (state.value.idSoccer != null) _state.value.positionIdSoccer else null
        } else return
        val levelIdSoccer = if (state.value.levelIdSoccerError == null) {
            if (state.value.idSoccer != null) _state.value.levelIdSoccer else null
        } else return
        val valuationIdSoccer = if (state.value.valuationIdSoccerError == null) {
            if (state.value.idSoccer != null) _state.value.valuationIdSoccer else null
        } else return

        val abilitiesBasketball = if (state.value.abilitiesBasketballError == null) {
            if (state.value.idBasketball != null) _state.value.abilitiesBasketball else null
        } else return
        val positionIdBasketball = if (state.value.positionIdBasketballError == null) {
            if (state.value.idBasketball != null) _state.value.positionIdBasketball else null
        } else return
        val levelIdBasketball = if (state.value.levelIdBasketballError == null) {
            if (state.value.idBasketball != null) _state.value.levelIdBasketball else null
        } else return
        val valuationIdBasketball = if (state.value.valuationIdBasketballError == null) {
            if (state.value.idBasketball != null) _state.value.valuationIdBasketball else null
        } else return

        val abilitiesPadel = if (state.value.abilitiesPadelError == null) {
            if (state.value.idPadel != null) _state.value.abilitiesPadel else null
        } else return
        val positionIdPadel = if (state.value.positionIdPadelError == null) {
            if (state.value.idPadel != null) _state.value.positionIdPadel else null
        } else return
        val levelIdPadel = if (state.value.levelIdPadelError == null) {
            if (state.value.idPadel != null) _state.value.levelIdPadel else null
        } else return
        val valuationIdPadel = if (state.value.valuationIdPadelError == null) {
            if (state.value.idPadel != null) _state.value.valuationIdPadel else null
        } else return

        val abilitiesTennis = if (state.value.abilitiesTennisError == null) {
            if (state.value.idTennis != null) _state.value.abilitiesTennis else null
        } else return
        val positionIdTennis = if (state.value.positionIdTennisError == null) {
            if (state.value.idTennis != null) _state.value.positionIdTennis else null
        } else return
        val levelIdTennis = if (state.value.levelIdTennisError == null) {
            if (state.value.idTennis != null) _state.value.levelIdTennis else null
        } else return
        val valuationIdTennis = if (state.value.valuationIdTennisError == null) {
            if (state.value.idTennis != null) _state.value.valuationIdTennis else null
        } else return

        val abilitiesRecreationalActivity =
            if (state.value.abilitiesRecreationalActivityError == null) {
                if (state.value.idRecreationalActivity != null) _state.value.abilitiesRecreationalActivity else null
            } else return
        val positionIdRecreationalActivity =
            if (state.value.positionIdRecreationalActivityError == null) {
                if (state.value.idRecreationalActivity != null) _state.value.positionIdRecreationalActivity else null
            } else return
        val levelIdRecreationalActivity =
            if (state.value.levelIdRecreationalActivityError == null) {
                if (state.value.idRecreationalActivity != null) _state.value.levelIdRecreationalActivity else null
            } else return
        val valuationIdRecreationalActivity =
            if (state.value.valuationIdRecreationalActivityError == null) {
                if (state.value.idRecreationalActivity != null) _state.value.valuationIdRecreationalActivity else null
            } else return

        val userUid = firebaseAuthRepository.getUserId()
        val request = RequestPersonTransaction(
            userUid = userUid,
            lastName = lastName,
            name = name,
            dateOfBirth = dateOfBirth.toString(),
            nickname = nickname,
            sex = sex,
            latitude = latitude,
            longitude = longitude,
            address = address,
            addressAlias = addressAlias,
            startTimeSunday = startTimeSunday.toString(),
            endTimeSunday = endTimeSunday.toString(),
            startTimeMonday = startTimeMonday.toString(),
            endTimeMonday = endTimeMonday.toString(),
            startTimeTuesday = startTimeTuesday.toString(),
            endTimeTuesday = endTimeTuesday.toString(),
            startTimeWednesday = startTimeWednesday.toString(),
            endTimeWednesday = endTimeWednesday.toString(),
            startTimeThursday = startTimeThursday.toString(),
            endTimeThursday = endTimeThursday.toString(),
            startTimeFriday = startTimeFriday.toString(),
            endTimeFriday = endTimeFriday.toString(),
            startTimeSaturday = startTimeSaturday.toString(),
            endTimeSaturday = endTimeSaturday.toString(),
            minAge = minAge,
            maxAge = maxAge,
            notifications = notifications,
            willingDistance = willingDistance,
            idSoccer = state.value.idSoccer,
            abilitiesSoccer = abilitiesSoccer,
            positionIdSoccer = positionIdSoccer,
            levelIdSoccer = levelIdSoccer,
            valuationIdSoccer = valuationIdSoccer,

            idBasketball = state.value.idBasketball,
            abilitiesBasketball = abilitiesBasketball,
            positionIdBasketball = positionIdBasketball,
            levelIdBasketball = levelIdBasketball,
            valuationIdBasketball = valuationIdBasketball,

            idPadel = state.value.idPadel,
            abilitiesPadel = abilitiesPadel,
            positionIdPadel = positionIdPadel,
            levelIdPadel = levelIdPadel,
            valuationIdPadel = valuationIdPadel,

            idTennis = state.value.idTennis,
            abilitiesTennis = abilitiesTennis,
            positionIdTennis = positionIdTennis,
            levelIdTennis = levelIdTennis,
            valuationIdTennis = valuationIdTennis,

            idRecreationalActivity = state.value.idRecreationalActivity,
            abilitiesRecreationalActivity = abilitiesRecreationalActivity,
            positionIdRecreationalActivity = positionIdRecreationalActivity,
            levelIdRecreationalActivity = levelIdRecreationalActivity,
            valuationIdRecreationalActivity = valuationIdRecreationalActivity,
        )

        featRepository.createPersonTransaction(request).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message!!
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        isLoadingSubmitData = true
                    )
                }
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        isSuccessSubmitData = true
                    )
                }
            }
        }.launchIn(viewModelScope)


    }


    private fun validateFieldIsNotEmpty(field: String): ConfigProfileState.GenericError? {

        val trimmedField = field.trim()
        if (trimmedField.isBlank() || trimmedField == "null") {
            return ConfigProfileState.GenericError.FieldEmpty

        }
        return null
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
        _state.value = _state.value.copy(lastNameError = null)
    }

    private fun validateName(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                nameError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(nameError = null)
    }

    private fun validateDateOfBirth(date: LocalDate?) {
        if (date == null) {
            _state.value = _state.value.copy(
                dateOfBirthError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        } else if (!date.isBefore(LocalDate.now().minusYears(16))) {
            _state.value = _state.value.copy(
                dateOfBirthError = ConfigProfileState.DateError.DateInvalid
            )
        }
        _state.value = _state.value.copy(dateOfBirthError = null)
    }

    private fun validateNickname(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                nicknameError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(nicknameError = null)
    }

    private fun validateSex(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                sexError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(sexError = null)
    }

    private fun validateAddress(address: String) {
        val trimmedAddress = address.trim()
        if (trimmedAddress.isBlank()) {
            _state.value = _state.value.copy(
                addressError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(addressError = null)
    }

    private fun validateAddressAlias(address: String) {
        val trimmedAddress = address.trim()
        if (trimmedAddress.isBlank()) {
            _state.value = _state.value.copy(
                addressAliasError = ConfigProfileState.GenericError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(addressAliasError = null)
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

    private fun getSportList() {
        featRepository.getGenericsSports().onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        ConfigProfileState(
                            error = result.message
                                ?: "Error desconocido"
                        )
                }
                is Result.Loading -> {
                }
                is Result.Success -> {
                    _state.value = ConfigProfileState(sportsList = result.data ?: emptyList())
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