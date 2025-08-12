package com.smartcampus.crm.di

import com.smartcampus.crm.data.remote.apiServices.LoginApiService
import com.smartcampus.crm.data.remote.apiServices.SecurityApiService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val NetworkModule = module {
    singleOf(::LoginApiService)
    singleOf(::SecurityApiService)
}