package com.linh.cryptoviewer2.presentation.home.model

import app.cash.turbine.test
import com.linh.cryptoviewer2.domain.model.SearchResult
import com.linh.cryptoviewer2.domain.usecase.SearchUseCase
import com.linh.cryptoviewer2.presentation.util.ResourceProvider
import com.linh.cryptoviewer2.utils.TestHelper
import io.mockk.*
import io.mockk.InternalPlatformDsl.toArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {
    private val resourceProvider: ResourceProvider = mockk(relaxed = true)
    private val searchUseCase: SearchUseCase = mockk(relaxed = true)
    private val searchResultToSearchResultUiMapper: SearchResultToSearchResultUiMapper = mockk(relaxed = true)

    private lateinit var viewModel: HomeScreenViewModel

    @Before
    fun setup() {
        viewModel = HomeScreenViewModel(resourceProvider, searchUseCase, searchResultToSearchResultUiMapper)

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

    @Test
    fun `Given no query, When debounce finish but hasn't finished loading, Then show loading state`() = runTest {
        val query = TestHelper.randomString()

        coEvery { searchUseCase.invoke(query) } answers (CoFunctionAnswer {
            delay(1000)
            SearchResult(emptyList())
        })
        coEvery { searchResultToSearchResultUiMapper.map(any()) } returns emptyList()

        viewModel.uiState.test {
            viewModel.uiState.value.onQueryChange(query)

            assertTrue(awaitItem() is HomeScreenUiState.Initial)
            assertTrue(awaitItem() is HomeScreenUiState.Result)
            assertTrue(awaitItem() is HomeScreenUiState.Loading)
            assertTrue(awaitItem() is HomeScreenUiState.Result)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Given no query, When debounce finish but hasn't finished loading, Then show old list while loading`() = runTest {
        val query = TestHelper.randomString()
        val newQuery = TestHelper.randomString()

        println(query)
        println(newQuery)
        val uiResult = listOf(
            SearchResultUi(
                name = TestHelper.randomString(),
                symbol = TestHelper.randomString(),
                thumbUrl = TestHelper.randomString(),
                id = TestHelper.randomString()
            )
        )

        coEvery { searchUseCase.invoke(query) } answers (CoFunctionAnswer {
            delay(100)
            SearchResult(emptyList())
        })
        coEvery { searchResultToSearchResultUiMapper.map(any()) } returns uiResult

        viewModel.uiState.test {
            viewModel.uiState.value.onQueryChange(query)

            assertTrue(awaitItem() is HomeScreenUiState.Initial)
            assertTrue(awaitItem() is HomeScreenUiState.Result)
            assertTrue(awaitItem() is HomeScreenUiState.Loading)
            assertTrue(awaitItem() is HomeScreenUiState.Result)

            viewModel.uiState.value.onQueryChange(newQuery)

            val stateAfterQueryChange = awaitItem()
            assertTrue(stateAfterQueryChange is HomeScreenUiState.Result)
            assertEquals(uiResult, (stateAfterQueryChange as HomeScreenUiState.Result).results)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Given valid query and exception in use case call, When debounce finish, Then show error`() = runTest {
        val query = TestHelper.randomString()

        coEvery { searchUseCase.invoke(query) } throws Exception()
        coEvery { searchResultToSearchResultUiMapper.map(any()) } returns emptyList()

        viewModel.uiState.test {
            viewModel.uiState.value.onQueryChange(query)

            assertTrue(awaitItem() is HomeScreenUiState.Initial)
            assertTrue(awaitItem() is HomeScreenUiState.Result)
            assertTrue(awaitItem() is HomeScreenUiState.Error)

            cancelAndIgnoreRemainingEvents()
        }
    }
}