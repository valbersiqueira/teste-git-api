package com.br.valber.testegitapi.di

import com.br.valber.testegitapi.data.repositories.FetchJavaRepoRepository
import com.br.valber.testegitapi.data.repositories.FetchPullRequestRepository
import com.br.valber.testegitapi.domain.input.FetchPullRequestIn
import com.br.valber.testegitapi.domain.input.FetchPullRequestOut
import com.br.valber.testegitapi.domain.input.FetchRepoIn
import com.br.valber.testegitapi.domain.input.FetchRepoOut
import com.br.valber.testegitapi.domain.usecases.FetchPullRequestUseCase
import com.br.valber.testegitapi.domain.usecases.FetchRepoUseCase
import com.br.valber.testegitapi.framework.RemoteBuilder
import com.br.valber.testegitapi.framework.RequestApi
import com.br.valber.testegitapi.framework.RequestApiImpl
import com.br.valber.testegitapi.framework.RetrofitBuilderImpl
import com.br.valber.testegitapi.presentation.javarepo.viewmodel.JavaRepoViewModel
import com.br.valber.testegitapi.presentation.pullrequest.viewmodel.PullRequestViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val javaRepoModule = module {
    factory<RequestApi> { RequestApiImpl() }
    factory<RemoteBuilder> { RetrofitBuilderImpl() }
    factory<FetchRepoOut> { FetchJavaRepoRepository(get(), get(), Dispatchers.IO) }
    factory<FetchRepoIn> { FetchRepoUseCase(get()) }

    viewModel { JavaRepoViewModel(get()) }
}


private val pullsModule = module {
    factory<RequestApi> { RequestApiImpl() }
    factory<RemoteBuilder> { RetrofitBuilderImpl() }
    factory<FetchPullRequestOut> { FetchPullRequestRepository(get(), get(), Dispatchers.IO) }
    factory<FetchPullRequestIn> { FetchPullRequestUseCase(get()) }

    viewModel { (owner: String?, nameRepo: String) -> PullRequestViewModel(owner, nameRepo, get()) }
}


private val loadingJavaRepoModule by lazy {
    startKoin {
        loadKoinModules(arrayListOf(javaRepoModule, pullsModule))
    }
}

internal fun injectProjectMainModule() = loadingJavaRepoModule