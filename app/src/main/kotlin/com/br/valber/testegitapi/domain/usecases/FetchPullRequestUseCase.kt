package com.br.valber.testegitapi.domain.usecases

import com.br.valber.testegitapi.domain.input.FetchPullRequestInput
import com.br.valber.testegitapi.domain.output.FetchPullRequestOutput

internal class FetchPullRequestUseCase(
    private val fetchPullRequestOutput: FetchPullRequestOutput
) : FetchPullRequestInput {

    override suspend fun fetchPulls(
        page: Int,
        itemsPerPage: Int,
        owner: String,
        repo: String
    ) =  fetchPullRequestOutput.fetchPulls(page, itemsPerPage, owner, repo)

}