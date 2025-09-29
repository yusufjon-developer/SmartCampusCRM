package com.smartcampus.crm.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingData
import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.base.pagination.BasePagingSource
import com.smartcampus.crm.data.base.pagination.PagingResponse
import com.smartcampus.crm.data.remote.apiServices.SpecialityApiService
import com.smartcampus.crm.domain.models.SpecialityCreateRequest
import com.smartcampus.crm.domain.models.SpecialityDto
import com.smartcampus.crm.domain.models.SpecialityUpdateRequest
import com.smartcampus.crm.domain.repositories.SpecialityRepository
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

class SpecialityRepositoryImpl(
    private val apiService: SpecialityApiService
) : SpecialityRepository, BaseRepository() {
    override suspend fun getSpecialityList(sortBy: String?): Flow<PagingData<SpecialityDto>> = Pager(
        config = _root_ide_package_.app.cash.paging.PagingConfig(
            pageSize = 20,
            initialLoadSize = 20,
            prefetchDistance = 10
        ),
        pagingSourceFactory = {
            BasePagingSource { pageNumber ->
                doRequest<PagingResponse<SpecialityDto>> {
                    apiService.getSpecialities(
                        page = pageNumber,
                        size = 20,
                        sortedBy = sortBy
                    )
                }
            }
        }
    ).flow

    override suspend fun getSpecialityById(id: Int): RemoteWrapper<SpecialityDto> =
        doRequest { apiService.getSpecialityById(id) }

    override suspend fun createSpeciality(request: SpecialityCreateRequest): RemoteWrapper<SpecialityDto> =
        doRequest { apiService.createSpeciality(request) }

    override suspend fun updateSpeciality(id: Int, request: SpecialityUpdateRequest): RemoteWrapper<SpecialityDto> =
        doRequest { apiService.updateSpeciality(id, request) }

    override suspend fun deleteSpeciality(id: Int): RemoteWrapper<Unit> =
        doRequest { apiService.deleteSpeciality(id) }
}