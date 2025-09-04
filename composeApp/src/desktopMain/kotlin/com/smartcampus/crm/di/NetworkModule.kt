package com.smartcampus.crm.di

import com.smartcampus.crm.data.remote.apiServices.AuditoriumApiService
import com.smartcampus.crm.data.remote.apiServices.GroupApiService
import com.smartcampus.crm.data.remote.apiServices.LoginApiService
import com.smartcampus.crm.data.remote.apiServices.RoleApiService
import com.smartcampus.crm.data.remote.apiServices.ScheduleApiService
import com.smartcampus.crm.data.remote.apiServices.SecurityApiService
import com.smartcampus.crm.data.remote.apiServices.TeachersApiService
import com.smartcampus.crm.data.remote.apiServices.UserApiService
import com.smartcampus.crm.data.remote.apiServices.WorkloadApiService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val NetworkModule = module {
    singleOf(::AuditoriumApiService)
    singleOf(::GroupApiService)
    singleOf(::LoginApiService)
    singleOf(::RoleApiService)
    singleOf(::ScheduleApiService)
    singleOf(::SecurityApiService)
    singleOf(::TeachersApiService)
    singleOf(::UserApiService)
    singleOf(::WorkloadApiService)
}