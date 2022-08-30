package com.linh.cryptoviewer2.data.repository

import com.linh.cryptoviewer2.data.local.dao.WatchlistDao
import com.linh.cryptoviewer2.data.local.model.WatchlistItemLocal
import com.linh.cryptoviewer2.domain.model.Watchlist
import com.linh.cryptoviewer2.domain.repository.WatchlistRepository
import javax.inject.Inject

class WatchlistRepositoryImpl @Inject constructor(
    private val watchlistDao: WatchlistDao
): WatchlistRepository {

    override suspend fun addToWatchlist(coinId: String) {
        watchlistDao.insert(WatchlistItemLocal().apply { this.coinId = coinId })
    }

    override suspend fun removeFrmWatchlist(coinId: String) {
        watchlistDao.delete(coinId)
    }

    override suspend fun getWatchlist(): Watchlist {
        val items = watchlistDao.getAll()

        return Watchlist(items.map { it.coinId })
    }
}