package com.smartcampus.crm.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.base.pagination.BasePagingSource
import com.smartcampus.crm.data.base.pagination.PagingResponse
import com.smartcampus.crm.data.remote.apiServices.StudentApiService
import com.smartcampus.crm.domain.models.auth.RegisterRequest
import com.smartcampus.crm.domain.models.student.Student
import com.smartcampus.crm.domain.models.student.StudentInfo
import com.smartcampus.crm.domain.models.student.StudentUpdateRequest
import com.smartcampus.crm.domain.repositories.StudentRepository
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

class StudentRepositoryImpl(
private val apiService: StudentApiService
) : StudentRepository, BaseRepository() {

    override fun getStudents(): Flow<PagingData<Student>> =
        Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 10),
            pagingSourceFactory = {
                BasePagingSource { pageNumber ->
                    doRequest<PagingResponse<Student>> {
                        apiService.getStudentsList(page = pageNumber, size = 20)
                    }
                }
            }
        ).flow

    override fun getStudent(id: Int): RemoteWrapper<Student> =
        doRequest { apiService.getStudentById(id) }

    override fun getStudentInfo(id: Int): RemoteWrapper<StudentInfo> =
        doRequest { apiService.getStudentInfoById(id) }

    override fun updateStudentProfile(id: Int, student: StudentUpdateRequest): RemoteWrapper<String> =
        doRequest { apiService.updateStudentProfile(id, student) }

    override fun registerStudent(request: RegisterRequest): RemoteWrapper<String> =
        doRequest { apiService.registerStudent(request) }
}