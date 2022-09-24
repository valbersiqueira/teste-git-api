package com.br.valber.testegitapi.presentation.javarepo.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.br.valber.testegitapi.domain.entity.JavaRepo
import com.br.valber.testegitapi.presentation.javarepo.adapter.viewholder.JavaRepoViewHolder

class JavaRepoAdapter : PagingDataAdapter<JavaRepo, RecyclerView.ViewHolder>(ITEM_JAVA_COMPARATOR){

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemJava = getItem(position)
        (holder as JavaRepoViewHolder).bind(itemJava)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JavaRepoViewHolder.create(parent)
    }

    companion object {
        private val ITEM_JAVA_COMPARATOR = object : DiffUtil.ItemCallback<JavaRepo>() {
            override fun areItemsTheSame(oldItem: JavaRepo, newItem: JavaRepo): Boolean {
                return oldItem.fullName == newItem.fullName
            }

            override fun areContentsTheSame(oldItem: JavaRepo, newItem: JavaRepo): Boolean =
                oldItem == newItem

        }
    }
}