package com.br.valber.testegitapi.data.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.br.valber.testegitapi.core.RemoteBuilder
import com.br.valber.testegitapi.data.JavaRepoPagingSource
import com.br.valber.testegitapi.data.NETWORK_PAGE_SIZE
import com.br.valber.testegitapi.data.repositories.JavaRepoService
import com.br.valber.testegitapi.domain.entity.ItemJava
import com.br.valber.testegitapi.domain.input.FetchRepoOut
import kotlinx.coroutines.flow.Flow

internal class FetchJavaRepoAdapterOut(
    private val remoteBuilder: RemoteBuilder
) : FetchRepoOut {

    private val service by lazy { remoteBuilder.build().create(JavaRepoService::class.java) }

    override fun fetchJavaRepo(): Flow<PagingData<ItemJava>> {
            return Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
                pagingSourceFactory = { JavaRepoPagingSource(service)}
            ).flow
    }

}