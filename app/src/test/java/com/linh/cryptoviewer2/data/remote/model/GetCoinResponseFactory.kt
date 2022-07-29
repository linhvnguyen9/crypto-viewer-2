package com.linh.cryptoviewer2.data.remote.model

import com.linh.cryptoviewer2.data.remote.model.response.GetCoinResponse
import com.linh.cryptoviewer2.utils.TestHelper.randomDouble
import com.linh.cryptoviewer2.utils.TestHelper.randomFloat
import com.linh.cryptoviewer2.utils.TestHelper.randomInt
import com.linh.cryptoviewer2.utils.TestHelper.randomString

object GetCoinResponseFactory {
    fun makeGetCoinResponse() = GetCoinResponse(
        id = randomString(),
        symbol = randomString(),
        name = randomString(),
        image = GetCoinResponse.Image(
            thumbUrl = randomString(),
            smallUrl = randomString(),
            largeUrl = randomString()
        ),
        sentimentsVoteUpPercentage = randomFloat(),
        sentimentsVoteDownPercentage = randomFloat(),
        marketCapRank = randomInt(),
        coinGeckoRank = randomInt(),
        marketData = GetCoinResponse.MarketData(
            currentPrice = GetCoinResponse.MarketData.CurrentPrice(
                usd = randomDouble(),
                vnd = randomDouble(),
                btc = randomDouble()
            ),
            priceChangePercentage24h = randomDouble()
        )
    )
}