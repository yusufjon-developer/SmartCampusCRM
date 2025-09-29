package com.smartcampus.crm.domain.useCases

import com.smartcampus.crm.domain.models.LoginRequest
import com.smartcampus.crm.domain.repositories.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(request: LoginRequest) = loginRepository.login(request)
}