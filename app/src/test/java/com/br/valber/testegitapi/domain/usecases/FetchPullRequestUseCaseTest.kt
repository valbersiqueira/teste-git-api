package com.br.valber.testegitapi.domain.usecases

import com.br.valber.testegitapi.domain.entity.PullRequest
import com.br.valber.testegitapi.domain.output.FetchPullRequestOutput
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class FetchPullRequestUseCaseTest {

    private val fetchPullRequestOutput = mock(FetchPullRequestOutput::class.java)
    private val fetchPullRequestUseCase = FetchPullRequestUseCase(fetchPullRequestOutput)


    @Test
    fun `should fetch pull request when action use case`() = runTest {
        val pullRequest = mock(PullRequest::class.java)
        `when`(fetchPullRequestOutput.fetchPulls(0, 0, "", ""))
            .thenReturn(arrayListOf(pullRequest))

        val listPullRequest: List<PullRequest> = fetchPullRequestUseCase.fetchPulls(0, 0, "", "")
        assertThat(listPullRequest.size, `is`(1))
    }
}