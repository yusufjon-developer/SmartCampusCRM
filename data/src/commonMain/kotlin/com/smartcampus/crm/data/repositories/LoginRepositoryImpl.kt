package com.smartcampus.crm.data.repositories

import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.remote.apiServices.LoginApiService
import com.smartcampus.crm.domain.models.Groups
import com.smartcampus.crm.domain.models.LoginResponse
import com.smartcampus.crm.domain.models.Specialities
import com.smartcampus.crm.domain.models.UserRequest
import com.smartcampus.crm.domain.models.student.Student
import com.smartcampus.crm.domain.models.student.StudentInfo
import com.smartcampus.crm.domain.repositories.LoginRepository
import com.smartcampus.crm.domain.repositories.StudentRepository
import com.smartcampus.crm.domain.utils.Either
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(
    private val loginApiService: LoginApiService
) : LoginRepository, BaseRepository(), StudentRepository {
    override suspend fun login(user: UserRequest) = doRequest<LoginResponse, LoginResponse>(
        request = { loginApiService.login(user) },
        mapper = { it }
    )

    override fun getStudentInfo(id: Int) = flow {
        emit(Either.Right(StudentInfo(
            Student(
                groups = Groups(
                    1,
                    "group",
                    Specialities(
                        1,
                        "spec"
                    ),
                    2
                )
            )
        )))
    }
}