package com.smartcampus.presentation.ui.screen.home.speciality

import androidx.lifecycle.viewModelScope
import app.cash.paging.cachedIn
import com.smartcampus.crm.domain.models.SpecialityCreateRequest
import com.smartcampus.crm.domain.models.SpecialityUpdateRequest
import com.smartcampus.crm.domain.repositories.SpecialityRepository
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SpecialityViewModel(
    private val repository: SpecialityRepository
) : BaseViewModel<SpecialityContract.Event, SpecialityContract.Effect, SpecialityContract.State>() {

    override fun createInitialState() = SpecialityContract.State()

    override fun handleEvent(event: SpecialityContract.Event) {
        viewModelScope.launch {
            when (event) {
                is SpecialityContract.Event.GetSpecialities -> {
                    getSpecialities()
                }

                is SpecialityContract.Event.AddSpeciality -> {
                    createSpeciality(request = event.request)
                }

                is SpecialityContract.Event.ShowAddDialog -> {
                    setEffect { SpecialityContract.Effect.ShowAddDialog(show = event.show) }
                }

                is SpecialityContract.Event.UpdateSpeciality -> {
                    updateSpeciality(id = event.id, request = event.request)
                }
            }
        }
    }

    private fun getSpecialities() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val specialityFlow = repository.getSpecialityList(sortBy = null).cachedIn(viewModelScope)
            setState { copy(isLoading = false, specialities = specialityFlow) }
        }
    }

    private fun updateSpeciality(id: Int, request: SpecialityUpdateRequest) {
        viewModelScope.launch {
            repository.updateSpeciality(id, request)
                .onStart { }
                .onEach { result ->
                    when (result) {
                        is Either.Right -> {
                            setEffect { SpecialityContract.Effect.ShowSuccessMessage(result.value.toString()) }
                        }

                        is Either.Left -> {
                            setEffect { SpecialityContract.Effect.ShowError(result.value) }
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private suspend fun createSpeciality(request: SpecialityCreateRequest) {
        when (val result = repository.createSpeciality(request).first()) {
            is Either.Right -> {
                setEffect { SpecialityContract.Effect.ShowSuccessMessage("Специальность успешно добавлена") }
            }

            is Either.Left -> {
                setEffect { SpecialityContract.Effect.ShowError(result.value) }
            }
        }
    }
}