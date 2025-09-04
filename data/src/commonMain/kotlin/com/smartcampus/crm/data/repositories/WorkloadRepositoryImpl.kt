package com.smartcampus.crm.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.base.pagination.BasePagingSource
import com.smartcampus.crm.data.base.pagination.PagingResponse
import com.smartcampus.crm.data.remote.apiServices.WorkloadApiService
import com.smartcampus.crm.domain.models.*
import com.smartcampus.crm.domain.repositories.WorkloadRepository
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

class WorkloadRepositoryImpl(
    private val apiService: WorkloadApiService
) : WorkloadRepository, BaseRepository() {

    override suspend fun getWorkloadList(sortBy: String?): Flow<PagingData<TeacherWorkloadDto>> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 10),
        pagingSourceFactory = {
            BasePagingSource { pageNumber ->
                doRequest<PagingResponse<TeacherWorkloadDto>> {
                    apiService.getWorkloads(
                        page = pageNumber,
                        size = 20,
                        sortedBy = sortBy
                    )
                }
            }
        }
    ).flow

    override suspend fun getWorkloadById(id: Int): RemoteWrapper<TeacherWorkloadDto> =
        doRequest { apiService.getWorkloadById(id) }

    override suspend fun createWorkload(request: TeacherWorkloadCreateRequest): RemoteWrapper<TeacherWorkloadDto> =
        doRequest { apiService.createWorkload(request) }

    override suspend fun updateWorkload(id: Int, request: TeacherWorkloadUpdateRequest): RemoteWrapper<TeacherWorkloadDto> =
        doRequest { apiService.updateWorkload(id, request) }

    override suspend fun deleteWorkload(id: Int): RemoteWrapper<Unit> =
        doRequest { apiService.deleteWorkload(id) }

    override suspend fun getExecutions(workloadId: Int): RemoteWrapper<List<WorkloadExecutionDto>> =
        doRequest { apiService.getExecutions(workloadId) }

    override suspend fun createExecution(workloadId: Int, request: WorkloadExecutionCreateRequest): RemoteWrapper<WorkloadExecutionDto> =
        doRequest { apiService.createExecution(workloadId, request) }

    override suspend fun getSum(workloadId: Int): RemoteWrapper<ExecutedHoursResponse> =
        doRequest { apiService.getSum(workloadId) }

    override suspend fun getTeacherWorkload(teacherId: Int, academicYear: String?): RemoteWrapper<List<TeacherWorkloadDto>> =
        doRequest { apiService.getTeacherWorkload(teacherId, academicYear) }
}