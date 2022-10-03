package com.br.valber.testegitapi.domain.input

import com.br.valber.testegitapi.domain.entity.JavaRepo

interface FetchRepoInput {

    suspend fun fetchJavaRepo(
        page: Int,
        itemsPerPage: Int
    ): List<JavaRepo>

}