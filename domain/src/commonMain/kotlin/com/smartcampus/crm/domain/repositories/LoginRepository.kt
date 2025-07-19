package com.smartcampus.crm.domain.repositories

import com.smartcampus.crm.domain.models.LoginResponse
import com.smartcampus.crm.domain.models.UserRequest
import com.smartcampus.crm.domain.utils.RemoteWrapper

interface LoginRepository {
    suspend fun login(user: UserRequest): RemoteWrapper<LoginResponse>
}