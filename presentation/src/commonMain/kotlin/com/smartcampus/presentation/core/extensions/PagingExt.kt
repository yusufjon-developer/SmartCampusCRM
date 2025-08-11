package com.smartcampus.presentation.core.extensions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

/**
 * Extension function for LazyListScope to handle Paging LoadStates (refresh and append).
 *
 * @param T The type of the PagingData.
 * @param lazyPagingItems The [LazyPagingItems] instance managing the paged data.
 */
fun <T : Any> LazyListScope.pagingLoadStateIndicator(
    lazyPagingItems: LazyPagingItems<T>
) {
    lazyPagingItems.loadState.apply {
        when {
            // REFRESH states
            refresh is LoadState.Loading -> {
                item {
                    PagingLoadingIndicator(modifier = Modifier.fillParentMaxSize())
                }
            }

            refresh is LoadState.Error -> {
                val errorState = refresh as LoadState.Error
                item {
                    PagingErrorIndicator(
                        errorMessage = errorState.error.localizedMessage ?: "Unknown error",
                        onRetry = { lazyPagingItems.retry() },
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            }

            // APPEND states
            append is LoadState.Loading -> {
                item {
                    PagingLoadingIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }

            // Adjusted padding for append error
            append is LoadState.Error -> {
                val errorState = append as LoadState.Error
                item {
                    PagingErrorIndicator(
                        errorMessage = "Ошибка: ${errorState.error.localizedMessage ?: "Unknown error"}",
                        onRetry = { lazyPagingItems.retry() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )
                    )
                }
            }
        }
    }
}

/**
 * Composable for displaying a loading indicator.
 */
@Composable
fun PagingLoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Composable for displaying an error message and a retry button.
 */
@Composable
fun PagingErrorIndicator(
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp), // Added padding for the whole error item
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium, // Use typography
            modifier = Modifier.padding(bottom = 8.dp) // Adjusted padding
        )
        Button(onClick = onRetry) {
            Text("Повторить")
        }
    }
}