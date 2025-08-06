package com.smartcampus.crm.domain.repositories

import com.smartcampus.crm.domain.models.LoginResponse
import com.smartcampus.crm.domain.models.LoginRequest
import com.smartcampus.crm.domain.utils.RemoteWrapper

interface LoginRepository {
    suspend fun login(request: LoginRequest): RemoteWrapper<LoginResponse>
}