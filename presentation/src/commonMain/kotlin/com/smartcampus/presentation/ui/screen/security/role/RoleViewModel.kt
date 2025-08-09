package com.smartcampus.presentation.ui.screen.security.role

import com.smartcampus.presentation.core.base.BaseViewModel

class RoleViewModel(

) : BaseViewModel<RoleContract.Event, RoleContract.Effect, RoleContract.State>() {
    override fun createInitialState() = RoleContract.State

    override fun handleEvent(event: RoleContract.Event) {
        // TODO
    }
}