package com.br.valber.testegitapi.data.adapter

import com.br.valber.testegitapi.core.RemoteBuilder
import com.br.valber.testegitapi.core.RequestApi
import com.br.valber.testegitapi.data.model.ItemJavaModel
import com.br.valber.testegitapi.data.repositories.JavaRepoRepository
import com.br.valber.testegitapi.domain.entity.ItemJava
import com.br.valber.testegitapi.domain.entity.JavaRepo
import com.br.valber.testegitapi.domain.input.FetchRepoOut
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class FetchJavaRepoAdapterOut(
    private val dispatch: CoroutineDispatcher = Dispatchers.IO,
    private val requestApi: RequestApi,
    private val remoteBuilder: RemoteBuilder
) : FetchRepoOut {

    private val service by lazy { remoteBuilder.build().create(JavaRepoRepository::class.java) }

    override suspend fun fetchJavaRepo(page: Int): JavaRepo {
        return requestApi.safeRequestApi(dispatch) {
            val javaRepoModel = service.fetchJavaRepo(page)
            val items = javaRepoModel.items.toItemJava()
            JavaRepo(totalPage = javaRepoModel.totalCount, items = items)
        }
    }

    private fun ArrayList<ItemJavaModel>.toItemJava() = map {
        ItemJava(
            name = it.name,
            description = it.description,
            fullName = it.fullName,
            avatar = it.owner.avatarUrl,
            forksCount = it.forks,
            startCount = it.stargazersCount
        )
    }

}