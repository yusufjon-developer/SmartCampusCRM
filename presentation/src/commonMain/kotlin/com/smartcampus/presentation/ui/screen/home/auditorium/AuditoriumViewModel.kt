package com.smartcampus.presentation.ui.screen.home.auditorium

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.smartcampus.crm.domain.models.AuditoriumCreateRequest
import com.smartcampus.crm.domain.models.AuditoriumUpdateRequest
import com.smartcampus.crm.domain.repositories.AuditoriumRepository
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AuditoriumViewModel(
    private val repository: AuditoriumRepository
) : BaseViewModel<AuditoriumContract.Event, AuditoriumContract.Effect, AuditoriumContract.State>() {

    override fun createInitialState() = AuditoriumContract.State()

    override fun handleEvent(event: AuditoriumContract.Event) {
        viewModelScope.launch {
            when (event) {
                is AuditoriumContract.Event.GetAuditoriums -> {
                    getAuditoriums()
                }

                is AuditoriumContract.Event.AddAuditorium -> {
                    createAuditorium(request = event.request)
                }

                is AuditoriumContract.Event.ShowAddDialog -> {
                    setEffect { AuditoriumContract.Effect.ShowAddDialog(show = event.show) }
                }

                is AuditoriumContract.Event.UpdateAuditorium -> {
                    updateAuditorium(id = event.id, request = event.request)
                }
            }
        }
    }

    private fun getAuditoriums() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val auditoriumFlow = repository.getAuditoriumList(sortBy = null).cachedIn(viewModelScope)
            setState { copy(isLoading = false, auditoriums = auditoriumFlow) }
        }
    }

    private fun updateAuditorium(id: Int, request: AuditoriumUpdateRequest) {
        viewModelScope.launch {
            repository.updateAuditorium(id, request)
                .onStart { }
                .onEach { result ->
                    when (result) {
                        is Either.Right -> {
                            setEffect { AuditoriumContract.Effect.ShowSuccessMessage(result.value.toString()) }
                        }

                        is Either.Left -> {
                            setEffect { AuditoriumContract.Effect.ShowError(result.value) }
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private suspend fun createAuditorium(request: AuditoriumCreateRequest) {
        when (val result = repository.createAuditorium(request).first()) {
            is Either.Right -> {
                setEffect { AuditoriumContract.Effect.ShowSuccessMessage("Аудитория успешно добавлена") }
            }

            is Either.Left -> {
                setEffect { AuditoriumContract.Effect.ShowError(result.value) }
            }
        }
    }
}