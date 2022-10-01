package com.br.valber.testegitapi.data.repositories

import com.br.valber.testegitapi.MainDispatcherRule
import com.br.valber.testegitapi.data.model.JavaRepoModel
import com.br.valber.testegitapi.data.model.PullRequestModel
import com.br.valber.testegitapi.data.service.JavaRepoService
import com.br.valber.testegitapi.domain.entity.PullRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class FetchPullRequestRepositoryTest {

    @get:Rule
    val rule = MainDispatcherRule()

    private val service = mock(JavaRepoService::class.java)
    private lateinit var repository: FetchPullRequestRepository

    @Before
    fun setUp() {
        repository = FetchPullRequestRepository(service, rule.testDispatcher)
    }

    @Test
    fun `should return pull request with size 1 when action fetch request`() =
        runTest(rule.testDispatcher) {
            val pullRequestModel = mock(PullRequestModel::class.java)
            `when`(service.fetchPullsRequest("", "", 0, 0))
                .thenReturn(arrayListOf(pullRequestModel))

            var response: List<PullRequest>? = null
            launch {
                response = repository.fetchPulls(0, 0, "", "")
            }
            assertThat(response?.size, `is`(1))
        }

    @Test(expected = HttpException::class)
    fun `should throw exception when converter mapper fails`() = runTest(rule.testDispatcher) {
        val service = FakeServiceImpl()
        val pullRequestModel = mock(PullRequestModel::class.java)
        `when`(service.fetchPullsRequest("", "", 0, 0))
            .thenReturn(arrayListOf(pullRequestModel))

        launch {
            repository.fetchPulls(0, 0, "", "")
        }
    }

}