package com.linh.cryptoviewer2.presentation.home

import app.cash.turbine.test
import com.linh.cryptoviewer2.domain.model.CoinFactory
import com.linh.cryptoviewer2.domain.model.CoinMarketDataFactory
import com.linh.cryptoviewer2.domain.model.FiatCurrency
import com.linh.cryptoviewer2.domain.model.PriceChangePercentage
import com.linh.cryptoviewer2.domain.usecase.GetCoinMarketDataUseCase
import com.linh.cryptoviewer2.presentation.home.model.*
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
import org.mockito.Mockito.mock
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val getCoinMarketDataUseCase: GetCoinMarketDataUseCase = mockk(relaxed = true)
    private val mapper: CoinMarketDataToCoinUiMapper = mockk()
    private val resourceProvder: ResourceProvider = mockk(relaxed = true)

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(getCoinMarketDataUseCase, mapper, resourceProvder)

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
            assertTrue(awaitItem() is HomeScreenUiState.Loading)
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

        assertTrue(viewModel.uiState.value is HomeScreenUiState.Success)
        assertEquals(
            listOf(mappedCoinUi),
            (viewModel.uiState.value as HomeScreenUiState.Success).data.data
        )
    }

    @Test
    fun `Given get coin usecase error, When get coin success, Then show correct data`() {
        val coin = CoinFactory.makeCoin()

        coEvery { getCoinMarketDataUseCase.invoke(any(), any(), any()) } throws Exception()

        viewModel.getCoin(coin.id)

        assert(viewModel.uiState.value is HomeScreenUiState.Error)
    }

    @Test
    fun `Given data is loaded, When refresh, Then call to fetch the same data`() = runTest {
        val coin = CoinMarketDataFactory.makeCoinMarketData()
        val mappedCoinUi = CoinUiFactory.makeCoinUi()

        coEvery { getCoinMarketDataUseCase.invoke(FiatCurrency.USD, listOf(coin.id), listOf(PriceChangePercentage.CHANGE_24_HOURS)) } returns listOf(coin)
        every { mapper.map(listOf(coin)) } returns listOf(mappedCoinUi)

        viewModel.getCoin(coin.id)
        val uiState = viewModel.uiState.value
        (uiState as HomeScreenUiState.Success).data.onRefresh()
        coVerify(exactly = 2) { getCoinMarketDataUseCase(any(), any(), any()) }
    }

    @Test
    fun `Given data is loaded, When refresh, Then show old data while loading`() = runTest {
        val coin = CoinMarketDataFactory.makeCoinMarketData()
        val mappedCoinUi = CoinUiFactory.makeCoinUi()

        coEvery { getCoinMarketDataUseCase.invoke(FiatCurrency.USD, listOf(coin.id), listOf(PriceChangePercentage.CHANGE_24_HOURS)) } returns listOf(coin)
        every { mapper.map(listOf(coin)) } returns listOf(mappedCoinUi)

        viewModel.uiState.test {
            viewModel.getCoin(coin.id)

            assertEquals(HomeScreenUiState.Initial, awaitItem())
            assertTrue(awaitItem() is HomeScreenUiState.Loading)
            val successData = awaitItem()
            assertTrue(successData is HomeScreenUiState.Success)

            (viewModel.uiState.value as HomeScreenUiState.Success).data.onRefresh()
            val loading = awaitItem()
            assertTrue(loading is HomeScreenUiState.Loading)
            assertEquals((successData as HomeScreenUiState.Success).data.data ,(loading as HomeScreenUiState.Loading).oldData)
            cancelAndIgnoreRemainingEvents()
        }
    }
}