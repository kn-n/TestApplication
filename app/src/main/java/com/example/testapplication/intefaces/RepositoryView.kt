package com.example.testapplication.intefaces

import com.example.testapplication.SQLite.SQLiteHelper
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface RepositoryView : MvpView {
    fun showInformation(helper: SQLiteHelper)
    fun addToSaved()
    fun deleteFromSaved()
    fun back()
}