package com.br.valber.testegitapi.data.repositories

import com.br.valber.testegitapi.data.model.ItemJavaModel
import com.br.valber.testegitapi.data.service.JavaRepoService
import com.br.valber.testegitapi.domain.entity.JavaRepo
import com.br.valber.testegitapi.domain.output.FetchRepoOutput
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class FetchJavaRepoRepository(
    private val service: JavaRepoService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FetchRepoOutput {

    override suspend fun fetchJavaRepo(
        page: Int,
        itemsPerPage: Int
    ): List<JavaRepo> {
        return withContext(dispatcher) {
            try {
                val response = service.fetchJavaRepo(page, itemsPerPage)
                response.items.toItemJava()
            } catch (ex: Exception) {
                throw Exception(ex)
            }
        }
    }

   private fun List<ItemJavaModel>.toItemJava() = map {
        JavaRepo(
            name = it.name ?: "",
            description = it.description,
            fullName = it.fullName ?: "",
            avatar = it.owner?.avatarUrl ?: "",
            forksCount = it.forks.toString(),
            startCount = it.stargazersCount.toString(),
            pullsUrl = it.pullsUrl ?: "",
            login = it.owner?.login ?: ""
        )
    }

}
