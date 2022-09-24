package com.br.valber.testegitapi.framework

import kotlinx.coroutines.CoroutineDispatcher

interface RequestApi {

    suspend fun <T> safeRequestApi(
        dispatcher: CoroutineDispatcher,
        api: suspend () -> T
    ): T

}