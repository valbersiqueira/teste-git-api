package com.br.valber.testegitapi.di

import com.br.valber.testegitapi.framework.RemoteBuilder
import com.br.valber.testegitapi.framework.RequestApi
import com.br.valber.testegitapi.framework.RequestApiImpl
import com.br.valber.testegitapi.framework.RetrofitBuilderImpl
import com.br.valber.testegitapi.data.repositories.FetchJavaRepoRepositorie
import com.br.valber.testegitapi.domain.input.FetchRepoIn
import com.br.valber.testegitapi.domain.input.FetchRepoOut
import com.br.valber.testegitapi.domain.usecases.FetchRepoUseCase
import com.br.valber.testegitapi.presentation.javarepo.paging.JavaRepoPagingSource
import com.br.valber.testegitapi.presentation.javarepo.viewmodel.JavaRepoViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val projectMainModule = module {
    factory<RequestApi> { RequestApiImpl() }
    factory<RemoteBuilder> { RetrofitBuilderImpl() }
    factory<FetchRepoOut> { FetchJavaRepoRepositorie(get(), get(), Dispatchers.IO) }
    factory <FetchRepoIn>{ FetchRepoUseCase(get()) }
    single { JavaRepoPagingSource(get()) }
    viewModel { JavaRepoViewModel(get()) }
}

private val loadingProjectMainModule by lazy { startKoin { loadKoinModules(projectMainModule) } }

internal fun injectProjectMainModule() = loadingProjectMainModule