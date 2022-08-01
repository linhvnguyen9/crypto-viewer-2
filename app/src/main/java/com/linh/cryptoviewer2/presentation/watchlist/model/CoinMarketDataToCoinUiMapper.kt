package com.linh.cryptoviewer2.presentation.watchlist.model

import com.linh.cryptoviewer2.domain.model.CoinMarketData
import com.linh.cryptoviewer2.util.Mapper
import javax.inject.Inject

class CoinMarketDataToCoinUiMapper @Inject constructor(): Mapper<List<CoinMarketData>, List<CoinUi>> {
    override fun map(input: List<CoinMarketData>): List<CoinUi> {
        return input.map {
            with(it) {
                CoinUi(
                    name = name,
                    symbol = symbol.uppercase(),
                    imageUrl = imageUrl,
                    price = currentPrice,
                    priceChangePercentage24h = priceChangePercentage24h
                )
            }
        }
    }
}