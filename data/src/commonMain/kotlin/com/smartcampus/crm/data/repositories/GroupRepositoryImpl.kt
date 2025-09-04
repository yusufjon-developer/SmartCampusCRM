package com.smartcampus.crm.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.base.pagination.BasePagingSource
import com.smartcampus.crm.data.base.pagination.PagingResponse
import com.smartcampus.crm.data.remote.apiServices.GroupApiService
import com.smartcampus.crm.domain.models.*
import com.smartcampus.crm.domain.repositories.GroupRepository
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

class GroupRepositoryImpl(
    private val apiService: GroupApiService
) : GroupRepository, BaseRepository() {

    override suspend fun getGroupList(sortBy: String?): Flow<PagingData<GroupDto>> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 10),
        pagingSourceFactory = {
            BasePagingSource { pageNumber ->
                doRequest<PagingResponse<GroupDto>> {
                    apiService.getGroups(
                        page = pageNumber,
                        size = 20,
                        sortedBy = sortBy
                    )
                }
            }
        }
    ).flow

    override suspend fun getGroupById(id: Int): RemoteWrapper<GroupDto> =
        doRequest { apiService.getGroupById(id) }

    override suspend fun createGroup(request: GroupCreateRequest): RemoteWrapper<GroupDto> =
        doRequest { apiService.createGroup(request) }

    override suspend fun updateGroup(id: Int, request: GroupUpdateRequest): RemoteWrapper<GroupDto> =
        doRequest { apiService.updateGroup(id, request) }

    override suspend fun deleteGroup(id: Int): RemoteWrapper<Unit> =
        doRequest { apiService.deleteGroup(id) }

}