package com.example.testapplication

import androidx.fragment.app.Fragment
import com.example.testapplication.fragments.MainFragment
import com.example.testapplication.fragments.RepositoryFragment
import com.example.testapplication.fragments.SignInFragment
import com.example.testapplication.models.Repository
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.gms.auth.api.signin.GoogleSignInClient

object Screens {
    fun SignIn() = FragmentScreen { SignInFragment() }
    fun Main(id: String, tabIndex: Int) = FragmentScreen { MainFragment(id, tabIndex) }
    fun Repository(
        id: String,
        rep: Repository,
        tabIndex: Int
    ) = FragmentScreen {
        RepositoryFragment(
            id,
            rep,
            tabIndex
        )
    }
}