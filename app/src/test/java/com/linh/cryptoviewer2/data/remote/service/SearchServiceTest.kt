package com.linh.cryptoviewer2.data.remote.service

import com.linh.cryptoviewer2.getJson
import com.linh.cryptoviewer2.utils.BaseServiceTest
import com.linh.cryptoviewer2.utils.TestHelper
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchServiceTest: BaseServiceTest<SearchService>(SearchService::class.java) {

    @Test
    fun `Given SearchService, When search, Then return mapped data`() = runBlocking {
        val json = getJson("search.json")
        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

        val response = service.search(TestHelper.randomString())

        with(response) {
            assertEquals(2, coins?.size)
            assertEquals(2, exchanges?.size)
            assertEquals(0, icos?.size)
            assertEquals(2, nfts?.size)

            assertEquals("binance-usd", coins?.get(0)?.id)
            assertEquals("Binance USD", coins?.get(0)?.name)
            assertEquals("binance-usd", coins?.get(0)?.apiSymbol)
            assertEquals("BUSD", coins?.get(0)?.symbol)
            assertEquals(6, coins?.get(0)?.marketCapRank)
            assertEquals("https://assets.coingecko.com/coins/images/9576/thumb/BUSD.png", coins?.get(0)?.thumb)
            assertEquals("https://assets.coingecko.com/coins/images/9576/large/BUSD.png", coins?.get(0)?.large)
        }
    }

    @Test
    fun `Given SearchService, When search, Then call correct path`() = runBlocking {
        val json = getJson("search.json")
        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

        val query = TestHelper.randomString()

        service.search(query)

        assertEquals("/search?query=${query}", mockWebServer.takeRequest().path)
    }
}