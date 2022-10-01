package com.br.valber.testegitapi.presentation.pullrequest.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.br.valber.testegitapi.databinding.ActivityPullsRequestBinding
import com.br.valber.testegitapi.framework.extensions.viewBinding
import com.br.valber.testegitapi.presentation.pullrequest.adapter.PullRequestAdapter
import com.br.valber.testegitapi.presentation.pullrequest.viewmodel.PullRequestViewModel
import com.br.valber.testegitapi.presentation.view.apdater.LoadStateAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@ExperimentalCoroutinesApi
internal class PullRequestActivity : AppCompatActivity() {

    private val binding: ActivityPullsRequestBinding by viewBinding(ActivityPullsRequestBinding::inflate)

    private val viewModel: PullRequestViewModel by viewModel(
        parameters = {
            parametersOf(getOwner(intent), getNameRepo(intent))
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.recyclerPullRequest.addItemDecoration(decoration)
        binding.bindState()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.title = getNameRepo(intent)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun ActivityPullsRequestBinding.bindState() {
        val adapter = PullRequestAdapter()
        recyclerPullRequest.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { adapter.retry() },
            footer = LoadStateAdapter { adapter.retry() }
        )

        bindList(adapter)
    }

    private fun ActivityPullsRequestBinding.bindList(
        adapter: PullRequestAdapter
    ) {
        buttonRetry.setOnClickListener { adapter.retry() }
        recyclerPullRequest.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                viewModel.isScrollChanged(dy)
            }
        })

        val notLoading = adapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.source.refresh is LoadState.NotLoading }

        lifecycleScope.launch {
            notLoading.collect { shouldScroll ->
                if (shouldScroll) recyclerPullRequest.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect {
                progressBar.isVisible = it.refresh is LoadState.Loading && adapter.itemCount == 0
                val isListEmpty = it.refresh is LoadState.NotLoading && adapter.itemCount == 0
                emptyList.isVisible = isListEmpty
                recyclerPullRequest.isVisible = !isListEmpty
                buttonRetry.isVisible = it.source.refresh is LoadState.Error
            }
        }

        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest(adapter::submitData)
        }
    }

    companion object {

        private const val KEY_OWNER = "owner"
        private const val KEY_NAME_REPO = "nameRepo"

        fun startActivity(context: Context, owner: String?, nameRepo: String?) =
            Intent(context, PullRequestActivity::class.java).apply {
                putExtra(KEY_OWNER, owner)
                putExtra(KEY_NAME_REPO, nameRepo)
            }

        private fun getOwner(intent: Intent) = intent.getStringExtra(KEY_OWNER)

        private fun getNameRepo(intent: Intent) = intent.getStringExtra(KEY_NAME_REPO)
    }
}