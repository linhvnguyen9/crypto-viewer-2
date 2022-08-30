package com.linh.cryptoviewer2.presentation.watchlist

import app.cash.turbine.test
import com.linh.cryptoviewer2.domain.model.CoinFactory
import com.linh.cryptoviewer2.domain.model.CoinMarketDataFactory
import com.linh.cryptoviewer2.domain.model.FiatCurrency
import com.linh.cryptoviewer2.domain.model.PriceChangePercentage
import com.linh.cryptoviewer2.domain.usecase.GetCoinMarketDataUseCase
import com.linh.cryptoviewer2.presentation.watchlist.model.*
import com.linh.cryptoviewer2.presentation.util.ResourceProvider
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
class WatchlistViewModelTest {

    private val getCoinMarketDataUseCase: GetCoinMarketDataUseCase = mockk(relaxed = true)
    private val mapper: CoinMarketDataToCoinUiMapper = mockk()
    private val resourceProvider: ResourceProvider = mockk(relaxed = true)

    private lateinit var viewModel: WatchlistViewModel

    @Before
    fun setup() {
        viewModel = WatchlistViewModel(getCoinMarketDataUseCase, mapper, resourceProvider)

        val testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Given ViewModel, When get coin, Then call get coin market data use case`() {
        viewModel.getCoin()

        coVerify { getCoinMarketDataUseCase(any(), any()) }
    }

    @Test
    fun `Given ViewModel, When get coin, Then call get coin market data use case with correct params`() {
        viewModel.getCoin()

        coVerify { getCoinMarketDataUseCase(FiatCurrency.USD, listOf(PriceChangePercentage.CHANGE_24_HOURS)) }
    }

    @Test
    fun `Given ViewModel, When init, Then show initial state`() {
        assertEquals(WatchlistScreenUiState.Initial, viewModel.uiState.value)
    }

    @Test
    fun `Given ViewModel, When get coin, Then show loading state`() = runTest {
        viewModel.uiState.test {
            viewModel.getCoin()

            assertEquals(WatchlistScreenUiState.Initial, awaitItem())
            assertTrue(awaitItem() is WatchlistScreenUiState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Given get coin usecase success, When get coin success, Then show correct data`() {
        val coin = CoinMarketDataFactory.makeCoinMarketData()
        val mappedCoinUi = CoinUiFactory.makeCoinUi()

        coEvery { getCoinMarketDataUseCase.invoke(FiatCurrency.USD, listOf(coin.id), listOf(PriceChangePercentage.CHANGE_24_HOURS)) } returns listOf(coin)
        every { mapper.map(listOf(coin)) } returns listOf(mappedCoinUi)

        viewModel.getCoin()

        assertTrue(viewModel.uiState.value is WatchlistScreenUiState.Success)
        assertEquals(
            listOf(mappedCoinUi),
            (viewModel.uiState.value as WatchlistScreenUiState.Success).data.data
        )
    }

    @Test
    fun `Given get coin usecase error, When get coin success, Then show correct data`() {
        coEvery { getCoinMarketDataUseCase.invoke(any(), any(), any()) } throws Exception()

        viewModel.getCoin()

        assert(viewModel.uiState.value is WatchlistScreenUiState.Error)
    }

    @Test
    fun `Given data is loaded, When refresh, Then call to fetch the same data`() = runTest {
        val coin = CoinMarketDataFactory.makeCoinMarketData()
        val mappedCoinUi = CoinUiFactory.makeCoinUi()

        coEvery { getCoinMarketDataUseCase.invoke(FiatCurrency.USD, listOf(PriceChangePercentage.CHANGE_24_HOURS)) } returns listOf(coin)
        every { mapper.map(listOf(coin)) } returns listOf(mappedCoinUi)

        viewModel.getCoin()
        val uiState = viewModel.uiState.value
        (uiState as WatchlistScreenUiState.Success).data.onRefresh()
        coVerify(exactly = 2) { getCoinMarketDataUseCase(any(), any()) }
    }

    @Test
    fun `Given data is loaded, When refresh, Then show old data while loading`() = runTest {
        val coin = CoinMarketDataFactory.makeCoinMarketData()
        val mappedCoinUi = CoinUiFactory.makeCoinUi()

        coEvery { getCoinMarketDataUseCase.invoke(FiatCurrency.USD, listOf(PriceChangePercentage.CHANGE_24_HOURS)) } returns listOf(coin)
        every { mapper.map(listOf(coin)) } returns listOf(mappedCoinUi)

        viewModel.uiState.test {
            viewModel.getCoin()

            assertEquals(WatchlistScreenUiState.Initial, awaitItem())
            assertTrue(awaitItem() is WatchlistScreenUiState.Loading)
            val successData = awaitItem()
            assertTrue(successData is WatchlistScreenUiState.Success)

            (viewModel.uiState.value as WatchlistScreenUiState.Success).data.onRefresh()
            val loading = awaitItem()
            assertTrue(loading is WatchlistScreenUiState.Loading)
            assertEquals((successData as WatchlistScreenUiState.Success).data.data ,(loading as WatchlistScreenUiState.Loading).oldData)
            cancelAndIgnoreRemainingEvents()
        }
    }
}