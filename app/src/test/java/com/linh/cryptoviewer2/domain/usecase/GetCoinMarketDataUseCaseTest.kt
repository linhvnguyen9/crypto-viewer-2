package com.linh.cryptoviewer2.domain.usecase

import com.linh.cryptoviewer2.domain.model.FiatCurrency
import com.linh.cryptoviewer2.domain.model.PriceChangePercentage
import com.linh.cryptoviewer2.domain.repository.CoinRepository
import com.linh.cryptoviewer2.utils.TestHelper
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCoinMarketDataUseCaseTest {
    private val repository: CoinRepository = mockk(relaxed = true)

    private lateinit var useCase: GetCoinMarketDataUseCase

    @Before
    fun setup() {
        useCase = GetCoinMarketDataUseCase(repository)
    }

    @Test
    fun `Given use case, When invoke, Then call get coin in repository`() = runBlocking {
        useCase(FiatCurrency.USD, listOf(TestHelper.randomString()), listOf(PriceChangePercentage.CHANGE_24_HOURS))

        coVerify { repository.getCoinsMarketData(any(), any(), any()) }
    }

    @Test
    fun `Given use case, When invoke, Then get coin in repository with id`() = runBlocking {
        val fiat = FiatCurrency.USD
        val ids = listOf(TestHelper.randomString())
        val filter = listOf(PriceChangePercentage.CHANGE_24_HOURS)
        useCase(fiat, ids, filter)

        coVerify { repository.getCoinsMarketData(fiat, ids, filter) }
    }
}