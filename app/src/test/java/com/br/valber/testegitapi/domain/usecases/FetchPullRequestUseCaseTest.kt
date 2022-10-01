package com.br.valber.testegitapi.domain.usecases

import com.br.valber.testegitapi.data.model.PullRequestModel
import com.br.valber.testegitapi.domain.entity.PullRequest
import com.br.valber.testegitapi.domain.input.FetchPullRequestOut
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class FetchPullRequestUseCaseTest {

    private val fetchPullRequestOut = mock(FetchPullRequestOut::class.java)
    private val fetchPullRequestUseCase = FetchPullRequestUseCase(fetchPullRequestOut)


    @Test
    fun `should fetch pull request when action use case`() = runTest {
        val pullRequest = mock(PullRequest::class.java)
        `when`(fetchPullRequestOut.fetchPulls(0, 0, "", ""))
            .thenReturn(arrayListOf(pullRequest))

        val listPullRequest: List<PullRequest> = fetchPullRequestUseCase.fetchPulls(0, 0, "", "")
        assertThat(listPullRequest.size, `is`(1))
    }
}