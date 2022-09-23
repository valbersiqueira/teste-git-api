package com.br.valber.testegitapi.domain.input

import com.br.valber.testegitapi.domain.entity.ItemJava

interface FetchRepoOut {

    suspend fun fetchJavaRepo(
        page: Int,
        itemsPerPage: Int
    ): List<ItemJava>

}