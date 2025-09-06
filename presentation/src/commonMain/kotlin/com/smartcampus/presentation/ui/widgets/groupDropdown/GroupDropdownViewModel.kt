package com.smartcampus.presentation.ui.widgets.groupDropdown

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.smartcampus.crm.domain.repositories.GroupRepository
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class GroupDropdownViewModel(
    private val groupRepository: GroupRepository
) : BaseViewModel<GroupDropdownContract.Event, GroupDropdownContract.Effect, GroupDropdownContract.State>() {

    override fun createInitialState() = GroupDropdownContract.State()

    override fun handleEvent(event: GroupDropdownContract.Event) {
        when (event) {
            is GroupDropdownContract.Event.LoadGroups -> {
                loadGroups()
            }

            is GroupDropdownContract.Event.SelectGroup -> {
                setState { copy(selectedGroup = event.group) }
            }

            is GroupDropdownContract.Event.SearchGroups -> {
                // Для поиска создаем новый поток с фильтрацией
                setState { copy(searchQuery = event.query) }
                // В реальной реализации здесь должна быть фильтрация на уровне репозитория
                // Пока просто обновляем состояние поиска
            }

            is GroupDropdownContract.Event.ToggleDropdown -> setState { copy(expanded = !event.expanded) }
        }
    }

    private fun loadGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            setState {
                copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                val groupsFlow = groupRepository.getGroupList(null)
                    .cachedIn(viewModelScope)
                    .catch { exception ->
                        val error = NetworkError.Unexpected(exception.message.toString())
                        setState {
                            copy(
                                isLoading = false,
                                error = error
                            )
                        }
                        setEffect { GroupDropdownContract.Effect.ShowError(error) }
                    }

                setState {
                    copy(
                        isLoading = false,
                        groups = groupsFlow,
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
                setEffect { GroupDropdownContract.Effect.ShowError(error) }
            }
        }
    }
}