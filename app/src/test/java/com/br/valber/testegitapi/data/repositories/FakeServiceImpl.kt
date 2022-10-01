package com.br.valber.testegitapi.data.repositories

import com.br.valber.testegitapi.data.model.JavaRepoModel
import com.br.valber.testegitapi.data.model.PullRequestModel
import com.br.valber.testegitapi.data.service.JavaRepoService
import okhttp3.ResponseBody
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response

internal class FakeServiceImpl : JavaRepoService {
    private val responseBody = Mockito.mock(ResponseBody::class.java)

    override suspend fun fetchJavaRepo(page: Int, itemsPerPage: Int): JavaRepoModel {
        throw HttpException(Response.error<PullRequestModel>(400, responseBody))
    }

    override suspend fun fetchPullsRequest(
        owner: String,
        repo: String,
        page: Int,
        itemsPerPage: Int
    ): List<PullRequestModel> {
        throw HttpException(Response.error<PullRequestModel>(400, responseBody))
    }

}