package com.example.testapplication

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.example.testapplication.toothpick.DI
import com.example.testapplication.toothpick.appModule
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
        initAppScope(Toothpick.openScope(DI.APP_SCOPE))
    }

    @VisibleForTesting
    fun initAppScope(appScope: Scope){
        appScope.installModules(
            appModule(this)
        )
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG){
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction().preventMultipleRootScopes())
        }
    }
}