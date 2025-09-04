package com.smartcampus.crm.di

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import com.smartcampus.crm.data.manager.SessionManagerImpl
import com.smartcampus.crm.data.manager.SettingsManagerImpl
import com.smartcampus.crm.data.manager.TokenManagerImpl
import com.smartcampus.crm.data.preferencesKeys.PREFERENCE_STORE
import com.smartcampus.crm.data.repositories.AuditoriumRepositoryImpl
import com.smartcampus.crm.data.repositories.GroupRepositoryImpl
import com.smartcampus.crm.data.repositories.LoginRepositoryImpl
import com.smartcampus.crm.data.repositories.RoleRepositoryImpl
import com.smartcampus.crm.data.repositories.ScheduleRepositoryImpl
import com.smartcampus.crm.data.repositories.SecurityRepositoryImpl
import com.smartcampus.crm.data.repositories.TeacherRepositoryImpl
import com.smartcampus.crm.data.repositories.UserRepositoryImpl
import com.smartcampus.crm.data.repositories.WorkloadRepositoryImpl
import com.smartcampus.crm.domain.managers.SessionManager
import com.smartcampus.crm.domain.managers.SettingsManager
import com.smartcampus.crm.domain.managers.TokenManager
import com.smartcampus.crm.domain.repositories.AuditoriumRepository
import com.smartcampus.crm.domain.repositories.GroupRepository
import com.smartcampus.crm.domain.repositories.LoginRepository
import com.smartcampus.crm.domain.repositories.RoleRepository
import com.smartcampus.crm.domain.repositories.ScheduleRepository
import com.smartcampus.crm.domain.repositories.SecurityRepository
import com.smartcampus.crm.domain.repositories.StudentRepository
import com.smartcampus.crm.domain.repositories.TeacherRepository
import com.smartcampus.crm.domain.repositories.UserRepository
import com.smartcampus.crm.domain.repositories.WorkloadRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.prefs.Preferences

@OptIn(ExperimentalSettingsApi::class)
val DataModule = module {

    single<Preferences> {
        Preferences.userRoot().node(PREFERENCE_STORE)
    }

    single<FlowSettings> {
        PreferencesSettings(get<Preferences>()).toFlowSettings()
    }

    singleOf(::SettingsManagerImpl) bind SettingsManager::class
    singleOf(::TokenManagerImpl) bind TokenManager::class
    singleOf(::SessionManagerImpl) bind SessionManager::class

    singleOf(::LoginRepositoryImpl) bind LoginRepository::class
    singleOf(::LoginRepositoryImpl) bind StudentRepository::class

    singleOf(::SecurityRepositoryImpl) bind SecurityRepository::class

    singleOf(::RoleRepositoryImpl) bind RoleRepository::class
    singleOf(::UserRepositoryImpl) bind UserRepository::class

    singleOf(::ScheduleRepositoryImpl) bind ScheduleRepository::class
    singleOf(::GroupRepositoryImpl) bind GroupRepository::class
    singleOf(::AuditoriumRepositoryImpl) bind AuditoriumRepository::class
    singleOf(::WorkloadRepositoryImpl) bind WorkloadRepository::class
    singleOf(::TeacherRepositoryImpl) bind TeacherRepository::class
}