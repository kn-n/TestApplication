package com.example.testapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.testapplication.SQLite.SQLiteHelper
import com.example.testapplication.databinding.FragmentRepositoryBinding
import com.example.testapplication.intefaces.RepositoryView
import com.example.testapplication.makeDate
import com.example.testapplication.models.Repository
import com.example.testapplication.presenters.RepositoryPresenter
import com.example.testapplication.toothpick.DI
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class RepositoryFragment(
    private var id: String,
    private var repository: Repository,
    private val tabIndex: Int
) : MvpAppCompatFragment(), RepositoryView {

    @InjectPresenter
    lateinit var repositoryPresenter: RepositoryPresenter

    @ProvidePresenter
    fun provideRepositoryPresenter() =
        Toothpick.openScope(DI.APP_SCOPE).getInstance(RepositoryPresenter::class.java)

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryBinding.inflate(inflater, container, false)

        val helper = SQLiteHelper(requireContext())

        showInformation(helper)
        binding.back.setOnClickListener { back() }

        return binding.root
    }

    override fun showInformation(helper: SQLiteHelper) {
        binding.name.text = repository.full_name
        binding.gitUsername.text = repository.owner.login
        Picasso.get().load(repository.owner.avatar_url).into(binding.userImg)
        binding.dateOfCreation.text = makeDate(repository.created_at)
        binding.numberOfStars.text = repository.watchers
        binding.numberOfForks.text = repository.forks
        binding.description.text = repository.description
        if (id != "") {
            binding.btnSaveDelete.visibility = View.VISIBLE
            val savedRepo = helper.checkRepository(id, repository.full_name)
            if (savedRepo) {
                binding.btnSaveDelete.isSelected = true
                binding.btnSaveDelete.setOnClickListener { deleteFromSaved() }
            } else {
                binding.btnSaveDelete.isSelected = false
                binding.btnSaveDelete.setOnClickListener { addToSaved() }
            }
        } else binding.btnSaveDelete.visibility = View.GONE

    }

    override fun addToSaved() {
        binding.btnSaveDelete.isSelected = true
        repositoryPresenter.addRepositoryToSaved(id, repository, requireContext())
    }

    override fun deleteFromSaved() {
        binding.btnSaveDelete.isSelected = false
        repositoryPresenter.deleteRepositoryFromSaved(id, repository.full_name, requireContext())
    }

    override fun back() {
        repositoryPresenter.back(id, tabIndex)
    }
}