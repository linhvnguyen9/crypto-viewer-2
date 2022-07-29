package com.linh.cryptoviewer2.presentation.home

import app.cash.turbine.test
import com.linh.cryptoviewer2.domain.model.CoinFactory
import com.linh.cryptoviewer2.domain.usecase.GetCoinUseCase
import com.linh.cryptoviewer2.presentation.home.model.CoinToCoinUiMapper
import com.linh.cryptoviewer2.presentation.home.model.CoinUi
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

    private val getCoinUseCase: GetCoinUseCase = mockk(relaxed = true)
    private val mapper: CoinToCoinUiMapper = mockk()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(getCoinUseCase, mapper)

        val testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Given ViewModel, When get coin, Then call get coin use case`() {
        viewModel.getCoin(TestHelper.randomString())

        coVerify { getCoinUseCase(any()) }
    }

    @Test
    fun `Given ViewModel, When get coin, Then call get coin use case with correct id`() {
        val id = TestHelper.randomString()
        viewModel.getCoin(id)

        coVerify { getCoinUseCase(id) }
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
        val coin = CoinFactory.makeCoin()
        val mappedCoinUi = CoinUiFactory.makeCoinUi()

        coEvery { getCoinUseCase.invoke(coin.id) } returns coin
        every { mapper.map(coin) } returns mappedCoinUi

        viewModel.getCoin(coin.id)

        assertEquals(
            HomeScreenUiState.Success(mappedCoinUi),
            viewModel.uiState.value
        )
    }

    @Test
    fun `Given get coin usecase error, When get coin success, Then show correct data`() {
        val coin = CoinFactory.makeCoin()

        coEvery { getCoinUseCase.invoke(coin.id) } throws Exception()

        viewModel.getCoin(coin.id)

        assert(viewModel.uiState.value is HomeScreenUiState.Error)
    }
}