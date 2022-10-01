package com.br.valber.testegitapi.domain.usecases

import com.br.valber.testegitapi.domain.entity.JavaRepo
import com.br.valber.testegitapi.domain.input.FetchRepoOut
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class FetchRepoUseCaseTest {

    private val fetchRepoOut = Mockito.mock(FetchRepoOut::class.java)
    private val fetchRepoUseCase = FetchRepoUseCase(fetchRepoOut)

    @Test
    fun `should fetch pull request when action use case`() = runTest {
        val pullRequest = Mockito.mock(JavaRepo::class.java)
        Mockito.`when`(fetchRepoOut.fetchJavaRepo(0, 0))
            .thenReturn(arrayListOf(pullRequest))

        val listPullRequest: List<JavaRepo> = fetchRepoUseCase.fetchJavaRepo(0, 0)
        MatcherAssert.assertThat(listPullRequest.size, Matchers.`is`(1))
    }
}