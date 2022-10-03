package com.br.valber.testegitapi.domain.output

import com.br.valber.testegitapi.domain.entity.PullRequest

interface FetchPullRequestOutput {

    suspend fun fetchPulls(
        page: Int,
        itemsPerPage: Int,
        owner: String,
        repo: String
    ): List<PullRequest>

}