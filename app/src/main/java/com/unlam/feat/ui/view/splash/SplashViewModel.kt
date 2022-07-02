package com.unlam.feat.ui.view.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.unlam.feat.util.Result

@HiltViewModel
class SplashViewModel
@Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val featRepository: FeatRepositoryImp
) : ViewModel() {

    private val _state = MutableSharedFlow<SplashState>()
    val state: SharedFlow<SplashState> = _state.asSharedFlow()

    fun onEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.Authenticate -> {
                authenticateUser()
            }
        }
    }

    private fun authenticateUser() {
        val isAuthenticate: Boolean = firebaseAuthRepository.isLogged()
        if (isAuthenticate) {
            checkIsFirstLogin()
        } else {
            viewModelScope.launch {
                _state.emit(
                    SplashState(isAuthenticate = false)
                )
            }
        }
    }

    private fun checkIsFirstLogin() {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getPerson(uId).onEach { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data == null) {
                        _state.emit(
                            SplashState(isAuthenticate = true, isFirstLogin = true)
                        )
                    } else {
                        _state.emit(
                            SplashState(isAuthenticate = true, isFirstLogin = false)
                        )
                    }
                }
                is Result.Error -> {
                    SplashState(isAuthenticate = false)
                }
                is Result.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }
}