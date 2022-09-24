package com.br.valber.testegitapi.data.service

import com.br.valber.testegitapi.data.model.JavaRepoModel
import com.br.valber.testegitapi.data.model.PullRequestModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JavaRepoService {

    @GET("repositories?q=language:Java&sort=stars")
    suspend fun fetchJavaRepo(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): JavaRepoModel

    @GET("repos/{create}/{repo}/pulls")
    suspend fun fetchPullsRequest(
        @Path("create") create: String,
        @Path("repo") repo: String
    ): List<PullRequestModel>

}