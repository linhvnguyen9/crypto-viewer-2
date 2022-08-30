package com.linh.cryptoviewer2.data.local.dao

import com.linh.cryptoviewer2.data.local.model.WatchlistItemLocal

interface WatchlistDao {
    suspend fun insert(watchlistItemLocal: WatchlistItemLocal)
    suspend fun getAll(): List<WatchlistItemLocal>
    suspend fun delete(coinId: String)
}