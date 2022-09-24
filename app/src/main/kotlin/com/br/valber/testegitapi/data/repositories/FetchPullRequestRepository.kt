package com.br.valber.testegitapi.data.repositories

import com.br.valber.testegitapi.data.model.PullRequestModel
import com.br.valber.testegitapi.data.service.JavaRepoService
import com.br.valber.testegitapi.domain.entity.PullRequest
import com.br.valber.testegitapi.domain.input.FetchPullRequestOut
import com.br.valber.testegitapi.framework.RemoteBuilder
import com.br.valber.testegitapi.framework.RequestApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class FetchPullRequestRepository(
    private val remoteBuilder: RemoteBuilder,
    private val requestApi: RequestApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FetchPullRequestOut {

    private val service by lazy { remoteBuilder.build().create(JavaRepoService::class.java) }

    override suspend fun fetchPulls(
        page: Int,
        itemsPerPage: Int,
        owner: String,
        repo: String
    ) = requestApi.safeRequestApi(dispatcher) {
        service.fetchPullsRequest(
            page = page,
            itemsPerPage = itemsPerPage,
            owner = owner,
            repo = repo
        ).toPullRequest()
    }

    private fun List<PullRequestModel>.toPullRequest() = map {
        PullRequest(
            name = it.head.repo?.name ?: "",
            description = it.head.repo?.description,
            fullName = it.head.repo?.fullName ?: "",
            avatar = it.head.user.avatarUrl
        )
    }

}