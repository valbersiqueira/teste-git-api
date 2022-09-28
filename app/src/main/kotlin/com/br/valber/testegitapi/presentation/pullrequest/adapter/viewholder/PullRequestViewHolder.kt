package com.br.valber.testegitapi.presentation.pullrequest.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.br.valber.testegitapi.R
import com.br.valber.testegitapi.domain.entity.PullRequest
import com.br.valber.testegitapi.presentation.javarepo.adapter.viewholder.JavaRepoViewHolder
import com.br.valber.testegitapi.presentation.view.CircleTransform
import com.squareup.picasso.Picasso

internal class PullRequestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val repoName: TextView = view.findViewById(R.id.textViewRepoName)
    private val repoDescription: TextView = view.findViewById(R.id.textViewRepoDescription)
    private val userName: TextView = view.findViewById(R.id.textViewUserName)
    private val imageViewUser: ImageView = view.findViewById(R.id.imageViewUser)

    fun bind(pullRequest: PullRequest?) {
        showPullRequest(pullRequest)
    }

    private fun showPullRequest(pullRequest: PullRequest?) {
        repoName.text = pullRequest?.fullName
        repoDescription.apply {
            isVisible = pullRequest?.description != null
            text = pullRequest?.description
        }
        userName.text = pullRequest?.name

        Picasso.get()
            .load(pullRequest?.avatar)
            .placeholder(R.drawable.ic_baseline_supervised_user_circle_24)
            .transform(CircleTransform())
            .into(imageViewUser)
    }

    companion object {

        fun create(parent: ViewGroup): PullRequestViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_pull_request, parent, false)
            return PullRequestViewHolder(view)
        }

    }
}