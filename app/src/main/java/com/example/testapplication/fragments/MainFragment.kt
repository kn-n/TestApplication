package com.example.testapplication.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplication.SQLite.SQLiteHelper
import com.example.testapplication.adapter.RecyclerAdapter
import com.example.testapplication.databinding.FragmentMainBinding
import com.example.testapplication.hideKeyboard
import com.example.testapplication.intefaces.MainView
import com.example.testapplication.models.Repository
import com.example.testapplication.presenters.MainPresenter
import com.example.testapplication.toothpick.DI
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.tabs.TabLayout
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import toothpick.ktp.extension.getInstance

class MainFragment(private val id: String, private var tabIndex: Int) : MvpAppCompatFragment(),
    MainView {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter() =
        Toothpick.openScope(DI.APP_SCOPE).getInstance(MainPresenter::class.java)

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var listRepo: ArrayList<Repository> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        val helper = SQLiteHelper(requireContext())

        binding.tabLayout.getTabAt(tabIndex)!!.select()

        if (id != "") {
            binding.tabLayout.visibility = View.VISIBLE
        } else {
            binding.tabLayout.visibility = View.GONE
        }

        binding.listOfRepositories.layoutManager = LinearLayoutManager(context)

        binding.searchBtn.setOnClickListener {
            search(binding.searchText.text.toString())
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab) {
                    binding.tabLayout.getTabAt(0) -> {
                        tabIndex = 0
                        mainPresenter.showResult(listRepo)
                        binding.searchBtn.setOnClickListener {
                            search(binding.searchText.text.toString())
                        }
                    }
                    binding.tabLayout.getTabAt(1) -> {
                        tabIndex = 1
                        val list = helper.getUsersSavedRepositories(id)
                        mainPresenter.showResult(list)
                        binding.searchBtn.setOnClickListener {
                            val foundRep: ArrayList<Repository> = ArrayList()
                            for (rep in list) {
                                if (rep.full_name.contains(binding.searchText.text.toString()) || rep.description.contains(
                                        binding.searchText.text.toString()
                                    )
                                ) {
                                    foundRep.add(rep)
                                }
                            }
                            mainPresenter.showResult(foundRep)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        binding.signOut.setOnClickListener { signOut() }

        return binding.root
    }

    override fun search(query: String) {
        context?.hideKeyboard(requireView())
        mainPresenter.search(query)
    }

    override fun showResult(listRepository: ArrayList<Repository>) {
        binding.listOfRepositories.adapter =
            RecyclerAdapter(
                Toothpick.openScope(DI.APP_SCOPE).getInstance(),
                listRepository,
                id,
                requireContext(),
                tabIndex.toString()
            )
    }

    override fun saveLastSearchResult(listRepository: ArrayList<Repository>) {
        listRepo = listRepository
    }

    override fun deleteAllSaved() {
    }

    override fun showError(t: Throwable) {
        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
    }

    override fun signOut() {
        if (id != "") {
            val context: Context = requireContext()
            mainPresenter.signOut(context)
        } else mainPresenter.signOut()
    }

}