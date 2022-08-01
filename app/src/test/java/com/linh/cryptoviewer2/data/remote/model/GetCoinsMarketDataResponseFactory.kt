package com.linh.cryptoviewer2.data.remote.model

import com.linh.cryptoviewer2.data.remote.model.response.GetCoinsMarketDataResponse
import com.linh.cryptoviewer2.utils.TestHelper

object GetCoinsMarketDataResponseFactory {
    fun createGetCoinsMarketDataResponse(elements: Int = 1) =
        GetCoinsMarketDataResponse(List(elements) { createGetCoinsMarketDataResponseItem() })

    fun createGetCoinsMarketDataResponseItem() = GetCoinsMarketDataResponse.GetCoinsMarketDataResponseItem(
        ath = TestHelper.randomDouble(),
        athChangePercentage = TestHelper.randomDouble(),
        athDate = TestHelper.randomString(),
        atl = TestHelper.randomDouble(),
        atlChangePercentage = TestHelper.randomDouble(),
        atlDate = TestHelper.randomString(),
        circulatingSupply = TestHelper.randomDouble(),
        currentPrice = TestHelper.randomDouble(),
        fullyDilutedValuation = TestHelper.randomLong(),
        high24h = TestHelper.randomDouble(),
        id = TestHelper.randomString(),
        image = TestHelper.randomString(),
        lastUpdated = TestHelper.randomString(),
        low24h = TestHelper.randomDouble(),
        marketCap = TestHelper.randomLong(),
        marketCapChange24h = TestHelper.randomDouble(),
        marketCapChangePercentage24h = TestHelper.randomDouble(),
        marketCapRank = TestHelper.randomInt(),
        maxSupply = TestHelper.randomDouble(),
        name = TestHelper.randomString(),
        priceChange24h = TestHelper.randomDouble(),
        priceChangePercentage1hInCurrency = TestHelper.randomDouble(),
        priceChangePercentage24h = TestHelper.randomDouble(),
        priceChangePercentage24hInCurrency = TestHelper.randomDouble(),
        priceChangePercentage7dInCurrency = TestHelper.randomDouble(),
        roi = null,
        symbol = TestHelper.randomString(),
        totalSupply = TestHelper.randomDouble(),
        totalVolume = TestHelper.randomLong(),
    )
}