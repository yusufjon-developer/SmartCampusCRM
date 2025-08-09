package com.smartcampus.crm.di

import com.smartcampus.crm.domain.useCases.GetStudentInfoUseCase
import com.smartcampus.crm.domain.useCases.GetThemeUseCase
import com.smartcampus.crm.domain.useCases.LoginUseCase
import com.smartcampus.crm.domain.useCases.RoleUseCases
import com.smartcampus.crm.domain.useCases.SetThemeUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val DomainModule = module {
    singleOf(::LoginUseCase)
    singleOf(::GetStudentInfoUseCase)
    singleOf(::GetThemeUseCase)
    singleOf(::SetThemeUseCase)
    singleOf(::RoleUseCases)
}