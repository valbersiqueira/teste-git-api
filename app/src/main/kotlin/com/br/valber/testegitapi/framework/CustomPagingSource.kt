package com.br.valber.testegitapi.framework

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

internal class CustomPagingSource<Value : Any>(
    private val action: suspend (position: Int, perPage: Int) -> List<Value>
) : PagingSource<Int, Value>() {

    companion object {
        const val STARTING_PAGE = 1
        const val NETWORK_PAGE_SIZE = 30
    }

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        val position = params.key ?: STARTING_PAGE

        return try {
            val items = action.invoke(position, params.loadSize)

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