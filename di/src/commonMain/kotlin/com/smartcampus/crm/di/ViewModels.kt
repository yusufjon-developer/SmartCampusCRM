package com.smartcampus.crm.di

import com.smartcampus.presentation.ui.screen.login.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewModels = module {
    viewModelOf(::LoginViewModel)
}