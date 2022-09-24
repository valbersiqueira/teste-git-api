package com.br.valber.testegitapi.data.repositories

import com.br.valber.testegitapi.data.model.PullRequestModel
import com.br.valber.testegitapi.data.service.JavaRepoService
import com.br.valber.testegitapi.domain.entity.JavaRepo
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

    override suspend fun fetchPulls(create: String, repo: String): List<JavaRepo> {
        return requestApi.safeRequestApi(dispatcher) {
            val response = service.fetchPullsRequest(create, repo)
            response.toItemJava()
        }
    }

    private fun List<PullRequestModel>.toItemJava() = map {
        JavaRepo(
            name = it.head.repo.name,
            description = it.head.repo.description,
            fullName = it.head.repo.fullName,
            avatar = it.head.user.avatarUrl,
            forksCount = it.head.repo.forks.toString(),
            startCount = it.head.repo.stargazersCount.toString(),
            pullsUrl = it.head.repo.pullsUrl ?: ""
        )
    }

}