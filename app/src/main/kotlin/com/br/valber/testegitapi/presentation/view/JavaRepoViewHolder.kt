package com.br.valber.testegitapi.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.br.valber.testegitapi.R
import com.br.valber.testegitapi.domain.entity.ItemJava

class JavaRepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val repoName: TextView = view.findViewById(R.id.textViewRepoName)
    private val repoDescription: TextView = view.findViewById(R.id.textViewRepoDescription)

    fun bind(itemJava: ItemJava?) {
        showItems(itemJava)
    }

    private fun showItems(itemJava: ItemJava?) {
        repoName.text = itemJava?.fullName
        repoDescription.apply {
            isVisible = itemJava?.description != null
            text = itemJava?.description
        }
    }

    companion object {

        fun create(parent: ViewGroup): JavaRepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_java_repo, parent, false)
            return JavaRepoViewHolder(view)
        }

    }
}