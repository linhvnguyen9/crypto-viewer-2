package com.linh.cryptoviewer2.data.remote.service

import com.linh.cryptoviewer2.data.remote.model.response.GetCoinResponse
import com.linh.cryptoviewer2.data.remote.model.response.GetCoinsMarketDataResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinService {

    @GET("coins/{id}")
    suspend fun getCoin(@Path("id") id: String): GetCoinResponse

    @GET("coins/markets")
    suspend fun getCoinsMarketData(
        @Query("vs_currency") fiat: String,
        @Query("ids") coinIds: String,
        @Query("price_change_percentage") priceChangePercentageFilter: String
    ): GetCoinsMarketDataResponse
}