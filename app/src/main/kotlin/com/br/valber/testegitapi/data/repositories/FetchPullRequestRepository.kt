package com.br.valber.testegitapi.data.repositories

import com.br.valber.testegitapi.data.model.PullRequestModel
import com.br.valber.testegitapi.data.service.JavaRepoService
import com.br.valber.testegitapi.domain.entity.PullRequest
import com.br.valber.testegitapi.domain.output.FetchPullRequestOutput
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class FetchPullRequestRepository(
    private val service: JavaRepoService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FetchPullRequestOutput {

    override suspend fun fetchPulls(
        page: Int,
        itemsPerPage: Int,
        owner: String,
        repo: String
    ): List<PullRequest> {
        return withContext(dispatcher) {
            service.fetchPullsRequest(
                page = page,
                itemsPerPage = itemsPerPage,
                owner = owner,
                repo = repo
            ).toPullRequest()
        }
    }

    private fun List<PullRequestModel>.toPullRequest() = map {
        PullRequest(
            name = it.head?.repo?.name ?: "",
            description = it.head?.repo?.description,
            fullName = it.head?.repo?.fullName ?: "",
            avatar = it.head?.user?.avatarUrl ?: ""
        )
    }

}