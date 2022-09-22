package com.br.valber.testegitapi.core

import kotlinx.coroutines.CoroutineDispatcher

interface RequestApi {

    suspend fun <T> safeRequestApi(
        dispatcher: CoroutineDispatcher,
        api: suspend () -> T
    ): T

}