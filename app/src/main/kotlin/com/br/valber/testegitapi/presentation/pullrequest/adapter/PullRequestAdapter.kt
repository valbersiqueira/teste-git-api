package com.br.valber.testegitapi.presentation.pullrequest.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.br.valber.testegitapi.domain.entity.PullRequest
import com.br.valber.testegitapi.presentation.pullrequest.adapter.viewholder.PullRequestViewHolder

internal class PullRequestAdapter : PagingDataAdapter<PullRequest, RecyclerView.ViewHolder>(PULL_REQUEST_COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pullRequest = getItem(position)
        (holder as PullRequestViewHolder).bind(pullRequest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PullRequestViewHolder.create(parent)
    }

    companion object {

        private val PULL_REQUEST_COMPARATOR = object : DiffUtil.ItemCallback<PullRequest>() {
            override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
                return oldItem.fullName == newItem.fullName
            }

            override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean =
                oldItem == newItem

        }
    }

}