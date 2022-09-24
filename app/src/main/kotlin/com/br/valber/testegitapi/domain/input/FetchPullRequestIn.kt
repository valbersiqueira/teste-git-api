package com.br.valber.testegitapi.domain.input

import com.br.valber.testegitapi.domain.entity.JavaRepo

interface FetchPullRequestIn {

    suspend fun fetchPulls(create: String, repo: String): List<JavaRepo>

}