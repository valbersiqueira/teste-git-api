package com.br.valber.testegitapi.data.repositories

import com.br.valber.testegitapi.MainDispatcherRule
import com.br.valber.testegitapi.data.model.ItemJavaModel
import com.br.valber.testegitapi.data.model.JavaRepoModel
import com.br.valber.testegitapi.data.model.OwnerModel
import com.br.valber.testegitapi.data.service.JavaRepoService
import com.br.valber.testegitapi.domain.entity.JavaRepo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.HttpException
import java.lang.RuntimeException
import java.lang.reflect.Type


@ExperimentalCoroutinesApi
internal class FetchJavaRepoRepositoryTest {

    @get:Rule
    val rule = MainDispatcherRule()

    private val service = mock(JavaRepoService::class.java)
    private lateinit var fetchJavaRepoRepository: FetchJavaRepoRepository

    @Before
    fun setUp() {
        fetchJavaRepoRepository =
            FetchJavaRepoRepository(service, rule.testDispatcher)
    }

    @Test
    fun `should return javaRepo with size 1 when action fetch request`() =
        runTest(rule.testDispatcher) {

            `when`(service.fetchJavaRepo(0, 1)).thenReturn(
                JavaRepoModel(
                    totalCount = 0, items = arrayListOf(
                        ItemJavaModel(
                            name = "",
                            description = "",
                            forks = 0,
                            fullName = "",
                            pullsUrl = "",
                            owner = OwnerModel(login = "", avatarUrl = ""),
                            id = 0,
                            stargazersCount = 0
                        )
                    )
                )
            )

            var response: List<JavaRepo>? = null
            launch {
                response = fetchJavaRepoRepository.fetchJavaRepo(0, 1)
            }

            assertThat(response?.size, `is`(1))

        }

    @Test(expected = HttpException::class)
    fun `should throw exception when converter mapper fails`() = runTest(rule.testDispatcher) {
        val service = FakeServiceImpl()
        val response = mock(JavaRepoModel::class.java)
        `when`(service.fetchJavaRepo(0, 1))
            .thenReturn(
                response
            )

        launch {
            fetchJavaRepoRepository.fetchJavaRepo(0, 1)
        }
    }

}