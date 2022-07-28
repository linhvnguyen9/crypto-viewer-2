package com.linh.cryptoviewer2.domain.model

import com.linh.cryptoviewer2.utils.TestHelper

object CoinFactory {
    fun makeCoin(): Coin {
        return Coin(
            id = TestHelper.randomString(),
            symbol = TestHelper.randomString(),
            name = TestHelper.randomString(),
            image = makeImage(),
            sentimentsVoteUpPercentage = TestHelper.randomFloat(),
            sentimentsVoteDownPercentage = TestHelper.randomFloat(),
            marketCapRank = TestHelper.randomInt(),
            coinGeckoRank = TestHelper.randomInt(),
            currentPrice = makeCurrentPrice()
        )
    }

    private fun makeImage() = Coin.Image(
        thumbUrl = TestHelper.randomString(),
        smallUrl = TestHelper.randomString(),
        largeUrl = TestHelper.randomString(),
    )

    private fun makeCurrentPrice() = Coin.CurrentPrice(
        usd = TestHelper.randomDouble(),
        vnd = TestHelper.randomDouble(),
        btc = TestHelper.randomDouble(),
    )
}