package com.linh.cryptoviewer2.data.repository.mapper

import com.linh.cryptoviewer2.data.mapper.GetCoinResponseToCoinMapper
import com.linh.cryptoviewer2.data.remote.model.GetCoinResponseFactory
import com.linh.cryptoviewer2.data.remote.service.CoinService
import com.linh.cryptoviewer2.data.repository.CoinRepositoryImpl
import com.linh.cryptoviewer2.domain.model.Coin
import com.linh.cryptoviewer2.domain.model.CoinFactory
import com.linh.cryptoviewer2.domain.repository.CoinRepository
import com.linh.cryptoviewer2.utils.TestHelper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.ContinuationInterceptor

class CoinRepositoryImplTest {

    private var coinService: CoinService = mockk(relaxed = true)

    private var getCoinResponseToCoinMapper: GetCoinResponseToCoinMapper = mockk(relaxed = true)

    private lateinit var coinRepository: CoinRepository

    @Before
    fun setup() {
        coinRepository = CoinRepositoryImpl(coinService, getCoinResponseToCoinMapper)
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
}