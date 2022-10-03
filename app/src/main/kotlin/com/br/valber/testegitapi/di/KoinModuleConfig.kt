package com.br.valber.testegitapi.di

import com.br.valber.testegitapi.data.repositories.FetchJavaRepoRepository
import com.br.valber.testegitapi.data.repositories.FetchPullRequestRepository
import com.br.valber.testegitapi.data.service.JavaRepoService
import com.br.valber.testegitapi.domain.input.FetchPullRequestInput
import com.br.valber.testegitapi.domain.output.FetchPullRequestOutput
import com.br.valber.testegitapi.domain.input.FetchRepoInput
import com.br.valber.testegitapi.domain.output.FetchRepoOutput
import com.br.valber.testegitapi.domain.usecases.FetchPullRequestUseCase
import com.br.valber.testegitapi.domain.usecases.FetchRepoUseCase
import com.br.valber.testegitapi.framework.RemoteBuilder
import com.br.valber.testegitapi.framework.RetrofitBuilderImpl
import com.br.valber.testegitapi.presentation.javarepo.viewmodel.JavaRepoViewModel
import com.br.valber.testegitapi.presentation.pullrequest.viewmodel.PullRequestViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

internal val javaRepoModule = module {
    factory<RemoteBuilder> { RetrofitBuilderImpl() }
    factory<FetchRepoOutput> { FetchJavaRepoRepository(get(), Dispatchers.IO) }
    factory<JavaRepoService> { get<RemoteBuilder>().build().create(JavaRepoService::class.java) }
    factory<FetchRepoInput> { FetchRepoUseCase(get()) }

    factory<FetchPullRequestOutput> { FetchPullRequestRepository(get(), Dispatchers.IO) }
    factory<FetchPullRequestInput> { FetchPullRequestUseCase(get()) }

    viewModel { (owner: String?, nameRepo: String) -> PullRequestViewModel(owner, nameRepo, get()) }
    viewModel { JavaRepoViewModel(get()) }
}

private val loadingJavaRepoModule by lazy {
    startKoin {
        loadKoinModules(javaRepoModule)
    }
}

internal fun injectProjectMainModule() = loadingJavaRepoModule