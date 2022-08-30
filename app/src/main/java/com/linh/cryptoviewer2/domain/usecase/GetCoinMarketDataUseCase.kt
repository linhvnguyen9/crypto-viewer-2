package com.linh.cryptoviewer2.domain.usecase

import com.linh.cryptoviewer2.domain.model.CoinMarketData
import com.linh.cryptoviewer2.domain.model.FiatCurrency
import com.linh.cryptoviewer2.domain.model.PriceChangePercentage
import com.linh.cryptoviewer2.domain.repository.CoinRepository
import com.linh.cryptoviewer2.domain.repository.WatchlistRepository
import javax.inject.Inject

class GetCoinMarketDataUseCase @Inject constructor(
    private val repository: CoinRepository,
    private val watchlistRepository: WatchlistRepository
) {
    suspend operator fun invoke(
        fiatCurrency: FiatCurrency,
        coinIds: List<String>,
        filter: List<PriceChangePercentage>
    ) = repository.getCoinsMarketData(fiatCurrency, coinIds, priceChangePercentageFilter = filter)

    suspend operator fun invoke(
        fiatCurrency: FiatCurrency,
        filter: List<PriceChangePercentage>
    ) : List<CoinMarketData> {
        val watchlistIds = watchlistRepository.getWatchlist().coinIds

        return repository.getCoinsMarketData(fiatCurrency, watchlistIds, priceChangePercentageFilter = filter)
    }
}