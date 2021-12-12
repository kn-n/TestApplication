package com.example.testapplication.presenters

import android.content.Context
import com.example.testapplication.Screens.SignIn
import com.example.testapplication.api.RetrofitClient
import com.example.testapplication.intefaces.MainView
import com.example.testapplication.makeClient
import com.example.testapplication.models.Repositories
import com.example.testapplication.models.Repository
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(private val router: Router) : MvpPresenter<MainView>() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    fun search(query: String){
        compositeDisposable.add(RetrofitClient.buildService()
            .getSearchRepositories(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))
    }

    private fun onResponse(list: Repositories){
        viewState.saveLastSearchResult(ArrayList(list.items))
        showResult(ArrayList(list.items))
    }

    private fun onFailure(t: Throwable) {
//        viewState.showError(t)
        val emptyList: ArrayList<Repository> = ArrayList()
        showResult(ArrayList(emptyList))
    }

    fun signOut(context: Context){
        makeClient(context).signOut()
        router.navigateTo(SignIn())
    }

    fun signOut(){
        router.navigateTo(SignIn())
    }

    fun showResult(list: ArrayList<Repository>){
        viewState.showResult(list)
    }
}