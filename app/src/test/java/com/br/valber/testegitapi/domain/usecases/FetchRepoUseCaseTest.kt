package com.br.valber.testegitapi.domain.usecases

import com.br.valber.testegitapi.domain.entity.JavaRepo
import com.br.valber.testegitapi.domain.output.FetchRepoOutput
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class FetchRepoUseCaseTest {

    private val fetchRepoOutput = Mockito.mock(FetchRepoOutput::class.java)
    private val fetchRepoUseCase = FetchRepoUseCase(fetchRepoOutput)

    @Test
    fun `should fetch pull request when action use case`() = runTest {
        val javaRepo = Mockito.mock(JavaRepo::class.java)
        Mockito.`when`(fetchRepoOutput.fetchJavaRepo(0, 0))
            .thenReturn(arrayListOf(javaRepo))

        val listPullRequest: List<JavaRepo> = fetchRepoUseCase.fetchJavaRepo(0, 0)
        MatcherAssert.assertThat(listPullRequest.size, Matchers.`is`(1))
    }
}