package com.smartcampus.presentation.ui.widgets.auditoriumDropdown

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.smartcampus.crm.domain.repositories.AuditoriumRepository
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AuditoriumDropdownViewModel(
    private val auditoriumRepository: AuditoriumRepository
) : BaseViewModel<AuditoriumDropdownContract.Event, AuditoriumDropdownContract.Effect, AuditoriumDropdownContract.State>() {

    override fun createInitialState() = AuditoriumDropdownContract.State()

    override fun handleEvent(event: AuditoriumDropdownContract.Event) {
        when (event) {
            is AuditoriumDropdownContract.Event.LoadAuditoriums ->
                loadGroups(isAvailable = event.isAvailable, day = event.day)

            is AuditoriumDropdownContract.Event.SelectAuditorium ->
                setState { copy(selectedAuditorium = event.auditorium) }

            is AuditoriumDropdownContract.Event.ToggleDropdown ->
                setState { copy(expanded = !event.expanded) }
        }
    }

    private fun loadGroups(isAvailable: Boolean?, day: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(isLoading = true, error = null) }
            try {
                val groupsFlow = auditoriumRepository.getAuditoriumList(null, isAvailable, day)
                    .cachedIn(viewModelScope)
                    .catch { exception ->
                        val error = NetworkError.Unexpected(exception.message.toString())
                        setState { copy(isLoading = false, error = error) }
                        setEffect { AuditoriumDropdownContract.Effect.ShowError(error) }
                    }

                setState { copy(isLoading = false, auditoriums = groupsFlow, error = null) }
            } catch (e: Exception) {
                val error = NetworkError.Unexpected(e.message.toString())
                setState { copy(isLoading = false, error = error) }
                setEffect { AuditoriumDropdownContract.Effect.ShowError(error) }
            }
        }
    }
}