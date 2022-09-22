package com.br.valber.testegitapi.domain.input

import com.br.valber.testegitapi.domain.entity.JavaRepo

interface FetchRepoIn {

    suspend fun fetchJavaRepo(page: Int): ArrayList<JavaRepo>

}