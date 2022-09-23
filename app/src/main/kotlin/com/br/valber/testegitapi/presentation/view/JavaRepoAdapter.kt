package com.br.valber.testegitapi.presentation.view

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.br.valber.testegitapi.domain.entity.ItemJava

class JavaRepoAdapter : PagingDataAdapter<ItemJava, RecyclerView.ViewHolder>(ITEM_JAVA_COMPARATOR){

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemJava = getItem(position)
        (holder as JavaRepoViewHolder).bind(itemJava)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JavaRepoViewHolder.create(parent)
    }

    companion object {
        private val ITEM_JAVA_COMPARATOR = object : DiffUtil.ItemCallback<ItemJava>() {
            override fun areItemsTheSame(oldItem: ItemJava, newItem: ItemJava): Boolean {
                return oldItem.fullName == newItem.fullName
            }

            override fun areContentsTheSame(oldItem: ItemJava, newItem: ItemJava): Boolean =
                oldItem == newItem

        }
    }
}