package com.br.valber.testegitapi.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.br.valber.testegitapi.data.model.ItemJavaModel
import com.br.valber.testegitapi.data.repositories.JavaRepoService
import com.br.valber.testegitapi.domain.entity.ItemJava
import retrofit2.HttpException
import java.io.IOException

const val NETWORK_PAGE_SIZE = 30

class JavaRepoPagingSource(
    private val service: JavaRepoService
) : PagingSource<Int, ItemJava>() {

    companion object {
        private const val STARTING_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ItemJava>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemJava> {
        val position = params.key ?: STARTING_PAGE

        return try {
            val response = service.fetchJavaRepo(position, params.loadSize)
            val items = response.items
            val nextKey = if (items.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }

            LoadResult.Page(
                data = items.toItemJava(),
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey= nextKey
            )
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
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