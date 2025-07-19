package com.smartcampus.crm.domain.repositories

import kotlinx.coroutines.flow.SharedFlow

interface SessionManager {
    val onForceLogout: SharedFlow<Unit>
    suspend fun notifyForceLogout()
}