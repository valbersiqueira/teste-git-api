package com.br.valber.testegitapi.presentation.javarepo.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.br.valber.testegitapi.R
import com.br.valber.testegitapi.databinding.ViewHolderLoadStateFooterBinding

class LoadStateViewHolder(
    private val biding: ViewHolderLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(biding.root) {

    init {
        biding.buttonRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            biding.textViewerrorMsg.text = loadState.error.localizedMessage
        }
        biding.progressBar.isVisible = loadState is LoadState.Loading
        biding.buttonRetry.isVisible = loadState is LoadState.Error
        biding.textViewerrorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {

        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_load_state_footer, parent, false)
            val biding =  ViewHolderLoadStateFooterBinding.bind(view)
            return LoadStateViewHolder(biding, retry)
        }

    }
}