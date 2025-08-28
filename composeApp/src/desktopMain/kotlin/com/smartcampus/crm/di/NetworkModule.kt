package com.smartcampus.crm.di

import com.smartcampus.crm.data.remote.apiServices.LoginApiService
import com.smartcampus.crm.data.remote.apiServices.RoleApiService
import com.smartcampus.crm.data.remote.apiServices.SecurityApiService
import com.smartcampus.crm.data.remote.apiServices.UserApiService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val NetworkModule = module {
    singleOf(::LoginApiService)
    singleOf(::SecurityApiService)
    singleOf(::RoleApiService)
    singleOf(::UserApiService)
}