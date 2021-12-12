package com.example.testapplication.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.testapplication.SQLite.SQLiteHelper
import com.example.testapplication.databinding.FragmentSignInBinding
import com.example.testapplication.intefaces.SignInView
import com.example.testapplication.makeClient
import com.example.testapplication.presenters.SignInPresenter
import com.example.testapplication.toothpick.DI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick


class SignInFragment : MvpAppCompatFragment(), SignInView {

    @InjectPresenter
    lateinit var signInPresenter: SignInPresenter

    @ProvidePresenter
    fun providerSignInPresenter() =
        Toothpick.openScope(DI.APP_SCOPE).getInstance(SignInPresenter::class.java)

    lateinit var mGoogleSignInClient: GoogleSignInClient

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.signIn.setOnClickListener { signIn() }
        binding.signInWithGoogle.setOnClickListener { signInWithGoogle() }

        return binding.root
    }

    private val launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(
                    ApiException::class.java
                )
                // Signed in successfully
                val googleId = account?.id ?: ""
//                Toast.makeText(context, googleId, Toast.LENGTH_SHORT).show()

                signInPresenter.signIn(googleId,requireContext())

            } catch (e: ApiException) {
                // Sign in was unsuccessful
                Toast.makeText(context,"Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        }

    override fun signInWithGoogle() {
        val context: Context = requireContext()
        mGoogleSignInClient = makeClient(context)
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        launchSomeActivity.launch(signInIntent)

    }

    override fun signIn() {
        signInPresenter.signIn("")
    }

    override fun showError(id: String) {
        Toast.makeText(context, id, Toast.LENGTH_SHORT).show()
    }
}