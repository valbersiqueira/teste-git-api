package com.br.valber.testegitapi.presentation.pullrequest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.br.valber.testegitapi.domain.entity.PullRequest
import com.br.valber.testegitapi.domain.input.FetchPullRequestIn
import com.br.valber.testegitapi.framework.CustomPagingSource
import com.br.valber.testegitapi.presentation.pullrequest.state.UIPullRequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
internal class PullRequestViewModel (
    private val owner: String?,
    private val nameRepo: String?,
    private val fetchPullRequestIn: FetchPullRequestIn
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<PullRequest>>
    val accept: (UIPullRequestState) -> Unit

    init {
        val actionStateFlow = MutableSharedFlow<UIPullRequestState>()

        val queryScroll = actionStateFlow
            .filterIsInstance<UIPullRequestState>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(UIPullRequestState.Scroll) }

        @OptIn(ExperimentalCoroutinesApi::class)
        pagingDataFlow = queryScroll.flatMapLatest {
            Pager(
                config = PagingConfig(pageSize = CustomPagingSource.NETWORK_PAGE_SIZE),
                pagingSourceFactory = {
                    CustomPagingSource { page, perPage ->
                        fetchPullRequestIn.fetchPulls(page, perPage, owner ?: "", nameRepo ?: "")
                    }
                }
            ).flow
        }

        accept = { action ->
            viewModelScope.launch {
                actionStateFlow.emit(action)
            }
        }
    }

    fun isScrollChanged(dy: Int) {
        if (dy != 0) accept.invoke(UIPullRequestState.Scroll)
    }

}