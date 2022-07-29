package com.linh.cryptoviewer2.domain.repository

import com.linh.cryptoviewer2.domain.model.Coin
import com.linh.cryptoviewer2.domain.model.CoinMarketData
import com.linh.cryptoviewer2.domain.model.FiatCurrency
import com.linh.cryptoviewer2.domain.model.PriceChangePercentage

interface CoinRepository {

    suspend fun getCoin(id: String): Coin

    suspend fun getCoinsMarketData(fiat: FiatCurrency, coinIds: List<String>, priceChangePercentageFilter: List<PriceChangePercentage>): List<CoinMarketData>
}