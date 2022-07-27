package com.linh.cryptoviewer2.data.remote.service

import com.linh.cryptoviewer2.data.remote.model.GetCoinResponse
import com.linh.cryptoviewer2.getJson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoinServiceTest  {
    @get:Rule
    val mockWebServer = MockWebServer()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url(BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val coinService by lazy { retrofit.create(CoinService::class.java) }

    @Test
    fun `Given CoinService, When get coin, Then return data`() = runBlocking {
        val json = getJson("get_binancecoin_success.json")
        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

        val response = coinService.getCoin("binancecoin")

        println(response)

        assertNotNull(response)
    }

    @Test
    fun `Given CoinService, When get coin, Then return mapped data`() = runBlocking {
        val json = getJson("get_binancecoin_success.json")
        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

        val response = coinService.getCoin("binancecoin")

        with(response) {
            assertEquals("binancecoin", response.id)
            assertEquals("bnb", response.symbol)
            assertEquals("BNB", response.name)
            assertEquals(
                GetCoinResponse.Image(thumbUrl = "thumb", smallUrl = "small", largeUrl = "large"),
                response.image
            )
            assertEquals(72.78f, response.sentimentsVoteUpPercentage)
            assertEquals(27.22f, sentimentsVoteDownPercentage)
            assertEquals(5, marketCapRank)
            assertEquals(6, coinGeckoRank)
            assertEquals(
                GetCoinResponse.MarketData(
                    currentPrice = GetCoinResponse.MarketData.CurrentPrice(
                        252.54,
                        5904558.0,
                        0.01184735
                    )
                ),
                marketData
            )
        }
    }

    @Test
    fun `Given CoinService, When get coin, Then call get coin API`() = runBlocking {
        val json = getJson("get_binancecoin_success.json")
        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))
        val coinId = "binancecoin"

        coinService.getCoin(coinId)

        assertEquals("${BASE_URL}coin/$coinId", mockWebServer.takeRequest().path)
    }

    companion object {
        private const val BASE_URL = "/"
    }
}