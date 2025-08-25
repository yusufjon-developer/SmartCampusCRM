package com.smartcampus.presentation.core.extensions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

/**
 * Extension function for LazyListScope to handle Paging LoadStates (refresh and append).
 * It can also display a message for an empty list or provide a slot for custom "add" content.
 *
 * @param T The type of the PagingData.
 * @param lazyPagingItems The [LazyPagingItems] instance managing the paged data.
 * @param emptyListMessage Message to show when the list is empty after a successful load,
 *                         and `addButtonEnabled` is false.
 *                         Defaults to null (no message).
 * @param addButtonEnabled Controls whether the `addContent` slot is enabled and its content displayed.
 *                         Defaults to false.
 * @param addContent Composable lambda that is invoked if `addButtonEnabled` is true.
 *                   This allows for adding custom content (like an "Add" button) at the end
 *                   of the scope managed by this indicator. The `onClick` logic for any button
 *                   within this content should be handled internally by the provided Composable.
 * @param onErrorAddClicked Optional callback specifically for an "Add" button that might be shown
 *                          by `PagingErrorIndicator` in case of a refresh error on an empty list.
 *                          This is separate from the main `addContent`.
 */
fun <T : Any> LazyListScope.pagingLoadStateIndicator(
    lazyPagingItems: LazyPagingItems<T>,
    emptyListMessage: String? = null,
    addButtonEnabled: Boolean = false,
    onErrorAddClicked: (() -> Unit)? = null,
    addContent: @Composable (() -> Unit) = {},
) {
    val loadState = lazyPagingItems.loadState
    val itemCount = lazyPagingItems.itemCount

    // Показываем кнопку "Добавить" в PagingErrorIndicator, если:
    // 1. Произошла ошибка Refresh.
    // 2. Список пуст.
    // 3. Предоставлен обработчик onErrorAddClicked.
    val showErrorIndicatorAddButton = onErrorAddClicked != null && loadState.refresh is LoadState.Error && itemCount == 0

    when {
        // REFRESH states
        loadState.refresh is LoadState.Loading -> {
            item("refresh_loading_indicator") {
                PagingLoadingIndicator(modifier = Modifier.fillParentMaxSize())
            }
        }
        loadState.refresh is LoadState.Error -> {
            val errorState = loadState.refresh as LoadState.Error
            item("refresh_error_indicator") {
                PagingErrorIndicator(
                    errorMessage = errorState.error.localizedMessage ?: "Unknown error",
                    onRetry = { lazyPagingItems.retry() },
                    modifier = Modifier.fillParentMaxSize(),
                    showAddButton = showErrorIndicatorAddButton,
                    onAddClicked = onErrorAddClicked ?: {}
                )
            }
        }
        // Состояние: Успешная загрузка (refresh), но список пуст
        loadState.refresh is LoadState.NotLoading && itemCount == 0 && loadState.append.endOfPaginationReached -> {
            if (!addButtonEnabled && emptyListMessage != null) {
                // Если addButton не активен (т.е. addContent не будет показан),
                // и есть сообщение для пустого списка, показываем его.
                item("empty_list_message_indicator") {
                    Box(
                        modifier = Modifier.fillParentMaxSize().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(emptyListMessage, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        // APPEND states
        loadState.append is LoadState.Loading -> {
            item("append_loading_indicator") {
                PagingLoadingIndicator(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
            }
        }
        loadState.append is LoadState.Error -> {
            val errorState = loadState.append as LoadState.Error
            item("append_error_indicator") {
                PagingErrorIndicator(
                    errorMessage = "Ошибка: ${errorState.error.localizedMessage ?: "Unknown error"}",
                    onRetry = { lazyPagingItems.retry() },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    showAddButton = false
                )
            }
        }
    }

    if (addButtonEnabled) {
        item("custom_add_content_container") {
            addContent()
        }
    }
}

/**
 * Composable for displaying a loading indicator.
 */
@Composable
fun PagingLoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Composable for displaying an error message and a retry button, and optionally an "Add" button.
 */
@Composable
fun PagingErrorIndicator(
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    showAddButton: Boolean = false,
    onAddClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = onRetry) {
                Text("Повторить")
            }
            if (showAddButton) {
                FilledTonalButton(onClick = onAddClicked) {
                    Icon(Icons.Filled.Add, contentDescription = "Добавить")
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Добавить")
                }
            }
        }
    }
}
