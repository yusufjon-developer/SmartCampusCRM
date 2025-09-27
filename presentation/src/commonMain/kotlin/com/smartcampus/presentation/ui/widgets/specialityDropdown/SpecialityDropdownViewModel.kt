package com.smartcampus.presentation.ui.widgets.specialityDropdown

import androidx.lifecycle.viewModelScope
import app.cash.paging.cachedIn
import com.smartcampus.crm.domain.repositories.SpecialityRepository
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SpecialityDropdownViewModel(
    private val specialityRepository: SpecialityRepository
) : BaseViewModel<SpecialityDropdownContract.Event, SpecialityDropdownContract.Effect, SpecialityDropdownContract.State>() {

    override fun createInitialState() = SpecialityDropdownContract.State()

    override fun handleEvent(event: SpecialityDropdownContract.Event) {
        when (event) {
            is SpecialityDropdownContract.Event.LoadSpecialties -> {
                loadSpecialties()
            }

            is SpecialityDropdownContract.Event.SelectSpeciality -> {
                setState { copy(selectedSpeciality = event.speciality) }
            }

            is SpecialityDropdownContract.Event.SearchSpecialties -> {
                setState { copy(searchQuery = event.query) }
            }

            is SpecialityDropdownContract.Event.ToggleDropdown -> setState { copy(expanded = !event.expanded) }
        }
    }

    private fun loadSpecialties() { // 💡 ИЗМЕНЕНИЕ: Новое имя метода
        viewModelScope.launch(Dispatchers.IO) {
            setState {
                copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                val specialtiesFlow = specialityRepository.getSpecialityList(null)
                    .cachedIn(viewModelScope)
                    .catch { exception ->
                        val error = NetworkError.Unexpected(exception.message.toString())
                        setState {
                            copy(
                                isLoading = false,
                                error = error
                            )
                        }
                        setEffect { SpecialityDropdownContract.Effect.ShowError(error) }
                    }

                setState {
                    copy(
                        isLoading = false,
                        specialties = specialtiesFlow,
                        error = null
                    )
                }
            } catch (e: Exception) {
                val error = NetworkError.Unexpected(e.message.toString())
                setState {
                    copy(
                        isLoading = false,
                        error = error
                    )
                }
                setEffect { SpecialityDropdownContract.Effect.ShowError(error) }
            }
        }
    }
}