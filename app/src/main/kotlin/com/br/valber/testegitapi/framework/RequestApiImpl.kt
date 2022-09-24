package com.br.valber.testegitapi.framework

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class RequestApiImpl : RequestApi{

    override suspend fun <T> safeRequestApi(
        dispatcher: CoroutineDispatcher,
        api: suspend () -> T
    ): T {
        return withContext(dispatcher) {
            try {
                api.invoke()
            } catch (ex: Throwable) {
                throw Throwable(ex)
            }
        }
    }

}