package com.br.valber.testegitapi.presentation.state

import com.br.valber.testegitapi.domain.entity.JavaRepo

sealed class JavaRepoState {
    data class ShowJavaRepo(val javaRepo: JavaRepo) : JavaRepoState()
    data class ShowError(val message: String) : JavaRepoState()

    object Scroll : JavaRepoState()
}
