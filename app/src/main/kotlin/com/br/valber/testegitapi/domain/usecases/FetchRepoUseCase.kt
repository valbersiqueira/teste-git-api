package com.br.valber.testegitapi.domain.usecases

import com.br.valber.testegitapi.domain.input.FetchRepoIn
import com.br.valber.testegitapi.domain.input.FetchRepoOut

internal class FetchRepoUseCase(
    private val fetchRepoOut: FetchRepoOut
) : FetchRepoIn {

    override suspend fun fetchJavaRepo(page: Int) =
        fetchRepoOut.fetchJavaRepo(page)

}