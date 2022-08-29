package com.linh.cryptoviewer2.presentation.home.model

import androidx.lifecycle.viewmodel.compose.viewModel
import com.linh.cryptoviewer2.domain.usecase.GetCoinMarketDataUseCase
import com.linh.cryptoviewer2.domain.usecase.SearchUseCase
import com.linh.cryptoviewer2.presentation.util.ResourceProvider
import com.linh.cryptoviewer2.presentation.watchlist.WatchlistViewModel
import com.linh.cryptoviewer2.presentation.watchlist.model.CoinMarketDataToCoinUiMapper
import com.linh.cryptoviewer2.utils.TestHelper
import io.mockk.coVerify
import io.mockk.coVerifyAll
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {
    private val searchUseCase: SearchUseCase = mockk(relaxed = true)

    private val resourceProvider: ResourceProvider = mockk(relaxed = true)

    private lateinit var viewModel: HomeScreenViewModel

    @Before
    fun setup() {
        viewModel = HomeScreenViewModel(searchUseCase)

        val testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }
    
    @Test
    fun `Given ViewModel, When initialize, Then query must be null`() {
        assertEquals(null, viewModel.uiState.value.searchQuery)
    }

    @Test
    fun `Given no query, When enter query, Then UI state should be updated with query`() {
        val query = TestHelper.randomString()

        viewModel.uiState.value.onQueryChange(query)

        assertEquals(query, viewModel.uiState.value.searchQuery)
    }

    @Test
    fun `Given no query, When enter query and wait until debounce end, Then call search`() = runTest {
        val query = TestHelper.randomString()
        viewModel.uiState.value.onQueryChange(query)

        advanceTimeBy(HomeScreenViewModel.DEBOUNCE_TIME_MILLIS + 1)

        coVerify { searchUseCase.invoke(query) }
    }

    @Test
    fun `Given executed query, When enter new query and wait until debounce end, Then call search with new query`() = runTest {
        val oldQuery = TestHelper.randomString()
        val newQuery = TestHelper.randomString()

        viewModel.uiState.value.onQueryChange(oldQuery)
        advanceUntilIdle()
        viewModel.uiState.value.onQueryChange(newQuery)
        advanceUntilIdle()

        coVerifyAll {
            searchUseCase.invoke(oldQuery)
            searchUseCase.invoke(newQuery)
        }
    }

    @Test
    fun `Given query but hasn't finished debounce, When enter new query and wait for debounce end, Then only call search with newest query`() = runTest {
        val oldQuery = TestHelper.randomString()
        val newQuery = TestHelper.randomString()

        viewModel.uiState.value.onQueryChange(oldQuery)
        advanceTimeBy(HomeScreenViewModel.DEBOUNCE_TIME_MILLIS)
        viewModel.uiState.value.onQueryChange(newQuery)
        advanceUntilIdle()

        coVerify(exactly = 0) {
            searchUseCase.invoke(oldQuery)
        }
        coVerify(exactly = 1) {
            searchUseCase.invoke(newQuery)
        }
    }
}