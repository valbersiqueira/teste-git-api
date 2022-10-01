package com.br.valber.testegitapi.data.repositories

import com.br.valber.testegitapi.MainDispatcherRule
import com.br.valber.testegitapi.argumentCaptor
import com.br.valber.testegitapi.data.model.JavaRepoModel
import com.br.valber.testegitapi.data.service.JavaRepoService
import com.br.valber.testegitapi.domain.entity.JavaRepo
import com.br.valber.testegitapi.framework.RemoteBuilder
import com.br.valber.testegitapi.framework.RequestApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock


@ExperimentalCoroutinesApi
internal class FetchJavaRepoRepositoryTest {

    @get:Rule
    val rule = MainDispatcherRule()

    private val requestApi = mock(RequestApi::class.java)
    private val remoteBuilder = mock(RemoteBuilder::class.java)
    private lateinit var fetchJavaRepoRepository: FetchJavaRepoRepository

    @Before
    fun setUp() {
        fetchJavaRepoRepository =
            FetchJavaRepoRepository(remoteBuilder , requestApi, rule.testDispatcher)
    }

    @Test
    fun `should javaRepo with size 1 when action fetch request`() = runTest(rule.testDispatcher) {
        val argumentCaptorTestDispatcher = argumentCaptor<TestDispatcher>()
        val argumentCaptorResult = argumentCaptor<suspend () -> Any>()

        doAnswer {
            @Suppress("UNCHECKED_CAST")
            (it.arguments[1] as suspend () -> JavaRepoModel)
            arrayListOf(
                JavaRepo(
                    name = "",
                    description = "",
                    forksCount = "",
                    fullName = "",
                    startCount = "",
                    pullsUrl = "",
                    login = "",
                    avatar = ""
                )
            )
        }.`when`(requestApi).safeRequestApi(
            argumentCaptorTestDispatcher.capture(),
            argumentCaptorResult.captureLambda()
        )

        var response: List<JavaRepo>? = null
        launch {
            response = fetchJavaRepoRepository.fetchJavaRepo(0, 1)
        }
        assertThat(response?.size, `is`(1))

    }

}