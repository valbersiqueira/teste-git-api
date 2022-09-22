package com.br.valber.testegitapi.data.repositories

import com.br.valber.testegitapi.data.model.JavaRepoModel
import retrofit2.http.GET
import retrofit2.http.Query

interface JavaRepoRepository {

    @GET("repositories?q=language:Java&sort=stars")
    suspend fun fetchJavaRepo(@Query("page") page: Int ): JavaRepoModel

}