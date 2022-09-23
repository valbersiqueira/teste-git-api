package com.br.valber.testegitapi.presentation.javarepo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.br.valber.testegitapi.domain.entity.ItemJava
import com.br.valber.testegitapi.domain.input.FetchRepoIn
import retrofit2.HttpException
import java.io.IOException

class JavaRepoPagingSource(
    private val fetchRepoOut: FetchRepoIn
) : PagingSource<Int, ItemJava>() {

    companion object {
        private const val STARTING_PAGE = 1
        const val NETWORK_PAGE_SIZE = 30
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
            val items = fetchRepoOut.fetchJavaRepo(position, params.loadSize)

            val nextKey = if (items.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }

            LoadResult.Page(
                data = items,
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = nextKey
            )
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }
    }

}