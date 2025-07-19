package com.smartcampus.crm.domain.repositories

import kotlinx.coroutines.flow.Flow

interface TokenManager {
    suspend fun getAccessToken(): Flow<String?>
    suspend fun saveAccessToken(token: String)
    suspend fun deleteAccessToken()
}