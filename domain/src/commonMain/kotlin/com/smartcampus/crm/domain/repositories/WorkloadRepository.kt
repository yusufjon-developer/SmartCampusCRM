package com.smartcampus.crm.domain.repositories

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.ExecutedHoursResponse
import com.smartcampus.crm.domain.models.TeacherWorkloadCreateRequest
import com.smartcampus.crm.domain.models.TeacherWorkloadDto
import com.smartcampus.crm.domain.models.TeacherWorkloadUpdateRequest
import com.smartcampus.crm.domain.models.WorkloadExecutionCreateRequest
import com.smartcampus.crm.domain.models.WorkloadExecutionDto
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

interface WorkloadRepository {
    suspend fun getWorkloadList(sortBy: String?): Flow<PagingData<TeacherWorkloadDto>>
    suspend fun getWorkloadById(id: Int): RemoteWrapper<TeacherWorkloadDto>
    suspend fun createWorkload(request: TeacherWorkloadCreateRequest): RemoteWrapper<TeacherWorkloadDto>
    suspend fun updateWorkload(id: Int, request: TeacherWorkloadUpdateRequest): RemoteWrapper<TeacherWorkloadDto>
    suspend fun deleteWorkload(id: Int): RemoteWrapper<Unit>
    suspend fun getExecutions(workloadId: Int): RemoteWrapper<List<WorkloadExecutionDto>>
    suspend fun createExecution(workloadId: Int, request: WorkloadExecutionCreateRequest): RemoteWrapper<WorkloadExecutionDto>
    suspend fun getSum(workloadId: Int): RemoteWrapper<ExecutedHoursResponse>
    suspend fun getTeacherWorkload(teacherId: Int, academicYear: String?): RemoteWrapper<List<TeacherWorkloadDto>>
}