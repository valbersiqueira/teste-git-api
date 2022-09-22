package com.br.valber.testegitapi.core

import retrofit2.Retrofit

interface RemoteBuilder {

    fun build(): Retrofit
}