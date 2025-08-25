package com.smartcampus.crm.data.manager

import com.smartcampus.crm.domain.models.managers.SessionManager
import com.smartcampus.crm.domain.models.managers.TokenManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SessionManagerImpl(
    private val tokenManager: TokenManager
) : SessionManager {
    private val _onForceLogout = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1)
    override val onForceLogout: SharedFlow<Unit> = _onForceLogout.asSharedFlow()

    override suspend fun notifyForceLogout() {
        tokenManager.deleteAccessToken()
        _onForceLogout.emit(Unit)
    }
}