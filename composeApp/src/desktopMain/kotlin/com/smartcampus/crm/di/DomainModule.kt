package com.smartcampus.crm.di

import com.smartcampus.crm.domain.useCases.GetStudentInfoUseCase
import com.smartcampus.crm.domain.useCases.LoginUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val DomainModule = module {
    singleOf(::LoginUseCase)
    singleOf(::GetStudentInfoUseCase)
}