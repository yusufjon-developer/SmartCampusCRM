package com.smartcampus.crm.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.base.pagination.BasePagingSource
import com.smartcampus.crm.data.base.pagination.PagingResponse
import com.smartcampus.crm.data.remote.apiServices.RoleApiService
import com.smartcampus.crm.domain.models.security.Role
import com.smartcampus.crm.domain.models.security.RolePermission
import com.smartcampus.crm.domain.models.security.RoleRequest
import com.smartcampus.crm.domain.models.security.UpdatePermissionStatus
import com.smartcampus.crm.domain.repositories.RoleRepository
import com.smartcampus.crm.domain.utils.RemoteWrapper

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
                doRequest<PagingResponse<Role>> {
                    apiService.getRoleList(
                        page = pageNumber,
                        size = 20,
                        sortedBy = sortBy
                    )
                }
            }
        }
    ).flow

    override suspend fun getRoleById(id: Int): RemoteWrapper<RolePermission> =
        doRequest { apiService.getRoleById(id) }

    override suspend fun updateRolePermissions(id: Int, request: UpdatePermissionStatus): RemoteWrapper<RolePermission> =
        doRequest { apiService.updatePermissions(id, request) }

    override suspend fun createRole(request: RoleRequest): RemoteWrapper<Role> =
        doRequest { apiService.createRole(request) }

    override suspend fun deleteRole(id: Int): RemoteWrapper<Unit> =
        doRequest { apiService.deleteRole(id) }
}