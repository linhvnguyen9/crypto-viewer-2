package com.linh.cryptoviewer2.domain.model

import com.linh.cryptoviewer2.utils.TestHelper.randomDouble
import com.linh.cryptoviewer2.utils.TestHelper.randomInt
import com.linh.cryptoviewer2.utils.TestHelper.randomString

object CoinMarketDataFactory {
    fun makeCoinMarketData() = CoinMarketData(
        id = randomString(),
        symbol = randomString(),
        name = randomString(),
        imageUrl = randomString(),
        marketCapRank = randomInt(),
        currentPrice = randomDouble(),
        priceChangePercentage1h = randomDouble(),
        priceChangePercentage24h = randomDouble(),
        priceChangePercentage7d = randomDouble()
    )
}