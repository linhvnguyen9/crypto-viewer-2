package com.linh.cryptoviewer2.domain.usecase

import com.linh.cryptoviewer2.domain.model.FiatCurrency
import com.linh.cryptoviewer2.domain.model.PriceChangePercentage
import com.linh.cryptoviewer2.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinMarketDataUseCase @Inject constructor(private val repository: CoinRepository) {
    suspend operator fun invoke(
        fiatCurrency: FiatCurrency,
        coinIds: List<String>,
        filter: List<PriceChangePercentage>
    ) = repository.getCoinsMarketData(fiatCurrency, coinIds, priceChangePercentageFilter = filter)
}