package com.smartcampus.presentation.ui.screen.home

import com.smartcampus.presentation.core.base.BaseViewModel

class HomeViewModel(

) : BaseViewModel<HomeContract.Event, HomeContract.Effect, HomeContract.State>() {
    override fun createInitialState() = HomeContract.State()

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.NavigateToAuditorium -> {
                setEffect { HomeContract.Effect.NavigateToAuditorium }
            }
            HomeContract.Event.NavigateToGroup -> {
                setEffect { HomeContract.Effect.NavigateToGroup }
            }
        }
    }
}