package com.br.valber.testegitapi.presentation.javarepo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.br.valber.testegitapi.domain.entity.JavaRepo
import com.br.valber.testegitapi.domain.input.FetchRepoIn
import com.br.valber.testegitapi.framework.CustomPagingSource
import com.br.valber.testegitapi.presentation.javarepo.paging.JavaRepoPagingSource.Companion.NETWORK_PAGE_SIZE
import com.br.valber.testegitapi.presentation.javarepo.state.UIJavaRepoState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
internal class JavaRepoViewModel(
    private val fetchRepoIn: FetchRepoIn
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<JavaRepo>>
    val accept: (UIJavaRepoState) -> Unit

    init {
        val actionStateFlow = MutableSharedFlow<UIJavaRepoState>()

        val queryScroll = actionStateFlow
            .filterIsInstance<UIJavaRepoState.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(UIJavaRepoState.Scroll) }

        @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
        pagingDataFlow = queryScroll
            .flatMapLatest {
                Pager(
                    config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
                    pagingSourceFactory = {
                        CustomPagingSource { position, perPage ->
                            fetchRepoIn.fetchJavaRepo(position, perPage)
                        }
                    }
                ).flow
            }
            .cachedIn(viewModelScope)

        accept = { action ->
            viewModelScope.launch {
                actionStateFlow.emit(action)
            }
        }

    }

}
