package com.br.valber.testegitapi.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.valber.testegitapi.domain.input.FetchRepoIn
import com.br.valber.testegitapi.presentation.state.JavaRepoState
import kotlinx.coroutines.launch

internal class JavaRepoViewModel(
    private val fetchRepoIn: FetchRepoIn
) : ViewModel() {

    private val _state by lazy { MutableLiveData<JavaRepoState>() }
    val state: LiveData<JavaRepoState>
        get() = _state


    fun fetchJavaRepo() {
        viewModelScope.launch {
            try {
                val javaRepo = fetchRepoIn.fetchJavaRepo(1)
                _state.postValue(JavaRepoState.ShowJavaRepo(javaRepo))
            } catch (ex: Throwable) {
                _state.postValue(JavaRepoState.ShowError(ex.message ?: ""))
            }
        }
    }
}