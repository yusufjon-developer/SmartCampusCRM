package com.smartcampus.presentation.ui.screen.login

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.useCases.LoginUseCase
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginContract.Event, LoginContract.Effect, LoginContract.State>() {
    override fun createInitialState() = LoginContract.State(false)

    override fun handleEvent(event: LoginContract.Event) {
        viewModelScope.launch {
            when(event) {
                is LoginContract.Event.Login -> {
                    loginUseCase(event.user)
                        .onStart { setState { copy(isLoading = true) } }
                        .onEach { response ->
                            when(response) {
                                is Either.Left -> {
                                    setState { copy(isLoading = false) }
                                    setEffect { LoginContract.Effect.Error(response.value.toString()) }
                                }
                                is Either.Right -> {
                                    setState { copy(isLoading = false) }
                                    setEffect { LoginContract.Effect.Success }
                                }
                            }
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }
}