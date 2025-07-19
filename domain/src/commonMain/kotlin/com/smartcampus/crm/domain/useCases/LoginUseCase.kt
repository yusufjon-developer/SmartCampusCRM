package com.smartcampus.crm.domain.useCases

import com.smartcampus.crm.domain.models.UserRequest
import com.smartcampus.crm.domain.repositories.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(user: UserRequest) =  loginRepository.login(user)
}