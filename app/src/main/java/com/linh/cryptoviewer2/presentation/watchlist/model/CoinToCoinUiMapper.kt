package com.linh.cryptoviewer2.presentation.watchlist.model

import com.linh.cryptoviewer2.util.Mapper
import com.linh.cryptoviewer2.domain.model.Coin
import javax.inject.Inject

class CoinToCoinUiMapper @Inject constructor(): Mapper<Coin, CoinUi> {
    override fun map(input: Coin): CoinUi {
        with(input) {
            return CoinUi(
                name = name,
                symbol = symbol.uppercase(),
                imageUrl = image.smallUrl,
                price = currentPrice.usd,
                priceChangePercentage24h = priceChangePercentage24h
            )
        }
    }

    private fun Double.toFormattedPrice() = String.format("$%.2f", this)
}