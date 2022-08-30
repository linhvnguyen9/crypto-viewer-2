package com.linh.cryptoviewer2.domain.usecase

import com.linh.cryptoviewer2.domain.repository.WatchlistRepository
import javax.inject.Inject

class AddCoinToWatchlistUseCase @Inject constructor(private val repository: WatchlistRepository) {
    suspend operator fun invoke(coinId: String) = repository.addToWatchlist(coinId)
}