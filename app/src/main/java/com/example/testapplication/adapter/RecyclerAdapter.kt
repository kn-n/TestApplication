package com.example.testapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.SQLite.SQLiteHelper
import com.example.testapplication.Screens
import com.example.testapplication.fragments.MainFragment
import com.example.testapplication.models.Repository
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class RecyclerAdapter @Inject constructor(
    private val router: Router,
    private val repositories: ArrayList<Repository>,
    private val id: String,
    context: Context,
    private val tabIndex: String
) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val helper = SQLiteHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repositories[position]
        val savedRepo = helper.checkRepository(id, repository.full_name)
        if (savedRepo) {
            holder.inSaved.visibility = View.VISIBLE
            holder.delete.visibility = View.VISIBLE
            holder.delete.setOnClickListener {
                helper.deleteUsersSavedRepository(id, repository.full_name)
                holder.inSaved.visibility = View.GONE
                holder.delete.visibility = View.GONE
                if (tabIndex.toInt() == 1) holder.repository.visibility = View.GONE
            }
        } else {
            holder.inSaved.visibility = View.GONE
            holder.delete.visibility = View.GONE
        }
        holder.nameOfRepository.text = repository.full_name
        holder.descriptionOfRepository.text = repository.description

        holder.repository.setOnClickListener {
            router.navigateTo(
                Screens.Repository(
                    id,
                    repository,
                    tabIndex.toInt()
                )
            )
        }
    }

    override fun getItemCount() = repositories.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameOfRepository: TextView = itemView.findViewById(R.id.name)
        val descriptionOfRepository: TextView = itemView.findViewById(R.id.description)
        val inSaved: ImageView = itemView.findViewById(R.id.in_saved)
        val delete: ImageView = itemView.findViewById(R.id.delete)
        val repository: LinearLayout = itemView.findViewById(R.id.item)
    }
}