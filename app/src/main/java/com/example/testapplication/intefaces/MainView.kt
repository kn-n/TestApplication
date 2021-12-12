package com.example.testapplication.intefaces

import android.content.Context
import com.example.testapplication.models.Repository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface MainView : MvpView {
    fun search(query: String)
    fun showResult(listRepository: ArrayList<Repository>)
    fun deleteAllSaved()
    fun showError(t: Throwable)
    fun signOut()
    fun saveLastSearchResult(listRepository: ArrayList<Repository>)
}