package com.linh.cryptoviewer2.data.remote.service

import com.linh.cryptoviewer2.data.remote.model.response.GetCoinResponse
import com.linh.cryptoviewer2.getJson
import com.linh.cryptoviewer2.utils.TestHelper
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
                    ),
                    priceChangePercentage24h = 3.32841
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

        assertEquals("${BASE_URL}coins/$coinId", mockWebServer.takeRequest().path)
    }

    @Test
    fun `Given CoinService, When get coins market data, Then return data`() = runBlocking {
        val json = getJson("get_coins_market_data.json")
        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

        val response = coinService.getCoinsMarketData(TestHelper.randomString(), TestHelper.randomString(), TestHelper.randomString())

        println(response)

        assertEquals(2, response.size)
    }

    @Test
    fun `Given CoinService, When get coins market data, Then return mapped data`() = runBlocking {
        val json = getJson("get_coins_market_data.json")
        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

        val response = coinService.getCoinsMarketData(TestHelper.randomString(), TestHelper.randomString(), TestHelper.randomString())

        with(response[1]) {
            assertEquals("binancecoin", id)
            assertEquals("bnb", symbol)
            assertEquals("BNB", name)
            assertEquals("https://assets.coingecko.com/coins/images/825/large/bnb-icon2_2x.png?1644979850", image)
            assertEquals(5, marketCapRank)
            assertEquals(278.9, currentPrice)
            assertEquals(0.9692601460017406, priceChangePercentage1hInCurrency)
            assertEquals(3.6589859355208385, priceChangePercentage24hInCurrency)
            assertEquals(5.135957782380853, priceChangePercentage7dInCurrency)
        }
    }

    @Test
    fun `Given CoinService, When get coin, Then call get coin API with correct path`() = runBlocking {
        val json = getJson("get_coins_market_data.json")
        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))
        val coinId = TestHelper.randomString()
        val fiat = TestHelper.randomString()
        val priceChangePercentage = TestHelper.randomString()

        coinService.getCoinsMarketData(fiat = fiat, coinIds = coinId, priceChangePercentageFilter = priceChangePercentage)

        assertEquals("${BASE_URL}coins/markets?vs_currency=$fiat&ids=$coinId&price_change_percentage=$priceChangePercentage", mockWebServer.takeRequest().path)
    }

    companion object {
        private const val BASE_URL = "/"
    }
}