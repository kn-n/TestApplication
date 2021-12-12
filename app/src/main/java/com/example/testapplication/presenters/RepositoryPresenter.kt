package com.example.testapplication.presenters

import android.content.Context
import com.example.testapplication.SQLite.SQLiteHelper
import com.example.testapplication.Screens.Main
import com.example.testapplication.intefaces.RepositoryView
import com.example.testapplication.models.Repository
import com.example.testapplication.models.SavedRepository
import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class RepositoryPresenter @Inject constructor(private val router: Router) :
    MvpPresenter<RepositoryView>() {

    fun back(id: String, tabIndex: Int) {
        router.backTo(Main(id, tabIndex))
    }

    fun addRepositoryToSaved(id: String, repository: Repository, context: Context) {
        val helper = SQLiteHelper(context)
        val savedRepository = SavedRepository(
            repository.full_name,
            id,
            repository.full_name,
            repository.description,
            repository.forks,
            repository.watchers,
            repository.created_at,
            repository.owner.login,
            repository.owner.avatar_url
        )
        helper.insertSavedRepository(savedRepository)
    }

    fun deleteRepositoryFromSaved(id: String, name:String, context: Context){
        val helper = SQLiteHelper(context)
        helper.deleteUsersSavedRepository(id, name)
    }

}