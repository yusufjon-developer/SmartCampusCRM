package com.smartcampus.crm.data.repositories

import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.remote.apiServices.LoginApiService
import com.smartcampus.crm.domain.models.LoginRequest
import com.smartcampus.crm.domain.models.LoginResponse
import com.smartcampus.crm.domain.repositories.LoginRepository
import com.smartcampus.crm.domain.utils.RemoteWrapper

class LoginRepositoryImpl(
    private val loginApiService: LoginApiService
) : LoginRepository, BaseRepository() {
    override suspend fun login(request: LoginRequest): RemoteWrapper<LoginResponse> =
        doRequest {
            loginApiService.login(request)
        }
}