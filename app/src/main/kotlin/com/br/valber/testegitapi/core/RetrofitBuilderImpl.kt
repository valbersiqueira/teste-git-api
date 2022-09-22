package com.br.valber.testegitapi.core

import com.br.valber.testegitapi.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilderImpl : RemoteBuilder {

    override fun build(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.enviroment)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        return okHttpClientBuilder.build()
    }
}