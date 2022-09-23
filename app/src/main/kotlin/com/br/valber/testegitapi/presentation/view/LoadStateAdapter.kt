package com.br.valber.testegitapi.presentation.view

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

internal class LoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>(){
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder.create(parent, retry)
    }
}