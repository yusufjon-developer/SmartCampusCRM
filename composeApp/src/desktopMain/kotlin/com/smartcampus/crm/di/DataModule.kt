package com.smartcampus.crm.di

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import com.smartcampus.crm.data.preferencesKeys.PREFERENCE_STORE
import com.smartcampus.crm.data.repositories.LoginRepositoryImpl
import com.smartcampus.crm.data.repositories.SecurityRepositoryImpl
import com.smartcampus.crm.data.repositories.SessionManagerImpl
import com.smartcampus.crm.data.repositories.SettingsManagerImpl
import com.smartcampus.crm.data.repositories.TokenManagerImpl
import com.smartcampus.crm.domain.repositories.LoginRepository
import com.smartcampus.crm.domain.repositories.SecurityRepository
import com.smartcampus.crm.domain.repositories.SessionManager
import com.smartcampus.crm.domain.repositories.SettingsManager
import com.smartcampus.crm.domain.repositories.StudentRepository
import com.smartcampus.crm.domain.repositories.TokenManager
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
}