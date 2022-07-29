package com.linh.cryptoviewer2.presentation.home

import app.cash.turbine.test
import com.linh.cryptoviewer2.domain.model.CoinFactory
import com.linh.cryptoviewer2.domain.model.CoinMarketDataFactory
import com.linh.cryptoviewer2.domain.model.FiatCurrency
import com.linh.cryptoviewer2.domain.model.PriceChangePercentage
import com.linh.cryptoviewer2.domain.usecase.GetCoinMarketDataUseCase
import com.linh.cryptoviewer2.presentation.home.model.CoinMarketDataToCoinUiMapper
import com.linh.cryptoviewer2.presentation.home.model.CoinToCoinUiMapper
import com.linh.cryptoviewer2.presentation.home.model.CoinUiFactory
import com.linh.cryptoviewer2.presentation.home.model.HomeScreenUiState
import com.linh.cryptoviewer2.utils.TestHelper
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val getCoinMarketDataUseCase: GetCoinMarketDataUseCase = mockk(relaxed = true)
    private val mapper: CoinMarketDataToCoinUiMapper = mockk()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(getCoinMarketDataUseCase, mapper)

        val testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Given ViewModel, When get coin, Then call get coin market data use case`() {
        viewModel.getCoin(TestHelper.randomString())

        coVerify { getCoinMarketDataUseCase(any(), any(), any()) }
    }

    @Test
    fun `Given ViewModel, When get coin, Then call get coin market data use case with correct params`() {
        val id = TestHelper.randomString()
        viewModel.getCoin(id)

        coVerify { getCoinMarketDataUseCase(FiatCurrency.USD, listOf(id), listOf(PriceChangePercentage.CHANGE_24_HOURS)) }
    }

    @Test
    fun `Given ViewModel, When init, Then show initial state`() {
        assertEquals(HomeScreenUiState.Initial, viewModel.uiState.value)
    }

    @Test
    fun `Given ViewModel, When get coin, Then show loading state`() = runTest {
        val id = TestHelper.randomString()

        viewModel.uiState.test {
            viewModel.getCoin(id)

            assertEquals(HomeScreenUiState.Initial, awaitItem())
            assertEquals(HomeScreenUiState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Given get coin usecase success, When get coin success, Then show correct data`() {
        val coin = CoinMarketDataFactory.makeCoinMarketData()
        val mappedCoinUi = CoinUiFactory.makeCoinUi()

        coEvery { getCoinMarketDataUseCase.invoke(FiatCurrency.USD, listOf(coin.id), listOf(PriceChangePercentage.CHANGE_24_HOURS)) } returns listOf(coin)
        every { mapper.map(listOf(coin)) } returns listOf(mappedCoinUi)

        viewModel.getCoin(coin.id)

        assertEquals(
            HomeScreenUiState.Success(listOf(mappedCoinUi)),
            viewModel.uiState.value
        )
    }

    @Test
    fun `Given get coin usecase error, When get coin success, Then show correct data`() {
        val coin = CoinFactory.makeCoin()

        coEvery { getCoinMarketDataUseCase.invoke(any(), any(), any()) } throws Exception()

        viewModel.getCoin(coin.id)

        assert(viewModel.uiState.value is HomeScreenUiState.Error)
    }
}