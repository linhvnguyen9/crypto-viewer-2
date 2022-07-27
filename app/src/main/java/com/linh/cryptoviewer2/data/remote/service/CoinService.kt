package com.linh.cryptoviewer2.data.remote.service

import com.linh.cryptoviewer2.data.remote.model.GetCoinResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinService {
    @GET("coin/{id}")
    suspend fun getCoin(@Path("id") id: String): GetCoinResponse
}