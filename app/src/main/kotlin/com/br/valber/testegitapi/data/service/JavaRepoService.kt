package com.br.valber.testegitapi.data.service

import com.br.valber.testegitapi.data.model.JavaRepoModel
import retrofit2.http.GET
import retrofit2.http.Query

interface JavaRepoService {

    @GET("repositories?q=language:Java&sort=stars")
    suspend fun fetchJavaRepo(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): JavaRepoModel

}