package com.example.testapplication.presenters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.testapplication.SQLite.SQLiteHelper
import com.example.testapplication.Screens.Main
import com.example.testapplication.fragments.SignInFragment
import com.example.testapplication.intefaces.SignInView
import com.example.testapplication.models.User
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class SignInPresenter @Inject constructor(private val router: Router) : MvpPresenter<SignInView>() {

    fun signIn(id: String) {
        router.navigateTo(Main(id, 0))
    }

    fun signIn(id: String, context: Context) {
        val user = User(id)
        val helper = SQLiteHelper(context)
        helper.insertUser(user)
        router.navigateTo(Main(id, 0))
    }
}