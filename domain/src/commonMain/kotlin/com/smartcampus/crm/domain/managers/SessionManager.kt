package com.smartcampus.crm.domain.managers

import kotlinx.coroutines.flow.SharedFlow

interface SessionManager {
    val onForceLogout: SharedFlow<Unit>
    suspend fun notifyForceLogout()
}