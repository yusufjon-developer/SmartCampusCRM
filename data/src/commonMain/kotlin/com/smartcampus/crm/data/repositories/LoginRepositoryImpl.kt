package com.smartcampus.crm.data.repositories

import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.remote.apiServices.LoginApiService
import com.smartcampus.crm.domain.models.LoginResponse
import com.smartcampus.crm.domain.models.UserRequest
import com.smartcampus.crm.domain.repositories.LoginRepository

class LoginRepositoryImpl(
    private val loginApiService: LoginApiService
) : LoginRepository, BaseRepository() {
    override suspend fun login(user: UserRequest) = doRequest<LoginResponse, LoginResponse>(
        request = { loginApiService.login(user) },
        mapper = { it }
    )
}