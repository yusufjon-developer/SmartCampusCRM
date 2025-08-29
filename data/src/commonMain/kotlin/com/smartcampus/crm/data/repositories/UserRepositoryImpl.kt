package com.smartcampus.crm.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.base.pagination.BasePagingSource
import com.smartcampus.crm.data.base.pagination.PagingResponse
import com.smartcampus.crm.data.remote.apiServices.UserApiService
import com.smartcampus.crm.domain.models.security.UpdateUserPermissions
import com.smartcampus.crm.domain.models.security.User
import com.smartcampus.crm.domain.models.security.UserPermission
import com.smartcampus.crm.domain.repositories.UserRepository
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val apiService: UserApiService
) : UserRepository, BaseRepository() {
    override suspend fun getUserList(sortBy: String?): Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            initialLoadSize = 20,
            prefetchDistance = 10
        ),
        pagingSourceFactory = {
            BasePagingSource { pageNumber ->
                doRequest<PagingResponse<User>> {
                    apiService.getUserList(
                        page = pageNumber,
                        size = 20,
                        sortedBy = sortBy
                    )
                }
            }
        }
    ).flow

    override suspend fun getUserById(id: Int): RemoteWrapper<UserPermission> =
        doRequest { apiService.getUserById(id) }

    override suspend fun updateUserPermission(
        userId: Int,
        request: UpdateUserPermissions
    ): RemoteWrapper<UserPermission> =
        doRequest<UserPermission> {
            apiService.updateUserPermission(userId, request)
        }

}