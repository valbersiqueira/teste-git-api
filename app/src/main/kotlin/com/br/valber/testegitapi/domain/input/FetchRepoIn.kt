package com.br.valber.testegitapi.domain.input

import androidx.paging.PagingData
import com.br.valber.testegitapi.domain.entity.ItemJava
import kotlinx.coroutines.flow.Flow

interface FetchRepoIn {

    suspend fun fetchJavaRepo(): Flow<PagingData<ItemJava>>

}