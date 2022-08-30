package com.linh.cryptoviewer2.domain.usecase

import com.linh.cryptoviewer2.domain.model.SearchResult
import com.linh.cryptoviewer2.domain.model.Watchlist
import com.linh.cryptoviewer2.domain.repository.SearchRepository
import com.linh.cryptoviewer2.domain.repository.WatchlistRepository
import com.linh.cryptoviewer2.utils.TestHelper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchUseCaseTest {

    private val searchRepository = mockk<SearchRepository>(relaxed = true)
    private val watchlistRepository = mockk<WatchlistRepository>(relaxed = true)

    private lateinit var useCase: SearchUseCase

    @Before
    fun setup() {
        useCase = SearchUseCase(searchRepository, watchlistRepository)
    }

    @Test
    fun `Given valid query, list of search result and no coins in watchlist, When search, Then get all search result with watchlisted false`() = runTest {
        coEvery { searchRepository.search(any()) } returns SearchResult(
            listOf(
                SearchResult.Coin(
                    id = TestHelper.randomString(),
                    name = TestHelper.randomString(),
                    symbol = TestHelper.randomString(),
                    apiSymbol = TestHelper.randomString(),
                    marketCapRank = TestHelper.randomInt(),
                    thumbUrl = TestHelper.randomString(),
                    largeImageUrl = TestHelper.randomString(),
                    isWatchlisted = TestHelper.randomBoolean()
                )
            )
        )
        coEvery { watchlistRepository.getWatchlist() } returns Watchlist(emptyList())

        val result = useCase(TestHelper.randomString())

        assertFalse(result.coins.map { it.isWatchlisted }.contains(true))
    }

    @Test
    fun `Given valid query, 2 search result and 1 of them in watchlist, When search, Then get the search result item in the watchlist and 1 not watchlisted item`() = runTest {
        val watchlistedCoinId = TestHelper.randomString()
        coEvery { searchRepository.search(any()) } returns SearchResult(
            listOf(
                SearchResult.Coin(
                    id = watchlistedCoinId,
                    name = TestHelper.randomString(),
                    symbol = TestHelper.randomString(),
                    apiSymbol = TestHelper.randomString(),
                    marketCapRank = TestHelper.randomInt(),
                    thumbUrl = TestHelper.randomString(),
                    largeImageUrl = TestHelper.randomString(),
                    isWatchlisted = TestHelper.randomBoolean()
                ),
                SearchResult.Coin(
                    id = TestHelper.randomString(),
                    name = TestHelper.randomString(),
                    symbol = TestHelper.randomString(),
                    apiSymbol = TestHelper.randomString(),
                    marketCapRank = TestHelper.randomInt(),
                    thumbUrl = TestHelper.randomString(),
                    largeImageUrl = TestHelper.randomString(),
                    isWatchlisted = TestHelper.randomBoolean()
                )
            )
        )
        coEvery { watchlistRepository.getWatchlist() } returns Watchlist(listOf(watchlistedCoinId))

        val result = useCase(TestHelper.randomString())

        assertTrue(result.coins[0].isWatchlisted)
        assertFalse(result.coins[1].isWatchlisted)
    }
}