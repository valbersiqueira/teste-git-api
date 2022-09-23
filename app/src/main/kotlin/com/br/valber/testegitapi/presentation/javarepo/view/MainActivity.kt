package com.br.valber.testegitapi.presentation.javarepo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.br.valber.testegitapi.core.extensions.viewBinding
import com.br.valber.testegitapi.databinding.ActivityMainBinding
import com.br.valber.testegitapi.di.injectProjectMainModule
import com.br.valber.testegitapi.presentation.javarepo.state.JavaRepoState
import com.br.valber.testegitapi.presentation.javarepo.adapter.JavaRepoAdapter
import com.br.valber.testegitapi.presentation.javarepo.adapter.LoadStateAdapter
import com.br.valber.testegitapi.presentation.javarepo.viewmodel.JavaRepoViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    init {
        injectProjectMainModule()
    }

    private val viewModel: JavaRepoViewModel by viewModel()

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.recyclerRepo.addItemDecoration(decoration)
        binding.bindState(viewModel.accept)
    }

    private fun ActivityMainBinding.bindState(
        uiAction: (JavaRepoState) -> Unit
    ) {
        val adapter = JavaRepoAdapter()
        recyclerRepo.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { adapter.retry() },
            footer = LoadStateAdapter { adapter.retry() }
        )

        bindList(adapter, uiAction)
    }

    private fun ActivityMainBinding.bindList(
        adapter: JavaRepoAdapter,
        onScrollChanged: (JavaRepoState.Scroll) -> Unit
    ) {
        buttonRetry.setOnClickListener { adapter.retry() }
        recyclerRepo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged.invoke(JavaRepoState.Scroll)
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
            viewModel.pagingDataFlow.collectLatest(adapter::submitData)
        }
    }
}