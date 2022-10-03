package com.br.valber.testegitapi.domain.output

import com.br.valber.testegitapi.domain.entity.JavaRepo

interface FetchRepoOutput {

    suspend fun fetchJavaRepo(
        page: Int,
        itemsPerPage: Int
    ): List<JavaRepo>

}