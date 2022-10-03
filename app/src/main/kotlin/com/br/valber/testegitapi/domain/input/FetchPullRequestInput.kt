package com.br.valber.testegitapi.domain.input

import com.br.valber.testegitapi.domain.entity.PullRequest

interface FetchPullRequestInput {

    suspend fun fetchPulls(
        page: Int,
        itemsPerPage: Int,
        owner: String,
        repo: String
    ): List<PullRequest>

}