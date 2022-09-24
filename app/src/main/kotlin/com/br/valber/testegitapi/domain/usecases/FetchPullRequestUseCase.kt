package com.br.valber.testegitapi.domain.usecases

import com.br.valber.testegitapi.domain.input.FetchPullRequestIn
import com.br.valber.testegitapi.domain.input.FetchPullRequestOut

internal class FetchPullRequestUseCase(
    private val fetchPullRequestOut: FetchPullRequestOut
) : FetchPullRequestIn {

    override suspend fun fetchPulls(create: String, repo: String) =
        fetchPullRequestOut.fetchPulls(create, repo)

}