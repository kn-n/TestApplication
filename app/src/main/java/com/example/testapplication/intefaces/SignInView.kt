package com.example.testapplication.intefaces

import android.os.Message
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface SignInView : MvpView {
    fun signIn()
    fun signInWithGoogle()
    fun showError(id:String)
}