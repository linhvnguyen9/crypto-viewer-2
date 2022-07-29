package com.linh.cryptoviewer2.data.repository

import com.linh.cryptoviewer2.data.mapper.GetCoinResponseToCoinMapper
import com.linh.cryptoviewer2.data.mapper.GetCoinsMarketDataResponseToCoinMarketDataMapper
import com.linh.cryptoviewer2.data.remote.model.GetCoinResponseFactory
import com.linh.cryptoviewer2.data.remote.model.GetCoinsMarketDataResponseFactory
import com.linh.cryptoviewer2.data.remote.service.CoinService
import com.linh.cryptoviewer2.domain.model.*
import com.linh.cryptoviewer2.domain.repository.CoinRepository
import com.linh.cryptoviewer2.utils.TestHelper
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CoinRepositoryImplTest {

    private var coinService: CoinService = mockk(relaxed = true)

    private var getCoinResponseToCoinMapper: GetCoinResponseToCoinMapper = mockk(relaxed = true)
    private var getCoinsMarketDataResponseToCoinMarketDataMapper: GetCoinsMarketDataResponseToCoinMarketDataMapper = mockk(relaxed = true)

    private lateinit var coinRepository: CoinRepository

    @Before
    fun setup() {
        coinRepository = CoinRepositoryImpl(
            coinService,
            getCoinResponseToCoinMapper,
            getCoinsMarketDataResponseToCoinMarketDataMapper
        )
    }

    @Test
    fun `Given app, When get coin, Then call get coin service`() = runBlocking {
        val id = TestHelper.randomString()

        coinRepository.getCoin(id)

        coVerify { coinService.getCoin(id) }
    }

//    @Test
//    fun `Given app, When get coin, Then call get coin service with IO dispatcher`() = runBlocking {
//        val id = TestHelper.randomString()
//
//        coinRepository.getCoin(id)
//
//        assertEquals(Dispatchers.IO, coroutineContext[ContinuationInterceptor])
//
//        coVerify { coinService.getCoin(id) }
//    }

    @Test
    fun `Given app, When get coin, Then map data`() = runBlocking {
        val id = TestHelper.randomString()

        coinRepository.getCoin(id)

        coVerify { getCoinResponseToCoinMapper.map(any()) }
    }

    @Test
    fun `Given app, When get coin, Then return correctly mapped data`() = runBlocking {
        val id = TestHelper.randomString()
        val response = GetCoinResponseFactory.makeGetCoinResponse()
        coEvery { coinService.getCoin(id) } returns response
        val expectedCoin = CoinFactory.makeCoin()
        every { getCoinResponseToCoinMapper.map(response) } returns expectedCoin

        val result = coinRepository.getCoin(id)

        assertEquals(expectedCoin, result)
    }

    @Test
    fun `Given fiat currency, list of coin ids and list of filter, When get coins market data, Then call get coin market data service with correct fiat currency parameter`() = runBlocking {
        val fiatCurrency = FiatCurrency.USD
        val ids = List(2) { TestHelper.randomString() }
        val priceChangePercentage = listOf(PriceChangePercentage.CHANGE_1_HOUR, PriceChangePercentage.CHANGE_24_HOURS)

        coinRepository.getCoinsMarketData(fiatCurrency, ids, priceChangePercentage)

        coVerify { coinService.getCoinsMarketData(fiatCurrency.value, any(), any()) }
    }


    @Test
    fun `Given fiat currency, list of coin ids and list of filter, When get coins market data, Then call get coin market data service with correctly joined ids string`() = runBlocking {
        val fiatCurrency = FiatCurrency.USD
        val ids = List(2) { TestHelper.randomString() }
        val priceChangePercentage = listOf(PriceChangePercentage.CHANGE_1_HOUR, PriceChangePercentage.CHANGE_24_HOURS)

        coinRepository.getCoinsMarketData(fiatCurrency, ids, priceChangePercentage)

        coVerify { coinService.getCoinsMarketData(any(), ids.joinToString(","), any()) }
    }

    @Test
    fun `Given fiat currency, list of coin ids and list of filter, When get coins market data, Then call get coin market data service with correctly joined filter string`() = runBlocking {
        val fiatCurrency = FiatCurrency.USD
        val ids = List(2) { TestHelper.randomString() }
        val priceChangePercentage = listOf(PriceChangePercentage.CHANGE_1_HOUR, PriceChangePercentage.CHANGE_24_HOURS)

        coinRepository.getCoinsMarketData(fiatCurrency, ids, priceChangePercentage)

        coVerify { coinService.getCoinsMarketData(any(), any(), priceChangePercentage.joinToString(",") { it.value }) }
    }

    @Test
    fun `Given fiat currency, list of coin ids and list of filter, When get coins market data, Then data is mapped`() = runBlocking {
        val fiatCurrency = FiatCurrency.USD
        val ids = List(2) { TestHelper.randomString() }
        val priceChangePercentage = listOf(PriceChangePercentage.CHANGE_1_HOUR, PriceChangePercentage.CHANGE_24_HOURS)

        coinRepository.getCoinsMarketData(fiatCurrency, ids, priceChangePercentage)

        verify { getCoinsMarketDataResponseToCoinMarketDataMapper.map(any()) }
    }

    @Test
    fun `Given fiat currency, list of coin ids and list of filter, When get coins market data, Then data is mapped correctly`() = runBlocking {
        val fiatCurrency = FiatCurrency.USD
        val ids = listOf(TestHelper.randomString())
        val priceChangePercentage = listOf(PriceChangePercentage.CHANGE_1_HOUR, PriceChangePercentage.CHANGE_24_HOURS)
        val response = GetCoinsMarketDataResponseFactory.createGetCoinsMarketDataResponse()
        coEvery { coinService.getCoinsMarketData(any(), any(), any()) } returns response
        val expectedCoinMarketData = listOf(CoinMarketDataFactory.makeCoinMarketData())
        every { getCoinsMarketDataResponseToCoinMarketDataMapper.map(response) } returns expectedCoinMarketData

        val result = coinRepository.getCoinsMarketData(fiatCurrency, ids, priceChangePercentage)

        assertEquals(expectedCoinMarketData, result)
    }
}