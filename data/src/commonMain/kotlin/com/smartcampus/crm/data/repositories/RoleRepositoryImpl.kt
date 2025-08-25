package com.smartcampus.crm.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.base.pagination.BasePagingSource
import com.smartcampus.crm.data.base.pagination.PagingResponse
import com.smartcampus.crm.data.remote.apiServices.RoleApiService
import com.smartcampus.crm.domain.models.security.Role
import com.smartcampus.crm.domain.models.security.RoleItem
import com.smartcampus.crm.domain.models.security.RoleRequest
import com.smartcampus.crm.domain.models.security.UpdatePermissionStatus
import com.smartcampus.crm.domain.repositories.RoleRepository

class RoleRepositoryImpl(
    private val apiService: RoleApiService
) : RoleRepository, BaseRepository() {

    override suspend fun getRoleList(sortBy: String?) = Pager(
        config = PagingConfig(
            pageSize = 20,
            initialLoadSize = 20,
            prefetchDistance = 10
        ),
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

    override suspend fun getRoleById(id: Int) = doRequest<RoleItem> { apiService.getRoleById(id) }

    override suspend fun updateRolePermissions(id: Int, request: UpdatePermissionStatus) = doRequest<RoleItem, RoleItem>(
        request = { apiService.updatePermissions(id, request) },
        mapper = { it }
    )

    override suspend fun createRole(request: RoleRequest) = doRequest<Role, Role>(
        request = { apiService.createRole(request) },
        mapper = { it }
    )

    override suspend fun deleteRole(id: Int) = doRequest<Boolean, Boolean>(
        request = { apiService.deleteRole(id) },
        mapper = { it }
    )


}