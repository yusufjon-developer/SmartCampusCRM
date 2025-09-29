package com.smartcampus.crm.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.base.pagination.BasePagingSource
import com.smartcampus.crm.data.base.pagination.PagingResponse
import com.smartcampus.crm.data.remote.apiServices.SecurityApiService
import com.smartcampus.crm.domain.models.security.Permission
import com.smartcampus.crm.domain.repositories.SecurityRepository

class SecurityRepositoryImpl(
    private val apiService: SecurityApiService
) : SecurityRepository, BaseRepository() {

    override suspend fun getPermissionList(sortBy: String?) = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 10),
        pagingSourceFactory = {
            BasePagingSource { pageNumber ->
                doRequest<PagingResponse<Permission>> {
                    apiService.getPermissionList(
                        page = pageNumber,
                        size = 20,
                        sortedBy = sortBy
                    )
                }

            }
        }
    ).flow
}