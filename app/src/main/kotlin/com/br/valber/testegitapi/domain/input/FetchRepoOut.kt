package com.br.valber.testegitapi.domain.input

import com.br.valber.testegitapi.domain.entity.JavaRepo

interface FetchRepoOut {

    suspend fun fetchJavaRepo(page: Int): JavaRepo
}