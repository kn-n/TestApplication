package com.example.testapplication


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.testapplication.toothpick.DI
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import javax.inject.Inject

class Activity : MvpAppCompatActivity(), ActivityView {

    @InjectPresenter
    lateinit var activityPresenter: ActivityPresenter

    @ProvidePresenter
    fun provideActivityPresenter() = Toothpick.openScope(DI.APP_SCOPE).getInstance(ActivityPresenter::class.java)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this@Activity, Toothpick.openScope(DI.APP_SCOPE))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityPresenter.startActivity()
    }

    private val navigator = AppNavigator(this, R.id.container)

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        makeClient(this).signOut()
        super.onDestroy()
    }

}