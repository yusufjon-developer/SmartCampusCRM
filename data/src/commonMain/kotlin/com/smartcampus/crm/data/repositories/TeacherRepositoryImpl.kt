package com.smartcampus.crm.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.base.pagination.BasePagingSource
import com.smartcampus.crm.data.base.pagination.PagingResponse
import com.smartcampus.crm.data.remote.apiServices.TeachersApiService
import com.smartcampus.crm.domain.models.*
import com.smartcampus.crm.domain.repositories.TeacherRepository
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

class TeacherRepositoryImpl(
    private val apiService: TeachersApiService
) : TeacherRepository, BaseRepository() {

    override suspend fun getTeacherList(sortBy: String?): Flow<PagingData<TeacherDto>> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 10),
        pagingSourceFactory = {
            BasePagingSource { pageNumber ->
                doRequest<PagingResponse<TeacherDto>> {
                    apiService.getTeachers(
                        page = pageNumber,
                        size = 20,
                        sortedBy = sortBy
                    )
                }
            }
        }
    ).flow

    override suspend fun getTeacherById(id: Int): RemoteWrapper<TeacherDto> =
        doRequest { apiService.getTeacherById(id) }

    override suspend fun getTeacherInfoById(id: Int): RemoteWrapper<TeacherSensitiveDto> =
        doRequest { apiService.getTeacherInfoById(id) }

    override suspend fun updateTeacher(id: Int, request: TeacherUpdateRequest): RemoteWrapper<TeacherDto> =
        doRequest { apiService.updateTeacher(id, request) }

    override suspend fun deleteTeacher(id: Int): RemoteWrapper<Unit> =
        doRequest { apiService.deleteTeacher(id) }
}