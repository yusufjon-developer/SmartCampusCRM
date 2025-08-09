package com.smartcampus.crm.data.base.pagination

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.crm.domain.utils.NetworkError
import kotlinx.coroutines.flow.Flow

class BasePagingSource<T : Any, V : PagingItemList<T>>(
    private val block: suspend (pageNumber: Int) -> Flow<Either<NetworkError, V>>
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        val request = block(page)

        var loadResult: LoadResult<Int, T> = LoadResult.Error(Exception("Initial error"))

        request.collect {
            loadResult = when (it) {
                is Either.Left -> {
                    it.value.let { error ->
                        when (error) {
                            is NetworkError.Unexpected -> {
                                LoadResult.Error(Exception(error.reason))
                            }

                            is NetworkError.Api -> {
                                LoadResult.Error(Exception(error.reason))
                            }

                            NetworkError.NoInternetConnection -> {
                                LoadResult.Error(Exception("Вероятно, соединение с интернетом прервано. Пожалуйста повторите попытку позже."))
                            }

                            is NetworkError.ResponseDeserialization -> {
                                LoadResult.Error(Exception("Ошибка парсинга ответа от сервера"))
                            }

                            NetworkError.ServerIsNotAvailable -> {
                                LoadResult.Error(Exception("Сервис временно недоступен"))
                            }
                        }
                    }
                }

                is Either.Right -> {
                    LoadResult.Page(
                        data = it.value.listItems,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (it.value.listItems.size < params.loadSize) null else page + 1
                    )
                }
            }
        }

        return loadResult
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}