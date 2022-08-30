package com.linh.cryptoviewer2.domain.usecase

import com.linh.cryptoviewer2.domain.model.SearchResult
import com.linh.cryptoviewer2.domain.repository.SearchRepository
import com.linh.cryptoviewer2.domain.repository.WatchlistRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository,
    private val watchlistRepository: WatchlistRepository
) {
    suspend operator fun invoke(query: String): SearchResult {
        val result = repository.search(query)
        val watchlist = HashSet(watchlistRepository.getWatchlist().coinIds)

        return result.copy(coins = result.coins.map {
            it.copy(isWatchlisted = watchlist.contains(it.id))
        })
    }
}