package com.example.testapplication

import com.example.testapplication.Screens.SignIn
import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ActivityPresenter @Inject constructor(private val router: Router) : MvpPresenter<ActivityView>() {
    fun startActivity() {
        router.navigateTo(SignIn())
    }
}