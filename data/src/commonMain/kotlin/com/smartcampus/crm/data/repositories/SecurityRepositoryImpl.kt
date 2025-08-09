package com.smartcampus.crm.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.base.pagination.BasePagingSource
import com.smartcampus.crm.data.base.pagination.PagingResponse
import com.smartcampus.crm.data.remote.apiServices.SecurityApiService
import com.smartcampus.crm.domain.models.security.Role
import com.smartcampus.crm.domain.models.security.RoleRequest
import com.smartcampus.crm.domain.repositories.SecurityRepository

class SecurityRepositoryImpl(
    private val apiService: SecurityApiService
) : SecurityRepository, BaseRepository() {
    override suspend fun getRoleList(sortBy: String?) = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 10),
        pagingSourceFactory = {
            BasePagingSource { pageNumber ->
                doRequest<PagingResponse<Role>, PagingResponse<Role>>(
                    request = {
                        apiService.getRoleList(
                            page = pageNumber,
                            size = 20,
                            sortedBy = sortBy
                        )
                    },
                    mapper = { it }
                )

            }
        }
    ).flow

    override suspend fun createRole(request: RoleRequest) = doRequest<Role, Role>(
        request = { apiService.createRole(request) },
        mapper = { it }
    )

    override suspend fun deleteRoleById(id: Int) = doRequest<Any, Boolean>(
        request = { apiService.deleteRole(id = id) },
        mapper = { true }
    )

    override suspend fun getRoleById(id: Int) = doRequest<Role, Role>(
        request = { apiService.getRole(id) },
        mapper = { it }
    )
}