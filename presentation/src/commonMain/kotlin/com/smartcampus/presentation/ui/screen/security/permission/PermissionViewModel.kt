package com.smartcampus.presentation.ui.screen.security.permission

import com.smartcampus.presentation.core.base.BaseViewModel

class PermissionViewModel(

) : BaseViewModel<PermissionContract.Event, PermissionContract.Effect, PermissionContract.State>() {
    override fun createInitialState() = PermissionContract.State

    override fun handleEvent(event: PermissionContract.Event) {

    }
}