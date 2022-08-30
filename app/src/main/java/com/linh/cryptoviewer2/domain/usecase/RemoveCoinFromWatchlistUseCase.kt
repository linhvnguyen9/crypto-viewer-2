package com.linh.cryptoviewer2.domain.usecase

import com.linh.cryptoviewer2.domain.repository.WatchlistRepository
import javax.inject.Inject

class RemoveCoinFromWatchlistUseCase @Inject constructor(private val watchlistRepository: WatchlistRepository) {
    suspend operator fun invoke(coinId: String) = watchlistRepository.removeFrmWatchlist(coinId)
}