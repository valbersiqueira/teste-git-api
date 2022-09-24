package com.br.valber.testegitapi.presentation.javarepo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.br.valber.testegitapi.framework.extensions.viewBinding
import com.br.valber.testegitapi.databinding.ActivityJavaRepoBinding
import com.br.valber.testegitapi.di.injectProjectMainModule
import com.br.valber.testegitapi.presentation.javarepo.adapter.JavaRepoAdapter
import com.br.valber.testegitapi.presentation.javarepo.adapter.LoadStateAdapter
import com.br.valber.testegitapi.presentation.javarepo.state.UIJavaRepoState
import com.br.valber.testegitapi.presentation.javarepo.viewmodel.JavaRepoViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class JavaRepoActivity : AppCompatActivity() {

    init {
        injectProjectMainModule()
    }

    private val viewModel: JavaRepoViewModel by viewModel()

    private val binding: ActivityJavaRepoBinding by viewBinding(ActivityJavaRepoBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.recyclerRepo.addItemDecoration(decoration)
        binding.bindState(viewModel.accept)
    }

    private fun ActivityJavaRepoBinding.bindState(
        uiAction: (UIJavaRepoState) -> Unit
    ) {
        val adapter = JavaRepoAdapter()
        recyclerRepo.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { adapter.retry() },
            footer = LoadStateAdapter { adapter.retry() }
        )

        bindList(adapter, uiAction)
    }

    private fun ActivityJavaRepoBinding.bindList(
        adapter: JavaRepoAdapter,
        onScrollChanged: (UIJavaRepoState.Scroll) -> Unit
    ) {
        buttonRetry.setOnClickListener { adapter.retry() }
        recyclerRepo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged.invoke(UIJavaRepoState.Scroll)
            }
        })

        val notLoading = adapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.source.refresh is LoadState.NotLoading }

        lifecycleScope.launch {
            notLoading.collect { shouldScroll ->
                if (shouldScroll) recyclerRepo.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect{
                progressBar.isVisible = it.refresh is LoadState.Loading && adapter.itemCount == 0
                val isListEmpty = it.refresh is LoadState.NotLoading && adapter.itemCount == 0
                emptyList.isVisible = isListEmpty
                recyclerRepo.isVisible = !isListEmpty
                buttonRetry.isVisible = it.source.refresh is LoadState.Error
            }
        }

        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest(adapter::submitData)
        }
    }
}