package com.br.valber.testegitapi.domain.usecases

import com.br.valber.testegitapi.domain.input.FetchRepoInput
import com.br.valber.testegitapi.domain.output.FetchRepoOutput

internal class FetchRepoUseCase(
    private val fetchRepoOutput: FetchRepoOutput
) : FetchRepoInput {

    override suspend fun fetchJavaRepo(
        page: Int,
        itemsPerPage: Int
    ) = fetchRepoOutput.fetchJavaRepo(page, itemsPerPage)

}