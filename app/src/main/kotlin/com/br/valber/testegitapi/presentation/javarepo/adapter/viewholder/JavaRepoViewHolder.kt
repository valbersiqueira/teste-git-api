@file:OptIn(ExperimentalCoroutinesApi::class)

package com.br.valber.testegitapi.presentation.javarepo.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.br.valber.testegitapi.R
import com.br.valber.testegitapi.domain.entity.JavaRepo
import com.br.valber.testegitapi.presentation.pullrequest.view.PullRequestActivity
import com.br.valber.testegitapi.presentation.view.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.coroutines.ExperimentalCoroutinesApi


class JavaRepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {

            val context = view.context
            context.startActivity(
                PullRequestActivity.startActivity(
                    context,
                    javaRepo?.login,
                    javaRepo?.name
                )
            )
        }
    }

    private val repoName: TextView = view.findViewById(R.id.textViewRepoName)
    private val repoDescription: TextView = view.findViewById(R.id.textViewRepoDescription)
    private val countBranch: TextView = view.findViewById(R.id.textViewCountBranch)
    private val countStar: TextView = view.findViewById(R.id.textViewCountStar)
    private val userName: TextView = view.findViewById(R.id.textViewUserName)
    private val imageViewUser: ImageView = view.findViewById(R.id.imageViewUser)

    private var javaRepo: JavaRepo? = null

    fun bind(itemJava: JavaRepo?) {
        itemJava?.let { showItems(it) }
    }

    private fun showItems(javaRepo: JavaRepo) {
        this.javaRepo = javaRepo
        repoName.text = javaRepo.fullName
        repoDescription.apply {
            isVisible = javaRepo.description != null
            text = javaRepo.description
        }
        countStar.text = javaRepo.startCount
        countBranch.text = javaRepo.forksCount
        userName.text = javaRepo.name

        Picasso.get()
            .load(javaRepo.avatar)
            .placeholder(R.drawable.ic_baseline_supervised_user_circle_24)
            .transform(CircleTransform())
            .into(imageViewUser)
    }

    companion object {

        fun create(parent: ViewGroup): JavaRepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_java_repo, parent, false)
            return JavaRepoViewHolder(view)
        }

    }

}