package com.linh.cryptoviewer2.utils

import com.linh.cryptoviewer2.data.remote.service.CoinServiceTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseServiceTest<T>(serviceClass: Class<T>) {
    @get:Rule
    val mockWebServer = MockWebServer()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    protected val service: T = retrofit.create(serviceClass)
}