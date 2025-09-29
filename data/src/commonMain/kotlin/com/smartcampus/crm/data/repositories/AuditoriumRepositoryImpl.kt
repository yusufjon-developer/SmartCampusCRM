package com.smartcampus.crm.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.base.pagination.BasePagingSource
import com.smartcampus.crm.data.base.pagination.PagingResponse
import com.smartcampus.crm.data.remote.apiServices.AuditoriumApiService
import com.smartcampus.crm.domain.models.AuditoriumCreateRequest
import com.smartcampus.crm.domain.models.AuditoriumDto
import com.smartcampus.crm.domain.models.AuditoriumUpdateRequest
import com.smartcampus.crm.domain.repositories.AuditoriumRepository
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

class AuditoriumRepositoryImpl(
    private val apiService: AuditoriumApiService
) : AuditoriumRepository, BaseRepository() {

    override suspend fun getAuditoriumList(sortBy: String?, isAvailable: Boolean?, day: String): Flow<PagingData<AuditoriumDto>> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 10),
        pagingSourceFactory = {
            BasePagingSource { pageNumber ->
                doRequest<PagingResponse<AuditoriumDto>> {
                    apiService.getAuditoriums(
                        page = pageNumber,
                        size = 20,
                        sortedBy = sortBy,
                        isAvailable = isAvailable,
                        day = day
                    )
                }
            }
        }
    ).flow

    override suspend fun getAuditoriumById(id: Int): RemoteWrapper<AuditoriumDto> =
        doRequest { apiService.getAuditoriumById(id) }

    override suspend fun createAuditorium(request: AuditoriumCreateRequest): RemoteWrapper<AuditoriumDto> =
        doRequest { apiService.createAuditorium(request) }

    override suspend fun updateAuditorium(id: Int, request: AuditoriumUpdateRequest): RemoteWrapper<AuditoriumDto> =
        doRequest { apiService.updateAuditorium(id, request) }

    override suspend fun deleteAuditorium(id: Int): RemoteWrapper<Unit> =
        doRequest { apiService.deleteAuditorium(id) }
}