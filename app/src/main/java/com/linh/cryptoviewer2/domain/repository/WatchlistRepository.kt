package com.linh.cryptoviewer2.domain.repository

import com.linh.cryptoviewer2.domain.model.Watchlist

interface WatchlistRepository {
    suspend fun addToWatchlist(coinId: String)
    suspend fun removeFrmWatchlist(coinId: String)
    suspend fun getWatchlist(): Watchlist
}