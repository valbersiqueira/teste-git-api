package com.br.valber.testegitapi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.br.valber.testegitapi.domain.entity.ItemJava
import com.br.valber.testegitapi.domain.input.FetchRepoIn
import com.br.valber.testegitapi.presentation.state.JavaRepoState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
internal class JavaRepoViewModel(
    private val fetchRepoIn: FetchRepoIn
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<ItemJava>>
    val accept: (JavaRepoState) -> Unit

    init {
        val actionStateFlow = MutableSharedFlow<JavaRepoState>()

        val queryScroll = actionStateFlow
            .filterIsInstance<JavaRepoState.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(JavaRepoState.Scroll) }

        @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
        pagingDataFlow = queryScroll
            .flatMapLatest { fetchRepoIn.fetchJavaRepo() }
            .cachedIn(viewModelScope)

        accept = { action ->
            viewModelScope.launch {
                actionStateFlow.emit(action)
            }
        }

    }

}