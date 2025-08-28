package com.smartcampus.crm.domain.repositories

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.security.User
import com.smartcampus.crm.domain.models.security.UserPermission
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserList(sortBy: String?): Flow<PagingData<User>>
    suspend fun getUserById(id: Int): RemoteWrapper<UserPermission>
}