package com.smartcampus.presentation.ui.screen.home.group

import androidx.lifecycle.viewModelScope
import app.cash.paging.cachedIn
import com.smartcampus.crm.domain.models.GroupCreateRequest
import com.smartcampus.crm.domain.models.GroupUpdateRequest
import com.smartcampus.crm.domain.repositories.GroupRepository
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class GroupViewModel(
    private val repository: GroupRepository
) : BaseViewModel<GroupContract.Event, GroupContract.Effect, GroupContract.State>() {

    override fun createInitialState() = GroupContract.State()

    override fun handleEvent(event: GroupContract.Event) {
        viewModelScope.launch {
            when (event) {
                is GroupContract.Event.GetGroups -> {
                    getGroups()
                }

                is GroupContract.Event.AddGroup -> {
                    createGroup(request = event.request)
                }

                is GroupContract.Event.ShowAddDialog -> {
                    setEffect { GroupContract.Effect.ShowAddDialog(show = event.show) }
                }

                is GroupContract.Event.UpdateGroup -> {
                    updateGroup(id = event.id, request = event.request)
                }
            }
        }
    }

    private fun getGroups() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val groupFlow = repository.getGroupList(sortBy = null).cachedIn(viewModelScope)
            setState { copy(isLoading = false, groups = groupFlow) }
        }
    }

    private fun updateGroup(id: Int, request: GroupUpdateRequest) {
        viewModelScope.launch {
            repository.updateGroup(id, request)
                .onStart { }
                .onEach { result ->
                    when (result) {
                        is Either.Right -> {
                            setEffect { GroupContract.Effect.ShowSuccessMessage(result.value.toString()) }
                        }

                        is Either.Left -> {
                            setEffect { GroupContract.Effect.ShowError(result.value) }
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private suspend fun createGroup(request: GroupCreateRequest) {
        when (val result = repository.createGroup(request).first()) {
            is Either.Right -> {
                setEffect { GroupContract.Effect.ShowSuccessMessage("Группа успешно добавлена") }
            }

            is Either.Left -> {
                setEffect { GroupContract.Effect.ShowError(result.value) }
            }
        }
    }
}