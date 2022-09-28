package com.br.valber.testegitapi.domain.usecases

import com.br.valber.testegitapi.domain.input.FetchPullRequestIn
import com.br.valber.testegitapi.domain.input.FetchPullRequestOut

internal class FetchPullRequestUseCase(
    private val fetchPullRequestOut: FetchPullRequestOut
) : FetchPullRequestIn {

    override suspend fun fetchPulls(
        page: Int,
        itemsPerPage: Int,
        owner: String,
        repo: String
    ) =  fetchPullRequestOut.fetchPulls(page, itemsPerPage, owner, repo)

}