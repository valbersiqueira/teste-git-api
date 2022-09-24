package com.br.valber.testegitapi.data.adapterout

import com.br.valber.testegitapi.core.RemoteBuilder
import com.br.valber.testegitapi.core.RequestApi
import com.br.valber.testegitapi.data.model.ItemJavaModel
import com.br.valber.testegitapi.data.repositories.JavaRepoService
import com.br.valber.testegitapi.domain.entity.ItemJava
import com.br.valber.testegitapi.domain.input.FetchRepoOut
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class FetchJavaRepoAdapterOut(
    private val remoteBuilder: RemoteBuilder,
    private val requestApi: RequestApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FetchRepoOut {

    private val service by lazy { remoteBuilder.build().create(JavaRepoService::class.java) }

    override suspend fun fetchJavaRepo(
        page: Int,
        itemsPerPage: Int
    ): List<ItemJava> {
        return requestApi.safeRequestApi(dispatcher) {
            val response = service.fetchJavaRepo(page, itemsPerPage)
            response.items.toItemJava()
        }
    }

    private fun List<ItemJavaModel>.toItemJava() = map {
        ItemJava(
            name = it.name,
            description = it.description,
            fullName = it.fullName,
            avatar = it.owner.avatarUrl,
            forksCount = it.forks.toString(),
            startCount = it.stargazersCount.toString()
        )
    }

}
